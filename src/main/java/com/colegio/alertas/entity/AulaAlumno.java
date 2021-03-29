package com.colegio.alertas.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Anthony Gutiérrez
 */
@Entity
@Table(name = "sa_aula_alumno")
public class AulaAlumno implements Serializable {

    private static final long serialVersionUID = -4080388864429871556L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aula_alumno")
    private Integer idAulaAlumno;

    @ManyToOne
    @JoinColumn(name = "id_aula", nullable = false)
    private Aula aula;

    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

    public Integer getIdAulaAlumno() {
        return idAulaAlumno;
    }
    public void setIdAulaAlumno(Integer idAulaAlumno) {
        this.idAulaAlumno = idAulaAlumno;
    }

    public Aula getAula() {
        return aula;
    }
    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Alumno getAlumno() {
        return alumno;
    }
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

}
