/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.bean.impl;

import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.bean.api.OrientLink;
import net.sf.mmm.orient.db.impl.OrientBeanMapper;

/**
 * This is the abstract base implementation of {@link OrientLink} that can resolve the {@link #getTarget() link target}
 * via lazy loading. This will only work if the
 *
 * @param <T> the generic type of the {@link #getTarget() linked} {@link OrientBean}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public abstract class LazyOrientLink<T extends OrientBean> extends AbstractOrientLink<T> {

  private final String id;

  private OrientBeanMapper beanMapper;

  private T bean;

  /**
   * The constructor.
   *
   * @param id - see {@link #getId()}.
   * @param beanMapper the {@link OrientBeanMapper}.
   */
  public LazyOrientLink(String id, OrientBeanMapper beanMapper) {
    super();
    this.id = id;
    this.beanMapper = beanMapper;
    this.bean = null;
  }

  @Override
  public String getId() {

    return this.id;
  }

  @Override
  public boolean isResolved() {

    return (this.bean != null);
  }

  @Override
  public T getTarget() {

    if (this.bean == null) {
      this.bean = this.beanMapper.map2Bean(resolveLinkedDocument());
      this.beanMapper = null;
    }
    return this.bean;
  }

  /**
   * @return the resolved {@link ODocument} {@link #getId() identified} by this link. This method should only be called
   *         once per instance.
   */
  protected abstract ODocument resolveLinkedDocument();

}
