package mx.com.aon.portal.service.impl;


import mx.com.aon.portal.model.RolRenovacionVO;
import mx.com.aon.portal.model.ConsultaConfiguracionRenovacionVO;
import mx.com.aon.portal.service.RolesRenovacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RolesRenovacionManagerImpl extends AbstractManager implements RolesRenovacionManager {

	/**
	 *  Obtiene un encabezado de rol de renovacion en particular
	 *  Hace uso del Store Procedure PKG_RENOVA.P_OBTIENE_ENCABEZADO
	 * 
	 *  @param cdRenova
	 *  
	 *  @return Objeto ConsultaConfiguracionRenovacionVO
	 *  
	 *  @throws ApplicationException
	 */	    
    @SuppressWarnings("unchecked")
	public ConsultaConfiguracionRenovacionVO getEncabezadoRolesRenovacion(String cdRenova) throws ApplicationException{
    	HashMap map = new HashMap();
    	map.put("cdRenova", cdRenova);
    	return  (ConsultaConfiguracionRenovacionVO) getBackBoneInvoke(map,"OBTENER_ENCABEZADO_ROLES_RENOVACION");    	
    }
    
	/**
	 *  Obtiene un conjunto de roles de renovacion
	 *  Hace uso del Store Procedure PKG_RENOVA.P_OBTIENE_ROLES
	 * 
	 *  @param cdRenova
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public PagedList obtenerRolesRenovacion(String cdRenova, int start, int limit )throws ApplicationException{
    	// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("pv_cdrenova_i",cdRenova);
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "OBTENER_ROLES_RENOVACION", start, limit);
	}
	
    /**
	 *  Inserta o actualiza un rol de renocacion
	 *  Hace uso del Store Procedure PKG_RENOVA.P_GUARDA_ROL_RENOVA
	 * 
	 *  @param rolRenovacionVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public String agregarGuardarRolRenovacion(RolRenovacionVO rolRenovacionVO) throws ApplicationException{
    	HashMap map = new HashMap();       	
        
        map.put("cdRenova", rolRenovacionVO.getCdRenova());
        map.put("cdRol", rolRenovacionVO.getCdRol());       
        
        WrapperResultados res =  returnBackBoneInvoke(map,"AGREGAR_GUARDAR_ROL_RENOVACION");
        return res.getMsgText();
    }

	/**
	 *  Elimina una configuracion de roles de renovacion
	 *  Hace uso del Store Procedure PKG_RENOVA.P_BORRA_ROL
	 * 
	 *  @param cdRenova
	 *  @param cdRol
	 *    
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	 
   @SuppressWarnings("unchecked")
	public String borrarRolRenovacion(String cdRenova, String cdRol ) throws ApplicationException{
    
    	// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("pv_cdrenova_i",cdRenova);
		map.put("pv_cdrol_i",cdRol);
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_ROL_RENOVACION");
        return res.getMsgText();
	}   
    
   /**
	 *  Obtiene un conjunto de roles de renovacion para la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure PKG_RENOVA.P_OBTIENE_ROLES
	 *  
	 *  @param cdRenova
	 *    
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */ 
    @SuppressWarnings("unchecked")
	public TableModelExport getModel(String cdRenova) throws ApplicationException {
 		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("pv_cdrenova_i",cdRenova);
				
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_ROLES_RENOVACION_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Rol"});
		return model;
    }
}
