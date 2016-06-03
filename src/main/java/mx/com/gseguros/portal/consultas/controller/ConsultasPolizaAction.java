package mx.com.gseguros.portal.consultas.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ValidationDataException;
import mx.com.gseguros.portal.consultas.model.AseguradoDetalleVO;
import mx.com.gseguros.portal.consultas.model.AseguradoVO;
import mx.com.gseguros.portal.consultas.model.CoberturaBasicaVO;
import mx.com.gseguros.portal.consultas.model.CoberturaVO;
import mx.com.gseguros.portal.consultas.model.ContratanteVO;
import mx.com.gseguros.portal.consultas.model.CopagoVO;
import mx.com.gseguros.portal.consultas.model.DatosComplementariosVO;
import mx.com.gseguros.portal.consultas.model.HistoricoFarmaciaVO;
import mx.com.gseguros.portal.consultas.model.HistoricoVO;
import mx.com.gseguros.portal.consultas.model.PeriodoVigenciaVO;
import mx.com.gseguros.portal.consultas.model.PlanVO;
import mx.com.gseguros.portal.consultas.model.PolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.PolizaDTO;
import mx.com.gseguros.portal.consultas.model.ReciboAgenteVO;
import mx.com.gseguros.portal.consultas.model.SituacionVO;
import mx.com.gseguros.portal.consultas.model.SuplementoVO;
import mx.com.gseguros.portal.consultas.model.TarifaVO;
import mx.com.gseguros.portal.consultas.service.ConsultasPolizaManager;
import mx.com.gseguros.portal.cotizacion.model.AgentePolizaVO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ClausulaVO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.model.PolizaVO;
import mx.com.gseguros.portal.general.service.ConveniosManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.utils.Utils;

import org.apache.axis2.clustering.LoadBalanceEventHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author HMLT
 */
@ParentPackage(value = "default")
@Namespace("/consultasPoliza")
@Controller("ConsultasPolizaAction")
@Scope(value = "prototype")
public class ConsultasPolizaAction extends PrincipalCoreAction {

	private static final long serialVersionUID = -6321288906841302337L;
	
	final static Logger logger = LoggerFactory.getLogger(ConsultasPolizaAction.class);

	/**
	 * Success property
	 */
	private boolean success;

	private String mensajeRes;
	
	private String mensajeConv;
	
	private long start;
	
	private long limit;
	
	private long totalCount;

	@Autowired
	private ConsultasPolizaManager consultasPolizaManager;

	@Autowired
	private KernelManagerSustituto kernelManager;

	private HashMap<String, String> params;

	private PolizaDTO datosPoliza;

	private ContratanteVO datosContratante;

	private PlanVO datosPlan;

	private DatosComplementariosVO datosComplementarios;

	private List<SuplementoVO> datosSuplemento;

	private List<HistoricoVO> datosHistorico;

	private List<SituacionVO> datosSituacion;

	private List<CoberturaVO> datosCoberturas;

	private List<PolizaAseguradoVO> polizasAsegurado;

	private List<ReciboAgenteVO> recibosAgente;

	private List<TarifaVO> datosTarifa;

	private List<AseguradoVO> datosAsegurados;

	private List<CopagoVO> datosCopagosPoliza;

	private List<CoberturaBasicaVO> datosCoberturasPoliza;

	private List<CoberturaBasicaVO> datosCoberturasBasicas;

	private List<ClausulaVO> datosEndososPoliza;

	private List<AseguradoDetalleVO> datosAseguradoDetalle;

	private List<ClausulaVO> clausulasPoliza;

	private List<HistoricoFarmaciaVO> historicoFarmacia;

	private List<PeriodoVigenciaVO> periodosVigencia;

	ArrayList<AgentePolizaVO> agentesPoliza;

	private Map<String, Item> itemMap;

	private List<Map<String, String>> loadList;
	
	@Autowired
	ConveniosManager conveniosManager;
	
	/**
	 * Indica si el usuario es de CallCenter
	 */
	private boolean usuarioCallCenter;

	/**
	 * Metodo de entrada a consulta de polizas
	 */
	public String execute() throws Exception {

		// Obtenemos el rol de sistema del usuario en sesion:
		UserVO usuario = (UserVO) session.get("USUARIO");
		String cdRolSistema = usuario.getRolActivo().getClave();
		// Si es consulta de informaci�n, no tendra permiso de ver todo:
		if (cdRolSistema.equals(RolSistema.CONSULTA_INFORMACION.getCdsisrol())) {
			usuarioCallCenter = true;
		}

		return SUCCESS;
	}

	/**
	 * Obtiene los datos generales de una p&oacute;liza
	 * 
	 * @return String result
	 */
	public String consultaDatosPoliza() {
		logger.debug(" **** Entrando a Consulta de Poliza ****");
		try {

			PolizaAseguradoVO polizaAseguradoVO = new PolizaAseguradoVO();
			polizaAseguradoVO.setCdunieco(params.get("cdunieco"));
			polizaAseguradoVO.setCdramo(params.get("cdramo"));
			polizaAseguradoVO.setEstado(params.get("estado"));
			polizaAseguradoVO.setNmpoliza(params.get("nmpoliza"));
			polizaAseguradoVO.setIcodpoliza(params.get("icodpoliza"));

			List<PolizaDTO> lista = consultasPolizaManager
					.obtieneDatosPoliza(polizaAseguradoVO);

			if (lista != null && !lista.isEmpty())
				datosPoliza = lista.get(0);

			logger.debug("Resultado de la consulta de poliza:{}", datosPoliza);

		} catch (Exception e) {
			logger.error("Error al obtener los datos de la poliza ", e);
			return SUCCESS;
		}

		success = true;
		return SUCCESS;
	}

	/**
	 * Obtiene los datos generales de una p&oacute;liza
	 * 
	 * @return String result
	 */
	public String consultaDatosPolizaTvalopol() {
		logger.debug(" **** Entrando a Consulta de Poliza Tvalopol ****");
		try {
			
			PolizaAseguradoVO polizaAseguradoVO = new PolizaAseguradoVO();
			polizaAseguradoVO.setCdunieco(params.get("cdunieco"));
			polizaAseguradoVO.setCdramo(params.get("cdramo"));
			polizaAseguradoVO.setEstado(params.get("estado"));
			polizaAseguradoVO.setNmpoliza(params.get("nmpoliza"));
			polizaAseguradoVO.setIcodpoliza(params.get("icodpoliza"));
			polizaAseguradoVO.setNmsuplem(params.get("suplemento"));
			
			loadList = consultasPolizaManager
					.obtieneDatosPolizaTvalopol(polizaAseguradoVO);
			
			logger.debug("Resultado de la consulta de poliza tvalopol :{}", loadList);
			
		} catch (Exception e) {
			logger.error("Error al obtener los datos de la poliza  tvalopol ", e);
			return SUCCESS;
		}
		
		success = true;
		return SUCCESS;
	}

	/**
	 * Obtiene los datos complementarios de una p&oacute;liza
	 * 
	 * @return String result
	 */
	public String consultaDatosComplementarios() {
		logger.debug(" **** Entrando a Consulta de Datos Complementarios ****");
		try {

			PolizaVO poliza = new PolizaVO();
			AseguradoVO asegurado = new AseguradoVO();
			poliza.setIcodpoliza(params.get("icodpoliza"));
			asegurado.setCdperson(params.get("cdperson"));

			List<DatosComplementariosVO> lista = consultasPolizaManager
					.obtieneDatosComplementarios(poliza, asegurado);

			if (lista != null && !lista.isEmpty())
				datosComplementarios = lista.get(0);

			logger.debug("Resultado de consultaAseguradoDetalle:{}", datosComplementarios);

			/*
			 * PolizaAseguradoVO polizaAseguradoVO = new
			 * PolizaAseguradoVO();
			 * polizaAseguradoVO.setCdunieco(params.get("cdunieco"));
			 * polizaAseguradoVO.setCdramo(params.get("cdramo"));
			 * polizaAseguradoVO.setEstado(params.get("estado"));
			 * polizaAseguradoVO.setNmpoliza(params.get("nmpoliza"));
			 * polizaAseguradoVO.setIcodpoliza(params.get("icodpoliza"));
			 * 
			 * List<PolizaVO> lista =
			 * consultasPolizaManager.obtieneDatosPoliza(polizaAseguradoVO);
			 * 
			 * if(lista!=null && !lista.isEmpty()) datosPoliza = lista.get(0);
			 */

			logger.debug("Resultado de la consulta de datos complementarios:{}", datosComplementarios);

		} catch (Exception e) {
			logger.error("Error al obtener los datos complementarios ", e);
			return SUCCESS;
		}

		success = true;
		return SUCCESS;
	}

	/**
	 * Obtiene los datos de los suplementos de la poliza
	 * 
	 * @return String result
	 */
	public String consultaDatosSuplemento() {
		logger.debug(" **** Entrando a consultaDatosSuplemento ****");
		
		logger.debug("Parametros de entrada: "+params);
		
		mensajeRes = "";
		
		try {
			
//			groupTipoBusqueda
			
			if(params.containsKey("tipoBusqueda") && StringUtils.isNotBlank(params.get("tipoBusqueda")) && "5".equals(params.get("tipoBusqueda"))){
				datosSuplemento = consultasPolizaManager.obtieneHistoricoPolizaCorto(params.get("sucursal"), params.get("producto"), params.get("numpolizacorto"), params.get("cdsisrol"));
			}else{				
				PolizaAseguradoVO poliza = new PolizaAseguradoVO();
				poliza.setIcodpoliza(params.get("icodpoliza"));
				poliza.setNmpoliex(params.get("nmpoliex"));
				poliza.setCdsisrol(params.get("cdsisrol"));
				datosSuplemento = consultasPolizaManager.obtieneHistoricoPoliza(poliza);
			}
			logger.debug(Utils.log("datos suplemento",datosSuplemento));
			if (datosSuplemento != null) {
				logger.debug("Historicos encontrados: {}", datosSuplemento.size());
			}

			if (datosSuplemento != null && !datosSuplemento.isEmpty()) {
				try {
					mensajeRes = consultasPolizaManager.obtieneMensajeAgente(
							new PolizaVO(datosSuplemento.get(0).getCdunieco(),
										datosSuplemento.get(0).getCdramo(), 
										datosSuplemento.get(0).getEstado(), 
										datosSuplemento.get(0).getNmpoliza()));
					logger.debug("Mensaje para Agente: {}", mensajeRes);
				} catch (Exception e) {
					logger.error("Error!! no se pudo obtener el mensaje para el Agente de esta poliza!", e);
				}
			}

		} catch (Exception e) {
			success = false;
			logger.error("Error al obtener los consultaDatosSuplemento {}", datosSuplemento, e);
			return SUCCESS;
		}

		success = true;
		return SUCCESS;

	}

	/**
	 * Obtiene los datos del hist�rico de una p�liza
	 * 
	 * @return String result
	 */
	public String consultaDatosHistorico() {
		logger.debug(" **** Entrando a consultaDatosHist�rico ****");
		mensajeRes = "";
		try {
			PolizaAseguradoVO poliza = new PolizaAseguradoVO();
			poliza.setIcodpoliza(params.get("icodpoliza"));
			poliza.setNmpoliex(params.get("nmpoliex"));
			datosHistorico = consultasPolizaManager.obtieneHistoricoPolizaSISA(poliza);
			logger.debug("Resultado de la consultaDatosHistorico:{}", datosHistorico);

		} catch (Exception e) {
			success = false;
			logger.error("Error al obtener los consultaDatosHistorico ", e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}

	/**
	 * Obtiene los datos del contratante
	 * 
	 * @return String result
	 */
	public String consultaDatosContratante() {
		logger.debug(" **** Entrando a Consulta de Contratante ****");
		try {

			PolizaVO polizaVO = new PolizaVO();
			polizaVO.setCdunieco(params.get("cdunieco"));
			polizaVO.setCdramo(params.get("cdramo"));
			polizaVO.setEstado(params.get("estado"));
			polizaVO.setNmpoliza(params.get("nmpoliza"));
			polizaVO.setIcodpoliza(params.get("icodpoliza"));

			List<ContratanteVO> lista = consultasPolizaManager
					.obtieneDatosContratante(polizaVO);

			if (lista != null && !lista.isEmpty())
				datosContratante = lista.get(0);

			logger.debug("Resultado de la consulta de contratante:{}", datosContratante);

		} catch (Exception e) {
			logger.error("Error al obtener los datos del contratante ", e);
			return SUCCESS;
		}

		success = true;
		return SUCCESS;
	}


	/**
	 * Obtiene los endosos de una poliza
	 * 
	 * @return String result
	 */
	@Action(value = "consultaClausulasPoliza", results = {
		@Result(name = "success", type = "json", params = {"ignoreHierarchy","false", "includeProperties","clausulasPoliza.*,success" }) 
	})
	public String consultaClausulasPoliza() {
		logger.debug(" **** Entrando a consultaClausulasPoliza ****");
		PolizaVO poliza = new PolizaVO();
		poliza.setCdunieco(params.get("cdunieco"));
		poliza.setCdramo(params.get("cdramo"));
		poliza.setEstado(params.get("estado"));
		poliza.setNmpoliza(params.get("nmpoliza"));
		poliza.setNmsuplem(params.get("suplemento"));
		poliza.setNmsituac(params.get("nmsituac"));
		poliza.setIcodpoliza(params.get("icodpoliza"));
		AseguradoVO asegurado = new AseguradoVO();
		asegurado.setCdperson(params.get("cdperson"));
		try {
			clausulasPoliza = consultasPolizaManager.obtieneEndososPoliza(
					poliza, asegurado);
			success = true;
		} catch (Exception e) {
			logger.error("Error al obtener las cl�usulas de la p�liza", e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}

	/**
	 * Obtiene el hist�rico de farmacia
	 * 
	 * @return String result
	 */
	@Action(value = "consultaHistoricoFarmacia", results = { @Result(name = "success", type = "json", params = {
			"ignoreHierarchy", "false", "includeProperties",
			"historicoFarmacia.*,success" }) })
	public String consultaHistoricoFarmacia() {
		logger.debug(" **** Entrando a consultaHistoricoFarmacia ****");
		PolizaVO poliza = new PolizaVO();
		poliza.setCdunieco(params.get("cdunieco"));
		poliza.setCdramo(params.get("cdramo"));
		poliza.setEstado(params.get("estado"));
		poliza.setNmpoliza(params.get("nmpoliza"));
		poliza.setNmsuplem(params.get("suplemento"));
		poliza.setNmsituac(params.get("nmsituac"));
		poliza.setIcodpoliza(params.get("icodpoliza"));
		AseguradoVO asegurado = new AseguradoVO();
		asegurado.setCdperson(params.get("cdperson"));
		try {

			historicoFarmacia = consultasPolizaManager
					.obtieneHistoricoFarmacia(poliza, asegurado);
			success = true;
		} catch (Exception e) {
			logger.error("Error al obtener el hist�rico de farmacia", e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}

	/**
	 * Obtiene periodos de vigencia
	 * 
	 * @return String result
	 */
	@Action(value = "consultaPeriodosVigencia", results = { @Result(name = "success", type = "json", params = {
			"ignoreHierarchy", "false", "includeProperties",
			"periodosVigencia.*,success" }) })
	public String consultaPeriodosVigencia() {
		logger.debug(" **** Entrando a consultaVigencia ****");
		PolizaVO poliza = new PolizaVO();
		poliza.setCdunieco(params.get("cdunieco"));
		poliza.setCdramo(params.get("cdramo"));
		poliza.setEstado(params.get("estado"));
		poliza.setNmpoliza(params.get("nmpoliza"));
		poliza.setNmsuplem(params.get("suplemento"));
		poliza.setNmsituac(params.get("nmsituac"));
		poliza.setIcodpoliza(params.get("icodpoliza"));
		AseguradoVO asegurado = new AseguradoVO();
		asegurado.setCdperson(params.get("cdperson"));
		try {

			periodosVigencia = consultasPolizaManager.obtienePeriodosVigencia(
					poliza, asegurado);
			success = true;
		} catch (Exception e) {
			logger.error("Error al obtener periodos de vigencia", e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	
	
	/**
	 * Obtiene las polizas del asegurado por rfc
	 * 
	 * @return String result
	 */
	public String consultaPolizasAsegurado() {
		
		UserVO usuario = (UserVO)session.get("USUARIO");
		String cdusuari = usuario.getUser();
		String cdsisrol = usuario.getRolActivo().getClave();
		
		logger.debug(" **** Entrando a obtienePolizasAsegurado ****");
		
		if(RolSistema.AGENTE.getCdsisrol().equals(cdsisrol)) {
			try {
				polizasAsegurado = consultasPolizaManager.obtienePolizasAsegurado(
						cdusuari,
						params.get("rfc"), params.get("cdperson"),
						params.get("nombre"));

				if (polizasAsegurado != null) {
					logger.debug("Polizas por asegurado encontradas: {}", polizasAsegurado.size());
				}
			} catch (Exception e) {
				logger.error("Error al obtener los obtienePolizasAsegurado ", e);
				return SUCCESS;
			}

		} 
		
		else if(RolSistema.PROMOTOR_AUTO.getCdsisrol().equals(cdsisrol)) {
			try {
				polizasAsegurado = consultasPolizaManager.obtienePolizasAsegPromotor(
						cdusuari,
						params.get("rfc"), params.get("cdperson"),
						params.get("nombre"));

				if (polizasAsegurado != null) {
					logger.debug("Polizas por asegurado encontradas: {}", polizasAsegurado.size());
				}
			} catch (Exception e) {
				logger.error("Error al obtener los obtienePolizasAsegurado ", e);
				return SUCCESS;
			}

		}
		
		else {
			
			try {
				polizasAsegurado = consultasPolizaManager.obtienePolizasAsegurado(null,
						params.get("rfc"), params.get("cdperson"),
						params.get("nombre"));

				if (polizasAsegurado != null) {
					logger.debug("Polizas por asegurado encontradas: {}", polizasAsegurado.size());
				}
			} catch (Exception e) {
				logger.error("Error al obtener los obtienePolizasAsegurado ", e);
				return SUCCESS;
			}
		}
		
		success = true;
		return SUCCESS;
	}

	/**
	 * Obtiene los datos de la tarifa de la poliza
	 * 
	 * @return String result
	 */
	public String consultaDatosTarifaPoliza() {
		logger.debug(" **** Entrando a consultaDatosTarifaPoliza ****");
		try {
			datosTarifa = consultasPolizaManager.obtieneTarifasPoliza(
					new PolizaVO(params.get("cdunieco"), params.get("cdramo"), params.get("estado"), 
							params.get("nmpoliza"), params.get("suplemento"), null, null));
			
			logger.debug("Resultado de la consultaDatosTarifa:{}", datosTarifa);
		} catch (Exception e) {
			logger.error("Error al obtener los consultaDatosTarifaPoliza ", e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	

	/**
	 * Obtiene recibos de la poliza de un agente
	 * 
	 * @return String result
	 */
	public String consultaRecibosAgente() {
		logger.debug(" **** Entrando a consultaRecibosAgente ****");
		try {
			PolizaVO poliza = new PolizaVO();
			poliza.setCdunieco(params.get("cdunieco"));
			poliza.setCdramo(params.get("cdramo"));
			poliza.setEstado(params.get("estado"));
			poliza.setNmpoliza(params.get("nmpoliza"));
			poliza.setIcodpoliza(params.get("icodpoliza"));
			
			recibosAgente = consultasPolizaManager.obtieneRecibosAgente(poliza);
			
			if (recibosAgente != null) {
				logger.debug("Recibos del agente encontrados: {}", recibosAgente.size());
			}
		} catch (Exception e) {
			logger.error("Error al obtener los consultaRecibosAgente ", e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}


	public String consultaAgentesPoliza() throws Exception {
		logger.debug(" **** Entrando a consultaAgentesPoliza ****");
		try {
			PolizaVO poliza = new PolizaVO();
			poliza.setCdunieco(params.get("cdunieco"));
			poliza.setCdramo(params.get("cdramo"));
			poliza.setEstado(params.get("estado"));
			poliza.setNmpoliza(params.get("nmpoliza"));
			poliza.setNmsuplem(params.get("suplemento"));
			poliza.setIcodpoliza(params.get("icodpoliza"));

			agentesPoliza = (ArrayList<AgentePolizaVO>) consultasPolizaManager.obtieneAgentesPoliza(poliza);

			logger.debug("Resultado de la consultaAgentesPoliza={}", agentesPoliza);
		} catch (Exception e) {
			logger.error("Error en consultaAgentesPoliza", e);
			success = false;
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}

	/**
	 * Obtiene los datos del asegurado
	 * 
	 * @return String result
	 */
	public String consultaDatosAsegurado() {
		logger.debug(" **** Entrando a consultaDatosAsegurado ****");
		try {
			PolizaVO poliza = new PolizaVO();
			poliza.setCdunieco(params.get("cdunieco"));
			poliza.setCdramo(params.get("cdramo"));
			poliza.setEstado(params.get("estado"));
			poliza.setNmpoliza(params.get("nmpoliza"));
			poliza.setNmsuplem(params.get("suplemento"));
			poliza.setIcodpoliza(params.get("icodpoliza"));
			datosAsegurados = consultasPolizaManager.obtieneAsegurados(poliza);
			if(datosAsegurados != null) {
				logger.debug("Asegurados obtenidos:{}", datosAsegurados.size());
			}
		} catch (Exception e) {
			logger.error("Error al obtener los datos del Asegurado ", e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	
	/**
	 * Obtiene los datos del asegurado
	 * 
	 * @return String result
	 */
	public String consultaDatosAsegurados() {
		logger.debug(" **** Entrando a consultaDatosAsegurados ****");
		try {
			PolizaVO poliza = new PolizaVO();
			poliza.setCdunieco(params.get("cdunieco"));
			poliza.setCdramo(params.get("cdramo"));
			poliza.setEstado(params.get("estado"));
			poliza.setNmpoliza(params.get("nmpoliza"));
			poliza.setNmsuplem(params.get("suplemento"));
			poliza.setIcodpoliza(params.get("icodpoliza"));
			poliza.setCdperson(params.get("cdperson"));
			//poliza.setNmsitaux(params.get("nmsitaux"));
			poliza.setFamilia(params.get("familia"));
			poliza.setNombre(params.get("nombre"));
			datosAsegurados = consultasPolizaManager.obtieneAsegurados(poliza,start,limit);
			if(datosAsegurados != null && datosAsegurados.size() > 0) {
				totalCount = datosAsegurados.get(0).getTotal();
			}
			if(datosAsegurados != null) {
				logger.debug("Asegurados obtenidos:{}", datosAsegurados.size());
			}
		} catch (Exception e) {
			logger.error("Error al obtener los datos del Asegurado ", e);
			return SUCCESS;
		}
		success = true;	
		return SUCCESS;
	}

	/**
	 * Obtiene los copagos de una poliza
	 * 
	 * @return String result
	 */
	public String consultaCopagosPoliza() {
		logger.debug(" **** Entrando a consultaCopagosPoliza ****");
		logger.debug(" **** parametros: "+params);
		try {
			PolizaVO poliza = new PolizaVO();
			poliza.setCdunieco(params.get("cdunieco"));
			poliza.setCdramo(params.get("cdramo"));
			poliza.setEstado(params.get("estado"));
			poliza.setNmpoliza(params.get("nmpoliza"));
			poliza.setNmsuplem(params.get("suplemento"));
			poliza.setIcodpoliza(params.get("icodpoliza"));

			String nmsituac = params.get("nmsituac");
			
			datosCopagosPoliza = consultasPolizaManager.obtieneCopagosPoliza(poliza, nmsituac);
			logger.debug("Resultado de consultaCopagosPoliza:{}", datosCopagosPoliza);
		} catch (Exception e) {
			logger.error("Error al obtener los copagos de la poliza ", e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}

	/**
	 * Obtiene las coberturas
	 * 
	 * @return String result
	 */
	public String consultaCoberturasPoliza() {
		logger.debug(" **** Entrando a consultaCoberturasPoliza ****");
		try {

			PolizaVO poliza = new PolizaVO();
			poliza.setCdunieco(params.get("cdunieco"));
			poliza.setCdramo(params.get("cdramo"));
			poliza.setEstado(params.get("estado"));
			poliza.setNmpoliza(params.get("nmpoliza"));
			poliza.setNmsuplem(params.get("suplemento"));
			poliza.setIcodpoliza(params.get("icodpoliza"));
			datosCoberturasPoliza = consultasPolizaManager
					.obtieneCoberturasPoliza(poliza);

			logger.debug("Resultado de consultaCoberturasBasicas:{}", datosCoberturasBasicas);

		} catch (Exception e) {
			logger.error("Error al obtener las coberturas basicas ", e);
			return SUCCESS;
		}

		success = true;
		return SUCCESS;

	}

	/**
	 * Obtiene las coberturas b�sicas
	 * 
	 * @return String result
	 */
	public String consultaCoberturasBasicas() {
		logger.debug(" **** Entrando a consultaConsultaCoberturasBasicas ****");
		try {

			PolizaVO poliza = new PolizaVO();
			poliza.setCdunieco(params.get("cdunieco"));
			poliza.setCdramo(params.get("cdramo"));
			poliza.setEstado(params.get("estado"));
			poliza.setNmpoliza(params.get("nmpoliza"));
			poliza.setNmsuplem(params.get("suplemento"));
			poliza.setIcodpoliza(params.get("icodpoliza"));
			datosCoberturasBasicas = consultasPolizaManager
					.obtieneCoberturasBasicas(poliza);

			logger.debug("Resultado de consultaCoberturasBasicas:{}", datosCoberturasBasicas);

		} catch (Exception e) {
			logger.error("Error al obtener las coberturas basicas ", e);
			return SUCCESS;
		}

		success = true;
		return SUCCESS;

	}

	/**
	 * Obtiene los datos del plan
	 * 
	 * @return String result
	 */
	public String consultaDatosPlan() {
		logger.debug(" **** Entrando a Consulta de Plan ****");
		try {

			PolizaVO polizaVO = new PolizaVO();
			polizaVO.setCdunieco(params.get("cdunieco"));
			polizaVO.setCdramo(params.get("cdramo"));
			polizaVO.setEstado(params.get("estado"));
			polizaVO.setNmpoliza(params.get("nmpoliza"));
			polizaVO.setIcodpoliza(params.get("icodpoliza"));

			List<PlanVO> lista = consultasPolizaManager
					.obtieneDatosPlan(polizaVO);

			if (lista != null && !lista.isEmpty())
				datosPlan = lista.get(0);

			logger.debug("Resultado de la consulta de plan:{}", datosPlan);

		} catch (Exception e) {
			logger.error("Error al obtener los datos del plan ", e);
			return SUCCESS;
		}

		success = true;
		return SUCCESS;
	}

	/**
	 * Funcion para la visualizacion de las coberturas
	 * 
	 * @return params con los valores para hacer las consultas
	 */
	public String verCoberturas() {
		logger.debug(" **** Entrando a verCoberturas ****");
		try {
			logger.debug("params={}", params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		success = true;
		return SUCCESS;
	}

	/**
	 * Entrada a la pantalla de las clausulas de una poliza <br/>
	 * Sirve para propagar los parametros del atributo params a la pantalla
	 * 
	 * @return
	 */
	public String verClausulasPoliza() {
		success = true;
		return SUCCESS;
	}

	/**
	 * Entrada a la pantalla hist�rico de farmacia <br/>
	 * Sirve para propagar los parametros del atributo params a la pantalla
	 * 
	 * @return
	 */
	public String verHistoricoFarmacia() {
		success = true;
		return SUCCESS;
	}

	/**
	 * Entrada a la pantalla periodos vigencia <br/>
	 * Sirve para propagar los parametros del atributo params a la pantalla
	 * 
	 * @return
	 */
	public String verPeriodosVigencia() {
		success = true;
		return SUCCESS;
	}

	/**
	 * Entrada a la pantalla Aviso Hospitalizaci�n <br/>
	 * Sirve para propagar los parametros del atributo params a la pantalla
	 * 
	 * @return
	 */
	public String darAvisoHospitalizacion() {
		success = true;
		return SUCCESS;
	}

	/**
	 * Funcion para la visualizacion de las exclusiones
	 * 
	 * @return params con los valores para hacer las consultas
	 */
	public String verExclusiones() {
		logger.debug(" **** Entrando a verExclusiones ****");
		try {
			// logger.debug("params={}" + params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		success = true;
		return SUCCESS;
	}

	/**
	 * Funcion para la visualizacion de las endosos
	 * 
	 * @return params con los valores para hacer las consultas
	 */
	public String consultaEndososPoliza() {
		logger.debug(" **** Entrando a verExclusiones ****");
		try {

			PolizaVO poliza = new PolizaVO();
			AseguradoVO asegurado = new AseguradoVO();
			poliza.setIcodpoliza(params.get("icodpoliza"));
			asegurado.setCdperson(params.get("cdperson"));
			datosEndososPoliza = consultasPolizaManager.obtieneEndososPoliza(
					poliza, asegurado);

			logger.debug("Resultado de consultaEndososPoliza:{}", datosEndososPoliza);

		} catch (Exception e) {
			logger.error("Error al obtener los endosos de la poliza ", e);
			return SUCCESS;
		}

		success = true;
		return SUCCESS;
	}

	public String entradaDetalleAsegurado() {
		return "origen_sisa";
	}

	public String consultaAseguradoDetalle() {
		logger.debug(" *** Entrando a ver Detalle ****");
		try {
			AseguradoVO asegurado = new AseguradoVO();
			asegurado.setCdperson(params.get("cdperson"));
			datosAseguradoDetalle = consultasPolizaManager
					.obtieneAseguradoDetalle(asegurado);

			logger.debug("Resultado de consultaAseguradoDetalle:{}", datosAseguradoDetalle);

		} catch (Exception e) {
			logger.error("Error al obtener el detalle del asegurado.", e);
			return SUCCESS;
		}

		success = true;
		return SUCCESS;
	}

	public String obtenerDatosTatrisit() {
		logger.info("\n######################################\n###### pantallaConsultaTatrisit ######");
		logger.info("params: {}", params);
		try {
			String cdtipsit = params.get("cdtipsit");
			String cdusuari;
			{
				UserVO usuario = (UserVO) session.get("USUARIO");
				cdusuari = usuario.getUser();
			}
			List<ComponenteVO> camposTatrisit = kernelManager.obtenerTatrisit(
					cdtipsit, cdusuari);
			List<ComponenteVO> tatrisitTemp = new ArrayList<ComponenteVO>();
			// buscar cp
			for (ComponenteVO t : camposTatrisit)
				if (t.getLabel().contains("POSTAL"))
					tatrisitTemp.add(t);
			// buscar estado
			for (ComponenteVO t : camposTatrisit)
				if (t.getLabel().contains("ESTADO"))
					tatrisitTemp.add(t);
			// buscar municipio
			for (ComponenteVO t : camposTatrisit)
				if (t.getLabel().contains("MUNICIPIO"))
					tatrisitTemp.add(t);
			// agregar todos los demas
			for (ComponenteVO comp : camposTatrisit) {
				comp.setSoloLectura(true);
				comp.setObligatorio(false);
				comp.setMinLength(0);
				if (!comp.getLabel().contains("POSTAL")
						&& !comp.getLabel().contains("ESTADO")
						&& !comp.getLabel().contains("MUNICIPIO")) {
					tatrisitTemp.add(comp);
				}
			}
			camposTatrisit = tatrisitTemp;
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext
					.getServletContext().getServletContextName());
			gc.setCdtipsit(cdtipsit);
			gc.generaComponentes(camposTatrisit, true, true, true, false,
					false, false);
			itemMap = new HashMap<String, Item>();
			itemMap.put("fieldsModelo", gc.getFields());
			itemMap.put("itemsFormulario", gc.getItems());
		} catch (Exception ex) {
			logger.error("error al generar pantalla de consulta de tatrisit", ex);
		}
		logger.info("\n###### pantallaConsultaTatrisit ######\n######################################");
		return SUCCESS;
	}

	/**
	 * Obtiene los mensajes del action
	 * 
	 * @return String result
	 * @throws ValidationDataException 
	 */
	public String obtieneAvisos() throws ValidationDataException {
		logger.debug(Utils.log(" **** Entrando a obtieneMensajesAction ****"));
		
		logger.debug(Utils.log("Parametros de entrada: "+params));
		
		mensajeRes = "";
		
		mensajeConv = "";
		logger.debug(Utils.log(" **** Entrando a groupTipoBusqueda ****"));
		logger.debug(Utils.log("params entrada",params));
		Utils.validate(params,"No se recibieron datos");
		String cdunieco  = params.get("sucursal");
		String cdramo  = params.get("producto");
		String estado  = "M";
		String nmpoliza = params.get("numpolizacorto");
		String swconvenio = params.get("switchConvenios");
		try {		
					logger.debug(Utils.log(" **** datos suplemento diferente de null ****"));
					logger.debug(Utils.log(" **** datos de poliza ****\n",
										   "****",cdunieco,"****\n",
										   "****",cdramo,"****\n",
										   "****",estado,"****\n",
										   "****",nmpoliza,"****\n",
										   "****",swconvenio,"****\n"));
					mensajeRes = consultasPolizaManager.obtieneMensajeAgente(new PolizaVO(cdunieco,
																						  cdramo, 
																						  estado, 
																						  nmpoliza));
					if(mensajeRes == null){
						mensajeRes = "";
					}
					if(null != swconvenio && swconvenio.toUpperCase().equals("S")){
						loadList = conveniosManager.buscarPoliza(cdunieco, cdramo, null, estado, nmpoliza, null);
						if (loadList.size() > 0) {
							mensajeConv = loadList.get(0).get("LEYENDA");
						}
						logger.debug(Utils.log(" **** datos de poliza ****\n", loadList.size(), "**********"));
						logger.debug("Mensaje para Agente: {}", mensajeRes);
						logger.debug("Mensaje convenio: {}", mensajeConv);
					}
		} catch (Exception e) {
			logger.error("Error al obtener los avisos {}", datosSuplemento, e);
			mensajeRes = Utils.manejaExcepcion(e);
			success = false;
			return SUCCESS;
		}

		success = true;
		return SUCCESS;

	}
	
	// Getters and setters:

	public List<AseguradoVO> getDatosAsegurados() {
		return datosAsegurados;
	}

	public void setDatosAsegurados(List<AseguradoVO> datosAsegurados) {
		this.datosAsegurados = datosAsegurados;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public PolizaDTO getDatosPoliza() {
		return datosPoliza;
	}

	public void setDatosPoliza(PolizaDTO datosPoliza) {
		this.datosPoliza = datosPoliza;
	}

	public List<SuplementoVO> getDatosSuplemento() {
		return datosSuplemento;
	}

	public void setDatosSuplemento(
			List<SuplementoVO> datosSuplemento) {
		this.datosSuplemento = datosSuplemento;
	}

	public List<SituacionVO> getDatosSituacion() {
		return datosSituacion;
	}

	public void setDatosSituacion(List<SituacionVO> datosSituacion) {
		this.datosSituacion = datosSituacion;
	}

	public List<CoberturaVO> getDatosCoberturas() {
		return datosCoberturas;
	}

	public void setDatosCoberturas(
			List<CoberturaVO> datosCoberturas) {
		this.datosCoberturas = datosCoberturas;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<PolizaAseguradoVO> getPolizasAsegurado() {
		return polizasAsegurado;
	}

	public void setPolizasAsegurado(
			List<PolizaAseguradoVO> polizasAsegurado) {
		this.polizasAsegurado = polizasAsegurado;
	}

	public List<TarifaVO> getDatosTarifa() {
		return datosTarifa;
	}

	public void setDatosTarifa(List<TarifaVO> datosTarifa) {
		this.datosTarifa = datosTarifa;
	}

	public List<ReciboAgenteVO> getRecibosAgente() {
		return recibosAgente;
	}

	public void setRecibosAgente(List<ReciboAgenteVO> recibosAgente) {
		this.recibosAgente = recibosAgente;
	}

	public List<CopagoVO> getDatosCopagosPoliza() {
		return datosCopagosPoliza;
	}

	public void setDatosCopagosPoliza(List<CopagoVO> datosCopagosPoliza) {
		this.datosCopagosPoliza = datosCopagosPoliza;
	}

	public List<CoberturaBasicaVO> getDatosCoberturasBasicas() {
		return datosCoberturasBasicas;
	}

	public void setDatosCoberturasBasicas(
			List<CoberturaBasicaVO> datosCoberturasBasicas) {
		this.datosCoberturasBasicas = datosCoberturasBasicas;
	}

	public Map<String, Item> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<String, Item> itemMap) {
		this.itemMap = itemMap;
	}

	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}

	public String getMensajeRes() {
		return mensajeRes;
	}

	public void setMensajeRes(String mensajeRes) {
		this.mensajeRes = mensajeRes;
	}

	public List<Map<String, String>> getLoadList() {
		return loadList;
	}

	public void setLoadList(List<Map<String, String>> loadList) {
		this.loadList = loadList;
	}

	public List<ClausulaVO> getClausulasPoliza() {
		return clausulasPoliza;
	}

	public void setClausulasPoliza(List<ClausulaVO> clausulasPoliza) {
		this.clausulasPoliza = clausulasPoliza;
	}

	public boolean isUsuarioCallCenter() {
		return usuarioCallCenter;
	}

	public void setUsuarioCallCenter(boolean usuarioCallCenter) {
		this.usuarioCallCenter = usuarioCallCenter;
	}

	public List<ClausulaVO> getDatosEndososPoliza() {
		return datosEndososPoliza;
	}

	public void setDatosEndososPoliza(List<ClausulaVO> datosEndososPoliza) {
		this.datosEndososPoliza = datosEndososPoliza;
	}

	public List<AseguradoDetalleVO> getDatosAseguradoDetalle() {
		return datosAseguradoDetalle;
	}

	public void setDatosAseguradoDetalle(
			List<AseguradoDetalleVO> datosAseguradoDetalle) {
		this.datosAseguradoDetalle = datosAseguradoDetalle;
	}

	public List<HistoricoVO> getDatosHistorico() {
		return datosHistorico;
	}

	public void setDatosHistorico(List<HistoricoVO> datosHistorico) {
		this.datosHistorico = datosHistorico;
	}

	public DatosComplementariosVO getDatosComplementarios() {
		return datosComplementarios;
	}

	public void setDatosComplementarios(
			DatosComplementariosVO datosComplementarios) {
		this.datosComplementarios = datosComplementarios;
	}

	public List<HistoricoFarmaciaVO> getHistoricoFarmacia() {
		return historicoFarmacia;
	}

	public void setHistoricoFarmacia(List<HistoricoFarmaciaVO> historicoFarmacia) {
		this.historicoFarmacia = historicoFarmacia;
	}

	public ContratanteVO getDatosContratante() {
		return datosContratante;
	}

	public void setDatosContratante(ContratanteVO datosContratante) {
		this.datosContratante = datosContratante;
	}

	public PlanVO getDatosPlan() {
		return datosPlan;
	}

	public void setDatosPlan(PlanVO datosPlan) {
		this.datosPlan = datosPlan;
	}

	public List<PeriodoVigenciaVO> getPeriodosVigencia() {
		return periodosVigencia;
	}

	public void setPeriodosVigencia(
			List<PeriodoVigenciaVO> periodosVigencia) {
		this.periodosVigencia = periodosVigencia;
	}

	public List<CoberturaBasicaVO> getDatosCoberturasPoliza() {
		return datosCoberturasPoliza;
	}

	public void setDatosCoberturasPoliza(
			List<CoberturaBasicaVO> datosCoberturasPoliza) {
		this.datosCoberturasPoliza = datosCoberturasPoliza;
	}
	
	public ArrayList<AgentePolizaVO> getAgentesPoliza() {
		return agentesPoliza;
	}

	public void setAgentesPoliza(ArrayList<AgentePolizaVO> agentesPoliza) {
		this.agentesPoliza = agentesPoliza;
	}

	public String getMensajeConv() {
		return mensajeConv;
	}

	public void setMensajeConv(String mensajeConv) {
		this.mensajeConv = mensajeConv;
	}
	
	public long getStart(){
		return start;
	}
	
	public void setStart(long start){
		this.start=start;
	}
	
	public long getLimit(){
		return limit;
	}
	
	public void setLimit(long limit){
		this.limit=limit;
	}
	
	public long getTotalCount(){
		return totalCount;
	}
	
	public void setTotalCount(long totalcount){
		this.totalCount=totalcount;
	}
}