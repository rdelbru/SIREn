/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 29 Oct 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr;

/**
 * A collection of params used in SirenRequestHandler,
 * both for Plugin initialization and for Requests.
 */
public interface SirenParams {

  /** query and init param for keyword query fields */
  public static String KQF = "qf";

  /** ntriple query */
  public static String NQ = "nq";

  /** query and init param for ntriple query fields */
  public static String NQF = "nqf";

  /** query and init param for the ntriple query fields operator */
  public static String NQFO = "nqfo";

  /** keyword query parser param for disabling field queries */
  public static String KQ_DISABLE_FIELD = "disableField";

}
