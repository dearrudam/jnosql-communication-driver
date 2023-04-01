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
package org.eclipse.jnosql.communication.couchbase.keyvalue;

import com.couchbase.client.java.codec.TypeRef;
import com.couchbase.client.java.kv.GetResult;
import org.eclipse.jnosql.communication.TypeSupplier;
import org.eclipse.jnosql.communication.Value;

import java.lang.reflect.Type;
import java.util.Objects;

final class CouchbaseValue implements Value {

    private final GetResult result;

    CouchbaseValue(GetResult result) {
        this.result = result;
    }

    @Override
    public Object get() {
        return result;
    }

    @Override
    public <T> T get(Class<T> type) {
        Objects.requireNonNull(type, "type is required");
        return result.contentAs(type);
    }

    @Override
    public <T> T get(TypeSupplier<T> typeSupplier) {
        Objects.requireNonNull(typeSupplier, "typeSupplier is required");
        return result.contentAs(new TypeRef<>() {
            @Override
            public Type type() {
                return typeSupplier.get();
            }
        });
    }

    @Override
    public boolean isInstanceOf(Class<?> typeClass) {
        return false;
    }
}
