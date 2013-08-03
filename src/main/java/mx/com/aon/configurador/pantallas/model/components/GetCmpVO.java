/**
 * 
 */
package mx.com.aon.configurador.pantallas.model.components;

import java.io.Serializable;

/**
 * Clase Value Object con los atributos necesarios para la generacion de la sentencia Ext.getCmp('idComponente').getValue()
 * 
 * @author aurora.lozada
 * 
 */
public class GetCmpVO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3407698893636252261L;
    
    private String idComponent;

   
    // Constructors

    @Override
    public String toString() {

        StringBuffer bufferCmp = new StringBuffer();
        bufferCmp.append("Ext.getCmp('").append(this.idComponent).append("').getValue()");
        
        return bufferCmp.toString();

    }

    public GetCmpVO() {

    }

    public GetCmpVO(String idComponent) {
        this.idComponent = idComponent;
    
    }
    
    // Getters y setters

    /**
     * @return the idComponent
     */
    public String getIdComponent() {
        return idComponent;
    }

    /**
     * @param idComponent the idComponent to set
     */
    public void setIdComponent(String idComponent) {
        this.idComponent = idComponent;
    }

    
}
