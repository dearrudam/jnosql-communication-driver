/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 * You may elect to redistribute this code under either of these licenses.
 *
 * Contributors:
 *
 * Maximillian Arruda
 */

package org.eclipse.jnosql.databases.dynamodb.mapping;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterBeanDiscovery;
import jakarta.enterprise.inject.spi.Extension;
import org.eclipse.jnosql.mapping.metadata.ClassScanner;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamoDBExtension implements Extension {

    private static final Logger LOGGER = Logger.getLogger(DynamoDBExtension.class.getName());

    void onAfterBeanDiscovery(@Observes final AfterBeanDiscovery afterBeanDiscovery) {

        ClassScanner scanner = ClassScanner.load();
        Set<Class<?>> crudTypes = scanner.repositories(DynamoDBRepository.class);

        if (LOGGER.isLoggable(Level.INFO))
            LOGGER.info("Starting the onAfterBeanDiscovery with elements number: %s".formatted(crudTypes.size()));

        crudTypes.forEach(type -> afterBeanDiscovery.addBean(new DynamoDBRepositoryBean<>(type)));

        if (LOGGER.isLoggable(Level.INFO))
            LOGGER.info("Finished the onAfterBeanDiscovery");
    }
}
