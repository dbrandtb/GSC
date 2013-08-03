package mx.com.aon.catweb.configuracion.producto.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.conceptosCobertura.model.ConceptosCoberturaVO;
import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.service.ConceptosCoberturaManager;
import mx.com.aon.core.ApplicationException;

import org.apache.log4j.Logger;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

/**
 * Clase concreta que implementa los metodos de la interface
 * {@link ConceptosCoberturaManager} para insertar, modificar y eliminar los
 * conceptos de cobertura.
 * 
 * @since 1.0
 * @author <a href="mailto:edgar.perez@biosnettcs.com">Edgar Perez</a>
 * @version $Id$
 * @see ConceptosCoberturaManager
 */
public class ConceptosCoberturaManagerImpl implements ConceptosCoberturaManager {

	private static Logger logger = Logger
			.getLogger(ReglaValidacionManagerImpl.class);
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
	 * @see com.biosnet.ice.wizard.productos.service.ConceptosCoberturaManager#obtieneConceptosPorCobertura(String
	 *      codigoRamo)
	 */
	public List<ConceptosCoberturaVO> obtieneConceptosPorCobertura(
			String codigoRamo) throws ApplicationException {
		List<ConceptosCoberturaVO> listaConceptosCobertura = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("codigoProducto", codigoRamo);
		try {
			Endpoint manager = (Endpoint) endpoints
					.get("OBTIENE_CONCEPTOS_COBERTURA");
			listaConceptosCobertura = (List<ConceptosCoberturaVO>) manager
					.invoke(params);

			if (listaConceptosCobertura == null) {
				throw new ApplicationException(
						"No exiten conceptos de coberturas");
			}
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_CONCEPTOS_COBERTURA'",
					bae);
			throw new ApplicationException(
					"Error al cargar lista de conceptos de cobertura");
		}
		return listaConceptosCobertura;
	}

	public List<ConceptosCoberturaVO> obtieneConceptosPorCobertura(
			String codigoRamo, String codigoSituacion, String codigoCobertura)
			throws ApplicationException {
		List<ConceptosCoberturaVO> listaConceptosCobertura = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("codigoProducto", codigoRamo);
		params.put("codigoSituacion", codigoSituacion);
		params.put("codigoCobertura", codigoCobertura);
		try {
			
			Endpoint manager = (Endpoint) endpoints
					.get("OBTIENE_CONCEPTOS_COBERTURA");
			listaConceptosCobertura = (List<ConceptosCoberturaVO>) manager
					.invoke(params);

			if (listaConceptosCobertura == null) {
				throw new ApplicationException(
						"No exiten conceptos de coberturas");
			}
			
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_CONCEPTOS_COBERTURA'",
					bae);
			throw new ApplicationException(
					"Error al cargar lista de conceptos de cobertura");
		}
		return listaConceptosCobertura;
	}

	public List<LlaveValorVO> obtieneListaPeriodos(String codigoRamo)
			throws ApplicationException {
		List<LlaveValorVO> listaPeriodos = null;
		try {
			Endpoint manager = (Endpoint) endpoints
					.get("OBTIENE_LISTA_PERIODOS");
			listaPeriodos = (List<LlaveValorVO>) manager.invoke(codigoRamo);

			if (listaPeriodos == null) {
				throw new ApplicationException("No exiten periodos");
			}
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_LISTA_PERIODOS'", bae);
			throw new ApplicationException("Error al cargar lista de periodos");
		}
		return listaPeriodos;
	}

	public List<LlaveValorVO> obtieneListaCoberturas(String codigoRamo)
			throws ApplicationException {
		List<LlaveValorVO> listaCobertura = null;
		try {
			Endpoint manager = (Endpoint) endpoints
					.get("OBTIENE_LISTA_COBERTURA");
			listaCobertura = (List<LlaveValorVO>) manager.invoke(codigoRamo);

			if (listaCobertura == null) {
				throw new ApplicationException("No exiten coberturas");
			}
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_LISTA_COBERTURA'", bae);
			throw new ApplicationException(
					"Error al cargar lista de coberturas");
		}
		return listaCobertura;
	}

	public List<LlaveValorVO> obtieneListaConceptos(String parametro)
			throws ApplicationException {
		List<LlaveValorVO> listaConceptos = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("parametro", parametro);
		try {
			Endpoint manager = (Endpoint) endpoints
					.get("OBTIENE_LISTA_CONCEPTOS");
			listaConceptos = (List<LlaveValorVO>) manager.invoke(params);

			if (listaConceptos == null) {
				throw new ApplicationException("No exite ningun concepto");
			}
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_LISTA_CONCEPTOS'", bae);
			throw new ApplicationException(
					"Error al cargar los conceptos de conceptos por coberturas desde el sistema");
		}
		return listaConceptos;
	}

	public List<LlaveValorVO> obtieneListaComportamientos()
			throws ApplicationException {
		List<LlaveValorVO> listaComportamientos = null;
		try {
			Endpoint manager = (Endpoint) endpoints
					.get("OBTIENE_LISTA_COMPORTAMIENTOS");
			listaComportamientos = (List<LlaveValorVO>) manager.invoke(null);

			if (listaComportamientos == null) {
				throw new ApplicationException("No exite ningun comportamiento");
			}
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_LISTA_COMPORTAMIENTOS'",
					bae);
			throw new ApplicationException(
					"Error al cargar los comportamientos de conceptos por coberturas desde el sistema");
		}
		return listaComportamientos;
	}

	public List<LlaveValorVO> obtieneListaCondiciones()
			throws ApplicationException {
		List<LlaveValorVO> listaCondiciones = null;
		try {
			Endpoint manager = (Endpoint) endpoints
					.get("OBTIENE_LISTA_CONDICIONES");
			listaCondiciones = (List<LlaveValorVO>) manager.invoke(null);

			if (listaCondiciones == null) {
				throw new ApplicationException("No exite ninguna condicion");
			}
		} catch (BackboneApplicationException bae) {
			logger
					.error("Exception in invoke 'OBTIENE_LISTA_CONDICIONES'",
							bae);
			throw new ApplicationException(
					"Error al cargar las condiciones de conceptos por coberturas desde el sistema");
		}
		return listaCondiciones;
	}

	public void agregarConceptoPorCobertura(
			ConceptosCoberturaVO conceptoCobertura, String codigoRamo)
			throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints
				.get("AGREGAR_CONCEPTO_COBERTURA");
		Map params = new HashMap<String, String>();
		params.put("codigoRamo", codigoRamo);
		params.put("conceptoCobertura", conceptoCobertura);
		try {
			manager.invoke(params);
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke AGREGAR_CONCEPTO_COBERTURA", bae);
			throw new ApplicationException(
					"Error al agregar concepto por cobertura");
		}

	}

	public void eliminaConceptosPorCobertura(String codigoRamo,
			String codigoPeriodo, String orden) throws ApplicationException {
		Endpoint manager = (Endpoint) endpoints
				.get("ELIMINAR_CONCEPTO_COBERTURA");
		Map params = new HashMap<String, String>();
		params.put("codigoRamo", codigoRamo);
		params.put("codigoPeriodo", codigoPeriodo);
		params.put("orden", orden);
		try {
			manager.invoke(params);
		} catch (BackboneApplicationException bae) {
			logger
					.error("Exception in invoke ELIMINAR_CONCEPTO_COBERTURA",
							bae);
			throw new ApplicationException(
					"Error al eliminar concepto por cobertura");
		}

	}

	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}

}
