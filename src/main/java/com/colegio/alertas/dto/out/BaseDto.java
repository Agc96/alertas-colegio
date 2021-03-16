package com.colegio.alertas.dto.out;

import java.io.Serializable;

/**
 *
 * @author Sistema de Alertas
 */
public class BaseDto implements Serializable {

    private static final long serialVersionUID = -1266585805172643579L;

    private Boolean error;
    private String mensaje;

    public BaseDto() {
        error = false;
    }

    public Boolean getError() {
        return error;
    }
    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setError(Boolean error, String mensaje) {
        this.error = error;
        this.mensaje = mensaje;
    }

}
