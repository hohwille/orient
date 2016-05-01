/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.repository.api;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.sf.mmm.orient.api.bean.OrientBean;
import net.sf.mmm.util.lang.api.Id;

/**
 * This is the interface for a spring-data {@link PagingAndSortingRepository} responsible for a {@link #getEntityClass()
 * specific} {@link OrientBean}. It will internally map {@link com.orientechnologies.orient.core.record.impl.ODocument
 * OrientDB documents} to your {@link OrientBean}s.
 *
 * @param <E> the generic type of the {@link #getEntityClass() managed} {@link OrientBean}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@NoRepositoryBean
public abstract interface OrientBeanRepository<E extends OrientBean> extends PagingAndSortingRepository<E, Id<E>> {

  /**
   * @return the {@link Class} reflecting the interface of the {@link OrientBean} managed by this repository.
   */
  Class<E> getEntityClass();

  /**
   * @return a new instance of the managed {@link OrientBean} entity.
   */
  E newEntity();

}
