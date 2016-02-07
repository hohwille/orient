/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl.property;

import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for the {@link AbstractPropertyBuilder property builder} assembled automatically from various
 * {@link SinglePropertyBuilder}-instances.
 *
 * @author hohwille
 * @since 1.0.0
 */
@ComponentSpecification
public interface PropertyBuilder extends AbstractPropertyBuilder {

  /**
   * @param type the {@link OType}.
   * @return the {@link SinglePropertyBuilder} {@link SinglePropertyBuilder#getType() responsible} for the given
   *         {@link OType}.
   */
  SinglePropertyBuilder<?> getBuilder(OType type);

}
