/**
 * 
 */
package mx.com.aon.flujos.endoso.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author eflores
 * @date 16/12/2008
 *
 */
public class SuplemVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String nmSuplem;
    
    private String nsupLogi;
    
    private String fesolici;
    
    private String feinival;

    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    /**
     * @return the nmSuplem
     */
    public String getNmSuplem() {
        return nmSuplem;
    }

    /**
     * @param nmSuplem the nmSuplem to set
     */
    public void setNmSuplem(String nmSuplem) {
        this.nmSuplem = nmSuplem;
    }

    /**
     * @return the nsupLogi
     */
    public String getNsupLogi() {
        return nsupLogi;
    }

    /**
     * @param nsupLogi the nsupLogi to set
     */
    public void setNsupLogi(String nsupLogi) {
        this.nsupLogi = nsupLogi;
    }

    /**
     * @return the feinival
     */
    public String getFeinival() {
        return feinival;
    }

    /**
     * @param feinival the feinival to set
     */
    public void setFeinival(String feinival) {
        this.feinival = feinival;
    }

    /**
     * @return the fesolici
     */
    public String getFesolici() {
        return fesolici;
    }

    /**
     * @param fesolici the fesolici to set
     */
    public void setFesolici(String fesolici) {
        this.fesolici = fesolici;
    }
}
