package mx.com.aon.configurador.pantallas.model;

import net.sf.json.JSONObject;



public class FieldSet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private boolean autoHeight ;	
	
	/**
	 * 
	 */
	private String xtype;
	
	

	
	public String getXtype() {
		return xtype;
	}



	public void setXtype(String xtype) {
		this.xtype = xtype;
	}



	@Override
	public String toString() {
		JSONObject json = JSONObject.fromObject(this);
		return json.toString();
	}
	
	
	
	public boolean isAutoHeight() {
		return autoHeight;
	}


	public void setAutoHeight(boolean autoHeight) {
		this.autoHeight = autoHeight;
	}


	private Object[] items;


	public Object[] getItems() {
		return items;
	}


	public void setItems(Object[] items) {
		this.items = items;
	}
	
	

}
