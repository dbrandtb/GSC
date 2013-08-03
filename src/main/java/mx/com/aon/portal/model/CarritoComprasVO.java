package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener el carrito de compras
 * 
 * @param cdConfiguracion     codigo de configuaracion
 * @param cdCliente           codigo cliente  
 * @param cdElemento          codigo elemento
 * @param dsNombre            descripcion nombre
 * @param fgSiNo              indica si usa si o no el carrito    
 *
 */



public class CarritoComprasVO {

    private String cdConfiguracion;
    private String cdCliente;
    private String cdElemento;
    private String dsNombre;
    private String fgSiNo;
    
    
    
	public String getCdConfiguracion() {
		return cdConfiguracion;
	}
	public void setCdConfiguracion(String cdConfiguracion) {
		this.cdConfiguracion = cdConfiguracion;
	}
	public String getCdCliente() {
		return cdCliente;
	}
	public void setCdCliente(String cdCliente) {
		this.cdCliente = cdCliente;
	}
	public String getCdElemento() {
		return cdElemento;
	}
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
	public String getFgSiNo() {
		return fgSiNo;
	}
	public void setFgSiNo(String fgSiNo) {
		this.fgSiNo = fgSiNo;
	}
	public String getDsNombre() {
		return dsNombre;
	}
	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
	}
  

}
