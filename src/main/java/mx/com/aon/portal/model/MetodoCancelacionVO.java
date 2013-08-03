package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener un metodo de cancelacion.
 * 
 * @param cdMetodo 
 * @param dsMetodo 
 * @param pv_cdexprespndp_i 
 * @param pv_cdexprespndt_i 
 */
public class MetodoCancelacionVO {
	
	private String cdMetodo;
	private String dsMetodo;
	private String pv_cdexprespndp_i;
	private String pv_cdexprespndt_i;
	
	public String getCdMetodo() {
		return cdMetodo;
	}
	public void setCdMetodo(String cdMetodo) {
		this.cdMetodo = cdMetodo;
	}
	public String getDsMetodo() {
		return dsMetodo;
	}
	public void setDsMetodo(String dsMetodo) {
		this.dsMetodo = dsMetodo;
	}
	public String getPv_cdexprespndp_i() {
		return pv_cdexprespndp_i;
	}
	public void setPv_cdexprespndp_i(String pv_cdexprespndp_i) {
		this.pv_cdexprespndp_i = pv_cdexprespndp_i;
	}
	public String getPv_cdexprespndt_i() {
		return pv_cdexprespndt_i;
	}
	public void setPv_cdexprespndt_i(String pv_cdexprespndt_i) {
		this.pv_cdexprespndt_i = pv_cdexprespndt_i;
	}
	
}
