package com.colegio.alertas.controller;

import com.colegio.alertas.dto.BaseDto;
import com.colegio.alertas.dto.BusquedaAulaDto;
import com.colegio.alertas.dto.ComunicadoDto;
import com.colegio.alertas.dto.ResultadoDto;
import com.colegio.alertas.service.CommonService;
import com.colegio.alertas.service.ComunicadoService;
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
public class ComunicadoController {

    private static final Logger LOG = Logger.getLogger(ComunicadoController.class.getName());

    @Autowired
    private ComunicadoService comunicadoService;

    @Autowired
    private CommonService commonService;

    @GetMapping("/aulas/{idAula}/comunicados")
    public String listar(Model model, @PathVariable Integer idAula) {
        commonService.validarAula(model, idAula);
        return "docente/comunicados/lista";
    }

    @PostMapping("/docente/comunicados/buscar")
    @ResponseBody
    public ResultadoDto<ComunicadoDto> buscar(@RequestBody BusquedaAulaDto busqueda) {
        ResultadoDto<ComunicadoDto> response = new ResultadoDto<>();
        try {
            response.setTotal(comunicadoService.contar(busqueda));
            if (response.getTotal() > 0) {
                response.setLista(comunicadoService.buscar(busqueda));
                QueryUtils.setNumPaginas(busqueda, response);
            }
        } catch (Exception ex) {
            String msg = "Hubo un error desconocido al buscar los comunicados.";
            LOG.log(Level.SEVERE, msg + " " + ex.getMessage(), ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/docente/comunicados/guardar")
    @ResponseBody
    public BaseDto guardar(@RequestBody ComunicadoDto request) {
        BaseDto response = new BaseDto();
        try {
            comunicadoService.guardar(request);
        } catch (AppException ex) {
            String msg = "No se pudo registrar el comunicado. " + ex.getMessage();
            LOG.log(Level.WARNING, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/docente/comunicados/eliminar")
    @ResponseBody
    public BaseDto eliminar(@RequestParam Integer idComunicado) {
        BaseDto response = new BaseDto();
        try {
            comunicadoService.eliminar(idComunicado);
        } catch (AppException ex) {
            String msg = "No se pudo eliminar el comunicado. " + ex.getMessage();
            LOG.log(Level.WARNING, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @GetMapping("/padre/{idAlumno}/{idAula}/comunicados")
    public String listarPadre(Model model, @PathVariable Integer idAlumno,
            @PathVariable Integer idAula) {
        commonService.validarAulaAlumno(model, idAula, idAlumno);
        return "padre/comunicados/lista";
    }

    @PostMapping("/padre/comunicados/buscar")
    @ResponseBody
    public ResultadoDto<ComunicadoDto> buscarPadre(@RequestBody BusquedaAulaDto busqueda) {
        return buscar(busqueda);
    }

}
