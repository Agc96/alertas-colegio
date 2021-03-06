package com.colegio.alertas.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Anthony Gutiérrez
 */
@Entity
@Table(name = "sa_aula")
public class Aula implements Serializable {

    private static final long serialVersionUID = 1318429886862230436L;

    @Id
    @GeneratedValue
    @Column(name = "id_aula")
    private Long idAula;

    @Column(nullable = false)
    private Integer anio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_grado", nullable = false)
    private Grado grado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_docente", nullable = false)
    private Usuario docente;

    @ManyToMany
    @JoinTable(name = "sa_aula_alumno",
            joinColumns = @JoinColumn(name = "id_alumno"),
            inverseJoinColumns = @JoinColumn(name = "id_aula"))
    private List<Alumno> alumnos;

    public Long getIdAula() {
        return idAula;
    }
    public void setIdAula(Long idAula) {
        this.idAula = idAula;
    }

    public Integer getAnio() {
        return anio;
    }
    public void setAnio(Integer anio) {
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

    public List<Alumno> getAlumnos() {
        return alumnos;
    }
    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

}
