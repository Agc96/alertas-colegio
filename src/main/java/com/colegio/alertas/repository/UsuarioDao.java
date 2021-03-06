package com.colegio.alertas.repository;

import com.colegio.alertas.entity.Usuario;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Anthony Gutiérrez
 */
public interface UsuarioDao extends JpaRepository<Usuario, Long> {

    List<Usuario> findAllByIdTipoUsuario(Integer idTipoUsuario);

    @Query("SELECT u FROM Usuario u "
            + "WHERE u.nombreUsuario LIKE CONCAT('%', :filtro, '%')"
            + " OR u.dni LIKE CONCAT('%', :filtro, '%')"
            + " OR u.nombres LIKE CONCAT('%', :filtro, '%')"
            + " OR u.apellidos LIKE CONCAT('%', :filtro, '%')")
    List<Usuario> filtrarUsuario(String filtro, Pageable pageable);

}
