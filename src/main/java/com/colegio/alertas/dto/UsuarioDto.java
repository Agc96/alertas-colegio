package com.colegio.alertas.dto;

import java.io.Serializable;

/**
 *
 * @author Anthony Gutiérrez
 */
public class UsuarioDto implements Serializable {

    private static final long serialVersionUID = 3772350122988237925L;

    private Integer idUsuario;
    private String dni;
    private String nombres;
    private String apellidos;
    private String nombreUsuario;
    private Integer idTipoUsuario;
    private String nombreTipoUsuario;

    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getIdTipoUsuario() {
        return idTipoUsuario;
    }
    public void setIdTipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getNombreTipoUsuario() {
        return nombreTipoUsuario;
    }
    public void setNombreTipoUsuario(String nombreTipoUsuario) {
        this.nombreTipoUsuario = nombreTipoUsuario;
    }

}
