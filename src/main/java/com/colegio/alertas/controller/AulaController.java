package com.colegio.alertas.controller;

import com.colegio.alertas.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Sistema de Alertas
 */
@Controller
public class AulaController {

    @Autowired
    private CommonService commonService;

    @GetMapping("/admin/aulas")
    public String listaAdmin() {
        return "admin/aulas/lista";
    }

    @GetMapping("/admin/aulas/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("anios", commonService.listarAnios());
        model.addAttribute("grados", commonService.listarGrados());
        return "admin/aulas/registro";
    }

    @GetMapping("/admin/aulas/{idAula}")
    public String editar(@PathVariable Integer idAula) {
        return "admin/aulas/registro";
    }

    @GetMapping("/aulas")
    public String listaDocente() {
        return "docente/aulas/lista";
    }

    @GetMapping("/aulas/{idAula}")
    public String detalleDocente(@PathVariable Integer idAula) {
        return "docente/aulas/detalle";
    }

}
