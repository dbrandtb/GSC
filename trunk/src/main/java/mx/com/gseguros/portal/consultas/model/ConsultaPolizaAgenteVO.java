package mx.com.gseguros.portal.consultas.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author hector.lopez
 *
 */
public class ConsultaPolizaAgenteVO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8555353864912795413L;

	private String cdramo;
	private String dsramo;
	private String nmpoliza;
	private String cdunieco;
	private String dsunieco;
	private String nmcuadro;
	
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
	public String getNmpoliza() {
		return nmpoliza;
	}
	public void setNmpoliza(String nmpoliza) {
		this.nmpoliza = nmpoliza;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
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
	public String getNmcuadro() {
		return nmcuadro;
	}
	public void setNmcuadro(String nmcuadro) {
		this.nmcuadro = nmcuadro;
	}	
		
	
}
