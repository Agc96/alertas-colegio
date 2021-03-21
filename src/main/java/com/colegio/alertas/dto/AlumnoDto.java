package com.colegio.alertas.dto;

/**
 *
 * @author Sistema de Alertas
 */
public class AlumnoDto extends BaseDto {

    private static final long serialVersionUID = -3080929470016734775L;

    private Integer idAlumno;
    private String dni;
    private String nombres;
    private String apellidos;
    private String fechaNacimiento;
    private String nombreUsuarioPadre;
    private String nombreCompletoPadre;

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

}
