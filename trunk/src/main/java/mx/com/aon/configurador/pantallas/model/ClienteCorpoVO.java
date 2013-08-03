package mx.com.aon.configurador.pantallas.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 *  Clase Value Object utilizada para el listado de clientes
 * 
 * @author  aurora.lozada
 * 
 */

public class ClienteCorpoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3721427996029139829L;
	
	/**
	 * 
	 */
	private String cdCliente;
	
	/**
	 * 
	 */
	private String dsCliente;
	
	
	/**
	 * 
	 */
	private String cdPerson;


	/**
	 * @return the cdCliente
	 */
	public String getCdCliente() {
		return cdCliente;
	}


	/**
	 * @param cdCliente the cdCliente to set
	 */
	public void setCdCliente(String cdCliente) {
		this.cdCliente = cdCliente;
	}


	/**
	 * @return the dsCliente
	 */
	public String getDsCliente() {
		return dsCliente;
	}


	/**
	 * @param dsCliente the dsCliente to set
	 */
	public void setDsCliente(String dsCliente) {
		this.dsCliente = dsCliente;
	}


	/**
	 * @return the cdPerson
	 */
	public String getCdPerson() {
		return cdPerson;
	}


	/**
	 * @param cdPerson the cdPerson to set
	 */
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
		
	}
	
}
