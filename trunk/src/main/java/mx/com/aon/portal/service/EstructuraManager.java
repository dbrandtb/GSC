package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.EstructuraVO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para estructura.
 *
 */
public interface EstructuraManager {
	
	/**
	 *  Obtiene una estructura seleccionada.
	 * 
	 *  @param codigo
	 *  
	 *  @return Objeto EstructuraVO
	 *  
	 *  @throws ApplicationException
	 */
    public EstructuraVO getEstructura(String codigo) throws ApplicationException;
	
    /**
	 *  Inserta o actualiza una estructura.
	 * 
	 *  @param estructuraVO
	 *  @param pOpcionEstruct: parametro que define si se insertara o actualizara.
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String saveOrUpdateEstructura(EstructuraVO estructuraVO, String pOpcionEstruct) throws ApplicationException;
	
	/**
	 *  Elimina una estructura seleccionada.
	 * 
	 *  @param codigo
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String borraEstructura(String codigo) throws ApplicationException;
	
	/**
	 *  Obtiene un conjunto de estructuras.
	 * 
	 *  @param start, limit: marcan el rango de la lista a obtener para la paginacion del grid.
	 *  @param descripcion
	 *  
	 *  @return Conjunto de objetos EstructuraVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarEstructuras(int start, int limit, String descripcion) throws ApplicationException;
	
	/**
	 *  Copia una estructura seleccionada.
	 * 
	 *  @param objeto VO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String copiaEstructura(EstructuraVO estructuraVo)throws ApplicationException;
	
	/**
	 *  Obtiene una lista de estructuras para la exportacion a un formato predeterminado.
	 * 
	 *  @param descripcion: parametro con el que se realiza la busqueda.
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModel(String descripcion) throws ApplicationException;
}
