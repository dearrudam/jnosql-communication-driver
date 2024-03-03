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
package org.eclipse.jnosql.databases.orientdb.communication;


import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseType;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import org.eclipse.jnosql.communication.semistructured.DatabaseManagerFactory;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

/**
 * The OrientDB implementation of {@link DatabaseManagerFactory}
 */
public class OrientDBDocumentManagerFactory implements DatabaseManagerFactory {

    private final String host;
    private final String user;
    private final String password;
    private final ODatabaseType storageType;
    private final OrientDB orient;

    OrientDBDocumentManagerFactory(String host, String user, String password, String storageType) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.storageType = ofNullable(storageType)
                .map(String::toUpperCase)
                .map(ODatabaseType::valueOf)
                .orElse(ODatabaseType.PLOCAL);

        String prefix = this.storageType == ODatabaseType.MEMORY ? "embedded:" : "remote:";
        this.orient = new OrientDB(prefix + host, user, password, OrientDBConfig.defaultConfig());

    }

    @Override
    public OrientDBDocumentManager apply(String database) {
        requireNonNull(database, "database is required");

        orient.createIfNotExists(database, storageType);
        ODatabasePool pool = new ODatabasePool(orient, database, user, password);
        return new DefaultOrientDBDocumentManager(pool, database);

    }

    @Override
    public void close() {
        orient.close();
    }
}
