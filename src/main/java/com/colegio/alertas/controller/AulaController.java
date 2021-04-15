package com.colegio.alertas.controller;

import com.colegio.alertas.dto.AlumnoDto;
import com.colegio.alertas.dto.AulaAlumnoDto;
import com.colegio.alertas.dto.AulaDto;
import com.colegio.alertas.dto.BusquedaDto;
import com.colegio.alertas.dto.BaseDto;
import com.colegio.alertas.dto.BusquedaAulaDto;
import com.colegio.alertas.dto.ResultadoDto;
import com.colegio.alertas.service.AulaService;
import com.colegio.alertas.service.CommonService;
import com.colegio.alertas.service.UsuarioService;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.QueryUtils;
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
public class AulaController {

    private static final Logger LOG = Logger.getLogger(AulaController.class.getName());

    @Autowired
    private AulaService aulaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CommonService commonService;

    @GetMapping("/admin/aulas")
    public String listaAdmin() {
        return "admin/aulas/lista";
    }

    @PostMapping("/admin/aulas/buscar")
    @ResponseBody
    public ResultadoDto<AulaDto> buscar(@RequestBody BusquedaDto request) {
        ResultadoDto<AulaDto> response = new ResultadoDto<>();
        try {
            response.setTotal(aulaService.contarAdmin(request));
            if (response.getTotal() > 0) {
                response.setLista(aulaService.buscarAdmin(request));
                QueryUtils.setNumPaginas(request, response);
            }
        } catch (Exception ex) {
            String msg = "No se pudo encontrar la lista de aulas del administrador.";
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @GetMapping("/admin/aulas/nuevo")
    public String nuevo(Model model) {
        return registro(model);
    }

    @GetMapping("/admin/aulas/{idAula}")
    public String editar(Model model, @PathVariable Integer idAula) {
        // Verificar si existe el ID del aula
        if (!aulaService.existe(idAula)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        // Ir a la pantalla de registro de aulas
        model.addAttribute("idAula", idAula);
        return registro(model);
    }

    private String registro(Model model) {
        model.addAttribute("anios", commonService.listarAnios());
        model.addAttribute("grados", commonService.listarGrados());
        model.addAttribute("docentes", usuarioService.listarDocentes());
        return "admin/aulas/registro";
    }

    @PostMapping("/admin/aulas/detalle")
    @ResponseBody
    public AulaDto detalle(@RequestParam Integer idAula) {
        AulaDto aula = aulaService.detalle(idAula, true);
        return aula == null ? new AulaDto() : aula;
    }

    @PostMapping("/admin/aulas/guardar")
    @ResponseBody
    public BaseDto guardar(@RequestBody AulaDto dto) {
        BaseDto response = new BaseDto();
        try {
            aulaService.guardar(dto);
        } catch (AppException ex) {
            String msg = "No se pudo guardar el aula. " + ex.getMessage();
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/admin/aulas/eliminar")
    @ResponseBody
    public BaseDto eliminar(@RequestParam Integer idAula) {
        BaseDto response = new BaseDto();
        try {
            aulaService.eliminar(idAula);
        } catch (AppException ex) {
            LOG.log(Level.WARNING, "No se pudo eliminar el aula.", ex);
            response.setError(true, "No se pudo eliminar el aula. Asegúrese "
                    + "de que el ID del aula existe, y que los datos no estén "
                    + "siendo usados por otros elementos del sistema de "
                    + "alertas (comunicados, incidencias, entrevistas, etc).");
        }
        return response;
    }

    @GetMapping("/aulas")
    public String listaDocente() {
        return "docente/aulas/lista";
    }

    @PostMapping("/aulas/buscar")
    @ResponseBody
    public ResultadoDto<AulaDto> buscarDocente(@RequestBody BusquedaDto request) {
        ResultadoDto<AulaDto> response = new ResultadoDto<>();
        try {
            response.setTotal(aulaService.contarDocente(request));
            if (response.getTotal() > 0) {
                response.setLista(aulaService.buscarDocente(request));
                QueryUtils.setNumPaginas(request, response);
            }
        } catch (Exception ex) {
            String msg = "No se pudo encontrar la lista de aulas del docente.";
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @GetMapping("/aulas/{idAula}")
    public String detalleDocente(@PathVariable Integer idAula) {
        return "docente/aulas/detalle";
    }

    @PostMapping("/docente/alumnos/buscar")
    @ResponseBody
    public ResultadoDto<AlumnoDto> buscarAlumnos(@RequestBody BusquedaAulaDto busqueda) {
        ResultadoDto<AlumnoDto> response = new ResultadoDto<>();
        try {
            response.setTotal(aulaService.contarAlumnos(busqueda));
            if (response.getTotal() > 0) {
                response.setLista(aulaService.buscarAlumnos(busqueda));
                QueryUtils.setNumPaginas(busqueda, response);
            }
        } catch (Exception ex) {
            String msg = "Hubo un error al buscar los alumnos del aula. " + ex.getMessage();
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @GetMapping("/padre")
    public String listarAlumnosPadre() {
        return "padre/alumnos/lista";
    }

    @PostMapping("/padre/buscar")
    @ResponseBody
    public ResultadoDto<AulaAlumnoDto> buscarAlumnosPadre(@RequestBody BusquedaDto busqueda) {
        ResultadoDto<AulaAlumnoDto> response = new ResultadoDto<>();
        try {
            response.setTotal(aulaService.contarAlumnosPadre(busqueda));
            if (response.getTotal() > 0) {
                response.setLista(aulaService.buscarAlumnosPadre(busqueda));
                QueryUtils.setNumPaginas(busqueda, response);
            }
        } catch (Exception ex) {
            String msg = "Hubo un error al buscar los alumnos del padre de familia.";
            LOG.log(Level.SEVERE, msg + " " + ex.getMessage(), ex);
            response.setError(true, msg);
        }
        return response;
    }

}
