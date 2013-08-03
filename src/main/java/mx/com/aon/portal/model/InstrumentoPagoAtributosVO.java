package mx.com.aon.portal.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class InstrumentoPagoAtributosVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3170341789041309242L;
	
	private String cdInsCte; 	//Clave unica que idengtifica la combinacion (Clave Instrumento por cliente)
	private String cdForPag;	//Clave de la forma de pago (instrumento de pago)		
	private String dsForPag;	//Descripcion de la forma de pago (instrumento de pago)
	private String cdElemento;	//Clave de la aseguradora
	private String dsElemento;	//Descripcion de la cliente
	private String cdUnieco;	//Clave de la cliente
	private String dsUnieco;	//Descripcion de la aseguradora
	private String cdRamo;		//Clave del producto
	private String dsRamo;		//Descripcion del producto

	
	public String getCdForPag() {
		return cdForPag;
	}
	public void setCdForPag(String cdForPag) {
		this.cdForPag = cdForPag;
	}
	public String getDsForPag() {
		return dsForPag;
	}
	public void setDsForPag(String dsForPag) {
		this.dsForPag = dsForPag;
	}
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	public String getCdInsCte() {
		return cdInsCte;
	}
	public void setCdInsCte(String cdInstCte) {
		this.cdInsCte = cdInstCte;
	}
	public String getCdElemento() {
		return cdElemento;
	}
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
	public String getDsElemento() {
		return dsElemento;
	}
	public void setDsElemento(String dsElemento) {
		this.dsElemento = dsElemento;
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
	

}
