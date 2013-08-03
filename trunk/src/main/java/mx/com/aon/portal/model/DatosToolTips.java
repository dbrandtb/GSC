package mx.com.aon.portal.model;

public class DatosToolTips {
	private String clave;
	private String ayuda;
	private String fieldLabel;
	private String tooltip;
	public String getAyuda() {
		return ayuda;
	}
	public void setAyuda(String ayuda) {
		this.ayuda = ayuda;
	}
	public String getFieldLabel() {
		return fieldLabel;
	}
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("helpMap.put('").append(clave).append("', {");
		stringBuffer.append("fieldLabel: '").append(fieldLabel).append("', ");
		stringBuffer.append("ayuda: '").append(ayuda).append("', ");
		stringBuffer.append("tooltip: '").append(tooltip).append("'});\n");
		
		return stringBuffer.toString();
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
}
