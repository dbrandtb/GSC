package mx.com.aon.catweb.configuracion.producto.service.impl;

import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.service.TipoObjetoManager;
import mx.com.aon.catweb.configuracion.producto.tipoObjeto.model.DatoVariableObjetoVO;
import mx.com.aon.catweb.configuracion.producto.tipoObjeto.model.TipoObjetoVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;

import org.apache.log4j.Logger;

public class TipoObjetoManagerImpl implements TipoObjetoManager {

	private static Logger logger = Logger.getLogger(TipoObjetoManagerImpl.class);
	
	/**
	 * Mapa en el cual se introducen los Manager's para ser extraidos y
	 * utilizados como servicios.
	 */
	private Map<String, Endpoint> endpoints;
	
	public List<LlaveValorVO> catalogoTipoObjetoJson(String tipoObjeto)
			throws ApplicationException {
		List<LlaveValorVO> catalogo = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("CATALOGO_TIPO_OBJETO");
			catalogo = (List<LlaveValorVO>) manager.invoke(tipoObjeto);

			if (catalogo == null) {
				throw new ApplicationException(
						"No exite ningun atributo variable");
			} /*
				 * else { logger .debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				 * catalogotipo de objeto size" + catalogo.size()); }
				 */

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'CATALOGO_TIPO_OBJETO'",
					bae);
			throw new ApplicationException(
					"Error al cargar el catalogo de tipo objeto desde el sistema");
		}
		return catalogo;

	}

	public void agregaObjetoAlCatalogo(LlaveValorVO objeto)
	throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints.get("AGREGAR_TIPO_OBJETO_CATALOGO"); 
		try{
			manager.invoke(objeto);			
		}catch(BackboneApplicationException bae){
			logger.error("Exception in invoke AGREGAR_TIPO_OBJETO_CATALOGO", bae);
			throw new ApplicationException(
				"Error al insertar nuevo tipo de objeto al catalogo");
		}
		
	}

	public List<DatoVariableObjetoVO> listaDatosVariablesObjetos(String codigoObjeto)
	throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_DATOS_VARIABLES_OBJETO");
		 /*Map params = new HashMap<String, String>();
		 params.put("codigoRamo", codigoRamo);
		 params.put("codigoTipoSituacion", codigoTipoSituacion);
		 params.put("codigoRol", codigoRol);
		 params.put("codigoNivel", codigoNivel); 
		 */
		 List<DatoVariableObjetoVO> listaAtributos = null;
	        try {
	        	listaAtributos = (List<DatoVariableObjetoVO>) endpoint.invoke(codigoObjeto);
	        
	        } catch (BackboneApplicationException e) {
	            throw new ApplicationException("Error regresando lista de atributos variables de objeto");
	        }
	        return listaAtributos;
	}
	
	public void agregaDatosVariablesObjeto(List<DatoVariableObjetoVO> atributosObjeto)
		throws ApplicationException {
			Endpoint manager = (Endpoint) endpoints.get("AGREGAR_DATOS_VARIABLES_OBJETO"); 
			try{			
				manager.invoke(atributosObjeto);			
			}catch(BackboneApplicationException bae){
				logger.error("Exception in invoke AGREGAR_DATOS_VARIABLES_OBJETO", bae);
					throw new ApplicationException("Error al insertar atributoS variableS al objeto");
		}
		
	}
	
	public void agregarTipoObjetoInciso(TipoObjetoVO objeto)throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints.get("AGREGAR_TIPO_OBJETO_INCISO"); 
		try{
			manager.invoke(objeto);			
		}catch(BackboneApplicationException bae){
			logger.error("Exception in invoke AGREGAR_TIPO_OBJETO_INCISO", bae);
			throw new ApplicationException(
				"Error al insertar tipo de objeto al inciso");
		}
		
	}
	
	public MensajesVO borrarTipoObjetoInciso(TipoObjetoVO objeto)throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints.get("BORRAR_TIPO_OBJETO_INCISO");
		MensajesVO mensajeVO = null;
		try{
			mensajeVO = (MensajesVO)manager.invoke(objeto);			
		}catch(BackboneApplicationException bae){
			logger.error("Exception in invoke BORRAR_TIPO_OBJETO_INCISO", bae);
			throw new ApplicationException(
				"Error al borrar tipo de objeto al inciso");
		}
		return mensajeVO;
	}
	
	public void borraTatriObjeto(TipoObjetoVO objeto)throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("BORRA_TATRI_OBJETO");
		try{
			endpoint.invoke(objeto);
		}catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke BORRA_TATRI_OBJETO", bae);
			throw new ApplicationException("Error al eliminar objeto");
		}
	}
	
	public boolean tieneHijosAtributoVariableObjeto(TipoObjetoVO tipoObjeto)
			throws ApplicationException {
		MensajesVO mensaje = null;
		boolean tieneHijos = false;
		Endpoint endpoint = (Endpoint) endpoints.get("VALIDA_HIJOS_ATRIB_VAR_OBJETO");
		try {
			mensaje = (MensajesVO) endpoint.invoke(tipoObjeto);
		} catch (BackboneApplicationException bae) {
			logger.error("Se origino un error: " + bae.getMessage(), bae);
			throw new ApplicationException("Error intentando validar hijos de atributos variables de Objeto");
		} catch (Exception exc) {
			logger.error("Error: " + exc.getMessage(), exc);
			throw new ApplicationException("Error intentando validar hijos de atributos variables de Objeto");
		}
		if(mensaje.getMsgText().equals("1")){
			tieneHijos = true;
		}else{
			tieneHijos = false;
		}
		return tieneHijos;
	}

	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}

}