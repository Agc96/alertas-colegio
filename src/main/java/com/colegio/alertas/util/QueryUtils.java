package com.colegio.alertas.util;

import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Anthony Gutiérrez
 */
public final class QueryUtils {

    private QueryUtils() {
        // Nadie debe ser capaz de instanciar esta clase.
    }

    public static Query createQuery(String sql, Map<String, Object> parameters, EntityManager entityManager) {
        Query query = entityManager.createQuery(sql);
        for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
            query.setParameter(parameter.getKey(), parameter.getValue());
        }
        return query;
    }

    public static Query setPagination(Query query, Integer pageNumber, Integer maxResults) {
        query.setMaxResults(maxResults);
        query.setFirstResult(maxResults * (pageNumber - 1));
        return query;
    }

    public static String toString(Object column) {
        return column == null ? null : column.toString();
    }

    public static Integer toInteger(Object column) {
        return column == null ? null : ((Number) column).intValue();
    }

    public static Long toLong(Object column) {
        return column == null ? null : ((Number) column).longValue();
    }

}
