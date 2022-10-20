/*
 *  Copyright (c) 2017 Otávio Santana and others
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
package org.eclipse.jnosql.communication.couchbase.document;


import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.search.SearchQuery;
import jakarta.nosql.document.DocumentCollectionManager;
import jakarta.nosql.document.DocumentEntity;

import java.util.stream.Stream;

/**
 * The couchbase implementation of {@link DocumentCollectionManager}
 */
public interface CouchbaseDocumentCollectionManager extends DocumentCollectionManager {

    /**
     * Executes the N1QL with params and then result que result
     *
     * @param n1ql the query
     * @param params    the params
     * @return the query result
     * @throws NullPointerException when either n1ql or params are null
     */
    Stream<DocumentEntity> n1qlQuery(String n1ql, JsonObject params) throws NullPointerException;


    /**
     * Executes the n1qlquery  plain query and then result que result
     *
     * @param n1ql the query
     * @return the query result
     * @throws NullPointerException when either n1ql or params are null
     */
    Stream<DocumentEntity> n1qlQuery(String n1ql) throws NullPointerException;


    /**
     * Searches in Couchbase using Full Text Search
     *
     * @param query the query to be used
     * @return the elements from the query
     * @throws NullPointerException when either the query or index are null
     */
    Stream<DocumentEntity> search(SearchQuery query) throws NullPointerException;
}