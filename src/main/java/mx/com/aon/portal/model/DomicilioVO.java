package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener los datos de la persona
 * 
 * @param codigoPersona       codigo persona
 * @param tipoDomicilio       codigo de tipo domicilio  
 * @param dsTipoDomicilio     descripcion de tipo domicilio
 * @param numOrdenDomicilio   numeracion del domicilio
 * @param dsDomicilio         descripcion de domicilio    
 * @param cdPostal            codigo posta
 * @param numero              numero de telefono  
 * @param numeroInterno       numero de interno del telefono
 * @param codigoPais          codigo del pais
 * @param dsPais              descripcion del pais
 * @param codigoEstado        codigo del estado
 * @param dsEstado            nombre del estado
 * @param codigoMunicipio     codigo del municipio
 * @param dsMunicipio         nombre del municipio
 * @param codigoColonia       codigo de la colonia 
 * @param dsColonia           nombre de la colonia 
 */
public class DomicilioVO {
	private String codigoPersona;
	private String tipoDomicilio;
	private String dsTipoDomicilio;
	private String numOrdenDomicilio;
	private String dsDomicilio;
	private String cdPostal;
	private String numero;
	private String numeroInterno;
	private String codigoPais;
	private String dsPais;
	private String codigoEstado;
	private String dsEstado;
	private String codigoMunicipio;
	private String dsMunicipio;
	private String codigoColonia;
	private String dsColonia;

	public String getCodigoPersona() {
		return codigoPersona;
	}
	public void setCodigoPersona(String codigoPersona) {
		this.codigoPersona = codigoPersona;
	}
	public String getTipoDomicilio() {
		return tipoDomicilio;
	}
	public void setTipoDomicilio(String tipoDomicilio) {
		this.tipoDomicilio = tipoDomicilio;
	}
	public String getNumOrdenDomicilio() {
		return numOrdenDomicilio;
	}
	public void setNumOrdenDomicilio(String numOrdenDomicilio) {
		this.numOrdenDomicilio = numOrdenDomicilio;
	}
	public String getDsDomicilio() {
		return dsDomicilio;
	}
	public void setDsDomicilio(String dsDomicilio) {
		this.dsDomicilio = dsDomicilio;
	}
	public String getCdPostal() {
		return cdPostal;
	}
	public void setCdPostal(String cdPostal) {
		this.cdPostal = cdPostal;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNumeroInterno() {
		return numeroInterno;
	}
	public void setNumeroInterno(String numeroInterno) {
		this.numeroInterno = numeroInterno;
	}
	public String getCodigoPais() {
		return codigoPais;
	}
	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}
	public String getCodigoEstado() {
		return codigoEstado;
	}
	public void setCodigoEstado(String codigoEstado) {
		this.codigoEstado = codigoEstado;
	}
	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}
	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}
	public String getCodigoColonia() {
		return codigoColonia;
	}
	public void setCodigoColonia(String codigoColonia) {
		this.codigoColonia = codigoColonia;
	}
	public String getDsTipoDomicilio() {
		return dsTipoDomicilio;
	}
	public void setDsTipoDomicilio(String dsTipoDomicilio) {
		this.dsTipoDomicilio = dsTipoDomicilio;
	}
	public String getDsPais() {
		return dsPais;
	}
	public void setDsPais(String dsPais) {
		this.dsPais = dsPais;
	}
	public String getDsEstado() {
		return dsEstado;
	}
	public void setDsEstado(String dsEstado) {
		this.dsEstado = dsEstado;
	}
	public String getDsColonia() {
		return dsColonia;
	}
	public void setDsColonia(String dsColonia) {
		this.dsColonia = dsColonia;
	}
	public String getDsMunicipio() {
		return dsMunicipio;
	}
	public void setDsMunicipio(String dsMunicipio) {
		this.dsMunicipio = dsMunicipio;
	}

}
