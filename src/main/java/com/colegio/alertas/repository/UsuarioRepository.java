package com.colegio.alertas.repository;

import com.colegio.alertas.entity.Usuario;
import com.colegio.alertas.util.enums.Rol;
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
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findByNombreUsuario(String nombreUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.usuarioRol.rol = :rol")
    List<Usuario> listarPorRol(@Param("rol") Rol rol);

    @Query("SELECT COUNT(u) FROM Usuario u "
            + "WHERE u.nombreUsuario LIKE CONCAT('%', :termino, '%')"
            + " OR u.dni LIKE CONCAT('%', :termino, '%')"
            + " OR u.nombres LIKE CONCAT('%', :termino, '%')"
            + " OR u.apellidos LIKE CONCAT('%', :termino, '%')")
    Integer contar(@Param("termino") String termino);

    @Query("SELECT u FROM Usuario u "
            + "WHERE u.nombreUsuario LIKE CONCAT('%', :termino, '%')"
            + " OR u.dni LIKE CONCAT('%', :termino, '%')"
            + " OR u.nombres LIKE CONCAT('%', :termino, '%')"
            + " OR u.apellidos LIKE CONCAT('%', :termino, '%')")
    List<Usuario> buscar(@Param("termino") String termino, Pageable pageable);

}
