package com.colegio.alertas.dto;

/**
 *
 * @author Anthony Gutiérrez
 */
public class IncidenciaDto extends BaseDto {

    private static final long serialVersionUID = -3709549938281926147L;

    private Integer idIncidencia;
    private Integer idAula;
    private String fecha;
    private Integer idAlumno;
    private String dniAlumno;
    private String nombresAlumno;
    private String apellidosAlumno;
    private String descripcion;
    private String descripcionHtml;

    public Integer getIdIncidencia() {
        return idIncidencia;
    }
    public void setIdIncidencia(Integer idIncidencia) {
        this.idIncidencia = idIncidencia;
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

    public Integer getIdAlumno() {
        return idAlumno;
    }
    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getDniAlumno() {
        return dniAlumno;
    }
    public void setDniAlumno(String dniAlumno) {
        this.dniAlumno = dniAlumno;
    }

    public String getNombresAlumno() {
        return nombresAlumno;
    }
    public void setNombresAlumno(String nombresAlumno) {
        this.nombresAlumno = nombresAlumno;
    }

    public String getApellidosAlumno() {
        return apellidosAlumno;
    }
    public void setApellidosAlumno(String apellidosAlumno) {
        this.apellidosAlumno = apellidosAlumno;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionHtml() {
        return descripcionHtml;
    }
    public void setDescripcionHtml(String descripcionHtml) {
        this.descripcionHtml = descripcionHtml;
    }

}
