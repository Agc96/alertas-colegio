package com.colegio.alertas.dto;

import java.util.List;

/**
 *
 * @author Sistema de Alertas
 */
public class AsistenciaDto extends BaseDto {

    private static final long serialVersionUID = -2632001550099953996L;

    private Integer idAsistencia;
    private Integer idAula;
    private String fecha;
    private Integer numAsistentes;
    private Integer numFaltos;
    private Integer numJustificados;
    private List<DetalleAsistenciaDto> detalles;

    public Integer getIdAsistencia() {
        return idAsistencia;
    }
    public void setIdAsistencia(Integer idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public Integer getIdAula() {
        return idAula;
    }
    public void setIdAula(Integer idAula) {
        this.idAula = idAula;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getNumAsistentes() {
        return numAsistentes;
    }
    public void setNumAsistentes(Integer numAsistentes) {
        this.numAsistentes = numAsistentes;
    }

    public Integer getNumFaltos() {
        return numFaltos;
    }
    public void setNumFaltos(Integer numFaltos) {
        this.numFaltos = numFaltos;
    }

    public Integer getNumJustificados() {
        return numJustificados;
    }
    public void setNumJustificados(Integer numJustificados) {
        this.numJustificados = numJustificados;
    }

    public List<DetalleAsistenciaDto> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetalleAsistenciaDto> detalles) {
        this.detalles = detalles;
    }

}
