/**
 * Copyright (c) 2009-2011 Sindice Limited. All Rights Reserved.
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
 * @author Renaud Delbru [ 16 Feb 2011 ]
 * @link http://renaud.delbru.fr/
 * @copyright Copyright (C) 2010, 2011, by Renaud Delbru, All rights reserved.
 */
package org.apache.solr.core;

import java.io.IOException;

import org.apache.solr.common.params.EventParams;
import org.apache.solr.common.params.UpdateParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.search.SolrIndexSearcher;
import org.apache.solr.update.CommitUpdateCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scheduler for optimize operations. After each commit, it checks if the time
 * since the last performed optimize operation is greater than the given
 * interval time. If this is the case, it will trigger an optimize operation.
 */
public class OptimizeSchedulerListener implements SolrEventListener {

  protected final SolrCore core;
  protected NamedList args;

  private int maxOptimizeSegments = 1;
  private int optimizeInterval = 1;

  private static long lastOptimizeTime = System.currentTimeMillis();

  /**
   * The optimize interval in hour
   */
  public static final String OPTIMIZE_INTERVAL = "optimizeInterval";

  private static final
  Logger logger = LoggerFactory.getLogger(OptimizeSchedulerListener.class);

  public OptimizeSchedulerListener(final SolrCore core) {
    this.core = core;
  }

  public void init(final NamedList args) {
    this.args = args;
    if (args.get(UpdateParams.MAX_OPTIMIZE_SEGMENTS) != null) {
      maxOptimizeSegments = (Integer) args.get(UpdateParams.MAX_OPTIMIZE_SEGMENTS);
    }
    if (args.get(OPTIMIZE_INTERVAL) != null) {
      optimizeInterval = (Integer) args.get(OPTIMIZE_INTERVAL);
    }
    logger.info("{maxOptimizeSegments={},optimizeInterval={}}", maxOptimizeSegments, optimizeInterval);
  }

  public void postCommit() {
    if (optimizeInterval <= this.getIntervalInHour()) {
      lastOptimizeTime = System.currentTimeMillis();
      this.doOptimize();
    }
  }

  public void newSearcher(final SolrIndexSearcher newSearcher, final SolrIndexSearcher currentSearcher) {
    throw new UnsupportedOperationException();
  }

  /**
   * Get the time interval in hour since the last optimized operation. Time is
   * rounded down.
   */
  private long getIntervalInHour() {
    final long timeInterval = System.currentTimeMillis() - lastOptimizeTime;
    final long timeIntervalInHour = timeInterval / (1000 * 3600);
    return timeIntervalInHour;
  }

  private void doOptimize() {
    final CommitUpdateCommand cmd = new CommitUpdateCommand(true);
    cmd.waitFlush = true;
    cmd.waitSearcher = true;
    cmd.maxOptimizeSegments = maxOptimizeSegments;
    try {
      core.getUpdateHandler().commit(cmd);
    } catch (final IOException e) {
      // don't throw exception, just log it...
      logger.error("Scheduled Optimize Failed", e);
    }
  }

  @Override
  public String toString() {
    return this.getClass().getName() + args;
  }

  /**
   * Add the {@link org.apache.solr.common.params.EventParams#EVENT} with either the {@link org.apache.solr.common.params.EventParams#NEW_SEARCHER}
   * or {@link org.apache.solr.common.params.EventParams#FIRST_SEARCHER} values depending on the value of currentSearcher.
   * <p/>
   * Makes a copy of NamedList and then adds the parameters.
   *
   *
   * @param currentSearcher If null, add FIRST_SEARCHER, otherwise NEW_SEARCHER
   * @param nlst The named list to add the EVENT value to
   */
  protected NamedList addEventParms(final SolrIndexSearcher currentSearcher, final NamedList nlst) {
    final NamedList result = new NamedList();
    result.addAll(nlst);
    if (currentSearcher != null) {
      result.add(EventParams.EVENT, EventParams.NEW_SEARCHER);
    } else {
      result.add(EventParams.EVENT, EventParams.FIRST_SEARCHER);
    }
    return result;
  }

}
