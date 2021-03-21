package com.colegio.alertas.service;

import com.colegio.alertas.dto.BusquedaAulaDto;
import com.colegio.alertas.dto.IncidenciaDto;
import com.colegio.alertas.dto.IncidenciaDto;
import com.colegio.alertas.entity.Alumno;
import com.colegio.alertas.entity.Aula;
import com.colegio.alertas.entity.Incidencia;
import com.colegio.alertas.entity.Incidencia;
import com.colegio.alertas.repository.AlumnoRepository;
import com.colegio.alertas.repository.AulaRepository;
import com.colegio.alertas.repository.IncidenciaRepository;
import com.colegio.alertas.repository.IncidenciaRepository;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.DateUtils;
import com.colegio.alertas.util.HtmlUtils;
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
 * @author Anthony Gutiérrez
 */
@Service
public class IncidenciaService {

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    public Integer contar(BusquedaAulaDto busqueda) {
        return incidenciaRepository.contar(busqueda.getIdAula(), busqueda.getTermino());
    }

    public List<IncidenciaDto> buscar(BusquedaAulaDto busqueda) {
        List<Incidencia> incidencias = incidenciaRepository.buscar(busqueda.getIdAula(),
                busqueda.getTermino(), QueryUtils.createPagination(busqueda));
        if (!Preconditions.isEmpty(incidencias)) {
            List<IncidenciaDto> listaDto = new ArrayList<>(incidencias.size());
            for (Incidencia incidencia : incidencias) {
                IncidenciaDto dto = new IncidenciaDto();
                dto.setIdIncidencia(incidencia.getIdIncidencia());
                dto.setIdAula(incidencia.getAula().getIdAula());
                dto.setFecha(DateUtils.format(incidencia.getFecha()));
                dto.setIdAlumno(incidencia.getAlumno().getIdAlumno());
                dto.setDniAlumno(incidencia.getAlumno().getDni());
                dto.setNombresAlumno(incidencia.getAlumno().getNombres());
                dto.setApellidosAlumno(incidencia.getAlumno().getApellidos());
                dto.setDescripcion(incidencia.getDescripcion());
                dto.setDescripcionHtml(HtmlUtils.escape(incidencia.getDescripcion()));
                listaDto.add(dto);
            }
            return listaDto;
        }
        return Collections.EMPTY_LIST;
    }

    public void guardar(IncidenciaDto dto) throws AppException {
        Incidencia incidencia = new Incidencia();
        // Validar e ingresar el ID de la incidencia
        Integer idIncidencia = dto.getIdIncidencia();
        if (idIncidencia != null) {
            incidencia = incidenciaRepository.findByIdIncidencia(idIncidencia);
            if (incidencia == null) {
                throw new AppException("La incidencia con el ID especificado no existe.");
            }
        }
        // Validar e ingresar el ID del aula
        Aula aula = aulaRepository.findByIdAula(dto.getIdAula());
        if (aula == null) {
            throw new AppException("El aula de estudios seleccionado no es válido.");
        }
        incidencia.setAula(aula);
        // Validar e ingresar la fecha de la incidencia
        Date fecha = DateUtils.parse(dto.getFecha());
        if (fecha == null) {
            throw new AppException("La fecha ingresada no es válida.");
        }
        incidencia.setFecha(fecha);
        // Validar e ingresar el ID del alumno
        Alumno alumno = alumnoRepository.findByIdAlumno(dto.getIdAlumno());
        if (alumno == null) {
            throw new AppException("El alumno seleccionado no es válido.");
        }
        incidencia.setAlumno(alumno);
        // Validar e ingresar la descripción de la incidencia
        String descripcion = dto.getDescripcion();
        if (Preconditions.isEmpty(descripcion)) {
            throw new AppException("Debe ingresar un título para el incidencia.");
        }
        incidencia.setDescripcion(descripcion);
        // Guardar la incidencia en la base de datos
        incidenciaRepository.save(incidencia);
    }

    public void eliminar(Integer idIncidencia) throws AppException {
        Incidencia incidencia = incidenciaRepository.findByIdIncidencia(idIncidencia);
        if (incidencia == null) {
            throw new AppException("La incidencia con el ID especificado no existe.");
        }
        incidenciaRepository.delete(incidencia);
    }

}