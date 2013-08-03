/**
 * 
 */
package mx.com.aon.configurador.pantallas.model.components;

import java.io.Serializable;

/**
 * Clase Value Object con los atributos necesarios para la generacion de baseParams de un combo al llamar la funcion on select
 * 
 * @author aurora.lozada
 * 
 */
public class BaseParamComboOnSelectVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7511997326781174086L;

    private String storeVariable;
    
    private String name;

    private String idComponent;
    
    private String value;

    
    // Constructors

    @Override
    public String toString() {

        StringBuffer bufferParam = new StringBuffer();
        bufferParam.append(this.storeVariable).append(".baseParams['").append(this.name).append("'] =");
        if(this.idComponent!=null){
            bufferParam.append(new GetCmpVO(this.idComponent).toString());
        }
        if(this.value!=null){
            bufferParam.append("'").append(this.value).append("'");
        }
        
        return bufferParam.toString();

    }

    
    public BaseParamComboOnSelectVO() {

    }

    public BaseParamComboOnSelectVO(String storeVariable, String name, String idComponent, String value) {
        this.storeVariable = storeVariable;
        this.name = name;
        this.idComponent = idComponent;
        this.value = value;

    }

    // Getters y setters
    /**
     * @return the storeVariable
     */
    public String getStoreVariable() {
        return storeVariable;
    }

    /**
     * @param storeVariable the storeVariable to set
     */
    public void setStoreVariable(String storeVariable) {
        this.storeVariable = storeVariable;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

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


    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }


    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    
}
