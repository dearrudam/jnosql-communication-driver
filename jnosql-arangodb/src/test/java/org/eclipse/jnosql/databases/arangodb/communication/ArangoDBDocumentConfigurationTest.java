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

package org.eclipse.jnosql.databases.arangodb.communication;

import org.eclipse.jnosql.communication.Settings;
import org.eclipse.jnosql.communication.document.DocumentConfiguration;
import org.eclipse.jnosql.communication.document.DocumentManagerFactory;
import org.eclipse.jnosql.databases.arangodb.communication.ArangoDBDocumentConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArangoDBDocumentConfigurationTest {


    @Test
    public void shouldCreateDocumentManagerFactory() {
        ArangoDBDocumentConfiguration configuration = new ArangoDBDocumentConfiguration();
        configuration.addHost("localhost", 8529);
        DocumentManagerFactory managerFactory = configuration.apply(Settings.builder().build());
        assertNotNull(managerFactory);
    }

    @Test
    public void shouldReturnFromConfiguration() {
        ArangoDBDocumentConfiguration configuration = DocumentConfiguration.getConfiguration();
        Assertions.assertNotNull(configuration);
        Assertions.assertTrue(configuration instanceof ArangoDBDocumentConfiguration);
    }

    @Test
    public void shouldReturnFromConfigurationQuery() {
        ArangoDBDocumentConfiguration configuration = DocumentConfiguration
                .getConfiguration(ArangoDBDocumentConfiguration.class);
        Assertions.assertNotNull(configuration);
        Assertions.assertTrue(configuration instanceof ArangoDBDocumentConfiguration);
    }

}