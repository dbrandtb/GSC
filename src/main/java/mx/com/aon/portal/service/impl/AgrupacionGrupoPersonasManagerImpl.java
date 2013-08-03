package mx.com.aon.portal.service.impl;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import mx.com.aon.core.ApplicationException;

import mx.com.aon.portal.model.AgrupacionGrupoPersonaVO;
import mx.com.aon.portal.model.GrupoPersonaVO;
import mx.com.aon.portal.service.AgrupacionGrupoPersonasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements AgrupacionGrupoPersonasManager
 * 
 * @extends AbstractManager
 */
public class AgrupacionGrupoPersonasManagerImpl extends AbstractManager implements AgrupacionGrupoPersonasManager {
	
	/**
	 *  Obtiene una agrupacion grupo de personas
	 *  Hace uso del Store Procedure PKG_AGRUPAPOL.P_OBTIENE_AGRUPACION
	 * 
	 *  @param cdGrupo
	 *  
	 *  @return Objeto AgrupacionGrupoPersonaVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public AgrupacionGrupoPersonaVO getAgrupacionGrupoPersonas (String cdGrupo) throws ApplicationException
	{

			HashMap map = new HashMap();
			map.put("cdGrupo",cdGrupo);
						
            return (AgrupacionGrupoPersonaVO)getBackBoneInvoke(map,"OBTIENERREG_AGRUPACION_GRUPO_PERSONAS");

	}

	/**
	 *  Obtiene un conjunto de agrupacion grupos de persona
	 *  Hace uso del Store Procedure PKG_AGRUPAPOL.P_OBTIENE_GRUPOPER
	 * 
	 *  @param cdGrupo
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *   
	 *  @return Objeto GrupoPersonaVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList getGruposPersonas(String cdGrupo,int start, int limit )throws ApplicationException
	{
			// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
			map.put("cdGrupo",cdGrupo);
			map.put("start",start);
			map.put("limit",limit);
	
            return pagedBackBoneInvoke(map, "OBTIENERREG_GRUPO_PERSONAS", start, limit);

	}
	
	/**
	 *  Inserta una agrupacion grupo de persona
	 *  Hace uso del Store Procedure PKG_AGRUPAPOL.P_GUARDA_GRUPOPER
	 * 
	 *  @param cdConfiguracion
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")	
	public String agregarAgrupacionGrupoPersona(GrupoPersonaVO grupoPersonaVO) throws ApplicationException{
   		
		HashMap map = new HashMap();
		map.put("cdGrupo",grupoPersonaVO.getCdGrupo());
		map.put("cdAgrGrupo", grupoPersonaVO.getCdAgrGrupo());
		map.put("cdAgrupa",grupoPersonaVO.getCdAgrupa());
		map.put("cdGrupoPer",grupoPersonaVO.getCdGrupoPer());	
		map.put("cdPolMtra",grupoPersonaVO.getCdPolMtra());	
      
        WrapperResultados res =  returnBackBoneInvoke(map,"INSERTA_AGRUPACION_GRUPO_PERSONAS");

        return res.getMsgText();

    }
	
	/**
	 *  Actualiza una agrupacion grupo de persona
	 *  Hace uso del Store Procedure PKG_AGRUPAPOL.P_GUARDA_GRUPOPER
	 * 
	 *  @param cdConfiguracion
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String guardarAgrupacionGrupoPersona(GrupoPersonaVO grupoPersonaVO) throws ApplicationException{
	
		HashMap map = new HashMap();
		map.put("cdGrupo",grupoPersonaVO.getCdGrupo());
		map.put("cdAgrGrupo", grupoPersonaVO.getCdAgrGrupo());
		map.put("cdAgrupa",grupoPersonaVO.getCdAgrupa());
		map.put("cdGrupoPer",grupoPersonaVO.getCdGrupoPer());	
		map.put("cdPolMtra",grupoPersonaVO.getCdPolMtra());	
		
				
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_AGRUPACION_GRUPO_PERSONAS");
        return res.getMsgText();
	}


	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}
	
	 @SuppressWarnings("unchecked")
	    public String borrarGrupo(String pv_cve_grupo_i, String pv_cdagrgrupo_i)throws ApplicationException {
	    	// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
			map.put("pv_cve_grupo_i",pv_cve_grupo_i);
			map.put("pv_cdagrgrupo_i",pv_cdagrgrupo_i);
	        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_GRUPO_PERSONAS");
	        return res.getMsgText();
		}
	
	
	
   

}
