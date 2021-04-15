package com.colegio.alertas.util.enums;

/**
 *
 * @author Sistema de Alertas
 */
public enum Rol {

    ADMIN(1, "Administrador"),
    DOCENTE(2, "Docente"),
    PADRE(3, "Padre de Familia");

    private final int id;
    private final String nombre;

    private Rol(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

    public static Rol find(Integer id) {
        if (id != null) {
            for (Rol tipoUsuario : Rol.values()) {
                if (tipoUsuario.getId() == id) return tipoUsuario;
            }
        }
        return null;
    }

    public static Rol find(String name) {
        if (name != null) {
            for (Rol rol : Rol.values()) {
                if (rol.toString().equals(name)) return rol;
            }
        }
        return null;
    }

}
