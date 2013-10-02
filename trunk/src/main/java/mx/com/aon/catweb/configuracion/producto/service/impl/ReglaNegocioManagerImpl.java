package mx.com.aon.catweb.configuracion.producto.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.reglanegocio.model.ReglaNegocioVO;
import mx.com.aon.catweb.configuracion.producto.service.ReglaNegocioManager;
import mx.com.aon.catweb.configuracion.producto.util.ReglaNegocio;
import mx.com.aon.core.DatosInvalidosException;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

/**
 * Clase concreta que implementa los metodos de la interface
 * {@link ReglaNegocioManager} para insertar, modificar y eliminar las reglas de
 * negocio del producto; tales como:
 * <p>
 * <ul>
 * <li>Validaciones</li>
 * <li>Condiciones</li>
 * <li>Variables Temporales</li>
 * <li>Conceptos de Tarificacion</li>
 * <li>Autorizaciones</li>
 * </ul>
 * 
 * @since 1.0
 * @author <a href="mailto:adolfo.gonzalez@biosnettcs.com">Adolfo Gonzalez</a>
 * @version $Id$
 * @see ReglaNegocioManager
 */
public class ReglaNegocioManagerImpl extends AbstractManager implements ReglaNegocioManager {

	/**
	 * Logger de la clase.
	 */
	private static Logger logger = Logger
			.getLogger(ReglaNegocioManagerImpl.class);

	/**
	 * Endpoints para acceder a los VM
	 */
	private Map<String, Endpoint> endpoints;

	/**
	 * Retorna Map de objetos de tipo {@link Endpoint} con los vm que se pueden
	 * invocar.
	 * 
	 * @return Map de {@link Endpoint}
	 */
	/*public Map<String, Endpoint> getEndpoints() {
		return endpoints;
	}*/

	/**
	 * Asigna al Map de objetos de tipo {@link Endpoint} con los vm que se
	 * pueden invocar.
	 * 
	 * @param endpoints
	 */
	/*public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}*/

	/**
	 * Metodo que se utiliza para la insercion en la base de datos de la regla
	 * de negocio.
	 * 
	 * @param tipo
	 *            Identifica que endpoint se va a invocar.
	 * @param params
	 *            Parametros que se envian para el query.
	 * @throws ApplicationException
	 *             Si se genera una exception al insertar en la aplicacion.
	 */
	private void insertar(String tipo, Map<String, String> params)
			throws ApplicationException {

		try {
			Endpoint manager = (Endpoint) endpoints.get(tipo);
			manager.invoke(params);

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_TABLA_APOYO'", bae);
			throw new ApplicationException(
					"Error al cargar los tipos de seguro desde el sistema");
		}
	}

	/**
	 * 
	 * @param tipo
	 * @return
	 * @throws ApplicationException
	 */
	private List<ReglaNegocioVO> obtenerReglasNegocio(String tipo) throws ApplicationException {
		List<ReglaNegocioVO> resultado = new ArrayList<ReglaNegocioVO>();
		WrapperResultados wrapperResult = null;
		logger.debug("Entrando a obtenerReglasNegocio(String) Endpoint:" + tipo);
		try {
			Endpoint manager = (Endpoint) endpoints.get(tipo);
			wrapperResult = (WrapperResultados) manager.invoke(null);
			resultado = (ArrayList<ReglaNegocioVO>)wrapperResult.getItemList();

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_TABLA_APOYO'", bae);
			throw new ApplicationException(
					"Error al cargar los tipos de seguro desde el sistema");
		} catch (Exception e) {
			logger.error("ERROR: Exception in invoke 'OBTIENE_TABLA_APOYO'" + e, e);
			throw new ApplicationException("Error al cargar los tipos de seguro desde el sistema");
		}

		return resultado;
	}
	
	
	public PagedList obtenerReglasNegocioPagedList(ReglaNegocio reglaNegocio, int start, int limit) 
		throws ApplicationException {
		logger.debug(">>>>>>> obtenerReglasNegocioPagedList");
		Map<String, String> map = new HashMap<String, String>();
		String endpoint = "";

		switch (reglaNegocio) {
		case Validacion:
			endpoint += "OBTENER_VALIDACION";
			break;
		case ConceptoTarificacion:
			endpoint += "OBTENER_CONCEPTO_TARIFICACION";
			break;
		case Condicion:
			endpoint += "OBTENER_CONDICION";
			break;
		case Autorizacion:
			endpoint += "OBTENER_AUTORIZACION";
			break;
		case VariableTemporal:
			endpoint += "OBTENER_VARIABLE_TEMPORAL";
			break;
		}
		
		return pagedBackBoneInvoke(map, endpoint, start, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.biosnet.ice.wizard.productos.service.ReglaNegocioManager#insertarReglaNegocio(com.biosnet.ice.wizard.productos.reglanegocio.model.ReglaNegocioVO)
	 */
	public void insertarReglaNegocio(ReglaNegocioVO reglaNegocio)
			throws ApplicationException, DatosInvalidosException {

		if (reglaNegocio == null)
			throw new DatosInvalidosException("Parametro reglaNegocio null");

		if (reglaNegocio.getCodigo() == null)
			throw new DatosInvalidosException(
					"Atributo codigo del parametro reglaNegocio null");

		if (reglaNegocio.getNombre() == null
				|| reglaNegocio.getNombre().equals(""))
			throw new DatosInvalidosException(
					"Atributo nombre del parametro reglaNegocio null");

		if (reglaNegocio.getDescripcion() == null
				|| reglaNegocio.getDescripcion().equals(""))
			throw new DatosInvalidosException(
					"Atributo descripcion del parametro reglaNegocio null");

		Map<String, String> params = new HashMap<String, String>();

		switch (reglaNegocio.getCodigo()) {
		case Validacion:
			params.put("nombre", reglaNegocio.getNombre());
			params.put("descripcion", reglaNegocio.getDescripcion());
			params.put("codigoExpresion", reglaNegocio
					.getCodigoExpresion());
			params.put("tipo", reglaNegocio.getTipo());
			params.put("mensaje", reglaNegocio.getMensaje());
			insertar("INSERTAR_VALIDACION", params);
			break;
		case ConceptoTarificacion:
			params.put("nombre", reglaNegocio.getNombre());
			params.put("descripcion", reglaNegocio.getDescripcion());
			params.put("tipo", reglaNegocio.getTipo());
			params.put("codigoExpresion", reglaNegocio
					.getCodigoExpresion());
			insertar("INSERTAR_CONCEPTO_TARIFICACION", params);
			break;
		case VariableTemporal:
			params.put("nombre", reglaNegocio.getNombre());
			params.put("descripcion", reglaNegocio.getDescripcion());
			params.put("codigoExpresion", reglaNegocio
					.getCodigoExpresion());
			insertar("INSERTAR_VARIABLE_TEMPORAL", params);
			break;
		case Autorizacion:
			params.put("nombre", reglaNegocio.getNombre());
			params.put("descripcion", reglaNegocio.getDescripcion());
			params.put("nivel", reglaNegocio.getNivel());
			params.put("codigoExpresion", reglaNegocio
					.getCodigoExpresion());
			insertar("INSERTAR_AUTORIZACION", params);
			break;
		case Condicion:
			params.put("nombre", reglaNegocio.getNombre());
			params.put("descripcion", reglaNegocio.getDescripcion());
			params.put("codigoExpresion", reglaNegocio
					.getCodigoExpresion());
			insertar("INSERTAR_CONDICION", params);
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.biosnet.ice.wizard.productos.service.ReglaNegocioManager#obtenerReglasNegocio(com.biosnet.ice.wizard.util.ReglaNegocio)
	 */
	public List<ReglaNegocioVO> obtenerReglasNegocio(ReglaNegocio reglaNegocio)
			throws ApplicationException {

		List<ReglaNegocioVO> reglasNegocio = new ArrayList<ReglaNegocioVO>();
		
		switch (reglaNegocio) {
		case Validacion:
			reglasNegocio = obtenerReglasNegocio("OBTENER_VALIDACION");
			break;
		case ConceptoTarificacion:
			reglasNegocio = obtenerReglasNegocio("OBTENER_CONCEPTO_TARIFICACION");
			break;
		case Condicion:
			reglasNegocio = obtenerReglasNegocio("OBTENER_CONDICION");
			break;
		case Autorizacion:
			reglasNegocio = obtenerReglasNegocio("OBTENER_AUTORIZACION");
			break;
		case VariableTemporal:
			reglasNegocio = obtenerReglasNegocio("OBTENER_VARIABLE_TEMPORAL");
			break;
		}
		return reglasNegocio;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.biosnet.ice.wizard.productos.service.ReglaNegocioManager#obtenerTiposConceptosTarificacion()
	 */
	public List<LlaveValorVO> obtenerTiposConceptosTarificacion() throws ApplicationException {
		List<LlaveValorVO> lista = new ArrayList<LlaveValorVO>();
		
		try {
			Endpoint manager = (Endpoint) endpoints.get("OBTIENE_TIPO_CONCEPTOS");
			lista = (ArrayList<LlaveValorVO>) manager.invoke(null);

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_TABLA_APOYO'", bae);
			throw new ApplicationException(
					"Error al cargar los tipos de seguro desde el sistema");
		}
		
		return lista;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.biosnet.ice.wizard.productos.service.ReglaNegocioManager#obtenerTiposValidaciones()
	 */
	public List<LlaveValorVO> obtenerTiposValidaciones() {
		List<LlaveValorVO> lista = new ArrayList<LlaveValorVO>();
		
		LlaveValorVO valor = new LlaveValorVO();
		
		valor.setKey("A");
		valor.setValue("Aviso");
		lista.add(valor);
		
		valor = new LlaveValorVO();
		valor.setKey("E");
		valor.setValue("Error");
		lista.add(valor);
		
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * @see com.biosnet.ice.wizard.productos.service.ReglaNegocioManager#obtenerVariablesTemporalesAsociadasAlProducto()
	 */
	@SuppressWarnings({ "unchecked", "unchecked" })
	public List<ReglaNegocioVO> obtenerVariablesTemporalesAsociadasAlProducto(
			String cdramo) throws ApplicationException {
		List<ReglaNegocioVO> listaVariablesTemporales = new ArrayList<ReglaNegocioVO>();
		WrapperResultados res;
		try {
			//Endpoint manager = (Endpoint) endpoints.get("OBTIENE_VARIABLES_TEMPORALES_PRODUCTO");
			res = (WrapperResultados) returnBackBoneInvoke(cdramo, "OBTIENE_VARIABLES_TEMPORALES_PRODUCTO");
			listaVariablesTemporales = res.getItemList();
		} catch (ApplicationException bae) {
			logger.error("Exception in invoke 'OBTIENE_VARIABLES_TEMPORALES_PRODUCTO'", bae);
			throw new ApplicationException(
					"Error al cargar las variables temporales asociadas al producto desde el sistema");
		}		
		return listaVariablesTemporales;
	}

	/*
	 * (non-Javadoc)
	 * @see com.biosnet.ice.wizard.productos.service.ReglaNegocioManager#asociarVariablesDelProducto()
	 */
	public void asociarVariablesDelProducto(
			List<ReglaNegocioVO> listaReglaNegocioVariables, String codigoRamo)
			throws ApplicationException {
			Map params = new HashMap<String, String>();
			params.put("listaReglaNegocioVariables", listaReglaNegocioVariables);
			params.put("codigoRamo", codigoRamo);
			Endpoint manager = (Endpoint) endpoints.get("ASOCIAR_VARIABLES_TEMPORALES_PRODUCTO"); 
			try{
				manager.invoke(params);			
			}catch(BackboneApplicationException bae){
				logger.error("Exception in invoke ASOCIAR_VARIABLES_TEMPORALES_PRODUCTO", bae);
				throw new ApplicationException(
					"Error al insertar las variables temporales del producto");
			}
		
	}

	public void desasociarVariablesDelProducto(List<ReglaNegocioVO> list,
			String codigoRamo) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("listaVariablesDesasociadas", list);
		params.put("codigoRamo", codigoRamo);
		Endpoint manager = (Endpoint) endpoints.get("DESASOCIAR_VARIABLES_TEMPORALES_PRODUCTO"); 
		try{
			manager.invoke(params);			
		}catch(BackboneApplicationException bae){
			logger.error("Exception in invoke DESASOCIAR_VARIABLES_TEMPORALES_PRODUCTO", bae);
			throw new ApplicationException(
				"Error al eliminar las variables temporales del producto");
		}
	
		
	}
	
	public String borraVarTmp(String cdVariat) throws ApplicationException {
		String res = "Variable temporal borrada con &eacute;xito";
		Map params = new HashMap<String, String>();
		params.put("pv_cdvariat_i", cdVariat);
		Endpoint manager = (Endpoint) endpoints.get("ELIMINA_VAR_TMP"); 
		try{
			manager.invoke(params);			
		}catch(BackboneApplicationException bae){
			logger.error("Exception in invoke ELIMINA_VAR_TMP", bae);
			throw new ApplicationException(
				"Error al eliminar las variables temporales del producto");
		}
		
		return res;
	
		
	}
	
}