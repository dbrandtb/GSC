
package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.AtributoVaribleAgenteVO;


/**
 * Interface de servicios para Atributos Varibles por Agente
 *
 */

public interface AtributosVariablesAgenteManager {
	
	/**
	 *  Elimina datos de asociar formatos
	 * 
	 *  @param cdAtribu
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
    public String borrarAtributosVariablesAgente(String cdAtribu) throws ApplicationException;

	/**
	 *  Obtiene un conjunto de atributos variables por agente
	 * 
	 *  @param dsAtribu
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos 
	 *  
	 *  @throws ApplicationException
	 */
    public PagedList buscarAtributosVariablesAgente(int start, int limit) throws ApplicationException;

	/**
	 *  Guarda atributos variable por agente
	 * 
	 *  @param AtributoVaribleAgenteVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
    public String guardarAtributosVariablesAgente(AtributoVaribleAgenteVO atributoVaribleAgenteVO) throws ApplicationException;
	
}
