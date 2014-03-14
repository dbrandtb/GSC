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
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.EstatusTramite;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.Rango;
import mx.com.gseguros.portal.general.util.Rol;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.general.util.TipoPago;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.siniestros.model.AltaTramiteVO;
import mx.com.gseguros.portal.siniestros.model.AutorizaServiciosVO;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaManteniVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaPorcentajeVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTDETAUTSVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTTAPVAATVO;
import mx.com.gseguros.portal.siniestros.model.DatosSiniestroVO;
import mx.com.gseguros.portal.siniestros.model.ListaFacturasVO;
import mx.com.gseguros.portal.siniestros.model.MesaControlVO;
import mx.com.gseguros.portal.siniestros.model.PolizaVigenteVO;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.opensymphony.xwork2.ActionContext;

public class SiniestrosAction extends PrincipalCoreAction{
    
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
    private String existePenalizacion;
    private String autorizarProceso;
    private String porcentajePenalizacion;
    private List<HashMap<String, String>> loadList;
    private List<HashMap<String, String>> saveList;
    private List<GenericVO> listaPlazas;
    private List<ListaFacturasVO> listaFacturas;
    
    private Item                     item;
    private Map<String,Item>         imap;
    private String                   mensaje;
    private List<Map<String,String>> slist1;
    private List<Map<String,String>> slist2;
    private List<Map<String,String>> slist3;
    private Map<String,String>       smap;
    private Map<String,String>       smap2;
    private Map<String,String>       smap3;
    private List<Map<String,String>> lhosp;
    private List<Map<String,String>> lpdir;
    private List<Map<String,String>> lprem;
	
    private List<List<Map<String,String>>> llist1;
    
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
		logger.debug("PARAMETROS INICIALES " + params);
		
		//Obtenemos el Rol a ocupar
		UserVO usuario  = (UserVO)session.get("USUARIO");
    	String cdrol    = usuario.getRolActivo().getObjeto().getValue();
    	
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
		logger.debug("PARAMETROS FINALES " + params);
		
		
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
			logger.debug("VALOR DE LOS PARAMETROS DE ENTRADA AL MOMENTO DE GUARDARLO");
			logger.debug(params);
			
			try {
					this.session=ActionContext.getContext().getSession();
			        UserVO usuario=(UserVO) session.get("USUARIO");
			        logger.debug("VALOR DEL ROL");
					logger.debug(usuario.getRolActivo().getObjeto().getValue());
					
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
					paramsR.put("pv_copagofi_i",params.get("copagoTotal")); // Copago Final
					paramsR.put("pv_porpenal_i","0"); // penalizacion Final
					paramsR.put("pv_cdicd_i",params.get("cdicd"));
					paramsR.put("pv_cdcausa_i",params.get("cdcausa"));
					paramsR.put("pv_aaapertu_i",params.get("aaapertu"));
					paramsR.put("pv_status_i",params.get("status"));
					paramsR.put("pv_dstratam_i",params.get("dstratam"));
					paramsR.put("pv_dsobserv_i",params.get("dsobserv"));
					paramsR.put("pv_dsnotas_i",params.get("dsnotas"));
					paramsR.put("pv_fesistem_i",params.get("fesistem")); 
					paramsR.put("pv_cduser_i",usuario.getUser());
					
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
							
							logger.debug("VALOR DEL STATUS");
							logger.debug(params.get("status"));
							if(params.get("status").trim().equalsIgnoreCase("2")){
								paramsMCAut.put("pv_status_i",EstatusTramite.CONFIRMADO.getCodigo());//  con
								//generarAutoriServicio();
								
							}else{
								if(usuario.getRolActivo().getObjeto().getValue().trim().equalsIgnoreCase("COORDMEDMULTI"))
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
							WrapperResultados res = kernelManagerSustituto.PMovMesacontrol(paramsMCAut);
							
							logger.debug("VALOR DE LA MESA DE CONTROL");
							logger.debug(res.getItemMap());
							logger.debug(res.getItemMap().get("ntramite"));
							
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
								//mca.setParamsO((HashMap<String, Object>) paramsO);
								logger.debug("VALORES A ENVIAR PARA LA GENERACION DEL PDF");
								logger.debug(paramsO);
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
				    		siniestrosManager.actualizaOTValorMesaControl(otvalor);
				    		
				    		// Tenemos que actualizar el status para el guardado
				    		if(params.get("status").trim().equalsIgnoreCase("2")){
								//paramsMCAut.put("pv_status_i",EstatusTramite.CONFIRMADO.getCodigo());
				    			MesaControlAction mca = new MesaControlAction();
				    			mca.setKernelManager(kernelManagerSustituto);
				    			mca.setSession(session);
				    			Map<String,String>smap1=new HashMap<String,String>();
				    			smap1.put("ntramite" , params.get("idNumtramiteInicial"));
				    			smap1.put("status"   , EstatusTramite.CONFIRMADO.getCodigo());
				    			smap1.put("cdmotivo" , null);
				    			smap1.put("comments" , null);
				    			mca.setSmap1(smap1);
				    			mca.actualizarStatusTramite();
				    			
				    			Map<String,Object>paramsO =new HashMap<String,Object>();
								paramsO.put("pv_ntramite_i" , params.get("idNumtramiteInicial"));
								paramsO.put("pv_cdunieco_i" , params.get("cdunieco"));
								paramsO.put("pv_cdramo_i" , params.get("cdramo"));
								paramsO.put("pv_estado_i" , params.get("estado"));
								paramsO.put("pv_nmpoliza_i" , params.get("nmpoliza"));
								paramsO.put("pv_nmAutSer_i" , lista.get(0).getNmautser());
								paramsO.put("pv_cdperson_i" , params.get("cdperson"));
								paramsO.put("pv_nmsuplem_i" , params.get("nmsuplem"));
								logger.debug("VALORES A ENVIAR PARA LA GENERACION DEL PDF");
								logger.debug(paramsO);
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
			try {
				
					
					
				
					logger.debug("VALOR DE ENTRADA");
					logger.debug(params);
					
					this.session=ActionContext.getContext().getSession();
			        UserVO usuario=(UserVO) session.get("USUARIO");
		            
					HashMap<String, Object> parMesCon = new HashMap<String, Object>();
					parMesCon.put("pv_cdunieco_i",params.get("cdunieco"));
					parMesCon.put("pv_cdramo_i",params.get("cdramo"));
					parMesCon.put("pv_estado_i",params.get("estado"));
					parMesCon.put("pv_nmpoliza_i",params.get("nmpoliza"));
					parMesCon.put("pv_nmsuplem_i",params.get("nmsuplem"));
					parMesCon.put("pv_cdsucadm_i",params.get("cmbOficEmisora"));
					parMesCon.put("pv_cdsucdoc_i",params.get("cmbOficReceptora"));
					parMesCon.put("pv_cdtiptra_i","16");
					parMesCon.put("pv_ferecepc_i",getDate(params.get("dtFechaRecepcion")));
					parMesCon.put("pv_cdagente_i",null);
					parMesCon.put("pv_referencia_i",null);
					parMesCon.put("pv_nombre_i",params.get("idnombreAsegurado")); // Se guardara la informaci�n del Asegurado
					parMesCon.put("pv_festatus_i",getDate(params.get("dtFechaFactura")));
					parMesCon.put("pv_status_i","2");
					parMesCon.put("pv_comments_i",null);
					parMesCon.put("pv_nmsolici_i",params.get("nmsolici"));
					parMesCon.put("pv_cdtipsit_i",params.get("cdtipsit"));
					parMesCon.put("pv_otvalor02",params.get("cmbTipoPago"));							// TIPO DE PAGO
					parMesCon.put("pv_otvalor03",params.get("txtImporte"));								// IMPORTE
					parMesCon.put("pv_otvalor04",params.get("cmbBeneficiario"));
					parMesCon.put("pv_otvalor05",usuario.getUser());
					parMesCon.put("pv_otvalor06",params.get("dtFechaFactura"));							// FECHA FACTURA
					parMesCon.put("pv_otvalor07",params.get("cmbTipoAtencion"));						// TIPO DE ANTENCION
					parMesCon.put("pv_otvalor08",params.get("txtNoFactura"));							// NO. DE FACTURA
					parMesCon.put("pv_otvalor09",params.get("cmbAseguradoAfectado"));					// CDPERSON
					parMesCon.put("pv_otvalor10",params.get("dtFechaOcurrencia"));						// FECHA OCURRENCIA
					parMesCon.put("pv_otvalor11",params.get("cmbProveedor"));
					parMesCon.put("pv_otvalor15",params.get("idnombreBeneficiarioProv"));
					if(params.get("cmbProveedor").toString().length() > 0){
						parMesCon.put("pv_otvalor13",Rol.CLINICA.getCdrol());
					}
					
					if(params.get("idNumTramite").toString().length() <= 0){
						WrapperResultados res = kernelManagerSustituto.PMovMesacontrol(parMesCon);
					if(res.getItemMap() == null)
					{
						logger.error("Sin mensaje respuesta de nmtramite!!");
					}
					else{
						msgResult = (String) res.getItemMap().get("ntramite");
						if(params.get("cmbTipoPago").trim().equalsIgnoreCase("1"))
						{
							for(int i=0;i<datosTablas.size();i++)
						    {
								HashMap<String, Object> paramsTworkSin = new HashMap<String, Object>();
								paramsTworkSin.put("pv_nmtramite_i",msgResult);
								paramsTworkSin.put("pv_cdunieco_i",datosTablas.get(i).get("unieco"));
								paramsTworkSin.put("pv_cdramo_i",datosTablas.get(i).get("ramo"));
								paramsTworkSin.put("pv_estado_i",datosTablas.get(i).get("estado"));
								paramsTworkSin.put("pv_nmpoliza_i",datosTablas.get(i).get("polizaAfectada"));
								paramsTworkSin.put("pv_nmsolici_i",datosTablas.get(i).get("nmsolici"));
								paramsTworkSin.put("pv_nmsuplem_i",datosTablas.get(i).get("nmsuplem"));
								paramsTworkSin.put("pv_nmsituac_i",datosTablas.get(i).get("nmsituac"));
								paramsTworkSin.put("pv_cdtipsit_i",datosTablas.get(i).get("cdtipsit"));
								paramsTworkSin.put("pv_cdperson_i",datosTablas.get(i).get("cdperson"));
								paramsTworkSin.put("pv_feocurre_i",datosTablas.get(i).get("fechaOcurrencia"));
								paramsTworkSin.put("pv_nmautser_i",null);
						        siniestrosManager.guardaListaTworkSin(paramsTworkSin);
						    }
					        
					        siniestrosManager.guardaListaFacMesaControl(
					        		msgResult, 
					        		params.get("txtNoFactura"), 
					        		params.get("dtFechaFactura"), 
					        		params.get("cmbTipoAtencion"), 
					        		params.get("cmbProveedor"), 
					        		params.get("txtImporte"), 
					        		null, 
					        		null,
					        		null,
					        		null
					        		);
							
						}else{
							
							for(int i=0;i<datosTablas.size();i++)
						    {
						        siniestrosManager.guardaListaFacMesaControl(
						        		msgResult, 
						        		datosTablas.get(i).get("nfactura"), 
						        		datosTablas.get(i).get("ffactura"), 
						        		datosTablas.get(i).get("cdtipser"), 
						        		datosTablas.get(i).get("cdpresta"), 
						        		datosTablas.get(i).get("ptimport"), 
						        		null,
						        		null,
						        		null, 
						        		null
						        		);
						    }
							HashMap<String, Object> paramsTworkSinPagRem = new HashMap<String, Object>();
					        paramsTworkSinPagRem.put("pv_nmtramite_i",msgResult);
					        paramsTworkSinPagRem.put("pv_cdunieco_i",params.get("cdunieco"));
					        paramsTworkSinPagRem.put("pv_cdramo_i",params.get("cdramo"));
					        paramsTworkSinPagRem.put("pv_estado_i",params.get("estado"));
					        paramsTworkSinPagRem.put("pv_nmpoliza_i",params.get("nmpoliza"));
					        paramsTworkSinPagRem.put("pv_nmsolici_i",params.get("nmsolici"));
					        paramsTworkSinPagRem.put("pv_nmsuplem_i",params.get("nmsuplem"));
					        paramsTworkSinPagRem.put("pv_nmsituac_i",params.get("nmsituac"));
					        paramsTworkSinPagRem.put("pv_cdtipsit_i",params.get("cdtipsit"));
					        paramsTworkSinPagRem.put("pv_cdperson_i",params.get("cmbAseguradoAfectado"));
					        paramsTworkSinPagRem.put("pv_feocurre_i",params.get("dtFechaOcurrencia"));
					        paramsTworkSinPagRem.put("pv_nmautser_i",null);
					        siniestrosManager.guardaListaTworkSin(paramsTworkSinPagRem);
						}
					}
				}
			}catch( Exception e){
				logger.error("Error en el guardado de alta de tr�mite ",e);
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
			logger.debug(lista);
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
			logger.debug(lista);
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
   	try {
				List<PolizaVigenteVO> lista = siniestrosManager.getConsultaListaPoliza(params.get("cdperson"));
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
			logger.debug(lista);
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
   
   
   public String generarSiniestroAutServicio(){
		logger.debug(" **** Entrando a generar el siniestro  por medio de la autorizacion del servicio ***");
		try {
				siniestrosManager.getAltaSiniestroAutServicio(params.get("nmautser"));
		}catch( Exception e){
			logger.error("Error al obtener los datos de Autorizaci�n de Servicio en Especifico",e);
			return SUCCESS;
		}
	   success = true;
	   return SUCCESS;
   }
   
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
				logger.debug("RESPUESTA DE LA CONSULTA");
				logger.debug(lista);
				
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
		   loadList = new ArrayList<HashMap<String,String>>();;//siniestrosManager.loadListaIncisosRechazos(params);
	   }catch( Exception e){
		   logger.error("Error en loadListaRechazos",e);
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
		   String cdrol    = usuario.getRolActivo().getObjeto().getValue();
		   String tipoPago = (String) paramsO.get("tipopago");
		   
		   String nombreRdf = "";
		   
		   if(RolSistema.MEDICO.getCdsisrol().equals(cdrol) || RolSistema.COORDINADOR_MEDICO.getCdsisrol().equals(cdrol) 
				   || RolSistema.COORDINADOR_MEDICO_MULTIREGIONAL.getCdsisrol().equals(cdrol) || RolSistema.GERENTE_MEDICO_MULTIREGIONAL.getCdsisrol().equals(cdrol)){
			   nombreRdf = getText("rdf.siniestro.cartarechazo.medico.nombre");
		   }else {
				if(TipoPago.PAGO_DIRECTO.getCodigo().equals(tipoPago)){
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
           paramsO.put("pv_cdtiptra_i"   , TipoTramite.SINIESTRO.getCodigo());
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
	   				msgResult = "No se puede Generar el Contra Recibo ya que en Revision de Documentos no se han marcado como entregados todos los documentos obligatorios.";
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
                               + "&report="+getText("rdf.siniestro.autorizacion.servicio.nombre")
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
		   logger.debug("solicitarPago Siniestros");
		   siniestrosManager.solicitarPago(params);
	   }catch( Exception e){
		   logger.error("Error en solicitarPago",e);
		   success =  false;
		   return SUCCESS;
	   }
	   success = true;
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
	   		logger.debug("VALORES A OCUPAR");
	   		diasMaximos= catalogosManager.obtieneCantidadMaxima(params.get("cdramo"), params.get("cdtipsit"), TipoTramite.SINIESTRO, Rango.DIAS);
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
	   		montoMaximo = catalogosManager.obtieneCantidadMaxima(params.get("cdramo"), params.get("cdtipsit"), TipoTramite.SINIESTRO, Rango.PESOS);
	   	}catch( Exception e){
	   		logger.error("Error al consultar la Lista de los asegurados ",e);
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
	   		porcentajePenalizacion = siniestrosManager.validaPorcentajePenalizacion(params.get("zonaContratada"), params.get("zonaAtencion"));
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
	    	String cdrol    = usuario.getRolActivo().getObjeto().getValue();
	    	String pantalla = "SELECCION_COBERTURA";
	    	String seccion  = "FORMULARIO";
	    	
	    	List<ComponenteVO> componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
	    	
	    	gc.generaComponentes(componentes, true, false, true, false, false, false);
	    	
	    	item = gc.getItems();
	    	
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
	    		
	    		siniestrosManager.guardaListaFacMesaControl(ntramite, nfactura, fefactura, cdtipser, cdpresta, ptimport, cdgarant, cdconval, descporc, descnume);
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
    		logger.debug("factura encontrada: "+factura);
    		params.put("OTVALOR12",factura.get("CDGARANT"));
    		params.put("OTVALOR14",factura.get("CDCONVAL"));
    		
    		params.put("DESCPORC",factura.get("DESCPORC"));
    		params.put("DESCNUME",factura.get("DESCNUME"));
    		
    		UserVO usuario  = (UserVO)session.get("USUARIO");
	    	String cdrol    = usuario.getRolActivo().getObjeto().getValue();
	    	String pantalla = "AFILIADOS_AGRUPADOS";
	    	String seccion  = "FORMULARIO";
	    	
	    	List<ComponenteVO> componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
	    	
	    	gc.generaComponentes(componentes, true, false, true, false, false, false);
	    	
	    	imap = new HashMap<String,Item>();
	    	imap.put("itemsForm",gc.getItems());
	    	
	    	slist1 = siniestrosManager.listaSiniestrosTramite(ntramite);
	    	
	    	seccion = "COLUMNAS";
	    	
	    	componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	for(ComponenteVO com:componentes)
	    	{
	    		com.setWidth(100);
	    	}
	    	
	    	gc.generaComponentes(componentes, true, false, false, true, false, false);
	    	
	    	imap.put("columnas",gc.getColumns());
	    	
	    	seccion = "FORM_EDICION";
	    	
	    	componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	gc.generaComponentes(componentes, true, false, true, false, false, false);
	    	
	    	imap.put("itemsEdicion",gc.getItems());
	    	
	    	pantalla = "RECHAZO_SINIESTRO";
	    	seccion  = "FORMULARIO";
	    	
	    	componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	gc.generaComponentes(componentes, true, false, true, false, false, false);
	    	
	    	imap.put("itemsCancelar",gc.getItems());
	    	
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
    				descnume
    				);
    		
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

    		//CUANDO SE PIDE EL NUMERO DE AUTORIZACION DE SERVICIO EN PANTALLA
    		//SE EJECUTAN LOS SIGUIENTES PL:
    		
    		//INSERTA EL NUMERO DE AUTORIZACION EN TWORKSIN
    		siniestrosManager.actualizarAutorizacionTworksin(ntramite,nmpoliza,cdperson,nmautser);
    		
    		//CREA UN MSINIEST A PARTIR DE TWORKSIN
    		siniestrosManager.getAltaSiniestroAutServicio(nmautser);
    		
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
    		String cdrol   = usuario.getRolActivo().getObjeto().getValue();
    		
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
    				throw new Exception("Error al cancelar el trámite");
    			}
    			
    			String nombreReporte = null;
    			String nombreArchivo = null;
    			if(rolMedico)
    			{
    				nombreReporte = getText("rdf.siniestro.rechazo.medico.nombre");
    				nombreArchivo = getText("pdf.siniestro.rechazo.medico.nombre");
    			}
    			else//cancelacion por area de reclamaciones
    			{
    				boolean esReembolso = tipoPago.equalsIgnoreCase(TipoPago.REEMBOLSO.getCodigo());
    				if(esReembolso)
    				{
    					nombreReporte = getText("rdf.siniestro.rechazo.reemb.nombre");
        				nombreArchivo = getText("pdf.siniestro.rechazo.reemb.nombre");
    				}
    				else
    				{
    					nombreReporte = getText("rdf.siniestro.rechazo.pdir.nombre");
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
    	try
    	{
    		UserVO usuario = (UserVO)session.get("USUARIO");
    		
    		String cdunieco  = params.get("cdunieco");
    		String cdramo    = params.get("cdramo");
    		String estado    = params.get("estado");
    		String nmpoliza  = params.get("nmpoliza");
    		String nmsuplem  = params.get("nmsuplem");
    		String nmsituac  = params.get("nmsituac");
    		String aaapertu  = params.get("aaapertu");
    		String status    = params.get("status");
    		String nmsinies  = params.get("nmsinies");
    		String nfactura  = params.get("nfactura");
    		String cdgarant  = params.get("cdgarant");
    		String cdconval  = params.get("cdconval");
    		String cdconcep  = params.get("cdconcep");
    		String idconcep  = params.get("idconcep");
    		String cdcapita  = params.get("cdcapita");
    		String nmordina  = params.get("nmordina");
//    		String femovimi  = params.get("femovimi");
    		Date   dFemovimi = new Date();
    		String cdmoneda  = params.get("cdmoneda");
    		String ptprecio  = params.get("ptprecio");
    		String cantidad  = params.get("cantidad");
    		String destopor  = params.get("destopor");
    		String destoimp  = params.get("destoimp");
    		String ptimport  = params.get("ptimport");
    		String ptrecobr  = params.get("ptrecobr");
    		String nmanno    = Calendar.getInstance().get(Calendar.YEAR)+"";
    		String nmapunte  = params.get("nmapunte");
    		String userregi  = usuario.getUser();
    		String feregist  = params.get("feregist");
    		Date   dFeregist = new Date();//renderFechas.parse(feregist);
    		
    		String operacion =  params.get("operacion");
    		if(StringUtils.isBlank(operacion)) operacion = Constantes.INSERT_MODE;
    		
    		siniestrosManager.P_MOV_MSINIVAL(
    				cdunieco, cdramo, estado, nmpoliza, nmsuplem,
    				nmsituac, aaapertu, status, nmsinies, nfactura,
    				cdgarant, cdconval, cdconcep, idconcep, cdcapita,
    				nmordina, dFemovimi, cdmoneda, ptprecio, cantidad,
    				destopor, destoimp, ptimport, ptrecobr, nmanno,
    				nmapunte, userregi, dFeregist, operacion);
    		
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
    		imap = new HashMap<String,Item>();
    		
    		GeneradorCampos    gc          = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
    		String             pantalla    = "CALCULO_SINIESTRO";
    		String             seccion     = null;
    	    String             ntramite    = params.get("ntramite");
    		List<ComponenteVO> componentes = null;
    		UserVO             usuario     = (UserVO)session.get("USUARIO");
    		String             cdrol       = usuario.getRolActivo().getObjeto().getValue();
    		
    		Map<String,String>       tramite    = siniestrosManager.obtenerTramiteCompleto(ntramite);
    		List<Map<String,String>> facturas   = siniestrosManager.obtenerFacturasTramite(ntramite);
    		List<Map<String,String>> siniestros = siniestrosManager.listaSiniestrosTramite(ntramite);
    		
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
    		slist2                            = new ArrayList<Map<String,String>>();
    		slist3                            = new ArrayList<Map<String,String>>();
    		llist1                            = new ArrayList<List<Map<String,String>>>();
    		lhosp                             = new ArrayList<Map<String,String>>();
    		lpdir                             = new ArrayList<Map<String,String>>();
    		lprem                             = new ArrayList<Map<String,String>>();
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
    				slist2.add(siniestrosManager.obtenerCopagoDeducible(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura));
    				
    				List<Map<String,String>>listaConceptosSiniestro = new ArrayList<Map<String,String>>();
    				llist1.add(listaConceptosSiniestro);
    				
    				//hospitalizacion
    				Map<String,String> hosp = new HashMap<String,String>();
    				lhosp.add(hosp);
    				hosp.put("PTIMPORT" , "0");
    				hosp.put("DESTO"    , "0");
    				hosp.put("IVA"      , "0");
    				//hospitalizacion
    				
    				//reembolso
    				Map<String,String>mprem=new HashMap<String,String>();
    				mprem.put("dummy","dummy");
    				lprem.add(mprem);
    				//remmbolso
    				
    				//pago directo
    				Map<String,String> mpdir = new HashMap<String,String>();
    				mpdir.put("total","0");
    				lpdir.add(mpdir);
    				//pago directo
    				
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
    						
    						//hospitalizacion
    						if(factura.get("CDGARANT").equalsIgnoreCase("18HO")||factura.get("CDGARANT").equalsIgnoreCase("18MA"))
    						{
    							logger.debug(">>HOSPITALIZACION");
    							double PTIMPORT=Double.parseDouble(concepto.get("PTIMPORT"));
    							double DESTOPOR=Double.parseDouble(concepto.get("DESTOPOR"));
    							double DESTOIMP=Double.parseDouble(concepto.get("DESTOIMP"));
    							logger.debug("concepto importe "+PTIMPORT);
    							logger.debug("concepto desto "+DESTOPOR);
    							logger.debug("concepto destoimp "+DESTOIMP);
    							logger.debug("usando iva proveedor "+ivaprov);
    							boolean copagoPorc = false;
    							String scopago =concepto.get("COPAGO");
    							if(scopago.equalsIgnoreCase("no"));
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
    							
    							double hPTIMPORT = Double.parseDouble(hosp.get("PTIMPORT"));
    							double hDESTO    = Double.parseDouble(hosp.get("DESTO"));
    							double hIVA      = Double.parseDouble(hosp.get("IVA"));
    							logger.debug("base import "+hPTIMPORT);
    							logger.debug("base desto "+hDESTO);
    							logger.debug("base iva "+hIVA);
    							hPTIMPORT += PTIMPORT;
    							hDESTO    += (PTIMPORT*(DESTOPOR/100d)) + (DESTOIMP);
    							hIVA      += PTIMPORT*(ivaprov/100d);
    							logger.debug("new import "+hPTIMPORT);
    							logger.debug("new desto "+hDESTO);
    							logger.debug("new iva "+hIVA);
    							hosp.put("PTIMPORT" , hPTIMPORT+"");
    							hosp.put("DESTO"    , hDESTO+"");
    							hosp.put("IVA"      , hIVA+"");
    							logger.debug("<<HOSPITALIZACION");
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
    							logger.debug(">>PAGO DIRECTO");
    							Map<String,String>row=new HashMap<String,String>();
    							row.putAll(concepto);
    							
    							double cantidad = Double.valueOf(row.get("CANTIDAD"));
    							logger.debug("cantidad "+cantidad);
    							double precioArancel = 0d;
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
    							row.put("SUBTTDESCUENTO",subtotalDescuento+"");
    							
    							boolean copagoPorc = false;
    							double  copago = 0d;
    							double  copagoAplicado = 0d;//++
    							String scopago =concepto.get("COPAGO");
    							logger.debug("procesar copago "+scopago);
    							if(StringUtils.isNotBlank(scopago))
    							{
    								if(scopago.contains("%"))
    								{
    									copagoPorc = true;
    								}
    								scopago=scopago.replace("%", "").replace("$", "");
    								copago=Double.valueOf(scopago);
    								if(copagoPorc)
    								{
    									copagoAplicado=subtotalDescuento*(copagoAplicado/100d);
    								}
    								else
    								{
    									copagoAplicado=copago;
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
    							double cedularaplicado   = subtotalCopago*(cedprov/100d);//++
    							logger.debug("cedularaplicado "+cedularaplicado);
    							row.put("CEDUAPLICA",cedularaplicado+"");
    							double subtotalImpuestos = subtotalCopago-(israplicado+cedularaplicado);//++
    							logger.debug("subtotalImpuestos "+subtotalImpuestos);
    							row.put("SUBTTIMPUESTOS",subtotalImpuestos+"");
    							double ivaaplicado       = subtotalImpuestos*(ivaprov/100d);//++
    							logger.debug("ivaaplicado "+ivaaplicado);
    							row.put("IVAAPLICA",ivaaplicado+"");
    							double ptimportauto      = subtotalImpuestos-ivaaplicado;//++
    							logger.debug("ptimportauto "+ptimportauto);
    							row.put("PTIMPORTAUTO",ptimportauto+"");
    							
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
    							
    							concepto.putAll(row);
    							logger.debug("<<PAGO DIRECTO");
    						}
    						//pago directo
    					}
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
				
    			
    			for(Map<String,String>facturaIte:facturas)
    			{
    				//reembolso
    				Map<String,String>mprem=new HashMap<String,String>(0);
    				mprem.put("TOTALNETO" , "0");
    				mprem.put("SUBTOTAL"  , "0");
    				lprem.add(mprem);
    				//reembolso
    				
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
    				slist2.add(siniestrosManager.obtenerCopagoDeducible(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura));
    				
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
    						
    						//hospitalizacion
    						/*
    						if(facturaIte.get("CDGARANT").equalsIgnoreCase("18HO")||facturaIte.get("CDGARANT").equalsIgnoreCase("18MA"))
    						{
    							logger.debug(">>HOSPITALIZACION");
    							double PTIMPORT=Double.parseDouble(concepto.get("PTIMPORT"));
    							double DESTOPOR=Double.parseDouble(concepto.get("DESTOPOR"));
    							double DESTOIMP=Double.parseDouble(concepto.get("DESTOIMP"));
    							logger.debug("concepto importe "+PTIMPORT);
    							logger.debug("concepto desto "+DESTOPOR);
    							logger.debug("concepto destoimp "+DESTOIMP);
    							logger.debug("usando iva proveedor "+ivaprov);
    							boolean copagoPorc = false;
    							String scopago =concepto.get("COPAGO");
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
    							
    							double hPTIMPORT = Double.parseDouble(hosp.get("PTIMPORT"));
    							double hDESTO    = Double.parseDouble(hosp.get("DESTO"));
    							double hIVA      = Double.parseDouble(hosp.get("IVA"));
    							logger.debug("base import "+hPTIMPORT);
    							logger.debug("base desto "+hDESTO);
    							logger.debug("base iva "+hIVA);
    							hPTIMPORT += PTIMPORT;
    							hDESTO    += (PTIMPORT*(DESTOPOR/100d)) + (DESTOIMP);
    							hIVA      += PTIMPORT*(ivaprov/100d);
    							logger.debug("new import "+hPTIMPORT);
    							logger.debug("new desto "+hDESTO);
    							logger.debug("new iva "+hIVA);
    							hosp.put("PTIMPORT" , hPTIMPORT+"");
    							hosp.put("DESTO"    , hDESTO+"");
    							hosp.put("IVA"      , hIVA+"");
    							logger.debug("<<HOSPITALIZACION");
    						}
    						//hospitalizacion
    						else
    						//pago reembolso
    						{
    						*/
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
    						/*}*/
    						//pago reembolso
    					}
    				}
    			}
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
    		slist1 = siniestrosManager.listaSiniestrosTramite(smap.get("ntramite"));
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
    		smap.put("CEDULAR"  , "5.0");
    		smap.put("IVA"      , "11");
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
    		String  cdrol       = usuario.getRolActivo().getObjeto().getValue();
    		
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
    				throw new Exception("Error al cancelar el trámite");
    			}
    			
    			String nombreReporte = null;
    			String nombreArchivo = null;
    			if(rolMedico)
    			{
    				nombreReporte = getText("rdf.siniestro.rechazo.medico.nombre");
    				nombreArchivo = getText("pdf.siniestro.rechazo.medico.nombre");
    			}
    			else//cancelacion por area de reclamaciones
    			{
    				boolean esReembolso = tipoPago.equalsIgnoreCase(TipoPago.REEMBOLSO.getCodigo());
    				if(esReembolso)
    				{
    					nombreReporte = getText("rdf.siniestro.rechazo.reemb.nombre");
        				nombreArchivo = getText("pdf.siniestro.rechazo.reemb.nombre");
    				}
    				else
    				{
    					nombreReporte = getText("rdf.siniestro.rechazo.pdir.nombre");
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
	
	
}