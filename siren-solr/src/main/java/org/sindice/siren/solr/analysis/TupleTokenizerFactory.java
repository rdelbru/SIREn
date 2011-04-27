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
 * @author Renaud Delbru [ 25 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.solr.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;
import org.apache.solr.analysis.BaseTokenizerFactory;
import org.apache.solr.common.SolrException;
import org.apache.solr.core.SolrResourceLoader;
import org.apache.solr.schema.SubIndexSchema;
import org.sindice.siren.analysis.TupleTokenizer;

public class TupleTokenizerFactory extends BaseTokenizerFactory {

  public static final String MAXLENGTH_KEY = "maxLength";
  public static final String SUBSCHEMA_KEY = "subschema";
  public static final String LITERAL_FILEDTYPE_KEY = "literal-fieldtype";

  private int maxLength = 0;
  private Analyzer literalAnalyzer;

	@Override
	public void init(final Map<String,String> args) {
	  super.init(args);
	  this.assureMatchVersion();
	  // load maxLength param
	  final String maxArg = args.get(MAXLENGTH_KEY);
	  maxLength = (maxArg != null ? Integer.parseInt(maxArg) : Integer.MAX_VALUE);

	  // load subschema filename param
	  final String subschema = args.get(SUBSCHEMA_KEY);
	  if (subschema == null) {
	    throw new SolrException(SolrException.ErrorCode.NOT_FOUND, "No subschema file defined");
	  }
	  // load field type for literal
	  final String fieldtype = args.get(LITERAL_FILEDTYPE_KEY);
	  if (fieldtype == null) {
	    throw new SolrException(SolrException.ErrorCode.NOT_FOUND, "No literal field type defined");
	  }

	  // load subschema and extract literal index analyzer
    final String solrHome = SolrResourceLoader.locateSolrHome();
    final String schemaPath = solrHome + "conf" + File.separator + subschema;
    try {
      final SubIndexSchema schema = new SubIndexSchema(schemaPath, Version.LUCENE_31);
      literalAnalyzer = schema.getFieldTypeByName(fieldtype).getAnalyzer();
    } catch (final FileNotFoundException e) {
      throw new SolrException(SolrException.ErrorCode.NOT_FOUND, e.getMessage());
    }
	}

	@Override
	public TupleTokenizer create(final Reader input) {
		return new TupleTokenizer(input, maxLength, literalAnalyzer);
	}

}
