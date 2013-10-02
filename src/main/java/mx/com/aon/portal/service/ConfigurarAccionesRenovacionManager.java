package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfigurarAccionRenovacionVO;
import mx.com.aon.portal.model.ConsultaConfiguracionRenovacionVO;
import mx.com.gseguros.exception.ApplicationException;

public interface ConfigurarAccionesRenovacionManager {
	
	/**
	 *  Obtiene configuracion del encabezado de acciones de renovacion
	 * 
	 *  @param cdRenova
	 *  
	 *  @return Objeto ConsultaConfiguracionRenovacionVO
	 *  
	 *  @throws ApplicationException
	 */
	public ConsultaConfiguracionRenovacionVO getEncabezadoConfigurarAccionesRenovacion(String cdRenova) throws ApplicationException;

	/**
	 *  Obtiene configuracion de acciones de renovacion
	 * 
	 *  @param cdRenova
	 *  
	 *  @return Objeto ConfigurarAccionRenovacionVO
	 *  
	 *  @throws ApplicationException
	 */
	public ConfigurarAccionRenovacionVO getConfigurarAccionesRenovacionAccion(String cdRenova) throws ApplicationException;

	/**
	 *  Inserta o actualiza datos acciones de renovacion
	 * 
	 *  @param configurarAccionRenovacionVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String guardarConfigurarAccionesRenovacion(ConfigurarAccionRenovacionVO configurarAccionRenovacionVO) throws ApplicationException;
    
	/**
	 *  Elimina datos de un rol de renovacion
	 * 
	 *  @param cdRenova
	 *  @param cdTitulo
	 *  @param cdCampo
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarConfigurarAccionesRenovacion(String cdRenova, String cdTitulo, String cdRol, String cdCampo ) throws ApplicationException;
	
	/**
	 *  Obtiene un conjunto acciones de renovacion
	 * 
	 *  @param cdrenova
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos ConfigurarAccionRenovacionVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarConfigurarAccionesRenovacion(String cdRenova, int start, int limit )throws ApplicationException;
    
	/**
	 *  Obtiene una lista de acciones de renovacion para la exportar a un formato predeterminado.
	 * 
	 *  @param cdRenova
	 *      
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModel(String cdRenova) throws ApplicationException;
	

}
