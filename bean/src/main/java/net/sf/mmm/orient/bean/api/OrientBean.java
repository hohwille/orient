/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.bean.api;

import javax.inject.Named;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.EntityBean;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for an {@link EntityBean} used with OrientDB. Simply create a sub-interface of this
 * {@link OrientBean} interface for each {@link com.orientechnologies.orient.core.metadata.schema.OClass OrientDB class}
 * you want to map. Within the interface define a {@link WritableProperty property}-method for each
 * {@link com.orientechnologies.orient.core.metadata.schema.OProperty OrientDB property} you want to access in a
 * type-safe way. You can also access all properties via {@link BeanAccess#getProperty(String)}. <br/>
 * <b>NOTE:</b><br/>
 * Never implement an {@link OrientBean} interface. See {@link Bean} and {@code OrientBeanRepository.newEntity()} for
 * further details.
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface OrientBean extends EntityBean<String> {

  @Named("@rid")
  @Override
  WritableProperty<String> Id();

  @Named("@version")
  @Override
  int getVersion();

}
