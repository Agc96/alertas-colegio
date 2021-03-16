package com.colegio.alertas.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Sistema de Alertas
 */
@Controller
public class LoginController {

    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                if (ex != null) {
                    LOG.log(Level.WARNING, "El usuario no pudo iniciar sesión", ex);
                    model.addAttribute("mostrarError", true);
                }
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error al verificar si hubo un error en inicio de sesión", ex);
        }
        return "usuario/login";
    }

    @GetMapping("/perfil")
    public String perfil() {
        return "usuario/perfil";
    }

    @GetMapping("/contrasenia")
    public String contrasenia() {
        return "usuario/contrasenia";
    }

}
