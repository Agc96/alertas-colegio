package com.colegio.alertas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Anthony Gutiérrez
 */
@Controller
public class AulaController {

    @GetMapping("/admin/aulas")
    public String listar() {
        return "admin/aulas/lista";
    }

    @GetMapping("/admin/aulas/nuevo")
    public String nuevo() {
        return "admin/aulas/registro";
    }

    @GetMapping("/admin/aulas/{idAula}")
    public String nuevo(@PathVariable Integer idAula) {
        return "admin/aulas/registro";
    }

}
