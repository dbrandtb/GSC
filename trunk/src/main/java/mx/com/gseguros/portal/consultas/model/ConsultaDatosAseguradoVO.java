package mx.com.gseguros.portal.consultas.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author JAGC
 *
 */
public class ConsultaDatosAseguradoVO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8555353864912795413L;

	//private String cdgarant;//garantia
	private String cdperson;
	private String nmsituac;
	private String titular;
	private String cdrfc;
	private String cdrol;
	private String dsrol;
	private String Sexo;
	private String fenacimi;
	private String status;
	private String parentesco;

		
	public String getSexo() {
		return Sexo;
	}


	public void setSexo(String sexo) {
		Sexo = sexo;
	}


	public String getFenacimi() {
		return fenacimi;
	}


	public void setFenacimi(String fenacimi) {
		this.fenacimi = fenacimi;
	}


	public String getCdperson() {
		return cdperson;
	}


	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}


	public String getNmsituac() {
		return nmsituac;
	}


	public void setNmsituac(String nmsituac) {
		this.nmsituac = nmsituac;
	}


	public String getTitular() {
		return titular;
	}


	public void setTitular(String titular) {
		this.titular = titular;
	}


	public String getCdrfc() {
		return cdrfc;
	}


	public void setCdrfc(String cdrfc) {
		this.cdrfc = cdrfc;
	}


	public String getCdrol() {
		return cdrol;
	}


	public void setCdrol(String cdrol) {
		this.cdrol = cdrol;
	}


	public String getDsrol() {
		return dsrol;
	}


	public void setDsrol(String dsrol) {
		this.dsrol = dsrol;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getParentesco() {
		return parentesco;
	}

	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}


	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}	
		
}