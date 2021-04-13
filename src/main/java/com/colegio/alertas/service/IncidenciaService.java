package com.colegio.alertas.service;

import com.colegio.alertas.dto.BusquedaAulaDto;
import com.colegio.alertas.dto.IncidenciaDto;
import com.colegio.alertas.entity.Alumno;
import com.colegio.alertas.entity.Aula;
import com.colegio.alertas.entity.Incidencia;
import com.colegio.alertas.entity.Usuario;
import com.colegio.alertas.repository.AlumnoRepository;
import com.colegio.alertas.repository.AulaRepository;
import com.colegio.alertas.repository.IncidenciaRepository;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.DateUtils;
import com.colegio.alertas.util.DtoUtils;
import com.colegio.alertas.util.Preconditions;
import com.colegio.alertas.util.QueryUtils;
import com.colegio.alertas.util.enums.TipoWebPush;
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
public class IncidenciaService {

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private WebPushService webPushService;

    public Integer contar(BusquedaAulaDto busqueda) {
        return incidenciaRepository.contar(busqueda.getIdAula(), busqueda.getTermino());
    }

    public List<IncidenciaDto> buscar(BusquedaAulaDto busqueda) {
        return convertir(incidenciaRepository.buscar(busqueda.getIdAula(),
                busqueda.getTermino(), QueryUtils.createPagination(busqueda)));
    }

    private List<IncidenciaDto> convertir(List<Incidencia> incidencias) {
        if (!Preconditions.isEmpty(incidencias)) {
            List<IncidenciaDto> listaDto = new ArrayList<>(incidencias.size());
            for (Incidencia incidencia : incidencias) {
                IncidenciaDto dto = new IncidenciaDto();
                dto.setIdIncidencia(incidencia.getIdIncidencia());
                dto.setIdAula(incidencia.getAula().getIdAula());
                dto.setFecha(DateUtils.format(incidencia.getFecha()));
                Alumno alumno = incidencia.getAlumno();
                dto.setIdAlumno(alumno.getIdAlumno());
                dto.setDniAlumno(alumno.getDni());
                dto.setNombresAlumno(alumno.getNombres());
                dto.setApellidosAlumno(alumno.getApellidos());
                dto.setDescripcion(incidencia.getDescripcion());
                dto.setDescripcionHtml(DtoUtils.escapeHtml(incidencia.getDescripcion()));
                listaDto.add(dto);
            }
            return listaDto;
        }
        return Collections.emptyList();
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
        // Enviar notificación push
        if (idIncidencia == null) {
            webPushService.notificar(alumno.getPadre(), TipoWebPush.INCIDENCIA, alumno);
        }
    }

    public void eliminar(Integer idIncidencia) throws AppException {
        Incidencia incidencia = incidenciaRepository.findByIdIncidencia(idIncidencia);
        if (incidencia == null) {
            throw new AppException("La incidencia con el ID especificado no existe.");
        }
        incidenciaRepository.delete(incidencia);
    }

    public Integer contarPadre(BusquedaAulaDto busqueda) {
        return incidenciaRepository.contarPadre(busqueda.getIdAula(),
                busqueda.getIdAlumno(), busqueda.getTermino());
    }

    public List<IncidenciaDto> buscarPadre(BusquedaAulaDto busqueda) {
        return convertir(incidenciaRepository.buscarPadre(busqueda.getIdAula(),
                busqueda.getIdAlumno(), busqueda.getTermino(),
                QueryUtils.createPagination(busqueda)));
    }

}
