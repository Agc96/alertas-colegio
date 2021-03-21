package com.colegio.alertas.controller;

import com.colegio.alertas.dto.AlumnoDto;
import com.colegio.alertas.dto.AulaDto;
import com.colegio.alertas.dto.BaseDto;
import com.colegio.alertas.dto.BusquedaAulaDto;
import com.colegio.alertas.dto.IncidenciaDto;
import com.colegio.alertas.dto.ResultadoDto;
import com.colegio.alertas.service.AulaService;
import com.colegio.alertas.service.IncidenciaService;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.QueryUtils;
import com.colegio.alertas.util.SecurityUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/aulas/{idAula}/incidencias")
    public String listar(Model model, @PathVariable Integer idAula) {
        // Validar y colocar datos del aula
        AulaDto aula = aulaService.detalle(idAula, false);
        if (aula == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String docente = aula.getNombreUsuarioDocente();
        if (!docente.equals(SecurityUtils.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        model.addAttribute("aula", aula);
        // Validar y colocar datos de los alumnos del aula
        List<AlumnoDto> alumnos = aulaService.listarAlumnos(idAula);
        LOG.log(Level.INFO, "alumnos: {0}", alumnos.size());
        model.addAttribute("alumnos", alumnos);
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

}
