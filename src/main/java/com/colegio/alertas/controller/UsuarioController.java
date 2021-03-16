package com.colegio.alertas.controller;

import com.colegio.alertas.dto.UsuarioDto;
import com.colegio.alertas.dto.in.BusquedaDto;
import com.colegio.alertas.dto.out.BaseDto;
import com.colegio.alertas.dto.out.ResultadoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.colegio.alertas.service.UsuarioService;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.enums.TipoUsuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.ui.Model;

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
        model.addAttribute("tiposUsuario", TipoUsuario.values());
        return "admin/usuarios/lista";
    }

    @PostMapping("/admin/usuarios/buscar")
    @ResponseBody
    public ResultadoDto<UsuarioDto> buscar(BusquedaDto request) {
        ResultadoDto<UsuarioDto> response = new ResultadoDto<>();
        try {
            response.setTotal(usuarioService.contar(request));
            if (response.getTotal() > 0) {
                response.setLista(usuarioService.buscar(request));
            }
        } catch (Exception ex) {
            String msg = "No se pudo encontrar la lista de usuarios.";
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/admin/usuarios/registrar")
    @ResponseBody
    public BaseDto registrar(UsuarioDto request) {
        BaseDto response = new BaseDto();
        try {
            if (request.getIdUsuario() == null) {
                usuarioService.registrar(request);
            } else {
                usuarioService.editar(request);
            }
        } catch (AppException ex) {
            String msg = "No se pudo registrar el usuario. " + ex.getMessage();
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

    @PostMapping("/admin/usuarios/eliminar")
    @ResponseBody
    public BaseDto eliminar(Integer idUsuario) {
        BaseDto response = new BaseDto();
        try {
            usuarioService.eliminar(idUsuario);
        } catch (AppException ex) {
            String msg = "No se pudo eliminar el usuario. " + ex.getMessage();
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

}
