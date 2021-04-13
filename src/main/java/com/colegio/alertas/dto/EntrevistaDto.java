package com.colegio.alertas.dto;

/**
 *
 * @author Sistema de Alertas
 */
public class EntrevistaDto extends BaseDto {

    private static final long serialVersionUID = 3575973782133326075L;

    private Integer idEntrevista;
    private Integer idAlumno;
    private String dniAlumno;
    private String nombreCompletoAlumno;
    private String nombreUsuarioPadre;
    private String nombreCompletoPadre;
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

    public String getNombreCompletoAlumno() {
        return nombreCompletoAlumno;
    }
    public void setNombreCompletoAlumno(String nombreCompletoAlumno) {
        this.nombreCompletoAlumno = nombreCompletoAlumno;
    }

    public String getNombreUsuarioPadre() {
        return nombreUsuarioPadre;
    }
    public void setNombreUsuarioPadre(String nombreUsuarioPadre) {
        this.nombreUsuarioPadre = nombreUsuarioPadre;
    }

    public String getNombreCompletoPadre() {
        return nombreCompletoPadre;
    }

    public void setNombreCompletoPadre(String nombreCompletoPadre) {
        this.nombreCompletoPadre = nombreCompletoPadre;
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
