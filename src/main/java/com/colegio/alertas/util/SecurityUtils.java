package com.colegio.alertas.util;

import com.colegio.alertas.util.enums.Rol;
import java.util.Collection;
import java.util.logging.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Anthony Gutiérrez
 */
public final class SecurityUtils {

    private static final Logger LOG = Logger.getLogger(SecurityUtils.class.getName());

    private SecurityUtils() {
        // Nadie debe ser capaz de instanciar esta clase.
    }

    public static UserDetails getCurrentUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user instanceof UserDetails ? (UserDetails) user : null;
    }

    public static String getUsername() {
        UserDetails user = getCurrentUser();
        return user == null ? null : user.getUsername();
    }

    public static boolean hasRole(Rol rol) {
        Collection<? extends GrantedAuthority> roles = getCurrentUser().getAuthorities();
        if (!Preconditions.isEmpty(roles)) {
            for (GrantedAuthority authority : roles) {
                if (authority.getAuthority().equals(rol.toString())) return true;
            }
        }
        return false;
    }

}
