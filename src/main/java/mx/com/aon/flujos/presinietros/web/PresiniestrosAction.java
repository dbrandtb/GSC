package mx.com.aon.flujos.presinietros.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import java.io.InputStream;

import mx.com.aon.portal.util.Util;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.flujos.presinietros.model.DocumentoVO;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.flujos.presinietros.service.PresiniestrosManager;
import mx.com.aon.portal.model.presiniestros.BeneficioVO;
import mx.com.aon.portal.model.presiniestros.PreSiniestroVO;
import mx.com.aon.portal.model.presiniestros.PreSiniestroPorPolizaVO;
import mx.com.aon.portal.model.presiniestros.AutomovilVO;
import mx.com.aon.portal.model.presiniestros.DanoVO;
import mx.com.aon.portal.model.BaseObjectVO;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import mx.com.aon.portal.service.PagedList;
import mx.com.gseguros.exception.ApplicationException;

import com.opensymphony.xwork2.ActionSupport;

import static mx.com.aon.utils.Constantes.CDUNIECO;
import static mx.com.aon.utils.Constantes.INSERT_MODE;
import static mx.com.aon.utils.Constantes.UPDATE_MODE;

import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_EMPRESAS;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_ASEGURADORAS;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_RAMOS;

import static mx.com.aon.portal.dao.PresiniestrosDAO.CONSULTA_POLIZAS;
import static mx.com.aon.portal.dao.PresiniestrosDAO.CONSULTA_POLIZAS_EXPORT;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_TPREAUTO;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_TPREAUTO_EXPORT;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_TPREBEN;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_TPREBEN_EXPORT;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_TPREBEN_GASFUN;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_TPREBEN_GASFUN_EXPORT;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_TPREDANO;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_TPREDANO_EXPORT;


public class PresiniestrosAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = -8208069327212770972L;

	@SuppressWarnings("unchecked")
	public Map session;

	protected final transient Logger logger = Logger.getLogger(PresiniestrosAction.class);

	private static final String MSGTITLE_ERROR = "1";
	
	private static final String AUTOMOVILES = "automoviles";
	private static final String DANOS = "danos";
	private static final String BENEFICIOS = "beneficios";
	private static final String GASTOS_FUNERARIOS = "gastosfunerarios";

	private static final String TIPORA_DANOS = "1";
	private static final String TIPORA_BENEFICIOS = "2";

	private static final String TIPRAM_AUTOMOVILES = "2";
	private static final String TIPRAM_VIDA = "3";
	private static final String TIPRAM_GASTOS_FUNERARIOS = "9";
	
	private boolean success;

	/*
	 * VARIABLES PARA EXPORTAR RESULTADOS DE LA BUSQUEDA DE PRESINIESTROS
	 */
	private String formato;
	private String contentType;
	private String filename;
	private InputStream inputStream;
	private ExportMediator exportMediator;
	
	/*
	 * VARIABLES PARA PAGINACION
	 */
	private int start = 0;
	private int limit = 20;
	private int totalCount;
	
	private static int idCount = 100;

	/*
	 * Tipo de operacion AGREGAR (I) / EDITAR (U)
	 */
	private String tipoOperacion;
	
	private String mensajeRespuesta;
	private String mensajeAux;
	
	private List<BaseObjectVO> listaEmpresas;
	private List<BaseObjectVO> listaAseguradoras;
	private List<BaseObjectVO> listaRamos;
	
	private Map<String,String> parametrosConsultaPresiniestros;
	private List<PreSiniestroVO> listaConsultaPresiniestros;
	private PreSiniestroVO agregarPresiniestro;
	private PreSiniestroVO consultarPresiniestro;
	private PreSiniestroVO editarPresiniestro;
	private List<PreSiniestroPorPolizaVO> listaPresiniestrosPorPoliza;
	
	private AutomovilVO automovil;
	private BeneficioVO beneficio;
	private DanoVO dano;

	private List<BaseObjectVO> listaTramites;
	private List<BaseObjectVO> listaPadecimientos;
	
	private DocumentoVO documento;
	private List<DocumentoVO> listaDocumentos;

	private PresiniestrosManager presiniestrosManager;
	
	private String cdValoDoc;

	public String test()throws Exception{
		
		logger.debug("PRUEBA ENTRADA SINIESTROS ACTION ");
		
		String hola;
		hola = presiniestrosManager.test();
		logger.debug("PRUEBA SALIDA SINIESTROS ACTION: "+ hola);
		
		return SUCCESS;
	}
	
	/**
	 * Método que obtiene el catálogo de empresas para la pantalla de Consulta de Presiniestros.
	 * @return Objeto String SUCCESS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String obtieneEmpresas() throws Exception {
		
		logger.debug("*** Entrando a método: obtieneEmpresas");
		
		Map<String, String> params = new HashMap<String,String>();

		listaEmpresas = presiniestrosManager.getItemList( params, OBTIENE_EMPRESAS );

		if( listaEmpresas == null )
			listaEmpresas = new ArrayList<BaseObjectVO>();

		success = true;
		return SUCCESS;
	}
	
	/**
	 * Método que obtiene el catálogo de aseguradoras para la pantalla de Consulta de Presiniestros.
	 * @return Objeto String SUCCESS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String obtieneAseguradoras() throws Exception{
		
		logger.debug("*** Entrando a método: obtieneAseguradoras");

		Map<String, String> params = new HashMap<String,String>();
		  
		listaAseguradoras = presiniestrosManager.getItemList( params, OBTIENE_ASEGURADORAS );

		if(listaAseguradoras == null)
			listaAseguradoras = new ArrayList<BaseObjectVO>();

		success = true;
		return SUCCESS;
	}
	
	/**
	 * Método que obtiene el catálogo de ramos para la pantalla de Consulta de Presiniestros.
	 * @return Objeto String SUCCESS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String obtieneRamos() throws Exception{

		logger.debug("*** Entrando a método: obtieneRamos");
		
		Map<String, String> params = new HashMap<String,String>();
		
		listaRamos = presiniestrosManager.getItemList( params, OBTIENE_RAMOS );

		if(listaRamos == null)
			listaRamos = new ArrayList<BaseObjectVO>();

		success = true;
		return SUCCESS;
	}

	/**
	 * Método que busca presiniestros desde la pantalla de Consulta de Presiniestros.
	 * @return Objeto String para utilizar como action response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String buscarPresiniestro() throws Exception{

		logger.debug("*** Entrando a método: buscarPresiniestro");

		Map<String, String> params = new HashMap<String,String>();
		
		try {
			params.put( "pi_CDUNIECO", CDUNIECO );
			params.put( "pi_CDRAMO", parametrosConsultaPresiniestros.get("ramo") );
			params.put( "pi_POLIZA", parametrosConsultaPresiniestros.get("poliza") );
			params.put( "pi_INCISO", parametrosConsultaPresiniestros.get("inciso") );
			params.put( "pi_SUBINCISO", parametrosConsultaPresiniestros.get("subinciso") );
			params.put( "pi_ASEGURADORA", parametrosConsultaPresiniestros.get("aseguradora") );
			params.put( "pi_ASEGURADO", parametrosConsultaPresiniestros.get("asegurado") );
			params.put( "pi_EMPRESA", parametrosConsultaPresiniestros.get("empresa") );
		
			PagedList pagedList = presiniestrosManager.paginaResultado( params, CONSULTA_POLIZAS, start, limit );
			listaConsultaPresiniestros = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();

			if(listaConsultaPresiniestros == null)
				listaConsultaPresiniestros = new ArrayList<PreSiniestroVO>();
			
			success = true;
		
		} catch ( Exception e ){ 
			logger.error("Error = " + e,e);
			success = false;
		}
		
		return SUCCESS;
	}

	/**
	 * Método que exporta la búsqueda de presiniestros desde la pantalla de Consulta de Presiniestros.
	 * @return Objeto String para utilizar como action response
	 * @throws Exception
	 */
	public String exportarBusqueda() throws Exception{

		logger.debug("*** Entrando a método: exportarBusqueda");
		
		Map<String, String> params = new HashMap<String,String>();
		
		try {
			logger.debug( "Formato : " + formato );
			contentType = Util.getContentType(formato);
			logger.debug( "content-type : " + contentType );
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "BusquedaPolizas." + exportFormat.getExtension();

			params.put( "pi_CDUNIECO", CDUNIECO );
			params.put( "pi_CDRAMO", parametrosConsultaPresiniestros.get("ramo") );
			params.put( "pi_POLIZA", parametrosConsultaPresiniestros.get("poliza") );
			params.put( "pi_INCISO", parametrosConsultaPresiniestros.get("inciso") );
			params.put( "pi_SUBINCISO", parametrosConsultaPresiniestros.get("subinciso") );
			params.put( "pi_ASEGURADORA", parametrosConsultaPresiniestros.get("aseguradora") );
			params.put( "pi_ASEGURADO", parametrosConsultaPresiniestros.get("asegurado") );
			params.put( "pi_EMPRESA", parametrosConsultaPresiniestros.get("empresa") );

			String columnas [] = 
				new String[]{	"Póliza",
								"Inciso",
								"Subinciso",
								"Empresa o Corporativo",
								"Aseguradora",
								"Asegurado",
								"Ramo",
								"Incio Vigencia",
								"Fin Vigencia",
								"Prima Total",
								"Forma de Pago",
								"Instrumento de Pago"
			};
			TableModelExport model = presiniestrosManager.obtieneModelo(params, CONSULTA_POLIZAS_EXPORT, columnas);
			inputStream = exportFormat.export(model);
			success = true;
			
		} catch (Exception e) {
			logger.error("Exception en Action Export " + e,e);
			success = false;
		}

		return SUCCESS;
	}
	
	/**
	 * Método que consulta los presiniestros asociados a una póliza.
	 * @return Objeto String para utilizar como action response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String consultarPresiniestro() throws Exception{
		
		logger.debug("*** Entrando a método: consultarPresiniestro");
		
		Map<String, String> params = new HashMap<String,String>();
		PagedList pagedList = new PagedList();

		session.put("consultarPresiniestro", consultarPresiniestro);
		
		if ( consultarPresiniestro.getTipoPresiniestro().equals(TIPORA_DANOS) &&
				consultarPresiniestro.getCdTipRam().equals(TIPRAM_AUTOMOVILES) ) {

			try {
				params.put("p_poliza_i", consultarPresiniestro.getPoliza());
				pagedList = presiniestrosManager.paginaResultado( params, OBTIENE_TPREAUTO, start, limit );
				listaPresiniestrosPorPoliza = pagedList.getItemsRangeList();
				logger.debug("RESULTADO = " + listaPresiniestrosPorPoliza);
			} catch(ApplicationException ae){
				logger.error("!!! Error: " + ae,ae);
				success = false;
			} catch(Exception e) {
				logger.error("!!! Error = " + e,e);
				success = false;
			}

		} else if ( consultarPresiniestro.getTipoPresiniestro().equals(TIPORA_BENEFICIOS) ) {

			try {
				params.put("p_poliza", consultarPresiniestro.getPoliza());
				
				/*PARA EL CASO DE VIDA Y GASTOS FUNERARIOS*/
				if(StringUtils.isNotBlank(consultarPresiniestro.getCdTipRam()) && (consultarPresiniestro.getCdTipRam().equals(TIPRAM_GASTOS_FUNERARIOS) || consultarPresiniestro.getCdTipRam().equals(TIPRAM_VIDA)) ){
					pagedList = presiniestrosManager.paginaResultado( params, OBTIENE_TPREBEN_GASFUN, start, limit );
				}else{
					pagedList = presiniestrosManager.paginaResultado( params, OBTIENE_TPREBEN, start, limit );
				}
				
				listaPresiniestrosPorPoliza = pagedList.getItemsRangeList();
				logger.debug("RESULTADO = " + listaPresiniestrosPorPoliza);
			} catch(ApplicationException ae){
				logger.error("!!! Error: " + ae,ae);
				success = false;
			} catch(Exception e) {
				logger.error("!!! Error = " + e,e);
				success = false;
			}
			
		}  else if ( consultarPresiniestro.getTipoPresiniestro().equals(TIPORA_DANOS) &&
				!consultarPresiniestro.getTipoPresiniestro().equals(TIPRAM_AUTOMOVILES) ) {

			try {
				params.put("p_poliza_i", consultarPresiniestro.getPoliza());
				pagedList = presiniestrosManager.paginaResultado( params, OBTIENE_TPREDANO, start, limit );
				listaPresiniestrosPorPoliza = pagedList.getItemsRangeList();
				logger.debug("RESULTADO = " + listaPresiniestrosPorPoliza);
			} catch(ApplicationException ae) {
				logger.error("!!! Error: " + ae,ae);
				success = false;
			} catch(Exception e) {
				logger.error("!!! Error = " + e,e);
				success = false;
			}
			
		}
		
		totalCount = pagedList.getTotalItems();
		
		if(listaPresiniestrosPorPoliza == null)
			listaPresiniestrosPorPoliza = new ArrayList<PreSiniestroPorPolizaVO>();
		
		success = true;
		return SUCCESS;
	}

	/**
	 * Método que exporta la consulta de presiniestros por póliza desde la pantalla de Consulta de Presiniestros.
	 * @return Objeto String para utilizar como action response
	 * @throws Exception
	 */
	public String exportarConsultarPresiniestro() throws Exception{

		logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		logger.debug("*** Entrando a método: exportarConsultarPresiniestro");
		
		Map<String, String> params = new HashMap<String,String>();
		
		try {
			logger.debug( "Formato : " + formato );
			contentType = Util.getContentType(formato);
			logger.debug( "content-type : " + contentType );
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "ConsultaPresiniestros." + exportFormat.getExtension();

			String columnas [] = 
				new String[]{	"Folio del Presiniestro",
								"Fecha de Registro del Presiniestro"
			};
			
			TableModelExport model = null;
			PreSiniestroVO pvo = (PreSiniestroVO) session.get("consultarPresiniestro");			

			if ( pvo.getTipoPresiniestro().equals(TIPORA_DANOS) &&
					pvo.getCdTipRam().equals(TIPRAM_AUTOMOVILES) ) {

				params.put("p_poliza_i", pvo.getPoliza());
				model = presiniestrosManager.obtieneModelo(params, OBTIENE_TPREAUTO_EXPORT, columnas);

			} else if ( pvo.getTipoPresiniestro().equals(TIPORA_BENEFICIOS) ) {

				params.put("p_poliza", pvo.getPoliza());
				
				if(StringUtils.isNotBlank(pvo.getCdTipRam()) && (pvo.getCdTipRam().equals(TIPRAM_GASTOS_FUNERARIOS) || pvo.getCdTipRam().equals(TIPRAM_VIDA)) ){
					model = presiniestrosManager.obtieneModelo(params, OBTIENE_TPREBEN_GASFUN_EXPORT, columnas);
				}else{
					model = presiniestrosManager.obtieneModelo(params, OBTIENE_TPREBEN_EXPORT, columnas);
				}
				
				
			}  else if ( pvo.getTipoPresiniestro().equals(TIPORA_DANOS) &&
					!pvo.getTipoPresiniestro().equals(TIPRAM_AUTOMOVILES) ) {

				params.put("p_poliza_i", pvo.getPoliza());
				model = presiniestrosManager.obtieneModelo(params, OBTIENE_TPREDANO_EXPORT, columnas);
				
			}
			
			inputStream = exportFormat.export(model);
			success = true;
			
		} catch (Exception e) {
			logger.error("Exception en Action Export " + e,e);
			success = false;
		}

		return SUCCESS;
	}
	
	/**
	 * Método que agrega un presiniestro dependiendo de su ramo asociado.
	 * @return Objeto String para utilizar como action response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String agregarPresiniestro() throws Exception{
		
		tipoOperacion = INSERT_MODE;

		logger.debug("*** Entrando a método: agregarPresiniestro");
		
		// Variable de sesion de la poliza que se agregará un presiniestro
		session.put("DATOS_POLIZA_PRESINIESTRO", agregarPresiniestro);
		
		success = true;
		 
		if ( agregarPresiniestro.getTipoPresiniestro().equals(TIPORA_DANOS) &&
				agregarPresiniestro.getCdTipRam().equals(TIPRAM_AUTOMOVILES) ) {

			try {

				WrapperResultados res = presiniestrosManager.servicePresiniestroAutomovil(agregarPresiniestro, automovil, null);

				if ( res.getMsgTitle().equals(MSGTITLE_ERROR) ) {
					logger.debug(res.getMsg());
					logger.debug(res.getMsgId());
					logger.debug(res.getMsgText());
					logger.debug(res.getMsgTitle());

					mensajeRespuesta = res.getMsgText();
					success = false;
					return SUCCESS;
				}
				if ( res.getItemList().size() > 0 )
					automovil = (AutomovilVO) res.getItemList().get(0);
				else
					automovil = new AutomovilVO();
				logger.debug(">>> Resultado automovil (Método agregarPresiniestro)");
				logger.debug(automovil);
				session.put("AutomovilVO", automovil);

			} catch(ApplicationException ae) {
				logger.error("!!! Error: " + ae,ae);
				success = false;
				return SUCCESS;
			}

			return AUTOMOVILES;

		} else if ( agregarPresiniestro.getTipoPresiniestro().equals(TIPORA_BENEFICIOS) ) {
			try{
				/*PARA EL CASO DE VIDA Y GASTOS FUNERARIOS*/
				if(StringUtils.isNotBlank(agregarPresiniestro.getCdTipRam()) && (agregarPresiniestro.getCdTipRam().equals(TIPRAM_GASTOS_FUNERARIOS) || agregarPresiniestro.getCdTipRam().equals(TIPRAM_VIDA)) ){
					beneficio = presiniestrosManager.obtieneDatosAgregarBeneficioGastosFunerarios(agregarPresiniestro.getNmpoliza(), agregarPresiniestro.getCdramo());
					
					if(logger.isDebugEnabled())logger.debug("BeneficioVO en Vida y Gastos Funerarios = " + beneficio);
					success = true;
					return GASTOS_FUNERARIOS;
				}
				
					beneficio = presiniestrosManager.obtieneDatosAgregarBeneficio(agregarPresiniestro.getNmpoliza(), agregarPresiniestro.getCdramo());
					beneficio.setCdCorporativo(agregarPresiniestro.getCdEmpresaOCorporativo());
					if(logger.isDebugEnabled())logger.debug("BeneficioVO = " + beneficio);
				
			}catch(Exception ae){
				beneficio = new BeneficioVO();
				mensajeRespuesta = ae.getMessage();
				success = false;
				return BENEFICIOS;
			}
				success = true;
				return BENEFICIOS;

		} else if ( agregarPresiniestro.getTipoPresiniestro().equals(TIPORA_DANOS) &&
				!agregarPresiniestro.getCdTipRam().equals(TIPRAM_AUTOMOVILES) ) {

			try {

				WrapperResultados res = presiniestrosManager.servicePresiniestroDano(agregarPresiniestro, dano, null);

				if ( res.getMsgTitle().equals(MSGTITLE_ERROR) ) {
					logger.debug(res.getMsg());
					logger.debug(res.getMsgId());
					logger.debug(res.getMsgText());
					logger.debug(res.getMsgTitle());

					mensajeRespuesta = res.getMsgText();
					success = false;
					return SUCCESS;
				}
				if ( res.getItemList().size() > 0 )
					dano = (DanoVO) res.getItemList().get(0);
				else
					dano = new DanoVO();
				logger.debug(">>> Resultado dano (Método agregarPresiniestro)");
				logger.debug(dano);
				session.put("DanoVO", dano);

			} catch(Exception ae) {
				logger.error("!!! Error: " + ae,ae);
				success = false;
				return DANOS;
			}
			success = true;
			return DANOS;

		}
		
		return SUCCESS;
	}
	
	/**
	 * Método que agrega un presiniestro dependiendo de su ramo asociado.
	 * @return Objeto String para utilizar como action response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String editarPresiniestro() throws Exception{
		
		tipoOperacion = UPDATE_MODE;

		logger.debug("*** Entrando a método: editarPresiniestro");
		
		// Variable de sesion de la poliza que se editará para un presiniestro
		session.put("DATOS_POLIZA_PRESINIESTRO", editarPresiniestro);
		
		success = true;
		 
		if ( editarPresiniestro.getTipoPresiniestro().equals(TIPORA_DANOS) &&
				editarPresiniestro.getCdTipRam().equals(TIPRAM_AUTOMOVILES) ) {

			try {

				WrapperResultados res = presiniestrosManager.obtieneDatosEditarAutomovil(editarPresiniestro);

				if ( res.getMsgTitle().equals(MSGTITLE_ERROR) ) {
					logger.debug(res.getMsg());
					logger.debug(res.getMsgId());
					logger.debug(res.getMsgText());
					logger.debug(res.getMsgTitle());

					mensajeRespuesta = res.getMsgText();
					success = false;
					return SUCCESS;
				}
				if (res.getItemList().size() > 0)
					automovil = (AutomovilVO) res.getItemList().get(0);
				else
					automovil = new AutomovilVO();
				logger.debug(">>> Resultado automovil (Método editarPresiniestro)");
				logger.debug(automovil);
				session.put("AutomovilVO", automovil);

			} catch(ApplicationException ae) {
				logger.error("!!! Error: " + ae,ae);
				success = false;
			}
			return AUTOMOVILES;

		} else if ( editarPresiniestro.getTipoPresiniestro().equals(TIPORA_BENEFICIOS) ) {
			try{
				/*PARA EL CASO DE VIDA Y GASTOS FUNERARIOS*/
				if(StringUtils.isNotBlank(editarPresiniestro.getCdTipRam()) && (editarPresiniestro.getCdTipRam().equals(TIPRAM_GASTOS_FUNERARIOS) || editarPresiniestro.getCdTipRam().equals(TIPRAM_VIDA)) ){
					beneficio = presiniestrosManager.obtienePresiniestroBeneficioGastosFunerarios(editarPresiniestro.getFolio(),editarPresiniestro.getCdramo(), editarPresiniestro.getNmpoliza());
					beneficio.setPoliza(editarPresiniestro.getNmpoliza());
		        	//beneficio.setCdAseguradora(editarPresiniestro.getCdAseguradora());
		        	//beneficio.setEmpresa(editarPresiniestro.getEmpresaOCorporativo());
		        	//beneficio.setCdCorporativo(editarPresiniestro.getCdEmpresaOCorporativo());
		        	beneficio.setDsRamo(editarPresiniestro.getRamo());
		        	
		        	//session.put("DESC_PRE_TMP", beneficio.getDescripcionTramite());
		        	session.put("OBS_PRE_TMP", beneficio.getObservaciones());
		        	
					if(logger.isDebugEnabled())logger.debug("BeneficioVOEdicion en Vida y Gastos Funerarios =  = " + beneficio);
					
					success = true;
					return GASTOS_FUNERARIOS;
				}
				
				beneficio = presiniestrosManager.obtienePresiniestroBeneficio(editarPresiniestro.getFolio(),editarPresiniestro.getCdramo(), editarPresiniestro.getNmpoliza());
				beneficio.setPoliza(editarPresiniestro.getNmpoliza());
	        	beneficio.setCdAseguradora(editarPresiniestro.getCdAseguradora());
	        	beneficio.setEmpresa(editarPresiniestro.getEmpresaOCorporativo());
	        	beneficio.setCdCorporativo(editarPresiniestro.getCdEmpresaOCorporativo());
	        	beneficio.setDsRamo(editarPresiniestro.getRamo());
	        	
	        	session.put("DESC_PRE_TMP", beneficio.getDescripcionTramite());
	        	session.put("OBS_PRE_TMP", beneficio.getObservaciones());
	        	
				if(logger.isDebugEnabled())logger.debug("BeneficioVOEdicion = " + beneficio);
			}catch(Exception ae){
				mensajeRespuesta = ae.getMessage();
				beneficio = new BeneficioVO();
				success = false;
				return BENEFICIOS;
			}
				success = true;
				return BENEFICIOS;
		} else if ( editarPresiniestro.getTipoPresiniestro().equals(TIPORA_DANOS) &&
				!editarPresiniestro.getCdTipRam().equals(TIPRAM_AUTOMOVILES) ) {

			try {

				WrapperResultados res = presiniestrosManager.obtieneDatosEditarDano(editarPresiniestro);

				if ( res.getMsgTitle().equals(MSGTITLE_ERROR) ) {
					logger.debug(res.getMsg());
					logger.debug(res.getMsgId());
					logger.debug(res.getMsgText());
					logger.debug(res.getMsgTitle());

					mensajeRespuesta = res.getMsgText();
					success = false;
					return SUCCESS;
				}
				if (res.getItemList().size() > 0)
					dano = (DanoVO) res.getItemList().get(0);
				else
					dano = new DanoVO();
				logger.debug(">>> Resultado dano (Método editarPresiniestro)");
				logger.debug(dano);
				session.put("DanoVO", dano);

			} catch(Exception ae) {
				logger.error("!!! Error: " + ae,ae);
				success = false;
			}
			return DANOS;
			
		}
		
		return SUCCESS;
	}
	
	/**
	 * Método que guarda un presiniestro de tipo ramo Automoviles.
	 * @return Objeto String para utilizar como action response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String guardarPresiniestroAutomovil() throws Exception {
		
		logger.debug("*** Entrando a guardarPresiniestroAutomovil");
	
		// tipoOperacion será seteado desde JSP
		try {
			AutomovilVO aVO = (AutomovilVO)session.get("AutomovilVO");
			automovil.setFolio( aVO.getFolio() );
			automovil.setFecha( aVO.getFecha() );
			automovil.setPoliza( aVO.getPoliza() );
			automovil.setCertificado( aVO.getCertificado() );
			automovil.setAseguradora( aVO.getAseguradora() );
			automovil.setAsegurado( aVO.getAsegurado() );
			automovil.setTelefono1( aVO.getTelefono1() );
			automovil.setMarca( aVO.getMarca() );
			automovil.setModelo( aVO.getModelo() );
			automovil.setNumeroMotor( aVO.getNumeroMotor() );
			automovil.setNumeroSerie( aVO.getNumeroSerie() );
			automovil.setNumeroPlacas( aVO.getNumeroPlacas() );
			automovil.setColor( aVO.getColor() );
			
			WrapperResultados res = presiniestrosManager.servicePresiniestroAutomovil((PreSiniestroVO)session.get("DATOS_POLIZA_PRESINIESTRO"), automovil, tipoOperacion);

			success = true;
			if ( res.getMsgTitle().equals(MSGTITLE_ERROR) ) {
				logger.debug(res.getMsg());
				logger.debug(res.getMsgId());
				logger.debug(res.getMsgText());
				logger.debug(res.getMsgTitle());

				mensajeRespuesta = res.getMsgText();
				logger.debug("@@@ mensajeRespuesta = " + mensajeRespuesta );
				success = false;
			}

		} catch (Exception e) {
			logger.error("Error en método guardarPresiniestroAutomovil = " + e,e);
			success = false;
		}
		
		return SUCCESS;

	}
	
	/**
	 * Método que guarda un presiniestro de tipo ramo Daños.
	 * @return Objeto String para utilizar como action response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String guardarPresiniestroDano() throws Exception {
		
		logger.debug("*** Entrando a guardarPresiniestroDano");
		
		// tipoOperacion será seteado desde JSP
		try {
			DanoVO dVO = (DanoVO)session.get("DanoVO");
			dano.setFecha( dVO.getFecha() );
			dano.setNombreAsegurado( dVO.getNombreAsegurado() );
			dano.setRamo( dVO.getRamo() );
			dano.setFolio( dVO.getFolio() );
			dano.setPoliza( dVO.getPoliza() );
			dano.setAseguradora( dVO.getAseguradora() );
			dano.setProducto( dVO.getProducto() );
			dano.setInciso( dVO.getInciso() );
			
			WrapperResultados res = presiniestrosManager.servicePresiniestroDano((PreSiniestroVO)session.get("DATOS_POLIZA_PRESINIESTRO"), dano, tipoOperacion);

			success = true;
			if ( res.getMsgTitle().equals(MSGTITLE_ERROR) ) {
				logger.debug(res.getMsg());
				logger.debug(res.getMsgId());
				logger.debug(res.getMsgText());
				logger.debug(res.getMsgTitle());

				mensajeRespuesta = res.getMsgText();
				logger.debug("@@@ mensajeRespuesta = " + mensajeRespuesta );
				success = false;
			}

		} catch (Exception e) {
			logger.error("Error en método guardarPresiniestroDano = " + e,e);
			success = false;
		}
		
		return SUCCESS;
	}
	
	
	/**
	 *  INICIO CODIGO PARA PRESINIESTROS DE BENEFICIOS
	 */
	@SuppressWarnings("unchecked")
	public String obtieneComboTipTramit() throws Exception{

		listaTramites = presiniestrosManager.obtieneListaTramites();

		if(listaTramites == null)
			listaTramites = new ArrayList<BaseObjectVO>();

		success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String obtieneComboPadecimientos() throws Exception{
		listaPadecimientos = presiniestrosManager.obtieneListaPadecimientos();

		if(listaPadecimientos == null)
			listaPadecimientos = new ArrayList<BaseObjectVO>();
		
		success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String guardaPresiniestroBeneficios() throws Exception{
		String numFolio = null;
		if(logger.isDebugEnabled())logger.debug("guardaPresiniestroBeneficios - Datos a guardar: "+ beneficio);
		try{
			numFolio = presiniestrosManager.guardaPresiniestroBeneficios(beneficio, tipoOperacion);
			
			guardaFinalDocumentosPresiniestro(numFolio, tipoOperacion);
			mensajeRespuesta =  "Presiniestro Guardado";
			
			if(StringUtils.isNotBlank(tipoOperacion) && !tipoOperacion.equals(UPDATE_MODE)) mensajeRespuesta +=  (". N&uacute;mero de folio: " + numFolio); 
			/* TODO: if(guardaFinalDocumentosPresiniestro)... else mensajeRespuesta =  "Existió un error al guardar los documentos";*/
			success = true;
		}catch(Exception ae){
			logger.error("Error en el metodo guardaPresiniestroBeneficios "+ae.getMessage(),ae);
			mensajeRespuesta = ae.getMessage();
			success = false;
		}
		
		if(session.containsKey("DOCUMENTOS_PRE")) session.remove("DOCUMENTOS_PRE");
		if(session.containsKey("DOCUMENTOS_PRE_INSERT")) session.remove("DOCUMENTOS_PRE_INSERT");
		if(session.containsKey("DOCUMENTOS_PRE_UPDATE")) session.remove("DOCUMENTOS_PRE_UPDATE");
		if(session.containsKey("DOCUMENTOS_PRE_DELETE")) session.remove("DOCUMENTOS_PRE_DELETE");
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String guardaPresiniestroGastosFunerarios() throws Exception{
		String numFolio = null;
		if(logger.isDebugEnabled())logger.debug("guardaPresiniestroGastosFunerarios - Datos a guardar: "+ beneficio);
		try{
			numFolio = presiniestrosManager.guardaPresiniestroBeneficiosGastosFunerarios(beneficio, tipoOperacion);
			
			guardaFinalDocumentosPresiniestro(numFolio, tipoOperacion);
			mensajeRespuesta =  "Presiniestro Guardado";
			
			if(StringUtils.isNotBlank(tipoOperacion) && !tipoOperacion.equals(UPDATE_MODE)) mensajeRespuesta +=  (". N&uacute;mero de folio: " + numFolio); 
			/* TODO: if(guardaFinalDocumentosPresiniestro)... else mensajeRespuesta =  "Existió un error al guardar los documentos";*/
		}catch(Exception ae){
			logger.error("Error en el metodo guardaPresiniestroGastosFunerarios "+ae.getMessage(),ae);
			mensajeRespuesta = ae.getMessage();
			success = false;
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public boolean guardaFinalDocumentosPresiniestro(String folio, String operacion) throws Exception{
		
		if(logger.isDebugEnabled())logger.debug("guardaFinalDocumentosPresiniestro - folio: "+ folio);
		
		if(!session.containsKey("DOCUMENTOS_PRE_DELETE") || !session.containsKey("DOCUMENTOS_PRE_INSERT") || !session.containsKey("DOCUMENTOS_PRE_UPDATE") ){
			logger.error("Error en el metodo guardaFinalDocumentosPresiniestro,  NO HAY VALORES EN LA SESION!!!");
			return false;
		}
		
		HashMap<String, DocumentoVO> docs = null;
		Map<String, String> params = new HashMap<String,String>();
		PreSiniestroVO presiniestro = null;
		
		if(session.containsKey("DATOS_POLIZA_PRESINIESTRO")) presiniestro = (PreSiniestroVO) session.get("DATOS_POLIZA_PRESINIESTRO");
		if(presiniestro== null)return false;
		
		params.put( "pv_cdunieco", presiniestro.getCdunieco());
		params.put( "pv_cdramo", presiniestro.getCdramo());
		params.put( "pv_estado", presiniestro.getEstado());
		params.put( "pv_nmpoliza", presiniestro.getNmpoliza());
		params.put( "pv_nmsuplem", presiniestro.getNumeroSuplemento());
		
		if(StringUtils.isNotBlank(operacion) && operacion.equals(UPDATE_MODE)) params.put( "pv_nmfolacw", presiniestro.getFolio());
		else params.put( "pv_nmfolacw", folio);
		
		try{
			
			docs = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE_INSERT");
			for(DocumentoVO doc : new ArrayList<DocumentoVO>(docs.values())){
				
				params.put( "pv_cdvalodoc", null);
				
				presiniestrosManager.insertaDocumento(params, doc);
			}
			
			docs = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE_UPDATE");
			for(DocumentoVO doc : new ArrayList<DocumentoVO>(docs.values())){
				
				params.put( "pv_cdvalodoc", doc.getCdValoDoc());
				
				presiniestrosManager.actualizaDocumento(params, doc);
			}
			
			/*EN CASO DE QUE SE HAYA ENTRADO EN AGREGAR PRESINIESTRO, NO TIENE CASO BUSCAR BORRAR EN LA BASE PORQUE NO HAY NADA*/
			if(StringUtils.isNotBlank(operacion) && operacion.equals(UPDATE_MODE)){
				docs = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE_DELETE");
				for(DocumentoVO doc : new ArrayList<DocumentoVO>(docs.values())){
					
					params.put( "pv_cdvalodoc", doc.getCdValoDoc());
					
					presiniestrosManager.eliminaDocumento(params, doc);
				}
			}
		}catch(Exception e){
			logger.error("Error al guardarlos documentos en la base de datos "+e.getMessage(),e );
			return false;
		}
		
		return true;
	}

	@SuppressWarnings("unchecked")
	public String obtieneDescObs() throws Exception{
		if(session.containsKey("DESC_PRE_TMP")){
			mensajeRespuesta = (String) session.get("DESC_PRE_TMP");
			session.remove("DESC_PRE_TMP");
		}else{
			mensajeRespuesta = "";
		}
		if(session.containsKey("OBS_PRE_TMP")){
			mensajeAux = (String) session.get("OBS_PRE_TMP");
			session.remove("OBS_PRE_TMP");
		}else{
			mensajeAux = "";
		}
		
		success = true;
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String obtieneDocumentosPresiniestro() throws Exception{

		if(logger.isDebugEnabled())logger.debug("*** Entrando a método: obtieneDocumentosPresiniestro en modo: "+ tipoOperacion);

		if(StringUtils.isNotBlank(mensajeAux) && mensajeAux.equals("PRIMER_CARGA")){
			idCount = 100;
			logger.debug("PRIMER_CARGA");

			session.put("DOCUMENTOS_PRE", new HashMap<String, DocumentoVO>());
			session.put("DOCUMENTOS_PRE_INSERT",new HashMap<String, DocumentoVO>());
			session.put("DOCUMENTOS_PRE_UPDATE",new HashMap<String, DocumentoVO>());
			session.put("DOCUMENTOS_PRE_DELETE",new HashMap<String, DocumentoVO>());

			if(StringUtils.isNotBlank(tipoOperacion) && tipoOperacion.equals(INSERT_MODE)){
				listaDocumentos = new ArrayList<DocumentoVO>();
				totalCount = 0;
				success = true;
				return SUCCESS;
			}

			try {
				if(session.containsKey("DATOS_POLIZA_PRESINIESTRO")) editarPresiniestro = (PreSiniestroVO) session.get("DATOS_POLIZA_PRESINIESTRO");
				else {
					logger.error("Error en el metodo obtieneDocumentosPresiniestro, NO HAY VALOR DE SESSION PARA DOCUMENTOS_PRE");
					listaDocumentos = new ArrayList<DocumentoVO>();
					totalCount = 0;
					success = false;
					return SUCCESS;
				}
				Map<String, String> params = new HashMap<String,String>();
				params.put( "pv_cdunieco", editarPresiniestro.getCdunieco());
				params.put( "pv_cdramo", editarPresiniestro.getCdramo());
				params.put( "pv_estado", editarPresiniestro.getEstado());
				params.put( "pv_nmpoliza", editarPresiniestro.getNmpoliza());
				params.put( "pv_nmfolacw", editarPresiniestro.getFolio());

				PagedList pagedList = presiniestrosManager.obtieneDocumentosPre( params, start, -1 );
				listaDocumentos = pagedList.getItemsRangeList();
				
				logger.debug("Lista de los documentos obtenidos: "+listaDocumentos);
				//listaDocumentos = obtenerAtributosDoc(listaDocumentos);
				
				totalCount = pagedList.getTotalItems();

				if(listaDocumentos == null)
					listaDocumentos = new ArrayList<DocumentoVO>();

				if(listaDocumentos.size() > 0)listaDocumentos.get(0).setMontoTotal(Double.toString(obtieneMontoTotal(listaDocumentos)));
				
				HashMap<String, DocumentoVO> documentos = new HashMap<String, DocumentoVO>();
				for(DocumentoVO doc : listaDocumentos ){
					documentos.put(doc.getCdValoDoc(), doc);
				}

				session.put("DOCUMENTOS_PRE", documentos);

				success = true;

			} catch ( Exception e ){
				logger.error("Error en el metodo obtieneDocumentosPresiniestro " + e.getMessage(),e);
				success = false;
			}

			return SUCCESS;

		}
		
		if(session.containsKey("DOCUMENTOS_PRE")){
			try{
				HashMap<String, DocumentoVO> docs = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE");
				listaDocumentos = new ArrayList<DocumentoVO>(docs.values());
				
				if(session.containsKey("DOCUMENTOS_PRE_UPDATE")){
					HashMap<String, DocumentoVO> docsEdit = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE_UPDATE");
					listaDocumentos.addAll( new ArrayList<DocumentoVO>(docsEdit.values()));
				}
				
				if(session.containsKey("DOCUMENTOS_PRE_INSERT")){
					HashMap<String, DocumentoVO> docsIns = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE_INSERT");
					listaDocumentos.addAll( new ArrayList<DocumentoVO>(docsIns.values()));
				}
				
				if(listaDocumentos.size() > 0)listaDocumentos.get(0).setMontoTotal(Double.toString(obtieneMontoTotal(listaDocumentos)));
				
				logger.debug("Lista de los documentos obtenidos: "+listaDocumentos);
				totalCount = listaDocumentos.size();
				success = true;
			}catch(Exception e){
				logger.error("ERROR AL REGRESAR LOS DATOS DE listaDocumentos"+e.getMessage(),e);
				success = false;
			}
			
			return SUCCESS;
		}else{
			logger.error("Error en el metodo obtieneDocumentosPresiniestro, NO HAY VALOR DE SESSION PARA DOCUMENTOS_PRE");
			listaDocumentos = new ArrayList<DocumentoVO>();
			totalCount = 0;
			success = false;
			return SUCCESS;
		}

	}
	
	public Double obtieneMontoTotal(List<DocumentoVO> docs){
		Double total = 0.00;
		
		try{
			
			for(DocumentoVO doc: docs){
				total += Double.parseDouble(doc.getAtr2());
			}
			
		}catch(Exception e){
			total = 0.00;
			logger.error("Error al parsear y sumar el Monto Total de los documentos "+e.getMessage(),e);
		}
		
		
		return total;
	}

	@SuppressWarnings("unchecked")
	public List<DocumentoVO> obtenerAtributosDoc(List<DocumentoVO> documentos) throws Exception{
		try {
			Map<String, String> params = new HashMap<String,String>();
			params.put( "pv_cdunieco", editarPresiniestro.getCdunieco());
			params.put( "pv_cdramo", editarPresiniestro.getCdramo());
			params.put( "pv_estado", editarPresiniestro.getEstado());
			params.put( "pv_nmpoliza", editarPresiniestro.getNmpoliza());
			params.put( "pv_nmfolacw", editarPresiniestro.getFolio());

			for(DocumentoVO doc:documentos ){
				doc = presiniestrosManager.obtenerAtributosDocumentoPre(params, doc);	
			}
			
		} catch ( Exception e ){
			logger.error("Error en el metodo obtenerAtributosDoc " + e.getMessage(),e);
			success = false;
		}
		
		return documentos;
	}

	@SuppressWarnings("unchecked")
	public String editaDocumento() throws Exception{

		if(logger.isDebugEnabled())logger.debug("*** Entrando a método: editarDocumento: "+ documento);

		if(!session.containsKey("DOCUMENTOS_PRE") || !session.containsKey("DOCUMENTOS_PRE_INSERT") || !session.containsKey("DOCUMENTOS_PRE_UPDATE") ){
			logger.error("Error en el metodo editarDocumento,  NO HAY VALORES EN LA SESION!!!");
			success = false;
			return SUCCESS;
		}
		
		try{
		HashMap<String, DocumentoVO> docs = null;
		HashMap<String, DocumentoVO> docsInsert = null;
		HashMap<String, DocumentoVO> docsEdit = null;

		if(documento != null){
			cdValoDoc = documento.getCdValoDoc();
			
			if(documento.isNew()){
				
				docsInsert = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE_INSERT");
				docsInsert.put(cdValoDoc, documento);
				session.put("DOCUMENTOS_PRE_INSERT", docsInsert);
				
			}else{
				docs = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE");
				docsEdit = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE_UPDATE");
				
				if(docs.containsKey(cdValoDoc)) docs.remove(cdValoDoc);
				docsEdit.put(cdValoDoc, documento);
				
				session.put("DOCUMENTOS_PRE", docs);
				session.put("DOCUMENTOS_PRE_UPDATE", docsEdit);

			}
			success = true;
			return SUCCESS;

		}else{
			logger.error("Error en el metodo editarDocumento,  parametros nulos!!!");
			success = false;
			return SUCCESS;
		}
		}catch(Exception e){
			logger.error("Error en metodo editarDocumento: "+e.getMessage(),e);
			success = false;
			return SUCCESS;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public String agregarDocumento() throws Exception{

		if(logger.isDebugEnabled())logger.debug("*** Entrando a método: agregarDocumento: "+ documento);

		if(!session.containsKey("DOCUMENTOS_PRE_INSERT")){
			logger.error("Error en el metodo editarDocumento,  NO HAY VALORES EN LA SESION para DOCUMENTOS_PRE_INSERT!!!");
			success = false;
			return SUCCESS;
		}

		HashMap<String, DocumentoVO> docsInsert = null;

		if(documento != null){
			
			documento.setCdValoDoc(Integer.toString(idCount++));
			documento.setNew(true);
			if(logger.isDebugEnabled())logger.debug("nuevo ID generado: "+idCount);;
			
			docsInsert = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE_INSERT");
			docsInsert.put(documento.getCdValoDoc(), documento);
			session.put("DOCUMENTOS_PRE_INSERT", docsInsert);
			
			success = true;
			return SUCCESS;

		}else{
			logger.error("Error en el metodo agregarDocumento,  parametros nulos!!!");
			success = false;
			return SUCCESS;
		}

	}
	
	@SuppressWarnings("unchecked")
	public String eliminaDocumento() throws Exception{
		
		if(logger.isDebugEnabled())logger.debug("*** Entrando a método: eliminaDocumento con id: "+ cdValoDoc);
		
		HashMap<String, DocumentoVO> docs = null;
		HashMap<String, DocumentoVO> docsInsert = null;
		HashMap<String, DocumentoVO> docsEdit = null;
		HashMap<String, DocumentoVO> docsDel = null;
		
		/*tipoOperacion para saber si es un registro nuevo o solo es para update, este viene de la propiedad isNew*/
		if(cdValoDoc != null){
			if(StringUtils.isNotBlank(tipoOperacion) && (tipoOperacion.equals("true"))){
				logger.debug("El documento a eliminar No se encontraba originalmente en la base de datos");
				if(!session.containsKey("DOCUMENTOS_PRE_INSERT")){
					logger.error("Error en el metodo eliminarDocumento,  NO HAY VALORES EN LA SESION para DOCUMENTOS_PRE_INSERT!!!");
					success = false;
					return SUCCESS;
				}else{
					docsInsert = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE_INSERT");
					if(docsInsert.containsKey(cdValoDoc)){
						docsInsert.remove(cdValoDoc);
						session.put("DOCUMENTOS_PRE_INSERT", docsInsert);
						success = true;
						return SUCCESS;
					}else{
						logger.error("Error en el metodo eliminaDocumento,  NO HAY VALORES QUE ELIMINAR!!!");
						success = false;
						return SUCCESS;
					}
				}
				
			}else{
				if(session.containsKey("DOCUMENTOS_PRE")){
					docs = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE");
					docsDel = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE_DELETE");
					
					if(docs.containsKey(cdValoDoc)){
						
						docsDel.put(cdValoDoc, docs.get(cdValoDoc));
						session.put("DOCUMENTOS_PRE_DELETE", docsDel);
						
						docs.remove(cdValoDoc);
						session.put("DOCUMENTOS_PRE", docs);
						
						success = true;
						return SUCCESS;
					}
				}
				if(session.containsKey("DOCUMENTOS_PRE_UPDATE")){
					docsEdit = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE_UPDATE");
					docsDel = (HashMap<String, DocumentoVO>) session.get("DOCUMENTOS_PRE_DELETE");
					
					if(docsEdit.containsKey(cdValoDoc)){
						
						docsDel.put(cdValoDoc, docsEdit.get(cdValoDoc));
						session.put("DOCUMENTOS_PRE_DELETE", docsDel);
						
						docsEdit.remove(cdValoDoc);
						session.put("DOCUMENTOS_PRE_UPDATE", docsEdit);
						
						success = true;
						return SUCCESS;
					}else{
						logger.error("Error en el metodo eliminaDocumento,  NO HAY VALORES QUE ELIMINAR!!!");
						success = false;
						return SUCCESS;
					}
				}else {
					logger.error("Error en el metodo eliminaDocumento,  NO HAY VALORES EN LA SESION para DOCUMENTOS!!!");
					success = false;
					return SUCCESS;
				}
			}
			
		}else{
			logger.error("Error en el metodo eliminaDocumento,  parametros nulos!!!");
			success = false;
			return SUCCESS;
		}
	}
	
	
	
	/**
	 *	FIN CODIGO PARA PRESINIESTROS DE BENEFICIOS
	 */

	//// -->	

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}

	public void setPresiniestrosManager(PresiniestrosManager presiniestrosManager) {
		this.presiniestrosManager = presiniestrosManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<BaseObjectVO> getListaEmpresas() {
		return listaEmpresas;
	}

	public void setListaEmpresas(List<BaseObjectVO> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}

	public List<BaseObjectVO> getListaAseguradoras() {
		return listaAseguradoras;
	}

	public void setListaAseguradoras(List<BaseObjectVO> listaAseguradoras) {
		this.listaAseguradoras = listaAseguradoras;
	}

	public List<BaseObjectVO> getListaRamos() {
		return listaRamos;
	}

	public void setListaRamos(List<BaseObjectVO> listaRamos) {
		this.listaRamos = listaRamos;
	}

	public List<PreSiniestroVO> getListaConsultaPresiniestros() {
		return listaConsultaPresiniestros;
	}

	public void setListaConsultaPresiniestros(
			List<PreSiniestroVO> listaConsultaPresiniestros) {
		this.listaConsultaPresiniestros = listaConsultaPresiniestros;
	}

	public Map<String, String> getParametrosConsultaPresiniestros() {
		return parametrosConsultaPresiniestros;
	}

	public void setParametrosConsultaPresiniestros(
			Map<String, String> parametrosConsultaPresiniestros) {
		this.parametrosConsultaPresiniestros = parametrosConsultaPresiniestros;
	}

	public List<BaseObjectVO> getListaTramites() {
		return listaTramites;
	}

	public void setListaTramites(List<BaseObjectVO> listaTramites) {
		this.listaTramites = listaTramites;
	}

	public List<BaseObjectVO> getListaPadecimientos() {
		return listaPadecimientos;
	}

	public void setListaPadecimientos(List<BaseObjectVO> listaPadecimientos) {
		this.listaPadecimientos = listaPadecimientos;
	}

	public PreSiniestroVO getAgregarPresiniestro() {
		return agregarPresiniestro;
	}

	public void setAgregarPresiniestro(PreSiniestroVO agregarPresiniestro) {
		this.agregarPresiniestro = agregarPresiniestro;
	}

	public AutomovilVO getAutomovil() {
		return automovil;
	}

	public void setAutomovil(AutomovilVO automovil) {
		this.automovil = automovil;
	}

	public List<PreSiniestroPorPolizaVO> getListaPresiniestrosPorPoliza() {
		return listaPresiniestrosPorPoliza;
	}

	public void setListaPresiniestrosPorPoliza(
			List<PreSiniestroPorPolizaVO> listaPresiniestrosPorPoliza) {
		this.listaPresiniestrosPorPoliza = listaPresiniestrosPorPoliza;
	}

	public PreSiniestroVO getEditarPresiniestro() {
		return editarPresiniestro;
	}

	public void setEditarPresiniestro(PreSiniestroVO editarPresiniestro) {
		this.editarPresiniestro = editarPresiniestro;
	}

	public PreSiniestroVO getConsultarPresiniestro() {
		return consultarPresiniestro;
	}

	public void setConsultarPresiniestro(PreSiniestroVO consultarPresiniestro) {
		this.consultarPresiniestro = consultarPresiniestro;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public BeneficioVO getBeneficio() {
		return beneficio;
	}

	public void setBeneficio(BeneficioVO beneficio) {
		this.beneficio = beneficio;
	}

	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}

	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}

	public String getMensajeAux() {
		return mensajeAux;
	}

	public void setMensajeAux(String mensajeAux) {
		this.mensajeAux = mensajeAux;
	}

	public List<DocumentoVO> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<DocumentoVO> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public String getCdValoDoc() {
		return cdValoDoc;
	}

	public void setCdValoDoc(String cdValoDoc) {
		this.cdValoDoc = cdValoDoc;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ExportMediator getExportMediator() {
		return exportMediator;
	}

	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}

	public DanoVO getDano() {
		return dano;
	}

	public void setDano(DanoVO dano) {
		this.dano = dano;
	}
	
	public DocumentoVO getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoVO documento) {
		this.documento = documento;
	}

}
