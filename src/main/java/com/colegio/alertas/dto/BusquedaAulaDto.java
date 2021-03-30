package com.colegio.alertas.dto;

/**
 *
 * @author Sistema de Alertas
 */
public class BusquedaAulaDto extends BusquedaDto {

    private static final long serialVersionUID = -1551016045642860347L;

    private Integer idAula;
    private Integer idAlumno;

    public Integer getIdAula() {
        return idAula;
    }
    public void setIdAula(Integer idAula) {
        this.idAula = idAula;
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }
    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

}
