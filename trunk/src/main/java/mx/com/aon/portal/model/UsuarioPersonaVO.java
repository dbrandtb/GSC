package mx.com.aon.portal.model;

/**Clase VO que representa la estructura de datos usada para obtener 
 * una lista de valores de las personas las cuales puede ver el usuario de la aplicación
 *  (Ejecutivo de cuenta, usuario final, etc.) de la pantalla Agregar Funcion en la poliza.
 *  
 *  @vars cdPerson,persona,cdTipPer,otFisJur,otSexo
 *  
 */
public class UsuarioPersonaVO {
	private String cdPerson; 
    private String persona; 
    private String cdTipPer; 
    private String otFisJur; 
    private String otSexo;
    
	public String getCdPerson() {
		return cdPerson;
	}
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}
	public String getPersona() {
		return persona;
	}
	public void setPersona(String persona) {
		this.persona = persona;
	}
	public String getCdTipPer() {
		return cdTipPer;
	}
	public void setCdTipPer(String cdTipPer) {
		this.cdTipPer = cdTipPer;
	}
	public String getOtFisJur() {
		return otFisJur;
	}
	public void setOtFisJur(String otFisJur) {
		this.otFisJur = otFisJur;
	}
	public String getOtSexo() {
		return otSexo;
	}
	public void setOtSexo(String otSexo) {
		this.otSexo = otSexo;
	}
}
