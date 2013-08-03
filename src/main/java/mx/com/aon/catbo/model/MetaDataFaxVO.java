package mx.com.aon.catbo.model;

import java.util.List;

import mx.com.aon.portal.model.FormConfigVO;

public class MetaDataFaxVO {
	private FormConfigVO formConfig;
	private List<ExtJSAtributosFaxVO> fields;

	public FormConfigVO getFormConfig() {
		return formConfig;
	}
	public void setFormConfig(FormConfigVO formConfig) {
		this.formConfig = formConfig;
	}
	public List<ExtJSAtributosFaxVO> getFields() {
		return fields;
	}
	public void setFields(List<ExtJSAtributosFaxVO> fields) {
		this.fields = fields;
	}
	
}