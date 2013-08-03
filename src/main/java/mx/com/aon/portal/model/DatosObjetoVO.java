package mx.com.aon.portal.model;

/**Clase VO que representa la estructura de datos de los objetos
 * usada en la pantalla Datos Variables del Objeto Asegurable.
 *
 *@vars cdAtribu, dsAtribu, otValor
 */
public class DatosObjetoVO {
	private String cdAtribu;
	private String dsAtribu;
	private String otValor;
	
	
	public String getCdAtribu() {
		return cdAtribu;
	}
	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
	}
	public String getDsAtribu() {
		return dsAtribu;
	}
	public void setDsAtribu(String dsAtribu) {
		this.dsAtribu = dsAtribu;
	}
	public String getOtValor() {
		return otValor;
	}
	public void setOtValor(String otValor) {
		this.otValor = otValor;
	}
}
