package mx.com.aon.portal.service.impl;


import mx.com.aon.portal.model.ConfigurarAccionRenovacionVO;
import mx.com.aon.portal.model.ConsultaConfiguracionRenovacionVO;
import mx.com.aon.portal.service.ConfigurarAccionesRenovacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigurarAccionesRenovacionManagerImpl extends AbstractManager implements ConfigurarAccionesRenovacionManager {

	/**
	 *  Obtiene un encabezado de acciones de renovacion en particular
	 *  Hace uso del Store Procedure PKG_RENOVA.P_OBTIENE_ENCABEZADO
	 * 
	 *  @param cdRenova
	 *  
	 *  @return Objeto ConsultaConfiguracionRenovacionVO
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public ConsultaConfiguracionRenovacionVO getEncabezadoConfigurarAccionesRenovacion(String cdRenova) throws ApplicationException{
    	HashMap map = new HashMap();
    	map.put("cdRenova", cdRenova);
    	return  (ConsultaConfiguracionRenovacionVO) getBackBoneInvoke(map,"OBTIENE_ENCABEZADO_CONFIGURAR_ACCIONES_RENOVACION");  
    }
    
	/**
	 *  Obtiene acciones de renovacion en particular
	 *  Hace uso del Store Procedure PKG_RENOVA.P_OBTIENE_ACCION
	 * 
	 *  @param cdRenova
	 *  
	 *  @return Objeto ConsultaConfiguracionRenovacionVO
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public ConfigurarAccionRenovacionVO getConfigurarAccionesRenovacionAccion(String cdRenova) throws ApplicationException{
    	HashMap map = new HashMap();
    	map.put("cdRenova", cdRenova);
    	return  (ConfigurarAccionRenovacionVO) getBackBoneInvoke(map,"OBTIENE_CONFIGURAR_ACCIONES_RENOVACION_ACCION");     	  	
    }
  
    /**
	 *  Inserta o actualiza una accion de renocacion
	 *  Hace uso del Store Procedure PKG_RENOVA.P_GUARDA_ACCION
	 * 
	 *  @param configurarAccionRenovacionVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public String guardarConfigurarAccionesRenovacion(ConfigurarAccionRenovacionVO configurarAccionRenovacionVO) throws ApplicationException {
        // Se crea un mapa para pasar los parametros de ejecucion al endpoint
    	HashMap map = new HashMap();       	
        
        map.put("cdRenova",configurarAccionRenovacionVO.getCdRenova());
        map.put("cdRol",configurarAccionRenovacionVO.getCdRol());       
        map.put("cdTitulo",configurarAccionRenovacionVO.getCdTitulo());
        map.put("cdAccion",configurarAccionRenovacionVO.getCdAccion());
        map.put("cdCampo",configurarAccionRenovacionVO.getCdCampo());
        
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_CONFIGURAR_ACCIONES_RENOVACION");
        return res.getMsgText();
    }

	/**
	 *  Elimina una configuracion de acciones de renovacion
	 *  Hace uso del Store Procedure PKG_RENOVA.P_BORRA_ACCION
	 * 
	 *  @param cdRenova
	 *  @param cdTitulo
	 *  @param cdRol
	 *  @param cdCampo
	 *    
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	 
    @SuppressWarnings("unchecked")
	public String borrarConfigurarAccionesRenovacion(String cdRenova, String cdTitulo, String cdRol, String cdCampo ) throws ApplicationException{
    
    	// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("cdRenova",cdRenova);
		map.put("cdTitulo",cdTitulo);
		map.put("cdRol",cdRol);
		map.put("cdCampo",cdCampo);
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_CONFIGURAR_ACCIONES_RENOVACION");
        return res.getMsgText();
	}   
    
	/**
	 *  Obtiene un conjunto de acciones de renovacion
	 *  Hace uso del Store Procedure PKG_RENOVA.P_ACCIONES_ROL
	 * 
	 *  @param cdRenova
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public PagedList buscarConfigurarAccionesRenovacion(String cdRenova,int start, int limit )throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdRenova",cdRenova);
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "BUSCAR_CONFIGURAR_ACCIONES_RENOVACION", start, limit);
	}

    /**
	 *  Obtiene un conjunto de acciones de renovacion para la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure PKG_RENOVA.P_ACCIONES_ROL
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
		map.put("cdRenova",cdRenova);
				
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_CONFIGURAR_ACCIONES_RENOVACION_EXPORT");

		model.setInformation(lista);

		model.setColumnName(new String[]{"Rol","Pantalla","Campo","Accion"});
		return model;
    }
    
}
