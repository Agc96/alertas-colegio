package com.colegio.alertas.dto;

/**
 *
 * @author Sistema de Alertas
 */
public class SuscripcionDto extends BaseDto {

    private static final long serialVersionUID = 5221099016476732916L;

    private String endpoint;
    private String key;
    private String auth;

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

}
