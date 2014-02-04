/**
 * Copyright 2014 National University of Ireland, Galway.
 *
 * This file is part of the SIREn project. Project and contact information:
 *
 *  https://github.com/rdelbru/SIREn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sindice.siren.solr.schema;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.FieldType;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrException.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all datatypes used by an index schema.
 * <p>
 * Code taken from {@link FieldType} and adapted for the SIREn's use case.
 */
public abstract class Datatype {

  /** The name of the datatype */
  protected String datatypeName;
  /** additional arguments specified in the datatype declaration */
  protected Map<String, String> args;

  public static final Logger log = LoggerFactory.getLogger(Datatype.class);

  /**
   * subclasses should initialize themselves with the args provided
   * and remove valid arguments.  leftover arguments will cause an exception.
   * Common boolean properties have already been handled.
   */
  protected void init(final Map<String, String> args) {

  }

  protected String getArg(final String n, final Map<String,String> args) {
    final String s = args.remove(n);
    if (s == null) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
        "Missing parameter '"+n+"' for Datatype=" + datatypeName +args);
    }
    return s;
  }

  // Handle additional arguments...
  void setArgs(final Map<String,String> args) {

    this.args = args;
    final Map<String,String> initArgs = new HashMap<String,String>(args);

    this.init(initArgs);

    if (initArgs.size() > 0) {
      throw new RuntimeException("schema datatype " + datatypeName
              + "("+ this.getClass().getName() + ")"
              + " invalid arguments:" + initArgs);
    }
  }

  /** The Name of this Datatype as specified in the schema file */
  public String getDatatypeName() {
    return datatypeName;
  }

  void setDatatypeName(final String datatypeName) {
    this.datatypeName = datatypeName;
  }

  @Override
  public String toString() {
    return datatypeName + "{class=" + this.getClass().getName()
            + (analyzer != null ? ",analyzer=" + analyzer.getClass().getName() : "")
            + ",args=" + args
            +"}";
  }

  /**
   * Analyzer set by schema for text types to use when indexing fields
   * of this type, subclasses can set analyzer themselves or override
   * getAnalyzer()
   * @see #getAnalyzer
   * @see #setAnalyzer
   */
  protected Analyzer analyzer;

  /**
   * Analyzer set by schema for text types to use when searching fields
   * of this type, subclasses can set analyzer themselves or override
   * getAnalyzer()
   * @see #getQueryAnalyzer
   * @see #setQueryAnalyzer
   */
  protected Analyzer queryAnalyzer;

  /**
   * Returns the Analyzer to be used when indexing fields of this type.
   * <p>
   * This method may be called many times, at any time.
   * </p>
   * @see #getQueryAnalyzer
   */
  public Analyzer getAnalyzer() {
    return analyzer;
  }

  /**
   * Returns the Analyzer to be used when searching fields of this type.
   * <p>
   * This method may be called many times, at any time.
   * </p>
   * @see #getAnalyzer
   */
  public Analyzer getQueryAnalyzer() {
    return queryAnalyzer;
  }

  /**
   * Sets the Analyzer to be used when indexing values of this datatype.
   *
   * <p>
   * The default implementation throws a SolrException.
   * Subclasses that override this method need to ensure the behavior
   * of the analyzer is consistent with the implementation of toInternal.
   * </p>
   *
   * @see #toInternal
   * @see #setQueryAnalyzer
   * @see #getAnalyzer
   */
  public void setAnalyzer(final Analyzer analyzer) {
    throw new SolrException(ErrorCode.SERVER_ERROR,
       "FieldType: " + this.getClass().getSimpleName() +
       " (" + datatypeName + ") does not support specifying an analyzer");
  }

  /**
   * Sets the Analyzer to be used when querying values of this datatype.
   *
   * <p>
   * The default implementation throws a SolrException.
   * Subclasses that override this method need to ensure the behavior
   * of the analyzer is consistent with the implementation of toInternal.
   * </p>
   *
   * @see #toInternal
   * @see #setAnalyzer
   * @see #getQueryAnalyzer
   */
  public void setQueryAnalyzer(final Analyzer analyzer) {
    throw new SolrException(ErrorCode.SERVER_ERROR,
     "FieldType: " + this.getClass().getSimpleName() +
     " (" + datatypeName + ") does not support specifying an analyzer");
  }

}
