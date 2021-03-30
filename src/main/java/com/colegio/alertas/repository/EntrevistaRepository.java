package com.colegio.alertas.repository;

import com.colegio.alertas.entity.Entrevista;
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
public interface EntrevistaRepository extends JpaRepository<Entrevista, Integer> {

    @Query("SELECT COUNT(e) FROM Entrevista e "
            + "WHERE (e.fecha LIKE CONCAT('%', :termino, '%') "
            + "   AND e.alumno.dni LIKE CONCAT('%', :termino, '%') "
            + "   AND e.alumno.nombres LIKE CONCAT('%', :termino, '%') "
            + "   AND e.alumno.apellidos LIKE CONCAT('%', :termino, '%')) "
            + "AND e.aula.idAula = :idAula")
    Integer contarDocente(
            @Param("idAula") Integer idAula,
            @Param("termino") String termino
    );

    @Query("SELECT e FROM Entrevista e "
            + "WHERE (e.fecha LIKE CONCAT('%', :termino, '%') "
            + "   AND e.alumno.dni LIKE CONCAT('%', :termino, '%') "
            + "   AND e.alumno.nombres LIKE CONCAT('%', :termino, '%') "
            + "   AND e.alumno.apellidos LIKE CONCAT('%', :termino, '%')) "
            + "AND e.aula.idAula = :idAula")
    List<Entrevista> buscarDocente(
            @Param("idAula") Integer idAula,
            @Param("termino") String termino,
            Pageable pageable
    );

    @Query("SELECT COUNT(e) FROM Entrevista e "
            + "WHERE e.aula.idAula = :idAula "
            + "AND e.alumno.idAlumno = :idAlumno "
            + "AND e.fecha LIKE CONCAT('%', :termino, '%')")
    Integer contarPadre(
            @Param("idAula") Integer idAula,
            @Param("idAlumno") Integer idAlumno,
            @Param("termino") String termino
    );

    @Query("SELECT e FROM Entrevista e "
            + "WHERE e.aula.idAula = :idAula "
            + "AND e.alumno.idAlumno = :idAlumno "
            + "AND e.fecha LIKE CONCAT('%', :termino, '%')")
    List<Entrevista> buscarPadre(
            @Param("idAula") Integer idAula,
            @Param("idAlumno") Integer idAlumno,
            @Param("termino") String termino,
            Pageable pageable
    );

}
