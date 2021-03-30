package com.colegio.alertas.service;

import com.colegio.alertas.dto.AlumnoDto;
import com.colegio.alertas.dto.BusquedaAulaDto;
import com.colegio.alertas.dto.BusquedaDto;
import com.colegio.alertas.entity.Alumno;
import com.colegio.alertas.entity.Usuario;
import com.colegio.alertas.repository.AlumnoRepository;
import com.colegio.alertas.repository.UsuarioRepository;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.DateUtils;
import com.colegio.alertas.util.Preconditions;
import com.colegio.alertas.util.QueryUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sistema de Alertas
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
                listaDto.add(convertir(alumno));
            }
            return listaDto;
        }
        return Collections.emptyList();
    }

    public void guardar(AlumnoDto dto) throws AppException {
        Alumno alumno = new Alumno();
        // Validar e ingresar el ID del alumno
        Integer idAlumno = dto.getIdAlumno();
        if (idAlumno != null) {
            alumno = alumnoRepository.findByIdAlumno(idAlumno);
            if (alumno == null) {
                throw new AppException("El alumno ingresado no existe.");
            }
        }
        // Validar e ingresar el DNI del alumno
        String dni = dto.getDni();
        if (Preconditions.isEmpty(dni)) {
            throw new AppException("Debe ingresar el DNI del alumno.");
        }
        if (!Preconditions.isValidDni(dni)) {
            throw new AppException("El DNI debe tener exactamente 8 dígitos.");
        }
        alumno.setDni(dni);
        // Validar e ingresar los nombres del alumno
        String nombres = dto.getNombres();
        if (Preconditions.isEmpty(nombres)) {
            throw new AppException("Debe ingresar los nombres del alumno.");
        }
        alumno.setNombres(nombres);
        // Validar e ingresar los apellidos del alumno
        String apellidos = dto.getApellidos();
        if (Preconditions.isEmpty(apellidos)) {
            throw new AppException("Debe ingresar los apellidos del alumno.");
        }
        alumno.setApellidos(apellidos);
        // Validar e ingresar la fecha de nacimiento del alumno
        Date fechaNacimiento = DateUtils.parse(dto.getFechaNacimiento());
        if (fechaNacimiento == null) {
            throw new AppException("La fecha de nacimiento ingresada no es válida.");
        }
        alumno.setFechaNacimiento(fechaNacimiento);
        // Validar e ingresar el padre de familia del alumno
        Usuario padre = usuarioRepository.findByNombreUsuario(dto.getNombreUsuarioPadre());
        if (padre == null) {
            throw new AppException("El padre de familia seleccionado no existe.");
        }
        alumno.setPadre(padre);
        // Guardar el alumno
        alumnoRepository.save(alumno);
    }

    public void eliminar(Integer idAlumno) throws AppException {
        Alumno alumno = alumnoRepository.findByIdAlumno(idAlumno);
        if (alumno == null) {
            throw new AppException("El alumno ingresado no existe.");
        }
        alumnoRepository.delete(alumno);
    }

    public AlumnoDto detalle(Integer idAlumno) {
        Alumno alumno = alumnoRepository.findByIdAlumno(idAlumno);
        return alumno == null ? null : convertir(alumno);
    }

    private AlumnoDto convertir(Alumno alumno) {
        AlumnoDto dto = new AlumnoDto();
        dto.setIdAlumno(alumno.getIdAlumno());
        dto.setNombres(alumno.getNombres());
        dto.setDni(alumno.getDni());
        dto.setApellidos(alumno.getApellidos());
        dto.setFechaNacimiento(DateUtils.format(alumno.getFechaNacimiento()));
        Usuario padre = alumno.getPadre();
        dto.setNombreUsuarioPadre(padre.getNombreUsuario());
        dto.setNombreCompletoPadre(padre.getNombres() + " " + padre.getApellidos());
        return dto;
    }

}
