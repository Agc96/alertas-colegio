package com.colegio.alertas.service;

import com.colegio.alertas.dto.AlumnoDto;
import com.colegio.alertas.dto.in.BusquedaDto;
import com.colegio.alertas.entity.Alumno;
import com.colegio.alertas.entity.Usuario;
import com.colegio.alertas.repository.AlumnoRepository;
import com.colegio.alertas.repository.UsuarioRepository;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.DateUtils;
import com.colegio.alertas.util.Preconditions;
import com.colegio.alertas.util.QueryUtils;
import com.colegio.alertas.util.enums.TipoUsuario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Anthony Gutiérrez
 */
@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Integer contar(BusquedaDto busqueda) {
        return alumnoRepository.contar(busqueda.getTermino());
    }

    public List<AlumnoDto> buscar(BusquedaDto busqueda) {
        List<Alumno> alumnos = alumnoRepository.buscar(busqueda.getTermino(),
                    QueryUtils.createPagination(busqueda));
        if (!Preconditions.isEmpty(alumnos)) {
            List<AlumnoDto> listaDto = new ArrayList<>(alumnos.size());
            for (Alumno alumno : alumnos) {
                AlumnoDto dto = new AlumnoDto();
                dto.setIdAlumno(alumno.getIdAlumno());
                dto.setNombres(alumno.getNombres());
                dto.setDni(alumno.getDni());
                dto.setApellidos(alumno.getApellidos());
                dto.setFechaNacimiento(DateUtils.format(alumno.getFechaNacimiento()));
                Usuario padre = alumno.getPadre();
                if (padre != null) {
                    dto.setIdPadre(padre.getIdUsuario());
                    dto.setNombrePadre(padre.getNombres() + " " + padre.getApellidos());
                }
                listaDto.add(dto);
            }
            return listaDto;
        }
        return Collections.EMPTY_LIST;
    }

    public void registrar(AlumnoDto dto) throws AppException {
        // Verificar los datos ingresados
        Date fechaNacimiento = DateUtils.parse(dto.getFechaNacimiento());
        if (fechaNacimiento == null) {
            throw new AppException("La fecha de nacimiento ingresada no es válida.");
        }
        Usuario padre = usuarioRepository.findByIdUsuario(dto.getIdPadre());
        if (padre == null) {
            throw new AppException("El padre de familia seleccionado no existe.");
        }
        // Crear el registro y guardarlo en la base de datos
        Alumno alumno = new Alumno();
        alumno.setDni(dto.getDni());
        alumno.setNombres(dto.getNombres());
        alumno.setApellidos(dto.getApellidos());
        alumno.setFechaNacimiento(fechaNacimiento);
        alumno.setPadre(padre);
        alumnoRepository.save(alumno);
    }

    public void editar(AlumnoDto dto) throws AppException {
        // Verificar los datos ingresados
        Alumno alumno = alumnoRepository.findByIdAlumno(dto.getIdAlumno());
        if (alumno == null) {
            throw new AppException("El alumno ingresado no existe.");
        }
        Date fechaNacimiento = DateUtils.parse(dto.getFechaNacimiento());
        if (fechaNacimiento == null) {
            throw new AppException("La fecha de nacimiento ingresada no es válida.");
        }
        Usuario padre = usuarioRepository.findByIdUsuario(dto.getIdPadre());
        if (padre == null) {
            throw new AppException("El padre de familia seleccionado no existe.");
        }
        // Actualizar el registro y guardarlo en la base de datos
        alumno.setDni(dto.getDni());
        alumno.setNombres(dto.getNombres());
        alumno.setApellidos(dto.getApellidos());
        alumno.setFechaNacimiento(fechaNacimiento);
        alumno.setPadre(padre);
        alumnoRepository.save(alumno);
    }

    public void eliminar(Integer idAlumno) throws AppException {
        // Verificar los datos ingresados
        Alumno alumno = alumnoRepository.findByIdAlumno(idAlumno);
        if (alumno == null) {
            throw new AppException("El alumno ingresado no existe.");
        }
        // Eliminar el registro
        alumnoRepository.delete(alumno);
    }

}
