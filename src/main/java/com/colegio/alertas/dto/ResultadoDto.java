package com.colegio.alertas.dto;

import java.util.List;

/**
 *
 * @author Sistema de Alertas
 * @param <T>
 */
public class ResultadoDto<T> extends BaseDto {

    private static final long serialVersionUID = 4156744879313765453L;

    private Integer total;
    private List<T> lista;
    private Integer numPaginas;

    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getLista() {
        return lista;
    }
    public void setLista(List<T> lista) {
        this.lista = lista;
    }

    public Integer getNumPaginas() {
        return numPaginas;
    }
    public void setNumPaginas(Integer numPaginas) {
        this.numPaginas = numPaginas;
    }

}
