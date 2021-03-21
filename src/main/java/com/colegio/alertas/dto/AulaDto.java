package com.colegio.alertas.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anthony Gutiérrez
 */
public class AulaDto extends BaseDto {

    private static final long serialVersionUID = -8258654843608261752L;

    private Integer idAula;
    private Integer idAnio;
    private Integer idGrado;
    private String nombreGrado;
    private String nombreUsuarioDocente;
    private String nombreCompletoDocente;
    private Integer numAlumnos;
    private List<Integer> alumnos = new ArrayList<>();

    public Integer getIdAula() {
        return idAula;
    }
    public void setIdAula(Integer idAula) {
        this.idAula = idAula;
    }

    public Integer getIdAnio() {
        return idAnio;
    }
    public void setIdAnio(Integer idAnio) {
        this.idAnio = idAnio;
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

    public String getNombreUsuarioDocente() {
        return nombreUsuarioDocente;
    }
    public void setNombreUsuarioDocente(String nombreUsuarioDocente) {
        this.nombreUsuarioDocente = nombreUsuarioDocente;
    }

    public String getNombreCompletoDocente() {
        return nombreCompletoDocente;
    }
    public void setNombreCompletoDocente(String nombreCompletoDocente) {
        this.nombreCompletoDocente = nombreCompletoDocente;
    }

    public Integer getNumAlumnos() {
        return numAlumnos;
    }
    public void setNumAlumnos(Integer numAlumnos) {
        this.numAlumnos = numAlumnos;
    }

    public List<Integer> getAlumnos() {
        return alumnos;
    }
    public void setAlumnos(List<Integer> alumnos) {
        this.alumnos = alumnos;
    }

}
