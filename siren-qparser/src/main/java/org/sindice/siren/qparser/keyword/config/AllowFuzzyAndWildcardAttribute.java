/**
 * Copyright 2010, 2011, Renaud Delbru
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
 * @author Renaud Delbru [ 21 May 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010, 2011, by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.keyword.config;

import org.apache.lucene.queryParser.core.config.QueryConfigHandler;
import org.apache.lucene.util.Attribute;
import org.sindice.siren.qparser.keyword.query.processors.AllowFuzzyAndWildcardProcessor;

/**
 * This attribute is used by {@link AllowFuzzyAndWildcardProcessor} processor and
 * must be defined in the {@link QueryConfigHandler}. It basically tells the
 * processor if it should allow fuzzy and wildcard query. <br/>
 *
 */
public interface AllowFuzzyAndWildcardAttribute extends Attribute {
  public void setAllowFuzzyAndWildcard(boolean allowLeadingWildcard);
  public boolean isAllowFuzzyAndWildcard();
}
