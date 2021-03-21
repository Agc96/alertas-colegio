package com.colegio.alertas.unused;

import java.io.Serializable;

/**
 *
 * @author Sistema de Alertas
 * @param <T>
 */
public class FiltroDto<T> implements Serializable {

    private static final long serialVersionUID = 8366451460598820222L;

    private T filtro;
    private Integer firstResult;
    private Integer maxResults;

    public T getFiltro() {
        return filtro;
    }
    public void setFiltro(T filtro) {
        this.filtro = filtro;
    }

    public Integer getFirstResult() {
        return firstResult;
    }
    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public Integer getMaxResults() {
        return maxResults;
    }
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

}
