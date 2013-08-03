package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.AtributosVariablesPersonaVO;

/**
 * Interface de servicios para Atributos Varibles por Persona
 *
 */
public interface AtributosVariablesPersonaManager {	

	/**
	 *  Elimina datos de la configuracion de datos adicionales de personas
	 * 
	 *  @param cdAtribu
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
    public String borrarAtributosVariablesPersona(String cdAtribu) throws ApplicationException;

	/**
	 *  Obtiene un conjunto de atributos variables por persona
	 * 
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos 
	 *  
	 *  @throws ApplicationException
	 */
    public PagedList buscarAtributosVariablesPersona(int start, int limit ) throws ApplicationException;

	/**
	 *  Guarda atributos variable por persona
	 * 
	 *  @param AtributoVaribleAgenteVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
    public String guardarAtributosVariablesPersona(AtributosVariablesPersonaVO atributosVariablesPersonaVO) throws ApplicationException;
}
