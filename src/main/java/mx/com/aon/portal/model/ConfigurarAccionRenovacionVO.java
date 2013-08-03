package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener el acciones de renovacion
 * 
 * @param cdRenova              codigo renovacion
 * @param cdRol                 codigo del rol  
 * @param dsRol                 descripcion del rol
 * @param cdTitulo              codigo titulo     
 * @param dsTitulo              descripcion del titulo
 * @param cdCampo               codigo campo 
 * @param dsCampo               descripcion campo
 * @param cdAccion              codigo accion 
 * @param dsAccion              descripcion accion    
 */
public class ConfigurarAccionRenovacionVO {
	
	private String cdRenova;
    private String cdRol;
    private String dsRol;
    private String cdTitulo;
    private String dsTitulo;
    private String cdCampo;
    private String dsCampo;
    private String cdAccion;
    private String dsAccion;
    
    
	public String getCdRenova() {
		return cdRenova;
	}
	public void setCdRenova(String cdRenova) {
		this.cdRenova = cdRenova;
	}
	public String getCdRol() {
		return cdRol;
	}
	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}
	public String getDsTitulo() {
		return dsTitulo;
	}
	public void setDsTitulo(String dsTitulo) {
		this.dsTitulo = dsTitulo;
	}
	public String getDsCampo() {
		return dsCampo;
	}
	public void setDsCampo(String dsCampo) {
		this.dsCampo = dsCampo;
	}
	public String getDsAccion() {
		return dsAccion;
	}
	public void setDsAccion(String dsAccion) {
		this.dsAccion = dsAccion;
	}
	public String getDsRol() {
		return dsRol;
	}
	public void setDsRol(String dsRol) {
		this.dsRol = dsRol;
	}
	
	public String getCdTitulo() {
		return cdTitulo;
	}
	public void setCdTitulo(String cdTitulo) {
		this.cdTitulo = cdTitulo;
	}
	public String getCdCampo() {
		return cdCampo;
	}
	public void setCdCampo(String cdCampo) {
		this.cdCampo = cdCampo;
	}
	public String getCdAccion() {
		return cdAccion;
	}
	public void setCdAccion(String cdAccion) {
		this.cdAccion = cdAccion;
	}   
    
    
}
	
