package com.colegio.alertas.repository;

import com.colegio.alertas.entity.Grado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sistema de Alertas
 */
@Repository
public interface GradoRepository extends JpaRepository<Grado, Integer> {

    Grado findByIdGrado(Integer idGrado);

}
