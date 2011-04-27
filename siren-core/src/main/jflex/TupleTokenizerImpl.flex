/**
 * Copyright 2009, Renaud Delbru
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
 * @author Renaud Delbru [ 16 Mar 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
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
   * Fills Lucene TermAttribute with the current token text, removing the first character.
   */
  public final void getLanguageText(CharTermAttribute t) {
    t.copyBuffer(zzBuffer, zzStartRead + 1, zzMarkedPos - zzStartRead - 1);
  }
	
	/**
	 * Fills Lucene TermAttribute with the current token text, removing the three first and 
	 * the last characters.
	 */
	public final void getDatatypeText(CharTermAttribute t) {
	  t.copyBuffer(zzBuffer, zzStartRead + 3, zzMarkedPos - zzStartRead - 4);
	}
	
  /**
   * Fills Lucene TermAttribute with the current token text, removing the two first 
   * characters.
   */
  public final void getBNodeText(CharTermAttribute t) {
    t.copyBuffer(zzBuffer, zzStartRead + 2, zzMarkedPos - zzStartRead - 2);
  }
  
   /**
   * Fills Lucene TermAttribute with the current token text, removing the two first 
   * characters.
   */
  public final void getFlagText(CharTermAttribute t) {
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

	{URI}				    { return URI; }
	
	{BNODE}         { return BNODE; }
	
	\"							{ buffer.setLength(0); yybegin(STRING); }
									
	@{LANGUAGE}			{ return LANGUAGE; }
	
	\^\^{URI}		    { return DATATYPE; }
	
	{WHITESPACE}		{ /* ignore */ }
	
	\.							{ return DOT; }
	
}

<STRING> {

	\" 							{ yybegin(YYINITIAL);	return LITERAL; }

  [^\"\\]+  			{ buffer.append(yytext()); }
  
  \\\"          	{ buffer.append(yytext()); }
  \\.            	{ buffer.append(yytext()); }
  
  \\u[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F] { buffer.append(Character.toChars(Integer.parseInt(new String(zzBufferL, zzStartRead+2, zzMarkedPos - zzStartRead - 2 ), 16))); }
}

// Ignore the rest
.	| {WHITESPACE}	{ /* ignore */ }
