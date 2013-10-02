/**
 * 
 */
package mx.com.aon.portal.service;

import mx.com.aon.portal.model.AgrupacionGrupoPersonaVO;
import mx.com.aon.portal.model.GrupoPersonaVO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para Agrupacion Grupo de Personas 
 *
 */
public interface AgrupacionGrupoPersonasManager {
	
	 /**
	 *  Realiza la insercion de una nueva agrupacion grupo personas 
	 * 
	 *  @param grupoPersonaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String agregarAgrupacionGrupoPersona(GrupoPersonaVO grupoPersonaVO) throws ApplicationException;

	 /**
	 *  Actualiza agrupacion grupo de personas
	 * 
	 *  @param grupoPersonaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String guardarAgrupacionGrupoPersona(GrupoPersonaVO grupoPersonaVO) throws ApplicationException;
	
	/**
	 *  Obtiene configuracion de agrupacion grupo de persona
	 * 
	 *  @param cdGrupo
	 *  
	 *  @return Objeto AgrupacionGrupoPersonaVO
	 *  
	 *  @throws ApplicationException
	 */
	public AgrupacionGrupoPersonaVO getAgrupacionGrupoPersonas (String cdGrupo) throws ApplicationException;
	
	/**
	 *  Obtiene configuracion de la configuracion de AgrupacionGrupoPersonas
	 * 
	 *  @param cdGrupo
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos GrupoPersonaVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList getGruposPersonas(String cdGrupo, int start, int limit) throws ApplicationException;	
	public String borrarGrupo(String pv_cve_grupo_i, String pv_cdagrgrupo_i)throws ApplicationException;
	
	
}
