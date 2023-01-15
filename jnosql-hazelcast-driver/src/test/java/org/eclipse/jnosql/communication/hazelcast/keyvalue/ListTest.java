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

package org.eclipse.jnosql.communication.hazelcast.keyvalue;

import org.eclipse.jnosql.communication.keyvalue.BucketManagerFactory;
import org.eclipse.jnosql.communication.hazelcast.keyvalue.util.KeyValueEntityManagerFactoryUtils;
import org.eclipse.jnosql.communication.hazelcast.keyvalue.model.ProductCart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListTest {


    private static final String FRUITS = "fruits";
    private ProductCart banana = new ProductCart("banana", BigDecimal.ONE);
    private ProductCart orange = new ProductCart("orange", BigDecimal.ONE);
    private ProductCart waterMelon = new ProductCart("waterMelon", BigDecimal.TEN);
    private ProductCart melon = new ProductCart("melon", BigDecimal.ONE);

    private BucketManagerFactory keyValueEntityManagerFactory;

    private List<ProductCart> fruits;

    @BeforeEach
    public void init() {
        keyValueEntityManagerFactory =  KeyValueEntityManagerFactoryUtils.get();
        fruits = keyValueEntityManagerFactory.getList(FRUITS, ProductCart.class);
    }

    @Test
    public void shouldReturnsList() {
        assertNotNull(fruits);
    }

    @Test
    public void shouldAddList() {
        assertTrue(fruits.isEmpty());
        fruits.add(banana);
        assertFalse(fruits.isEmpty());
        ProductCart banana = fruits.get(0);
        assertNotNull(banana);
        assertEquals(banana.getName(), "banana");
    }

    @Test
    public void shouldSetList() {

        fruits.add(banana);
        fruits.add(0, orange);
        assertTrue(fruits.size() == 2);

        assertEquals(fruits.get(0).getName(), "orange");
        assertEquals(fruits.get(1).getName(), "banana");

        fruits.set(0, waterMelon);
        assertEquals(fruits.get(0).getName(), "waterMelon");
        assertEquals(fruits.get(1).getName(), "banana");

    }

    @Test
    public void shouldRemoveList() {
        fruits.add(banana);
    }

    @Test
    public void shouldReturnIndexOf() {

        fruits.add(new ProductCart("orange", BigDecimal.ONE));
        fruits.add(banana);
        fruits.add(new ProductCart("watermellon", BigDecimal.ONE));
        fruits.add(banana);
        assertTrue(fruits.indexOf(banana) == 1);
        assertTrue(fruits.lastIndexOf(banana) == 3);

        assertTrue(fruits.contains(banana));
        assertTrue(fruits.indexOf(melon) == -1);
        assertTrue(fruits.lastIndexOf(melon) == -1);
    }

    @Test
    public void shouldReturnContains() {

        fruits.add(orange);
        fruits.add(banana);
        fruits.add(waterMelon);
        assertTrue(fruits.contains(banana));
        assertFalse(fruits.contains(melon));
        assertTrue(fruits.containsAll(Arrays.asList(banana, orange)));
        assertFalse(fruits.containsAll(Arrays.asList(banana, melon)));

    }

    @SuppressWarnings("unused")
    @Test
    public void shouldIterate() {
        fruits.add(melon);
        fruits.add(banana);
        int count = 0;
        for (ProductCart fruiCart: fruits) {
            count++;
        }
        assertTrue(count == 2);
        fruits.remove(0);
        fruits.remove(0);
        count = 0;
        for (ProductCart fruiCart: fruits) {
            count++;
        }
        assertTrue(count == 0);
    }
    @AfterEach
    public  void end() {
        fruits.clear();
    }
}
