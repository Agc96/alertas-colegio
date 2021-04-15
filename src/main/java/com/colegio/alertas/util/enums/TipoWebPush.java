package com.colegio.alertas.util.enums;

/**
 *
 * @author Sistema de Alertas
 */
public enum TipoWebPush {

    COMUNICADO("sistema-alertas-comunicado", "Nuevo comunicado registrado",
            "Estimado(a) %s: el profesor %s ha enviado un comunicado al aula de %s del año %s."),
    INCIDENCIA("sistema-alertas-incidencia", "Nueva incidencia registrada",
            "Estimado(a) %s: el profesor %s ha registrado una incidencia cometida por su menor hijo %s."),
    ENTREVISTA("sistema-alertas-entrevista", "Nueva entrevista registrada",
            "Estimado(a) %s: el padre de familia %s, del alumno %s, le ha solicitado una entrevista.");

    private final String tag;
    private final String titulo;
    private final String mensaje;

    private TipoWebPush(String tag, String titulo, String mensaje) {
        this.tag = tag;
        this.titulo = titulo;
        this.mensaje = mensaje;
    }

    public String getTag() {
        return tag;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getMensaje() {
        return mensaje;
    }

}
