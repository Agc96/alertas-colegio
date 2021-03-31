package com.colegio.alertas.service;

import com.colegio.alertas.dto.BusquedaAulaDto;
import com.colegio.alertas.dto.EntrevistaDto;
import com.colegio.alertas.entity.Alumno;
import com.colegio.alertas.entity.Aula;
import com.colegio.alertas.entity.Entrevista;
import com.colegio.alertas.entity.Usuario;
import com.colegio.alertas.repository.AlumnoRepository;
import com.colegio.alertas.repository.AulaRepository;
import com.colegio.alertas.repository.EntrevistaRepository;
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
public class EntrevistaService {

    @Autowired
    private EntrevistaRepository entrevistaRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    public Integer contarPadre(BusquedaAulaDto busqueda) {
        return entrevistaRepository.contarPadre(busqueda.getIdAula(),
                busqueda.getIdAlumno(), busqueda.getTermino());
    }

    public List<EntrevistaDto> buscarPadre(BusquedaAulaDto busqueda) {
        List<Entrevista> entrevistas = entrevistaRepository.buscarPadre(busqueda.getIdAula(),
                busqueda.getIdAlumno(), busqueda.getTermino(), QueryUtils.createPagination(busqueda));
        if (!Preconditions.isEmpty(entrevistas)) {
            List<EntrevistaDto> listaDto = new ArrayList<>(entrevistas.size());
            for (Entrevista entrevista : entrevistas) {
                EntrevistaDto dto = new EntrevistaDto();
                dto.setIdEntrevista(entrevista.getIdEntrevista());
                dto.setFecha(DateUtils.format(entrevista.getFecha()));
                dto.setMotivo(entrevista.getMotivo());
                dto.setMotivoHtml(HtmlUtils.escape(entrevista.getMotivo()));
                listaDto.add(dto);
            }
            return listaDto;
        }
        return Collections.emptyList();
    }

    public void guardar(EntrevistaDto dto) throws AppException {
        Entrevista entrevista = new Entrevista();
        // Validar e ingresar el ID de la entrevista
        Integer idEntrevista = dto.getIdEntrevista();
        if (idEntrevista != null) {
            entrevista = entrevistaRepository.findByIdEntrevista(idEntrevista);
            if (entrevista == null) {
                throw new AppException("La entrevista con el ID especificado no existe.");
            }
        }
        // Validar e ingresar el ID del aula
        Aula aula = aulaRepository.findByIdAula(dto.getIdAula());
        if (aula == null) {
            throw new AppException("El aula de estudios seleccionado no es válido.");
        }
        entrevista.setAula(aula);
        // Validar e ingresar el ID del alumno
        Alumno alumno = alumnoRepository.findByIdAlumno(dto.getIdAlumno());
        if (alumno == null) {
            throw new AppException("El alumno seleccionado no es válido.");
        }
        entrevista.setAlumno(alumno);
        // Validar e ingresar la fecha del entrevista
        Date fecha = DateUtils.parse(dto.getFecha());
        if (fecha == null) {
            throw new AppException("La fecha ingresada está vacía o no es válida.");
        }
        entrevista.setFecha(fecha);
        // Validar e ingresar el motivo de la entrevista
        String motivo = dto.getMotivo();
        if (Preconditions.isEmpty(motivo)) {
            throw new AppException("Debe ingresar un motivo para el entrevista.");
        }
        entrevista.setMotivo(motivo);
        // Guardar la entrevista en la base de datos
        entrevistaRepository.save(entrevista);
    }

    public void eliminar(Integer idEntrevista) throws AppException {
        Entrevista entrevista = entrevistaRepository.findByIdEntrevista(idEntrevista);
        if (entrevista == null) {
            throw new AppException("La entrevista con el ID especificado no existe.");
        }
        entrevistaRepository.delete(entrevista);
    }

    public Integer contarDocente(BusquedaAulaDto busqueda) {
        return entrevistaRepository.contarDocente(busqueda.getIdAula(), busqueda.getTermino());
    }

    public List<EntrevistaDto> buscarDocente(BusquedaAulaDto busqueda) {
        List<Entrevista> entrevistas = entrevistaRepository.buscarDocente(busqueda.getIdAula(),
                busqueda.getTermino(), QueryUtils.createPagination(busqueda));
        if (!Preconditions.isEmpty(entrevistas)) {
            List<EntrevistaDto> listaDto = new ArrayList<>(entrevistas.size());
            for (Entrevista entrevista : entrevistas) {
                EntrevistaDto dto = new EntrevistaDto();
                dto.setIdEntrevista(entrevista.getIdEntrevista());
                Alumno alumno = entrevista.getAlumno();
                dto.setIdAlumno(alumno.getIdAlumno());
                dto.setDniAlumno(alumno.getDni());
                dto.setNombresAlumno(alumno.getNombres());
                dto.setApellidosAlumno(alumno.getApellidos());
                Usuario padre = alumno.getPadre();
                dto.setNombreUsuarioPadre(padre.getNombreUsuario());
                dto.setNombresPadre(padre.getNombres());
                dto.setApellidosPadre(padre.getApellidos());
                dto.setIdAula(entrevista.getAula().getIdAula());
                dto.setFecha(DateUtils.format(entrevista.getFecha()));
                dto.setMotivo(entrevista.getMotivo());
                dto.setMotivoHtml(HtmlUtils.escape(entrevista.getMotivo()));
                listaDto.add(dto);
            }
            return listaDto;
        }
        return Collections.emptyList();
    }

}
