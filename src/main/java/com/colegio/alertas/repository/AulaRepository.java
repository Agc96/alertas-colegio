package com.colegio.alertas.repository;

import com.colegio.alertas.entity.Aula;
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
public interface AulaRepository extends JpaRepository<Aula, Integer> {

    Aula findByIdAula(Integer idAula);

    @Query("SELECT COUNT(a) FROM Aula a "
            + "WHERE a.anio.idAnio LIKE CONCAT('%', :termino, '%')"
            + " OR a.grado.nombre LIKE CONCAT('%', :termino, '%')"
            + " OR a.docente.nombres LIKE CONCAT('%', :termino, '%')"
            + " OR a.docente.apellidos LIKE CONCAT('%', :termino, '%')")
    Integer contarAdmin(@Param("termino") String termino);

    @Query("SELECT a FROM Aula a "
            + "WHERE a.anio.idAnio LIKE CONCAT('%', :termino, '%')"
            + "   OR a.grado.nombre LIKE CONCAT('%', :termino, '%')"
            + "   OR a.docente.nombres LIKE CONCAT('%', :termino, '%')"
            + "   OR a.docente.apellidos LIKE CONCAT('%', :termino, '%')")
    List<Aula> buscarAdmin(@Param("termino") String termino, Pageable pageable);

    @Query("SELECT COUNT(a) FROM Aula a "
            + "WHERE (a.anio.idAnio LIKE CONCAT('%', :termino, '%')"
            + "    OR a.grado.nombre LIKE CONCAT('%', :termino, '%'))"
            + "   AND a.docente.nombreUsuario = :docente")
    Integer contarDocente(@Param("docente") String docente, @Param("termino") String termino);

    @Query("SELECT a FROM Aula a "
            + "WHERE (a.anio.idAnio LIKE CONCAT('%', :termino, '%')"
            + "    OR a.grado.nombre LIKE CONCAT('%', :termino, '%'))"
            + "   AND a.docente.nombreUsuario = :docente")
    List<Aula> buscarDocente(@Param("docente") String docente,
            @Param("termino") String termino, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM sa_aula_alumno aa "
            + "INNER JOIN sa_alumno a ON a.id_alumno = aa.id_alumno "
            + "WHERE aa.id_aula = :idAula", nativeQuery = true)
    Integer totalAlumnos(@Param("idAula") Integer idAula);

    @Query(value = "SELECT a.id_alumno, a.dni, a.nombres, a.apellidos "
            + "FROM sa_aula_alumno aa "
            + "INNER JOIN sa_alumno a ON a.id_alumno = aa.id_alumno "
            + "WHERE aa.id_aula = :idAula", nativeQuery = true)
    List<Object[]> listarAlumnos(@Param("idAula") Integer idAula);

    @Query(value = "SELECT COUNT(*) FROM sa_aula_alumno aa "
            + "INNER JOIN sa_alumno a ON a.id_alumno = aa.id_alumno "
            + "WHERE (a.dni LIKE CONCAT('%', :termino, '%') "
            + "    OR a.nombres LIKE CONCAT('%', :termino, '%') "
            + "    OR a.apellidos LIKE CONCAT('%', :termino, '%')) "
            + "AND aa.id_aula = :idAula", nativeQuery = true)
    Integer contarAlumnos(@Param("termino") String termino,
            @Param("idAula") Integer idAula);

    @Query(value = "SELECT a.id_alumno, a.dni, a.nombres, a.apellidos "
            + "FROM sa_aula_alumno aa "
            + "INNER JOIN sa_alumno a ON a.id_alumno = aa.id_alumno "
            + "WHERE (a.dni LIKE CONCAT('%', :termino, '%') "
            + "    OR a.nombres LIKE CONCAT('%', :termino, '%') "
            + "    OR a.apellidos LIKE CONCAT('%', :termino, '%')) "
            + "AND aa.id_aula = :idAula", nativeQuery = true)
    List<Object[]> buscarAlumnos(@Param("termino") String termino,
            @Param("idAula") Integer idAula, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM sa_aula_alumno aa "
            + "INNER JOIN sa_alumno a ON a.id_alumno = aa.id_alumno "
            + "INNER JOIN sa_aula al ON al.id_aula = aa.id_aula "
            + "INNER JOIN sa_grado g ON g.id_grado = al.id_grado "
            + "WHERE (a.dni LIKE CONCAT('%', :termino, '%') "
            + "    OR a.nombres LIKE CONCAT('%', :termino, '%') "
            + "    OR a.apellidos LIKE CONCAT('%', :termino, '%') "
            + "    OR g.nombre LIKE CONCAT('%', :termino, '%') "
            + "    OR al.id_anio LIKE CONCAT('%', :termino, '%')) "
            + "AND a.padre = :padre", nativeQuery = true)
    Integer contarAlumnosPadre(@Param("termino") String termino,
            @Param("padre") String padre);

    @Query(value = "SELECT a.id_alumno, a.dni, a.nombres, a.apellidos, "
            + "al.id_aula, al.id_anio, al.id_grado, g.nombre "
            + "FROM sa_aula_alumno aa "
            + "INNER JOIN sa_alumno a ON a.id_alumno = aa.id_alumno "
            + "INNER JOIN sa_aula al ON al.id_aula = aa.id_aula "
            + "INNER JOIN sa_grado g ON g.id_grado = al.id_grado "
            + "WHERE (a.dni LIKE CONCAT('%', :termino, '%') "
            + "    OR a.nombres LIKE CONCAT('%', :termino, '%') "
            + "    OR a.apellidos LIKE CONCAT('%', :termino, '%') "
            + "    OR g.nombre LIKE CONCAT('%', :termino, '%') "
            + "    OR al.id_anio LIKE CONCAT('%', :termino, '%')) "
            + "AND a.padre = :padre", nativeQuery = true)
    List<Object[]> buscarAlumnosPadre(@Param("termino") String termino,
            @Param("padre") String padre, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM sa_aula_alumno aa "
            + "INNER JOIN sa_aula a on a.id_aula = aa.id_aula "
            + "WHERE aa.id_alumno = :idAlumno "
            + "  AND a.id_anio = :idAnio", nativeQuery = true)
    Integer verificarAlumnoAulaNueva(@Param("idAlumno") Integer idAlumno,
            @Param("idAnio") Integer idAnio);

    @Query(value = "SELECT COUNT(*) FROM sa_aula_alumno aa "
            + "INNER JOIN sa_aula a on a.id_aula = aa.id_aula "
            + "WHERE aa.id_alumno = :idAlumno "
            + "  AND aa.id_aula != :idAula "
            + "  AND a.id_anio = :idAnio", nativeQuery = true)
    Integer verificarAlumnoAulaExistente(@Param("idAlumno") Integer idAlumno,
            @Param("idAnio") Integer idAnio, @Param("idAula") Integer idAula);

}
