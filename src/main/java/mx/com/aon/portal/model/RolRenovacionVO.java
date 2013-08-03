package mx.com.aon.portal.model;
/**
 * Clase VO usada para obtener rol en configuracion de la  renovacion
 * 
 * @param cdRenova              codigo renovacion
 * @param dsSisRol              descripcion del rol
 * @param cdRol                 codigo del rol
 */
public class RolRenovacionVO {
	private String cdRenova;
	private String dsSisRol;
	private String cdRol;

	public String getCdRenova() {
		return cdRenova;
	}
	public void setCdRenova(String cdRenova) {
		this.cdRenova = cdRenova;
	}
	public String getDsSisRol() {
		return dsSisRol;
	}
	public void setDsSisRol(String dsSisRol) {
		this.dsSisRol = dsSisRol;
	}
	public String getCdRol() {
		return cdRol;
	}
	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}
	
}
