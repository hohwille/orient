/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Named;

import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OSchemaProxy;
import com.orientechnologies.orient.core.record.ORecordInternal;
import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.orient.api.bean.Edge;
import net.sf.mmm.orient.api.bean.OrientBean;
import net.sf.mmm.orient.api.bean.Vertex;
import net.sf.mmm.orient.api.mapping.OrientBeanMapper;
import net.sf.mmm.orient.base.id.OrientId;
import net.sf.mmm.orient.impl.property.PropertyBuilder;
import net.sf.mmm.orient.impl.property.PropertyBuilderImpl;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.base.mapping.AbstractDocumentBeanMapper;
import net.sf.mmm.util.data.api.entity.Entity;
import net.sf.mmm.util.data.api.id.Id;
import net.sf.mmm.util.data.api.link.Link;
import net.sf.mmm.util.data.base.link.IdLink;
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
public class OrientBeanMapperImpl extends AbstractDocumentBeanMapper<ODocument, OrientBean, OrientBeanMapperImpl.OrientClass> implements OrientBeanMapper {

  private static final String PACKAGE_PREFIX = "net.sf.mmm.orient.";

  private static final Bean[] NO_BEANS = new Bean[0];

  private final Map<Integer, OrientClass> clusterId2OrientClassMap;

  private PropertyBuilder propertyBuilder;

  private ReflectionUtil reflectionUtil;

  private OrientBean documentPrototype;

  private OrientMappingConfigProperties config;

  /**
   * The constructor.
   *
   */
  public OrientBeanMapperImpl() {
    super();
    this.clusterId2OrientClassMap = new ConcurrentHashMap<>();
  }

  /**
   * @param propertyBuilder the {@link PropertyBuilder} to {@link Inject}.
   */
  @Inject
  public void setPropertyBuilder(PropertyBuilder propertyBuilder) {

    getInitializationState().requireNotInitilized();
    this.propertyBuilder = propertyBuilder;
  }

  /**
   * @param reflectionUtil the {@link ReflectionUtil} to {@link Inject}.
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    this.reflectionUtil = reflectionUtil;
  }

  /**
   * @param config the {@link OrientMappingConfigProperties} to {@link Inject}.
   */
  @Inject
  public void setConfig(OrientMappingConfigProperties config) {

    this.config = config;
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
    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtilImpl.getInstance();
    }
    if (this.config != null) {
      Set<String> classNames = new HashSet<>();
      for (String packageName : this.config.getPackagesToScan()) {
        this.reflectionUtil.findClassNames(packageName, true, classNames);
      }
      Set<Class<?>> classes = this.reflectionUtil.loadClasses(classNames, (c) -> OrientBean.class.isAssignableFrom(c));
      for (Class orientClass : classes) {
        register(orientClass);
      }
    }
    List<OrientClass> classes = new ArrayList<>(getMappings());
    for (OrientClass orientClass : classes) {
      initClass(orientClass);
    }
    this.documentPrototype = getBeanPrototypeBuilder().createPrototype(OrientBean.class, "Document");
  }

  @SuppressWarnings("unchecked")
  private void initClass(OrientClass orientClass) {

    if (orientClass.isSuperClassesInitialized()) {
      return;
    }
    getLogger().debug("Initializing mapping for {}", orientClass);
    BeanAccess access = orientClass.getPrototype().access();
    Class<?> beanClass = access.getBeanClass();
    Class<?>[] superInterfaces = beanClass.getInterfaces();
    List<OrientClass> superClasses = orientClass.getSuperClasses();
    for (Class<?> superBeanClass : superInterfaces) {
      if (OrientBean.class.isAssignableFrom(superBeanClass) && (OrientBean.class != superBeanClass)) {
        OrientBean prototype = getBeanPrototypeBuilder().getOrCreatePrototype((Class<? extends OrientBean>) superBeanClass);
        OrientClass superOrientClass = getOrCreateMapping(prototype);
        superClasses.add(superOrientClass);
      }
    }
  }

  /**
   * {@link #registerBean(Class) Registers} the default {@link OrientBean}s for the standard schema types.
   */
  protected void registerDefaults() {

    registerBean(Vertex.class);
    registerBean(Edge.class);
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
    OrientBean prototype = getBeanPrototypeBuilder().getOrCreatePrototype(beanClass);
    OrientClass orientClass = new OrientClass(prototype);
    addMapping(orientClass);
    return orientClass;
  }

  @Override
  public OrientBean getBeanPrototype(int clusterId) {

    Integer key = Integer.valueOf(clusterId);
    OrientClass orientClass = this.clusterId2OrientClassMap.get(key);
    if (orientClass == null) {
      return null;
      // throw new ObjectNotFoundException(OrientClass.class, key);
    }
    return orientClass.getPrototype();
  }

  @Override
  public void syncSchema(OSchemaProxy schema) {

    getLogger().debug("Synchronizing OrientDB schema started...");
    // two-way-sync:
    // 1. create missing OClasses for Java OrientBean model
    for (OrientClass orientClass : getMappings()) {
      syncClass(orientClass, schema);
    }
    getLogger().debug("Creation of missing OClasses completed...");
    // 2. sync OrientDB schema with Java OrientBean model (including two-way-sync of properties)
    for (OClass oclass : schema.getClasses()) {
      if (!"OSchedule".equals(oclass.getName())) {
        syncClass(oclass);
      }
    }
    schema.reload();
    getLogger().debug("Synchronizing OrientDB schema completed...");
  }

  private OClass syncClass(OrientClass orientClass, OSchemaProxy schema) {

    // only create OClass if not exists, OProperty created afterwards in syncClass(OClass)
    BeanAccess access = orientClass.getPrototype().access();
    String name = access.getSimpleName();
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
      if (isAbstract(orientClass.getPrototype())) {
        oClass.setAbstract(true);
      }
      schema.reload();
    }
    return oClass;
  }

  @Override
  public void syncClass(OClass oClass) {

    OrientClass orientClass = getOrCreateMapping(oClass);
    OClass currentOClass = orientClass.getOClass();
    if (currentOClass == null) {
      orientClass.setOClass(oClass);
    } else if (!currentOClass.equals(oClass)) {
      throw new IllegalStateException("OClass changed for " + orientClass);
    }
    for (int clusterId : oClass.getClusterIds()) {
      this.clusterId2OrientClassMap.put(Integer.valueOf(clusterId), orientClass);
    }
    BeanAccess access = orientClass.getPrototype().access();
    Set<String> propertyNames;
    if (access.isVirtual()) {
      propertyNames = Collections.emptySet();
    } else {
      propertyNames = new HashSet<>(access.getDeclaredPropertyNames());
    }
    // for each property in OrientDB class create corresponding property in according OrientBean
    for (OProperty oProperty : oClass.properties()) {
      if ((oProperty.getOwnerClass() == oClass) && isAcceptedProperty(oProperty)) {
        WritableProperty<?> property = this.propertyBuilder.build(oProperty, orientClass.getPrototype());
        if (property != null) {
          propertyNames.remove(property.getName());
        }
      }
    }
    if (!access.getBeanClass().getPackage().equals(Vertex.class.getPackage())) {
      // for each property in OrinetBean prototype create corresponding property in according OrientDB class
      for (String propertyName : propertyNames) {
        WritableProperty<?> property = access.getProperty(propertyName);
        this.propertyBuilder.build(property, oClass);
      }
    }
  }

  private boolean isAcceptedProperty(OProperty oProperty) {

    String name = oProperty.getName();
    if (name.startsWith("_")) {
      return false;
    }
    if (name.equals(OrientBean.PROPERTY_ALIAS_ID) || name.equals(OrientBean.PROPERTY_ALIAS_VERSION) || name.equals(Entity.PROPERTY_NAME_ID)) {
      getLogger().debug("Ignoring sync of property {}.{} because the name is reserved.", oProperty.getOwnerClass().getName(), name);
      return false;
    }
    return true;
  }

  private boolean isAbstract(OrientBean prototype) {

    if (prototype.access().getSimpleName().startsWith("Abstract")) {
      return true;
    }
    return false;
  }

  private OrientClass createMapping(OClass oClass) {

    OrientClass orientClass;
    OrientBean superPrototype = null;
    List<OrientClass> superOrientClasses = null;
    List<OClass> superClasses = oClass.getSuperClasses();
    Bean[] mixins = NO_BEANS;
    if ((superClasses != null) && (!superClasses.isEmpty())) {
      mixins = new Bean[superClasses.size() - 1];
      superOrientClasses = new ArrayList<>(superClasses.size());
      int mixinIndex = 0;
      for (OClass superClass : superClasses) {
        OrientClass superOrientClass = getOrCreateMapping(superClass);
        superOrientClasses.add(superOrientClass);
        if (superPrototype == null) {
          superPrototype = superOrientClass.getPrototype();
        } else {
          mixins[mixinIndex++] = superOrientClass.getPrototype();
        }
      }
    } else {
      superPrototype = this.documentPrototype;
    }
    String name = oClass.getName();
    String qualifiedName = PACKAGE_PREFIX + name;
    OrientBean prototype = getBeanPrototypeBuilder().createPrototype(superPrototype, qualifiedName, mixins);
    orientClass = new OrientClass(prototype);
    orientClass.setOClass(oClass);
    if (superOrientClasses != null) {
      orientClass.getSuperClasses().addAll(superOrientClasses);
    }
    return orientClass;
  }

  @Override
  public OrientBean getBeanPrototype(OClass oClass) {

    if (oClass == null) {
      return this.documentPrototype;
    }
    OrientClass orientClass = getOrCreateMapping(oClass);
    return orientClass.getPrototype();
  }

  @Override
  public <B extends OrientBean> B getBeanPrototype(Class<B> type) {

    return getBeanPrototypeBuilder().getOrCreatePrototype(type);
  }

  @Override
  public OClass getOClass(Class<? extends OrientBean> beanClass) {

    OrientClass orientClass = getOrCreateMapping(beanClass);
    if (orientClass == null) {
      return null;
    }
    return orientClass.getOClass();
  }

  @Override
  protected void mapPropertiesFromBean(OrientBean bean, ODocument document) {

    BeanAccess access = bean.access();
    for (ReadableProperty<?> property : access.getProperties()) {
      String propertyName = property.getName();
      if (!Entity.PROPERTY_NAME_ID.equals(propertyName)) {
        Object value = property.getValue();
        document.field(propertyName, value);
      }
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected void mapPropertiesToBean(ODocument document, OrientBean bean) {

    OrientBean prototype = getBeanPrototype(document.getSchemaClass());
    BeanAccess access = bean.access();
    Class<? extends Bean> beanClass = access.getBeanClass();
    Class<? extends Bean> prototypeClass = prototype.access().getBeanClass();
    if (!beanClass.equals(prototypeClass)) {
      throw new ObjectMismatchException(beanClass, prototypeClass);
    }
    for (Entry<String, Object> entry : document) {
      String propertyName = entry.getKey();
      Object value = entry.getValue();
      WritableProperty property = access.getProperty(propertyName);
      if ((property == null) && (value != null)) {
        Class<?> valueClass;
        if (value instanceof ODocument) {
          valueClass = Link.class;
        } else {
          valueClass = value.getClass();
        }
        access.createProperty(propertyName, new SimpleGenericTypeImpl<>(valueClass));
      }
      if (property != null) {
        if (Link.class.isAssignableFrom(property.getType().getRetrievalClass()) && (value != null)) {
          final ODocument oDocument = (ODocument) value;
          Id id = OrientId.of(access.getBeanClass(), oDocument.getIdentity(), document.getVersion());
          IdLink<?> link = IdLink.valueOf(id, x -> toBean(oDocument));
          property.setValue(link);
        } else {
          property.setValue(value);
        }
      }
    }
    ORID identity = document.getIdentity();
    if (identity != null) {
      Id<?> id = OrientId.of(access.getBeanClass(), identity, document.getVersion());
      bean.setId(id);
    }
  }

  @Override
  protected String getKey(OrientBean bean) {

    return bean.access().getSimpleName();
  }

  @Override
  protected String getKey(ODocument document) {

    return getKey(document.getSchemaClass());
  }

  /**
   * @see #getKey(ODocument)
   * @param oClass the {@link OClass}.
   * @return the key.
   */
  protected String getKey(OClass oClass) {

    if (oClass == null) {
      return "Document";
    }
    return oClass.getName();
  }

  /**
   * @param oClass the {@link OClass}.
   * @return the {@link OrientClass}.
   */
  protected OrientClass getOrCreateMapping(OClass oClass) {

    String key = getKey(oClass);
    return getOrCreateMapping(key, x -> createMapping(oClass));
  }

  /**
   * @param type the {@link Class} reflecting the {@link OrientBean}.
   * @return the {@link OrientClass}.
   */
  protected OrientClass getOrCreateMapping(Class<? extends OrientBean> type) {

    OrientBean prototype = getBeanPrototypeBuilder().getOrCreatePrototype(type);
    return getOrCreateMapping(prototype);
  }

  @Override
  protected OrientClass createMapping(OrientBean bean) {

    OrientBean prototype = getBeanFactory().getPrototype(bean);
    OrientClass mapping = new OrientClass(prototype);
    initClass(mapping);
    return mapping;
  }

  @Override
  protected OrientClass createMapping(String key, ODocument document) {

    OClass schemaClass = document.getSchemaClass();
    return createMapping(schemaClass);
  }

  /**
   * @param id the {@link Id} to convert.
   * @return the {@link Id} as {@link ORID}.
   */
  public ORID convertId(Id<? extends OrientBean> id) {

    if (id == null) {
      return null;
    }
    if (id instanceof OrientId) {
      return ((OrientId<?>) id).getOrid();
    } else {
      OrientClass orientClass = getOrCreateMapping(id.getType());
      int clusterId = orientClass.oClass.getDefaultClusterId();
      return new ORecordId(clusterId, id.getId());
    }
  }

  /**
   * Simple container for {@link OrientBean} meta-data.
   */
  protected class OrientClass extends AbstractDocumentBeanMapper.Mapping<ODocument, OrientBean> {

    /** The OrientC */
    private List<OrientClass> superClasses;

    private OClass oClass;

    /**
     * The constructor.
     *
     * @param prototype the {@link Bean} prototype.
     */
    public OrientClass(OrientBean prototype) {
      super(prototype);
    }

    /**
     * @return the {@link OClass}.
     */
    public OClass getOClass() {

      return this.oClass;
    }

    /**
     * @param oClass the {@link OClass} to set.
     */
    public void setOClass(OClass oClass) {

      if (this.oClass != null) {
        throw new IllegalStateException("OClass can be set only once: " + getPrototype().access().getSimpleName());
      }
      this.oClass = oClass;
    }

    /**
     * @return {@code true} if {@code getSuperClasses()} has already been called, {@code false} otherwise.
     */
    public boolean isSuperClassesInitialized() {

      return (this.superClasses != null);
    }

    /**
     * @return the {@link List} of super classes.
     */
    public List<OrientClass> getSuperClasses() {

      if (this.superClasses == null) {
        this.superClasses = new ArrayList<>();
      }
      return this.superClasses;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ODocument newDocument(OrientBean bean) {

      Id<? extends OrientBean> id = (Id<? extends OrientBean>) bean.getId();
      String className = bean.access().getSimpleName();
      ODocument document;
      if (id != null) {
        document = new ODocument(className, convertId(id));
        ORecordInternal.setVersion(document.getRecord(), (int) id.getVersion());
      } else {
        document = new ODocument(className);
      }
      return document;
    }
  }

}
