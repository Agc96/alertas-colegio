package com.colegio.alertas.config;

import com.colegio.alertas.entity.Usuario;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Sistema de Alertas
 */
public class AppUserDetails implements UserDetails {

    private static final long serialVersionUID = 2648860578214202041L;

    private static final Logger LOG = Logger.getLogger(AppUserDetails.class.getName());

    private final Usuario usuario;

    public AppUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String nombre = usuario.getUsuarioRol().getRol().toString();
        return Collections.singletonList(new SimpleGrantedAuthority(nombre));
    }

    @Override
    public String getPassword() {
        return usuario.getContrasenia();
    }

    @Override
    public String getUsername() {
        return usuario.getNombreUsuario();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
