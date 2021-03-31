package com.colegio.alertas.util;

/**
 *
 * @author Sistema de Alertas
 */
public final class HtmlUtils {

    private HtmlUtils() {
        // Nadie debe ser capaz de instanciar esta clase.
    }

    public static String escape(String text) {
        text = text.replace("&", "&amp;");
        text = text.replace("<", "&lt;");
        text = text.replace(">", "&gt;");
        text = text.replace("\"", "&quot;");
        text = text.replace("\n", "<br/>");
        return text;
    }

}
