/*
 *  Copyright (c) 2022 Contributors to the Eclipse Foundation
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
package org.eclipse.jnosql.communication.dynamodb.keyvalue;

import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;
import org.eclipse.jnosql.communication.dynamodb.DynamoTableUtils;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class DynamoDBBucketManagerFactory implements BucketManagerFactory {

    private DynamoDbClient client;

    DynamoDBBucketManagerFactory(DynamoDbClient client) {
        this.client = client;
    }

    @Override
    public DynamoDBBucketManager apply(String bucketName) {

        return getBucketManager(bucketName, null, null);
    }

    public DynamoDBBucketManager getBucketManager(String bucketName, Long readCapacityUnits, Long writeCapacityUnit) {

        DynamoTableUtils.manageTables(bucketName, client, readCapacityUnits, writeCapacityUnit);
        return new DynamoDBBucketManager(client, bucketName);
    }

    @Override
    public <T> List<T> getList(String bucketName, Class<T> clazz) {
        throw new UnsupportedOperationException("The DynamoDB does not support getMap method");
    }

    @Override
    public <T> Set<T> getSet(String bucketName, Class<T> clazz) {
        throw new UnsupportedOperationException("The DynamoDB does not support getMap method");
    }

    @Override
    public <T> Queue<T> getQueue(String bucketName, Class<T> clazz) {
        throw new UnsupportedOperationException("The DynamoDB does not support getMap method");
    }

    @Override
    public <K, V> Map<K, V> getMap(String bucketName, Class<K> keyValue, Class<V> valueValue) {
        throw new UnsupportedOperationException("The DynamoDB does not support getMap method");
    }

    @Override
    public void close() {
        client.close();
    }
}
