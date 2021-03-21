package com.colegio.alertas.repository;

import com.colegio.alertas.entity.Entrevista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Anthony Gutiérrez
 */
@Repository
public interface EntrevistaRepository extends JpaRepository<Entrevista, Integer> {

}
