package com.colegio.alertas.util.enums;

/**
 *
 * @author Anthony Gutiérrez
 */
public enum TipoAsistencia {

    ASISTENTE(1, "Asistente"),
    FALTA(2, "Falta"),
    JUSTIFICADO(3, "Justificado");

    private final int id;
    private final String nombre;

    private TipoAsistencia(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public static TipoAsistencia find(Integer id) {
        for (TipoAsistencia tipoAsistencia : TipoAsistencia.values()) {
            if (tipoAsistencia.getId() == id) return tipoAsistencia;
        }
        return null;
    }

}
