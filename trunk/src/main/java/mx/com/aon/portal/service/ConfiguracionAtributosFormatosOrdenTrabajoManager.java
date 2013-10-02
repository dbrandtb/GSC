package mx.com.aon.portal.service;

import mx.com.aon.portal.model.ConfiguracionAtributoFormatoOrdenTrabajoVO;
import mx.com.gseguros.exception.ApplicationException;


/**
 * Interface de servicios para configuracion atributos formatos orden de trabajo.
 *
 */
public interface ConfiguracionAtributosFormatosOrdenTrabajoManager  {
	
	
	/**
	 * Agrega y/o Salva la informacion de configuracion atributos formatos orden de trabajo.
	 *  
	 *  @param configuracionAtributoFormatoOrdenTrabajoVO
	 *  
	 *	@return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */
	public String agregarGuardarConfiguracionAtributoFormatoOrdenTrabajo(ConfiguracionAtributoFormatoOrdenTrabajoVO configuracionAtributoFormatoOrdenTrabajoVO) throws ApplicationException;
    

	/**
	 *  Da de baja a la informacion de configuracion atributos formatos orden de trabajo.
	 *  
	 *  @param cdFormatoOrden
	 *	@param cdSeccion 
	 *	@param cdAtribu
	 *
	 *	@return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	public String borrarConfiguracionAtributoFormatoOrdenTrabajo(String cdFormatoOrden, String cdSeccion, String cdAtribu) throws ApplicationException;
	
	
	/**
	 * Obtiene un conjunto de configuracion atributos formatos orden de trabajo.
	 *
	 * @param cdFormatoOrden 
	 * @param cdSeccion
	 * @param start 
	 * @param limit
	 *
	 *  @return Objeto ConfiguracionAtributoFormatoOrdenTrabajoVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarConfiguracionAtributoFormatoOrdenTrabajo(String cdFormatoOrden, String cdSeccion, int start, int limit )throws ApplicationException;
	
}
