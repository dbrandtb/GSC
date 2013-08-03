package mx.com.aon.catbo.service.impl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.StatusCasoVO;
import mx.com.aon.catbo.model.StatusProcesoVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.StatusCasosManager;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements StatusCasosManager
 * 
 * @extends AbstractManager
 */
public class StatusCasosManagerImpl extends AbstractManager implements StatusCasosManager {


	/**
	 *  Obtiene un conjunto de Estatus de Caso
	 *  Hace uso del Store Procedure PKG_CATBO.P_OBTIENE_CATBOSTCAS
	 * 
	 *  @param dsStatus
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public PagedList buscarStatusCasos(String dsStatus, int start, int limit )throws ApplicationException{
 		HashMap map = new HashMap();
		map.put("dsStatus", dsStatus);
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "BUSCAR_STATUS_CASOS", start, limit);
	}
	
	/**
	 *  Elimina un Estatus de Caso
	 *  Hace uso del Store Procedure PKG_CATBO.P_BORRA_CATBOSTCAS
	 * 
	 *  @param cdStatus
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String borrarStatusCasos(String cdStatus) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdStatus",cdStatus);
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_STATUS_CASOS");
        return res.getMsgText();
	}
	
	/**
	 *  Obtiene un Estatus de Caso en particular
	 *  Hace uso del Store Procedure PKG_CATBO.P_OBTIENE_CATBOSTCAS_REG 
	 * 
	 *  @param cdFormato
	 *  
	 *  @return Objeto FormatoDocumentoVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public StatusCasoVO getStatusCasos(String cdStatus) throws ApplicationException{
			HashMap map = new HashMap();
			map.put("cdStatus",cdStatus);
            return (StatusCasoVO)getBackBoneInvoke(map,"OBTIENERREG_STATUS_CASOS");

	}
	
	/**
	 *  Actualiza o Modifica un Estatus de Caso
	 *  Hace uso del Store Procedure PKG_DOCUMENTOS.P_GUARDAR_FORMATO
	 * 
	 *  @param FormatoDocumentoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public String guardarStatusCasos(StatusCasoVO statusCasoVO) throws ApplicationException{

			HashMap map = new HashMap();
			map.put("cdStatus",statusCasoVO.getCdStatus());
			map.put("dsStatus",statusCasoVO.getDsStatus());
			map.put("indAviso",statusCasoVO.getIndAviso());

	
            WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_STATUS_CASOS");
            return res.getMsgText();
    }
    
	/**
	 *  Obtiene un conjunto de Tareas de Status de Casos sin parametros de entrada
	 *  Hace uso del Store Procedure PKG_CATBO.P_OBTIENE_MTAREAS 
	 * 
	 *  
	 *  @return Objeto TareaVO
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List getStatusCasosTareas(String cdStatus) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdStatus",cdStatus);
		
        return getAllBackBoneInvoke(map, "BUSCAR_STATUS_CASOS_TAREAS");
	}
    
	/**
	 *  Salva la informacion de notificaciones por proceso.
	 * 
	 *  @param statusProcesoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	@SuppressWarnings("unchecked")
	public String guardarStatusCasosTareas(StatusProcesoVO statusProcesoVO) throws ApplicationException{
		HashMap map = new HashMap();
		
		map.put("cdStatus",statusProcesoVO.getCdStatus());
		map.put("cdProceso",statusProcesoVO.getCdProceso());

        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_STATUS_CASOS_TAREAS");
        return res.getMsgText();
	}

    
	/**
	 *  Borra la informacion de notificaciones por proceso.
	 * 
	 *  @param statusProcesoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	@SuppressWarnings("unchecked")
	public String borrarStatusCasosTareas(StatusProcesoVO statusProcesoVO) throws ApplicationException{
		HashMap map = new HashMap();
		
		map.put("cdStatus",statusProcesoVO.getCdStatus());
		map.put("cdProceso",statusProcesoVO.getCdProceso());
		
		if(logger.isDebugEnabled()){
			logger.debug("cdStatus: "+statusProcesoVO.getCdStatus());
			logger.debug("cdProceso: "+ statusProcesoVO.getCdProceso());
		}
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_STATUS_CASOS_TAREAS");
        return res.getMsgText();
	}
    
    /**
	 *  Obtiene un un conjunto de Estatus de Caso para la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure PKG_DOCUMENTOS.P_OBTIENE_FORMATOS
	 *  
	 *  @param dsStatus
	 *    
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsStatus) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();

		map.put("dsStatus", dsStatus);
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_STATUS_CASOS_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Estatus","Indicador Aviso"});
		
		return model;
	}
    
	/**
	 *  Obtiene un conjunto de Tareas de Status de Casos por Procesos con parametros de entrada
	 *  Hace uso del Store Procedure PKG_CATBO.P_OBTIENE_CSTCASOTIEMPO_VALIDO 
	 * 
	 *  @param cdStatus
	 *  
	 *  @return Objeto TareaVO
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List getStatusCasosTareasProcesos(String cdStatus) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdStatus",cdStatus);
		
        return getExporterAllBackBoneInvoke(map, "BUSCAR_STATUS_CASOS_TAREAS_PROCESOS");
	}
    
    
}

	
	
	


