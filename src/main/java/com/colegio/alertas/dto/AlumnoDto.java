package com.colegio.alertas.dto;

import java.io.Serializable;

/**
 *
 * @author Sistema de Alertas
 */
public class AlumnoDto implements Serializable {

    private static final long serialVersionUID = -3080929470016734775L;

    private Integer idAlumno;
    private String dni;
    private String nombres;
    private String apellidos;
    private String fechaNacimiento;
    private Integer idPadre;
    private String nombrePadre;

    public Integer getIdAlumno() {
        return idAlumno;
    }
    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getIdPadre() {
        return idPadre;
    }
    public void setIdPadre(Integer idPadre) {
        this.idPadre = idPadre;
    }

    public String getNombrePadre() {
        return nombrePadre;
    }
    public void setNombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
    }

}
