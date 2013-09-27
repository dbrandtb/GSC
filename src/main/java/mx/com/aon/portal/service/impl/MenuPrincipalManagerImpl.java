package mx.com.aon.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.MenuPrincipalVO;
import mx.com.aon.portal.service.MenuPrincipalManager;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;

/**
 * 
 * @author sergio.ramirez
 * 
 */
public class MenuPrincipalManagerImpl extends AbstractManager implements MenuPrincipalManager {
	
	@SuppressWarnings("unchecked")
	private Map endpoints;

	/* Obtener el menu principal utilizando BACKBONE para el acceso a BD
	@SuppressWarnings("unchecked")
	public List<MenuPrincipalVO> getListaMenus(String claveCliente,String claveRol, String usuario) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("claveCliente", claveCliente);
		params.put("claveRol", claveRol);
		params.put("usuario", usuario);
		List<MenuPrincipalVO> listaMenu = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("CARGA_MENU_PRINCIPAL");
			listaMenu = (List<MenuPrincipalVO>) endpoint.invoke(params);
		} catch (BackboneApplicationException bae) {
			throw new ApplicationException("Error al recuperar el menu principal");
		}
		return (List<MenuPrincipalVO>) listaMenu;
	}*/
	
	/**
	 *  Obtiene menu principal utilizando JdbcTemplates para acceso a BD
	 *  Hace uso del Store Procedure PKG_MENU.P_FORMA_MENU
	 *
	 *  @param cdElemento
	 *  @param cdRol
	 *  @param cdUsuario
	 *
	 *  @return List MenuPrincipalVO
	 *
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<MenuPrincipalVO> getListaMenus (String cdElemento, String cdRol, String cdUsuario) throws ApplicationException {
	        
	        HashMap map = new HashMap();
	        map.put("PV_CDELEMENTO_I", cdElemento);
			map.put("PV_CDROL_I", cdRol);
			map.put("PV_CDUSUARIO_I", cdUsuario);
	        String endpointName = "CARGA_MENU_PRINCIPAL";
	        return  getAllBackBoneInvoke(map, endpointName);
	}
		
	/**
	 *  Obtiene menu VERTICAL utilizando JdbcTemplates para acceso a BD
	 *  Hace uso del Store Procedure PKG_MENU.P_FORMA_MENU_VERTICAL
	 *
	 *  @param claveCliente
	 *  @param claveRol
	 *  @param usuario
	 *
	 *  @return List MenuPrincipalVO
	 *
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<MenuPrincipalVO> getListaMenuVertical(String claveCliente,String claveRol, String usuario) throws ApplicationException {
		
		Map params = new HashMap<String, String>();
		params.put("claveCliente", claveCliente);
		params.put("claveRol", claveRol);
		params.put("usuario", usuario);
		List<MenuPrincipalVO> listaMenuVertical = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("CARGA_MENU_VERTICAL");
			listaMenuVertical = (List<MenuPrincipalVO>) endpoint.invoke(params);
		} catch (BackboneApplicationException bae) {
			throw new ApplicationException("Error al recuperar el menu vertical");
		}
		return (List<MenuPrincipalVO>) listaMenuVertical;
	}
	

	@SuppressWarnings("unchecked")
	public void setEndpoints(Map endpoints) {
		this.endpoints = endpoints;
	}

}
