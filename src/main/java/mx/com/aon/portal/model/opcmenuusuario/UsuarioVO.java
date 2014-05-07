/**
 * 
 */
package mx.com.aon.portal.model.opcmenuusuario;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author eflores
 * @date 26/05/2008
 *
 */
public class UsuarioVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String cdUsuario;
    
    private String dsUsuario;

    /**
     * @return the cdUsuario
     */
    public String getCdUsuario() {
        return cdUsuario;
    }

    /**
     * @param cdUsuario the cdUsuario to set
     */
    public void setCdUsuario(String cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    /**
     * @return the dsUsuario
     */
    public String getDsUsuario() {
        return dsUsuario;
    }

    /**
     * @param dsUsuario the dsUsuario to set
     */
    public void setDsUsuario(String dsUsuario) {
        this.dsUsuario = dsUsuario;
    }
    
    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
