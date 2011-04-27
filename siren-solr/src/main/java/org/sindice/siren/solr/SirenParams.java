/**
 * Copyright 2010, Renaud Delbru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
