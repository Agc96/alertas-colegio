package com.colegio.alertas.service;

import com.colegio.alertas.dto.AsistenciaDto;
import com.colegio.alertas.dto.BusquedaAulaDto;
import com.colegio.alertas.dto.DetalleAsistenciaDto;
import com.colegio.alertas.entity.Alumno;
import com.colegio.alertas.entity.Asistencia;
import com.colegio.alertas.entity.Aula;
import com.colegio.alertas.entity.DetalleAsistencia;
import com.colegio.alertas.repository.AlumnoRepository;
import com.colegio.alertas.repository.AsistenciaRepository;
import com.colegio.alertas.repository.AulaRepository;
import com.colegio.alertas.repository.DetalleAsistenciaRepository;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.DateUtils;
import com.colegio.alertas.util.Preconditions;
import com.colegio.alertas.util.QueryUtils;
import com.colegio.alertas.util.enums.EstadoAsistencia;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sistema de Alertas
 */
@Service
public class AsistenciaService {

    private static final Logger LOG = Logger.getLogger(AsistenciaService.class.getName());

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private DetalleAsistenciaRepository detalleAsistenciaRepository;

    public Integer contar(BusquedaAulaDto busqueda) {
        return asistenciaRepository.contar(busqueda.getIdAula(), busqueda.getTermino());
    }

    public List<AsistenciaDto> buscar(BusquedaAulaDto busqueda) {
        List<Asistencia> asistencias = asistenciaRepository.buscar(busqueda.getIdAula(),
                busqueda.getTermino(), QueryUtils.createPagination(busqueda));
        if (!Preconditions.isEmpty(asistencias)) {
            List<AsistenciaDto> listaDto = new ArrayList<>(asistencias.size());
            for (Asistencia asistencia : asistencias) {
                AsistenciaDto dto = new AsistenciaDto();
                dto.setIdAsistencia(asistencia.getIdAsistencia());
                dto.setIdAula(asistencia.getAula().getIdAula());
                dto.setFecha(DateUtils.format(asistencia.getFecha()));
                dto.setNumAsistentes(detalleAsistenciaRepository.contarPorEstado(EstadoAsistencia.ASISTENTE));
                dto.setNumFaltos(detalleAsistenciaRepository.contarPorEstado(EstadoAsistencia.FALTA));
                dto.setNumJustificados(detalleAsistenciaRepository.contarPorEstado(EstadoAsistencia.JUSTIFICADO));
                listaDto.add(dto);
            }
            return listaDto;
        }
        return Collections.EMPTY_LIST;
    }

    public AsistenciaDto detalle(Integer idAsistencia, boolean colocarDetalles) {
        Asistencia asistencia = asistenciaRepository.findByIdAsistencia(idAsistencia);
        if (asistencia == null) return null;
        // Obtener datos principales
        AsistenciaDto asistenciaDto = new AsistenciaDto();
        asistenciaDto.setIdAsistencia(asistencia.getIdAsistencia());
        asistenciaDto.setIdAula(asistencia.getAula().getIdAula());
        asistenciaDto.setFecha(DateUtils.format(asistencia.getFecha()));
        // Obtener lista de detalles de asistencia
        if (colocarDetalles) {
            List<DetalleAsistencia> detalles = detalleAsistenciaRepository.listar(idAsistencia);
            if (!Preconditions.isEmpty(detalles)) {
                List<DetalleAsistenciaDto> listaDto = new ArrayList<>(detalles.size());
                for (DetalleAsistencia detalle : detalles) {
                    DetalleAsistenciaDto dto = new DetalleAsistenciaDto();
                    dto.setIdDetalleAsistencia(detalle.getIdDetalleAsistencia());
                    dto.setIdAsistencia(asistencia.getIdAsistencia());
                    dto.setIdAlumno(detalle.getAlumno().getIdAlumno());
                    dto.setIdEstadoAsistencia(detalle.getEstadoAsistencia().getId());
                    listaDto.add(dto);
                }
                asistenciaDto.setDetalles(listaDto);
            }
        }
        return asistenciaDto;
    }

    public void guardar(AsistenciaDto dto) throws AppException {
        Asistencia asistencia = new Asistencia();
        // Validar e ingresar el ID de la asistencia
        Integer idAsistencia = dto.getIdAsistencia();
        if (idAsistencia != null) {
            asistencia = asistenciaRepository.findByIdAsistencia(idAsistencia);
            if (asistencia == null) {
                throw new AppException("El registro de asistencia no existe.");
            }
        }
        // Validar e ingresar el ID del aula
        Aula aula = aulaRepository.findByIdAula(dto.getIdAula());
        if (aula == null) {
            throw new AppException("El aula de estudios seleccionado no es válido.");
        }
        asistencia.setAula(aula);
        // Validar e ingresar la fecha de la asistencia
        Date fecha = DateUtils.parse(dto.getFecha());
        if (fecha == null) {
            throw new AppException("La fecha ingresada no es válida.");
        }
        asistencia.setFecha(fecha);
        // Validar e ingresar los detalles de la asistencia
        List<DetalleAsistenciaDto> detallesDto = dto.getDetalles();
        if (Preconditions.isEmpty(detallesDto)) {
            throw new AppException("Falta colocar el estado de la asistencia a los alumnos.");
        }
        Integer totalAlumnos = aulaRepository.totalAlumnos(aula.getIdAula());
        if (detallesDto.size() != totalAlumnos) {
            throw new AppException("No se colocó el estado de asistencia a todos los alumnos.");
        }
        List<DetalleAsistencia> detalles = new ArrayList<>(detallesDto.size());
        for (DetalleAsistenciaDto detalleDto : detallesDto) {
            detalles.add(convertirDetalle(asistencia, detalleDto));
        }
        // Guardar la asistencia en la base de datos
        asistenciaRepository.save(asistencia);
        for (DetalleAsistencia detalle : detalles) {
            detalleAsistenciaRepository.save(detalle);
        }
    }

    private DetalleAsistencia convertirDetalle(Asistencia asistencia, DetalleAsistenciaDto dto) throws AppException {
        DetalleAsistencia detalle = new DetalleAsistencia();
        // Validar e ingresar el ID del detalle de asistencia
        Integer idDetalleAsistencia = dto.getIdDetalleAsistencia();
        if (idDetalleAsistencia != null) {
            detalle = detalleAsistenciaRepository.findByIdDetalleAsistencia(idDetalleAsistencia);
            if (detalle == null) {
                throw new AppException("No se pudo encontrar el detalle de asistencia con el ID " + idDetalleAsistencia + ".");
            }
        }
        detalle.setAsistencia(asistencia);
        // Validar e ingresar el alumno
        Integer idAlumno = dto.getIdAlumno();
        Alumno alumno = alumnoRepository.findByIdAlumno(idAlumno);
        if (alumno == null) {
            throw new AppException("No se pudo encontrar al alumno con el ID " + idAlumno + ".");
        }
        detalle.setAlumno(alumno);
        // Validar e ingresar el estado de la asistencia
        Integer idEstadoAsistencia = dto.getIdEstadoAsistencia();
        EstadoAsistencia estado = EstadoAsistencia.find(idEstadoAsistencia);
        if (estado == null) {
            throw new AppException("No se pudo encontrar al estado de asistencia con el ID " + idEstadoAsistencia + ".");
        }
        detalle.setEstadoAsistencia(estado);
        return detalle;
    }

}
