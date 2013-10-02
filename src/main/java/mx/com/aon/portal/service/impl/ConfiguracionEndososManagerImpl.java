package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.service.ConfiguracionEndososManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Clase que implementa la interfaz ConfiguracionEndososManager.
 * 
 * @extends AbstractManager
 *
 */
public class ConfiguracionEndososManagerImpl extends AbstractManager implements ConfiguracionEndososManager{

	/**
	 * Metodo que realiza la eliminacion de un tipo de suplemento seleccionado en pantalla.
	 * Invoca al Store Procedure PKG_ENDOSOS.P_BORRA_TTIPSUPL.
	 * 
	 * @param cdTipSup
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.

	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String borrarTipoSuplemento(String cdTipSup)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdtipsup_i", cdTipSup);
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_TTIPSUPL");
        return res.getMsgText();
	}
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros con tipos de suplemento para la 
	 * pantalla de Configuracion de Endosos.
	 * Invoca al Store Procedure PKG_ENDOSOS.P_OBTIENE_TTIPSUPL.
	 * 
	 * @param start
	 * @param limit
	 * @param cdTipSup
	 * @param dsTipSup
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerTiposSuplementos(int start, int limit,String cdTipSup, String dsTipSup) throws ApplicationException {
		{			
			HashMap map = new HashMap();
			map.put("pv_cdtipsup_i", cdTipSup);
			map.put("pv_dstipsup_i", dsTipSup);
			String endpointName = "OBTIENE_TTIPSUPL";
			return pagedBackBoneInvoke(map, endpointName, start, limit);
		}
	}
	
	/**
	 * Metodo que obtiene un conjunto de registros de tipos de suplementos para ser 
	 * exportados en formato PDF, XSL, TXT, etc.
	 * Invoca al Store Procedure PKG_ENDOSOS.P_OBTIENE_TTIPSUPL.
	 * 
	 * @param dsTipSup
	 * 
	 * @return TableModelExport
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsTipSup) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();		
		List lista = null;
		
		HashMap map = new HashMap();
		map.put("pv_cdtipsup_i", null);
		map.put("pv_dstipsup_i", dsTipSup);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_TTIPSUPL_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Codigo","Descripcion","Tarifacion Automatica"});
		
		return model;
	}
	
	
	
	/**
	 * Metodo que inserta un nuevo tipo de suplemento o actualiza un tipo de suplemento
	 * editado en pantalla.
	 * Invoca al Store Procedure PKG_ENDOSOS.P_GUARDA_TTIPSUPL.
	 * 
	 * @param cdTipSup
	 * @param dsTipSup
	 * @param swTariFi
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String guardarOActualizarTipoSuplemento(String cdTipSup, 
			String dsTipSup, String swTariFi)throws ApplicationException {
		HashMap map = new HashMap();
		
		String codigo = (cdTipSup != "" || cdTipSup != null)?cdTipSup:null;
		map.put("pv_cdtipsup_i",codigo);
		map.put("pv_dstipsup_i",dsTipSup);
		map.put("pv_swtarifi_i",swTariFi);
        WrapperResultados res = returnBackBoneInvoke(map, "GUARDA_TTIPSUPL");
        return res.getMsgText();
	}
}
