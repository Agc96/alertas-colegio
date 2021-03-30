package com.colegio.alertas.repository;

import com.colegio.alertas.entity.Incidencia;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sistema de Alertas
 */
@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    Incidencia findByIdIncidencia(Integer idIncidencia);

    @Query("SELECT COUNT(i) FROM Incidencia i "
            + "WHERE (i.fecha LIKE CONCAT('%', :termino, '%')"
            + "    OR i.alumno.dni LIKE CONCAT('%', :termino, '%')"
            + "    OR i.alumno.nombres LIKE CONCAT('%', :termino, '%')"
            + "    OR i.alumno.apellidos LIKE CONCAT('%', :termino, '%'))"
            + "  AND (i.aula.idAula = :idAula)")
    Integer contar(@Param("idAula") Integer idAula, @Param("termino") String termino);

    @Query("SELECT i FROM Incidencia i "
            + "WHERE (i.fecha LIKE CONCAT('%', :termino, '%')"
            + "    OR i.alumno.dni LIKE CONCAT('%', :termino, '%')"
            + "    OR i.alumno.nombres LIKE CONCAT('%', :termino, '%')"
            + "    OR i.alumno.apellidos LIKE CONCAT('%', :termino, '%'))"
            + "  AND (i.aula.idAula = :idAula)")
    List<Incidencia> buscar(@Param("idAula") Integer idAula,
            @Param("termino") String termino, Pageable pageable);

    @Query("SELECT COUNT(i) FROM Incidencia i "
            + "WHERE i.fecha LIKE CONCAT('%', :termino, '%') "
            + "AND i.aula.idAula = :idAula "
            + "AND i.alumno.idAlumno = :idAlumno")
    Integer contarPadre(@Param("idAula") Integer idAula,
            @Param("idAlumno") Integer idAlumno,
            @Param("termino") String termino);

    @Query("SELECT i FROM Incidencia i "
            + "WHERE i.fecha LIKE CONCAT('%', :termino, '%') "
            + "AND i.aula.idAula = :idAula "
            + "AND i.alumno.idAlumno = :idAlumno")
    List<Incidencia> buscarPadre(@Param("idAula") Integer idAula,
            @Param("idAlumno") Integer idAlumno,
            @Param("termino") String termino, Pageable pageable);

}
