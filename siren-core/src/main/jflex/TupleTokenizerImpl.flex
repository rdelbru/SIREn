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
 * @author Renaud Delbru [ 16 Mar 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.sindice.siren.util.XSDDatatype;
import static org.sindice.siren.analysis.TupleTokenizer.*;

%%

/** 
 *  The name of the class JFlex will create will be Lexer.
 */
%class TupleTokenizerImpl

/**
 * Both options cause the generated scanner to use the full 16 bit 
 * Unicode input character set (character codes 0-65535).
 */
%unicode

/**
 * The current character number with the variable yychar.
 */
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

%function getNextToken

%{

	public static final String[] TOKEN_TYPES = getTokenTypes();

  StringBuffer buffer = new StringBuffer();
  StringBuffer metadataBuffer = new StringBuffer();
  
  /** Datatype representing xsd:anyURI */
  private static final char[] XSD_ANY_URI = XSDDatatype.XSD_ANY_URI.toCharArray();
  
  /** Datatype representing xsd:string */
  private static final char[] XSD_STRING = XSDDatatype.XSD_STRING.toCharArray();
  
	public final int yychar()	{
		return yychar;
	}
	
  /**
   * Fills Lucene TermAttribute with the current token text.
   */
  public final void getText(CharTermAttribute t) {
    t.copyBuffer(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
  }
	
  /**
   * Fills Lucene TermAttribute with the current token text, removing the two first 
   * characters.
   */
  public final void getBNodeText(CharTermAttribute t) {
    t.copyBuffer(zzBuffer, zzStartRead + 2, zzMarkedPos - zzStartRead - 2);
  }
	
  /**
   * Fills Lucene TermAttribute with the current token text, removing the first and 
   * last characters.
   */
  public final void getURIText(CharTermAttribute t) {
    t.copyBuffer(zzBuffer, zzStartRead + 1, zzMarkedPos - zzStartRead - 2);
  }
	
  /**
   * Fills Lucene TermAttribute with the current string buffer.
   */
  public final void getLiteralText(CharTermAttribute t) {
    char[] chars = new char[buffer.length()];
    buffer.getChars(0, buffer.length(), chars, 0);
    t.copyBuffer(chars, 0, chars.length);
  }

	/**
	 * Return the current string buffer.
	 */
	public final char[] getLiteralText() {
    char[] chars = new char[buffer.length()];
    buffer.getChars(0, buffer.length(), chars, 0);
    return chars;
	}
	
  /**
   * Return the language tag.
   * 
   * <p> Return empty char array if no language tag has been defined
   */
  public final char[] getLanguageTag() {
    char[] chars = new char[metadataBuffer.length()];
    metadataBuffer.getChars(0, metadataBuffer.length(), chars, 0);
    return chars;
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

// Blank Node
BNODE          = "_:" [A-Za-z][A-Za-z0-9]*

// URI Reference
URI            =  "<" [^>]+ ">"

// Language attribute
LANGUAGE			 =  [a-z]+('-'[a-z0-9]+)*

%state STRING

%%

<YYINITIAL> {

	{URI}				    { // Assign the xsd:anyURI
	                  metadataBuffer.setLength(0);
	                  metadataBuffer.append(XSD_ANY_URI);
	                  return URI; }
	
	{BNODE}         { return BNODE; }
	
	\"							{ buffer.setLength(0); 
	                  metadataBuffer.setLength(0); 
	                  yybegin(STRING); }
	
	{WHITESPACE}		{ /* ignore */ }
	
	\.							{ return DOT; }
	
}

// The class of plain literals without without a datatype or language tag can 
// be considered equivalent to the datatype class xsd:string.
// We assume that a literal with a language tag is considered equivalent to the 
// datatype class xsd:string.
// A literal cannot have both a datatype and a language tag.
// @link http://pedantic-web.org/fops.html

<STRING> {

  \"\^\^{URI}     { yybegin(YYINITIAL);
                    // remove the first four and the last characters.
                    metadataBuffer.append(zzBuffer, zzStartRead + 4, zzMarkedPos - zzStartRead - 5);
                    return LITERAL; }
                    
  \"@{LANGUAGE}   { yybegin(YYINITIAL);
                    // remove the first two characters.
                    metadataBuffer.append(zzBuffer, zzStartRead + 2, zzMarkedPos - zzStartRead - 2);
                    return LITERAL; }

	\" 							{ yybegin(YYINITIAL);	
	                  return LITERAL; }

  [^\"\\]+  			{ buffer.append(yytext()); }
  
  \\\"          	{ buffer.append(yytext()); }
  \\.            	{ buffer.append(yytext()); }
  
  \\u[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F] { buffer.append(Character.toChars(Integer.parseInt(new String(zzBufferL, zzStartRead+2, zzMarkedPos - zzStartRead - 2 ), 16))); }
}

// Ignore the rest
.	| {WHITESPACE}	{ /* ignore */ }
