package com.colegio.alertas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Anthony Gutiérrez
 */
@Controller
public class UsuarioController {

    @GetMapping("/admin/usuarios")
    public String lista() {
        return "admin/usuarios/lista";
    }

}
