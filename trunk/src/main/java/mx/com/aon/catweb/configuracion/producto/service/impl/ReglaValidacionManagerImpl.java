package mx.com.aon.catweb.configuracion.producto.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.reglaValidacion.model.ReglaValidacionVO;
import mx.com.aon.catweb.configuracion.producto.service.ReglaValidacionManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;

import org.apache.log4j.Logger;

/**
 * Clase concreta que implementa los metodos de la interface
 * {@link ReglaValidacionManager} para insertar, modificar y eliminar las reglas de
 * validacion del producto.
 * 
 * @since 1.0
 * @author <a href="mailto:edgar.perez@biosnettcs.com">Edgar Perez</a>
 * @version $Id$
 * @see ReglaValidacionManager
 */

public class ReglaValidacionManagerImpl implements ReglaValidacionManager {

	private static Logger logger = Logger.getLogger(ReglaValidacionManagerImpl.class);
	/**
	 * Asigna al Map de objetos de tipo {@link Endpoint} con los vm que se
	 * pueden invocar.
	 * 
	 * @param endpoints
	 */
	private Map<String, Endpoint> endpoints;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.biosnet.ice.wizard.productos.service.ReglaValidacionManager#obtieneReglasDeValidacion(com.biosnet.ice.wizard.productos.reglaValidacion.model.ReglaValidacionVO)
	 */
	public List<ReglaValidacionVO> obtieneReglasDeValidacion(String codigoRamo)
			throws ApplicationException {
		List<ReglaValidacionVO> listaReglasValidacion = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("OBTIENE_REGLAS_DE_VALIDACION");
			listaReglasValidacion = (List<ReglaValidacionVO>) manager.invoke(codigoRamo);

			if (listaReglasValidacion == null) {
				throw new ApplicationException("No exiten reglas de validacion");
			}
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_REGLAS_DE_VALIDACION'", bae);
			throw new ApplicationException(
					"Error al cargar lista de reglas de validacion");
		}
		return listaReglasValidacion;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.biosnet.ice.wizard.productos.service.ReglaValidacionManager#obtieneListaBloques()
	 */
	public List<LlaveValorVO> obtieneListaBloques() throws ApplicationException {
		List<LlaveValorVO> listaBloques = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("OBTIENE_LISTA_BLOQUES");
			listaBloques = (List<LlaveValorVO>) manager.invoke(null);
			
			if (listaBloques == null) {
				throw new ApplicationException("No exite ningun bloque");
			} 
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_LISTA_BLOQUES'", bae);
			throw new ApplicationException(
					"Error al cargar los bloques de reglas de validacion desde el sistema");
		}
		return listaBloques;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.biosnet.ice.wizard.productos.service.ReglaValidacionManager#obtieneListaValidaciones()
	 */
	public List<LlaveValorVO> obtieneListaValidaciones()
			throws ApplicationException {
		List<LlaveValorVO> listaValidaciones = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("OBTIENE_LISTA_VALIDACIONES");
			listaValidaciones = (List<LlaveValorVO>) manager.invoke(null);
			
			if (listaValidaciones == null) {
				throw new ApplicationException("No exite ninguna validacion");
			} 
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_LISTA_VALIDACIONES'", bae);
			throw new ApplicationException(
					"Error al cargar las validaciones de reglas de validacion desde el sistema");
		}
		return listaValidaciones;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.biosnet.ice.wizard.productos.service.ReglaValidacionManager#obtieneListaCondiciones()
	 */
	public List<LlaveValorVO> obtieneListaCondiciones()
	throws ApplicationException {
		List<LlaveValorVO> listaCondiciones = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("OBTIENE_LISTA_CONDICIONES");
			listaCondiciones = (List<LlaveValorVO>) manager.invoke(null);
			
			if (listaCondiciones == null) {
				throw new ApplicationException("No exite ninguna condicion");
			} 
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_LISTA_CONDICIONES'", bae);
			throw new ApplicationException(
			"Error al cargar las condiciones de reglas de validacion desde el sistema");
		}
		return listaCondiciones;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.biosnet.ice.wizard.productos.service.ReglaValidacionManager#asociarReglaDeValidacion(com.biosnet.ice.wizard.productos.reglaValidacion.model.ReglaValidacionVO)
	 */
	public void asociarReglaDeValidacion(ReglaValidacionVO reglaValidacion,
			String codigoRamo) throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints.get("ASOCIAR_REGLA_VALIDACION");
		Map params = new HashMap<String, String>();
		params.put("codigoRamo", codigoRamo);
		params.put("reglaValidacion", reglaValidacion);
		try {
			manager.invoke(params);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke ASOCIAR_REGLA_VALIDACION",bae);
			throw new ApplicationException(
					"Error al asociar regla de validacion del producto");
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.biosnet.ice.wizard.productos.service.ReglaValidacionManager#eliminaReglaDeValidacion(String,String,String)
	 */
	public void eliminaReglaDeValidacion(String codigoRamo,
			String codigoBloque, String secuencia) throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints.get("ELIMINAR_REGLA_VALIDACION");
		Map params = new HashMap<String, String>();
		params.put("codigoRamo", codigoRamo);
		params.put("codigoBloque", codigoBloque);
		params.put("secuencia", secuencia);
		try {
			manager.invoke(params);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke ELIMINAR_REGLA_VALIDACION",bae);
			throw new ApplicationException(
					"Error al eliminar regla de validacion del producto");
		}
		
	}
	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}



}
