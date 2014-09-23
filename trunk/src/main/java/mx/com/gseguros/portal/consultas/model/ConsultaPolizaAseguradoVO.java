package mx.com.gseguros.portal.consultas.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author hector.lopez
 *
 */
public class ConsultaPolizaAseguradoVO implements Serializable {

	private static final long serialVersionUID = -8555353864912795413L;

	private String cdunieco;
	private String dsunieco;
	private String cdramo;
	private String dsramo;
	private String estado;
	private String nmpoliza;
	private String nombreAsegurado;
	private String nmpoliex;
	/**
	 * C&oacute;digo de la p&oacute;liza usado en SISA 
	 */
	private String icodpoliza;
	
	
	public String getCdunieco() {
		return cdunieco;
	}

	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}

	public String getDsunieco() {
		return dsunieco;
	}

	public void setDsunieco(String dsunieco) {
		this.dsunieco = dsunieco;
	}

	public String getCdramo() {
		return cdramo;
	}

	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}

	public String getDsramo() {
		return dsramo;
	}

	public void setDsramo(String dsramo) {
		this.dsramo = dsramo;
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

	public String getNombreAsegurado() {
		return nombreAsegurado;
	}

	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado;
	}

	public String getNmpoliex() {
		return nmpoliex;
	}

	public void setNmpoliex(String nmpoliex) {
		this.nmpoliex = nmpoliex;
	}

	public String getIcodpoliza() {
		return icodpoliza;
	}

	public void setIcodpoliza(String icodpoliza) {
		this.icodpoliza = icodpoliza;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
		
}