package com.colegio.alertas.controller;

import com.colegio.alertas.dto.AlumnoDto;
import com.colegio.alertas.dto.in.BusquedaDto;
import com.colegio.alertas.dto.in.FiltroDto;
import com.colegio.alertas.dto.out.ResultadoDto;
import com.colegio.alertas.entity.Alumno;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.colegio.alertas.repository.AlumnoDao;
import com.colegio.alertas.util.Preconditions;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;

/**
 *
 * @author Anthony Gutiérrez
 */
@Controller("")
public class AlumnoController {

    private static final Logger LOG = Logger.getLogger(AlumnoController.class.getName());

    @Autowired
    private AlumnoDao alumnoDao;

    @GetMapping("/admin/alumnos")
    public String lista() {
        return "admin/alumnos/lista";
    }

    @PostMapping("/admin/alumnos/busqueda")
    public ResultadoDto<AlumnoDto> busqueda(@RequestBody BusquedaDto busqueda) {
        ResultadoDto<AlumnoDto> response = new ResultadoDto<>();
        try {
            List<Alumno> alumnos = alumnoDao.filtrarAlumnos(busqueda.getTerm(),
                    PageRequest.of(busqueda.getPage(), busqueda.getSize()));
            if (!Preconditions.isEmpty(alumnos)) {
                response.setTotal(0);
            } else {
                response.setTotal(alumnos.size());
                response.setLista(new ArrayList<>(alumnos.size()));
                for (Alumno alumno : alumnos) {
                    AlumnoDto dto = new AlumnoDto();
                    dto.setIdAlumno(alumno.getIdAlumno());
                    dto.setNombres(alumno.getNombres());
                    dto.setDni(alumno.getDni());
                    dto.setApellidos(alumno.getApellidos());
                    dto.setFechaNacimiento(null); // TODO
                    response.getLista().add(dto);
                }
            }
        } catch (Exception ex) {
            String msg = "Hubo un error al buscar los alumnos. " + ex.getMessage();
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return null;
    }

    @PostMapping("/admin/alumnos/filtrar")
    @ResponseBody
    public ResultadoDto<AlumnoDto> filtrar(@RequestBody FiltroDto<AlumnoDto> request) {
        ResultadoDto<AlumnoDto> response = new ResultadoDto<>();
        try {
            response.setTotal(alumnoDao.contarFiltro(request.getFiltro()).intValue());
            if (response.getTotal() > 0) {
                response.setLista(alumnoDao.listarFiltro(request.getFiltro(),
                        request.getFirstResult(), request.getMaxResults()));
            }
        } catch (Exception ex) {
            String msg = "Hubo un error al filtrar los alumnos. " + ex.getMessage();
            LOG.log(Level.SEVERE, msg, ex);
            response.setError(true, msg);
        }
        return response;
    }

}
