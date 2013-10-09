/**
 * 
 */
package mx.com.aon.configurador.pantallas.model.controls;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * @author eflores
 * @date 16/06/2008
 *
 */
@Deprecated
public abstract class AbstractMasterModelControl implements Serializable, ExtElement {
    
	/**
	 * 
	 */
    protected String name;
    
    /**
     * 
     */
    protected String xtype;
    
    /**
     * 
     */
    protected String fieldLabel;
    
    /**
     * 
     */
    protected String id;
    
    /**
     * 
     */
    protected String value;
    
    /**
	 * 
	 */
	public static final String NUMBER_FIELD_TYPE = "numberfield";
	
	/**
	 * 
	 */
	public static final String TEXT_AREA_TYPE = "textarea";
	
	/**
	 * 
	 */
	public static final String TEX_FIELD_TYPE = "textfield";
	
	/**
	 * 
	 */
	public static final String COMBO_TYPE ="combo";
	
	
	public static final String MULTISELECT_TYPE ="multiselect";
    
    
    /**
     * @return the fieldLabel
     */
    public String getFieldLabel() {
        return fieldLabel;
    }

    /**
     * @param fieldLabel the fieldLabel to set
     */
    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
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
     * @return the xtype
     */
    public String getXtype() {
        return xtype;
    }

    /**
     * @param xtype the xtype to set
     */
    public void setXtype(String xtype) {
        this.xtype = xtype;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId( String id ){    	
    	
    	if( id.startsWith("parameters.")   ){
        	//0: parameters   1: the id of the element    		
    		String[] idArray = id.split("[.]");
    		this.id = idArray[1];
    	}else{
    		this.id = id;
    	}
    	
    }

    /**
     * @return String
     */
    public String toString() {
        return JSONObject.fromObject(this).toString();
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
