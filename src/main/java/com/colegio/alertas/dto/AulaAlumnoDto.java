package com.colegio.alertas.dto;

/**
 *
 * @author Anthony Gutiérrez
 */
public class AulaAlumnoDto extends BaseDto {

    private static final long serialVersionUID = 4685555880464704104L;

    private Integer idAulaAlumno;
    private Integer idAula;
    private Integer idGrado;
    private String nombreGrado;
    private Integer idAnio;
    private Integer idAlumno;
    private String nombresAlumno;
    private String apellidosAlumno;

    public Integer getIdAulaAlumno() {
        return idAulaAlumno;
    }
    public void setIdAulaAlumno(Integer idAulaAlumno) {
        this.idAulaAlumno = idAulaAlumno;
    }

    public Integer getIdAula() {
        return idAula;
    }
    public void setIdAula(Integer idAula) {
        this.idAula = idAula;
    }

    public Integer getIdGrado() {
        return idGrado;
    }
    public void setIdGrado(Integer idGrado) {
        this.idGrado = idGrado;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }
    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }

    public Integer getIdAnio() {
        return idAnio;
    }
    public void setIdAnio(Integer idAnio) {
        this.idAnio = idAnio;
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }
    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
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

}
