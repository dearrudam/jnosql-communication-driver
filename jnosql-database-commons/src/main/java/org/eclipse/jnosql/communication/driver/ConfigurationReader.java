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
package org.eclipse.jnosql.communication.driver;


import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class ConfigurationReader {

    private static final Logger LOGGER = Logger.getLogger(ConfigurationReader.class.getName());

    private ConfigurationReader() {
    }

    public static Map<String, String> from(String resource) throws NullPointerException {
        Objects.requireNonNull(resource, "Resource is required");

        try {
            Properties properties = new Properties();
            InputStream stream = ConfigurationReader.class.getClassLoader()
                    .getResourceAsStream(resource);
            if (Objects.nonNull(stream)) {
                properties.load(stream);
                return properties.keySet().stream().collect(Collectors
                        .toMap(Object::toString, s -> properties.get(s).toString()));
            } else {
                LOGGER.info("The file " + resource + " as resource, returning an empty configuration");
                return Collections.emptyMap();
            }

        } catch (IOException e) {
            LOGGER.fine("The file was not found: " + resource);
            return Collections.emptyMap();
        }
    }
}
