package com.colegio.alertas.controller;

import com.colegio.alertas.dto.AsistenciaDto;
import com.colegio.alertas.dto.AulaDto;
import com.colegio.alertas.dto.BaseDto;
import com.colegio.alertas.dto.BusquedaAulaDto;
import com.colegio.alertas.dto.ResultadoDto;
import com.colegio.alertas.service.AsistenciaService;
import com.colegio.alertas.service.AulaService;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.QueryUtils;
import com.colegio.alertas.util.SecurityUtils;
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

    @GetMapping("/aulas/{idAula}/asistencia")
    public String listar(Model model, @PathVariable Integer idAula) {
        model.addAttribute("aula", validarAula(idAula));
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

    public AulaDto validarAula(Integer idAula) {
        AulaDto aula = aulaService.detalle(idAula, false);
        if (aula == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String docente = aula.getNombreUsuarioDocente();
        if (!docente.equals(SecurityUtils.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return aula;
    }

    @GetMapping("/aulas/{idAula}/asistencia/nuevo")
    public String nuevo(Model model, @PathVariable Integer idAula) {
        return registro(model, idAula);
    }

    @GetMapping("/aulas/{idAula}/asistencia/{idAsistencia}")
    public String editar(Model model, @PathVariable Integer idAula, @PathVariable Integer idAsistencia) {
        validarAsistencia(idAula, idAsistencia);
        model.addAttribute("idAsistencia", idAsistencia);
        return registro(model, idAula);
    }

    private String registro(Model model, Integer idAula) {
        model.addAttribute("aula", validarAula(idAula));
        model.addAttribute("estados", EstadoAsistencia.values());
        return "docente/asistencia/registro";
    }

    private void validarAsistencia(Integer idAula, Integer idAsistencia) {
        AsistenciaDto asistencia = asistenciaService.detalle(idAsistencia, false);
        if (asistencia == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!asistencia.getIdAula().equals(idAula)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
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
        } catch (Exception ex) {
            String msg = "Hubo un error desconocido y no se pudo guardar la asistencia.";
            LOG.log(Level.SEVERE, msg + " " + ex.getMessage(), ex);
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

}
