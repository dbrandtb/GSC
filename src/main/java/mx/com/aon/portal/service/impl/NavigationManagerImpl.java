package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.ItemVO;
import mx.com.aon.portal.model.RamaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.NavigationManager;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;
/**
 * 
 * @author sergio.ramirez
 * @version 1.1
 *
 */
public class NavigationManagerImpl implements NavigationManager {

	private static Logger logger = Logger.getLogger(NavigationManagerImpl.class);
	
	private Map<String, Endpoint> endpoints;

	/**
	 * Extrae la lista con los elementos del menu de navegacion del portal
	 * @param perfil con el que se hace la consulta
	 * @return List con los elementos del menu
	 * @throws ApplicationException 
	 */
	@SuppressWarnings("unchecked") // Manejo de ArrayList Controlado
	public List<ItemVO> getMenuNavegacion(String perfil) throws ApplicationException {
		
		List<ItemVO> lista = null;
		
		try {
			Endpoint manager = endpoints.get("CARGA_NAVEGACION");
			ArrayList<ItemVO> invoke = (ArrayList<ItemVO>)manager.invoke( perfil );
			lista = invoke;
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'Consulta de menu navegacion'",bae);
			throw new ApplicationException("Excepcion al consultarse el menu navigacion");
		}
		
		return lista;
	}
	
	/**
	 * Obtiene la lista de los Nodos y las Hojas para el arbol de Seleccion
	 * de Rol y Cliente
	 * @param User - usuario admitido desde el login
	 * @return Lista con elementos del Arbol.
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List<RamaVO> getClientesRoles(String user)throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("user", user);
		List<RamaVO> listaRolesClientes = null; 
		try{
			Endpoint endpoint= (Endpoint) endpoints.get("CARGA_ROLES_CLIENTES");
			listaRolesClientes=(List<RamaVO>) endpoint.invoke(params);
		}catch (BackboneApplicationException bae) {
			throw new ApplicationException("Error retrieving data");
		}
		return(List<RamaVO>) listaRolesClientes;
	}
	public void setEndpoints(Map<String, Endpoint> endpoints) {this.endpoints = endpoints;}

	/**
	 * Obtiene el numero de registros del usuario
	 * @param user
	 * @return numero
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public int getNumRegistro(String user) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("user", user);
		int numero = 0; 
		try{
			Endpoint endpoint= (Endpoint) endpoints.get("CARGA_NUMERO_REGISTRO");
			numero=(Integer)endpoint.invoke(params);
		}catch (BackboneApplicationException bae) {
			throw new ApplicationException("Error retrieving data");
		}		
		return numero;
	}

	/**
	 * Obtiene lista de variables Iso por usuario
	 * @param user
	 * @return Lista de variables iso.
	 * @throws ApplicationException.
	 */
	@SuppressWarnings("unchecked")
	public IsoVO getVariablesIso(String user) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("user", user);
		IsoVO listaVariablesIso = null; 
		try{
			Endpoint endpoint= (Endpoint) endpoints.get("CARGA_VARIABLES_USER_ISO");
			listaVariablesIso=  (IsoVO) endpoint.invoke(params);
		}catch (BackboneApplicationException bae) {
			throw new ApplicationException("Error retrieving data");
		}
		return (IsoVO)listaVariablesIso;
	}

	public List<UserVO> getAttributesUser(String user)	throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("user", user);
		List<UserVO> listaUsers = null; 
		try{
			Endpoint endpoint= (Endpoint) endpoints.get("CARGA_ROLES_CLIENTES_USER");
			listaUsers=(List<UserVO>) endpoint.invoke(params);
		}catch (BackboneApplicationException bae) {
			throw new ApplicationException("Error retrieving data");
		}
		return(List<UserVO>) listaUsers;
	}
}
