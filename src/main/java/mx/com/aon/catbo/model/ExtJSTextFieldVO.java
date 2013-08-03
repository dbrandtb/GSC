package mx.com.aon.catbo.model;

import net.sf.json.JSONObject;

public class ExtJSTextFieldVO extends ExtJSFieldVO{
	private String maxLength;

	
	public String toString() {
		String jsonObject = JSONObject.fromObject(this).toString();
		
		return jsonObject;
	}
}
