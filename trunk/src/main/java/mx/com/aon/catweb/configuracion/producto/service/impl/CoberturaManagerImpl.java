package mx.com.aon.catweb.configuracion.producto.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.coberturas.model.CoberturaVO;
import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.service.CoberturaManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;

import org.apache.log4j.Logger;

/**
 * Clase que implementa los metodos de la interface
 * {@link CoberturaManager} para agregar, asociar y obtener las listas
 * de coberturas asociadas a un producto
 * @author Edgar Perez
 * @version 2.0
 * @since 1.0
 * @see CoberturaManager
 * 
 */

public class CoberturaManagerImpl implements CoberturaManager {

	/**
	 * Logger de la clase.
	 */
	private static Logger logger = Logger.getLogger(CoberturaManagerImpl.class);

	/**
	 * Mapa en el cual se introducen los Manager's para ser extraidos y
	 * utilizados como servicios.
	 */

	private Map endpoints;

	/**
	 * Implementacion que extrae las coberturas de la BD.
	 * 
	 * 
	 * @return listaCoberturas List<LlaveValorVO> - Lista con la informacion de las
	 *         coberturas asociadas al producto.
	 *         
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public List<LlaveValorVO> obtieneCoberturas() throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_COBERTURAS");

		List<LlaveValorVO> listaCoberturas = null;
		try {
			listaCoberturas = (List<LlaveValorVO>) endpoint.invoke(null);

		} catch (BackboneApplicationException e) {
			throw new ApplicationException(
					"Error regresando lista de coberturas del catalogo");
		}
		return listaCoberturas;
	}

	/**
	 * Implementacion que extrae las condiciones de las coberturas de la BD.
	 * 
	 * 
	 * @return listaCondiciones List<LlaveValorVO> - Lista con la informacion de las condiciones para las
	 *         coberturas asociadas al producto.
	 *         
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public List<LlaveValorVO> obtieneCondicionCobertura()
			throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints
				.get("OBTIENE_CONDICION_COBERTURA");

		List<LlaveValorVO> listaCondiciones = null;
		try {
			listaCondiciones = (List<LlaveValorVO>) endpoint.invoke(null);

		} catch (BackboneApplicationException e) {
			throw new ApplicationException(
					"Error regresando lista de condiciones de coberturas");
		}
		return listaCondiciones;
	}

	/**
	 * Implementacion que extrae los ramos de las coberturas de la BD.
	 * 
	 * 
	 * @return listaRamo List<LlaveValorVO> - Lista con la informacion de los ramos para las
	 *         coberturas asociadas al producto.
	 *         
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public List<LlaveValorVO> obtieneRamoCobertura()
			throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_RAMO_COBERTURA");

		List<LlaveValorVO> listaRamo = null;
		try {
			listaRamo = (List<LlaveValorVO>) endpoint.invoke(null);

		} catch (BackboneApplicationException e) {
			throw new ApplicationException(
					"Error regresando lista de ramos de coberturas");
		}
		return listaRamo;
	}

	/**
	 * Implementacion que regresa los tipos de las coberturas.
	 * 
	 * 
	 * @return lista List<LlaveValorVO> - Lista con la informacion de los tipos de las
	 *         coberturas.
	 *         
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public List<LlaveValorVO> obtieneTipoCobertura()
			throws ApplicationException {
		List<LlaveValorVO> lista = new ArrayList<LlaveValorVO>();

		LlaveValorVO valor = new LlaveValorVO();

		valor.setKey("S");
		valor.setValue("Simple");
		lista.add(valor);

		valor = new LlaveValorVO();
		valor.setKey("C");
		valor.setValue("Compuesta");
		lista.add(valor);

		return lista;
	}

	/**
	 * Metodo que se utiliza para la insercion en la base de datos de la cobertura
	 * 
	 * @param CoberturaVO cobertura
	 *            Parametros que se envian para el query.
	 * @throws ApplicationException
	 *             Si se genera una exception al insertar en la aplicacion.
	 */
	public void insertaCobertura(CoberturaVO cobertura)
			throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("INSERTA_COBERTURA");

		try {
			endpoint.invoke(cobertura);
		} catch (BackboneApplicationException e) {
			throw new ApplicationException(
					"Error intentando insertar una nueva cobertura");
		}

	}

	/**
	 * Implementacion que extrae la suma asegurada de las coberturas de la BD.
	 * 
	 * 
	 * @return listaSumaAsegurada List<LlaveValorVO> - Lista con la informacion de la suma asegurada 
	 * para las coberturas asociadas al producto.
	 *         
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public List<LlaveValorVO> obtieneSumaAseguradaCobertura(String codigoRamo)
			throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints
				.get("OBTIENE_SUMA_ASEGURADA_COBERTURA");

		List<LlaveValorVO> listaSumaAsegurada = null;
		try {
			listaSumaAsegurada = (List<LlaveValorVO>) endpoint
					.invoke(codigoRamo);

		} catch (BackboneApplicationException e) {
			throw new ApplicationException(
					"Error regresando lista de suma asegurada de coberturas");
		}
		return listaSumaAsegurada;
	}

	/**
	 * Metodo que se utiliza para la asociacion en la base de datos de la cobertura
	 * 
	 * @param CoberturaVO cobertura
	 *            Parametros que se envian para el query.
	 * @throws ApplicationException
	 *             Si se genera una exception al insertar en la aplicacion.
	 */
	public void asociaCobertura(CoberturaVO cobertura)
			throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("ASOCIA_COBERTURA");

		try {
			endpoint.invoke(cobertura);
		} catch (BackboneApplicationException e) {
			throw new ApplicationException(
					"Error intentando asociar una cobertura");
		}

	}

	public CoberturaVO obtieneCoberturaAsociada(String codigoRamo,
			String codigoTipoSituacion, String claveCobertura)throws ApplicationException {
		
		Map params = new HashMap<String, Object>();
		params.put("codigoRamo", codigoRamo);
		params.put("codigoTipoSituacion", codigoTipoSituacion);
		params.put("claveCobertura", claveCobertura);
		Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_COBERTURA_ASOCIADA");
		CoberturaVO coberturaAsociada = null;
		try {
            coberturaAsociada = (CoberturaVO)endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando obtener la cobertura asociada");
        }
		return coberturaAsociada;
	}

	
	/**
	 * Asigna al Map de objetos de tipo {@link Endpoint} con los vm que se
	 * pueden invocar.
	 * 
	 * @param endpoints
	 */
	public void setEndpoints(Map endpoints) {
		this.endpoints = endpoints;
	}


}
