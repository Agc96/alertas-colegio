package com.colegio.alertas.dto;

/**
 *
 * @author Anthony Gutiérrez
 */
public class EntrevistaDto extends BaseDto {

    private static final long serialVersionUID = 3575973782133326075L;

    private Integer idEntrevista;
    private Integer idAlumno;
    private String dniAlumno;
    private String nombresAlumno;
    private String apellidosAlumno;
    private String nombreUsuarioPadre;
    private String nombresPadre;
    private String apellidosPadre;
    private Integer idAula;
    private String fecha;
    private String motivo;
    private String motivoHtml;

    public Integer getIdEntrevista() {
        return idEntrevista;
    }
    public void setIdEntrevista(Integer idEntrevista) {
        this.idEntrevista = idEntrevista;
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

    public String getNombreUsuarioPadre() {
        return nombreUsuarioPadre;
    }
    public void setNombreUsuarioPadre(String nombreUsuarioPadre) {
        this.nombreUsuarioPadre = nombreUsuarioPadre;
    }

    public String getNombresPadre() {
        return nombresPadre;
    }
    public void setNombresPadre(String nombresPadre) {
        this.nombresPadre = nombresPadre;
    }

    public String getApellidosPadre() {
        return apellidosPadre;
    }
    public void setApellidosPadre(String apellidosPadre) {
        this.apellidosPadre = apellidosPadre;
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

    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getMotivoHtml() {
        return motivoHtml;
    }
    public void setMotivoHtml(String motivoHtml) {
        this.motivoHtml = motivoHtml;
    }

}
