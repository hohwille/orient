/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.db.impl;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.orient.bean.api.OrientLink;
import net.sf.mmm.orient.bean.api.Vertex;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * Test of {@link ReflectionHelper}.
 *
 * @author hohwille
 */
public class ReflectionHelperTest extends Assertions {

  /**
   * Test of {@link ReflectionHelper#createLinkType(Class)}.
   */
  @Test
  public void testCreateLinkType() {

    // given
    Class<Vertex> beanClass = Vertex.class;
    // when
    GenericType<OrientLink<Vertex>> type = ReflectionHelper.createLinkType(beanClass);
    // then
    assertThat(type).isNotNull();
    assertThat(type.getAssignmentClass()).isSameAs(OrientLink.class);
    assertThat(type.getTypeArgument(0).getAssignmentClass()).isSameAs(beanClass);
  }

}
