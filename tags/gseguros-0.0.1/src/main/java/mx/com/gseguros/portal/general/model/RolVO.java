package mx.com.gseguros.portal.general.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.EmpresaVO;

/**
 * Rol
 * 
 * <pre>
 *   Action que atiende todas las peticiones de creacion, modificacion y consulta
 *   de datos relacionados con la definicion de un producto nuevo,
 *   como son: clausulas, periodos y el producto en si mismo.
 * &lt;Pre&gt;
 * 
 * author &lt;a href=&quot;mailto:alfonso.marquez@biosnetmx.com&quot;&gt;Alfonso M&amp;aacuterquez&lt;/a&gt;
 * &#064;version	 2.0
 * 
 * &#064;since	 1.0
 * 
 */
public class RolVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6843784051408138330L;

	private String codigoRol;
	private String descripcionRol;
	
	private BaseObjectVO objeto;
	private String condicion;
	private boolean activado;
	
	private List<EmpresaVO> empresas;

	
	/**
	 * @return the empresas
	 */
	public List<EmpresaVO> getEmpresas() {
		return empresas;
	}

	/**
	 * @param empresas the empresas to set
	 */
	public void setEmpresas(List<EmpresaVO> empresas) {
		this.empresas = empresas;
	}

	// Getters && Setters
	public BaseObjectVO getObjeto() {
		return objeto;
	}

	public void setObjeto(BaseObjectVO objeto) {
		this.objeto = objeto;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public boolean isActivado() {
		return activado;
	}

	public void setActivado(boolean activado) {
		this.activado = activado;
	}

	public String getCodigoRol() {
		return codigoRol;
	}

	public void setCodigoRol(String codigoRol) {
		this.codigoRol = codigoRol;
	}

	public String getDescripcionRol() {
		return descripcionRol;
	}

	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}
	
	/**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
	
}
