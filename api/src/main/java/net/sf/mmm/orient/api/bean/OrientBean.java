/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.api.bean;

import net.sf.mmm.bean.api.entity.EntityBean;
import net.sf.mmm.property.api.WritableProperty;
import net.sf.mmm.util.data.api.id.Id;

/**
 * This is the interface for an {@link EntityBean} used with OrientDB. Simply create a sub-interface of this
 * {@link OrientBean} interface for each {@link com.orientechnologies.orient.core.metadata.schema.OClass
 * OrientDB class} you want to map. Within the interface define a {@link WritableProperty property}-method for
 * each {@link com.orientechnologies.orient.core.metadata.schema.OProperty OrientDB property} you want to
 * access in a type-safe way. You can also access all properties via
 * {@link net.sf.mmm.bean.api.BeanAccess#getProperty(String)}. <br/>
 * <b>NOTE:</b><br/>
 * Never implement an {@link OrientBean} interface. See {@link net.sf.mmm.bean.api.Bean} and
 * {@code OrientBeanRepository.newEntity()} for further details.
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface OrientBean extends EntityBean {

  /** {@link net.sf.mmm.bean.api.BeanAccess#getPropertyNameForAlias(String) alias} for the {@link #Id()}. */
  String PROPERTY_ALIAS_ID = "@rid";

  /**
   * {@link net.sf.mmm.bean.api.BeanAccess#getPropertyNameForAlias(String) alias} for the
   * {@link Id#getVersion() version}.
   */
  String PROPERTY_ALIAS_VERSION = "@version";

  // @Named(PROPERTY_ALIAS_ID)
  @Override
  WritableProperty<Id<?>> Id();

}
