package com.colegio.alertas.repository;

import com.colegio.alertas.entity.DetalleAsistencia;
import com.colegio.alertas.util.enums.EstadoAsistencia;
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
public interface DetalleAsistenciaRepository extends JpaRepository<DetalleAsistencia, Integer> {

    DetalleAsistencia findByIdDetalleAsistencia(Integer idDetalleAsistencia);

    @Query("SELECT d FROM DetalleAsistencia d WHERE d.asistencia.idAsistencia = :idAsistencia")
    List<DetalleAsistencia> listar(@Param("idAsistencia") Integer idAsistencia);

    @Query("SELECT COUNT(da) FROM DetalleAsistencia da WHERE da.estadoAsistencia = :estado")
    Integer contarPorEstado(@Param("estado") EstadoAsistencia estado);

    @Query("SELECT COUNT(da) FROM DetalleAsistencia da "
            + "WHERE da.asistencia.aula.idAula = :idAula "
            + "AND da.alumno.idAlumno = :idAlumno "
            + "AND da.asistencia.fecha LIKE CONCAT('%', :termino, '%')")
    Integer contarPadre(
            @Param("idAula") Integer idAula,
            @Param("idAlumno") Integer idAlumno,
            @Param("termino") String termino
    );

    @Query("SELECT da FROM DetalleAsistencia da "
            + "WHERE da.asistencia.aula.idAula = :idAula "
            + "AND da.alumno.idAlumno = :idAlumno "
            + "AND da.asistencia.fecha LIKE CONCAT('%', :termino, '%')")
    List<DetalleAsistencia> buscarPadre(
            @Param("idAula") Integer idAula,
            @Param("idAlumno") Integer idAlumno,
            @Param("termino") String termino,
            Pageable pageable
    );

}
