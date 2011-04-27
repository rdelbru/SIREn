/**
 * Copyright 2010, Campinas Stephane
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
 * @project siren-qparser
 * @author Campinas Stephane [ 5 Nov 2010 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.qparser.ntriple.query;

/**
 * Exception handling with the query builder
 * @version $Id: QueryBuilderException 5 Nov 2010 Campinas Stephane $
 */
public class QueryBuilderException extends RuntimeException {

  public enum Error {
    NO_ERROR,
    PARSE_ERROR
  };
  
  private final Error                   error;
  private final String                  desc;
  private final StackTraceElement[]     stack;
  
  public QueryBuilderException(Error error, String desc, StackTraceElement[] stack) {
    this.error = error;
    this.desc = desc;
    this.stack = stack;
  }

  public QueryBuilderException(Error error, String desc) {
    this(error, desc, null);
  }

  public interface Exception {
    public boolean hasError();    
    public String getErrorDescription();
  }
  
  public Error getError() {
    return error;
  }
  
  @Override
  public String getMessage() {
    return desc == null ? super.getMessage() : error.toString() + ": " + desc;
  }

  @Override
  public StackTraceElement[] getStackTrace() {
    return stack == null ? super.getStackTrace() : stack;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    
    builder.append(this.getMessage() + "\n");
    for (StackTraceElement trace : this.getStackTrace()) {
      builder.append(trace.toString() + "\n");
    }
    return builder.toString();
  }

}
