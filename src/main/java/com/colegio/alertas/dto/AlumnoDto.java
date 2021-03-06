package com.colegio.alertas.dto;

import java.io.Serializable;

/**
 *
 * @author Anthony Gutiérrez
 */
public class AlumnoDto implements Serializable {

    private static final long serialVersionUID = -3080929470016734775L;

    private Long idAlumno;
    private String dni;
    private String nombres;
    private String apellidos;
    private String fechaNacimiento;

    public Long getIdAlumno() {
        return idAlumno;
    }
    public void setIdAlumno(Long idAlumno) {
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

}
