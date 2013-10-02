
package mx.com.aon.portal.service.impl;

import java.util.HashMap;

import mx.com.aon.portal.model.AtributoVaribleAgenteVO;
import mx.com.aon.portal.service.AtributosVariablesAgenteManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.service.PagedList;
import mx.com.gseguros.exception.ApplicationException;


/**
 * Interface de servicios para Atributos Varibles por Agente
 *
 */

public class AtributosVariablesAgenteManagerImpl extends AbstractManager implements AtributosVariablesAgenteManager{
	
    /**
	 *  Elimina un atributo variable por agente seleccionado.
	 *  Hace uso del Store Procedure PKG_WIZARD.p_elimina_tatriage.
	 * 
	 *  @param cdAtribu
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    
	@SuppressWarnings("unchecked")
	public String borrarAtributosVariablesAgente (String cdAtribu) throws ApplicationException{
    	
		HashMap map = new HashMap();
		map.put("cdAtribu",cdAtribu);
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_ATRIBUTOS_VARIABLES_AGENTE");
        return res.getMsgText();
    	
    }

	/**
	 *  Obtiene un conjunto de atributos variables por agente.
	 *  Hace uso del Store Procedure no tengo idea.
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public PagedList buscarAtributosVariablesAgente(int start, int limit ) throws ApplicationException{
    	
		HashMap map = new HashMap();
		map.put("start",start);
		map.put("limit",limit);
		
        return pagedBackBoneInvoke(map, "BUSCAR_ATRIBUTOS_VARIABLES_AGENTE", start, limit);
    	
    	
    }

	/**
	 *  Guarda atributos variable por agente
	 * 
	 *  @param AtributoVaribleAgenteVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public String guardarAtributosVariablesAgente(AtributoVaribleAgenteVO atributoVaribleAgenteVO) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdAtribu",atributoVaribleAgenteVO.getCdAtribu());
		map.put("dsAtribu",atributoVaribleAgenteVO.getDsAtribu());
		map.put("swFormat",atributoVaribleAgenteVO.getSwFormat());
		map.put("nmlMax",atributoVaribleAgenteVO.getNmlMax());
		map.put("nmlMin",atributoVaribleAgenteVO.getNmlMin());
		map.put("swObliga",atributoVaribleAgenteVO.getSwObliga());
		map.put("otTabVal",atributoVaribleAgenteVO.getOtTabVal());
		map.put("gbSwFormat",atributoVaribleAgenteVO.getGbSwFormat());
				
        WrapperResultados res =  returnBackBoneInvoke(map,"INSERTAR_ATRIBUTOS_VARIABLES_AGENTE");
        return res.getMsgText();
    	
    	
    }
	
}
