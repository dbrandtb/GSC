package mx.com.aon.portal.model;

import net.sf.json.JSONObject;

public class JSONRecordReaderVO {
	private String name;
	private String type;
	private String mapping;
	private String swLlave;

	public String toString () {
		String jsonResult = JSONObject.fromObject(this).toString();
		
		return jsonResult;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMapping() {
		return mapping;
	}
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public String getSwLlave() {
		return swLlave;
	}

	public void setSwLlave(String swLlave) {
		this.swLlave = swLlave;
	}
}
