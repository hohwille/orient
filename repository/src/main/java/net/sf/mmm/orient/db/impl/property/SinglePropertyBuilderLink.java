/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl.property;

import javax.inject.Inject;
import javax.inject.Named;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.bean.api.OrientLink;
import net.sf.mmm.orient.db.impl.OrientBeanMapper;
import net.sf.mmm.orient.db.impl.ReflectionHelper;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;

/**
 * The implementation of {@link SinglePropertyBuilder} for {@link OType#LINK}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
@Named
public class SinglePropertyBuilderLink extends AbstractLoggableComponent
    implements SinglePropertyBuilder<OrientLink> {

  private static final GenericType<OrientLink> TYPE = new SimpleGenericTypeImpl<>(OrientLink.class);

  private ReflectionUtil reflectionUtil;

  private OrientBeanMapper beanMapper;

  /**
   * The constructor.
   */
  public SinglePropertyBuilderLink() {
    super();
  }

  /**
   * @param reflectionUtil the {@link ReflectionUtil} to {@link Inject}.
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    this.reflectionUtil = reflectionUtil;
  }

  /**
   * @param beanMapper is the OrientBeanMapper to {@link Inject}.
   */
  @Inject
  public void setBeanMapper(OrientBeanMapper beanMapper) {

    this.beanMapper = beanMapper;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtilImpl.getInstance();
    }
    if (this.beanMapper == null) {
      throw new ResourceMissingException("beanMapper");
    }
  }

  @Override
  public OType getType() {

    return OType.LINK;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends WritableProperty<OrientLink>> getPropertyType(OProperty oProperty) {

    return (Class) GenericProperty.class;
  }

  @SuppressWarnings("unchecked")
  @Override
  public GenericType<OrientLink> getValueType(OProperty oProperty) {

    OClass linkedClass = oProperty.getLinkedClass();
    if (linkedClass != null) {
      OrientBean prototype = this.beanMapper.getBeanPrototype(linkedClass);
      if (prototype != null) {
        Class beanClass = prototype.access().getBeanClass();
        GenericType genericType = ReflectionHelper.createLinkType(beanClass);
        return genericType;
      }
    }
    return TYPE;
  }

}
