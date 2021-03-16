package com.colegio.alertas.util;

import com.colegio.alertas.dto.in.BusquedaDto;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Sistema de Alertas
 */
public final class QueryUtils {

    private QueryUtils() {
        // Nadie debe ser capaz de instanciar esta clase.
    }

    public static Query createNativeQuery(String sql, Map<String, Object> parameters, EntityManager entityManager) {
        Query query = entityManager.createQuery(sql);
        for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
            query.setParameter(parameter.getKey(), parameter.getValue());
        }
        return query;
    }

    public static Pageable createPagination(BusquedaDto busqueda) {
        return PageRequest.of(busqueda.getNumPagina(), busqueda.getNumResultados());
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

}
