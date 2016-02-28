/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import net.sf.mmm.util.bean.impl.BeanFactoryImpl;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
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

  private ReflectionUtil reflectionUtil;

  private OrientBean documentPrototype;

  private List<String> packagesToScan;

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

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.propertyBuilder == null) {
      PropertyBuilderImpl impl = new PropertyBuilderImpl();
      impl.setBeanMapper(this);
      impl.initialize();
      this.propertyBuilder = impl;
    }
    if (this.beanFactory == null) {
      this.beanFactory = BeanFactoryImpl.getInstance();
    }
    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtilImpl.getInstance();
    }
    if (this.packagesToScan != null) {
      Set<String> classNames = new HashSet<>();
      for (String packageName : this.packagesToScan) {
        this.reflectionUtil.findClassNames(packageName, true, classNames);
      }
      Set<Class<?>> classes = this.reflectionUtil.loadClasses(classNames,
          (c) -> OrientBean.class.isAssignableFrom(c));
      for (Class orientClass : classes) {
        register(orientClass);
      }
    }
    List<OrientClass> classes = new ArrayList<>(this.name2classMap.values());
    for (OrientClass orientClass : classes) {
      initClass(orientClass);
    }
    this.documentPrototype = this.beanFactory.createPrototype(OrientBean.class, true, "Document");
  }

  /**
   * {@link #registerBean(Class) Registers} the default {@link OrientBean}s for the standard schema types.
   */
  protected void registerDefaults() {

    registerBean(Vertex.class);
    registerBean(Edge.class);
  }

  /**
   * @param packages the {@link List} of {@link Package packages} to scan for {@link OrientBean}-{@link Class}es.
   */
  public void setPackagesToScan(List<String> packages) {

    this.packagesToScan = packages;
  }

  /**
   * @param packages the {@link List} of {@link Package packages} to scan for {@link OrientBean}-{@link Class}es.
   */
  public void addPackagesToScan(List<String> packages) {

    if (this.packagesToScan == null) {
      this.packagesToScan = new ArrayList<>();
    }
    this.packagesToScan.addAll(packages);
  }

  /**
   * @param packages the array of {@link Package packages} to scan for {@link OrientBean}-{@link Class}es.
   */
  public void addPackagesToScan(String... packages) {

    addPackagesToScan(Arrays.asList(packages));
  }

  /**
   * @param beanClass the {@link OrientBean} to register.
   */
  protected void registerBean(Class<? extends OrientBean> beanClass) {

    getInitializationState().requireNotInitilized();
    register(beanClass);
  }

  private OrientClass register(Class<? extends OrientBean> beanClass) {

    if (beanClass == OrientBean.class) {
      return null;
    }
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

    getLogger().debug("Synchronizing OrientDB schema started...");
    // two-way-sync:
    // 1. create missing OClasses for Java OrientBean model
    for (OrientClass orientClass : this.name2classMap.values()) {
      syncClass(orientClass, schema);
    }
    getLogger().debug("Creation of missing OClasses completed...");
    // 2. sync OrientDB schema with Java OrientBean model (including two-way-sync of properties)
    OrientBeanMapper.super.syncSchema(schema);
    getLogger().debug("Synchronizing OrientDB schema completed...");
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
      if (OrientBean.class.isAssignableFrom(superBeanClass) && (OrientBean.class != superBeanClass)) {
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

  private OClass syncClass(OrientClass orientClass, OSchemaProxy schema) {

    // only create OClass if not exists, OProperty created afterwards in syncClass(OClass)
    OrientBean prototype = orientClass.getPrototype();
    BeanAccess access = prototype.access();
    String name = access.getName();
    OClass oClass = schema.getClass(name);
    if (oClass == null) {
      Class<?> beanClass = access.getBeanClass();
      if (OrientBean.class.equals(beanClass)) {
        throw new IllegalStateException(name);
      }
      List<OrientClass> superClasses = orientClass.getSuperClasses();
      OClass[] oClasses = new OClass[superClasses.size()];
      int i = 0;
      for (OrientClass superClass : superClasses) {
        oClasses[i++] = syncClass(superClass, schema);
      }
      oClass = schema.createClass(name, oClasses);
      if (isAbstract(prototype)) {
        oClass.setAbstract(true);
      }
    }
    return oClass;
  }

  private boolean isAbstract(OrientBean prototype) {

    if (prototype.access().getName().startsWith("Abstract")) {
      return true;
    }
    return false;
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
    BeanAccess access = prototype.access();
    Set<String> propertyNames = new HashSet<>(access.getPropertyNames());
    // for each property in OrientDB class create corresponding property in according OrientBean
    for (OProperty oProperty : oClass.properties()) {
      WritableProperty<?> property = this.propertyBuilder.build(oProperty, prototype);
      if (property != null) {
        propertyNames.remove(property.getName());
      }
    }
    // for each property in OrinetBean prototype create corresponding property in according OrientDB class
    for (String propertyName : propertyNames) {
      WritableProperty<?> property = access.getProperty(propertyName);
      this.propertyBuilder.build(property, oClass);
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

  @Override
  public OClass getOClass(Class<? extends OrientBean> beanClass) {

    String beanName = this.beanClass2nameMap.get(beanClass);
    OrientClass orientClass = this.name2classMap.get(beanName);
    if (orientClass == null) {
      return null;
    }
    return orientClass.getOClass();
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
