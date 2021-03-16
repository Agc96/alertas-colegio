package com.colegio.alertas.repository;

import com.colegio.alertas.entity.Alumno;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Sistema de Alertas
 */
public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {

    Alumno findByIdAlumno(Integer idAlumno);

    @Query("SELECT COUNT(a) FROM Alumno a "
            + "WHERE a.dni LIKE CONCAT('%', :termino, '%')"
            + " OR a.nombres LIKE CONCAT('%', :termino, '%')"
            + " OR a.apellidos LIKE CONCAT('%', :termino, '%')")
    Integer contar(String termino);

    @Query("SELECT a FROM Alumno a "
            + "WHERE a.dni LIKE CONCAT('%', :termino, '%')"
            + " OR a.nombres LIKE CONCAT('%', :termino, '%')"
            + " OR a.apellidos LIKE CONCAT('%', :termino, '%')")
    List<Alumno> buscar(String termino, Pageable pageable);

}
