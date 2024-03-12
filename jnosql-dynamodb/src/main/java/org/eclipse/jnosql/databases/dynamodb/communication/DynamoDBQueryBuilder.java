/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 * You may elect to redistribute this code under either of these licenses.
 *
 * Contributors:
 *
 * Maximillian Arruda
 */

package org.eclipse.jnosql.databases.dynamodb.communication;

import org.eclipse.jnosql.communication.TypeReference;
import org.eclipse.jnosql.communication.document.Document;
import org.eclipse.jnosql.communication.document.DocumentCondition;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.eclipse.jnosql.databases.dynamodb.communication.DocumentEntityConverter.toAttributeValue;

abstract class DynamoDBQueryBuilder implements Supplier<DynamoDBQuery> {

    protected void condition(DocumentCondition condition,
                             StringBuilder filterExpression,
                             Map<String, String> expressionAttributeNames,
                             Map<String, AttributeValue> expressionAttributeValues) {
        Document document = condition.document();

        switch (condition.condition()) {
            case EQUALS:
                predicate(" = ", document, filterExpression, expressionAttributeNames, expressionAttributeValues);
                break;
            case LIKE:
                predicateLike(document, filterExpression, expressionAttributeNames, expressionAttributeValues);
                break;
            case IN:
                predicateIn(document, filterExpression, expressionAttributeNames, expressionAttributeValues);
                break;
            case GREATER_THAN:
                predicate(" > ", document, filterExpression, expressionAttributeNames, expressionAttributeValues);
                break;
            case LESSER_THAN:
                predicate(" < ", document, filterExpression, expressionAttributeNames, expressionAttributeValues);
                break;
            case GREATER_EQUALS_THAN:
                predicate(" >= ", document, filterExpression, expressionAttributeNames, expressionAttributeValues);
                break;
            case LESSER_EQUALS_THAN:
                predicate(" <= ", document, filterExpression, expressionAttributeNames, expressionAttributeValues);
                break;
            case BETWEEN:
                predicateBetween(document, filterExpression, expressionAttributeNames, expressionAttributeValues);
                break;
            case AND:
                appendCondition(document.get(new TypeReference<>() {
                }), " AND ", filterExpression, expressionAttributeNames, expressionAttributeValues);
                break;
            case OR:
                appendCondition(document.get(new TypeReference<>() {
                }), " OR ", filterExpression, expressionAttributeNames, expressionAttributeValues);
                break;
            case NOT:
                filterExpression.append(" NOT ");
                condition(document.get(DocumentCondition.class), filterExpression, expressionAttributeNames, expressionAttributeValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown condition " + condition.condition());
        }
    }

    private void predicateIn(Document document,
                             StringBuilder filterExpression,
                             Map<String, String> expressionAttributeNames,
                             Map<String, AttributeValue> expressionAttributeValues) {
        var name = document.name();

        var attributeName = "#" + name;
        expressionAttributeNames.put(attributeName, name);
        filterExpression.append(attributeName).append(" IN (");

        List<String> valuesExpressionNames = new LinkedList<>();
        ((Iterable<?>) document.get()).forEach(value -> {
            var attributeValueName = ":" + name + "_" + expressionAttributeValues.size();
            valuesExpressionNames.add(attributeValueName);
            expressionAttributeValues.put(attributeValueName, toAttributeValue(value));
        });

        filterExpression.append(String.join(", ", valuesExpressionNames));
        filterExpression.append(") ");
    }

    private void appendCondition(List<DocumentCondition> conditions,
                                 String condition,
                                 StringBuilder filterExpression,
                                 Map<String, String> expressionAttributeNames,
                                 Map<String, AttributeValue> expressionAttributeValues) {

        boolean isFirstCondition = true;
        for (DocumentCondition documentCondition : conditions) {
            StringBuilder tempFilterExpression = new StringBuilder();
            HashMap<String, String> tempExpressionAttributeNames = new HashMap<>(expressionAttributeNames);
            HashMap<String, AttributeValue> tempExpressionAttributeValues = new HashMap<>(expressionAttributeValues);
            condition(documentCondition,
                    tempFilterExpression,
                    tempExpressionAttributeNames,
                    tempExpressionAttributeValues);
            if (isFirstCondition && !tempFilterExpression.isEmpty()) {
                filterExpression.append(tempFilterExpression);
                expressionAttributeNames.putAll(tempExpressionAttributeNames);
                expressionAttributeValues.putAll(tempExpressionAttributeValues);
            } else if (!tempFilterExpression.isEmpty()) {
                if (!filterExpression.substring(filterExpression.length() - condition.length()).equals(condition)) {
                    filterExpression.append(condition);
                }
                filterExpression.append(tempFilterExpression);
                expressionAttributeNames.putAll(tempExpressionAttributeNames);
                expressionAttributeValues.putAll(tempExpressionAttributeValues);
            }
            isFirstCondition = false;
        }


    }

    private void predicateBetween(Document document,
                                  StringBuilder filterExpression,
                                  Map<String, String> expressionAttributeNames,
                                  Map<String, AttributeValue> expressionAttributeValues) {

        var name = document.name();

        List<Object> values = new ArrayList<>();
        ((Iterable<?>) document.get()).forEach(values::add);

        var attributeName = "#" + name;
        expressionAttributeNames.put(attributeName, name);

        filterExpression.append(attributeName).append(" BETWEEN ");

        var fistAttributeValueName = ":" + name + "_" + expressionAttributeValues.size();
        expressionAttributeValues.put(fistAttributeValueName, toAttributeValue(values.get(0)));
        filterExpression.append(fistAttributeValueName).append(" AND ");

        var secondAttributeValueName = ":" + name + "_" + expressionAttributeValues.size();
        expressionAttributeValues.put(secondAttributeValueName, toAttributeValue(values.get(1)));
        filterExpression.append(secondAttributeValueName);

    }

    private void predicateLike(Document document,
                               StringBuilder filterExpression,
                               Map<String, String> expressionAttributeNames,
                               Map<String, AttributeValue> expressionAttributeValues) {

        var name = document.name();
        var value = toAttributeValue(document.get());

        var attributeName = "#" + name;
        var attributeValueName = ":" + name + "_" + expressionAttributeValues.size();

        filterExpression.append("begins_with(")
                .append(attributeName).append(',')
                .append(attributeValueName).append(')');

        expressionAttributeNames.put(attributeName, name);
        expressionAttributeValues.put(attributeValueName, value);
    }

    protected void predicate(String operator,
                             Document document,
                             StringBuilder filterExpression,
                             Map<String, String> expressionAttributeNames,
                             Map<String, AttributeValue> expressionAttributeValues) {

        var name = document.name();
        var value = toAttributeValue(document.get());

        var attributeName = "#" + name;
        var attributeValueName = ":" + name + "_" + expressionAttributeValues.size();

        filterExpression.append(attributeName).append(operator).append(attributeValueName);
        expressionAttributeNames.put(attributeName, name);
        expressionAttributeValues.put(attributeValueName, value);

    }

}
