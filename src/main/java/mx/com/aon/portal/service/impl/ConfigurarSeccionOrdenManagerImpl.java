package mx.com.aon.portal.service.impl;

import java.util.HashMap;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.ConfigurarSeccionOrdenManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;


/**
 * Implementacion de la interface de servicios para configurar seccion orden.
 *
 */
public class ConfigurarSeccionOrdenManagerImpl extends AbstractManager implements ConfigurarSeccionOrdenManager{


	/**
	 *  Da de baja a informacion de configurar seccion orden.
	 *  Usa el store procedure PKG_ORDENT.P_ELIMINA_SECCION_FORMATO.
	 *  
	 *  @param cdFormatoOrden
	 *	@param cdSeccion 
	 *
	 *	@return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	@SuppressWarnings("unchecked")
	public String borraSeccionFormato(String cdFormatoOrden, String cdSeccion)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdformatoorden", cdFormatoOrden);
		map.put("cdseccion", cdSeccion);
		WrapperResultados res = returnBackBoneInvoke(map, "ELIMINA_SECCION_FORMATO");
		
		return res.getMsgText();
	}


	/**
	 * Salva la informacion de configurar seccion orden.
	 * Usa el store procedure PKG_ORDENT.P_GUARDA_SECCION_FORMATO.
	 *  
	 *  @param cdFormatoOrden
	 *  @param cdSeccion
	 *  @param nmOrden
	 *  @param cdTipSit
	 *  @param cdTipObj
	 *  
	 *	@return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */	
	@SuppressWarnings("unchecked")
	public String guardarSeccionFormato(String cdFormatoOrden, String cdSeccion,
			String nmOrden, String cdTipSit, String cdTipObj)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdformatoorden", cdFormatoOrden);
		map.put("cdseccion", cdSeccion);
		map.put("nmorden", nmOrden);
		map.put("cdtipsit", cdTipSit);
		map.put("cdtipobj", cdTipObj);
		WrapperResultados res = returnBackBoneInvoke(map, "GUARDA_SECCION_FORMATO");
		
		return res.getMsgText();
	}

	
	/**
	 * Obtiene un conjunto de configurar seccion orden.
	 * Usa el store procedure PKG_ORDENT.P_OBTIENE_SECCIONES_FORMATO.
	 * @param cdFormatoOrden 
	 * @param start
	 * @param limit
	 * 
	 * @return Objeto PagedList 
	 *			
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerSeccionesFormato(String cdFormatoOrden, int start, int limit)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdformatoorden", cdFormatoOrden);
		return pagedBackBoneInvoke(map, "OBTIENE_SECCIONES_FORMATO", start, limit);
	}

}
