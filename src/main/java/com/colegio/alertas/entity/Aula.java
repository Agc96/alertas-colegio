package com.colegio.alertas.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sistema de Alertas
 */
@Entity
@Table(name = "sa_aula")
public class Aula implements Serializable {

    private static final long serialVersionUID = 1318429886862230436L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_aula")
    private Integer idAula;

    @ManyToOne
    @JoinColumn(name = "id_anio", nullable = false)
    private Anio anio;

    @ManyToOne
    @JoinColumn(name = "id_grado", nullable = false)
    private Grado grado;

    @ManyToOne
    @JoinColumn(name = "docente", nullable = false)
    private Usuario docente;

    @ManyToMany
    @JoinTable(name = "sa_aula_alumno",
            joinColumns = @JoinColumn(name = "id_aula"),
            inverseJoinColumns = @JoinColumn(name = "id_alumno"))
    private Set<Alumno> alumnos;

    public Integer getIdAula() {
        return idAula;
    }
    public void setIdAula(Integer idAula) {
        this.idAula = idAula;
    }

    public Anio getAnio() {
        return anio;
    }
    public void setAnio(Anio anio) {
        this.anio = anio;
    }

    public Grado getGrado() {
        return grado;
    }
    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    public Usuario getDocente() {
        return docente;
    }
    public void setDocente(Usuario docente) {
        this.docente = docente;
    }

    public Set<Alumno> getAlumnos() {
        return alumnos;
    }
    public void setAlumnos(Set<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

}
