package mx.com.aon.configurador.pantallas.model.controls;

import java.util.List;

@Deprecated
public class SectionControl {

	private String dsBloque;
	
	private String cdBloque;
	
	private List<FieldControl> fieldList;

	/**
	 * @return the dsBloque
	 */
	public String getDsBloque() {
		return dsBloque;
	}

	/**
	 * @param dsBloque the dsBloque to set
	 */
	public void setDsBloque(String dsBloque) {
		this.dsBloque = dsBloque;
	}

	/**
	 * @return the cdBloque
	 */
	public String getCdBloque() {
		return cdBloque;
	}

	/**
	 * @param cdBloque the cdBloque to set
	 */
	public void setCdBloque(String cdBloque) {
		this.cdBloque = cdBloque;
	}

	/**
	 * @return the fieldList
	 */
	public List<FieldControl> getFieldList() {
		return fieldList;
	}

	/**
	 * @param fieldList the fieldList to set
	 */
	public void setFieldList(List<FieldControl> fieldList) {
		this.fieldList = fieldList;
	}
	
	
}
