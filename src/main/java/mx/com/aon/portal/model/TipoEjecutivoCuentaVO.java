package mx.com.aon.portal.model;

/**
 * 
 * Clase VO usada para obtener un tipo de ejecutivo de cuenta.
 * 
 * @param cdTipage 
 * @param dsTipage 
 */
public class TipoEjecutivoCuentaVO {

	private String cdTipage;
	private String dsTipage;

	public String getCdTipage() {
		return cdTipage;
	}

	public void setCdTipage(String cdTipage) {
		this.cdTipage = cdTipage;
	}

	public String getDsTipage() {
		return dsTipage;
	}

	public void setDsTipage(String dsTipage) {
		this.dsTipage = dsTipage;
	}

}
