package com.colegio.alertas.util;

/**
 *
 * @author Anthony Gutiérrez
 */
public final class HtmlUtils {

    private HtmlUtils() {
        // Nadie debe ser capaz de instanciar esta clase.
    }

    public static String escape(String text) {
        text = text.replace("&", "&amp;");
        text = text.replace("<", "&lt;");
        text = text.replace(">", "&gt;");
        text = text.replace("\n", "<br/>");
        return text;
    }

}
