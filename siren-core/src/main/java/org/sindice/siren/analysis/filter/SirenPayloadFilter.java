/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
 *
 * Project and contact information: http://www.siren.sindice.com/
 *
 * This file is part of the SIREn project.
 *
 * SIREn is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SIREn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SIREn. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @project siren
 * @author Renaud Delbru [ 8 Dec 2009 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2009 by Renaud Delbru, All rights reserved.
 */
package org.sindice.siren.analysis.filter;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.sindice.siren.analysis.attributes.CellAttribute;
import org.sindice.siren.analysis.attributes.TupleAttribute;
import org.sindice.siren.index.PackedIntSirenPayload;

/**
 * Filter that encode the structural information of each token into its payload.
 */
public class SirenPayloadFilter
extends TokenFilter {

  private final TupleAttribute tupleAtt;
  private final CellAttribute cellAtt;
  private final PayloadAttribute payloadAtt;

  public SirenPayloadFilter(final TokenStream input) {
    super(input);
    payloadAtt = this.addAttribute(PayloadAttribute.class);
    tupleAtt = this.addAttribute(TupleAttribute.class);
    cellAtt = this.addAttribute(CellAttribute.class);
  }

  @Override
  public final boolean incrementToken() throws IOException {
    if (input.incrementToken()) {
      payloadAtt.setPayload(new PackedIntSirenPayload(tupleAtt.tuple(), cellAtt.cell()));
      return true;
    }
    else {
      return false;
    }
  }

}
