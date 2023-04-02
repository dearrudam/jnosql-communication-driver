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
package org.eclipse.jnosql.mapping.solr.document;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterBeanDiscovery;
import jakarta.enterprise.inject.spi.Extension;
import org.eclipse.jnosql.mapping.reflection.ClassScanner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class SolrExtension implements Extension {

    private static final Logger LOGGER = Logger.getLogger(SolrExtension.class.getName());

    private final Collection<Class<?>> crudTypes = new HashSet<>();


    void onAfterBeanDiscovery(@Observes final AfterBeanDiscovery afterBeanDiscovery) {
        ClassScanner scanner = ClassScanner.INSTANCE;
        Set<Class<?>> crudTypes = scanner.repositories(SolrRepository.class);
        LOGGER.info("Starting the onAfterBeanDiscovery with elements number: " + crudTypes.size());

        crudTypes.forEach(type -> afterBeanDiscovery.addBean(new SolrRepositoryBean(type)));

        LOGGER.info("Finished the onAfterBeanDiscovery");
    }
}
