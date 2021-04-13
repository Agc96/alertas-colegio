package com.colegio.alertas.dto;

/**
 *
 * @author Sistema de Alertas
 */
public class MensajeDto extends BaseDto {

    private static final long serialVersionUID = 6583759062761689080L;

    private String tag;
    private String titulo;
    private String mensaje;

    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
