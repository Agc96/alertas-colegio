package com.colegio.alertas.repository;

import com.colegio.alertas.entity.Asistencia;
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
public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {

    Asistencia findByIdAsistencia(Integer idAsistencia);

    @Query("SELECT COUNT(a) FROM Asistencia a "
            + "WHERE a.aula.idAula = :idAula "
            + "AND FUNCTION('FORMAT', a.fecha, 'dd/MM/yyyy') LIKE CONCAT('%', :termino, '%')")
    Integer contar(@Param("idAula") Integer idAula, @Param("termino") String termino);

    @Query("SELECT a FROM Asistencia a "
            + "WHERE a.aula.idAula = :idAula "
            + "AND FUNCTION('FORMAT', a.fecha, 'dd/MM/yyyy') LIKE CONCAT('%', :termino, '%')")
    List<Asistencia> buscar(@Param("idAula") Integer idAula, @Param("termino") String termino,
            Pageable pageable);

}
