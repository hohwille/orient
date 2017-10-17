/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.data.bean.api.world;

import net.sf.mmm.orient.data.bean.api.world.de.DeBundesland;
import net.sf.mmm.orient.data.bean.api.world.fr.FrDepartement;
import net.sf.mmm.orient.data.bean.api.world.ie.IeCounty;
import net.sf.mmm.orient.data.bean.api.world.us.UsState;
import net.sf.mmm.property.api.link.LinkProperty;
import net.sf.mmm.util.validation.base.Mandatory;

/**
 * A {@link Region} is an {@link AbstractArea Area} a {@link Country} is divided into on the highest
 * administrative level, that is typically NUTS (nomenclature des unités territoriales statistiques) level 1.
 * Here are some common examples:
 * <table border="1">
 * <tr>
 * <th>{@link Country}</th>
 * <th>{@link Region}</th>
 * </tr>
 * <tr>
 * <td>USA</td>
 * <td>{@link UsState State}</td>
 * </tr>
 * <tr>
 * <td>Germany</td>
 * <td>{@link DeBundesland Bundesland}</td>
 * </tr>
 * <tr>
 * <td>France</td>
 * <td>{@link FrDepartement Département}</td>
 * </tr>
 * <tr>
 * <td>Ireland</td>
 * <td>{@link IeCounty County}</td>
 * </tr>
 * </table>
 * So for the USA this is a {@link UsState State}, for Germany this is a {@link DeBundesland Bundesland} and
 * for France
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface Region extends AbstractArea {

  /**
   * @return the {@link Country} of this {@link Region}.
   */
  @Mandatory
  LinkProperty<Country> Country();

}
