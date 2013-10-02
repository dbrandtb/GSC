package mx.com.aon.catweb.configuracion.producto.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.datosFijos.model.DatoFijoVO;
import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.service.DatosFijosManager;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

public class DatosFijosManagerImpl implements DatosFijosManager {
	/**
	 * Logger de la clase.
	 */
	private static Logger logger = Logger.getLogger(DatosFijosManagerImpl.class);

	private Map<String, Endpoint> endpoints;

	
	public List<LlaveValorVO> catalogoBloque() throws ApplicationException {
		
		List<LlaveValorVO> resultado = null;
		try{
			Endpoint manager = (Endpoint) endpoints.get("OBTENER_CATALOGO_BLOQUES");
			resultado = (ArrayList<LlaveValorVO>) manager.invoke(null);
		}catch(BackboneApplicationException bae){
			
		}
		return resultado;
	}

	public List<LlaveValorVO> catalogoCampo(String claveBloque)
			throws ApplicationException {
		List<LlaveValorVO> resultado = null;
		try{
			Endpoint manager = (Endpoint) endpoints.get("OBTENER_CATALOGO_CAMPOS");
			resultado = (ArrayList<LlaveValorVO>) manager.invoke(claveBloque);
		}catch(BackboneApplicationException bae){
			
		}
		return resultado;
	}

	public List<DatoFijoVO> listaDatosFijos(String codigoRamo) throws ApplicationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("codigoProducto", codigoRamo);
		List<DatoFijoVO> resultado = null;
		try{
			Endpoint manager = (Endpoint) endpoints.get("OBTENER_DATOS_FIJOS_PRODUCTO");
			resultado = (ArrayList<DatoFijoVO>) manager.invoke(params);
		}catch(BackboneApplicationException bae){
			
		}
		return resultado;
	}

	public void insertarDatoFijo(DatoFijoVO dfvo) throws ApplicationException{	
		
		try{
			Endpoint manager = (Endpoint) endpoints.get("INSERTAR_DATO_FIJO");
			manager.invoke(dfvo);
		}catch(BackboneApplicationException bae){
			
		}
	}
	/**
	 * @param endpoints
	 *            the endpoints to set
	 */
	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}
}
