package com.colegio.alertas.repository;

import com.colegio.alertas.entity.Suscripcion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sistema de Alertas
 */
@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, String> {

    Suscripcion findByEndpoint(String endpoint);

    @Query("SELECT s FROM Suscripcion s WHERE s.usuario.nombreUsuario = :nombreUsuario")
    List<Suscripcion> buscar(@Param("nombreUsuario") String nombreUsuario);

}
