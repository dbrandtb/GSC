/**
 * 
 */
package mx.com.aon.portal.model.configmenu;

import java.io.Serializable;

import mx.com.aon.utils.AONCatwebUtils;
import mx.com.aon.utils.Constantes;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author eflores
 * @date 22/05/2008
 *
 */
public class OpcionVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String cdTitulo;

    private String dsTitulo;
    
    private String dsTituloDes;
    
    private String dsUrl;
    
    private String dsTipDes;
    
    private String outParamText;

    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    /**
     * @return the dsTitulo
     */
    public String getDsTitulo() {
        return dsTitulo;
    }

    /**
     * @param dsTitulo the dsTitulo to set
     */
    public void setDsTitulo(String dsTitulo) {
        this.dsTitulo = dsTitulo;
    }

    /**
     * @return the dsUrl
     */
    public String getDsUrl() {
        return dsUrl;
    }

    /**
     * @param dsUrl the dsUrl to set
     */
    public void setDsUrl(String dsUrl) {
        this.dsUrl = dsUrl;
    }

    /**
     * @return the cdTitulo
     */
    public String getCdTitulo() {
        return cdTitulo;
    }

    /**
     * @param cdTitulo the cdTitulo to set
     */
    public void setCdTitulo(String cdTitulo) {
        this.cdTitulo = cdTitulo;
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
     * @return the dsTituloDes
     */
    public String getDsTituloDes() {
        return dsTituloDes;
    }

    /**
     * @param dsTituloDes the dsTituloDes to set
     */
    public void setDsTituloDes(String dsTituloDes) {
        this.dsTituloDes = dsTituloDes;
    }
    
	public String getDsTipDes() {
		return dsTipDes;
	}

	public void setDsTipDes(String dsTipDes) {
		if (StringUtils.isBlank(dsTipDes)) {
			this.dsTipDes = Constantes.NO;
		} else {
			this.dsTipDes = dsTipDes;	
		}		
	}
}
