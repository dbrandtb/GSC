package mx.com.aon.portal.model;

public class EstructuraConfigurarPlanesVO {
	private String codigoPlan;
	private String codigoRamo;
	private String tipoSituacion;
	private String garantia;
	private String oblig;

	

	public String getTipoSituacion() {
		return tipoSituacion;
	}

	public void setTipoSituacion(String tipoSituacion) {
		this.tipoSituacion = tipoSituacion;
	}

	public String getGarantia() {
		return garantia;
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}


    public String getCodigoPlan() {
        return codigoPlan;
    }

    public void setCodigoPlan(String codigoPlan) {
        this.codigoPlan = codigoPlan;
    }

    public String getCodigoRamo() {
        return codigoRamo;
    }

    public void setCodigoRamo(String codigoRamo) {
        this.codigoRamo = codigoRamo;
    }

    public String getOblig() {
        return oblig;
    }

    public void setOblig(String oblig) {
        this.oblig = oblig;
    }
}
