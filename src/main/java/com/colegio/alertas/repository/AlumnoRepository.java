package com.colegio.alertas.repository;

import com.colegio.alertas.entity.Alumno;
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
public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {

    Alumno findByIdAlumno(Integer idAlumno);

    @Query("SELECT COUNT(a) FROM Alumno a "
            + "WHERE a.dni LIKE CONCAT('%', :termino, '%')"
            + "   OR a.nombres LIKE CONCAT('%', :termino, '%')"
            + "   OR a.apellidos LIKE CONCAT('%', :termino, '%')"
            + "   OR FUNCTION('FORMAT', a.fechaNacimiento, 'dd/MM/yyyy') LIKE CONCAT('%', :termino, '%')")
    Integer contar(@Param("termino") String termino);

    @Query("SELECT a FROM Alumno a "
            + "WHERE a.dni LIKE CONCAT('%', :termino, '%')"
            + "   OR a.nombres LIKE CONCAT('%', :termino, '%')"
            + "   OR a.apellidos LIKE CONCAT('%', :termino, '%')"
            + "   OR FUNCTION('FORMAT', a.fechaNacimiento, 'dd/MM/yyyy') LIKE CONCAT('%', :termino, '%')")
    List<Alumno> buscar(@Param("termino") String termino, Pageable pageable);

    @Query("SELECT COUNT(a) FROM Alumno a WHERE a.dni = :dni")
    Integer contarDniNuevo(@Param("dni") String dni);

    @Query("SELECT COUNT(a) FROM Alumno a WHERE a.dni = :dni AND a.idAlumno != :idAlumno")
    Integer contarDniExistente(@Param("dni") String dni, @Param("idAlumno") Integer idAlumno);

}
