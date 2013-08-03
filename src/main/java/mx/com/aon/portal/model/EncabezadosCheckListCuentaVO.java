package mx.com.aon.portal.model;

public class EncabezadosCheckListCuentaVO {
	private String cdElemen;
	private String dsNombre;
	private String cdConfig;
	private String dsConfig;
	private String cdLinOpe;
	private String dsLinOpe;
	public String getDsNombre() {
		return dsNombre;
	}
	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
	}
	public String getDsConfig() {
		return dsConfig;
	}
	public void setDsConfig(String dsConfig) {
		this.dsConfig = dsConfig;
	}
	public String getDsLinOpe() {
		return dsLinOpe;
	}
	public void setDsLinOpe(String dsLinOpe) {
		this.dsLinOpe = dsLinOpe;
	}


    public String getCdConfig() {
        return cdConfig;
    }

    public void setCdConfig(String cdConfig) {
        this.cdConfig = cdConfig;
    }

    public String getCdLinOpe() {
        return cdLinOpe;
    }

    public void setCdLinOpe(String cdLinOpe) {
        this.cdLinOpe = cdLinOpe;
    }
	public String getCdElemen() {
		return cdElemen;
	}
	public void setCdElemen(String cdElemen) {
		this.cdElemen = cdElemen;
	}
}
