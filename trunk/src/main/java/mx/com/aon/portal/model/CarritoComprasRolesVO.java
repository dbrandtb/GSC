package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener el rol en el carrito de compras
 * 
 * @param cdRol               codigo del rol
 * @param dsRol               descripcion del rol  
 * @param nmOrden             numero de orden
 * @param nombreContratante   nombre del contratante
 * @param cdBanco             codigo del banco   
 * @param dsNombre            nombre del banco
 * @param cdPerson            codigo de pais 
 * @param dsForPag            descripcion de la forma de pago
 * @param cdForPag            codigo de la forma de pago
 * @param cdTitArg            codigo de la tarjeta de credito
 * @param dsTitArg            nombred de la tajeta de credito
 * @param nmTarj              numero de la tarjeta de credito
 * @param feVence             fecha de vencimiento de la tarjeta de credito
 *
 */

public class CarritoComprasRolesVO {

    private String cdRol;
    private String dsRol;
    private String nmOrden;
    private String nombreContratante;
    private String cdBanco;
    private String dsNombre;
    private String cdPerson;
    private String dsForPag;
    private String cdForPag;
    private String cdTitArg;
    private String dsTitArg;
    private String nmTarj;
    private String feVence;

    
    

	public String getCdRol() {
		return cdRol;
	}
	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}
	public String getDsRol() {
		return dsRol;
	}
	public void setDsRol(String dsRol) {
		this.dsRol = dsRol;
	}
	public String getNombreContratante() {
		return nombreContratante;
	}
	public void setNombreContratante(String nombreContratante) {
		this.nombreContratante = nombreContratante;
	}
	public String getNmOrden() {
		return nmOrden;
	}
	public void setNmOrden(String nmOrden) {
		this.nmOrden = nmOrden;
	}
	public String getCdBanco() {
		return cdBanco;
	}
	public void setCdBanco(String cdBanco) {
		this.cdBanco = cdBanco;
	}
	public String getDsNombre() {
		return dsNombre;
	}
	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
	}
	public String getCdPerson() {
		return cdPerson;
	}
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}
	public String getDsForPag() {
		return dsForPag;
	}
	public void setDsForPag(String dsForPag) {
		this.dsForPag = dsForPag;
	}
	public String getCdTitArg() {
		return cdTitArg;
	}
	public void setCdTitArg(String cdTitArg) {
		this.cdTitArg = cdTitArg;
	}
	public String getNmTarj() {
		return nmTarj;
	}
	public void setNmTarj(String nmTarj) {
		this.nmTarj = nmTarj;
	}
	public String getFeVence() {
		return feVence;
	}
	public void setFeVence(String feVence) {
		this.feVence = feVence;
	}
	public String getCdForPag() {
		return cdForPag;
	}
	public void setCdForPag(String cdForPag) {
		this.cdForPag = cdForPag;
	}
	public String getDsTitArg() {
		return dsTitArg;
	}
	public void setDsTitArg(String dsTitArg) {
		this.dsTitArg = dsTitArg;
	}
   
	
    
 
}
