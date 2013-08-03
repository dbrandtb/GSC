package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener el domicilio en el carrito de compras
 * 
 * @param nmOrdDom      numero de orden domicilio
 * @param cdTipDom      codigo tipo domicilio
 * @param dsTipDom      descripcion tipo domicilio
 * @param dsDomici      descripcion del domicilio
 * @param nmNumero      numero telefono   
 * @param nmNumInt      numero interno (telefono)
 * @param cdPais        codigo pais 
 * @param dsPais        nombre pais
 * @param cdEdo         codigo estado    
 * @param dsEdo         nombre estado
 * @param cdMunici      codigo municipio
 * @param dsMunici      nombre municipio
 * @param cdColoni      codigo colonia
 * @param dsColoni      nombre colonia
 *
 */


public class CarritoComprasDireccionOrdenVO {

    private String nmOrdDom;
    private String cdTipDom;
    private String dsTipDom;
    private String dsDomici;
    private String nmNumero;
    private String nmNumInt;
    private String cdPais;
    private String dsPais;
    private String cdEdo;
    private String dsEdo;
    private String cdMunici;
    private String dsMunici;
    private String cdColoni;
    private String dsColoni;
    
    
	public String getNmOrdDom() {
		return nmOrdDom;
	}
	public void setNmOrdDom(String nmOrdDom) {
		this.nmOrdDom = nmOrdDom;
	}
	public String getCdTipDom() {
		return cdTipDom;
	}
	public void setCdTipDom(String cdTipDom) {
		this.cdTipDom = cdTipDom;
	}
	public String getDsTipDom() {
		return dsTipDom;
	}
	public void setDsTipDom(String dsTipDom) {
		this.dsTipDom = dsTipDom;
	}
	public String getDsDomici() {
		return dsDomici;
	}
	public void setDsDomici(String dsDomici) {
		this.dsDomici = dsDomici;
	}
	public String getNmNumero() {
		return nmNumero;
	}
	public void setNmNumero(String nmNumero) {
		this.nmNumero = nmNumero;
	}
	public String getNmNumInt() {
		return nmNumInt;
	}
	public void setNmNumInt(String nmNumInt) {
		this.nmNumInt = nmNumInt;
	}
	public String getCdPais() {
		return cdPais;
	}
	public void setCdPais(String cdPais) {
		this.cdPais = cdPais;
	}
	public String getDsPais() {
		return dsPais;
	}
	public void setDsPais(String dsPais) {
		this.dsPais = dsPais;
	}
	public String getCdEdo() {
		return cdEdo;
	}
	public void setCdEdo(String cdEdo) {
		this.cdEdo = cdEdo;
	}
	public String getDsEdo() {
		return dsEdo;
	}
	public void setDsEdo(String dsEdo) {
		this.dsEdo = dsEdo;
	}
	public String getCdMunici() {
		return cdMunici;
	}
	public void setCdMunici(String cdMunici) {
		this.cdMunici = cdMunici;
	}
	public String getDsMunici() {
		return dsMunici;
	}
	public void setDsMunici(String dsMunici) {
		this.dsMunici = dsMunici;
	}
	public String getCdColoni() {
		return cdColoni;
	}
	public void setCdColoni(String cdColoni) {
		this.cdColoni = cdColoni;
	}
	public String getDsColoni() {
		return dsColoni;
	}
	public void setDsColoni(String dsColoni) {
		this.dsColoni = dsColoni;
	}
	
    
    
    
 
}
