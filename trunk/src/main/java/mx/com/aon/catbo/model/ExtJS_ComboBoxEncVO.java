package mx.com.aon.catbo.model;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

public class ExtJS_ComboBoxEncVO extends ExtJSFieldEncVO{
		private String empyText;
		private String typeAhead;
		private String forceSelection;
		private String displayField;
		private String valueField;
		private String hiddenName;
		private String triggerAction;
		private String mode;
		private String labelSeparator;
		private String selectOnFocus;
		private String store;
		private String lazyRender;
		private String tpl;
		private String cdColumna;
		private String nroOrdenClave;
		private String name;

		private String onSelect;
		
		public String getEmpyText() {
			return empyText;
		}


		public void setEmpyText(String empyText) {
			this.empyText = empyText;
		}


		public String getTypeAhead() {
			return typeAhead;
		}


		public void setTypeAhead(String typeAhead) {
			this.typeAhead = typeAhead;
		}


		public String getForceSelection() {
			return forceSelection;
		}


		public void setForceSelection(String forceSelection) {
			this.forceSelection = forceSelection;
		}


		public String getDisplayField() {
			return displayField;
		}


		public void setDisplayField(String displayField) {
			this.displayField = displayField;
		}


		public String getValueField() {
			return valueField;
		}


		public void setValueField(String valueField) {
			this.valueField = valueField;
		}


		public String getHiddenName() {
			return hiddenName;
		}


		public void setHiddenName(String hiddenName) {
			this.hiddenName = hiddenName;
		}


		public String getTriggerAction() {
			return triggerAction;
		}


		public void setTriggerAction(String triggerAction) {
			this.triggerAction = triggerAction;
		}


		public String getMode() {
			return mode;
		}


		public void setMode(String mode) {
			this.mode = mode;
		}


		public String getLabelSeparator() {
			return labelSeparator;
		}


		public void setLabelSeparator(String labelSeparator) {
			this.labelSeparator = labelSeparator;
		}


		public String getSelectOnFocus() {
			return selectOnFocus;
		}


		public void setSelectOnFocus(String selectOnFocus) {
			this.selectOnFocus = selectOnFocus;
		}


		public String getStore() {
			return store;
		}


		public void setStore(String store) {
			this.store = store;
		}


		public String getLazyRender() {
			return lazyRender;
		}


		public void setLazyRender(String lazyRender) {
			this.lazyRender = lazyRender;
		}


		public String toString () {
			String jsonObject = JSONObject.fromObject(this).toString();
			
			if (!StringUtils.isBlank(this.store)) {
				String jsonResult1 = "\"store\":\"" + this.getStore() + "\"";
				String jsonResult2 = "store:" + this.getStore();
				String result = jsonObject.replace(jsonResult1, jsonResult2);
				
				jsonResult1 = "\"hidden\":\""+ this.getHidden() + "\"";
				jsonResult2 = "hidden:" + this.getHidden();
				result = result.replace(jsonResult1, jsonResult2);
				return result;
			}
			return jsonObject;
		}


		public String getTpl() {
			return tpl;
		}


		public void setTpl(String tpl) {
			this.tpl = tpl;
		}

		public String getCdColumna() {
			return cdColumna;
		}


		public void setCdColumna(String cdColumna) {
			this.cdColumna = cdColumna;
		}


		public String getNroOrdenClave() {
			return nroOrdenClave;
		}


		public void setNroOrdenClave(String nroOrdenClave) {
			this.nroOrdenClave = nroOrdenClave;
		}


		public String getOnSelect() {
			return onSelect;
		}


		public void setOnSelect(String onSelect) {
			//String jsonObject = JSONObject.fromObject(this).toString();
			this.onSelect = onSelect;

			String jsonResult1 = "\"onSelect\":\"" + onSelect + "\"";
			String jsonResult2 = "onSelect:" + onSelect;
			String result = onSelect.replace(jsonResult1, jsonResult2);//jsonObject.replace(jsonResult1, jsonResult2);

			
			
			this.onSelect = result;
		}			
		

		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}
		
		
}
