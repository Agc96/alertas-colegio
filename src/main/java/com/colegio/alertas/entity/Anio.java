package com.colegio.alertas.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Sistema de Alertas
 */
@Entity
@Table(name = "sa_anio")
public class Anio implements Serializable {

    private static final long serialVersionUID = 2051499404540614609L;

    @Id
    @Column(name = "id_anio")
    private Integer idAnio;

    public Integer getIdAnio() {
        return idAnio;
    }
    public void setIdAnio(Integer idAnio) {
        this.idAnio = idAnio;
    }

}
