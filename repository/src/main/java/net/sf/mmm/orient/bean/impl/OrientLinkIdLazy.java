/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.bean.impl;

import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.db.impl.OrientBeanMapper;
import net.sf.mmm.orient.db.impl.OrientDatabase;

/**
 * Implementation of {@link OrientLinkDocumentLazy} only containing an {@link #getId()}.
 *
 * @param <T> the generic type of the {@link #getTarget() linked} {@link OrientBean}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class OrientLinkIdLazy<T extends OrientBean> extends LazyOrientLink<T> {

  private OrientDatabase database;

  /**
   * The constructor.
   *
   * @param id the {@link #getId() ID}.
   * @param beanMapper the {@link OrientBeanMapper}.
   * @param database the {@link OrientDatabase}.
   */
  public OrientLinkIdLazy(String id, OrientBeanMapper beanMapper, OrientDatabase database) {
    super(id, beanMapper);
    this.database = database;
  }

  @Override
  protected ODocument resolveLinkedDocument() {

    ODocument document = this.database.find(getId());
    this.database = null;
    return document;
  }

  /**
   * @param <T> the generic type of the {@link #getTarget() linked} {@link OrientBean}.
   * @param id the {@link #getId() ID}.
   * @param beanMapper the {@link OrientBeanMapper}.
   * @param database the {@link OrientDatabase}.
   * @return the new link.
   */
  public static <T extends OrientBean> OrientLinkIdLazy<T> valueOf(String id, OrientBeanMapper beanMapper,
      OrientDatabase database) {

    if (id == null) {
      return null;
    }
    return new OrientLinkIdLazy<>(id, beanMapper, database);
  }

}
