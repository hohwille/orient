/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.bean.impl;

import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.orient.bean.api.OrientBean;
import net.sf.mmm.orient.bean.impl.AbstractOrientLink;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 1.0.0
 */
public class OrientLinkDocument<T extends OrientBean> extends AbstractOrientLink<T> {

  private final String id;

  private ODocument document;

  private T bean;

  /**
   * The constructor.
   *
   * @param document the {@link ODocument} to link that will be lazily converted to the {@link #getTarget() target}
   *        bean.
   */
  public OrientLinkDocument(ODocument document) {
    super();
    this.id = document.getIdentity().toString();
    this.document = document;
    this.bean = null;
  }

  @Override
  public String getId() {

    return this.id;
  }

  @Override
  public T getTarget() {

    if (this.bean == null) {
      // TODO convert document to bean
      this.document = null;
    }
    return this.bean;
  }

  public static <T extends OrientBean> OrientLinkDocument<T> valueOf(ODocument document) {

    if (document == null) {
      return null;
    }
    return new OrientLinkDocument<>(document);
  }

}
