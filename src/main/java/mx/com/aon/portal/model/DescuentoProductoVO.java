package mx.com.aon.portal.model;

/**
 * Clase VO con la estructura de datos para detalle de Descuento por volumen
 	* @param prDescto
 	* @param mnDescto
 * 
 * @extends  DescuentoVO
 */
public class DescuentoProductoVO extends DescuentoVO{
	
	private String prDescto;
	private String mnDescto;
	
	
	public String getPrDescto() {
		return prDescto;
	}
	public void setPrDescto(String prDescto) {
		this.prDescto = prDescto;
	}
	public String getMnDescto() {
		return mnDescto;
	}
	public void setMnDescto(String mnDescto) {
		this.mnDescto = mnDescto;
	}
}
