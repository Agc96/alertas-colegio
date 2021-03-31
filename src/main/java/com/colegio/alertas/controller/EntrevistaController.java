package com.colegio.alertas.controller;

import com.colegio.alertas.dto.BaseDto;
import com.colegio.alertas.dto.BusquedaAulaDto;
import com.colegio.alertas.dto.EntrevistaDto;
import com.colegio.alertas.dto.ResultadoDto;
import com.colegio.alertas.service.CommonService;
import com.colegio.alertas.service.EntrevistaService;
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
 * @author Anthony Gutiérrez
 */
@Controller
public class EntrevistaController {

    private static final Logger LOG = Logger.getLogger(EntrevistaController.class.getName());

    @Autowired
    private CommonService commonService;

    @Autowired
    private EntrevistaService entrevistaService;

    @GetMapping("/padre/{idAlumno}/{idAula}/entrevistas")
    public String listarPadre(Model model, @PathVariable Integer idAlumno,
            @PathVariable Integer idAula) {
        commonService.validarAulaAlumno(model, idAula, idAlumno);
        return "padre/entrevistas/lista";
    }

    @PostMapping("/padre/entrevistas/buscar")
    @ResponseBody
    public ResultadoDto<EntrevistaDto> buscarPadre(@RequestBody BusquedaAulaDto busqueda) {
        ResultadoDto<EntrevistaDto> response = new ResultadoDto<>();
        try {
            response.setTotal(entrevistaService.contarPadre(busqueda));
            if (response.getTotal() > 0) {
                response.setLista(entrevistaService.buscarPadre(busqueda));
                QueryUtils.setNumPaginas(busqueda, response);
            }
        } catch (Exception ex) {
            String msg = "Hubo un error desconocido al buscar las entrevistas para el padre.";
            LOG.log(Level.SEVERE, msg + " " + ex.getMessage(), ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/padre/entrevistas/guardar")
    @ResponseBody
    public BaseDto guardar(@RequestBody EntrevistaDto request) {
        BaseDto response = new BaseDto();
        try {
            entrevistaService.guardar(request);
        } catch (AppException ex) {
            String msg = "No se pudo registrar la entrevista. " + ex.getMessage();
            LOG.log(Level.WARNING, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/padre/entrevistas/eliminar")
    @ResponseBody
    public BaseDto eliminar(@RequestParam Integer idEntrevista) {
        BaseDto response = new BaseDto();
        try {
            entrevistaService.eliminar(idEntrevista);
        } catch (AppException ex) {
            String msg = "No se pudo eliminar la entrevista. " + ex.getMessage();
            LOG.log(Level.WARNING, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @GetMapping("/aulas/{idAula}/entrevistas")
    public String listarDocente(Model model, @PathVariable Integer idAula) {
        commonService.validarAula(model, idAula);
        return "docente/entrevistas/lista";
    }

    @PostMapping("/docente/entrevistas/buscar")
    @ResponseBody
    public ResultadoDto<EntrevistaDto> buscarDocente(@RequestBody BusquedaAulaDto busqueda) {
        ResultadoDto<EntrevistaDto> response = new ResultadoDto<>();
        try {
            response.setTotal(entrevistaService.contarDocente(busqueda));
            if (response.getTotal() > 0) {
                response.setLista(entrevistaService.buscarDocente(busqueda));
                QueryUtils.setNumPaginas(busqueda, response);
            }
        } catch (Exception ex) {
            String msg = "Hubo un error desconocido al buscar las entrevistas para el docente.";
            LOG.log(Level.SEVERE, msg + " " + ex.getMessage(), ex);
            response.setError(true, msg);
        }
        return response;
    }

}
