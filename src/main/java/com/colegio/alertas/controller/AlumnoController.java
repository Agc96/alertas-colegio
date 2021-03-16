package com.colegio.alertas.controller;

import com.colegio.alertas.dto.AlumnoDto;
import com.colegio.alertas.dto.in.BusquedaDto;
import com.colegio.alertas.dto.out.BaseDto;
import com.colegio.alertas.dto.out.ResultadoDto;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.colegio.alertas.service.AlumnoService;
import com.colegio.alertas.service.UsuarioService;
import com.colegio.alertas.util.AppException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Sistema de Alertas
 */
@Controller("")
public class AlumnoController {

    private static final Logger LOG = Logger.getLogger(AlumnoController.class.getName());

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/admin/alumnos")
    public String lista(Model model) {
        model.addAttribute("padres", usuarioService.listarPadres());
        return "admin/alumnos/lista";
    }

    @PostMapping("/admin/alumnos/buscar")
    @ResponseBody
    public ResultadoDto<AlumnoDto> buscar(BusquedaDto busqueda) {
        ResultadoDto<AlumnoDto> response = new ResultadoDto<>();
        try {
            response.setTotal(alumnoService.contar(busqueda));
            if (response.getTotal() > 0) {
                response.setLista(alumnoService.buscar(busqueda));
            }
        } catch (Exception ex) {
            String msg = "Hubo un error al buscar los alumnos. " + ex.getMessage();
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/admin/alumnos/registrar")
    @ResponseBody
    public BaseDto registrar(AlumnoDto request) {
        BaseDto response = new BaseDto();
        try {
            if (request.getIdAlumno() == null) {
                alumnoService.registrar(request);
            } else {
                alumnoService.editar(request);
            }
        } catch (AppException ex) {
            String msg = "No se pudo registrar el alumno. " + ex.getMessage();
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/admin/alumnos/eliminar")
    @ResponseBody
    public BaseDto eliminar(Integer idAlumno) {
        BaseDto response = new BaseDto();
        try {
            alumnoService.eliminar(idAlumno);
        } catch (AppException ex) {
            String msg = "No se pudo eliminar el alumno. " + ex.getMessage();
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

}
