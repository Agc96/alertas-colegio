package com.colegio.alertas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Sistema de Alertas
 */
@Controller
public class AsistenciaController {

    @GetMapping("/aulas/{idAula}/asistencia")
    public String listar(@PathVariable Integer idAula) {
        return "docente/asistencia/lista";
    }

    @GetMapping("/aulas/{idAula}/asistencia/nuevo")
    public String nuevo(@PathVariable Integer idAula) {
        return "docente/asistencia/registro";
    }

    @GetMapping("/aulas/{idAula}/asistencia/{idAsistencia}")
    public String editar(@PathVariable Integer idAula, @PathVariable Integer idAsistencia) {
        return "docente/asistencia/registro";
    }

}
