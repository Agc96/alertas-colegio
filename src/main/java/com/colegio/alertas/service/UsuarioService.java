package com.colegio.alertas.service;

import com.colegio.alertas.dto.UsuarioDto;
import com.colegio.alertas.dto.in.BusquedaDto;
import com.colegio.alertas.entity.Usuario;
import com.colegio.alertas.repository.UsuarioRepository;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.Preconditions;
import com.colegio.alertas.util.QueryUtils;
import com.colegio.alertas.util.enums.TipoUsuario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Anthony Gutiérrez
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Integer contar(BusquedaDto busqueda) {
        return usuarioRepository.contar(busqueda.getTermino());
    }

    public List<UsuarioDto> buscar(BusquedaDto busqueda) {
        List<Usuario> listaBd = usuarioRepository.buscar(busqueda.getTermino(),
                QueryUtils.createPagination(busqueda));
        if (!Preconditions.isEmpty(listaBd)) {
            List<UsuarioDto> listaDto = new ArrayList<>(listaBd.size());
            for (Usuario usuario : listaBd) {
                UsuarioDto dto = new UsuarioDto();
                dto.setIdUsuario(usuario.getIdUsuario());
                dto.setDni(usuario.getDni());
                dto.setNombres(usuario.getNombres());
                dto.setApellidos(usuario.getApellidos());
                dto.setNombreUsuario(usuario.getNombreUsuario());
                TipoUsuario tipoUsuario = TipoUsuario.find(usuario.getIdTipoUsuario());
                if (tipoUsuario != null) {
                    dto.setIdTipoUsuario(tipoUsuario.getId());
                    dto.setNombreTipoUsuario(tipoUsuario.getNombre());
                }
                listaDto.add(dto);
            }
            return listaDto;
        }
        return Collections.EMPTY_LIST;
    }

    public void registrar(UsuarioDto dto) throws AppException {
        // Verificar los datos ingresados
        if (usuarioRepository.findByNombreUsuario(dto.getNombreUsuario()) != null) {
            throw new AppException("El nombre de usuario ya está registrado.");
        }
        if (TipoUsuario.find(dto.getIdTipoUsuario()) == null) {
            throw new AppException("El tipo de usuario ingresado no existe.");
        }
        // Crear el registro y guardarlo en la base de datos
        Usuario usuario = new Usuario();
        usuario.setDni(dto.getDni());
        usuario.setNombres(dto.getNombres());
        usuario.setApellidos(dto.getApellidos());
        usuario.setIdTipoUsuario(dto.getIdTipoUsuario());
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
        usuarioRepository.save(usuario);
    }

    public void editar(UsuarioDto dto) throws AppException {
        // Verificar los datos ingresados
        Usuario usuario = usuarioRepository.findByIdUsuario(dto.getIdUsuario());
        if (usuario == null) {
            throw new AppException("El usuario ingresado no existe.");
        }
        if (TipoUsuario.find(dto.getIdTipoUsuario()) == null) {
            throw new AppException("El tipo de usuario ingresado no existe.");
        }
        // Actualizar el registro y guardarlo en la base de datos
        usuario.setDni(dto.getDni());
        usuario.setNombres(dto.getNombres());
        usuario.setApellidos(dto.getApellidos());
        usuario.setIdTipoUsuario(dto.getIdTipoUsuario());
        usuarioRepository.save(usuario);
    }

    public void eliminar(Integer idUsuario) throws AppException {
        // Verificar los datos ingresados
        Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario);
        if (usuario == null) {
            throw new AppException("El usuario ingresado no existe.");
        }
        // Eliminar el registro
        usuarioRepository.delete(usuario);
    }

    public List<UsuarioDto> listarDocentes() {
        return listar(TipoUsuario.DOCENTE.getId());
    }

    public List<UsuarioDto> listarPadres() {
        return listar(TipoUsuario.PADRE.getId());
    }

    public List<UsuarioDto> listar(Integer idTipoUsuario) {
        List<Usuario> usuarios = usuarioRepository.findAllByIdTipoUsuario(idTipoUsuario);
        if (!Preconditions.isEmpty(usuarios)) {
            List<UsuarioDto> listaDto = new ArrayList<>(usuarios.size());
            for (Usuario usuario : usuarios) {
                UsuarioDto dto = new UsuarioDto();
                dto.setIdUsuario(usuario.getIdUsuario());
                dto.setDni(usuario.getDni());
                dto.setNombres(usuario.getNombres());
                dto.setApellidos(usuario.getApellidos());
                listaDto.add(dto);
            }
            return listaDto;
        }
        return Collections.EMPTY_LIST;
    }

}
