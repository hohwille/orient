/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.repository.api.OrientBeanRepository;
import net.sf.mmm.util.bean.api.BeanFactory;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 1.0.0
 */
public class OrientBeanRepositoryImpl<E extends OrientBean> implements OrientBeanRepository<E> {

  private final Class<E> entityClass;

  private final E prototype;

  private final BeanFactory beanFactory;

  /**
   * The constructor.
   */
  public OrientBeanRepositoryImpl(Class<E> entityClass, BeanFactory beanFactory) {
    super();
    this.entityClass = entityClass;
    this.beanFactory = beanFactory;
    // TODO: retrieve from bean mapper
    this.prototype = beanFactory.createPrototype(entityClass);
  }

  @Override
  public Class<E> getEntityClass() {

    return this.entityClass;
  }

  @Override
  public E newEntity() {

    return this.beanFactory.create(this.prototype);
  }

  @Override
  public Iterable<E> findAll(Sort sort) {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Page<E> findAll(Pageable pageable) {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <S extends E> S save(S entity) {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <S extends E> Iterable<S> save(Iterable<S> entities) {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public E findOne(String id) {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean exists(String id) {

    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Iterable<E> findAll() {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Iterable<E> findAll(Iterable<String> ids) {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public long count() {

    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void delete(String id) {

    // TODO Auto-generated method stub

  }

  @Override
  public void delete(E entity) {

    // TODO Auto-generated method stub

  }

  @Override
  public void delete(Iterable<? extends E> entities) {

    // TODO Auto-generated method stub

  }

  @Override
  public void deleteAll() {

    // TODO Auto-generated method stub

  }

}
