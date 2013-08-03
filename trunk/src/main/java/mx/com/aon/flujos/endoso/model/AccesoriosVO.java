/**
 * 
 */
package mx.com.aon.flujos.endoso.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author eflores
 * @date 09/10/2008
 *
 */
public class AccesoriosVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String nmObjeto;
    
    private String dsObjeto;
    
    private String ptObjeto;
    
    private String cdTipoObjeto;
    
    private String monto;
    
    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the cdTipoObjeto
     */
    public String getCdTipoObjeto() {
        return cdTipoObjeto;
    }

    /**
     * @param cdTipoObjeto the cdTipoObjeto to set
     */
    public void setCdTipoObjeto(String cdTipoObjeto) {
        this.cdTipoObjeto = cdTipoObjeto;
    }

    /**
     * @return the dsObjeto
     */
    public String getDsObjeto() {
        return dsObjeto;
    }

    /**
     * @param dsObjeto the dsObjeto to set
     */
    public void setDsObjeto(String dsObjeto) {
        this.dsObjeto = dsObjeto;
    }

    /**
     * @return the monto
     */
    public String getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(String monto) {
        this.monto = monto;
    }

    /**
     * @return the nmObjeto
     */
    public String getNmObjeto() {
        return nmObjeto;
    }

    /**
     * @param nmObjeto the nmObjeto to set
     */
    public void setNmObjeto(String nmObjeto) {
        this.nmObjeto = nmObjeto;
    }

    /**
     * @return the ptObjeto
     */
    public String getPtObjeto() {
        return ptObjeto;
    }

    /**
     * @param ptObjeto the ptObjeto to set
     */
    public void setPtObjeto(String ptObjeto) {
        this.ptObjeto = ptObjeto;
    }
}
