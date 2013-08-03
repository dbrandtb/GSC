package mx.com.aon.configurador.pantallas.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author developer
 *
 */
public class AtributosMasterVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2831034035931185541L;

	/**
	 * 
	 */
	private String tipo;
	
	/**
	 * 
	 */
	private String etiqueta;
	
	/**
	 * 
	 */
	private String valor;
	
	
	
	private Map<String,String> values;

	
	
	/**
	 * @return the values
	 */
	
	public Map<String,String> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	
	public void setValues(Map<String,String> values) {
		this.values = values;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * @param etiqueta the etiqueta to set
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	

}
