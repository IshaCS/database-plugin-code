/*
 * Copyright © 2022 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.plugin.postgres;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit tests for {@link PostgresConnector}
 */
public class PostgresConnectorUnitTest {
  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  private static final PostgresConnector CONNECTOR = new PostgresConnector(null);

  /**
   * Unit test for getTableName()
   */
  @Test
  public void getTableNameTest() {
    Assert.assertEquals("\"schema\".\"table\"",
                        CONNECTOR.getTableName("db", "schema", "table"));
  }

  /**
   * Unit tests for getTableQuery()
   */
  @Test
  public void getTableQueryTest() {
    String tableName = CONNECTOR.getTableName("db", "schema", "table");

    // random query
    Assert.assertEquals(String.format("SELECT * FROM %s\n" +
                                        "TABLESAMPLE BERNOULLI (100.0 * %d / (SELECT COUNT(*) FROM %s))",
                                      tableName, 100, tableName),
                        CONNECTOR.getRandomQuery(tableName, 100));
  }
}
