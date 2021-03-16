package com.colegio.alertas.util;

/**
 *
 * @author Anthony Gutiérrez
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
