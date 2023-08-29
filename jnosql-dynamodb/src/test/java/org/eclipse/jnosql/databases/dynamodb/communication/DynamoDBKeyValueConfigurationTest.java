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
package org.eclipse.jnosql.databases.dynamodb.communication;

import org.eclipse.jnosql.communication.keyvalue.KeyValueConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.eclipse.jnosql.communication.driver.IntegrationTest.MATCHES;
import static org.eclipse.jnosql.communication.driver.IntegrationTest.NAMED;

@EnabledIfSystemProperty(named = NAMED, matches = MATCHES)
public class DynamoDBKeyValueConfigurationTest {
	
	private DynamoDBKeyValueConfiguration configuration;
	
	@BeforeEach
	public void setUp(){
		configuration = new DynamoDBKeyValueConfiguration();
	}


	@Test
	public void shouldReturnFromConfiguration() {
		DynamoDBKeyValueConfiguration configuration = KeyValueConfiguration.getConfiguration();
		Assertions.assertNotNull(configuration);
		Assertions.assertTrue(configuration instanceof DynamoDBKeyValueConfiguration);
	}

	@Test
	public void shouldReturnFromConfigurationQuery() {
		DynamoDBKeyValueConfiguration configuration = KeyValueConfiguration
				.getConfiguration(DynamoDBKeyValueConfiguration.class);
		Assertions.assertNotNull(configuration);
		Assertions.assertTrue(configuration instanceof DynamoDBKeyValueConfiguration);
	}
}
