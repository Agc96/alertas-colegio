package com.colegio.alertas.repository.custom;

import com.colegio.alertas.dto.AlumnoDto;
import java.util.List;

/**
 *
 * @author Anthony Gutiérrez
 */
public interface AlumnoDaoCustom {

    Long contarFiltro(AlumnoDto filtro);
    List<AlumnoDto> listarFiltro(AlumnoDto filtro, Integer firstResult,
            Integer maxResults);

}
