package com.colegio.alertas.controller;

import com.colegio.alertas.dto.AsistenciaDto;
import com.colegio.alertas.dto.BaseDto;
import com.colegio.alertas.dto.BusquedaAulaDto;
import com.colegio.alertas.dto.DetalleAsistenciaDto;
import com.colegio.alertas.dto.ResultadoDto;
import com.colegio.alertas.service.AsistenciaService;
import com.colegio.alertas.service.AulaService;
import com.colegio.alertas.service.CommonService;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.QueryUtils;
import com.colegio.alertas.util.enums.EstadoAsistencia;
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
public class AsistenciaController {

    private static final Logger LOG = Logger.getLogger(AsistenciaController.class.getName());

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private AulaService aulaService;

    @Autowired
    private CommonService commonService;

    @GetMapping("/aulas/{idAula}/asistencia")
    public String listar(Model model, @PathVariable Integer idAula) {
        commonService.validarAula(model, idAula);
        return "docente/asistencia/lista";
    }

    @PostMapping("/docente/asistencia/buscar")
    @ResponseBody
    public ResultadoDto<AsistenciaDto> buscar(@RequestBody BusquedaAulaDto request) {
        ResultadoDto<AsistenciaDto> response = new ResultadoDto<>();
        try {
            response.setTotal(asistenciaService.contar(request));
            if (response.getTotal() > 0) {
                response.setLista(asistenciaService.buscar(request));
                QueryUtils.setNumPaginas(request, response);
            }
        } catch (Exception ex) {
            String msg = "No se pudo encontrar la lista de asistencias.";
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @GetMapping("/aulas/{idAula}/asistencia/nuevo")
    public String nuevo(Model model, @PathVariable Integer idAula) {
        return registro(model, idAula);
    }

    @GetMapping("/aulas/{idAula}/asistencia/{idAsistencia}")
    public String editar(Model model, @PathVariable Integer idAula, @PathVariable Integer idAsistencia) {
        AsistenciaDto asistencia = asistenciaService.detalle(idAsistencia, false);
        if (asistencia == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!asistencia.getIdAula().equals(idAula)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        model.addAttribute("idAsistencia", idAsistencia);
        return registro(model, idAula);
    }

    private String registro(Model model, Integer idAula) {
        commonService.validarAula(model, idAula);
        model.addAttribute("estados", EstadoAsistencia.values());
        return "docente/asistencia/registro";
    }

    @PostMapping("/docente/asistencia/guardar")
    @ResponseBody
    public BaseDto guardar(@RequestBody AsistenciaDto request) {
        BaseDto response = new BaseDto();
        try {
            asistenciaService.guardar(request);
        } catch (AppException ex) {
            String msg = "No se pudo guardar la asistencia. " + ex.getMessage();
            LOG.log(Level.WARNING, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/docente/asistencia/detalle")
    @ResponseBody
    public AsistenciaDto detalle(@RequestParam Integer idAsistencia) {
        AsistenciaDto asistencia = asistenciaService.detalle(idAsistencia, true);
        return asistencia == null ? new AsistenciaDto() : asistencia;
    }

    @PostMapping("/docente/asistencia/eliminar")
    @ResponseBody
    public BaseDto eliminar(@RequestParam Integer idAsistencia) {
        BaseDto response = new BaseDto();
        try {
            asistenciaService.eliminar(idAsistencia);
        } catch (AppException ex) {
            String msg = "No se pudo eliminar la asistencia. " + ex.getMessage();
            LOG.log(Level.WARNING, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @GetMapping("/padre/{idAlumno}/{idAula}/asistencia")
    public String listarPadre(Model model, @PathVariable Integer idAlumno,
            @PathVariable Integer idAula) {
        commonService.validarAulaAlumno(model, idAula, idAlumno);
        return "padre/asistencia/lista";
    }

    @PostMapping("/padre/asistencia/buscar")
    @ResponseBody
    public ResultadoDto<DetalleAsistenciaDto> buscarPadre(@RequestBody BusquedaAulaDto busqueda) {
        ResultadoDto<DetalleAsistenciaDto> response = new ResultadoDto<>();
        try {
            response.setTotal(asistenciaService.contarPadre(busqueda));
            if (response.getTotal() > 0) {
                response.setLista(asistenciaService.buscarPadre(busqueda));
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
