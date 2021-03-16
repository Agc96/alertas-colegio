package com.colegio.alertas.dto.in;

import java.io.Serializable;

/**
 *
 * @author Sistema de Alertas
 */
public class BusquedaDto implements Serializable {

    private static final long serialVersionUID = -5678667590862601117L;

    private String termino;
    private Integer numPagina;
    private Integer numResultados;

    public String getTermino() {
        return termino;
    }
    public void setTermino(String termino) {
        this.termino = termino;
    }

    public Integer getNumPagina() {
        return numPagina;
    }
    public void setNumPagina(Integer numPagina) {
        this.numPagina = numPagina;
    }

    public Integer getNumResultados() {
        return numResultados;
    }
    public void setNumResultados(Integer numResultados) {
        this.numResultados = numResultados;
    }

}
