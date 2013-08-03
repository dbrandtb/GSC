package mx.com.aon.catbo.service.impl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.ArchivosFaxesVO;
import mx.com.aon.catbo.model.CalendarioVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.MatrizAsignacionVO;
import mx.com.aon.catbo.model.OperacionCATVO;
import mx.com.aon.catbo.model.ResponsablesVO;
import mx.com.aon.catbo.model.TiemposVO;
import mx.com.aon.catbo.service.OperacionCATManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mx.com.aon.catbo.service.*;
/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements NotificacionesManager
 * 
 * @extends AbstractManager
 */
public class DatosArchivosManagerImpl extends AbstractManagerJdbcTemplateInvoke implements  DatosArchivosManager {
	
    
    @SuppressWarnings("unchecked")
	public PagedList ObtenerArchivos(String pv_dsarchivo_i, String pv_nmtiparc_i, int start, int limit) throws ApplicationException {

	HashMap map = new HashMap();
	map.put("pv_dsarchivo_i",pv_dsarchivo_i);
	map.put("pv_nmtiparc_i",ConvertUtil.nvl(pv_nmtiparc_i));
		
	/*map.put("start",start);
	map.put("limit",limit);*/
	
	/*String endpointName = "OBTENER_DATOS_ARCHIVOS";
    return pagedBackBoneInvoke(map, endpointName, start, limit);*/
    
    return pagedBackBoneInvoke(map,"OBTENER_DATOS_ARCHIVOS", start, limit);
}
    
   public TableModelExport getModel(String pv_dsarchivo_i, String pv_nmtiparc_i) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		List lista = null;
		HashMap map = new HashMap();
		
		map.put("pv_dsarchivo_i", pv_dsarchivo_i);
		map.put("pv_nmtiparc_i", pv_nmtiparc_i);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map,"OBTIENE_ARCHIVO_EXPORT");
        model.setInformation(lista);
		model.setColumnName(new String[]{"Número","Atributo","Valor","Fecha"});

		return model;
	}   
	
	
    }

  
    
    
	