/*
 *  Copyright (c) 2022 Eclipse Contribuitor
 * All rights reserved. This program and the accompanying materials
 *  and Apache License v2.0 which accompanies this distribution.
 *  The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *  and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *    You may elect to redistribute this code under either of these licenses.
 */

package org.eclipse.jnosql.databases.couchbase.mapping;

import jakarta.data.exceptions.MappingException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.Typed;
import org.eclipse.jnosql.communication.Settings;
import org.eclipse.jnosql.databases.couchbase.communication.CouchbaseDocumentConfiguration;
import org.eclipse.jnosql.databases.couchbase.communication.CouchbaseDocumentManager;
import org.eclipse.jnosql.databases.couchbase.communication.CouchbaseDocumentManagerFactory;
import org.eclipse.jnosql.mapping.config.MicroProfileSettings;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.eclipse.jnosql.mapping.config.MappingConfigurations.DOCUMENT_DATABASE;

@ApplicationScoped
class DocumentManagerSupplier implements Supplier<CouchbaseDocumentManager> {

    private static final Logger LOGGER = Logger.getLogger(DocumentManagerSupplier.class.getName());


    @Override
    @Produces
    @Typed(CouchbaseDocumentManager.class)
    public CouchbaseDocumentManager get() {
        Settings settings = MicroProfileSettings.INSTANCE;
        CouchbaseDocumentConfiguration configuration = new CouchbaseDocumentConfiguration();
        CouchbaseDocumentManagerFactory factory = configuration.apply(settings);
        Optional<String> database = settings.get(DOCUMENT_DATABASE, String.class);
        String db = database.orElseThrow(() -> new MappingException("Please, inform the database filling up the property "
                + DOCUMENT_DATABASE));
        CouchbaseDocumentManager manager = factory.apply(db);
        LOGGER.log(Level.FINEST, "Starting  a CouchbaseDocumentManager instance using Eclipse MicroProfile Config," +
                " database name: " + db);
        return manager;
    }

    public void close(@Disposes CouchbaseDocumentManager manager) {
        LOGGER.log(Level.FINEST, "Closing CouchbaseDocumentManager resource, database name: " + manager.name());
        manager.close();
    }

}
