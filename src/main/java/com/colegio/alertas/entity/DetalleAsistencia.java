package com.colegio.alertas.entity;

import com.colegio.alertas.util.enums.EstadoAsistencia;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sistema de Alertas
 */
@Entity
@Table(name = "sa_detalle_asistencia")
public class DetalleAsistencia implements Serializable {

    private static final long serialVersionUID = -7479305424187242246L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_detalle_asistencia")
    private Integer idDetalleAsistencia;

    @ManyToOne
    @JoinColumn(name = "id_asistencia", nullable = false)
    private Asistencia asistencia;

    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_asistencia", nullable = false)
    private EstadoAsistencia estadoAsistencia;

    public Integer getIdDetalleAsistencia() {
        return idDetalleAsistencia;
    }
    public void setIdDetalleAsistencia(Integer idDetalleAsistencia) {
        this.idDetalleAsistencia = idDetalleAsistencia;
    }

    public Asistencia getAsistencia() {
        return asistencia;
    }
    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
    }

    public Alumno getAlumno() {
        return alumno;
    }
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public EstadoAsistencia getEstadoAsistencia() {
        return estadoAsistencia;
    }
    public void setEstadoAsistencia(EstadoAsistencia estadoAsistencia) {
        this.estadoAsistencia = estadoAsistencia;
    }

}
