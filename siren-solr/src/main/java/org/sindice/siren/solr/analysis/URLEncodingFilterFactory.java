package org.sindice.siren.solr.analysis;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;
import org.sindice.siren.analysis.filter.URLEncodingFilter;

public class URLEncodingFilterFactory extends BaseTokenFilterFactory {

  /*
   * In URI, the characters are by default encoded with UTF-8.
   * http://tools.ietf.org/html/rfc3986
   */
  public static final String DEFAULT_ENCODING = "UTF-8";
  
  @Override
  public void init(final Map<String,String> args) {
   super.init(args);
   this.assureMatchVersion();
  }
  
  @Override
  public TokenStream create(TokenStream input) {
    return new URLEncodingFilter(input, DEFAULT_ENCODING);
  }

}
