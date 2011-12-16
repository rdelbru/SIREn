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
 * @project siren-qparser
 * @author Campinas Stephane [ 5 Nov 2010 ]
 * @link stephane.campinas@deri.org
 */
package org.sindice.siren.qparser.tuple;

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
