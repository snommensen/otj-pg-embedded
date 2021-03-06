/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.opentable.db.postgres.junit;

import javax.sql.DataSource;

import com.google.common.base.Preconditions;

import org.junit.rules.ExternalResource;

import com.opentable.db.postgres.embedded.DatabasePreparer;
import com.opentable.db.postgres.embedded.PreparedDbProvider;

public class PreparedDbRule extends ExternalResource {

    private final DatabasePreparer preparer;
    private volatile DataSource dataSource;
    private volatile PreparedDbProvider provider;

    PreparedDbRule(DatabasePreparer preparer) {
        Preconditions.checkArgument(preparer != null, "null preparer");
        this.preparer = preparer;
    }

    @Override
    protected void before() throws Throwable {
        provider = PreparedDbProvider.forPreparer(preparer);
        dataSource = provider.createDataSource();
    }

    @Override
    protected void after() {
        dataSource = null;
        provider = null;
    }

    public DataSource getTestDatabase() {
        Preconditions.checkState(dataSource != null, "not initialized");
        return dataSource;
    }

    public PreparedDbProvider getDbProvider() {
        Preconditions.checkState(provider != null, "not initialized");
        return provider;
    }
}
