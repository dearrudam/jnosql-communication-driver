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
package org.eclipse.jnosql.communication.elasticsearch.document;

import jakarta.nosql.document.DocumentConfiguration;
import jakarta.nosql.document.DocumentManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ElasticsearchDocumentConfigurationTest {

    @Test
    public void shouldCreateDocumentManagerFactoryByMap() {
        DocumentManagerFactory managerFactory = ElasticsearchDocumentManagerFactorySupplier.INSTANCE.get();
        assertNotNull(managerFactory);
    }

    @Test
    public void shouldCreateDocumentManagerFactoryByFile() {
        DocumentManagerFactory managerFactory = ElasticsearchDocumentManagerFactorySupplier.INSTANCE.get();
        assertNotNull(managerFactory);
    }

    @Test
    public void shouldReturnFromConfiguration() {
        ElasticsearchDocumentConfiguration configuration = DocumentConfiguration.getConfiguration();
        Assertions.assertNotNull(configuration);
        Assertions.assertTrue(configuration instanceof ElasticsearchDocumentConfiguration);
    }

    @Test
    public void shouldReturnFromConfigurationQuery() {
        ElasticsearchDocumentConfiguration configuration = DocumentConfiguration
                .getConfiguration(ElasticsearchDocumentConfiguration.class);
        Assertions.assertNotNull(configuration);
        Assertions.assertTrue(configuration instanceof ElasticsearchDocumentConfiguration);
    }

}