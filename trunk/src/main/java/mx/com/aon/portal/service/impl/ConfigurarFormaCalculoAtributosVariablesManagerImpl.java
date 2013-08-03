package mx.com.aon.portal.service.impl;

import mx.com.aon.portal.service.ConfigurarFormaCalculoAtributosVariablesManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.ConfigurarFormaCalculoAtributoVariableVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements ConfigurarFormaCalculoAtributosVariablesManager
 * 
 * @extends AbstractManager
 */
public class ConfigurarFormaCalculoAtributosVariablesManagerImpl extends AbstractManager implements ConfigurarFormaCalculoAtributosVariablesManager {

	/**
	 *  Obtiene un atributo de forma de calculo variable
	 *  Hace uso del Store Procedure PKG_PARAMETRIZA.P_ATRIB_FORCAL
	 * 
	 *  @param cdIden
	 *  
	 *  @return Objeto ConfigurarFormaCalculoAtributoVariableVO
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public ConfigurarFormaCalculoAtributoVariableVO getConfigurarFormaCalculoAtributo(String cdIden) throws ApplicationException{
    	HashMap map = new HashMap();
    	map.put("cdIden", cdIden);
    	return   (ConfigurarFormaCalculoAtributoVariableVO) getBackBoneInvoke(map,"OBTIENE_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS");    	
    }
    
	/**
	 *  Obtiene un atributo de forma de calculo variable para copiar
	 *  Hace uso del Store Procedure PKG_PARAMETRIZA.P_OBTIENE_INFOCOPIA
	 * 
	 *  @param cdIden
	 *  
	 *  @return Objeto ConfigurarFormaCalculoAtributoVariableVO
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public ConfigurarFormaCalculoAtributoVariableVO getConfigurarFormaCalculoAtributoCopia(String cdIden) throws ApplicationException{
    	HashMap map = new HashMap();
    	map.put("cdIden", cdIden);
    	return   (ConfigurarFormaCalculoAtributoVariableVO) getBackBoneInvoke(map,"OBTIENE_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS_COPIA");    	
    }
    
    /**
	 *  Inserta una forma de calculo variable
	 *  Hace uso del Store Procedure PKG_PARAMETRIZA.P_GUARDA_ATRIBFC
	 * 
	 *  @param configurarFormaCalculoAtributoVariableVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public String agregarConfigurarFormaCalculoAtributo(ConfigurarFormaCalculoAtributoVariableVO configurarFormaCalculoAtributoVariableVO) throws ApplicationException{
    	HashMap map = new HashMap();       	
        
        map.put("cdIden",configurarFormaCalculoAtributoVariableVO.getCdIden());
        map.put("cdUnieco",configurarFormaCalculoAtributoVariableVO.getCdUnieco());       
        map.put("cdRamo",configurarFormaCalculoAtributoVariableVO.getCdRamo());
        map.put("cdElemento",configurarFormaCalculoAtributoVariableVO.getCdElemento());
        map.put("cdTipSit",configurarFormaCalculoAtributoVariableVO.getCdTipSit());
        map.put("cdTabla",configurarFormaCalculoAtributoVariableVO.getCdTabla());        
        map.put("swFormaCalculo",configurarFormaCalculoAtributoVariableVO.getSwFormaCalculo());
        
        WrapperResultados res =  returnBackBoneInvoke(map,"AGREGAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS");
        return res.getMsgText();
    }
    
    /**
	 *  Actualiza una forma de calculo variable
	 *  Hace uso del Store Procedure PKG_PARAMETRIZA.P_GUARDA_ATRIBFC
	 * 
	 *  @param configurarFormaCalculoAtributoVariableVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public String guardarConfigurarFormaCalculoAtributo(ConfigurarFormaCalculoAtributoVariableVO configurarFormaCalculoAtributoVariableVO) throws ApplicationException {
      
    	HashMap map = new HashMap();       	
       
        map.put("cdIden",configurarFormaCalculoAtributoVariableVO.getCdIden());
        map.put("cdUnieco",configurarFormaCalculoAtributoVariableVO.getCdUnieco());       
        map.put("cdRamo",configurarFormaCalculoAtributoVariableVO.getCdRamo());
        map.put("cdElemento",configurarFormaCalculoAtributoVariableVO.getCdElemento());
        map.put("cdTipSit",configurarFormaCalculoAtributoVariableVO.getCdTipSit());
        map.put("cdTabla",configurarFormaCalculoAtributoVariableVO.getCdTabla());        
        map.put("swFormaCalculo",configurarFormaCalculoAtributoVariableVO.getSwFormaCalculo());
        
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS");
        return res.getMsgText();
    }

	/**
	 *  Elimina una configuracion de forma de calculo variable
	 *  Hace uso del Store Procedure PKG_PARAMETRIZA.P_BORRA_FORCALATRIB
	 * 
	 *  @param cdIden
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public String borrarConfigurarFormaCalculoAtributo(String cdIden) throws ApplicationException{
    
    	// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("cdIden",cdIden);
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS");
        return res.getMsgText();
	}   
    
	/**
	 *  Copia una forma de calculo varible
	 *  Hace uso del Store Procedure PKG_PARAMETRIZA.P_COPIA_FORM_CAL
	 * 
	 *  @param configurarFormaCalculoAtributoVariableVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public WrapperResultados copiarConfigurarFormaCalculoAtributo(ConfigurarFormaCalculoAtributoVariableVO configurarFormaCalculoAtributoVariableVO) throws ApplicationException{
		
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		 map.put("cdIden",configurarFormaCalculoAtributoVariableVO.getCdIden() );
	     map.put("cdUnieco",configurarFormaCalculoAtributoVariableVO.getCdUnieco());
	     map.put("cdRamo",configurarFormaCalculoAtributoVariableVO.getCdRamo());       
	     map.put("cdElemento",configurarFormaCalculoAtributoVariableVO.getCdElemento());
		
        WrapperResultados res =  returnBackBoneInvoke(map,"COPIAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS");
        return res;
    	
    }
    
	/**
	 *  Obtiene un formas de calculo variable
	 *  Hace uso del Store Procedure PKG_PARAMETRIZA.P_ATRIB_FORCAL
	 * 
	 *  @param dsUnieco
	 *  @param dsRamo
	 *  @param dsElemen
	 *  @param dsTipSit
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public PagedList buscarConfigurarFormaCalculoAtributo(String dsUnieco, String dsRamo, String dsElemen, String dsTipSit, int start, int limit )throws ApplicationException{
    	// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("dsUnieco",dsUnieco);
		map.put("dsRamo",dsRamo);
		map.put("dsElemen",dsElemen);
		map.put("dsTipSit", dsTipSit);
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "BUSCAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS", start, limit);
	}

    /**
	 *  Obtiene un conjunto de formas de calculo variable para la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure PKG_PARAMETRIZA.P_ATRIB_FORCAL
	 *  
	 *  @param dsUnieco
	 *  @param dsRamo
	 *  @param dsElemen
	 *  @param dsTipSit
	 *    
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */  
    @SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsUnieco, String dsRamo, String dsElemen, String dsTipSit) throws ApplicationException {
 		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("dsUnieco",dsUnieco);
		map.put("dsRamo",dsRamo);
		map.put("dsElemen",dsElemen);
		map.put("dsTipSit", dsTipSit);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Cliente","Tipo de Situacion","Aseguradora","Producto","Atributo","Calculo"});
		return model;
    }
    
    
}
