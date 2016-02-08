/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.orient.bean.api.Edge;
import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.bean.api.OrientLink;
import net.sf.mmm.orient.bean.api.Vertex;
import net.sf.mmm.orient.bean.impl.OrientLinkDocumentLazy;
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

  private final Map<String, OrientBean> name2prototypeMap;

  private BeanFactory beanFactory;

  private PropertyBuilder propertyBuilder;

  /**
   * The constructor.
   *
   */
  public OrientBeanMapperImpl() {
    super();
    this.name2prototypeMap = new HashMap<>();
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
  }

  /**
   * {@link #registerBean(Class) Registers} the default {@link OrientBean}s for the standard schema types.
   */
  public void registerDefaults() {

    registerBean(Vertex.class);
    registerBean(Edge.class);
  }

  /**
   * @param bean the {@link OrientBean} to register.
   */
  public void registerBean(Class<? extends OrientBean> bean) {

    OrientBean prototype = this.beanFactory.createPrototype(bean, true);
    String name = prototype.access().getName();
    OrientBean old = this.name2prototypeMap.put(name, prototype);
    if (old != null) {
      throw new DuplicateObjectException(prototype, name, old);
    }
  }

  @Override
  public void syncClass(OClass oClass) {

    String name = oClass.getName();
    OrientBean prototype = this.name2prototypeMap.get(name);
    if (prototype == null) {
      // TODO...
      return;
    }
    for (OProperty oProperty : oClass.properties()) {
      this.propertyBuilder.build(oProperty, prototype);
    }
  }

  @Override
  public OrientBean getBeanPrototype(OClass oClass) {

    OrientBean prototype = this.name2prototypeMap.get(oClass.getName());
    return prototype;
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
      result.field(property.getName(), property.getValue());
    }
    return result;
  }

}
