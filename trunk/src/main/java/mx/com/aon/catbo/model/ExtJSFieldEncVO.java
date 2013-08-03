package mx.com.aon.catbo.model;

import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;

public class ExtJSFieldEncVO {
	private String xtype;
	private String id;
	private String name;
	private String fieldLabel;
	private String value;
	private String allowBlank;
	private String anchor;
	private String minLength;
	private String maxLength;
	private String width;
	private String readOnly;
	private String hidden;
	private String cdSecuencia;
	private String otValor;
	
	private String onChange;
	
	private String disabled;

	public String getXtype() {
		return xtype;
	}


	public void setXtype(String xtype) {
		if (xtype.equals("TEXT")) {
			this.xtype = "textfield";
		}
		if (xtype.equals("LIST")) {
			this.xtype = "combo";
		}
		if (xtype.equals("FECHA")) {
			this.xtype = "datefield";
		}
		if (xtype.equals("NUMBER")) {
			this.xtype = "numberfield";
		}
		if(xtype.equals("HIDDEN")){
			this.xtype ="hidden";
		}
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFieldLabel() {
		return fieldLabel;
	}


	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getAllowBlank() {
		return allowBlank;
	}


	public void setAllowBlank(String allowBlank) {
		this.allowBlank = allowBlank;
	}


	public String getAnchor() {
		return anchor;
	}


	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}


	public String getMinLength() {
		return minLength;
	}


	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}


	public String getWidth() {
		return width;
	}


	public void setWidth(String width) {
		this.width = width;
	}


	public String toString () {
		String jsonObject = JSONObject.fromObject(this).toString();

		String jsonResult1 = "\"onChange\":\"" + this.getOnChange() + "\"";
		String jsonResult2 = "onChange:" + this.getOnChange();
		String result = jsonObject.replace(jsonResult1, jsonResult2);

		if (!xtype.equals("datefield")) {
			String str = "\"allowBlank\":\"" + this.allowBlank + "\"";
			String rep = "allowBlank:" + this.allowBlank;
			String res = result.replace(str, rep);
			return res;
		}
		return result.toString();
	}


	public String getMaxLength() {
		return maxLength;
	}


	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}


	public String isReadOnly() {
		return readOnly;
	}


	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}



	public String getReadOnly() {
		return readOnly;
	}


	public String getHidden() {
		return hidden;
	}


	public void setHidden(String hidden) {
		this.hidden = hidden;
	}


	public String getOnChange() {
		return onChange;
	}


	public void setOnChange(String onChange) {
		//String jsonObject = JSONObject.fromObject(this).toString();
		this.onChange = onChange;

		String jsonResult1 = "\"onChange\":\"" + onChange + "\"";
		String jsonResult2 = "onChange:" + onChange;
		String result = onChange.replace(jsonResult1, jsonResult2);//jsonObject.replace(jsonResult1, jsonResult2);

		
		
		this.onChange = result;
	}


	public String getCdSecuencia() {
		return cdSecuencia;
	}


	public void setCdSecuencia(String cdSecuencia) {
		this.cdSecuencia = cdSecuencia;
	}


	public String getOtValor() {
		return otValor;
	}


	public void setOtValor(String otValor) {
		this.otValor = otValor;
	}


	public String getDisabled() {
		return disabled;
	}


	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	
/*
	public JSONFunction getOnChange() {
		return onChange;
	}


	public void setOnChange(JSONFunction onChange) {
		this.onChange = onChange;
	}
*/

	
}