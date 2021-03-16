package com.colegio.alertas.repository;

import com.colegio.alertas.entity.Usuario;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Sistema de Alertas
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByIdUsuario(Integer idUsuario);

    Usuario findByNombreUsuario(String nombreUsuario);

    List<Usuario> findAllByIdTipoUsuario(Integer idTipoUsuario);

    @Query("SELECT COUNT(u) FROM Usuario u "
            + "WHERE u.nombreUsuario LIKE CONCAT('%', :termino, '%')"
            + " OR u.dni LIKE CONCAT('%', :termino, '%')"
            + " OR u.nombres LIKE CONCAT('%', :termino, '%')"
            + " OR u.apellidos LIKE CONCAT('%', :termino, '%')")
    Integer contar(String termino);

    @Query("SELECT u FROM Usuario u "
            + "WHERE u.nombreUsuario LIKE CONCAT('%', :termino, '%')"
            + " OR u.dni LIKE CONCAT('%', :termino, '%')"
            + " OR u.nombres LIKE CONCAT('%', :termino, '%')"
            + " OR u.apellidos LIKE CONCAT('%', :termino, '%')")
    List<Usuario> buscar(String termino, Pageable pageable);

}
