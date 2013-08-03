/**
 * 
 */
package mx.com.aon.kernel.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import mx.com.ice.services.to.Campo;

/**
 * @author eflores
 * @date 26/09/2008
 *
 */
public class ValoresXDefectoCoberturaVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String ptCapita;
    
    private String valorCob;
    
    private String tipo;
    
    private String nombre;
    
    private String nombreAtributo;
    
    private String cobertura;

    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the ptCapita
     */
    public String getPtCapita() {
        return ptCapita;
    }

    /**
     * @param ptCapita the ptCapita to set
     */
    public void setPtCapita(String ptCapita) {
        this.ptCapita = ptCapita;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the valorCob
     */
    public String getValorCob() {
        return valorCob;
    }

    /**
     * @param valorCob the valorCob to set
     */
    public void setValorCob(String valorCob) {
        this.valorCob = valorCob;
    }

    /**
     * @return the cobertura
     */
    public String getCobertura() {
        return cobertura;
    }

    /**
     * @param cobertura the cobertura to set
     */
    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    /**
     * @return the nombreAtributo
     */
    public String getNombreAtributo() {
        return nombreAtributo;
    }

    /**
     * @param nombreAtributo the nombreAtributo to set
     */
    public void setNombreAtributo(String nombreAtributo) {
        this.nombreAtributo = nombreAtributo;
    }
}
