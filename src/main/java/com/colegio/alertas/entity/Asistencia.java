package com.colegio.alertas.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Anthony Gutiérrez
 */
@Entity
@Table(name = "sa_asistencia")
public class Asistencia implements Serializable {

    private static final long serialVersionUID = -5605679861656801953L;

    @Id
    @GeneratedValue
    @Column(name = "id_asistencia")
    private Long idAsistencia;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

    @Column(name = "id_tipo_asistencia", nullable = false)
    private Integer idTipoAsistencia;

    public Long getIdAsistencia() {
        return idAsistencia;
    }
    public void setIdAsistencia(Long idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Alumno getAlumno() {
        return alumno;
    }
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Integer getIdTipoAsistencia() {
        return idTipoAsistencia;
    }
    public void setIdTipoAsistencia(Integer idTipoAsistencia) {
        this.idTipoAsistencia = idTipoAsistencia;
    }

}
