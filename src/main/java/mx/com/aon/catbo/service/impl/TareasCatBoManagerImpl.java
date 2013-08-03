package mx.com.aon.catbo.service.impl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.TareaVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.TareasCatBoManager;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements TareasCatBoManager
 * 
 * @extends AbstractManager
 */
public class TareasCatBoManagerImpl extends AbstractManager implements TareasCatBoManager {


	/**
	 *  Obtiene un conjunto de Tareas Cat Bo
	 *  Hace uso del Store Procedure PKG_CATBO.P_OBTIENE_MTAREAS
	 * 
	 *  @param dsStatus
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public PagedList buscarTareasCatBo(String dsProceso, String dsModulo, String cdPriord, int start, int limit )throws ApplicationException{
 		HashMap map = new HashMap();
		map.put("dsProceso", dsProceso);
		map.put("dsModulo", dsModulo);
		map.put("cdPriord", cdPriord);
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "BUSCAR_TAREAS_CAT_BO", start, limit);
	}
	
    //Buscar tareas para validar la compra de tiempo
    
    public WrapperResultados buscarTareasCatBoValidar(String pv_cdproceso_i )throws ApplicationException{
 		HashMap map = new HashMap();
		map.put("pv_cdproceso_i", pv_cdproceso_i);
		//return pagedBackBoneInvoke(map, "BUSCAR_TAREAS_CAT_BO_VALIDA");
		 return returnBackBoneInvoke(map,"BUSCAR_TAREAS_CAT_BO_VALIDA");
        
	}
	/**
	 *  Elimina un Tareas Cat Bo
	 *  Hace uso del Store Procedure PKG_CATBO.P_BORRA_MTAREAS
	 * 
	 *  @param cdStatus
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String borrarTareasCatBo(String cdProceso) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdProceso",cdProceso);
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_TAREAS_CAT_BO");
        return res.getMsgText();
	}
	
	/**
	 *  Obtiene una Tarea Cat Bo en particular
	 *  Hace uso del Store Procedure PKG_CATBO.P_OBTIENE_MTAREAS
	 * 
	 *  @param cdFormato
	 *  
	 *  @return Objeto FormatoDocumentoVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public TareaVO getTareasCatBo(String cdProceso) throws ApplicationException{
			HashMap map = new HashMap();
			map.put("cdProceso",cdProceso);
            return (TareaVO)getBackBoneInvoke(map,"OBTIENERREG_TAREAS_CAT_BO");

	}
	
	/**
	 *  Actualiza o Modifica una Tarea Cat Bo
	 *  Hace uso del Store Procedure PKG_DOCUMENTOS.P_GUARDAR_MTAREAS
	 * 
	 *  @param FormatoDocumentoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public String guardarTareasCatBo(TareaVO tareaVO) throws ApplicationException{
   	 
			HashMap map = new HashMap();
			map.put("cdProceso",tareaVO.getCdProceso());
			map.put("estatus",tareaVO.getEstatus());
			map.put("cdModulo",tareaVO.getCdModulo());
			map.put("cdPriord",tareaVO.getCdPriord());
			map.put("indSemaforo",tareaVO.getIndSemaforo());
			map.put("frmAb",tareaVO.getFrmAb());

	
            WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_TAREAS_CAT_BO");
           
            return res.getMsgText();
    }
    

    
    /**
	 *  Obtiene un un conjunto de Tareas Cat Bo para la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure PKG_DOCUMENTOS.P_OBTIENE_MTAREAS
	 *  
	 *  @param dsStatus
	 *    
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsProceso, String dsModulo, String cdPriord) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();

		map.put("dsProceso", dsProceso);
		map.put("dsModulo", dsModulo);
		map.put("cdPriord", cdPriord);
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_TAREAS_CAT_BO_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Tarea","Modulo","Prioridad"});
		
		return model;
	}
    
    
}

	
	
	


