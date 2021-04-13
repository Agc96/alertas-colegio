package com.colegio.alertas.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sistema de Alertas
 */
@Entity
@Table(name = "sa_suscripcion")
public class Suscripcion implements Serializable {

    private static final long serialVersionUID = 6668575410755191052L;

    @Id
    @Column(nullable = false)
    private String endpoint;

    @Column(name = "public_key", nullable = false)
    private String key;

    @Column(nullable = false)
    private String auth;

    @ManyToOne
    @JoinColumn(name = "nombre_usuario", nullable = false)
    private Usuario usuario;

    public String getEndpoint() {
        return endpoint;
    }
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getAuth() {
        return auth;
    }
    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
