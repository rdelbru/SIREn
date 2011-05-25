/**
 * Copyright 2011, Campinas Stephane
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
 * @project siren-solr
 * @author Campinas Stephane [ 21 May 2011 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.solr.analysis;

import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;
import org.sindice.siren.analysis.filter.MailtoFilter;

/**
 * 
 */
public class MailtoFilterFactory extends BaseTokenFilterFactory {

  @Override
  public TokenStream create(TokenStream input) {
    return new MailtoFilter(input);
  }

}
