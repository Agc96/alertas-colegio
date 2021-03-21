package com.colegio.alertas.repository;

import com.colegio.alertas.entity.Anio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sistema de Alertas
 */
@Repository
public interface AnioRepository extends JpaRepository<Anio, Integer> {

    Anio findByIdAnio(Integer idAnio);

}
