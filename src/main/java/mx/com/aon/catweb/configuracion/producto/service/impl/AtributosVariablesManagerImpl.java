package mx.com.aon.catweb.configuracion.producto.service.impl;

import static mx.com.aon.catweb.configuracion.producto.dao.AtributoDAO.ATRIBUTO_PADRE_COBERTURA;
import static mx.com.aon.catweb.configuracion.producto.dao.AtributoDAO.ATRIBUTO_PADRE_OBJETO;
import static mx.com.aon.catweb.configuracion.producto.dao.AtributoDAO.ATRIBUTO_PADRE_PRODUCTO;
import static mx.com.aon.catweb.configuracion.producto.dao.AtributoDAO.ATRIBUTO_PADRE_ROL;
import static mx.com.aon.catweb.configuracion.producto.dao.AtributoDAO.GUARDA_ATRIBUTOS_VARIABLES;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.atributosVariables.model.AtributosVariablesVO;
import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.service.AtributosVariablesManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.log4j.Logger;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

/**
 * 
 * @author Edgar Perez
 * @version 2.0
 * @since 1.0
 * 
 * Clase que implementa los metodos para agregar y obtener las listas de
 * atributos variables asociados a un producto
 * 
 */
public class AtributosVariablesManagerImpl extends
		AbstractManagerJdbcTemplateInvoke implements AtributosVariablesManager {

	private static Logger logger = Logger.getLogger(IncisoManagerImpl.class);

	/**
	 * Mapa en el cual se introducen los Manager's para ser extraidos y
	 * utilizados como servicios.
	 */

	@SuppressWarnings("unchecked")
	private Map endpoints;

	/**
	 * Metodo que se utiliza para obtener las claves de los atributos variables.
	 * 
	 * @param point
	 *            Parametro que contiene el VM que sera invocado.
	 * @param params
	 *            Contiene los valores de los parametros que se van a buscar en
	 *            la base de datos.
	 * @return Retorna la clave de los atributos.
	 * @throws ApplicationException
	 *             Si existe algun error en la consulta.
	 */
	@SuppressWarnings("unused")
	private int obtenerClaveAtributo(String point, Map<String, String> params)
			throws ApplicationException {
		int codigo = 0;
		if (logger.isInfoEnabled()) {
			logger.info("Entro al metodo de obtener Clave");
		}
		Endpoint endpoint = (Endpoint) endpoints.get(point);
		try {
			codigo = (Integer) endpoint.invoke(params);
			if (logger.isDebugEnabled()) {
				logger.debug("Codigo nuevo de Atributo: " + codigo);
			}
		} catch (BackboneApplicationException e) {
			logger.error("Se origino un error: " + e.getMessage());
			throw new ApplicationException(
					"Error intentando obtener la clave del nuevo atributo variable");
		}
		if (logger.isInfoEnabled()) {
			logger.info("Esta saliendo del metodo de obtener Clave");
		}
		return codigo;
	}

	/**
	 * Implementacion que extrae todas las tablas asociadas al los atributos
	 * variables de un producto.
	 * 
	 * 
	 * @return List<AtributosVariablesVO> - Lista con la informacion de todas
	 *         las tablas asociadas al los atributos variables de un producto
	 *         solicitado.
	 * @throws ApplicationException
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */

	@SuppressWarnings("unchecked")
	public PagedList listaDeValoresAtributosVariables(String valor, int start, int limit) throws ApplicationException {

		Map<String,String> map = new HashMap<String,String>();
        map.put("pv_ottipotb_i", valor);
        
        String endpointName = "OBTIENE_VALORES_ATRIBUTOS";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
		
	}

	/**
	 * Implementacion que guarda todos los atributos variables de un producto.
	 * 
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	@SuppressWarnings("unchecked")
	public String guardarAtributosVariables(AtributosVariablesVO atributos)
			throws ApplicationException {

		logger.info("Entro metodo: guardarAtributosVariables");
		
		

		Map params = new HashMap();

		params.put("pv_cdramo_i", atributos.getCdRamo());
		params.put("pv_clavecampo_i", ConvertUtil.nvl(atributos.getClaveCampo()));
		params.put("pv_descripcion_i", atributos.getDescripcion());
		params.put("pv_cdformato_i", atributos.getCdFormato());
		params.put("pv_obligatorio_i", atributos.getObligatorio());
		params.put("pv_maximo_i", atributos.getMaximo());
		params.put("pv_minimo_i", atributos.getMinimo());
		params.put("pv_cdtabla_i", atributos.getCdTabla());
		params.put("pv_emision_i", atributos.getEmision());
		params.put("pv_endosos_i", atributos.getEndosos());
		params.put("pv_retarificacion_i", atributos.getRetarificacion());
		params.put("pv_cotizador_i", atributos.getCotizador());
		params.put("pv_swdatcom_i", atributos.getDatoComplementario());
		params.put("pv_swcomobl_i", atributos.getObligatorioComplementario());
		params.put("pv_swcomupd_i", atributos.getModificableComplementario());
		params.put("pv_swendoso_i", atributos.getApareceEndoso());
		params.put("pv_swendobl_i", atributos.getObligatorioEndoso());
		params.put("pv_cdatribu_padre_i", atributos.getCodigoPadre());
		params.put("pv_nmagrupa_i", atributos.getAgrupador());
		params.put("pv_cdcondicvis_i", atributos.getCodigoCondicion());
		params.put("pv_swinsert_i", atributos.getInserta());
		params.put("pv_cdexpres_i", atributos.getCodigoExpresion());
		params.put("pv_nmorden_i", atributos.getOrden());

		if (logger.isDebugEnabled())
			logger.debug("Return"); 
		try {
			WrapperResultados res = returnBackBoneInvoke(params, GUARDA_ATRIBUTOS_VARIABLES);
			logger.info("Salida metodo: guardarAtributosVariables");
			return res.getMsgText();
		} catch (ApplicationException ap) {
			logger.error("Error: " + ap, ap);
			ap.printStackTrace();
			throw new ApplicationException(ap.getMessage());
		} catch (Exception e){
			logger.error("Error: " + e, e);
			throw new ApplicationException(e.getMessage());
		}
		
		

	}

	public MensajesVO guardarAtributosVariablesInciso(AtributosVariablesVO atributos)
			throws ApplicationException {
		MensajesVO mensaje = null;
		Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_ATRIBUTOS_VARIABLES_INCISO");

		try {
			if (atributos.getClaveCampo() == null) {
				if (atributos.getClaveCampo().equals(""))
					atributos.setClaveCampo(null);
			}
			mensaje = (MensajesVO)endpoint.invoke(atributos);
			
		} catch (BackboneApplicationException e) {
			throw new ApplicationException(
					"Error intentando guardar los atributos variables");
		} catch (Exception ex) {
			logger.error("Error: " + ex, ex );
			throw new ApplicationException(
			"Error intentando guardar los atributos variables");
}
		return mensaje;
	}

	public MensajesVO guardarAtributosVariablesCobertura(AtributosVariablesVO atributos) throws ApplicationException {
		MensajesVO mensaje = null;
		Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_ATRIBUTOS_VARIABLES_GARANTIA");
		try {
			if (atributos.getClaveCampo() == null) {
				if (atributos.getClaveCampo().equals(""))
					atributos.setClaveCampo(null);
			}
			mensaje = (MensajesVO)endpoint.invoke(atributos);
		} catch (BackboneApplicationException e) {
			throw new ApplicationException("Error intentando guardar los atributos variables");
		}
		return mensaje;
	}
	
	public MensajesVO eliminarAtributosVariables(AtributosVariablesVO atributos, int nivel) throws ApplicationException {
		String vm = null;
		switch (nivel) {
		case 1:
			vm = "ELIMINA_ATRIBUTOS_VARIABLES_PRODUCTO";
			break;
		case 2:
			vm = "ELIMINA_ATRIBUTOS_VARIABLES_INCISO";
			break;
		case 3:
			vm = "ELIMINA_ATRIBUTOS_VARIABLES_GARANTIA";
			break;
		}
		
		MensajesVO mensajeVO = null;
		Endpoint endpoint = (Endpoint) endpoints.get(vm);
		try {
			mensajeVO = (MensajesVO)endpoint.invoke(atributos);
			
			WrapperResultados res = new WrapperResultados();
			res.setMsgId(mensajeVO.getMsgId());
			res = returnProcessResultMessageId(res);
			mensajeVO.setMsgText(res.getMsgText());
			
			
			if(logger.isDebugEnabled()) {
				logger.debug("MsgId=" + mensajeVO.getMsgId());
				logger.debug("MsgText=" + mensajeVO.getMsgText());
				logger.debug("Text=" + mensajeVO.getText());
				logger.debug("Title=" + mensajeVO.getTitle());
			}
		} catch (Exception e) {
			mensajeVO.setMsgText(e.getMessage());
			logger.error("Error intentando eliminar los atributos variables",e);
		}
		
		return mensajeVO;
	}

	public AtributosVariablesVO obtenerAtributoVariablePorNivel(int nivel,
			AtributosVariablesVO atributoVariable) throws ApplicationException {
		String vm = null;
		AtributosVariablesVO resultado = null;

		switch (nivel) {
		case 1:
			vm = "OBTIENE_ATRIBUTO_VARIABLE_PRODUCTO";
			break;
		case 2:
			vm = "OBTIENE_ATRIBUTO_VARIABLE_RIESGO";
			break;
		case 3:
			vm = "OBTIENE_ATRIBUTO_VARIABLE_COBERTURA";
			break;
		}
		if (vm != null) {
			try {
				Endpoint manager = (Endpoint) endpoints.get(vm);
				resultado = (AtributosVariablesVO) manager
						.invoke(atributoVariable);

			} catch (BackboneApplicationException bae) {
				logger.error("Exception in invoke", bae);
				throw new ApplicationException(
						"Error al cargar los Atributos Variables");
			}
		}

		return resultado;
	}

	@SuppressWarnings("unchecked")
	public void setEndpoints(Map endpoints) {
		this.endpoints = endpoints;
	}

	@SuppressWarnings("unchecked")
	public List<LlaveValorVO> getPadre(String cdTipSituacion,
			String codigoAtributoP) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("cdTipSituacion", cdTipSituacion);
		params.put("codigoAtributoP", codigoAtributoP);
		ArrayList<LlaveValorVO> padres = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_PADRES");
			padres = (ArrayList<LlaveValorVO>) endpoint.invoke(params);
		} catch (BackboneApplicationException bae) {
			logger.error(bae.getMessage());
			throw new ApplicationException("Error al recuperar datos de padre");
		}
		return (ArrayList<LlaveValorVO>) padres;
	}

	@SuppressWarnings("unchecked")
	public List<LlaveValorVO> getPadreObjeto(String cdTipSituacion,
			String codigoAtributoP) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("pv_cdtipobj_i", cdTipSituacion);
		params.put("pv_cdatribu_i", ConvertUtil.nvl(codigoAtributoP));
		
		return getAllBackBoneInvoke(params, ATRIBUTO_PADRE_OBJETO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.com.aon.catweb.configuracion.producto.service.AtributosVariablesManager#getPadrePoliza(java.lang.String,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<LlaveValorVO> getPadrePoliza(String cdRamo,
			String codigoAtributoP) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("pv_cdramo_i", cdRamo);
		params.put("pv_cdatribu_i", ConvertUtil.nvl(codigoAtributoP));

		return getAllBackBoneInvoke(params, ATRIBUTO_PADRE_PRODUCTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.com.aon.catweb.configuracion.producto.service.AtributosVariablesManager#getPadreGarantia(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<LlaveValorVO> getPadreGarantia(String codigoRamo,
			String codigoGarantia, String cdTipSituacion, String codigoAtributoP)
			throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("pv_cdramo_i", codigoRamo);
		params.put("pv_cdgarant_i", codigoGarantia);
		params.put("pv_cdtipsit_i", cdTipSituacion);
		params.put("pv_cdatribu_i", ConvertUtil.nvl(codigoAtributoP));

		return getAllBackBoneInvoke(params, ATRIBUTO_PADRE_COBERTURA);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.com.aon.catweb.configuracion.producto.service.AtributosVariablesManager#getPadreRol(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<LlaveValorVO> getPadreRol(String codigoRamo, String codigoRol,
			String codigoAtributoP) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("pv_cdrol_i", codigoRol);
		params.put("pv_cdramo_i", codigoRamo);
		params.put("pv_cdatribu_i", ConvertUtil.nvl(codigoAtributoP));

		return getAllBackBoneInvoke(params, ATRIBUTO_PADRE_ROL);
	}
	
	
	public boolean tieneHijosAtributoVariable(String endpointName, AtributosVariablesVO atributo) throws ApplicationException {
		MensajesVO mensaje = null;
		boolean tieneHijos = false;
		Endpoint endpoint = (Endpoint) endpoints.get(endpointName);
		try {
			mensaje = (MensajesVO) endpoint.invoke(atributo);
		} catch (BackboneApplicationException bae) {
			logger.error("Se origino un error: " + bae.getMessage(), bae);
			throw new ApplicationException("Error intentando validar hijos de atributos variables");
		} catch (Exception exc) {
			logger.error("Error: " + exc.getMessage(), exc);
			throw new ApplicationException("Error intentando validar hijos de atributos variables");
		}
		if(mensaje.getMsgText().equals("1")){
			tieneHijos = true;
		}else{
			tieneHijos = false;
		}
		return tieneHijos;
	}
	
}