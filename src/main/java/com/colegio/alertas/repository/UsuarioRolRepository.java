package com.colegio.alertas.repository;

import com.colegio.alertas.entity.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Anthony Gutiérrez
 */
@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Integer> {

}
