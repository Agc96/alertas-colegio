package com.colegio.alertas.service;

import com.colegio.alertas.config.AppUserDetails;
import com.colegio.alertas.dto.UsuarioDto;
import com.colegio.alertas.dto.BusquedaDto;
import com.colegio.alertas.entity.Usuario;
import com.colegio.alertas.entity.UsuarioRol;
import com.colegio.alertas.repository.UsuarioRepository;
import com.colegio.alertas.repository.UsuarioRolRepository;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.Preconditions;
import com.colegio.alertas.util.QueryUtils;
import com.colegio.alertas.util.SecurityUtils;
import com.colegio.alertas.util.enums.Rol;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sistema de Alertas
 */
@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Integer contar(BusquedaDto busqueda) {
        return usuarioRepository.contar(busqueda.getTermino());
    }

    public List<UsuarioDto> buscar(BusquedaDto busqueda) {
        List<Usuario> usuarios = usuarioRepository.buscar(busqueda.getTermino(),
                QueryUtils.createPagination(busqueda));
        if (!Preconditions.isEmpty(usuarios)) {
            List<UsuarioDto> listaDto = new ArrayList<>(usuarios.size());
            for (Usuario usuario : usuarios) {
                UsuarioDto dto = new UsuarioDto();
                dto.setNombreUsuario(usuario.getNombreUsuario());
                dto.setDni(usuario.getDni());
                dto.setNombres(usuario.getNombres());
                dto.setApellidos(usuario.getApellidos());
                // Colocar datos del rol del usuario
                Rol rol = usuario.getUsuarioRol().getRol();
                dto.setIdRol(rol.getId());
                dto.setNombreRol(rol.getNombre());
                // Verificar si tiene o no los permisos para editar al usuario
                dto.setPuedeEditar(!SecurityUtils.getUsername().equals(usuario.getNombreUsuario()));
                // Añadir DTO del usuario a la lista
                listaDto.add(dto);
            }
            return listaDto;
        }
        return Collections.emptyList();
    }

    public void crear(UsuarioDto dto) throws AppException {
        Usuario usuario = new Usuario();
        validarYLlenar(usuario, dto);
        // Validar nombre de usuario
        String nombreUsuario = dto.getNombreUsuario();
        if (Preconditions.isEmpty(nombreUsuario)) {
            throw new AppException("Debe ingresar el nombre de usuario.");
        }
        Usuario existente = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (existente != null) {
            throw new AppException("El nombre de usuario ya está registrado.");
        }
        usuario.setNombreUsuario(nombreUsuario);
        // Validar contraseña del usuario
        String contrasenia = dto.getContrasenia();
        if (Preconditions.isEmpty(contrasenia)) {
            throw new AppException("Debe ingresar la contraseña del usuario.");
        }
        usuario.setContrasenia(passwordEncoder.encode(contrasenia));
        // Guardar el usuario en la base de datos
        usuarioRepository.save(usuario);
    }

    public void editar(UsuarioDto dto) throws AppException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(dto.getNombreUsuario());
        if (usuario == null) {
            throw new AppException("El usuario ingresado no existe.");
        }
        usuarioRolRepository.delete(usuario.getUsuarioRol());
        validarYLlenar(usuario, dto);
        usuarioRepository.save(usuario);
    }

    public void validarYLlenar(Usuario usuario, UsuarioDto dto) throws AppException {
        // Validar e ingresar el DNI del usuario
        String dni = dto.getDni();
        if (Preconditions.isEmpty(dni)) {
            throw new AppException("Debe ingresar el DNI del usuario.");
        }
        if (!Preconditions.isValidDni(dni)) {
            throw new AppException("El DNI debe tener exactamente 8 dígitos.");
        }
        usuario.setDni(dni);
        // Validar e ingresar los nombres del usuario
        String nombres = dto.getNombres();
        if (Preconditions.isEmpty(nombres)) {
            throw new AppException("Debe ingresar los nombres del usuario.");
        }
        usuario.setNombres(nombres);
        // Validar e ingresar los apellidos del usuario
        String apellidos = dto.getApellidos();
        if (Preconditions.isEmpty(apellidos)) {
            throw new AppException("Debe ingresar los apellidos del usuario.");
        }
        usuario.setApellidos(apellidos);
        // Validar e ingresar el tipo de usuario
        Rol rol = Rol.find(dto.getIdRol());
        if (rol == null) {
            throw new AppException("El tipo de usuario ingresado no existe.");
        }
        if (usuario.getUsuarioRol() == null) {
            usuario.setUsuarioRol(new UsuarioRol());
            usuario.getUsuarioRol().setUsuario(usuario);
        }
        usuario.getUsuarioRol().setRol(rol);
    }

    public void eliminar(String nombreUsuario) throws AppException {
        // Verificar los datos ingresados
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (usuario == null) {
            throw new AppException("El usuario ingresado no existe.");
        }
        // Eliminar el registro
        usuarioRolRepository.delete(usuario.getUsuarioRol());
        usuarioRepository.delete(usuario);
    }

    public List<UsuarioDto> listarDocentes() {
        return listar(Rol.DOCENTE);
    }

    public List<UsuarioDto> listarPadres() {
        return listar(Rol.PADRE);
    }

    public List<UsuarioDto> listar(Rol rol) {
        List<Usuario> usuarios = usuarioRepository.listarPorRol(rol);
        if (!Preconditions.isEmpty(usuarios)) {
            List<UsuarioDto> listaDto = new ArrayList<>(usuarios.size());
            for (Usuario usuario : usuarios) {
                UsuarioDto dto = new UsuarioDto();
                dto.setNombreUsuario(usuario.getNombreUsuario());
                dto.setDni(usuario.getDni());
                dto.setNombres(usuario.getNombres());
                dto.setApellidos(usuario.getApellidos());
                listaDto.add(dto);
            }
            return listaDto;
        }
        return Collections.emptyList();
    }

    @Override
    public AppUserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (usuario == null) {
            throw new UsernameNotFoundException(nombreUsuario);
        }
        return new AppUserDetails(usuario);
    }

}
