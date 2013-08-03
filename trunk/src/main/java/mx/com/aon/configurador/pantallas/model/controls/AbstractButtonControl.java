package mx.com.aon.configurador.pantallas.model.controls;

import java.io.Serializable;

import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;

/**
 * 
 * @author Leopoldo Ramirez Montes
 *
 */
@Deprecated
public abstract class AbstractButtonControl implements ExtElement, Serializable {
	
	/**
	 * 
	 */
	protected String id;
	
	/**
	 * 
	 */
	protected String xtype;
	
	/**
	 * 
	 */
	protected String text;
	
	/**
	 * 
	 */
	protected JSONFunction handler;
	
	
	/**
	 * 
	 */
	@Override
	public String toString() {		
		JSONObject json = JSONObject.fromObject(this);
		return json.toString();
	}
	
	
	/**
	 * @return the handler
	 */
	public JSONFunction getHandler() {
		return handler;
	}
	
	/**
	 * @param handler the handler to set
	 */
	public void setHandler(JSONFunction handler) {
		this.handler = handler;
	}
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
	public void setId(String id) {
		this.id = id;
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
	
		
	
}
