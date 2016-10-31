/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.api.mapping;

import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OSchemaProxy;
import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.orient.api.bean.OrientBean;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.api.mapping.DocumentBeanMapper;
import net.sf.mmm.util.data.api.id.Id;

/**
 * This is the interface for mapping between {@link ODocument} and {@link OrientBean} and vice versa.
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface OrientBeanMapper extends DocumentBeanMapper<ODocument, OrientBean> {

  /**
   * Synchronizes the {@link OSchemaProxy OrientDB schema} with the {@link OrientBean Java data model}.
   *
   * @see #syncClass(OClass)
   *
   * @param schema the {@link OSchemaProxy}.
   */
  void syncSchema(OSchemaProxy schema);

  /**
   * Synchronizes a {@link OClass OrientDB class} with the according {@link OrientBean}.
   *
   * @param oclass the {@link OClass} to synchronize.
   */
  void syncClass(OClass oclass);

  /**
   * @param oClass the {@link OClass OrientDB class} to map.
   * @return the corresponding {@link OrientBean} {@link BeanFactory#createPrototype(Class) prototype}.
   */
  OrientBean getBeanPrototype(OClass oClass);

  /**
   * @param clusterId the {@link ORID#getClusterId() cluster ID}.
   * @return the corresponding {@link OrientBean} {@link BeanFactory#createPrototype(Class) prototype}.
   */
  OrientBean getBeanPrototype(int clusterId);

  /**
   * @param <B> the generic type of the {@link OrientBean}.
   * @param type the {@link Class} reflecting the {@link OrientBean}-interface to get the prototype for.
   * @return the corresponding {@link OrientBean} {@link BeanFactory#createPrototype(Class) prototype}.
   */
  <B extends OrientBean> B getBeanPrototype(Class<B> type);

  /**
   * @param beanClass the {@link OrientBean}-{@link Class}.
   * @return the corresponding {@link OClass}.
   */
  OClass getOClass(Class<? extends OrientBean> beanClass);

  /**
   * @param id the {@link Id} to convert.
   * @return the {@link Id} as {@link ORID}.
   */
  ORID convertId(Id<? extends OrientBean> id);

}
