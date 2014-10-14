package mx.com.aon.portal2.web;

import java.io.Serializable;

/**
 *
 * @author Jair
 */
public class GenericVO implements Serializable {
    
	private static final long serialVersionUID = -5060990997960225913L;
	
	private String key;
    private String value;
    
    public GenericVO(){}
    
    public GenericVO(String key,String value)
    {
        this.key=key;
        this.value=value;
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
    	return new StringBuilder(this.key).append(" - ").append(this.value).toString();
    }
    
}
