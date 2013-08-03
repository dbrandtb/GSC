package mx.com.aon.catbo.service;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.NotificacionVO;
import mx.com.aon.portal.service.PagedList;



/**
 * Interface de servicios para AyudaCoberturas.
 *
 */
public interface NotificacionesManager{
	
	/**
	 *  Obtiene un conjunto de Notificaciones
	 * 
	 * @param cdNotificacion
	 * @param dsNotificacion 
	 * @param dsMensaje 
	 * @param cdFormatoOrden 
	 * @param cdMetEnv
	 * 
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarNotificaciones(String dsNotificacion, String dsRegion, String dsProceso, String dsEdoCaso, String dsMetEnv, int start, int limit) throws ApplicationException;

	/**
	 *  Elimina una Notificacion.
	 * 
	 *  @param cdNotificacion
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarNotificaciones(String cdNotificacion) throws ApplicationException;

	/**
	 *  Elimina una Notificacion por Proceso.
	 * 
	 *  @param cdNotificacion
	 *  @param cdProceso
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarNotificacionesProcesos(String cdNotificacion) throws ApplicationException;
	
	/**
	 *  Obtiene una configuracion de notificacione especifica en base a un parametro de entrada.
	 * 
	 *  @param cdNotificacion
	 *  @param dsNotificacion
	 *  @param dsMensaje
	 *  @param cdFormatoOrden
	 *  @param cdMetEnv
	 *  
	 *  @return Objeto NotificacionVO
	 *  
	 *  @throws ApplicationException
	 *  
	 * 
	 */
	public NotificacionVO getNotificaciones(String cdNotificacion) throws ApplicationException;
	
	/**
	 *  Obtiene un conjunto configuracion de notificaciones por procesos especifica en base a un parametro de entrada.
	 * 
	 *  @param cdNotificacion
	 *  
	 *  @return Objeto NotificacionVO
	 *  
	 *  @throws ApplicationException
	 *  
	 * 
	 */
	public PagedList getNotificacionesProceso(String cdNotificacion, int start, int limit) throws ApplicationException;

	/**
	 *  Obtiene un conjunto de procesos especifica en base a un parametro de entrada.
	 * 
	 *  @param dsProceso
	 *  @param cdNotificacion
	 *  
	 *  @return Objeto NotificacionVO
	 *  
	 *  @throws ApplicationException
	 *  
	 * 
	 */
	public PagedList getProcesoNotificaciones(String dsProceso, String cdNotificacion, int start, int limit) throws ApplicationException;
	
	/**
	 *  Obtiene un conjunto de estados especifica en base a un parametro de entrada.
	 * 
	 *  
	 *  @param cdNotificacion
	 *  
	 *  @return Objeto NotificacionVO
	 *  
	 *  @throws ApplicationException
	 *  
	 * 
	 */
	public PagedList getEstadosNotificaciones(String cdNotificacion, int start, int limit) throws ApplicationException;
	
	/**
	 *  Obtiene un conjunto de estados especifica en base a un parametro de entrada.
	 * 
	 *  
	 *  @param cdNotificacion
	 *  
	 *  @return Objeto NotificacionVO
	 *  
	 *  @throws ApplicationException
	 *  
	 * 
	 */
	public PagedList getEstadosCaso(String cdNotificacion, int start, int limit) throws ApplicationException;
	/**
	 *  Salva la informacion de notificaciones.
	 * 
	 *  @param notificacionVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public BackBoneResultVO guardarNotificaciones(NotificacionVO notificacionVO) throws ApplicationException;
	
	/**
	 *  Salva la informacion de notificaciones por proceso.
	 * 
	 *  @param notificacionVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String guardarNotificacionesProc(String cdNotificacion, List<NotificacionVO> listaNotificacionVO) throws ApplicationException;
	
	/**
	 * Obtiene las variables a utilizar en el campo de Mensaje de Agregar/Editar
	 * @return List
	 * @throws ApplicationException
	 */
	public List obtieneVariables() throws ApplicationException ;
}
