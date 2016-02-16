/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.datatype.impl;

import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.datatype.api.OrientLink;
import net.sf.mmm.orient.db.impl.OrientBeanMapper;

/**
 * This is the implementation of the {@link OrientLink} based on an {@link ODocument} using lazy loading.
 *
 * @param <T> the generic type of the {@link #getTarget() linked} {@link OrientBean}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public class OrientLinkDocumentLazy<T extends OrientBean> extends LazyOrientLink<T> {

  private ODocument document;

  /**
   * The constructor.
   *
   * @param document the {@link ODocument} to link that will be lazily converted to the {@link #getTarget() target}
   *        bean.
   * @param beanMapper the {@link OrientBeanMapper}.
   */
  public OrientLinkDocumentLazy(ODocument document, OrientBeanMapper beanMapper) {
    super(document.getIdentity().toString(), beanMapper);
    this.document = document;
  }

  @Override
  protected ODocument resolveLinkedDocument() {

    ODocument result = this.document;
    this.document = null;
    return result;
  }

  /**
   * @param <T> the generic type of the {@link #getTarget() linked} {@link OrientBean}.
   * @param document the {@link ODocument} to wrap.
   * @param beanMapper the {@link OrientBeanMapper}.
   * @return the new link instance.
   */
  public static <T extends OrientBean> OrientLinkDocumentLazy<T> valueOf(ODocument document,
      OrientBeanMapper beanMapper) {

    if (document == null) {
      return null;
    }
    return new OrientLinkDocumentLazy<>(document, beanMapper);
  }

}
