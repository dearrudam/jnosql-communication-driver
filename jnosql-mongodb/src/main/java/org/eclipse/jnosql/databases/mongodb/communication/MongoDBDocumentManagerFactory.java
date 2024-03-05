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

package org.eclipse.jnosql.databases.mongodb.communication;

import com.mongodb.client.MongoClient;
import org.eclipse.jnosql.communication.semistructured.DatabaseManagerFactory;

import java.util.Objects;

/**
 * The mongodb implementation to {@link DatabaseManagerFactory}
 */
public class MongoDBDocumentManagerFactory implements DatabaseManagerFactory {

    private final MongoClient mongoClient;

    MongoDBDocumentManagerFactory(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public MongoDBDocumentManager apply(String database) {
        Objects.requireNonNull(database, "database is required");
        return new MongoDBDocumentManager(mongoClient.getDatabase(database), database);
    }


    @Override
    public void close() {
        mongoClient.close();
    }

    @Override
    public String toString() {
       return "MongoDBDocumentManagerFactory{" + "mongoClient=" + mongoClient +
                '}';
    }
}
