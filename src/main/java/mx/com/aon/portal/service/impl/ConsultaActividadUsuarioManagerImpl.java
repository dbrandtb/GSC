
package mx.com.aon.portal.service.impl;


import mx.com.aon.catbo.model.ActividadUsuarioVO;
import mx.com.aon.catbo.model.FormatoTimeStampVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;

import mx.com.aon.portal.service.ConsultaActividadUsuarioManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.ProcessResultManagerJdbcTemplate;
import mx.com.aon.portal.util.UserSQLDateConverter;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.util.ConvertUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;
import java.sql.Timestamp;

import org.apache.commons.beanutils.Converter;


/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements ConsultaActividadUsuarioManager
 * 
 * @extends AbstractManager
 */
public class ConsultaActividadUsuarioManagerImpl extends AbstractManagerJdbcTemplateInvoke implements ConsultaActividadUsuarioManager {

	private ProcessResultManagerJdbcTemplate processResultManager;
	
	
	/**
	 *  Obtiene un conjunto de asociar formatos
	 *  Hace uso del Store Procedure PKG_CATBO.P_OBTIENE_SEC_CONTEXT_AUDIT
	 * 
	 *  @param dsProceso
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
   
	@SuppressWarnings("unchecked")
	//public PagedList buscarActividadesUsuario(String pv_dsusuari_i,String pv_url_i,String pv_fe_ini_i, String pv_fe_fin_i, int start, int limit)throws ApplicationException {
	public WrapperResultados buscarActividadesUsuario(String pv_dsusuari_i,String pv_url_i,Timestamp pv_fe_ini_i, Timestamp pv_fe_fin_i,/*String sort, String dir,*/ int start, int limit)throws ApplicationException {
		 // Se crea un mapa para pasar los parametros de ejecucion al endpoint
		Converter converter = new UserSQLDateConverter("");
		WrapperResultados wrapperResultados = new WrapperResultados();
		HashMap map = new HashMap();
		map.put("pv_dsusuari_i",pv_dsusuari_i);
		map.put("pv_url_i",pv_url_i);
		//map.put("pv_sort_i", sort);
		//map.put("pv_dir_i", dir);
		map.put("pv_fe_ini_i", pv_fe_ini_i);
		map.put("pv_fe_fin_i", pv_fe_fin_i);
		//map.put("pv_fe_ini_i", ConvertUtil.convertToTimeStamp(pv_fe_ini_i));
		//map.put("pv_fe_fin_i", ConvertUtil.convertToTimeStamp(pv_fe_fin_i));
		/******Se envian el start y limit para que el SP sea quien pagine la búsqueda*****/
		map.put("pv_start",start);
		map.put("pv_limit",limit);
		
       // return pagedBackBoneInvoke(map, "BUSCAR_ACTIVIDAD_USUARIO", start, limit);
		
		wrapperResultados = returnBackBoneInvoke(map, "BUSCAR_ACTIVIDAD_USUARIO"); 
		
		/*****Se colocó la captura de excepción, porque el returnBackBoneInvoke, en el Abstract, no la capturaba*****/
		if (wrapperResultados.getItemList()==null) {
            logger.debug("No se encontraron datos");
            throw new ApplicationException("No se encontraron datos");
        }
        if (wrapperResultados.getItemList().size()==0) {
            logger.debug("No se encontraron datos");
            throw new ApplicationException("No se encontraron datos");
        }
        
        if (wrapperResultados.getItemList() != null && wrapperResultados.getItemList().size()>0 )  {
        	return wrapperResultados;
        } else {
            throw new ApplicationException("Registro no encontrado");
        }
		
		
		  
	}


    public String insertarActividadesUsuario(String pv_reqid_i, String pv_url_i, String pv_reqtype_i, String pv_cdusuario_i, String pv_cdrol_i) throws ApplicationException {

        HashMap map = new HashMap();
        map.put("pv_reqid_i", pv_reqid_i);
        map.put("pv_url_i", pv_url_i);
        map.put("pv_reqtype_i", pv_reqtype_i);
        map.put("pv_cdusuario_i", pv_cdusuario_i);
       // map.put("pv_timestamp_i", new  Date(System.currentTimeMillis()));
        map.put("pv_timestamp_i", new  Timestamp(System.currentTimeMillis()));
        map.put("pv_cdrol_i", pv_cdrol_i);

        WrapperResultados res =  returnBackBoneInvoke(map,"INSERTAR_ACTIVIDAD_USUARIO");
        return res.getMsgText();
    }


	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String pv_dsusuari_i, String pv_url_i,Timestamp pv_fe_ini_i, Timestamp pv_fe_fin_i)throws ApplicationException {
		// Se crea el objeto de respuesta
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("pv_dsusuari_i",pv_dsusuari_i);
		map.put("pv_url_i",pv_url_i);
		//map.put("pv_fe_ini_i", ConvertUtil.convertToTimeStamp(pv_fe_ini_i));
		//map.put("pv_fe_fin_i", ConvertUtil.convertToTimeStamp(pv_fe_fin_i));
		map.put("pv_fe_ini_i", pv_fe_ini_i);
		map.put("pv_fe_fin_i", pv_fe_fin_i);
		
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "BUSCAR_ACTIVIDAD_USUARIO_EXPORT");

		model.setInformation(lista);
		// Se agregan los nombre de las columnas del modelo de datos
		model.setColumnName(new String[]{"NOMBRE","FECHA","ACTIVIDAD O EVENTO"});
		
		return model;
	}
	
	public String buscarFechaInicialActividadesUsuario(String pv_dsusuari_i,String pv_url_i, int start, int limit)throws ApplicationException {
		 // Se crea un mapa para pasar los parametros de ejecucion al endpoint
		ActividadUsuarioVO fechaInicialDeTabla;	
		HashMap map = new HashMap();
		map.put("pv_dsusuari_i",pv_dsusuari_i);
		map.put("pv_url_i",pv_url_i);
		
		PagedList pagedListFI = pagedBackBoneInvoke(map, "BUSCAR_FECHA_INICIAL_ACTIVIDAD_USUARIO", start, limit);
		fechaInicialDeTabla = (ActividadUsuarioVO)pagedListFI.getItemsRangeList().get(0);
		return fechaInicialDeTabla.getFechaInicial();
		
	}
	
	public String buscarFechaFinalActividadesUsuario(String pv_dsusuari_i,String pv_url_i, int start, int limit)throws ApplicationException {
		 // Se crea un mapa para pasar los parametros de ejecucion al endpoint
		ActividadUsuarioVO fechaFinalDeTabla;
		HashMap map = new HashMap();
		map.put("pv_dsusuari_i",pv_dsusuari_i);
		map.put("pv_url_i",pv_url_i);
		
		PagedList pagedListFF = pagedBackBoneInvoke(map, "BUSCAR_FECHA_FINAL_ACTIVIDAD_USUARIO", start, limit);
		fechaFinalDeTabla = (ActividadUsuarioVO)pagedListFF.getItemsRangeList().get(0);
		return fechaFinalDeTabla.getFechaFinal();
		
      
	}
	
}


	
	
	


