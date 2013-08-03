package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.PortalVO;
import mx.com.aon.portal.service.PaginaPrincipalManager;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

public class PaginaPrincipalManagerImpl implements PaginaPrincipalManager {

	/**
	 *Serial Version 
	 */
	private static final long serialVersionUID = -6140188280302464695L;
	@SuppressWarnings("unchecked")
	private Map endpoints;
	
	/*
	 * (non-Javadoc)
	 * @see mx.com.aon.portal.service.PaginaPrincipalManager#getConfiguracionInicial(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<PortalVO> getConfiguracionInicial(String rolUsuario, String elemento)throws ApplicationException{
		
		Map params = new HashMap<String, String>();
		params.put("nombreCliente", elemento);
		params.put("rolCliente", rolUsuario);
		List<PortalVO> componentes=null;
		try{
			Endpoint endpoint = (Endpoint) endpoints.get("CARGA_COMPONENTES");
			componentes =(List<PortalVO>) endpoint.invoke(params);
			
		}catch (BackboneApplicationException bae) {
			throw new ApplicationException("Error al recuperar la lista de componentes");
		}
		return (List<PortalVO>) componentes;
	
	}
	
	@SuppressWarnings("unchecked")
	public String getUserEmail(String cdUsuario)throws ApplicationException{
		String email=null;
		Map params = new HashMap<String, String>();
		params.put("CDUSUARIO", cdUsuario);
		
		ArrayList emails;
		
		try{
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_EMAIL");
			emails=(ArrayList) endpoint.invoke(params);
			
		}catch (BackboneApplicationException bae) {
			throw new ApplicationException("Error obtener los emails");
		}
		
		if(emails!=null && !emails.isEmpty()){
			if(emails.get(0)!=null){
				email= (String) emails.get(0);
			}
		}
		return email;
	}

	@SuppressWarnings("unchecked")
	public void setEndpoints(Map endpoints) {
		this.endpoints = endpoints;
	}
}

