package mx.com.aon.configurador.pantallas.model.controls;

import java.util.List;

@Deprecated
public class FieldControl {
	
	private String cdCampo;
	
	private String dsCampo;
	
	private ExtElement control;
	
	private String visible;
	
	private String campoPadre;
	
	private String agrupador;
	
	private String ordenAgrupacion;
	
	private List<ExtElement> fieldSetItems;
	

	/**
	 * @return the cdCampo
	 */
	public String getCdCampo() {
		return cdCampo;
	}

	/**
	 * @param cdCampo the cdCampo to set
	 */
	public void setCdCampo(String cdCampo) {
		this.cdCampo = cdCampo;
	}

	/**
	 * @return the dsCampo
	 */
	public String getDsCampo() {
		return dsCampo;
	}

	/**
	 * @param dsCampo the dsCampo to set
	 */
	public void setDsCampo(String dsCampo) {
		this.dsCampo = dsCampo;
	}

	/**
	 * @return the control
	 */
	public ExtElement getControl() {
		return control;
	}

	/**
	 * @param control the control to set
	 */
	public void setControl(ExtElement control) {
		this.control = control;
	}

	/**
	 * @return the visible
	 */
	public String getVisible() {
		return visible;
	}

	/**
	 * @param visible the visible to set
	 */
	public void setVisible(String visible) {
		this.visible = visible;
	}

	/**
	 * @return the campoPadre
	 */
	public String getCampoPadre() {
		return campoPadre;
	}

	/**
	 * @param campoPadre the campoPadre to set
	 */
	public void setCampoPadre(String campoPadre) {
		this.campoPadre = campoPadre;
	}

	/**
	 * @return the agrupador
	 */
	public String getAgrupador() {
		return agrupador;
	}

	/**
	 * @param agrupador the agrupador to set
	 */
	public void setAgrupador(String agrupador) {
		this.agrupador = agrupador;
	}

	/**
	 * @return the ordenAgrupacion
	 */
	public String getOrdenAgrupacion() {
		return ordenAgrupacion;
	}

	/**
	 * @param ordenAgrupacion the ordenAgrupacion to set
	 */
	public void setOrdenAgrupacion(String ordenAgrupacion) {
		this.ordenAgrupacion = ordenAgrupacion;
	}

	/**
	 * @return the fieldSetItems
	 */
	public List<ExtElement> getFieldSetItems() {
		return fieldSetItems;
	}

	/**
	 * @param fieldSetItems the fieldSetItems to set
	 */
	public void setFieldSetItems(List<ExtElement> fieldSetItems) {
		this.fieldSetItems = fieldSetItems;
	}
	
	

}
