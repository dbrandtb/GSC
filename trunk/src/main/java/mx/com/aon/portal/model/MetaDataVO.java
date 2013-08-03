package mx.com.aon.portal.model;

import java.util.List;

public class MetaDataVO {
	private FormConfigVO formConfig;
	private List<ExtJSFieldVO> fields;

	public FormConfigVO getFormConfig() {
		return formConfig;
	}
	public void setFormConfig(FormConfigVO formConfig) {
		this.formConfig = formConfig;
	}
	public List<ExtJSFieldVO> getFields() {
		return fields;
	}
	public void setFields(List<ExtJSFieldVO> fields) {
		this.fields = fields;
	}
}