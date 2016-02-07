/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Named;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.db.impl.property.PropertyBuilder;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the implementation of {@link OrientBeanMapper}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named
public class OrientBeanMapperImpl extends AbstractLoggableComponent implements OrientBeanMapper {

  private final Map<OType, GenericType<?>> typeMap;

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
    this.typeMap = new HashMap<>();
  }

  //
  // /**
  // * TODO: javadoc
  // *
  // * @return
  // */
  // protected void initTypeMap(Map<OType, GenericType<?>> map) {
  //
  // map.put(OType.BOOLEAN, this.reflectionUtil.createGenericType(Boolean.class));
  // map.put(OType.BYTE, this.reflectionUtil.createGenericType(Byte.class));
  // map.put(OType.DATE, this.reflectionUtil.createGenericType(LocalDate.class));
  // map.put(OType.DATETIME, this.reflectionUtil.createGenericType(Instant.class));
  // map.put(OType.DECIMAL, this.reflectionUtil.createGenericType(BigDecimal.class));
  // map.put(OType.DOUBLE, this.reflectionUtil.createGenericType(Double.class));
  // map.put(OType.EMBEDDED, this.reflectionUtil.createGenericType(OrientBean.class));
  // // map.put(OType.EMBEDDEDLIST, this.reflectionUtil.createGenericType(List<OrientBean>.class));
  // // map.put(OType.EMBEDDEDMAP, this.reflectionUtil.createGenericType(Map<String, OrientBean>.class));
  // // map.put(OType.EMBEDDEDSET, this.reflectionUtil.createGenericType(Set<OrientBean>.class));
  // map.put(OType.FLOAT, this.reflectionUtil.createGenericType(Float.class));
  // map.put(OType.INTEGER, this.reflectionUtil.createGenericType(Integer.class));
  // map.put(OType.LONG, this.reflectionUtil.createGenericType(Long.class));
  // map.put(OType.SHORT, this.reflectionUtil.createGenericType(Short.class));
  // map.put(OType.STRING, this.reflectionUtil.createGenericType(String.class));
  // // map.put(OType.TRANSIENT, this.reflectionUtil.createGenericType(Integer.class));
  // map.put(OType.BINARY, this.reflectionUtil.createGenericType(byte[].class));
  // map.put(OType.ANY, this.reflectionUtil.createGenericType(Object.class));
  // }

  // protected final void registerDefaultType(OType type, Map<OType, GenericType<?>> map) {
  //
  // map.put(type, this.reflectionUtil.createGenericType(type.getDefaultJavaType()));
  // }

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

  @SuppressWarnings("unchecked")
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
      access.setPropertyValue(entry.getKey(), entry.getValue(), null);
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
      result = new ODocument(access.getName());
    }
    for (ReadableProperty<?> property : access.getProperties()) {
      result.field(property.getName(), property.getValue());
    }
    return result;
  }

  // private GenericType<?> getGenericType(OProperty oProperty) {
  //
  // OType type = oProperty.getType();
  // switch (type) {
  // case LINK:
  // return createLinkType(oProperty);
  // case LINKLIST:
  // GenericType<?> linkType = createLinkType(oProperty);
  // if (linkType != null) {
  // return this.reflectionUtil.createGenericTypeOfList(linkType);
  // }
  // default:
  // break;
  // }
  // return null;
  // }

  // @SuppressWarnings({ "rawtypes", "unchecked" })
  // private GenericType<?> createLinkType(OProperty oProperty) {
  //
  // OClass linkedClass = oProperty.getLinkedClass();
  // if (linkedClass != null) {
  // OrientBean prototype = this.name2prototypeMap.get(linkedClass.getName());
  // if (prototype != null) {
  // Class<? extends OrientBean> beanClass = (Class) prototype.access().getBeanClass();
  // return ReflectionHelper.createLinkType(beanClass, this.reflectionUtil);
  // }
  // }
  // return null;
  // }

}
