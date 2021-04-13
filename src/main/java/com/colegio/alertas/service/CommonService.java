package com.colegio.alertas.service;

import com.colegio.alertas.dto.AlumnoDto;
import com.colegio.alertas.dto.AulaDto;
import com.colegio.alertas.entity.Anio;
import com.colegio.alertas.entity.Grado;
import com.colegio.alertas.entity.Usuario;
import com.colegio.alertas.repository.AnioRepository;
import com.colegio.alertas.repository.GradoRepository;
import com.colegio.alertas.util.SecurityUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Sistema de Alertas
 */
@Service
public class CommonService {

    @Autowired
    private AnioRepository anioRepository;

    @Autowired
    private GradoRepository gradoRepository;

    @Autowired
    private AulaService aulaService;

    @Autowired
    private AlumnoService alumnoService;

    public List<Anio> listarAnios() {
        return anioRepository.findAll();
    }

    public List<Grado> listarGrados() {
        return gradoRepository.findAll();
    }

    public void validarAula(Model model, Integer idAula) {
        AulaDto aula = aulaService.detalle(idAula, false);
        if (aula == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String docente = aula.getNombreUsuarioDocente();
        if (!docente.equals(SecurityUtils.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        model.addAttribute("aula", aula);
    }

    public void validarAulaAlumno(Model model, Integer idAula, Integer idAlumno) {
        AulaDto aula = aulaService.detalle(idAula, false);
        AlumnoDto alumno = alumnoService.detalle(idAlumno);
        if (aula == null || alumno == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String padre = alumno.getNombreUsuarioPadre();
        if (!padre.equals(SecurityUtils.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        model.addAttribute("aula", aula);
        model.addAttribute("alumno", alumno);
    }

}
