package com.colegio.alertas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Anthony Gutiérrez
 */
@Controller
public class AsistenciaController {

    @GetMapping("/docente/asistencia")
    public String listar() {
        return "docente/asistencia/lista";
    }

    @GetMapping("/docente/asistencia/nuevo")
    public String nuevo() {
        return "docente/asistencia/registro";
    }

    @GetMapping("/docente/asistencia/{idAsistencia}")
    public String editar(@PathVariable Long idAsistencia) {
        return "docente/asistencia/registro";
    }

}
