/**
 * 
 */
package mx.com.aon.configurador.pantallas.model.components;

import java.io.Serializable;

/**
 * Clase Value Object con los atributos necesarios para la generacion de baseParams de los store
 * 
 * @author aurora.lozada
 * 
 */
public class BaseParamVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3783681463425873532L;

    private String name;

    private String value;

    // Constructors

    @Override
    public String toString() {

        StringBuffer bufferStore = new StringBuffer();
        bufferStore.append("{").append(this.name).append(": '").append(this.value).append("'}");

        return bufferStore.toString();

    }

    public BaseParamVO() {

    }

    public BaseParamVO(String name, String value) {
        this.name = name;
        this.value = value;

    }

    // Getters y setters

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
