package mx.com.aon.configurador.pantallas.model;

import java.io.Serializable;

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
public class PropertyVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1154596139394016429L;
	
	private String dsProperty;
	private String otvalor;
	private String swModif;
	private String cdProperty;
	
	public String getDsProperty() {
		return dsProperty;
	}
	public void setDsProperty(String dsProperty) {
		this.dsProperty = dsProperty;
	}
	public String getOtvalor() {
		return otvalor;
	}
	public void setOtvalor(String otvalor) {
		this.otvalor = otvalor;
	}
	public String getSwModif() {
		return swModif;
	}
	public void setSwModif(String swModif) {
		this.swModif = swModif;
	}
	public String getCdProperty() {
		return cdProperty;
	}
	public void setCdProperty(String cdProperty) {
		this.cdProperty = cdProperty;
	}
	
	
	

}
