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
