package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener el requisitos de rehabilitacion
 * 
 * @param cdRequisito           codigo requisito
 * @param dsRequisito           descripcion del requisito  
 * @param cdPerson              codigo persona
 * @param cdUnieco              codigo aseguradora
 * @param dsUnieco              nombre aseguradora    
 * @param cdRamo                codigo producto
 * @param cdElemento            codigo cliente
 * @param dsElemen              nombre cliente
 * @param cdDocXcta             indica si usa si o no el carrito    
 */
public class RequisitoRehabilitacionVO {

    private String cdRequisito;
    private String dsRequisito;
	private String cdPerson;
	private String cdUnieco;
	private String dsUnieco;
    private String cdRamo;
    private String dsRamo;
    private String cdElemento;
    private String dsElemen;
    private String cdDocXcta;
    
    

	public String getCdRequisito() {
		return cdRequisito;
	}

	public void setCdRequisito(String cdRequisito) {
		this.cdRequisito = cdRequisito;
	}

	public String getDsRequisito() {
		return dsRequisito;
	}

	public void setDsRequisito(String dsRequisito) {
		this.dsRequisito = dsRequisito;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public String getCdUnieco() {
		return cdUnieco;
	}

	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}

	public String getDsUnieco() {
		return dsUnieco;
	}

	public void setDsUnieco(String dsUnieco) {
		this.dsUnieco = dsUnieco;
	}

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

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

	public String getDsElemen() {
		return dsElemen;
	}

	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}

	public String getCdDocXcta() {
		return cdDocXcta;
	}

	public void setCdDocXcta(String cdDocXcta) {
		this.cdDocXcta = cdDocXcta;
	}

}
