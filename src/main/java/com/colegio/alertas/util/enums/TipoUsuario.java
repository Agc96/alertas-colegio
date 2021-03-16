package com.colegio.alertas.util.enums;

/**
 *
 * @author Sistema de Alertas
 */
public enum TipoUsuario {

    ADMIN(1, "Administrador"),
    DOCENTE(2, "Docente"),
    PADRE(3, "Padre de Familia");

    private final int id;
    private final String nombre;

    private TipoUsuario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public static TipoUsuario find(Integer id) {
        if (id != null) {
            for (TipoUsuario tipoUsuario : TipoUsuario.values()) {
                if (tipoUsuario.getId() == id) return tipoUsuario;
            }
        }
        return null;
    }

}
