package mx.com.aon.catbo.model;

import net.sf.json.JSONObject;

public class ExtJSAtributosVO{

	private String cdFormatoOrden;
	private String dsFomatoOrden;
	private String cdSeccion;
	private String dsSeccion;
	private String dsAtribu;
	private String cdAgrupa;
	private String cdBloque;
	private String dsBloque;
	private String cdCampo;
	private String dsCampo;
	private String swFormat;
	private String cdExpres;
	private String nmOrden;
	
	//Agregados, ver despues si borrar
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
	private String disabled;
	
	
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
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
		this.cdAtribu = cdAtribu;
	}
	public String getMinLength() {
		return minLength;
	}
	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}
	public String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
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
	public String getFgObligatorio() {
		return fgObligatorio;
	}
	public void setFgObligatorio(String fgObligatorio) {
		this.fgObligatorio = fgObligatorio;
	}
	public String getReadOnly() {
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
	}
	public String getXtype() {
		return xtype;
	}
	public String getCdFormatoOrden() {
		return cdFormatoOrden;
	}
	public void setCdFormatoOrden(String cdFormatoOrden) {
		this.cdFormatoOrden = cdFormatoOrden;
	}
	public String getDsFomatoOrden() {
		return dsFomatoOrden;
	}
	public void setDsFomatoOrden(String dsFomatoOrden) {
		this.dsFomatoOrden = dsFomatoOrden;
	}
	public String getCdSeccion() {
		return cdSeccion;
	}
	public void setCdSeccion(String cdSeccion) {
		this.cdSeccion = cdSeccion;
	}
	public String getDsSeccion() {
		return dsSeccion;
	}
	public void setDsSeccion(String dsSeccion) {
		this.dsSeccion = dsSeccion;
	}
	public String getDsAtribu() {
		return dsAtribu;
	}
	public void setDsAtribu(String dsAtribu) {
		this.dsAtribu = dsAtribu;
	}
	public String getCdBloque() {
		return cdBloque;
	}
	public void setCdBloque(String cdBloque) {
		this.cdBloque = cdBloque;
	}
	public String getDsBloque() {
		return dsBloque;
	}
	public void setDsBloque(String dsBloque) {
		this.dsBloque = dsBloque;
	}
	public String getCdCampo() {
		return cdCampo;
	}
	public void setCdCampo(String cdCampo) {
		this.cdCampo = cdCampo;
	}
	public String getDsCampo() {
		return dsCampo;
	}
	public void setDsCampo(String dsCampo) {
		this.dsCampo = dsCampo;
	}
	public String getSwFormat() {
		return swFormat;
	}
	public void setSwFormat(String swFormat) {
		this.swFormat = swFormat;
	}
	public String getCdExpres() {
		return cdExpres;
	}
	public void setCdExpres(String cdExpres) {
		this.cdExpres = cdExpres;
	}
	public String getNmOrden() {
		return nmOrden;
	}
	public void setNmOrden(String nmOrden) {
		this.nmOrden = nmOrden;
	}
	
	/*public void setXtype(String xtype){
		if(this.getOtTabVal().equals("")){
			this.setXtype("textfield");
		}else{
			this.setXtype("combo");
		}		
	}*/
	
	public String toString () {
		String jsonObject = JSONObject.fromObject(this).toString();
		
		return jsonObject;
	}
	public void setXtype(String xtype) {
		this.xtype = xtype;
	}
	public String getCdAgrupa() {
		return cdAgrupa;
	}
	public void setCdAgrupa(String cdAgrupa) {
		this.cdAgrupa = cdAgrupa;
	}

}
