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
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.sindice.siren.analysis.attributes.NodeAttribute;
import org.sindice.siren.analysis.attributes.PositionAttribute;

/**
 * Filter that encode the position relative to the node of each token into
 * the {@link PositionAttribute}.
 */
public class PositionAttributeFilter extends TokenFilter {

  private final NodeAttribute nodeAtt;
  private final PositionAttribute posAtt;
  private final PositionIncrementAttribute posIncrAtt;

  private long lastNodeHash = Long.MAX_VALUE;
  private int lastPosition = 0;

  public PositionAttributeFilter(final TokenStream input) {
    super(input);
    nodeAtt = this.addAttribute(NodeAttribute.class);
    posAtt = this.addAttribute(PositionAttribute.class);
    posIncrAtt = this.addAttribute(PositionIncrementAttribute.class);
  }

  @Override
  public void reset() throws IOException {
    super.reset();
    lastPosition = 0;
    lastNodeHash = Long.MAX_VALUE;

  }

  @Override
  public final boolean incrementToken() throws IOException {
    if (!input.incrementToken()) {
      return false;
    }

    final int nodeHash = nodeAtt.node().hashCode();
    if (lastNodeHash != nodeHash) { // new node
      lastPosition = 0;
      lastNodeHash = nodeHash;
    }

    lastPosition += posIncrAtt.getPositionIncrement();
    if (lastPosition > 0) {
      posAtt.setPosition(lastPosition - 1);
    }
    else {
      posAtt.setPosition(lastPosition);
    }
    return true;
  }

}
