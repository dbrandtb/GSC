/**
 * 
 */
package mx.com.aon.portal.model.opcmenuusuario;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author eflores
 * @date 28/05/2008
 *
 */
public class ProductoClienteVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String cdTituloCliente;
    
    private String dsTituloCliente;
   
    /**
     * @return the cdTituloCliente
     */
    public String getCdTituloCliente() {
        return cdTituloCliente;
    }

    /**
     * @param cdTituloCliente the cdTituloCliente to set
     */
    public void setCdTituloCliente(String cdTituloCliente) {
        this.cdTituloCliente = cdTituloCliente;
    }

    /**
     * @return the dsTituloCliente
     */
    public String getDsTituloCliente() {
        return dsTituloCliente;
    }

    /**
     * @param dsTituloCliente the dsTituloCliente to set
     */
    public void setDsTituloCliente(String dsTituloCliente) {
        this.dsTituloCliente = dsTituloCliente;
    }

    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
