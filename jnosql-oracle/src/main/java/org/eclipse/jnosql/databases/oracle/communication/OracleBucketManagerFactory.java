/*
 *  Copyright (c) 2023 Contributors to the Eclipse Foundation
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
package org.eclipse.jnosql.databases.oracle.communication;

import jakarta.json.bind.Jsonb;
import oracle.nosql.driver.ops.TableLimits;
import oracle.nosql.driver.ops.TableRequest;
import oracle.nosql.driver.ops.TableResult;
import org.eclipse.jnosql.communication.CommunicationException;
import org.eclipse.jnosql.communication.driver.JsonbSupplier;
import org.eclipse.jnosql.communication.keyvalue.BucketManager;
import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Logger;

final class OracleBucketManagerFactory implements BucketManagerFactory {
    private static final Jsonb JSON = JsonbSupplier.getInstance().get();
    private final NoSQLHandleConfiguration configuration;

    OracleBucketManagerFactory(NoSQLHandleConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> List<T> getList(String bucketName, Class<T> type) {
        throw new UnsupportedOperationException("Oracle NoSQL does not support list");
    }

    @Override
    public <T> Set<T> getSet(String bucketName, Class<T> type) {
        throw new UnsupportedOperationException("Oracle NoSQL does not support set");
    }

    @Override
    public <T> Queue<T> getQueue(String bucketName, Class<T> type) {
        throw new UnsupportedOperationException("Oracle NoSQL does not support queue");
    }

    @Override
    public <K, V> Map<K, V> getMap(String bucketName, Class<K> keyType, Class<V> valueType) {
        throw new UnsupportedOperationException("Oracle NoSQL does not support map");
    }

    @Override
    public BucketManager apply(String bucketName) {
        Objects.requireNonNull(bucketName, "bucketName is required");
        createTable(bucketName);
        return new OracleBucketManager(bucketName, configuration.serviceHandle(), JSON);
    }

    private void createTable(String bucketName) {
        TableCreationConfiguration creationConfiguration = configuration.tableCreationConfiguration();
        creationConfiguration.createTable(bucketName, configuration.serviceHandle());
    }

    @Override
    public void close() {
        configuration.serviceHandle().close();
    }


}
