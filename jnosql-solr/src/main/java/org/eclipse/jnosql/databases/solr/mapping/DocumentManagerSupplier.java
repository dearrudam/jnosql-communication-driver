/*
 *  Copyright (c) 2022 Eclipse Contribuitor
 * All rights reserved. This program and the accompanying materials
 *  and Apache License v2.0 which accompanies this distribution.
 *  The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *  and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *    You may elect to redistribute this code under either of these licenses.
 */

package org.eclipse.jnosql.databases.solr.mapping;

import jakarta.data.exceptions.MappingException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.Typed;
import org.eclipse.jnosql.communication.Settings;
import org.eclipse.jnosql.databases.solr.communication.SolrDocumentConfiguration;
import org.eclipse.jnosql.databases.solr.communication.SolrDocumentManager;
import org.eclipse.jnosql.databases.solr.communication.SolrDocumentManagerFactory;
import org.eclipse.jnosql.mapping.config.MicroProfileSettings;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.eclipse.jnosql.mapping.config.MappingConfigurations.DOCUMENT_DATABASE;

@ApplicationScoped
class DocumentManagerSupplier implements Supplier<SolrDocumentManager> {

    private static final Logger LOGGER = Logger.getLogger(DocumentManagerSupplier.class.getName());


    @Override
    @Produces
    @Typed(SolrDocumentManager.class)
    public SolrDocumentManager get() {
        Settings settings = MicroProfileSettings.INSTANCE;
        SolrDocumentConfiguration configuration = new SolrDocumentConfiguration();
        SolrDocumentManagerFactory factory = configuration.apply(settings);
        Optional<String> database = settings.get(DOCUMENT_DATABASE, String.class);
        String db = database.orElseThrow(() -> new MappingException("Please, inform the database filling up the property "
                + DOCUMENT_DATABASE));
        SolrDocumentManager manager = factory.apply(db);
        LOGGER.log(Level.FINEST, "Starting  a SolrDocumentManager instance using Eclipse MicroProfile Config," +
                " database name: " + db);
        return manager;
    }

    public void close(@Disposes SolrDocumentManager manager) {
        LOGGER.log(Level.FINEST, "Closing SolrDocumentManager resource, database name: " + manager.getName());
        manager.close();
    }

}
