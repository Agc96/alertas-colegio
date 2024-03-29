package com.colegio.alertas.dto;

/**
 *
 * @author Sistema de Alertas
 */
public class DetalleAsistenciaDto extends BaseDto {

    private static final long serialVersionUID = 2262641607221732698L;

    private Integer idDetalleAsistencia;
    private Integer idAsistencia;
    private Integer idAlumno;
    private String fecha;
    private Integer idEstadoAsistencia;
    private String nombreEstadoAsistencia;

    public Integer getIdDetalleAsistencia() {
        return idDetalleAsistencia;
    }
    public void setIdDetalleAsistencia(Integer idDetalleAsistencia) {
        this.idDetalleAsistencia = idDetalleAsistencia;
    }

    public Integer getIdAsistencia() {
        return idAsistencia;
    }
    public void setIdAsistencia(Integer idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }
    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getIdEstadoAsistencia() {
        return idEstadoAsistencia;
    }
    public void setIdEstadoAsistencia(Integer idEstadoAsistencia) {
        this.idEstadoAsistencia = idEstadoAsistencia;
    }

    public String getNombreEstadoAsistencia() {
        return nombreEstadoAsistencia;
    }
    public void setNombreEstadoAsistencia(String nombreEstadoAsistencia) {
        this.nombreEstadoAsistencia = nombreEstadoAsistencia;
    }

}
