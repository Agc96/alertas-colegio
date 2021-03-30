package com.colegio.alertas.controller;

import com.colegio.alertas.dto.BaseDto;
import com.colegio.alertas.dto.BusquedaAulaDto;
import com.colegio.alertas.dto.IncidenciaDto;
import com.colegio.alertas.dto.ResultadoDto;
import com.colegio.alertas.service.AulaService;
import com.colegio.alertas.service.CommonService;
import com.colegio.alertas.service.IncidenciaService;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.QueryUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Sistema de Alertas
 */
@Controller
public class IncidenciaController {

    private static final Logger LOG = Logger.getLogger(IncidenciaController.class.getName());

    @Autowired
    private IncidenciaService incidenciaService;

    @Autowired
    private AulaService aulaService;

    @Autowired
    private CommonService commonService;

    @GetMapping("/aulas/{idAula}/incidencias")
    public String listar(Model model, @PathVariable Integer idAula) {
        commonService.validarAula(model, idAula);
        model.addAttribute("alumnos", aulaService.listarAlumnos(idAula));
        return "docente/incidencias/lista";
    }

    @PostMapping("/docente/incidencias/buscar")
    @ResponseBody
    public ResultadoDto<IncidenciaDto> buscar(@RequestBody BusquedaAulaDto busqueda) {
        ResultadoDto<IncidenciaDto> response = new ResultadoDto<>();
        try {
            response.setTotal(incidenciaService.contar(busqueda));
            if (response.getTotal() > 0) {
                response.setLista(incidenciaService.buscar(busqueda));
                QueryUtils.setNumPaginas(busqueda, response);
            }
        } catch (Exception ex) {
            String msg = "Hubo un error desconocido al buscar las incidencias.";
            LOG.log(Level.SEVERE, msg + " " + ex.getMessage(), ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/docente/incidencias/guardar")
    @ResponseBody
    public BaseDto guardar(@RequestBody IncidenciaDto request) {
        BaseDto response = new BaseDto();
        try {
            incidenciaService.guardar(request);
        } catch (AppException ex) {
            String msg = "No se pudo guardar la incidencia. " + ex.getMessage();
            LOG.log(Level.WARNING, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/docente/incidencias/eliminar")
    @ResponseBody
    public BaseDto eliminar(@RequestParam Integer idIncidencia) {
        BaseDto response = new BaseDto();
        try {
            incidenciaService.eliminar(idIncidencia);
        } catch (AppException ex) {
            String msg = "No se pudo eliminar la incidencia. " + ex.getMessage();
            LOG.log(Level.WARNING, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @GetMapping("/padre/{idAlumno}/{idAula}/incidencias")
    public String listarPadre(Model model, @PathVariable Integer idAlumno,
            @PathVariable Integer idAula) {
        commonService.validarAulaAlumno(model, idAula, idAlumno);
        return "padre/incidencias/lista";
    }

    @PostMapping("/padre/incidencias/buscar")
    @ResponseBody
    public ResultadoDto<IncidenciaDto> buscarPadre(@RequestBody BusquedaAulaDto busqueda) {
        ResultadoDto<IncidenciaDto> response = new ResultadoDto<>();
        try {
            response.setTotal(incidenciaService.contarPadre(busqueda));
            if (response.getTotal() > 0) {
                response.setLista(incidenciaService.buscarPadre(busqueda));
                QueryUtils.setNumPaginas(busqueda, response);
            }
        } catch (Exception ex) {
            String msg = "Hubo un problema al buscar la lista de asistencias del alumno.";
            LOG.log(Level.SEVERE, msg + " " + ex.getMessage(), ex);
            response.setError(true, msg);
        }
        return response;
    }

}
