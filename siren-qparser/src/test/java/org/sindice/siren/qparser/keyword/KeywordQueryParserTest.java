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
package org.sindice.siren.qparser.keyword;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.sindice.siren.search.AbstractTestSirenScorer.BooleanClauseBuilder.must;
import static org.sindice.siren.search.AbstractTestSirenScorer.BooleanClauseBuilder.not;
import static org.sindice.siren.search.AbstractTestSirenScorer.BooleanClauseBuilder.should;
import static org.sindice.siren.search.AbstractTestSirenScorer.BooleanQueryBuilder.bq;
import static org.sindice.siren.search.AbstractTestSirenScorer.NodeBooleanQueryBuilder.nbq;
import static org.sindice.siren.search.AbstractTestSirenScorer.NodePhraseQueryBuilder.npq;
import static org.sindice.siren.search.AbstractTestSirenScorer.NodeTermQueryBuilder.ntq;
import static org.sindice.siren.search.AbstractTestSirenScorer.TwigQueryBuilder.twq;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.ConfigurationKey;
import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorPipeline;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.LuceneTestCase;
import org.junit.Ignore;
import org.junit.Test;
import org.sindice.siren.analysis.AnyURIAnalyzer;
import org.sindice.siren.analysis.DoubleNumericAnalyzer;
import org.sindice.siren.analysis.FloatNumericAnalyzer;
import org.sindice.siren.analysis.IntNumericAnalyzer;
import org.sindice.siren.analysis.LongNumericAnalyzer;
import org.sindice.siren.analysis.filter.ASCIIFoldingExpansionFilter;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler;
import org.sindice.siren.qparser.keyword.config.KeywordQueryConfigHandler.KeywordConfigurationKeys;
import org.sindice.siren.qparser.keyword.nodes.TwigQueryNode;
import org.sindice.siren.qparser.keyword.nodes.WildcardNodeQueryNode;
import org.sindice.siren.qparser.keyword.processors.NodeNumericQueryNodeProcessor;
import org.sindice.siren.qparser.keyword.processors.NodeNumericRangeQueryNodeProcessor;
import org.sindice.siren.search.node.LuceneProxyNodeQuery;
import org.sindice.siren.search.node.MultiNodeTermQuery;
import org.sindice.siren.search.node.NodeBooleanClause;
import org.sindice.siren.search.node.NodeBooleanClause.Occur;
import org.sindice.siren.search.node.NodeBooleanQuery;
import org.sindice.siren.search.node.NodeFuzzyQuery;
import org.sindice.siren.search.node.NodeNumericRangeQuery;
import org.sindice.siren.search.node.NodePhraseQuery;
import org.sindice.siren.search.node.NodePrefixQuery;
import org.sindice.siren.search.node.NodePrimitiveQuery;
import org.sindice.siren.search.node.NodeQuery;
import org.sindice.siren.search.node.NodeRegexpQuery;
import org.sindice.siren.search.node.NodeTermQuery;
import org.sindice.siren.search.node.NodeTermRangeQuery;
import org.sindice.siren.search.node.NodeWildcardQuery;
import org.sindice.siren.search.node.TwigQuery;
import org.sindice.siren.util.JSONDatatype;
import org.sindice.siren.util.SirenTestCase;
import org.sindice.siren.util.XSDDatatype;

@SuppressWarnings("rawtypes")
public class KeywordQueryParserTest {

  /**
   * Helper method to parse a query string using the {@link KeywordQueryParser}
   */
  @SuppressWarnings("unchecked")
  public Query parse(final HashMap<ConfigurationKey, Object> keys, final String query)
  throws QueryNodeException {
    final KeywordQueryParser parser = new KeywordQueryParser();
    if (keys != null) {
      final KeywordQueryConfigHandler config = new KeywordQueryConfigHandler();
      for (Entry<ConfigurationKey, Object> key: keys.entrySet()) {
        config.set(key.getKey(), key.getValue());
      }
      parser.setQueryConfigHandler(config);
    }
    return parser.parse(query, SirenTestCase.DEFAULT_TEST_FIELD);
  }

  private void _assertSirenQuery(final Query expected, final String query)
  throws Exception {
    assertEquals(expected, this.parse(null, query));
    assertEquals(expected, this.parse(null, expected.toString()));
  }

  private void _assertSirenQuery(final HashMap<ConfigurationKey, Object> keys,
                                 final Query expected,
                                 final String query)
  throws Exception {
    assertEquals(expected, parse(keys, query));
    assertEquals(expected, parse(keys, expected.toString()));
  }

  private Properties loadQNamesFile(final String qnamesFile) throws IOException {
    final Properties qnames = new Properties();
    qnames.load(new FileInputStream(new File(qnamesFile)));
    return qnames;
  }

  @Test
  public void testQuerySyntax()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final HashMap<String, Analyzer> dtAnalyzers = new HashMap<String, Analyzer>();
    dtAnalyzers.put(XSDDatatype.XSD_STRING, new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dtAnalyzers);

    Query bq = bq(must("term", "term", "term")).getQuery();
    this._assertSirenQuery(config, bq, "term term term");

    bq = bq(must("t�rm", "term", "term")).getQuery();
    this._assertSirenQuery(config, bq, "t�rm term term");
    Query q = ntq("�mlaut").getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "�mlaut");

    bq = bq(must("a", "b")).getQuery();
    this._assertSirenQuery(config, bq, "a AND b");
    this._assertSirenQuery(config, bq, "(a AND b)");
    this._assertSirenQuery(config, bq, "a && b");

    bq = bq(must("a"), not("b")).getQuery();
    this._assertSirenQuery(config, bq, "a AND NOT b");
    this._assertSirenQuery(config, bq, "a AND -b");
    this._assertSirenQuery(config, bq, "a AND !b");
    this._assertSirenQuery(config, bq, "a && ! b");

    /*
     * For the OR queries, the #toString outputs "a b". Because the default
     * operator of KeywordQueryParser is AND, parsing it back gives "+a +b".
     * TODO Find a way around this ? Maybe an operator for SHOULD.
     */
    bq = bq(should("a", "b")).getQuery();
    assertEquals(bq, parse(config, "a OR b"));
    assertEquals(bq, parse(config, "a || b"));

    bq = bq(should(ntq("a")), not(ntq("b"))).getQuery();
    assertEquals(bq, parse(config, "a OR !b"));
    assertEquals(bq, parse(config, "a OR ! b"));
    assertEquals(bq, parse(config, "a OR -b"));

    bq = bq(must("term"), must(npq("phrase", "phrase"))).getQuery();
    this._assertSirenQuery(config, bq, "term AND \"phrase phrase\"");
    q = npq("hello", "there").getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "\"hello there\"");
  }

  @Test
  public void testEscaped()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final HashMap<String, Analyzer> dtAnalyzers = new HashMap<String, Analyzer>();
    dtAnalyzers.put(XSDDatatype.XSD_STRING, new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dtAnalyzers);

    Query q = ntq("*").getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "\\*");

    q = ntq("a").getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "\\a");

    q = ntq("a-b").getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "a\\-b");
    q = ntq("a+b").getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "a\\+b");
    q = ntq("a:b").getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "a\\:b");
    q = ntq("a\\b").getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "a\\\\b");

    q = bq(must("a", "b-c")).getQuery();
    this._assertSirenQuery(config, q, "a b\\-c");
    q = bq(must("a", "b+c")).getQuery();
    this._assertSirenQuery(config, q, "a b\\+c");
    q = bq(must("a", "b:c")).getQuery();
    this._assertSirenQuery(config, q, "a b\\:c");
    q = bq(must("a", "b\\c")).getQuery();
    this._assertSirenQuery(config, q, "a b\\\\c");

    q = ntq("a\\+b").getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "a\\\\\\+b");

    q = bq(must("a", "\"b", "c\"", "d")).getQuery();
    this._assertSirenQuery(config, q, "a \\\"b c\\\" d");
    q = npq("a", "\"b\"aa\"", "c\"", "d").getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "\"a \\\"b\\\"aa\\\" c\\\" d\"");
    q = npq("a", "+b", "c", "d").getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "\"a \\+b c d\"");
  }

  @Test
  public void testQueryType()
  throws Exception {
    final KeywordQueryParser parser = new KeywordQueryParser();
    parser.setAllowTwig(false);

    Query query = parser.parse("aaa AND bbb", "a");
    assertTrue(query instanceof NodeBooleanQuery);
    query = parser.parse("hello", "a");
    assertTrue(query instanceof NodeTermQuery);
    query = parser.parse("\"hello Future\"", "a");
    assertTrue(query instanceof NodePhraseQuery);
  }

  @Test
  public void testRemoveTopLevelQueryNode()
  throws Exception {
    // Twigs are disabled
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    config.put(KeywordConfigurationKeys.ALLOW_TWIG, false);
    final HashMap<String, Analyzer> dtAnalyzers = new HashMap<String, Analyzer>();
    dtAnalyzers.put(XSDDatatype.XSD_STRING, new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dtAnalyzers);

    final Query q1 = nbq(must("a"), must("b"), should("c")).getNodeQuery();
    this._assertSirenQuery(config, q1, "+a +\"b\" OR \"c\"");
    // Twigs are enabled
    config.put(KeywordConfigurationKeys.ALLOW_TWIG, true);
    final Query q2 = bq(must("a"),must("b"), should("c")).getQuery();
    this._assertSirenQuery(config, q2, "+a +\"b\" OR \"c\"");
  }

  @Test
  public void testRegexps() throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    config.put(KeywordConfigurationKeys.ALLOW_TWIG, false);

    final String df = SirenTestCase.DEFAULT_TEST_FIELD;
    final NodeRegexpQuery q = new NodeRegexpQuery(new Term(SirenTestCase.DEFAULT_TEST_FIELD, "[a-z][123]"));
    this._assertSirenQuery(config, q, "/[a-z][123]/");
    config.put(ConfigurationKeys.LOWERCASE_EXPANDED_TERMS, true);
    this._assertSirenQuery(config, q, "/[A-Z][123]/");
    q.setBoost(0.5f);
    this._assertSirenQuery(config, q, "/[A-Z][123]/^0.5");
    q.setRewriteMethod(MultiNodeTermQuery.SCORING_BOOLEAN_QUERY_REWRITE);
    config.put(KeywordConfigurationKeys.MULTI_NODE_TERM_REWRITE_METHOD, MultiNodeTermQuery.SCORING_BOOLEAN_QUERY_REWRITE);
    this._assertSirenQuery(config, q, "/[A-Z][123]/^0.5");
    config.put(KeywordConfigurationKeys.MULTI_NODE_TERM_REWRITE_METHOD, MultiNodeTermQuery.CONSTANT_SCORE_AUTO_REWRITE_DEFAULT);

    final Query escaped = new NodeRegexpQuery(new Term(df, "[a-z]\\/[123]"));
    this._assertSirenQuery(config, escaped, "/[a-z]\\/[123]/");
    final Query escaped2 = new NodeRegexpQuery(new Term(df, "[a-z]\\*[123]"));
    this._assertSirenQuery(config, escaped2, "/[a-z]\\*[123]/");

    final HashMap<String, Analyzer> dtAnalyzers = new HashMap<String, Analyzer>();
    dtAnalyzers.put(XSDDatatype.XSD_STRING, new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dtAnalyzers);
    final NodeBooleanQuery complex = new NodeBooleanQuery();
    complex.add(new NodeRegexpQuery(new Term(df, "[a-z]\\/[123]")), NodeBooleanClause.Occur.MUST);
    complex.add(new NodeTermQuery(new Term(df, "/etc/init.d/")), Occur.MUST);
    complex.add(new NodeTermQuery(new Term(df, "/etc/init[.]d/lucene/")), Occur.SHOULD);
    this._assertSirenQuery(config, complex, "+/[a-z]\\/[123]/ +\"/etc/init.d/\" OR \"/etc\\/init\\[.\\]d/lucene/\" ");

    Query re = new NodeRegexpQuery(new Term(df, "http.*"));
    this._assertSirenQuery(config, re, "/http.*/");

    re = new NodeRegexpQuery(new Term(df, "http~0.5"));
    this._assertSirenQuery(config, re, "/http~0.5/");

    re = new NodeRegexpQuery(new Term(df, "boo"));
    this._assertSirenQuery(config, re, "/boo/");

    this._assertSirenQuery(config, new NodeTermQuery(new Term(df, "/boo/")), "\"/boo/\"");
    this._assertSirenQuery(config, new NodeTermQuery(new Term(df, "/boo/")), "\\/boo\\/");

    config.put(ConfigurationKeys.DEFAULT_OPERATOR, Operator.OR);
    final NodeBooleanQuery two = new NodeBooleanQuery();
    two.add(new NodeRegexpQuery(new Term(df, "foo")), Occur.SHOULD);
    two.add(new NodeRegexpQuery(new Term(df, "bar")), Occur.SHOULD);
    this._assertSirenQuery(config, two, "/foo/ /bar/");

    final NodeRegexpQuery regexpQueryexp = new NodeRegexpQuery(new Term(df, "[abc]?[0-9]"));
    this._assertSirenQuery(config, regexpQueryexp, "/[abc]?[0-9]/");
  }

  @Test
  public void testQueryTermAtSamePosition()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();

    final Analyzer analyser = new Analyzer() {
      @Override
      protected TokenStreamComponents createComponents(final String fieldName,
                                                       final Reader reader) {
        final WhitespaceTokenizer t = new WhitespaceTokenizer(LuceneTestCase.TEST_VERSION_CURRENT, reader);
        final TokenStream ts = new ASCIIFoldingExpansionFilter(t);
        return new TokenStreamComponents(t, ts);
      }
    };
    config.put(ConfigurationKeys.DEFAULT_OPERATOR, Operator.OR);
    final HashMap<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("exp", analyser);
    dts.put(XSDDatatype.XSD_STRING, new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    /*
     * Here we cannot parse the toString output, because the query
     * has been expanded by DatatypeAnalyzerProcessor
     */
    Query q = bq(
      should(ntq("latte")),
      must(bq(should(ntq("cafe").setDatatype("exp")),
              should(ntq("café").setDatatype("exp")))),
      should("the")
    ).getQuery();
    assertEquals(q, parse(config, "latte +exp(café) the"));

    q = bq(
      must(bq(should(ntq("cafe").setDatatype("exp")),
              should(ntq("café").setDatatype("exp"))))
    ).getQuery();
    assertEquals(q, parse(config, "+exp(café)"));

    q = bq(
      must(bq(should(ntq("cafe").setDatatype("exp")),
              should(ntq("café").setDatatype("exp")))),
      must(bq(should(ntq("mate").setDatatype("exp")),
              should(ntq("maté").setDatatype("exp"))))
    ).getQuery();
    assertEquals(q, parse(config, "exp(+café +maté)"));

    q = bq(
      must(bq(should(ntq("cafe").setDatatype("exp")),
              should(ntq("café").setDatatype("exp")))),
      not(bq(should(ntq("mate").setDatatype("exp")),
             should(ntq("maté").setDatatype("exp"))))
    ).getQuery();
    assertEquals(q, parse(config, "exp(+café -maté)"));

    q = bq(
      should(bq(should(ntq("cafe").setDatatype("exp")),
                should(ntq("café").setDatatype("exp")))),
      should(bq(should(ntq("mate").setDatatype("exp")),
                should(ntq("maté").setDatatype("exp"))))
    ).getQuery();
    assertEquals(q, parse(config, "exp(café maté)"));
  }

  @Test
  public void testSingleWord()
  throws Exception {
    final Query q = ntq("hello").getLuceneProxyQuery();
    this._assertSirenQuery(q, "hello");
  }

  @Test(expected=QueryNodeException.class)
  public void testParseEmpty()
  throws Exception {
    this.parse(null, "");
  }

  @Test(expected=IllegalArgumentException.class)
  public void testTwigQueryNodeWithMoreThan2Children()
  throws Exception {
    final KeywordQueryParser parser = new KeywordQueryParser();
    final QueryNodeProcessorPipeline pipeline = new QueryNodeProcessorPipeline();
    pipeline.add(new QueryNodeProcessorImpl() {
      @Override
      protected List<QueryNode> setChildrenOrder(final List<QueryNode> children)
      throws QueryNodeException {
        return children;
      }
      @Override
      protected QueryNode preProcessNode(final QueryNode node)
      throws QueryNodeException {
        if (node instanceof TwigQueryNode) {
          node.add(new FieldQueryNode("field", "text", 0, 4));
        }
        return node;
      }
      @Override
      protected QueryNode postProcessNode(final QueryNode node)
      throws QueryNodeException {
        return node;
      }
    });
    parser.setQueryNodeProcessor(pipeline);

    parser.parse("a : b", SirenTestCase.DEFAULT_TEST_FIELD);
  }

  @Test
  public void testQName()
  throws Exception {
    final String qnames = "./src/test/resources/conf/qnames";
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    config.put(KeywordConfigurationKeys.QNAMES, this.loadQNamesFile(qnames));
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("uri", new AnyURIAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    final Query q = ntq("http://xmlns.com/foaf/0.1/name")
                    .setDatatype("uri")
                    .getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "uri('foaf:name')");
  }

  @Test
  public void testQNameInDatatype()
  throws Exception {
    final String qnames = "./src/test/resources/conf/qnames";
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    config.put(KeywordConfigurationKeys.QNAMES, this.loadQNamesFile(qnames));
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put(XSDDatatype.XSD_LONG, new LongNumericAnalyzer(4));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    final NodeNumericRangeQuery q = NodeNumericRangeQuery.newLongRange(SirenTestCase.DEFAULT_TEST_FIELD,
      4, 50l, 60l, true, false);
    q.setDatatype(XSDDatatype.XSD_LONG);
    this._assertSirenQuery(config, new LuceneProxyNodeQuery(q), "xsd:long([50 TO 60})");
  }

  @Test
  public void testNotQName() throws Exception {
    final String qnames = "./src/test/resources/conf/qnames";
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    config.put(KeywordConfigurationKeys.QNAMES, this.loadQNamesFile(qnames));
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("ws", new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    final Query q = ntq("mailto:aidan.hogan@deri.org")
                    .setDatatype("ws")
                    .getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "ws('mailto:aidan.hogan@deri.org')");
  }

  @Test
  public void testInvalidQName() throws Exception {
    final String query = "ws('http:' 'foaf:2' 'foaf:-qw')";
    final String qnames = "./src/test/resources/conf/qnames";
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    config.put(KeywordConfigurationKeys.QNAMES, this.loadQNamesFile(qnames));
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("ws", new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    final Query q = bq(
      must(ntq("http:").setDatatype("ws")),
      must(ntq("foaf:2").setDatatype("ws")),
      must(ntq("foaf:-qw").setDatatype("ws"))
    ).getQuery();
    this._assertSirenQuery(config, q, query);
  }

  @Test
  public void testQNameHTTP() throws Exception {
    final String query = "uri('http://ns/#s' 'http://ns/p' 'http://ns/o')";
    final String qnames = "./src/test/resources/conf/qnames";
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    config.put(KeywordConfigurationKeys.QNAMES, this.loadQNamesFile(qnames));
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("uri", new AnyURIAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    final Query q = bq(
      must(ntq("http://ns/#s").setDatatype("uri")),
      must(ntq("http://ns/p").setDatatype("uri")),
      must(ntq("http://ns/o").setDatatype("uri"))
    ).getQuery();
    this._assertSirenQuery(config, q, query);
  }

  @Test
  public void testEmptyLeafTwig() throws Exception {
    final Query q = twq(1).root(ntq("hello")).getLuceneProxyQuery();
    this._assertSirenQuery(q, "hello : *");
  }

  @Test
  public void testEmptyInternalNodeTwig()
  throws Exception {
    final Query q = twq(1).root(ntq("hello"))
                          .with(twq(2).with(ntq("world")))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "hello : * : world");
  }

  @Test
  public void testTwigQueryNodeParent()
  throws Exception {
    final TwigQueryNode twig = new TwigQueryNode(new WildcardNodeQueryNode(),
                                                 new WildcardNodeQueryNode());
    final FieldQueryNode term = new FieldQueryNode("field", "term", 0, 4);
    assertTrue(term.getParent() == null);
    assertEquals(twig, twig.getRoot().getParent());
    assertEquals(twig, twig.getChild().getParent());
    twig.setRoot(term);
    twig.setChild(term);
    assertEquals(twig, twig.getRoot().getParent());
    assertEquals(twig, twig.getChild().getParent());
  }

  @Test
  public void testEmptyDescendantTwig()
  throws Exception {
    final Query q = twq(1).root(ntq("aaa"))
                          .with(twq(2).with(twq(3).with(ntq("b"))))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "aaa : * : * : b");
  }

  @Test(expected=QueryNodeException.class)
  public void testEmptyTwig()
  throws QueryNodeException {
    this.parse(null, "* : *");
  }

  @Test(expected=QueryNodeException.class)
  public void testBadObjectQuery() throws QueryNodeException {
    this.parse(null, "{ a }");
  }

  @Test
  public void testMultipleWords()
  throws Exception {
    final Query q = bq(must("hello", "world")).getQuery();
    this._assertSirenQuery(q, "hello world");
  }

  @Test(expected=QueryNodeException.class)
  public void testUnsupportedSlopQuery() throws QueryNodeException {
    this.parse(null, "\"hello world\"~2");
  }

  @Test
  public void testURIsWithDefaultOR()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    config.put(ConfigurationKeys.DEFAULT_OPERATOR, Operator.OR);
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("uri", new AnyURIAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    final Query q = bq(
      should(ntq("http://www.google.com").setDatatype("uri")),
      should(ntq("http://hello.world#me").setDatatype("uri"))
    ).getQuery();
    this._assertSirenQuery(config, q, "uri('http://www.google.com' 'http://hello.world#me')");
  }

  @Test
  public void testURIsWithDefaultAND()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    config.put(ConfigurationKeys.DEFAULT_OPERATOR, Operator.AND);
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("uri", new AnyURIAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    final Query q = bq(
      must(ntq("http://www.google.com").setDatatype("uri")),
      must(ntq("http://hello.world#me").setDatatype("uri"))
    ).getQuery();
    this._assertSirenQuery(config, q, "uri('http://www.google.com' 'http://hello.world#me')");
  }

  @Test
  public void testCompoundQuery()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put(XSDDatatype.XSD_STRING, new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    dts.put("uri", new AnyURIAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    final Query q = bq(
      should(ntq("http://www.google.com").setDatatype("uri")),
      must(ntq("hello")),
      not(ntq("world"))
    ).getQuery();
    this._assertSirenQuery(config, q, "uri('http://www.google.com/') +hello -world");
  }

  @Test(expected=QueryNodeException.class)
  public void testFuzzyQuery1()
  throws Exception {
    final KeywordQueryParser parser = new KeywordQueryParser();
    parser.setAllowFuzzyAndWildcard(false);
    parser.parse("miche~", SirenTestCase.DEFAULT_TEST_FIELD);
  }

  @Test
  public void testFuzzyQuery2()
  throws Exception {
    final NodeQuery q1 = new NodeFuzzyQuery(new Term(SirenTestCase.DEFAULT_TEST_FIELD, "michel"));
    this._assertSirenQuery(new LuceneProxyNodeQuery(q1), "michel~");

    final TwigQuery q2 = new TwigQuery(1);
    q2.addChild(q1, NodeBooleanClause.Occur.MUST);
    this._assertSirenQuery(new LuceneProxyNodeQuery(q2), "* : michel~");

    final int numEdits = FuzzyQuery.floatToEdits(0.8f, "michel".codePointCount(0, "michel".length()));
    final NodeQuery q3 = new NodeFuzzyQuery(new Term(SirenTestCase.DEFAULT_TEST_FIELD, "michel"), numEdits);
    this._assertSirenQuery(new LuceneProxyNodeQuery(q3), "michel~0.8");

    // first tilde is escaped, not the second one
    final NodeQuery q4 = new NodeFuzzyQuery(new Term(SirenTestCase.DEFAULT_TEST_FIELD, "http://sw.deri.org/~aida"));
    this._assertSirenQuery(new LuceneProxyNodeQuery(q4), "'http://sw.deri.org/~aida'~");
  }

  @Test(expected=QueryNodeException.class)
  public void testWildcardQuery1() throws Exception {
    final KeywordQueryParser parser = new KeywordQueryParser();
    parser.setAllowFuzzyAndWildcard(false);
    parser.parse("miche*", SirenTestCase.DEFAULT_TEST_FIELD);
  }

  @Test
  public void testWildcardQuery2()
  throws Exception {
    final NodeQuery q1 = new NodeWildcardQuery(new Term(SirenTestCase.DEFAULT_TEST_FIELD, "st*e.ca?as"));
    this._assertSirenQuery(new LuceneProxyNodeQuery(q1), "st*e.ca?as");
  }

  @Test
  public void testWildcardInURI() throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("uri", new AnyURIAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    Query q = ntq("http://example.com/~foo=bar").setDatatype("uri").getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "uri('http://example.com/~foo=bar')");

    q = ntq("http://example.com/?foo=bar").setDatatype("uri").getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "uri('http://example.com/?foo=bar')");
  }

  @Test
  public void testEncoding() throws Exception {
    final Query q = ntq("möller").getLuceneProxyQuery();
    this._assertSirenQuery(q, "möller");
  }

  @Test
  public void testDashedURI() throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("uri", new AnyURIAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    final Query q = ntq("http://semantic-conference.com/session/569")
                    .setDatatype("uri").getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "uri('http://semantic-conference.com/session/569/')");
  }

  @Test
  public void testDisabledFieldQuery()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("ws", new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    final Query q = bq(
      must(ntq("foaf:name").setDatatype("ws")),
      not(ntq("foaf\\:person").setDatatype("ws")),
      should(ntq("domain:dbpedia.org").setDatatype("ws")),
      should(ntq("http://test.org/").setDatatype("ws")),
      should(ntq("http://test2.org/").setDatatype("ws"))
    ).getQuery();
    this._assertSirenQuery(config, q, "ws(+'foaf:name' -'foaf\\:person' 'domain:dbpedia.org' 'http://test.org/' 'http://test2.org/')");
  }

  @Test
  public void testMailtoURI()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("ws", new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    final Query q1 = ntq("mailto:stephane.campinas@deri.org")
                     .setDatatype("ws").getLuceneProxyQuery();
    this._assertSirenQuery(config, q1, "ws('mailto:stephane.campinas@deri.org')");

    final Query q2 = bq(must(ntq("mailto:stephane.campinas@deri.org").setDatatype("ws")),
                        must(ntq("domain:dbpedia.org").setDatatype("ws"))
               ).getQuery();
    this._assertSirenQuery(config, q2, "ws('mailto:stephane.campinas@deri.org' 'domain:dbpedia.org')");
  }

  /**
   * Test for special Lucene characters within URIs.
   */
  @Test
  public void testLuceneSpecialCharacter()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("ws", new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    /*
     * Test special tilde character
     */
    Query q = ntq("http://sw.deri.org/~aidanh").setDatatype("ws")
              .getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "ws('http://sw.deri.org/~aidanh')");

    /*
     * ? Wildcard
     */
    q = ntq("http://example.com/?foo=bar").setDatatype("ws")
        .getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "ws('http://example.com/?foo=bar')");

    q = ntq("http://example.com/?foo=bar").setDatatype("ws")
        .getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "ws('http://example.com/?foo=bar')");
  }

  @Test
  public void testPhraseQuery()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("ws", new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    final Query q = npq("a", "simple", "literal").setDatatype("ws")
                    .getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "ws(\"a simple literal\")");
  }

  @Test
  public void testNestedGroups()
  throws Exception {
    final Query q = bq(
      must(ntq("test")),
      must(
        bq(must(
          bq(should("literal", "uri", "resource")),
          bq(should("pattern", "patterns", "query"))
        ))
      )
    ).getQuery();
    this._assertSirenQuery(q, "Test AND ((literal OR uri OR resource) AND (pattern OR patterns OR query))");
  }

  @Test
  public void testNestedGroups2()
  throws Exception {
    final Query q = bq(
      must(ntq("test")),
      must(
        bq(must(
          bq(should(ntq("literal")), must(ntq("uri")), not(ntq("resource"))),
          bq(should(ntq("pattern")), must(ntq("patterns")), not(ntq("query")))
        ))
      )
    ).getQuery();
    this._assertSirenQuery(q, "Test AND ((literal OR +uri OR -resource) AND (pattern OR +patterns OR -query))");
  }

  @Test
  public void testBoostQuery()
  throws Exception {
    final BooleanQuery q = new BooleanQuery();
    q.add(ntq("c").getLuceneProxyQuery(), BooleanClause.Occur.MUST);
    final NodeQuery nq = ntq("b").getNodeQuery();
    nq.setBoost(2);
    q.add(new LuceneProxyNodeQuery(nq), BooleanClause.Occur.MUST);
    this._assertSirenQuery(q, "c b^2");
  }

  @Test
  public void testTwigQuery()
  throws Exception {
    final Query q = twq(1).root(ntq("aaa"))
                          .with(ntq("b"))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "aaa : b");
    this._assertSirenQuery(q, "aaa:b");
  }

  @Test
  public void testTwigQueryDatatype()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("ws", new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    dts.put(JSONDatatype.JSON_FIELD, new StandardAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    // json:field is always applied on the top level node of the twig.
    final Query q = twq(1).root(ntq("aaa"))
                          .with(ntq("b").setDatatype("ws"))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "AAA : ws(b)");
  }

  @Test
  public void testTwigQueryDatatypeOnRoot()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("ws", new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    // json:field is always applied on the top level node of the twig.
    final Query q = twq(1).root(ntq("AAA").setDatatype("ws"))
                          .with(ntq("b").setDatatype("ws"))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "ws(AAA) : ws(b)");
    this._assertSirenQuery(config, q, "ws(AAA : b)");
  }

  @Test
  public void testTwigQueryStopWord()
  throws Exception {
    final Query q = twq(1).root(ntq("aaa"))
                          .with(twq(2).with(ntq("coffee")))
                    .getLuceneProxyQuery();
    // The word "the" is a stop word, and is therefore removed by the standard
    // analyzer associated to xsd:string.
    this._assertSirenQuery(q, "aaa : the : coffee");
  }

  @Test(expected=QueryNodeException.class)
  public void testTwigQueryBothStopWords()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final HashMap<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put(JSONDatatype.JSON_FIELD, new StandardAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    dts.put(XSDDatatype.XSD_STRING, new StandardAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    // Word "a" and "the" are stop words, and are therefore removed by the
    // standard analyzer associated to json:field and xsd:string, respectively.
    parse(config, "a : the");
  }

  @Test
  public void testArrayQueryStopWord()
  throws Exception {
    final Query q = twq(1).root(ntq("aaa"))
                          .with(ntq("coffee"))
                    .getLuceneProxyQuery();
    // The word "the" is a stop word, and is therefore removed by the standard
    // analyzer associated to xsd:string.
    this._assertSirenQuery(q, "aaa : [ the , coffee ]");
  }

  @Test
  public void testRootLevelTwigQuery()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    config.put(KeywordConfigurationKeys.ROOT_LEVEL, 2);

    final Query q = twq(2).root(ntq("aaa"))
                          .with(ntq("b"))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(config, q, "aaa : b");
  }

  @Test
  public void testBooleanTwigQuery()
  throws Exception {
    final Query q = twq(1).root(nbq(must("aaa", "b")))
                          .with(nbq(should("c", "d")))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "aaa AND b : c OR d");
  }

  @Test
  public void testBooleanTwigQuery2()
  throws Exception {
    final Query bq = bq(
      must(ntq("e")),
      must(twq(1).root(ntq("b"))
              .with(nbq(should("c", "d")))
      )
    ).getQuery();
    this._assertSirenQuery(bq, "e AND (b : c OR d)");
  }

  @Test
  public void testBooleanTwigQuery3()
  throws Exception {
    final Query q = twq(1).root(ntq("aaa"))
                          .with(nbq(must("c", "d")))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "aaa : c AND d");
  }

  @Test
  public void testBooleanTwigQuery4()
  throws Exception {
    final Query q = twq(1).root(ntq("aaa"))
                          .with(nbq(not("c"), must("d")))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "aaa : -c AND d");
  }

  @Test
  public void testTwigQueriesConjunction()
  throws Exception {
    final Query bq = bq(must(
      twq(1).root(ntq("aaa"))
            .with(ntq("c"))), must(
      twq(1).root(ntq("b"))
            .with(ntq("d"))
    )).getQuery();
    this._assertSirenQuery(bq, "(aaa : c) AND (b : d)");
  }

  @Test
  public void testTwigQueriesDisjunction()
  throws Exception {
    final Query bq = bq(should(
      twq(1).root(ntq("aaa"))
            .with(ntq("c"))), should(
      twq(1).root(ntq("b"))
            .with(ntq("d"))
    )).getQuery();
    this._assertSirenQuery(bq, "(aaa : c) OR (b : d)");
  }

  @Test
  public void testTwigQueriesComplement()
  throws Exception {
    final Query bq = bq(must(
      twq(1).root(ntq("aaa"))
            .with(ntq("c"))
     ), not(
      twq(1).root(ntq("b"))
            .with(ntq("d"))
    )).getQuery();
    this._assertSirenQuery(bq, "(aaa : c) - (b : d)");
  }

  /**
   * SRN-91
   */
  @Test
  public void testTwigComplement2()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("ws", new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    dts.put(JSONDatatype.JSON_FIELD, new StandardAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    final Query bq = bq(must(
      twq(1)
            .with(ntq("literal").setDatatype("ws"))
     ), not(
      twq(1)
            .with(ntq("http://o.org").setDatatype("ws"))
    )).getQuery();
    this._assertSirenQuery(config, bq, "ws((* : literal) NOT (* : 'http://o.org'))");
  }

  @Test
  public void testTwigQueryLineFeed()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put(XSDDatatype.XSD_STRING, new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    dts.put("ws", new WhitespaceAnalyzer(LuceneTestCase.TEST_VERSION_CURRENT));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    final Query bq = bq(must(
      twq(1)
            .with(ntq("literal"))), must(
      twq(1)
            .with(ntq("http://o.org").setDatatype("ws"))
    )).getQuery();
    this._assertSirenQuery(config, bq, "(* : literal) AND\r\n (* \n\r : \n ws('http://o.org'))");
  }

  @Test
  public void testPrefixQuery()
  throws Exception {
    final Query ntq = new LuceneProxyNodeQuery(
      new NodePrefixQuery(new Term(SirenTestCase.DEFAULT_TEST_FIELD, "lit"))
    );
    this._assertSirenQuery(ntq, "lit*");

    final TwigQuery twq = new TwigQuery(1);
    twq.addChild(new NodePrefixQuery(new Term(SirenTestCase.DEFAULT_TEST_FIELD, "lit")),
      NodeBooleanClause.Occur.MUST);
    this._assertSirenQuery(new LuceneProxyNodeQuery(twq), "* : lit*");
  }

  @Test
  public void testEmptyRootQuery()
  throws Exception {
    final Query q = twq(1)
                          .with(ntq("b"))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "* : b");
  }

  @Test
  public void testNestedTwigQuery()
  throws Exception {
    final Query q = twq(1).root(ntq("aaa"))
                          .with(twq(2).root(ntq("b")).with(ntq("c")))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "aaa : b : c");
  }

  @Test
  public void testNestedTwigQuery2()
  throws Exception {
    final Query q = twq(1).root(ntq("aaa"))
                          .with(twq(2).root(nbq(must("d", "b")))
                                      .with(ntq("c")))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "aaa : d AND b : c");
  }

  @Test
  public void testArrayQuery()
  throws Exception {
    final Query q = twq(1)
                          .with(ntq("b"))
                          .with(ntq("c"))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "* : [ b, c ]");
  }

  @Test
  public void testArrayQueryWithModifiers()
  throws Exception {
    final Query q = twq(1)
                          .with(ntq("aaa"))
                          .without(ntq("b"))
                          .with(ntq("c"))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "* : [ aaa, -b, +c ]");
  }

  @Test
  public void testArrayQueryWithModifiers2()
  throws Exception {
    final Query q = twq(1)
                          .with(twq(2)
                                      .with(ntq("aaa"))
                                      .with(ntq("b")))
                          .without(twq(2)
                                         .with(ntq("c"))
                                         .with(ntq("d")))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "* : [ * : [ aaa, b ], -(*:[ c, d ]) ]");
  }

  // TODO: issue GH-52
  @Ignore
  @Test
  public void testArrayQueryWithModifiers3()
  throws QueryNodeException {
    this.parse(null, "* : [ a, -[ c, d ] ]");
  }

  @Test
  public void testNestedArrayQuery()
  throws Exception {
    final Query q = twq(1)
                          .with(ntq("b"))
                          .with(twq(2)
                                      .with(ntq("c"))
                                      .with(ntq("d")))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "* : [ b, * : [ c, d ] ]");
    this._assertSirenQuery(q, "* : [ b, [ c , d ] ]");
  }

  /**
   * Tests for a nested array with a single child
   */
  @Test
  public void testNestedArrayQuery2()
  throws Exception {
    final Query q = twq(1)
                          .with(ntq("aaa"))
                          .with(twq(2)
                                      .with(ntq("b")))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "* : [ aaa, [ b ] ]");
  }

  /**
   * A grouping of children is not possible
   */
  @Test(expected=QueryNodeException.class)
  public void testWrongArrayQuery1()
  throws QueryNodeException {
    this.parse(null, "* : b AND [ c , d ]");
  }

  /**
   * A grouping of children is not possible
   */
  @Test(expected=QueryNodeException.class)
  public void testWrongArrayQuery2()
  throws QueryNodeException {
    this.parse(null, "* : [a, b] AND [ c , d ]");
  }

  /**
   * A grouping of children is not possible
   */
  @Test(expected=QueryNodeException.class)
  public void testWrongArrayQuery3()
  throws QueryNodeException {
    this.parse(null, "* : +[a, b] -[ c , d ]");
  }

  /**
   * An array query is only possible inside a Twig query
   */
  @Test(expected=QueryNodeException.class)
  public void testWrongArrayQuery4()
  throws QueryNodeException {
    this.parse(null, "[ c , d ]");
  }

  /**
   * An array query is only possible inside a Twig query
   */
  @Test(expected=QueryNodeException.class)
  public void testWrongArrayQuery5()
  throws QueryNodeException {
    this.parse(null, "a AND [ c , d ]");
  }

  /**
   * An array query is only possible as the value of a Twig query
   */
  @Test(expected=QueryNodeException.class)
  public void testWrongArrayQuery6()
  throws QueryNodeException {
    this.parse(null, "a :: [ c , d ] :: e");
  }

  @Test
  public void testArrayQueryWithBoolean()
  throws Exception {
    final Query q = twq(1)
                          .with(ntq("b"))
                          .with(nbq(must("c", "d")))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "* : [ b, c AND d ]");
  }

  /**
   * issue GH-50
   */
  @Test
  public void testObjectQuery1()
  throws Exception {
    final Query q = twq(1)
                          .with(twq(2).root(ntq("aaa"))
                                      .with(ntq("b")))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "{ aaa : b }");
  }

  /**
   * issue GH-50
   */
  @Test
  public void testObjectQuery2()
  throws Exception {
    final Query q = twq(1).with(twq(2).with(twq(3).root(ntq("aaa")).with(ntq("b"))))
    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "* : { aaa : b }");
  }

  /**
   * issue GH-50
   */
  @Test
  public void testObjectQuery3()
  throws Exception {
    final Query q = twq(1).with(twq(2).with(ntq("b")))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "{ * : b }");
  }

  /**
   * issue GH-50
   */
  @Test
  public void testObjectQuery4()
  throws Exception {
    final Query q = twq(1).with(twq(2).root(ntq("aaa"))).getLuceneProxyQuery();
    this._assertSirenQuery(q, "{ aaa : * }");
  }

  /**
   * issue GH-50
   */
  @Test(expected=QueryNodeException.class)
  public void testObjectQuery5()
  throws QueryNodeException {
    this.parse(null, "{ * : * }");
  }

  @Test
  public void testObjectQueryWithMultipleFields1()
  throws Exception {
    final Query q = twq(1).root(ntq("p"))
                          .with(twq(2).with(twq(3).root(ntq("aaa")).with(ntq("b")))
                                      .with(twq(3).root(ntq("c")).with(ntq("d"))))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "p : { aaa : b , c : d }");
  }

  @Test
  public void testObjectQueryWithMultipleFields2()
  throws Exception {
    final Query q = twq(1).root(ntq("p"))
                          .with(twq(2).with(twq(3).root(ntq("aaa")))
                                      .with(twq(3).with(ntq("d"))))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "p : { aaa : * , * : d }");
  }

  @Test
  public void testObjectWithArrayQuery()
  throws Exception {
    final Query q = twq(1).root(ntq("aaa"))
                          .with(ntq("d"))
                          .with(twq(3).with(twq(4).with(ntq("b"))))
                    .getLuceneProxyQuery();
    this._assertSirenQuery(q, "aaa : [ d, { * : b } ]");
  }

  @Test(expected=QueryNodeException.class)
  public void testMultiPhraseQuery()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();

    final Analyzer analyser = new Analyzer() {
      @Override
      protected TokenStreamComponents createComponents(final String fieldName,
                                                       final Reader reader) {
        final WhitespaceTokenizer t = new WhitespaceTokenizer(LuceneTestCase.TEST_VERSION_CURRENT, reader);
        final TokenStream ts = new ASCIIFoldingExpansionFilter(t);
        return new TokenStreamComponents(t, ts);
      }
    };
    final HashMap<String, Analyzer> dts = new HashMap<String, Analyzer>();
    dts.put("exp", analyser);
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, dts);

    this.parse(config, "exp(\"café coffe\")");
  }

  @Test
  public void testRangeQueries()
  throws Exception {
    NodeQuery q = new NodeTermRangeQuery(SirenTestCase.DEFAULT_TEST_FIELD,
      new BytesRef("a"), new BytesRef("b"), true, true);
    this._assertSirenQuery(new LuceneProxyNodeQuery(q), "[ a TO b ]");

    q = new NodeTermRangeQuery(SirenTestCase.DEFAULT_TEST_FIELD,
      new BytesRef("a"), new BytesRef("b"), false, true);
    this._assertSirenQuery(new LuceneProxyNodeQuery(q), "{ a TO b ]");

    q = new NodeTermRangeQuery(SirenTestCase.DEFAULT_TEST_FIELD,
      new BytesRef("a"), new BytesRef("b"), true, false);
    this._assertSirenQuery(new LuceneProxyNodeQuery(q), "[ a TO b }");

    q = new NodeTermRangeQuery(SirenTestCase.DEFAULT_TEST_FIELD,
      new BytesRef("a"), new BytesRef("b"), false, false);
    this._assertSirenQuery(new LuceneProxyNodeQuery(q), "{ a TO b }");

    final TwigQuery twq1 = new TwigQuery(1);
    twq1.addChild(q, NodeBooleanClause.Occur.MUST);
    // TODO parsing the output of #toString of twq1 is not possible because of GH-52
    assertEquals(new LuceneProxyNodeQuery(twq1), this.parse(null, "* : { a TO b }"));

    final TwigQuery twq2 = new TwigQuery(1);
    twq2.addChild(new NodeTermRangeQuery(SirenTestCase.DEFAULT_TEST_FIELD,
      new BytesRef("a"), new BytesRef("b"), true, true), NodeBooleanClause.Occur.MUST);
    twq2.addChild(q, NodeBooleanClause.Occur.MUST);
    assertEquals(new LuceneProxyNodeQuery(twq2), this.parse(null, "* : [ [ a TO b ], { a TO b } ]"));
  }

  @Test
  public void testRegexQueries()
  throws Exception {
    final Query reg = new LuceneProxyNodeQuery(
      new NodeRegexpQuery(new Term(SirenTestCase.DEFAULT_TEST_FIELD, "s*e"))
    );
    this._assertSirenQuery(reg, "/s*e/");
  }

  @Test
  public void testPhrase1term()
  throws Exception {
    final Query q = ntq("test").getLuceneProxyQuery();
    this._assertSirenQuery(q, "\"test\"");
  }

  @Test(expected=IllegalArgumentException.class)
  public void testNotRegisteredDatatype()
  throws Exception {
    this.parse(null, "notRegistered(aaa)");
  }

  @Test
  public void testDatatypes1()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    // Set the default datatypes
    final Map<String, Analyzer> datatypes = new HashMap<String, Analyzer>();
    datatypes.put("xsd:int", new IntNumericAnalyzer(4));

    final Analyzer dateAnalyser = new Analyzer() {
      @Override
      protected TokenStreamComponents createComponents(final String fieldName,
                                                       final Reader reader) {
        final WhitespaceTokenizer t = new WhitespaceTokenizer(LuceneTestCase.TEST_VERSION_CURRENT, reader);
        final TokenStream ts = new LowerCaseFilter(LuceneTestCase.TEST_VERSION_CURRENT, t);
        return new TokenStreamComponents(t, ts);
      }
    };
    datatypes.put("xsd:date", dateAnalyser);

    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, datatypes);

    // Test for custom datatypes
    final BooleanQuery bq1 = new BooleanQuery();
    final NodePrimitiveQuery range1 = NodeNumericRangeQuery
        .newIntRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 12, 21, true, true);
    range1.setDatatype("xsd:int");
    bq1.add(new LuceneProxyNodeQuery(range1), BooleanClause.Occur.MUST);
    final Query npq = npq("12", "oct", "2012").setDatatype("xsd:date")
                       .getLuceneProxyQuery();
    bq1.add(npq, BooleanClause.Occur.MUST);
    this._assertSirenQuery(config, bq1, "xsd:int([12 TO 21]) xsd:date(\"12 Oct 2012\")");
  }

  @Test
  public void testDatatypes2()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    // Set the default datatypes
    final Map<String, Analyzer> datatypes = new HashMap<String, Analyzer>();
    datatypes.put("xsd:int", new IntNumericAnalyzer(4));

    final Analyzer dateAnalyser = new Analyzer() {
      @Override
      protected TokenStreamComponents createComponents(final String fieldName,
                                                       final Reader reader) {
        final WhitespaceTokenizer t = new WhitespaceTokenizer(LuceneTestCase.TEST_VERSION_CURRENT, reader);
        final TokenStream ts = new LowerCaseFilter(LuceneTestCase.TEST_VERSION_CURRENT, t);
        return new TokenStreamComponents(t, ts);
      }
    };
    datatypes.put("xsd:date", dateAnalyser);

    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, datatypes);

    // Test for custom datatypes
    final BooleanQuery bq1 = new BooleanQuery();

    final BooleanQuery bq2 = new BooleanQuery();
    final NodePrimitiveQuery range1 = NodeNumericRangeQuery
        .newIntRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 12, 21, true, true);
    range1.setDatatype("xsd:int");
    bq2.add(new LuceneProxyNodeQuery(range1), BooleanClause.Occur.MUST);
    final NodePrimitiveQuery range2 = NodeNumericRangeQuery
        .newIntRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 12, 12, true, true);
    range2.setDatatype("xsd:int");
    bq2.add(new LuceneProxyNodeQuery(range2), BooleanClause.Occur.MUST);

    bq1.add(bq2, BooleanClause.Occur.MUST);
    final Query npq = npq("12", "oct", "2012").setDatatype("xsd:date")
    .getLuceneProxyQuery();
    bq1.add(npq, BooleanClause.Occur.MUST);
    this._assertSirenQuery(config, bq1, "xsd:int([12 TO 21] '12') xsd:date(\"12 Oct 2012\")");
  }

  /**
   * Multiple terms in a datatype are in a group
   */
  @Test
  public void testDatatypes3()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    // Set the default datatypes
    final Map<String, Analyzer> datatypes = new HashMap<String, Analyzer>();
    datatypes.put("xsd:int", new IntNumericAnalyzer(4));

    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, datatypes);

    // Test for custom datatypes
    final BooleanQuery bq1 = new BooleanQuery();
    final NodePrimitiveQuery range1To10 = NodeNumericRangeQuery
        .newIntRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 1, 10, true, true);
    range1To10.setDatatype("xsd:int");
    final NodePrimitiveQuery range20To40 = NodeNumericRangeQuery
        .newIntRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 20, 40, true, true);
    range20To40.setDatatype("xsd:int");
    bq1.add(new LuceneProxyNodeQuery(range1To10), BooleanClause.Occur.SHOULD);
    bq1.add(new LuceneProxyNodeQuery(range20To40), BooleanClause.Occur.SHOULD);

    final BooleanQuery bq2 = new BooleanQuery();
    final NodePrimitiveQuery range10To15 = NodeNumericRangeQuery
        .newIntRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 10, 15, true, true);
    range10To15.setDatatype("xsd:int");
    final NodePrimitiveQuery range50To55 = NodeNumericRangeQuery
        .newIntRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 50, 55, true, true);
    range50To55.setDatatype("xsd:int");
    bq2.add(new LuceneProxyNodeQuery(range10To15), BooleanClause.Occur.SHOULD);
    bq2.add(new LuceneProxyNodeQuery(range50To55), BooleanClause.Occur.SHOULD);

    final BooleanQuery bq3 = new BooleanQuery();
    bq3.add(bq1, BooleanClause.Occur.MUST);
    bq3.add(bq2, BooleanClause.Occur.MUST);
    this._assertSirenQuery(config, bq3, "xsd:int([1 TO 10] OR [20 TO 40]) AND xsd:int([10 TO 15] OR [50 TO 55])");
  }

  @Test
  public void testDatatypes4()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    // Set the default datatypes
    final Map<String, Analyzer> datatypes = new HashMap<String, Analyzer>();
    datatypes.put("xsd:int", new IntNumericAnalyzer(4));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, datatypes);

    // Test for custom datatypes
    final BooleanQuery bq1 = new BooleanQuery();
    final NodePrimitiveQuery range1 = NodeNumericRangeQuery
        .newIntRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 1, 1, true, true);
    range1.setDatatype("xsd:int");
    bq1.add(new LuceneProxyNodeQuery(range1), BooleanClause.Occur.MUST);
    final NodePrimitiveQuery range2 = NodeNumericRangeQuery
      .newIntRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 2, 2, true, true);
    range2.setDatatype("xsd:int");
    bq1.add(new LuceneProxyNodeQuery(range2), BooleanClause.Occur.MUST_NOT);

    this._assertSirenQuery(config, bq1, "+xsd:int(1) -xsd:int(2)");
    this._assertSirenQuery(config, bq1, "xsd:int(+1 -2)");
  }

  @Test(expected=AssertionError.class)
  public void testNumericDatatypeWrongPrecision()
  throws Exception {
    final KeywordQueryParser parser = new KeywordQueryParser();
    // Set the default datatypes
    final Map<String, Analyzer> datatypes = new HashMap<String, Analyzer>();
    datatypes.put("int", new IntNumericAnalyzer(4));
    parser.setDatatypeAnalyzers(datatypes);

    final NodeQuery rangeWrong = NodeNumericRangeQuery.newIntRange(SirenTestCase.DEFAULT_TEST_FIELD, 42, 12, 21, true, true);
    assertEquals(new LuceneProxyNodeQuery(rangeWrong), parser.parse("int([12 TO 21])", SirenTestCase.DEFAULT_TEST_FIELD));
  }

  /**
   * Test for incorrect numeric values at query time.
   * <p>
   * Numeric ranges get processed with {@link NodeNumericRangeQueryNodeProcessor}.
   * Single numeric values are processed with {@link NodeNumericQueryNodeProcessor}.
   */
  @Test(expected=QueryNodeException.class)
  public void testNumericQuery1()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> datatypes = new HashMap<String, Analyzer>();
    datatypes.put("int", new IntNumericAnalyzer(4));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, datatypes);

    parse(config, "int([10 TO bla])");
  }

  /**
   * Test for wildcard bounds.
   * <p>
   * Numeric ranges get processed with {@link NodeNumericRangeQueryNodeProcessor}.
   * Single numeric values are processed with {@link NodeNumericQueryNodeProcessor}.
   */
  @Test
  public void testNumericQuery2()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> datatypes = new HashMap<String, Analyzer>();
    datatypes.put("int4", new IntNumericAnalyzer(4));
    datatypes.put("float4", new FloatNumericAnalyzer(4));
    datatypes.put("long4", new LongNumericAnalyzer(4));
    datatypes.put("double4", new DoubleNumericAnalyzer(4));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, datatypes);

    // Integer
    assertOpenRange(config, "int4");
    // Float
    assertOpenRange(config, "float4");
    // Long
    assertOpenRange(config, "long4");
    // Double
    assertOpenRange(config, "double4");
  }

  private void assertOpenRange(final HashMap<ConfigurationKey, Object> config,
                               final String datatype)
  throws Exception {
    final NodePrimitiveQuery openLeft;
    final NodePrimitiveQuery openRight;

    if (datatype.equals("int4")) {
      openLeft = NodeNumericRangeQuery
          .newIntRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, null, 10, true, true);
      openLeft.setDatatype(datatype);
      openRight = NodeNumericRangeQuery
          .newIntRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 10, null, true, true);
      openRight.setDatatype(datatype);
    } else if (datatype.equals("float4")) {
      openLeft = NodeNumericRangeQuery
          .newFloatRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, null, 10f, true, true);
      openLeft.setDatatype(datatype);
      openRight = NodeNumericRangeQuery
          .newFloatRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 10f, null, true, true);
      openRight.setDatatype(datatype);
    } else if (datatype.equals("long4")) {
      openLeft = NodeNumericRangeQuery
          .newLongRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, null, 10l, true, true);
      openLeft.setDatatype(datatype);
      openRight = NodeNumericRangeQuery
          .newLongRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 10l, null, true, true);
      openRight.setDatatype(datatype);
    } else {
      openLeft = NodeNumericRangeQuery
          .newDoubleRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, null, 10d, true, true);
      openLeft.setDatatype(datatype);
      openRight = NodeNumericRangeQuery
          .newDoubleRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 10d, null, true, true);
      openRight.setDatatype(datatype);
    }

    this._assertSirenQuery(config, new LuceneProxyNodeQuery(openLeft), datatype + "([* TO 10])");
    this._assertSirenQuery(config, new LuceneProxyNodeQuery(openRight), datatype + "([10 TO *])");
  }

  /**
   * Boolean of ranges.
   * <p>
   * Numeric ranges get processed with {@link NodeNumericRangeQueryNodeProcessor}.
   * Single numeric values are processed with {@link NodeNumericQueryNodeProcessor}.
   */
  @Test
  public void testNumericQuery3()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> datatypes = new HashMap<String, Analyzer>();
    datatypes.put("int", new IntNumericAnalyzer(4));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, datatypes);
    config.put(ConfigurationKeys.DEFAULT_OPERATOR, Operator.OR);

    final NodePrimitiveQuery r1 = NodeNumericRangeQuery
        .newIntRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 50, 100, true, true);
    r1.setDatatype("int");
    final NodePrimitiveQuery r2 = NodeNumericRangeQuery
        .newIntRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 100, 500, true, true);
    r2.setDatatype("int");
    final BooleanQuery bq = new BooleanQuery();
    bq.add(new LuceneProxyNodeQuery(r1), BooleanClause.Occur.SHOULD);
    bq.add(new LuceneProxyNodeQuery(r2), BooleanClause.Occur.SHOULD);

    this._assertSirenQuery(config, bq, "int([50 TO 100] OR [100 TO 500])");
  }

  /**
   * Test for float.
   * <p>
   * Numeric ranges get processed with {@link NodeNumericRangeQueryNodeProcessor}.
   * Single numeric values are processed with {@link NodeNumericQueryNodeProcessor}.
   */
  @Test
  public void testNumericQuery4()
  throws Exception {
    final HashMap<ConfigurationKey, Object> config = new HashMap<ConfigurationKey, Object>();
    final Map<String, Analyzer> datatypes = new HashMap<String, Analyzer>();
    datatypes.put("float", new FloatNumericAnalyzer(4));
    config.put(KeywordConfigurationKeys.DATATYPES_ANALYZERS, datatypes);

    final NodePrimitiveQuery q = NodeNumericRangeQuery
        .newFloatRange(SirenTestCase.DEFAULT_TEST_FIELD, 4, 50.5f, 1000.34f, true, true);
    q.setDatatype("float");
    this._assertSirenQuery(config, new LuceneProxyNodeQuery(q), "float([50.5 TO 1000.34])");
  }

}
