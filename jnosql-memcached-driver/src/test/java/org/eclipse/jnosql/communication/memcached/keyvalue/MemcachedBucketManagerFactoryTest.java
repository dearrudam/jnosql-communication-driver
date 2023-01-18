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
package org.eclipse.jnosql.communication.memcached.keyvalue;

import org.eclipse.jnosql.communication.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MemcachedBucketManagerFactoryTest {

    private MemcachedBucketManagerFactory managerFactory;

    @BeforeEach
    public void setUp() {
        MemcachedKeyValueConfiguration configuration = new MemcachedKeyValueConfiguration();
        Settings settings = Settings.builder()
                .put(MemcachedConfigurations.HOST.get()+".1", "localhost:11211")
                .build();
        managerFactory = configuration.apply(settings);
    }

    @Test
    public void shouldReturnErrorList() {
        assertThrows(UnsupportedOperationException.class, () -> managerFactory.getList(null, String.class));
    }

    @Test
    public void shouldReturnErrorSet() {
        assertThrows(UnsupportedOperationException.class, () -> managerFactory.getSet(null, String.class));
    }

    @Test
    public void shouldReturnErrorQueue() {
        assertThrows(UnsupportedOperationException.class, () -> managerFactory.getQueue(null, String.class));
    }

    @Test
    public void shouldReturnErrorMap() {
        assertThrows(UnsupportedOperationException.class, () -> managerFactory.getMap(null, String.class, String.class));
    }

}