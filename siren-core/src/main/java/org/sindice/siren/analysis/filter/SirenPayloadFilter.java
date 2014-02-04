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

package org.sindice.siren.analysis.filter;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.util.BytesRef;
import org.sindice.siren.analysis.attributes.NodeAttribute;
import org.sindice.siren.analysis.attributes.PositionAttribute;

/**
 * Filter that encodes the {@link NodeAttribute} and the
 * {@link PositionAttribute} into the {@link PayloadAttribute}.
 */
public class SirenPayloadFilter extends TokenFilter  {

  private final NodeAttribute nodeAtt;
  private final PositionAttribute posAtt;
  private final PayloadAttribute payloadAtt;

  VIntPayloadCodec codec = new VIntPayloadCodec();

  public SirenPayloadFilter(final TokenStream input) {
    super(input);
    payloadAtt = this.addAttribute(PayloadAttribute.class);
    nodeAtt = this.addAttribute(NodeAttribute.class);
    posAtt = this.addAttribute(PositionAttribute.class);
  }

  @Override
  public final boolean incrementToken() throws IOException {
    if (input.incrementToken()) {
      // encode node path
      final BytesRef bytes = codec.encode(nodeAtt.node(), posAtt.position());
      payloadAtt.setPayload(bytes);
      return true;
    }
    else {
      return false;
    }
  }

}
