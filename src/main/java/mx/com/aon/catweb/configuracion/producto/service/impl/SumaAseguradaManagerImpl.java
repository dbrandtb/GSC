package mx.com.aon.catweb.configuracion.producto.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.service.SumaAseguradaManager;
import mx.com.aon.catweb.configuracion.producto.sumaAsegurada.model.SumaAseguradaIncisoVO;
import mx.com.aon.catweb.configuracion.producto.sumaAsegurada.model.SumaAseguradaVO;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

public class SumaAseguradaManagerImpl implements SumaAseguradaManager {

	private static Logger logger = Logger
			.getLogger(SumaAseguradaManagerImpl.class);

	/**
	 * Mapa en el cual se introducen los Manager's para ser extraidos y
	 * utilizados como servicios.
	 */
	private Map<String, Endpoint> endpoints;

	public List<LlaveValorVO> catalogoTipoSumaAsegurada()
			throws ApplicationException {
		List<LlaveValorVO> catalogoTipoSumaAsegurada = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("CATALOGO_TIPO_SUMA_ASEGURADA");
			catalogoTipoSumaAsegurada = (List<LlaveValorVO>) manager.invoke(null);

			if (catalogoTipoSumaAsegurada == null) {
				throw new ApplicationException("No exite ningun tipo de suma asegurada");
			}
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'CATALOGO_TIPO_SUMA_ASEGURADA'",bae);
			throw new ApplicationException(
					"Error al cargar el catalogo de tipo suma asegurada desde el sistema");
		}
		return catalogoTipoSumaAsegurada;

	}

	public List<LlaveValorVO> catalogoSumaAsegurada(String codigoRamo)
			throws ApplicationException {
		List<LlaveValorVO> catalogoSumaAsegurada = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("CATALOGO_SUMA_ASEGURADA");
			catalogoSumaAsegurada = (List<LlaveValorVO>) manager.invoke(codigoRamo);

			if (catalogoSumaAsegurada == null) {
				throw new ApplicationException("No exite suma asegurada");
			}
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'CATALOGO_SUMA_ASEGURADA'", bae);
			throw new ApplicationException(
					"Error al cargar el catalogo de suma asegurada desde el sistema");
		}
		return catalogoSumaAsegurada;
	}

	public List<LlaveValorVO> catalogoMonedaSumaASegurada()
			throws ApplicationException {
		List<LlaveValorVO> catalogoMoneda = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("CATALOGO_MONEDA_SUMA_ASEGURADA");
			catalogoMoneda = (List<LlaveValorVO>) manager.invoke(null);

			if (catalogoMoneda == null) {
				throw new ApplicationException("No exite ninguna moneda de suma asegurada");
			}
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'CATALOGO_MONEDA_SUMA_ASEGURADA'",bae);
			throw new ApplicationException(
					"Error al cargar el catalogo de moneda suma asegurada desde el sistema");
		}
		return catalogoMoneda;
	}

	public List<SumaAseguradaVO> listaSumaAsegurada(String codigoRamo,
			String codigoCapital) throws ApplicationException {
		List<SumaAseguradaVO> listaSumasAseguradas = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("LISTA_SUMAS_ASEGURADAS");
			Map params = new HashMap<String, String>();
			params.put("codigoRamo", codigoRamo);
			params.put("codigoCapital", codigoCapital);

			listaSumasAseguradas = (List<SumaAseguradaVO>) manager.invoke(params);

			if (listaSumasAseguradas == null) {
				throw new ApplicationException("No exite suma asegurada");
			}
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'LISTA_SUMAS_ASEGURADAS'", bae);
			throw new ApplicationException(
					"Error al cargar la lista de sumas aseguradas desde el sistema");
		}
		return listaSumasAseguradas;
	}

	public void agregaSumaAseguradaProducto(SumaAseguradaVO sumaAsegurada)
			throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints.get("AGREGAR_SUMA_ASEGURADA");
		try {
			manager.invoke(sumaAsegurada);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke AGREGAR_SUMA_ASEGURADA", bae);
			throw new ApplicationException("Error al insertar suma asegurada");
		}

	}

	public void eliminaSumaAseguradaProducto(String codigoCapital,
			String codigoRamo) throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints.get("ELIMINAR_SUMA_ASEGURADA_PRODUCTO");
		Map params = new HashMap<String, String>();
		params.put("codigoRamo", codigoRamo);
		params.put("codigoCapital", codigoCapital);
		try {
			manager.invoke(params);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke ELIMINAR_SUMA_ASEGURADA_PRODUCTO",bae);
			throw new ApplicationException(
					"Error al eliminar suma asegurada del producto");
		}

	}

	public List<SumaAseguradaIncisoVO> listaSumaAseguradaInciso(
			String codigoRamo, String codigoCobertura, String codigoCapital, String codigoTipoSituacion)
			throws ApplicationException {
		List<SumaAseguradaIncisoVO> listaSumasAseguradasInciso = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("LISTA_SUMAS_ASEGURADAS_INCISO");
			Map params = new HashMap<String, String>();
			params.put("codigoRamo", codigoRamo);
			params.put("codigoCapital", codigoCapital);
			params.put("codigoCobertura", codigoCobertura);
			params.put("codigoTipoSituacion", codigoTipoSituacion);
			listaSumasAseguradasInciso = (List<SumaAseguradaIncisoVO>) manager.invoke(params);

			if (listaSumasAseguradasInciso == null) {
				throw new ApplicationException("No exite suma asegurada");
			}
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'LISTA_SUMAS_ASEGURADAS_INCISO'", bae);
			throw new ApplicationException(
					"Error al cargar la lista de sumas aseguradas nivel inciso desde el sistema");
		}
		return listaSumasAseguradasInciso;
	}

	/*
	 * (non-Javadoc)
	 * @see com.biosnet.ice.wizard.productos.service.SumaAseguradaManager#agregaSumaAseguradaInciso(com.biosnet.ice.wizard.productos.sumaAsegurada.model.SumaAseguradaIncisoVO)
	 */
	public void agregaSumaAseguradaInciso(SumaAseguradaIncisoVO sumaAseguradaInciso)
			throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints.get("AGREGAR_SUMA_ASEGURADA_INCISO");
		try {
			manager.invoke(sumaAseguradaInciso);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke AGREGAR_SUMA_ASEGURADA_INCISO", bae);
			throw new ApplicationException("Error al insertar suma asegurada nivel inciso");
		}
		
	}

	public void eliminaSumaAseguradaInciso(String codigoCapital,
			String codigoRamo, String codigoTipoSituacion)
	throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints.get("ELIMINAR_SUMA_ASEGURADA_INCISO");
		Map params = new HashMap<String, String>();
		params.put("codigoRamo", codigoRamo);
		params.put("codigoCapital", codigoCapital);
		params.put("codigoTipoSituacion",codigoTipoSituacion);
		try {
			manager.invoke(params);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke ELIMINAR_SUMA_ASEGURADA_INCISO",bae);
			throw new ApplicationException(
					"Error al eliminar suma asegurada del inciso");
		}
		
	}

	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}



}
