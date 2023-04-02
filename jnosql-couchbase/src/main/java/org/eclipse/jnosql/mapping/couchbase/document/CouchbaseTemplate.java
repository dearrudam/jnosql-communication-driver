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
package org.eclipse.jnosql.mapping.couchbase.document;


import com.couchbase.client.java.json.JsonObject;
import jakarta.nosql.document.DocumentTemplate;
import org.eclipse.jnosql.mapping.document.JNoSQLDocumentTemplate;

import java.util.stream.Stream;

/**
 * A {@link DocumentTemplate} to couchbase
 */
public interface CouchbaseTemplate extends JNoSQLDocumentTemplate {


    /**
     * Executes the n1qlquery with params and then result que result
     *
     * @param n1qlQuery the query
     * @param params    the params
     * @return the query result
     * @throws NullPointerException when either n1qlQuery or params are null
     */
    <T> Stream<T> n1qlQuery(String n1qlQuery, JsonObject params);


    /**
     * Executes the n1ql  plain query and then result que result
     *
     * @param n1qlQuery the query
     * @return the query result
     * @throws NullPointerException when either n1qlQuery or params are null
     */
    <T> Stream<T> n1qlQuery(String n1qlQuery);


}
