package com.colegio.alertas.util;

import com.colegio.alertas.entity.Alumno;
import com.colegio.alertas.entity.Usuario;

/**
 *
 * @author Sistema de Alertas
 */
public final class DtoUtils {

    private DtoUtils() {
        // Nadie debe ser capaz de instanciar esta clase.
    }

    public static String escapeHtml(String text) {
        text = text.replace("&", "&amp;");
        text = text.replace("<", "&lt;");
        text = text.replace(">", "&gt;");
        text = text.replace("\"", "&quot;");
        text = text.replace("\n", "<br/>");
        return text;
    }

    public static String obtenerNombreCompleto(Alumno alumno) {
        return alumno.getNombres() + " " + alumno.getApellidos();
    }

    public static String obtenerNombreCompleto(Usuario usuario) {
        return usuario.getNombres() + " " + usuario.getApellidos();
    }

}
