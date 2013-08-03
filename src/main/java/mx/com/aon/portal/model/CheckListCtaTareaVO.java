package mx.com.aon.portal.model;

/**
 * 
 * Clase VO usada para obtener un modelo de CheckList de la cuenta.
 * 
 * @param codigoConfiguracion
 * @param codigoSeccion
 * @param codigoTarea
 * @param dsTarea
 * @param fgComple
 * @param fgNoRequ
 * @param cdtareaPadre
 * @param dsTareaPadre
 * @param dsUrl
 * @param dsAyuda
 */

public class CheckListCtaTareaVO {
	private String codigoConfiguracion;
	private String codigoSeccion;
	private String codigoTarea;
	private String dsTarea;
	private String fgComple;
	private String fgNoRequ;
	private String cdtareaPadre;
	private String dsTareaPadre;
	private String dsUrl;
	private String dsAyuda;
	private String fgPendiente;

	public String getDsTarea() {
		return dsTarea;
	}
	public void setDsTarea(String dsTarea) {
		this.dsTarea = dsTarea;
	}


    public String getCodigoConfiguracion() {
        return codigoConfiguracion;
    }

    public void setCodigoConfiguracion(String codigoConfiguracion) {
        this.codigoConfiguracion = codigoConfiguracion;
    }

    public String getCodigoSeccion() {
        return codigoSeccion;
    }

    public void setCodigoSeccion(String codigoSeccion) {
        this.codigoSeccion = codigoSeccion;
    }

    public String getCodigoTarea() {
        return codigoTarea;
    }

    public void setCodigoTarea(String codigoTarea) {
        this.codigoTarea = codigoTarea;
    }

    public String getFgComple() {
        return fgComple;
    }

    public void setFgComple(String fgComple) {
        this.fgComple = fgComple;
    }

    public String getFgNoRequ() {
        return fgNoRequ;
    }

    public void setFgNoRequ(String fgNoRequ) {
        this.fgNoRequ = fgNoRequ;
    }
	public String getCdtareaPadre() {
		return cdtareaPadre;
	}
	public void setCdtareaPadre(String cdtareaPadre) {
		this.cdtareaPadre = cdtareaPadre;
	}
	public String getDsTareaPadre() {
		return dsTareaPadre;
	}
	public void setDsTareaPadre(String dsTareaPadre) {
		this.dsTareaPadre = dsTareaPadre;
	}
	public String getDsUrl() {
		return dsUrl;
	}
	public void setDsUrl(String dsUrl) {
		this.dsUrl = dsUrl;
	}
	public String getDsAyuda() {
		return dsAyuda;
	}
	public void setDsAyuda(String dsAyuda) {
		this.dsAyuda = dsAyuda;
	}
	public String getFgPendiente() {
		return fgPendiente;
	}
	public void setFgPendiente(String fgPendiente) {
		this.fgPendiente = fgPendiente;
	}
}
