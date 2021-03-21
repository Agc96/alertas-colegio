package com.colegio.alertas.util.enums;

/**
 *
 * @author Sistema de Alertas
 */
public enum EstadoAsistencia {

    ASISTENTE(1, "Asistente"),
    FALTA(2, "Falta"),
    JUSTIFICADO(3, "Justificado");

    private final int id;
    private final String nombre;

    private EstadoAsistencia(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public static EstadoAsistencia find(Integer id) {
        if (id != null) {
            for (EstadoAsistencia tipoAsistencia : EstadoAsistencia.values()) {
                if (tipoAsistencia.getId() == id) return tipoAsistencia;
            }
        }
        return null;
    }

}
