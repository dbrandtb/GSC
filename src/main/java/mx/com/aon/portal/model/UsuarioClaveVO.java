package mx.com.aon.portal.model;

/**Clase VO que representa la estructura de datos usada para obtener 
 * una lista de valores de las personas las cuales puede ver el usuario de la aplicación
 *  de la pantalla Persona Crear Usuario.
 *  
 *  @vars clave,contrasenha,registGrid
 *  
 */
public class UsuarioClaveVO {
	
	private String cdPerson;
	private String cdUsuari; 
    private String contrasenha; 
    private String registGrid;
    private String dsUsuari;
	
    
    public String getCdPerson() {
		return cdPerson;
	}
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}
	public String getCdUsuari() {
		return cdUsuari;
	}
	public void setCdUsuari(String cdUsuari) {
		this.cdUsuari = cdUsuari;
	}
	public String getContrasenha() {
		return contrasenha;
	}
	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}
	public String getRegistGrid() {
		return registGrid;
	}
	public void setRegistGrid(String registGrid) {
		this.registGrid = registGrid;
	}
	public String getDsUsuari() {
		return dsUsuari;
	}
	public void setDsUsuari(String dsUsuari) {
		this.dsUsuari = dsUsuari;
	} 
    
}
