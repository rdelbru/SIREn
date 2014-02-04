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
package org.sindice.siren.qparser.json;

import org.apache.lucene.queryparser.flexible.core.QueryNodeParseException;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;

/**
 * Exception thrown by the {@link JsonQueryParser} if an error occurs during
 * parsing.
 */
public class ParseException extends QueryNodeParseException {

  private static final long serialVersionUID = 1L;

  public ParseException(final String message, final Throwable throwable) {
    super(new MessageImpl(QueryParserMessages.INVALID_SYNTAX, message), throwable);
  }

  public ParseException(final String message) {
    super(new MessageImpl(QueryParserMessages.INVALID_SYNTAX, message));
  }

}
