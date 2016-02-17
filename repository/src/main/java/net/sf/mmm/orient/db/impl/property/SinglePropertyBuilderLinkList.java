/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl.property;

import javax.inject.Named;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import javafx.collections.ObservableList;
import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.datatype.api.OrientLink;
import net.sf.mmm.orient.property.api.LinkListProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * The implementation of {@link SinglePropertyBuilder} for {@link OType#LINKLIST}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
@Named
public class SinglePropertyBuilderLinkList extends SinglePropertyBuilderLinkBase<ObservableList<OrientLink>> {

  /**
   * The constructor.
   */
  public SinglePropertyBuilderLinkList() {
    super();
  }

  @Override
  public OType getType() {

    return OType.LINKLIST;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends ObservableList<OrientLink>> getValueClass() {

    return (Class) ObservableList.class;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends WritableProperty<ObservableList<OrientLink>>> getPropertyType(OProperty oProperty) {

    return (Class) LinkListProperty.class;
  }

  @Override
  protected GenericType getValueType(Class<? extends OrientBean> beanClass) {

    return LinkListProperty.createLinkType(beanClass);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected OClass getLinkedClass(WritableProperty<ObservableList<OrientLink>> property) {

    GenericType<? extends ObservableList<OrientLink>> type = property.getType();
    assert (type.getRetrievalClass() == ObservableList.class);
    int typeCount = type.getTypeArgumentCount();
    if (typeCount == 1) {
      GenericType<?> linkType = type.getTypeArgument(0);
      assert (linkType.getRetrievalClass() == OrientLink.class);
      int linkTypeCount = linkType.getTypeArgumentCount();
      if (linkTypeCount == 1) {
        GenericType<?> linkedType = linkType.getTypeArgument(0);
        return getBeanMapper().getOClass((Class) linkedType.getRetrievalClass());
      }
    }
    return null;
  }

}
