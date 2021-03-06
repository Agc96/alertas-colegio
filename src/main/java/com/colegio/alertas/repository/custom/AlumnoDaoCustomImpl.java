package com.colegio.alertas.repository.custom;

import com.colegio.alertas.dto.AlumnoDto;
import com.colegio.alertas.util.Preconditions;
import com.colegio.alertas.util.QueryUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Anthony Gutiérrez
 */
public class AlumnoDaoCustomImpl implements AlumnoDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long contarFiltro(AlumnoDto filtro) {
        return (Long) queryFiltro(filtro, false).getSingleResult();
    }

    @Override
    public List<AlumnoDto> listarFiltro(AlumnoDto filtro, Integer pageNumber, Integer maxResults) {
        Query query = QueryUtils.setPagination(queryFiltro(filtro, true), pageNumber, maxResults);
        List<Object[]> alumnosBD = query.getResultList();
        // Convertir lista de registros de BD a lista de DTOs
        List<AlumnoDto> alumnosDto = new ArrayList<>(alumnosBD.size());
        for (Object[] alumnoBD : alumnosBD) {
            AlumnoDto alumnoDto = new AlumnoDto();
            alumnoDto.setIdAlumno(QueryUtils.toLong(alumnoBD[0]));
            alumnoDto.setNombres(QueryUtils.toString(alumnoBD[1]));
            alumnoDto.setApellidos(QueryUtils.toString(alumnoBD[2]));
            alumnoDto.setFechaNacimiento(QueryUtils.toString(alumnoBD[3]));
            alumnosDto.add(alumnoDto);
        }
        return alumnosDto;
    }

    private Query queryFiltro(AlumnoDto filtro, boolean showResults) {
        // Formular la sentencia SQL
        StringBuilder sql = new StringBuilder("SELECT ");
        if (showResults) {
            sql.append(" a.id_alumno, "); // 0
            sql.append(" a.nombres, "); // 1
            sql.append(" a.apellidos, "); // 2
            sql.append(" FORMAT(a.fecha_nacimiento, 'dd/MM/yyyy') "); // 3
        } else {
            sql.append(" COUNT(*) ");
        }
        sql.append("FROM sa_alumno a ");
        sql.append("WHERE 1=1 ");
        // Agregar las condiciones a la sentencia SQL
        Map<String, Object> parameters = new HashMap<>();
        if (!Preconditions.isEmpty(filtro.getNombres())) {
            sql.append(" AND a.nombres LIKE concat('%', :nombres, '%') ");
            parameters.put("nombres", filtro.getNombres());
        }
        if (!Preconditions.isEmpty(filtro.getApellidos())) {
            sql.append(" AND a.apellidos LIKE concat('%', :apellidos, '%') ");
            parameters.put("apellidos", filtro.getApellidos());
        }
        // Formar el query
        return QueryUtils.createQuery(sql.toString(), parameters, entityManager);
    }

}
