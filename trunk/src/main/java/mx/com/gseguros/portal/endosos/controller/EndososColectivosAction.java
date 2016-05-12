package mx.com.gseguros.portal.endosos.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cancelacion.service.CancelacionManager;
import mx.com.gseguros.portal.consultas.service.ConsultasManager;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.service.CotizacionManager;
import mx.com.gseguros.portal.endosos.model.RespuestaConfirmacionEndosoVO;
import mx.com.gseguros.portal.endosos.service.EndososManager;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.model.RespuestaVO;
import mx.com.gseguros.portal.general.model.RolVO;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.EstatusTramite;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.general.util.TipoEndoso;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.mesacontrol.service.MesaControlManager;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
@ParentPackage(value="default")
@Namespace("/endosos")
public class EndososColectivosAction extends PrincipalCoreAction
{
	private final static Logger logger = LoggerFactory.getLogger(EndososColectivosAction.class);
	
	private SimpleDateFormat         renderFechas     = new SimpleDateFormat("dd/MM/yyyy");
	private boolean                  success;
	private boolean                  endosoConfirmado;//Para saber si el endoso se ha enviado a la mesa de autorizacion
	private String                   message;
	private Map<String,String>       params;
	private List<Map<String,String>> list;
	private Map<String,Item>         items;
	private String                   mensaje;
	
	private List<Map<String,String>> slist1;
	private List<Map<String,String>> slist2;
	private List<Map<String,Object>> olist1;
	
	private Map<String,String>       smap1;
	private Map<String,String>       smap2;
	private Map<String,String>       smap3;
	private String                   error;
	private Map<String,Item>         imap1;
	private File 					 censo;	

	@Autowired
	private ConsultasManager         consultasManager;
	
	@Autowired
	private EndososManager endososManager;
	private CotizacionDAO cotizacionDAO;
	@Autowired
	private PantallasManager pantallasManager;
	
	@Autowired
	private CotizacionManager cotizacionManager;
	
	@Autowired
	private MesaControlManager mesaControlManager;
	
	@Autowired
	private SiniestrosManager  siniestrosManager;
	
	@Autowired
	private KernelManagerSustituto kernelManager;
	
	public EndososColectivosAction()
	{
		this.session = ActionContext.getContext().getSession();
	}

	@Value("${ruta.documentos.poliza}")
	private String rutaDocumentosPoliza;
	
	@Value("${ruta.servidor.reports}")
	private String rutaServidorReports;
	
	@Value("${pass.servidor.reports}")
	private String passServidorReports;

	@Action(value   = "includes/pantallaEndosoAltaBajaFamilia",
	        results = {
			    @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
                @Result(name="success" , location="/jsp-script/proceso/endosos/endosoFamilia.jsp")
            },
            interceptorRefs = {
                @InterceptorRef(value = "json", params = {"enableSMD", "true", "ignoreSMDMethodInterfaces", "false" })
            }
	)
	public String pantallaEndosoAltaBajaFamilia()
	{
		logger.debug(Utils.log(
				 "\n###########################################"
				,"\n###### pantallaEndosoAltaBajaFamilia ######"
				,"\n###### params=",params
				));
		String result = ERROR;
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(params, "No se recibieron datos");
			
			String cdunieco = params.get("CDUNIECO");
			String cdramo   = params.get("CDRAMO");
			String estado   = params.get("ESTADO");
			String nmpoliza = params.get("NMPOLIZA");
			String tipoflot = params.get("TIPOFLOT");
			String cdtipsup = params.get("cdtipsup");
			if(StringUtils.isBlank(tipoflot))
			{
				tipoflot = "I";
			}
			
			Utils.validate(
					cdunieco  , "No se recibi\u00F3 la sucursal"
					,cdramo   , "No se recibi\u00F3 el producto"
					,estado   , "No se recibi\u00F3 el estado de p\u00F3liza"
					,nmpoliza , "No se recibi\u00F3 el n\u00FCmero de p\u00F3liza"
					,cdtipsup , "No se recibi\u00F3 el c\u00F3digo de endoso"
					);
			
			Map<String,Object> res = endososManager.pantallaEndosoAltaBajaFamilia(
					usuario.getUser()
					,usuario.getRolActivo().getClave()
					,cdunieco
					,cdramo
					,estado
					,nmpoliza
					,tipoflot
					,"FAMILIA"
					,cdtipsup
					,ServletActionContext.getServletContext().getServletContextName()
					);
			items = (Map<String,Item>)res.get("items");
			params.put("nmsuplem_endoso" , (String)res.get("nmsuplem_endoso"));
			params.put("nsuplogi"        , (String)res.get("nsuplogi"));
			
			if(TipoEndoso.ALTA_ASEGURADOS.getCdTipSup().toString().equals(cdtipsup))
			{
				params.put("operacion" , "alta");
			}
			else if(TipoEndoso.BAJA_ASEGURADOS.getCdTipSup().toString().equals(cdtipsup))
			{
				params.put("operacion" , "baja");
			}
			else
			{
				throw new ApplicationException("Tipo de endoso mal definido");
			}
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		return result;
	}
	
	@Action(value   = "recuperarComponentesAltaAsegurado",
			results = { @Result(name="success", type="json") }
			)
	public String recuperarComponentesAltaAsegurado()
	{
		logger.debug(Utils.log(
				 "\n###############################################"
				,"\n###### recuperarComponentesAltaAsegurado ######"
				,"\n###### params=",params
				));
		
		try
		{
			UserVO usuario  = Utils.validateSession(session);
			String cdsisrol = usuario.getRolActivo().getClave();
					
			Utils.validate(params , "No se recibieron datos");
			String cdramo   = params.get("cdramo");
			String cdtipsit = params.get("cdtipsit");
			String depFam   = params.get("depFam");
			Utils.validate(
					cdramo    , "No se recibi\u00F3 el producto"
					,cdtipsit , "No se recibi\u00F3 la clave de situaci\u00F3n"
					,depFam   , "No se recibi\u00F3 el tipo de formulario"
					);
			
			params.putAll(endososManager.recuperarComponentesAltaAsegurado(
					cdramo
					,cdtipsit
					,depFam
					,cdsisrol
					,ServletActionContext.getServletContext().getServletContextName()
					));
			
			success = true;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### recuperarComponentesAltaAsegurado ######"
				,"\n###############################################"
				));
		return SUCCESS;
	}
	
	@Action(value           = "confirmarEndosoFamilias",
			results         = { @Result(name="success", type="json") },
            interceptorRefs = {
			    @InterceptorRef(value = "json", params = {"enableSMD", "true", "ignoreSMDMethodInterfaces", "false" })
			})
	public String confirmarEndosoFamilias()
	{
		logger.debug(Utils.log(
				 "\n#####################################"
				,"\n###### confirmarEndosoFamilias ######"
				,"\n###### params=" , params
				,"\n###### list="   , list
				));
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String cdunieco             = params.get("cdunieco"),
			       cdramo               = params.get("cdramo"),
			       estado               = params.get("estado"),
			       nmpoliza             = params.get("nmpoliza"),
			       cdtipsup             = params.get("cdtipsup"),
			       nmsuplem             = params.get("nmsuplem"),
			       nsuplogi             = params.get("nsuplogi"),
			       fecha                = params.get("fecha"),
			       cdtipsitPrimerInciso = params.get("cdtipsitPrimerInciso"),
			       nmsolici             = params.get("nmsolici");
			
			Utils.validate(
					cdunieco              , "No se recibi\u00F3 la sucursal"
					,cdramo               , "No se recibi\u00F3 el producto"
					,estado               , "No se recibi\u00F3 el estado de p\u00F3liza"
					,nmpoliza             , "No se recibi\u00F3 la p\u00F3liza"
					,cdtipsup             , "No se recibi\u00F3 la clave de endoso"
					,nmsuplem             , "No se recibi\u00F3 el suplemento"
					,nsuplogi             , "No se recibi\u00F3 el consecutivo de endoso"
					,fecha                , "No se recibi\u00F3 la fecha de efecto"
					,cdtipsitPrimerInciso , "No se recibi\u00F3 la modalidad del primer inciso"
					,nmsolici             , "No se recibi\u00F3 la cotizaci\u00F3n"
					);
			
			if(TipoEndoso.ALTA_ASEGURADOS.getCdTipSup() == Integer.parseInt(cdtipsup))
			{
				List<String> incisos = new ArrayList<String>();
				for(Map<String,String> inciso : list)
				{
					incisos.add(inciso.get("nmsituac"));
				}
				
				message = endososManager.confirmarEndosoAltaFamilia(
						usuario.getUser()
						,usuario.getRolActivo().getClave()
						,usuario.getEmpresa().getElementoId()
						,cdunieco
						,cdramo
						,estado
						,nmpoliza
						,cdtipsup
						,nmsuplem
						,nsuplogi
						,Utils.parse(fecha)
						,rutaDocumentosPoliza
						,rutaServidorReports
						,passServidorReports
						,usuario
						,incisos
						,cdtipsitPrimerInciso
						,nmsolici
					);
			}
			else
			{
				List<String> incisos = new ArrayList<String>();
				for(Map<String,String> inciso : list)
				{
					incisos.add(inciso.get("nmsituac"));
				}
				
				message = endososManager.confirmarEndosoBajaFamilia(
						usuario.getUser()
						,usuario.getRolActivo().getClave()
						,usuario.getEmpresa().getElementoId()
						,cdunieco
						,cdramo
						,estado
						,nmpoliza
						,cdtipsup
						,nmsuplem
						,nsuplogi
						,Utils.parse(fecha)
						,rutaDocumentosPoliza
						,rutaServidorReports
						,passServidorReports
						,usuario
						,incisos
						,cdtipsitPrimerInciso
						,nmsolici
						);
			}
			
			success = true;
			
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### message=",message
				,"\n###### confirmarEndosoFamilias ######"
				,"\n#####################################"
				));
		return SUCCESS;
	}
		
	////////////////////////////////
	////// cargaPantallaClonacion //////
	/*////////////////////////////*/
	@Action(value   = "cargaPantallaClonacion",
		    results = {
		        @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
		        @Result(name="success" , location="/jsp-script/proceso/endosos/endosoTramiteClonacion.jsp")
		    })
	public String cargaPantallaClonacion() {
		logger.debug(Utils.log("########################################\n",
							   "########################################\n",
							   "###### cargaInfoClonacionPantalla ######\n",
							   "######                    		  ######\n"));
		logger.debug(Utils.log("smap1: ",smap1,"\n"));
		
		String result = ERROR;
		//this.session = ActionContext.getContext().getSession();

		//RespuestaVO resp = null;

		try {
			imap1 = endososManager.cargaInfoPantallaClonacion();
			result = SUCCESS;
			//new EndososAction().transformaEntrada(smap1, slist1, false);

//			String cdunieco = smap1.get("CDUNIECO");
//			String cdramo = smap1.get("CDRAMO");
//			String cdtipsit = smap1.get("CDTIPSIT");
//			String estado = smap1.get("ESTADO");
//			String nmpoliza = smap1.get("NMPOLIZA");
//			String cdtipsup = TipoEndoso.CANCELACION_POR_REEXPEDICION.getCdTipSup().toString();
//
//			String keyItemsPanelLectura = "itemsPanelLectura";
//			String keyFieldsPanelLectura = "fieldsFormLectura";
//			String keyItemsDatosEndoso = "itemsDatosEndoso";
//
//			UserVO usuario = (UserVO) session.get("USUARIO");
//			String cdelemento = usuario.getEmpresa().getElementoId();
//			String cdusuari = usuario.getUser();
//			String rol = usuario.getRolActivo().getClave();
//
//			// Valida si hay un endoso anterior pendiente:
//			resp = endososManager.validaEndosoAnterior(cdunieco, cdramo, estado, nmpoliza, cdtipsup);
//			error = resp.getMensaje();
//
//			if (resp.isSuccess()) {
//				try {
//					GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
//					imap1 = new HashMap<String, Item>();
//
//					gc.generaParcial(pantallasManager.obtenerComponentes(null, 
//																		 cdunieco, 
//																		 cdramo, 
//																		 cdtipsit, 
//																		 estado, 
//																		 rol,
//																		 "ENDOSO_REEXPEDICION", 
//																		 "PANEL_LECTURA", 
//																		 null));
//
//					imap1.put(keyItemsPanelLectura, gc.getItems());
//					imap1.put(keyFieldsPanelLectura, gc.getFields());
//
//					gc.generaParcial(pantallasManager.obtenerComponentes(null, 
//																		 cdunieco, 
//																		 cdramo, 
//																		 cdtipsit, 
//																		 estado, 
//																		 rol,
//																		 "ENDOSO_REEXPEDICION", 
//																		 "DATOS_ENDOSO", 
//																		 null));
//
//					imap1.put(keyItemsDatosEndoso, gc.getItems());
//				} catch (Exception ex) {
//					logger.error(Utils.log("error al mostrar pantalla endoso reexpedicion", ex));
//					error = ex.getMessage();
//					resp.setSuccess(false);
//				}
//			}
//
//			GeneradorCampos gcTatri = null;
//			GeneradorCampos gcGral = null;
//			List<ComponenteVO> tatrisitOriginal = null;
//
//			// componentes
//			if (resp.isSuccess()) {
//				try {
//					gcTatri = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
//					gcTatri.setCdramo(cdramo);
//					gcTatri.setCdtipsit(cdtipsit);
//
//					gcGral = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
//					gcGral.setCdramo(cdramo);
//
//					// TATRISIT ORIGINAL
//					tatrisitOriginal = cotizacionDAO.cargarTatrisit(cdtipsit, cdusuari);
//
//					// columnas base
//					List<ComponenteVO> tatrisitColsBase = new ArrayList<ComponenteVO>();
//					for (ComponenteVO iTatri : tatrisitOriginal) {
//						if (StringUtils.isNotBlank(iTatri.getSwsuscri()) && iTatri.getSwsuscri().equals("N")
//								&& iTatri.getSwGrupo().equals("S") && iTatri.getSwGrupoLinea().equals("N")) {
//							logger.debug(new StringBuilder("SE AGREGA PARA COLUMNA BASE ").append(iTatri).toString());
//							iTatri.setColumna("S");
//							tatrisitColsBase.add(iTatri);
//						}
//					}
//
//					if (tatrisitColsBase.size() > 0) {
//						gcTatri.generaComponentes(tatrisitColsBase, true, true, false, true, true, false);
//						imap1.put("colsBaseFields", gcTatri.getFields());
//						imap1.put("colsBaseColumns", gcTatri.getColumns());
//					} else {
//						imap1.put("colsBaseFields", null);
//						imap1.put("colsBaseColumns", null);
//					}
//					// columnas base
//
//					// columnas extendidas (de coberturas)
//					List<ComponenteVO> tatrisitColsCober = new ArrayList<ComponenteVO>();
//					for (ComponenteVO iTatri : tatrisitOriginal) {
//						if (StringUtils.isNotBlank(iTatri.getSwsuscri()) && iTatri.getSwsuscri().equals("N")
//								&& iTatri.getSwGrupo().equals("S")) {
//							logger.debug(Utils.log("SE AGREGA PARA COLUMNA DE COBERTURA ",iTatri,"\n"));
//							iTatri.setColumna("S");
//							tatrisitColsCober.add(iTatri);
//						}
//					}
//					if (tatrisitColsCober.size() > 0) {
//						gcTatri.generaComponentes(tatrisitColsCober, true, true, false, true, true, false);
//						imap1.put("colsExtFields", gcTatri.getFields());
//						imap1.put("colsExtColumns", gcTatri.getColumns());
//					} else {
//						imap1.put("colsExtFields", null);
//						imap1.put("colsExtColumns", null);
//					}
//					// columnas extendidas (de coberturas)
//
//					// factores
//					List<ComponenteVO> factores = new ArrayList<ComponenteVO>();
//					for (ComponenteVO iTatri : tatrisitOriginal) {
//						if (StringUtils.isNotBlank(iTatri.getSwsuscri()) && iTatri.getSwsuscri().equals("N")
//								&& iTatri.getSwGrupo().equals("N") && iTatri.getSwGrupoFact().equals("S")) {
//							logger.debug(Utils.log("SE AGREGA PARA FACTOR ",iTatri,"\n"));
//							iTatri.setColumna("S");
//							factores.add(iTatri);
//							iTatri.setMenorCero(true);
//						}
//					}
//
//					List<ComponenteVO> columnaEditorPlan = pantallasManager.obtenerComponentes(null, 
//																							   null, 
//																							   null, 
//																							   null,
//																							   null, 
//																							   null, 
//																							   "COTIZACION_GRUPO", 
//																							   "EDITOR_PLANES_REEXP", 
//																							   null);
//					gcGral.generaComponentes(columnaEditorPlan, true, false, false, true, true, false);
//					imap1.put("editorPlanesColumn", gcGral.getColumns());
//
//				} catch (Exception ex) {
//					logger.error(Utils.log("Error al obtener componentes", ex,"\n"));
//				}
//			}
			// componentes
		} catch (Exception ex) {
			logger.error(Utils.log("Error al construir pantalla de endoso de cancelacion por reexpedicion", ex,"\n"));
			error = Utils.manejaExcepcion(ex);
		}

		logger.debug(Utils.log("######                    		  ######\n",
							   "###### cargaInfoClonacionPantalla ######\n",
							   "########################################\n",
							   "########################################\n"));

//		return resp != null && resp.isSuccess() ? SUCCESS : ERROR;
		return result;
	}
	/*////////////////////////////*/
	////// cargaPantallaClonacion //////
	////////////////////////////////
	
	////////////////////////////////
	////// buscarTramites	  //////
	////// smap1:             //////
	//////     pv_cdunieco_i  //////
	//////     pv_ntramite_i  //////
	//////     pv_cdramo_i    //////
	//////     pv_nmpoliza_i  //////
	//////     pv_estado_i    //////
	//////     pv_cdagente_i  //////
	//////     pv_status_i    //////
	//////     pv_cdtipsit_i  //////
	//////     pv_fedesde_i   //////
	//////     pv_fehasta_i   //////
	//////     pv_cdtiptra_i  //////
	/*////////////////////////////*/
	@Action(value   = "buscarTramites",
			results = { @Result(name="success", type="json") }
			)
	public String buscarTramites()
	{
		logger.debug(""
				+ "\n##################################################"
				+ "\n##################################################"
				+ "\n###### clonacion de tramites buscarTramites ######"
				+ "\n######                                    	 ######"
				);
		logger.debug("smap1: "+smap1);
		try
		{
			UserVO usu=(UserVO) session.get("USUARIO");
			String cdunieco = smap1.get("pv_cdunieco_i");
			String cdramo 	= smap1.get("pv_cdramo_i");
			String cdtipsit = smap1.get("pv_cdtipsit_i");
			String estado   = smap1.get("pv_estado_i");
			String nmpoliza = smap1.get("pv_nmpoliza_i");
			String ntramite = smap1.get("pv_ntramite_i");
			String status   = smap1.get("pv_status_i");
			String fecini   = smap1.get("pv_fedesde_i");
			String fecfin   = smap1.get("pv_fehasta_i");
			String cdsisrol = usu.getRolActivo().getClave();
			String cdusuari = usu.getUser();
			slist1 = endososManager.buscarCotizaciones(cdunieco, cdramo, cdtipsit, estado, nmpoliza, ntramite, status, fecini, fecfin, cdsisrol, cdusuari);
			success=true;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		logger.debug(""
				+ "\n######                                      ######"
				+ "\n###### clonacion de tramites buscarTramites ######"
				+ "\n##################################################"
				+ "\n##################################################"
				);
		return SUCCESS;
	}
	/*////////////////////////////*/
	////// buscarTramites	  //////
	////////////////////////////////
	
	///////////////////////////////////////
	////// guardarEndosoReexpedicion //////
	
	/*//////////////////////////////////////*/
	@Action(value   = "generarCopiaCompleta",
			results = { @Result(name="success", type="json") }
			)
	public String generarCopiaCompleta() {
		logger.debug(Utils.log("##########################\n", 
				  			   "##########################\n",
				  			   "###### generarCopia ######\n",
				  			   "######              ######\n"));		

		this.session = ActionContext.getContext().getSession();
        ArrayList<String> rolesAsignacion = new ArrayList<String>(Arrays.asList("COTIZADOR","SUPTECSALUD","SUBDIRSALUD","DIRECSALUD"));
        ArrayList<String> rolesAsignar    = new ArrayList<String>(Arrays.asList("EJECUTIVOCUENTA","EJECUTIVOINTERNO","MESADECONTROL","COTIZADOR"));
		try {
            String cdunieco           = params.get("cdunieco");
            String cdramo	          = params.get("cdramo");
            String cdtipsit           = params.get("cdtipsit");
            String estado             = params.get("estado");
            String nmpoliza           = params.get("nmpoliza");
            String nmsolici           = params.get("nmsolici");
            String ntramite           = params.get("ntramite");
            String status             = params.get("status");
            String ferecepc	          = params.get("ferecepc");
            String fecstatus          = params.get("fecstatus");
            String usuarioTramite     = "";
            UserVO usuario            = (UserVO) session.get("USUARIO");
			String cdusuari           = usuario.getUser();
			String cdtipsup           = TipoEndoso.EMISION_POLIZA.getCdTipSup().toString();
			long timestamp            = System.currentTimeMillis();
			boolean esProductoSalud   = consultasManager.esProductoSalud(cdramo);
			boolean esPolizaColectiva = ("F".equalsIgnoreCase(params.get("TIPOFLOT")));// flotilla o Colectiva
			logger.debug(Utils.log("usuario en sesion ",usuario.getRolActivo().getClave()));
            logger.debug(Utils.log("######		parametros		######\n", 
					  			   "##################################\n",
					  			   "cdunieco ", cdunieco, "\n",
					  	           "cdramo "  , cdramo,   "\n",
					  	           "cdtipsit ", cdtipsit, "\n",
					  	           "estado "  , estado,   "\n",
					  	           "nmpoliza ", nmpoliza, "\n",
					  	           "nmsolici ", nmsolici, "\n",
					  	           "ntramite ", ntramite, "\n",
					  	           "status "  , status,   "\n",
					  	           "ferecepc" , ferecepc, "\n",
					  	           "fecstatus", fecstatus,"\n",
					  	           "tipoflot" , params.get("TIPOFLOT")
					  	           ));

            logger.debug(Utils.log("esProductoSalud && esPolizaColectiva",esProductoSalud,"",esPolizaColectiva));
            if (esProductoSalud && esPolizaColectiva) {
					// P_CLONAR_POLIZA_REEXPED            		
					Map<String, String> resReexped = endososManager.pClonarCotizacionTotal(cdunieco,
																						 	 cdramo,
																						 	 estado,
																						 	 nmpoliza,
																						 	 ferecepc,
																						 	 cdusuari,
																						 	 cdunieco,
																						 	 "TODO");					
					String nmpolizaNuevaPoliza = resReexped.get("pv_nmpolnew_o");
					String ntramiteNuevaPoliza = resReexped.get("pv_ntramite_o");
            		params.put("nmpoliza", nmpolizaNuevaPoliza);
            		params.put("ntramite", ntramiteNuevaPoliza);
            		boolean exitoGrupos = endososManager.actualizaTodosGruposReexp(cdunieco, 
																				   cdramo, 
																				   estado,
																				   nmpolizaNuevaPoliza);
            		logger.debug(Utils.log("paso clonar grupos ",nmpolizaNuevaPoliza, "|",ntramiteNuevaPoliza));            		
					if (exitoGrupos) {
					exitoGrupos = endososManager.valoresDefectoGruposCotizacion(cdunieco, 
																			    cdramo, 
																			    "W",
																			    nmpolizaNuevaPoliza, 
																			    "0", 
																			    cdtipsup);
					
						if (exitoGrupos) {
							logger.debug(Utils.log("exito en grupos"));
							cotizacionManager.ejecutasigsvdefEnd(cdunieco, 
																 cdramo, 
																 "W", 
																 nmpolizaNuevaPoliza, 
																 "0",
																 "0", 
																 "TODO", 
																 cdtipsup);
						}
					}

				
				if (rolesAsignar.contains(usuario.getRolActivo().getClave())){
					Map<String, Object> res = siniestrosManager.moverTramite(ntramiteNuevaPoliza,
																			 EstatusTramite.EN_ESPERA_DE_COTIZACION.getCodigo(),
																			 "Se Clona Cotizacion del tramite original: " + ntramiteNuevaPoliza, 
																			 usuario.getUser(),
																			 usuario.getRolActivo().getClave(), 
																			 null, 
																			 RolSistema.SUSCRIPTOR_TECNICO.getCdsisrol(), 
																			 null,
																			 null, 
																			 "N", 
																			 timestamp);
					if (res.containsKey("NOMBRE") && StringUtils.isNotBlank((String) res.get("NOMBRE"))) {
						usuarioTramite = " fue asignado a: " + (String) res.get("NOMBRE");
					}
					logger.debug(Utils.log("*****************Resultado de asignacion******************"));
					logger.debug(Utils.log(res));
					setMensaje("El tr&aacute;mite "
								+ ntramiteNuevaPoliza
								+ usuarioTramite);
            		params.put("redireccion", "N");
				}
				else if(rolesAsignacion.contains(usuario.getRolActivo().getClave())){
					Map<String, Object> res = siniestrosManager.moverTramite(ntramiteNuevaPoliza,
																			 EstatusTramite.EN_ESPERA_DE_COTIZACION.getCodigo(),
																			 "Se Clona Cotizacion del tramite original: " + ntramiteNuevaPoliza, 
																			 usuario.getUser(),
																			 usuario.getRolActivo().getClave(), 
																			 usuario.getUser(),
																			 usuario.getRolActivo().getClave(),
																			 null,
																			 null, 
																			 "N", 
																			 timestamp);
					logger.debug(Utils.log("*****************Resultado de asignacion******************"));
					logger.debug(Utils.log(res));
					setMensaje("Se genero el tr&aacute;mite "
								+ ntramiteNuevaPoliza);
            		params.put("redireccion", "S");
				}
	         }
            

						
//			String cdunieco = smap1.get("CDUNIECO");
//			String newcdunieco = smap2.get("NEWCDUNIECO");
//			String cdramo = smap1.get("CDRAMO");
//			String estado = smap1.get("ESTADO");
//			String nmpoliza = smap1.get("NMPOLIZA");
//			String sFecha = smap2.get("fecha_endoso");
//			Date dFecha = renderFechas.parse(sFecha);

			
//			String cdtipsit = smap1.get("CDTIPSIT");
//			String ntramite = smap1.get("NTRAMITE");
//			String feIniVig = smap3.get("feefecto");
//			String feFinvig = smap3.get("feproren");
//			String cdrazonReexp = smap2.get("trazreexped");
//			String comentaReexp = smap2.get("comment");
//			String cdplan = smap2.get("cdplan");
//
//			String usuarioTramite = "";
//
//			boolean esProductoSalud = consultasManager.esProductoSalud(cdramo);
//			boolean esPolizaColectiva = (StringUtils.isNotBlank(smap1.get("TIPOFLOT")) && 
//										 "F".equalsIgnoreCase(smap1.get("TIPOFLOT")));// flotilla o Colectiva
//
//			boolean actualizarGrupos = (slist1 != null && !slist1.isEmpty());
//			boolean clonarGrupos = (slist2 != null && !slist2.isEmpty());

			// PKG_ENDOSOS.P_ENDOSO_INICIA
//			Map<String, String> resIniEnd = endososManager.iniciarEndoso(cdunieco, 
//																		 cdramo, 
//																		 estado, 
//																		 nmpoliza, 
//																		 sFecha,
//																		 cdelemento, 
//																		 cdusuari, 
//																		 proceso, 
//																		 cdtipsup);

//			String nmsuplem = resIniEnd.get("pv_nmsuplem_o");
//			String nsuplogi = resIniEnd.get("pv_nsuplogi_o");

//			if (StringUtils.isBlank(newcdunieco)) {
//				newcdunieco = cdunieco;
//			}

			// P_CLONAR_POLIZA_REEXPED
//			Map<String, String> resReexped = endososManager.pClonarPolizaReexped(cdunieco, 
//																				 cdramo, 
//																				 estado, 
//																				 nmpoliza,
//																				 sFecha, 
//																				 cdplan, 
//																				 cdusuari, 
//																				 newcdunieco);
//			String nmpolizaNuevaPoliza = resReexped.get("pv_nmpolnew_o");
//			String ntramiteNuevaPoliza = resReexped.get("pv_ntramite_o");

			/**
			 * Si se actializaron planes y suma asegurada de grupos
			 */
//			if (esProductoSalud && esPolizaColectiva && (actualizarGrupos || clonarGrupos)) {

				// Se clonan tablas para tarificacion de grupos que no cambian
				// valores
//				if (clonarGrupos) {
//					boolean exitoGrupos = endososManager.clonaGruposReexp(cdunieco, 
//																		  cdramo, 
//																		  estado, 
//																		  nmpoliza,
//																		  newcdunieco, 
//																		  nmpolizaNuevaPoliza, 
//																		  slist2);
//
//					if (!exitoGrupos) {
//						long timestamp = System.currentTimeMillis();
//
//						error = "Error al Clonar Grupos para endoso de Reexpedicion" + timestamp;
//						logger.error(error);
//						success = false;
//						return SUCCESS;
//					}
//				}

				// Se actualizan valores de tarificacion por grupo que haya
				// cambiado y disparan valores por defecto
//				if (actualizarGrupos) {
//					boolean exitoGrupos = endososManager.actualizaGruposReexp(newcdunieco, 
//																			  cdramo, 
//																			  "W",
//																			  nmpolizaNuevaPoliza, 
//																			  slist1);
//
//					if (exitoGrupos) {
//						exitoGrupos = endososManager.valoresDefectoGruposReexp(newcdunieco, 
//																			   cdramo, 
//																			   "W",
//																			   nmpolizaNuevaPoliza, 
//																			   "0", 
//																			   cdtipsup, 
//																			   slist1);
//
//						if (exitoGrupos) {
//							cotizacionManager.ejecutasigsvdefEnd(newcdunieco, 
//																 cdramo, 
//																 "W", 
//																 nmpolizaNuevaPoliza, 
//																 "0",
//																 "0", 
//																 "TODO", 
//																 cdtipsup);
//						}
//					}
//
//					if (!exitoGrupos) {
//						long timestamp = System.currentTimeMillis();
//						error = "Error al Actualizar Grupos para endoso de Reexpedicion" + timestamp;
//						logger.error(error);
//						success = false;
//						return SUCCESS;
//					}
//				}
//
//			}

			// pkg_cancela.p_selecciona_poliza_unica
//			cancelacionManager.seleccionaPolizaUnica(cdunieco, 
//													 cdramo, 
//													 nmpoliza, 
//													 null, 
//													 dFecha);

			// pkg_cancela.p_cancela_poliza
//			String nmsuplemCancela = cancelacionManager.cancelaPoliza(cdunieco, 
//																	  cdramo, 
//																	  null, 
//																	  estado, 
//																	  nmpoliza, 
//																	  null,
//																	  cdrazonReexp, 
//																	  comentaReexp, 
//																	  feIniVig, 
//																	  feFinvig, 
//																	  sFecha, 
//																	  cdusuari, 
//																	  cdtipsup,
//																	  usuario.getRolActivo().getClave());

			// Se confirma el endoso si cumple la validacion de fechas:
//			RespuestaConfirmacionEndosoVO respConfirmacionEndoso = this.confirmarEndoso(cdunieco, 
//																						cdramo, 
//																						estado,
//																						nmpoliza, 
//																						nmsuplemCancela, 
//																						nsuplogi, 
//																						cdtipsup, 
//																						comentaReexp, 
//																						dFecha, 
//																						cdtipsit);
//
//			consultasManager.copiarArchivosUsuarioTramite(cdunieco, 
//														  cdramo, 
//														  estado, 
//														  nmpoliza, 
//														  ntramiteNuevaPoliza,
//														  this.getText("ruta.documentos.poliza"));

			/**
			 * Para cambiar el estatus del tramite nuevo
			 */
//			if (esProductoSalud && esPolizaColectiva) {
//				long timestamp = System.currentTimeMillis();

				/**
				 * Si se hicieron cambios a los gruos se manda a estatus
				 * cotizador, si no se realizaron cambios se manda a estatus
				 * carga completa
				 */
//				if (actualizarGrupos) {
//					Map<String, Object> res = siniestrosManager.moverTramite(ntramiteNuevaPoliza,
//																			 EstatusTramite.EN_ESPERA_DE_COTIZACION.getCodigo(),
//																			 "Se Reexpide Poliza del tramite original: " + ntramite, 
//																			 usuario.getUser(),
//																			 usuario.getRolActivo().getClave(), 
//																			 null, 
//																			 RolSistema.SUSCRIPTOR_TECNICO.getCdsisrol(), 
//																			 null,
//																			 null, 
//																			 "N", 
//																			 timestamp);
//
//					if (res.containsKey("NOMBRE") && StringUtils.isNotBlank((String) res.get("NOMBRE"))) {
//						usuarioTramite = " asignado a: " + (String) res.get("NOMBRE");
//					}
//
//					usuarioTramite += " en suscripci\u00F3n t&eacute;cnica para su recotizaci&oacute;n";
//				} else {
//					Map<String, Object> res = siniestrosManager.moverTramite(ntramiteNuevaPoliza,
//																			 EstatusTramite.TRAMITE_COMPLETO.getCodigo(),
//																			 "Se Reexpide Poliza del tramite original: " + ntramite, 
//																			 usuario.getUser(),
//																			 usuario.getRolActivo().getClave(), 
//																			 null, 
//																			 RolSistema.SUSCRIPTOR.getCdsisrol(), 
//																			 null, 
//																			 null,
//																			 "N", 
//																			 timestamp);
//
//					if (res.containsKey("NOMBRE") && StringUtils.isNotBlank((String) res.get("NOMBRE"))) {
//						usuarioTramite = " asignado a: " + (String) res.get("NOMBRE");
//					}
//
//					usuarioTramite += " en suscripci\u00F3n de emisi&oacute;n para su reexpedici&oacute;n";
//
//				}
//			}

			// Si el endoso fue confirmado:
//			if (respConfirmacionEndoso.isConfirmado()) {
//				endosoConfirmado = true;
//
//				List<Map<String, String>> listaDocu = cancelacionManager.reimprimeDocumentos(cdunieco, 
//																							 cdramo, 
//																							 estado,
//																							 nmpoliza, 
//																							 cdtipsup);
//				logger.debug("documentos que se regeneran: " + listaDocu);
//
//				String rutaCarpeta = this.getText("ruta.documentos.poliza") + "/" + ntramite;

				// listaDocu contiene: nmsolici,nmsituac,descripc,descripl
//				for (Map<String, String> docu : listaDocu) {
//					logger.debug(Utils.log("docu iterado: ",docu,"\n"));
//					String nmsolici = docu.get("nmsolici");
//					String descripc = docu.get("descripc");
//					String descripl = docu.get("descripl");
//					String url = this.getText("ruta.servidor.reports") + 
//								 "?destype=cache" + "&desformat=PDF"+ 
//								 "&userid=" + 
//								 this.getText("pass.servidor.reports") + 
//								 "&report=" + 
//								 descripl + 
//								 "&paramform=no" + 
//								 "&ACCESSIBLE=YES" +// parametro que habilita salida en PDF
//								 "&p_unieco=" + cdunieco + 
//								 "&p_ramo=" + cdramo + 
//								 "&p_estado=" + estado + 
//								 "&p_poliza=" + nmpoliza + 
//								 "&p_suplem=" + nmsuplem + 
//								 "&desname=" + rutaCarpeta + "/" + descripc;
//					if (descripc.substring(0, 6).equalsIgnoreCase("CREDEN")) {
						// C R E D E N C I A L _ X X X X X X . P D F
						// 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
//						url += "&p_cdperson=" + descripc.substring(11, descripc.lastIndexOf("_"));
//					}
//					logger.debug(Utils.log("#################################\n",
//								 		   "###### Se solicita reporte ######\n", 
//								 		   url,"\n",
//								 		   "#################################\n"));
//					HttpUtil.generaArchivo(url, rutaCarpeta + "/" + descripc);
//					logger.debug(Utils.log("######                    ######\n", 
//								 		   "###### reporte solicitado ######\n",
//								 		   url,"\n",
//								 		   "################################\n",
//								 		   "################################\n"));
//				}

//				setMensaje("Se ha generado la p&oacute;liza " + nmpolizaNuevaPoliza
//						+ " con n&uacute;mero de tr&aacute;mite " + ntramiteNuevaPoliza + usuarioTramite);
//
//				String sucursal = cdunieco;

				// Ejecutamos el Web Service de Recibos:
//				ice2sigsService.ejecutaWSrecibos(cdunieco, cdramo, estado, nmpoliza, nmsuplemCancela, null, sucursal,
//						"", ntramite, true, cdtipsup, (UserVO) session.get("USUARIO"));

//			} else {
//				setMensaje("El endoso " + nsuplogi + " se guard&oacute; en mesa de control para autorizaci&oacute;n "
//						+ "con n&uacute;mero de tr&aacute;mite " + respConfirmacionEndoso.getNumeroTramite() + ". "
//						+ "La p&oacute;liza reexpedida es " + nmpolizaNuevaPoliza + " con tr&aacute;mite "
//						+ "de emisi&oacute;n " + ntramiteNuevaPoliza);
//			}
			
            success = true;

		} catch (Exception ex) {
			error = ex.getMessage();
			success = false;			
			logger.error("error al guardar endoso de reexpedicion", ex);
		}
		logger.debug(Utils.log("######                           ######\n",
							   "###### guardarEndosoReexpedicion ######\n",
							   "#######################################\n",
							   "#######################################\n"));
		return SUCCESS;
	}
	/*///////////////////////////////////*/
	////// guardarEndosoReexpedicion //////
	///////////////////////////////////////
	
	@Action(value   = "clonarPolizaCenso",
			results = { @Result(name="success", type="json") }
			)
	public String clonarPolizaCenso() {
		logger.debug(Utils.log("###############################\n", 
				  			   "###############################\n",
				  			   "###### clonarPolizaCenso ######\n",
				  			   "######                   ######\n"));

//		this.session = ActionContext.getContext().getSession();
//		ArrayList<String> rolesAsignar    = new ArrayList<String>(Arrays.asList("EJECUTIVO DE VENTAS","MESA DE CONTROL","EMISIÓN SALUD","SUSCRIPTOR EMISION SALUD"));
//		ArrayList<String> rolesAsignacion = new ArrayList<String>(Arrays.asList("SUSCRIPTOR TÉCNICO SALUD","SUPERVISOR TÉCNICO SALUD","SUBDIRECTOR TECNICO SALUD","DIRECTOR OPERACIONES SALUD"));
//		try {
//			String cdunieco           = params.get("cdunieco");
//			String cdramo	          = params.get("cdramo");
//			String cdtipsit           = params.get("cdtipsit");
//			String estado             = params.get("estado");
//			String nmpoliza           = params.get("nmpoliza");
//			String nmsolici           = params.get("nmsolici");
//			String ntramite           = params.get("ntramite");
//			String status             = params.get("status");
//			String ferecepc	          = params.get("ferecepc");
//			String fecstatus          = params.get("fecstatus");
//			String usuarioTramite     = "";
//			UserVO usuario            = (UserVO) session.get("USUARIO");
//			String cdusuari           = usuario.getUser();
//			String cdelemento         = usuario.getEmpresa().getElementoId();
//			String proceso            = "END";
//			String cdtipsup           = TipoEndoso.EMISION_POLIZA.getCdTipSup().toString();
//			long timestamp            = System.currentTimeMillis();
//			boolean esProductoSalud   = consultasManager.esProductoSalud(cdramo);
//			boolean esPolizaColectiva = ("F".equalsIgnoreCase(params.get("TIPOFLOT")));// flotilla o Colectiva
//			logger.debug(Utils.log("######		parametros		######\n", 
//					  			   "##################################\n",
//					  			   "cdunieco ", cdunieco, "\n",
//					  	           "cdramo "  , cdramo,   "\n",
//					  	           "cdtipsit ", cdtipsit, "\n",
//					  	           "estado "  , estado,   "\n",
//					  	           "nmpoliza ", nmpoliza, "\n",
//					  	           "nmsolici ", nmsolici, "\n",
//					  	           "ntramite ", ntramite, "\n",
//					  	           "status "  , status,   "\n",
//					  	           "ferecepc" , ferecepc, "\n",
//					  	           "fecstatus", fecstatus,"\n"
//					  	           ));
//			if (esProductoSalud && esPolizaColectiva) {
//					// P_CLONAR_POLIZA_REEXPED
//					Map<String, String> resReexped = endososManager.pClonarCotizacionReexped(cdunieco, 
//																						 	 cdramo, 
//																						 	 estado, 
//																						 	 nmpoliza,
//																						 	 ferecepc, 
//																						 	 cdusuari, 
//																						 	 null);
//					String nmpolizaNuevaPoliza = resReexped.get("pv_nmpolnew_o");
//					String ntramiteNuevaPoliza = resReexped.get("pv_ntramite_o");
//					boolean exitoGrupos = endososManager.actualizaTodosGruposReexp(cdunieco, 
//																			  	   cdramo, 
//																			  	   "W",
//																			  	   nmpolizaNuevaPoliza);
//				}
			success = true;
//			
//		} catch (Exception ex) {
//			error = ex.getMessage();
//			success = false;
//			logger.error(Utils.log("error al clonar tramite", ex,"\n"));
//		}
		logger.debug(Utils.log("######                   ######\n",
							   "###### clonarPolizaCenso ######\n",
							   "###############################\n",
							   "###############################\n"));
		return SUCCESS;
	}
	
	//////////////////////////////
	////// confirmar endoso //////
	/*//////////////////////////*/
	/**
	 * Confirma un endoso si no excede un maximo de dias permitidos (+-30 dias) 
	 * //+- 30 dias ? PKG_SATELITES.P_MOV_MESACONTROL : PKG_ENDOSOS.P_CONFIRMAR_ENDOSOB
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param nsuplogi
	 * @param cdtipsup
	 * @param dscoment
	 * @param fechaEndoso
	 * @param cdtipsit
	 * @return Respuesta de la confirmacion del Endoso
	 * @throws Exception
	 */
	private RespuestaConfirmacionEndosoVO confirmarEndoso(String cdunieco,String cdramo,String estado,String nmpoliza,
			String nmsuplem, String nsuplogi, String cdtipsup, String dscoment, Date fechaEndoso, String cdtipsit)
			throws Exception {
		
		RespuestaConfirmacionEndosoVO respuesta = new RespuestaConfirmacionEndosoVO();
		long numMaximoDias = (long)endososManager.recuperarDiasDiferenciaEndosoValidos(cdramo,cdtipsup);
		
		// Se obtiene el numero de tramite de emision de una poliza:
		String ntramiteEmision=endososManager.obtenerNtramiteEmision(cdunieco, cdramo, estado, nmpoliza);
		
		// Se almacena la diferencia entre la fecha actual y a fecha que tendra el endoso:
		Date fechaHoy=new Date();
		long diferenciaFechaActualVSEndoso = fechaHoy.getTime() - fechaEndoso.getTime();
		diferenciaFechaActualVSEndoso = Math.abs(diferenciaFechaActualVSEndoso);
		// Se almacena el maximo de dias permitidos para realizar un endoso (30 dias):
		long maximoDiasPermitidos = numMaximoDias*24l*60l*60l*1000l;
		
		String descEndoso = endososManager.obtieneDescripcionEndoso(cdtipsup); 
		
		logger.debug("************* diferenciaFechaActualVSEndoso=" + diferenciaFechaActualVSEndoso);
		logger.debug("************* maximoDiasPermitidos         =" + maximoDiasPermitidos);
		
		String estatusTramite = null;
		if(diferenciaFechaActualVSEndoso > maximoDiasPermitidos) {
			logger.debug("************* El Endoso esta en espera, confirmado false");
			estatusTramite = EstatusTramite.ENDOSO_EN_ESPERA.getCodigo();
			respuesta.setConfirmado(false);
		} else {
			// Se confirma endoso:
			Map<String,String> paramsConfirmarEndosoB = new LinkedHashMap<String,String>(0);
			paramsConfirmarEndosoB.put("pv_cdunieco_i" , cdunieco);
			paramsConfirmarEndosoB.put("pv_cdramo_i"   , cdramo);
			paramsConfirmarEndosoB.put("pv_estado_i"   , estado);
			paramsConfirmarEndosoB.put("pv_nmpoliza_i" , nmpoliza);
			paramsConfirmarEndosoB.put("pv_nmsuplem_i" , nmsuplem);
			paramsConfirmarEndosoB.put("pv_nsuplogi_i" , nsuplogi);
			paramsConfirmarEndosoB.put("pv_cdtipsup_i" , cdtipsup);
			paramsConfirmarEndosoB.put("pv_dscoment_i" , dscoment);
			
			endososManager.confirmarEndosoB(paramsConfirmarEndosoB);
			
			estatusTramite = EstatusTramite.ENDOSO_CONFIRMADO.getCodigo();
			respuesta.setConfirmado(true);
			logger.debug("************* El Endoso fue confirmado, confirmado true");
		}
		
		// Se inserta en la Mesa de Control:
		/*Map<String,Object>paramsMesaControl = new HashMap<String,Object>();
		paramsMesaControl.put("pv_cdunieco_i"   , cdunieco);
		paramsMesaControl.put("pv_cdramo_i"     , cdramo);
		paramsMesaControl.put("pv_estado_i"     , estado);
		paramsMesaControl.put("pv_nmpoliza_i"   , nmpoliza);
		paramsMesaControl.put("pv_nmsuplem_i"   , nmsuplem);
		paramsMesaControl.put("pv_cdsucadm_i"   , cdunieco);
		paramsMesaControl.put("pv_cdsucdoc_i"   , cdunieco);
		paramsMesaControl.put("pv_cdtiptra_i"   , TipoTramite.ENDOSO.getCdtiptra());
		paramsMesaControl.put("pv_ferecepc_i"   , fechaEndoso);
		paramsMesaControl.put("pv_cdagente_i"   , null);
		paramsMesaControl.put("pv_referencia_i" , null);
		paramsMesaControl.put("pv_nombre_i"     , null);
		paramsMesaControl.put("pv_festatus_i"   , fechaEndoso);
		paramsMesaControl.put("pv_status_i"     , estatusTramite);
		paramsMesaControl.put("pv_comments_i"   , dscoment);
		paramsMesaControl.put("pv_nmsolici_i"   , null);
		paramsMesaControl.put("pv_cdtipsit_i"   , cdtipsit);
		paramsMesaControl.put("pv_otvalor01"    , ntramiteEmision);
		paramsMesaControl.put("pv_otvalor02"    , cdtipsup);
		paramsMesaControl.put("pv_otvalor03"    , descEndoso);
		paramsMesaControl.put("pv_otvalor04"    , nsuplogi);
		paramsMesaControl.put("pv_otvalor05"    , ((UserVO)session.get("USUARIO")).getUser());
		
		paramsMesaControl.put("cdusuari" , ((UserVO)session.get("USUARIO")).getUser());
		paramsMesaControl.put("cdsisrol" , ((UserVO)session.get("USUARIO")).getRolActivo().getClave());
		
		WrapperResultados wr = kernelManager.PMovMesacontrol(paramsMesaControl);*/
		
		Map<String,String> valores = new LinkedHashMap<String,String>();
		valores.put("otvalor01" , ntramiteEmision);
		valores.put("otvalor02" , cdtipsup);
		valores.put("otvalor03" , descEndoso);
		valores.put("otvalor04" , nsuplogi);
		valores.put("otvalor05" , ((UserVO)session.get("USUARIO")).getUser());
		
		String ntramiteGenerado = mesaControlManager.movimientoTramite(cdunieco
																		,cdramo
																		,estado
																		,nmpoliza
																		,nmsuplem
																		,cdunieco
																		,cdunieco
																		,TipoTramite.ENDOSO.getCdtiptra()
																		,fechaEndoso
																		,null
																		,null
																		,null
																		,fechaEndoso
																		,estatusTramite
																		,dscoment
																		,null
																		,cdtipsit
																		,((UserVO)session.get("USUARIO")).getUser()
																		,((UserVO)session.get("USUARIO")).getRolActivo().getClave()
																		,null //swimpres
																		,null //cdtipflu
																		,null //cdflujomc
																		,valores, null
																		);
		
		// Si fue confirmado no asignamos numero de tramite:
		if(respuesta.isConfirmado()) {
			respuesta.setNumeroTramite(null);
		} else {
			//respuesta.setNumeroTramite( (String)wr.getItemMap().get("ntramite") );
			respuesta.setNumeroTramite(ntramiteGenerado);
		}
	    
	    return respuesta;
	}
	/*//////////////////////////*/
	////// confirmar endoso //////
	//////////////////////////////
	
	/**
	 * Getters y setters
	 * @return
	 */
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public List<Map<String, String>> getList() {
		return list;
	}

	public void setList(List<Map<String, String>> list) {
		this.list = list;
	}

	public Map<String, Item> getItems() {
		return items;
	}

	public void setItems(Map<String, Item> items) {
		this.items = items;
	}
	
	public boolean isEndosoConfirmado() {
		return endosoConfirmado;
	}

	public void setEndosoConfirmado(boolean endosoConfirmado) {
		this.endosoConfirmado = endosoConfirmado;
	}

	public void setCotizacionDAO(CotizacionDAO cotizacionDAO) {
		this.cotizacionDAO = cotizacionDAO;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public List<Map<String, Object>> getOlist1() {
		return olist1;
	}

	public void setOlist1(List<Map<String, Object>> olist1) {
		this.olist1 = olist1;
	}
	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public List<Map<String, String>> getSlist2() {
		return slist2;
	}

	public void setSlist2(List<Map<String, String>> slist2) {
		this.slist2 = slist2;
	}

	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public Map<String, String> getSmap2() {
		return smap2;
	}

	public void setSmap2(Map<String, String> smap2) {
		this.smap2 = smap2;
	}

	public Map<String, String> getSmap3() {
		return smap3;
	}

	public void setSmap3(Map<String, String> smap3) {
		this.smap3 = smap3;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Map<String, Item> getImap1() {
		return imap1;
	}

	public void setImap1(Map<String, Item> imap1) {
		this.imap1 = imap1;
	}
	
	public File getCenso() {
		return censo;
	}

	public void setCenso(File censo) {
		this.censo = censo;
	}
}