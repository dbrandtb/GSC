package mx.com.aon.portal.model;


/**
 * Clase VO usada para obtener razones de cancelacion de productos.
 * 
 * @param cdRamo 
 * @param dsRamo 
 * @param cdRazon 
 * @param dsRazon
 * @param cdMetodo
 * @param dsMetodo
 */
public class RazonesCancelacionProductoVO {
	
	private String cdRamo;
	private String dsRamo;
	private String cdRazon;
	private String dsRazon;
	private String cdMetodo;
	private String dsMetodo;
	
	
	public String getCdRamo() {
		return cdRamo;
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	public String getDsRamo() {
		return dsRamo;
	}
	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}
	public String getCdRazon() {
		return cdRazon;
	}
	public void setCdRazon(String cdRazon) {
		this.cdRazon = cdRazon;
	}
	public String getDsRazon() {
		return dsRazon;
	}
	public void setDsRazon(String dsRazon) {
		this.dsRazon = dsRazon;
	}
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

}
