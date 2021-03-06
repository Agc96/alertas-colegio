package com.colegio.alertas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Anthony Gutiérrez
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
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
