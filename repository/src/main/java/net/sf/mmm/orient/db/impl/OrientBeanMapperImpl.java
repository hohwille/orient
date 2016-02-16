/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OSchemaProxy;
import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.orient.bean.api.Edge;
import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.bean.api.Vertex;
import net.sf.mmm.orient.datatype.api.OrientLink;
import net.sf.mmm.orient.datatype.impl.OrientLinkDocumentLazy;
import net.sf.mmm.orient.db.impl.property.PropertyBuilder;
import net.sf.mmm.orient.db.impl.property.PropertyBuilderImpl;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;

/**
 * This is the implementation of {@link OrientBeanMapper}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named
public class OrientBeanMapperImpl extends AbstractLoggableComponent implements OrientBeanMapper {

  private final Map<String, OrientClass> name2classMap;

  private final Map<Class<?>, String> beanClass2nameMap;

  private BeanFactory beanFactory;

  private PropertyBuilder propertyBuilder;

  private OrientBean documentPrototype;

  /**
   * The constructor.
   *
   */
  public OrientBeanMapperImpl() {
    super();
    this.name2classMap = new HashMap<>();
    this.beanClass2nameMap = new HashMap<>();
  }

  /**
   * @param propertyBuilder is the {@link PropertyBuilder} to {@link Inject}.
   */
  @Inject
  public void setPropertyBuilder(PropertyBuilder propertyBuilder) {

    getInitializationState().requireNotInitilized();
    this.propertyBuilder = propertyBuilder;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.propertyBuilder == null) {
      this.propertyBuilder = PropertyBuilderImpl.getInstance();
    }
    for (OrientClass orientClass : this.name2classMap.values()) {
      initClass(orientClass);
    }
    this.documentPrototype = this.beanFactory.createPrototype(OrientBean.class, true, "Document");
  }

  /**
   * {@link #registerBean(Class) Registers} the default {@link OrientBean}s for the standard schema types.
   */
  public void registerDefaults() {

    registerBean(Vertex.class);
    registerBean(Edge.class);
  }

  /**
   * @param beanClass the {@link OrientBean} to register.
   */
  public void registerBean(Class<? extends OrientBean> beanClass) {

    getInitializationState().requireNotInitilized();
    register(beanClass);
  }

  private OrientClass register(Class<? extends OrientBean> beanClass) {

    OrientBean prototype = this.beanFactory.createPrototype(beanClass, true);
    String name = prototype.access().getName();
    OrientClass orientClass = new OrientClass(prototype);
    OrientClass old = this.name2classMap.put(name, orientClass);
    if (old != null) {
      throw new DuplicateObjectException(orientClass, name, old);
    }
    this.beanClass2nameMap.put(beanClass, name);
    return orientClass;
  }

  @Override
  public void syncSchema(OSchemaProxy schema) {

    OrientBeanMapper.super.syncSchema(schema);

    // two-way-sync
    for (OrientClass orientClass : this.name2classMap.values()) {
      syncClass(orientClass, schema);
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private void initClass(OrientClass orientClass) {

    if (orientClass.isSuperClassesInitialized()) {
      return;
    }
    OrientBean prototype = orientClass.getPrototype();
    BeanAccess access = prototype.access();
    Class<?> beanClass = access.getBeanClass();
    Class<?>[] superInterfaces = beanClass.getInterfaces();
    List<OrientClass> superClasses = orientClass.getSuperClasses();
    for (Class<?> superBeanClass : superInterfaces) {
      if (OrientBean.class.isAssignableFrom(superBeanClass)) {
        String superName = this.beanClass2nameMap.get(superBeanClass);
        OrientClass superOrientClass;
        if (superName == null) {
          superOrientClass = register((Class) superBeanClass);
          initClass(superOrientClass);
        } else {
          superOrientClass = this.name2classMap.get(superName);
        }
        superClasses.add(superOrientClass);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private OClass syncClass(OrientClass orientClass, OSchemaProxy schema) {

    OrientBean prototype = orientClass.getPrototype();
    BeanAccess access = prototype.access();
    String name = access.getName();
    OClass oClass = schema.getClass(name);
    if (oClass == null) {
      Class<?> beanClass = access.getBeanClass();
      if (OrientBean.class.equals(beanClass)) {
        throw new IllegalStateException();
      }
      List<OrientClass> superClasses = orientClass.getSuperClasses();
      OClass[] oClasses = new OClass[superClasses.size()];
      int i = 0;
      for (OrientClass superClass : superClasses) {
        oClasses[i++] = syncClass(superClass, schema);
      }
      oClass = schema.createClass(name, oClasses);
    }
    return oClass;
  }

  @Override
  public void syncClass(OClass oClass) {

    String name = oClass.getName();
    OrientClass orientClass = this.name2classMap.get(name);
    if (orientClass == null) {
      orientClass = createDynamicClass(oClass);
    }
    OClass currentOClass = orientClass.getOClass();
    if (currentOClass == null) {
      orientClass.setOClass(oClass);
    } else if (!currentOClass.equals(oClass)) {
      throw new IllegalStateException("OClass changed for " + orientClass);
    }
    OrientBean prototype = orientClass.getPrototype();
    for (OProperty oProperty : oClass.properties()) {
      this.propertyBuilder.build(oProperty, prototype);
    }
  }

  private OrientClass createDynamicClass(OClass oClass) {

    OrientClass orientClass;
    List<OClass> superClasses = oClass.getSuperClasses();
    Class<? extends OrientBean> superBeanClass = OrientBean.class;
    for (OClass superClass : superClasses) {
      String superName = superClass.getName();
      if (superName.equals(Vertex.NAME)) {
        superBeanClass = Vertex.class;
      } else if (superName.equals(Edge.NAME)) {
        superBeanClass = Edge.class;
      }
    }
    String name = oClass.getName();
    OrientBean prototype = this.beanFactory.createPrototype(superBeanClass, true, name);
    orientClass = new OrientClass(prototype);
    orientClass.setOClass(oClass);
    for (OClass superClass : superClasses) {
      String superName = superClass.getName();
      OrientClass superOrientClass = this.name2classMap.get(superName);
      if (superOrientClass == null) {
        superOrientClass = createDynamicClass(superClass);
      }
      orientClass.getSuperClasses().add(superOrientClass);
    }
    this.name2classMap.put(name, orientClass);
    return orientClass;
  }

  @Override
  public OrientBean getBeanPrototype(OClass oClass) {

    if (oClass == null) {
      return this.documentPrototype;
    }
    OrientClass orientClass = this.name2classMap.get(oClass.getName());
    if (orientClass == null) {
      orientClass = createDynamicClass(oClass);
    }
    return orientClass.getPrototype();
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <BEAN extends OrientBean> BEAN map2Bean(ODocument document, BEAN bean) {

    if (document == null) {
      return bean;
    }
    OrientBean result = bean;
    OrientBean prototype = getBeanPrototype(document.getSchemaClass());
    if (result == null) {
      result = this.beanFactory.create(prototype);
    } else {
      Class<? extends Bean> beanClass = bean.access().getBeanClass();
      Class<? extends Bean> prototypeClass = prototype.access().getBeanClass();
      if (beanClass.equals(prototypeClass)) {
        throw new ObjectMismatchException(beanClass, prototypeClass);
      }
    }
    BeanAccess access = result.access();
    for (Entry<String, Object> entry : document) {
      String propertyName = entry.getKey();
      Object value = entry.getValue();
      WritableProperty property = access.getProperty(propertyName);
      if ((property == null) && (value != null)) {
        Class<?> valueClass;
        if (value instanceof ODocument) {
          valueClass = OrientLink.class;
        } else {
          valueClass = value.getClass();
        }
        access.createProperty(propertyName, new SimpleGenericTypeImpl<>(valueClass));
      }
      if (property != null) {
        if (OrientLink.class.isAssignableFrom(property.getType().getRetrievalClass())) {
          property.setValue(OrientLinkDocumentLazy.valueOf((ODocument) value, this));
        } else {
          property.setValue(value);
        }
      }
    }
    return (BEAN) result;
  }

  @Override
  public ODocument map2Document(OrientBean bean, ODocument document) {

    if (bean == null) {
      return document;
    }
    BeanAccess access = bean.access();
    ODocument result = document;
    if (result == null) {
      String id = bean.getId();
      if (id != null) {
        result = new ODocument(access.getName(), new ORecordId(id));
      } else {
        result = new ODocument(access.getName());
      }
    }
    for (ReadableProperty<?> property : access.getProperties()) {
      Object value = property.getValue();
      result.field(property.getName(), value);
    }
    return result;
  }

}
