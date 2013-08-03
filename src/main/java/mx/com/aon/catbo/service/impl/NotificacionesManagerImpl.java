package mx.com.aon.catbo.service.impl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.NotificacionVO;
import mx.com.aon.catbo.service.NotificacionesManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements NotificacionesManager
 * 
 * @extends AbstractManager
 */
public class NotificacionesManagerImpl extends AbstractManager implements NotificacionesManager {


	/**
	 *  Obtiene un conjunto de Notificaciones
	 *  Hace uso del Store Procedure PKG_NOTIFICACIONES_CATBO.P_OBTIENE_MNOTIFIC
	 * 
	 *  @param cdNotificacion
	 *  @param dsNotificacion
	 *  @param dsMensaje
	 *  @param cdFormato
	 *  @param cdMetEnv
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public PagedList buscarNotificaciones(String dsNotificacion, String dsRegion, String dsProceso, String dsEdoCaso, String dsMetEnv, int start, int limit)throws ApplicationException{
 		HashMap map = new HashMap();
		map.put("dsNotificacion", dsNotificacion);
		map.put("dsRegion", dsRegion);
		map.put("dsProceso", dsProceso);
		map.put("dsEdoCaso", dsEdoCaso);
		map.put("dsMetEnv", dsMetEnv);
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "BUSCAR_NOTIFICACIONES", start, limit);
	}
	
	/**
	 *  Elimina una notificacion 
	 *  Hace uso del Store Procedure PKG_NOTIFICACIONES_CATBO.P_BORRA_MNOTIFIC
	 * 
	 *  @param cdAsocia
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String borrarNotificaciones(String cdNotificacion) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdNotificacion",cdNotificacion);
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_NOTIFICACION");
        return res.getMsgText();
	}

	/**
	 *  Elimina una notificacion 
	 *  Hace uso del Store Procedure PKG_NOTIFICACIONES_CATBO.P_BORRA_TNOTPROC
	 * 
	 *  @param cdAsocia
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String borrarNotificacionesProcesos(String cdNotificacion)throws ApplicationException{
        
        HashMap map = new HashMap();
		map.put("cdNotificacion",cdNotificacion);

        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_NOTIFICACION_PROCESO");
        return res.getMsgText();
	}
	

	/**
	 *  Obtiene una configuracion de notificaciones en base a un parametro de entrada.
	 *  Hace uso del Store Procedure PKG_NOTIFICACIONES_CATBO.P_OBTIENE_MNOTIFIC_REG
	 * 
	 *  @param cdNotificacion
	 *  
	 *  @return Objeto EstructuraVO
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public NotificacionVO getNotificaciones(String cdNotificacion) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdNotificacion",cdNotificacion);
	
        return (NotificacionVO)getBackBoneInvoke(map,"OBTENERREG_NOTIFICACION");
	}
	

	/**
	 *  Obtiene un conjunto de notificaciones por procesos en base a un parametro de entrada.
	 *  Hace uso del Store Procedure PKG_NOTIFICACIONES_CATBO.P_OBTIENE_TNOTPROC
	 * 
	 *  @param cdNotificacion
	 *  
	 *  @return Objeto EstructuraVO
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList getNotificacionesProceso(String cdNotificacion, int start, int limit) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdNotificacion",cdNotificacion);
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "OBTENERREG_NOTIFICACION_PROCESO", start, limit);
	}
	
	/**
	 *  Obtiene un conjunto de procesos en base a un parametro de entrada.
	 *  Hace uso del Store Procedure PKG_NOTIFICACIONES_CATBO.P_OBTIENE_PROCESOS
	 * 
	 *  @param cdNotificacion
	 *  
	 *  @return Objeto EstructuraVO
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList getProcesoNotificaciones(String dsProceso, String cdNotificacion, int start, int limit) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("dsProceso",dsProceso);
		map.put("cdNotificacion",cdNotificacion);
		
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "OBTENERREG_PROCESO_NOTIFICACION", start, limit);
	}
	
	/**
	 *  Obtiene un conjunto de estados en base a un parametro de entrada.
	 *  
	 * 
	 *  @param cdNotificacion
	 *  
	 *  @return Objeto EstructuraVO
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList getEstadosCaso(String cdNotificacion, int start, int limit) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdNotificacion",cdNotificacion);
		
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "OBTIENE_ESTADOS_CASO", start, limit);
	}
	
	/**
	 *  Obtiene un conjunto de estados en base a un parametro de entrada.
	 *  
	 * 
	 *  @param cdNotificacion
	 *  
	 *  @return Objeto EstructuraVO
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList getEstadosNotificaciones(String cdNotificacion, int start, int limit) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdNotificacion",cdNotificacion);
		
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "OBTIENE_ESTADOS_NOTIFI", start, limit);
	}
	
	/**
	 *  Inserta o Actualiza una Notificacion por proceso.
	 *  Hace uso del Store Procedure PKG_NOTIFICACIONES_CATBO.P_GUARDA_COBERTURA
	 * 
	 *  @param AyudaCoberturasVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")	
    public BackBoneResultVO guardarNotificaciones(NotificacionVO notificacionVO) throws ApplicationException{
      
		BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
		
			HashMap map = new HashMap();
			map.put("cdNotificacion",notificacionVO.getCdNotificacion());
			map.put("dsNotificacion",notificacionVO.getDsNotificacion());
			map.put("dsMensaje",notificacionVO.getDsMensaje());
			map.put("cdRegion",notificacionVO.getCdRegion());
			map.put("cdMetEnv",notificacionVO.getCdMetEnv());
				
			WrapperResultados res = returnBackBoneInvoke(map, "INSERTAR_NOTIFICACIONES");
			backBoneResultVO.setOutParam(res.getResultado());
			backBoneResultVO.setMsgText(res.getMsgText());
			return backBoneResultVO;
			
			
          
	}	
	
	/**
	 *  Inserta o Actualiza una Notificacion por proceso.
	 *  Hace uso del Store Procedure PKG_NOTIFICACIONES_CATBO.P_GUARDA_COBERTURA
	 * 
	 *  @param AyudaCoberturasVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")	
    public String guardarNotificacionesProc(String cdNotificacion, List<NotificacionVO> listaNotificacionVO) throws ApplicationException{
	
	 WrapperResultados res = new WrapperResultados();	
	 
	 /*HashMap mapBorrar = new HashMap();
	 mapBorrar.put("cdNotificacion",cdNotificacion);

	 res = returnBackBoneInvoke(mapBorrar, "BORRAR_NOTIFICACION_PROCESO");
	*/
	 for (int i=0; i<listaNotificacionVO.size(); i++) {
	   HashMap mapGuardar = new HashMap();
	   NotificacionVO notificacionVO=listaNotificacionVO.get(i);
	   mapGuardar.put("cdNotificacion",notificacionVO.getCdNotificacion());
	   mapGuardar.put("cdProceso",notificacionVO.getCdProceso());
	   mapGuardar.put("cdEstado",notificacionVO.getCdEstado());
	   
	   res =  returnBackBoneInvoke(mapGuardar,"INSERTAR_NOTIFICACIONES_PROCESO");
     }
     return res.getMsgText();
	 }	
	
	/**
	 * Obtiene las variables a utilizar en el campo de Mensaje de Agregar/Editar
	 * @return List
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List obtieneVariables() throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", "VARNOTIFIC");

		return getAllBackBoneInvoke(map, "OBTIENE_VARIABLES_NOTIFI");
	}
		   
}


	
	
	


