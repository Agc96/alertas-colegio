package com.colegio.alertas.dto;

/**
 *
 * @author Anthony Gutiérrez
 */
public class ComunicadoDto extends BaseDto {

    private static final long serialVersionUID = 8657660345393565620L;

    private Integer idComunicado;
    private Integer idAula;
    private String titulo;
    private String fecha;
    private String descripcion;
    private String descripcionHtml;

    public Integer getIdComunicado() {
        return idComunicado;
    }
    public void setIdComunicado(Integer idComunicado) {
        this.idComunicado = idComunicado;
    }

    public Integer getIdAula() {
        return idAula;
    }
    public void setIdAula(Integer idAula) {
        this.idAula = idAula;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionHtml() {
        return descripcionHtml;
    }
    public void setDescripcionHtml(String descripcionHtml) {
        this.descripcionHtml = descripcionHtml;
    }

}
