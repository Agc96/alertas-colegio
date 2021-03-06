package com.colegio.alertas.dto.in;

import java.io.Serializable;

/**
 *
 * @author Anthony Gutiérrez
 */
public class BusquedaDto implements Serializable {

    private static final long serialVersionUID = -5678667590862601117L;

    private String term;
    private Integer page;
    private Integer size;

    public String getTerm() {
        return term;
    }
    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }

}
