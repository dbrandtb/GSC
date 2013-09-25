package mx.com.gseguros.portal.consultas.model;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author hector.lopez
 *
 */
public class ConsultaDatosAgenteVO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8555353864912795413L;

	private String cdagente;
	private String cdideper;
	private String nombre;
	private Date fedesde;
	


	public String getCdagente() {
		return cdagente;
	}

	public void setCdagente(String cdagente) {
		this.cdagente = cdagente;
	}

	public String getCdideper() {
		return cdideper;
	}

	public void setCdideper(String cdideper) {
		this.cdideper = cdideper;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFedesde() {
		return fedesde;
	}

	public void setFedesde(Date date) {
		this.fedesde = date;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}	
	
}
