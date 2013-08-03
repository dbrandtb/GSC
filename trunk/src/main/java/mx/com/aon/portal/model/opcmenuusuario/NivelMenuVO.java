/**
 * 
 */
package mx.com.aon.portal.model.opcmenuusuario;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author eflores
 * @date 29/05/2008
 *
 */
public class NivelMenuVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String cdNivel;
    
    private String dsMenuEst;    

    /**
     * @return the dsMenuEst
     */
    public String getDsMenuEst() {
        return dsMenuEst;
    }

    /**
     * @param dsMenuEst the dsMenuEst to set
     */
    public void setDsMenuEst(String dsMenuEst) {
        this.dsMenuEst = dsMenuEst;
    }
    
    /**
     * @return the cdNivel
     */
    public String getCdNivel() {
        return cdNivel;
    }

    /**
     * @param cdNivel the cdNivel to set
     */
    public void setCdNivel(String cdNivel) {
        this.cdNivel = cdNivel;
    }

    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
