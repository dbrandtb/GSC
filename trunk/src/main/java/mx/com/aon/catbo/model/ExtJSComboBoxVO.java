package mx.com.aon.catbo.model;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

public class ExtJSComboBoxVO extends ExtJSAtributosVO{
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
		private int maxHeight;
		
		protected JSONFunction onSelect;
		protected JSONObject listeners;
		
		private String lazyRender;
		private String tpl;

		
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


		/*public String toString () {
			String jsonObject = JSONObject.fromObject(this).toString();
			
			if (!StringUtils.isBlank(this.store)) {
				String jsonResult1 = "\"store\":\"" + this.getStore() + "\"";
				String jsonResult2 = "store:" + this.getStore();
				String result = jsonObject.replace(jsonResult1, jsonResult2);
				jsonResult1 = "\"true\"";
				jsonResult2 = "true";
				result = result.replace(jsonResult1, jsonResult2);
				jsonResult1 = "\"false\"";
				jsonResult2 = "false";
				result = result.replace(jsonResult1, jsonResult2);
				return result;
			}
			return jsonObject;
		}*/
		/**
		 * 
		 */
		@Override
		public String toString() {
			
			super.toString();

			JSONObject jsonResult = JSONObject.fromObject(this);
			
			if( listeners == null ){
				jsonResult.remove("listeners");
			}
			

			if( StringUtils.isBlank(this.displayField) ){
				jsonResult.remove("displayField");
			}

			if( StringUtils.isBlank( this.hiddenName ) ){
				jsonResult.remove("hiddenName");
			}

			if( StringUtils.isBlank( this.mode ) ){
				jsonResult.remove("mode");
			}

			if( StringUtils.isBlank(this.triggerAction) ){
				jsonResult.remove("triggerAction");
			}

			if( StringUtils.isBlank( this.valueField ) ){
				jsonResult.remove("valueField");
			}
			
			if( StringUtils.isBlank( this.tpl ) ){
				jsonResult.remove("tpl");
			}

			if( this.onSelect == null){
				jsonResult.remove("onSelect");
			}

			if (StringUtils.isBlank(this.store)) {
				jsonResult.remove("store");
				return jsonResult.toString();
			}else {
				if (JSONUtils.mayBeJSON(this.store) ){
					return jsonResult.toString();
				}else{
					StringBuilder jsonResult1 = new StringBuilder().append("\"store\":\"").append(this.store).append("\"");
					StringBuilder jsonResult2 = new StringBuilder().append("\"store\":").append(this.store);        		

					return jsonResult.toString().replace(jsonResult1.toString(), jsonResult2.toString());
				}
			}

		}


		public String getTpl() {
			return tpl;
		}


		public void setTpl(String tpl) {
			this.tpl = tpl;
		}


		public JSONFunction getOnSelect() {
			return onSelect;
		}


		public void setOnSelect(JSONFunction onSelect) {
			this.onSelect = onSelect;
		}


		public JSONObject getListeners() {
			return listeners;
		}


		public void setListeners(JSONObject listeners) {
			this.listeners = listeners;
		}


		public int getMaxHeight() {
			return maxHeight;
		}


		public void setMaxHeight(int maxHeight) {
			this.maxHeight = maxHeight;
		}


}
