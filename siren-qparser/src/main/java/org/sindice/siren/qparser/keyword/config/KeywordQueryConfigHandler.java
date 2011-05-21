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
    this.addAttribute(PositionIncrementsAttribute.class);
    this.addAttribute(LocaleAttribute.class);
    this.addAttribute(DefaultPhraseSlopAttribute.class);
    this.addAttribute(MultiTermRewriteMethodAttribute.class);

  }

}
