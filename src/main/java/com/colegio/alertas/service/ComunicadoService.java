package com.colegio.alertas.service;

import com.colegio.alertas.dto.BusquedaAulaDto;
import com.colegio.alertas.dto.ComunicadoDto;
import com.colegio.alertas.entity.Aula;
import com.colegio.alertas.entity.Comunicado;
import com.colegio.alertas.repository.AulaRepository;
import com.colegio.alertas.repository.ComunicadoRepository;
import com.colegio.alertas.util.AppException;
import com.colegio.alertas.util.DateUtils;
import com.colegio.alertas.util.HtmlUtils;
import com.colegio.alertas.util.Preconditions;
import com.colegio.alertas.util.QueryUtils;
import com.colegio.alertas.util.enums.EstadoAsistencia;
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
public class ComunicadoService {

    @Autowired
    private ComunicadoRepository comunicadoRepository;

    @Autowired
    private AulaRepository aulaRepository;

    public Integer contar(BusquedaAulaDto busqueda) {
        return comunicadoRepository.contar(busqueda.getIdAula(), busqueda.getTermino());
    }

    public List<ComunicadoDto> buscar(BusquedaAulaDto busqueda) {
        List<Comunicado> comunicados = comunicadoRepository.buscar(busqueda.getIdAula(),
                busqueda.getTermino(), QueryUtils.createPagination(busqueda));
        if (!Preconditions.isEmpty(comunicados)) {
            List<ComunicadoDto> listaDto = new ArrayList<>(comunicados.size());
            for (Comunicado comunicado : comunicados) {
                ComunicadoDto dto = new ComunicadoDto();
                dto.setIdComunicado(comunicado.getIdComunicado());
                dto.setIdAula(comunicado.getAula().getIdAula());
                dto.setTitulo(comunicado.getTitulo());
                dto.setFecha(DateUtils.format(comunicado.getFecha()));
                dto.setDescripcion(comunicado.getDescripcion());
                dto.setDescripcionHtml(HtmlUtils.escape(comunicado.getDescripcion()));
                listaDto.add(dto);
            }
            return listaDto;
        }
        return Collections.emptyList();
    }

    public void guardar(ComunicadoDto dto) throws AppException {
        Comunicado comunicado = new Comunicado();
        // Validar e ingresar el ID del comunicado
        Integer idComunicado = dto.getIdComunicado();
        if (idComunicado != null) {
            comunicado = comunicadoRepository.findByIdComunicado(idComunicado);
            if (comunicado == null) {
                throw new AppException("El comunicado con el ID especificado no existe.");
            }
        }
        // Validar e ingresar el ID del aula
        Aula aula = aulaRepository.findByIdAula(dto.getIdAula());
        if (aula == null) {
            throw new AppException("El aula de estudios seleccionado no es válido.");
        }
        comunicado.setAula(aula);
        // Validar e ingresar el título del comunicado
        String titulo = dto.getTitulo();
        if (Preconditions.isEmpty(titulo)) {
            throw new AppException("Debe ingresar un título para el comunicado.");
        }
        comunicado.setTitulo(titulo);
        // Validar e ingresar la fecha del comunicado
        Date fecha = DateUtils.parse(dto.getFecha());
        if (fecha == null) {
            throw new AppException("La fecha ingresada no es válida.");
        }
        comunicado.setFecha(fecha);
        // Validar e ingresar la descripción del comunicado
        String descripcion = dto.getDescripcion();
        if (Preconditions.isEmpty(descripcion)) {
            throw new AppException("Debe ingresar un título para el comunicado.");
        }
        comunicado.setDescripcion(descripcion);
        // Guardar el comunicado en la base de datos
        comunicadoRepository.save(comunicado);
    }

    public void eliminar(Integer idComunicado) throws AppException {
        Comunicado comunicado = comunicadoRepository.findByIdComunicado(idComunicado);
        if (comunicado == null) {
            throw new AppException("El comunicado con el ID especificado no existe.");
        }
        comunicadoRepository.delete(comunicado);
    }

}
