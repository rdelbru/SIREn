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

package org.sindice.siren.qparser.keyword.processors;

import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys;
import org.sindice.siren.qparser.keyword.nodes.DatatypeQueryNode;
import org.sindice.siren.qparser.keyword.nodes.TwigQueryNode;
import org.sindice.siren.util.JSONDatatype;
import org.sindice.siren.util.XSDDatatype;

/**
 * This processor tags all the descendant of a {@link DatatypeQueryNode}
 * with the datatype label using the TAG {@link DatatypeQueryNode#DATATYPE_TAGID}.
 *
 * <p>
 *
 * By default, a node without a {@link DatatypeQueryNode} ancestor is tagged
 * with {@link XSDDatatype#XSD_STRING}.
 *
 * <p>
 *
 * The top level node of a twig is tagged with {@link JSONDatatype#JSON_FIELD}.
 * If a custom datatype is used on the top level node, it is used instead of
 * {@link JSONDatatype#JSON_FIELD}.
 */
public class DatatypeQueryNodeProcessor
extends QueryNodeProcessorImpl {

  /** The current datatype */
  private String datatype = null;
  /** the number of twigs */
  private int nbTwigs = 0;

  @Override
  protected QueryNode preProcessNode(final QueryNode node)
  throws QueryNodeException {
    if (node instanceof DatatypeQueryNode) {
      // Set the datatype analyzer to use on the descendant querynodes
      final QueryConfigHandler conf = this.getQueryConfigHandler();
      final Map<String, Analyzer> dtAnalyzers = conf.get(KeywordConfigurationKeys.DATATYPES_ANALYZERS);
      final DatatypeQueryNode dt = (DatatypeQueryNode) node;

      if (dtAnalyzers == null) {
        throw new IllegalArgumentException("KeywordConfigurationKeys.DATAYPES_ANALYZERS " +
            "should be set on the KeywordQueryConfigHandler");
      }
      if (!dtAnalyzers.containsKey(dt.getDatatype())) {
        throw new IllegalArgumentException("Unknown datatype: [" + dt.getDatatype() + "]");
      }
      // check no datatype is already set
      if (datatype != null) {
        throw new IllegalArgumentException("Cannot use more than one datatype in a same tree. " +
            "Using [" + datatype + "], but receieved also [" + dt.getDatatype() + "]");
      }
      if (dtAnalyzers.get(dt.getDatatype()) == null) {
        throw new IllegalArgumentException("Analyzer of datatype [" + datatype + "] cannot be null.");
      }

      datatype = dt.getDatatype();
    }
    // parent twig query
    else if (node instanceof TwigQueryNode) {
      nbTwigs++;
      if (nbTwigs == 1) {
        // Set the json:field datatype on the top level node only
        final TwigQueryNode twig = (TwigQueryNode) node;
        twig.getRoot().setTag(DatatypeQueryNode.DATATYPE_TAGID, JSONDatatype.JSON_FIELD);
      }
    }
    // A datatype is being used
    else if (datatype != null) {
      node.setTag(DatatypeQueryNode.DATATYPE_TAGID, datatype);
    }
    // Default datatype
    else if (node.getTag(DatatypeQueryNode.DATATYPE_TAGID) == null) {
      node.setTag(DatatypeQueryNode.DATATYPE_TAGID, XSDDatatype.XSD_STRING);
    }
    return node;
  }

  @Override
  protected QueryNode postProcessNode(final QueryNode node)
  throws QueryNodeException {
    if (node instanceof DatatypeQueryNode) {
      datatype = null;
    } else if (node instanceof TwigQueryNode) {
      nbTwigs--;
      assert nbTwigs >= 0;
    }
    return node;
  }

  @Override
  protected List<QueryNode> setChildrenOrder(final List<QueryNode> children)
  throws QueryNodeException {
    return children;
  }

}
