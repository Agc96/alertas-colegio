package com.colegio.alertas.controller;

import com.colegio.alertas.dto.AlumnoDto;
import com.colegio.alertas.dto.BusquedaDto;
import com.colegio.alertas.dto.BaseDto;
import com.colegio.alertas.dto.ResultadoDto;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.colegio.alertas.service.AlumnoService;
import com.colegio.alertas.service.UsuarioService;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.QueryUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Sistema de Alertas
 */
@Controller
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
    public ResultadoDto<AlumnoDto> buscar(@RequestBody BusquedaDto busqueda) {
        ResultadoDto<AlumnoDto> response = new ResultadoDto<>();
        try {
            response.setTotal(alumnoService.contar(busqueda));
            if (response.getTotal() > 0) {
                response.setLista(alumnoService.buscar(busqueda));
                QueryUtils.setNumPaginas(busqueda, response);
            }
        } catch (Exception ex) {
            String msg = "Hubo un error al buscar los alumnos. " + ex.getMessage();
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/admin/alumnos/guardar")
    @ResponseBody
    public BaseDto guardar(@RequestBody AlumnoDto request) {
        BaseDto response = new BaseDto();
        try {
            alumnoService.guardar(request);
        } catch (AppException ex) {
            String msg = "No se pudo registrar el alumno. " + ex.getMessage();
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/admin/alumnos/eliminar")
    @ResponseBody
    public BaseDto eliminar(@RequestParam Integer idAlumno) {
        BaseDto response = new BaseDto();
        try {
            alumnoService.eliminar(idAlumno);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "No se pudo eliminar el alumno.", ex);
            response.setError(true, "No se pudo eliminar el alumno. Asegúrese "
                    + "de que el alumno existe, y de que no haya sido incluido "
                    + "en aulas, incidencias, entrevistas, etc.");
        }
        return response;
    }

}
