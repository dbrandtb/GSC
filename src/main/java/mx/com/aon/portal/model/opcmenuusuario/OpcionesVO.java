/**
 * 
 */
package mx.com.aon.portal.model.opcmenuusuario;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author eflores
 * @date 30/05/2008
 *
 */
public class OpcionesVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String cdTitulo;
    
    private String dsTitulo;

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
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
