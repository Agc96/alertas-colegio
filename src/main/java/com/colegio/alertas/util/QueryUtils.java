package com.colegio.alertas.util;

import com.colegio.alertas.dto.BusquedaDto;
import com.colegio.alertas.dto.ResultadoDto;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Sistema de Alertas
 */
public final class QueryUtils {

    private static final Logger LOG = Logger.getLogger(QueryUtils.class.getName());

    private QueryUtils() {
        // Nadie debe ser capaz de instanciar esta clase.
    }

    public static Pageable createPagination(BusquedaDto busqueda) {
        return PageRequest.of(busqueda.getIndicePagina(), busqueda.getNumResultados());
    }

    public static void setNumPaginas(BusquedaDto busqueda, ResultadoDto<?> resultado) {
        double total = resultado.getTotal();
        double numResultados = busqueda.getNumResultados();
        double numPaginas = Math.ceil(total / numResultados);
        resultado.setNumPaginas((int)numPaginas);
    }

    public static String toString(Object column) {
        return column == null ? null : column.toString();
    }

    public static Integer toInteger(Object column) {
        if (column != null && column instanceof Number) {
            return ((Number) column).intValue();
        }
        return null;
    }

}
