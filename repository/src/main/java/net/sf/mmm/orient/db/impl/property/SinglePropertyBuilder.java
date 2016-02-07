/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl.property;

import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the interface for an {@link AbstractPropertyBuilder} responsible for a single {@link #getType() type} of
 * {@link OProperty properties}.
 *
 * @param <V> the generic type of the {@link WritableProperty#getValue() property value}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@ComponentSpecification(plugin = true)
public interface SinglePropertyBuilder<V> extends AbstractPropertyBuilder {

  /**
   * @return the static {@link OType} value this {@link SinglePropertyBuilder} is responsible for.
   */
  OType getType();

  @Override
  default void build(OProperty oProperty, OrientBean prototype) {

    assert (oProperty.getType() == getType());
    GenericType<V> valueType = getValueType(oProperty);
    Class<? extends WritableProperty<V>> propertyType = getPropertyType(oProperty);
    String propertyName = oProperty.getName();
    prototype.access().getOrCreateProperty(propertyName, valueType, propertyType);
  }

  /**
   * @param oProperty the {@link OProperty}.
   * @return the {@link Class} reflecting the corresponding {@link WritableProperty} type such as e.g.
   *         {@link net.sf.mmm.util.property.api.StringProperty} for {@link OType#STRING}.
   */
  Class<? extends WritableProperty<V>> getPropertyType(OProperty oProperty);

  /**
   * @param oProperty the {@link OProperty}.
   * @return the {@link GenericType} reflecting the corresponding {@link WritableProperty#getType() value type}.
   */
  GenericType<V> getValueType(OProperty oProperty);

}