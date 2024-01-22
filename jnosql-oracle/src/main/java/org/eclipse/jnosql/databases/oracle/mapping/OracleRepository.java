/*
 *  Copyright (c) 2024 Contributors to the Eclipse Foundation
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   and Apache License v2.0 which accompanies this distribution.
 *   The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *   and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 *   You may elect to redistribute this code under either of these licenses.
 *
 *   Contributors:
 *
 *   Otavio Santana
 */
package org.eclipse.jnosql.databases.oracle.mapping;


import jakarta.data.repository.PageableRepository;

/**
 * The {@code OracleRepository} is an extension of {@link PageableRepository} specific to Oracle NoSQL database.
 * It provides CRUD (Create, Read, Update, Delete) operations for entities in the Oracle NoSQL database
 * and supports pagination and sorting through its parent interface.
 * <p>
 * In addition to basic CRUD operations and pagination, you can also define custom SQL queries using the
 * {@link SQL} annotation within repository methods. Here's an example of using the {@link SQL} annotation
 * with a parameterized SQL query:
 * </p>
 * <pre>
 * {@code
 * public interface UserRepository extends OracleRepository<User, Long> {
 *     // Find all active users with a custom SQL query and a parameter
 *     @SQL("SELECT * FROM users WHERE status = ?")
 *     List<User> findActiveUsersWithStatus(String status);
 * }
 * }
 * </pre>
 * <p>
 * In this example, the {@code UserRepository} interface extends {@code OracleRepository} and defines
 * a custom SQL query for the {@code findActiveUsersWithStatus} method. The {@link SQL} annotation allows
 * you to specify a parameterized SQL query and pass parameters to the query method.
 * </p>
 *
 * @param <T> the entity type managed by this repository
 * @param <K> the type of the entity's primary key
 *
 * @see PageableRepository
 * @see SQL
 */
public interface OracleRepository<T, K> extends PageableRepository<T, K> {
}
