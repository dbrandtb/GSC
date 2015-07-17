package mx.com.aon.portal2.web;

import java.io.Serializable;

import mx.com.gseguros.utils.Utils;

/**
 *
 * @author Jair
 */
public class GenericVO implements Serializable {
    
	private static final long serialVersionUID = -5060990997960225913L;
	
	private String key;
    private String value;
    private String aux;
    
    public GenericVO(){}
    
    public GenericVO(String key,String value)
    {
        this.key   = key;
        this.value = value;
    }
    
    public GenericVO(String key,String value,String aux)
    {
        this.key   = key;
        this.value = value;
        this.aux   = aux;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String toString()
    {
    	return Utils.join("key=",key,", value=",value,", aux=",aux);
    }

	public String getAux() {
		return aux;
	}

	public void setAux(String aux) {
		this.aux = aux;
	}
    
}
