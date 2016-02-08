/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.bean.api;

/**
 * This is the interface for a link to an {@link OrientBean}. It acts as a reference that can be evaluated lazily. It
 * will hold the {@link #getId() primary key} of the linked bean.
 *
 * @param <T> the generic type of the {@link #getTarget() linked} {@link OrientBean}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public interface OrientLink<T extends OrientBean> {

  /**
   * @return the {@link OrientBean#Id() primary key} of the linked {@link #getTarget() bean}. When creating new
   *         {@link OrientBean}s a link may hold a transient {@link OrientBean} as {@link #getTarget() target} that has
   *         no ID assigned, yet. In such case this method will return <code>null</code>.
   */
  String getId();

  /**
   * @return {@code true} if the {@link #getTarget() link target} is already resolved, {@code false} otherwise (if
   *         {@link #getTarget()} may trigger lazy loading and could also fail if called without an open transaction).
   */
  boolean isResolved();

  /**
   * This method resolves the linked {@link OrientBean}.<br/>
   * <b>ATTENTION:</b><br/>
   * An {@link OrientLink} can be evaluated lazily. In such case the first call of this method will resolve the linked
   * {@link OrientBean} from the database. That can be a relatively expensive operation and requires an open
   * transaction. Use {@link #isResolved()} to distinguish and prevent undesired link resolving.<br>
   * Further, after serialization (e.g. mapping to JSON and back) maybe only the {@link #getId() ID} was transferred and
   * this link can not be resolved. In that case this method may return {@code null}. Please note that {@link #getId()
   * id} and {@link #getTarget() target} can not both be null. In case a link property is empty it will contain
   * {@code null} instead of an empty {@link OrientLink} so you can always properly distinguish the scenarios.
   *
   * @return the link target or {@code null} if the link can not be resolved.
   */
  T getTarget();

}
