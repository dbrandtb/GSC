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
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.catbo.service.AdministrarTipoArchivoManager;


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
public class AdministrarTipoArchivoManagerImpl extends AbstractManagerJdbcTemplateInvoke implements  AdministrarTipoArchivoManager {
	   
    @SuppressWarnings("unchecked")
    
	public PagedList obtenerArchivo(String pv_dsarchivo_i, int start, int limit) throws ApplicationException {

	HashMap map = new HashMap();
	map.put("pv_dsarchivo_i",pv_dsarchivo_i);
		
    return pagedBackBoneInvoke(map, "OBTIENE_ARCHIVOS", start, limit);
}
    
    public String guardaArchivos(String pv_cdtipoar_i, String pv_dsarchivo_i,
    		String pv_indarchivo_i) throws ApplicationException {
    	// TODO Auto-generated method stub
    	
    	HashMap map =new HashMap();
    	map.put("pv_cdtipoar_i", pv_cdtipoar_i);
    	map.put("pv_dsarchivo_i", pv_dsarchivo_i);
    	map.put("pv_indarchivo_i", pv_indarchivo_i);
    	
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_TIPO_ARCHIVO");
    	
        return res.getMsgText();
    }

/*	public String borrarArchivo(String pv_cdtipoar_i) throws ApplicationException {
		
		     HashMap map = new HashMap();
	        map.put("pv_cdtipoar_i", pv_cdtipoar_i);
	        
	        WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_ARCHIVO");
	        return res.getMsgText();
	}
*/    
    
    
	public TableModelExport getModel2(String pv_dsarchivo_i) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("pv_dsarchivo_i", pv_dsarchivo_i);
				
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTENER_ARCHIVOS_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Archivo","Tipo de Archivo"});
		return model;
   }
    
    
    
    public TableModelExport getModel(String pv_dsmodulo_i, String pv_dsusuario_i) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		List lista = null;
		HashMap map = new HashMap();
		
		map.put("pv_dsmodulo_i", pv_dsmodulo_i);
		map.put("pv_dsusuario_i", pv_dsusuario_i);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map,"OBTIENE_USUARIOS_EXPORT");
        model.setInformation(lista);
		model.setColumnName(new String[]{"USUARIO","MODULO"});

	   return model;
	}

	public String borrarUsuarios(String pv_cdmodulo_i, String pv_cdusuario_i)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cdmodulo_i",pv_cdmodulo_i);		
		map.put("pv_cdusuario_i",pv_cdusuario_i);
	    WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_USUARIOS");
	    return res.getMsgText();
	}   
	
  public String guardarUsuarios(String pv_cdmodulo_i, String pv_cdusuario_i)throws ApplicationException {

		HashMap map = new HashMap();
		map.put("pv_cdmodulo_i",pv_cdmodulo_i);		
		map.put("pv_cdusuario_i",pv_cdusuario_i);
	    WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_USUARIOS");
	    return res.getMsgText();
	}

public PagedList obtenerUsuariosAsignar(String pv_dsusuario_i, int start,
		int limit) throws ApplicationException {
	HashMap map = new HashMap();
	//map.put("pv_dsmodulo_i",pv_dsmodulo_i);
	map.put("pv_dsusuario_i",pv_dsusuario_i);
		
	map.put("start",start);
	map.put("limit",limit);
    return pagedBackBoneInvoke(map, "OBTIENE_USUARIOS_ASIGNAR", start, limit);
}

public String borraArchivo(String pv_cdtipoar_i) throws ApplicationException {
	// TODO Auto-generated method stub

    HashMap map = new HashMap();
    map.put("pv_cdtipoar_i", pv_cdtipoar_i);
    
    WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_TIPO_ARCHIVO");
	
    return res.getMsgText();
}





 
    
	           
}	    
   
    
  
  
    
    
	