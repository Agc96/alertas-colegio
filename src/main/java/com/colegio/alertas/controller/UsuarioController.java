package com.colegio.alertas.controller;

import com.colegio.alertas.dto.UsuarioDto;
import com.colegio.alertas.dto.BusquedaDto;
import com.colegio.alertas.dto.BaseDto;
import com.colegio.alertas.dto.ResultadoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.colegio.alertas.service.UsuarioService;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.QueryUtils;
import com.colegio.alertas.util.enums.Rol;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Sistema de Alertas
 */
@Controller
public class UsuarioController {

    private static final Logger LOG = Logger.getLogger(UsuarioController.class.getName());

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/admin/usuarios")
    public String lista(Model model) {
        model.addAttribute("roles", Rol.values());
        return "admin/usuarios/lista";
    }

    @PostMapping("/admin/usuarios/buscar")
    @ResponseBody
    public ResultadoDto<UsuarioDto> buscar(@RequestBody BusquedaDto request) {
        ResultadoDto<UsuarioDto> response = new ResultadoDto<>();
        try {
            response.setTotal(usuarioService.contar(request));
            if (response.getTotal() > 0) {
                response.setLista(usuarioService.buscar(request));
                QueryUtils.setNumPaginas(request, response);
            }
        } catch (Exception ex) {
            String msg = "No se pudo encontrar la lista de usuarios.";
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/admin/usuarios/crear")
    @ResponseBody
    public BaseDto crear(@RequestBody UsuarioDto request) {
        BaseDto response = new BaseDto();
        try {
            usuarioService.crear(request);
        } catch (AppException ex) {
            String msg = "No se pudo registrar el usuario. " + ex.getMessage();
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/admin/usuarios/editar")
    @ResponseBody
    public BaseDto editar(@RequestBody UsuarioDto request) {
        BaseDto response = new BaseDto();
        try {
            usuarioService.editar(request);
        } catch (AppException ex) {
            String msg = "No se pudo registrar el usuario. " + ex.getMessage();
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/admin/usuarios/eliminar")
    @ResponseBody
    public BaseDto eliminar(@RequestParam String nombreUsuario) {
        BaseDto response = new BaseDto();
        try {
            usuarioService.eliminar(nombreUsuario);
        } catch (AppException ex) {
            LOG.log(Level.SEVERE, "No se pudo eliminar el usuario.", ex);
            response.setError(true, "No se pudo eliminar el usuario. Asegúrese "
                    + "de que el nombre de usuario existe y que los datos no "
                    + "estén siendo usados por otros elementos del sistema de "
                    + "alertas (alumnos, aulas, etc).");
        }
        return response;
    }

}
