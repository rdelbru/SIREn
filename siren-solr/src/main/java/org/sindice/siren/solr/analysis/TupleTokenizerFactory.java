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
