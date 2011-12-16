/**
 * Copyright (c) 2009-2011 National University of Ireland, Galway. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 21 May 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010, 2011, by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.keyword.config;

import org.apache.lucene.queryParser.core.config.QueryConfigHandler;
import org.apache.lucene.queryParser.standard.config.AllowLeadingWildcardAttribute;
import org.apache.lucene.queryParser.standard.config.AnalyzerAttribute;
import org.apache.lucene.queryParser.standard.config.DefaultOperatorAttribute;
import org.apache.lucene.queryParser.standard.config.DefaultPhraseSlopAttribute;
import org.apache.lucene.queryParser.standard.config.FieldBoostMapFCListener;
import org.apache.lucene.queryParser.standard.config.FieldDateResolutionFCListener;
import org.apache.lucene.queryParser.standard.config.FuzzyAttribute;
import org.apache.lucene.queryParser.standard.config.LocaleAttribute;
import org.apache.lucene.queryParser.standard.config.LowercaseExpandedTermsAttribute;
import org.apache.lucene.queryParser.standard.config.MultiTermRewriteMethodAttribute;
import org.apache.lucene.queryParser.standard.config.PositionIncrementsAttribute;
import org.apache.lucene.queryParser.standard.config.RangeCollatorAttribute;

public class KeywordQueryConfigHandler
extends QueryConfigHandler {

  public KeywordQueryConfigHandler() {
    // Add listener that will build the FieldConfig attributes.
    this.addFieldConfigListener(new FieldBoostMapFCListener(this));
    this.addFieldConfigListener(new FieldDateResolutionFCListener(this));

    // Default Values
    this.addAttribute(RangeCollatorAttribute.class);
    this.addAttribute(DefaultOperatorAttribute.class);
    this.addAttribute(AnalyzerAttribute.class);
    this.addAttribute(FuzzyAttribute.class);
    this.addAttribute(LowercaseExpandedTermsAttribute.class);
    this.addAttribute(MultiTermRewriteMethodAttribute.class);
    this.addAttribute(AllowFuzzyAndWildcardAttribute.class);
    this.addAttribute(AllowLeadingWildcardAttribute.class);
    this.addAttribute(PositionIncrementsAttribute.class).setPositionIncrementsEnabled(true);
    this.addAttribute(LocaleAttribute.class);
    this.addAttribute(DefaultPhraseSlopAttribute.class);
    this.addAttribute(MultiTermRewriteMethodAttribute.class);

  }

}
