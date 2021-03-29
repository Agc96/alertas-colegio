package com.colegio.alertas.repository;

import com.colegio.alertas.entity.AulaAlumno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Anthony Gutiérrez
 */
@Repository
public interface AulaAlumnoRepository extends JpaRepository<AulaAlumno, Integer> {

    AulaAlumno findByIdAulaAlumno(Integer idAulaAlumno);

    @Query("SELECT COUNT(a) FROM AulaAlumno a WHERE a.aula.idAula = :idAula")
    Integer contarPorAula(@Param("idAula") Integer idAula);

    @Query("SELECT a FROM AulaAlumno a WHERE a.aula.idAula = :idAula")
    List<AulaAlumno> listarPorAula(@Param("idAula") Integer idAula);

}
