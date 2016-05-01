/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import javax.inject.Named;

import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.lang.BooleanProperty;
import net.sf.mmm.util.property.api.lang.ReadableBooleanProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * The implementation of {@link SinglePropertyBuilder} for {@link OType#BYTE}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named
public class SinglePropertyBuilderBoolean implements SinglePropertyBuilder<Boolean> {

  /**
   * The constructor.
   */
  public SinglePropertyBuilderBoolean() {
    super();
  }

  @Override
  public OType getType() {

    return OType.BOOLEAN;
  }

  @Override
  public Class<Boolean> getValueClass() {

    return Boolean.class;
  }

  @Override
  public Class<? extends WritableProperty<Boolean>> getPropertyType(OProperty oProperty) {

    return BooleanProperty.class;
  }

  @Override
  public GenericType<Boolean> getValueType(OProperty oProperty) {

    return ReadableBooleanProperty.TYPE;
  }

}
