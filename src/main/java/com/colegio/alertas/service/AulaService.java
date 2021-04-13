package com.colegio.alertas.service;

import com.colegio.alertas.dto.AlumnoDto;
import com.colegio.alertas.dto.AulaAlumnoDto;
import com.colegio.alertas.dto.AulaDto;
import com.colegio.alertas.dto.BusquedaAulaDto;
import com.colegio.alertas.dto.BusquedaDto;
import com.colegio.alertas.entity.Alumno;
import com.colegio.alertas.entity.Anio;
import com.colegio.alertas.entity.Aula;
import com.colegio.alertas.entity.Grado;
import com.colegio.alertas.entity.Usuario;
import com.colegio.alertas.repository.AlumnoRepository;
import com.colegio.alertas.repository.AnioRepository;
import com.colegio.alertas.repository.AulaRepository;
import com.colegio.alertas.repository.GradoRepository;
import com.colegio.alertas.repository.UsuarioRepository;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.DtoUtils;
import com.colegio.alertas.util.Preconditions;
import com.colegio.alertas.util.QueryUtils;
import com.colegio.alertas.util.SecurityUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sistema de Alertas
 */
@Service
public class AulaService {

    private static final Logger LOG = Logger.getLogger(AulaService.class.getName());

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private AnioRepository anioRepository;

    @Autowired
    private GradoRepository gradoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    public Integer contarAdmin(BusquedaDto busqueda) {
        return aulaRepository.contarAdmin(busqueda.getTermino());
    }

    public List<AulaDto> buscarAdmin(BusquedaDto busqueda) {
        List<Aula> aulas = aulaRepository.buscarAdmin(busqueda.getTermino(),
                    QueryUtils.createPagination(busqueda));
        if (!Preconditions.isEmpty(aulas)) {
            List<AulaDto> listaDto = new ArrayList<>(aulas.size());
            for (Aula aula : aulas) {
                AulaDto dto = convertir(aula);
                dto.setNumAlumnos(aula.getAlumnos().size());
                listaDto.add(dto);
            }
            return listaDto;
        }
        return Collections.emptyList();
    }

    public boolean existe(Integer idAula) {
        return aulaRepository.existsById(idAula);
    }

    public AulaDto detalle(Integer idAula, boolean convertirAlumnos) {
        Aula aula = aulaRepository.findByIdAula(idAula);
        if (aula == null) return null;
        // Convertir datos del aula
        AulaDto dto = convertir(aula);
        // Convertir datos de los alumnos del aula
        if (convertirAlumnos) {
            Set<Alumno> alumnos = aula.getAlumnos();
            if (!Preconditions.isEmpty(alumnos)) {
                List<Integer> listaIds = new ArrayList<>(alumnos.size());
                for (Alumno alumno : alumnos) {
                    listaIds.add(alumno.getIdAlumno());
                }
                dto.setAlumnos(listaIds);
            }
        }
        return dto;
    }

    private AulaDto convertir(Aula aula) {
        AulaDto dto = new AulaDto();
        dto.setIdAula(aula.getIdAula());
        // Colocar datos del año de estudios
        Anio anio = aula.getAnio();
        dto.setIdAnio(anio.getIdAnio());
        // Colocar datos del grado de estudios
        Grado grado = aula.getGrado();
        dto.setIdGrado(grado.getIdGrado());
        dto.setNombreGrado(grado.getNombre());
        // Colocar datos del docente
        Usuario docente = aula.getDocente();
        dto.setNombreUsuarioDocente(docente.getNombreUsuario());
        dto.setNombreCompletoDocente(DtoUtils.obtenerNombreCompleto(docente));
        return dto;
    }

    public void guardar(AulaDto dto) throws AppException {
        Aula aula = new Aula();
        // Validar e ingresar el ID del aula
        Integer idAula = dto.getIdAula();
        if (idAula != null) {
            aula = aulaRepository.findByIdAula(idAula);
            if (aula == null) {
                throw new AppException("El ID del aula ingresado no existe.");
            }
        }
        // Validar e ingresar el año de estudios
        Anio anio = anioRepository.findByIdAnio(dto.getIdAnio());
        if (anio == null) {
            throw new AppException("El año de estudios seleccionado no existe.");
        }
        aula.setAnio(anio);
        // Validar e ingresar el grado de estudios
        Grado grado = gradoRepository.findByIdGrado(dto.getIdGrado());
        if (grado == null) {
            throw new AppException("El grado de estudios seleccionado no existe.");
        }
        aula.setGrado(grado);
        // Validar e ingresar el docente a cargo del aula
        Usuario docente = usuarioRepository.findByNombreUsuario(dto.getNombreUsuarioDocente());
        if (docente == null) {
            throw new AppException("El docente seleccionado no existe.");
        }
        aula.setDocente(docente);
        // Validar e ingresar la lista de alumnos
        List<Integer> listaIdAlumno = dto.getAlumnos();
        if (Preconditions.isEmpty(listaIdAlumno)) {
            throw new AppException("Debe seleccionar al menos un alumno.");
        }
        Set<Alumno> alumnos = new HashSet<>();
        for (Integer idAlumno : listaIdAlumno) {
            Alumno alumno = alumnoRepository.findByIdAlumno(idAlumno);
            if (alumno == null) {
                throw new AppException("El alumno con el ID " + idAlumno + " no existe.");
            }
            alumnos.add(alumno);
        }
        aula.setAlumnos(alumnos);
        // Guardar el aula en la base de datos
        aulaRepository.save(aula);
    }

    public Integer contarDocente(BusquedaDto busqueda) {
        return aulaRepository.contarDocente(SecurityUtils.getUsername(), busqueda.getTermino());
    }

    public List<AulaDto> buscarDocente(BusquedaDto busqueda) {
        List<Aula> aulas = aulaRepository.buscarDocente(SecurityUtils.getUsername(),
                    busqueda.getTermino(), QueryUtils.createPagination(busqueda));
        if (!Preconditions.isEmpty(aulas)) {
            List<AulaDto> listaDto = new ArrayList<>(aulas.size());
            for (Aula aula : aulas) {
                AulaDto dto = convertir(aula);
                dto.setNumAlumnos(aula.getAlumnos().size());
                listaDto.add(dto);
            }
            return listaDto;
        }
        return Collections.emptyList();
    }

    public Integer contarAlumnos(BusquedaAulaDto busqueda) {
        return aulaRepository.contarAlumnos(busqueda.getTermino(), busqueda.getIdAula());
    }

    public List<AlumnoDto> buscarAlumnos(BusquedaAulaDto busqueda) {
        List<Object[]> alumnos = aulaRepository.buscarAlumnos(busqueda.getTermino(),
                busqueda.getIdAula(), QueryUtils.createPagination(busqueda));
        return nativeQueryListToDto(alumnos);
    }

    public List<AlumnoDto> listarAlumnos(Integer idAula) {
        List<Object[]> alumnos = aulaRepository.listarAlumnos(idAula);
        return nativeQueryListToDto(alumnos);
    }

    private List<AlumnoDto> nativeQueryListToDto(List<Object[]> alumnos) {
        if (!Preconditions.isEmpty(alumnos)) {
            List<AlumnoDto> listaDto = new ArrayList<>(alumnos.size());
            for (Object[] alumno : alumnos) {
                AlumnoDto dto = new AlumnoDto();
                dto.setIdAlumno(QueryUtils.toInteger(alumno[0]));
                dto.setDni(QueryUtils.toString(alumno[1]));
                dto.setNombres(QueryUtils.toString(alumno[2]));
                dto.setApellidos(QueryUtils.toString(alumno[3]));
                listaDto.add(dto);
            }
            return listaDto;
        }
        return Collections.emptyList();
    }

    public void eliminar(Integer idAula) throws AppException {
        Aula aula = aulaRepository.findByIdAula(idAula);
        if (aula == null) {
            throw new AppException("El ID del aula ingresado no existe.");
        }
        aulaRepository.delete(aula);
    }

    public Integer contarAlumnosPadre(BusquedaDto busqueda) {
        return aulaRepository.contarAlumnosPadre(busqueda.getTermino(),
                SecurityUtils.getUsername());
    }

    public List<AulaAlumnoDto> buscarAlumnosPadre(BusquedaDto busqueda) {
        List<Object[]> alumnos = aulaRepository.buscarAlumnosPadre(busqueda.getTermino(),
                SecurityUtils.getUsername(), QueryUtils.createPagination(busqueda));
        if (!Preconditions.isEmpty(alumnos)) {
            List<AulaAlumnoDto> alumnosDto = new ArrayList<>(alumnos.size());
            for (Object[] alumno : alumnos) {
                AulaAlumnoDto alumnoDto = new AulaAlumnoDto();
                alumnoDto.setIdAlumno(QueryUtils.toInteger(alumno[0]));
                alumnoDto.setDniAlumno(QueryUtils.toString(alumno[1]));
                alumnoDto.setNombresAlumno(QueryUtils.toString(alumno[2]));
                alumnoDto.setApellidosAlumno(QueryUtils.toString(alumno[3]));
                alumnoDto.setIdAula(QueryUtils.toInteger(alumno[4]));
                alumnoDto.setIdAnio(QueryUtils.toInteger(alumno[5]));
                alumnoDto.setIdGrado(QueryUtils.toInteger(alumno[6]));
                alumnoDto.setNombreGrado(QueryUtils.toString(alumno[7]));
                alumnosDto.add(alumnoDto);
            }
            return alumnosDto;
        }
        return Collections.emptyList();
    }

}
