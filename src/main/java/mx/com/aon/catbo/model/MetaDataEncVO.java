package mx.com.aon.catbo.model;

import java.util.List;

import mx.com.aon.portal.model.FormConfigVO;

public class MetaDataEncVO {
	private FormConfigVO formConfig;
	private List<ExtJSFieldEncVO> fields;

	public FormConfigVO getFormConfig() {
		return formConfig;
	}
	public void setFormConfig(FormConfigVO formConfig) {
		this.formConfig = formConfig;
	}
	public List<ExtJSFieldEncVO> getFields() {
		return fields;
	}
	public void setFields(List<ExtJSFieldEncVO> fields) {
		this.fields = fields;
	}
	
}