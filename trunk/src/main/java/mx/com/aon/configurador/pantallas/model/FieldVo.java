package mx.com.aon.configurador.pantallas.model;

import java.io.Serializable;


import mx.com.aon.configurador.pantallas.model.controls.ExtElement;

/**
 * FieldVo
 * 
 * 
 *  Clase Value Object para los atributos del master 
 *  NOTA:esta repetida dentro de la carpeta master
 * 
 * @author  aurora.lozada
 * 
 */
@Deprecated
public class FieldVo implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 4889426963563281837L;

    private String dsField;    
    
    private String cdField;
    
    private String visible;
    
    private String campoPadre;
    
    private String agrupador;
    
    private String ordenAgrupacion;
    
    private ExtElement controlItem;
    
    
    public String getDsField() {
        return dsField;
    }
    public void setDsField(String dsField) {
        this.dsField = dsField;
    }
    public String getCdField() {
        return cdField;
    }
    public void setCdField(String cdField) {
        this.cdField = cdField;
    }
    
	/**
	 * @return the controlItem
	 */
	public ExtElement getControlItem() {
		return controlItem;
	}
	/**
	 * @param controlItem the controlItem to set
	 */
	public void setControlItem(ExtElement controlItem) {
		this.controlItem = controlItem;
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

}
