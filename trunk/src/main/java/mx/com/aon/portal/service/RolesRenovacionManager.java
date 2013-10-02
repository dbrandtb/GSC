package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConsultaConfiguracionRenovacionVO;
import mx.com.aon.portal.model.RolRenovacionVO;
import mx.com.gseguros.exception.ApplicationException;
/**
 * Interface de servicios para el Roles de Renovacion
 *
 */
public interface RolesRenovacionManager {
	
	/**
	 *  Obtiene configuracion de un periodo gracia en base a un parametro de entrada
	 * 
	 *  @param cdRenova
	 *  
	 *  @return Objeto ConsultaConfiguracionRenovacionVO
	 *  
	 *  @throws ApplicationException
	 */
	public ConsultaConfiguracionRenovacionVO getEncabezadoRolesRenovacion(String cdRenova) throws ApplicationException;
	
	/**
	 *  Obtiene un conjunto roles de renovacion
	 * 
	 *  @param cdrenova
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos RolRenovacionVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList obtenerRolesRenovacion(String cdrenova, int start, int limit )throws ApplicationException;
    
	/**
	 *  Inserta o actualiza datos roles de renovacion
	 * 
	 *  @param rolRenovacionVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String agregarGuardarRolRenovacion(RolRenovacionVO rolRenovacionVO) throws ApplicationException;	

	/**
	 *  Elimina datos de un rol de renovacion
	 * 
	 *  @param cdRenova
	 *  @param cdRol
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarRolRenovacion(String cdRenova, String cdRol) throws ApplicationException;
	
    /**
	 *  Obtiene una lista de roles de renovacion para la exportar a un formato predeterminado.
	 * 
	 *  @param cdRenova
	 *      
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModel(String cdRenova) throws ApplicationException;
			
}
