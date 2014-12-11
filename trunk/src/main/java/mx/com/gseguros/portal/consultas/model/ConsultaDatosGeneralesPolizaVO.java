package mx.com.gseguros.portal.consultas.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author MAVR
 *
 */
public class ConsultaDatosGeneralesPolizaVO extends ConsultaDatosPolizaVO implements Serializable{

	private static final long serialVersionUID = -8555353864912795413L;
	
	private String agente;

	public String getAgente() {
		return agente;
	}

	public void setAgente(String agente) {
		this.agente = agente;
	}
	
	
	
}