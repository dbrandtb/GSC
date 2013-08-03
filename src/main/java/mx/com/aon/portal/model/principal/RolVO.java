package mx.com.aon.portal.model.principal;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
/**
 * 
 * @author sergio.ramirez
 *
 */
public class RolVO implements Serializable{

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -7014261752969320955L;
	private String cdConfigura;
    private String cdRol;
    private String dsRol;
    private String cdElemento;
    private String dsElemen;
    private String cdSeccion;
    private String dsSeccion;
    
    private String dsTipo;
    private String swHabilitado;
    private String dsArchivo;
    private String dsConfigura;
    private String especificacion;
    private String contenidoDato;
    private String contenidoDatoSec;
    private boolean habilitado;
    
    
    
    
    /**
     * 
     * @return
     */ 
    public boolean isHabilitado() {
		return habilitado;
	}
    /**
     * 
     * @param habilitado
     */
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	/**
     * 
     * @return
     */
    public String getDsTipo() {
		return dsTipo;
	}
    /**
     * 
     * @param dsTipo
     */
	public void setDsTipo(String dsTipo) {
		this.dsTipo = dsTipo;
	}
	/**
	 * 
	 * @return
	 */
	public String getSwHabilitado() {
		return swHabilitado;
	}
	/**
	 * 
	 * @param swHabilitado
	 */
	public void setSwHabilitado(String swHabilitado) {
		this.swHabilitado = swHabilitado;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsArchivo() {
		return dsArchivo;
	}
	/**
	 * 
	 * @param dsArchivo
	 */
	public void setDsArchivo(String dsArchivo) {
		this.dsArchivo = dsArchivo;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsConfigura() {
		return dsConfigura;
	}
	/**
	 * 
	 * @param dsConfigura
	 */
	public void setDsConfigura(String dsConfigura) {
		this.dsConfigura = dsConfigura;
	}
	/**
	 * 
	 * @return
	 */
	public String getEspecificacion() {
		return especificacion;
	}
	/**
	 * 
	 * @param especificacion
	 */
	public void setEspecificacion(String especificacion) {
		this.especificacion = especificacion;
	}
	/**
	 * 
	 * @return
	 */
	public String getCdConfigura() {
		return cdConfigura;
	}
	/**
	 * 
	 * @param cdConfigura
	 */
	public void setCdConfigura(String cdConfigura) {
		this.cdConfigura = cdConfigura;
	}
	/**
	 * 
	 * @return
	 */
	public String getCdRol() {
		return cdRol;
	}
	/**
	 * 
	 * @param cdRol
	 */
	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsRol() {
		return dsRol;
	}
	/**
	 * 
	 * @param dsRol
	 */
	public void setDsRol(String dsRol) {
		this.dsRol = dsRol;
	}
	/**
	 * 
	 * @return
	 */
	public String getCdElemento() {
		return cdElemento;
	}
	/**
	 * 
	 * @param cdElemento
	 */
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsElemen() {
		return dsElemen;
	}
	/**
	 * 
	 * @param dsElemen
	 */
	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}
	/**
	 * 
	 * @return
	 */
	public String getCdSeccion() {
		return cdSeccion;
	}
	/**
	 * 
	 * @param cdSeccion
	 */
	public void setCdSeccion(String cdSeccion) {
		this.cdSeccion = cdSeccion;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsSeccion() {
		return dsSeccion;
	}
	/**
	 * 
	 * @param dsSeccion
	 */
	public void setDsSeccion(String dsSeccion) {
		this.dsSeccion = dsSeccion;
	}
	/**
	 * 
	 * @return
	 */
	public String getContenidoDato() {
		return contenidoDato;
	}
	/**
	 * 
	 * @param contenidoDato
	 */
	public void setContenidoDato(String contenidoDato) {
		this.contenidoDato = contenidoDato;
	}
	/**
	 * 
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		
				.append("cdConfigura", cdConfigura )
				.append("cdRol",cdRol )
				.append("dsRol",dsRol)
				.append("cdElemento",cdElemento)
				.append("dsElemen",dsElemen)
				.append("cdSeccion",cdSeccion)
				.append("dsSeccion",dsSeccion)
				.append("dsTipo",dsTipo)
				.append("swHabilitado",swHabilitado)
				.append("dsArchivo",dsArchivo)
				.append("dsConfigura",dsConfigura)
				.append("contenidoDato",contenidoDato )
				.append("especificacion",especificacion).toString();
		

	}
	public String getContenidoDatoSec() {
		return contenidoDatoSec;
	}
	public void setContenidoDatoSec(String contenidoDatoSec) {
		this.contenidoDatoSec = contenidoDatoSec;
	}

}
