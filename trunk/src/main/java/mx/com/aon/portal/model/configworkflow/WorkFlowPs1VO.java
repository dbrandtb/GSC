/**
 * 
 */
package mx.com.aon.portal.model.configworkflow;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Alejandro Garcia
 *
 */
public class WorkFlowPs1VO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String cdprocxcta;
	private String cdproceso;
	private String cdunieco;
	private String cdramo;
	private String cdperson;
	private String cdestado;
	private String cdelemento;
	private String dsproceso;
	private String dsprocesoflujo;
	private String dselemen;
	private String dsramo;
	private String dsunieco;
	private String outParamText;
	
    /**
     * @return String
     */
	
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }


	public String getCdprocxcta() {
		return cdprocxcta;
	}


	public void setCdprocxcta(String cdprocxcta) {
		this.cdprocxcta = cdprocxcta;
	}


	public String getCdproceso() {
		return cdproceso;
	}


	public void setCdproceso(String cdproceso) {
		this.cdproceso = cdproceso;
	}


	public String getCdunieco() {
		return cdunieco;
	}


	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}


	public String getCdramo() {
		return cdramo;
	}


	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}


	public String getCdperson() {
		return cdperson;
	}


	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}


	public String getCdestado() {
		return cdestado;
	}


	public void setCdestado(String cdestado) {
		this.cdestado = cdestado;
	}


	public String getCdelemento() {
		return cdelemento;
	}


	public void setCdelemento(String cdelemento) {
		this.cdelemento = cdelemento;
	}


	public String getDsproceso() {
		return dsproceso;
	}


	public void setDsproceso(String dsproceso) {
		this.dsproceso = dsproceso;
	}


	public String getDsprocesoflujo() {
		return dsprocesoflujo;
	}


	public void setDsprocesoflujo(String dsprocesoflujo) {
		this.dsprocesoflujo = dsprocesoflujo;
	}


	public String getDselemen() {
		return dselemen;
	}


	public void setDselemen(String dselemen) {
		this.dselemen = dselemen;
	}


	public String getDsramo() {
		return dsramo;
	}


	public void setDsramo(String dsramo) {
		this.dsramo = dsramo;
	}


	public String getDsunieco() {
		return dsunieco;
	}


	public void setDsunieco(String dsunieco) {
		this.dsunieco = dsunieco;
	}


	/**
	 * @return the outParamText
	 */
	public String getOutParamText() {
		return outParamText;
	}


	/**
	 * @param outParamText the outParamText to set
	 */
	public void setOutParamText(String outParamText) {
		this.outParamText = outParamText;
	}
}
