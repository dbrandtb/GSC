
package mx.com.aon.portal.service.impl;


import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.service.AsociarFormatosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements AsociarFormatosManager
 * 
 * @extends AbstractManager
 */
public class AsociarFormatosManagerImpl extends AbstractManager implements AsociarFormatosManager {

	/**
	 *  Elimina una configuracion de asociar formatos
	 *  Hace uso del Store Procedure PKG_ORDENT.P_BORRA_FORMATOSXCTA
	 * 
	 *  @param cdAsocia
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String borrarAsociarFormatos(String cdAsocia) throws ApplicationException{
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("cdAsocia",cdAsocia);
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_ASOCIAR_FORMATOS");
        return res.getMsgText();
	}
	
	/**
	 *  Obtiene un conjunto de asociar formatos
	 *  Hace uso del Store Procedure PKG_ORDENT.P_OBTIENE_FORMATOSXCTA
	 * 
	 *  @param dsProceso
	 *  @param dsElemen
	 *  @param dsRamo
	 *  @param dsFormatoOrden
	 *  @param dsUnieco
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public PagedList buscarAsociarFormatos(String dsProceso, String dsElemen, String dsRamo, String dsFormatoOrden, String dsUnieco, int start, int limit ) throws ApplicationException{
        // Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("dsProceso",dsProceso);
		map.put("dsElemen", dsElemen);
		map.put("dsRamo",dsRamo);
		map.put("dsFormatoOrden",dsFormatoOrden);
		map.put("dsUnieco", dsUnieco);
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "BUSCAR_ASOCIAR_FORMATOS", start, limit);
	}
	
    /**
	 *  Obtiene un conjunto de asociar formatos para la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure PKG_ORDENT.P_OBTIENE_FORMATOSXCTA
	 *  
	 *  @param dsProceso
	 *  @param dsElemen
	 *  @param dsRamo
	 *  @param dsFormatoOrden
	 *  @param dsUnieco
	 *    
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */ 	
    @SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsProceso, String dsElemen, String dsRamo, String dsFormatoOrden, String dsUnieco) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("dsProceso",dsProceso);
		map.put("dsElemen", dsElemen);
		map.put("dsRamo",dsRamo);
		map.put("dsFormatoOrden",dsFormatoOrden);
		map.put("dsUnieco", dsUnieco);				
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_ASOCIAR_FORMATOS_EXPORT");
		model.setInformation(lista);
		model.setColumnName(new String[]{"Proceso","Formato","Cliente","Aseguradora","Producto"});
		return model;
    }
	   
}


	
	
	


