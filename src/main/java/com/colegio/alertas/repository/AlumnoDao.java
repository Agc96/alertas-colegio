package com.colegio.alertas.repository;

import com.colegio.alertas.repository.custom.AlumnoDaoCustom;
import com.colegio.alertas.entity.Alumno;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Anthony Gutiérrez
 */
public interface AlumnoDao extends JpaRepository<Alumno, Long>, AlumnoDaoCustom {

    @Query("SELECT a FROM Alumno a "
            + "WHERE a.dni LIKE CONCAT('%', :filtro, '%')"
            + " OR a.nombres LIKE CONCAT('%', :filtro, '%')"
            + " OR a.apellidos LIKE CONCAT('%', :filtro, '%')")
    List<Alumno> filtrarAlumnos(String filtro, Pageable pageable);

}
