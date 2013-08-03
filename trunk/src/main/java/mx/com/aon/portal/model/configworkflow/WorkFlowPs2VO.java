/**
 * 
 */
package mx.com.aon.portal.model.configworkflow;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Consultor Java
 *
 */
public class WorkFlowPs2VO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String cdprocxcta;
	private String cdpaso;
	private String cdestado;
	private String cdpasoexito;
	private String cdpasofracaso;
	private String cdcondici;
	private String cdtitulo;
	private String nmorden;
	private String dspaso;
	private String dspasoexito;
	private String dspasofracaso;
	private String dstitulo;
	private String outParamText;
    private boolean accionFinal;
    private String swfinal;
	
	
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


	public String getCdpaso() {
		return cdpaso;
	}


	public void setCdpaso(String cdpaso) {
		this.cdpaso = cdpaso;
	}


	public String getCdestado() {
		return cdestado;
	}


	public void setCdestado(String cdestado) {
		this.cdestado = cdestado;
	}


	public String getCdpasoexito() {
		return cdpasoexito;
	}


	public void setCdpasoexito(String cdpasoexito) {
		this.cdpasoexito = cdpasoexito;
	}


	public String getCdpasofracaso() {
		return cdpasofracaso;
	}


	public void setCdpasofracaso(String cdpasofracaso) {
		this.cdpasofracaso = cdpasofracaso;
	}


	public String getCdcondici() {
		return cdcondici;
	}


	public void setCdcondici(String cdcondici) {
		this.cdcondici = cdcondici;
	}


	public String getCdtitulo() {
		return cdtitulo;
	}


	public void setCdtitulo(String cdtitulo) {
		this.cdtitulo = cdtitulo;
	}


	public String getNmorden() {
		return nmorden;
	}


	public void setNmorden(String nmorden) {
		this.nmorden = nmorden;
	}


	public String getDspaso() {
		return dspaso;
	}


	public void setDspaso(String dspaso) {
		this.dspaso = dspaso;
	}


	public String getDspasoexito() {
		return dspasoexito;
	}


	public void setDspasoexito(String dspasoexito) {
		this.dspasoexito = dspasoexito;
	}


	public String getDspasofracaso() {
		return dspasofracaso;
	}


	public void setDspasofracaso(String dspasofracaso) {
		this.dspasofracaso = dspasofracaso;
	}


	public String getDstitulo() {
		return dstitulo;
	}


	public void setDstitulo(String dstitulo) {
		this.dstitulo = dstitulo;
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


    /**
     * @return the accionFinal
     */
    public boolean getAccionFinal() {
        return accionFinal;
    }


    /**
     * @param accionFinal the accionFinal to set
     */
    public void setAccionFinal(boolean accionFinal) {
        this.accionFinal = accionFinal;
    }


	public String getSwfinal() {
		return swfinal;
	}


	public void setSwfinal(String swfinal) {
		this.swfinal = swfinal;
	}
}
