package mx.com.gseguros.portal.general.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class PolizaVO implements Serializable {


	private static final long serialVersionUID = 1L;
	
	/**
	 * Unidad econ&oacute;mica de la p&oacute;liza
	 */
	private String cdunieco;
	
	/**
	 * Ramo de la p&oacute;liza
	 */
	private String cdramo;
	
	/**
	 * Estatus de la p&oacute;liza
	 */
	private String estado;
	
	/**
	 * N&uacute;mero de p&oacute;liza
	 */
	private String nmpoliza;
	
	/**
	 * N&uacute;mero de suplemento (imagen) de la p&oacute;liza 
	 */
	private String nmsuplem;
	
	/**
	 * Tipo de situaci&oacute;n
	 */
	private String cdtipsit;
	
	/**
	 * N&uacute;mero de situaci&oacute;n
	 */
	private String nmsituac;
	
	
	//Constructors:
	
	public PolizaVO() {
		super();
	}

	public PolizaVO(String cdunieco, String cdramo, String estado, String nmpoliza) {
		super();
		this.cdunieco = cdunieco;
		this.cdramo = cdramo;
		this.estado = estado;
		this.nmpoliza = nmpoliza;
	}

	public PolizaVO(String cdunieco, String cdramo, String estado,
			String nmpoliza, String nmsuplem, String cdtipsit, String nmsituac) {
		super();
		this.cdunieco = cdunieco;
		this.cdramo = cdramo;
		this.estado = estado;
		this.nmpoliza = nmpoliza;
		this.nmsuplem = nmsuplem;
		this.cdtipsit = cdtipsit;
		this.nmsituac = nmsituac;
	}
	
	
	//Getters and setters:
	
	public String getCdunieco() {
		return cdunieco;
	}

	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}

	public String getCdramo() {
		return cdramo;
	}

	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNmpoliza() {
		return nmpoliza;
	}

	public void setNmpoliza(String nmpoliza) {
		this.nmpoliza = nmpoliza;
	}

	public String getNmsuplem() {
		return nmsuplem;
	}

	public void setNmsuplem(String nmsuplem) {
		this.nmsuplem = nmsuplem;
	}

	public String getCdtipsit() {
		return cdtipsit;
	}

	public void setCdtipsit(String cdtipsit) {
		this.cdtipsit = cdtipsit;
	}

	public String getNmsituac() {
		return nmsituac;
	}

	public void setNmsituac(String nmsituac) {
		this.nmsituac = nmsituac;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
