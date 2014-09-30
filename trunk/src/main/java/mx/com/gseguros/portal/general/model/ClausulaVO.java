package mx.com.gseguros.portal.general.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ClausulaVO implements Serializable {

	private static final long serialVersionUID = 2582531730846897501L;
	
	/**
	 * Clave de la clausula
	 */
	private String cdclausu;
	
	/**
	 * Descripcion de la clausula
	 */
	private String dsclausu;
	
	/**
	 * Tipo de Clausula
	 */
	private String cdtipcla;
	
	/**
	 * Estatus de la exclusion
	 */
	private String status;
	
	/**
	 * Contenido de la clausula
	 */
	private String contenidoClausula;
	
	//private String swmodi; 
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	
	// Getters and setters:
	
	public String getCdclausu() {
		return cdclausu;
	}

	public void setCdclausu(String cdclausu) {
		this.cdclausu = cdclausu;
	}

	public String getDsclausu() {
		return dsclausu;
	}

	public void setDsclausu(String dsclausu) {
		this.dsclausu = dsclausu;
	}

	public String getCdtipcla() {
		return cdtipcla;
	}

	public void setCdtipcla(String cdtipcla) {
		this.cdtipcla = cdtipcla;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContenidoClausula() {
		return contenidoClausula;
	}

	public void setContenidoClausula(String contenidoClausula) {
		this.contenidoClausula = contenidoClausula;
	}

}