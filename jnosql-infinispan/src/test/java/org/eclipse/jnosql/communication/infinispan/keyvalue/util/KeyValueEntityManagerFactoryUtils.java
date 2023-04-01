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
 *   The Infinispan Team
 */
package org.eclipse.jnosql.communication.infinispan.keyvalue.util;

import org.eclipse.jnosql.communication.Settings;
import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;
import org.eclipse.jnosql.communication.keyvalue.KeyValueConfiguration;
import org.eclipse.jnosql.communication.infinispan.keyvalue.InfinispanConfigurations;
import org.eclipse.jnosql.communication.infinispan.keyvalue.InfinispanKeyValueConfiguration;


public class KeyValueEntityManagerFactoryUtils {

    public static BucketManagerFactory get() {
        KeyValueConfiguration configuration = new InfinispanKeyValueConfiguration();
        Settings settings = Settings.builder()
                .put(InfinispanConfigurations.CONFIG, "infinispan.xml")
                .build();
        BucketManagerFactory managerFactory = configuration.apply(settings);
        return managerFactory;
    }
}
