package mx.com.aon.catbo.model;

import net.sf.json.JSONObject;

public class ExtJSFieldVO {
	private String xtype;
	private String id;
	private String name;
	private String fieldLabel;
	private String value;
	private String allowBlank;
	private String anchor;
	private String cdAtribu;
	private String minLength;
	private String maxLength;
	private String width;
	private String otTabVal;
	private String swLlave;
	private String fgObligatorio;
	private String readOnly;
	private String permitirBlancos;
	
	public String getXtype() {
		return xtype;
	}


	public void setXtype(String xtype) {
		if (otTabVal.equals("")) {
			this.xtype = "";
		}else{
			xtype = "combo";
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


	public String getCdAtribu() {
		return cdAtribu;
	}


	public void setCdAtribu(String cdAtribu) {
		if (Integer.parseInt(cdAtribu) <= 10) {
			this.cdAtribu = "0" + cdAtribu;
		}else {
			this.cdAtribu = cdAtribu;
		}
	}

/*
	public String getMaxLength() {
		return maxLength;
	}


	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

*/
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
		
		return jsonObject;
	}


	public String getOtTabVal() {
		return otTabVal;
	}


	public void setOtTabVal(String otTabVal) {
		this.otTabVal = otTabVal;
	}


	public String getSwLlave() {
		return swLlave;
	}


	public void setSwLlave(String swLlave) {
		this.swLlave = swLlave;
	}


	public String getMaxLength() {
		return maxLength;
	}


	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}


	public String getFgObligatorio() {
		return fgObligatorio;
	}


	public void setFgObligatorio(String fgObligatorio) {
		this.fgObligatorio = fgObligatorio;
	}


	public String isReadOnly() {
		return readOnly;
	}


	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}


	public String getPermitirBlancos() {
		return permitirBlancos;
	}


	public void setPermitirBlancos(String permitirBlancos) {
		this.permitirBlancos = permitirBlancos;
		if (this.permitirBlancos == null  || this.permitirBlancos.equals("") || this.permitirBlancos.equals("N")) {
			this.allowBlank = "false";
		} else {
			this.allowBlank = "true";
		}
	}


	public String getReadOnly() {
		return readOnly;
	}
}