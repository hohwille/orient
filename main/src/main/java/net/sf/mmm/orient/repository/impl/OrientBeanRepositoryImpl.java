/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.orient.api.bean.OrientBean;
import net.sf.mmm.orient.api.mapping.OrientBeanMapper;
import net.sf.mmm.orient.db.api.OrientDatabase;
import net.sf.mmm.orient.repository.api.OrientBeanRepository;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.lang.api.Id;

/**
 * This is the implementation of {@link OrientBeanRepository}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public class OrientBeanRepositoryImpl<E extends OrientBean> implements OrientBeanRepository<E> {

  private final Class<E> entityClass;

  private final BeanFactory beanFactory;

  private final OrientBeanMapper beanMapper;

  private final OrientDatabase database;

  private final E prototype;

  private final String oClassName;

  /**
   * The constructor.
   *
   * @param entityClass
   * @param beanFactory
   * @param beanMapper
   * @param database
   */
  public OrientBeanRepositoryImpl(Class<E> entityClass, BeanFactory beanFactory, OrientBeanMapper beanMapper,
      OrientDatabase database) {
    super();
    this.entityClass = entityClass;
    this.beanFactory = beanFactory;
    this.beanMapper = beanMapper;
    this.database = database;
    this.prototype = beanMapper.getBeanPrototype(entityClass);
    this.oClassName = this.prototype.access().getSimpleName();
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

    ORecordIteratorClass<ODocument> iterator = this.database.getConnection().browseClass(this.oClassName);
    return null;
  }

  @Override
  public Page<E> findAll(Pageable pageable) {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <S extends E> S save(S entity) {

    ODocument document = this.beanMapper.fromBean(entity);
    document.save();
    return this.beanMapper.toBean(document);
  }

  @Override
  public <S extends E> Iterable<S> save(Iterable<S> entities) {

    List<S> result = new ArrayList<>();
    for (S entity : entities) {
      S saved = save(entity);
      result.add(saved);
    }
    return result;
  }

  @Override
  public E findOne(Id<E> id) {

    ODocument document = this.database.find(id);
    if (document == null) {
      return null;
    }
    return this.beanMapper.toBean(document);
  }

  @Override
  public boolean exists(Id<E> id) {

    ODocument document = this.database.find(id);
    return (document != null);
  }

  @Override
  public Iterable<E> findAll() {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Iterable<E> findAll(Iterable<Id<E>> ids) {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public long count() {

    return this.database.getConnection().countClass(this.oClassName);
  }

  @Override
  public void delete(Id<E> id) {

    this.database.delete(id);
  }

  @Override
  public void delete(E entity) {

    Id<E> id = (Id<E>) entity.getId();
    if (id == null) {
      // transient entity can not be deleted...
      return;
    }
    delete(id);
  }

  @Override
  public void delete(Iterable<? extends E> entities) {

    for (E entity : entities) {
      delete(entity);
    }
  }

  @Override
  public void deleteAll() {

    // TODO Auto-generated method stub

  }

}
