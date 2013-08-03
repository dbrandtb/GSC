/**
 * 
 */
package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener contratante en Orden de compra
 * 
 * @param nmOrden               numero de orden
 * @param contratante           persona contratante   
 * 
 */

public class OrdenDeCompraEncOrdenVO {


	private String nmOrden;
	private String contratante;

	public String getNmOrden() {
		return nmOrden;
	}

	public void setNmOrden(String nmOrden) {
		this.nmOrden = nmOrden;
	}
	public String getContratante() {
		return contratante;
	}
	public void setContratante(String contratante) {
		this.contratante = contratante;
	}
}



