/**
 * Copyright (C) 2010-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.flyway.core.resolver.java;

import com.googlecode.flyway.core.api.FlywayException;
import com.googlecode.flyway.core.dbsupport.DbSupport;
import com.googlecode.flyway.core.migration.java.JavaMigration;
import com.googlecode.flyway.core.resolver.MigrationExecutor;
import com.googlecode.flyway.core.dbsupport.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 * Adapter for executing migrations implementing JavaMigration.
 */
public class JavaMigrationExecutor implements MigrationExecutor {
    /**
     * The JavaMigration to execute.
     */
    private final JavaMigration javaMigration;

    /**
     * Creates a new JavaMigrationExecutor.
     *
     * @param javaMigration The JavaMigration to execute.
     */
    public JavaMigrationExecutor(JavaMigration javaMigration) {
        this.javaMigration = javaMigration;
    }

    public void execute(JdbcTemplate jdbcTemplate, DbSupport dbSupport) {
        try {
            javaMigration.migrate(new org.springframework.jdbc.core.JdbcTemplate(
                    new SingleConnectionDataSource(jdbcTemplate.getConnection(), true)));
        } catch (Exception e) {
            throw new FlywayException("Migration failed !", e);
        }
    }
}
