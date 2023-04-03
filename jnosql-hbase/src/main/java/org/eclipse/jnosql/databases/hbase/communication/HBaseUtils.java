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
package org.eclipse.jnosql.databases.hbase.communication;


import org.eclipse.jnosql.communication.column.Column;

final class HBaseUtils {

    static final String KEY_COLUMN = "_id";

    private HBaseUtils() {
    }


    static Column getKey(Object value) {
        return Column.of(KEY_COLUMN, value);
    }


}
