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
 * @project solr-plugins
 * @author Renaud Delbru [ 7 Sep 2010 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.qparser.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

%%

/** 
 *  The name of the lexer class JFlex will create.
 */
%class EntityQueryTokenizerImpl

/**
 * The current line number can be accessed with the variable yyline,
 * the current column number with the variable yycolumn
 */
%line
%column

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

  public static final int EOF 	   = EntityQueryTokenizer.EOF;
  public static final int ERROR    = EntityQueryTokenizer.ERROR;
  public static final int PLUS 	   = EntityQueryTokenizer.PLUS;
  public static final int MINUS    = EntityQueryTokenizer.MINUS;
  public static final int LPAR     = EntityQueryTokenizer.LPAR;
  public static final int RPAR     = EntityQueryTokenizer.RPAR;
  public static final int EQUAL    = EntityQueryTokenizer.EQUAL;
  public static final int QUOTE    = EntityQueryTokenizer.QUOTE;
  public static final int WILDCARD = EntityQueryTokenizer.WILDCARD;
  public static final int TERM     = EntityQueryTokenizer.TERM;
  
  public static final String[] TOKEN_TYPES = EntityQueryTokenizer.TOKEN_TYPES;

	private static final
	Logger logger = LoggerFactory.getLogger(EntityQueryTokenizerImpl.class);
	
	public final int yychar()	{
		return yychar;
	}
	
  /**
   * Fills CharTermAttribute with the current token text.
   */
  public final void getText(CharTermAttribute t) {
    t.copyBuffer(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }
	
%}

META_CHAR      = "+" | "-" | "(" | ")" | "=" | "*" | "\"" | "\\"

ESCAPED        = "\\" .

// From the JFlex manual: "the expression that matches everything of <a> not 
// matched by <b> is !(!<a>|<b>)"
TERM_START     = !(!.|({META_CHAR}|{WHITESPACE})) | {ESCAPED}

// "(" and ")" not allowed in term: need escaping
// "=", "*", "+" and "-" allowed. With a space before, they are considered as meta-char
_TERM          = {TERM_START} ( {ESCAPED} | {TERM_START} | "-" | "+" | "=" | "*" )*

ENDOFLINE      =  \r|\n|\r\n

WHITESPACE     =  {ENDOFLINE} | [ \t\f] | \u3000

%%

<YYINITIAL> {

  <<EOF>>         { return EOF; }
	
  {WHITESPACE}    { /* ignore */ }
	
	"+"             { return PLUS; }
	
	"-"    					{ return MINUS; }
	
	"("    					{ return LPAR; }
	
	")"             { return RPAR; }
	
	"="             { return EQUAL; }
	
	"\""            { return QUOTE; }
	
  "*"             { return WILDCARD; }
	
	{_TERM}         { return TERM; }
  
  /**
   * No token was found for the input, throw an error.
   */
  .               { return ERROR; }
 
}


