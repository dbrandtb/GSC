/*
 *ICE-WIZARD
 * 
 * Creado el 02/04/2008 10:13:26 a.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.catweb.configuracion.producto.service.impl;

import static mx.com.aon.catweb.configuracion.producto.dao.ProductoDAO.AGREGAR_CLAUSULA;
import static mx.com.aon.catweb.configuracion.producto.dao.ProductoDAO.CLONAR_PRODUCTO;
import static mx.com.aon.catweb.configuracion.producto.dao.ProductoDAO.GENERAR_PRODUCTO;
import static mx.com.aon.catweb.configuracion.producto.dao.ProductoDAO.INSERTAR_PRODUCTO;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.definicion.model.ClausulaVO;
import mx.com.aon.catweb.configuracion.producto.definicion.model.PeriodoVO;
import mx.com.aon.catweb.configuracion.producto.definicion.model.ProductoVO;
import mx.com.aon.catweb.configuracion.producto.definicion.model.TipoPoliza;
import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.service.ProductoManager;
import mx.com.aon.catweb.configuracion.producto.util.WizardUtils;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.kernel.cache.CacheSistema;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.OracleLobHandler;
import org.springframework.jdbc.support.nativejdbc.SimpleNativeJdbcExtractor;

/**
 * ProductoManagerImpl
 * 
 * <pre>
 *    Implementacion de servicio para consulta de informacion referente a los productos.
 * &lt;Pre&gt;
 * 
 * &#064;author   &lt;a href=&quot;mailto:alfonso.marquez@biosnetmx.com&quot;&gt;Alfonso M&amp;aacuterquez&lt;/a&gt;
 * &#064;version	 1.0
 * 
 * &#064;since	 1.0
 * 
 */
public class ProductoManagerImpl extends AbstractManagerJdbcTemplateInvoke implements ProductoManager {

	private static Logger logger = Logger.getLogger(ProductoManagerImpl.class);
	/**
	 * Mapa en el cual se introducen los Manager's para ser extraidos y
	 * utilizados como servicios.
	 */
	private Map<String, Endpoint> endpoints;

	
	private void insertarTipoPoliza(int codigoProducto, List<TipoPoliza> tipoPolizas) throws ApplicationException {
		
        Map<String, String> params = null;
        Endpoint manager = null;
        
		for (TipoPoliza tipoPoliza : tipoPolizas) {
			params = new HashMap<String, String>();
			params.put("codigoProducto", Integer.toString(codigoProducto));
			
			try {
				if (!tipoPoliza.isMarcado()) {
					params.put("tipoPoliza", tipoPoliza.getCodigoPoliza());
					manager = (Endpoint) endpoints.get("ELIMINAR_TIPO_POLIZA");					
				} else {
					params.put("tipoPoliza", tipoPoliza.getCodigoPoliza());
					manager = (Endpoint) endpoints.get("INSERTAR_TIPO_POLIZA");
				}
				
				manager.invoke(params);
				
			} catch (BackboneApplicationException bae) {
				
				logger.error("Exception in invoke 'INSERTAR_TIPO_POLIZA'", bae);
				throw new ApplicationException(
						"Error al insertar los datos del productos en el sistema");
			}
		}
	}

	private List<LlaveValorVO> obtenerRamoPolizaProducto(String codigoProducto)
			throws ApplicationException {
		List<LlaveValorVO> respuesta = new ArrayList<LlaveValorVO>();

		try {

			Endpoint manager = (Endpoint) endpoints.get("LISTA_RAMO_POLIZA");
			respuesta = (ArrayList<LlaveValorVO>) manager.invoke(codigoProducto);

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'LISTA_RAMO_POLIZA'", bae);
			throw new ApplicationException("Error al cargar los datos del productos en el sistema");
		}

		return respuesta;
	}

	/**
	 * Implementacion que consulta los datos del producto para editarlo apartir
	 * de su id.
	 * 
	 * @param idProducto -
	 *            identificador del producto.
	 * @return ProductoVO - Bean generado apartir de la consulta.
	 * @throws ApplicationException -
	 *             Excepcion con la informacion y descripción del problema en la
	 *             ejecución.
	 */
	public ProductoVO editarProducto(String idProducto) throws ApplicationException {
		ProductoVO producto = null;
		try {

			Endpoint manager = (Endpoint) endpoints.get("EDITAR_PRODUCTO_WIZARD");
			producto = (ProductoVO) manager.invoke(idProducto);

			if (producto == null) {
				throw new ApplicationException("No existe producto " + idProducto);
			} else {
				producto.setCodigoRamo(Integer.parseInt(idProducto));
				producto.setDescripcion(StringEscapeUtils.unescapeHtml(producto.getDescripcion()));
				producto.setDescripcionRamo(StringEscapeUtils.unescapeHtml(producto.getDescripcionRamo()));
			}

			List<LlaveValorVO> lista = obtenerRamoPolizaProducto(idProducto);
			
			for (LlaveValorVO llaveValorVO : lista) {
                logger.debug(":::: llaveValorVO.getValue :::: " + llaveValorVO.getValue());
				if (llaveValorVO.getValue().equals("T")) {
					producto.setTemporal("S");
                }
				//else
					//producto.setTemporal("N");
				
				if (llaveValorVO.getValue().equals("R")) {
					producto.setRenovable("S");
                }
				//else
					//producto.setRenovable("N");
				
				if (llaveValorVO.getValue().equals("P")) {
					producto.setVidaEntera("S");
                }
				//else
					//producto.setVidaEntera("N");
			}
			
			
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'EDITAR_PRODUCTO'", bae);
			throw new ApplicationException("Error al cargar los datos del productos en el sistema");
		}
		return producto;
	}

	/**
	 * Implementacion que inserta el producto en la base apartir de los datos
	 * capturados.
	 * 
	 * @param productoVO -
	 *            Bean con los datos para realizar la insercion del producto.
	 * @return boolean - boleano indica si ocurrio algun problema durante la
	 *         ejecucion el metodo.
	 * @throws ApplicationException -
	 *             Excepcion con la informacion y descripción del problema en la
	 *             ejecución.
	 * @throws BackboneApplicationException
	 */
	public String insertarProducto(ProductoVO productoVO, boolean success) throws ApplicationException {

		Map<String, Object> params = new HashMap<String, Object>();
		if (productoVO.getCodigoRamo() == 0){
			params.put("pv_cdramo_i", ConvertUtil.nvl(null));
		}else{
			params.put("pv_cdramo_i", productoVO.getCodigoRamo());
		}
		params.put("pv_dsramo_i", productoVO.getDescripcionRamo());
		params.put("pv_cdtipram_i", productoVO.getCodigoTipoRamo());
		params.put("pv_cdtipora_i", productoVO.getCodigoTipoParametro());
		params.put("pv_swsuscri_i", productoVO.getSwitchSuscripcion());
		params.put("pv_swinflac_i", "N");
		params.put("pv_swinclnt_i", productoVO.getSwitchClausulasNoTipificadas());
		params.put("pv_ttipcalc_i", productoVO.getTipoCalculoPolizasTemporales());
		params.put("pv_swrescat_i", "N");
		params.put("pv_mmrescat_i", ConvertUtil.nvl(""));
		params.put("pv_swreduci_i", "N");
		params.put("pv_mmreduci_i", ConvertUtil.nvl(""));
		params.put("pv_swrehabi_i", productoVO.getSwitchRehabilitacion());
		params.put("pv_mmbenefi_i", productoVO.getMesesBeneficios());
		params.put("pv_swantici_i", "N");
		params.put("pv_mmantici_i", ConvertUtil.nvl(""));
		params.put("pv_swbenefi_i", "N");
		params.put("pv_swpriper_i", productoVO.getSwitchPrimasPeriodicas());
		params.put("pv_swsinomn_i", productoVO.getSwitchPermisoPagosOtrasMonedas());
		params.put("pv_swfronti_i", "N");
		params.put("pv_swreaseg_i", productoVO.getSwitchReaseguro());
		params.put("pv_swsinsit_i", productoVO.getSwitchSiniestros());
		params.put("pv_swtarifa_i", productoVO.getSwitchTarifa());
		params.put("pv_swreauto_i", productoVO.getSwitchReinstalacionAutomatica());
		params.put("pv_swpriuni_i", productoVO.getSwitchPrimasUnicas());
		params.put("pv_swindper_i", productoVO.getSwitchDistintasPolizasPorAsegurado());
		params.put("pv_swpoldec_i", productoVO.getSwitchPolizasDeclarativas());
		params.put("pv_swcoaseg_i", "N");
		params.put("pv_swprecar_i", "N");
		params.put("pv_swtipo_i", "N");
		params.put("pv_swreserv_i", "N");
		params.put("pv_swcmptdi_i", productoVO.getSwitchTarifaDireccionalTotal());
		params.put("pv_nmprerec_i", productoVO.getCantidadDiasReclamacion());
		params.put("pv_swdipaco_i", "N");
		params.put("pv_swsubinc_i", productoVO.getSwitchSubincisos());
		params.put("pv_traza_i", "");
		params.put("pv_cdtippol_i", productoVO.getCodigoTipoSeguro());
		params.put("pv_cdcaltippol_i", productoVO.getCodigoTipoPoliza());
		params.put("pv_dslinea_i", productoVO.getDescripcion());
		params.put("pv_swcancel_i", productoVO.getSwitchCancelacion());
		params.put("pv_swendoso_i", productoVO.getSwitchEndosos());
		
		String mensajeRespuesta = "Error al guardar el producto. Consulte a su soporte";
		
		try {
			WrapperResultados resultado = returnBackBoneInvoke(params, INSERTAR_PRODUCTO);
			mensajeRespuesta = resultado.getMsgText();
			
			if (productoVO.getCodigoRamo() == 0){
				productoVO.setCodigoRamo(Integer.parseInt((String)resultado.getItemMap().get("pv_cdramo_o")));
			}
			
			success = true;
			/*Endpoint manager = (Endpoint) endpoints.get("INSERTAR_PRODUCTO");
			manager.invoke(productoVO);*/

			insertarTipoPoliza(productoVO.getCodigoRamo(), productoVO.getListTiposPoliza());
			
			if (success != true) {
				throw new ApplicationException("No se pudo insertar el producto");
			}

		} catch (ApplicationException bae) {
			success = false;
			logger.error("Exception in invoke 'INSERTAR_PRODUCTO'", bae);
			throw new ApplicationException("Error al insertar los datos del productos en el sistema");
		}
		
		return mensajeRespuesta;
	}

	/**
	 * Implementacion que consulta los id's y las descripciones de productos
	 * para evitar que se repitan.
	 * 
	 * @return Lista de Bean's generados a partir de la consulta.
	 * @throws ApplicationException -
	 *             Excepcion con la informacion y descripción del problema en la
	 *             ejecución.
	 */
	public List<ProductoVO> validarProductos() throws ApplicationException {
		Map<String, String> mapa = new HashMap<String, String>();
		// mapa.put("algunDato", parametro);
		List<ProductoVO> validaciones = null;
		try {

			// Endpoint manager = (Endpoint)endpoints.get("LISTA_PRODUCTOS");
			// identificadores = (List<String>)manager.invoke(mapa);

			validaciones = new ArrayList<ProductoVO>();
			ProductoVO productoVO = null;
			for (int i = 1; i < 10; i++) {
				productoVO = new ProductoVO();
				// se llenan el id y la descripcion del producto
				validaciones.add(productoVO);
			}
			if (validaciones == null) {
				throw new ApplicationException("No existe ningun producto ");
			}

		} catch (ApplicationException bae) {
			logger.error("Exception in invoke 'LISTA_PRODUCTOS'", bae);
			throw new ApplicationException("Error al cargar los id's de productos en el sistema");
		}
		return validaciones;
	}

	public boolean validacionDePeriodo(String finAnterior, Date inicio, Date fin)
			throws ApplicationException, ParseException {
		boolean result = false;
		Date temp = WizardUtils.parseDate(finAnterior);
		if (logger.isDebugEnabled()) {
			logger.debug("@@@@@@@@@@@@@@@@ parseDate" + temp);
		}
		result = WizardUtils.validarRangoFechas(WizardUtils.parseDate(finAnterior), inicio);

		return result;
	}

	/**
	 * Implementacion que consulta los datos del periodo para editarlo apartir
	 * de su id.
	 * 
	 * @param idPeriodo -
	 *            identificador del Periodo.
	 * @return PeriodoVO - Bean generado a partir de la consulta.
	 * @throws ApplicationException -
	 *             Excepcion con la informacion y descripción del problema en la
	 *             ejecución.
	 */
	public PeriodoVO editarPeriodo(String idPeriodo)
			throws ApplicationException {
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("PERIODO", idPeriodo);
		PeriodoVO periodoVO = null;
		try {

			// Endpoint manager = (Endpoint)endpoints.get("EDITAR_PERIODO");
			// periodoVO = (PeriodoVO)manager.invoke(mapa);

			periodoVO = new PeriodoVO();
			periodoVO.setCodigoPeriodo(1);
			periodoVO.setCodigoRamo(2);
			periodoVO.setFin("2008-09-11");
			periodoVO.setInicio("2008-02-09");

			if (periodoVO == null) {
				throw new ApplicationException("No existe periodo " + idPeriodo);
			}

		} catch (ApplicationException bae) {
			logger.error("Exception in invoke 'EDITAR_PERIODO'", bae);
			throw new ApplicationException("Error al cargar los datos del periodo en el sistema");
		}

		return periodoVO;
	}

	/**
	 * 
	 * Implementacion que agrega los periodos de valides al producto en la base
	 * apartir de los recientemente relacionados.
	 * 
	 * @param periodos -
	 *            Lista de Bean's recientemente relacionados al producto.
	 * @param idProducto -
	 *            Identificador del Producto, sirve para comparar los periodos
	 *            ya relacionados.
	 * @return boolean - boleano indica si ocurrio algun problema durante la
	 *         ejecucion el metodo.
	 * @throws ApplicationException -
	 *             Excepcion con la informacion y descripción del problema en la
	 *             ejecución.
	 */
	public void agregarPeriodos(List<PeriodoVO> periodos, int idProducto,
			boolean success) throws ApplicationException {
		List<PeriodoVO> temp = periodos;
		// List<PeriodoVO> remove =periodosJson(idProducto);
		// if(remove!=null && !remove.isEmpty()){
		// temp.removeAll(periodosJson(idProducto));
		// }
		Map mapa = new HashMap();
		mapa.put("PERIODOS", periodos);
		mapa.put("CDRAMO", idProducto);
		try {

			Endpoint manager = (Endpoint) endpoints.get("INSERTAR_PERIODOS");
			manager.invoke(mapa);

			if (success != true) {
				throw new ApplicationException(
						"No se puede insertar el periodo ");
			}

		} catch (BackboneApplicationException bae) {
			success = false;
			logger.error("Exception in invoke 'INSERTAR_PERIODOS'", bae);
			throw new ApplicationException("Error al insertar los datos de los periodos en el sistema");
		}
	}

	/**
	 * Implementacion que extrae todos los periodos de valides realcionados con
	 * un producto.
	 * 
	 * @param productoId -
	 *            Identificador del producto.
	 * @return List<PeriodoVO> - Lista con la informacion de todos los periodos
	 *         de valides del producto solicitado.
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public List<PeriodoVO> periodosJson(int idProducto)
			throws ApplicationException {
		List<PeriodoVO> periodos = null;
		try {

			Endpoint manager = (Endpoint) endpoints.get("LISTA_PERIODOS");
			periodos = (List<PeriodoVO>) manager.invoke(idProducto);

			// List<PeriodoVO> item = new ArrayList<PeriodoVO>();
			// PeriodoVO pvo = null;
			// for (int i = 0; i < 5; i++) {
			// pvo = new PeriodoVO();
			// pvo.setCodigoPeriodo("1");
			// pvo.setCodigoRamo("2");
			// pvo.setFin("2008-09-06");
			// pvo.setInicio("2008-09-01");
			// item.add(pvo);
			// }
			// periodos = item;

			if (periodos == null) {
				throw new ApplicationException("No exiten periodos de valides en el producto" + idProducto);
			} else {
				if (!periodos.isEmpty()) {
					for (PeriodoVO periodo : periodos) {
						String[] periodoEspacios = periodo.getFin().split(" ");
						//						
						String[] periodosDiagonales = periodoEspacios[0]
								.split("-");
						periodo.setFinFormato(periodosDiagonales[2] + "/"
								+ periodosDiagonales[1] + "/"
								+ periodosDiagonales[0]);

						periodoEspacios = periodo.getInicio().split(" ");
						periodosDiagonales = periodoEspacios[0].split("-");
						periodo.setInicioFormato(periodosDiagonales[2] + "/"
								+ periodosDiagonales[1] + "/"
								+ periodosDiagonales[0]);

					}
				}
				if (logger.isDebugEnabled()) {
					logger.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!listaPeriodo size" + periodos.size());
				}

			}

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'LISTA_PERIODOS'", bae);
			throw new ApplicationException("Error al cargar los periodos de valides de productos en el sistema");
		} catch (Exception e) {
		}

		return periodos;
	}

	/**
	 * Implementacion que consulta los datos de la clausula para editarla
	 * apartir de su id.
	 * 
	 * @param idClausula -
	 *            identificador de la Clausula
	 * @return ClausulaVO - Bean generado apartir de la consulta.
	 * @throws ApplicationException -
	 *             Excepcion con la informacion y descripción del problema en la
	 *             ejecución.
	 */
	public List<ClausulaVO> editarClausula(String idClausula)
			throws ApplicationException {
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("CLAUSULA", idClausula);
		ClausulaVO clausulaVO = null;
		List<ClausulaVO> lcvo = new ArrayList<ClausulaVO>();
		try {

			// Endpoint manager = (Endpoint)endpoints.get("EDITAR_CLAUSULA");
			// clausula = (ProductoVO)manager.invoke(mapa);

			clausulaVO = new ClausulaVO();
			clausulaVO.setCodigoRamo(1);
			clausulaVO.setCodigoClausula("FI");
			clausulaVO.setDescripcionClausula("this is a description");
			clausulaVO.setDescripcionLinea("This is a very extended html code");

			if (clausulaVO == null) {
				throw new ApplicationException("No existe clausula "
						+ idClausula);
			} else {
				lcvo.add(clausulaVO);
			}

		} catch (ApplicationException bae) {
			logger.error("Exception in invoke 'EDITAR_CLAUSULA'", bae);
			throw new ApplicationException("Error al cargar los datos de la clausula en el sistema");
		}

		return lcvo;
	}

	/**
	 * Implementacion que agrega un tipo de clausula al catalogo en la base
	 * apartir de los datos capturados.
	 * 
	 * @param clausula -
	 *            Bean con los datos para realizar la insercion del producto.
	 * @return boolean - boleano indica si ocurrio algun problema durante la
	 *         ejecucion el metodo.
	 * @throws ApplicationException -
	 *             Excepcion con la informacion y descripción del problema en la
	 *             ejecución.
	 */
	public WrapperResultados agregarClausula(ClausulaVO clausula, boolean success) throws ApplicationException {
		
		//Crear parametro CLOB
        OracleLobHandler lobHandler = new OracleLobHandler();
        lobHandler.setNativeJdbcExtractor(new SimpleNativeJdbcExtractor());
        SqlLobValue clobDsLinea = new SqlLobValue(clausula.getDescripcionLinea(), lobHandler);
		
		Map params = new HashMap();
		params.put("P_CDCLAUSU", clausula.getCodigoClausula());
		params.put("P_DSCLAUSU", clausula.getDescripcionClausula());
		params.put("P_NMLINEA", clausula.getNumeroLineas());
		params.put("P_DSLINEA", clobDsLinea);
		
		WrapperResultados resultado = returnBackBoneInvoke(params, AGREGAR_CLAUSULA);
		
		return resultado;
	}

	/**
	 * Implementacion que agrega las clausulas al producto en la base apartir de
	 * las recientemente relacionadas.
	 * 
	 * @param clausulas -
	 *            Lista de Bean's recientemente relacionados al producto.
	 * @param idProducto -
	 *            Identificador del Producto, sirve para comparar las clausulas
	 *            ya relacionados.
	 * @return boolean - boleano indica si ocurrio algun problema durante la
	 *         ejecucion el metodo.
	 * @throws ApplicationException -
	 *             Excepcion con la informacion y descripción del problema en la
	 *             ejecución.
	 */
	public void asociarClausulas(List<ClausulaVO> clausulas, int idProducto,
			boolean success) throws ApplicationException {
		List<ClausulaVO> temp = clausulas;
		// List<ClausulaVO> remove=clausulasJson(idProducto);
		// if(remove!=null && !remove.isEmpty()){
		// temp.removeAll(clausulasJson(idProducto));
		// }
		Map mapa = new HashMap();
		mapa.put("CLAUSULAS", clausulas);
		mapa.put("CDRAMO", idProducto);
		try {

			Endpoint manager = (Endpoint) endpoints.get("INSERTAR_CLAUSULAS");
			manager.invoke(mapa);

			if (success != true) {
				throw new ApplicationException(
						"No se puede insertar las clausulas ");
			}

		} catch (BackboneApplicationException bae) {
			success = false;
			logger.error("Exception in invoke 'INSERTAR_CLAUSULAS'", bae);
			throw new ApplicationException(
					"Error al insertar los datos de las clausulas en el sistema");
		}
	}

	/**
	 * Implementacion que extrae todos las clausulas asociadas a un producto.
	 * 
	 * @param productoId -
	 *            Identificador del producto.
	 * @return List<ClausulasVO> - Lista con la informacion de todos las
	 *         clausulas asociadas al producto solicitado.
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public List<ClausulaVO> clausulasJson(int idProducto)
			throws ApplicationException {

		List<ClausulaVO> clausulas = null;
		try {

			Endpoint manager = (Endpoint) endpoints
					.get("LISTA_CLAUSULAS_ASOCIADAS");
			clausulas = (List<ClausulaVO>) manager.invoke(idProducto);

			// List<ClausulaVO> item = new ArrayList<ClausulaVO>();
			// ClausulaVO cvo = null;
			// for (int i = 0; i < 5; i++) {
			// cvo = new ClausulaVO();
			// cvo.setCodigoRamo("1");
			// cvo.setCodigoClausula("2");
			// cvo.setDescripcionClausula("this is a description");
			// cvo.setDescripcionLinea("This is a very extended html code");
			// item.add(cvo);
			// }
			// clausulas = item;

			if (clausulas == null) {
				throw new ApplicationException(
						"No exiten clausulas asociadas al producto"
								+ idProducto);
			} else {
				if (logger.isDebugEnabled()) {
					logger
							.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!listaClausula size"
									+ clausulas.size());
				}

			}

		} catch (BackboneApplicationException bae) {
			logger
					.error("Exception in invoke 'LISTA_CLAUSULAS_ASOCIADAS'",
							bae);
			throw new ApplicationException(
					"Error al cargar las clausulas asociadas a un producto desde el sistema");
		}

		return clausulas;
	}

	public List<ClausulaVO> catalogoClausulasJson() throws ApplicationException {

		List<ClausulaVO> clausulas = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("LISTA_CLAUSULAS");
			clausulas = (List<ClausulaVO>) manager.invoke(null);

			// List<ClausulaVO> item = new ArrayList<ClausulaVO>();
			// ClausulaVO cvo = null;
			// for (int i = 0; i < 5; i++) {
			// cvo = new ClausulaVO();
			// cvo.setCodigoRamo("1");
			// cvo.setCodigoClausula("2");
			// cvo.setDescripcionClausula("this is a description");
			// cvo.setDescripcionLinea("This is a very extended html code");
			// item.add(cvo);
			// }
			// clausulas = item;

			if (clausulas == null) {
				throw new ApplicationException("No exite ninguna clausula");
			} else {
				logger
						.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!lidtaCatalogoClausula size"
								+ clausulas.size());
			}

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'LISTA_CLAUSULAS'", bae);
			throw new ApplicationException(
					"Error al cargar el catalogo de clausulas desde el sistema");
		}
		return clausulas;
	}

	public List<LlaveValorVO> catalogoTipoProductoJson()
			throws ApplicationException {

		List<LlaveValorVO> productos = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("LISTA_TIPO_PRODUCTO");
			productos = (List<LlaveValorVO>) manager.invoke(null);

			// List<ComboVO> item = new ArrayList<ComboVO>();
			// comboVO cvo = null;
			// for (int i = 0; i < 5; i++) {
			// cvo = new ComboVO();
			// cvo.setKey("1");
			// cvo.setValue("Hallo");
			//		
			// item.add(cvo);
			// }
			// productos = item;

			if (productos == null) {
				throw new ApplicationException(
						"No exite ningun tipo de Producto");
			} else {
				logger
						.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!lidtaCatalogoTipoProducto size"
								+ productos.size());
			}

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'LISTA_TIPO_PRODUCTOS'", bae);
			throw new ApplicationException(
					"Error al cargar los tipos de productos desde el sistema");
		}
		return productos;
	}

	public List<LlaveValorVO> catalogoTipoRamoJson()
			throws ApplicationException {

		List<LlaveValorVO> productos = null;
		try {
			Endpoint manager = (Endpoint) endpoints.get("LISTA_TIPO_RAMO");
			productos = (List<LlaveValorVO>) manager.invoke(null);

			// List<ComboVO> item = new ArrayList<ComboVO>();
			// ComboVO cvo = null;
			// for (int i = 0; i < 5; i++) {
			// cvo = new ComboVO();
			// cvo.setKey("1");
			// cvo.setValue("Hallo");
			//	
			// item.add(cvo);
			// }
			// productos = item;

			if (productos == null) {
				throw new ApplicationException("No exite ningun tipo de Ramo");
			} else {
				logger
						.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!lidtaCatalogoTipoRamo size"
								+ productos.size());
			}

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'LISTA_TIPO_RAMO'", bae);
			throw new ApplicationException(
					"Error al cargar los tipos de ramo desde el sistema");
		}
		return productos;
	}

	public List<LlaveValorVO> catalogoTipoPolizaJson()
			throws ApplicationException {

		List<LlaveValorVO> productos = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("tabla", "CTIPOPOLIZ");
		try {
			Endpoint manager = (Endpoint) endpoints.get("OBTIENE_TABLA_APOYO");
			productos = (List<LlaveValorVO>) manager.invoke(params);

			// List<ComboVO> item = new ArrayList<ComboVO>();
			// ComboVO cvo = null;
			// for (int i = 0; i < 5; i++) {
			// cvo = new ComboVO();
			// cvo.setKey("1");
			// cvo.setValue("Hallo");
			//	
			// item.add(cvo);
			// }
			// productos = item;

			if (productos == null) {
				throw new ApplicationException("No exite ningun tipo de Poliza");
			} else {
				logger
						.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!listaCatalogoTipoPOliza size"
								+ productos.size());
			}

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'LISTA_TIPO_POLIZA'", bae);
			throw new ApplicationException(
					"Error al cargar los tipos de poliza desde el sistema");
		}
		return productos;
	}

	public List<LlaveValorVO> catalogoTipoSeguroJson()
			throws ApplicationException {

		List<LlaveValorVO> listaTipoSeguro = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("tabla", "CTIPOSEGUR");

		try {
			Endpoint manager = (Endpoint) endpoints.get("OBTIENE_TABLA_APOYO");
			listaTipoSeguro = (List<LlaveValorVO>) manager.invoke(params);

			if (listaTipoSeguro == null) {
				throw new ApplicationException("No exite ningun tipo de Seguro");
			} else {
				logger
						.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!listaCatalogoTipoSegurp size"
								+ listaTipoSeguro.size());
			}

		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'LISTA_TIPO_SEGURO'", bae);
			throw new ApplicationException("Error al cargar los tipos de seguro desde el sistema");
		}
		return listaTipoSeguro;
	}

	public List<PeriodoVO> editarPeriodos(String cdRamo) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean validacionDePeriodo(String finAnterior, Date inicio,
			Date fin, String inicioPosterior) throws ApplicationException,
			ParseException {
		boolean result = false;
		Date temp = WizardUtils.parseDate(finAnterior);
		if (logger.isDebugEnabled()) {
			logger.debug("@@@@@@@@@@@@@@@@ parseDate" + temp);
		}
		result = WizardUtils.validarRangoFechas(WizardUtils
				.parseDate(finAnterior), inicio);
		if (result)
			result = WizardUtils.validarRangoFechas(fin, WizardUtils
					.parseDate(inicioPosterior));
		return result;
	}

	// Getters and Setters

	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}

	public String clonarProducto(String codigoProductoAnterior,
			String descripcionProducto,
			String langCode, String usuario) throws ApplicationException {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("pv_cdramo_ori_i", codigoProductoAnterior);
		//map.put("pv_cdramo_des_i", codigoProductoNuevo);
		map.put("pv_dsramo_des_i", descripcionProducto);
		map.put("pv_lang_code_i", langCode);
		map.put("pv_username_i", usuario);

		WrapperResultados res = returnBackBoneInvoke(map, CLONAR_PRODUCTO);
		return res.getMsgText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.com.aon.catweb.configuracion.producto.service.ProductoManager#generarProducto(java.lang.String)
	 */
	public String generarProducto(String codigoProducto)
			throws ApplicationException {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("pv_cdramo_i", codigoProducto);

		WrapperResultados res = returnBackBoneInvoke(map, GENERAR_PRODUCTO);

		try {
			CacheSistema.generarProducto(codigoProducto);
			CacheSistema.cargarProducto(codigoProducto);
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage());
		}

		return res.getMsgText();
	}
}