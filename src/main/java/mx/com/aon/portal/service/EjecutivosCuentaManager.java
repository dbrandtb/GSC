/**
 * 
 */
package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.EjecutivoCuentaVO;


/**
 * Interface de servicios para ejecutivos por cuenta.
 *
 */
public interface EjecutivosCuentaManager  {

	
	/**
	 *  Obtiene un ejecutivo de cunenta.
	 * 
	 *  @param cdAgente
	 *  @param cdPerson
	 *  
	 *  @return EjecutivoCuentaVO
	 */			
	public EjecutivoCuentaVO getEjecutivoCuenta(String cdAgente,String cdPerson) throws ApplicationException;
   
	
	/**
	 *  Obtiene un conjunto de ejecutivos por cuenta.
	 * 
	 *  @param dsNombre
	 *  @param nomAgente
	 *  @param desGrupo
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList 
	 */		
	public PagedList buscarEjecutivosCuenta(String dsNombre,String nomAgente, String desGrupo, int start, int limit) throws ApplicationException;
	

	/**
	 *  Agrega un nuevo ejecutivo a la cuenta.
	 * 
	 *  @param ejecutivoCuentaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */		
	public String agregarGuardarEjecutivoCuenta(EjecutivoCuentaVO ejecutivoCuentaVO) throws ApplicationException;
   
	
	/**
	 *  Relaiza la baja de un ejecutivo de cuenta.
	 * 
	 *  @param cdAgente
	 *  @param cdElemento
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */		
	public String borrarEjecutivoCuenta(String cdAgente,String cdElemento) throws ApplicationException;
	
	/**
	 *  Exporta la busqueda del grid.
	 * 
	 *  @param dsNombre
	 *  @param nomAgente
	 *  @param desGrupo
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */		
	public TableModelExport getModel(String dsNombre, String nomAgente,String desGrupo) throws ApplicationException;
}
