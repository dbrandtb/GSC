package mx.com.gseguros.portal.siniestros.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.cotizacion.controller.MesaControlAction;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.model.RespuestaVO;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.CausaSiniestro;
import mx.com.gseguros.portal.general.util.EstatusTramite;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.Rango;
import mx.com.gseguros.portal.general.util.Rol;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.general.util.TipoPago;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.general.util.Validacion;
import mx.com.gseguros.portal.siniestros.model.AltaTramiteVO;
import mx.com.gseguros.portal.siniestros.model.AutorizaServiciosVO;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaManteniVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaPorcentajeVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaProveedorVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTDETAUTSVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTTAPVAATVO;
import mx.com.gseguros.portal.siniestros.model.DatosSiniestroVO;
import mx.com.gseguros.portal.siniestros.model.ListaFacturasVO;
import mx.com.gseguros.portal.siniestros.model.MesaControlVO;
import mx.com.gseguros.portal.siniestros.model.PolizaVigenteVO;
import mx.com.gseguros.portal.siniestros.model.SiniestroVO;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService.Operacion;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.opensymphony.xwork2.ActionContext;

public class SiniestrosAction extends PrincipalCoreAction{
    
	private static final String IMPORTE_WS_IMPORTE = "importe";
	private static final String IMPORTE_WS_IVA     = "iva";
	private static final String IMPORTE_WS_IVR     = "ivr";
	private static final String IMPORTE_WS_ISR     = "isr";
	private static final String IMPORTE_WS_CEDULAR = "cedular";
	
    private static final long serialVersionUID = -6321288906841302337L;
	private Logger logger = Logger.getLogger(SiniestrosAction.class);
	private DateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	private boolean success;
    private SiniestrosManager siniestrosManager;
    private KernelManagerSustituto kernelManagerSustituto;
    private transient CatalogosManager catalogosManager;
    private PantallasManager       pantallasManager;
    private transient Ice2sigsService ice2sigsService;
    private HashMap<String,String> params;
    private HashMap<String,Object> paramsO;
    
    private AutorizacionServicioVO datosAutorizacionEsp;
    private AutorizacionServicioVO numeroAutorizacion;
    private List<GenericVO> listaAsegurado;
    private List<GenericVO> listaCausaSiniestro;
    private List<AutorizaServiciosVO> listaAutorizacion;
    private List<CoberturaPolizaVO> listaCoberturaPoliza;
    private List<AltaTramiteVO> listaAltaTramite;
    private List<MesaControlVO> listaMesaControl;
    private List<DatosSiniestroVO> listaDatosSiniestro;
    private List<GenericVO> listaSubcobertura;
    private List<GenericVO> listaCPTICD;
    private List<ConsultaTDETAUTSVO> listaConsultaTablas;
    private List<ConsultaTTAPVAATVO> listaConsultaTTAPVAATV;
    private List<ConsultaManteniVO> listaConsultaManteni;
    private List<ConsultaPorcentajeVO> listaPorcentaje;
    private List<HashMap<String,String>> datosTablas;
    private List<PolizaVigenteVO> listaPoliza;
    private List<PolizaVigenteVO> polizaUnica;
    private String msgResult;
    private String diasMaximos;
    private String montoMaximo;
    private String mesMaximoMaternidad;
    private String factPagada;
    private String existePenalizacion;
    private String montoArancel;
    private String requiereAutServ;
    private String mesesTiempoEspera;
    private String existeDocAutServicio;
    private String autorizarProceso;
    private String porcentajePenalizacion;
    private List<HashMap<String, String>> loadList;
    private List<HashMap<String, String>> saveList;
    private List<GenericVO> listaPlazas;
    private List<GenericVO> listaTipoAtencion;
    private List<GenericVO> listadoRamosSalud;
    private List<ListaFacturasVO> listaFacturas;
    
    private Item                     item;
    private Map<String,Item>         imap;
    private String                   mensaje;
    private String					 validaCdTipsitTramite;
    private List<Map<String,String>> slist1;
    private List<Map<String,String>> slist2;
    private List<Map<String,String>> slist3;
    private Map<String,String>       smap;
    private Map<String,String>       smap2;
    private List<Map<String, Object>> facturasxSiniestro;
    private List<Map<String, Object>> aseguradosxSiniestro;
    private List<Map<String, String>> conceptosxSiniestro;
    private Map<String,String>       smap3;
    private List<Map<String,String>> lhosp;
    private List<Map<String,String>> lpdir;
    private List<Map<String,String>> lprem;
    private List<Map<String,String>> listaImportesWS;
    private List<Map<String,String>> facturasxTramite;
    
    private List<Map<String,String>> datosPenalizacion;
    private List<Map<String,String>> datosCoberturaxCal;
    private List<List<Map<String,String>>> llist1;
    private Map<String, String> map1;
    private List<Map<String,String>>  datosInformacionAdicional;
    private List<Map<String,String>>  datosValidacion;
    
    
	/**
     * Funci�n para la visualizaci�n de la autorizacion de servicio 
     * @return params con los valores para hacer las consultas
     */
	public String verAutorizacionServicio(){
		logger.debug(" **** Entrando a ver Autorizacion de servicio ****");
		try {
			//modificamos el valor del params para colocarle el rol que se esta ocupando
			logger.debug("params=" + params);
		}catch( Exception e){
			logger.error(e.getMessage(), e);
		}
		success = true;
		return SUCCESS;
    }
	
	
	public String altaTramite(){
		logger.debug(" **** Entrando al metodo de alta de tramite ****");
		try {
			//modificamos el valor del params para colocarle el rol que se esta ocupando
			String ntramite = null;
	    	String cdunieco = null;
	    	
	    	UserVO usuario  = (UserVO)session.get("USUARIO");
			//String cdunueco="1007";
	    	String cdUnieco = usuario.getCdUnieco();
			cdunieco = usuario.getCdUnieco().toString();
	    	
	    	if(params != null)
	    	{
	    		cdunieco  = params.get("cdunieco");
	    		ntramite  = params.get("ntramite");
	    	}
	    	
	    	HashMap<String, String> params = new HashMap<String, String>();
			params.put("cdunieco",cdunieco);
			params.put("ntramite",ntramite);
			setParamsJson(params);
			logger.debug("params=" + params);
		}catch( Exception e){
			logger.error(e.getMessage(), e);
		}
		success = true;
		return SUCCESS;
    }
	
	

    public String autorizacionServicios() {
	logger.debug(" **** Entrando a autorizacion Servicio ****");
	try {
		//Obtenemos el Rol a ocupar
		UserVO usuario  = (UserVO)session.get("USUARIO");
    	String cdrol    = usuario.getRolActivo().getClave();
    	
    	String pantalla            = "AUTORIZACION_SERVICIOS";
		String seccion             = "PANELBUTTONS";
		
		////// obtener valores del formulario //////
		List<ComponenteVO>ltFormulario=pantallasManager.obtenerComponentes(
				null, null, null,
				null, null, cdrol,
				pantalla, seccion, null);
		
		GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
		
		////// generar grid //////
		gc.generaComponentes(ltFormulario, true, false, false, false, false, true);
		imap=new HashMap<String,Item>(0);
		imap.put("panelbuttons",gc.getButtons());
    	
    	String numero_aut = null;
    	String ntramite = null;
    	
    	if(params != null)
    	{
    		numero_aut  = params.get("nmAutSer");
        	ntramite  =  params.get("ntramite");
    	}
    	
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("nmAutSer",numero_aut);
		params.put("ntramite",ntramite);
		params.put("cdrol",cdrol);
		setParamsJson(params);
		
		/*params={nmAutSer=98, cdrol=, ntramite=1673}*/
	} catch (Exception e) {
		logger.error(e.getMessage(), e);
	}
	success = true;
	return SUCCESS;
    }
	
	/**
     * Funci�n que realiza la busqueda de la consulta de Autorizaci�n de servicio en especifico
     * @param String nmautser
     * @return String autorizaci�n de Servicio
     */
    public String consultaAutorizacionServicio(){
    		logger.debug(" **** Entrando a Consulta de Autorizaci�n de Servicio en Especifico****");
    		try {
    				List<AutorizacionServicioVO> lista = siniestrosManager.getConsultaAutorizacionesEsp(params.get("nmautser"));
    				if(lista!=null && !lista.isEmpty())	datosAutorizacionEsp = lista.get(0);
    		}catch( Exception e){
    			logger.error("Error al obtener los datos de Autorizaci�n de Servicio en Especifico",e);
            return SUCCESS;
        }
        success = true;
        return SUCCESS;
    }
    
    /**
     * Funci�n que obtiene la lista del asegurado
     * @param void sin parametros de entrada
     * @return Lista GenericVO con la informaci�n de los asegurados
     */    
    public String consultaListaAsegurado(){
    	logger.debug(" **** Entrando al m�todo de Lista de Asegurado ****");
	   	try {
	   		listaAsegurado= siniestrosManager.getConsultaListaAsegurado(params.get("cdperson"));
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de los asegurados ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
    }
    
	/**
	 * Funci�n que obtiene el listado de  de Autorizaci�n de Servicio
	 * @param String cdperson
	 * @return Lista AutorizaServiciosVO con la informaci�n de los asegurados
	 */
	public String consultaListaAutorizacion(){
		logger.debug(" **** Entrando a consulta de lista de Autorizaci�n por CDPERSON ****");
		try {
				
				List<AutorizaServiciosVO> lista = siniestrosManager.getConsultaListaAutorizaciones(params.get("tipoAut"),params.get("cdperson"));
				if(lista!=null && !lista.isEmpty())	listaAutorizacion = lista;
		}catch( Exception e){
			logger.error("Error al obtener la lista de autorizaciones ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
    }
    
	/**
	 * metodo para el guardado de la autorizaci�n de Servicio
	 * @param Json con todos los valores del formulario y los grid
	 * @return Lista AutorizaServiciosVO con la informaci�n de los asegurados
	 */
	public String guardaAutorizacionServicio(){
			logger.debug(" **** Entrando a guardado de Autorizaci�n de Servicio ****");
			
			try {
					this.session=ActionContext.getContext().getSession();
			        UserVO usuario=(UserVO) session.get("USUARIO");
			        
					HashMap<String, Object> paramsR = new HashMap<String, Object>();
					paramsR.put("pv_nmautser_i",params.get("nmautser"));
					paramsR.put("pv_nmautant_i",params.get("nmautant"));
					paramsR.put("pv_cdperson_i",params.get("cdperson"));
					paramsR.put("pv_fesolici_i",params.get("fesolici"));
					paramsR.put("pv_feautori_i",params.get("feautori"));
					paramsR.put("pv_fevencim_i",params.get("fevencim"));
					paramsR.put("pv_feingres_i",params.get("feingres"));
					paramsR.put("pv_cdunieco_i",params.get("cdunieco"));
					paramsR.put("pv_estado_i",params.get("estado"));
					paramsR.put("pv_cdramo_i",params.get("cdramo"));
					paramsR.put("pv_nmpoliza_i",params.get("nmpoliza"));
					paramsR.put("pv_nmsituac_i",params.get("nmsituac"));
					paramsR.put("pv_cduniecs_i",params.get("cduniecs"));
					paramsR.put("pv_cdgarant_i",params.get("cdgarant"));
					paramsR.put("pv_cdconval_i",params.get("cdconval"));
					paramsR.put("pv_cdprovee_i",params.get("cdprovee"));
					paramsR.put("pv_cdmedico_i",params.get("cdmedico"));
					paramsR.put("pv_mtsumadp_i",params.get("mtsumadp"));
					paramsR.put("pv_copagofi_i",params.get("copagoTotal"));   // Copago Final  copagoTotal
					paramsR.put("pv_porpenal_i",params.get("idPenalCircHospitalario")); // penalizacion por circulo hospitalario
					paramsR.put("pv_cdicd_i",params.get("cdicd"));
					paramsR.put("pv_cdcausa_i",params.get("cdcausa"));
					paramsR.put("pv_aaapertu_i",params.get("aaapertu"));
					paramsR.put("pv_status_i",params.get("status"));
					paramsR.put("pv_dstratam_i",params.get("dstratam"));
					paramsR.put("pv_dsobserv_i",params.get("dsobserv"));
					paramsR.put("pv_dsnotas_i",params.get("dsnotas"));
					paramsR.put("pv_fesistem_i",params.get("fesistem")); 
					paramsR.put("pv_cduser_i",usuario.getUser());
					paramsR.put("pv_nombmedi_i",params.get("medicoPExt"));
					paramsR.put("pv_especmed_i",params.get("especialidadPExt"));
					paramsR.put("pv_tpautori_i",params.get("cveTipoAutorizaG"));
					//ELIMINACION DE LOS REGISTROS EN LA TABLA
					siniestrosManager.getEliminacionRegistros(params.get("nmautser"));
					
					List<AutorizacionServicioVO> lista = siniestrosManager.guardarAutorizacionServicio(paramsR);
					if(lista!=null && !lista.isEmpty())
					{
						numeroAutorizacion = lista.get(0);
						for(int i=0;i<datosTablas.size();i++)
				   		{
				   			HashMap<String, Object> paramsTDeTauts = new HashMap<String, Object>();
				   			paramsTDeTauts.put("pv_nmautser_i",lista.get(0).getNmautser());
							paramsTDeTauts.put("pv_cdtipaut_i",datosTablas.get(i).get("cdtipaut"));
							paramsTDeTauts.put("pv_cdmedico_i",datosTablas.get(i).get("cdmedico"));
							paramsTDeTauts.put("pv_cdtipmed_i",datosTablas.get(i).get("cdtipmed"));
							paramsTDeTauts.put("pv_cdctp_i",datosTablas.get(i).get("cdcpt"));
							paramsTDeTauts.put("pv_precio_i",datosTablas.get(i).get("precio"));
							paramsTDeTauts.put("pv_cantporc_i",datosTablas.get(i).get("cantporc"));
							paramsTDeTauts.put("pv_ptimport_i",datosTablas.get(i).get("ptimport"));
							paramsTDeTauts.put("pv_nombprov_i",datosTablas.get(i).get("nombreMedico"));
							//GUARDADO DE LOS DATOS PARA LAS TABLAS
							siniestrosManager.guardaListaTDeTauts(paramsTDeTauts);
				   		}
						
						if(params.get("claveTipoAutoriza").trim().equalsIgnoreCase("1") || params.get("claveTipoAutoriza").trim().equalsIgnoreCase("3"))
						{
							/* VALORES A ENVIAR A MESA DE CONTROL */
							HashMap<String, Object> paramsMCAut = new HashMap<String, Object>();
							paramsMCAut.put("pv_cdunieco_i",params.get("cdunieco"));
							paramsMCAut.put("pv_cdramo_i",params.get("cdramo"));
							paramsMCAut.put("pv_estado_i",params.get("estado"));
							paramsMCAut.put("pv_nmpoliza_i",params.get("nmpoliza"));
							paramsMCAut.put("pv_nmsuplem_i",params.get("nmsuplem"));
							paramsMCAut.put("pv_cdsucadm_i",null);
							paramsMCAut.put("pv_cdsucdoc_i",null);
							paramsMCAut.put("pv_cdtiptra_i","14");
							paramsMCAut.put("pv_ferecepc_i",null);
							paramsMCAut.put("pv_cdagente_i",null);
							paramsMCAut.put("pv_referencia_i",null);
							paramsMCAut.put("pv_nombre_i",null);
							paramsMCAut.put("pv_festatus_i",null);
							
							if(params.get("status").trim().equalsIgnoreCase("2")){
								paramsMCAut.put("pv_status_i",EstatusTramite.CONFIRMADO.getCodigo());//  con
								//generarAutoriServicio();
								
							}else{
								if(usuario.getRolActivo().getClave().trim().equalsIgnoreCase(RolSistema.COORDINADOR_MEDICO_MULTIREGIONAL.getCdsisrol()))
								{
									paramsMCAut.put("pv_status_i",EstatusTramite.EN_CAPTURA_CMM.getCodigo());// valor 12
								}else{
									paramsMCAut.put("pv_status_i",EstatusTramite.EN_CAPTURA.getCodigo());// valor 7
								}
							}
							//paramsMCAut.put("pv_status_i","7");
							paramsMCAut.put("pv_comments_i",params.get("dsnotas"));
							paramsMCAut.put("pv_nmsolici_i",null);
							paramsMCAut.put("pv_cdtipsit_i",params.get("cdtipsit"));
							paramsMCAut.put("pv_otvalor01",lista.get(0).getNmautser());         		// No. de autorizaci�n
							paramsMCAut.put("pv_otvalor02",params.get("fesolici"));             		// Fecha de Solicitud
							paramsMCAut.put("pv_otvalor03",params.get("feautori"));             		// Fecha de autorizacion
							paramsMCAut.put("pv_otvalor04",params.get("fevencim"));             		// Fecha de Vencimiento
							paramsMCAut.put("pv_otvalor05",params.get("dsNombreAsegurado"));            // Nombre del asegurado
							paramsMCAut.put("pv_otvalor06",params.get("copagoTotal"));					// Total Penalizacinn
							paramsMCAut.put("pv_otvalor07",params.get("idHospitalPlus"));				// Tipo Hospital Plus
							WrapperResultados res = kernelManagerSustituto.PMovMesacontrol(paramsMCAut);
							
							if(params.get("status").trim().equalsIgnoreCase("2")){
								Map<String,Object>paramsO =new HashMap<String,Object>();
								paramsO.put("pv_ntramite_i" , (String)res.getItemMap().get("ntramite"));
								paramsO.put("pv_cdunieco_i" , params.get("cdunieco"));
								paramsO.put("pv_cdramo_i" , params.get("cdramo"));
								paramsO.put("pv_estado_i" , params.get("estado"));
								paramsO.put("pv_nmpoliza_i" , params.get("nmpoliza"));
								paramsO.put("pv_nmAutSer_i" , lista.get(0).getNmautser());//params.get("nmautant"));
								paramsO.put("pv_cdperson_i" , params.get("cdperson"));
								paramsO.put("pv_nmsuplem_i" , params.get("nmsuplem"));
								generarAutoriServicio(paramsO);
							}
							
						}else{
							// aqui va la actualizacion de los campos de mesa control
							//verificamos el valor del dsnom
							Map<String,Object> otvalor = new HashMap<String,Object>();
							if(params.get("dsNombreAsegurado").toString().length() > 0){
								otvalor.put("pv_otvalor05_i"  , params.get("dsNombreAsegurado"));
							}
				    		otvalor.put("pv_ntramite_i" , params.get("idNumtramiteInicial"));
				    		otvalor.put("pv_cdramo_i"   , params.get("cdramo"));
				    		otvalor.put("pv_cdtipsit_i" , params.get("cdtipsit"));
				    		otvalor.put("pv_otvalor02_i"  , params.get("fesolici"));
				    		otvalor.put("pv_otvalor03_i"  , params.get("feautori"));
				    		otvalor.put("pv_otvalor04_i"  , params.get("fevencim"));
				    		otvalor.put("pv_otvalor06_i",params.get("copagoTotal"));
				    		otvalor.put("pv_otvalor07_i",params.get("idHospitalPlus"));
				    		siniestrosManager.actualizaOTValorMesaControl(otvalor);
				    		
				    		// Tenemos que actualizar el status para el guardado
				    		if(params.get("status").trim().equalsIgnoreCase("2")){
								//paramsMCAut.put("pv_status_i",EstatusTramite.CONFIRMADO.getCodigo());
				    			
				    			String statusNuevo = EstatusTramite.CONFIRMADO.getCodigo();
				    			String ntramite = params.get("idNumtramiteInicial");
				    			String comments = null;
				    			String cdmotivo = null;
				    			UserVO usu=(UserVO)session.get("USUARIO");
				    			String cdusuariSesion = usu.getUser();
				    			String cdsisrolSesion = usu.getRolActivo().getClave();
				    			String rolDestino     = null;
				    			String usuarioDestino = null;
				    			String cdclausu       = null;
				    			
				    			siniestrosManager.moverTramite(ntramite, statusNuevo, comments, cdusuariSesion, cdsisrolSesion, usuarioDestino, rolDestino, cdmotivo, cdclausu);
				    			
				    			Map<String,Object>paramsO =new HashMap<String,Object>();
								paramsO.put("pv_ntramite_i" , params.get("idNumtramiteInicial"));
								paramsO.put("pv_cdunieco_i" , params.get("cdunieco"));
								paramsO.put("pv_cdramo_i" , params.get("cdramo"));
								paramsO.put("pv_estado_i" , params.get("estado"));
								paramsO.put("pv_nmpoliza_i" , params.get("nmpoliza"));
								paramsO.put("pv_nmAutSer_i" , lista.get(0).getNmautser());
								paramsO.put("pv_cdperson_i" , params.get("cdperson"));
								paramsO.put("pv_nmsuplem_i" , params.get("nmsuplem"));
								generarAutoriServicio(paramsO);
				    			
							}
							
						}
					}
			}catch( Exception e){
				logger.error("Error al guardar la autorizaci�n de servicio ",e);
	        return SUCCESS;
	    }
	    
	    success = true;
	    return SUCCESS;
	}

	
	/**
	 * metodo para el guardado de la autorizaci�n de Servicio
	 * @param Json con todos los valores del formulario y los grid
	 * @return Lista AutorizaServiciosVO con la informaci�n de los asegurados
	 */
	public String guardaAltaTramite(){
			logger.debug(" **** Entrando al guardado de alta de tramite ****");
			logger.debug(params);
			logger.debug(datosTablas);
			try{
				// 1.- Guardar en TMESACONTROL 
				this.session=ActionContext.getContext().getSession();
				UserVO usuario=(UserVO) session.get("USUARIO");
				HashMap<String, Object> parMesCon = new HashMap<String, Object>();
				parMesCon.put("pv_cdunieco_i",params.get("cdunieco"));
				parMesCon.put("pv_cdramo_i",params.get("cmbRamos"));
				parMesCon.put("pv_estado_i",params.get("estado"));
				parMesCon.put("pv_nmpoliza_i",params.get("polizaAfectada"));
				parMesCon.put("pv_nmsuplem_i",params.get("idNmsuplem"));
				parMesCon.put("pv_nmsolici_i",params.get("idNmsolici"));
				parMesCon.put("pv_cdtipsit_i",params.get("idCdtipsit"));
				parMesCon.put("pv_cdsucadm_i",params.get("cmbOficEmisora"));
				parMesCon.put("pv_cdsucdoc_i",params.get("cmbOficReceptora"));
				parMesCon.put("pv_cdtiptra_i",TipoTramite.SINIESTRO.getCdtiptra());
				parMesCon.put("pv_ferecepc_i",getDate(params.get("dtFechaRecepcion")));
				parMesCon.put("pv_cdagente_i",null);
				parMesCon.put("pv_referencia_i",null);
				parMesCon.put("pv_nombre_i",params.get("idnombreAsegurado"));
				parMesCon.put("pv_festatus_i",getDate(params.get("dtFechaRecepcion")));
				parMesCon.put("pv_status_i",EstatusTramite.PENDIENTE.getCodigo());
				parMesCon.put("pv_comments_i",null);
				parMesCon.put("pv_otvalor02",params.get("cmbTipoPago"));
				parMesCon.put("pv_otvalor03",params.get("ImporteIndFactura"));
				parMesCon.put("pv_otvalor04",params.get("cmbBeneficiario"));
				parMesCon.put("pv_otvalor15",params.get("idnombreBeneficiarioProv"));
				parMesCon.put("pv_otvalor05",usuario.getUser());
				parMesCon.put("pv_otvalor06",params.get("fechaIndFactura"));
				parMesCon.put("pv_otvalor07",params.get("cmbTipoAtencion"));
				parMesCon.put("pv_otvalor08",params.get("numIndFactura"));
				parMesCon.put("pv_otvalor09",params.get("cmbAseguradoAfectado"));
				parMesCon.put("pv_otvalor10",params.get("dtFechaOcurrencia"));
				parMesCon.put("pv_otvalor20",params.get("cmbRamos"));
				parMesCon.put("pv_otvalor11",params.get("cmbProveedor"));
				if(params.get("cmbProveedor").toString().length() > 0){
					parMesCon.put("pv_otvalor13",Rol.CLINICA.getCdrol());
				}
				//Si el tr&aacute;mite es nuevo
				if(params.get("idNumTramite").toString().length() <= 0){
				    WrapperResultados res = kernelManagerSustituto.PMovMesacontrol(parMesCon);
				    if(res.getItemMap() == null)
				    {
				        logger.error("Sin mensaje respuesta de nmtramite!!");
				    }
				    else{
				        msgResult = (String) res.getItemMap().get("ntramite");
				        ProcesoAltaTramite(msgResult);
			        }
				}else{
					//Existe el tr�mite y solo lo vamos a actualizar
					HashMap<String, Object> modMesaControl = new HashMap<String, Object>();
					//1.- Verificamos si cambio el tipo de atenci�n
					List<MesaControlVO> lista = siniestrosManager.getConsultaListaMesaControl(params.get("idNumTramite").toString());
					String valorTipoAtencion = lista.get(0).getOtvalor07mc();
					if(!valorTipoAtencion.equalsIgnoreCase(params.get("cmbTipoAtencion"))){
						siniestrosManager.eliminaDocumentosxTramite(params.get("idNumTramite").toString());
						modMesaControl.put("pv_otvalor01_i",null);
					}else{
						modMesaControl.put("pv_otvalor01_i",lista.get(0).getOtvalor01mc());
					}
					//Actualizar los valores de ntramite
					
					modMesaControl.put("pv_ntramite_i",params.get("idNumTramite"));
					modMesaControl.put("pv_cdunieco_i",params.get("cdunieco"));
					modMesaControl.put("pv_cdramo_i",params.get("cmbRamos"));
					modMesaControl.put("pv_estado_i",params.get("estado"));
					modMesaControl.put("pv_nmpoliza_i",params.get("polizaAfectada"));
					modMesaControl.put("pv_nmsuplem_i",params.get("idNmsuplem"));
					modMesaControl.put("pv_nmsolici_i",params.get("idNmsolici"));
					modMesaControl.put("pv_cdtipsit_i",params.get("idCdtipsit"));
					modMesaControl.put("pv_cdsucadm_i",params.get("cmbOficEmisora"));
					modMesaControl.put("pv_cdsucdoc_i",params.get("cmbOficReceptora"));
					modMesaControl.put("pv_cdtiptra_i",TipoTramite.SINIESTRO.getCdtiptra());
					modMesaControl.put("pv_ferecepc_i",params.get("dtFechaRecepcion"));
					modMesaControl.put("pv_nombre_i",params.get("idnombreAsegurado"));
					modMesaControl.put("pv_festatus_i",params.get("dtFechaRecepcion"));
					modMesaControl.put("pv_status_i",EstatusTramite.PENDIENTE.getCodigo());
					modMesaControl.put("pv_otvalor02_i",params.get("cmbTipoPago"));
					modMesaControl.put("pv_otvalor03_i",params.get("ImporteIndFactura"));
					modMesaControl.put("pv_otvalor04_i",params.get("cmbBeneficiario"));
					modMesaControl.put("pv_otvalor05_i",usuario.getUser());
					modMesaControl.put("pv_otvalor06_i",params.get("fechaIndFactura"));
					modMesaControl.put("pv_otvalor07_i",params.get("cmbTipoAtencion"));
					modMesaControl.put("pv_otvalor08_i",params.get("numIndFactura"));
					modMesaControl.put("pv_otvalor09_i",params.get("cmbAseguradoAfectado"));
					modMesaControl.put("pv_otvalor10_i",params.get("dtFechaOcurrencia"));
					modMesaControl.put("pv_otvalor11_i",params.get("cmbProveedor"));
					modMesaControl.put("pv_otvalor15_i",params.get("idnombreBeneficiarioProv"));
					modMesaControl.put("pv_otvalor20_i",params.get("cmbRamos"));
					siniestrosManager.actualizaValorMC(modMesaControl);
					
					//2.- Verificamos Si el tipo de pago es 
					//    1.- Reembolso
					//    2.- Indemnizacion
					//
					if(params.get("cmbTipoPago").trim().equalsIgnoreCase(TipoPago.REEMBOLSO.getCodigo())||params.get("cmbTipoPago").trim().equalsIgnoreCase(TipoPago.INDEMNIZACION.getCodigo()))
					{
						 ProcesoAltaTramite(params.get("idNumTramite"));
					}
				}
				
			}catch( Exception e){
				logger.error("Error en el guardado de alta de tramite ",e);
		        return SUCCESS;
			}
			
			
	    success = true;
	    return SUCCESS;
	}
    
    
   /**
	 * metodo que obtiene el listado de las coberturas de poliza
	 * @param maps [cdunieco,estado,cdramo,nmpoliza,nmsituac,cdgarant]
	 * @return Lista CoberturaPolizaVO con la informaci�n de los asegurados
	 */
	public String consultaListaCoberturaPoliza(){
		logger.debug(" **** Entrando a consulta de lista de Cobertura de poliza ****");
		try {
			HashMap<String, Object> paramCobertura = new HashMap<String, Object>();
			paramCobertura.put("pv_cdunieco_i",params.get("cdunieco"));
			paramCobertura.put("pv_estado_i",params.get("estado"));
			paramCobertura.put("pv_cdramo_i",params.get("cdramo"));
			paramCobertura.put("pv_nmpoliza_i",params.get("nmpoliza"));
			paramCobertura.put("pv_nmsituac_i",params.get("nmsituac"));
			paramCobertura.put("pv_cdgarant_i",params.get("cdgarant"));
			
			List<CoberturaPolizaVO> lista = siniestrosManager.getConsultaListaCoberturaPoliza(paramCobertura);
			if(lista!=null && !lista.isEmpty())	listaCoberturaPoliza = lista;
		}catch( Exception e){
			logger.error("Error al obtener la lista de la cobertura de la poliza ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
   }
	
	
	public String consultaListadoAltaTramite(){
		logger.debug(" **** Entrando a consulta de lista de alta de tramite ****");
		try {
			
			List<AltaTramiteVO> lista = siniestrosManager.getConsultaListaAltaTramite(params.get("ntramite"));
			if(lista!=null && !lista.isEmpty())	listaAltaTramite = lista;
		}catch( Exception e){
			logger.error("Error al obtener la lista del alta de tramite ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
   }
	
	public String consultaListadoMesaControl(){
		logger.debug(" **** Entrando a consulta del registro de la mesa de control  ****");
		try {
			
			List<MesaControlVO> lista = siniestrosManager.getConsultaListaMesaControl(params.get("ntramite"));
			if(lista!=null && !lista.isEmpty())	listaMesaControl = lista;
		}catch( Exception e){
			logger.error("Error al obtener los registros de la mesa de control  ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
   }
	
	/**
	 * metodo que obtiene la informaci�n de deducible y copago
	 * @param String params [cdunieco,estado,cdramo,nmpoliza,nmsituac,cdgarant,subcober]
	 * @return Lista DatosSiniestroVO con la informaci�n de los asegurados
	 */
	public String consultaListaDatSubGeneral(){
		logger.debug(" **** Entrando a consulta de lista de subcobertura **");
		try {
			HashMap<String, Object> paramDatSubGral = new HashMap<String, Object>();
			paramDatSubGral.put("pv_cdunieco_i",params.get("cdunieco"));
			paramDatSubGral.put("pv_estado_i",params.get("estado"));
			paramDatSubGral.put("pv_cdramo_i",params.get("cdramo"));
			paramDatSubGral.put("pv_nmpoliza_i",params.get("nmpoliza"));
			paramDatSubGral.put("pv_nmsituac_i",params.get("nmsituac"));
			paramDatSubGral.put("pv_cdgarant_i",params.get("cdgarant"));
			paramDatSubGral.put("pv_subcober_i",params.get("subcober"));
			paramDatSubGral.put("pv_cdpresta_i",params.get("cdpresta"));
			paramDatSubGral.put("pv_cdtipo_i",Rol.CLINICA.getCdrol());
			
			List<DatosSiniestroVO> lista = siniestrosManager.getConsultaListaDatSubGeneral(paramDatSubGral);
			if(lista!=null && !lista.isEmpty())	listaDatosSiniestro = lista;
		}catch( Exception e){
			logger.error("Error al obtener ls datos de deducible y copago ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
    }
	
    /**
    * Funci�n que obtiene la lista de Sbcobertura
    * @param cdgarant
    * @param cdsubcob
    * @return Lista GenericVO con la informaci�n de los asegurados
    */    
   public String consultaListaSubcobertura(){
   	logger.debug(" **** Entrando al m�todo de Lista de Subcobertura ****");
   	  	try {
	   		listaSubcobertura= siniestrosManager.getConsultaListaSubcobertura(params.get("cdgarant"),params.get("cdsubcob"));
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de subcoberturas ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;   	
  }
	
   /**
    * Funci�n que obtiene la lista de las poliza
    * @param cdperson
    * @return Lista GenericVO con la informaci�n de los asegurados
    */ 
   public String consultaListaPoliza(){
   	logger.debug(" **** Entrando al m�todo de Lista de Poliza ****");
   	logger.debug(params);
   	try {
				List<PolizaVigenteVO> lista = siniestrosManager.getConsultaListaPoliza(params.get("cdperson"), params.get("cdramo"));
				if(lista!=null && !lista.isEmpty())	listaPoliza = lista;
			}catch( Exception e){
				logger.error("Error al obtener los datos de la poliza ",e);
				return SUCCESS;
			}
	   	success = true;
	   	return SUCCESS;
   }
   
   
   public String consultaPolizaUnica(){
	   	logger.debug(" **** Entrando al metodo de consulta poliza Unica ****");
	   	try {
		   		HashMap<String, Object> paramPolUnica = new HashMap<String, Object>();
		   		paramPolUnica.put("pv_cdunieco_i",params.get("cdunieco"));
		   		paramPolUnica.put("pv_cdramo_i",params.get("cdramo"));
		   		paramPolUnica.put("pv_estado_i",params.get("estado"));
		   		paramPolUnica.put("pv_nmpoliza_i",params.get("nmpoliza"));
		   		paramPolUnica.put("pv_cdperson_i",params.get("cdperson"));
		   		List<PolizaVigenteVO> polUnica = siniestrosManager.getConsultaPolizaUnica(paramPolUnica);
				if(polUnica!=null && !polUnica.isEmpty())	polizaUnica = polUnica;
			}catch( Exception e){
				logger.error("Error al obtener los datos de la poliza ",e);
				return SUCCESS;
			}
		   	success = true;
		   	return SUCCESS;
	   }


public String getMsgResult() {
	return msgResult;
}

public void setMsgResult(String msgResult) {
	this.msgResult = msgResult;
}

/**
   * Funci�n que obtiene el listado de las subcobertura
   * @param cdtabla
   * @param otclave
   * @return Lista GenericVO con la informaci�n de los asegurados
   */    
   public String consultaListaCPTICD(){
	  	logger.debug(" **** Entrando al m�todo de Lista de Subcobertura ****");
		   	try {	   		
		   		listaCPTICD= siniestrosManager.getConsultaListaCPTICD(params.get("cdtabla"),params.get("otclave"));
		   	}catch( Exception e){
		   		logger.error("Error al consultar la Lista de subcoberturas ",e);
		   		return SUCCESS;
		   	}
		   	success = true;
		   	return SUCCESS;   	
   }
	
   	/**
	 * Funci�n que obtiene el listado de las listas de los grids
	 * @param String nmautser
	 * @return Lista ConsultaTDETAUTSVO con la informaci�n
	 */
	public String consultaListaTDeTauts(){
		logger.debug(" **** Entrando a consulta de lista TDETAUTS ****");
		try {
				
				List<ConsultaTDETAUTSVO> lista = siniestrosManager.getConsultaListaTDeTauts(params.get("nmautser"));
				if(lista!=null && !lista.isEmpty())	listaConsultaTablas = lista;
		}catch( Exception e){
			logger.error("Error al obtener los datos para la informaci�n de las tablas iternas",e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	
	/**
	 * Funci�n para eliminar la listas de los grids
	 * @param String nmautser
	 * @return void
	 */
	public void borraRegistrosTabla(){
		logger.debug(" **** Entrando a eliminaci�n de registros de tablas ****");
		try {
				siniestrosManager.getEliminacionRegistros(params.get("nmautser"));
				
		}catch( Exception e){
			logger.error("Error al eliminar los registros de la tabla",e);
		}
   }
	
	public String cambiarEstatusMAUTSERV(){
		logger.debug(" **** Entrando a cambiar el estatus al momento de cancelarlos ****");
		try {
				siniestrosManager.getCambiarEstatusMAUTSERV(params.get("nmautser"), params.get("status"));
				
		}catch( Exception e){
			logger.error("Error al cambiar el estatus",e);
		}
		
		success = true;
		return SUCCESS;
   }
	
	/**
	 * Funci�n para eliminar la listas de los grids
	 * @param params [nmautser,cdtipaut,cdmedico,cdtipmed,cdcpt,precio,cantporc,ptimport]
	 * @return void
	 */
	public String guardaListaTDeTauts(){
		logger.debug(" **** Entrando a guardado de Autorizaci�n de Servicio ****");
		try {
				// Recibir� una lista con los valores y de ahi guardarlos en forma de mapas
				HashMap<String, Object> paramsTDeTauts = new HashMap<String, Object>();
				paramsTDeTauts.put("pv_nmautser_i",params.get("nmautser"));
				paramsTDeTauts.put("pv_cdtipaut_i",params.get("cdtipaut"));
				paramsTDeTauts.put("pv_cdmedico_i",params.get("cdmedico"));
				paramsTDeTauts.put("pv_cdtipmed_i",params.get("cdtipmed"));
				paramsTDeTauts.put("pv_cdctp_i",params.get("cdcpt"));
				paramsTDeTauts.put("pv_precio_i",params.get("precio"));
				paramsTDeTauts.put("pv_cantporc_i",params.get("cantporc"));
				paramsTDeTauts.put("pv_ptimport_i",params.get("ptimport"));
				
				siniestrosManager.guardaListaTDeTauts(paramsTDeTauts);
		}catch( Exception e){
			logger.error("Error al guardado de la tablas internas",e);
       return SUCCESS;
   }
   success = true;
   return SUCCESS;
}
	
	/**
	 * Funci�n que obtiene el listado de  de Autorizaci�n de Servicio
	 * @param String cdperson
	 * @return Lista AutorizaServiciosVO con la informaci�n de los asegurados
	 */
	public String consultaListaTTAPVAATVO(){
		logger.debug(" **** Entrando a consulta de lista de Cobertura de poliza ****");
		try {
			HashMap<String, Object> paramTTAPVAAT = new HashMap<String, Object>();
			paramTTAPVAAT.put("pv_cdtabla_i",params.get("cdtabla"));
			paramTTAPVAAT.put("pv_otclave1_i",params.get("otclave1"));
			paramTTAPVAAT.put("pv_otclave2_i",params.get("otclave2"));
			paramTTAPVAAT.put("pv_otclave3_i",params.get("otclave3"));
			paramTTAPVAAT.put("pv_otclave4_i",params.get("otclave4"));
			paramTTAPVAAT.put("pv_otclave5_i",params.get("otclave5"));
			List<ConsultaTTAPVAATVO> lista = siniestrosManager.getConsultaListaTTAPVAAT(paramTTAPVAAT);
			if(lista!=null && !lista.isEmpty())	listaConsultaTTAPVAATV = lista;
		}catch( Exception e){
			logger.error("Error al obtener los datos de la poliza ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
   }	
	
   @Deprecated	
   public String consultaListaManteni(){
		logger.debug(" **** Entrando a consulta de lista de Mantenimiento****");
		try {
				List<ConsultaManteniVO> lista = siniestrosManager.getConsultaListaManteni(params.get("cdtabla"),params.get("codigo"));
				if(lista!=null && !lista.isEmpty())	listaConsultaManteni = lista;
		}catch( Exception e){
			logger.error("Error al obtener los datos de la poliza ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
   }
   
   //ConsultaPorcentajeVO> listaPorcentaje
   public String consultaListaPorcentaje(){
		logger.debug(" **** Entrando a consulta de lista de Mantenimiento****");
		try {
				List<ConsultaPorcentajeVO> lista = siniestrosManager.getConsultaListaPorcentaje(params.get("cdcpt"),params.get("cdtipmed"),params.get("mtobase"));
				if(lista!=null && !lista.isEmpty())	listaPorcentaje = lista;
		}catch( Exception e){
			logger.error("Error al obtener los datos de la poliza ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
   }
   
   
   /*public String generarSiniestroAutServicio(){
		logger.debug(" **** Entrando a generar el siniestro  por medio de la autorizacion del servicio ***");
		try {
				siniestrosManager.getAltaSiniestroAutServicio(params.get("nmautser"));
		}catch( Exception e){
			logger.error("Error al obtener los datos de Autorizaci�n de Servicio en Especifico",e);
			return SUCCESS;
		}
	   success = true;
	   return SUCCESS;
   }*/
   
   public String generarSiniestroAltaTramite(){
			logger.debug(" **** Entrando a generar el siniestro por medio de la autorizacion de servicio ***");
		try {
				siniestrosManager.getAltaSiniestroAltaTramite(params.get("ntramite"));
		}catch( Exception e){
			logger.error("Error al obtener los datos de autorizacion de ",e);
			return SUCCESS;
		}
	   success = true;
	   return SUCCESS;
	}
   
   /*public String generarSiniestroSinAutorizacion(){
		logger.debug(" **** Entrando a generar el siniestro sin autorizacion de servicio ***");
	try {
			siniestrosManager.getAltaSiniestroSinAutorizacion(params.get("ntramite"));
	}catch( Exception e){
		logger.error("Error al obtener los datos de autorizacion del siniestro sin autorizacion de servicio ",e);
		return SUCCESS;
	}
  success = true;
  return SUCCESS;
}*/
   
   
   
public String generarSiniestroSinAutorizacion()
{
	   	logger.debug(""
			+ "\n#############################################"
			+ "\n#############################################"
			+ "\n###### generarSiniestroSinAutorizacion ######"
			+ "\n######                          		######"
			);
	logger.debug("params: "+params);
	try
	{
		//CREA UN MSINIEST A PARTIR DE TWORKSIN
		siniestrosManager.getAltaSiniestroSinAutorizacion(params.get("ntramite"),params.get("cdunieco"),params.get("cdramo"),params.get("estado"),
														  params.get("nmpoliza"),params.get("nmsuplem"),params.get("nmsituac"),params.get("cdtipsit"), params.get("dateOcurrencia"), params.get("nfactura"));
		mensaje = "Se ha generado el siniestro";
		success=true;
	}
	catch(Exception ex)
	{
		logger.error("error al inicar siniestro desde tworksin",ex);
		mensaje = ex.getMessage();
		success=false;
	}
	logger.debug(""
			+ "\n######               		  	        ######"
			+ "\n###### generarSiniestroSinAutorizacion ######"
			+ "\n#############################################"
			+ "\n#############################################"
	   			);
	   	return SUCCESS;
}
   
   public String generarAltaMsinival(){
		logger.debug(" **** Entrando a generar la alta de MSINIVAL***");
	try {
		HashMap<String, Object> paramMsinival = new HashMap<String, Object>();
		paramMsinival.put("pv_cdunieco_i",params.get("cdunieco"));
		paramMsinival.put("pv_cdramo_i",params.get("cdramo"));
		paramMsinival.put("pv_aaapertu_i",params.get("aaapertu"));
		paramMsinival.put("pv_status_i",params.get("status"));
		paramMsinival.put("pv_nmsinies_i",params.get("nmsinies"));
		paramMsinival.put("pv_cdgarant_i",params.get("cdgarant"));
		paramMsinival.put("pv_cdconval_i",params.get("cdconval"));
		paramMsinival.put("pv_cdcapita_i",params.get("cdcapita"));
		paramMsinival.put("pv_nmordina_i",params.get("nmordina"));
		paramMsinival.put("pv_femovimi_i",params.get("femovimi"));
		paramMsinival.put("pv_cdmoneda_i",params.get("cdmoneda"));
		paramMsinival.put("pv_ptpagos_i",params.get("ptpagos"));
		paramMsinival.put("pv_ptrecobr_i",params.get("ptrecobr"));
		paramMsinival.put("pv_ptimprec_i",params.get("ptimprec"));
		paramMsinival.put("pv_ptimpimp_i",params.get("ptimpimp"));
		paramMsinival.put("pv_factura_i",params.get("factura"));
		paramMsinival.put("pv_swlibera_i",params.get("swlibera"));
		paramMsinival.put("pv_cdtipmov_i",params.get("cdtipmov"));
		paramMsinival.put("pv_cdidemov_i",params.get("cdidemov"));
		siniestrosManager.getAltaMsinival(paramMsinival);
	}catch( Exception e){
		logger.error("Error al al generar el alta de MSINIVAL ",e);
			return SUCCESS;
		}
	   success = true;
	   return SUCCESS;
   }
   
   public String generarBajaMsinival(){
		logger.debug(" **** Entrando a generar baja de MSINIVAL***");
	try {
		HashMap<String, Object> paramBajasinival = new HashMap<String, Object>();
		paramBajasinival.put("pv_cdunieco_i",params.get("cdunieco"));
		paramBajasinival.put("pv_cdramo_i",params.get("cdramo"));
		paramBajasinival.put("pv_aaapertu_i",params.get("aaapertu"));
		paramBajasinival.put("pv_status_i",params.get("status"));
		paramBajasinival.put("pv_nmsinies_i",params.get("nmsinies"));
		siniestrosManager.getBajaMsinival(paramBajasinival);
	}catch( Exception e){
		logger.error("Error al al generar la baja de MSINIVAL ",e);
			return SUCCESS;
		}
	   success = true;
	   return SUCCESS;
   }
   
   public String consultaListaFacturas(){
		logger.debug(" **** Entrando a consulta de lista de facturas****");
		try {
	            HashMap<String, Object> paramFact = new HashMap<String, Object>();
	            paramFact.put("pv_cdunieco_i",params.get("cdunieco"));
	            paramFact.put("pv_cdramo_i",params.get("cdramo"));
	            paramFact.put("pv_aaapertu_i",params.get("aapertu"));
	            paramFact.put("pv_nmsinies_i",params.get("nmsinies"));
				List<ListaFacturasVO> lista = siniestrosManager.getConsultaListaFacturas(paramFact);
				
				if(lista!=null && !lista.isEmpty())	listaFacturas = lista;
		}catch( Exception e){
			logger.error("Error al obtener los datos de lista de facturas ",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
  }
   

   public String loadListaDocumentos(){
   	try {
   		loadList = siniestrosManager.loadListaDocumentos(params);
   	}catch( Exception e){
   		logger.error("Error en loadListaDocumentos",e);
   		success =  false;
   		return SUCCESS;
   	}
   	success = true;
   	return SUCCESS;
   }
   
   public String guardaListaDocumentos(){
   	
   	try {
   		logger.debug("SaveList: "+ saveList);
   		siniestrosManager.guardaEstatusDocumentos(params, saveList);
   	}catch( Exception e){
   		logger.error("Error en guardaListaDocumentos",e);
   		success =  false;
   		return SUCCESS;
   	}
   	success = true;
   	return SUCCESS;
   }

   public String validaDocumentosCargados(){
	   
	   try {
		   logger.debug("ValidaDocumentosCargados params: "+ params);
		   msgResult = siniestrosManager.validaDocumentosCargados(params);
		   logger.debug("Respuesta ValidaDocumentosCargados: "+ msgResult);
		   if(StringUtils.isBlank(msgResult)){
			   msgResult = "Error al realizar validaci&oacute; de documentos";
			   success = false;
		   }else if(Constantes.NO.equalsIgnoreCase(msgResult)){
			   msgResult = "No se han anexado todos los documentos, favor de subir todos los documentos marcados como entregados en el checklist.";
			   success =  false;
		   }else if(Constantes.SI.equalsIgnoreCase(msgResult)){
			   success =  true;
		   }else{
			   success =  false;
		   }
		   
	   }catch( Exception e){
		   logger.error("Error en validaDocumentosCargados",e);
		   success =  false;
		   return SUCCESS;
	   }
	   
	   return SUCCESS;
   }

   public String loadListaRechazos(){
	   	try {
	   		loadList = new ArrayList<HashMap<String,String>>();
	   		List<Map<String,String>>lista=siniestrosManager.loadListaRechazos();
	   		for(Map<String,String>ele:lista)
	   		{
	   			HashMap<String,String>map=new HashMap<String,String>();
	   			map.put("key"   , ele.get("CDMOTIVO"));
	   			map.put("value" , ele.get("DSMOTIVO"));
	   			loadList.add(map);
	   		}
	   	}catch( Exception e){
	   		logger.error("Error en loadListaRechazos",e);
	   		success =  false;
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
   }

   public String loadListaIncisosRechazos(){
	   try {
		   loadList = new ArrayList<HashMap<String,String>>();
		   List<Map<String,String>>lista= siniestrosManager.loadListaIncisosRechazos(params);
		   for(Map<String,String>ele:lista)
	   		{
	   			HashMap<String,String>map=new HashMap<String,String>();
	   			map.put("key"   , ele.get("CDCAUMOT"));
	   			map.put("value" , ele.get("DSCAUMOT"));
	   			loadList.add(map);
	   		}
	   }catch( Exception e){
		   logger.error("Error en loadListaIncisosRechazos",e);
		   success =  false;
		   return SUCCESS;
	   }
	   success = true;
	   return SUCCESS;
   }
   
   public String rechazarTramite(){
	   
	   try {
		   logger.debug("RechazarTramite Siniestros");
		   siniestrosManager.rechazarTramite(params);
	   }catch( Exception e){
		   logger.error("Error en rechazarTramite",e);
		   success =  false;
		   return SUCCESS;
	   }
	   success = true;
	   return SUCCESS;
   }

   public String generaCartaRechazo(){
	   
	   try {
		   
		   logger.debug("generaCartaRechazo Siniestros");
		   
		   UserVO usuario=(UserVO)session.get("USUARIO");
		   String cdrol    = usuario.getRolActivo().getClave();
		   String tipoPago = (String) paramsO.get("tipopago");
		   
		   String nombreRdf = "";
		   
		   if(RolSistema.COORDINADOR_MEDICO.getCdsisrol().equals(cdrol)
				   || RolSistema.COORDINADOR_MEDICO_MULTIREGIONAL.getCdsisrol().equals(cdrol)
				   || RolSistema.GERENTE_MEDICO_MULTIREGIONAL.getCdsisrol().equals(cdrol)
				   || RolSistema.MEDICO.getCdsisrol().equals(cdrol)
				   || RolSistema.MEDICO_AJUSTADOR.getCdsisrol().equals(cdrol)
				   ){
			   nombreRdf = getText("rdf.siniestro.cartarechazo.medico.nombre");
		   }else {
				if(TipoPago.DIRECTO.getCodigo().equals(tipoPago)){
					nombreRdf = getText("rdf.siniestro.cartarechazo.pagodirecto.nombre");
				} else if(TipoPago.REEMBOLSO.getCodigo().equals(tipoPago)){
					nombreRdf = getText("rdf.siniestro.cartarechazo.reembolso.nombre");
				}
		   }
		   
		   File carpeta=new File(getText("ruta.documentos.poliza") + "/" + paramsO.get("pv_ntramite_i"));
           if(!carpeta.exists()){
           		logger.debug("no existe la carpeta::: "+paramsO.get("pv_ntramite_i"));
           		carpeta.mkdir();
           		if(carpeta.exists()){
           			logger.debug("carpeta creada");
           		} else {
           			logger.debug("carpeta NO creada");
           		}
           } else {
           	 logger.debug("existe la carpeta   ::: "+paramsO.get("pv_ntramite_i"));
           }
           
           
           String urlContrareciboSiniestro = ""
           					   + getText("ruta.servidor.reports")
                               + "?p_usuario=" + usuario.getUser() 
                               + "&p_ntramite=" + paramsO.get("pv_ntramite_i")
                               + "&destype=cache"
                               + "&desformat=PDF"
                               + "&userid="+getText("pass.servidor.reports")
                               + "&ACCESSIBLE=YES"
                               + "&report="+ nombreRdf
                               + "&paramform=no"
                               ;
           String nombreArchivo = getText("pdf.siniestro.cartarechazo.nombre");
           String pathArchivo=""
           					+ getText("ruta.documentos.poliza")
           					+ "/" + paramsO.get("pv_ntramite_i")
           					+ "/" + nombreArchivo
           					;
           HttpUtil.generaArchivo(urlContrareciboSiniestro, pathArchivo);
           
           paramsO.put("pv_feinici_i"  , new Date());
           paramsO.put("pv_cddocume_i" , nombreArchivo);
           paramsO.put("pv_dsdocume_i" , "Carta Rechazo");
           paramsO.put("pv_swvisible_i"   , null);
           paramsO.put("pv_codidocu_i"   , null);
           paramsO.put("pv_cdtiptra_i"   , TipoTramite.SINIESTRO.getCdtiptra());
           kernelManagerSustituto.guardarArchivo(paramsO);
		   
	   }catch( Exception e){
		   logger.error("Error en generaCartaRechazo",e);
		   success =  false;
		   return SUCCESS;
	   }
	   success = true;
	   return SUCCESS;
   }

   public String generarContrarecibo(){
	   
	   try {
		    params =  new HashMap<String, String>();
		    params.put("pv_nmtramite_i", (String) paramsO.get("pv_ntramite_i"));
		    params.put("pv_cdtippag_i", (String) paramsO.get("pv_cdtippag_i"));
		    params.put("pv_cdtipate_i", (String) paramsO.get("pv_cdtipate_i"));
	   		loadList = siniestrosManager.loadListaDocumentos(params);
	   		
	   		
	   		if(loadList == null || loadList.isEmpty()){
	   			msgResult = "No se puede Generar el Contra Recibo. No hay documentos";
   				success = false;
   				return SUCCESS;
	   		}
	   		
	   		for(HashMap<String, String> doc: loadList){
	   			
	   			if( "Si".equalsIgnoreCase((String)doc.get("obligatorio")) && !(doc.get("listo")!= null && "true".equalsIgnoreCase((String)doc.get("listo")))){
	   				msgResult = "No se puede Generar el Contra Recibo ya que en Revision de Documentos no se han marcado como entregados todos los documentos obligatorios (checklist).";
	   				success = false;
	   				return SUCCESS;
	   			}
	   		}
	   	}catch( Exception e){
	   		logger.error("Error en loadListaDocumentos",e);
	   		msgResult = "Error al cargar documentos obligatorios";
	   		success =  false;
	   		return SUCCESS;
	   	}
	   
	   try {
		   logger.debug("generarContrarecibo Siniestros: "+ paramsO);
		   
		   if(Constantes.MSG_TITLE_ERROR.equals(siniestrosManager.generaContraRecibo(paramsO))){
			    msgResult = "Error al generar el n&uacute; de Contra Recibo";
		   		success =  false;
		   		return SUCCESS;
		   }
		   
		   File carpeta=new File(getText("ruta.documentos.poliza") + "/" + paramsO.get("pv_ntramite_i"));
           if(!carpeta.exists()){
           		logger.debug("no existe la carpeta::: "+paramsO.get("pv_ntramite_i"));
           		carpeta.mkdir();
           		if(carpeta.exists()){
           			logger.debug("carpeta creada");
           		} else {
           			logger.debug("carpeta NO creada");
           		}
           } else {
           	 logger.debug("existe la carpeta   ::: "+paramsO.get("pv_ntramite_i"));
           }
           
           UserVO usuario=(UserVO)session.get("USUARIO");
           
           String urlContrareciboSiniestro = ""
           					   + getText("ruta.servidor.reports")
                               + "?p_usuario=" + usuario.getUser() 
                               + "&p_TRAMITE=" + paramsO.get("pv_ntramite_i")
                               + "&destype=cache"
                               + "&desformat=PDF"
                               + "&userid="+getText("pass.servidor.reports")
                               + "&ACCESSIBLE=YES"
                               + "&report="+getText("rdf.siniestro.contrarecibo.nombre")
                               + "&paramform=no"
                               ;
           String nombreArchivo = getText("siniestro.contrarecibo.nombre");
           String pathArchivo=""
           					+ getText("ruta.documentos.poliza")
           					+ "/" + paramsO.get("pv_ntramite_i")
           					+ "/" + nombreArchivo
           					;
           HttpUtil.generaArchivo(urlContrareciboSiniestro, pathArchivo);
           
           paramsO.put("pv_feinici_i"  , new Date());
           paramsO.put("pv_cddocume_i" , nombreArchivo);
           paramsO.put("pv_dsdocume_i" , "Contra Recibo");
           paramsO.put("pv_swvisible_i"   , null);
           paramsO.put("pv_codidocu_i"   , null);
           paramsO.put("pv_cdtiptra_i"   , TipoTramite.SINIESTRO.getCdtiptra());
           kernelManagerSustituto.guardarArchivo(paramsO);
		   
	   }catch( Exception e){
		   logger.error("Error en generarContrarecibo",e);
		   success =  false;
		   return SUCCESS;
	   }
	   success = true;
	   return SUCCESS;
   }

   /*private void generarAutoriServicio(Map<String, Object> paramsO) {
		// TODO Auto-generated method stub
	   paramsO2.get(key)
	}
   */
   public String generarAutoriServicio(Map<String, Object> paramsO){
	   
	   
	   
	   try {
		   File carpeta=new File(getText("ruta.documentos.poliza") + "/" + paramsO.get("pv_ntramite_i"));
           if(!carpeta.exists()){
           		logger.debug("no existe la carpeta::: "+paramsO.get("pv_ntramite_i"));
           		carpeta.mkdir();
           		if(carpeta.exists()){
           			logger.debug("carpeta creada");
           		} else {
           			logger.debug("carpeta NO creada");
           		}
           } else {
           	 logger.debug("existe la carpeta   ::: "+paramsO.get("pv_ntramite_i"));
           }
           
           UserVO usuario=(UserVO)session.get("USUARIO");
           //urlContrareciboSiniestro
           String reporteSeleccion = null;
           if(paramsO.get("pv_cdramo_i").toString().equalsIgnoreCase("4")){
        	   reporteSeleccion = getText("rdf.siniestro.autorizacion.servicio.nombre.MS");
           }else{
        	   reporteSeleccion = getText("rdf.siniestro.autorizacion.servicio.nombre");
           }
           String urlAutorizacionServicio = ""
           					   + getText("ruta.servidor.reports")
                               + "?p_unieco=" +  paramsO.get("pv_cdunieco_i")
                               + "&p_ramo=" + paramsO.get("pv_cdramo_i")
                               + "&p_estado=" + paramsO.get("pv_estado_i")
                               + "&p_poliza=" + paramsO.get("pv_nmpoliza_i")
                               + "&P_AUTSER=" + paramsO.get("pv_nmAutSer_i")
                               + "&P_CDPERSON=" + paramsO.get("pv_cdperson_i")
                               + "&destype=cache"
                               + "&desformat=PDF"
                               + "&userid="+getText("pass.servidor.reports")
                               + "&ACCESSIBLE=YES"
                               + "&report="+reporteSeleccion
                               + "&paramform=no"
                               ;
           logger.debug(urlAutorizacionServicio);
           String nombreArchivo = getText("siniestro.autorizacionServicio.nombre");
           String pathArchivo=""
           					+ getText("ruta.documentos.poliza")
           					+ "/" + paramsO.get("pv_ntramite_i")
           					+ "/" + nombreArchivo
           					;
           HttpUtil.generaArchivo(urlAutorizacionServicio, pathArchivo);
           
           paramsO.put("pv_feinici_i"  , new Date());
           paramsO.put("pv_cddocume_i" , nombreArchivo);
           paramsO.put("pv_dsdocume_i" , "Autorizacion Servicio");
           paramsO.put("pv_swvisible_i"   , null);
           paramsO.put("pv_codidocu_i"   , null);
           paramsO.put("pv_cdtiptra_i"   , TipoTramite.AUTORIZACION_SERVICIOS.getCdtiptra());
           paramsO.put("pv_nmsolici_i",null);
           paramsO.put("pv_tipmov_i",TipoTramite.AUTORIZACION_SERVICIOS.getCdtiptra());
           logger.debug(paramsO);
           kernelManagerSustituto.guardarArchivo(paramsO);
		   
	   }catch( Exception e){
		   logger.error("Error al generar la autorizaci�n de Servicio ",e);
		   success =  false;
		   return SUCCESS;
	   }
	   success = true;
	   return SUCCESS;
   }
   public String solicitarPago(){
	   
	   try {
		   logger.debug("solicitarPago Siniestros params: "+ params);
		   UserVO usuario  = (UserVO)session.get("USUARIO");
		   
		   RespuestaVO res = ice2sigsService.ejecutaWSreclamosTramite(params.get("pv_ntramite_i"), Operacion.INSERTA, false, usuario);
		   success = res.isSuccess();
		   mensaje = res.getMensaje();
		   
		   if(success){
			   List<SiniestroVO> siniestrosTramite = siniestrosManager.solicitudPagoEnviada(params);
			   
			   paramsO = new HashMap<String, Object>();
			   paramsO.putAll(params);
			   
			   String nombreRdf = getText("rdf.siniestro.cartafiniquito.nombre");
			   
			   File carpeta=new File(getText("ruta.documentos.poliza") + "/" + paramsO.get("pv_ntramite_i"));
	           if(!carpeta.exists()){
	           		logger.debug("no existe la carpeta::: "+paramsO.get("pv_ntramite_i"));
	           		carpeta.mkdir();
	           		if(carpeta.exists()){
	           			logger.debug("carpeta creada");
	           		} else {
	           			logger.debug("carpeta NO creada");
	           		}
	           } else {
	           	 logger.debug("existe la carpeta   ::: "+paramsO.get("pv_ntramite_i"));
	           }
	           
	           for(SiniestroVO siniestro : siniestrosTramite){
	        	   String urlFiniquitoSiniestro = ""
       					   + getText("ruta.servidor.reports")
                           + "?p_unieco=" + siniestro.getCdunieco() 
                           + "&p_ramo="   + siniestro.getCdramo()
                           + "&p_estado=" + siniestro.getEstado()
                           + "&p_poliza=" + siniestro.getNmpoliza()
                           + "&p_suplem=" + siniestro.getNmsuplem()
                           + "&p_nmsituac="+ siniestro.getNmsituac()
                           + "&p_aaapertu="+ siniestro.getAapertu()
                           + "&p_status="  + siniestro.getStatusSinies()
                           + "&p_sinies="+ siniestro.getNmsinies()
                           + "&destype=cache"
                           + "&desformat=PDF"
                           + "&userid="+getText("pass.servidor.reports")
                           + "&ACCESSIBLE=YES"
                           + "&report="+ nombreRdf
                           + "&paramform=no"
                           ;
			       String nombreArchivo = siniestro.getNmsinies() +"_"+ siniestro.getAapertu() +"_" + getText("pdf.siniestro.finiquito.nombre");
			       String pathArchivo=""
			       					+ getText("ruta.documentos.poliza")
			       					+ "/" + paramsO.get("pv_ntramite_i")
			       					+ "/" + nombreArchivo
			       					;
			       HttpUtil.generaArchivo(urlFiniquitoSiniestro, pathArchivo);
			       
			       paramsO.put("pv_cdunieco_i"  , siniestro.getCdunieco());
			       paramsO.put("pv_cdramo_i"  , siniestro.getCdramo());
			       paramsO.put("pv_estado_i"  , siniestro.getEstado());
			       paramsO.put("pv_nmpoliza_i"  , siniestro.getNmpoliza());
			       paramsO.put("pv_nmsuplem_i"  , siniestro.getNmsuplem());
			       paramsO.put("pv_feinici_i"  , new Date());
			       paramsO.put("pv_cddocume_i" , nombreArchivo);
			       paramsO.put("pv_dsdocume_i" , "Carta Finiquito");
			       paramsO.put("pv_nmsolici_i" , siniestro.getNmpoliza());
			       paramsO.put("pv_swvisible_i"   , null);
			       paramsO.put("pv_codidocu_i"   , null);
			       paramsO.put("pv_cdtiptra_i"   , TipoTramite.SINIESTRO.getCdtiptra());
			       kernelManagerSustituto.guardarArchivo(paramsO);
	           }
		   }
	   }catch( Exception e){
		   logger.error("Error en solicitarPago generacion de cartas finiquito",e);
		   success =  false;
		   mensaje = "Error al solicitar Pago, generaci&oacute;n de cartas finiquito. Consulte a Soporte T&eacute;cnico.";
		   return SUCCESS;
	   }
	   
	   return SUCCESS;
   }

   public String generaSiniestroTramite(){
	   
	   try {
		   
		   logger.debug("generaSiniestroTramite Siniestros, params:" + params);
		   siniestrosManager.getAltaSiniestroAltaTramite(params.get("pv_ntramite_i"));
	   }catch( Exception e){
		   logger.error("Error en generaSiniestroTramite",e);
		   success =  false;
		   return SUCCESS;
	   }
	   success = true;
	   return SUCCESS;
   }
	
   
   /**
    * Funci�n que obtiene la lista del asegurado
    * @param void sin parametros de entrada
    * @return Lista GenericVO con la informaci�n de los asegurados
    */    
   public String consultaListaTipoAtencion(){
   	logger.debug(" **** consultaListaTipoAtencion ****");
	   	try {
	   		listaTipoAtencion= siniestrosManager.getconsultaListaTipoAtencion(params.get("cdramo"), params.get("tipoPago"));
	   		logger.debug(listaTipoAtencion);
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de los asegurados ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
   }
   
   
   public List<GenericVO> getListaTipoAtencion() {
	return listaTipoAtencion;
}


public void setListaTipoAtencion(List<GenericVO> listaTipoAtencion) {
	this.listaTipoAtencion = listaTipoAtencion;
}


public String consultaListaPlazas(){
	   	logger.debug(" **** Entrando al m�todo de Lista de Plazas ****");
		   	try {
		   		listaPlazas= siniestrosManager.getConsultaListaPlaza();
		   	}catch( Exception e){
		   		logger.error("Error al consultar la Lista de los asegurados ",e);
		   		return SUCCESS;
		   	}
		   	success = true;
		   	return SUCCESS;
	   }
   
   public String consultaNumeroDias(){
	   	logger.debug(" **** Entrando al metodo para obtener los numeros  de dias ****");
	   	try {
	   		diasMaximos= catalogosManager.obtieneCantidadMaxima(params.get("cdramo"), params.get("cdtipsit"), TipoTramite.SINIESTRO, Rango.DIAS, Validacion.DIAS_MAX_AUTORIZACION_SERVICIOS);
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de los asegurados ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
   }
   
   public String consultaMontoMaximo(){
	   	logger.debug(" **** Entrando al metodo para obtener los numeros  de dias ****");
	   	try {
	   		montoMaximo = catalogosManager.obtieneCantidadMaxima(params.get("cdramo"), params.get("cdtipsit"), TipoTramite.SINIESTRO, Rango.PESOS, Validacion.MONTO_MAXIMO_AUTORIZACION_SERVICIOS);
	   	}catch( Exception e){
	   		logger.error("Error al consultar el monto maximo",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
  }
   
   public String consultaMesesMaximoMaternidad(){
	   	logger.debug(" **** Entrando al metodo para obtener el numero de meses max para validar Maternidad****");
	   	try {
	   		mesMaximoMaternidad = catalogosManager.obtieneCantidadMaxima(params.get("cdramo"), params.get("cdtipsit"), TipoTramite.SINIESTRO, Rango.MESES, Validacion.MESES_MAX_MATERNIDAD);
	   	}catch( Exception e){
	   		logger.error("Error al consultar el numero de meses maximo para maternidad ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
 }
   
   public String validaExclusionPenalizacion(){
	   	logger.debug(" **** Entrando al metodo de validacion de Penalizacion ****");
	   	try {
		   		HashMap<String, Object> paramExclusion = new HashMap<String, Object>();
		   		paramExclusion.put("pv_cdunieco_i",params.get("cdunieco"));
		   		paramExclusion.put("pv_estado_i",params.get("estado"));
		   		paramExclusion.put("pv_cdramo_i",params.get("cdramo"));
		   		paramExclusion.put("pv_nmpoliza_i",params.get("nmpoliza"));
		   		paramExclusion.put("pv_nmsituac_i",params.get("nmsituac"));
		   		existePenalizacion = siniestrosManager.validaExclusionPenalizacion(paramExclusion);
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de los asegurados ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
  }
   
   public String obtieneMontoArancel(){
	   	logger.debug(" **** Entrando al metodo de obtener monto del arancel  ****");
	   	logger.debug(params);
	   	try {
	   		String tipoConcepto = null;
	   		String claveProveedor = null;
	   		String idConceptoRegis = null;
	   		if(params.get("tipoConcepto") != null){
	   			tipoConcepto = params.get("tipoConcepto");
	   		}
			if(params.get("idProveedor") != null){
				claveProveedor = params.get("idProveedor");
	   		}
			if(params.get("idConceptoTipo") != null){
				idConceptoRegis = params.get("idConceptoTipo");
	   		}


	   		montoArancel = siniestrosManager.obtieneMontoArancelCPT(tipoConcepto ,claveProveedor,idConceptoRegis);
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de los asegurados ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
 }
   
   
   public String obtieneRequiereAutServ(){
	   	logger.debug(" **** Entrando al metodo para verificar si requiere autorizaci�n de servicio****");
	   	try {
	   		datosInformacionAdicional = siniestrosManager.requiereInformacionAdicional(params.get("cobertura"),params.get("subcobertura"));
	   	}catch( Exception e){
	   		logger.error("Error al obtener si requiere autorizacion servicio ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
}
   
   public String validaAutorizacionProceso(){
	   	logger.debug(" **** Entrando al metodo de validacion de Penalizacion ****");
	   	try {
		   		autorizarProceso = siniestrosManager.validaAutorizacionProceso(params.get("nmAutSer"));
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de los asegurados ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
 }
   public String validaPorcentajePenalizacion(){
	   	logger.debug(" **** Entrando al metodo de porcentaje de validaci�n ****");
	   	try {
	   		porcentajePenalizacion = siniestrosManager.validaPorcentajePenalizacion(params.get("zonaContratada"), params.get("zonaAtencion"), params.get("cdRamo"));
	   	}catch( Exception e){
	   		logger.error("Error al consultar al metodo de porcentaje de penalizaci�n ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
 }
   
    public String pantallaSeleccionCobertura()
    {
    	logger.debug(""
    			+ "\n########################################"
    			+ "\n########################################"
    			+ "\n###### pantallaSeleccionCobertura ######"
    			+ "\n######                            ######"
    			);
    	logger.debug("params: "+params);
    	
    	try
    	{
	    	UserVO usuario  = (UserVO)session.get("USUARIO");
	    	String cdrol    = usuario.getRolActivo().getClave();
	    	params.put("cdrol", cdrol);
	    	String pantalla = "SELECCION_COBERTURA";
	    	String seccion  = "FORMULARIO";
	    	imap = new HashMap<String,Item>();
	    	
	    	List<ComponenteVO> componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
	    	
	    	gc.generaComponentes(componentes, true, false, true, false, false, false);
	    	
	    	//item=gc.getItems();
	    	imap.put("item",gc.getItems());
	    	
	    	seccion = "COLUMNAS";
	    	componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	gc.generaComponentes(componentes, true, true, false, true, false, false);
	    	
	    	imap.put("columnas",gc.getColumns());
	    	imap.put("modelFacturas", gc.getFields());
	    	
	    	
    	}
    	catch(Exception ex)
    	{
    		logger.error("error al cargar pantalla de seleccion de cobertura",ex);
    	}
    	
    	logger.debug(""
    			+ "\n######                            ######"
    			+ "\n###### pantallaSeleccionCobertura ######"
    			+ "\n########################################"
    			+ "\n########################################"
    			);
    	return SUCCESS;
    }
    
    /*
    params:
    	cdramo   = 2,
    	cdgarant = 18HO,
    	cdtipsit = SL,
    	cdconval = 18HO001,
    	cdunieco = 1006,
    	ntramite = 1010
    */
    public String guardarSeleccionCobertura()
    {
    	logger.debug(""
    			+ "\n#######################################"
    			+ "\n#######################################"
    			+ "\n###### guardarSeleccionCobertura ######"
    			+ "\n######                           ######"
    			);
    	logger.debug("params: "+params);
    	
    	try
    	{
    		String cdramo   = params.get("cdramo");
    		String cdtipsit = params.get("cdtipsit");
    		String cdgarant = params.get("cdgarant");
    		String cdconval = params.get("cdconval");
    		String ntramite = params.get("ntramite");
    		
    		Map<String,Object> otvalor = new HashMap<String,Object>();
    		otvalor.put("pv_ntramite_i" , ntramite);
    		otvalor.put("pv_cdramo_i"   , cdramo);
    		otvalor.put("pv_cdtipsit_i" , cdtipsit);
    		otvalor.put("pv_otvalor12_i"  , cdgarant);
    		otvalor.put("pv_otvalor14_i"  , cdconval);
    		siniestrosManager.actualizaOTValorMesaControl(otvalor);
    		
    		List<Map<String,String>> facturas  = siniestrosManager.obtenerFacturasTramite(ntramite);
    		
    		for(Map<String,String>factura:facturas)
    		{
	    		String                   nfactura  = factura.get("NFACTURA");
	    		String                   fefactura = factura.get("FFACTURA");
	    		String                   cdtipser  = factura.get("CDTIPSER");
	    		String                   cdpresta  = factura.get("CDPRESTA");
	    		String                   ptimport  = factura.get("PTIMPORT");
	    		String                   descporc  = factura.get("DESCPORC");
	    		String                   descnume  = factura.get("DESCNUME");
	    		String                   cdmoneda  = factura.get("CDMONEDA");
	    		String                   tasacamb  = factura.get("TASACAMB");
	    		String                   ptimporta = factura.get("PTIMPORTA");
	    		String                   dctonuex = factura.get("DCTONUEX");
	    		
	    		siniestrosManager.guardaListaFacMesaControl(ntramite, nfactura, fefactura, cdtipser, cdpresta, ptimport, cdgarant, cdconval, descporc, descnume,cdmoneda,tasacamb,ptimporta,dctonuex,null);
    		}
    		
    		success = true;
    		mensaje = "Tr&aacute;mite actualizado";
    	}
    	catch(Exception ex)
    	{
    		success=false;
    		logger.error("error al seleccionar la cobertura",ex);
    		mensaje = ex.getMessage();
    	}
    	
    	logger.debug(""
    			+ "\n######                           ######"
    			+ "\n###### guardarSeleccionCobertura ######"
    			+ "\n#######################################"
    			+ "\n#######################################"
    			);
    	return SUCCESS;
    }
    
    
    public String guardarSeleccionCoberturaxTramite()
    {
    	logger.debug(""
    			+ "\n###############################################"
    			+ "\n###############################################"
    			+ "\n###### guardarSeleccionCoberturaxTramite ######"
    			+ "\n######                           		  ######"
    			);
    	logger.debug("params: "+params);
    	
    	try
    	{
    		String cdramo   = params.get("cdramo");
    		String cdtipsit = params.get("cdtipsit");
    		String cdgarant = params.get("cdgarant");
    		String cdconval = params.get("cdconval");
    		String ntramite = params.get("ntramite");
    		
    		Map<String,Object> otvalor = new HashMap<String,Object>();
    		otvalor.put("pv_ntramite_i" , ntramite);
    		otvalor.put("pv_cdramo_i"   , cdramo);
    		otvalor.put("pv_cdtipsit_i" , cdtipsit);
    		otvalor.put("pv_otvalor12_i"  , cdgarant);
    		otvalor.put("pv_otvalor14_i"  , cdconval);
    		siniestrosManager.actualizaOTValorMesaControl(otvalor);
    		
    		success = true;
    		mensaje = "Tr&aacute;mite actualizado";
    	}
    	catch(Exception ex)
    	{
    		success=false;
    		logger.error("error al seleccionar la cobertura",ex);
    		mensaje = ex.getMessage();
    	}
    	
    	logger.debug(""
    			+ "\n######                           		  ######"
    			+ "\n###### guardarSeleccionCoberturaxTramite ######"
    			+ "\n###############################################"
    			+ "\n###############################################"
    			);
    	return SUCCESS;
    }
    
    public String guardarConceptoDestino()
    {
    	logger.debug(""
    			+ "\n###############################################"
    			+ "\n###############################################"
    			+ "\n###### guardarConceptoDestino			  ######"
    			+ "\n######                           		  ######"
    			);
    	logger.debug("params: "+params);
    	
    	try
    	{
    		String ntramite = params.get("ntramite");
    		String cdtipsit = params.get("cdtipsit");
    		String cdDestinoPago = params.get("destinoPago");
    		String cdConceptoPago = params.get("concepPago");
    		
    		Map<String,Object> otvalor = new HashMap<String,Object>();
    		otvalor.put("pv_ntramite_i" , ntramite);
    		otvalor.put("pv_cdtipsit_i" , cdtipsit);
    		otvalor.put("pv_otvalor18_i"  , cdDestinoPago);
    		otvalor.put("pv_otvalor19_i"  , cdConceptoPago);
    		siniestrosManager.actualizaOTValorMesaControl(otvalor);
    		
    		success = true;
    		mensaje = "Tr&aacute;mite actualizado";
    	}
    	catch(Exception ex)
    	{
    		success=false;
    		logger.error("error al seleccionar la cobertura",ex);
    		mensaje = ex.getMessage();
    	}
    	
    	logger.debug(""
    			+ "\n######                           		  ######"
    			+ "\n###### 	guardarConceptoDestino 	      ######"
    			+ "\n###############################################"
    			+ "\n###############################################"
    			);
    	return SUCCESS;
    }
    
    public String guardarCoberturaxFactura()
    {
    	logger.debug(""
    			+ "\n#######################################"
    			+ "\n#######################################"
    			+ "\n###### guardarCoberturaxFactura  ######"
    			+ "\n######                           ######"
    			);
    	logger.debug("params: "+params);
    	
    	try
    	{
    		
    		String                   ntramite  = params.get("ntramite");
			String                   nfactura  = params.get("nfactura");
			String                   fefactura = params.get("ffactura");
    		String                   cdtipser  = params.get("cdtipser");
    		String                   cdpresta  = params.get("cdpresta");
    		String                   ptimport  = params.get("ptimport");
    		String                   descporc  = params.get("descporc");
    		String                   descnume  = params.get("descnume");
    		String                   cdmoneda  = params.get("cdmoneda");
    		String                   tasacamb  = params.get("tasacamb");
    		String                   ptimporta = params.get("ptimporta");
    		String                   dctonuex =  params.get("dctonuex");
    		String                   cdgarant =  params.get("cdgarant");
    		String                   cdconval =  params.get("cdconval");
    		String                   tipoAccion =  params.get("tipoAccion");
    		
    		siniestrosManager.guardaListaFacMesaControl(ntramite, nfactura, fefactura, cdtipser, cdpresta, ptimport, cdgarant, cdconval, descporc, descnume,cdmoneda,tasacamb,ptimporta,dctonuex,tipoAccion);
    		
    		success = true;
    		mensaje = "Cobertura y subcobertura modificada";
    	}
    	catch(Exception ex)
    	{
    		success=false;
    		logger.error("error al seleccionar la cobertura",ex);
    		mensaje = ex.getMessage();
    	}
    	
    	logger.debug(""
    			+ "\n######                           ######"
    			+ "\n###### guardarSeleccionCobertura ######"
    			+ "\n#######################################"
    			+ "\n#######################################"
    			);
    	return SUCCESS;
    }
    
    public String afiliadosAfectados()
    {
    	logger.info(""
    			+ "\n################################"
    			+ "\n################################"
    			+ "\n###### afiliadosAfectados ######"
    			+ "\n######                    ######"
    			);
    	logger.info("params: "+params);
    	
    	try
    	{
    		String ntramite = params.get("ntramite");
    		
    		if(ntramite==null)
    		{
    			throw new Exception("No hay tramite");
    		}
    		
    		params = (HashMap<String, String>) siniestrosManager.obtenerTramiteCompleto(ntramite);
    		
    		List<Map<String,String>> facturas = siniestrosManager.obtenerFacturasTramite(ntramite);
    		
    		if(facturas==null || facturas.size()==0)
    		{
    			throw new Exception("No se encuentra la factura");
    		}
    		
    		Map<String,String> factura = facturas.get(0);
    		params.put("OTVALOR12",factura.get("CDGARANT"));
    		params.put("OTVALOR14",factura.get("CDCONVAL"));
    		
    		params.put("DESCPORC",factura.get("DESCPORC"));
    		params.put("DESCNUME",factura.get("DESCNUME"));
    		
    		UserVO usuario  = (UserVO)session.get("USUARIO");
	    	String cdrol    = usuario.getRolActivo().getClave();
	    	params.put("cdrol", cdrol);
	    	String pantalla = "AFILIADOS_AGRUPADOS";
	    	String seccion  = "FORMULARIO";
	    	
	    	List<ComponenteVO> componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
	    	
	    	gc.generaComponentes(componentes, true, false, true, false, false, false);
	    	
	    	imap = new HashMap<String,Item>();
	    	imap.put("itemsForm",gc.getItems());
	    	
	    	String cobertura= factura.get("CDGARANT");
	    	String subcobertura = factura.get("CDCONVAL");
	    	String valorComplementario = "0";
	    	
	    	/*List<Map<String,String>>  DatosInformacionAdicional = siniestrosManager.requiereInformacionAdicional(cobertura,subcobertura);
	    	
	    	String requiereAutorizacion = DatosInformacionAdicional.get(0).get("REQAUTSERV");//ser� otvalor8
	    	//String requiereAutorizacion = siniestrosManager.requiereAutorizacionServ(cobertura, subcobertura);
	    	if(requiereAutorizacion.equalsIgnoreCase("OP")){
	    		valorComplementario = "1";
	    	}*/
	    	
	    	slist2=siniestrosManager.obtenerFacturasTramite(ntramite);
	    	logger.debug("#####VALOR DE LAS FACTURAS#####");
	    	logger.debug(slist2);
	    	seccion = "COLUMNAS";
	    	
	    	componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	for(ComponenteVO com:componentes)
	    	{
	    		com.setWidth(100);
	    	}
	    	
	    	gc.generaComponentes(componentes, true, false, false, true, false, false);
	    	
	    	imap.put("columnas",gc.getColumns());
	    	
	    	pantalla = "RECHAZO_SINIESTRO";
	    	seccion  = "FORMULARIO";
	    	
	    	componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	gc.generaComponentes(componentes, true, false, true, false, false, false);
	    	
	    	imap.put("itemsCancelar",gc.getItems());
	    	
	    	
	    	pantalla = "DETALLE_FACTURA";
	    	seccion = "FORM_EDICION";
	    	componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	gc.generaComponentes(componentes, true, false, true, false, false, false);
	    	imap.put("itemsEdicion",gc.getItems());
	    	
	    	List<ComponenteVO>tatrisin=kernelManagerSustituto.obtenerTatrisinPoliza("1000","2","M","53");
	    	gc.generaComponentes(tatrisin, true, false, true, false, false, false);
	    	imap.put("tatrisinItems",gc.getItems());
    	}
    	catch(Exception ex)
    	{
    		logger.error("error al cargar pantalla de asegurados afectados",ex);
    	}
    	
    	logger.info(""
    			+ "\n######                    ######"
    			+ "\n###### afiliadosAfectados ######"
    			+ "\n################################"
    			+ "\n################################"
    			);
    	return SUCCESS;
    }
	
    /*
    params:
    	OTVALOR11=101, 
    	CDTIPSIT=SL,
    	OTVALOR12=18HO, 
    	OTVALOR07=3, 
    	OTVALOR13=18HO001, 
    	OTVALOR08=696913, 
    	CDSUCDOC=1006, 
    	OTVALOR06=25/02/2014, 
    	CDRAMO=2, 
    	OTVALOR03=3000, 
    	ntramite=1397
    */
    public String guardarAfiliadosAfectados()
    {
    	logger.debug(""
    			+ "\n#######################################"
    			+ "\n#######################################"
    			+ "\n###### guardarAfiliadosAfectados ######"
    			+ "\n######                           ######"
    			);
    	logger.debug("params: "+params);
    	
    	try
    	{
    		String ntramite = params.get("ntramite");
    		String cdramo   = params.get("CDRAMO");
    		String cdtipsit = params.get("CDTIPSIT");
    		String importe  = params.get("OTVALOR03");
    		String feFactu  = params.get("OTVALOR06");
    		String tipoate  = params.get("OTVALOR07");
    		String factura  = params.get("OTVALOR08");
    		String cdprove  = params.get("OTVALOR11");
    		String cdgarant = params.get("OTVALOR12");
    		String cdconval = params.get("OTVALOR14");
    		String descporc = params.get("DESCPORC");
    		String descnume = params.get("DESCNUME");
    		
    		siniestrosManager.guardaListaFacMesaControl(
    				ntramite, 
    				factura, 
    				feFactu, 
    				tipoate, 
    				cdprove, 
    				importe, 
    				cdgarant,
    				cdconval,
    				descporc, 
    				descnume,
    				"001",
    				"0",
    				"0",
    				"0",
    				null);
    		
    		Map<String,Object> otvalor = new HashMap<String,Object>();
    		otvalor.put("pv_ntramite_i"  , ntramite);
    		otvalor.put("pv_cdramo_i"    , cdramo);
    		otvalor.put("pv_cdtipsit_i"  , cdtipsit);
    		otvalor.put("pv_otvalor03_i" , importe);
    		otvalor.put("pv_otvalor06_i" , feFactu);
    		otvalor.put("pv_otvalor07_i" , tipoate);
    		otvalor.put("pv_otvalor08_i" , factura);
    		otvalor.put("pv_otvalor11_i" , cdprove);
    		otvalor.put("pv_otvalor12_i" , cdgarant);
    		otvalor.put("pv_otvalor14_i" , cdconval);
    		siniestrosManager.actualizaOTValorMesaControl(otvalor);
    		
    		mensaje = "Datos guardados correctamente";
    		success=true;
    	}
    	catch(Exception ex)
    	{
    		logger.error("error al guardar afiliados afectados",ex);
    		success=false;
    		mensaje=ex.getMessage();
    	}
    	
    	logger.debug(""
    			+ "\n######                           ######"
    			+ "\n###### guardarAfiliadosAfectados ######"
    			+ "\n#######################################"
    			+ "\n#######################################"
    			);
    	return SUCCESS;
    }
    
    /*
    params:
    	nmautser=123, 
    	cdperson=514262, 
    	nmpoliza=42, 
    	ntramite=1428
    */
    public String iniciarSiniestroTworksin()
    {
    	logger.debug(""
    			+ "\n######################################"
    			+ "\n######################################"
    			+ "\n###### iniciarSiniestroTworksin ######"
    			+ "\n######                          ######"
    			);
    	logger.debug("params: "+params);
    	try
    	{
    		String nmautser = params.get("nmautser");
    		String cdperson = params.get("cdperson");
    		String nmpoliza = params.get("nmpoliza");
    		String ntramite = params.get("ntramite");
    		String nfactura = params.get("nfactura");
    		String feocurrencia = params.get("feocurrencia");

    		//CUANDO SE PIDE EL NUMERO DE AUTORIZACION DE SERVICIO EN PANTALLA
    		//SE EJECUTAN LOS SIGUIENTES PL:
    		
    		//INSERTA EL NUMERO DE AUTORIZACION EN TWORKSIN
    		siniestrosManager.actualizarAutorizacionTworksin(ntramite,nmpoliza,cdperson,nmautser,nfactura,feocurrencia);
    		
    		//CREA UN MSINIEST A PARTIR DE TWORKSIN
    		siniestrosManager.getAltaSiniestroAutServicio(nmautser,nfactura);
    		
    		mensaje = "Se ha asociado el siniestro con la autorizaci&oacute;n";
    		success=true;
    	}
    	catch(Exception ex)
    	{
    		logger.error("error al inicar siniestro desde tworksin",ex);
    		mensaje = ex.getMessage();
    		success=false;
    	}
    	logger.debug(""
    			+ "\n######                          ######"
    			+ "\n###### iniciarSiniestroTworksin ######"
    			+ "\n######################################"
    			+ "\n######################################"
    			);
    	return SUCCESS;
    }
    
    
    
    /*
    params:
    	_icd: "A600"
		_icd2: "C761"
		aaapertu: 2014
		autmedic: "N"
		autrecla: "S"
		cdicd: ""
		cdicd2: ""
		cdperson: "517982"
		cdramo: "2"
		cdunieco: "1006"
		commenar: "OK"
		commenme: "OK"
		estado: "M"
		feocurre: "01/01/2014"
		nmautser: "12"
		nmpoliza: "44"
		nmsinies: "20"
		nmsituac: "1"
		nmsuplem: "245671518430000000"
		nombre: "ALVAROJAIR,MARTINEZ VARELA"
		nreclamo: "6960"
		ntramite: "1445"
		--cancelar: "si"      solo con cancelacion
		--cdmotivo: "9"       solo con cancelacion
		--cdsubmotivo: "1"    solo con cancelacion
		--rechazocomment:     solo con cancelacion
		status: "W"
     */
    public String actualizarMultiSiniestro()
    {
    	this.session = ActionContext.getContext().getSession();
    	logger.debug(""
    			+ "\n######################################"
    			+ "\n######################################"
    			+ "\n###### actualizarMultiSiniestro ######"
    			+ "\n######                          ######"
    			);
    	logger.debug("params: "+params);
    	try
    	{
    		String cdunieco  = params.get("cdunieco");
    		String cdramo    = params.get("cdramo");
    		String estado    = params.get("estado");
    		String nmpoliza  = params.get("nmpoliza");
    		String nmsituac  = params.get("nmsituac");
    		String nmsuplem  = params.get("nmsuplem");
    		String status    = params.get("status");
    		String aaapertu  = params.get("aaapertu");
    		String nmsinies  = params.get("nmsinies");
    		
    		String feocurre  = params.get("feocurre");
    		Date   dFeocurre = renderFechas.parse(feocurre);
    		String cdicd     = params.get("cdicd");
    		String cdicd2    = params.get("cdicd2");
    		String nreclamo  = params.get("nreclamo");
    		
    		String autrecla = params.get("autrecla");
    		String commenar = params.get("commenar");
    		String autmedic = params.get("autmedic");
    		String commenme = params.get("commenme");
    		
    		boolean cancela     = StringUtils.isNotBlank(params.get("cancelar"));
    		String  cdmotivo    = params.get("cdmotivo");
    		String  rechazoCome = params.get("rechazocomment");
    		String  ntramite    = params.get("ntramite");
    		
    		UserVO usuario = (UserVO)session.get("USUARIO");
    		String cdrol   = usuario.getRolActivo().getClave();
    		
    		Map<String,String>tramiteCompleto = siniestrosManager.obtenerTramiteCompleto(ntramite);
			String tipoPago = tramiteCompleto.get("OTVALOR02");
    		
    		siniestrosManager.actualizaMsinies(
    				cdunieco,
    				cdramo,
    				estado,
    				nmpoliza,
    				nmsituac,
    				nmsuplem,
    				status,
    				aaapertu,
    				nmsinies,
    				
    				dFeocurre,
    				cdicd,
    				cdicd2,
    				nreclamo);
    		
    		siniestrosManager.P_MOV_MAUTSINI(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, null,
    				null,null,null,null,null,
    				Constantes.MAUTSINI_AREA_RECLAMACIONES, autrecla, Constantes.MAUTSINI_SINIESTRO, commenar, Constantes.INSERT_MODE);
    		
    		siniestrosManager.P_MOV_MAUTSINI(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, null,
    				null,null,null,null,null,
    				Constantes.MAUTSINI_AREA_MEDICA, autmedic, Constantes.MAUTSINI_SINIESTRO, commenme, Constantes.INSERT_MODE);
    		
    		if(cancela)
    		{
    			Boolean rolMedico = null;
    			if(cdrol.equalsIgnoreCase(RolSistema.COORDINADOR_MEDICO.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.COORDINADOR_MEDICO_MULTIREGIONAL.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.GERENTE_MEDICO_MULTIREGIONAL.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.MEDICO.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.MEDICO_AJUSTADOR.getCdsisrol())
    					)
    			{
    				rolMedico = Boolean.TRUE;
    			}
    			else if(cdrol.equalsIgnoreCase(RolSistema.COORDINADOR_SINIESTROS.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.OPERADOR_SINIESTROS.getCdsisrol())
    					)
    			{
    				rolMedico = Boolean.FALSE;
    			}
    			
    			if(rolMedico==null)
    			{
    				throw new Exception("El usuario actual no puede cancelar");
    			}
    			
    			MesaControlAction mca = new MesaControlAction();
    			mca.setKernelManager(kernelManagerSustituto);
    			mca.setSession(session);
    			Map<String,String>smap1=new HashMap<String,String>();
    			smap1.put("ntramite" , ntramite);
    			smap1.put("status"   , EstatusTramite.RECHAZADO.getCodigo());
    			smap1.put("cdmotivo" , cdmotivo);
    			smap1.put("comments" , rechazoCome);
    			mca.setSmap1(smap1);
    			mca.actualizarStatusTramite();
    			if(!mca.isSuccess())
    			{
    				throw new Exception("Error al cancelar el tr�mite");
    			}
    			
    			String nombreReporte = null;
    			String nombreArchivo = null;
    			if(rolMedico)
    			{
    				nombreReporte = getText("rdf.siniestro.cartarechazo.medico.nombre");
    				nombreArchivo = getText("pdf.siniestro.rechazo.medico.nombre");
    			}
    			else//cancelacion por area de reclamaciones
    			{
    				boolean esReembolso = tipoPago.equalsIgnoreCase(TipoPago.REEMBOLSO.getCodigo());
    				if(esReembolso)
    				{
    					nombreReporte = getText("rdf.siniestro.cartarechazo.reembolso.nombre");
        				nombreArchivo = getText("pdf.siniestro.rechazo.reemb.nombre");
    				}
    				else
    				{
    					nombreReporte = getText("rdf.siniestro.cartarechazo.pagodirecto.nombre");
        				nombreArchivo = getText("pdf.siniestro.rechazo.pdir.nombre");
    				}
    			}
    			
    			File carpeta=new File(getText("ruta.documentos.poliza") + "/" + ntramite);
    			if(!carpeta.exists())
    			{
    				logger.debug("no existe la carpeta::: "+ntramite);
    				carpeta.mkdir();
    				if(carpeta.exists())
    				{
    					logger.debug("carpeta creada");
    				}
    				else
    				{
    					logger.debug("carpeta NO creada");
    				}
    			}
    			else
    			{
    				logger.debug("existe la carpeta   ::: "+ntramite);
    			}
    			
    			String urlContrareciboSiniestro = ""
    					+ getText("ruta.servidor.reports")
    					+ "?p_usuario="  + usuario.getUser()
    					+ "&P_NTRAMITE=" + ntramite
    					+ "&userid="     + getText("pass.servidor.reports")
    					+ "&report="     + nombreReporte
    					+ "&destype=cache"
    					+ "&desformat=PDF"
    					+ "&ACCESSIBLE=YES"
    					+ "&paramform=no";
    			String pathArchivo=""
    					+ getText("ruta.documentos.poliza")
    					+ "/" + ntramite
    					+ "/" + nombreArchivo;
    			
    			HttpUtil.generaArchivo(urlContrareciboSiniestro, pathArchivo);
    	        
    			Map<String,Object>paramsDocupol = new HashMap<String,Object>();
    			paramsDocupol.put("pv_cdunieco_i"  , cdunieco);
    			paramsDocupol.put("pv_cdramo_i"    , cdramo);
    			paramsDocupol.put("pv_estado_i"    , estado);
    			paramsDocupol.put("pv_nmpoliza_i"  , nmpoliza);
    			paramsDocupol.put("pv_nmsuplem_i"  , nmsuplem);
    			paramsDocupol.put("pv_feinici_i"   , new Date());
    			paramsDocupol.put("pv_cddocume_i"  , nombreArchivo);
    			paramsDocupol.put("pv_dsdocume_i"  , "Carta rechazo");
    			paramsDocupol.put("pv_ntramite_i"  , ntramite);
    			paramsDocupol.put("pv_nmsolici_i"  , null);
    			paramsDocupol.put("pv_tipmov_i"    , tipoPago);
    			paramsDocupol.put("pv_swvisible_i" , Constantes.SI);
    			paramsDocupol.put("pv_codidocu_i"  , null);
    			paramsDocupol.put("pv_cdtiptra_i"  , TipoTramite.SINIESTRO.getCdtiptra());
    	        kernelManagerSustituto.guardarArchivo(paramsDocupol);
    		}
    		
    		success = true;
    		mensaje = "Siniestro actualizado";
    	}
    	catch(Exception ex)
    	{
    		logger.error("error al actualizar siniestro desde pantalla multisiniestro",ex);
    		success = false;
    		mensaje = ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                          ######"
    			+ "\n###### actualizarMultiSiniestro ######"
    			+ "\n######################################"
    			+ "\n######################################"
    			);
    	return SUCCESS;
    }
    
    public String obtenerMsinival()
    {
    	logger.debug(""
    			+ "\n#############################"
    			+ "\n#############################"
    			+ "\n###### obtenerMsinival ######"
    			+ "\n######                 ######"
    			);
    	logger.debug("params: "+params);
    	try
    	{
    		String cdunieco = params.get("cdunieco");
    		String cdramo   = params.get("cdramo");
    		String estado   = params.get("estado");
    		String nmpoliza = params.get("nmpoliza");
    		String nmsuplem = params.get("nmsuplem");
    		String nmsituac = params.get("nmsituac");
    		String aaapertu = params.get("aaapertu");
    		String status   = params.get("status");
    		String nmsinies = params.get("nmsinies");
    		String nfactura = params.get("nfactura");
    		
    		List<Map<String,String>>lista = siniestrosManager.P_GET_MSINIVAL(
    				cdunieco, cdramo, estado, nmpoliza, nmsuplem,
    				nmsituac, aaapertu, status, nmsinies, nfactura);
    		
    		loadList = new ArrayList<HashMap<String,String>>();
    		
    		for(Map<String,String>map:lista)
    		{
    			loadList.add((HashMap<String,String>)map);
    		}
    		
    		mensaje="Datos obtenidos";
    		success=true;
    	}
    	catch(Exception ex)
    	{
    		logger.error("error al obtener msinival",ex);
    		success=false;
    		mensaje=ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                 ######"
    			+ "\n###### obtenerMsinival ######"
    			+ "\n#############################"
    			+ "\n#############################"
    			);
    	return SUCCESS;
    }
    
    public String guardarMsinival()
    {
    	logger.debug(""
    			+ "\n#############################"
    			+ "\n#############################"
    			+ "\n###### guardarMsinival ######"
    			+ "\n######                 ######"
    			);
    	logger.debug("params: "+params);
    	logger.debug("params: "+datosTablas);
    	try
    	{
    		
    		/*UserVO usuario = (UserVO)session.get("USUARIO");
    		String userregi  = usuario.getUser();*/
    		Date   dFemovimi = new Date();
    		Date   dFeregist = new Date();
    		String nmanno    = Calendar.getInstance().get(Calendar.YEAR)+"";
    		
    		//1.- Eliminamos los registros de la tabla msinival
    		try {
    			HashMap<String, Object> paramBajasinival = new HashMap<String, Object>();
    			paramBajasinival.put("pv_cdunieco_i",datosTablas.get(0).get("cdunieco"));
    			paramBajasinival.put("pv_cdramo_i",datosTablas.get(0).get("cdramo"));
    			paramBajasinival.put("pv_estado_i",datosTablas.get(0).get("estado"));
    			paramBajasinival.put("pv_nmpoliza_i",datosTablas.get(0).get("nmpoliza"));
    			paramBajasinival.put("pv_nmsuplem_i",datosTablas.get(0).get("nmsuplem"));
    			paramBajasinival.put("pv_nmsituac_i",datosTablas.get(0).get("nmsituac"));
    			paramBajasinival.put("pv_aaapertu_i",datosTablas.get(0).get("aaapertu"));
    			paramBajasinival.put("pv_status_i",datosTablas.get(0).get("status"));
    			paramBajasinival.put("pv_nmsinies_i",datosTablas.get(0).get("nmsinies"));
    			paramBajasinival.put("pv_nfactura_i",datosTablas.get(0).get("nfactura"));
    			siniestrosManager.getBajaMsinival(paramBajasinival);
    			
			} catch (Exception e) {
				logger.error("error al eliminar en TfacMesCtrl ",e);
			}
    		//2.- Guardamos los registros de 
    		
    		
    		for(int i=0;i<datosTablas.size();i++)
            {
        		
    			//datosTablas.get(i).get("")
    			//renderFechas.parse(feregist);
        		siniestrosManager.P_MOV_MSINIVAL(
        				datosTablas.get(i).get("cdunieco"), datosTablas.get(i).get("cdramo"), datosTablas.get(i).get("estado"), datosTablas.get(i).get("nmpoliza"), datosTablas.get(i).get("nmsuplem"),
        				datosTablas.get(i).get("nmsituac"), datosTablas.get(i).get("aaapertu"), datosTablas.get(i).get("status"), datosTablas.get(i).get("nmsinies"), datosTablas.get(i).get("nfactura"),
        				datosTablas.get(i).get("cdgarant"), datosTablas.get(i).get("cdconval"), datosTablas.get(i).get("cdconcep"), datosTablas.get(i).get("idconcep"), datosTablas.get(i).get("cdcapita"),
        				datosTablas.get(i).get("nmordina"), dFemovimi, datosTablas.get(i).get("cdmoneda"), datosTablas.get(i).get("ptprecio"), datosTablas.get(i).get("cantidad"),
        				datosTablas.get(i).get("destopor"), datosTablas.get(i).get("destoimp"),datosTablas.get(i).get("ptimport"), datosTablas.get(i).get("ptrecobr"),nmanno,
        				datosTablas.get(i).get("nmapunte"), "", dFeregist, datosTablas.get(i).get("operacion"),datosTablas.get(i).get("ptpcioex"),datosTablas.get(i).get("dctoimex"),datosTablas.get(i).get("ptimpoex"),datosTablas.get(i).get("mtoArancel"));
            }
    		mensaje = "Datos guardados";
    		success = true;
    		
    	}
    	catch(Exception ex)
    	{
    		logger.debug("error al guardar msinival",ex);
    		success=false;
    		mensaje=ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                 ######"
    			+ "\n###### guardarMsinival ######"
    			+ "\n#############################"
    			+ "\n#############################"
    			);
    	return SUCCESS;
    }
    
    public String calculoSiniestros()
    {
    	logger.debug(""
    			+ "\n###############################"
    			+ "\n###############################"
    			+ "\n###### calculoSiniestros ######"
    			+ "\n######                   ######"
    			);
    	logger.debug("params: "+params);
    	try
    	{
    		this.facturasxSiniestro=new ArrayList<Map<String,Object>>();
    		imap = new HashMap<String,Item>();
    		
    		GeneradorCampos    gc          = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
    		String             pantalla    = "CALCULO_SINIESTRO";
    		String             seccion     = null;
    		String             ntramite    = params.get("ntramite");
    		List<ComponenteVO> componentes = null;
    		UserVO             usuario     = (UserVO)session.get("USUARIO");
    		String             cdrol       = usuario.getRolActivo().getClave();
    		
    		//Se obtiene el tramite completo del siniestro
    		Map<String,String> tramite     = siniestrosManager.obtenerTramiteCompleto(ntramite);
    		logger.debug("TRAMITE -->"+tramite);
    		smap = tramite;
    		//Se obtiene el listado de las facturas
    		List<Map<String,String>> facturasAux = siniestrosManager.obtenerFacturasTramite(ntramite);
    		logger.debug("LISTADO DE FACTURAS -->"+facturasAux);

    		if(tramite==null||facturasAux==null)
    		{
    			throw new Exception("No se encontro tramite/facturas para el tramite");
    		}
    		
    		//Borramos los calculos anteriores
    		/*siniestrosManager.movTimpsini(
    				Constantes.DELETE_MODE, null, null, null, null,
    				null, null, null, null, null,
    				ntramite, null, null, null, null, null, false
    		);*/

    		boolean esPagoDirecto = false;
    		if(tramite.get("OTVALOR02").equals("1"))
    		{
    			esPagoDirecto = true;
    		}
    		logger.debug("TIPO DE PAGO ES DIRECTO --> "+esPagoDirecto);
    		
    		Map<String,String> factura        = null;
    		Map<String,String> siniestroIte   = null;
    		Map<String,String> proveedor      = null;
    		Map<String,String> siniestro      = null;

    		List<Map<String,String>>conceptos = null;
    		
    		slist2                  = new ArrayList<Map<String,String>>();
    		slist3                  = new ArrayList<Map<String,String>>();
    		llist1                  = new ArrayList<List<Map<String,String>>>();
    		lhosp                   = new ArrayList<Map<String,String>>();
    		lpdir                   = new ArrayList<Map<String,String>>();
    		lprem                   = new ArrayList<Map<String,String>>();
    		datosPenalizacion       = new ArrayList<Map<String,String>>();
    		datosCoberturaxCal      = new ArrayList<Map<String,String>>();
    		listaImportesWS         = new ArrayList<Map<String,String>>(); 
    		if(esPagoDirecto)
    		{
    			//Verificamos la informacion del proveedor
    			smap.put("PAGODIRECTO","S");
    			smap2     = facturasAux.get(0);
    			
    			proveedor = siniestrosManager.obtenerDatosProveedor(facturasAux.get(0).get("CDPRESTA"));
    			logger.debug("PROVEEDOR-->"+proveedor);
    			smap3     = proveedor;
    			double ivaprov = Double.parseDouble(proveedor.get("IVA")); 
    			double cedprov = Double.parseDouble(proveedor.get("CEDULAR"));
    			double isrprov = Double.parseDouble(proveedor.get("ISR"));
    			
    			//RECORREMOS LAS FACTURAS DEL TRAMITE
    			for(int i = 0; i < facturasAux.size(); i++)
    			{
    				factura = facturasAux.get(i);
    				logger.debug("FACTURA PROCESANDO ---> "+factura.get("NFACTURA"));
    				//Se grega la factura c/u
    				Map<String,Object>facturaObj=new HashMap<String,Object>();
        			facturaObj.putAll(factura);
        			this.facturasxSiniestro.add(facturaObj);
        			
        			//Se agrega los Asegurados o siniestros por factura
        			List<Map<String,String>> siniestros = siniestrosManager.listaSiniestrosMsiniesTramite(ntramite,factura.get("NFACTURA"),null);
        			logger.debug("VALOR DE SINIESTROS POR FACTURAS--->"+siniestros);
        			
        			conceptos = siniestrosManager.P_GET_CONCEPTOS_FACTURA(
    					null, null, null, null, null, null, null, null, null, factura.get("NFACTURA"));
    				logger.debug("OBTENEMOS LA INFORMACION DE LOS CONCEPTOS POR FACTURA -->"+conceptos);
        			
    				/*LINEA PARA IR GUARDANDA A CADA UNO DE LOS ASEGURADOS
        			 * 					facturaObj.put("siniestroPD", siniestros);
        			 */
        			
    				//RECORREMOS LOS SINIESTROS
    				this.aseguradosxSiniestro=new ArrayList<Map<String,Object>>();
    				for( int j= 0; j < siniestros.size();j++){
    					
    					//Se realiza la asignacion del primer asegurado
        				siniestroIte    = siniestros.get(j);
        				Map<String,Object>aseguradoObj=new HashMap<String,Object>();
    					aseguradoObj.putAll(siniestroIte);
    					this.aseguradosxSiniestro.add(aseguradoObj);
    					
    					String cdunieco = siniestroIte.get("CDUNIECO");
        				String cdramo   = siniestroIte.get("CDRAMO");
        				String estado   = siniestroIte.get("ESTADO");
        				String nmpoliza = siniestroIte.get("NMPOLIZA");
        				String nmsuplem = siniestroIte.get("NMSUPLEM");
        				String nmsituac = siniestroIte.get("NMSITUAC");
        				String aaapertu = siniestroIte.get("AAAPERTU");
        				String status   = siniestroIte.get("STATUS");
        				String nmsinies = siniestroIte.get("NMSINIES");
        				String nfactura = factura.get("NFACTURA");
        				
        				//Asignacion de variables
        				//Map<String,String> penalizacion = new HashMap<String,String>();
        				//Map<String,String> calcxCobe = new HashMap<String,String>();
        				double penalizacionCambioZona =0d;
        				double penalizacionCirculoHosp =0d;
        				String aplicaIVA= "S";
    					String seleccionAplica= "D";
    					String ivaRetenido= "N";
    					double deducibleSiniestroIte      = 0d;
        				double copagoAplicadoSiniestroIte = 0d;
        				double cantidadCopagoSiniestroIte = 0d;
        				String penalizacionPesos = "0";
        				String penalizacionPorcentaje = "0";
        				
        				//0.- Guardamos datos adicionales en factura
        				Map<String,String>autorizacionesFactura = siniestrosManager.obtenerAutorizacionesFactura(
        						cdunieco, cdramo, estado, nmpoliza, nmsuplem,
        						nmsituac, aaapertu, status, nmsinies, nfactura);
        				logger.debug("AUTORIACION DE LAS FACTURAS ---> "+autorizacionesFactura);
        				
        				facturaObj.put("AUTMEDIC"+nmsinies,autorizacionesFactura.get("AUTMEDIC"));
        				facturaObj.put("COMMENME"+nmsinies,autorizacionesFactura.get("COMMENME"));
        				facturaObj.put("AUTRECLA"+nmsinies,autorizacionesFactura.get("AUTRECLA"));
        				facturaObj.put("COMMENAR"+nmsinies,autorizacionesFactura.get("COMMENAR"));
        				
        				//1.- Obtenemos los datos generales del siniestros
        				List<Map<String,String>> informacionGral = siniestrosManager.obtieneDatosGeneralesSiniestro(cdunieco, cdramo,
        						estado, nmpoliza,nmsituac, nmsuplem, status, aaapertu, nmsinies, factura.get("NTRAMITE"));
        				
        				if(informacionGral.size()>0){
        					aseguradoObj.put("CAUSASINIESTRO", informacionGral.get(0).get("CDCAUSA"));
        				}else{
        					aseguradoObj.put("CAUSASINIESTRO", CausaSiniestro.ENFERMEDAD.getCodigo());
        				}
        				
        				Map<String,String>copagoDeducibleSiniestroIte =siniestrosManager.obtenerCopagoDeducible(
        						cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura);
        				logger.debug("INFORMACION DEDUCIBLE/COPAGO ASEGURADO -->"+copagoDeducibleSiniestroIte);
        				
        				String tipoFormatoCalculo         = copagoDeducibleSiniestroIte.get("FORMATOCALCULO");
        				String calculosPenalizaciones     = copagoDeducibleSiniestroIte.get("PENALIZACIONES");
        				//Se guarda la informacion de tipo de formato y calculo de penalizaciones en factura
        				facturaObj.put("TIPOFORMATOCALCULO",""+tipoFormatoCalculo);
        				facturaObj.put("CALCULOSPENALIZACIONES",""+calculosPenalizaciones);
        				
        				if(calculosPenalizaciones.equalsIgnoreCase("1")){
        		   			//4.1.- Verificamos si existe exclusi�n de penalizaci�n
        		   			HashMap<String, Object> paramExclusion = new HashMap<String, Object>();
            		   		paramExclusion.put("pv_cdunieco_i",cdunieco);
            		   		paramExclusion.put("pv_estado_i",estado);
            		   		paramExclusion.put("pv_cdramo_i",cdramo);
            		   		paramExclusion.put("pv_nmpoliza_i",nmpoliza);
            		   		paramExclusion.put("pv_nmsituac_i",nmsituac);
            		   		//existePenalizacion = siniestrosManager.validaExclusionPenalizacion(paramExclusion);
            		   		
            		   		//1.- verificamos el ramo
            		   		if(cdramo.equalsIgnoreCase("2")){
                		   		existePenalizacion = siniestrosManager.validaExclusionPenalizacion(paramExclusion);
                		   		//4.2.- Obtenemos la penalizaci�n por cambio de Zona
                		   		penalizacionCambioZona = penalizacionCambioZona(existePenalizacion,informacionGral.get(0).get("CDCAUSA"),informacionGral.get(0).get("CIRHOSPI"),
                						informacionGral.get(0).get("DSZONAT"),informacionGral.get(0).get("CDPROVEE"), cdramo);
                		   		aseguradoObj.put("PENALIZACIONCAMBIOZONA",""+penalizacionCambioZona);
                				//4.3.- Obtenemos la penalizaci�n por circulo Hospitalario
                				penalizacionCirculoHosp = calcularPenalizacionCirculo(informacionGral.get(0).get("CIRHOSPI"), informacionGral.get(0).get("CIRHOPROV"),informacionGral.get(0).get("CDCAUSA"));
                				aseguradoObj.put("PENALIZACIONCIRCULOHOSP",""+penalizacionCirculoHosp);            		   			
            		   		}else{
            		   			aseguradoObj.put("PENALIZACIONCAMBIOZONA",""+penalizacionCambioZona);
            		   			aseguradoObj.put("PENALIZACIONCIRCULOHOSP",""+penalizacionCirculoHosp);
            		   		}
            				
        		   		}else{
        		   			//4.2.- Obtenemos la penalizaci�n por cambio de Zona
        		   			aseguradoObj.put("PENALIZACIONCAMBIOZONA",""+penalizacionCambioZona);
        		   			//4.3.- Obtenemos la penalizaci�n por circulo Hospitalario
        		   			aseguradoObj.put("PENALIZACIONCIRCULOHOSP",""+penalizacionCirculoHosp);
        		   		}
        				
        				//4.4.- Obtenemos el total de penalizaci�n
        				String calcularTotalPenalizacion = calcularTotalPenalizacion(penalizacionCambioZona,penalizacionCirculoHosp,informacionGral.get(0).get("CDCAUSA"),
        																			 copagoDeducibleSiniestroIte.get("COPAGO"),copagoDeducibleSiniestroIte.get("TIPOCOPAGO"),
        																			 informacionGral.get(0).get("CDPROVEE"),cdramo, informacionGral.get(0).get("FEOCURRE"));
        				
        				aseguradoObj.put("TOTALPENALIZACIONGLOBAL",""+calcularTotalPenalizacion);
        				String penalizacionT[] = calcularTotalPenalizacion.split("\\|");
        				penalizacionPorcentaje = penalizacionT[0].toString();
        				penalizacionPesos = penalizacionT[1].toString();
        				aseguradoObj.put("COPAGOPORCENTAJES",penalizacionPorcentaje);
        				aseguradoObj.put("COPAGOPESOS",penalizacionPesos);
        				
    					//5.- Obtenemos informaci�n adicional de las facturas, para realizar la validaci�n de aplica IVA o No
        				List<Map<String, String>> listaFactura = siniestrosManager.P_GET_FACTURAS_SINIESTRO(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies);
        				logger.debug("VALOR DE listaFactura -->"+listaFactura);
        				
        				if(listaFactura.get(0).get("APLICA_IVA") != null){
        					aplicaIVA= listaFactura.get(0).get("APLICA_IVA");
        					seleccionAplica =listaFactura.get(0).get("ANTES_DESPUES");
        					ivaRetenido = listaFactura.get(0).get("IVARETENIDO");
        					if(!StringUtils.isNotBlank(ivaRetenido)){
        						ivaRetenido= "N";
        					}
        				}
        				
        				String sDeducibleSiniestroIte     = copagoDeducibleSiniestroIte.get("DEDUCIBLE").replace(",","");
        				String sCopagoSiniestroIte        = copagoDeducibleSiniestroIte.get("COPAGO").replace(",", "");
        				String tipoCopagoSiniestroIte     = copagoDeducibleSiniestroIte.get("TIPOCOPAGO");
        				
        				//Verificacaci�n de la informaci�n de Deducible
        				if(StringUtils.isNotBlank(sDeducibleSiniestroIte)
        						&&(!sDeducibleSiniestroIte.equalsIgnoreCase("na"))
        						&&(!sDeducibleSiniestroIte.equalsIgnoreCase("no"))
        						)
        				{
        					try
        					{
        						deducibleSiniestroIte = Double.valueOf(sDeducibleSiniestroIte);
        					}
        					catch(Exception ex)
        					{
        						logger.debug(""
        								+ "\n### ERROR ##################################################"
        								+ "\n### no es numero deducible: '"+sDeducibleSiniestroIte+"' ###"
        								+ "\n############################################################"
        								);
        						deducibleSiniestroIte = 0d;
        					}
        				}
        				//Verificacaci�n de la informaci�n de copago
        				if(StringUtils.isNotBlank(sCopagoSiniestroIte)
        						&&(!sCopagoSiniestroIte.equalsIgnoreCase("na"))
        						&&(!sCopagoSiniestroIte.equalsIgnoreCase("no"))
        						)
        				{
        					try
        					{
        						cantidadCopagoSiniestroIte = Double.valueOf(sCopagoSiniestroIte);
        					}
        					catch(Exception ex)
        					{
        						logger.debug(""
        								+ "\n### ERROR ############################################"
        								+ "\n### no es numero copago: '"+sCopagoSiniestroIte+"' ###"
        								+ "\n######################################################"
        								);
        						cantidadCopagoSiniestroIte = 0d;
        					}
        				}
        				
        				Map<String,String>importesWSSiniestroIte=new HashMap<String,String>();
        				importesWSSiniestroIte.put("cdunieco" , siniestroIte.get("CDUNIECO"));
        				importesWSSiniestroIte.put("cdramo"   , siniestroIte.get("CDRAMO"));
        				importesWSSiniestroIte.put("estado"   , siniestroIte.get("ESTADO"));
        				importesWSSiniestroIte.put("nmpoliza" , siniestroIte.get("NMPOLIZA"));
        				importesWSSiniestroIte.put("nmsuplem" , siniestroIte.get("NMSUPLEM"));
        				importesWSSiniestroIte.put("nmsituac" , siniestroIte.get("NMSITUAC"));
        				importesWSSiniestroIte.put("aaapertu" , siniestroIte.get("AAAPERTU"));
        				importesWSSiniestroIte.put("status"   , siniestroIte.get("STATUS"));
        				importesWSSiniestroIte.put("nmsinies" , siniestroIte.get("NMSINIES"));
        				importesWSSiniestroIte.put("ntramite" , ntramite);
        				listaImportesWS.add(importesWSSiniestroIte);
        				double importeSiniestroIte;
        				double ivaSiniestroIte;
        				double ivrSiniestroIte;
        				double isrSiniestroIte;
        				double cedSiniestroIte;

        				//hospitalizacion
        				Map<String,String> hosp = new HashMap<String,String>();
        				lhosp.add(hosp);
        				hosp.put("PTIMPORT" 	, "0");
        				hosp.put("DESTO"    	, "0");
        				hosp.put("IVA"      	, "0");
        				hosp.put("PRECIO"   	, "0");
        				hosp.put("DESCPRECIO"   , "0");
        				hosp.put("IMPISR"   , "0");
        				hosp.put("IMPCED"   , "0");
        				//hospitalizacion
        				
        				//reembolso
        				Map<String,String>mprem=new HashMap<String,String>();
        				mprem.put("dummy","dummy");
        				lprem.add(mprem);
        				//remmbolso
        				
        				//pago directo
        				Map<String,String> mpdir = new HashMap<String,String>();
        				 mpdir.put("total","0");
        				 mpdir.put("totalcedular","0");
        				 mpdir.put("ivaTotalMostrar","0");
        				 mpdir.put("ivaRetenidoMostrar","0");
        				 mpdir.put("iSRMostrar","0");
        				 lpdir.add(mpdir);
        				
        				//INICIO DE CONCEPTOS
        				this.conceptosxSiniestro = new ArrayList<Map<String,String>>();
        				for(int k = 0; k < conceptos.size() ; k++)
        				{
        					Map<String, String> concepto = conceptos.get(k);
        					if(concepto.get("CDUNIECO").equals(siniestroIte.get("CDUNIECO"))
        							&&concepto.get("CDRAMO").equals(siniestroIte.get("CDRAMO"))
        							&&concepto.get("ESTADO").equals(siniestroIte.get("ESTADO"))
        							&&concepto.get("NMPOLIZA").equals(siniestroIte.get("NMPOLIZA"))
        							&&concepto.get("NMSUPLEM").equals(siniestroIte.get("NMSUPLEM"))
        							&&concepto.get("NMSITUAC").equals(siniestroIte.get("NMSITUAC"))
        							&&concepto.get("AAAPERTU").equals(siniestroIte.get("AAAPERTU"))
        							&&concepto.get("STATUS").equals(siniestroIte.get("STATUS"))
        							&&concepto.get("NMSINIES").equals(siniestroIte.get("NMSINIES"))
        							)
        					{
        						conceptosxSiniestro.add(concepto);
        						
        						//---> listaConceptosSiniestro.add(concepto);
        						if(tipoFormatoCalculo.equalsIgnoreCase("1"))
        						{
        							// CALCULOS PARA CUANDO ES HOSPITALIZACI�N Y AYUDA DE MATERNIDAD
        							logger.debug(">>HOSPITALIZACION");
        							double PTIMPORT    = Double.parseDouble(concepto.get("PTIMPORT"));
        							double DESTOPOR    = Double.parseDouble(concepto.get("DESTOPOR"));
        							double DESTOIMP    = Double.parseDouble(concepto.get("DESTOIMP"));
        							double PTPRECIO    = Double.parseDouble(concepto.get("PTPRECIO")) * Double.parseDouble(concepto.get("CANTIDAD"));
        							logger.debug("concepto importe "+PTIMPORT);
        							logger.debug("concepto desto "+DESTOPOR);
        							logger.debug("concepto destoimp "+DESTOIMP);
        							logger.debug("usando iva proveedor "+ivaprov);
        							boolean copagoPorc = false;
        							String scopago     = concepto.get("COPAGO");
        							
        							if(scopago.equalsIgnoreCase("no") ||scopago.equalsIgnoreCase("na"));
        							{
        								scopago="0";
        							}
        							logger.debug("procesar copago "+scopago);
        							if(StringUtils.isNotBlank(scopago))
        							{
        								if(scopago.contains("%"))
        								{
        									copagoPorc = true;
        								}
        								scopago=scopago.replace("%", "").replace("$", "");
        								if(copagoPorc)
        								{
        									DESTOPOR=DESTOPOR+Double.valueOf(scopago);
        								}
        								else
        								{
        									DESTOIMP=DESTOIMP+Double.valueOf(scopago);
        								}
        							}
        							logger.debug("concepto desto + copago % "+DESTOPOR);
        							logger.debug("concepto destoimp + copago $ "+DESTOIMP);
        							
        							//CALCULOS ANTES DE COPAGO
        							double hPTIMPORT = Double.parseDouble(hosp.get("PTIMPORT"));
        							double hDESTO    = Double.parseDouble(hosp.get("DESTO"));
        							double hIVA      = Double.parseDouble(hosp.get("IVA"));
        							double hISR      = Double.parseDouble(hosp.get("IMPISR"));
        							double hICED      = Double.parseDouble(hosp.get("IMPCED"));
        							double hPRECIO      = Double.parseDouble(hosp.get("PRECIO"));
        							double hDESCPRECIO  = Double.parseDouble(hosp.get("DESCPRECIO"));
        							
        							hPTIMPORT 	+= PTIMPORT;
        							hDESTO    	+= (PTIMPORT*(DESTOPOR/100d)) + (DESTOIMP);
        							hIVA      	+= PTIMPORT*(ivaprov/100d);
        							hISR		+= PTIMPORT*(isrprov/100d);
        							hICED		+= PTIMPORT*(cedprov/100d);
        							hPRECIO 	+= PTPRECIO;
        							hDESCPRECIO += (PTPRECIO*(DESTOPOR/100d)) + (DESTOIMP);
        							
        							hosp.put("PTIMPORT" , hPTIMPORT+"");
        							hosp.put("DESTO"    , hDESTO+"");
        							hosp.put("IVA"      , hIVA+"");
        							hosp.put("IMPISR"   , hISR+"");
        							hosp.put("PRECIO"   , hPRECIO+"");
        							hosp.put("DESCPRECIO", hDESCPRECIO+"");
        							hosp.put("IMPCED"   , hICED+"");
        							
        							logger.debug("#### VALORES DEL VECTOR #####");
        							logger.debug("hPTIMPORT "+hPTIMPORT);
        							logger.debug("hDESTO "+hDESTO);
        							logger.debug("hIVA "+hIVA);
        							logger.debug("hISR "+hISR);
        							logger.debug("hPRECIO "+hPRECIO);
        							logger.debug("hDESCPRECIO "+hDESCPRECIO);
        							logger.debug("hICED "+hICED);
        							
        							logger.debug("<<HOSPITALIZACION");
        						}
        						else
        						{
        							//logger.debug("######  COBERTURA DIFERENTE DE HOSPITALIZACI�N Y AYUDA DE MATERNIDAD  ######");
        							logger.debug(">>PAGO DIRECTO DIFERENTE A HOSPITALIZACION");
        							Map<String,String>row=new HashMap<String,String>();
        							row.putAll(concepto);
        							
        							double cantidad = Double.valueOf(row.get("CANTIDAD"));
        							logger.debug("cantidad "+cantidad);
        							double precioArancel = 0d;
        							//Obtenemos el valor original del arancel
        							if(StringUtils.isNotBlank(row.get("IMP_ARANCEL")))
        							{
        								precioArancel = Double.valueOf(row.get("IMP_ARANCEL"));
        							}
        							row.put("IMP_ARANCEL",precioArancel+"");
        							logger.debug("precioArancel "+precioArancel);
        							double subtotalArancel = cantidad*precioArancel;//++
        							logger.debug("subtotalArancel "+subtotalArancel);
        							row.put("SUBTTARANCEL",subtotalArancel+"");
        							double descuentoPorc   = 0d;
        							double descuentoImpo   = 0d;
        							if(StringUtils.isNotBlank(row.get("DESTOPOR")))
        							{
        								descuentoPorc = Double.parseDouble(row.get("DESTOPOR"));
        							}
        							if(StringUtils.isNotBlank(row.get("DESTOIMP")))
        							{
        								descuentoImpo = Double.parseDouble(row.get("DESTOIMP"));
        							}
        							double descuentoAplicado=(subtotalArancel*(descuentoPorc/100d))+descuentoImpo;//++
        							logger.debug("descuentoAplicado "+descuentoAplicado);
        							row.put("DESTOAPLICA",descuentoAplicado+"");
        							double subtotalDescuento=subtotalArancel-descuentoAplicado;//++
        							logger.debug("subtotalDescuento "+subtotalDescuento);
        							row.put("SUBTTDESCUENTO",subtotalDescuento+""); // SUBTOTAL A OCUPAR CUANDO SEA ANTES DE COPAGO
        							
        							if(aplicaIVA.equalsIgnoreCase("S")){
        								if(seleccionAplica.equalsIgnoreCase("A")){ // ANTES DEL COPAGO
        									double iVaaplicaAntes = subtotalDescuento*(ivaprov/100d);//++
        									row.put("IVAAPLICA",iVaaplicaAntes+"");
        								}
        							}
        							
        							boolean copagoPorc = false;
        							double  copago = 0d;
        							double  copagoAplicado = 0d;//++
        							String scopago =concepto.get("COPAGO");
        							String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
        							
        							if(causadelSiniestro ==""||causadelSiniestro == null){
        								causadelSiniestro = "1";
        							}
        							if(causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
        								copagoAplicado = 0d;
        							}else{
        								if(StringUtils.isNotBlank(scopago))
        								{
        									if(scopago.equalsIgnoreCase("na") || scopago.equalsIgnoreCase("no")){
        										copagoAplicado = 0d;
        									}else{
        										if(scopago.contains("%"))
        										{
        											copagoPorc = true;
        										}
        										scopago=scopago.replace("%", "").replace("$", "").replaceAll(",", "");
        										copago=Double.valueOf(scopago);
        										if(copagoPorc)
        										{
        											copagoAplicado=(subtotalDescuento*(copago/100d));
        										}
        										else
        										{
        											copagoAplicado=copago * cantidad;
        										}
        									}
        								}
        							}
        							
        							row.put("COPAGOAPLICA",copagoAplicado+"");
        							logger.debug("copagoAplicado "+copagoAplicado);
        							double subtotalCopago    = subtotalDescuento - copagoAplicado;//++
        							logger.debug("subtotalCopago "+subtotalCopago);
        							row.put("SUBTTCOPAGO",subtotalCopago+"");
        							double israplicado       = subtotalCopago*(isrprov/100d);//++
        							logger.debug("israplicado "+israplicado);
        							row.put("ISRAPLICA",israplicado+"");
        							double subtotalImpuestos = subtotalCopago-(israplicado+0d);//cedularaplicado);//++
        							logger.debug("subtotalImpuestos "+subtotalImpuestos);
        							
        							double totalISRMostrar = Double.parseDouble(mpdir.get("iSRMostrar"));
        							logger.debug("base totalISRMostrar"+totalISRMostrar);
        							totalISRMostrar += israplicado;
        							//logger.debug("new totalISRMostrar"+totalISRMostrar);
        							mpdir.put("iSRMostrar",totalISRMostrar+"");
        							
        							////// modificado
        							double cedularaplicado   = subtotalCopago*(cedprov/100d);//++
        							logger.debug("cedularaplicado "+cedularaplicado);
        							row.put("CEDUAPLICA",cedularaplicado+"");
        							////// modificado
        							
        							subtotalImpuestos = subtotalImpuestos - cedularaplicado;
        							row.put("SUBTTIMPUESTOS",subtotalImpuestos+"");
        							
        							double ivaaplicado =0d;
        							double ivaRetenidos =0d;
        							double ptimportauto =0d;
        							//logger.debug("#####...aplicaIVA.....--->"+aplicaIVA);
        							
        							if(aplicaIVA.equalsIgnoreCase("S")){
        								if(seleccionAplica.equalsIgnoreCase("D")){
        									ivaaplicado       = subtotalCopago*(ivaprov/100d);//++
        									row.put("IVAAPLICA",ivaaplicado+"");
        									//logger.debug("#####...ivaRetenido.....--->"+ivaRetenido);
        									if(ivaRetenido.equalsIgnoreCase("S")){
        										ivaRetenidos      = ((2d * ivaaplicado)/3);
        										row.put("IVARETENIDO",ivaRetenidos+"");
        									}else{
        										ivaRetenidos      = 0d;
        										row.put("IVARETENIDO",ivaRetenidos+"");
        									}
        									ptimportauto      = (subtotalImpuestos-ivaRetenidos)+ivaaplicado;//++
        									row.put("PTIMPORTAUTO",ptimportauto+"");
        								}else{
        									ivaaplicado       = subtotalDescuento*(ivaprov/100d);
        									row.put("IVAAPLICA",ivaaplicado+"");
        									if(ivaRetenido.equalsIgnoreCase("S")){
        										ivaRetenidos      = ((2d * ivaaplicado)/3);
        										row.put("IVARETENIDO",ivaRetenidos+"");
        									}else{
        										ivaRetenidos      = 0d;
        										row.put("IVARETENIDO",ivaRetenidos+"");
        									}
        									ptimportauto      = (subtotalImpuestos-ivaRetenidos)+ivaaplicado; //++
        									row.put("PTIMPORTAUTO",ptimportauto+"");
        								}
        							}else{
        								ivaaplicado       = 0d;//++
        								ivaRetenidos      = 0d;
        								row.put("IVAAPLICA",ivaaplicado+"");
        								row.put("IVARETENIDO",ivaRetenidos+"");
        								ptimportauto      = (subtotalImpuestos-ivaRetenidos)+ivaaplicado;//++
        								row.put("PTIMPORTAUTO",ptimportauto+"");
        							}
        							
        							
        							double totalIVAMostrar = Double.parseDouble(mpdir.get("ivaTotalMostrar"));
        							logger.debug("base totalIVAMostrar"+totalIVAMostrar);
        							totalIVAMostrar += ivaaplicado;
        							logger.debug("new totalIVAMostrar"+totalIVAMostrar);
        							mpdir.put("ivaTotalMostrar",totalIVAMostrar+"");
        							
        							
        							double totalIVARetenidoMostrar = Double.parseDouble(mpdir.get("ivaRetenidoMostrar"));
        							logger.debug("base totalIVARetenidoMostrar"+totalIVARetenidoMostrar);
        							totalIVARetenidoMostrar += ivaRetenidos;
        							logger.debug("new totalIVAMostrar"+totalIVARetenidoMostrar);
        							mpdir.put("ivaRetenidoMostrar",totalIVARetenidoMostrar+"");
        							
        							double ptimport = Double.parseDouble(row.get("PTIMPORT"));
        							logger.debug("ptimport "+ptimport);
        							
        							double valorusado        = ptimportauto;//++
        							
        							String autmedic = row.get("AUTMEDIC");
        							if(StringUtils.isNotBlank(autmedic)&&autmedic.equalsIgnoreCase("S"))
        							{
        								valorusado = ptimport;
        							}
        							logger.debug("valorusado "+valorusado);
        							row.put("VALORUSADO",valorusado+"");
        							
        							double totalGrupo = Double.parseDouble(mpdir.get("total"));
        							logger.debug("base totalGrupo"+totalGrupo);
        							totalGrupo += valorusado;
        							logger.debug("new totalGrupo"+totalGrupo);
        							mpdir.put("total",totalGrupo+"");
        							
        							double totalGrupoCedular = Double.parseDouble(mpdir.get("totalcedular"));
        							logger.debug("base totalGrupoCedular"+totalGrupoCedular);
        							totalGrupoCedular += cedularaplicado;
        							logger.debug("new totalGrupoCedular"+totalGrupoCedular);
        							mpdir.put("totalcedular",totalGrupoCedular+"");
        							
        							concepto.putAll(row);
        							logger.debug("<<PAGO DIRECTO DIFERENTE A HOSPITALIZACION");
        						}
        					}
        				}//FIN DE CONCEPTOS
        				aseguradoObj.put("conceptosAsegurado", conceptosxSiniestro);
        				
        				
        				
        				
        				//hospitalizacion
        				//if(factura.get("CDGARANT").equalsIgnoreCase("18HO")||factura.get("CDGARANT").equalsIgnoreCase("18MA"))
        				//logger.debug("######  HOSPITALIZACI�N Y AYUDA DE MATERNIDAD WS ######");
        				if(tipoFormatoCalculo.equalsIgnoreCase("1"))
    					{
    						logger.debug(">>WS del siniestro iterado");
    						logger.debug("deducible siniestro iterado: "+sDeducibleSiniestroIte);
    						logger.debug("copago siniestro iterado: "+sCopagoSiniestroIte);
    						logger.debug("tipo copago siniestro iterado: "+tipoCopagoSiniestroIte);
    						
    						logger.debug("hosp.get(PTIMPORT)-->"+hosp.get("PTIMPORT"));
    						logger.debug("hosp.get(DESTO)-->"+hosp.get("DESTO"));
    						logger.debug("hosp.get(IVA)-->"+hosp.get("IVA"));
    						logger.debug("deducibleSiniestroIte--->"+deducibleSiniestroIte);
    						
    						double hPTIMPORT = Double.valueOf(hosp.get("PTIMPORT"));
    						double DESTOIMP  = Double.valueOf(hosp.get("DESTO"));
    						double hIVA      = Double.valueOf(hosp.get("IVA"));
    						String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
    						double subttDesto =0d;
    						
    						if(!causadelSiniestro.equalsIgnoreCase("2")){
    							//Diferente de accidente
    							subttDesto = (hPTIMPORT + DESTOIMP) - (DESTOIMP + deducibleSiniestroIte );
    						}else{
    							//accidente
    							subttDesto = (hPTIMPORT + DESTOIMP) - (DESTOIMP);
    						}
    						
    						if(StringUtils.isNotBlank(tipoCopagoSiniestroIte))
    						{
    							if(!causadelSiniestro.equalsIgnoreCase("2")){
    								//Diferente de accidente
    								copagoAplicadoSiniestroIte = Double.parseDouble(penalizacionPesos) + (subttDesto * ( Double.parseDouble(penalizacionPorcentaje) / 100d ));
    							}else{
    								//accidente
    								copagoAplicadoSiniestroIte= 0d;
    							}
    						}
    						
    						
    						importeSiniestroIte = subttDesto - copagoAplicadoSiniestroIte;
    						
    						double hIVADesCopago  = importeSiniestroIte*(ivaprov/100d);
    						logger.debug("Iva a ocupar despues de copago");
    						logger.debug(hIVADesCopago);
    						
    						hosp.put("PTIMPORT_DESCOPAGO" , importeSiniestroIte+"");
    						hosp.put("IVA_DESCOPAGO"    , hIVADesCopago+"");
    						
    		            	double importeBase=0d;
    		            	
    		            	importeBase= hPTIMPORT - DESTOIMP;
    		            	if(aplicaIVA.equalsIgnoreCase("S")){
    		            		//SI LOS VALORES SON ANTES DE COPAGO ENTONCES SE QUEDAN IGUALES LOS VALORES DE DESCUENTO, IVA Y PTIMPORT
    		            		if(seleccionAplica.equalsIgnoreCase("D")){ // ANTES DEL COPAGO
    		            			hosp.put("IVA"    , hIVADesCopago+"");
    		            			hosp.put("BASEIVA" , importeSiniestroIte+"");
    		            		}else{
    		            			
    		            			hosp.put("BASEIVA" , subttDesto+"");
    		            		}
    		            	}else{
    		            		hosp.put("IVA"    ,0d+"");
    		            		hosp.put("BASEIVA" , subttDesto+"");
    		            	}
    						
    		            	//APLICAMOS EL IVA RETENIDO
    		            	if(ivaRetenido.equalsIgnoreCase("S")){
    							ivrSiniestroIte = ((2d * Double.parseDouble(hosp.get("IVA")))/3d);
                                hosp.put("IVARETENIDO"    , ivrSiniestroIte+"");
                                
                            }else{
                            	ivrSiniestroIte = 0d;
                                hosp.put("IVARETENIDO"    , ivrSiniestroIte+"");
                            }
    						ivaSiniestroIte = Double.parseDouble(hosp.get("IVA"));//hIVA;
    						//ivrSiniestroIte = 0d;
    						isrSiniestroIte = Double.valueOf(hosp.get("IMPISR"));
    						cedSiniestroIte = Double.valueOf(hosp.get("IMPCED"));
    						importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(importeSiniestroIte)).toString());
    						importesWSSiniestroIte.put(IMPORTE_WS_IVA     , (new Double(ivaSiniestroIte)    ).toString());
    						importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroIte)    ).toString());
    						importesWSSiniestroIte.put(IMPORTE_WS_ISR     , (new Double(isrSiniestroIte)    ).toString());
    						importesWSSiniestroIte.put(IMPORTE_WS_CEDULAR , (new Double(cedSiniestroIte)    ).toString());
    						logger.debug("mapa WS siniestro iterado: "+importesWSSiniestroIte);
    						logger.debug("<<WS del siniestro iterado");
    						//logger.debug("###### HOSPITALIZACION Y AYUDA DE MATERNIDA WS ######");
    					}
    					else//pago directo
    					{
    						//logger.debug("######  COBERTURA DIFERENTE DE HOSPITALIZACI�N Y AYUDA DE MATERNIDAD  ######");
    						logger.debug(">>WS del siniestro iterado");
    						logger.debug("deducible siniestro iterado: "+sDeducibleSiniestroIte);
    						logger.debug("copago siniestro iterado: "+sCopagoSiniestroIte);
    						logger.debug("tipo copago siniestro iterado: "+tipoCopagoSiniestroIte);
    						
    						double totalGrupo = Double.valueOf(mpdir.get("total"));
    						
    						importeSiniestroIte = totalGrupo / ( 1d + ( ivaprov  / 100d ) );
    						
    						ivrSiniestroIte = 0d;
    						isrSiniestroIte = 0d;
    						ivaSiniestroIte = 0d;
    						cedSiniestroIte = Double.valueOf(mpdir.get("totalcedular"));
    						
    						double subttDescuentoSiniestroIte= 0d;
    						double subttISRSiniestroIte= 0d;
    						double subttcopagototalSiniestroIte=0;
    						
    						for(Map<String,String>concepto : conceptos)
    	    				{
    	    					if(concepto.get("CDUNIECO").equals(siniestroIte.get("CDUNIECO"))
    	    							&&concepto.get("CDRAMO").equals(siniestroIte.get("CDRAMO"))
    	    							&&concepto.get("ESTADO").equals(siniestroIte.get("ESTADO"))
    	    							&&concepto.get("NMPOLIZA").equals(siniestroIte.get("NMPOLIZA"))
    	    							&&concepto.get("NMSUPLEM").equals(siniestroIte.get("NMSUPLEM"))
    	    							&&concepto.get("NMSITUAC").equals(siniestroIte.get("NMSITUAC"))
    	    							&&concepto.get("AAAPERTU").equals(siniestroIte.get("AAAPERTU"))
    	    							&&concepto.get("STATUS").equals(siniestroIte.get("STATUS"))
    	    							&&concepto.get("NMSINIES").equals(siniestroIte.get("NMSINIES"))
    	    							)
    	    					{
    	    						subttDescuentoSiniestroIte+= Double.valueOf(concepto.get("SUBTTDESCUENTO"));
    	    						subttcopagototalSiniestroIte+= Double.valueOf(concepto.get("SUBTTCOPAGO"));
    	    						subttISRSiniestroIte+= Double.valueOf(concepto.get("ISRAPLICA"));
    	    						ivaSiniestroIte+= Double.valueOf(concepto.get("IVAAPLICA"));
    	    						ivrSiniestroIte += ((2 * Double.valueOf(concepto.get("IVAAPLICA")))/3);
    	    					}
    	    				}
    						
    						if(aplicaIVA.equalsIgnoreCase("S")){
    							if(seleccionAplica.equalsIgnoreCase("D")){
    								importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(subttcopagototalSiniestroIte)).toString());
    							}else{
    								importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(subttDescuentoSiniestroIte)).toString());
    							}
    							//logger.debug("####VALOR DE IVA RETENIDO ##### ---> "+ivaRetenido);
    							if(ivaRetenido.equalsIgnoreCase("S")){
    								importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroIte)    ).toString());
    							}else{
    								importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(0d)    ).toString());
    							}
    							
    						}else{
    							importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(subttcopagototalSiniestroIte)).toString());
    							importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(0d)    ).toString());
    						}
    						
    						importesWSSiniestroIte.put(IMPORTE_WS_IVA     , (new Double(ivaSiniestroIte)    ).toString());
    						//importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroIte)    ).toString());
    						importesWSSiniestroIte.put(IMPORTE_WS_ISR     , (new Double(subttISRSiniestroIte)    ).toString());
    						importesWSSiniestroIte.put(IMPORTE_WS_CEDULAR , (new Double(cedSiniestroIte)    ).toString());
    						logger.debug("mapa WS siniestro iterado: "+importesWSSiniestroIte);
    						logger.debug("<<WS del siniestro iterado");
    						//logger.debug("###### COBERTURA DIFERENTE DE HOSPITALIZACI�N Y AYUDA DE MATERNIDA ######");
    					}
    				}
    				facturaObj.put("siniestroPD", aseguradosxSiniestro);
    			}
    			//logger.debug("VALOR TOTAL DE FACTURAS --->"+factura);
    		}
    		else//REEMBOLSO
    		{
    			
    			List<Map<String,String>> siniestros = siniestrosManager.listaSiniestrosMsiniesTramite(ntramite,null,null);
    			//List<Map<String,String>> siniestros = siniestrosManager.listaSiniestrosMsiniesTramite(ntramite,null);
        		logger.debug(siniestros);
    			siniestro  = siniestros.get(0);
    			siniestros = null;
    			smap2      = siniestro;
    			smap3      = new HashMap<String,String>();
    			smap3.put("a","a");
    			smap.put("PAGODIRECTO","N");
    			conceptos  = siniestrosManager.P_GET_CONCEPTOS_FACTURA(
    					siniestro.get("CDUNIECO"),
    					siniestro.get("CDRAMO"),
    					siniestro.get("ESTADO"),
    					siniestro.get("NMPOLIZA"),
    					siniestro.get("NMSUPLEM"),
    					siniestro.get("NMSITUAC"),
    					siniestro.get("AAAPERTU"),
    					siniestro.get("STATUS"),
    					siniestro.get("NMSINIES"),
    					null);
    			logger.debug("VALOR DEL CONCEPTOS");
    			logger.debug(conceptos);
    			slist1     = facturasAux;
    			
    			//hospitalizacion
    			Map<String,String> hosp = new HashMap<String,String>();
    			lhosp.add(hosp);
    			hosp.put("PTIMPORT" , "0");
    			hosp.put("DESTO"    , "0");
    			hosp.put("IVA"      , "0");
    			//hospitalizacion
    			
    			//directo
    			Map<String,String>mpdir=new HashMap<String,String>();
    			mpdir.put("dummy","dummy");
    			lpdir.add(mpdir);
    			//directo
    			
    			Map<String,String>importesWSSiniestroUnico=new HashMap<String,String>();
    			importesWSSiniestroUnico.put("cdunieco" , siniestro.get("CDUNIECO"));
    			importesWSSiniestroUnico.put("cdramo"   , siniestro.get("CDRAMO"));
    			importesWSSiniestroUnico.put("estado"   , siniestro.get("ESTADO"));
    			importesWSSiniestroUnico.put("nmpoliza" , siniestro.get("NMPOLIZA"));
    			importesWSSiniestroUnico.put("nmsuplem" , siniestro.get("NMSUPLEM"));
    			importesWSSiniestroUnico.put("nmsituac" , siniestro.get("NMSITUAC"));
    			importesWSSiniestroUnico.put("aaapertu" , siniestro.get("AAAPERTU"));
    			importesWSSiniestroUnico.put("status"   , siniestro.get("STATUS"));
    			importesWSSiniestroUnico.put("nmsinies" , siniestro.get("NMSINIES"));
    			importesWSSiniestroUnico.put("ntramite" , ntramite);
    			listaImportesWS.add(importesWSSiniestroUnico);
    			double importeSiniestroUnico = 0d;
    			double ivaSiniestroUnico     = 0d;
    			double ivrSiniestroUnico     = 0d;
    			double isrSiniestroUnico     = 0d;
    			double cedularSiniestroUnico = 0d;
    			
    			
    			Map<String,String> facturaIte        = null;
    			//for(Map<String,String>facturaIte:facturasAux)
    			for(int i = 0; i < facturasAux.size(); i++)
    			{
    				facturaIte = facturasAux.get(i);
    				Map<String,Object>facturaObj=new HashMap<String,Object>();
            			facturaObj.putAll(facturaIte);
            			this.facturasxSiniestro.add(facturaObj);
    				
    				double penalizacionCambioZona = 0d;
    				double penalizacionCirculoHosp = 0d;
    				double totalPenalizacion = 0d;
    				double deducibleFacturaIte      = 0d;
    				double cantidadCopagoFacturaIte = 0d;
    				double copagoAplicadoFacturaIte = 0d;
    				
    				Map<String,String> calcxCobe = new HashMap<String,String>();
    				Map<String,String> penalizacion = new HashMap<String,String>();
    				
    				//reembolso
    				Map<String,String>mprem=new HashMap<String,String>(0);
    				mprem.put("TOTALNETO" , "0");
    				mprem.put("SUBTOTAL"  , "0");
    				lprem.add(mprem);
    				//reembolso
    				
    				String destopor = facturaIte.get("DESCPORC");
    				if(StringUtils.isBlank(destopor) || destopor  == null)
    				{
    					facturaObj.put("DESCPORC","0");
    				}
    				String destoimp = facturaIte.get("DESCNUME");
    				if(StringUtils.isBlank(destoimp)  || destoimp  == null)
    				{
    					facturaObj.put("DESCNUME","0");
    				}
    				//Asignaci�n de las variables principales
    				String cdunieco = siniestro.get("CDUNIECO");
    				String cdramo   = siniestro.get("CDRAMO");
    				String estado   = siniestro.get("ESTADO");
    				String nmpoliza = siniestro.get("NMPOLIZA");
    				String nmsuplem = siniestro.get("NMSUPLEM");
    				String nmsituac = siniestro.get("NMSITUAC");
    				String aaapertu = siniestro.get("AAAPERTU");
    				String status   = siniestro.get("STATUS");
    				String nmsinies = siniestro.get("NMSINIES");
    				String nfactura = facturaIte.get("NFACTURA");
    				
    				Map<String,String>copagoDeducibleFacturaIte =siniestrosManager.obtenerCopagoDeducible(
    						cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura);
    				
    				//1.- Obtenemos la informaci�n de Autorizaci�n de Factura
    				Map<String,String>autorizacionesFacturaIte = siniestrosManager.obtenerAutorizacionesFactura(
    						siniestro.get("CDUNIECO"),
    						siniestro.get("CDRAMO"),
    						siniestro.get("ESTADO"),
    						siniestro.get("NMPOLIZA"),
    						siniestro.get("NMSUPLEM"),
    						siniestro.get("NMSITUAC"),
    						siniestro.get("AAAPERTU"),
    						siniestro.get("STATUS"),
    						siniestro.get("NMSINIES"),
    						facturaIte.get("NFACTURA"));
    				facturaObj.put("AUTMEDIC",autorizacionesFacturaIte.get("AUTMEDIC"));
    				facturaObj.put("COMMENME",autorizacionesFacturaIte.get("COMMENME"));
    				facturaObj.put("AUTRECLA",autorizacionesFacturaIte.get("AUTRECLA"));
    				facturaObj.put("COMMENAR",autorizacionesFacturaIte.get("COMMENAR"));
    				
    				
    				//2.- Obtenemo los datos generales del siniestros
    				List<Map<String,String>> informacionGral = siniestrosManager.obtieneDatosGeneralesSiniestro(siniestro.get("CDUNIECO"), siniestro.get("CDRAMO"),
    						siniestro.get("ESTADO"), siniestro.get("NMPOLIZA"),siniestro.get("NMSITUAC"), siniestro.get("NMSUPLEM"), siniestro.get("STATUS"), siniestro.get("AAAPERTU"), siniestro.get("NMSINIES") , facturaIte.get("NTRAMITE"));
    				
    				//3.- Guardamos los valores en calculosPenalizaciones
    				Map<String,String>copagoDeducibleSiniestroIte =siniestrosManager.obtenerCopagoDeducible(siniestro.get("CDUNIECO"), siniestro.get("CDRAMO"),
    						siniestro.get("ESTADO"), siniestro.get("NMPOLIZA"), siniestro.get("NMSUPLEM"),siniestro.get("NMSITUAC"),
    						siniestro.get("AAAPERTU"),siniestro.get("STATUS"),siniestro.get("NMSINIES") ,facturaIte.get("NFACTURA"));
    						
    				String tipoFormatoCalculo = copagoDeducibleSiniestroIte.get("FORMATOCALCULO");
    				String calculosPenalizaciones = copagoDeducibleSiniestroIte.get("PENALIZACIONES");
    				calcxCobe.put("tipoFormatoCalculo",""+tipoFormatoCalculo);
    				calcxCobe.put("calculosPenalizaciones",""+calculosPenalizaciones);
    				datosCoberturaxCal.add(calcxCobe);
    				
    				penalizacion.put("causaSiniestro", informacionGral.get(0).get("CDCAUSA"));
    				//if(facturaIte.get("CDGARANT").equalsIgnoreCase("18HO")||facturaIte.get("CDGARANT").equalsIgnoreCase("18MA"))
    				if(tipoFormatoCalculo.equalsIgnoreCase("1"))
    				{
    					if(calculosPenalizaciones.equalsIgnoreCase("1")){
    						HashMap<String, Object> paramExclusion = new HashMap<String, Object>();
    						paramExclusion.put("pv_cdunieco_i",siniestro.get("CDUNIECO"));
    						paramExclusion.put("pv_estado_i",siniestro.get("ESTADO"));
    						paramExclusion.put("pv_cdramo_i",siniestro.get("CDRAMO"));
    						paramExclusion.put("pv_nmpoliza_i",siniestro.get("NMPOLIZA"));
    						paramExclusion.put("pv_nmsituac_i",siniestro.get("NMSITUAC"));
    						if(siniestro.get("CDRAMO").toString().equalsIgnoreCase("2")){
    							//--> SALUD VITAL
    							//1.- Verificamos si existe exclusi�n de penalizaci�n
        						existePenalizacion = siniestrosManager.validaExclusionPenalizacion(paramExclusion);
        						//2.- Obtenemos la penalizaci�n por cambio de Zona
        						penalizacionCambioZona = penalizacionCambioZona(existePenalizacion,informacionGral.get(0).get("CDCAUSA"),informacionGral.get(0).get("CIRHOSPI"),
        								informacionGral.get(0).get("DSZONAT"),facturaIte.get("CDPRESTA"),siniestro.get("CDRAMO"));
        						//3.- Obtenemos la penalizaci�n por circulo Hospitalario
        						List<ConsultaProveedorVO> medicos = siniestrosManager.getConsultaListaProveedorMedico(Rol.MEDICO.getCdrol(),facturaIte.get("CDPRESTA"));
        						penalizacionCirculoHosp = calcularPenalizacionCirculo(informacionGral.get(0).get("CIRHOSPI"), medicos.get(0).getCirculo(),informacionGral.get(0).get("CDCAUSA"));
    						}else{
    							// --> DIFERENTE DE SALUD VITAL
    							penalizacionCambioZona = 0d;
    							penalizacionCirculoHosp = 0d;
    						}
    					}
    				}
    				penalizacion.put("penalizacionCambioZona",""+penalizacionCambioZona);
    				penalizacion.put("penalizacionCirculoHosp",""+penalizacionCirculoHosp);
    				
    				//3.- Obtenemos el total de penalizaci�n
    				String calcularTotalPenalizacion = calcularTotalPenalizacion(penalizacionCambioZona,penalizacionCirculoHosp,informacionGral.get(0).get("CDCAUSA"),
    																			 copagoDeducibleFacturaIte.get("COPAGO"),copagoDeducibleFacturaIte.get("TIPOCOPAGO"),
    																			 informacionGral.get(0).get("CDPROVEE"),siniestro.get("CDRAMO"), informacionGral.get(0).get("FEOCURRE"));
    				
    				
    				penalizacion.put("totalPenalizacionGlobal",""+calcularTotalPenalizacion);
    				String penalizacionT[] = calcularTotalPenalizacion.split("\\|");
    				penalizacion.put("copagoPorcentajes",penalizacionT[0].toString());
    				penalizacion.put("copagoPesos",penalizacionT[1].toString());
    				datosPenalizacion.add(penalizacion);
    				
    				String sDeducibleFacturaIte     = copagoDeducibleFacturaIte.get("DEDUCIBLE").replace(",","");
    				String sCopagoFacturaIte        = copagoDeducibleFacturaIte.get("COPAGO").replace(",","");
    				String tipoCopagoFacturaIte     = copagoDeducibleFacturaIte.get("TIPOCOPAGO");
    				
    				//OBTENEMOS LOS VALORES DE PENALIZACION Y COPAGO
    				if(StringUtils.isNotBlank(sDeducibleFacturaIte)
    						&&(!sDeducibleFacturaIte.equalsIgnoreCase("na"))
    						&&(!sDeducibleFacturaIte.equalsIgnoreCase("no"))
    						)
    				{
    					try
    					{
    						deducibleFacturaIte = Double.valueOf(sDeducibleFacturaIte);
    					}
    					catch(Exception ex)
    					{
    						logger.debug(""
    								+ "\n### ERROR ##################################################"
    								+ "\n### no es numero deducible: '"+sDeducibleFacturaIte+"' ###"
    								+ "\n############################################################"
    								);
    						deducibleFacturaIte = 0d;
    					}
    				}
    				if(StringUtils.isNotBlank(sCopagoFacturaIte)
    						&&(!sCopagoFacturaIte.equalsIgnoreCase("na"))
    						&&(!sCopagoFacturaIte.equalsIgnoreCase("no"))
    						)
    				{
    					try
    					{
    						cantidadCopagoFacturaIte = Double.valueOf(sCopagoFacturaIte);
    					}
    					catch(Exception ex)
    					{
    						logger.debug(""
    								+ "\n### ERROR ############################################"
    								+ "\n### no es numero copago: '"+sCopagoFacturaIte+"' ###"
    								+ "\n######################################################"
    								);
    						cantidadCopagoFacturaIte = 0d;
    					}
    				}
    				
    				slist2.add(copagoDeducibleFacturaIte);
    				
    				Map<String,String>datosProveedor =siniestrosManager.obtenerDatosProveedor(facturaIte.get("CDPRESTA")); 
    				slist3.add(datosProveedor);
    				double ivaprov=Double.parseDouble(datosProveedor.get("IVA"));
    				
    				List<Map<String,String>>listaConceptosFactura = new ArrayList<Map<String,String>>();
    				llist1.add(listaConceptosFactura);
    				for(Map<String,String>concepto : conceptos)
    				{
    					if(concepto.get("NFACTURA").equals(facturaIte.get("NFACTURA")))
    					{
    						listaConceptosFactura.add(concepto);
    							logger.debug(">>REEMBOLSO");
    							Map<String,String>row=new HashMap<String,String>();
    							row.putAll(concepto);
    							
    							double ptimport = Double.parseDouble(row.get("PTIMPORT"));
    							logger.debug("ptimport "+ptimport);
    							
    							double ajusteaplica = 0d;
    							if(StringUtils.isNotBlank(row.get("PTIMPORT_AJUSTADO")))
    							{
    								ajusteaplica = Double.parseDouble(row.get("PTIMPORT_AJUSTADO"));
    							}
    							logger.debug("ajusteaplica "+ajusteaplica);
    							
    							double subtotal = ptimport-ajusteaplica;
    							logger.debug("subtotal "+subtotal);
    							row.put("SUBTOTAL",subtotal+"");
    							
    							double gtotalneto = Double.parseDouble(mprem.get("TOTALNETO"));
    							double gsubtotal  = Double.parseDouble(mprem.get("SUBTOTAL"));
    							logger.debug("base totalneto " + gtotalneto);
    							logger.debug("base subtotal "  + gsubtotal);
    							gtotalneto += ptimport;
    							gsubtotal  += subtotal;
    							logger.debug("new totalneto " + gtotalneto);
    							logger.debug("new subtotal "  + gsubtotal);
    							
    							mprem.put("TOTALNETO" , gtotalneto + "");
    							mprem.put("SUBTOTAL"  , gsubtotal  + "");
    							
    							concepto.putAll(row);
    							logger.debug("<<REEMBOLSO");
    						//pago reembolso
    					}
    				}
    				
    				//Verificamos la informaci�n del deducible
    				//if(facturaIte.get("CDGARANT").equalsIgnoreCase("18HO")||facturaIte.get("CDGARANT").equalsIgnoreCase("18MA"))
    				if(tipoFormatoCalculo.equalsIgnoreCase("1")){
    					//verificamos la causa del siniestro
    					String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
    					if(causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
    						deducibleFacturaIte = 0d;
    					}
    				}
    				
    				logger.debug(">>Calculando total factura iterada para WS");
    				logger.debug("deducible :"+deducibleFacturaIte);
    				logger.debug("scopago: "+sCopagoFacturaIte);
    				logger.debug("tipocopago: "+tipoCopagoFacturaIte);
    				logger.debug("facturaIte.get(DESCPORC) -->"+facturaIte.get("DESCPORC"));
    				double totalFactura  = Double.valueOf(mprem.get("SUBTOTAL"));
    				double destoPorFac = 0d;
    				double destoImpFac= 0d;
    				if(!StringUtils.isBlank(facturaIte.get("DESCPORC")) || !(facturaIte.get("DESCPORC")  == null))
    				{
    					destoPorFac = Double.valueOf(facturaIte.get("DESCPORC"));
    				}
    				if(!StringUtils.isBlank(facturaIte.get("DESCNUME"))  || !(facturaIte.get("DESCNUME")  == null))
    				{
    					destoImpFac = Double.valueOf(facturaIte.get("DESCNUME"));
    				}
    				//double destoPorFac   = Double.valueOf(facturaIte.get("DESCPORC"));
    				//double destoImpFac   = Double.valueOf(facturaIte.get("DESCNUME"));
    				double destoAplicado = (totalFactura*(destoPorFac/100d)) + destoImpFac;
    				logger.debug("subtotal: "+totalFactura);
    				totalFactura -= destoAplicado;
    				logger.debug("subtotal desto: "+totalFactura);
    				totalFactura -= deducibleFacturaIte;
    				logger.debug("subtotal deducible: "+totalFactura);
    				
    				if(StringUtils.isNotBlank(tipoCopagoFacturaIte))
    				{
    					String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
    					//if(facturaIte.get("CDGARANT").equalsIgnoreCase("18HO")||facturaIte.get("CDGARANT").equalsIgnoreCase("18MA"))
    					if(tipoFormatoCalculo.equalsIgnoreCase("1"))
    					{
    						//verificamos la causa del siniestro
    						if(!causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
    							//Diferente de accidente
    							copagoAplicadoFacturaIte = Double.parseDouble(penalizacionT[1].toString()) + (totalFactura * ( Double.parseDouble(penalizacionT[0].toString()) / 100d ));
    						}else{
    							copagoAplicadoFacturaIte = 0d;
    						}
    					}else{
    						//COBERTURA DIFERENTE HOSPITALIZACI�N Y AYUDA DE MATERNIDAD
    							if(tipoCopagoFacturaIte.equalsIgnoreCase("$")){
    								copagoAplicadoFacturaIte = cantidadCopagoFacturaIte;
    							}
    							if(tipoCopagoFacturaIte.equalsIgnoreCase("%"))
    							{
    								copagoAplicadoFacturaIte = totalFactura * ( cantidadCopagoFacturaIte / 100d );
    							}
    					}
    				}
    				totalFactura -= copagoAplicadoFacturaIte;
    				logger.debug("total copago (final): "+totalFactura);
    				logger.debug("<<Calculando total factura iterada para WS");
    				
    				importeSiniestroUnico += totalFactura;
    			}
    			
    			logger.debug(">>WS del siniestro unico");
    			importesWSSiniestroUnico.put(IMPORTE_WS_IMPORTE , (new Double(importeSiniestroUnico)).toString());
    			importesWSSiniestroUnico.put(IMPORTE_WS_IVA     , (new Double(ivaSiniestroUnico)    ).toString());
    			importesWSSiniestroUnico.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroUnico)    ).toString());
    			importesWSSiniestroUnico.put(IMPORTE_WS_ISR     , (new Double(isrSiniestroUnico)    ).toString());
    			importesWSSiniestroUnico.put(IMPORTE_WS_CEDULAR , (new Double(cedularSiniestroUnico)).toString());
    			logger.debug("mapa WS siniestro unico: "+importesWSSiniestroUnico);
    			logger.debug("<<WS del siniestro unico");
    			
    		}
    		
    		if(conceptos!=null&&conceptos.size()>0)
    		{
    			logger.debug("conceptos[0]: "+conceptos);
    		}
    		
    		seccion     = "FORM_TRAMITE";
    		componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
    		gc.generaComponentes(componentes, true, true, true, false, false, false);
    		imap.put("tramiteFields" , gc.getFields());
    		imap.put("tramiteItems"  , gc.getItems());
    		
    		seccion     = "FACTURA";
    		componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
    		gc.generaComponentes(componentes, true, true, true, true, false, false);
    		imap.put("facturaFields"  , gc.getFields());
    		imap.put("facturaItems"   , gc.getItems());
    		imap.put("facturaColumns" , gc.getColumns());
    		
    		seccion     = "SINIESTRO";
    		componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
    		gc.generaComponentes(componentes, true, true, true, true, false, false);
    		imap.put("siniestroFields"  , gc.getFields());
    		imap.put("siniestroItems"   , gc.getItems());
    		imap.put("siniestroColumns" , gc.getColumns());
    		
    		seccion     = "PROVEEDOR";
    		componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
    		gc.generaComponentes(componentes, true, true, true, false, false, false);
    		imap.put("proveedorFields" , gc.getFields());
    		imap.put("proveedorItems"  , gc.getItems());
    		
    		seccion     = "CONCEPTO";
    		componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
    		gc.generaComponentes(componentes, true, true, false, true, false, false);
    		imap.put("conceptoFields"  , gc.getFields());
    		imap.put("conceptoColumns" , gc.getColumns());
    	
    		seccion     = "FORM_AUTORIZA";
    		componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
    		gc.generaComponentes(componentes, true, false, true, false, false, false);
    		imap.put("autorizaItems",gc.getItems());
    		
    		pantalla    = "RECHAZO_SINIESTRO";
    		seccion     = "FORMULARIO";
    		componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
    		gc.generaComponentes(componentes, true, false, true, false, false, false);
    		imap.put("rechazoitems",gc.getItems());
    	}
    	catch(Exception ex)
    	{
    		logger.debug("error al cargar pantalla de calculo de siniestros",ex);
    	}
    	logger.debug(""
    			+ "\n######                   ######"
    			+ "\n###### calculoSiniestros ######"
    			+ "\n###############################"
    			+ "\n###############################"
    			);
    	return SUCCESS;
    }
    
    
    //calcularPorcentajeTotalPenalizacion(penalizacionCambioZona,penalizacionCirculoHosp,informacionGral.get(0).get("CDCAUSA"),copagoDeducibleSiniestroIte.get("COPAGO"),copagoDeducibleSiniestroIte.get("TIPOCOPAGO"));
    private String calcularTotalPenalizacion(double penalizacionCambioZona, double penalizacionCirculoHosp, String causaSiniestro, String copagoOriginal, String tipoCopago,String proveedor,String ramo, String fechaOcurrencia) {
    	double copagoPenaPorcentaje = 0d;
    	double copagoPenaPesos = 0d;
    	String copagoFinal= null;
    	
    	double copagoOriginalPoliza = 0d;
    	String copagoModificado= copagoOriginal.replaceAll(",", "");
    	
    	if(copagoOriginal.equalsIgnoreCase("no") || copagoOriginal.equalsIgnoreCase("na")){
    		copagoOriginalPoliza = 0d;
    	}else{
    		if(copagoOriginal.equalsIgnoreCase("#######")){
    			copagoOriginalPoliza = 0d;
    		}else{
    			copagoOriginalPoliza= Double.parseDouble(copagoModificado);
    		}
    	}
    	
    	if(causaSiniestro != null)
    	{
    		if(!causaSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
    			//1.- Verificamos el el Ramo
    			if(ramo.equalsIgnoreCase("2")){ //SALUD VITAL
            		if(tipoCopago.equalsIgnoreCase("%")){
            			copagoPenaPorcentaje = penalizacionCambioZona + penalizacionCirculoHosp + Double.parseDouble(""+copagoOriginalPoliza);
            			if(copagoPenaPorcentaje <= 0){
            				copagoPenaPorcentaje= 0d;
            			}
            			copagoFinal = copagoPenaPorcentaje+"|"+copagoPenaPesos;
            		}else{
            			copagoPenaPorcentaje = penalizacionCambioZona + penalizacionCirculoHosp;
            			if(copagoPenaPorcentaje <= 0){
            				copagoPenaPorcentaje= 0d;
            			}
            			copagoPenaPesos		 = Double.parseDouble(""+copagoOriginalPoliza);
            			copagoFinal = copagoPenaPorcentaje+"|"+copagoPenaPesos;
            		}    				
    			}else{ // DIFERENTE DE SALUD VITAL
    				try {
						datosInformacionAdicional = siniestrosManager.listaConsultaCirculoHospitalario(proveedor,ramo,fechaOcurrencia);
						logger.debug(datosInformacionAdicional.get(0).get("MULTINCREMENTO"));
	    				logger.debug(datosInformacionAdicional.get(0).get("HOSPITALPLUS"));
	    				logger.debug(datosInformacionAdicional.get(0).get("PORCINCREMENTO"));
	    				if(datosInformacionAdicional.get(0).get("HOSPITALPLUS").toString().equalsIgnoreCase("0")){
	    					copagoFinal = copagoPenaPorcentaje+"|"+copagoPenaPesos;
	    				}else{
	    					if(tipoCopago.equalsIgnoreCase("%")){
	                			copagoPenaPorcentaje =  Double.parseDouble(""+copagoOriginalPoliza) + Double.parseDouble(""+datosInformacionAdicional.get(0).get("PORCINCREMENTO").toString());
	                			if(copagoPenaPorcentaje <= 0){
	                				copagoPenaPorcentaje= 0d;
	                			}
	                			copagoFinal = copagoPenaPorcentaje+"|"+copagoPenaPesos;
	                		}else{
	                			copagoPenaPorcentaje = Double.parseDouble(""+datosInformacionAdicional.get(0).get("PORCINCREMENTO").toString());
	                			if(copagoPenaPorcentaje <= 0){
	                				copagoPenaPorcentaje= 0d;
	                			}
	                			copagoPenaPesos		 = Double.parseDouble(""+copagoOriginalPoliza);
	                			copagoFinal = copagoPenaPorcentaje+"|"+copagoPenaPesos;
	                		}
	    				}
	    				
    				} catch (Exception e) {
    					logger.error("error al obtener los datos del proveedor",e);
    				}
    				
    				/*
    							if(Ext.getCmp('idHospitalPlus').getValue() =="0"){
									Ext.getCmp('idCopagoFin').setValue(copagoOrig);
									return true;
								}else{
									var valorCopago = 0;
									if( copagoOrig =="NO" || copagoOrig =="NA" || copagoOrig =="null"|| copagoOrig == null)
							    	{
							    		valorCopago = 0;
							    	}else{
							    		valorCopago = copagoOrig;
							    	}
							    	
							    	if(tipoCopago =="$")
								    {
								    	Ext.getCmp('idCopagoFin').setValue("$"+valorCopago +" y "+ Ext.getCmp('idPorcIncremento').getValue() +"%");
								        return true;
								    }
								    if(tipoCopago =="%")
								    {
								    	var sumatoria = + valorCopago + +Ext.getCmp('idPorcIncremento').getValue();
										Ext.getCmp('idCopagoFin').setValue(sumatoria);
								        return true;
								    }
								}
    				*/
    			}
    		}else{
    			if(tipoCopago.equalsIgnoreCase("%")){
        			copagoFinal = copagoOriginalPoliza+"|"+copagoPenaPesos;
        		}else{
        			copagoFinal = copagoPenaPorcentaje+"|"+copagoOriginalPoliza;
        		}
    		}
    	}else{
    		copagoFinal = copagoPenaPorcentaje+"|"+copagoOriginalPoliza;
    	}
    	return copagoFinal;
	}


	private double penalizacionCambioZona(String existePenalizacion, String causaSiniestro, String circuloHospAsegurado,
    		String zonaTarifiAsegurado, String idProveedor, String cdRamo) {
		double penalizacionCambioZona = 0;
		if(causaSiniestro!= null){
			if(!causaSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
				//obtenemos la penalizaci�n por el cambio de zona
				if(!existePenalizacion.equalsIgnoreCase("S")){
					//obtenemos el valor del porcentaje
					try{
						//Obtenemos la informacion de la zona hospitalaria
						List<ConsultaProveedorVO> medicos = siniestrosManager.getConsultaListaProveedorMedico(Rol.MEDICO.getCdrol(),idProveedor);
						//	String circuloHosProveedor = medicos.get(0).getCirculo();
						porcentajePenalizacion = siniestrosManager.validaPorcentajePenalizacion(zonaTarifiAsegurado, medicos.get(0).getZonaHospitalaria(), cdRamo);
						penalizacionCambioZona =  Double.parseDouble(porcentajePenalizacion);
					}catch(Exception ex){
						logger.debug("Error en la obtencion de la consulta"+ex);
						penalizacionCambioZona =  Double.parseDouble("0");
					}
				}
			}
		}
		return penalizacionCambioZona;
	}
    
    
    
    private double calcularPenalizacionCirculo(String circuloHospAsegurado, String circuloHospProveedor, String causaSiniestro){
    	double penaliCirculoHosp= 0;
    	String valor1 = null;
    	String valor2 = null;
    	if(causaSiniestro !=null){
        	if(!causaSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
        		if(circuloHospAsegurado == null){
        			valor1="0";
        		}else{
        			if(circuloHospAsegurado.equalsIgnoreCase("PLUS 100")) {valor1="1";}
                	if(circuloHospAsegurado.equalsIgnoreCase("PLUS 500")) {valor1="2";}
                	if(circuloHospAsegurado.equalsIgnoreCase("PLUS 1000"))
                	{
                		valor1="3";
                	}
                	else{
                		valor1="0";
                	}
        		}
        		
        		if(circuloHospProveedor == null){
        			valor2="0";
        		}else{
        			if(circuloHospProveedor.equalsIgnoreCase("PLUS 100")) {valor2="1";}
                	if(circuloHospProveedor.equalsIgnoreCase("PLUS 500")) {valor2="2";}
                	if(circuloHospProveedor.equalsIgnoreCase("PLUS 1000")){valor2="3";}
        		}
        		
            	
            	
            	String valorCirculoHosp = valor1+""+valor2;
            	switch(Integer.parseInt(valorCirculoHosp))
            	{
    	        case 12 :
    	        case 23 :
    	        	penaliCirculoHosp = 20;
    	            break;
    	        case 13 :
    	        	penaliCirculoHosp = 40;
    	            break;
    	        case 21 :
    	        case 32 :
    	        	penaliCirculoHosp = -5;
    	            break;
    	        case 31 :
    	        	penaliCirculoHosp = -10;
    	            break;
    	        default:
    	        	penaliCirculoHosp = 0;
    	          
            	}
    		}
    	}
    	return penaliCirculoHosp;
    }

    
    public String consultaCalculoSiniestros()
    {
    	logger.debug(""
    			+ "\n#######################################"
    			+ "\n#######################################"
    			+ "\n###### consultaCalculoSiniestros ######"
    			+ "\n######                   		  ######"
    			);
    	logger.debug("params: "+params);
    	try
    	{
    		imap = new HashMap<String,Item>();
    		
    		GeneradorCampos    gc          = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
    		String             pantalla    = "CALCULO_SINIESTRO";
    		String             seccion     = null;
    	    String             ntramite    = params.get("ntramite");
    		List<ComponenteVO> componentes = null;
    		UserVO             usuario     = (UserVO)session.get("USUARIO");
    		String             cdrol       = usuario.getRolActivo().getClave();
    		
    		Map<String,String>       tramite    = siniestrosManager.obtenerTramiteCompleto(ntramite);
    		List<Map<String,String>> facturas   = siniestrosManager.obtenerFacturasTramite(ntramite);
    		List<Map<String,String>> siniestros = siniestrosManager.listaSiniestrosTramite(ntramite,null);
    		
    		smap = tramite;
    		
    		if(tramite==null||siniestros==null||facturas==null)
    		{
    			throw new Exception("No se encontro tramite/facturas/siniestros para el tramite");
    		}
    		
    		/*NTRAMITE=1445, CDUNIECO=null, CDRAMO=2, ESTADO=null, 
NMPOLIZA=null, NMSUPLEM=null, NMSOLICI=null, CDSUCADM=1000, CDSUCDOC=1000, CDSUBRAM=213, CDTIPTRA=16, FERECEPC=26/0
2/2014, CDAGENTE=11060, REFERENCIA=null, NOMBRE=PROSPECTO, FECSTATU=26/02/2014, STATUS=10, COMMENTS=null, CDTIPSIT=
SL, OTVALOR01=15, OTVALOR02=1, OTVALOR03=2000, OTVALOR04=null, OTVALOR05=A100, OTVALOR06=26/02/2014, OTVALOR07=3, O
TVALOR08=1002, OTVALOR09=null, OTVALOR10=null, OTVALOR11=101, OTVALOR12=18SO, OTVALOR13=null, OTVALOR14=18SO003, OT
VALOR15=null, OTVALOR16=null, OTVALOR17=null, OTVALOR18=null, OTVALOR19=null, OTVALOR20=null, OTVALOR21=null, OTVAL
OR22=null, OTVALOR23=null, OTVALOR24=null, OTVALOR25=null, OTVALOR26=null, OTVALOR27=null, OTVALOR28=null, OTVALOR2
9=null, OTVALOR30=null, OTVALOR31=null, OTVALOR32=null, OTVALOR33=null, OTVALOR34=null, OTVALOR35=null, OTVALOR36=n
ull, OTVALOR37=null, OTVALOR38=null, OTVALOR39=null, OTVALOR40=null, OTVALOR41=null, OTVALOR42=null, OTVALOR43=null
, OTVALOR44=null, OTVALOR45=null, OTVALOR46=null, OTVALOR47=null, OTVALOR48=null, OTVALOR49=null, OTVALOR50=null*/
    		logger.debug("tramite: "+tramite);
    		/*[{NTRAMITE=1445, NFACTURA=1002, FFACTURA=26/02/2014, 
CDTIPSER=3, DESCSERVICIO=CONSULTA MCP(Médico General, Familiar, Internista, Pediatra, Ginecólogo y Odontólogo), CDP
RESTA=101, NOMBREPROVEEDOR=HOMERO,RUEDA DE LEON CASTILLO, PTIMPORT=2000, CDGARANT=18SO, DSGARANT=Servicios Odontoló
gicos, DESCPORC=0, DESCNUME=0, CDCONVAL=18SO003, DSSUBGAR=Profilaxis}]*/
    		logger.debug("facturas: "+facturas);
    		/*[{NMSINIES=20, NMAUTSER=12, CDPERSON=517982, NOMBRE
=ALVAROJAIR,MARTINEZ VARELA, FEOCURRE=27/02/2014, CDUNIECO=1006, DSUNIECO=SALUD TIJUANA, CDRAMO=2, DSRAMO=SALUD VIT
AL, CDTIPSIT=SL, DSTIPSIT=SALUD VITAL, STATUS=W, ESTADO=M, NMPOLIZA=44, VOBOAUTO=S, CDICD=A500, DSICD=SIFILIS CONGE
NITA PRECOZ, SINTOMATICA, CDICD2=A600, DSICD2=INFECCION DE GENITALES Y TRAYECTO UROGENITAL DEBIDA A VIRUS DEL HERPE
S (HERPES S, DESCPORC=12.5, DESCNUME=300, :B2=null, PTIMPORT=3000, AUTRECLA=S, NMRECLAMO=6969, COMMENAR=OK, COMMENM
E=OK, AUTMEDIC=S, AAAPERTU=2014, NMSITUAC=1, NMSUPLEM=245671518430000000}]*/
    		logger.debug("siniestros: "+siniestros);

    		boolean esPagoDirecto = false;
    		if(tramite.get("OTVALOR02").equals("1"))
    		{
    			esPagoDirecto = true;
    		}
    		logger.debug("esPagoDirecto: "+esPagoDirecto);
    		
    		Map<String,String> factura        = null;
    		/*"CDPRESTA" , "NOMBRE" , "ISR" , "CEDULAR" , "IVA"*/
    		Map<String,String> proveedor      = null;
    		Map<String,String> siniestro      = null;
    		
    		/*{CDUNIECO=1006, CDRAMO=2, ESTADO=M, NMPOLIZA=44,
 NMSUPLEM=245671518430000000, NMSITUAC=1, AAAPERTU=2014, STATUS=W, NMSINIES=20, CDCONCEP=1, OTVALOR=Costo Administr
acion de Salud, IDCONCEP=2, DESCRIPC=HCPC, CANTIDAD=2, DESTOPOR=1, DESTOIMP=16, DEDUCIBLE=NA, COPAGO=$100.00, AUTME
DIC=null, COMMENME=null, PTIMPORT=346, IMP_ARANCEL=null}*/
    		List<Map<String,String>>conceptos = null;
    		
    		slist2          = new ArrayList<Map<String,String>>();
    		slist3          = new ArrayList<Map<String,String>>();
    		llist1          = new ArrayList<List<Map<String,String>>>();
    		lhosp           = new ArrayList<Map<String,String>>();
    		lpdir           = new ArrayList<Map<String,String>>();
    		lprem           = new ArrayList<Map<String,String>>();
    		datosPenalizacion = new ArrayList<Map<String,String>>();
    		datosCoberturaxCal = new ArrayList<Map<String,String>>();
    		listaImportesWS = new ArrayList<Map<String,String>>(); 
    		if(esPagoDirecto)
    		{
    			factura   = facturas.get(0);
    			facturas  = null;
    			proveedor = siniestrosManager.obtenerDatosProveedor(factura.get("CDPRESTA"));
    			smap2     = factura;
    			smap3     = proveedor;
    			smap.put("PAGODIRECTO","S");
    			conceptos = siniestrosManager.P_GET_CONCEPTOS_FACTURA(
    					null, null, null, null, null, null, null, null, null, factura.get("NFACTURA"));
    			slist1    = siniestros;
    			
    			double ivaprov = Double.parseDouble(proveedor.get("IVA")); 
    			double cedprov = Double.parseDouble(proveedor.get("CEDULAR"));
    			double isrprov = Double.parseDouble(proveedor.get("ISR"));

    			//OBTENEMOS LOS SINIESTROS
    			for(Map<String,String>siniestroIte:siniestros)
    			{
    				String cdunieco = siniestroIte.get("CDUNIECO");
    				String cdramo   = siniestroIte.get("CDRAMO");
    				String estado   = siniestroIte.get("ESTADO");
    				String nmpoliza = siniestroIte.get("NMPOLIZA");
    				String nmsuplem = siniestroIte.get("NMSUPLEM");
    				String nmsituac = siniestroIte.get("NMSITUAC");
    				String aaapertu = siniestroIte.get("AAAPERTU");
    				String status   = siniestroIte.get("STATUS");
    				String nmsinies = siniestroIte.get("NMSINIES");
    				String nfactura = factura.get("NFACTURA");
    				
    				Map<String,String> penalizacion = new HashMap<String,String>();
    				Map<String,String> calcxCobe = new HashMap<String,String>();
    				double penalizacionCambioZona =0d;
    				double penalizacionCirculoHosp =0d;
    				String aplicaIVA= "S";
					String seleccionAplica= "D";
					String ivaRetenido= "N";
					double deducibleSiniestroIte      = 0d;
    				double copagoAplicadoSiniestroIte = 0d;
    				double cantidadCopagoSiniestroIte = 0d;
    				String penalizacionPesos = "0";
    				String penalizacionPorcentaje = "0";
    				
    				//0.- Guardamos datos adicionales en factura
    				Map<String,String>autorizacionesFactura = siniestrosManager.obtenerAutorizacionesFactura(
    						cdunieco, cdramo, estado, nmpoliza, nmsuplem,
    						nmsituac, aaapertu, status, nmsinies, nfactura);
    				factura.put("AUTMEDIC"+nmsinies,autorizacionesFactura.get("AUTMEDIC"));
    				factura.put("COMMENME"+nmsinies,autorizacionesFactura.get("COMMENME"));
    				factura.put("AUTRECLA"+nmsinies,autorizacionesFactura.get("AUTRECLA"));
    				factura.put("COMMENAR"+nmsinies,autorizacionesFactura.get("COMMENAR"));
    				
    				//1.- Obtenemos los datos generales del siniestros
    				List<Map<String,String>> informacionGral = siniestrosManager.obtieneDatosGeneralesSiniestro(cdunieco, cdramo,
    						estado, nmpoliza,nmsituac, nmsuplem, status, aaapertu, nmsinies, factura.get("NTRAMITE"));
    				
    				if(informacionGral.size()>0){
    					penalizacion.put("causaSiniestro", informacionGral.get(0).get("CDCAUSA"));
    				}else{
    					penalizacion.put("causaSiniestro", CausaSiniestro.ENFERMEDAD.getCodigo());
    				}
    				
    				Map<String,String>copagoDeducibleSiniestroIte =siniestrosManager.obtenerCopagoDeducible(
    						cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura);
    				//logger.debug("%%%%RESPUESTA%%%%"+copagoDeducibleSiniestroIte);
    				//copagoDeducibleSiniestroIte.get("TIPOCOPAGO");
    				String tipoFormatoCalculo = copagoDeducibleSiniestroIte.get("FORMATOCALCULO");
    				String calculosPenalizaciones = copagoDeducibleSiniestroIte.get("PENALIZACIONES");
    				calcxCobe.put("tipoFormatoCalculo",""+tipoFormatoCalculo);
					calcxCobe.put("calculosPenalizaciones",""+calculosPenalizaciones);
					datosCoberturaxCal.add(calcxCobe);
					
    				//4.- Verificamos si va a necesitar calculos de penalizaciones
    				if(calculosPenalizaciones.equalsIgnoreCase("1")){
    		   			
    		   			//4.1.- Verificamos si existe exclusi�n de penalizaci�n
    		   			HashMap<String, Object> paramExclusion = new HashMap<String, Object>();
        		   		paramExclusion.put("pv_cdunieco_i",cdunieco);
        		   		paramExclusion.put("pv_estado_i",estado);
        		   		paramExclusion.put("pv_cdramo_i",cdramo);
        		   		paramExclusion.put("pv_nmpoliza_i",nmpoliza);
        		   		paramExclusion.put("pv_nmsituac_i",nmsituac);
        		   		if(cdramo.equalsIgnoreCase("2")){
	        		   		existePenalizacion = siniestrosManager.validaExclusionPenalizacion(paramExclusion);
	        		   		//4.2.- Obtenemos la penalizaci�n por cambio de Zona
	        		   		penalizacionCambioZona = penalizacionCambioZona(existePenalizacion,informacionGral.get(0).get("CDCAUSA"),informacionGral.get(0).get("CIRHOSPI"),
	        						informacionGral.get(0).get("DSZONAT"),informacionGral.get(0).get("CDPROVEE"),cdramo);
	        				penalizacion.put("penalizacionCambioZona",""+penalizacionCambioZona);
	        				//4.3.- Obtenemos la penalizaci�n por circulo Hospitalario
	        				penalizacionCirculoHosp = calcularPenalizacionCirculo(informacionGral.get(0).get("CIRHOSPI"), informacionGral.get(0).get("CIRHOPROV"),informacionGral.get(0).get("CDCAUSA"));
	        				penalizacion.put("penalizacionCirculoHosp",""+penalizacionCirculoHosp);
        		   		}else{
        		   			penalizacion.put("penalizacionCambioZona",""+penalizacionCambioZona);
        		   			penalizacion.put("penalizacionCirculoHosp",""+penalizacionCirculoHosp);
        		   		}
        				
    		   		}else{
    		   			//4.2.- Obtenemos la penalizaci�n por cambio de Zona
    		   			penalizacion.put("penalizacionCambioZona",""+penalizacionCambioZona);
    		   			//4.3.- Obtenemos la penalizaci�n por circulo Hospitalario
        				penalizacion.put("penalizacionCirculoHosp",""+penalizacionCirculoHosp);
    		   		}
    				
    				//4.4.- Obtenemos el total de penalizaci�n
    				String calcularTotalPenalizacion = calcularTotalPenalizacion(penalizacionCambioZona,penalizacionCirculoHosp,informacionGral.get(0).get("CDCAUSA"),
    																			 copagoDeducibleSiniestroIte.get("COPAGO"),copagoDeducibleSiniestroIte.get("TIPOCOPAGO"),
    																			 informacionGral.get(0).get("CDPROVEE"),cdramo, informacionGral.get(0).get("FEOCURRE"));

    				penalizacion.put("totalPenalizacionGlobal",""+calcularTotalPenalizacion);
    				String penalizacionT[] = calcularTotalPenalizacion.split("\\|");
    				penalizacionPorcentaje = penalizacionT[0].toString();
    				penalizacionPesos = penalizacionT[1].toString();
    				penalizacion.put("copagoPorcentajes",penalizacionPorcentaje);
                    penalizacion.put("copagoPesos",penalizacionPesos);
    				datosPenalizacion.add(penalizacion);
    				
    				//5.- Obtenemos informaci�n adicional de las facturas, para realizar la validaci�n de aplica IVA o No
    				List<Map<String, String>> listaFactura = siniestrosManager.P_GET_FACTURAS_SINIESTRO(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies);
    				if(listaFactura.get(0).get("APLICA_IVA") != null){
    					aplicaIVA= listaFactura.get(0).get("APLICA_IVA");
    					seleccionAplica =listaFactura.get(0).get("ANTES_DESPUES");
    					ivaRetenido = listaFactura.get(0).get("IVARETENIDO");
    					if(!StringUtils.isNotBlank(ivaRetenido)){
    						ivaRetenido= "N";
    					}
    				}
    				
    				String sDeducibleSiniestroIte     = copagoDeducibleSiniestroIte.get("DEDUCIBLE").replace(",","");
    				String sCopagoSiniestroIte        = copagoDeducibleSiniestroIte.get("COPAGO").replace(",", "");
    				String tipoCopagoSiniestroIte     = copagoDeducibleSiniestroIte.get("TIPOCOPAGO");
    				
    				//Verificacaci�n de la informaci�n de Deducible
    				if(StringUtils.isNotBlank(sDeducibleSiniestroIte)
    						&&(!sDeducibleSiniestroIte.equalsIgnoreCase("na"))
    						&&(!sDeducibleSiniestroIte.equalsIgnoreCase("no"))
    						)
    				{
    					try
    					{
    						deducibleSiniestroIte = Double.valueOf(sDeducibleSiniestroIte);
    					}
    					catch(Exception ex)
    					{
    						logger.debug(""
    								+ "\n### ERROR ##################################################"
    								+ "\n### no es numero deducible: '"+sDeducibleSiniestroIte+"' ###"
    								+ "\n############################################################"
    								);
    						deducibleSiniestroIte = 0d;
    					}
    				}
    				//Verificacaci�n de la informaci�n de copago
    				if(StringUtils.isNotBlank(sCopagoSiniestroIte)
    						&&(!sCopagoSiniestroIte.equalsIgnoreCase("na"))
    						&&(!sCopagoSiniestroIte.equalsIgnoreCase("no"))
    						)
    				{
    					try
    					{
    						cantidadCopagoSiniestroIte = Double.valueOf(sCopagoSiniestroIte);
    					}
    					catch(Exception ex)
    					{
    						logger.debug(""
    								+ "\n### ERROR ############################################"
    								+ "\n### no es numero copago: '"+sCopagoSiniestroIte+"' ###"
    								+ "\n######################################################"
    								);
    						cantidadCopagoSiniestroIte = 0d;
    					}
    				}
    				slist2.add(copagoDeducibleSiniestroIte);
    				
    				List<Map<String,String>>listaConceptosSiniestro = new ArrayList<Map<String,String>>();
    				llist1.add(listaConceptosSiniestro);
    				
    				Map<String,String>importesWSSiniestroIte=new HashMap<String,String>();
    				importesWSSiniestroIte.put("cdunieco" , siniestroIte.get("CDUNIECO"));
    				importesWSSiniestroIte.put("cdramo"   , siniestroIte.get("CDRAMO"));
    				importesWSSiniestroIte.put("estado"   , siniestroIte.get("ESTADO"));
    				importesWSSiniestroIte.put("nmpoliza" , siniestroIte.get("NMPOLIZA"));
    				importesWSSiniestroIte.put("nmsuplem" , siniestroIte.get("NMSUPLEM"));
    				importesWSSiniestroIte.put("nmsituac" , siniestroIte.get("NMSITUAC"));
    				importesWSSiniestroIte.put("aaapertu" , siniestroIte.get("AAAPERTU"));
    				importesWSSiniestroIte.put("status"   , siniestroIte.get("STATUS"));
    				importesWSSiniestroIte.put("nmsinies" , siniestroIte.get("NMSINIES"));
    				importesWSSiniestroIte.put("ntramite" , ntramite);
    				listaImportesWS.add(importesWSSiniestroIte);
    				double importeSiniestroIte;
    				double ivaSiniestroIte;
    				double ivrSiniestroIte;
    				double isrSiniestroIte;
    				double cedSiniestroIte;
    				
    				//hospitalizacion
    				Map<String,String> hosp = new HashMap<String,String>();
    				lhosp.add(hosp);
    				hosp.put("PTIMPORT" 	, "0");
    				hosp.put("DESTO"    	, "0");
    				hosp.put("IVA"      	, "0");
    				hosp.put("PRECIO"   	, "0");
    				hosp.put("DESCPRECIO"   , "0");
    				hosp.put("IMPISR"   , "0");
    				hosp.put("IMPCED"   , "0");
    				//hospitalizacion
    				
    				//reembolso
    				Map<String,String>mprem=new HashMap<String,String>();
    				mprem.put("dummy","dummy");
    				lprem.add(mprem);
    				//remmbolso
    				
    				//pago directo
    				Map<String,String> mpdir = new HashMap<String,String>();
    				mpdir.put("total","0");
    				mpdir.put("totalcedular","0");
    				mpdir.put("ivaTotalMostrar","0");
    				mpdir.put("ivaRetenidoMostrar","0");
    				mpdir.put("iSRMostrar","0");
					lpdir.add(mpdir);
    				//PAGO DIRECTO --> CONCEPTOS
    				for(Map<String,String>concepto : conceptos)
    				{
    					if(concepto.get("CDUNIECO").equals(siniestroIte.get("CDUNIECO"))
    							&&concepto.get("CDRAMO").equals(siniestroIte.get("CDRAMO"))
    							&&concepto.get("ESTADO").equals(siniestroIte.get("ESTADO"))
    							&&concepto.get("NMPOLIZA").equals(siniestroIte.get("NMPOLIZA"))
    							&&concepto.get("NMSUPLEM").equals(siniestroIte.get("NMSUPLEM"))
    							&&concepto.get("NMSITUAC").equals(siniestroIte.get("NMSITUAC"))
    							&&concepto.get("AAAPERTU").equals(siniestroIte.get("AAAPERTU"))
    							&&concepto.get("STATUS").equals(siniestroIte.get("STATUS"))
    							&&concepto.get("NMSINIES").equals(siniestroIte.get("NMSINIES"))
    							)
    					{
    						listaConceptosSiniestro.add(concepto);
    						
    						//if(factura.get("CDGARANT").equalsIgnoreCase("18HO")||factura.get("CDGARANT").equalsIgnoreCase("18MA") || factura.get("CDGARANT").equalsIgnoreCase("4HOS"))
    						if(tipoFormatoCalculo.equalsIgnoreCase("1"))// CALCULOS PARA CUANDO ES HOSPITALIZACI�N Y AYUDA DE MATERNIDAD
    						{
    							//logger.debug("######  HOSPITALIZACI�N Y AYUDA DE MATERNIDAD  CONCEPTOS ######");
    							logger.debug(">>HOSPITALIZACION");
    							String cveConcepto = concepto.get("CDCONCEP");
    							logger.debug(">>CVECONCEPTO");
    							logger.debug(cveConcepto);
    							double PTIMPORT=Double.parseDouble(concepto.get("PTIMPORT"));
    							double DESTOPOR=Double.parseDouble(concepto.get("DESTOPOR"));
    							double DESTOIMP=Double.parseDouble(concepto.get("DESTOIMP"));
    							double PTPRECIO=Double.parseDouble(concepto.get("PTPRECIO")) * Double.parseDouble(concepto.get("CANTIDAD"));
    							logger.debug("concepto importe "+PTIMPORT);
    							logger.debug("concepto desto "+DESTOPOR);
    							logger.debug("concepto destoimp "+DESTOIMP);
    							logger.debug("usando iva proveedor "+ivaprov);
    							boolean copagoPorc = false;
    							String scopago =concepto.get("COPAGO");
    							if(scopago.equalsIgnoreCase("no") ||scopago.equalsIgnoreCase("na"));
    							{
    								scopago="0";
    							}
    							logger.debug("procesar copago "+scopago);
    							if(StringUtils.isNotBlank(scopago))
    							{
    								if(scopago.contains("%"))
    								{
    									copagoPorc = true;
    								}
    								scopago=scopago.replace("%", "").replace("$", "");
    								if(copagoPorc)
    								{
    									DESTOPOR=DESTOPOR+Double.valueOf(scopago);
    								}
    								else
    								{
    									DESTOIMP=DESTOIMP+Double.valueOf(scopago);
    								}
    							}
    							logger.debug("concepto desto + copago % "+DESTOPOR);
    							logger.debug("concepto destoimp + copago $ "+DESTOIMP);
    							
    							//CALCULOS ANTES DE COPAGO
    							double hPTIMPORT = Double.parseDouble(hosp.get("PTIMPORT"));
    							double hDESTO    = Double.parseDouble(hosp.get("DESTO"));
    							double hIVA      = Double.parseDouble(hosp.get("IVA"));
    							double hISR      = Double.parseDouble(hosp.get("IMPISR"));
    							double hICED      = Double.parseDouble(hosp.get("IMPCED"));
    							double hPRECIO      = Double.parseDouble(hosp.get("PRECIO"));
    							double hDESCPRECIO  = Double.parseDouble(hosp.get("DESCPRECIO"));
    							logger.debug("base import "+hPTIMPORT);
    							logger.debug("base desto "+hDESTO);
    							logger.debug("base iva "+hIVA);
    							hPTIMPORT 	+= PTIMPORT;
    							hDESTO    	+= (PTIMPORT*(DESTOPOR/100d)) + (DESTOIMP);
    							hIVA      	+= PTIMPORT*(ivaprov/100d);
                                hISR		+= PTIMPORT*(isrprov/100d);
                                hICED		+= PTIMPORT*(cedprov/100d);
    							hPRECIO 	+= PTPRECIO;
    							hDESCPRECIO += (PTPRECIO*(DESTOPOR/100d)) + (DESTOIMP);
    							logger.debug("new import "+hPTIMPORT);
    							logger.debug("new desto "+hDESTO);
    							logger.debug("new iva "+hIVA);
    							
    							hosp.put("PTIMPORT" , hPTIMPORT+"");
    							hosp.put("DESTO"    , hDESTO+"");
    							hosp.put("IVA"      , hIVA+"");
    							hosp.put("PRECIO"   , hPRECIO+"");
    							hosp.put("DESCPRECIO", hDESCPRECIO+"");
    							hosp.put("IMPISR"   , hISR+"");
                				hosp.put("IMPCED"   , hICED+"");
    							logger.debug("<<HOSPITALIZACION");
    							//logger.debug("###### HOSPITALIZACI�N Y AYUDA DE MATERNIDAD  CONCEPTOS ######");
    						}
    						//hospitalizacion
    						else
    						//pago directo
    						{
    							/*
    							CDUNIECO=1006,
    							CDRAMO=2,
    							ESTADO=M,
    							NMPOLIZA=44,
    							NMSUPLEM=245671518430000000, 
    							MSITUAC=1, 
    							AAAPERTU=2014, 
    							STATUS=W, 
    							NMSINIES=20, 
    							CDCONCEP=1, 
    							OTVALOR=Costo Administracion de Salud, 
    							IDCONCEP=2, 
    							DESCRIPC=HCPC, 
    							CANTIDAD=2, 
    							DESTOPOR=1, 
    							DESTOIMP=16, 
    							DEDUCIBLE=NA, 
    							COPAGO=$100.00, 
    							AUTMEDIC=null, 
    							COMMENME=null, 
    							PTIMPORT=346, 
    							IMP_ARANCEL=null
    							NFACTURA
    							*/
    							//logger.debug("######  COBERTURA DIFERENTE DE HOSPITALIZACI�N Y AYUDA DE MATERNIDAD  ######");
    							logger.debug(">>PAGO DIRECTO");
    							Map<String,String>row=new HashMap<String,String>();
    							row.putAll(concepto);
    							
    							double cantidad = Double.valueOf(row.get("CANTIDAD"));
    							logger.debug("cantidad "+cantidad);
    							double precioArancel = 0d;
    							//Obtenemos el valor original del arancel
    							if(StringUtils.isNotBlank(row.get("IMP_ARANCEL")))
    							{
    								precioArancel = Double.valueOf(row.get("IMP_ARANCEL"));
    							}
    							row.put("IMP_ARANCEL",precioArancel+"");
    							logger.debug("precioArancel "+precioArancel);
    							double subtotalArancel = cantidad*precioArancel;//++
    							logger.debug("subtotalArancel "+subtotalArancel);
    							row.put("SUBTTARANCEL",subtotalArancel+"");
    							double descuentoPorc   = 0d;
    							double descuentoImpo   = 0d;
    							if(StringUtils.isNotBlank(row.get("DESTOPOR")))
    							{
    								descuentoPorc = Double.parseDouble(row.get("DESTOPOR"));
    							}
    							if(StringUtils.isNotBlank(row.get("DESTOIMP")))
    							{
    								descuentoImpo = Double.parseDouble(row.get("DESTOIMP"));
    							}
    							double descuentoAplicado=(subtotalArancel*(descuentoPorc/100d))+descuentoImpo;//++
    							logger.debug("descuentoAplicado "+descuentoAplicado);
    							row.put("DESTOAPLICA",descuentoAplicado+"");
    							double subtotalDescuento=subtotalArancel-descuentoAplicado;//++
    							logger.debug("subtotalDescuento "+subtotalDescuento);
    							row.put("SUBTTDESCUENTO",subtotalDescuento+""); // SUBTOTAL A OCUPAR CUANDO SEA ANTES DE COPAGO
    							
    							if(aplicaIVA.equalsIgnoreCase("S")){
    			            		if(seleccionAplica.equalsIgnoreCase("A")){ // ANTES DEL COPAGO
    			            			double iVaaplicaAntes = subtotalDescuento*(ivaprov/100d);//++
    			            			row.put("IVAAPLICA",iVaaplicaAntes+"");
    			            		}
    			            	}
    							
    							boolean copagoPorc = false;
    							double  copago = 0d;
    							double  copagoAplicado = 0d;//++
    							String scopago =concepto.get("COPAGO");
    							String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
    							
    							if(causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
    								copagoAplicado = 0d;
    							}else{
    								if(StringUtils.isNotBlank(scopago))
        							{
    									if(scopago.equalsIgnoreCase("na") || scopago.equalsIgnoreCase("no")){
        									copagoAplicado = 0d;
        								}else{
        									if(scopago.contains("%"))
            								{
            									copagoPorc = true;
            								}
            								scopago=scopago.replace("%", "").replace("$", "").replaceAll(",", "");
            								copago=Double.valueOf(scopago);
            								if(copagoPorc)
            								{
            									copagoAplicado=(subtotalDescuento*(copago/100d));
            								}
            								else
            								{
            									copagoAplicado=copago * cantidad;
            								}
        								}
        							}
    							}
    							row.put("COPAGOAPLICA",copagoAplicado+"");
    							logger.debug("copagoAplicado "+copagoAplicado);
    							double subtotalCopago    = subtotalDescuento - copagoAplicado;//++
    							logger.debug("subtotalCopago "+subtotalCopago);
    							row.put("SUBTTCOPAGO",subtotalCopago+"");
    							double israplicado       = subtotalCopago*(isrprov/100d);//++
    							logger.debug("israplicado "+israplicado);
    							row.put("ISRAPLICA",israplicado+"");
    							double subtotalImpuestos = subtotalCopago-(israplicado+0d);//cedularaplicado);//++
    							logger.debug("subtotalImpuestos "+subtotalImpuestos);
    							
    							double totalISRMostrar = Double.parseDouble(mpdir.get("iSRMostrar"));
    							logger.debug("base totalISRMostrar"+totalISRMostrar);
    							totalISRMostrar += israplicado;
    							//logger.debug("new totalISRMostrar"+totalISRMostrar);
    							mpdir.put("iSRMostrar",totalISRMostrar+"");
    							
    							////// modificado
    							double cedularaplicado   = subtotalCopago*(cedprov/100d);//++
    							logger.debug("cedularaplicado "+cedularaplicado);
    							row.put("CEDUAPLICA",cedularaplicado+"");
    							////// modificado
    							
    							subtotalImpuestos = subtotalImpuestos - cedularaplicado;
    							row.put("SUBTTIMPUESTOS",subtotalImpuestos+"");
    							
    			            	double ivaaplicado =0d;
    			            	double ivaRetenidos =0d;
    			            	double ptimportauto =0d;
    			            	//logger.debug("#####...aplicaIVA.....--->"+aplicaIVA);
    			            	
    			            	if(aplicaIVA.equalsIgnoreCase("S")){
    			            		if(seleccionAplica.equalsIgnoreCase("D")){
    			            			ivaaplicado       = subtotalCopago*(ivaprov/100d);//++
    			            			row.put("IVAAPLICA",ivaaplicado+"");
    			            			//logger.debug("#####...ivaRetenido.....--->"+ivaRetenido);
    			            			if(ivaRetenido.equalsIgnoreCase("S")){
    			            				ivaRetenidos      = ((2d * ivaaplicado)/3);
        			            			row.put("IVARETENIDO",ivaRetenidos+"");
    									}else{
    										ivaRetenidos      = 0d;
        			            			row.put("IVARETENIDO",ivaRetenidos+"");
    									}
    			            			ptimportauto      = (subtotalImpuestos-ivaRetenidos)+ivaaplicado;//++
    	    							row.put("PTIMPORTAUTO",ptimportauto+"");
    			            		}else{
    			            			ivaaplicado       = subtotalDescuento*(ivaprov/100d);
    			            			row.put("IVAAPLICA",ivaaplicado+"");
    			            			if(ivaRetenido.equalsIgnoreCase("S")){
    			            				ivaRetenidos      = ((2d * ivaaplicado)/3);
        			            			row.put("IVARETENIDO",ivaRetenidos+"");
    									}else{
    										ivaRetenidos      = 0d;
        			            			row.put("IVARETENIDO",ivaRetenidos+"");
    									}
    			            			ptimportauto      = (subtotalImpuestos-ivaRetenidos)+ivaaplicado; //++
            							row.put("PTIMPORTAUTO",ptimportauto+"");
    			            		}
    			            	}else{
    			            		ivaaplicado       = 0d;//++
    			            		ivaRetenidos      = 0d;
        							row.put("IVAAPLICA",ivaaplicado+"");
        							row.put("IVARETENIDO",ivaRetenidos+"");
        							ptimportauto      = (subtotalImpuestos-ivaRetenidos)+ivaaplicado;//++
        							row.put("PTIMPORTAUTO",ptimportauto+"");
    			            	}
    			            	
    			            	
								double totalIVAMostrar = Double.parseDouble(mpdir.get("ivaTotalMostrar"));
    							logger.debug("base totalIVAMostrar"+totalIVAMostrar);
    							totalIVAMostrar += ivaaplicado;
    							logger.debug("new totalIVAMostrar"+totalIVAMostrar);
    							mpdir.put("ivaTotalMostrar",totalIVAMostrar+"");
    							
    							
    							double totalIVARetenidoMostrar = Double.parseDouble(mpdir.get("ivaRetenidoMostrar"));
    							logger.debug("base totalIVARetenidoMostrar"+totalIVARetenidoMostrar);
    							totalIVARetenidoMostrar += ivaRetenidos;
    							logger.debug("new totalIVAMostrar"+totalIVARetenidoMostrar);
    							mpdir.put("ivaRetenidoMostrar",totalIVARetenidoMostrar+"");
    							
    							double ptimport = Double.parseDouble(row.get("PTIMPORT"));
    							logger.debug("ptimport "+ptimport);
    							
    							double valorusado        = ptimportauto;//++
    							
    							String autmedic = row.get("AUTMEDIC");
    							if(StringUtils.isNotBlank(autmedic)&&autmedic.equalsIgnoreCase("S"))
    							{
    								valorusado = ptimport;
    							}
    							logger.debug("valorusado "+valorusado);
    							row.put("VALORUSADO",valorusado+"");
    							
    							double totalGrupo = Double.parseDouble(mpdir.get("total"));
    							logger.debug("base totalGrupo"+totalGrupo);
    							totalGrupo += valorusado;
    							logger.debug("new totalGrupo"+totalGrupo);
    							mpdir.put("total",totalGrupo+"");
    							
    							double totalGrupoCedular = Double.parseDouble(mpdir.get("totalcedular"));
    							logger.debug("base totalGrupoCedular"+totalGrupoCedular);
    							totalGrupoCedular += cedularaplicado;
    							logger.debug("new totalGrupoCedular"+totalGrupoCedular);
    							mpdir.put("totalcedular",totalGrupoCedular+"");
    							
    							concepto.putAll(row);
    							logger.debug("<<PAGO DIRECTO");
    							//logger.debug("###### COBERTURA DIFERENTE DE HOSPITALIZACI�N Y AYUDA DE MATERNIDAD ######");
    						}
    						//pago directo
    					}
    				}
    				
    				//hospitalizacion
    				//if(factura.get("CDGARANT").equalsIgnoreCase("18HO")||factura.get("CDGARANT").equalsIgnoreCase("18MA"))
    				//logger.debug("######  HOSPITALIZACI�N Y AYUDA DE MATERNIDAD WS ######");
    				if(tipoFormatoCalculo.equalsIgnoreCase("1"))
					{
						logger.debug(">>WS del siniestro iterado");
						logger.debug("deducible siniestro iterado: "+sDeducibleSiniestroIte);
						logger.debug("copago siniestro iterado: "+sCopagoSiniestroIte);
						logger.debug("tipo copago siniestro iterado: "+tipoCopagoSiniestroIte);
						
						double hPTIMPORT = Double.valueOf(hosp.get("PTIMPORT"));
						double DESTOIMP  = Double.valueOf(hosp.get("DESTO"));
						double hIVA      = Double.valueOf(hosp.get("IVA"));
						String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
						double subttDesto =0d;
						
						if(!causadelSiniestro.equalsIgnoreCase("2")){
							//Diferente de accidente
							subttDesto = (hPTIMPORT + DESTOIMP) - (DESTOIMP + deducibleSiniestroIte );
						}else{
							//accidente
							subttDesto = (hPTIMPORT + DESTOIMP) - (DESTOIMP);
						}
						
						if(StringUtils.isNotBlank(tipoCopagoSiniestroIte))
						{
							if(!causadelSiniestro.equalsIgnoreCase("2")){
								//Diferente de accidente
								copagoAplicadoSiniestroIte = Double.parseDouble(penalizacionPesos) + (subttDesto * ( Double.parseDouble(penalizacionPorcentaje) / 100d ));
							}else{
								//accidente
								copagoAplicadoSiniestroIte= 0d;
							}
						}
						
						
						importeSiniestroIte = subttDesto - copagoAplicadoSiniestroIte;
						
						double hIVADesCopago  = importeSiniestroIte*(ivaprov/100d);
						logger.debug("Iva a ocupar despues de copago");
						logger.debug(hIVADesCopago);
						
						hosp.put("PTIMPORT_DESCOPAGO" , importeSiniestroIte+"");
						hosp.put("IVA_DESCOPAGO"    , hIVADesCopago+"");
						
		            	double importeBase=0d;
		            	
		            	importeBase= hPTIMPORT - DESTOIMP;
		            	if(aplicaIVA.equalsIgnoreCase("S")){
		            		//SI LOS VALORES SON ANTES DE COPAGO ENTONCES SE QUEDAN IGUALES LOS VALORES DE DESCUENTO, IVA Y PTIMPORT
		            		if(seleccionAplica.equalsIgnoreCase("D")){ // ANTES DEL COPAGO
		            			hosp.put("IVA"    , hIVADesCopago+"");
		            			hosp.put("BASEIVA" , importeSiniestroIte+"");
		            		}else{
		            			
		            			hosp.put("BASEIVA" , subttDesto+"");
		            		}
		            	}else{
		            		hosp.put("IVA"    ,0d+"");
		            		hosp.put("BASEIVA" , subttDesto+"");
		            	}
						
		            	//APLICAMOS EL IVA RETENIDO
		            	if(ivaRetenido.equalsIgnoreCase("S")){
							ivrSiniestroIte = ((2d * Double.parseDouble(hosp.get("IVA")))/3d);
                            hosp.put("IVARETENIDO"    , ivrSiniestroIte+"");
                            
                        }else{
                        	ivrSiniestroIte = 0d;
                            hosp.put("IVARETENIDO"    , ivrSiniestroIte+"");
                        }
						ivaSiniestroIte = Double.parseDouble(hosp.get("IVA"));//hIVA;
						//ivrSiniestroIte = 0d;
						isrSiniestroIte = Double.valueOf(hosp.get("IMPISR"));
						cedSiniestroIte = Double.valueOf(hosp.get("IMPCED"));
						importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(importeSiniestroIte)).toString());
						importesWSSiniestroIte.put(IMPORTE_WS_IVA     , (new Double(ivaSiniestroIte)    ).toString());
						importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroIte)    ).toString());
						importesWSSiniestroIte.put(IMPORTE_WS_ISR     , (new Double(isrSiniestroIte)    ).toString());
						importesWSSiniestroIte.put(IMPORTE_WS_CEDULAR , (new Double(cedSiniestroIte)    ).toString());
						logger.debug("mapa WS siniestro iterado: "+importesWSSiniestroIte);
						logger.debug("<<WS del siniestro iterado");
						//logger.debug("###### HOSPITALIZACION Y AYUDA DE MATERNIDA WS ######");
					}
					else//pago directo
					{
						//logger.debug("######  COBERTURA DIFERENTE DE HOSPITALIZACI�N Y AYUDA DE MATERNIDAD  ######");
						logger.debug(">>WS del siniestro iterado");
						logger.debug("deducible siniestro iterado: "+sDeducibleSiniestroIte);
						logger.debug("copago siniestro iterado: "+sCopagoSiniestroIte);
						logger.debug("tipo copago siniestro iterado: "+tipoCopagoSiniestroIte);
						
						double totalGrupo = Double.valueOf(mpdir.get("total"));
						
						importeSiniestroIte = totalGrupo / ( 1d + ( ivaprov  / 100d ) );
						
						ivrSiniestroIte = 0d;
						isrSiniestroIte = 0d;
						ivaSiniestroIte = 0d;
						cedSiniestroIte = Double.valueOf(mpdir.get("totalcedular"));
						
						double subttDescuentoSiniestroIte= 0d;
						double subttISRSiniestroIte= 0d;
						double subttcopagototalSiniestroIte=0;
						
						for(Map<String,String>concepto : conceptos)
	    				{
	    					if(concepto.get("CDUNIECO").equals(siniestroIte.get("CDUNIECO"))
	    							&&concepto.get("CDRAMO").equals(siniestroIte.get("CDRAMO"))
	    							&&concepto.get("ESTADO").equals(siniestroIte.get("ESTADO"))
	    							&&concepto.get("NMPOLIZA").equals(siniestroIte.get("NMPOLIZA"))
	    							&&concepto.get("NMSUPLEM").equals(siniestroIte.get("NMSUPLEM"))
	    							&&concepto.get("NMSITUAC").equals(siniestroIte.get("NMSITUAC"))
	    							&&concepto.get("AAAPERTU").equals(siniestroIte.get("AAAPERTU"))
	    							&&concepto.get("STATUS").equals(siniestroIte.get("STATUS"))
	    							&&concepto.get("NMSINIES").equals(siniestroIte.get("NMSINIES"))
	    							)
	    					{
	    						subttDescuentoSiniestroIte+= Double.valueOf(concepto.get("SUBTTDESCUENTO"));
	    						
	    						subttcopagototalSiniestroIte+= Double.valueOf(concepto.get("SUBTTCOPAGO"));
	    						subttISRSiniestroIte+= Double.valueOf(concepto.get("ISRAPLICA"));
	    						ivaSiniestroIte+= Double.valueOf(concepto.get("IVAAPLICA"));
	    						ivrSiniestroIte += ((2 * Double.valueOf(concepto.get("IVAAPLICA")))/3);
	    					}
	    				}
						
						if(aplicaIVA.equalsIgnoreCase("S")){
							if(seleccionAplica.equalsIgnoreCase("D")){
								importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(subttcopagototalSiniestroIte)).toString());
							}else{
								importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(subttDescuentoSiniestroIte)).toString());
							}
							
							if(ivaRetenido.equalsIgnoreCase("S")){
								importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroIte)    ).toString());
							}else{
								importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(0d)    ).toString());
							}
							
						}else{
							importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(subttcopagototalSiniestroIte)).toString());
							importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(0d)    ).toString());
						}
						
						importesWSSiniestroIte.put(IMPORTE_WS_IVA     , (new Double(ivaSiniestroIte)    ).toString());
						importesWSSiniestroIte.put(IMPORTE_WS_ISR     , (new Double(subttISRSiniestroIte)    ).toString());
						importesWSSiniestroIte.put(IMPORTE_WS_CEDULAR , (new Double(cedSiniestroIte)    ).toString());
						logger.debug("mapa WS siniestro iterado: "+importesWSSiniestroIte);
						logger.debug("<<WS del siniestro iterado");
						//logger.debug("###### COBERTURA DIFERENTE DE HOSPITALIZACI�N Y AYUDA DE MATERNIDA ######");
					}
    				
    			}
    		}
    		else//REEMBOLSO
    		{
    			siniestro  = siniestros.get(0);
    			siniestros = null;
    			smap2      = siniestro;
    			smap3      = new HashMap<String,String>();
    			smap3.put("a","a");
    			smap.put("PAGODIRECTO","N");
    			conceptos  = siniestrosManager.P_GET_CONCEPTOS_FACTURA(
    					siniestro.get("CDUNIECO"),
    					siniestro.get("CDRAMO"),
    					siniestro.get("ESTADO"),
    					siniestro.get("NMPOLIZA"),
    					siniestro.get("NMSUPLEM"),
    					siniestro.get("NMSITUAC"),
    					siniestro.get("AAAPERTU"),
    					siniestro.get("STATUS"),
    					siniestro.get("NMSINIES"),
    					null);
    			slist1     = facturas;
    			
    			//hospitalizacion
    			Map<String,String> hosp = new HashMap<String,String>();
				lhosp.add(hosp);
				hosp.put("PTIMPORT" , "0");
				hosp.put("DESTO"    , "0");
				hosp.put("IVA"      , "0");
				//hospitalizacion
				
				//directo
				Map<String,String>mpdir=new HashMap<String,String>();
				mpdir.put("dummy","dummy");
				lpdir.add(mpdir);
				//directo
				
				Map<String,String>importesWSSiniestroUnico=new HashMap<String,String>();
				importesWSSiniestroUnico.put("cdunieco" , siniestro.get("CDUNIECO"));
				importesWSSiniestroUnico.put("cdramo"   , siniestro.get("CDRAMO"));
				importesWSSiniestroUnico.put("estado"   , siniestro.get("ESTADO"));
				importesWSSiniestroUnico.put("nmpoliza" , siniestro.get("NMPOLIZA"));
				importesWSSiniestroUnico.put("nmsuplem" , siniestro.get("NMSUPLEM"));
				importesWSSiniestroUnico.put("nmsituac" , siniestro.get("NMSITUAC"));
				importesWSSiniestroUnico.put("aaapertu" , siniestro.get("AAAPERTU"));
				importesWSSiniestroUnico.put("status"   , siniestro.get("STATUS"));
				importesWSSiniestroUnico.put("nmsinies" , siniestro.get("NMSINIES"));
				importesWSSiniestroUnico.put("ntramite" , ntramite);
				listaImportesWS.add(importesWSSiniestroUnico);
				double importeSiniestroUnico = 0d;
				double ivaSiniestroUnico     = 0d;
				double ivrSiniestroUnico     = 0d;
				double isrSiniestroUnico     = 0d;
				double cedularSiniestroUnico = 0d;
    			
				
    			for(Map<String,String>facturaIte:facturas)
    			{
    				double penalizacionCambioZona = 0d;
    				double penalizacionCirculoHosp = 0d;
    				double totalPenalizacion = 0d;
    				double deducibleFacturaIte      = 0d;
    				double cantidadCopagoFacturaIte = 0d;
    				double copagoAplicadoFacturaIte = 0d;
    				
    				Map<String,String> calcxCobe = new HashMap<String,String>();
    				Map<String,String> penalizacion = new HashMap<String,String>();
    				
    				//reembolso
    				Map<String,String>mprem=new HashMap<String,String>(0);
    				mprem.put("TOTALNETO" , "0");
    				mprem.put("SUBTOTAL"  , "0");
    				lprem.add(mprem);
    				//reembolso
    				
    				String destopor = facturaIte.get("DESCPORC");
    				if(StringUtils.isBlank(destopor))
    				{
    					facturaIte.put("DESCPORC","0");
    				}
    				String destoimp = facturaIte.get("DESCNUME");
    				if(StringUtils.isBlank(destoimp))
    				{
    					facturaIte.put("DESCNUME","0");
    				}
    				//Asignaci�n de las variables principales
    				String cdunieco = siniestro.get("CDUNIECO");
    				String cdramo   = siniestro.get("CDRAMO");
    				String estado   = siniestro.get("ESTADO");
    				String nmpoliza = siniestro.get("NMPOLIZA");
    				String nmsuplem = siniestro.get("NMSUPLEM");
    				String nmsituac = siniestro.get("NMSITUAC");
    				String aaapertu = siniestro.get("AAAPERTU");
    				String status   = siniestro.get("STATUS");
    				String nmsinies = siniestro.get("NMSINIES");
    				String nfactura = facturaIte.get("NFACTURA");
    				
    				Map<String,String>copagoDeducibleFacturaIte =siniestrosManager.obtenerCopagoDeducible(
    						cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura);
    				
    				//1.- Obtenemos la informaci�n de Autorizaci�n de Factura
    				Map<String,String>autorizacionesFacturaIte = siniestrosManager.obtenerAutorizacionesFactura(
    						siniestro.get("CDUNIECO"),
        					siniestro.get("CDRAMO"),
        					siniestro.get("ESTADO"),
        					siniestro.get("NMPOLIZA"),
        					siniestro.get("NMSUPLEM"),
        					siniestro.get("NMSITUAC"),
        					siniestro.get("AAAPERTU"),
        					siniestro.get("STATUS"),
        					siniestro.get("NMSINIES"),
        					facturaIte.get("NFACTURA"));
					facturaIte.put("AUTMEDIC",autorizacionesFacturaIte.get("AUTMEDIC"));
					facturaIte.put("COMMENME",autorizacionesFacturaIte.get("COMMENME"));
					facturaIte.put("AUTRECLA",autorizacionesFacturaIte.get("AUTRECLA"));
					facturaIte.put("COMMENAR",autorizacionesFacturaIte.get("COMMENAR"));
					
					
    				//2.- Obtenemo los datos generales del siniestros
    		   		List<Map<String,String>> informacionGral = siniestrosManager.obtieneDatosGeneralesSiniestro(siniestro.get("CDUNIECO"), siniestro.get("CDRAMO"),
    		   				siniestro.get("ESTADO"), siniestro.get("NMPOLIZA"),siniestro.get("NMSITUAC"), siniestro.get("NMSUPLEM"), siniestro.get("STATUS"), siniestro.get("AAAPERTU"), siniestro.get("NMSINIES") , facturaIte.get("NTRAMITE"));
    		   		
    		   		//3.- Guardamos los valores en calculosPenalizaciones
    		   		Map<String,String>copagoDeducibleSiniestroIte =siniestrosManager.obtenerCopagoDeducible(siniestro.get("CDUNIECO"), siniestro.get("CDRAMO"),
    		   				siniestro.get("ESTADO"), siniestro.get("NMPOLIZA"), siniestro.get("NMSUPLEM"),siniestro.get("NMSITUAC"),
    		   				siniestro.get("AAAPERTU"),siniestro.get("STATUS"),siniestro.get("NMSINIES") ,facturaIte.get("NFACTURA"));
    		   				
    				String tipoFormatoCalculo = copagoDeducibleSiniestroIte.get("FORMATOCALCULO");
    				String calculosPenalizaciones = copagoDeducibleSiniestroIte.get("PENALIZACIONES");
    				
    		   		calcxCobe.put("tipoFormatoCalculo",""+tipoFormatoCalculo);
					calcxCobe.put("calculosPenalizaciones",""+calculosPenalizaciones);
    				datosCoberturaxCal.add(calcxCobe);
    				
    				penalizacion.put("causaSiniestro", informacionGral.get(0).get("CDCAUSA"));
					//if(facturaIte.get("CDGARANT").equalsIgnoreCase("18HO")||facturaIte.get("CDGARANT").equalsIgnoreCase("18MA"))
    		   		if(tipoFormatoCalculo.equalsIgnoreCase("1"))
					{
    		   			if(calculosPenalizaciones.equalsIgnoreCase("1")){
    		   				HashMap<String, Object> paramExclusion = new HashMap<String, Object>();
            		   		paramExclusion.put("pv_cdunieco_i",siniestro.get("CDUNIECO"));
            		   		paramExclusion.put("pv_estado_i",siniestro.get("ESTADO"));
            		   		paramExclusion.put("pv_cdramo_i",siniestro.get("CDRAMO"));
            		   		paramExclusion.put("pv_nmpoliza_i",siniestro.get("NMPOLIZA"));
            		   		paramExclusion.put("pv_nmsituac_i",siniestro.get("NMSITUAC"));
            		   		
            		   		if(siniestro.get("CDRAMO").toString().equalsIgnoreCase("2")){
            		   			//--> SALUD VITAL
            		   			//1.- Verificamos si existe exclusi�n de penalizaci�n
                		   		existePenalizacion = siniestrosManager.validaExclusionPenalizacion(paramExclusion);
                		   		//2.- Obtenemos la penalizaci�n por cambio de Zona
        	    		   		penalizacionCambioZona = penalizacionCambioZona(existePenalizacion,informacionGral.get(0).get("CDCAUSA"),informacionGral.get(0).get("CIRHOSPI"),
        	    						informacionGral.get(0).get("DSZONAT"),facturaIte.get("CDPRESTA"),siniestro.get("CDRAMO"));
        	    		   		//3.- Obtenemos la penalizaci�n por circulo Hospitalario
        	    		   		List<ConsultaProveedorVO> medicos = siniestrosManager.getConsultaListaProveedorMedico(Rol.MEDICO.getCdrol(),facturaIte.get("CDPRESTA"));
        						penalizacionCirculoHosp = calcularPenalizacionCirculo(informacionGral.get(0).get("CIRHOSPI"), medicos.get(0).getCirculo(),informacionGral.get(0).get("CDCAUSA"));
            		   		}else{
            		   			// --> DIFERENTE DE SALUD VITAL
            		   			penalizacionCambioZona = 0d;
            		   			penalizacionCirculoHosp = 0d;
            		   		}
            		   		
    		   			}
					}
    		   		penalizacion.put("penalizacionCambioZona",""+penalizacionCambioZona);
    				penalizacion.put("penalizacionCirculoHosp",""+penalizacionCirculoHosp);
    				
    				//3.- Obtenemos el total de penalizaci�n
    				String calcularTotalPenalizacion = calcularTotalPenalizacion(penalizacionCambioZona,penalizacionCirculoHosp,informacionGral.get(0).get("CDCAUSA"),
    																			 copagoDeducibleFacturaIte.get("COPAGO"),copagoDeducibleFacturaIte.get("TIPOCOPAGO"),
    																			 informacionGral.get(0).get("CDPROVEE"),siniestro.get("CDRAMO"), informacionGral.get(0).get("FEOCURRE"));

    				penalizacion.put("totalPenalizacionGlobal",""+calcularTotalPenalizacion);
                    String penalizacionT[] = calcularTotalPenalizacion.split("\\|");
                    penalizacion.put("copagoPorcentajes",penalizacionT[0].toString());
                    penalizacion.put("copagoPesos",penalizacionT[1].toString());
                    datosPenalizacion.add(penalizacion);
					
    				String sDeducibleFacturaIte     = copagoDeducibleFacturaIte.get("DEDUCIBLE").replace(",","");
    				String sCopagoFacturaIte        = copagoDeducibleFacturaIte.get("COPAGO").replace(",","");
    				String tipoCopagoFacturaIte     = copagoDeducibleFacturaIte.get("TIPOCOPAGO");
    				
    				//OBTENEMOS LOS VALORES DE PENALIZACION Y COPAGO
    				if(StringUtils.isNotBlank(sDeducibleFacturaIte)
    						&&(!sDeducibleFacturaIte.equalsIgnoreCase("na"))
    						&&(!sDeducibleFacturaIte.equalsIgnoreCase("no"))
    						)
    				{
    					try
    					{
    						deducibleFacturaIte = Double.valueOf(sDeducibleFacturaIte);
    					}
    					catch(Exception ex)
    					{
    						logger.debug(""
    								+ "\n### ERROR ##################################################"
    								+ "\n### no es numero deducible: '"+sDeducibleFacturaIte+"' ###"
    								+ "\n############################################################"
    								);
    						deducibleFacturaIte = 0d;
    					}
    				}
    				if(StringUtils.isNotBlank(sCopagoFacturaIte)
    						&&(!sCopagoFacturaIte.equalsIgnoreCase("na"))
    						&&(!sCopagoFacturaIte.equalsIgnoreCase("no"))
    						)
    				{
    					try
    					{
    						cantidadCopagoFacturaIte = Double.valueOf(sCopagoFacturaIte);
    					}
    					catch(Exception ex)
    					{
    						logger.debug(""
    								+ "\n### ERROR ############################################"
    								+ "\n### no es numero copago: '"+sCopagoFacturaIte+"' ###"
    								+ "\n######################################################"
    								);
    						cantidadCopagoFacturaIte = 0d;
    					}
    				}
    				
    				slist2.add(copagoDeducibleFacturaIte);
    				
    				Map<String,String>datosProveedor =siniestrosManager.obtenerDatosProveedor(facturaIte.get("CDPRESTA")); 
    				slist3.add(datosProveedor);
    				double ivaprov=Double.parseDouble(datosProveedor.get("IVA"));
    				
    				List<Map<String,String>>listaConceptosFactura = new ArrayList<Map<String,String>>();
    				llist1.add(listaConceptosFactura);
    				for(Map<String,String>concepto : conceptos)
    				{
    					if(concepto.get("NFACTURA").equals(facturaIte.get("NFACTURA")))
    					{
    						listaConceptosFactura.add(concepto);
    							logger.debug(">>REEMBOLSO");
    							Map<String,String>row=new HashMap<String,String>();
    							row.putAll(concepto);
    							
    							double ptimport = Double.parseDouble(row.get("PTIMPORT"));
    							logger.debug("ptimport "+ptimport);
    							
    							double ajusteaplica = 0d;
    							if(StringUtils.isNotBlank(row.get("PTIMPORT_AJUSTADO")))
    							{
    								ajusteaplica = Double.parseDouble(row.get("PTIMPORT_AJUSTADO"));
    							}
    							logger.debug("ajusteaplica "+ajusteaplica);
    							
    							double subtotal = ptimport-ajusteaplica;
    							logger.debug("subtotal "+subtotal);
    							row.put("SUBTOTAL",subtotal+"");
    							
    							double gtotalneto = Double.parseDouble(mprem.get("TOTALNETO"));
    							double gsubtotal  = Double.parseDouble(mprem.get("SUBTOTAL"));
    							logger.debug("base totalneto " + gtotalneto);
    							logger.debug("base subtotal "  + gsubtotal);
    							gtotalneto += ptimport;
    							gsubtotal  += subtotal;
    							logger.debug("new totalneto " + gtotalneto);
    							logger.debug("new subtotal "  + gsubtotal);
    							
    							mprem.put("TOTALNETO" , gtotalneto + "");
    							mprem.put("SUBTOTAL"  , gsubtotal  + "");
    							
    							concepto.putAll(row);
    							logger.debug("<<REEMBOLSO");
    						//pago reembolso
    					}
    				}
    				
    				//Verificamos la informaci�n del deducible
    				//if(facturaIte.get("CDGARANT").equalsIgnoreCase("18HO")||facturaIte.get("CDGARANT").equalsIgnoreCase("18MA"))
    				if(tipoFormatoCalculo.equalsIgnoreCase("1")){
						//verificamos la causa del siniestro
						String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
						if(causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
							deducibleFacturaIte = 0d;
						}
					}
    				
    				logger.debug(">>Calculando total factura iterada para WS");
    				logger.debug("deducible :"+deducibleFacturaIte);
    				logger.debug("scopago: "+sCopagoFacturaIte);
    				logger.debug("tipocopago: "+tipoCopagoFacturaIte);
    				double totalFactura  = Double.valueOf(mprem.get("SUBTOTAL"));
    				double destoPorFac   = Double.valueOf(facturaIte.get("DESCPORC"));
    				double destoImpFac   = Double.valueOf(facturaIte.get("DESCNUME"));
    				double destoAplicado = (totalFactura*(destoPorFac/100d)) + destoImpFac;
    				logger.debug("subtotal: "+totalFactura);
    				totalFactura -= destoAplicado;
    				logger.debug("subtotal desto: "+totalFactura);
    				totalFactura -= deducibleFacturaIte;
    				logger.debug("subtotal deducible: "+totalFactura);
    				
    				if(StringUtils.isNotBlank(tipoCopagoFacturaIte))
					{
    					String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
    					//if(facturaIte.get("CDGARANT").equalsIgnoreCase("18HO")||facturaIte.get("CDGARANT").equalsIgnoreCase("18MA"))
    					if(tipoFormatoCalculo.equalsIgnoreCase("1"))
    					{
    						//verificamos la causa del siniestro
    						if(!causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
    							//Diferente de accidente
    							copagoAplicadoFacturaIte = Double.parseDouble(penalizacionT[1].toString()) + (totalFactura * ( Double.parseDouble(penalizacionT[0].toString()) / 100d ));
    						}else{
    							copagoAplicadoFacturaIte = 0d;
    						}
    					}else{
    						//COBERTURA DIFERENTE HOSPITALIZACI�N Y AYUDA DE MATERNIDAD
    							if(tipoCopagoFacturaIte.equalsIgnoreCase("$")){
        							copagoAplicadoFacturaIte = cantidadCopagoFacturaIte;
        						}
        						if(tipoCopagoFacturaIte.equalsIgnoreCase("%"))
        						{
        							copagoAplicadoFacturaIte = totalFactura * ( cantidadCopagoFacturaIte / 100d );
        						}
    					}
					}
    				totalFactura -= copagoAplicadoFacturaIte;
    				logger.debug("total copago (final): "+totalFactura);
    				logger.debug("<<Calculando total factura iterada para WS");
    				
    				importeSiniestroUnico += totalFactura;
    			}
    			
    			logger.debug(">>WS del siniestro unico");
				importesWSSiniestroUnico.put(IMPORTE_WS_IMPORTE , (new Double(importeSiniestroUnico)).toString());
				importesWSSiniestroUnico.put(IMPORTE_WS_IVA     , (new Double(ivaSiniestroUnico)    ).toString());
				importesWSSiniestroUnico.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroUnico)    ).toString());
				importesWSSiniestroUnico.put(IMPORTE_WS_ISR     , (new Double(isrSiniestroUnico)    ).toString());
				importesWSSiniestroUnico.put(IMPORTE_WS_CEDULAR , (new Double(cedularSiniestroUnico)).toString());
				logger.debug("mapa WS siniestro unico: "+importesWSSiniestroUnico);
				logger.debug("<<WS del siniestro unico");
				
    		}
    		
    		if(conceptos!=null&&conceptos.size()>0)
    		{
    			logger.debug("conceptos[0]: "+conceptos);
    		}
    		
    		seccion     = "FORM_TRAMITE";
    		componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
    		gc.generaComponentes(componentes, true, true, true, false, false, false);
    		imap.put("tramiteFields" , gc.getFields());
    		imap.put("tramiteItems"  , gc.getItems());
    		
    		seccion     = "FACTURA";
    		componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
    		gc.generaComponentes(componentes, true, true, true, true, false, false);
    		imap.put("facturaFields"  , gc.getFields());
    		imap.put("facturaItems"   , gc.getItems());
    		imap.put("facturaColumns" , gc.getColumns());
    		
    		seccion     = "SINIESTRO";
    		componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
    		gc.generaComponentes(componentes, true, true, true, true, false, false);
    		imap.put("siniestroFields"  , gc.getFields());
    		imap.put("siniestroItems"   , gc.getItems());
    		imap.put("siniestroColumns" , gc.getColumns());
    		
    		seccion     = "PROVEEDOR";
    		componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
    		gc.generaComponentes(componentes, true, true, true, false, false, false);
    		imap.put("proveedorFields" , gc.getFields());
    		imap.put("proveedorItems"  , gc.getItems());
    		
    		seccion     = "CONCEPTO";
    		componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
    		gc.generaComponentes(componentes, true, true, false, true, false, false);
    		imap.put("conceptoFields"  , gc.getFields());
    		imap.put("conceptoColumns" , gc.getColumns());
    	
    		seccion     = "FORM_AUTORIZA";
    		componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
    		gc.generaComponentes(componentes, true, false, true, false, false, false);
    		imap.put("autorizaItems",gc.getItems());
    		
    		pantalla    = "RECHAZO_SINIESTRO";
    		seccion     = "FORMULARIO";
    		componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
    		gc.generaComponentes(componentes, true, false, true, false, false, false);
    		imap.put("rechazoitems",gc.getItems());
    	}
    	catch(Exception ex)
    	{
    		logger.debug("error al cargar pantalla de calculo de siniestros",ex);
    	}
    	logger.debug(""
    			+ "\n######                   		  ######"
    			+ "\n###### consultaCalculoSiniestros ######"
    			+ "\n#######################################"
    			+ "\n#######################################"
    			);
    	return SUCCESS;
    }
    
    
    public String obtenerFacturasTramite()
    {
    	logger.debug(""
    			+ "\n####################################"
    			+ "\n####################################"
    			+ "\n###### obtenerFacturasTramite ######"
    			+ "\n######                        ######"
    			);
    	logger.debug("smap:"+smap);
    	try
    	{
    		slist1=siniestrosManager.obtenerFacturasTramite(smap.get("ntramite"));
    		mensaje="Facturas obtenidas";
    		success=true;
    	}
    	catch(Exception ex)
    	{
    		success=false;
    		logger.error("error al obtener facturas de tramite",ex);
    		mensaje=ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                        ######"
    			+ "\n###### obtenerFacturasTramite ######"
    			+ "\n####################################"
    			+ "\n####################################"
    			);
    	return SUCCESS;
    }
    
    public String validarFacturaAsegurado()
    {
    	logger.debug(""
    			+ "\n######################################"
    			+ "\n######################################"
    			+ "\n###### valida Factura-Asegurado ######"
    			+ "\n######                          ######"
    			);
    	logger.debug("smap:"+smap);
    	try
    	{
    		String tipoPagoTramite = smap.get("tipoPago");
    		String faltaAsegurados="";
    		String validacionAseg=null;
    		boolean faltaFacturas=true;
    		
    		
    		boolean esReembolso = tipoPagoTramite.equalsIgnoreCase(TipoPago.REEMBOLSO.getCodigo()) ||tipoPagoTramite.equalsIgnoreCase(TipoPago.INDEMNIZACION.getCodigo());
			if(esReembolso)
			{
				validacionAseg= "0";
			}else{
				List<Map<String,String>> facturas=siniestrosManager.obtenerFacturasTramite(smap.get("ntramite"));
	    		for(Map<String,String>factura:facturas)
	    		{
	    			List<Map<String,String>> asegurados =siniestrosManager.obtenerAseguradosTramite(factura.get("NTRAMITE"), factura.get("NFACTURA"));
	    			if(asegurados.size() <= 0){
	    				faltaFacturas= false;
	    				faltaAsegurados = faltaAsegurados +" "+factura.get("NFACTURA");
	    			}
	    		}
	    		if(faltaFacturas){
	    			validacionAseg= "0";
	    		}else{
	    			validacionAseg="1";
	    		}
			}
			
			loadList = new ArrayList<HashMap<String,String>>();
	   		HashMap<String,String>map=new HashMap<String,String>();
	   			map.put("faltaAsegurados"   ,validacionAseg );
	   			map.put("facturasFaltantes" , faltaAsegurados);
	   		loadList.add(map);
			success=true;
    	}
    	catch(Exception ex)
    	{
    		success=false;
    		logger.error("error al obtener facturas de tramite",ex);
    		mensaje=ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                          ######"
    			+ "\n###### valida Factura-Asegurado ######"
    			+ "\n######################################"
    			+ "\n######################################"
    			);
    	return SUCCESS;
    }
    
    
    public String obtenerAseguradosTramite()
    {
    	logger.debug(""
    			+ "\n######################################"
    			+ "\n######################################"
    			+ "\n###### obtenerAseguradosTramite ######"
    			+ "\n######                          ######"
    			);
    	logger.debug(params);
    	try
    	{
    		slist1=siniestrosManager.obtenerAseguradosTramite(params.get("ntramite"), params.get("nfactura"));
    		mensaje="Asegurados obtenidos";
    		success=true;
    	}
    	catch(Exception ex)
    	{
    		success=false;
    		logger.error("error al obtener facturas de tramite",ex);
    		mensaje=ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                          ######"
    			+ "\n###### obtenerAseguradosTramite ######"
    			+ "\n######################################"
    			+ "\n######################################"
    			);
    	return SUCCESS;
    }
    
    public String obtenerSiniestrosTramite()
    {
    	logger.debug(""
    			+ "\n######################################"
    			+ "\n######################################"
    			+ "\n###### obtenerSiniestrosTramite ######"
    			+ "\n######                          ######"
    			);
    	logger.debug("smap: "+smap);
    	try
    	{
    		//slist1 = siniestrosManager.listaSiniestrosTramite(smap.get("ntramite"),null);
    		slist1 = siniestrosManager.listaSiniestrosTramite2(smap.get("ntramite"),smap.get("nfactura"),null);
    		success=true;
    		mensaje="Siniestros obtenidos";
    	}
    	catch(Exception ex)
    	{
    		logger.debug("error al obtener siniestros de tramite",ex);
    		success=false;
    		mensaje=ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                          ######"
    			+ "\n###### obtenerSiniestrosTramite ######"
    			+ "\n######################################"
    			+ "\n######################################"
    			);
    	return SUCCESS;
    }
    
    public String obtenerDatosProveedor()
    {
    	logger.debug(""
    			+ "\n###################################"
    			+ "\n###################################"
    			+ "\n###### obtenerDatosProveedor ######"
    			+ "\n######                       ######"
    			);
    	logger.debug("smap: "+smap);
    	try
    	{
    		smap.put("CDPRESTA" , "69");
    		smap.put("NOMBRE"   , "PROVEEDOR");
    		smap.put("ISR"      , "12.5");
    		smap.put("CEDULAR"  , "1.0");
    		smap.put("IVA"      , "16");
    		success=true;
    		mensaje="Datos obtenidos";
    	}
    	catch(Exception ex)
    	{
    		logger.debug("error al obtener datos del proveedor",ex);
    		success=false;
    		mensaje=ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                       ######"
    			+ "\n###### obtenerDatosProveedor ######"
    			+ "\n###################################"
    			+ "\n###################################"
    			);
    	return SUCCESS;
    }
    
    public String obtenerConceptosCalculo()
    {
    	logger.debug(""
    			+ "\n#####################################"
    			+ "\n#####################################"
    			+ "\n###### obtenerConceptosCalculo ######"
    			+ "\n######                         ######"
    			);
    	logger.debug("smap: "+smap);
    	try
    	{
    		slist1=siniestrosManager.P_GET_CONCEPTOS_FACTURA(null,null,null,null,null,null,null,null,null,smap.get("nfactura"));
    				
    	    /*new ArrayList<Map<String,String>>();
    		
    		Map<String,String>c1=new LinkedHashMap<String,String>();
    		c1.put("CDUNIECO"  , "1006");
    		c1.put("CDRAMO"    , "2");
    		c1.put("ESTADO"    , "W");
    		c1.put("NMPOLIZA"  , "6969");
    		c1.put("NMSUPLEM"  , "123456789012345678");
    		c1.put("NMSITUAC"  , "1");
    		c1.put("AAAPERTU"  , "2014");
    		c1.put("STATUS"    ,  "W");
    		c1.put("NMSINIES"  , "69");
    		c1.put("NFACTURA"  , "1069");
    		c1.put("IDCONCEP"  , "1");
    		c1.put("DSIDCONC"  , "CPT");
    		c1.put("CDCONCEP"  , "72000");
    		c1.put("DSCONCEP"  , "EXTRACCION MUELA");
    		c1.put("CANTIDAD"  , "3");
    		c1.put("IMPARANC"  , "150");
    		c1.put("DESTOPOR"  , "10");
    		c1.put("DESTOIMP"  , "5");
    		c1.put("COPAGO"    , "100");
    		c1.put("DEDUCIBLE" , "0");
    		c1.put("IVACONCEP" , "0");
    		c1.put("PTIMPORT"  , "450");
    		c1.put("AUTORIZA"  , "");
    		c1.put("IMPAJUSTE" , "-35");
    		slist1.add(c1);
    		
    		Map<String,String>c2=new LinkedHashMap<String,String>();
    		c2.put("CDUNIECO"  , "1006");
    		c2.put("CDRAMO"    , "2");
    		c2.put("ESTADO"    , "W");
    		c2.put("NMPOLIZA"  , "6969");
    		c2.put("NMSUPLEM"  , "123456789012345678");
    		c2.put("NMSITUAC"  , "1");
    		c2.put("AAAPERTU"  , "2014");
    		c2.put("STATUS"    ,  "W");
    		c2.put("NMSINIES"  , "69");
    		c2.put("NFACTURA"  , "1069");
    		c2.put("IDCONCEP"  , "1");
    		c2.put("DSIDCONC"  , "CPT");
    		c2.put("CDCONCEP"  , "500");
    		c2.put("DSCONCEP"  , "AMALGAMA");
    		c2.put("CANTIDAD"  , "2");
    		c2.put("IMPARANC"  , "95");
    		c2.put("DESTOPOR"  , "5");
    		c2.put("DESTOIMP"  , "0");
    		c2.put("COPAGO"    , "10");
    		c2.put("DEDUCIBLE" , "0");
    		c2.put("IVACONCEP" , "15");
    		c2.put("PTIMPORT"  , "200");
    		c2.put("AUTORIZA"  , "");
    		c2.put("IMPAJUSTE" , "0");
    		slist1.add(c2);
    		*/
    		
    		success=true;
    		mensaje="Datos obtenidos";
    	}
    	catch(Exception ex)
    	{
    		logger.error("error al obtener los conceptos de calculo",ex);
    		success=false;
    		mensaje=ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                         ######"
    			+ "\n###### obtenerConceptosCalculo ######"
    			+ "\n#####################################"
    			+ "\n#####################################"
    			);
    	return SUCCESS;
    }
    
    public String autorizaConcepto()
    {
    	this.session=ActionContext.getContext().getSession();
    	logger.debug(""
    			+ "\n##############################"
    			+ "\n##############################"
    			+ "\n###### autorizaConcepto ######"
    			+ "\n######                  ######"
    			);
    	logger.debug("params: "+params);
    	try
    	{
    		String cdunieco = params.get("CDUNIECO");
    		String cdramo   = params.get("CDRAMO");
    		String estado   = params.get("ESTADO");
    		String nmpoliza = params.get("NMPOLIZA");
    		String nmsuplem = params.get("NMSUPLEM");
    		String nmsituac = params.get("NMSITUAC");
    		String aaapertu = params.get("AAAPERTU");
    		String status   = params.get("STATUS");
    		String nmsinies = params.get("NMSINIES");
    		String nfactura = params.get("NFACTURA");
    		String cdgarant = params.get("CDGARANT");
    		String cdconval = params.get("CDCONVAL");
    		String cdconcep = params.get("CDCONCEP");
    		String idconcep = params.get("IDCONCEP");
    		String nmordina = params.get("NMORDINA");
    		String autmedic = params.get("AUTMEDIC");
    		String commenme = params.get("COMMENME");
    		String autrecla = params.get("AUTRECLA");
    		String commenre = params.get("COMMENRE");
    		
    		boolean cancela     = StringUtils.isNotBlank(params.get("cancela"));
    		String  cdmotivo    = params.get("cdmotivo");
    		String  rechazoCome = params.get("commenrechazo");
    		UserVO  usuario     = (UserVO)session.get("USUARIO");
    		String  cdrol       = usuario.getRolActivo().getClave();
    		
    		if(StringUtils.isNotBlank(autmedic))
    		{
	    		siniestrosManager.P_MOV_MAUTSINI(
	    				cdunieco, cdramo,   estado,   nmpoliza, nmsuplem,
	    				nmsituac, aaapertu, status,   nmsinies, nfactura,
	    				cdgarant, cdconval, cdconcep, idconcep, nmordina,
	    				Constantes.MAUTSINI_AREA_MEDICA,
	    				autmedic,
	    				Constantes.MAUTSINI_DETALLE,
	    				commenme,
	    				Constantes.INSERT_MODE);
    		}
    		
    		if(StringUtils.isNotBlank(autrecla))
    		{
    			siniestrosManager.P_MOV_MAUTSINI(
	    				cdunieco, cdramo,   estado,   nmpoliza, nmsuplem,
	    				nmsituac, aaapertu, status,   nmsinies, nfactura,
	    				cdgarant, cdconval, cdconcep, idconcep, nmordina,
	    				Constantes.MAUTSINI_AREA_RECLAMACIONES,
	    				autrecla,
	    				Constantes.MAUTSINI_DETALLE,
	    				commenre,
	    				Constantes.INSERT_MODE);
    		}
    		
    		if(cancela)
    		{
    			String ntramite = params.get("ntramite");
    			Map<String,String> tramiteCompleto = siniestrosManager.obtenerTramiteCompleto(ntramite);
    			String tipoPago = tramiteCompleto.get("OTVALOR02");
    			Boolean rolMedico = null;
    			if(cdrol.equalsIgnoreCase(RolSistema.COORDINADOR_MEDICO.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.COORDINADOR_MEDICO_MULTIREGIONAL.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.GERENTE_MEDICO_MULTIREGIONAL.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.MEDICO.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.MEDICO_AJUSTADOR.getCdsisrol())
    					)
    			{
    				rolMedico = Boolean.TRUE;
    			}
    			else if(cdrol.equalsIgnoreCase(RolSistema.COORDINADOR_SINIESTROS.getCdsisrol())
    					||cdrol.equalsIgnoreCase(RolSistema.OPERADOR_SINIESTROS.getCdsisrol())
    					)
    			{
    				rolMedico = Boolean.FALSE;
    			}
    			
    			if(rolMedico==null)
    			{
    				throw new Exception("El usuario actual no puede cancelar");
    			}
    			
    			MesaControlAction mca = new MesaControlAction();
    			mca.setKernelManager(kernelManagerSustituto);
    			mca.setSession(session);
    			Map<String,String>smap1=new HashMap<String,String>();
    			smap1.put("ntramite" , ntramite);
    			smap1.put("status"   , EstatusTramite.RECHAZADO.getCodigo());
    			smap1.put("cdmotivo" , cdmotivo);
    			smap1.put("comments" , rechazoCome);
    			mca.setSmap1(smap1);
    			mca.actualizarStatusTramite();
    			if(!mca.isSuccess())
    			{
    				throw new Exception("Error al cancelar el tr�mite");
    			}
    			
    			String nombreReporte = null;
    			String nombreArchivo = null;
    			if(rolMedico)
    			{
    				nombreReporte = getText("rdf.siniestro.cartarechazo.medico.nombre");
    				nombreArchivo = getText("pdf.siniestro.rechazo.medico.nombre");
    			}
    			else//cancelacion por area de reclamaciones
    			{
    				boolean esReembolso = tipoPago.equalsIgnoreCase(TipoPago.REEMBOLSO.getCodigo());
    				if(esReembolso)
    				{
    					nombreReporte = getText("rdf.siniestro.cartarechazo.reembolso.nombre");
        				nombreArchivo = getText("pdf.siniestro.rechazo.reemb.nombre");
    				}
    				else
    				{
    					nombreReporte = getText("rdf.siniestro.cartarechazo.pagodirecto.nombre");
        				nombreArchivo = getText("pdf.siniestro.rechazo.pdir.nombre");
    				}
    			}
    			
    			File carpeta=new File(getText("ruta.documentos.poliza") + "/" + ntramite);
    			if(!carpeta.exists())
    			{
    				logger.debug("no existe la carpeta::: "+ntramite);
    				carpeta.mkdir();
    				if(carpeta.exists())
    				{
    					logger.debug("carpeta creada");
    				}
    				else
    				{
    					logger.debug("carpeta NO creada");
    				}
    			}
    			else
    			{
    				logger.debug("existe la carpeta   ::: "+ntramite);
    			}
    			
    			String urlContrareciboSiniestro = ""
    					+ getText("ruta.servidor.reports")
    					+ "?p_usuario="  + usuario.getUser()
    					+ "&P_NTRAMITE=" + ntramite
    					+ "&userid="     + getText("pass.servidor.reports")
    					+ "&report="     + nombreReporte
    					+ "&destype=cache"
    					+ "&desformat=PDF"
    					+ "&ACCESSIBLE=YES"
    					+ "&paramform=no";
    			String pathArchivo=""
    					+ getText("ruta.documentos.poliza")
    					+ "/" + ntramite
    					+ "/" + nombreArchivo;
    			
    			HttpUtil.generaArchivo(urlContrareciboSiniestro, pathArchivo);
    	        
    			Map<String,Object>paramsDocupol = new HashMap<String,Object>();
    			paramsDocupol.put("pv_cdunieco_i"  , cdunieco);
    			paramsDocupol.put("pv_cdramo_i"    , cdramo);
    			paramsDocupol.put("pv_estado_i"    , estado);
    			paramsDocupol.put("pv_nmpoliza_i"  , nmpoliza);
    			paramsDocupol.put("pv_nmsuplem_i"  , nmsuplem);
    			paramsDocupol.put("pv_feinici_i"   , new Date());
    			paramsDocupol.put("pv_cddocume_i"  , nombreArchivo);
    			paramsDocupol.put("pv_dsdocume_i"  , "Carta rechazo");
    			paramsDocupol.put("pv_ntramite_i"  , ntramite);
    			paramsDocupol.put("pv_nmsolici_i"  , null);
    			paramsDocupol.put("pv_tipmov_i"    , tipoPago);
    			paramsDocupol.put("pv_swvisible_i" , Constantes.SI);
    			paramsDocupol.put("pv_codidocu_i"  , null);
    			paramsDocupol.put("pv_cdtiptra_i"  , TipoTramite.SINIESTRO.getCdtiptra());
    	        kernelManagerSustituto.guardarArchivo(paramsDocupol);
    		}
    		
    		success = true;
    		mensaje = "Datos guardados";
    	}
    	catch(Exception ex)
    	{
    		logger.error("error al autorizar concepto",ex);
    		success = false;
    		mensaje = ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                  ######"
    			+ "\n###### autorizaConcepto ######"
    			+ "\n##############################"
    			+ "\n##############################"
    			);
    	return SUCCESS;
    }
    
    
    public String ProcesoAltaTramite(String msgResult) throws Exception
    {
        // si tipo de pago es Directo
        if(params.get("cmbTipoPago").trim().equalsIgnoreCase(TipoPago.DIRECTO.getCodigo()))
        {
        	for(int i=0;i<datosTablas.size();i++)
            {
        		siniestrosManager.guardaListaFacMesaControl(
                    msgResult, 
                    datosTablas.get(i).get("nfactura"),
                    datosTablas.get(i).get("ffactura").substring(8,10)+"/"+datosTablas.get(i).get("ffactura").substring(5,7)+"/"+datosTablas.get(i).get("ffactura").substring(0,4),//getDate(datosTablas.get(i).get("ffactura"))+"",
                    datosTablas.get(i).get("cdtipser"),
                    datosTablas.get(i).get("cdpresta"),
                    datosTablas.get(i).get("ptimport"),
                    null,
                    null,
                    null,
                    null,
                    datosTablas.get(i).get("cdmoneda"),
                    datosTablas.get(i).get("tasacamb"),
                    datosTablas.get(i).get("ptimporta"),
                    null,
                    null
                );
            }
        }else{
        	/*Se agrega la informaci�n de las facturas*/
        	if(params.get("idNumTramite").toString().length() > 0){
        		try {
        			// Se realiza la eliminacion de las facturas
					siniestrosManager.getEliminacionTFacMesaControl(msgResult);
				} catch (Exception e) {
					logger.error("error al eliminar en TfacMesCtrl ",e);
				}
        	}
        	
        	for(int i=0;i<datosTablas.size();i++)
            {
        		String nfactura= "0";
        		if(params.get("cmbTipoPago").trim().equalsIgnoreCase(TipoPago.REEMBOLSO.getCodigo())){
            		nfactura = datosTablas.get(i).get("nfactura");
        		}else{
        			nfactura= msgResult;
        		}
        		siniestrosManager.guardaListaFacMesaControl(
                    msgResult, 
                    nfactura,
                    datosTablas.get(i).get("ffactura").substring(8,10)+"/"+datosTablas.get(i).get("ffactura").substring(5,7)+"/"+datosTablas.get(i).get("ffactura").substring(0,4),
                    datosTablas.get(i).get("cdtipser"),
                    datosTablas.get(i).get("cdpresta"),
                    datosTablas.get(i).get("ptimport"),
                    null,
                    null,
                    null,
                    null,
                    datosTablas.get(i).get("cdmoneda"),
                    datosTablas.get(i).get("tasacamb"),
                    datosTablas.get(i).get("ptimporta"),
                    null,
                    null
                );
            }
            
            if(params.get("idNumTramite").toString().length() > 0){
        	    // SE REALIZA LA ELIMINACION EN TWORKSIN
            	try {
        			// numero de tramite, 
					siniestrosManager.getEliminacionTworksin(msgResult);
				} catch (Exception e) {
					logger.error("error al eliminar en TworkSin ",e);
				}
        	}
            
            HashMap<String, Object> paramsTworkSinPagRem = new HashMap<String, Object>();
            paramsTworkSinPagRem.put("pv_nmtramite_i",msgResult);
            paramsTworkSinPagRem.put("pv_cdunieco_i",params.get("cdunieco"));
            paramsTworkSinPagRem.put("pv_cdramo_i",params.get("cmbRamos"));
            paramsTworkSinPagRem.put("pv_estado_i",params.get("estado"));
            paramsTworkSinPagRem.put("pv_nmpoliza_i",params.get("polizaAfectada"));
            paramsTworkSinPagRem.put("pv_nmsolici_i",params.get("idNmsolici"));
            paramsTworkSinPagRem.put("pv_nmsuplem_i",params.get("idNmsuplem"));
            paramsTworkSinPagRem.put("pv_nmsituac_i",params.get("nmsituac"));
            paramsTworkSinPagRem.put("pv_cdtipsit_i",params.get("idCdtipsit"));
            paramsTworkSinPagRem.put("pv_cdperson_i",params.get("cmbAseguradoAfectado"));
            paramsTworkSinPagRem.put("pv_feocurre_i",params.get("dtFechaOcurrencia"));
            paramsTworkSinPagRem.put("pv_nfactura_i",null);
            paramsTworkSinPagRem.put("pv_nmautser_i",null);
            siniestrosManager.guardaListaTworkSin(paramsTworkSinPagRem);
        }
        success = true;
    	return SUCCESS;
    }
    
    public String guardarCalculos()
    {
    	this.session=ActionContext.getContext().getSession();
    	logger.info(""
    			+ "\n#############################"
    			+ "\n###### guardarCalculos ######"
    			);
    	logger.info("slist1: "+slist1);
    	logger.info("slist2: "+slist2);
    	try
    	{
    		for(Map<String,String> importe : slist1)
    		{
    			/*  aaapertu : "2014"
					cdramo   : "2"
					cdunieco : "1006"
					estado   : "M"
					importe  : "1546.66"
					isr      : "0.0"
					iva      : "0.0"
					ivr      : "0.0"
					cedular  : "0.0"
					nmpoliza : "44"
					nmsinies : "20"
					nmsituac : "1"
					nmsuplem : "245671518430000000"
					ntramite : "1445"
					status   : "W"
    			 */
    			String cduniecoIte = importe.get("cdunieco");
    			String cdramoIte   = importe.get("cdramo");
    			String estadoIte   = importe.get("estado");
    			String nmpolizaIte = importe.get("nmpoliza");
    			String nmsuplemIte = importe.get("nmsuplem");
    			String nmsituacIte = importe.get("nmsituac");
    			String aaapertuIte = importe.get("aaapertu");
    			String statusIte   = importe.get("status");
    			String nmsiniesIte = importe.get("nmsinies");
    			String ntramiteIte = importe.get("ntramite");
    			String importeIte  = importe.get("importe");
    			String ivaIte      = importe.get("iva");
    			String ivrIte      = importe.get("ivr");
    			String isrIte      = importe.get("isr");
    			String cedularIte  = importe.get("cedular");
    			siniestrosManager.movTimpsini(
    					Constantes.INSERT_MODE
    					,cduniecoIte
    					,cdramoIte
    					,estadoIte
    					,nmpolizaIte
    					,nmsuplemIte
    					,nmsituacIte
    					,aaapertuIte
    					,statusIte
    					,nmsiniesIte
    					,ntramiteIte
    					,importeIte
    					,ivaIte
    					,ivrIte
    					,isrIte
    					,cedularIte
    					,false);
    		}
    		for(Map<String,String> totalFacturaIte : slist2)
    		{
    			String ntramite     = totalFacturaIte.get("NTRAMITE");
    			String nfactura     = totalFacturaIte.get("NFACTURA");
    			String totalFactura = totalFacturaIte.get("TOTALFACTURA"); 
    			siniestrosManager.guardarTotalProcedenteFactura(ntramite,nfactura,totalFactura);
    		}
    		success = true;
    		mensaje = "Datos guardados";
    	}
    	catch(Exception ex)
    	{
    		logger.error("Error al guardaar calculos",ex);
    		success = false;
    		mensaje = ex.getMessage();
    	}
    	logger.info(""
    			+ "\n###### guardarCalculos ######"
    			+ "\n#############################"
    			);
    	return SUCCESS;
    }
    
    public String validaDocumentosAutoServ(){
	   	logger.debug(" **** Entrando al metodo de validacion de documentos de Autorizacion de servicio ****");
	   	try {
		   		existeDocAutServicio = siniestrosManager.validaDocumentosAutServicio(params.get("ntramite"));
	   	}catch( Exception e){
	   		logger.error("Error al consultar la validacion de documentos de Autorizacion de servicio ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
  }
    
    public String obtenerUsuariosPorRol()
    {
    	logger.info(""
    			+ "\n###################################"
    			+ "\n###### obtenerUsuariosPorRol ######"
    			);
    	logger.info("smap: "+smap);
    	try
    	{
    		String cdsisrol = smap.get("cdsisrol");
    		slist1  = siniestrosManager.obtenerUsuariosPorRol(cdsisrol);
    		success = true;
    	}
    	catch(Exception ex)
		{
    		logger.error("error al obtener usuarios por rol",ex);
    		slist1  = new ArrayList<Map<String,String>>();
    		success = false;
		}
    	logger.info(""
    			+ "\n###### obtenerUsuariosPorRol ######"
    			+ "\n###################################"
    			);
    	return SUCCESS;
    }
    
    public String obtieneMesesTiempoEspera(){
	   	logger.debug(" **** Entrando al metodo de obtencion de Tiempo de Espera ****");
	   	try {
	   			mesesTiempoEspera = siniestrosManager.obtieneMesesTiempoEspera(params.get("otvalor"),params.get("cdtabla"));
	   	}catch( Exception e){
	   		logger.error("Error al consultar el periodo de espera en meses",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
 }
    /**
     * Funci�n que obtiene la lista del asegurado
     * @param void sin parametros de entrada
     * @return Lista GenericVO con la informaci�n de los asegurados
     */    
    public String consultaRamosSalud(){
    	logger.debug(" **** Entrando al metodo para la consulta de Ramos para salud ****");
 	   	try {
 	   		listadoRamosSalud = siniestrosManager.getConsultaListaRamoSalud();
 	   	}catch( Exception e){
 	   		logger.error("Error al consultar los ramos para Salud ",e);
 	   		return SUCCESS;
 	   	}
 	   	success = true;
 	   	return SUCCESS;
    }
    
    
	public String guardarCartaRechazoAutServ()
	{
		logger.info(""
				+ "\n#################################"
				+ "\n###### guardarCartaRechazo ######"
				);
		logger.info("map1: "+map1);
		String ntramite    = map1.get("ntramite");
		String comments    = map1.get("comments");
		logger.debug(comments);
		String commentsM   = comments.replaceAll("\n", "%0A").
				replaceAll("�", "%C3%A1").
				replaceAll("�", "%C3%A9").
				replaceAll("�", "%C3%AD").
				replaceAll("�", "%C3%B3").
				replaceAll("�", "%C3%BA").
				replaceAll("�", "%C3%B1").
				replaceAll("�", "%C3%81").
				replaceAll("�", "%C3%89").
				replaceAll("�", "%C3%8D").
				replaceAll("�", "%C3%93").
				replaceAll("�", "%C3%9A").
				replaceAll("�", "%C3%91");
		String cdsisrol    = map1.get("cdsisrol");
		String cdunieco    = map1.get("cdunieco");
		String cdramo      = map1.get("cdramo");
		String estado      = map1.get("estado");
		String nmpoliza    = map1.get("nmpoliza");
		String rutaCarpeta = this.getText("ruta.documentos.poliza")+"/"+ntramite;
		
		File carpeta=new File(this.getText("ruta.documentos.poliza")+"/"+ntramite);
        if(!carpeta.exists()){
        		logger.debug("no existe la carpeta::: "+ntramite);
        		carpeta.mkdir();
        		if(carpeta.exists()){
        			logger.debug("carpeta creada");
        		} else {
        			logger.debug("carpeta NO creada");
        		}
        } else {
        	 logger.debug("existe la carpeta   ::: "+ntramite);
        }
        
		String url         = this.getText("ruta.servidor.reports")
				+ "?destype=cache"
				+ "&desformat=PDF"
				+ "&userid="+this.getText("pass.servidor.reports")
				+ "&report="+(cdsisrol.equalsIgnoreCase(RolSistema.MEDICO.getCdsisrol())?
						this.getText("rdf.emision.rechazo.medico.nombre"):
							this.getText("rdf.emision.rechazo.admin.nombre"))
				+ "&paramform=no"
				+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
				+ "&p_ntramite="+ntramite
				+ "&p_comments="+commentsM;
		logger.debug(""
				+ "\n#################################"
				+ "\n###### Se solicita reporte ######"
				+ "\n###### "+url
				);
		HttpUtil.generaArchivo(url,rutaCarpeta+"/"+this.getText("pdf.emision.rechazo.nombre"));
		logger.debug(""
				+ "\n###### Se solicita reporte ######"
				+ "\n#################################"
				);
		try
		{
			HashMap<String, Object> paramsR = new HashMap<String, Object>();
			paramsR.put("pv_cdunieco_i"  , cdunieco);
			paramsR.put("pv_cdramo_i"    , cdramo);
			paramsR.put("pv_estado_i"    , estado);
			paramsR.put("pv_nmpoliza_i"  , nmpoliza);
			paramsR.put("pv_nmsuplem_i"  , 0);
			paramsR.put("pv_feinici_i"   , new Date());
			paramsR.put("pv_cddocume_i"  , this.getText("pdf.emision.rechazo.nombre"));
			paramsR.put("pv_dsdocume_i"  , "CARTA RECHAZO");
			paramsR.put("pv_nmsolici_i"  , nmpoliza);
			paramsR.put("pv_ntramite_i"  , ntramite);
			paramsR.put("pv_tipmov_i"    , TipoTramite.POLIZA_NUEVA.getCdtiptra());
			paramsR.put("pv_swvisible_i" , Constantes.SI);
			kernelManagerSustituto.guardarArchivo(paramsR);
	    }
		catch(Exception ex)
		{
			logger.error("error al crear la carta rechazo",ex);
		}
	
		logger.info(""
				+ "\n###### guardarCartaRechazo ######"
				+ "\n#################################"
				);
		return SUCCESS;
	}
    
	
	public String guardaFacturaAltaTramite(){
		logger.debug(""
		+ "\n#########################################"
		+ "\n#########################################"
		+ "\n###### GUARDA FACTURA ALTA TRAMITE ######"
		+ "\n######                             ######"
		);
        logger.debug(datosTablas);
        logger.debug(params);
		try {
				//Realizamos la eliminaci�n de las facturas
				siniestrosManager.getEliminacionTFacMesaControl(params.get("idNumTramite"));
        		for(int i=0;i<datosTablas.size();i++)
	            {
	        		siniestrosManager.guardaListaFacMesaControl(
	        			params.get("idNumTramite"), 
	                    datosTablas.get(i).get("nfactura"),
	                    datosTablas.get(i).get("ffactura").substring(8,10)+"/"+datosTablas.get(i).get("ffactura").substring(5,7)+"/"+datosTablas.get(i).get("ffactura").substring(0,4),//getDate(datosTablas.get(i).get("ffactura"))+"",
	                    datosTablas.get(i).get("cdtipser"),
	                    datosTablas.get(i).get("cdpresta"),
	                    datosTablas.get(i).get("ptimport"),
	                    null,
	                    null,
	                    null,
	                    null,
	                    datosTablas.get(i).get("cdmoneda"),
	                    datosTablas.get(i).get("tasacamb"),
	                    datosTablas.get(i).get("ptimporta"),
	                    null,
	                    null
	                );
	            }
	   	}catch( Exception e){
	   		logger.error("Error al guardar las facturas",e);
	   		success =  false;
	   		return SUCCESS;
	   	}
	   	
	   	logger.debug(""
		+ "\n######                             ######"
		+ "\n###### GUARDA FACTURA ALTA TRAMITE ######"
		+ "\n#########################################"
		+ "\n#########################################"
		);
	   	success = true;
	   	return SUCCESS;
	}
	
    public String guardaTworksin()
    {
    	logger.debug(""
    			+ "\n#############################"
    			+ "\n#############################"
    			+ "\n###### guardaTworksin ######"
    			+ "\n######                ######"
    			);
    	logger.debug("params: "+params);
    	logger.debug(datosTablas);
    	try
    	{
    		/*Primero tenemos que hacer la eliminaci�n de los Asegurados*/
    		// SE REALIZA LA ELIMINACION EN TWORKSIN
    		try {
        			// numero de tramite, 
    				siniestrosManager.getEliminacionTworksin(params.get("idNumTramite"),datosTablas.get(0).get("modFactura"));
    				//siniestrosManager.getEliminacionTworksin(msgResult,msgResult);
    		} catch (Exception e) {
    				logger.error("error al eliminar en TworkSin ",e);
    		}
    		for(int i=0;i<datosTablas.size();i++)
            {
    			HashMap<String, Object> paramsTworkSin = new HashMap<String, Object>();
    			paramsTworkSin.put("pv_nmtramite_i",params.get("idNumTramite"));
    			paramsTworkSin.put("pv_cdunieco_i",	datosTablas.get(i).get("modUnieco"));
    			paramsTworkSin.put("pv_cdramo_i",	datosTablas.get(i).get("modRamo"));
    			paramsTworkSin.put("pv_estado_i",	datosTablas.get(i).get("modEstado"));
    			paramsTworkSin.put("pv_nmpoliza_i",	datosTablas.get(i).get("modPolizaAfectada"));
    			paramsTworkSin.put("pv_nmsolici_i",	datosTablas.get(i).get("modNmsolici"));
                paramsTworkSin.put("pv_nmsuplem_i",	datosTablas.get(i).get("modNmsuplem"));
                paramsTworkSin.put("pv_nmsituac_i",	datosTablas.get(i).get("modNmsituac"));
                paramsTworkSin.put("pv_cdtipsit_i",	datosTablas.get(i).get("modCdtipsit"));
                paramsTworkSin.put("pv_cdperson_i",	datosTablas.get(i).get("modCdperson"));
                paramsTworkSin.put("pv_feocurre_i",	datosTablas.get(i).get("modFechaOcurrencia"));
                paramsTworkSin.put("pv_nmautser_i",	null);
                paramsTworkSin.put("pv_nfactura_i",	datosTablas.get(i).get("modFactura"));
                siniestrosManager.guardaListaTworkSin(paramsTworkSin);
            }
    		mensaje = "Asegurado guardado";
    		success = true;
    		
    	}
    	catch(Exception ex)
    	{
    		logger.debug("error al guardar tworksin",ex);
    		success=false;
    		mensaje=ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                 ######"
    			+ "\n###### guardaTworksin  ######"
    			+ "\n#############################"
    			+ "\n#############################"
    			);
    	return SUCCESS;
    }
    
    public String guardaaseguradoUnico()
    {
    	logger.debug(""
    			+ "\n#############################"
    			+ "\n#############################"
    			+ "\n###### guardaTworksin ######"
    			+ "\n######                ######"
    			);
    	logger.debug("params: "+params);
    	/*try
    	{
    		HashMap<String, Object> paramsTworkSin = new HashMap<String, Object>();
				paramsTworkSin.put("pv_nmtramite_i",params.get("nmtramite"));
                paramsTworkSin.put("pv_cdunieco_i",params.get("cdunieco"));
                paramsTworkSin.put("pv_cdramo_i",params.get("cdramo"));
                paramsTworkSin.put("pv_estado_i",params.get("estado"));
                paramsTworkSin.put("pv_nmpoliza_i",params.get("nmpoliza"));
                paramsTworkSin.put("pv_nmsolici_i",params.get("nmsolici"));
                paramsTworkSin.put("pv_nmsuplem_i",params.get("nmsuplem"));
                paramsTworkSin.put("pv_nmsituac_i",params.get("nmsituac"));
                paramsTworkSin.put("pv_cdtipsit_i",params.get("cdtipsit"));
                paramsTworkSin.put("pv_cdperson_i",params.get("cdperson"));
                paramsTworkSin.put("pv_feocurre_i",params.get("feocurre"));
                paramsTworkSin.put("pv_nmautser_i",null);
                
				siniestrosManager.guardaListaTworkSin(paramsTworkSin);
    		mensaje = "Asegurado guardado";
    		success = true;
    		
    	}
    	catch(Exception ex)
    	{
    		logger.debug("error al guardar tworksin",ex);
    		success=false;
    		mensaje=ex.getMessage();
    	}*/
    	logger.debug(""
    			+ "\n######                 ######"
    			+ "\n###### guardaTworksin  ######"
    			+ "\n#############################"
    			+ "\n#############################"
    			);
    	return SUCCESS;
    }
    
    
    public String eliminarAsegurado()
    {
    	logger.debug(""
    			+ "\n###############################"
    			+ "\n###############################"
    			+ "\n###### eliminarAsegurado ######"
    			+ "\n######                   ######"
    			);
    	logger.debug("params: "+params);
    	try
    	{
    		String fechaOcurrencia = params.get("feocurre");
			String feOcurrencia= fechaOcurrencia.substring(8,10)+"/"+fechaOcurrencia.substring(5,7)+"/"+fechaOcurrencia.substring(0,4);
			
			HashMap<String, Object> paramsTworkSin = new HashMap<String, Object>();
    			paramsTworkSin.put("pv_nmtramite_i",params.get("nmtramite"));
                paramsTworkSin.put("pv_cdunieco_i",params.get("cdunieco"));
                paramsTworkSin.put("pv_cdramo_i",params.get("cdramo"));
                paramsTworkSin.put("pv_estado_i",params.get("estado"));
                paramsTworkSin.put("pv_nmpoliza_i",params.get("nmpoliza"));
                paramsTworkSin.put("pv_nmsuplem_i",params.get("nmsuplem"));
                paramsTworkSin.put("pv_nmsituac_i",params.get("nmsituac"));
                paramsTworkSin.put("pv_cdtipsit_i",params.get("cdtipsit"));
                paramsTworkSin.put("pv_cdperson_i",params.get("cdperson"));
                paramsTworkSin.put("pv_feocurre_i",feOcurrencia);
                
				siniestrosManager.eliminarAsegurado(paramsTworkSin);
    		mensaje = "Asegurado eliminado";
    		success = true;
    		
    	}
    	catch(Exception ex)
    	{
    		logger.debug("error al eliminar registro",ex);
    		success=false;
    		mensaje=ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                    ######"
    			+ "\n###### eliminarAsegurado  ######"
    			+ "\n################################"
    			+ "\n################################"
    			);
    	return SUCCESS;
    }

    public String consultaFacturaPagada(){
	   	logger.debug(" **** Entrando al metodo el tramite  de la factura ****");
	   	try {
	   		factPagada = siniestrosManager.obtieneTramiteFacturaPagada(params.get("nfactura"), params.get("cdpresta"));
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de los asegurados ",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
  }
    /**
     * Funci�n que obtiene la lista de las poliza
     * @param cdperson
     * @return Lista GenericVO con la informaci�n de los asegurados
     */ 
    public String consultaAutServicioSiniestro(){
    	logger.debug(" **** Entrando al metodo para obtener las autorizaciones ****");
    	logger.debug(params);
    	try {
    			datosInformacionAdicional = siniestrosManager.getConsultaListaAutServicioSiniestro(params.get("cdperson"));
    			logger.debug("VALOR DE RESPUESTA");
    			logger.debug(datosInformacionAdicional);
 			}catch( Exception e){
 				logger.error("Error al obtener las autorizaciones",e);
 				return SUCCESS;
 			}
 	   	success = true;
 	   	return SUCCESS;
    }
    
    public String consultaSiniestroMaestro(){
    	logger.debug(" **** Entrando al metodo para obtener las autorizaciones ****");
    	logger.debug(params);
    	try {
    			datosInformacionAdicional = siniestrosManager.getConsultaListaMSiniestMaestro(params.get("cdunieco"),params.get("cdramo"),params.get("estado"),params.get("nmpoliza"),
    														params.get("nmsuplem"),params.get("nmsituac"),params.get("status"));
    			logger.debug("VALOR DE RESPUESTA");
    			logger.debug(datosInformacionAdicional);
 			}catch( Exception e){
 				logger.error("Error al obtener las consultaSiniestroMaestro",e);
 				return SUCCESS;
 			}
 	   	success = true;
 	   	return SUCCESS;
    }
    
    
    
    public String consultaDatosValidacionSiniestro(){
    	logger.debug(" **** Entrando al metodo para obtener los datos de la validacion****");
    	logger.debug(params);
    	try {
    		datosValidacion = siniestrosManager.getConsultaDatosValidacionSiniestro(params.get("ntramite"),params.get("nfactura"),params.get("tipoPago"));
    			logger.debug("###VALOR DE RESPUESTA ###");
    			logger.debug(datosValidacion);
 			}catch( Exception e){
 				logger.error("Error al obtener las autorizaciones",e);
 				return SUCCESS;
 			}
 	   	success = true;
 	   	return SUCCESS;
    }
    
    public String consultaDatosValidacionAjustadorMed(){
    	logger.debug(" **** Entrando al metodo para obtener consultaDatosValidacionAjustadorMed ****");
    	logger.debug(params);
    	try {
    		datosValidacion = siniestrosManager.getConsultaDatosValidacionAjustadorMed(params.get("ntramite"));
    			logger.debug("###VALOR DE RESPUESTA ###");
    			logger.debug(datosValidacion);
 			}catch( Exception e){
 				logger.error("Error al obtener las consultaDatosValidacionAjustadorMed",e);
 				return SUCCESS;
 			}
 	   	success = true;
 	   	return SUCCESS;
    }
    
    public String validaCdTipsitTramite(){
	   	logger.debug(" **** Entrando al metodo de validacion de Penalizacion ****");
	   	try {
		   		HashMap<String, Object> paramTramite= new HashMap<String, Object>();
		   		paramTramite.put("pv_ntramite_i",params.get("ntramite"));
				
		   		validaCdTipsitTramite = siniestrosManager.validaCdTipsitAltaTramite(paramTramite);
	   	}catch( Exception e){
	   		logger.error("Error al obtener el cdtipsit",e);
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
  }

    public String generarCalculoSiniestros()
    {
    	logger.debug(""
    			+ "\n#######################################"
    			+ "\n#######################################"
    			+ "\n###### Generar calculoSiniestros ######"
    			+ "\n######             		      ######"
    			);
    	logger.debug("params: "+params);
    	try
    	{
    		this.facturasxSiniestro=new ArrayList<Map<String,Object>>();
    		imap = new HashMap<String,Item>();
    		String             ntramite    = params.get("ntramite");
    		UserVO             usuario     = (UserVO)session.get("USUARIO");
    		String             cdrol       = usuario.getRolActivo().getClave();
    		
    		//Se obtiene el tramite completo
    		Map<String,String> tramite     = siniestrosManager.obtenerTramiteCompleto(ntramite);
    		logger.debug("TRAMITE -->"+tramite);
    		smap = tramite;
    		//Se obtiene el listado de las facturas
    		List<Map<String,String>> facturasAux = siniestrosManager.obtenerFacturasTramite(ntramite);
    		logger.debug("LISTADO DE FACTURAS -->"+facturasAux);

    		if(tramite==null||facturasAux==null)
    		{
    			throw new Exception("No se encontro tramite/facturas para el tramite");
    		}
    		
    		//Borramos los calculos anteriores
    		siniestrosManager.movTimpsini(
    				Constantes.DELETE_MODE, null, null, null, null,
    				null, null, null, null, null,
    				ntramite, null, null, null, null, null, false
    		);

    		boolean esPagoDirecto = false;
    		if(tramite.get("OTVALOR02").equals("1"))
    		{
    			esPagoDirecto = true;
    		}
    		logger.debug("TIPO DE PAGO ES DIRECTO --> "+esPagoDirecto);
    		
    		Map<String,String> factura        = null;
    		Map<String,String> siniestroIte   = null;
    		Map<String,String> proveedor      = null;
    		Map<String,String> siniestro      = null;

    		List<Map<String,String>>conceptos = null;
    		
    		slist2                  = new ArrayList<Map<String,String>>();
    		slist3                  = new ArrayList<Map<String,String>>();
    		llist1                  = new ArrayList<List<Map<String,String>>>();
    		lhosp                   = new ArrayList<Map<String,String>>();
    		lpdir                   = new ArrayList<Map<String,String>>();
    		lprem                   = new ArrayList<Map<String,String>>();
    		datosPenalizacion       = new ArrayList<Map<String,String>>();
    		datosCoberturaxCal      = new ArrayList<Map<String,String>>();
    		listaImportesWS         = new ArrayList<Map<String,String>>(); 
    		if(esPagoDirecto)
    		{
    			//Verificamos la informacion del proveedor
    			smap.put("PAGODIRECTO","S");
    			smap2     = facturasAux.get(0);
    			
    			proveedor = siniestrosManager.obtenerDatosProveedor(facturasAux.get(0).get("CDPRESTA"));
    			logger.debug("PROVEEDOR-->"+proveedor);
    			smap3     = proveedor;
    			double ivaprov = Double.parseDouble(proveedor.get("IVA")); 
    			double cedprov = Double.parseDouble(proveedor.get("CEDULAR"));
    			double isrprov = Double.parseDouble(proveedor.get("ISR"));
    			
    			//RECORREMOS LAS FACTURAS DEL TRAMITE
    			for(int i = 0; i < facturasAux.size(); i++)
    			{
    				factura = facturasAux.get(i);
    				logger.debug("FACTURA PROCESANDO ---> "+factura.get("NFACTURA"));
    				//Se grega la factura c/u
    				Map<String,Object>facturaObj=new HashMap<String,Object>();
        			facturaObj.putAll(factura);
        			this.facturasxSiniestro.add(facturaObj);
        			
        			//Se agrega los Asegurados o siniestros por factura
        			List<Map<String,String>> siniestros = siniestrosManager.listaSiniestrosMsiniesTramite(ntramite,factura.get("NFACTURA"),null);
        			logger.debug("VALOR DE SINIESTROS POR FACTURAS--->"+siniestros);
        			
        			conceptos = siniestrosManager.P_GET_CONCEPTOS_FACTURA(
    					null, null, null, null, null, null, null, null, null, factura.get("NFACTURA"));
    				logger.debug("OBTENEMOS LA INFORMACION DE LOS CONCEPTOS POR FACTURA -->"+conceptos);
        			
    				//RECORREMOS LOS SINIESTROS
    				this.aseguradosxSiniestro=new ArrayList<Map<String,Object>>();
    				for( int j= 0; j < siniestros.size();j++){
    					//Se realiza la asignacion del primer asegurado
        				siniestroIte    = siniestros.get(j);
        				Map<String,Object>aseguradoObj=new HashMap<String,Object>();
    					aseguradoObj.putAll(siniestroIte);
    					this.aseguradosxSiniestro.add(aseguradoObj);
    					
    					String cdunieco = siniestroIte.get("CDUNIECO");
        				String cdramo   = siniestroIte.get("CDRAMO");
        				String estado   = siniestroIte.get("ESTADO");
        				String nmpoliza = siniestroIte.get("NMPOLIZA");
        				String nmsuplem = siniestroIte.get("NMSUPLEM");
        				String nmsituac = siniestroIte.get("NMSITUAC");
        				String aaapertu = siniestroIte.get("AAAPERTU");
        				String status   = siniestroIte.get("STATUS");
        				String nmsinies = siniestroIte.get("NMSINIES");
        				String nfactura = factura.get("NFACTURA");
        				
        				//Asignacion de variables
        				double penalizacionCambioZona =0d;
        				double penalizacionCirculoHosp =0d;
        				String aplicaIVA= "S";
    					String seleccionAplica= "D";
    					String ivaRetenido= "N";
    					double deducibleSiniestroIte      = 0d;
        				double copagoAplicadoSiniestroIte = 0d;
        				double cantidadCopagoSiniestroIte = 0d;
        				String penalizacionPesos = "0";
        				String penalizacionPorcentaje = "0";
        				
        				//0.- Guardamos datos adicionales en factura
        				Map<String,String>autorizacionesFactura = siniestrosManager.obtenerAutorizacionesFactura(
        						cdunieco, cdramo, estado, nmpoliza, nmsuplem,
        						nmsituac, aaapertu, status, nmsinies, nfactura);
        				logger.debug("AUTORIACION DE LAS FACTURAS ---> "+autorizacionesFactura);
        				
        				facturaObj.put("AUTMEDIC"+nmsinies,autorizacionesFactura.get("AUTMEDIC"));
        				facturaObj.put("COMMENME"+nmsinies,autorizacionesFactura.get("COMMENME"));
        				facturaObj.put("AUTRECLA"+nmsinies,autorizacionesFactura.get("AUTRECLA"));
        				facturaObj.put("COMMENAR"+nmsinies,autorizacionesFactura.get("COMMENAR"));
        				
        				//1.- Obtenemos los datos generales del siniestros
        				List<Map<String,String>> informacionGral = siniestrosManager.obtieneDatosGeneralesSiniestro(cdunieco, cdramo,
        						estado, nmpoliza,nmsituac, nmsuplem, status, aaapertu, nmsinies, factura.get("NTRAMITE"));
        				
        				if(informacionGral.size()>0){
        					aseguradoObj.put("CAUSASINIESTRO", informacionGral.get(0).get("CDCAUSA"));
        				}else{
        					aseguradoObj.put("CAUSASINIESTRO", CausaSiniestro.ENFERMEDAD.getCodigo());
        				}
        				
        				Map<String,String>copagoDeducibleSiniestroIte =siniestrosManager.obtenerCopagoDeducible(
        						cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura);
        				logger.debug("INFORMACION DEDUCIBLE/COPAGO ASEGURADO -->"+copagoDeducibleSiniestroIte);
        				
        				String tipoFormatoCalculo         = copagoDeducibleSiniestroIte.get("FORMATOCALCULO");
        				String calculosPenalizaciones     = copagoDeducibleSiniestroIte.get("PENALIZACIONES");
        				//Se guarda la informacion de tipo de formato y calculo de penalizaciones en factura
        				facturaObj.put("TIPOFORMATOCALCULO",""+tipoFormatoCalculo);
        				facturaObj.put("CALCULOSPENALIZACIONES",""+calculosPenalizaciones);
        				
        				if(calculosPenalizaciones.equalsIgnoreCase("1")){
        		   			//4.1.- Verificamos si existe exclusi�n de penalizaci�n
        		   			HashMap<String, Object> paramExclusion = new HashMap<String, Object>();
            		   		paramExclusion.put("pv_cdunieco_i",cdunieco);
            		   		paramExclusion.put("pv_estado_i",estado);
            		   		paramExclusion.put("pv_cdramo_i",cdramo);
            		   		paramExclusion.put("pv_nmpoliza_i",nmpoliza);
            		   		paramExclusion.put("pv_nmsituac_i",nmsituac);
            		   		
            		   		//1.- verificamos el ramo
            		   		if(cdramo.equalsIgnoreCase("2")){
                		   		existePenalizacion = siniestrosManager.validaExclusionPenalizacion(paramExclusion);
                		   		//4.2.- Obtenemos la penalizaci�n por cambio de Zona
                		   		penalizacionCambioZona = penalizacionCambioZona(existePenalizacion,informacionGral.get(0).get("CDCAUSA"),informacionGral.get(0).get("CIRHOSPI"),
                						informacionGral.get(0).get("DSZONAT"),informacionGral.get(0).get("CDPROVEE"),cdramo);
                		   		aseguradoObj.put("PENALIZACIONCAMBIOZONA",""+penalizacionCambioZona);
                				//4.3.- Obtenemos la penalizaci�n por circulo Hospitalario
                				penalizacionCirculoHosp = calcularPenalizacionCirculo(informacionGral.get(0).get("CIRHOSPI"), informacionGral.get(0).get("CIRHOPROV"),informacionGral.get(0).get("CDCAUSA"));
                				aseguradoObj.put("PENALIZACIONCIRCULOHOSP",""+penalizacionCirculoHosp);            		   			
            		   		}else{
            		   			aseguradoObj.put("PENALIZACIONCAMBIOZONA",""+penalizacionCambioZona);
            		   			aseguradoObj.put("PENALIZACIONCIRCULOHOSP",""+penalizacionCirculoHosp);
            		   		}
        		   		}else{
        		   			//4.2.- Obtenemos la penalizaci�n por cambio de Zona
        		   			aseguradoObj.put("PENALIZACIONCAMBIOZONA",""+penalizacionCambioZona);
        		   			//4.3.- Obtenemos la penalizaci�n por circulo Hospitalario
        		   			aseguradoObj.put("PENALIZACIONCIRCULOHOSP",""+penalizacionCirculoHosp);
        		   		}
        				
        				//4.4.- Obtenemos el total de penalizaci�n
        				String calcularTotalPenalizacion = calcularTotalPenalizacion(penalizacionCambioZona,penalizacionCirculoHosp,informacionGral.get(0).get("CDCAUSA"),
        																			 copagoDeducibleSiniestroIte.get("COPAGO"),copagoDeducibleSiniestroIte.get("TIPOCOPAGO"),
        																			 informacionGral.get(0).get("CDPROVEE"),cdramo, informacionGral.get(0).get("FEOCURRE"));

        				aseguradoObj.put("TOTALPENALIZACIONGLOBAL",""+calcularTotalPenalizacion);
        				String penalizacionT[] = calcularTotalPenalizacion.split("\\|");
        				penalizacionPorcentaje = penalizacionT[0].toString();
        				penalizacionPesos = penalizacionT[1].toString();
        				aseguradoObj.put("COPAGOPORCENTAJES",penalizacionPorcentaje);
        				aseguradoObj.put("COPAGOPESOS",penalizacionPesos);
        				
    					//5.- Obtenemos informaci�n adicional de las facturas, para realizar la validaci�n de aplica IVA o No
        				List<Map<String, String>> listaFactura = siniestrosManager.P_GET_FACTURAS_SINIESTRO(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies);
        				logger.debug("VALOR DE listaFactura -->"+listaFactura);
        				
        				if(listaFactura.get(0).get("APLICA_IVA") != null){
        					aplicaIVA= listaFactura.get(0).get("APLICA_IVA");
        					seleccionAplica =listaFactura.get(0).get("ANTES_DESPUES");
        					ivaRetenido = listaFactura.get(0).get("IVARETENIDO");
        					if(!StringUtils.isNotBlank(ivaRetenido)){
        						ivaRetenido= "N";
        					}
        				}
        				
        				String sDeducibleSiniestroIte     = copagoDeducibleSiniestroIte.get("DEDUCIBLE").replace(",","");
        				String sCopagoSiniestroIte        = copagoDeducibleSiniestroIte.get("COPAGO").replace(",", "");
        				String tipoCopagoSiniestroIte     = copagoDeducibleSiniestroIte.get("TIPOCOPAGO");
        				
        				//Verificacaci�n de la informaci�n de Deducible
        				if(StringUtils.isNotBlank(sDeducibleSiniestroIte)
        						&&(!sDeducibleSiniestroIte.equalsIgnoreCase("na"))
        						&&(!sDeducibleSiniestroIte.equalsIgnoreCase("no"))
        						)
        				{
        					try
        					{
        						deducibleSiniestroIte = Double.valueOf(sDeducibleSiniestroIte);
        					}
        					catch(Exception ex)
        					{
        						logger.debug(""
        								+ "\n### ERROR ##################################################"
        								+ "\n### no es numero deducible: '"+sDeducibleSiniestroIte+"' ###"
        								+ "\n############################################################"
        								);
        						deducibleSiniestroIte = 0d;
        					}
        				}
        				//Verificacaci�n de la informaci�n de copago
        				if(StringUtils.isNotBlank(sCopagoSiniestroIte)
        						&&(!sCopagoSiniestroIte.equalsIgnoreCase("na"))
        						&&(!sCopagoSiniestroIte.equalsIgnoreCase("no"))
        						)
        				{
        					try
        					{
        						cantidadCopagoSiniestroIte = Double.valueOf(sCopagoSiniestroIte);
        					}
        					catch(Exception ex)
        					{
        						logger.debug(""
        								+ "\n### ERROR ############################################"
        								+ "\n### no es numero copago: '"+sCopagoSiniestroIte+"' ###"
        								+ "\n######################################################"
        								);
        						cantidadCopagoSiniestroIte = 0d;
        					}
        				}
        				
        				Map<String,String>importesWSSiniestroIte=new HashMap<String,String>();
        				importesWSSiniestroIte.put("cdunieco" , siniestroIte.get("CDUNIECO"));
        				importesWSSiniestroIte.put("cdramo"   , siniestroIte.get("CDRAMO"));
        				importesWSSiniestroIte.put("estado"   , siniestroIte.get("ESTADO"));
        				importesWSSiniestroIte.put("nmpoliza" , siniestroIte.get("NMPOLIZA"));
        				importesWSSiniestroIte.put("nmsuplem" , siniestroIte.get("NMSUPLEM"));
        				importesWSSiniestroIte.put("nmsituac" , siniestroIte.get("NMSITUAC"));
        				importesWSSiniestroIte.put("aaapertu" , siniestroIte.get("AAAPERTU"));
        				importesWSSiniestroIte.put("status"   , siniestroIte.get("STATUS"));
        				importesWSSiniestroIte.put("nmsinies" , siniestroIte.get("NMSINIES"));
        				importesWSSiniestroIte.put("ntramite" , ntramite);
        				listaImportesWS.add(importesWSSiniestroIte);
        				double importeSiniestroIte;
        				double ivaSiniestroIte;
        				double ivrSiniestroIte;
        				double isrSiniestroIte;
        				double cedSiniestroIte;

        				//hospitalizacion
        				Map<String,String> hosp = new HashMap<String,String>();
        				lhosp.add(hosp);
        				hosp.put("PTIMPORT" 	, "0");
        				hosp.put("DESTO"    	, "0");
        				hosp.put("IVA"      	, "0");
        				hosp.put("PRECIO"   	, "0");
        				hosp.put("DESCPRECIO"   , "0");
        				hosp.put("IMPISR"   , "0");
        				hosp.put("IMPCED"   , "0");
        				//hospitalizacion
        				
        				//reembolso
        				Map<String,String>mprem=new HashMap<String,String>();
        				mprem.put("dummy","dummy");
        				lprem.add(mprem);
        				//remmbolso
        				
        				//pago directo
        				Map<String,String> mpdir = new HashMap<String,String>();
        				 mpdir.put("total","0");
        				 mpdir.put("totalcedular","0");
        				 mpdir.put("ivaTotalMostrar","0");
        				 mpdir.put("ivaRetenidoMostrar","0");
        				 mpdir.put("iSRMostrar","0");
        				 lpdir.add(mpdir);
        				
        				//INICIO DE CONCEPTOS
        				this.conceptosxSiniestro = new ArrayList<Map<String,String>>();
        				for(int k = 0; k < conceptos.size() ; k++)
        				{
        					Map<String, String> concepto = conceptos.get(k);
        					if(concepto.get("CDUNIECO").equals(siniestroIte.get("CDUNIECO"))
        							&&concepto.get("CDRAMO").equals(siniestroIte.get("CDRAMO"))
        							&&concepto.get("ESTADO").equals(siniestroIte.get("ESTADO"))
        							&&concepto.get("NMPOLIZA").equals(siniestroIte.get("NMPOLIZA"))
        							&&concepto.get("NMSUPLEM").equals(siniestroIte.get("NMSUPLEM"))
        							&&concepto.get("NMSITUAC").equals(siniestroIte.get("NMSITUAC"))
        							&&concepto.get("AAAPERTU").equals(siniestroIte.get("AAAPERTU"))
        							&&concepto.get("STATUS").equals(siniestroIte.get("STATUS"))
        							&&concepto.get("NMSINIES").equals(siniestroIte.get("NMSINIES"))
        							)
        					{
        						conceptosxSiniestro.add(concepto);
        						
        						//---> listaConceptosSiniestro.add(concepto);
        						if(tipoFormatoCalculo.equalsIgnoreCase("1"))
        						{
        							// CALCULOS PARA CUANDO ES HOSPITALIZACI�N Y AYUDA DE MATERNIDAD
        							logger.debug(">>HOSPITALIZACION");
        							double PTIMPORT    = Double.parseDouble(concepto.get("PTIMPORT"));
        							double DESTOPOR    = Double.parseDouble(concepto.get("DESTOPOR"));
        							double DESTOIMP    = Double.parseDouble(concepto.get("DESTOIMP"));
        							double PTPRECIO    = Double.parseDouble(concepto.get("PTPRECIO")) * Double.parseDouble(concepto.get("CANTIDAD"));
        							logger.debug("concepto importe "+PTIMPORT);
        							logger.debug("concepto desto "+DESTOPOR);
        							logger.debug("concepto destoimp "+DESTOIMP);
        							logger.debug("usando iva proveedor "+ivaprov);
        							boolean copagoPorc = false;
        							String scopago     = concepto.get("COPAGO");
        							
        							if(scopago.equalsIgnoreCase("no") ||scopago.equalsIgnoreCase("na"));
        							{
        								scopago="0";
        							}
        							logger.debug("procesar copago "+scopago);
        							if(StringUtils.isNotBlank(scopago))
        							{
        								if(scopago.contains("%"))
        								{
        									copagoPorc = true;
        								}
        								scopago=scopago.replace("%", "").replace("$", "");
        								if(copagoPorc)
        								{
        									DESTOPOR=DESTOPOR+Double.valueOf(scopago);
        								}
        								else
        								{
        									DESTOIMP=DESTOIMP+Double.valueOf(scopago);
        								}
        							}
        							logger.debug("concepto desto + copago % "+DESTOPOR);
        							logger.debug("concepto destoimp + copago $ "+DESTOIMP);
        							
        							//CALCULOS ANTES DE COPAGO
        							double hPTIMPORT = Double.parseDouble(hosp.get("PTIMPORT"));
        							double hDESTO    = Double.parseDouble(hosp.get("DESTO"));
        							double hIVA      = Double.parseDouble(hosp.get("IVA"));
        							double hISR      = Double.parseDouble(hosp.get("IMPISR"));
        							double hICED      = Double.parseDouble(hosp.get("IMPCED"));
        							double hPRECIO      = Double.parseDouble(hosp.get("PRECIO"));
        							double hDESCPRECIO  = Double.parseDouble(hosp.get("DESCPRECIO"));
        							
        							hPTIMPORT 	+= PTIMPORT;
        							hDESTO    	+= (PTIMPORT*(DESTOPOR/100d)) + (DESTOIMP);
        							hIVA      	+= PTIMPORT*(ivaprov/100d);
        							hISR		+= PTIMPORT*(isrprov/100d);
        							hICED		+= PTIMPORT*(cedprov/100d);
        							hPRECIO 	+= PTPRECIO;
        							hDESCPRECIO += (PTPRECIO*(DESTOPOR/100d)) + (DESTOIMP);
        							
        							hosp.put("PTIMPORT" , hPTIMPORT+"");
        							hosp.put("DESTO"    , hDESTO+"");
        							hosp.put("IVA"      , hIVA+"");
        							hosp.put("IMPISR"   , hISR+"");
        							hosp.put("PRECIO"   , hPRECIO+"");
        							hosp.put("DESCPRECIO", hDESCPRECIO+"");
        							hosp.put("IMPCED"   , hICED+"");
        							
        							logger.debug("#### VALORES DEL VECTOR #####");
        							logger.debug("hPTIMPORT "+hPTIMPORT);
        							logger.debug("hDESTO "+hDESTO);
        							logger.debug("hIVA "+hIVA);
        							logger.debug("hISR "+hISR);
        							logger.debug("hPRECIO "+hPRECIO);
        							logger.debug("hDESCPRECIO "+hDESCPRECIO);
        							logger.debug("hICED "+hICED);
        							
        							logger.debug("<<HOSPITALIZACION");
        						}
        						else
        						{
        							//logger.debug("######  COBERTURA DIFERENTE DE HOSPITALIZACI�N Y AYUDA DE MATERNIDAD  ######");
        							logger.debug(">>PAGO DIRECTO DIFERENTE A HOSPITALIZACION");
        							Map<String,String>row=new HashMap<String,String>();
        							row.putAll(concepto);
        							
        							double cantidad = Double.valueOf(row.get("CANTIDAD"));
        							logger.debug("cantidad "+cantidad);
        							double precioArancel = 0d;
        							//Obtenemos el valor original del arancel
        							if(StringUtils.isNotBlank(row.get("IMP_ARANCEL")))
        							{
        								precioArancel = Double.valueOf(row.get("IMP_ARANCEL"));
        							}
        							row.put("IMP_ARANCEL",precioArancel+"");
        							logger.debug("precioArancel "+precioArancel);
        							double subtotalArancel = cantidad*precioArancel;//++
        							logger.debug("subtotalArancel "+subtotalArancel);
        							row.put("SUBTTARANCEL",subtotalArancel+"");
        							double descuentoPorc   = 0d;
        							double descuentoImpo   = 0d;
        							if(StringUtils.isNotBlank(row.get("DESTOPOR")))
        							{
        								descuentoPorc = Double.parseDouble(row.get("DESTOPOR"));
        							}
        							if(StringUtils.isNotBlank(row.get("DESTOIMP")))
        							{
        								descuentoImpo = Double.parseDouble(row.get("DESTOIMP"));
        							}
        							double descuentoAplicado=(subtotalArancel*(descuentoPorc/100d))+descuentoImpo;//++
        							logger.debug("descuentoAplicado "+descuentoAplicado);
        							row.put("DESTOAPLICA",descuentoAplicado+"");
        							double subtotalDescuento=subtotalArancel-descuentoAplicado;//++
        							logger.debug("subtotalDescuento "+subtotalDescuento);
        							row.put("SUBTTDESCUENTO",subtotalDescuento+""); // SUBTOTAL A OCUPAR CUANDO SEA ANTES DE COPAGO
        							
        							if(aplicaIVA.equalsIgnoreCase("S")){
        								if(seleccionAplica.equalsIgnoreCase("A")){ // ANTES DEL COPAGO
        									double iVaaplicaAntes = subtotalDescuento*(ivaprov/100d);//++
        									row.put("IVAAPLICA",iVaaplicaAntes+"");
        								}
        							}
        							
        							boolean copagoPorc = false;
        							double  copago = 0d;
        							double  copagoAplicado = 0d;//++
        							String scopago =concepto.get("COPAGO");
        							String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
        							
        							if(causadelSiniestro ==""||causadelSiniestro == null){
        								causadelSiniestro = "1";
        							}
        							if(causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
        								copagoAplicado = 0d;
        							}else{
        								if(StringUtils.isNotBlank(scopago))
        								{
        									if(scopago.equalsIgnoreCase("na") || scopago.equalsIgnoreCase("no")){
        										copagoAplicado = 0d;
        									}else{
        										if(scopago.contains("%"))
        										{
        											copagoPorc = true;
        										}
        										scopago=scopago.replace("%", "").replace("$", "").replaceAll(",", "");
        										copago=Double.valueOf(scopago);
        										if(copagoPorc)
        										{
        											copagoAplicado=(subtotalDescuento*(copago/100d));
        										}
        										else
        										{
        											copagoAplicado=copago * cantidad;
        										}
        									}
        								}
        							}
        							
        							row.put("COPAGOAPLICA",copagoAplicado+"");
        							logger.debug("copagoAplicado "+copagoAplicado);
        							double subtotalCopago    = subtotalDescuento - copagoAplicado;//++
        							logger.debug("subtotalCopago "+subtotalCopago);
        							row.put("SUBTTCOPAGO",subtotalCopago+"");
        							double israplicado       = subtotalCopago*(isrprov/100d);//++
        							logger.debug("israplicado "+israplicado);
        							row.put("ISRAPLICA",israplicado+"");
        							double subtotalImpuestos = subtotalCopago-(israplicado+0d);//cedularaplicado);//++
        							logger.debug("subtotalImpuestos "+subtotalImpuestos);
        							
        							double totalISRMostrar = Double.parseDouble(mpdir.get("iSRMostrar"));
        							logger.debug("base totalISRMostrar"+totalISRMostrar);
        							totalISRMostrar += israplicado;
        							//logger.debug("new totalISRMostrar"+totalISRMostrar);
        							mpdir.put("iSRMostrar",totalISRMostrar+"");
        							
        							////// modificado
        							double cedularaplicado   = subtotalCopago*(cedprov/100d);//++
        							logger.debug("cedularaplicado "+cedularaplicado);
        							row.put("CEDUAPLICA",cedularaplicado+"");
        							////// modificado
        							
        							subtotalImpuestos = subtotalImpuestos - cedularaplicado;
        							row.put("SUBTTIMPUESTOS",subtotalImpuestos+"");
        							
        							double ivaaplicado =0d;
        							double ivaRetenidos =0d;
        							double ptimportauto =0d;
        							//logger.debug("#####...aplicaIVA.....--->"+aplicaIVA);
        							
        							if(aplicaIVA.equalsIgnoreCase("S")){
        								if(seleccionAplica.equalsIgnoreCase("D")){
        									ivaaplicado       = subtotalCopago*(ivaprov/100d);//++
        									row.put("IVAAPLICA",ivaaplicado+"");
        									//logger.debug("#####...ivaRetenido.....--->"+ivaRetenido);
        									if(ivaRetenido.equalsIgnoreCase("S")){
        										ivaRetenidos      = ((2d * ivaaplicado)/3);
        										row.put("IVARETENIDO",ivaRetenidos+"");
        									}else{
        										ivaRetenidos      = 0d;
        										row.put("IVARETENIDO",ivaRetenidos+"");
        									}
        									ptimportauto      = (subtotalImpuestos-ivaRetenidos)+ivaaplicado;//++
        									row.put("PTIMPORTAUTO",ptimportauto+"");
        								}else{
        									ivaaplicado       = subtotalDescuento*(ivaprov/100d);
        									row.put("IVAAPLICA",ivaaplicado+"");
        									if(ivaRetenido.equalsIgnoreCase("S")){
        										ivaRetenidos      = ((2d * ivaaplicado)/3);
        										row.put("IVARETENIDO",ivaRetenidos+"");
        									}else{
        										ivaRetenidos      = 0d;
        										row.put("IVARETENIDO",ivaRetenidos+"");
        									}
        									ptimportauto      = (subtotalImpuestos-ivaRetenidos)+ivaaplicado; //++
        									row.put("PTIMPORTAUTO",ptimportauto+"");
        								}
        							}else{
        								ivaaplicado       = 0d;//++
        								ivaRetenidos      = 0d;
        								row.put("IVAAPLICA",ivaaplicado+"");
        								row.put("IVARETENIDO",ivaRetenidos+"");
        								ptimportauto      = (subtotalImpuestos-ivaRetenidos)+ivaaplicado;//++
        								row.put("PTIMPORTAUTO",ptimportauto+"");
        							}
        							
        							
        							double totalIVAMostrar = Double.parseDouble(mpdir.get("ivaTotalMostrar"));
        							logger.debug("base totalIVAMostrar"+totalIVAMostrar);
        							totalIVAMostrar += ivaaplicado;
        							logger.debug("new totalIVAMostrar"+totalIVAMostrar);
        							mpdir.put("ivaTotalMostrar",totalIVAMostrar+"");
        							
        							
        							double totalIVARetenidoMostrar = Double.parseDouble(mpdir.get("ivaRetenidoMostrar"));
        							logger.debug("base totalIVARetenidoMostrar"+totalIVARetenidoMostrar);
        							totalIVARetenidoMostrar += ivaRetenidos;
        							logger.debug("new totalIVAMostrar"+totalIVARetenidoMostrar);
        							mpdir.put("ivaRetenidoMostrar",totalIVARetenidoMostrar+"");
        							
        							double ptimport = Double.parseDouble(row.get("PTIMPORT"));
        							logger.debug("ptimport "+ptimport);
        							
        							double valorusado        = ptimportauto;//++
        							
        							String autmedic = row.get("AUTMEDIC");
        							if(StringUtils.isNotBlank(autmedic)&&autmedic.equalsIgnoreCase("S"))
        							{
        								valorusado = ptimport;
        							}
        							logger.debug("valorusado "+valorusado);
        							row.put("VALORUSADO",valorusado+"");
        							
        							double totalGrupo = Double.parseDouble(mpdir.get("total"));
        							logger.debug("base totalGrupo"+totalGrupo);
        							totalGrupo += valorusado;
        							logger.debug("new totalGrupo"+totalGrupo);
        							mpdir.put("total",totalGrupo+"");
        							
        							double totalGrupoCedular = Double.parseDouble(mpdir.get("totalcedular"));
        							logger.debug("base totalGrupoCedular"+totalGrupoCedular);
        							totalGrupoCedular += cedularaplicado;
        							logger.debug("new totalGrupoCedular"+totalGrupoCedular);
        							mpdir.put("totalcedular",totalGrupoCedular+"");
        							
        							concepto.putAll(row);
        							logger.debug("<<PAGO DIRECTO DIFERENTE A HOSPITALIZACION");
        						}
        					}
        				}//FIN DE CONCEPTOS
        				aseguradoObj.put("conceptosAsegurado", conceptosxSiniestro);
        				
        				
        				
        				
        				//hospitalizacion
        				//if(factura.get("CDGARANT").equalsIgnoreCase("18HO")||factura.get("CDGARANT").equalsIgnoreCase("18MA"))
        				//logger.debug("######  HOSPITALIZACI�N Y AYUDA DE MATERNIDAD WS ######");
        				if(tipoFormatoCalculo.equalsIgnoreCase("1"))
    					{
    						logger.debug(">>WS del siniestro iterado");
    						logger.debug("deducible siniestro iterado: "+sDeducibleSiniestroIte);
    						logger.debug("copago siniestro iterado: "+sCopagoSiniestroIte);
    						logger.debug("tipo copago siniestro iterado: "+tipoCopagoSiniestroIte);
    						
    						logger.debug("hosp.get(PTIMPORT)-->"+hosp.get("PTIMPORT"));
    						logger.debug("hosp.get(DESTO)-->"+hosp.get("DESTO"));
    						logger.debug("hosp.get(IVA)-->"+hosp.get("IVA"));
    						logger.debug("deducibleSiniestroIte--->"+deducibleSiniestroIte);
    						
    						double hPTIMPORT = Double.valueOf(hosp.get("PTIMPORT"));
    						double DESTOIMP  = Double.valueOf(hosp.get("DESTO"));
    						double hIVA      = Double.valueOf(hosp.get("IVA"));
    						String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
    						double subttDesto =0d;
    						
    						if(!causadelSiniestro.equalsIgnoreCase("2")){
    							//Diferente de accidente
    							subttDesto = (hPTIMPORT + DESTOIMP) - (DESTOIMP + deducibleSiniestroIte );
    						}else{
    							//accidente
    							subttDesto = (hPTIMPORT + DESTOIMP) - (DESTOIMP);
    						}
    						
    						if(StringUtils.isNotBlank(tipoCopagoSiniestroIte))
    						{
    							if(!causadelSiniestro.equalsIgnoreCase("2")){
    								//Diferente de accidente
    								copagoAplicadoSiniestroIte = Double.parseDouble(penalizacionPesos) + (subttDesto * ( Double.parseDouble(penalizacionPorcentaje) / 100d ));
    							}else{
    								//accidente
    								copagoAplicadoSiniestroIte= 0d;
    							}
    						}
    						
    						
    						importeSiniestroIte = subttDesto - copagoAplicadoSiniestroIte;
    						
    						double hIVADesCopago  = importeSiniestroIte*(ivaprov/100d);
    						logger.debug("Iva a ocupar despues de copago");
    						logger.debug(hIVADesCopago);
    						
    						hosp.put("PTIMPORT_DESCOPAGO" , importeSiniestroIte+"");
    						hosp.put("IVA_DESCOPAGO"    , hIVADesCopago+"");
    						
    		            	double importeBase=0d;
    		            	
    		            	importeBase= hPTIMPORT - DESTOIMP;
    		            	if(aplicaIVA.equalsIgnoreCase("S")){
    		            		//SI LOS VALORES SON ANTES DE COPAGO ENTONCES SE QUEDAN IGUALES LOS VALORES DE DESCUENTO, IVA Y PTIMPORT
    		            		if(seleccionAplica.equalsIgnoreCase("D")){ // ANTES DEL COPAGO
    		            			hosp.put("IVA"    , hIVADesCopago+"");
    		            			hosp.put("BASEIVA" , importeSiniestroIte+"");
    		            		}else{
    		            			
    		            			hosp.put("BASEIVA" , subttDesto+"");
    		            		}
    		            	}else{
    		            		hosp.put("IVA"    ,0d+"");
    		            		hosp.put("BASEIVA" , subttDesto+"");
    		            	}
    						
    		            	//APLICAMOS EL IVA RETENIDO
    		            	if(ivaRetenido.equalsIgnoreCase("S")){
    							ivrSiniestroIte = ((2d * Double.parseDouble(hosp.get("IVA")))/3d);
                                hosp.put("IVARETENIDO"    , ivrSiniestroIte+"");
                                
                            }else{
                            	ivrSiniestroIte = 0d;
                                hosp.put("IVARETENIDO"    , ivrSiniestroIte+"");
                            }
    						ivaSiniestroIte = Double.parseDouble(hosp.get("IVA"));//hIVA;
    						//ivrSiniestroIte = 0d;
    						isrSiniestroIte = Double.valueOf(hosp.get("IMPISR"));
    						cedSiniestroIte = Double.valueOf(hosp.get("IMPCED"));
    						importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(importeSiniestroIte)).toString());
    						importesWSSiniestroIte.put(IMPORTE_WS_IVA     , (new Double(ivaSiniestroIte)    ).toString());
    						importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroIte)    ).toString());
    						importesWSSiniestroIte.put(IMPORTE_WS_ISR     , (new Double(isrSiniestroIte)    ).toString());
    						importesWSSiniestroIte.put(IMPORTE_WS_CEDULAR , (new Double(cedSiniestroIte)    ).toString());
    						logger.debug("mapa WS siniestro iterado: "+importesWSSiniestroIte);
    						logger.debug("<<WS del siniestro iterado");
    						//logger.debug("###### HOSPITALIZACION Y AYUDA DE MATERNIDA WS ######");
    					}
    					else//pago directo
    					{
    						//logger.debug("######  COBERTURA DIFERENTE DE HOSPITALIZACI�N Y AYUDA DE MATERNIDAD  ######");
    						logger.debug(">>WS del siniestro iterado");
    						logger.debug("deducible siniestro iterado: "+sDeducibleSiniestroIte);
    						logger.debug("copago siniestro iterado: "+sCopagoSiniestroIte);
    						logger.debug("tipo copago siniestro iterado: "+tipoCopagoSiniestroIte);
    						
    						double totalGrupo = Double.valueOf(mpdir.get("total"));
    						
    						importeSiniestroIte = totalGrupo / ( 1d + ( ivaprov  / 100d ) );
    						
    						ivrSiniestroIte = 0d;
    						isrSiniestroIte = 0d;
    						ivaSiniestroIte = 0d;
    						cedSiniestroIte = Double.valueOf(mpdir.get("totalcedular"));
    						
    						double subttDescuentoSiniestroIte= 0d;
    						double subttISRSiniestroIte= 0d;
    						double subttcopagototalSiniestroIte=0;
    						
    						for(Map<String,String>concepto : conceptos)
    	    				{
    	    					if(concepto.get("CDUNIECO").equals(siniestroIte.get("CDUNIECO"))
    	    							&&concepto.get("CDRAMO").equals(siniestroIte.get("CDRAMO"))
    	    							&&concepto.get("ESTADO").equals(siniestroIte.get("ESTADO"))
    	    							&&concepto.get("NMPOLIZA").equals(siniestroIte.get("NMPOLIZA"))
    	    							&&concepto.get("NMSUPLEM").equals(siniestroIte.get("NMSUPLEM"))
    	    							&&concepto.get("NMSITUAC").equals(siniestroIte.get("NMSITUAC"))
    	    							&&concepto.get("AAAPERTU").equals(siniestroIte.get("AAAPERTU"))
    	    							&&concepto.get("STATUS").equals(siniestroIte.get("STATUS"))
    	    							&&concepto.get("NMSINIES").equals(siniestroIte.get("NMSINIES"))
    	    							)
    	    					{
    	    						subttDescuentoSiniestroIte+= Double.valueOf(concepto.get("SUBTTDESCUENTO"));
    	    						subttcopagototalSiniestroIte+= Double.valueOf(concepto.get("SUBTTCOPAGO"));
    	    						subttISRSiniestroIte+= Double.valueOf(concepto.get("ISRAPLICA"));
    	    						ivaSiniestroIte+= Double.valueOf(concepto.get("IVAAPLICA"));
    	    						ivrSiniestroIte += ((2 * Double.valueOf(concepto.get("IVAAPLICA")))/3);
    	    					}
    	    				}
    						
    						if(aplicaIVA.equalsIgnoreCase("S")){
    							if(seleccionAplica.equalsIgnoreCase("D")){
    								importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(subttcopagototalSiniestroIte)).toString());
    							}else{
    								importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(subttDescuentoSiniestroIte)).toString());
    							}
    							//logger.debug("####VALOR DE IVA RETENIDO ##### ---> "+ivaRetenido);
    							if(ivaRetenido.equalsIgnoreCase("S")){
    								importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroIte)    ).toString());
    							}else{
    								importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(0d)    ).toString());
    							}
    							
    						}else{
    							importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(subttcopagototalSiniestroIte)).toString());
    							importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(0d)    ).toString());
    						}
    						
    						importesWSSiniestroIte.put(IMPORTE_WS_IVA     , (new Double(ivaSiniestroIte)    ).toString());
    						//importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroIte)    ).toString());
    						importesWSSiniestroIte.put(IMPORTE_WS_ISR     , (new Double(subttISRSiniestroIte)    ).toString());
    						importesWSSiniestroIte.put(IMPORTE_WS_CEDULAR , (new Double(cedSiniestroIte)    ).toString());
    						logger.debug("mapa WS siniestro iterado: "+importesWSSiniestroIte);
    						logger.debug("<<WS del siniestro iterado");
    						//logger.debug("###### COBERTURA DIFERENTE DE HOSPITALIZACI�N Y AYUDA DE MATERNIDA ######");
    					}
    				}
    				facturaObj.put("siniestroPD", aseguradosxSiniestro);
    			}
    			//logger.debug("VALOR TOTAL DE FACTURAS --->"+factura);
    		}
    		else//REEMBOLSO
    		{
    			
    			List<Map<String,String>> siniestros = siniestrosManager.listaSiniestrosMsiniesTramite(ntramite,null,null);
    			//List<Map<String,String>> siniestros = siniestrosManager.listaSiniestrosMsiniesTramite(ntramite,null);
        		logger.debug(siniestros);
    			siniestro  = siniestros.get(0);
    			siniestros = null;
    			smap2      = siniestro;
    			smap3      = new HashMap<String,String>();
    			smap3.put("a","a");
    			smap.put("PAGODIRECTO","N");
    			conceptos  = siniestrosManager.P_GET_CONCEPTOS_FACTURA(
    					siniestro.get("CDUNIECO"),
    					siniestro.get("CDRAMO"),
    					siniestro.get("ESTADO"),
    					siniestro.get("NMPOLIZA"),
    					siniestro.get("NMSUPLEM"),
    					siniestro.get("NMSITUAC"),
    					siniestro.get("AAAPERTU"),
    					siniestro.get("STATUS"),
    					siniestro.get("NMSINIES"),
    					null);
    			logger.debug("VALOR DEL CONCEPTOS");
    			logger.debug(conceptos);
    			slist1     = facturasAux;
    			
    			//hospitalizacion
    			Map<String,String> hosp = new HashMap<String,String>();
    			lhosp.add(hosp);
    			hosp.put("PTIMPORT" , "0");
    			hosp.put("DESTO"    , "0");
    			hosp.put("IVA"      , "0");
    			//hospitalizacion
    			
    			//directo
    			Map<String,String>mpdir=new HashMap<String,String>();
    			mpdir.put("dummy","dummy");
    			lpdir.add(mpdir);
    			//directo
    			
    			Map<String,String>importesWSSiniestroUnico=new HashMap<String,String>();
    			importesWSSiniestroUnico.put("cdunieco" , siniestro.get("CDUNIECO"));
    			importesWSSiniestroUnico.put("cdramo"   , siniestro.get("CDRAMO"));
    			importesWSSiniestroUnico.put("estado"   , siniestro.get("ESTADO"));
    			importesWSSiniestroUnico.put("nmpoliza" , siniestro.get("NMPOLIZA"));
    			importesWSSiniestroUnico.put("nmsuplem" , siniestro.get("NMSUPLEM"));
    			importesWSSiniestroUnico.put("nmsituac" , siniestro.get("NMSITUAC"));
    			importesWSSiniestroUnico.put("aaapertu" , siniestro.get("AAAPERTU"));
    			importesWSSiniestroUnico.put("status"   , siniestro.get("STATUS"));
    			importesWSSiniestroUnico.put("nmsinies" , siniestro.get("NMSINIES"));
    			importesWSSiniestroUnico.put("ntramite" , ntramite);
    			listaImportesWS.add(importesWSSiniestroUnico);
    			double importeSiniestroUnico = 0d;
    			double ivaSiniestroUnico     = 0d;
    			double ivrSiniestroUnico     = 0d;
    			double isrSiniestroUnico     = 0d;
    			double cedularSiniestroUnico = 0d;
    			
    			
    			Map<String,String> facturaIte        = null;
    			//for(Map<String,String>facturaIte:facturasAux)
    			for(int i = 0; i < facturasAux.size(); i++)
    			{
    				facturaIte = facturasAux.get(i);
    				Map<String,Object>facturaObj=new HashMap<String,Object>();
            			facturaObj.putAll(facturaIte);
            			this.facturasxSiniestro.add(facturaObj);
    				
    				double penalizacionCambioZona = 0d;
    				double penalizacionCirculoHosp = 0d;
    				double totalPenalizacion = 0d;
    				double deducibleFacturaIte      = 0d;
    				double cantidadCopagoFacturaIte = 0d;
    				double copagoAplicadoFacturaIte = 0d;
    				
    				Map<String,String> calcxCobe = new HashMap<String,String>();
    				Map<String,String> penalizacion = new HashMap<String,String>();
    				
    				//reembolso
    				Map<String,String>mprem=new HashMap<String,String>(0);
    				mprem.put("TOTALNETO" , "0");
    				mprem.put("SUBTOTAL"  , "0");
    				lprem.add(mprem);
    				//reembolso
    				
    				String destopor = facturaIte.get("DESCPORC");
    				if(StringUtils.isBlank(destopor) || destopor  == null)
    				{
    					facturaObj.put("DESCPORC","0");
    				}
    				String destoimp = facturaIte.get("DESCNUME");
    				if(StringUtils.isBlank(destoimp)  || destoimp  == null)
    				{
    					facturaObj.put("DESCNUME","0");
    				}
    				//Asignaci�n de las variables principales
    				String cdunieco = siniestro.get("CDUNIECO");
    				String cdramo   = siniestro.get("CDRAMO");
    				String estado   = siniestro.get("ESTADO");
    				String nmpoliza = siniestro.get("NMPOLIZA");
    				String nmsuplem = siniestro.get("NMSUPLEM");
    				String nmsituac = siniestro.get("NMSITUAC");
    				String aaapertu = siniestro.get("AAAPERTU");
    				String status   = siniestro.get("STATUS");
    				String nmsinies = siniestro.get("NMSINIES");
    				String nfactura = facturaIte.get("NFACTURA");
    				
    				Map<String,String>copagoDeducibleFacturaIte =siniestrosManager.obtenerCopagoDeducible(
    						cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura);
    				
    				//1.- Obtenemos la informaci�n de Autorizaci�n de Factura
    				Map<String,String>autorizacionesFacturaIte = siniestrosManager.obtenerAutorizacionesFactura(
    						siniestro.get("CDUNIECO"),
    						siniestro.get("CDRAMO"),
    						siniestro.get("ESTADO"),
    						siniestro.get("NMPOLIZA"),
    						siniestro.get("NMSUPLEM"),
    						siniestro.get("NMSITUAC"),
    						siniestro.get("AAAPERTU"),
    						siniestro.get("STATUS"),
    						siniestro.get("NMSINIES"),
    						facturaIte.get("NFACTURA"));
    				facturaObj.put("AUTMEDIC",autorizacionesFacturaIte.get("AUTMEDIC"));
    				facturaObj.put("COMMENME",autorizacionesFacturaIte.get("COMMENME"));
    				facturaObj.put("AUTRECLA",autorizacionesFacturaIte.get("AUTRECLA"));
    				facturaObj.put("COMMENAR",autorizacionesFacturaIte.get("COMMENAR"));
    				
    				
    				//2.- Obtenemo los datos generales del siniestros
    				List<Map<String,String>> informacionGral = siniestrosManager.obtieneDatosGeneralesSiniestro(siniestro.get("CDUNIECO"), siniestro.get("CDRAMO"),
    						siniestro.get("ESTADO"), siniestro.get("NMPOLIZA"),siniestro.get("NMSITUAC"), siniestro.get("NMSUPLEM"), siniestro.get("STATUS"), siniestro.get("AAAPERTU"), siniestro.get("NMSINIES") , facturaIte.get("NTRAMITE"));
    				
    				//3.- Guardamos los valores en calculosPenalizaciones
    				Map<String,String>copagoDeducibleSiniestroIte =siniestrosManager.obtenerCopagoDeducible(siniestro.get("CDUNIECO"), siniestro.get("CDRAMO"),
    						siniestro.get("ESTADO"), siniestro.get("NMPOLIZA"), siniestro.get("NMSUPLEM"),siniestro.get("NMSITUAC"),
    						siniestro.get("AAAPERTU"),siniestro.get("STATUS"),siniestro.get("NMSINIES") ,facturaIte.get("NFACTURA"));
    						
    				String tipoFormatoCalculo = copagoDeducibleSiniestroIte.get("FORMATOCALCULO");
    				String calculosPenalizaciones = copagoDeducibleSiniestroIte.get("PENALIZACIONES");
    				calcxCobe.put("tipoFormatoCalculo",""+tipoFormatoCalculo);
    				calcxCobe.put("calculosPenalizaciones",""+calculosPenalizaciones);
    				datosCoberturaxCal.add(calcxCobe);
    				
    				penalizacion.put("causaSiniestro", informacionGral.get(0).get("CDCAUSA"));
    				//if(facturaIte.get("CDGARANT").equalsIgnoreCase("18HO")||facturaIte.get("CDGARANT").equalsIgnoreCase("18MA"))
    				if(tipoFormatoCalculo.equalsIgnoreCase("1"))
    				{
    					if(calculosPenalizaciones.equalsIgnoreCase("1")){
    						HashMap<String, Object> paramExclusion = new HashMap<String, Object>();
    						paramExclusion.put("pv_cdunieco_i",siniestro.get("CDUNIECO"));
    						paramExclusion.put("pv_estado_i",siniestro.get("ESTADO"));
    						paramExclusion.put("pv_cdramo_i",siniestro.get("CDRAMO"));
    						paramExclusion.put("pv_nmpoliza_i",siniestro.get("NMPOLIZA"));
    						paramExclusion.put("pv_nmsituac_i",siniestro.get("NMSITUAC"));
    						if(siniestro.get("CDRAMO").toString().equalsIgnoreCase("2")){
    							//--> SALUD VITAL
    							//1.- Verificamos si existe exclusi�n de penalizaci�n
        						existePenalizacion = siniestrosManager.validaExclusionPenalizacion(paramExclusion);
        						//2.- Obtenemos la penalizaci�n por cambio de Zona
        						penalizacionCambioZona = penalizacionCambioZona(existePenalizacion,informacionGral.get(0).get("CDCAUSA"),informacionGral.get(0).get("CIRHOSPI"),
        								informacionGral.get(0).get("DSZONAT"),facturaIte.get("CDPRESTA"),siniestro.get("CDRAMO"));
        						//3.- Obtenemos la penalizaci�n por circulo Hospitalario
        						List<ConsultaProveedorVO> medicos = siniestrosManager.getConsultaListaProveedorMedico(Rol.MEDICO.getCdrol(),facturaIte.get("CDPRESTA"));
        						penalizacionCirculoHosp = calcularPenalizacionCirculo(informacionGral.get(0).get("CIRHOSPI"), medicos.get(0).getCirculo(),informacionGral.get(0).get("CDCAUSA"));
    						}else{
    							// --> DIFERENTE DE SALUD VITAL
    							penalizacionCambioZona = 0d;
    							penalizacionCirculoHosp = 0d;
    						}
    						
    					}
    				}
    				penalizacion.put("penalizacionCambioZona",""+penalizacionCambioZona);
    				penalizacion.put("penalizacionCirculoHosp",""+penalizacionCirculoHosp);
    				
    				//3.- Obtenemos el total de penalizaci�n
    				String calcularTotalPenalizacion = calcularTotalPenalizacion(penalizacionCambioZona,penalizacionCirculoHosp,informacionGral.get(0).get("CDCAUSA"),
    																			 copagoDeducibleFacturaIte.get("COPAGO"),copagoDeducibleFacturaIte.get("TIPOCOPAGO"),
    																			 informacionGral.get(0).get("CDPROVEE"),siniestro.get("CDRAMO"), informacionGral.get(0).get("FEOCURRE"));

    				penalizacion.put("totalPenalizacionGlobal",""+calcularTotalPenalizacion);
    				String penalizacionT[] = calcularTotalPenalizacion.split("\\|");
    				penalizacion.put("copagoPorcentajes",penalizacionT[0].toString());
    				penalizacion.put("copagoPesos",penalizacionT[1].toString());
    				datosPenalizacion.add(penalizacion);
    				
    				String sDeducibleFacturaIte     = copagoDeducibleFacturaIte.get("DEDUCIBLE").replace(",","");
    				String sCopagoFacturaIte        = copagoDeducibleFacturaIte.get("COPAGO").replace(",","");
    				String tipoCopagoFacturaIte     = copagoDeducibleFacturaIte.get("TIPOCOPAGO");
    				
    				//OBTENEMOS LOS VALORES DE PENALIZACION Y COPAGO
    				if(StringUtils.isNotBlank(sDeducibleFacturaIte)
    						&&(!sDeducibleFacturaIte.equalsIgnoreCase("na"))
    						&&(!sDeducibleFacturaIte.equalsIgnoreCase("no"))
    						)
    				{
    					try
    					{
    						deducibleFacturaIte = Double.valueOf(sDeducibleFacturaIte);
    					}
    					catch(Exception ex)
    					{
    						logger.debug(""
    								+ "\n### ERROR ##################################################"
    								+ "\n### no es numero deducible: '"+sDeducibleFacturaIte+"' ###"
    								+ "\n############################################################"
    								);
    						deducibleFacturaIte = 0d;
    					}
    				}
    				if(StringUtils.isNotBlank(sCopagoFacturaIte)
    						&&(!sCopagoFacturaIte.equalsIgnoreCase("na"))
    						&&(!sCopagoFacturaIte.equalsIgnoreCase("no"))
    						)
    				{
    					try
    					{
    						cantidadCopagoFacturaIte = Double.valueOf(sCopagoFacturaIte);
    					}
    					catch(Exception ex)
    					{
    						logger.debug(""
    								+ "\n### ERROR ############################################"
    								+ "\n### no es numero copago: '"+sCopagoFacturaIte+"' ###"
    								+ "\n######################################################"
    								);
    						cantidadCopagoFacturaIte = 0d;
    					}
    				}
    				
    				slist2.add(copagoDeducibleFacturaIte);
    				
    				Map<String,String>datosProveedor =siniestrosManager.obtenerDatosProveedor(facturaIte.get("CDPRESTA")); 
    				slist3.add(datosProveedor);
    				double ivaprov=Double.parseDouble(datosProveedor.get("IVA"));
    				
    				List<Map<String,String>>listaConceptosFactura = new ArrayList<Map<String,String>>();
    				llist1.add(listaConceptosFactura);
    				for(Map<String,String>concepto : conceptos)
    				{
    					if(concepto.get("NFACTURA").equals(facturaIte.get("NFACTURA")))
    					{
    						listaConceptosFactura.add(concepto);
    							logger.debug(">>REEMBOLSO");
    							Map<String,String>row=new HashMap<String,String>();
    							row.putAll(concepto);
    							
    							double ptimport = Double.parseDouble(row.get("PTIMPORT"));
    							logger.debug("ptimport "+ptimport);
    							
    							double ajusteaplica = 0d;
    							if(StringUtils.isNotBlank(row.get("PTIMPORT_AJUSTADO")))
    							{
    								ajusteaplica = Double.parseDouble(row.get("PTIMPORT_AJUSTADO"));
    							}
    							logger.debug("ajusteaplica "+ajusteaplica);
    							
    							double subtotal = ptimport-ajusteaplica;
    							logger.debug("subtotal "+subtotal);
    							row.put("SUBTOTAL",subtotal+"");
    							
    							double gtotalneto = Double.parseDouble(mprem.get("TOTALNETO"));
    							double gsubtotal  = Double.parseDouble(mprem.get("SUBTOTAL"));
    							logger.debug("base totalneto " + gtotalneto);
    							logger.debug("base subtotal "  + gsubtotal);
    							gtotalneto += ptimport;
    							gsubtotal  += subtotal;
    							logger.debug("new totalneto " + gtotalneto);
    							logger.debug("new subtotal "  + gsubtotal);
    							
    							mprem.put("TOTALNETO" , gtotalneto + "");
    							mprem.put("SUBTOTAL"  , gsubtotal  + "");
    							
    							concepto.putAll(row);
    							logger.debug("<<REEMBOLSO");
    						//pago reembolso
    					}
    				}
    				
    				//Verificamos la informaci�n del deducible
    				//if(facturaIte.get("CDGARANT").equalsIgnoreCase("18HO")||facturaIte.get("CDGARANT").equalsIgnoreCase("18MA"))
    				if(tipoFormatoCalculo.equalsIgnoreCase("1")){
    					//verificamos la causa del siniestro
    					String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
    					if(causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
    						deducibleFacturaIte = 0d;
    					}
    				}
    				
    				logger.debug(">>Calculando total factura iterada para WS");
    				logger.debug("deducible :"+deducibleFacturaIte);
    				logger.debug("scopago: "+sCopagoFacturaIte);
    				logger.debug("tipocopago: "+tipoCopagoFacturaIte);
    				logger.debug("facturaIte.get(DESCPORC) -->"+facturaIte.get("DESCPORC"));
    				double totalFactura  = Double.valueOf(mprem.get("SUBTOTAL"));
    				double destoPorFac = 0d;
    				double destoImpFac= 0d;
    				if(!StringUtils.isBlank(facturaIte.get("DESCPORC")) || !(facturaIte.get("DESCPORC")  == null))
    				{
    					destoPorFac = Double.valueOf(facturaIte.get("DESCPORC"));
    				}
    				if(!StringUtils.isBlank(facturaIte.get("DESCNUME"))  || !(facturaIte.get("DESCNUME")  == null))
    				{
    					destoImpFac = Double.valueOf(facturaIte.get("DESCNUME"));
    				}
    				//double destoPorFac   = Double.valueOf(facturaIte.get("DESCPORC"));
    				//double destoImpFac   = Double.valueOf(facturaIte.get("DESCNUME"));
    				double destoAplicado = (totalFactura*(destoPorFac/100d)) + destoImpFac;
    				logger.debug("subtotal: "+totalFactura);
    				totalFactura -= destoAplicado;
    				logger.debug("subtotal desto: "+totalFactura);
    				totalFactura -= deducibleFacturaIte;
    				logger.debug("subtotal deducible: "+totalFactura);
    				
    				if(StringUtils.isNotBlank(tipoCopagoFacturaIte))
    				{
    					String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
    					//if(facturaIte.get("CDGARANT").equalsIgnoreCase("18HO")||facturaIte.get("CDGARANT").equalsIgnoreCase("18MA"))
    					if(tipoFormatoCalculo.equalsIgnoreCase("1"))
    					{
    						//verificamos la causa del siniestro
    						if(!causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
    							//Diferente de accidente
    							copagoAplicadoFacturaIte = Double.parseDouble(penalizacionT[1].toString()) + (totalFactura * ( Double.parseDouble(penalizacionT[0].toString()) / 100d ));
    						}else{
    							copagoAplicadoFacturaIte = 0d;
    						}
    					}else{
    						//COBERTURA DIFERENTE HOSPITALIZACI�N Y AYUDA DE MATERNIDAD
    							if(tipoCopagoFacturaIte.equalsIgnoreCase("$")){
    								copagoAplicadoFacturaIte = cantidadCopagoFacturaIte;
    							}
    							if(tipoCopagoFacturaIte.equalsIgnoreCase("%"))
    							{
    								copagoAplicadoFacturaIte = totalFactura * ( cantidadCopagoFacturaIte / 100d );
    							}
    					}
    				}
    				totalFactura -= copagoAplicadoFacturaIte;
    				logger.debug("total copago (final): "+totalFactura);
    				logger.debug("<<Calculando total factura iterada para WS");
    				
    				importeSiniestroUnico += totalFactura;
    			}
    			
    			logger.debug(">>WS del siniestro unico");
    			importesWSSiniestroUnico.put(IMPORTE_WS_IMPORTE , (new Double(importeSiniestroUnico)).toString());
    			importesWSSiniestroUnico.put(IMPORTE_WS_IVA     , (new Double(ivaSiniestroUnico)    ).toString());
    			importesWSSiniestroUnico.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroUnico)    ).toString());
    			importesWSSiniestroUnico.put(IMPORTE_WS_ISR     , (new Double(isrSiniestroUnico)    ).toString());
    			importesWSSiniestroUnico.put(IMPORTE_WS_CEDULAR , (new Double(cedularSiniestroUnico)).toString());
    			logger.debug("mapa WS siniestro unico: "+importesWSSiniestroUnico);
    			logger.debug("<<WS del siniestro unico");
    			
    		}
    		
    		if(conceptos!=null&&conceptos.size()>0)
    		{
    			logger.debug("conceptos[0]: "+conceptos);
    		}

    		//aqui verificamos toda la informaci�n del WS
    		logger.debug("###VALOR A IMPRIMIR#####");
    		logger.debug(listaImportesWS);
    		logger.debug(esPagoDirecto);
    		logger.debug(facturasxSiniestro);
    		
    		
        	try
        	{
        		for(Map<String,String> importe : listaImportesWS)
        		{
        			logger.debug("Valor de Importe--->"+importe);
        			
        			String cduniecoIte = importe.get("cdunieco");
        			String cdramoIte   = importe.get("cdramo");
        			String estadoIte   = importe.get("estado");
        			String nmpolizaIte = importe.get("nmpoliza");
        			String nmsuplemIte = importe.get("nmsuplem");
        			String nmsituacIte = importe.get("nmsituac");
        			String aaapertuIte = importe.get("aaapertu");
        			String statusIte   = importe.get("status");
        			String nmsiniesIte = importe.get("nmsinies");
        			String ntramiteIte = importe.get("ntramite");
        			String importeIte  = importe.get("importe");
        			String ivaIte      = importe.get("iva");
        			String ivrIte      = importe.get("ivr");
        			String isrIte      = importe.get("isr");
        			String cedularIte  = importe.get("cedular");
        			
        			
        			siniestrosManager.movTimpsini(
        					Constantes.INSERT_MODE
        					,cduniecoIte
        					,cdramoIte
        					,estadoIte
        					,nmpolizaIte
        					,nmsuplemIte
        					,nmsituacIte
        					,aaapertuIte
        					,statusIte
        					,nmsiniesIte
        					,ntramiteIte
        					,importeIte
        					,ivaIte
        					,ivrIte
        					,isrIte
        					,cedularIte
        					,false);
        		}
        		
        		for(Map<String, Object> totalFacturaIte : facturasxSiniestro)
        		{
        			logger.debug("VALOR DE LAS FACTURAS -->"+totalFacturaIte);
        			String ntramiteA     = (String) totalFacturaIte.get("NTRAMITE");
        			String nfacturaA     = (String) totalFacturaIte.get("NFACTURA");
        			String totalFacturaA = (String) totalFacturaIte.get("TOTALFACTURA"); 
        			siniestrosManager.guardarTotalProcedenteFactura(ntramiteA,nfacturaA,totalFacturaA);
        		}
        		success = true; 
        		mensaje = "Datos guardados";
        	}
        	catch(Exception ex)
        	{
        		logger.error("Error al guardaar calculos",ex);
        		success = false;
        		mensaje = ex.getMessage();
        	}
    		
    	}
    	catch(Exception ex)
    	{
    		logger.debug("error al cargar pantalla de calculo de siniestros",ex);
    	}
    	logger.debug(""
    			+ "\n######            			      ######"
    			+ "\n###### Generar calculoSiniestros ######"
    			+ "\n#######################################"
    			+ "\n#######################################"
    			);
    	return SUCCESS;
    }
    
    public String consultaCirculoHospitalario(){
		logger.debug(" **** Entrando a consultaCirculoHospitalario **");
		logger.debug(params);
		
		try {
			String fechaAutorizacion = params.get("feautori");
			logger.debug("fechaAutorizacion -->"+fechaAutorizacion);
			String feAutorizacion= fechaAutorizacion.substring(8,10)+"/"+fechaAutorizacion.substring(5,7)+"/"+fechaAutorizacion.substring(0,4);
			logger.debug("feAutorizacion -->"+feAutorizacion);
			
			datosInformacionAdicional = siniestrosManager.listaConsultaCirculoHospitalario(params.get("cdpresta"),params.get("cdramo"),feAutorizacion);
			logger.debug("VALOR DE RESPUESTA -->");
			logger.debug(datosInformacionAdicional);
		}catch( Exception e){
			logger.error("Error al obtener los datos de consultaCirculoHospitalario",e);
			return SUCCESS;
		}
	success = true;
	return SUCCESS;
    }
	/*public String subirCenso()
	{
		logger.info(""
				+ "\n########################"
				+ "\n###### subirCenso ######"
				+ "\n censo "+censo+""
				+ "\n censoFileName "+censoFileName+""
				+ "\n censoContentType "+censoContentType+""
				+ "\n smap1 "+imap
				);
		
		success = true;
		exito   = true;
		
		String ntramite=params.get("ntramite");
		if(StringUtils.isBlank(ntramite))
		{
			String timestamp = imap.get("timestamp");
			censo.renameTo(new File(this.getText("ruta.documentos.temporal")+"/censo_"+timestamp));
			logger.info("censo renamed to: "+this.getText("ruta.documentos.temporal")+"/censo_"+timestamp);
		}
		
		logger.info(""
				+ "\n###### subirCenso ######"
				+ "\n########################"
				);
		return SUCCESS;
	}*/
	
    public String getExistePenalizacion() {
		return existePenalizacion;
	}

	public void setExistePenalizacion(String existePenalizacion) {
		this.existePenalizacion = existePenalizacion;
	}

	public List<PolizaVigenteVO> getPolizaUnica() {
		return polizaUnica;
	}

	public void setPolizaUnica(List<PolizaVigenteVO> polizaUnica) {
		this.polizaUnica = polizaUnica;
	}

	public void setListaFacturas(List<ListaFacturasVO> listaFacturas) {
	this.listaFacturas = listaFacturas;
}

	public void setCatalogosManager(CatalogosManager catalogosManager) {
		this.catalogosManager = catalogosManager;
	}

	public List<HashMap<String, String>> getDatosTablas() {
		return datosTablas;
	}

	public void setDatosTablas(List<HashMap<String, String>> datosTablas) {
		this.datosTablas = datosTablas;
	}

    public String execute() throws Exception {
    	success = true;
    	return SUCCESS;
    }
    
    public List<GenericVO> getListaCPTICD() {
    	return listaCPTICD;
	}
	
    
	public List<ListaFacturasVO> getListaFacturas() {
		return listaFacturas;
	}

	public List<ConsultaTDETAUTSVO> getListaConsultaTablas() {
		return listaConsultaTablas;
	}

	public void setListaConsultaTablas(List<ConsultaTDETAUTSVO> listaConsultaTablas) {
		this.listaConsultaTablas = listaConsultaTablas;
	}

	public void setListaCPTICD(List<GenericVO> listaCPTICD) {
		this.listaCPTICD = listaCPTICD;
	}

	public List<GenericVO> getListaSubcobertura() {
		return listaSubcobertura;
	}

	public void setListaSubcobertura(List<GenericVO> listaSubcobertura) {
		this.listaSubcobertura = listaSubcobertura;
	}

	public List<DatosSiniestroVO> getListaDatosSiniestro() {
		return listaDatosSiniestro;
	}

	public void setListaDatosSiniestro(List<DatosSiniestroVO> listaDatosSiniestro) {
		this.listaDatosSiniestro = listaDatosSiniestro;
	}

	public List<CoberturaPolizaVO> getListaCoberturaPoliza() {
		return listaCoberturaPoliza;
	}

	public void setListaCoberturaPoliza(List<CoberturaPolizaVO> listaCoberturaPoliza) {
		this.listaCoberturaPoliza = listaCoberturaPoliza;
	}

	public List<GenericVO> getListaCausaSiniestro() {
		return listaCausaSiniestro;
	}

	public void setListaCausaSiniestro(List<GenericVO> listaCausaSiniestro) {
		this.listaCausaSiniestro = listaCausaSiniestro;
	}

	public List<PolizaVigenteVO> getListaPoliza() {
		return listaPoliza;
	}

	public void setListaPoliza(List<PolizaVigenteVO> listaPoliza) {
		this.listaPoliza = listaPoliza;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public HashMap<String, String> getParams() {
		return params;
	}
    public AutorizacionServicioVO getDatosAutorizacionEsp() {
		return datosAutorizacionEsp;
	}
    
    public List<GenericVO> getListaAsegurado() {
		return listaAsegurado;
	}
    
    public List<AutorizaServiciosVO> getListaAutorizacion() {
		return listaAutorizacion;
	}

    public Date getDate(String date)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return df.parse(date);
        } catch (ParseException ex) {
        	
        }
        return null;
    }
    
    /*	SETTER 	*/    
    public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setSiniestrosManager(SiniestrosManager siniestrosManager) {
		this.siniestrosManager = siniestrosManager;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}
	
	public void setDatosAutorizacionEsp(AutorizacionServicioVO datosAutorizacionEsp) {
		this.datosAutorizacionEsp = datosAutorizacionEsp;
	}

	public void setListaAsegurado(List<GenericVO> listaAsegurado) {
		this.listaAsegurado = listaAsegurado;
	}

	public void setListaAutorizacion(List<AutorizaServiciosVO> listaAutorizacion) {
		this.listaAutorizacion = listaAutorizacion;
	}
    
    public List<ConsultaPorcentajeVO> getListaPorcentaje() {
		return listaPorcentaje;
	}

	public void setListaPorcentaje(List<ConsultaPorcentajeVO> listaPorcentaje) {
		this.listaPorcentaje = listaPorcentaje;
	}

	public List<ConsultaManteniVO> getListaConsultaManteni() {
		return listaConsultaManteni;
	}

	public void setListaConsultaManteni(
			List<ConsultaManteniVO> listaConsultaManteni) {
		this.listaConsultaManteni = listaConsultaManteni;
	}

	public List<ConsultaTTAPVAATVO> getListaConsultaTTAPVAATV() {
		return listaConsultaTTAPVAATV;
	}

	public void setListaConsultaTTAPVAATV(
			List<ConsultaTTAPVAATVO> listaConsultaTTAPVAATV) {
		this.listaConsultaTTAPVAATV = listaConsultaTTAPVAATV;
	}
	
	public AutorizacionServicioVO getNumeroAutorizacion() {
		return numeroAutorizacion;
	}

	public void setNumeroAutorizacion(AutorizacionServicioVO numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	public List<HashMap<String, String>> getLoadList() {
		return loadList;
	}

	public void setLoadList(List<HashMap<String, String>> loadList) {
		this.loadList = loadList;
	}

	public void setSaveList(List<HashMap<String, String>> saveList) {
		this.saveList = saveList;
	}

	public List<GenericVO> getListaPlazas() {
		return listaPlazas;
	}

	public void setListaPlazas(List<GenericVO> listaPlazas) {
		this.listaPlazas = listaPlazas;
	}

	public HashMap<String, Object> getParamsO() {
		return paramsO;
	}

	public void setParamsO(HashMap<String, Object> paramsO) {
		this.paramsO = paramsO;
	}

	public void setKernelManagerSustituto(
			KernelManagerSustituto kernelManagerSustituto) {
		this.kernelManagerSustituto = kernelManagerSustituto;
	}

	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Map<String, Item> getImap() {
		return imap;
	}

	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}
	public String getDiasMaximos() {
		return diasMaximos;
	}

	public void setDiasMaximos(String diasMaximos) {
		this.diasMaximos = diasMaximos;
	}

	public Map<String, String> getSmap() {
		return smap;
	}

	public void setSmap(Map<String, String> smap) {
		this.smap = smap;
	}
	
	public String getSmapJson()
	{
		String r=null;
		try
		{
			r=JSONUtil.serialize(smap);
		}
		catch (JSONException ex)
		{
			logger.error("error al convertir smap a json",ex);
		}
		return r;
	}

	public Map<String, String> getSmap2() {
		return smap2;
	}

	public void setSmap2(Map<String, String> smap2) {
		this.smap2 = smap2;
	}
	
	public String getSmap2Json()
	{
		String r=null;
		try
		{
			r=JSONUtil.serialize(smap2);
		}
		catch (JSONException ex)
		{
			logger.error("error al convertir smap2 a json",ex);
		}
		return r;
	}

	public Map<String, String> getSmap3() {
		return smap3;
	}

	public void setSmap3(Map<String, String> smap3) {
		this.smap3 = smap3;
	}
	
	public String getSmap3Json()
	{
		String r=null;
		try
		{
			r=JSONUtil.serialize(smap3);
		}
		catch (JSONException ex)
		{
			logger.error("error al convertir smap3 a json",ex);
		}
		return r;
	}

	public List<Map<String, String>> getSlist2() {
		return slist2;
	}
	
	public String getSlist1Json() {
		String r=null;
		try
		{
			r=JSONUtil.serialize(slist1);
		}
		catch (JSONException ex)
		{
			logger.error("error al convertir slist1 a json",ex);
		}
		return r;
	}
	
	public String getSlist2Json() {
		String r=null;
		try
		{
			r=JSONUtil.serialize(slist2);
		}
		catch (JSONException ex)
		{
			logger.error("error al convertir slist2 a json",ex);
		}
		return r;
	}
	
	public String getLlist1Json() {
		String r=null;
		try
		{
			r=JSONUtil.serialize(llist1);
		}
		catch (JSONException ex)
		{
			logger.error("error al convertir llist1 a json",ex);
		}
		return r;
	}
	
	public String getSlist3Json() {
		String r=null;
		try
		{
			r=JSONUtil.serialize(slist3);
		}
		catch (JSONException ex)
		{
			logger.error("error al convertir slist3 a json",ex);
		}
		return r;
	}
	
	public String getLhospJson() {
		String r=null;
		try
		{
			r=JSONUtil.serialize(lhosp);
		}
		catch (JSONException ex)
		{
			logger.error("error al convertir lhosp a json",ex);
		}
		return r;
	}
	
	public String getLpdirJson() {
		String r=null;
		try
		{
			r=JSONUtil.serialize(lpdir);
		}
		catch (JSONException ex)
		{
			logger.error("error al convertir lpdir a json",ex);
		}
		return r;
	}
	
	public String getLpremJson() {
		String r=null;
		try
		{
			r=JSONUtil.serialize(lprem);
		}
		catch (JSONException ex)
		{
			logger.error("error al convertir lprem a json",ex);
		}
		return r;
	}

	public void setSlist2(List<Map<String, String>> slist2) {
		this.slist2 = slist2;
	}

	public List<List<Map<String, String>>> getLlist1() {
		return llist1;
	}

	public void setLlist1(List<List<Map<String, String>>> llist1) {
		this.llist1 = llist1;
	}

	public List<Map<String, String>> getLhosp() {
		return lhosp;
	}

	public void setLhosp(List<Map<String, String>> lhosp) {
		this.lhosp = lhosp;
	}

	public List<Map<String, String>> getSlist3() {
		return slist3;
	}

	public void setSlist3(List<Map<String, String>> slist3) {
		this.slist3 = slist3;
	}

	public List<Map<String, String>> getLpdir() {
		return lpdir;
	}

	public void setLpdir(List<Map<String, String>> lpdir) {
		this.lpdir = lpdir;
	}

	public List<Map<String, String>> getLprem() {
		return lprem;
	}

	public void setLprem(List<Map<String, String>> lprem) {
		this.lprem = lprem;
	}
	
	public String getPorcentajePenalizacion() {
		return porcentajePenalizacion;
	}

	public void setPorcentajePenalizacion(String porcentajePenalizacion) {
		this.porcentajePenalizacion = porcentajePenalizacion;
	}

	
	public String getAutorizarProceso() {
		return autorizarProceso;
	}

	public void setAutorizarProceso(String autorizarProceso) {
		this.autorizarProceso = autorizarProceso;
	}

	public void setParamsJson(HashMap<String, String> params2) {
		this.params = params2;
	}
	
	public String getParamsJson() {
		try {
			return JSONUtil.serialize(params);
		} catch (Exception e) {
			logger.error("Error al generar JSON de params",e);
			return null;
		}
	}

	public void setIce2sigsService(Ice2sigsService ice2sigsService) {
		this.ice2sigsService = ice2sigsService;
	}

	public String getMontoMaximo() {
		return montoMaximo;
	}

	public void setMontoMaximo(String montoMaximo) {
		this.montoMaximo = montoMaximo;
	}


	public List<AltaTramiteVO> getListaAltaTramite() {
		return listaAltaTramite;
	}


	public void setListaAltaTramite(List<AltaTramiteVO> listaAltaTramite) {
		this.listaAltaTramite = listaAltaTramite;
	}


	public List<MesaControlVO> getListaMesaControl() {
		return listaMesaControl;
	}


	public void setListaMesaControl(List<MesaControlVO> listaMesaControl) {
		this.listaMesaControl = listaMesaControl;
	}

	public List<Map<String, String>> getListaImportesWS() {
		return listaImportesWS;
	}
	
	public String getListaImportesWebServiceJson() {
		try {
			return JSONUtil.serialize(listaImportesWS);
		} catch (Exception e) {
			logger.error("Error al generar JSON de listaImportesWS",e);
			return null;
		}
	}

	public void setListaImportesWS(List<Map<String, String>> listaImportesWS) {
		this.listaImportesWS = listaImportesWS;
	}

	public String getIMPORTE_WS_IMPORTE() {
		return IMPORTE_WS_IMPORTE;
	}

	public String getIMPORTE_WS_IVA() {
		return IMPORTE_WS_IVA;
	}

	public String getIMPORTE_WS_IVR() {
		return IMPORTE_WS_IVR;
	}

	public String getIMPORTE_WS_ISR() {
		return IMPORTE_WS_ISR;
	}


	public String getExisteDocAutServicio() {
		return existeDocAutServicio;
	}


	public void setExisteDocAutServicio(String existeDocAutServicio) {
		this.existeDocAutServicio = existeDocAutServicio;
	}


	public List<Map<String, String>> getFacturasxTramite() {
		return facturasxTramite;
	}


	public void setFacturasxTramite(List<Map<String, String>> facturasxTramite) {
		this.facturasxTramite = facturasxTramite;
	}


	public List<Map<String, String>> getDatosPenalizacion() {
		return datosPenalizacion;
	}
	
	
	public List<Map<String, String>> getDatosCoberturaxCal() {
		return datosCoberturaxCal;
	}


	public void setDatosPenalizacion(List<Map<String, String>> datosPenalizacion) {
		this.datosPenalizacion = datosPenalizacion;
	}
	
	public void setDatosCoberturaxCal(List<Map<String, String>> datosCoberturaxCal) {
		this.datosCoberturaxCal = datosCoberturaxCal;
	}
	
	
	public String getDatosPenalizacionJson() {
		String r=null;
		try
		{
			r=JSONUtil.serialize(datosPenalizacion);
		}
		catch (JSONException ex)
		{
			logger.error("error al convertir datosPenalizacion a json",ex);
		}
		return r;
	}
	
	public String getDatosCoberturaxCalJson() {
		String r=null;
		try
		{
			r=JSONUtil.serialize(datosCoberturaxCal);
		}
		catch (JSONException ex)
		{
			logger.error("error al convertir datosCoberturaxCal a json",ex);
		}
		return r;
	}


	public String getMontoArancel() {
		return montoArancel;
	}

	public String getMesesTiempoEspera() {
		return mesesTiempoEspera;
	}
	
	public void setMesesTiempoEspera(String mesesTiempoEspera) {
		this.mesesTiempoEspera = mesesTiempoEspera;
	}

	public void setMontoArancel(String montoArancel) {
		this.montoArancel = montoArancel;
	}

	public String getRequiereAutServ() {
		return requiereAutServ;
	}


	public void setRequiereAutServ(String requiereAutServ) {
		this.requiereAutServ = requiereAutServ;
	}

	public List<GenericVO> getListadoRamosSalud() {
		return listadoRamosSalud;
	}

	public void setListadoRamosSalud(List<GenericVO> listadoRamosSalud) {
		this.listadoRamosSalud = listadoRamosSalud;
	}


	public Map<String, String> getMap1() {
		return map1;
	}


	public List<Map<String, String>> getDatosInformacionAdicional() {
		return datosInformacionAdicional;
	}


	public void setDatosInformacionAdicional(
			List<Map<String, String>> datosInformacionAdicional) {
		this.datosInformacionAdicional = datosInformacionAdicional;
	}


	public void setMap1(Map<String, String> map1) {
		this.map1 = map1;
	}

	public String getFactPagada() {
		return factPagada;
	}


	public void setFactPagada(String factPagada) {
		this.factPagada = factPagada;
	}

	public String getMesMaximoMaternidad() {
		return mesMaximoMaternidad;
	}

	public void setMesMaximoMaternidad(String mesMaximoMaternidad) {
		this.mesMaximoMaternidad = mesMaximoMaternidad;
	}


	public List<Map<String, String>> getDatosValidacion() {
		return datosValidacion;
	}


	public void setDatosValidacion(List<Map<String, String>> datosValidacion) {
		this.datosValidacion = datosValidacion;
	}


	public String getValidaCdTipsitTramite() {
		return validaCdTipsitTramite;
	}


	public void setValidaCdTipsitTramite(String validaCdTipsitTramite) {
		this.validaCdTipsitTramite = validaCdTipsitTramite;
	}


	public List<Map<String, Object>> getFacturasxSiniestro() {
		return facturasxSiniestro;
	}


	public void setFacturasxSiniestro(List<Map<String, Object>> facturasxSiniestro) {
		this.facturasxSiniestro = facturasxSiniestro;
	}
	
	public String getFacturasxSiniestroJson() {
		String r=null;
		try
		{
			r=JSONUtil.serialize(facturasxSiniestro);
		}
		catch (JSONException ex)
		{
			logger.error("error al convertir slist1 a json",ex);
		}
		return r;
	}
}