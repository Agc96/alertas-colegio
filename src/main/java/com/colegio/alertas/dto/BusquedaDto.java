package com.colegio.alertas.dto;

/**
 *
 * @author Sistema de Alertas
 */
public class BusquedaDto extends BaseDto {

    private static final long serialVersionUID = -5678667590862601117L;

    private String termino;
    private Integer indicePagina;
    private Integer numResultados;

    public String getTermino() {
        return termino;
    }
    public void setTermino(String termino) {
        this.termino = termino;
    }

    public Integer getIndicePagina() {
        return indicePagina;
    }
    public void setIndicePagina(Integer indicePagina) {
        this.indicePagina = indicePagina;
    }

    public Integer getNumResultados() {
        return numResultados;
    }
    public void setNumResultados(Integer numResultados) {
        this.numResultados = numResultados;
    }

}
