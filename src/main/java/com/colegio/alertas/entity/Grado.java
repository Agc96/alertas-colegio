package com.colegio.alertas.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Sistema de Alertas
 */
@Entity
@Table(name = "sa_grado")
public class Grado implements Serializable {

    private static final long serialVersionUID = -8022130146769104255L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grado")
    private Integer idGrado;

    @Column(nullable = false)
    private String nombre;

    public Integer getIdGrado() {
        return idGrado;
    }
    public void setIdGrado(Integer idGrado) {
        this.idGrado = idGrado;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
