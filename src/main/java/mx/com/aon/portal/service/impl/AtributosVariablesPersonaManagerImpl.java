package mx.com.aon.portal.service.impl;

import java.util.HashMap;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.AtributosVariablesPersonaVO;
import mx.com.aon.portal.service.AtributosVariablesPersonaManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

public class AtributosVariablesPersonaManagerImpl extends AbstractManager implements AtributosVariablesPersonaManager{
	
	/**
	 *  Elimina un atributo variable por persona seleccionado.
	 *  Hace uso del Store Procedure PKG_WIZARD.P_BORRA_TATRIPER.
	 * 
	 *  @param cdAtribu
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String borrarAtributosVariablesPersona(String cdAtribu)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cdatribu_i", cdAtribu);
		
		String endpointName = "BORRA_TATRIPER";		
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}
	
	/**
	 *  Obtiene un conjunto de atributos variables por persona.
	 *  Hace uso del Store Procedure PKG_WIZARD. P_ATRIBVARROLINC.
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarAtributosVariablesPersona(int start, int limit)
			throws ApplicationException {
		
		String endpointName = "ATRIBVARROLINC";
		return pagedBackBoneInvoke(null, endpointName, start, limit);
	}
	
	/**
	 *  Guarda atributos variable por persona
	 * 
	 *  @param AtributoVaribleAgenteVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String guardarAtributosVariablesPersona(AtributosVariablesPersonaVO atributosVariablesPersonaVO)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("cdAtribu",atributosVariablesPersonaVO.getCdAtribu());
		map.put("dsAtribu",atributosVariablesPersonaVO.getDsAtribu());
		map.put("swFormat",atributosVariablesPersonaVO.getSwFormat());
		map.put("nmlMin",atributosVariablesPersonaVO.getNmlmin());
		map.put("nmlMax",atributosVariablesPersonaVO.getNmlmax());
		map.put("otTabVal",atributosVariablesPersonaVO.getOtTabVal());
		map.put("gbSwFormat",atributosVariablesPersonaVO.getGbSwFormat());
		//String _swObliga = (atributosVariablesPersonaVO.getSwObliga().equals("true"))?"S":"N";
		map.put("pv_swobliga_i",atributosVariablesPersonaVO.getSwObliga());
        map.put("pv_cdfisjur_i",atributosVariablesPersonaVO.getCdFisJur());
		map.put("pv_cdcatego_i",atributosVariablesPersonaVO.getCdCatego());
		
		String endpointName = "INSERT_TATRIPER";		
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}

}
