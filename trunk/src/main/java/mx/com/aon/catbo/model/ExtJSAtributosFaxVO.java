package mx.com.aon.catbo.model;

import net.sf.json.JSONObject;

public class ExtJSAtributosFaxVO{

	private String cdFormatoOrden;
	private String dsFomatoOrden;
	private String cdSeccion;
	private String dsSeccion;
	private String dsAtribu;
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
	
	//Agregados para la pantalla Administracion de Fax
	
	private String cdtipoar;
	private String dsarchivo;
	private String cdatribu;
	private String dsTabla;
	private String nmfax;
	private String nmpoliex;
	private String nmcaso;
	private String nmLmax;
	private String nmLmin;
	private String cdusuario;
	private String dsusuari;
	private String ferecepcion;
	private String feingreso;
	private String blarchivo;
	private String otvalor;
	private String swObliga;
	private String status;
	private String dstipoar;
	private String nmsituac;
	private String formato;
	private String dsvalor;
	
	
	
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
	public String getCdtipoar() {
		return cdtipoar;
	}
	public void setCdtipoar(String cdtipoar) {
		this.cdtipoar = cdtipoar;
	}
	public String getDsarchivo() {
		return dsarchivo;
	}
	public void setDsarchivo(String dsarchivo) {
		this.dsarchivo = dsarchivo;
	}
	public String getCdatribu() {
		return cdatribu;
	}
	public void setCdatribu(String cdatribu) {
		this.cdatribu = cdatribu;
	}
	
	public String getNmLmin() {
		return nmLmin;
	}
	public void setNmLmin(String nmLmin) {
		this.nmLmin = nmLmin;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public String getDsTabla() {
		return dsTabla;
	}
	public void setDsTabla(String dsTabla) {
		this.dsTabla = dsTabla;
	}
	public String getNmfax() {
		return nmfax;
	}
	public void setNmfax(String nmfax) {
		this.nmfax = nmfax;
	}
	public String getNmpoliex() {
		return nmpoliex;
	}
	public void setNmpoliex(String nmpoliex) {
		this.nmpoliex = nmpoliex;
	}
	public String getNmcaso() {
		return nmcaso;
	}
	public void setNmcaso(String nmcaso) {
		this.nmcaso = nmcaso;
	}
	public String getNmLmax() {
		return nmLmax;
	}
	public void setNmLmax(String nmLmax) {
		this.nmLmax = nmLmax;
	}
	public String getCdusuario() {
		return cdusuario;
	}
	public void setCdusuario(String cdusuario) {
		this.cdusuario = cdusuario;
	}
	public String getDsusuari() {
		return dsusuari;
	}
	public void setDsusuari(String dsusuari) {
		this.dsusuari = dsusuari;
	}
	public String getFerecepcion() {
		return ferecepcion;
	}
	public void setFerecepcion(String ferecepcion) {
		this.ferecepcion = ferecepcion;
	}
	public String getFeingreso() {
		return feingreso;
	}
	public void setFeingreso(String feingreso) {
		this.feingreso = feingreso;
	}
	public String getBlarchivo() {
		return blarchivo;
	}
	public void setBlarchivo(String blarchivo) {
		this.blarchivo = blarchivo;
	}
	public String getOtvalor() {
		return otvalor;
	}
	public void setOtvalor(String otvalor) {
		this.otvalor = otvalor;
	}
	public String getSwObliga() {
		return swObliga;
	}
	public void setSwObliga(String swObliga) {
		this.swObliga = swObliga;
	}
	public String getDstipoar() {
		return dstipoar;
	}
	public void setDstipoar(String dstipoar) {
		this.dstipoar = dstipoar;
	}
	public String getNmsituac() {
		return nmsituac;
	}
	public void setNmsituac(String nmsituac) {
		this.nmsituac = nmsituac;
	}
	public String getDsvalor() {
		return dsvalor;
	}
	public void setDsvalor(String dsvalor) {
		this.dsvalor = dsvalor;
	}

	
	
}
