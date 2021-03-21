package com.colegio.alertas.util;

/**
 *
 * @author Sistema de Alertas
 */
public class AppException extends Exception {

    private static final long serialVersionUID = 4750749860637343534L;

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

}
