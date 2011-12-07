/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
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
 * @project solr-plugins
 * @author Renaud Delbru [ 25 Jul 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.lucene.analysis.Token;
import java_cup.runtime.*;
import org.sindice.siren.util.XSDDatatype;
import org.sindice.siren.qparser.ntriple.MetadataValue;

%%

/** 
 *  The name of the class JFlex will create will be Lexer.
 */
%class NTripleQueryTokenizerImpl

%public
%final

/**
 * The current line number can be accessed with the variable yyline,
 * the current column number with the variable yycolumn and the current
 * character number with the variable yychar.
 */
%line
%column
%char

/**
 * Both cause the scanning method to be declared as of Java type int. 
 * Actions in the specification can then return int values as tokens
 */
%integer

/** 
 * causes JFlex to compress the generated DFA table and to store it 
 * in one or more string literals.
 */
%pack

/**
 * Both options cause the generated scanner to use the full 16 bit 
 * Unicode input character set (character codes 0-65535).
 */
%unicode

%function getNextToken

%{

  public static final int EOF 	      = NTripleQueryTokenizer.EOF;
  public static final int ERROR       = NTripleQueryTokenizer.ERROR;
  public static final int AND 	      = NTripleQueryTokenizer.AND;
  public static final int OR 		      = NTripleQueryTokenizer.OR;
  public static final int MINUS       = NTripleQueryTokenizer.MINUS;
  public static final int LPAREN      = NTripleQueryTokenizer.LPAREN;
  public static final int RPAREN      = NTripleQueryTokenizer.RPAREN;
  public static final int URIPATTERN  = NTripleQueryTokenizer.URIPATTERN;
  public static final int LITERAL     = NTripleQueryTokenizer.LITERAL;
  public static final int LPATTERN    = NTripleQueryTokenizer.LPATTERN;
  public static final int WILDCARD    = NTripleQueryTokenizer.WILDCARD;
	
	public static final String[] TOKEN_TYPES = new String[] {
	    "<EOF>",
	    "<ERROR>",
	    "<AND>",
	    "<OR>",
	    "<MINUS>",
	    "<LPAREN>",
	    "<RPAREN>",
	    "<WILDCARD>",
	    "<URIPATTERN>",
	    "<LITERAL>",
	    "<LPATTERN>"
	};

	private static final
	Logger logger = LoggerFactory.getLogger(NTripleQueryTokenizerImpl.class);
	
	private final StringBuffer buffer = new StringBuffer();
	private final StringBuffer metadataBuffer = new StringBuffer();
  
  /** Datatype representing xsd:string */
  private static final char[] XSD_STRING = XSDDatatype.XSD_STRING.toCharArray();
  
	public final int yychar()	{
		return yychar;
	}
  
	/** 
	 * To create a new java_cup.runtime.Symbol with information about
	 * the current token, the token will have no value in this case.
	 */
	private Symbol symbol(int type) {
		logger.debug("Obtain token {}", type);
	  return new Symbol(type, yyline, yycolumn);
	}
	
	/**
	 * Creates a new java_cup.runtime.Symbol with information
	 * about the current token, but this object has a value.
	 */
	private Symbol symbol(int type, String value) {
		logger.debug("Obtain token {} \"{}\"", type, value);
	  return new Symbol(type, yyline, yycolumn, value);
	}
	
	private Symbol symbol(int type, MetadataValue dl) {
	logger.debug("Obtain token {} \"{}\"", type, dl);
    return new Symbol(type, yyline, yycolumn, dl);
	}
	
	/**
	 * Fills Lucene token with the current token text, removing the first and 
	 * last characters.
	 */
	final String getURIText() {
	  return new String(zzBuffer, zzStartRead + 1, zzMarkedPos - zzStartRead - 2);
	}

	/**
	 * Fills Lucene token with the current token text, removing the first and 
	 * last characters.
	 */
	final void getURIText(Token t) {
	  t.setTermBuffer(zzBuffer, zzStartRead + 1, zzMarkedPos - zzStartRead - 2);
	}
	
	/**
	 * Return the current string buffer.
	 */
	final String getLiteralText() {
		return new String(buffer);
	}
	
	/**
	 * Fills Lucene token with the current string buffer.
	 */
	final void getLiteralText(Token t) {
    char[] chars = new char[buffer.length()];
    buffer.getChars(0, buffer.length(), chars, 0);
	  t.setTermBuffer(chars, 0, chars.length);
	}
	
	/**
   * Return the datatype URI.
   *
   * <p> Return the datatype xsd:string by default.
   */
  public final char[] getDatatypeURI() {
    // If not datatype, returns by default datatype xsd:string
    if (metadataBuffer.length() == 0) {
      return XSD_STRING;
    }
    char[] chars = new char[metadataBuffer.length()];
    metadataBuffer.getChars(0, metadataBuffer.length(), chars, 0);
    return chars;
  }
	
%}

ENDOFLINE 		 =  \r|\n|\r\n

WHITESPACE     =  {ENDOFLINE} | [ \t\f]

URIREF				 =  "<" [^>]+ ">"

%state LITSTR
%state LITPATSTR

%%

<YYINITIAL> {

	{URIREF}				{ // Assign the xsd:anyURI
                    metadataBuffer.setLength(0);
                    metadataBuffer.append(XSDDatatype.XSD_ANY_URI);
	                  return URIPATTERN; }
	
	"AND" | "&&"    { return AND; }
	
	"OR" | "||"     { return OR; }
	
	"NOT" | "-"    	{ return MINUS; }
	
	"("    					{ return LPAREN; }
	
	")"    					{ return RPAREN; }
	
	"*"    					{ return WILDCARD; }
	
	\"							{ buffer.setLength(0);
	                  metadataBuffer.setLength(0);
	                  yybegin(LITSTR); }
	
	\'							{ buffer.setLength(0);
	                  metadataBuffer.setLength(0);
	                  yybegin(LITPATSTR); }
	
	{WHITESPACE}		{ /* ignore */ }
	
	
	<<EOF>>					{ return EOF; }
	
 /**
	* No token was found for the input so through an error.
	*/
	.                { return ERROR; }
 
}

<LITSTR> {

  \"\^\^{URIREF}  { yybegin(YYINITIAL);
                    // remove the first four and the last characters.
                    metadataBuffer.append(zzBuffer, zzStartRead + 4, zzMarkedPos - zzStartRead - 5);
                    return LITERAL; }
                    
	\" 							{ yybegin(YYINITIAL); return LITERAL; }

  [^\"\\]+  			{ buffer.append(yytext()); }
  
  \\\"          	{ buffer.append("\""); }
  \\.            	{ buffer.append(yytext()); }
  
  <<EOF>>					{ return ERROR; }

}

<LITPATSTR> {

  \'\^\^{URIREF}  { yybegin(YYINITIAL);
                    // remove the first four and the last characters.
                    metadataBuffer.append(zzBuffer, zzStartRead + 4, zzMarkedPos - zzStartRead - 5);
                    return LPATTERN; }
                    
	\'							{ yybegin(YYINITIAL); return LPATTERN; }

  [^\'\\]+  			{ buffer.append(yytext()); }
  
  \\\'          	{ buffer.append("\'"); }
  \\.            	{ buffer.append(yytext()); }
  
  <<EOF>>					{ return ERROR; }

}


