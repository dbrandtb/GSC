package mx.com.aon.configurador.pantallas.model;

import java.util.List;

import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;

public class RamaMasterVO extends RamaPantallaVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3529237943409505107L;
	
	private JSONObject item;
	
	private JSONFunction nestedItem;
	
	
	private String otro;
	
	
	private Object[] children;
//	
//	
	public Object[] getChildren() {
		return children;
	}
//
//
	public void setChildren(Object[] groupList) {
		this.children = groupList;
	}


	public JSONFunction getNestedItem() {
		return nestedItem;
	}


	public void setNestedItem(JSONFunction nestedItem) {
		this.nestedItem = nestedItem;
	}


	
	
	
	
	
	public String getOtro() {
		return otro;
	}


	public void setOtro(String otro) {
		this.otro = otro;
	}


	private JSONFunction nestedItems;


	public JSONObject getItem() {
		return item;
	}


	public void setItem(JSONObject item) {
		this.item = item;
	}


	public JSONFunction getNestedItems() {
		return nestedItems;
	}


	public void setNestedItems(JSONFunction nestedItems) {
		this.nestedItems = nestedItems;
	}
	
	
	
	
	

}
