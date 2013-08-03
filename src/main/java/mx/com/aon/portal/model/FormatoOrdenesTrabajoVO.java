package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener formato orden de trabajo.
 * 
 * @param cdFormatoOrden        codigo formato orden
 * @param dsFormatoOrden        descripcion formato orden 
 *  
*/
public class FormatoOrdenesTrabajoVO {
	private String cdFormatoOrden;
	private String dsFormatoOrden;

	public String getCdFormatoOrden() {
		return cdFormatoOrden;
	}

	public void setCdFormatoOrden(String cdFormatoOrden) {
		this.cdFormatoOrden = cdFormatoOrden;
	}

	public String getDsFormatoOrden() {
		return dsFormatoOrden;
	}

	public void setDsFormatoOrden(String dsFormatoOrden) {
		this.dsFormatoOrden = dsFormatoOrden;
	}

}