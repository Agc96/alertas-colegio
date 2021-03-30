package com.colegio.alertas.repository;

import com.colegio.alertas.entity.Comunicado;
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
public interface ComunicadoRepository extends JpaRepository<Comunicado, Integer> {

    Comunicado findByIdComunicado(Integer idComunicado);

    @Query("SELECT COUNT(c) FROM Comunicado c "
            + "WHERE (c.titulo LIKE CONCAT('%', :termino, '%')"
            + "    OR c.fecha LIKE CONCAT('%', :termino, '%'))"
            + "  AND (c.aula.idAula = :idAula)")
    Integer contar(@Param("idAula") Integer idAula, @Param("termino") String termino);

    @Query("SELECT c FROM Comunicado c "
            + "WHERE (c.titulo LIKE CONCAT('%', :termino, '%')"
            + "    OR c.fecha LIKE CONCAT('%', :termino, '%'))"
            + "   AND c.aula.idAula = :idAula")
    List<Comunicado> buscar(@Param("idAula") Integer idAula,
            @Param("termino") String termino, Pageable pageable);

}
