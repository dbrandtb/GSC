package mx.com.gseguros.portal.siniestros.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosGeneralesPolizaVO;
import mx.com.gseguros.portal.consultas.model.PolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.service.ConsultasAseguradoManager;
import mx.com.gseguros.portal.cotizacion.controller.MesaControlAction;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.model.RespuestaVO;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.CausaSiniestro;
import mx.com.gseguros.portal.general.util.EstatusTramite;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.Rol;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.general.util.TipoPago;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaProveedorVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTTAPVAATVO;
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
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;

public class SiniestrosAction extends PrincipalCoreAction {
	
	private static final String IMPORTE_WS_IMPORTE = "importe";
	private static final String IMPORTE_WS_IVA     = "iva";
	private static final String IMPORTE_WS_IVR     = "ivr";
	private static final String IMPORTE_WS_ISR     = "isr";
	private static final String IMPORTE_WS_CEDULAR = "cedular";
	private static final long serialVersionUID = -6321288906841302337L;
	static final Logger logger = LoggerFactory.getLogger(SiniestrosAction.class);
	private DateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	private boolean success;
	private SiniestrosManager siniestrosManager;
	private KernelManagerSustituto kernelManagerSustituto;
	private transient CatalogosManager catalogosManager;
	private PantallasManager       pantallasManager;
	private transient Ice2sigsService ice2sigsService;
	private HashMap<String,String> params;
	private HashMap<String,Object> paramsO;
	
	private List<GenericVO> listaAsegurado;
	private List<GenericVO> listaCausaSiniestro;
	private List<CoberturaPolizaVO> listaCoberturaPoliza;
	private List<MesaControlVO> listaMesaControl;
	private List<GenericVO> listaSubcobertura;
	private List<GenericVO> listaCPTICD;
	private List<ConsultaTTAPVAATVO> listaConsultaTTAPVAATV;
	private List<HashMap<String,String>> datosTablas;
	private List<PolizaVigenteVO> listaPoliza;
	private List<PolizaVigenteVO> polizaUnica;
	private String msgResult;
	
	private String existePenalizacion;
	private String montoArancel;
	private String usuarioTurnadoSiniestro;
	private String porcentajePenalizacion;
	private List<HashMap<String, String>> loadList;
	private List<HashMap<String, String>> saveList;
	private List<GenericVO> listaTipoAtencion;
	private List<GenericVO> listadoRamosSalud;
	private List<ListaFacturasVO> listaFacturas;
	
	private Item                     item;
	private Map<String,Item>         imap;
	private String                   mensaje;
	
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

	@Autowired
	@Qualifier("consultasAseguradoManagerImpl")
	private ConsultasAseguradoManager consultasAseguradoManager;

	/**
	* metodo para consultar la poliza en especifico
	* @param unieco, Ramo, Estado, Nmpoliza, cdperson
	* @return PolizaVigenteVO - Informacion de Poliza en especifico
	*/
	public String consultaPolizaUnica(){
		logger.debug(""
			+ "\n#################################"
			+ "\n###### consultaPolizaUnica ######"
		);
		logger.debug("params : {}",params);
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
			logger.error("Error al obtener los datos de la poliza unica : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
			+ "\n###### consultaPolizaUnica ######"
			+ "\n#################################"
		);
		success = true;
		return SUCCESS;
	}

	/**
	* metodo para generar un complemento
	* @param ntramiteAnterior
	* @return Nuevo tramite
	*/
	public String solicitarComplemento(){
		logger.debug(""
			+ "\n####################################"
			+ "\n######  solicitarComplemento  ######"
		);
		logger.debug("params : {}",params);
		try{
			List<MesaControlVO> listaMesaControl = siniestrosManager.getConsultaListaMesaControl(params.get("pv_ntramite_i"));
			logger.debug("Total Registros MC : {}", listaMesaControl.size());
			// 1.- Guardar en TMESACONTROL 
			this.session=ActionContext.getContext().getSession();
			UserVO usuario=(UserVO) session.get("USUARIO");
			HashMap<String, Object> parMesCon = new HashMap<String, Object>();
			parMesCon.put("pv_cdunieco_i",		params.get("cdunieco"));
			parMesCon.put("pv_cdunieco_i", 		listaMesaControl.get(0).getCduniecomc());
			parMesCon.put("pv_cdramo_i", 		listaMesaControl.get(0).getCdramomc());
			parMesCon.put("pv_estado_i", 		listaMesaControl.get(0).getEstadomc());
			parMesCon.put("pv_nmpoliza_i", 		listaMesaControl.get(0).getNmpolizamc());
			parMesCon.put("pv_nmsuplem_i", 		listaMesaControl.get(0).getNmsuplemmc());
			parMesCon.put("pv_cdsucadm_i", 		listaMesaControl.get(0).getCdsucadmmc());
			parMesCon.put("pv_cdsucdoc_i", 		listaMesaControl.get(0).getCdsucdocmc());
			parMesCon.put("pv_cdtiptra_i", 		listaMesaControl.get(0).getCdtiptramc());
			parMesCon.put("pv_ferecepc_i", 		getDate(listaMesaControl.get(0).getFerecepcmc()));
			parMesCon.put("pv_cdagente_i", 		listaMesaControl.get(0).getCdagentemc());
			parMesCon.put("pv_referencia_i", 	listaMesaControl.get(0).getReferenciamc());
			parMesCon.put("pv_nombre_i", 		listaMesaControl.get(0).getNombremc());
			parMesCon.put("pv_festatus_i",      getDate(listaMesaControl.get(0).getFecstatumc()));
			parMesCon.put("pv_status_i",        EstatusTramite.EN_CAPTURA.getCodigo());
			parMesCon.put("pv_comments_i",      listaMesaControl.get(0).getCommentsmc());
			parMesCon.put("pv_nmsolici_i",      listaMesaControl.get(0).getNmsolicimc());
			parMesCon.put("pv_cdtipsit_i",      listaMesaControl.get(0).getCdtipsitmc());
			parMesCon.put("pv_otvalor01"    , 	listaMesaControl.get(0).getOtvalor01mc());
			parMesCon.put("pv_otvalor02"    , 	listaMesaControl.get(0).getOtvalor02mc());
			parMesCon.put("pv_otvalor03"    , 	listaMesaControl.get(0).getOtvalor03mc());
			parMesCon.put("pv_otvalor04"    , 	listaMesaControl.get(0).getOtvalor04mc());
			parMesCon.put("pv_otvalor05"    , 	listaMesaControl.get(0).getOtvalor05mc());
			parMesCon.put("pv_otvalor06"    , 	listaMesaControl.get(0).getOtvalor06mc());
			parMesCon.put("pv_otvalor07"    , 	listaMesaControl.get(0).getOtvalor07mc());
			parMesCon.put("pv_otvalor08"    , 	listaMesaControl.get(0).getOtvalor08mc());
			parMesCon.put("pv_otvalor09"    , 	listaMesaControl.get(0).getOtvalor09mc());
			parMesCon.put("pv_otvalor10"    , 	listaMesaControl.get(0).getOtvalor10mc());
			parMesCon.put("pv_otvalor11"    , 	listaMesaControl.get(0).getOtvalor11mc());
			parMesCon.put("pv_otvalor12"    , 	listaMesaControl.get(0).getOtvalor12mc());
			parMesCon.put("pv_otvalor13"    , 	listaMesaControl.get(0).getOtvalor13mc());
			parMesCon.put("pv_otvalor14"    , 	listaMesaControl.get(0).getOtvalor14mc());
			parMesCon.put("pv_otvalor15"    , 	listaMesaControl.get(0).getOtvalor15mc());
			parMesCon.put("pv_otvalor16"    , 	listaMesaControl.get(0).getOtvalor16mc());
			parMesCon.put("pv_otvalor17"    , 	listaMesaControl.get(0).getOtvalor17mc());
			parMesCon.put("pv_otvalor18"    , 	listaMesaControl.get(0).getOtvalor18mc());
			parMesCon.put("pv_otvalor19"    , 	listaMesaControl.get(0).getOtvalor19mc());
			parMesCon.put("pv_otvalor20"    , 	listaMesaControl.get(0).getOtvalor20mc());
			parMesCon.put("pv_otvalor21"    , 	listaMesaControl.get(0).getOtvalor21mc());
			parMesCon.put("pv_otvalor22"    , 	listaMesaControl.get(0).getOtvalor22mc());
			parMesCon.put("pv_otvalor23"    , 	listaMesaControl.get(0).getOtvalor23mc());
			parMesCon.put("pv_otvalor24"    , 	listaMesaControl.get(0).getOtvalor24mc());
			parMesCon.put("pv_otvalor25"    , 	listaMesaControl.get(0).getOtvalor25mc());
			parMesCon.put("pv_otvalor26"    , 	listaMesaControl.get(0).getOtvalor26mc());
			parMesCon.put("pv_otvalor27"    , 	listaMesaControl.get(0).getOtvalor27mc());
			parMesCon.put("pv_otvalor28"    , 	listaMesaControl.get(0).getOtvalor28mc());
			parMesCon.put("pv_otvalor29"    , 	listaMesaControl.get(0).getOtvalor29mc());
			parMesCon.put("pv_otvalor30"    , 	listaMesaControl.get(0).getOtvalor30mc());
			parMesCon.put("pv_otvalor31"    , 	listaMesaControl.get(0).getOtvalor31mc());
			parMesCon.put("pv_otvalor32"    , 	listaMesaControl.get(0).getOtvalor32mc());
			parMesCon.put("pv_otvalor33"    , 	listaMesaControl.get(0).getOtvalor33mc());
			parMesCon.put("pv_otvalor34"    , 	listaMesaControl.get(0).getOtvalor34mc());
			parMesCon.put("pv_otvalor35"    , 	listaMesaControl.get(0).getOtvalor35mc());
			parMesCon.put("pv_otvalor36"    , 	listaMesaControl.get(0).getOtvalor36mc());
			parMesCon.put("pv_otvalor37"    , 	listaMesaControl.get(0).getOtvalor37mc());
			parMesCon.put("pv_otvalor38"    , 	listaMesaControl.get(0).getOtvalor38mc());
			parMesCon.put("pv_otvalor39"    , 	listaMesaControl.get(0).getOtvalor39mc());
			parMesCon.put("pv_otvalor40"    , 	listaMesaControl.get(0).getOtvalor40mc());
			parMesCon.put("pv_otvalor41"    , 	listaMesaControl.get(0).getOtvalor41mc());
			parMesCon.put("pv_otvalor42"    , 	listaMesaControl.get(0).getOtvalor42mc());
			parMesCon.put("pv_otvalor43"    , 	listaMesaControl.get(0).getOtvalor43mc());
			parMesCon.put("pv_otvalor44"    , 	listaMesaControl.get(0).getOtvalor44mc());
			parMesCon.put("pv_otvalor45"    , 	listaMesaControl.get(0).getOtvalor45mc());
			parMesCon.put("pv_otvalor46"    , 	listaMesaControl.get(0).getOtvalor46mc());
			parMesCon.put("pv_otvalor47"    , 	listaMesaControl.get(0).getOtvalor47mc());
			parMesCon.put("pv_otvalor48"    , 	listaMesaControl.get(0).getOtvalor48mc());
			parMesCon.put("pv_otvalor49"    , 	listaMesaControl.get(0).getOtvalor49mc());
			parMesCon.put("pv_otvalor50"    , 	listaMesaControl.get(0).getOtvalor50mc());
			WrapperResultados res = kernelManagerSustituto.PMovMesacontrol(parMesCon);
			if(res.getItemMap() == null){
				logger.error("Sin mensaje respuesta de nmtramite!!");
			}else{
				msgResult = (String) res.getItemMap().get("ntramite");
				logger.debug("valor de nuevo tramite : {}",msgResult);
				//2.- Obtenemos los valores de las facturas anteriores y lo guardamos a TFACMESCTRL
				List<Map<String,String>> facturas = siniestrosManager.obtenerFacturasTramite(params.get("pv_ntramite_i"));
				
				for(int i=0; i< facturas.size();i++){
					String fechaEgreso;
					logger.debug("Valor de Fecha Egreso :{}",facturas.get(i).get("FEEGRESO").toString());
					if(facturas.get(i).get("FEEGRESO").toString().length() > 0 && (!facturas.get(i).get("FEEGRESO").toString().equalsIgnoreCase("null"))){
						fechaEgreso = facturas.get(i).get("FEEGRESO");
					}else{
						fechaEgreso = facturas.get(i).get("FFACTURA");
					}
					siniestrosManager.guardaListaFacMesaControl2(
						msgResult,							facturas.get(i).get("NFACTURA"),		renderFechas.parse(facturas.get(i).get("FFACTURA")),
						facturas.get(i).get("CDTIPSER"),	facturas.get(i).get("CDPRESTA"),		facturas.get(i).get("PTIMPORT"),
						facturas.get(i).get("CDGARANT"),	facturas.get(i).get("CDCONVAL"),		facturas.get(i).get("DESCPORC"),
						facturas.get(i).get("DESCNUME"),	facturas.get(i).get("CDMONEDA"),		facturas.get(i).get("TASACAMB"),
						facturas.get(i).get("PTIMPORTA"),	facturas.get(i).get("DCTONUEX"),		renderFechas.parse(fechaEgreso),
						facturas.get(i).get("DIASDEDU"),	null,									facturas.get(i).get("NOMBPROV")
					);
				}
				
				//3.- Obtenemos los valores de MSINIEST y MSINIPER para llenar la tabla de TWORKSIN
				List<Map<String,String>> msiniper = siniestrosManager.obtenerDatoMsiniper(params.get("pv_ntramite_i"));
				
				for(int i=0;i<msiniper.size();i++) {
					HashMap<String, Object> paramsTworkSin = new HashMap<String, Object>();
					paramsTworkSin.put("pv_nmtramite_i",msgResult);												paramsTworkSin.put("pv_cdunieco_i",	msiniper.get(i).get("CDUNIECO"));
					paramsTworkSin.put("pv_cdramo_i",	msiniper.get(i).get("CDRAMO"));							paramsTworkSin.put("pv_estado_i",	msiniper.get(i).get("ESTADO"));
					paramsTworkSin.put("pv_nmpoliza_i",	msiniper.get(i).get("NMPOLIZA"));						paramsTworkSin.put("pv_nmsolici_i",	msiniper.get(i).get("NMSOLICI"));
					paramsTworkSin.put("pv_nmsuplem_i",	msiniper.get(i).get("NMSUPLEM"));						paramsTworkSin.put("pv_nmsituac_i",	msiniper.get(i).get("NMSITUAC"));
					paramsTworkSin.put("pv_cdtipsit_i",	msiniper.get(i).get("CDTIPSIT"));						paramsTworkSin.put("pv_cdperson_i",	msiniper.get(i).get("CDPERSON"));
					paramsTworkSin.put("pv_feocurre_i",	renderFechas.parse(msiniper.get(i).get("FEOCURRE")));	paramsTworkSin.put("pv_nmautser_i",	msiniper.get(i).get("NMAUTSER"));
					paramsTworkSin.put("pv_nfactura_i",	msiniper.get(i).get("NFACTURA"));
					siniestrosManager.guardaListaTworkSin(paramsTworkSin);
				}
				
				logger.debug("Tipo de Pago : {} ",listaMesaControl.get(0).getOtvalor02mc());
				
				if(TipoPago.DIRECTO.getCodigo().equals(listaMesaControl.get(0).getOtvalor02mc())){
					//PAGO DIRECTO
					for(int i=0; i< facturas.size();i++){
						List<Map<String,String>> siniesxfactura = siniestrosManager.listaSiniestrosTramite2(params.get("pv_ntramite_i"),facturas.get(i).get("NFACTURA"),null);
						
						for(int a=0; a< siniesxfactura.size();a++){
							siniestrosManager.getAltaSiniestroSinAutorizacion(msgResult,siniesxfactura.get(a).get("CDUNIECO"),siniesxfactura.get(a).get("CDRAMO"),
									siniesxfactura.get(a).get("ESTADO"),siniesxfactura.get(a).get("NMPOLIZA"),siniesxfactura.get(a).get("NMSUPLEM"),siniesxfactura.get(a).get("NMSITUAC"),
									siniesxfactura.get(a).get("CDTIPSIT"),renderFechas.parse(siniesxfactura.get(a).get("FEOCURRE")),facturas.get(i).get("NFACTURA"));
						}
					}
					
					for(int ii=0; ii< facturas.size();ii++){
						List<Map<String,String>> siniestrosAnterior = siniestrosManager.listaSiniestrosMsiniesTramite(params.get("pv_ntramite_i"),facturas.get(ii).get("NFACTURA"),null);
						logger.debug("Valor del siniestrosAnterior :{}",siniestrosAnterior);
						List<Map<String,String>> siniestrosNuevo = siniestrosManager.listaSiniestrosMsiniesTramite(msgResult,facturas.get(ii).get("NFACTURA"),null);
						logger.debug("Valor del siniestrosNuevo :{}",siniestrosNuevo);
						
						for(int r= 0; r< siniestrosAnterior.size(); r++)
						{
							for(int q = 0 ; q < siniestrosNuevo.size(); q++){
								if(siniestrosAnterior.get(r).get("CDUNIECO").equalsIgnoreCase(siniestrosNuevo.get(q).get("CDUNIECO")) &&
										siniestrosAnterior.get(r).get("CDRAMO").equalsIgnoreCase(siniestrosNuevo.get(q).get("CDRAMO")) &&
										siniestrosAnterior.get(r).get("ESTADO").equalsIgnoreCase(siniestrosNuevo.get(q).get("ESTADO")) &&
										siniestrosAnterior.get(r).get("NMPOLIZA").equalsIgnoreCase(siniestrosNuevo.get(q).get("NMPOLIZA")) &&
										siniestrosAnterior.get(r).get("NMSITUAC").equalsIgnoreCase(siniestrosNuevo.get(q).get("NMSITUAC")) &&
										siniestrosAnterior.get(r).get("CDTIPSIT").equalsIgnoreCase(siniestrosNuevo.get(q).get("CDTIPSIT")) &&
										siniestrosAnterior.get(r).get("CDPERSON").equalsIgnoreCase(siniestrosNuevo.get(q).get("CDPERSON")) &&
										siniestrosAnterior.get(r).get("FEOCURRE").equalsIgnoreCase(siniestrosNuevo.get(q).get("FEOCURRE")) &&
										siniestrosAnterior.get(r).get("CDUNIECO").equalsIgnoreCase(siniestrosNuevo.get(q).get("CDUNIECO")) &&
										siniestrosAnterior.get(r).get("STATUS").equalsIgnoreCase(siniestrosNuevo.get(q).get("STATUS"))
										
								){
									//5.- Actualizamos los valores de MSINIEST
									siniestrosManager.actualizaDatosGeneralesSiniestro(
											siniestrosNuevo.get(q).get("CDUNIECO"),siniestrosNuevo.get(q).get("CDRAMO"),
											siniestrosNuevo.get(q).get("ESTADO"),siniestrosNuevo.get(q).get("NMPOLIZA"),
											siniestrosNuevo.get(q).get("NMSUPLEM"),siniestrosNuevo.get(q).get("AAAPERTU"),
											siniestrosNuevo.get(q).get("NMSINIES"),renderFechas.parse(siniestrosNuevo.get(q).get("FEOCURRE")),
											siniestrosAnterior.get(r).get("NMSINREF"),siniestrosAnterior.get(r).get("CDICD"),
											siniestrosAnterior.get(r).get("CDICD2"),  siniestrosAnterior.get(r).get("CDCAUSA"),
											siniestrosAnterior.get(r).get("CDGARANT"),siniestrosAnterior.get(r).get("CDCONVAL"),
											siniestrosAnterior.get(r).get("NMAUTSER"),siniestrosAnterior.get(r).get("CDPERSON"),"1",
											siniestrosAnterior.get(r).get("NMRECLAMO"));
									
									List<Map<String,String>>lista = siniestrosManager.P_GET_MSINIVAL(
											siniestrosAnterior.get(r).get("CDUNIECO"), siniestrosAnterior.get(r).get("CDRAMO"), 
											siniestrosAnterior.get(r).get("ESTADO"),siniestrosAnterior.get(r).get("NMPOLIZA"), 
											siniestrosAnterior.get(r).get("NMSUPLEM"), siniestrosAnterior.get(r).get("NMSITUAC"),
											siniestrosAnterior.get(r).get("AAAPERTU"), siniestrosAnterior.get(r).get("STATUS"), 
											siniestrosAnterior.get(r).get("NMSINIES"), facturas.get(ii).get("NFACTURA"));
									loadList = new ArrayList<HashMap<String,String>>();
									for(Map<String,String>map:lista) {
										loadList.add((HashMap<String,String>)map);
									}
										
									for(int j=0;j<loadList.size();j++){
										Date   dFemovimi = new Date();
										Date   dFeregist = new Date();
										String nmanno    = Calendar.getInstance().get(Calendar.YEAR)+"";
										//6.- Se realiza el guardado en MSINIVAL
										siniestrosManager.P_MOV_MSINIVAL(
											loadList.get(j).get("CDUNIECO"),		loadList.get(j).get("CDRAMO"),			loadList.get(j).get("ESTADO"),				loadList.get(j).get("NMPOLIZA"),
											loadList.get(j).get("NMSUPLEM"),		loadList.get(j).get("NMSITUAC"),		siniestrosNuevo.get(q).get("AAAPERTU"),		siniestrosNuevo.get(q).get("STATUS"),
											siniestrosNuevo.get(q).get("NMSINIES"),	loadList.get(j).get("NFACTURA"),		loadList.get(j).get("CDGARANT"),			loadList.get(j).get("CDCONVAL"),
											loadList.get(j).get("CDCONCEP"),		loadList.get(j).get("IDCONCEP"),		loadList.get(j).get("CDCAPITA"),			loadList.get(j).get("NMORDINA"),
											dFemovimi,								loadList.get(j).get("CDMONEDA"),		loadList.get(j).get("PTPRECIO"),			loadList.get(j).get("CANTIDAD"),
											loadList.get(j).get("DESTOPOR"),		loadList.get(j).get("DESTOIMP"),		loadList.get(j).get("PTIMPORT"),			loadList.get(j).get("PTRECOBR"),
											nmanno,									loadList.get(j).get("NMAPUNTE"),		loadList.get(j).get("USERREGI"),			dFeregist,
											"I",									loadList.get(j).get("PTIMPOEX"),		loadList.get(j).get("DCTOIMEX"),			loadList.get(j).get("PTIMPOEX"),	
											loadList.get(j).get("PTMTOARA"));
										
										//7.- Obtenemos los valores de TDSINIVAL para ver si tiene Ajuste Medico
										List<Map<String,String>> ajusteMedico = siniestrosManager.P_GET_TDSINIVAL(
												loadList.get(j).get("CDUNIECO"),		loadList.get(j).get("CDRAMO"),			loadList.get(j).get("ESTADO"),				loadList.get(j).get("NMPOLIZA"),
												loadList.get(j).get("NMSUPLEM"),		loadList.get(j).get("NMSITUAC"),		loadList.get(j).get("AAAPERTU"),			loadList.get(j).get("STATUS"),
												loadList.get(j).get("NMSINIES"),		loadList.get(j).get("NFACTURA"),		loadList.get(j).get("CDGARANT"),			loadList.get(j).get("CDCONVAL"),
												loadList.get(j).get("CDCONCEP"),		loadList.get(j).get("IDCONCEP"),		loadList.get(j).get("NMORDINA"));
										
										for(int t=0;t<ajusteMedico.size();t++){
											String userregi  = usuario.getUser();
											Date   fechaRegistro = new Date();
											//8.- Guaramos la informacion de del Ajuste Medico
											siniestrosManager.P_MOV_TDSINIVAL(
													loadList.get(j).get("CDUNIECO"), loadList.get(j).get("CDRAMO"), loadList.get(j).get("ESTADO"), loadList.get(j).get("NMPOLIZA"),
													loadList.get(j).get("NMSUPLEM"), loadList.get(j).get("NMSITUAC"), siniestrosNuevo.get(q).get("AAAPERTU"), siniestrosNuevo.get(q).get("STATUS"),
													siniestrosNuevo.get(q).get("NMSINIES"), loadList.get(j).get("NFACTURA"),loadList.get(j).get("CDGARANT"), loadList.get(j).get("CDCONVAL"),
													loadList.get(j).get("CDCONCEP"), loadList.get(j).get("IDCONCEP"),loadList.get(j).get("NMORDINA"),loadList.get(j).get("NMORDMOV"),
													ajusteMedico.get(t).get("PTIMPORT"), ajusteMedico.get(t).get("COMMENTS"), userregi, fechaRegistro,
													Constantes.INSERT_MODE);
										}
									}
									
									//9.- Obtenemos los valores para realizar el guardado de TVALOSIN
									List<Map<String, String>> validacionFacturas = siniestrosManager.P_GET_FACTURAS_SINIESTRO(siniestrosAnterior.get(r).get("CDUNIECO"), siniestrosAnterior.get(r).get("CDRAMO"),
											siniestrosAnterior.get(r).get("ESTADO"),siniestrosAnterior.get(r).get("NMPOLIZA"),siniestrosAnterior.get(r).get("NMSUPLEM"), siniestrosAnterior.get(r).get("NMSITUAC"),
											siniestrosAnterior.get(r).get("AAAPERTU"), siniestrosAnterior.get(r).get("STATUS"),siniestrosAnterior.get(r).get("NMSINIES"), siniestrosAnterior.get(r).get("CDTIPSIT"));
									for(int k=0; k < validacionFacturas.size();k++){
										//10.- Obtenemos la informacion de Siniestro por tramite
										List<Map<String,String>> asegurados = siniestrosManager.listaSiniestrosTramite2(msgResult, validacionFacturas.get(k).get("NFACTURA"),null);
										
										for(int h =0; h < asegurados.size();h++){
											String munSiniestro=asegurados.get(h).get("NMSINIES")+"";
											if(!munSiniestro.equalsIgnoreCase("null")){
												//11.- Guardamos la informacion en TVALOSIN
												Map<String,Object>paramsTvalosin = new HashMap<String,Object>();
												paramsTvalosin.put("pv_cdunieco"  , asegurados.get(h).get("CDUNIECO"));
												paramsTvalosin.put("pv_cdramo"    , asegurados.get(h).get("CDRAMO"));
												paramsTvalosin.put("pv_aaapertu"  , asegurados.get(h).get("AAAPERTU"));
												paramsTvalosin.put("pv_status"    , asegurados.get(h).get("STATUS"));
												paramsTvalosin.put("pv_nmsinies"  , asegurados.get(h).get("NMSINIES"));
												paramsTvalosin.put("pv_cdtipsit"  , asegurados.get(h).get("CDTIPSIT"));
												paramsTvalosin.put("pv_nmsuplem"  , asegurados.get(h).get("NMSUPLEM"));
												paramsTvalosin.put("pv_cdusuari"  , null);
												paramsTvalosin.put("pv_feregist"  , null);
												paramsTvalosin.put("pv_otvalor01" , validacionFacturas.get(k).get("APLICA_IVA"));
												paramsTvalosin.put("pv_otvalor02" , validacionFacturas.get(k).get("ANTES_DESPUES"));
												paramsTvalosin.put("pv_otvalor03" , validacionFacturas.get(k).get("IVARETENIDO"));
												paramsTvalosin.put("pv_otvalor04" , msgResult);
												paramsTvalosin.put("pv_otvalor05" , validacionFacturas.get(k).get("NFACTURA"));
												paramsTvalosin.put("pv_accion_i"  , "I");
												kernelManagerSustituto.PMovTvalosin(paramsTvalosin);
												
												//11.- Guardamos la informacion en MAUTSINI
												siniestrosManager.P_MOV_MAUTSINI(asegurados.get(h).get("CDUNIECO"), asegurados.get(h).get("CDRAMO"), asegurados.get(h).get("ESTADO"), 
													asegurados.get(h).get("NMPOLIZA"), asegurados.get(h).get("NMSUPLEM"), asegurados.get(h).get("NMSITUAC"), asegurados.get(h).get("AAAPERTU"), 
													asegurados.get(h).get("STATUS"), asegurados.get(h).get("NMSINIES"),validacionFacturas.get(k).get("NFACTURA"),
													null,null,null,null,null,
													Constantes.MAUTSINI_AREA_RECLAMACIONES,validacionFacturas.get(k).get("AUTRECLA"), Constantes.MAUTSINI_FACTURA, validacionFacturas.get(k).get("COMMENAR"), Constantes.INSERT_MODE);
												
												siniestrosManager.P_MOV_MAUTSINI(asegurados.get(h).get("CDUNIECO"), asegurados.get(h).get("CDRAMO"), asegurados.get(h).get("ESTADO"), 
													asegurados.get(h).get("NMPOLIZA"), asegurados.get(h).get("NMSUPLEM"), asegurados.get(h).get("NMSITUAC"), asegurados.get(h).get("AAAPERTU"), 
													asegurados.get(h).get("STATUS"), asegurados.get(h).get("NMSINIES"), validacionFacturas.get(k).get("NFACTURA"),
													null,null,null,null,null,
													Constantes.MAUTSINI_AREA_MEDICA, validacionFacturas.get(k).get("AUTMEDIC"), Constantes.MAUTSINI_FACTURA, validacionFacturas.get(k).get("COMMENME"), Constantes.INSERT_MODE);
											}
										}	
									}
								}
							}
						}
					}
					
				}else{
					//3.- Se gnera el nuevo Siniestro de dicho tramite.
					siniestrosManager.getAltaSiniestroAltaTramite(msgResult);
					
					//4.- Obtenemos los valores del siniestro Anterior
					List<Map<String,String>> siniestros = siniestrosManager.listaSiniestrosMsiniesTramite(params.get("pv_ntramite_i"),null,null);
					List<Map<String,String>> siniestroNuevo = siniestrosManager.listaSiniestrosMsiniesTramite(msgResult,null,null);
					
					//5.- Actualizamos los valores de MSINIEST
					siniestrosManager.actualizaDatosGeneralesSiniestro(
							siniestroNuevo.get(0).get("CDUNIECO"),siniestroNuevo.get(0).get("CDRAMO"),
							siniestroNuevo.get(0).get("ESTADO"),siniestroNuevo.get(0).get("NMPOLIZA"),
							siniestroNuevo.get(0).get("NMSUPLEM"),siniestroNuevo.get(0).get("AAAPERTU"),
							siniestroNuevo.get(0).get("NMSINIES"),renderFechas.parse(siniestroNuevo.get(0).get("FEOCURRE")),
							siniestros.get(0).get("NMSINREF"),siniestros.get(0).get("CDICD"),
							siniestros.get(0).get("CDICD2"),siniestros.get(0).get("CDCAUSA"),
							siniestros.get(0).get("CDGARANT"),siniestros.get(0).get("CDCONVAL"),
							siniestros.get(0).get("NMAUTSER"),siniestroNuevo.get(0).get("CDPERSON"),"1",
							siniestroNuevo.get(0).get("NMRECLAMO"));
					
					for(int i=0; i< facturas.size();i++){
						//6.- Obtenemos los valores de MSINIVAL por facturas
						List<Map<String,String>>lista = siniestrosManager.P_GET_MSINIVAL(
							msiniper.get(0).get("CDUNIECO"), msiniper.get(0).get("CDRAMO"), 
							msiniper.get(0).get("ESTADO"),msiniper.get(0).get("NMPOLIZA"), 
							msiniper.get(0).get("NMSUPLEM"), msiniper.get(0).get("NMSITUAC"),
							siniestros.get(0).get("AAAPERTU"), siniestros.get(0).get("STATUS"), 
							siniestros.get(0).get("NMSINIES"), facturas.get(i).get("NFACTURA"));
						
						loadList = new ArrayList<HashMap<String,String>>();
						for(Map<String,String>map:lista) {
							loadList.add((HashMap<String,String>)map);
						}
						logger.debug("Valor del Siniestro nuevo : {}",siniestroNuevo.get(0).get("NMSINIES"));
						for(int j=0;j<loadList.size();j++){
							Date   dFemovimi = new Date();
							Date   dFeregist = new Date();
							String nmanno    = Calendar.getInstance().get(Calendar.YEAR)+"";
							//6.- Se realiza el guardado en MSINIVAL
							siniestrosManager.P_MOV_MSINIVAL(
								loadList.get(j).get("CDUNIECO"),		loadList.get(j).get("CDRAMO"),			loadList.get(j).get("ESTADO"),				loadList.get(j).get("NMPOLIZA"),
								loadList.get(j).get("NMSUPLEM"),		loadList.get(j).get("NMSITUAC"),		siniestroNuevo.get(0).get("AAAPERTU"),		siniestroNuevo.get(0).get("STATUS"),
								siniestroNuevo.get(0).get("NMSINIES"),	loadList.get(j).get("NFACTURA"),		loadList.get(j).get("CDGARANT"),			loadList.get(j).get("CDCONVAL"),
								loadList.get(j).get("CDCONCEP"),		loadList.get(j).get("IDCONCEP"),		loadList.get(j).get("CDCAPITA"),			loadList.get(j).get("NMORDINA"),
								dFemovimi,								loadList.get(j).get("CDMONEDA"),		loadList.get(j).get("PTPRECIO"),			loadList.get(j).get("CANTIDAD"),
								loadList.get(j).get("DESTOPOR"),		loadList.get(j).get("DESTOIMP"),		loadList.get(j).get("PTIMPORT"),			loadList.get(j).get("PTRECOBR"),
								nmanno,									loadList.get(j).get("NMAPUNTE"),		loadList.get(j).get("USERREGI"),			dFeregist,
								"I",									loadList.get(j).get("PTIMPOEX"),		loadList.get(j).get("DCTOIMEX"),			loadList.get(j).get("PTIMPOEX"),	
								loadList.get(j).get("PTMTOARA"));
							
							//7.- Obtenemos los valores de TDSINIVAL para ver si tiene Ajuste Medico
							List<Map<String,String>> ajusteMedico = siniestrosManager.P_GET_TDSINIVAL(
									loadList.get(j).get("CDUNIECO"),		loadList.get(j).get("CDRAMO"),			loadList.get(j).get("ESTADO"),				loadList.get(j).get("NMPOLIZA"),
									loadList.get(j).get("NMSUPLEM"),		loadList.get(j).get("NMSITUAC"),		loadList.get(j).get("AAAPERTU"),			loadList.get(j).get("STATUS"),
									loadList.get(j).get("NMSINIES"),		loadList.get(j).get("NFACTURA"),		loadList.get(j).get("CDGARANT"),			loadList.get(j).get("CDCONVAL"),
									loadList.get(j).get("CDCONCEP"),		loadList.get(j).get("IDCONCEP"),		loadList.get(j).get("NMORDINA"));
							
							for(int t=0;t<ajusteMedico.size();t++){
								String userregi  = usuario.getUser();
					    		Date   fechaRegistro = new Date();
					    		//8.- Guaramos la informacion de del Ajuste Medico
					    		siniestrosManager.P_MOV_TDSINIVAL(
					    				loadList.get(j).get("CDUNIECO"), loadList.get(j).get("CDRAMO"), loadList.get(j).get("ESTADO"), loadList.get(j).get("NMPOLIZA"),
					    				loadList.get(j).get("NMSUPLEM"), loadList.get(j).get("NMSITUAC"), siniestroNuevo.get(0).get("AAAPERTU"), siniestroNuevo.get(0).get("STATUS"),
					    				siniestroNuevo.get(0).get("NMSINIES"), loadList.get(j).get("NFACTURA"),loadList.get(j).get("CDGARANT"), loadList.get(j).get("CDCONVAL"),
					    				loadList.get(j).get("CDCONCEP"), loadList.get(j).get("IDCONCEP"),loadList.get(j).get("NMORDINA"),loadList.get(j).get("NMORDMOV"),
					    				ajusteMedico.get(t).get("PTIMPORT"), ajusteMedico.get(t).get("COMMENTS"), userregi, fechaRegistro,
					    				Constantes.INSERT_MODE);
							}
						}
					}
					
					//9.- Obtenemos los valores para realizar el guardado de TVALOSIN
					List<Map<String, String>> validacionFacturas = siniestrosManager.P_GET_FACTURAS_SINIESTRO(msiniper.get(0).get("CDUNIECO"), msiniper.get(0).get("CDRAMO"),
							msiniper.get(0).get("ESTADO"),msiniper.get(0).get("NMPOLIZA"),msiniper.get(0).get("NMSUPLEM"), msiniper.get(0).get("NMSITUAC"),
							siniestros.get(0).get("AAAPERTU"), siniestros.get(0).get("STATUS"),siniestros.get(0).get("NMSINIES"), siniestros.get(0).get("CDTIPSIT"));
					
					for(int k=0; k < validacionFacturas.size();k++){
						//10.- Obtenemos la informacion de Siniestro por tramite
						List<Map<String,String>> asegurados = siniestrosManager.listaSiniestrosTramite2(msgResult, validacionFacturas.get(k).get("NFACTURA"),null);
						
						for(int h =0; h < asegurados.size();h++){
							String munSiniestro=asegurados.get(h).get("NMSINIES")+"";
							if(!munSiniestro.equalsIgnoreCase("null")){
								//11.- Guardamos la informacion en TVALOSIN
								Map<String,Object>paramsTvalosin = new HashMap<String,Object>();
								paramsTvalosin.put("pv_cdunieco"  , asegurados.get(h).get("CDUNIECO"));
								paramsTvalosin.put("pv_cdramo"    , asegurados.get(h).get("CDRAMO"));
								paramsTvalosin.put("pv_aaapertu"  , asegurados.get(h).get("AAAPERTU"));
								paramsTvalosin.put("pv_status"    , asegurados.get(h).get("STATUS"));
								paramsTvalosin.put("pv_nmsinies"  , asegurados.get(h).get("NMSINIES"));
								paramsTvalosin.put("pv_cdtipsit"  , asegurados.get(h).get("CDTIPSIT"));
								paramsTvalosin.put("pv_nmsuplem"  , asegurados.get(h).get("NMSUPLEM"));
								paramsTvalosin.put("pv_cdusuari"  , null);
								paramsTvalosin.put("pv_feregist"  , null);
								paramsTvalosin.put("pv_otvalor01" , validacionFacturas.get(k).get("APLICA_IVA"));
								paramsTvalosin.put("pv_otvalor02" , validacionFacturas.get(k).get("ANTES_DESPUES"));
								paramsTvalosin.put("pv_otvalor03" , validacionFacturas.get(k).get("IVARETENIDO"));
								paramsTvalosin.put("pv_otvalor04" , msgResult);
								paramsTvalosin.put("pv_otvalor05" , validacionFacturas.get(k).get("NFACTURA"));
								paramsTvalosin.put("pv_accion_i"  , "I");
								kernelManagerSustituto.PMovTvalosin(paramsTvalosin);
								
								//11.- Guardamos la informacion en MAUTSINI
								siniestrosManager.P_MOV_MAUTSINI(asegurados.get(h).get("CDUNIECO"), asegurados.get(h).get("CDRAMO"), asegurados.get(h).get("ESTADO"), 
									asegurados.get(h).get("NMPOLIZA"), asegurados.get(h).get("NMSUPLEM"), asegurados.get(h).get("NMSITUAC"), asegurados.get(h).get("AAAPERTU"), 
									asegurados.get(h).get("STATUS"), asegurados.get(h).get("NMSINIES"),validacionFacturas.get(k).get("NFACTURA"),
									null,null,null,null,null,
									Constantes.MAUTSINI_AREA_RECLAMACIONES,validacionFacturas.get(k).get("AUTRECLA"), Constantes.MAUTSINI_FACTURA, validacionFacturas.get(k).get("COMMENAR"), Constantes.INSERT_MODE);
								
								siniestrosManager.P_MOV_MAUTSINI(asegurados.get(h).get("CDUNIECO"), asegurados.get(h).get("CDRAMO"), asegurados.get(h).get("ESTADO"), 
									asegurados.get(h).get("NMPOLIZA"), asegurados.get(h).get("NMSUPLEM"), asegurados.get(h).get("NMSITUAC"), asegurados.get(h).get("AAAPERTU"), 
									asegurados.get(h).get("STATUS"), asegurados.get(h).get("NMSINIES"), validacionFacturas.get(k).get("NFACTURA"),
									null,null,null,null,null,
									Constantes.MAUTSINI_AREA_MEDICA, validacionFacturas.get(k).get("AUTMEDIC"), Constantes.MAUTSINI_FACTURA, validacionFacturas.get(k).get("COMMENME"), Constantes.INSERT_MODE);
							}
						}	
					}
				}
			}
		}catch( Exception e){
			logger.error("Error al generar el complemento del tramite : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
			+ "\n######  solicitarComplemento  ######"
			+ "\n####################################"
		);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion para obtener las facturas del tramite
	* @param ntramite  
	* @return List<Map<String, String>>  de las facturas del tramite
	*/ 
	public String obtenerFacturasTramite() {
		logger.debug(""
			+ "\n####################################"
			+ "\n###### obtenerFacturasTramite ######"
		);
		logger.debug("smap :{}",smap);
		try{
			slist1=siniestrosManager.obtenerFacturasTramite(smap.get("ntramite"));
			mensaje="Facturas obtenidas";
			success=true;
		}
		catch(Exception ex) {
			success=false;
			logger.error("Error al obtener facturas de tramite : {}", ex.getMessage(), ex);
			mensaje=ex.getMessage();
		}
		logger.debug(""
			+ "\n###### obtenerFacturasTramite ######"
			+ "\n####################################"
		);
		return SUCCESS;
	}

	/**
	* Funcion que obtiene la lista de las poliza
	* @param cdperson
	* @return PolizaVigenteVO con la informacion de los asegurados
	*/ 
	public String consultaListaPoliza(){
		logger.debug(""
			+ "\n####################################"
			+ "\n######  consultaListaPoliza   ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			List<PolizaVigenteVO> lista = siniestrosManager.getConsultaListaPoliza(params.get("cdperson"), params.get("cdramo"));
			if(lista!=null && !lista.isEmpty())	listaPoliza = lista;
		}catch( Exception e){
			logger.error("Error al obtener los datos de la poliza : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
				+ "\n######  consultaListaPoliza   ######"
				+ "\n####################################"
			);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion que obtiene la lista del asegurado
	* @param void sin parametros de entrada
	* @return Lista GenericVO con la informacion de los asegurados
	*/    
	public String consultaListaAsegurado(){
		logger.debug(""
			+ "\n####################################"
			+ "\n###### consultaListaAsegurado ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			listaAsegurado= siniestrosManager.getConsultaListaAsegurado(params.get("cdperson"));
		}catch( Exception e){
			logger.error("Error al consultar la Lista de los asegurados : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
			+ "\n###### consultaListaAsegurado ######"
			+ "\n####################################"
		);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion que obtiene la lista del asegurado
	* @param void sin parametros de entrada
	* @return Lista GenericVO con la informacion de los asegurados
	*/    
	public String consultaListaAseguradoPoliza(){
		logger.debug(""
			+ "\n##########################################"
			+ "\n###### consultaListaAseguradoPoliza ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			listaAsegurado= siniestrosManager.getConsultaListaAseguradoPoliza(params.get("cdunieco"),params.get("cdramo"),params.get("estado"),params.get("nmpoliza"));
		}catch( Exception e){
			logger.error("Error al consultar la Lista de los asegurados : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
			+ "\n###### consultaListaAseguradoPoliza ######"
			+ "\n##########################################"
		);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion que obtiene el listado de Ramos de Salud vital
	* @param void sin parametros de entrada
	* @return Lista GenericVO - Informacion de los ramos
	*/    
	public String consultaRamosSalud(){
		logger.debug(""
				+ "\n####################################"
				+ "\n######   consultaRamosSalud   ######"
			);
		logger.debug(" **** Entrando al metodo para la consulta de Ramos para salud ****");
		try {
			listadoRamosSalud = siniestrosManager.getConsultaListaRamoSalud();
		}catch( Exception e){
			logger.error("Error al consultar los ramos para Salud : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
				+ "\n######   consultaRamosSalud   ######"
				+ "\n####################################"
			);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion que obtener el listado de los motivos de rechazos
	* @param 
	* @return List<HashMap<String, String>> - Motivo de rechazo
	*/
	public String loadListaRechazos(){
		logger.debug(""
			+ "\n####################################"
			+ "\n######   loadListaRechazos    ######"
		);
		try {
			loadList = new ArrayList<HashMap<String,String>>();
			List<Map<String,String>>lista=siniestrosManager.loadListaRechazos();
			for(Map<String,String>ele:lista){
				HashMap<String,String>map=new HashMap<String,String>();
				map.put("key"   , ele.get("CDMOTIVO"));
				map.put("value" , ele.get("DSMOTIVO"));
				loadList.add(map);
			}
		}catch( Exception e){
			logger.error("Error en loadListaRechazos: {}", e.getMessage(), e);
			success =  false;
			return SUCCESS;
		}
		logger.debug(""
			+ "\n######   loadListaRechazos    ######"
			+ "\n####################################"
		);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion que obtener el listado de los incisos de rechazos
	* @param 
	* @return List<HashMap<String, String>> - incisos de rechazo
	*/
	public String loadListaIncisosRechazos(){
		logger.debug(""
			+ "\n######################################"
			+ "\n###### loadListaIncisosRechazos ######"
		);
		try {
			loadList = new ArrayList<HashMap<String,String>>();
			List<Map<String,String>>lista= siniestrosManager.loadListaIncisosRechazos(params);
			for(Map<String,String>ele:lista) {
				HashMap<String,String>map=new HashMap<String,String>();
				map.put("key"   , ele.get("CDCAUMOT"));
				map.put("value" , ele.get("DSCAUMOT"));
				loadList.add(map);
			}
		}catch( Exception e){
			logger.error("Error en loadListaIncisosRechazos: {}", e.getMessage(), e);
			success =  false;
			return SUCCESS;
		}
		logger.debug(""
			+ "\n###### loadListaIncisosRechazos ######"
			+ "\n######################################"
		);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion para generar la carta de rechazo
	* @param params
	* @return PDF - Carta de rechazo
	*/
	public String generaCartaRechazo(){
		logger.debug(""
			+ "\n####################################"
			+ "\n######   generaCartaRechazo   ######"
		);
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
				logger.debug("no existe la carpeta : {}",paramsO.get("pv_ntramite_i"));
				carpeta.mkdir();
				if(carpeta.exists()){
					logger.debug("carpeta creada");
				} else {
					logger.debug("carpeta NO creada");
				}
			} else {
				logger.debug("existe la carpeta :{}",paramsO.get("pv_ntramite_i"));
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
			logger.error("Error en generaCartaRechazo: {}", e.getMessage(), e);
			success =  false;
			return SUCCESS;
		}
		logger.debug(""
				+ "\n######   generaCartaRechazo   ######"
				+ "\n####################################"
			);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion para obtener el usuario al que se le turna el tramite
	* @param ntramite, rolDestino
	* @return usuarioTurnadoSiniestro - Usuario al que se le turno el tramite
	*/    
	public String obtieneUsuarioTurnado(){
		logger.debug(""
				+ "\n####################################"
				+ "\n###### obtieneUsuarioTurnado  ######"
			);
		logger.debug("params de entrada :{}",params);
		try {
			usuarioTurnadoSiniestro = siniestrosManager.obtieneUsuarioTurnadoSiniestro(params.get("ntramite"),params.get("rolDestino"));
		}catch( Exception e){
			logger.error("Error al consultar el periodo de espera en meses : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
				+ "\n###### obtieneUsuarioTurnado  ######"
				+ "\n####################################"
			);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion que valida si requiere autorizacion de servicio
	* @param Cobertura, Subcobertura, cdramo, cdtipsit
	* @return datosInformacionAdicional informacion adicional
	*/
	public String obtieneRequiereAutServ(){
		logger.debug(""
				+ "\n####################################"
				+ "\n###### obtieneRequiereAutServ ######"
			);
		logger.debug("params de entrada :{}",params);
		try {
			datosInformacionAdicional = siniestrosManager.requiereInformacionAdicional(params.get("cobertura"),params.get("subcobertura"),params.get("cdramo"),params.get("cdtipsit"));
		}catch( Exception e){
			logger.error("Error al obtener si requiere autorizacion servicio : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
				+ "\n###### obtieneRequiereAutServ ######"
				+ "\n####################################"
			);
		success = true;
		return SUCCESS;
	}
	
	/**
	* metodo que obtiene el listado de las coberturas de poliza
	* @param maps [cdunieco,estado,cdramo,nmpoliza,nmsituac,cdgarant]
	* @return Lista CoberturaPolizaVO con la informacion de los asegurados
	*/
	public String consultaListaCoberturaPoliza(){
		logger.debug(""
				+ "\n##########################################"
				+ "\n###### consultaListaCoberturaPoliza ######"
			);
		logger.debug("params de entrada :{}",params);
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
			logger.error("Error al obtener la lista de la cobertura de la poliza : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
				+ "\n###### consultaListaCoberturaPoliza ######"
				+ "\n##########################################"
			);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion que obtiene la lista de Sbcobertura
	* @param cdgarant
	* @param cdsubcob
	* @return Lista GenericVO con la informacion de los asegurados
	*/    
	public String consultaListaSubcobertura(){
		logger.debug(""
				+ "\n#######################################"
				+ "\n###### consultaListaSubcobertura ######"
			);
		logger.debug("params de entrada :{}",params);
		try {
			//cdunieco, cdramo, estado, nmpoliza, nmsituac, cdtipsit, cdgarant, cdsubcob
			listaSubcobertura= siniestrosManager.getConsultaListaSubcobertura(params.get("cdunieco"),params.get("cdramo"), params.get("estado"),
								params.get("nmpoliza"),params.get("nmsituac"),params.get("cdtipsit"),params.get("cdgarant"),params.get("cdsubcob"));
		}catch( Exception e){
			logger.error("Error al consultar la Lista de subcoberturas : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
				+ "\n###### consultaListaSubcobertura ######"
				+ "\n#######################################"
			);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion para obtener el tipo de atencion
	* @param ramo y tipoPago
	* @return Lista GenericVO - Tipo de Atencion
	*/    
	public String consultaListaTipoAtencion(){
		logger.debug(""
			+ "\n#######################################"
			+ "\n###### consultaListaTipoAtencion ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			if(params != null){
				listaTipoAtencion= siniestrosManager.getconsultaListaTipoAtencion(params.get("cdramo"), params.get("tipoPago"));
				logger.debug("listaTipoAtencion : {}",listaTipoAtencion);
			}
		}catch( Exception e){
			logger.error("Error al consultar la Lista de los asegurados : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
			+ "\n###### consultaListaTipoAtencion ######"
			+ "\n#######################################"
		);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion para obtener la informacion que se encuentra en TMESACONTROL
	* @param ntramite
	* @return MesaControlVO - Informacion del tramite
	*/ 
	public String consultaListadoMesaControl(){
		logger.debug(""
			+ "\n########################################"
			+ "\n###### consultaListadoMesaControl ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			List<MesaControlVO> lista = siniestrosManager.getConsultaListaMesaControl(params.get("ntramite"));
			if(lista!=null && !lista.isEmpty())	listaMesaControl = lista;
		}catch( Exception e){
			logger.error("Error al obtener los registros de la mesa de control : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
			+ "\n###### consultaListadoMesaControl ######"
			+ "\n########################################"
		);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion para validar si ya existen documento cargados
	* @param params
	* @return Success - Exito si se genera el numero de tramite
	*/
	public String validaDocumentosCargados(){
		logger.debug(""
			+ "\n########################################"
			+ "\n######  validaDocumentosCargados  ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			msgResult = siniestrosManager.validaDocumentosCargados(params);
			logger.debug("Respuesta ValidaDocumentosCargados:{} ", msgResult);
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
			logger.error("Error en validaDocumentosCargados : {}", e.getMessage(), e);
			success =  false;
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	/**
	* Funcion que obtiene el listado de las subcobertura
	* @param cdtabla
	* @param otclave
	* @return Lista GenericVO con la informacion de los asegurados
	*/
	public String consultaListaCPTICD(){
		logger.debug(""
			+ "\n####################################"
			+ "\n######  consultaListaCPTICD   ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			listaCPTICD= siniestrosManager.getConsultaListaCPTICD(params.get("cdtabla"),params.get("otclave"));
		}catch( Exception e){
			logger.error("Error al consultar la Lista de subcoberturas : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
			+ "\n######  consultaListaCPTICD   ######"
			+ "\n####################################"
		);
		success = true;
		return SUCCESS;
	}

	/**
	* Funcion que nos muestra la informacion de las pantalla principal de facturas y afiliados
	* @param params
	* @return visualizacion de la pantalla
	*/
	public String afiliadosAfectados() {
		logger.info(""
			+ "\n################################"
			+ "\n################################"
			+ "\n###### afiliadosAfectados ######"
			+ "\n######                    ######"
		);
		logger.info("params: {}",params);
		try{
			String ntramite = params.get("ntramite");
			if(ntramite==null){
				throw new Exception("No hay tramite");
			}
			params = (HashMap<String, String>) siniestrosManager.obtenerTramiteCompleto(ntramite);
			List<Map<String,String>> facturas = siniestrosManager.obtenerFacturasTramite(ntramite);

			if(facturas==null || facturas.size()==0){
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

			slist2=siniestrosManager.obtenerFacturasTramite(ntramite);
			seccion = "COLUMNAS";

			componentes = pantallasManager.obtenerComponentes(
				null, null, null, null, null, cdrol, pantalla, seccion, null);

			for(ComponenteVO com:componentes){
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
		catch(Exception ex){
			logger.error("error al cargar pantalla de asegurados afectados : {}", ex.getMessage(), ex);
		}
		logger.info(""
			+ "\n######                    ######"
			+ "\n###### afiliadosAfectados ######"
			+ "\n################################"
			+ "\n################################"
		);
		return SUCCESS;
	}

	/**
	* Funcion para generar el Siniestro sin Autorizacion de servicio
	* @param params
	* @return Num. de autorizacion
	*/
	public String generarSiniestroSinAutorizacion(){
		logger.debug(""
			+ "\n#############################################"
			+ "\n###### generarSiniestroSinAutorizacion ######"
		);
		logger.debug("params: {}",params);
		try {
			String fechaOcurrencia = params.get("dateOcurrencia");
			Date   dFeOcurrencia   = renderFechas.parse(fechaOcurrencia);
			
			siniestrosManager.getAltaSiniestroSinAutorizacion(params.get("ntramite"),params.get("cdunieco"),params.get("cdramo"),params.get("estado"),
				  params.get("nmpoliza"),params.get("nmsuplem"),params.get("nmsituac"),params.get("cdtipsit"),dFeOcurrencia, params.get("nfactura"));
			mensaje = "Se ha generado el siniestro";
			success=true;
		}
		catch(Exception ex) {
			logger.error("error al inicar siniestro desde tworksin : {}", ex.getMessage(), ex);
			mensaje = ex.getMessage();
			success=false;
		}
		logger.debug(""
			+ "\n###### generarSiniestroSinAutorizacion ######"
			+ "\n#############################################"
		);
		return SUCCESS;
	}
	
	/**
	* Funcion para generar el Siniestro Por asegurado
	* @param params
	* @return Num. de autorizacion
	*/
	public String iniciarSiniestroTworksin() {
		logger.debug(""
			+ "\n######################################"
			+ "\n###### iniciarSiniestroTworksin ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			String nmautser = params.get("nmautser");
			String cdperson = params.get("cdperson");
			String nmpoliza = params.get("nmpoliza");
			String ntramite = params.get("ntramite");
			String nfactura = params.get("nfactura");
			Date feocurrencia = renderFechas.parse(params.get("feocurrencia"));

			//CUANDO SE PIDE EL NUMERO DE AUTORIZACION DE SERVICIO EN PANTALLA
			//INSERTA EL NUMERO DE AUTORIZACION EN TWORKSIN
			siniestrosManager.actualizarAutorizacionTworksin(ntramite,nmpoliza,cdperson,nmautser,nfactura,feocurrencia);
			//CREA UN MSINIEST A PARTIR DE TWORKSIN
			siniestrosManager.getAltaSiniestroAutServicio(nmautser,nfactura);
			mensaje = "Se ha asociado el siniestro con la autorizaci&oacute;n";
			success=true;
		}
		catch(Exception ex){
			logger.error("error al inicar siniestro desde tworksin : {}", ex.getMessage(), ex);
			mensaje = ex.getMessage();
			success=false;
		}
		logger.debug(""
			+ "\n###### iniciarSiniestroTworksin ######"
			+ "\n######################################"
		);
		return SUCCESS;
	}

	/**
	* Funcion Para guardar para guardarl el Concepto, Destino de pago y Fecha de Pago
	* @param params
	* @return Num. de autorizacion
	*/
	public String guardarConceptoDestino() {
		logger.debug(""
			+ "\n###############################################"
			+ "\n###### guardarConceptoDestino			  ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			String ntramite = params.get("ntramite");
			String cdtipsit = params.get("cdtipsit");
			String cdDestinoPago = params.get("destinoPago");
			String cdConceptoPago = params.get("concepPago");
			String beneficiario = params.get("beneficiario");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String tipoPago = params.get("tipoPago");
			String fechaSiniestro   = sdf.format(new Date());
			
			Map<String,Object> otvalor = new HashMap<String,Object>();
			otvalor.put("pv_ntramite_i" , ntramite);
			otvalor.put("pv_cdtipsit_i" , cdtipsit);
			otvalor.put("pv_otvalor18_i"  , cdDestinoPago);
			otvalor.put("pv_otvalor19_i"  , cdConceptoPago);
			otvalor.put("pv_otvalor21_i"  , fechaSiniestro); // Guardaremos la fecha cuando se solicito el pago del siniestro
			
			if(!tipoPago.equalsIgnoreCase("1")){
				otvalor.put("pv_otvalor04_i"  , beneficiario);
			}
			siniestrosManager.actualizaOTValorMesaControl(otvalor);
			
			success = true;
			mensaje = "Tr&aacute;mite actualizado";
		}
		catch(Exception ex) {
			success=false;
			logger.error("error al seleccionar la cobertura : {}", ex.getMessage(), ex);
			mensaje = ex.getMessage();
		}
		logger.debug(""
			+ "\n###### 	guardarConceptoDestino 	      ######"
			+ "\n###############################################"
		);
		return SUCCESS;
	}

	/**
	* Funcion que realiza el proceso de solicitud de pago del Siniestro
	* @param params
	* @return Exito en el pago del Siniestro
	*/
	public String solicitarPago(){
		logger.debug(""
			+ "\n####################################"
			+ "\n######     solicitarPago      ######"
		);
		logger.debug("Datos de Entrada :{}",params);
		try {
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
					logger.debug("no existe la carpeta: {}",paramsO.get("pv_ntramite_i"));
					carpeta.mkdir();
					if(carpeta.exists()){
						logger.debug("carpeta creada");
					} else {
						logger.debug("carpeta NO creada");
					}
				} else {
					logger.debug("existe la carpeta   : {} ",paramsO.get("pv_ntramite_i"));
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
			logger.error("Error en solicitarPago generacion de cartas finiquito : {}", e.getMessage(), e);
			success =  false;
			mensaje = "Error al solicitar Pago, generaci&oacute;n de cartas finiquito. Consulte a Soporte T&eacute;cnico.";
			return SUCCESS;
		}
		logger.debug(""
			+ "\n######     solicitarPago      ######"
			+ "\n####################################"
		);
		return SUCCESS;
	}

	/**
	* Funcion para obtener los conceptos
	* @param params
	* @return obtenemos el listado de los conceptos
	*/
	public String obtenerMsinival() {
		logger.debug(""
			+ "\n#############################"
			+ "\n###### obtenerMsinival ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
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

			for(Map<String,String>map:lista) {
				loadList.add((HashMap<String,String>)map);
			}
			mensaje = "Datos obtenidos";
			success = true;
		}
		catch(Exception ex) {
			logger.error("error al obtener el listado de los conceptos : {}", ex.getMessage(), ex);
			success = false;
			mensaje = ex.getMessage();
		}
		logger.debug(""
			+ "\n###### obtenerMsinival ######"
			+ "\n#############################"
		);
		return SUCCESS;
	}
	/**
	* Funcion para eliminar los conceptos
	* @param params
	* @return exito cuando eliminamos el concepto
	*/
	public String guardarMsinival() {
		logger.debug(""
			+ "\n#############################"
			+ "\n###### guardarMsinival ######"
		);
		logger.debug("params de entrada :{} datosTabla : {}",params,datosTablas);
		try {
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
				logger.error("error al eliminar en TfacMesCtrl : {}", e.getMessage(), e);
			}
			//2.- Guardamos los registros de 
			for(int i=0;i<datosTablas.size();i++){
					siniestrosManager.P_MOV_MSINIVAL(
						datosTablas.get(i).get("cdunieco"), 	datosTablas.get(i).get("cdramo"), 		datosTablas.get(i).get("estado"), 		datosTablas.get(i).get("nmpoliza"),
						datosTablas.get(i).get("nmsuplem"),		datosTablas.get(i).get("nmsituac"), 	datosTablas.get(i).get("aaapertu"), 	datosTablas.get(i).get("status"), 
						datosTablas.get(i).get("nmsinies"), 	datosTablas.get(i).get("nfactura"),		datosTablas.get(i).get("cdgarant"), 	datosTablas.get(i).get("cdconval"), 
						datosTablas.get(i).get("cdconcep"), 	datosTablas.get(i).get("idconcep"), 	datosTablas.get(i).get("cdcapita"),		datosTablas.get(i).get("nmordina"), 
						dFemovimi, 								datosTablas.get(i).get("cdmoneda"), 	datosTablas.get(i).get("ptprecio"), 	datosTablas.get(i).get("cantidad"),
						datosTablas.get(i).get("destopor"), 	datosTablas.get(i).get("destoimp"),		datosTablas.get(i).get("ptimport"), 	datosTablas.get(i).get("ptrecobr"),
						nmanno,									datosTablas.get(i).get("nmapunte"),		 "", 									dFeregist,
						datosTablas.get(i).get("operacion"),	datosTablas.get(i).get("ptpcioex"),		datosTablas.get(i).get("dctoimex"),		datosTablas.get(i).get("ptimpoex"),
						datosTablas.get(i).get("mtoArancel"));
			}
			mensaje = "Datos guardados";
			success = true;
		}
		catch(Exception ex){
			logger.debug("error al guardar msinival : {}", ex.getMessage(), ex);
			success=false;
			mensaje=ex.getMessage();
		}
		logger.debug(""
			+ "\n###### guardarMsinival ######"
			+ "\n#############################"
		);
		return SUCCESS;
	}

	/**
	* Funcion para obtener el monto del arancel
	* @param params
	* @return montoArancel
	*/
	public String obtieneMontoArancel(){
		logger.debug(""
			+ "\n####################################"
			+ "\n######  obtieneMontoArancel   ######"
		);
		logger.debug("params de entrada :{}",params);
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
			logger.error("Error al obtener el monto del arancel : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
			+ "\n######  obtieneMontoArancel   ######"
			+ "\n####################################"
		);
		success = true;
		return SUCCESS;
	}

	/**
	* Funcion donde obtenemos la informacion adicional por el cdperson
	* @param cdperson
	* @return List<Map<String, String>> datosInformacionAdicional
	*/ 
	public String consultaAutServicioSiniestro(){
		logger.debug(""
			+ "\n##########################################"
			+ "\n###### consultaAutServicioSiniestro ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			datosInformacionAdicional = siniestrosManager.getConsultaListaAutServicioSiniestro(params.get("cdperson"));
			logger.debug("Valor de Respuesta : {} Total de registros :{}" ,datosInformacionAdicional,datosInformacionAdicional.size());
		}catch( Exception e){
			logger.error("Error al obtener las autorizaciones : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
			+ "\n###### consultaAutServicioSiniestro ######"
			+ "\n##########################################"
		);
		success = true;
		return SUCCESS;
	}

	/**
	* Funcion donde obtenemos los informacion del siniestro maestro
	* @param params
	* @return List<Map<String, String>> datosInformacionAdicional
	*/ 
	public String consultaSiniestroMaestro(){
		logger.debug(""
			+ "\n######################################"
			+ "\n###### consultaSiniestroMaestro ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			datosInformacionAdicional = siniestrosManager.getConsultaListaMSiniestMaestro( params.get("cdunieco"),		params.get("cdramo"),		params.get("estado"),
																						   params.get("nmpoliza"),		params.get("nmsuplem"),		params.get("nmsituac"),
																						   params.get("status"));
			logger.debug("Siniestros Maestros : {}",datosInformacionAdicional);
		}catch( Exception e){
			logger.error("Error al obtener las consultaSiniestroMaestro : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
				+ "\n###### consultaSiniestroMaestro ######"
				+ "\n######################################"
			);
		success = true;
		return SUCCESS;
	}

	/**
	* Funcion donde obtenemos los datos de las validaciones del siniestro
	* @param params
	* @return List<Map<String, String>> datosValidacion
	*/ 
	public String consultaDatosValidacionSiniestro(){
		logger.debug(""
			+ "\n##############################################"
			+ "\n###### consultaDatosValidacionSiniestro ######"
		);
		logger.debug("params de entrada :{} ",params);
		try {
			datosValidacion = siniestrosManager.getConsultaDatosValidacionSiniestro(params.get("ntramite"),params.get("nfactura"),params.get("tipoPago"));
			logger.debug("Respuesta datosValidacion : {}",datosValidacion);
		}catch( Exception e){
			logger.error("Error al obtener los datos de la validacion del Siniestro : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
			+ "\n###### consultaDatosValidacionSiniestro ######"
			+ "\n##############################################"
		);
		success = true;
		return SUCCESS;
	}
	/**
	* Funcion donde obtenemos los datos de las validaciones del siniestro
	* @param params
	* @return List<Map<String, String>> datosValidacion
	*/ 
	public String consultaDatosAseguradoSiniestro(){
		logger.debug(""
			+ "\n#############################################"
			+ "\n###### consultaDatosAseguradoSiniestro ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			datosValidacion = siniestrosManager.obtenerInfAseguradosTramite(params.get("ntramite"));
			logger.debug("Respuesta datosValidacion : {}", datosValidacion);
		}catch( Exception e){
			logger.error("Error al obtener los datos de la validacion del Siniestro : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
			+ "\n###### consultaDatosAseguradoSiniestro ######"
			+ "\n#############################################"
		);
		success = true;
		return SUCCESS;
	}
	/**
	* Funcion donde obtenemos los datos de la validacion del Ajustador Medico
	* @param params
	* @return List<Map<String, String>> datosValidacion
	*/
	public String consultaDatosValidacionAjustadorMed(){
		logger.debug(""
			+ "\n#################################################"
			+ "\n###### consultaDatosValidacionAjustadorMed ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			datosValidacion = siniestrosManager.getConsultaDatosValidacionAjustadorMed(params.get("ntramite"));
			logger.debug("Respuesta datosValidacion : {}", datosValidacion);
		}catch( Exception e){
			logger.error("Error al obtener las consultaDatosValidacionAjustadorMed : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
			+ "\n###### consultaDatosValidacionAjustadorMed ######"
			+ "\n#################################################"
		);
		success = true;
		return SUCCESS;
	}

	/**
	* Funcion para guardar un solo asegurado desde afiliados afectados
	* @param params
	* @return List<Map<String, String>> datosValidacion
	*/
	public String guardaaseguradoUnico(){
		logger.debug(""
			+ "\n#############################"
			+ "\n###### guardaTworksin #######"
		);
		logger.debug("params de entrada :{}",params);
		try{
			HashMap<String, Object> paramsTworkSin = new HashMap<String, Object>();
			paramsTworkSin.put("pv_nmtramite_i",params.get("nmtramite"));
			paramsTworkSin.put("pv_cdunieco_i",	params.get("cdunieco"));
			paramsTworkSin.put("pv_cdramo_i",	params.get("cdramo"));
			paramsTworkSin.put("pv_estado_i",	params.get("estado"));
			paramsTworkSin.put("pv_nmpoliza_i",	params.get("nmpoliza"));
			paramsTworkSin.put("pv_nmsolici_i",	params.get("nmsolici"));
			paramsTworkSin.put("pv_nmsuplem_i",	params.get("nmsuplem"));
			paramsTworkSin.put("pv_nmsituac_i",	params.get("nmsituac"));
			paramsTworkSin.put("pv_cdtipsit_i",	params.get("cdtipsit"));
			paramsTworkSin.put("pv_cdperson_i",	params.get("cdperson"));
			paramsTworkSin.put("pv_feocurre_i",	renderFechas.parse(params.get("feocurre")));
			paramsTworkSin.put("pv_nmautser_i",	null);
			paramsTworkSin.put("pv_nfactura_i",	params.get("nfactura"));
			siniestrosManager.guardaListaTworkSin(paramsTworkSin);
			mensaje = "Asegurado guardado";
			success = true;
		}
		catch(Exception ex){
			logger.debug("error al guardar tworksin : {}", ex.getMessage(), ex);
			success=false;
			mensaje=ex.getMessage();
		}
		logger.debug(""
			+ "\n###### guardaTworksin  ######"
			+ "\n#############################"
		);
		return SUCCESS;
	}

	/**
	* Funcion para eliminar un asegurado desde afiliados afectados
	* @param params
	* @return exito
	*/
	public String eliminarAsegurado(){
		logger.debug(""
			+ "\n###############################"
			+ "\n###### eliminarAsegurado ######"
			);
		logger.debug("params de entrada :{}",params);
		try{
			HashMap<String, Object> paramsTworkSin = new HashMap<String, Object>();
				paramsTworkSin.put("pv_nmtramite_i",params.get("nmtramite"));
				paramsTworkSin.put("pv_nfactura_i",params.get("nfactura"));
				paramsTworkSin.put("pv_cdunieco_i",params.get("cdunieco"));
				paramsTworkSin.put("pv_cdramo_i",params.get("cdramo"));
				paramsTworkSin.put("pv_estado_i",params.get("estado"));
				paramsTworkSin.put("pv_nmpoliza_i",params.get("nmpoliza"));
				paramsTworkSin.put("pv_nmsuplem_i",params.get("nmsuplem"));
				paramsTworkSin.put("pv_nmsituac_i",params.get("nmsituac"));
				paramsTworkSin.put("pv_cdtipsit_i",params.get("cdtipsit"));
				paramsTworkSin.put("pv_cdperson_i",params.get("cdperson"));
				paramsTworkSin.put("pv_feocurre_i",renderFechas.parse(params.get("feocurre")));
				paramsTworkSin.put("pv_nmsinies_i",params.get("nmsinies"));
				
				siniestrosManager.eliminarAsegurado(paramsTworkSin);
			mensaje = "Asegurado eliminado";
			success = true;
		}catch(Exception ex){
			logger.debug("error al eliminar registro : {}", ex.getMessage(), ex);
			success=false;
			mensaje=ex.getMessage();
		}
		logger.debug(""
			+ "\n###### eliminarAsegurado  ######"
			+ "\n################################"
		);
		return SUCCESS;
	}
	
	/**
	* Funcion para obtener el Siniestro x tramite
	* @param params
	* @return List<Map<String, String>> slist1
	*/
	public String obtenerSiniestrosTramite() {
		logger.debug(""
			+ "\n######################################"
			+ "\n###### obtenerSiniestrosTramite ######"
		);
		logger.debug("smap de entrada :{}",smap);
		try {
			slist1 = siniestrosManager.listaSiniestrosTramite2(smap.get("ntramite"),smap.get("nfactura"),null);
			success=true;
			mensaje="Siniestros obtenidos";
		}
		catch(Exception ex) {
			logger.debug("error al obtener siniestros de tramite : {}", ex.getMessage(), ex);
			success = false;
			mensaje = ex.getMessage();
		}
		logger.debug(""
			+ "\n###### obtenerSiniestrosTramite ######"
			+ "\n######################################"
		);
		return SUCCESS;
	}

	/**
	* Funcion para obtener el listado de las Suma Asegurada
	* @param params
	* @return List<Map<String, String>> datosValidacion
	*/
	public String consultaDatosSumaAsegurada(){
		logger.debug(""
			+ "\n########################################"
			+ "\n###### consultaDatosSumaAsegurada ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			datosValidacion = siniestrosManager.getConsultaDatosSumaAsegurada(params.get("cdunieco"),		params.get("cdramo"),		params.get("estado"),
																			  params.get("nmpoliza"),		params.get("cdperson"),		params.get("nmsinref"));
			logger.debug("Respuesta datosValidacion : {}", datosValidacion);
		}catch( Exception e){
			logger.error("Error al obtener las autorizaciones : {}", e.getMessage(), e);
			return SUCCESS;
		}
		logger.debug(""
			+ "\n###### consultaDatosSumaAsegurada ######"
			+ "\n########################################"
		);
		success = true;
		return SUCCESS;
	}

	/**
	* Funcion para obtener la pantalla de Seleccion Cobertura
	* @param params
	* @return List<Map<String, String>> datosValidacion
	*/
	public String pantallaSeleccionCobertura() {
		logger.debug(""
			+ "\n########################################"
			+ "\n###### pantallaSeleccionCobertura ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
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

			imap.put("item",gc.getItems());
			seccion = "COLUMNAS";
			componentes = pantallasManager.obtenerComponentes(null, null, null, null, null, cdrol, pantalla, seccion, null);
			gc.generaComponentes(componentes, true, true, false, true, false, false);
			imap.put("columnas",gc.getColumns());
			imap.put("modelFacturas", gc.getFields());
		}
		catch(Exception ex){
			logger.error("error al cargar pantalla de seleccion de cobertura : {}", ex.getMessage(), ex);
		}
		logger.debug(""
			+ "\n###### pantallaSeleccionCobertura ######"
			+ "\n########################################"
		);
		return SUCCESS;
	}

	/**
	* Funcion para obtener la informacion de los usuarios por rol
	* @param params
	* @return List<Map<String, String>> datosValidacion
	*/
	public String obtenerUsuariosPorRol() {
		logger.debug(""
			+ "\n###################################"
			+ "\n###### obtenerUsuariosPorRol ######"
		);
		logger.debug("smap de entrada :{}",smap);
		try {
			String cdsisrol = smap.get("cdsisrol");
			slist1  = siniestrosManager.obtenerUsuariosPorRol(cdsisrol);
			success = true;
		}
		catch(Exception ex) {
			logger.error("error al obtener usuarios por rol : {}", ex.getMessage(), ex);
			slist1  = new ArrayList<Map<String,String>>();
			success = false;
		}
		logger.info(""
			+ "\n###### obtenerUsuariosPorRol ######"
			+ "\n###################################"
		);
		return SUCCESS;
	}

	/**
	* Funcion para obtener los documentos
	* @param params
	* @return List<Map<String, String>> loadList
	*/
	public String loadListaDocumentos(){
		logger.debug(""
			+ "\n####################################"
			+ "\n######  loadListaDocumentos   ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			loadList = siniestrosManager.loadListaDocumentos(params);
		}catch( Exception e){
			logger.error("Error en loadListaDocumentos : {}", e.getMessage(), e);
			success =  false;
			return SUCCESS;
		}
		logger.debug(""
				+ "\n######  loadListaDocumentos   ######"
				+ "\n####################################"
			);
		success = true;
		return SUCCESS;
	}

	/**
	* Funcion para guardar la lista de los documentos
	* @param params Y saveList
	* @return SUCCESS
	*/
	public String guardaListaDocumentos(){
		logger.debug(""
			+ "\n####################################"
			+ "\n###### guardaListaDocumentos  ######"
		);
		logger.debug("params de entrada :{} SaveList : {}",params, saveList);
		try {
			siniestrosManager.guardaEstatusDocumentos(params, saveList);
		}catch( Exception e){
			logger.error("Error en guardaListaDocumentos : {}", e.getMessage(), e);
			success =  false;
			return SUCCESS;
		}
		logger.debug(""
				+ "\n###### guardaListaDocumentos  ######"
				+ "\n####################################"
			);
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion para obtener el total de Penalizacion por cambio de Zona
	* @param existePenalizacion,causaSiniestro,circuloHospAsegurado,zonaTarifiAsegurado,idProveedor,cdRamo
	* @return penalizacionCambioZona
	*/
	private double penalizacionCambioZona(String existePenalizacion, String causaSiniestro, String circuloHospAsegurado,
			String zonaTarifiAsegurado, String idProveedor, String cdRamo) {
		logger.debug(""
			+ "\n####################################"
			+ "\n###### penalizacionCambioZona ######"
		);
		logger.debug("existePenalizacion : {}  causaSiniestro :{}  circuloHospAsegurado :{}  zonaTarifiAsegurado :{}  idProveedor :{}  cdRamo :{} ",
					existePenalizacion,causaSiniestro,circuloHospAsegurado,zonaTarifiAsegurado,idProveedor,cdRamo);
		double penalizacionCambioZona = 0;
		if(causaSiniestro!= null){
			if(!causaSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
				if(!existePenalizacion.equalsIgnoreCase("S")){
					try{
						List<ConsultaProveedorVO> medicos = siniestrosManager.getConsultaListaProveedorMedico(Rol.MEDICO.getCdrol(),idProveedor);
						porcentajePenalizacion = siniestrosManager.validaPorcentajePenalizacion(zonaTarifiAsegurado, medicos.get(0).getZonaHospitalaria(), cdRamo);
						penalizacionCambioZona =  Double.parseDouble(porcentajePenalizacion);
					}catch(Exception ex){
						logger.debug("Error en la obtencion de la consulta : {}", ex.getMessage(), ex);
						penalizacionCambioZona =  Double.parseDouble("0");
					}
				}
			}
		}
		logger.debug(""
			+ "\n###### penalizacionCambioZona ######"
			+ "\n####################################"
		);
		return penalizacionCambioZona;
	}
	
	/**
	* Funcion para obtener el total de Penalizacion Por circulo Hospitalario
	* @param circuloHospAsegurado, circuloHospProveedor, causaSiniestro, ramo
	* @return penaliCirculoHosp
	*/
	private double calcularPenalizacionCirculo(String circuloHospAsegurado, String circuloHospProveedor, String causaSiniestro,String ramo){
		logger.debug(""
			+ "\n#########################################"
			+ "\n###### calcularPenalizacionCirculo ######"
		);
		logger.debug("circuloHospAsegurado : {} circuloHospProveedor : {}  causaSiniestro : {}  ramo : {} ",
					circuloHospAsegurado, circuloHospProveedor, causaSiniestro, ramo);
		double penaliCirculoHosp = 0;
		if(causaSiniestro != null){
			try {
				SimpleDateFormat sdf 	= new SimpleDateFormat("dd/MM/yyyy");
				String feAutorizacion   = sdf.format(new Date());
				HashMap<String, Object> paramPenalizacion = new HashMap<String, Object>();
				paramPenalizacion.put("pv_circuloHosPoliza_i", circuloHospAsegurado);
				paramPenalizacion.put("pv_circuloHosProv_i",circuloHospProveedor);
				paramPenalizacion.put("pv_cdramo_i",ramo);
				paramPenalizacion.put("pv_feautori_i", feAutorizacion);
				porcentajePenalizacion = siniestrosManager.penalizacionCirculoHospitalario(paramPenalizacion);
				penaliCirculoHosp      = Double.parseDouble(porcentajePenalizacion);
			} catch (Exception ex) {
				logger.debug("Error en la obtencion de la consulta : {}", ex.getMessage(), ex);
				penaliCirculoHosp =  Double.parseDouble("0");
			}
		}
		logger.debug(""
			+ "\n###### calcularPenalizacionCirculo ######"
			+ "\n#########################################"
		);
		return penaliCirculoHosp;
	}
	
	/**
	* Funcion para obtener el Total de Penalizaciones
	* @param penalizacionCambioZona, penalizacionCirculoHosp, causaSiniestro, copagoOriginal, tipoCopago, proveedor,ramo, fechaOcurrencia
	* @return copagoFinal
	*/
	private String calcularTotalPenalizacion(double penalizacionCambioZona, double penalizacionCirculoHosp, String causaSiniestro, String copagoOriginal, String tipoCopago,String proveedor,String ramo, String fechaOcurrencia) {
		logger.debug(""
			+ "\n#######################################"
			+ "\n###### calcularTotalPenalizacion ######"
		);
		logger.debug("penalizacionCambioZona : {} penalizacionCirculoHosp : {} causaSiniestro : {} copagoOriginal : {} tipoCopago : {} proveedor : {} ramo : {} fechaOcurrencia : {} ",
				penalizacionCambioZona, penalizacionCirculoHosp, causaSiniestro, copagoOriginal, tipoCopago, proveedor,ramo, fechaOcurrencia);
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
		if(causaSiniestro != null){
			if(!causaSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
				//1.- Verificamos el el Ramo
				if(ramo.equalsIgnoreCase("2") || ramo.equalsIgnoreCase("7")){ //SALUD VITAL
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
						datosInformacionAdicional = siniestrosManager.listaConsultaCirculoHospitalarioMultisalud(proveedor,ramo,renderFechas.parse(fechaOcurrencia));
						logger.debug("Total de registros {}",datosInformacionAdicional.size());
						if(datosInformacionAdicional.size() > 0){
							logger.debug("Multi Incremento :{} ",datosInformacionAdicional.get(0).get("MULTINCREMENTO"));
							logger.debug("Hospital Plus : {}   ",datosInformacionAdicional.get(0).get("HOSPITALPLUS"));
							logger.debug("% Incremento : {}    ",datosInformacionAdicional.get(0).get("PORCINCREMENTO"));

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
						}else{
							copagoFinal = copagoPenaPorcentaje+"|"+copagoPenaPesos;
						}
					} catch (Exception e) {
						logger.error("error al obtener los datos del proveedor : {}", e.getMessage(), e);
					}
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
		logger.debug(""
				+ "\n###### calcularTotalPenalizacion ######"
				+ "\n#######################################"
			);
		return copagoFinal;
	}
	
	/**
	 * Funcion para generar el calculo de siniestros para la nueva version de la pantalla
	 * @param params
	 * @return Exito en el pago del Siniestro
	 */
	public String generarCalculoSiniestros() {
		logger.debug(""
			+ "\n#######################################"
			+ "\n###### Generar calculoSiniestros ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			Map<String,String> factura        = null;
			Map<String,String> siniestroIte   = null;
			Map<String,String> proveedor      = null;
			Map<String,String> siniestro      = null;
			List<Map<String,String>>conceptos = null;
			
			slist2                  		  = new ArrayList<Map<String,String>>();
			slist3                  		  = new ArrayList<Map<String,String>>();
			llist1                  		  = new ArrayList<List<Map<String,String>>>();
			lhosp                   		  = new ArrayList<Map<String,String>>();
			lpdir                   		  = new ArrayList<Map<String,String>>();
			lprem                   		  = new ArrayList<Map<String,String>>();
			datosPenalizacion       		  = new ArrayList<Map<String,String>>();
			datosCoberturaxCal      		  = new ArrayList<Map<String,String>>();
			listaImportesWS         		  = new ArrayList<Map<String,String>>();
			String                ntramite    = params.get("ntramite");
			UserVO                usuario     = (UserVO)session.get("USUARIO");
			String                cdrol       = usuario.getRolActivo().getClave();
			
			this.facturasxSiniestro=new ArrayList<Map<String,Object>>();
			imap = new HashMap<String,Item>();
			Map<String,String> tramite     = siniestrosManager.obtenerTramiteCompleto(ntramite);
			logger.debug("Paso 1.- Tramite : {}",tramite);
			smap = tramite;
			List<Map<String,String>> facturasAux = siniestrosManager.obtenerFacturasTramite(ntramite);
			logger.debug("Paso 2.- Listado Factura : {}",facturasAux.size());
			if(tramite==null||facturasAux==null){
				throw new Exception("No se encontro tramite/facturas para el tramite");
			}

			siniestrosManager.movTimpsini(Constantes.DELETE_MODE, null, null, null, null,
					null, null, null, null, null,
					ntramite, null, null, null, null, null, false);
			logger.debug("Paso 3.- Eliminacion de TIMPSINI");
			boolean esPagoDirecto = false;
			//4.- Verificamos el tipo de pago
			if(TipoPago.DIRECTO.getCodigo().equals(tramite.get("OTVALOR02"))){
				esPagoDirecto = true;
			}
			logger.debug("Paso 4.- Es pago Directo : {} ",esPagoDirecto);
			
			/***************************** 		P A G O		D I R E C T O  		*************************/
			if(TipoPago.DIRECTO.getCodigo().equals(tramite.get("OTVALOR02"))) {
				logger.debug("Paso 5.- EL PROCESO DE PAGO ES DIRECTO ");
				smap.put("PAGODIRECTO","S");
				smap2     = facturasAux.get(0);
				proveedor = siniestrosManager.obtenerDatosProveedor(facturasAux.get(0).get("CDPRESTA"));
				logger.debug("Paso 6.- Datos del Proveedor : {}",proveedor);
				smap3     = proveedor;
				double ivaprov = Double.parseDouble(proveedor.get("IVA")); 
				double cedprov = Double.parseDouble(proveedor.get("CEDULAR"));
				double isrprov = Double.parseDouble(proveedor.get("ISR"));
				
				for(int i = 0; i < facturasAux.size(); i++){
					this.aseguradosxSiniestro = new ArrayList<Map<String,Object>>();
					factura = facturasAux.get(i);
					logger.debug("Paso 7.- Recorremos las Facturas  - El proceso i : {} de la factura : {}",i,factura.get("NFACTURA"));

					Map<String,Object>facturaObj = new HashMap<String,Object>();
					facturaObj.putAll(factura);
					this.facturasxSiniestro.add(facturaObj);
					
					List<Map<String,String>> siniestros = siniestrosManager.listaSiniestrosMsiniesTramite(ntramite,factura.get("NFACTURA"),null);
					logger.debug("Paso 8.- Obtenemos los Siniestros Maestros (MSINIEST) : {}",siniestros);
					
					conceptos = siniestrosManager.P_GET_CONCEPTOS_FACTURA(null, tramite.get("CDRAMO"), null, null, null, null, null, 
										null, null, factura.get("NFACTURA"),tramite.get("CDTIPSIT"));
					logger.debug("Paso 9.- Obtenemos la informacion de los conceptos de la factura : {}", conceptos);

					for( int j= 0; j < siniestros.size();j++){
						String	aplicaPenalCircHosp		  = "S";
						String	aplicaPenalZonaHosp		  = "S";
						
						logger.debug("Paso 10.- Recorremos los Siniestros - El proceso j : {} Siniestro : {}",j,siniestros.get(j));
						siniestroIte    = siniestros.get(j);
						
						if(StringUtils.isNotBlank(siniestroIte.get("NMAUTSER"))){
							List<AutorizacionServicioVO> lista = siniestrosManager.getConsultaAutorizacionesEsp(siniestroIte.get("NMAUTSER"));
							logger.debug("Paso 10.1.- Verificamos la informacion si tiene Autorizacion de servicio : {} ",lista);
							aplicaPenalCircHosp		  = lista.get(0).getAplicaCirHos()+"";
							aplicaPenalZonaHosp		  = lista.get(0).getAplicaZonaHosp()+"";
						}
						
						Map<String,Object>aseguradoObj = new HashMap<String,Object>();
						aseguradoObj.putAll(siniestroIte);
						this.aseguradosxSiniestro.add(aseguradoObj);
						String cdunieco 				  = siniestroIte.get("CDUNIECO");
						String cdramo   				  = siniestroIte.get("CDRAMO");
						String estado   				  = siniestroIte.get("ESTADO");
						String nmpoliza 				  = siniestroIte.get("NMPOLIZA");
						String nmsuplem 				  = siniestroIte.get("NMSUPLEM");
						String nmsituac 				  = siniestroIte.get("NMSITUAC");
						String aaapertu 				  = siniestroIte.get("AAAPERTU");
						String status   				  = siniestroIte.get("STATUS");
						String cdtipsit 				  = siniestroIte.get("CDTIPSIT");
						String nmsinies 				  = siniestroIte.get("NMSINIES");
						String nfactura 				  = factura.get("NFACTURA");
						
						double penalizacionCambioZona 	  = 0d;
						double penalizacionCirculoHosp	  = 0d;
						String aplicaIVA				  = "S";
						String seleccionAplica			  = "D";
						String ivaRetenido				  = "N";
						double deducibleSiniestroIte      = 0d;
						double copagoAplicadoSiniestroIte = 0d;
						double cantidadCopagoSiniestroIte = 0d;
						String penalizacionPesos 		  = "0";
						String penalizacionPorcentaje     = "0";
						boolean existeCobertura           = false;

						Map<String,String>autorizacionesFactura = siniestrosManager.obtenerAutorizacionesFactura(
						cdunieco, cdramo, estado, nmpoliza, nmsuplem,
						nmsituac, aaapertu, status, nmsinies, nfactura);
						facturaObj.put("AUTMEDIC"+nmsinies,autorizacionesFactura.get("AUTMEDIC"));
						facturaObj.put("COMMENME"+nmsinies,autorizacionesFactura.get("COMMENME"));
						facturaObj.put("AUTRECLA"+nmsinies,autorizacionesFactura.get("AUTRECLA"));
						facturaObj.put("COMMENAR"+nmsinies,autorizacionesFactura.get("COMMENAR"));
						logger.debug("Paso 11.- Autorizacion de la informacion de la factura : {}", autorizacionesFactura);
						
						List<Map<String,String>> informacionGral = siniestrosManager.obtieneDatosGeneralesSiniestro(cdunieco, cdramo,
									estado, nmpoliza,nmsituac, nmsuplem, status, aaapertu, nmsinies, factura.get("NTRAMITE"));
						logger.debug("Paso 12.- Datos generales del Siniestro : {} ",informacionGral);
						
						if(informacionGral.size()> 0){
							aseguradoObj.put("CAUSASINIESTRO", informacionGral.get(0).get("CDCAUSA"));
							if(informacionGral.get(0).get("CDCAUSA").toString().equalsIgnoreCase(CausaSiniestro.ENFERMEDAD.getCodigo())){
								HashMap<String, Object> paramCobertura = new HashMap<String, Object>();
								paramCobertura.put("pv_cdunieco_i",cdunieco);
								paramCobertura.put("pv_estado_i",estado);
								paramCobertura.put("pv_cdramo_i",cdramo);
								paramCobertura.put("pv_nmpoliza_i",nmpoliza);
								paramCobertura.put("pv_nmsituac_i",nmsituac);
								paramCobertura.put("pv_cdgarant_i",null);
								List<CoberturaPolizaVO> listaCobertura = siniestrosManager.getConsultaCoberturaAsegurado(paramCobertura);
								logger.debug("Paso 13.- Listado de Coberturas  : {} ",listaCobertura);
								for(int j1 = 0 ;j1 < listaCobertura.size();j1++){
									if(listaCobertura.get(j1).getCdgarant().toString().equalsIgnoreCase("7EDA")){
										existeCobertura = true;
									}
								}
							}
						}else{
							aseguradoObj.put("CAUSASINIESTRO", CausaSiniestro.ENFERMEDAD.getCodigo());
						}
						
						Map<String,String>copagoDeducibleSiniestroIte =siniestrosManager.obtenerCopagoDeducible(
									cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura,tramite.get("OTVALOR02"), cdtipsit);
						logger.debug("Paso 13.- Informacion Deducible/Copago : {}",copagoDeducibleSiniestroIte);
						
						String tipoFormatoCalculo         = copagoDeducibleSiniestroIte.get("FORMATOCALCULO");
						String calculosPenalizaciones     = copagoDeducibleSiniestroIte.get("PENALIZACIONES");
						facturaObj.put("TIPOFORMATOCALCULO",""+tipoFormatoCalculo);
						facturaObj.put("CALCULOSPENALIZACIONES",""+calculosPenalizaciones);
						logger.debug("Paso 14.- Aplica Penalizacion : {} ",calculosPenalizaciones);
						if(calculosPenalizaciones.equalsIgnoreCase("1")){
							HashMap<String, Object> paramExclusion = new HashMap<String, Object>();
							paramExclusion.put("pv_cdunieco_i",cdunieco);
							paramExclusion.put("pv_estado_i",estado);
							paramExclusion.put("pv_cdramo_i",cdramo);
							paramExclusion.put("pv_nmpoliza_i",nmpoliza);
							paramExclusion.put("pv_nmsituac_i",nmsituac);
							if(cdramo.equalsIgnoreCase("2") || cdramo.equalsIgnoreCase("7")){
								logger.debug("Valor de aplicaPenalZonaHosp : {} ",aplicaPenalZonaHosp);
								logger.debug("Valor de aplicaPenalCircHosp : {} ",aplicaPenalCircHosp);
								if(aplicaPenalZonaHosp.equalsIgnoreCase("N")){
									penalizacionCambioZona = 0d;
								}else{
									existePenalizacion = siniestrosManager.validaExclusionPenalizacion(paramExclusion);
									penalizacionCambioZona = penalizacionCambioZona(existePenalizacion,informacionGral.get(0).get("CDCAUSA"),informacionGral.get(0).get("CIRHOSPI"),
									informacionGral.get(0).get("DSZONAT"),informacionGral.get(0).get("CDPROVEE"),cdramo);
								}
								if(aplicaPenalCircHosp.equalsIgnoreCase("N")){
									penalizacionCirculoHosp = 0d;
								}else{
									penalizacionCirculoHosp = calcularPenalizacionCirculo(informacionGral.get(0).get("CIRHOSPI"), informacionGral.get(0).get("CIRHOPROV"),informacionGral.get(0).get("CDCAUSA"), cdramo);
								}
								aseguradoObj.put("PENALIZACIONCAMBIOZONA",""+penalizacionCambioZona);
								aseguradoObj.put("PENALIZACIONCIRCULOHOSP",""+penalizacionCirculoHosp);
							}else{
								aseguradoObj.put("PENALIZACIONCAMBIOZONA",""+penalizacionCambioZona);
								aseguradoObj.put("PENALIZACIONCIRCULOHOSP",""+penalizacionCirculoHosp);
							}
						}else{
							aseguradoObj.put("PENALIZACIONCAMBIOZONA",""+penalizacionCambioZona);
							aseguradoObj.put("PENALIZACIONCIRCULOHOSP",""+penalizacionCirculoHosp);
						}
						logger.debug("Paso 15.- Existe Exclusion de Penalizacion : {} ",existePenalizacion);
						logger.debug("Paso 16.- Penalizacion por Cambio de Zona : {} ",penalizacionCambioZona);
						logger.debug("Paso 17.- Penalizacion por Circulo Hospitalario : {} ",penalizacionCirculoHosp);
						
						String calcularTotalPenalizacion = calcularTotalPenalizacion(penalizacionCambioZona,penalizacionCirculoHosp,informacionGral.get(0).get("CDCAUSA"),
																					copagoDeducibleSiniestroIte.get("COPAGO"),copagoDeducibleSiniestroIte.get("TIPOCOPAGO"),
																					informacionGral.get(0).get("CDPROVEE"),cdramo, informacionGral.get(0).get("FEOCURRE"));
						logger.debug("Paso 18.- Total Penalizacion : {} ",calcularTotalPenalizacion);
						
						aseguradoObj.put("TOTALPENALIZACIONGLOBAL",""+calcularTotalPenalizacion);
						String penalizacionT[] = calcularTotalPenalizacion.split("\\|");
						penalizacionPorcentaje = penalizacionT[0].toString();
						penalizacionPesos = penalizacionT[1].toString();
						aseguradoObj.put("COPAGOPORCENTAJES",penalizacionPorcentaje);
						aseguradoObj.put("COPAGOPESOS",penalizacionPesos);
						
						List<Map<String, String>> listaFactura = siniestrosManager.P_GET_FACTURAS_SINIESTRO(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, cdtipsit);
						logger.debug("Paso 19.- Informacion de la Factura aplicacion de IVA's : {} ",listaFactura);
						
						if(listaFactura.get(0).get("APLICA_IVA") != null){
							aplicaIVA       = listaFactura.get(0).get("APLICA_IVA");
							seleccionAplica = listaFactura.get(0).get("ANTES_DESPUES");
							ivaRetenido     = listaFactura.get(0).get("IVARETENIDO");
							if(!StringUtils.isNotBlank(ivaRetenido)){
								ivaRetenido = "N";
							}
						}
						
						String sDeducibleSiniestroIte     = copagoDeducibleSiniestroIte.get("DEDUCIBLE").replace(",","");
						String sCopagoSiniestroIte        = copagoDeducibleSiniestroIte.get("COPAGO").replace(",", "");
						String tipoCopagoSiniestroIte     = copagoDeducibleSiniestroIte.get("TIPOCOPAGO");
						
						if(StringUtils.isNotBlank(sDeducibleSiniestroIte)
							&&(!sDeducibleSiniestroIte.equalsIgnoreCase("na"))
							&&(!sDeducibleSiniestroIte.equalsIgnoreCase("no"))
						){
							try{
								deducibleSiniestroIte = Double.valueOf(sDeducibleSiniestroIte);
							}
							catch(Exception ex){
								logger.debug(""
									+ "\n### ERROR ##################################################"
									+ "\n### no es numero deducible: '"+sDeducibleSiniestroIte+"' ###"
									+ "\n############################################################"
								);
								deducibleSiniestroIte = 0d;
							}
						}
						
						if(cdramo.toString().equalsIgnoreCase("7")){//GMMI
							if(existeCobertura == true){
								deducibleSiniestroIte = 0d;
							}
						}
						logger.debug("Paso 20.- Valor de deducibleSiniestroIte : {} ",deducibleSiniestroIte);
						
						if(StringUtils.isNotBlank(sCopagoSiniestroIte)
							&&(!sCopagoSiniestroIte.equalsIgnoreCase("na"))
							&&(!sCopagoSiniestroIte.equalsIgnoreCase("no"))
						){
							try {
									cantidadCopagoSiniestroIte = Double.valueOf(sCopagoSiniestroIte);
							}
							catch(Exception ex){
								logger.debug(""
									+ "\n### ERROR ############################################"
									+ "\n### no es numero copago: '"+sCopagoSiniestroIte+"' ###"
									+ "\n######################################################"
								);
								cantidadCopagoSiniestroIte = 0d;
							}
						}
						logger.debug("Paso 21.- Valor de cantidadCopagoSiniestroIte : {} ",cantidadCopagoSiniestroIte);
						
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
						for(int k = 0; k < conceptos.size() ; k++){
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
							){
								conceptosxSiniestro.add(concepto);
								if(tipoFormatoCalculo.equalsIgnoreCase("1")) {
									logger.debug("--->>>>>>> HOSPITALIZACION");
									double PTIMPORT    = Double.parseDouble(concepto.get("PTIMPORT"));
									double DESTOPOR    = Double.parseDouble(concepto.get("DESTOPOR"));
									double DESTOIMP    = Double.parseDouble(concepto.get("DESTOIMP"));
									double PTPRECIO    = Double.parseDouble(concepto.get("PTPRECIO")) * Double.parseDouble(concepto.get("CANTIDAD"));
									boolean copagoPorc = false;
									String scopago     = concepto.get("COPAGO");
									
									if(scopago.equalsIgnoreCase("no") ||scopago.equalsIgnoreCase("na"));{
										scopago="0";
									}
									
									if(StringUtils.isNotBlank(scopago)){
										if(scopago.contains("%")){
											copagoPorc = true;
										}
										scopago = scopago.replace("%", "").replace("$", "");
										if(copagoPorc) {
											DESTOPOR = DESTOPOR+Double.valueOf(scopago);
										}
										else{
											DESTOIMP=DESTOIMP+Double.valueOf(scopago);
										}
									}
									
									double hPTIMPORT 	= Double.parseDouble(hosp.get("PTIMPORT"));
									double hDESTO    	= Double.parseDouble(hosp.get("DESTO"));
									double hIVA      	= Double.parseDouble(hosp.get("IVA"));
									double hISR      	= Double.parseDouble(hosp.get("IMPISR"));
									double hICED      	= Double.parseDouble(hosp.get("IMPCED"));
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
									
									logger.debug("Concepto importe 			    : {} ",PTIMPORT);
									logger.debug("Concepto desto 			    : {} ",DESTOPOR);
									logger.debug("Concepto destoimp 		    : {} ",DESTOIMP);
									logger.debug("Usando iva proveedor          : {} ",ivaprov);
									logger.debug("Concepto copago               : {} ",scopago);
									logger.debug("Concepto desto + copago %     : {} ",DESTOPOR);
									logger.debug("Concepto destoimp + copago $  : {} ",DESTOIMP);
									
									logger.debug("#### VALORES DEL VECTOR #####");
									logger.debug("Concepto hPTIMPORT total      : {} ",hPTIMPORT);
									logger.debug("Concepto hDESTO total         : {} ",hDESTO);
									logger.debug("Concepto hIVA total           : {} ",hIVA);
									logger.debug("Concepto hISR total 		    : {} ",hISR);
									logger.debug("Concepto hPRECIO total        : {} ",hPRECIO);
									logger.debug("Concepto hDESCPRECIO total    : {} ",hDESCPRECIO);
									logger.debug("Concepto hICED total          : {} ",hICED);
									
									logger.debug("<<<<<<<--- HOSPITALIZACION");
								}else {
									logger.debug("--->>>>>>> PAGO DIRECTO DIFERENTE A HOSPITALIZACION Y AYUDA DE MATERNIDAAD");
									double precioArancel   	 = 0d;
									double descuentoPorc   	 = 0d;
									double descuentoImpo   	 = 0d;
									boolean copagoPorc 		 = false;
									double  copago     		 = 0d;
									double  copagoAplicado   = 0d;

									Map<String,String>row = new HashMap<String,String>();
									row.putAll(concepto);

									double cantidad 		= Double.valueOf(row.get("CANTIDAD"));

									if(StringUtils.isNotBlank(row.get("IMP_ARANCEL"))) {
										precioArancel 		= Double.valueOf(row.get("IMP_ARANCEL"));
									}
									row.put("IMP_ARANCEL",precioArancel+"");

									double subtotalArancel 	= cantidad*precioArancel;//++
									row.put("SUBTTARANCEL",subtotalArancel+"");

									if(StringUtils.isNotBlank(row.get("DESTOPOR"))){
										descuentoPorc 		= Double.parseDouble(row.get("DESTOPOR"));
									}

									if(StringUtils.isNotBlank(row.get("DESTOIMP"))){
										descuentoImpo 		= Double.parseDouble(row.get("DESTOIMP"));
									}

									double descuentoAplicado = (subtotalArancel*(descuentoPorc/100d))+descuentoImpo;
									row.put("DESTOAPLICA",descuentoAplicado+"");

									double subtotalDescuento = subtotalArancel-descuentoAplicado;//++
									row.put("SUBTTDESCUENTO",subtotalDescuento+""); // SUBTOTAL A OCUPAR CUANDO SEA ANTES DE COPAGO

									if(aplicaIVA.equalsIgnoreCase("S")){
										if(seleccionAplica.equalsIgnoreCase("A")){ // ANTES DEL COPAGO
											double iVaaplicaAntes = subtotalDescuento*(ivaprov/100d);
											row.put("IVAAPLICA",iVaaplicaAntes+"");
										}
									}

									String scopago 			 = concepto.get("COPAGO");
									String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");

									if(causadelSiniestro == ""||causadelSiniestro == null){
										causadelSiniestro = CausaSiniestro.ENFERMEDAD.getCodigo();
									}
									if(causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
										copagoAplicado    = 0d;
									}else{
										if(StringUtils.isNotBlank(scopago)){
											if(scopago.equalsIgnoreCase("na") || scopago.equalsIgnoreCase("no")){
												copagoAplicado = 0d;
											}else{
												if(scopago.contains("%")){
													copagoPorc = true;
												}
												scopago=scopago.replace("%", "").replace("$", "").replaceAll(",", "");
												copago=Double.valueOf(scopago);
												if(copagoPorc) {
													copagoAplicado=(subtotalDescuento*(copago/100d));
												}
												else {
													copagoAplicado=copago * cantidad;
												}
											}
										}
									}
									row.put("COPAGOAPLICA",copagoAplicado+"");
									double subtotalCopago    = subtotalDescuento - copagoAplicado;//++
									row.put("SUBTTCOPAGO",subtotalCopago+"");
									
									double israplicado       = subtotalCopago*(isrprov/100d);//++
									row.put("ISRAPLICA",israplicado+"");
									
									double subtotalImpuestos = subtotalCopago-(israplicado+0d);
									
									double totalISRMostrar   = Double.parseDouble(mpdir.get("iSRMostrar"));
									totalISRMostrar 		+= israplicado;
									mpdir.put("iSRMostrar",totalISRMostrar+"");
									
									double cedularaplicado   = subtotalCopago*(cedprov/100d);//++
									row.put("CEDUAPLICA",cedularaplicado+"");
									
									logger.debug("Concepto cantidad 			 : {} ",cantidad);
									logger.debug("Concepto precioArancel 		 : {} ",precioArancel);
									logger.debug("Concepto subtotalArancel 		 : {} ",subtotalArancel);
									logger.debug("Concepto descuentoAplicado 	 : {} ",descuentoAplicado);
									logger.debug("Concepto subtotalDescuento 	 : {} ",subtotalDescuento);
									logger.debug("Concepto copagoAplicado        : {} ",copagoAplicado);
									logger.debug("Concepto subtotalCopago 		 : {} ",subtotalCopago);
									logger.debug("Concepto israplicado 			 : {} ",israplicado);
									logger.debug("Concepto subtotalImpuestos 	 : {} ",subtotalImpuestos);
									logger.debug("Concepto base totalISRMostrar  : {} ",totalISRMostrar);
									logger.debug("Concepto cedularaplicado 		 : {} ",cedularaplicado);
									
									subtotalImpuestos 		 = subtotalImpuestos - cedularaplicado;
									row.put("SUBTTIMPUESTOS",subtotalImpuestos+"");
									
									double ivaaplicado  = 0d;
									double ivaRetenidos = 0d;
									double ptimportauto = 0d;
									
									if(aplicaIVA.equalsIgnoreCase("S")){
										if(seleccionAplica.equalsIgnoreCase("D")){
											ivaaplicado       = subtotalCopago*(ivaprov/100d);
											row.put("IVAAPLICA",ivaaplicado+"");
											if(ivaRetenido.equalsIgnoreCase("S")){
												ivaRetenidos      = ((2d * ivaaplicado)/3);
												row.put("IVARETENIDO",ivaRetenidos+"");
											}else{
												ivaRetenidos      = 0d;
												row.put("IVARETENIDO",ivaRetenidos+"");
											}
											ptimportauto      = (subtotalImpuestos - ivaRetenidos)+ivaaplicado;
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
										ivaaplicado       = 0d;
										ivaRetenidos      = 0d;
										row.put("IVAAPLICA",ivaaplicado+"");
										row.put("IVARETENIDO",ivaRetenidos+"");
										ptimportauto      = (subtotalImpuestos-ivaRetenidos)+ivaaplicado;
										row.put("PTIMPORTAUTO",ptimportauto+"");
									}
									
									double totalIVAMostrar = Double.parseDouble(mpdir.get("ivaTotalMostrar"));
									logger.debug("Concepto base totalIVAMostrar : {} ",totalIVAMostrar);
									totalIVAMostrar += ivaaplicado;
									logger.debug("Sumatoria totalIVAMostrar : {} ",totalIVAMostrar);
									mpdir.put("ivaTotalMostrar",totalIVAMostrar+"");
									
									double totalIVARetenidoMostrar = Double.parseDouble(mpdir.get("ivaRetenidoMostrar"));
									logger.debug("Concepto base totalIVARetenidoMostrar : {} ",totalIVARetenidoMostrar);
									
									totalIVARetenidoMostrar += ivaRetenidos;
									logger.debug("Sumatoria totalIVAMostrar      : {} ",totalIVARetenidoMostrar);
									mpdir.put("ivaRetenidoMostrar",totalIVARetenidoMostrar+"");
									
									double ptimport = Double.parseDouble(row.get("PTIMPORT"));
									double valorusado      = ptimportauto;
									
									String autmedic = row.get("AUTMEDIC");
									if(StringUtils.isNotBlank(autmedic)&&autmedic.equalsIgnoreCase("S")){
										valorusado = ptimport;
									}
									row.put("VALORUSADO",valorusado+"");
									
									logger.debug("Concepto ptimport              : {} ",ptimport);
									logger.debug("Concepto valorusado            : {} ",valorusado);
									
									double totalGrupo = Double.parseDouble(mpdir.get("total"));
									logger.debug("Concepto base totalGrupo       : {} ",totalGrupo);
									totalGrupo += valorusado;
									logger.debug("Sumatoria totalGrupo           : {} ",totalGrupo);
									mpdir.put("total",totalGrupo+"");
									
									double totalGrupoCedular = Double.parseDouble(mpdir.get("totalcedular"));
									logger.debug("Concepto base totalGrupoCedular  : {} ",totalGrupoCedular);
									totalGrupoCedular += cedularaplicado;
									logger.debug("Sumatoria totalGrupoCedular      : {} ",totalGrupoCedular);
									mpdir.put("totalcedular",totalGrupoCedular+"");
									
									concepto.putAll(row);
									logger.debug("<<PAGO DIRECTO DIFERENTE A HOSPITALIZACION Y AYUDA DE MATERNIDAD");
								}
							}
						}//FIN DE CONCEPTOS
						aseguradoObj.put("conceptosAsegurado", conceptosxSiniestro);
						
						//hospitalizacion
						//	logger.debug("######  HOSPITALIZACI�N Y AYUDA DE MATERNIDAD WS ######");
						if(tipoFormatoCalculo.equalsIgnoreCase("1")){
							logger.debug("--->>>> WS del siniestro iterado Hospitalizacion y Ayuda de Maternidad");
							logger.debug("deducible siniestro iterado     : {} ",sDeducibleSiniestroIte);
							logger.debug("copago siniestro iterado        : {} ",sCopagoSiniestroIte);
							logger.debug("tipo copago siniestro iterado   : {} ",tipoCopagoSiniestroIte);
							
							logger.debug("Hospitalizacion Importe    : {} ",hosp.get("PTIMPORT"));
							logger.debug("Hospitalizacion Descuento  : {} ",hosp.get("DESTO"));
							logger.debug("Hospitalizacion IVA        : {} ",hosp.get("IVA"));
							logger.debug("Hospitalizacion Deducible  : {} ",deducibleSiniestroIte);
							
							double hPTIMPORT = Double.valueOf(hosp.get("PTIMPORT"));
							double DESTOIMP  = Double.valueOf(hosp.get("DESTO"));
							double hIVA      = Double.valueOf(hosp.get("IVA"));
							String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
							double subttDesto =0d;
							
							if(!causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){ // Diferente de Accidente
								subttDesto = (hPTIMPORT + DESTOIMP) - (DESTOIMP + deducibleSiniestroIte );
							}else{
								if(cdramo.equalsIgnoreCase("7")){
									subttDesto = (hPTIMPORT + DESTOIMP) - (DESTOIMP + deducibleSiniestroIte );
								}else{
									subttDesto = (hPTIMPORT + DESTOIMP) - (DESTOIMP);
								}
							}
							
							if(StringUtils.isNotBlank(tipoCopagoSiniestroIte)) {
								if(!causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){ //Diferente de Accidente
									copagoAplicadoSiniestroIte = Double.parseDouble(penalizacionPesos) + (subttDesto * ( Double.parseDouble(penalizacionPorcentaje) / 100d ));
								}else{
									copagoAplicadoSiniestroIte= 0d;
								}
							}
							
							importeSiniestroIte = subttDesto - copagoAplicadoSiniestroIte;
							double hIVADesCopago  = importeSiniestroIte*(ivaprov/100d);
							logger.debug("IVA despues de Copago  : {} ",hIVADesCopago);
							
							hosp.put("PTIMPORT_DESCOPAGO" , importeSiniestroIte+"");
							hosp.put("IVA_DESCOPAGO"    , hIVADesCopago+"");
							
							double importeBase = 0d;
							
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
							ivaSiniestroIte = Double.parseDouble(hosp.get("IVA"));
							isrSiniestroIte = Double.valueOf(hosp.get("IMPISR"));
							cedSiniestroIte = Double.valueOf(hosp.get("IMPCED"));
							importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(importeSiniestroIte)).toString());
							importesWSSiniestroIte.put(IMPORTE_WS_IVA     , (new Double(ivaSiniestroIte)    ).toString());
							importesWSSiniestroIte.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroIte)    ).toString());
							importesWSSiniestroIte.put(IMPORTE_WS_ISR     , (new Double(isrSiniestroIte)    ).toString());
							importesWSSiniestroIte.put(IMPORTE_WS_CEDULAR , (new Double(cedSiniestroIte)    ).toString());
							logger.debug("mapa WS siniestro iterado : {} ",importesWSSiniestroIte);
							logger.debug("<<<-- WS del siniestro iterado Hospitalizacion y ayuda de Maternidad");
						}else{
							logger.debug("-->>>> WS del siniestro iterado Diferente de Hospitalizacion y Ayuda de Maternidad ");
							logger.debug("deducible siniestro iterado : {} ",sDeducibleSiniestroIte);
							logger.debug("copago siniestro iterado : {} ",sCopagoSiniestroIte);
							logger.debug("tipo copago siniestro iterado : {} ",tipoCopagoSiniestroIte);
							
							double totalGrupo = Double.valueOf(mpdir.get("total"));
							
							importeSiniestroIte = totalGrupo / ( 1d + ( ivaprov  / 100d ) );
							ivrSiniestroIte = 0d;
							isrSiniestroIte = 0d;
							ivaSiniestroIte = 0d;
							cedSiniestroIte = Double.valueOf(mpdir.get("totalcedular"));
							
							double subttDescuentoSiniestroIte= 0d;
							double subttISRSiniestroIte= 0d;
							double subttcopagototalSiniestroIte=0;
							
							for(Map<String,String>concepto : conceptos) {
								if(concepto.get("CDUNIECO").equals(siniestroIte.get("CDUNIECO"))
									&&concepto.get("CDRAMO").equals(siniestroIte.get("CDRAMO"))
									&&concepto.get("ESTADO").equals(siniestroIte.get("ESTADO"))
									&&concepto.get("NMPOLIZA").equals(siniestroIte.get("NMPOLIZA"))
									&&concepto.get("NMSUPLEM").equals(siniestroIte.get("NMSUPLEM"))
									&&concepto.get("NMSITUAC").equals(siniestroIte.get("NMSITUAC"))
									&&concepto.get("AAAPERTU").equals(siniestroIte.get("AAAPERTU"))
									&&concepto.get("STATUS").equals(siniestroIte.get("STATUS"))
									&&concepto.get("NMSINIES").equals(siniestroIte.get("NMSINIES"))
								){
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
								importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE 	  , (new Double(subttcopagototalSiniestroIte)).toString());
								importesWSSiniestroIte.put(IMPORTE_WS_IVR     	  , (new Double(0d)    ).toString());
							}
							
							importesWSSiniestroIte.put(IMPORTE_WS_IVA     		 , (new Double(ivaSiniestroIte)    ).toString());
							importesWSSiniestroIte.put(IMPORTE_WS_ISR     		 , (new Double(subttISRSiniestroIte)    ).toString());
							importesWSSiniestroIte.put(IMPORTE_WS_CEDULAR 		 , (new Double(cedSiniestroIte)    ).toString());
							logger.debug("mapa WS siniestro iterado: {} ",importesWSSiniestroIte);
							logger.debug("<<WS del siniestro iterado Diferente de Hospitalizacion y Ayuda de Maternidad ");
						}
					}
					facturaObj.put("siniestroPD", aseguradosxSiniestro);
				}
			}
			/***************************** 		P A G O		R E E M B O L S O 		*************************/
			else if(TipoPago.REEMBOLSO.getCodigo().equals(tramite.get("OTVALOR02"))){//TIPO DE PAGO POR REEMBOLSO
				logger.debug("Paso 5.- EL PROCESO DE PAGO REEMBOLSO ");
				
				double importeSiniestroUnico 	= 0d;
				double ivaSiniestroUnico     	= 0d;
				double ivrSiniestroUnico     	= 0d;
				double isrSiniestroUnico     	= 0d;
				double cedularSiniestroUnico 	= 0d;
				double penalizacionCambioZona 	= 0d;
				double penalizacionCirculoHosp 	= 0d;
				double totalPenalizacion 		= 0d;
				double deducibleFacturaIte      = 0d;
				double cantidadCopagoFacturaIte = 0d;
				double copagoAplicadoFacturaIte = 0d;

				Map<String,String> calcxCobe 	= new HashMap<String,String>();
				Map<String,String> penalizacion = new HashMap<String,String>();
				
				List<Map<String,String>> siniestros = siniestrosManager.listaSiniestrosMsiniesTramite(ntramite,null,null);
				logger.debug("Paso 6.- Obtenemos los Siniestros Maestros : {} ",siniestros);
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
				null,
				siniestro.get("CDTIPSIT"));
				logger.debug("Paso 7.- Obtenemos los Conceptos de la Factura : {} ",conceptos);
				slist1     = facturasAux;

				HashMap<String, Object> paramCobertura = new HashMap<String, Object>();
				paramCobertura.put("pv_cdunieco_i",siniestro.get("CDUNIECO"));
				paramCobertura.put("pv_estado_i",siniestro.get("ESTADO"));
				paramCobertura.put("pv_cdramo_i",siniestro.get("CDRAMO"));
				paramCobertura.put("pv_nmpoliza_i",siniestro.get("NMPOLIZA"));
				paramCobertura.put("pv_nmsituac_i",siniestro.get("NMSITUAC"));
				paramCobertura.put("pv_cdgarant_i",null);

				List<CoberturaPolizaVO> listaCobertura = siniestrosManager.getConsultaListaCoberturaPoliza(paramCobertura);
				logger.debug("Paso 8.- Obtenemos la informacion de la Cobertura : {} ",listaCobertura);

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
				
				Map<String,String> facturaIte        = null;
				for(int i = 0; i < facturasAux.size(); i++) {
				facturaIte = facturasAux.get(i);
				Map<String,Object>facturaObj=new HashMap<String,Object>();
				facturaObj.putAll(facturaIte);
				this.facturasxSiniestro.add(facturaObj);
				//reembolso
				Map<String,String>mprem=new HashMap<String,String>(0);
				mprem.put("TOTALNETO" , "0");
				mprem.put("SUBTOTAL"  , "0");
				lprem.add(mprem);
				//reembolso
				
				String destopor = facturaIte.get("DESCPORC");
				if(StringUtils.isBlank(destopor) || destopor  == null) {
					facturaObj.put("DESCPORC","0");
				}
				String destoimp = facturaIte.get("DESCNUME");
				if(StringUtils.isBlank(destoimp)  || destoimp  == null){
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
				String cdtipsit = siniestro.get("CDTIPSIT");
				String nfactura = facturaIte.get("NFACTURA");
				
				boolean existeCobertura = false;
				
				Map<String,String>copagoDeducibleFacturaIte =siniestrosManager.obtenerCopagoDeducible(
					cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura,tramite.get("OTVALOR02"),cdtipsit);
				
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
				siniestro.get("AAAPERTU"),siniestro.get("STATUS"),siniestro.get("NMSINIES") ,facturaIte.get("NFACTURA"),tramite.get("OTVALOR02"),siniestro.get("CDTIPSIT"));

				String tipoFormatoCalculo = copagoDeducibleSiniestroIte.get("FORMATOCALCULO");
				String calculosPenalizaciones = copagoDeducibleSiniestroIte.get("PENALIZACIONES");
				calcxCobe.put("tipoFormatoCalculo",""+tipoFormatoCalculo);
				calcxCobe.put("calculosPenalizaciones",""+calculosPenalizaciones);
				datosCoberturaxCal.add(calcxCobe);
				if(informacionGral.size()>0){
					penalizacion.put("causaSiniestro", informacionGral.get(0).get("CDCAUSA"));
					if(informacionGral.get(0).get("CDCAUSA").toString().equalsIgnoreCase(CausaSiniestro.ENFERMEDAD.getCodigo())){
						HashMap<String, Object> paramDatosCo = new HashMap<String, Object>();
						paramDatosCo.put("pv_cdunieco_i",siniestro.get("CDUNIECO"));
						paramDatosCo.put("pv_estado_i",siniestro.get("ESTADO"));
						paramDatosCo.put("pv_cdramo_i",siniestro.get("CDRAMO"));
						paramDatosCo.put("pv_nmpoliza_i",siniestro.get("NMPOLIZA"));
						paramDatosCo.put("pv_nmsituac_i",siniestro.get("NMSITUAC"));
						paramDatosCo.put("pv_cdgarant_i",null);

						List<CoberturaPolizaVO> listadoCobertura = siniestrosManager.getConsultaCoberturaAsegurado(paramDatosCo);
						//logger.debug("VALOR DE LAS COBERTURAS  P. REEMBOLSO : {} ",listaCobertura);
						for(int j2 = 0 ;j2 < listadoCobertura.size();j2++){
							if(listadoCobertura.get(j2).getCdgarant().toString().equalsIgnoreCase("7EDA")){
								existeCobertura = true;
							}
						}
						logger.debug("existeCobertura : {} ",existeCobertura);
					}
				}else{
					penalizacion.put("causaSiniestro", CausaSiniestro.ENFERMEDAD.getCodigo());
				}
				
				//if(facturaIte.get("CDGARANT").equalsIgnoreCase("18HO")||facturaIte.get("CDGARANT").equalsIgnoreCase("18MA"))
				if(tipoFormatoCalculo.equalsIgnoreCase("1")){
					if(calculosPenalizaciones.equalsIgnoreCase("1")){
						HashMap<String, Object> paramExclusion = new HashMap<String, Object>();
						paramExclusion.put("pv_cdunieco_i",siniestro.get("CDUNIECO"));
						paramExclusion.put("pv_estado_i",siniestro.get("ESTADO"));
						paramExclusion.put("pv_cdramo_i",siniestro.get("CDRAMO"));
						paramExclusion.put("pv_nmpoliza_i",siniestro.get("NMPOLIZA"));
						paramExclusion.put("pv_nmsituac_i",siniestro.get("NMSITUAC"));
						if(siniestro.get("CDRAMO").toString().equalsIgnoreCase("2")){
							//--> SALUD VITAL
							//	1.- Verificamos si existe exclusi�n de penalizaci�n
							existePenalizacion = siniestrosManager.validaExclusionPenalizacion(paramExclusion);
							//2.- Obtenemos la penalizaci�n por cambio de Zona
							penalizacionCambioZona = penalizacionCambioZona(existePenalizacion,informacionGral.get(0).get("CDCAUSA"),informacionGral.get(0).get("CIRHOSPI"),
							informacionGral.get(0).get("DSZONAT"),facturaIte.get("CDPRESTA"),siniestro.get("CDRAMO"));
							//3.- Obtenemos la penalizaci�n por circulo Hospitalario
							List<ConsultaProveedorVO> medicos = siniestrosManager.getConsultaListaProveedorMedico(Rol.MEDICO.getCdrol(),facturaIte.get("CDPRESTA"));
							penalizacionCirculoHosp = calcularPenalizacionCirculo(informacionGral.get(0).get("CIRHOSPI"), medicos.get(0).getCirculo(),informacionGral.get(0).get("CDCAUSA"),siniestro.get("CDRAMO"));
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
				){
					try{
						deducibleFacturaIte = Double.valueOf(sDeducibleFacturaIte);
					}
					catch(Exception ex){
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
				){
					try{
						cantidadCopagoFacturaIte = Double.valueOf(sCopagoFacturaIte);
					}
					catch(Exception ex){
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
				for(Map<String,String>concepto : conceptos){
					if(concepto.get("NFACTURA").equals(facturaIte.get("NFACTURA"))){
						listaConceptosFactura.add(concepto);
						logger.debug(">>REEMBOLSO");
						Map<String,String>row=new HashMap<String,String>();
						row.putAll(concepto);
						
						double ptimport = Double.parseDouble(row.get("PTIMPORT"));
						logger.debug("ptimport : {} ",ptimport);
						
						double ajusteaplica = 0d;
						if(StringUtils.isNotBlank(row.get("PTIMPORT_AJUSTADO"))){
							ajusteaplica = Double.parseDouble(row.get("PTIMPORT_AJUSTADO"));
						}
						logger.debug("ajusteaplica : {} ",ajusteaplica);
						
						double subtotal = ptimport-ajusteaplica;
						logger.debug("subtotal : {} ",subtotal);
						row.put("SUBTOTAL",subtotal+"");
						
						double gtotalneto = Double.parseDouble(mprem.get("TOTALNETO"));
						double gsubtotal  = Double.parseDouble(mprem.get("SUBTOTAL"));
						logger.debug("base totalneto : {} ",gtotalneto);
						logger.debug("base subtotal : {} ",gsubtotal);
						gtotalneto += ptimport;
						gsubtotal  += subtotal;
						logger.debug("new totalneto : {} ",gtotalneto);
						logger.debug("new subtotal : {} ",gsubtotal);
						
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
						if(siniestro.get("CDRAMO").toString().equalsIgnoreCase("7")){
							//GMMI
							if(existeCobertura == true){
								deducibleFacturaIte = 0d;
							}
						}else{
							deducibleFacturaIte = 0d;
						}
					}
				}else{
					String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
					if(causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
						if(siniestro.get("CDRAMO").toString().equalsIgnoreCase("7")){
							//GMMI
							if(existeCobertura == true){
								deducibleFacturaIte = 0d;
							}
						}else{
							deducibleFacturaIte = 0d;
						}
					}
				}
				
				logger.debug(">>Calculando total factura iterada para WS");
				logger.debug("deducible : {} ",deducibleFacturaIte);
				logger.debug("scopago : {} ",sCopagoFacturaIte);
				logger.debug("tipocopago : {} ",tipoCopagoFacturaIte);
				logger.debug("facturaIte.get(DESCPORC) : {} ",facturaIte.get("DESCPORC"));
				double totalFactura  = Double.valueOf(mprem.get("SUBTOTAL"));
				double destoPorFac = 0d;
				double destoImpFac= 0d;
				if(!StringUtils.isBlank(facturaIte.get("DESCPORC")) || !(facturaIte.get("DESCPORC")  == null)){
					destoPorFac = Double.valueOf(facturaIte.get("DESCPORC"));
				}
				if(!StringUtils.isBlank(facturaIte.get("DESCNUME"))  || !(facturaIte.get("DESCNUME")  == null)){
					destoImpFac = Double.valueOf(facturaIte.get("DESCNUME"));
				}
				//double destoPorFac   = Double.valueOf(facturaIte.get("DESCPORC"));
				//double destoImpFac   = Double.valueOf(facturaIte.get("DESCNUME"));
				double destoAplicado = (totalFactura*(destoPorFac/100d)) + destoImpFac;
				logger.debug("subtotal : {} ",totalFactura);
				totalFactura -= destoAplicado;
				logger.debug("subtotal desto : {} ",totalFactura);
				totalFactura -= deducibleFacturaIte;
				logger.debug("subtotal deducible : {} ",totalFactura);
				
				if(StringUtils.isNotBlank(tipoCopagoFacturaIte)){
					String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
					//if(facturaIte.get("CDGARANT").equalsIgnoreCase("18HO")||facturaIte.get("CDGARANT").equalsIgnoreCase("18MA"))
					if(tipoFormatoCalculo.equalsIgnoreCase("1")){
						//verificamos la causa del siniestro
						if(!causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
							//Diferente de accidente
							copagoAplicadoFacturaIte = Double.parseDouble(penalizacionT[1].toString()) + (totalFactura * ( Double.parseDouble(penalizacionT[0].toString()) / 100d ));
						}else{
							copagoAplicadoFacturaIte = 0d;
						}
					}else{
						//COBERTURA DIFERENTE HOSPITALIZACI�N Y AYUDA DE MATERNIDAD
						if(!causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
							if(tipoCopagoFacturaIte.equalsIgnoreCase("$")){
								copagoAplicadoFacturaIte = cantidadCopagoFacturaIte;
							}
							if(tipoCopagoFacturaIte.equalsIgnoreCase("%")){
								copagoAplicadoFacturaIte = totalFactura * ( cantidadCopagoFacturaIte / 100d );
							}
						}else{
							copagoAplicadoFacturaIte = 0d;
						}
					}
				}
				totalFactura -= copagoAplicadoFacturaIte;
				facturaObj.put("TOTALFACTURAIND",totalFactura+"");
				logger.debug("total copago (final) : {} ",totalFactura);
				logger.debug("<<Calculando total factura iterada para WS");
				
				importeSiniestroUnico += totalFactura;
			}
			
			logger.debug(">>WS del siniestro unico");
			importesWSSiniestroUnico.put(IMPORTE_WS_IMPORTE , (new Double(importeSiniestroUnico)).toString());
			importesWSSiniestroUnico.put(IMPORTE_WS_IVA     , (new Double(ivaSiniestroUnico)    ).toString());
			importesWSSiniestroUnico.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroUnico)    ).toString());
			importesWSSiniestroUnico.put(IMPORTE_WS_ISR     , (new Double(isrSiniestroUnico)    ).toString());
			importesWSSiniestroUnico.put(IMPORTE_WS_CEDULAR , (new Double(cedularSiniestroUnico)).toString());
			logger.debug("mapa WS siniestro unico : {} ",importesWSSiniestroUnico);
			logger.debug("<<WS del siniestro unico");
			
		}else{
			/***************************** 		P A G O		I N D E M N I Z A C I O N 		*************************/
			logger.debug("Paso 5.- EL PROCESO DE PAGO DE INDEMNIZACION");
			
			List<Map<String,String>> siniestros = siniestrosManager.listaSiniestrosMsiniesTramite(ntramite,null,null);
			logger.debug("Paso 6.- Obtenemos el valor del Siniestro para el pago : {} ",siniestros.get(0));
			
			siniestro  = siniestros.get(0);
			siniestros = null;
			smap2      = siniestro;
			smap3      = new HashMap<String,String>();
			smap3.put("a","a");
			smap.put("PAGODIRECTO","N");
			slist1     = facturasAux;
			logger.debug("FACTURAS : {} ",slist1);
			
			HashMap<String, Object> paramCobertura = new HashMap<String, Object>();
			paramCobertura.put("pv_cdunieco_i",siniestro.get("CDUNIECO"));
			paramCobertura.put("pv_estado_i",siniestro.get("ESTADO"));
			paramCobertura.put("pv_cdramo_i",siniestro.get("CDRAMO"));
			paramCobertura.put("pv_nmpoliza_i",siniestro.get("NMPOLIZA"));
			paramCobertura.put("pv_nmsituac_i",siniestro.get("NMSITUAC"));
			paramCobertura.put("pv_cdgarant_i",null);

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
			for(int i = 0; i < facturasAux.size(); i++){
				facturaIte = facturasAux.get(i);
				logger.debug("Paso 6.- Factura en proceso : {} Informacion Factura : {} ",i,facturaIte);
				
				Map<String,Object>facturaObj=new HashMap<String,Object>();
				facturaObj.putAll(facturaIte);
				this.facturasxSiniestro.add(facturaObj);
				//reembolso
				Map<String,String>mprem=new HashMap<String,String>(0);
				mprem.put("TOTALNETO" , "0");
				mprem.put("SUBTOTAL"  , "0");
				lprem.add(mprem);
				
				if(siniestro.get("CDTIPSIT").equalsIgnoreCase("RI")){
					logger.debug("Paso 7.- El Pago a realizar es Recupera");
					
					PolizaAseguradoVO datosPol = new PolizaAseguradoVO();
					datosPol.setCdunieco(siniestro.get("CDUNIECO"));
					datosPol.setCdramo(siniestro.get("CDRAMO"));
					datosPol.setEstado(siniestro.get("ESTADO"));
					datosPol.setNmpoliza(siniestro.get("NMPOLIZA"));
					
					List<ConsultaDatosGeneralesPolizaVO> lista = consultasAseguradoManager.obtieneDatosPoliza(datosPol);
					String feEfecto = lista.get(0).getFeefecto();
					logger.debug("Paso 8.- Obtenemos la fecha Efecto de la Poliza : {} ",feEfecto);
					
					List<Map<String,String>> datosAdicionales = siniestrosManager.listaSumaAseguradaPeriodoEsperaRec(siniestro.get("CDRAMO"),facturaIte.get("CDGARANT"),facturaIte.get("CDCONVAL"),renderFechas.parse(feEfecto));
					double SumaAsegurada = Double.parseDouble(datosAdicionales.get(0).get("SUMAASEG"));
					logger.debug("Paso 9.- Obtenemos la suma Asegurada : {} ",SumaAsegurada);
					
					List<Map<String,String>> esquemaSum = siniestrosManager.listaEsquemaSumaAseguradaRec(siniestro.get("CDUNIECO"), siniestro.get("CDRAMO"), siniestro.get("ESTADO"), siniestro.get("NMPOLIZA"), siniestro.get("NMSITUAC"));
					double esquemaSumaAseg = Double.parseDouble(esquemaSum.get(0).get("ESQUEMAASEG"));
					logger.debug("Paso 10.- Obtenemos el esquema de suma Asegurada : {} ",esquemaSumaAseg);
					
					double totalFactura = SumaAsegurada * esquemaSumaAseg;
					facturaObj.put("TOTALFACTURAIND",totalFactura+"");
					importeSiniestroUnico += totalFactura;
					
				}else{
					Map<String,String>copagoDeducibleFacturaIte =siniestrosManager.obtenerCopagoDeducible(
							siniestro.get("CDUNIECO"), siniestro.get("CDRAMO"), siniestro.get("ESTADO"), siniestro.get("NMPOLIZA"), siniestro.get("NMSUPLEM"), siniestro.get("NMSITUAC"),
							siniestro.get("AAAPERTU"), siniestro.get("STATUS"), siniestro.get("NMSINIES"), facturaIte.get("NFACTURA"),tramite.get("OTVALOR02"),siniestro.get("CDTIPSIT"));
					logger.debug("copagoDeducibleFacturaIte : {} ",copagoDeducibleFacturaIte);
					
					Map<String,String>rentaDiariaxHospitalizacion =siniestrosManager.obtenerRentaDiariaxHospitalizacion(
						siniestro.get("CDUNIECO"), siniestro.get("CDRAMO"), siniestro.get("ESTADO"), siniestro.get("NMPOLIZA"), siniestro.get("NMSITUAC"), siniestro.get("NMSUPLEM"));
					
					double sumaAsegurada = Double.valueOf(rentaDiariaxHospitalizacion.get("OTVALOR"));
					double beneficioMax = Double.parseDouble(copagoDeducibleFacturaIte.get("BENEFMAX").replace("%",""));
					//OBTENEMOS EL VALOR Y SE REALIZA UN REEMPLA
					Calendar fechas = Calendar.getInstance();
					
					int diasPantalla = 0;
					String fechaEgreso  = facturaIte.get("FFACTURA");
					if(facturaIte.get("FEEGRESO").length() > 0){
						diasPantalla = Integer.parseInt(facturaIte.get("DIASDEDU"));
						fechaEgreso = facturaIte.get("FEEGRESO");
					}
					//fecha inicio
					Calendar fechaInicio = new GregorianCalendar();
					fechaInicio.set(Integer.parseInt(facturaIte.get("FFACTURA").substring(6,10)), Integer.parseInt(facturaIte.get("FFACTURA").substring(3,5)), Integer.parseInt(facturaIte.get("FFACTURA").substring(0,2)));
					Calendar fechaFin = new GregorianCalendar();
					fechaFin.set(Integer.parseInt(fechaEgreso.substring(6,10)), Integer.parseInt(fechaEgreso.substring(3,5)), Integer.parseInt(fechaEgreso.substring(0,2)));
					fechas.setTimeInMillis(fechaFin.getTime().getTime() - fechaInicio.getTime().getTime());
					int totalDias = fechas.get(Calendar.DAY_OF_YEAR);
					
					double totalFactura = sumaAsegurada * (beneficioMax/100d) * (totalDias - diasPantalla);
					facturaObj.put("TOTALFACTURAIND",totalFactura+"");
					importeSiniestroUnico += totalFactura;	
				}
			}

			logger.debug(">>WS del siniestro unico");
			importesWSSiniestroUnico.put(IMPORTE_WS_IMPORTE , (new Double(importeSiniestroUnico)).toString());
			importesWSSiniestroUnico.put(IMPORTE_WS_IVA     , (new Double(ivaSiniestroUnico)    ).toString());
			importesWSSiniestroUnico.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroUnico)    ).toString());
			importesWSSiniestroUnico.put(IMPORTE_WS_ISR     , (new Double(isrSiniestroUnico)    ).toString());
			importesWSSiniestroUnico.put(IMPORTE_WS_CEDULAR , (new Double(cedularSiniestroUnico)).toString());
			logger.debug("mapa WS siniestro unico : {} ",importesWSSiniestroUnico);
			logger.debug("<<WS del siniestro unico");
		}
		
		if(conceptos!=null&&conceptos.size()>0){
			logger.debug("conceptos[0] :{}",conceptos);
		}
		//aqui verificamos toda la informaci�n del WS
		logger.debug("###VALOR A IMPRIMIR#####");
		logger.debug("listaImportesWS :{}",listaImportesWS);
		logger.debug("Tipo de pago :{}",esPagoDirecto);
		logger.debug("facturasxSiniestro :{}",facturasxSiniestro);
			try{
				for(Map<String,String> importe : listaImportesWS){
					logger.debug("Valor de Importe :{}",importe);
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
				logger.debug("VALORES DE LAS FACTURAS---->");
				logger.debug("Total de registros :{}",facturasxSiniestro.size());
				logger.debug("facturasxSiniestro :{}",facturasxSiniestro);
				
				for(Map<String, Object> totalFacturaIte : facturasxSiniestro){
					logger.debug("VALOR DE LAS FACTURAS :{}",totalFacturaIte);
					String ntramiteA     = (String) totalFacturaIte.get("NTRAMITE");
					String nfacturaA     = (String) totalFacturaIte.get("NFACTURA");
					String totalFacturaA = (String) totalFacturaIte.get("TOTALFACTURAIND");
					siniestrosManager.guardarTotalProcedenteFactura(ntramiteA,nfacturaA,totalFacturaA);
				}
				success = true; 
				mensaje = "Datos guardados";
			}catch(Exception ex){
				logger.error("Error al guardaar calculos : {}", ex.getMessage(), ex);
				success = false;
				mensaje = ex.getMessage();
			}
		}catch(Exception ex){
			logger.debug("error al cargar pantalla de calculo de siniestros : {}", ex.getMessage(), ex);
		}
		logger.debug(""
			+ "\n###### Generar calculoSiniestros ######"
			+ "\n#######################################"
		);
		return SUCCESS;
	}
	
	/**
	* Funcion para guardar la cobertura  x factura
	* @param params
	* @return Exito se guarda la cobertura para la factura en especifica
	*/
	public String guardarCoberturaxFactura(){
		logger.debug(""
			+ "\n#######################################"
			+ "\n###### guardarCoberturaxFactura  ######"
		);
		logger.debug("params de entrada :{}",params);
		try{
			String                   ntramite  = params.get("ntramite");
			String                   nfactura  = params.get("nfactura");
			Date                     fefactura = renderFechas.parse(params.get("ffactura"));
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
			siniestrosManager.guardaListaFacMesaControl(ntramite, nfactura, fefactura, cdtipser, cdpresta, ptimport, cdgarant, cdconval, descporc, descnume,cdmoneda,tasacamb,ptimporta,dctonuex,null,null,tipoAccion);
			success = true;
			mensaje = "Cobertura y subcobertura modificada";
		}
		catch(Exception ex){
			success=false;
			logger.error("error al seleccionar la cobertura : {}", ex.getMessage(), ex);
			mensaje = ex.getMessage();
		}
		logger.debug(""
			+ "\n###### guardarCoberturaxFactura  ######"
			+ "\n#######################################"
		);
		return SUCCESS;
	}
	
	/**
	* Funcion para guardar la cobertura
	* @param params
	* @return Exito se guarda la cobertura
	*/
	public String guardarSeleccionCobertura(){
		logger.debug(""
			+ "\n#######################################"
			+ "\n###### guardarSeleccionCobertura ######"
		);
		logger.debug("params de entrada :{}",params);
		try{
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
			for(Map<String,String>factura:facturas){
				String                   nfactura  = factura.get("NFACTURA");
				Date                     fefactura = renderFechas.parse(factura.get("FFACTURA"));
				String                   cdtipser  = factura.get("CDTIPSER");
				String                   cdpresta  = factura.get("CDPRESTA");
				String                   ptimport  = factura.get("PTIMPORT");
				String                   descporc  = factura.get("DESCPORC");
				String                   descnume  = factura.get("DESCNUME");
				String                   cdmoneda  = factura.get("CDMONEDA");
				String                   tasacamb  = factura.get("TASACAMB");
				String                   ptimporta = factura.get("PTIMPORTA");
				String                   dctonuex  = factura.get("DCTONUEX");
				Date                     feegreso  = renderFechas.parse(factura.get("FEEGRESO"));
				String                   diasdedu  = factura.get("DIASDEDU");
				siniestrosManager.guardaListaFacMesaControl(ntramite, nfactura, fefactura, cdtipser, cdpresta, ptimport, cdgarant, cdconval, descporc, descnume,cdmoneda,tasacamb,ptimporta,dctonuex,feegreso,diasdedu,null);
			}
			success = true;
			mensaje = "Tr&aacute;mite actualizado";
		}
		catch(Exception ex) {
			success=false;
			logger.error("error al seleccionar la cobertura : {}", ex.getMessage(), ex);
			mensaje = ex.getMessage();
		}
		logger.debug(""
			+ "\n###### guardarSeleccionCobertura ######"
			+ "\n#######################################"
		);
		return SUCCESS;
	}

	/**
	* Funcion para guardar la cobertura en especifico de acuerdo al tramite
	* @param params
	* @return Exito se guarda la cobertura para la factura en especifica
	*/
	public String guardarSeleccionCoberturaxTramite(){
		logger.debug(""
			+ "\n###############################################"
			+ "\n###### guardarSeleccionCoberturaxTramite ######"
		);
		logger.debug("params de entrada :{}",params);
		try{
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
		catch(Exception ex){
			success=false;
			logger.error("error al seleccionar la cobertura : {}", ex.getMessage(), ex);
			mensaje = ex.getMessage();
		}
		logger.debug(""
			+ "\n###### guardarSeleccionCoberturaxTramite ######"
			+ "\n###############################################"
		);
		return SUCCESS;
	}

	/**
	* Funcion para obtener los afiliados afectados
	* @param params
	* @return listado de los afiliados afectados
	*/
	public String afiliadosAfectadosConsulta(){
		logger.debug(""
			+ "\n########################################"
			+ "\n###### afiliadosAfectadosConsulta ######"
		);
		logger.debug("params de entrada :{}",params);
		try{
			slist1 = siniestrosManager.listaAseguradosTramite(params.get("ntramite"), params.get("nfactura"), params.get("tipoProceso"));
			logger.debug("Total de registros : {}",slist1.size());
		}catch(Exception ex){
			logger.error("error al cargar pantalla de asegurados afectados : {}", ex.getMessage(), ex);
		}
		logger.debug(""
			+ "\n###### afiliadosAfectadosConsulta ######"
			+ "\n########################################"
		);
		return SUCCESS;
	}
	
	/**
	 * Funcion para obtener el periodo de espera
	 * @param params
	 * @return Exito los periodo de espera en dias 
	 */
	public String obtenerPeriodoEspera(){
		logger.debug(""
			+ "\n###############################################"
			+ "\n###### 		obtenerPeriodoEspera 	  ######"
		);
		logger.debug("params de entrada :{}",params);
		try{
			
			PolizaAseguradoVO datosPol = new PolizaAseguradoVO();
			datosPol.setCdunieco(params.get("cdunieco"));
			datosPol.setCdramo(params.get("cdramo"));
			datosPol.setEstado(params.get("estado"));
			datosPol.setNmpoliza(params.get("nmpoliza"));
			
			List<ConsultaDatosGeneralesPolizaVO> lista = consultasAseguradoManager.obtieneDatosPoliza(datosPol);
			String feEfecto = lista.get(0).getFeefecto();
			logger.debug("Paso 1.- Obtenemos la fecha Efecto de la Poliza : {}", feEfecto);
			
			List<Map<String,String>> datosAdicionales = siniestrosManager.listaSumaAseguradaPeriodoEsperaRec(params.get("cdramo"),params.get("cdgarant"),params.get("cdconval"),renderFechas.parse(feEfecto));
			double plazoEsperaCobertura = Double.parseDouble(datosAdicionales.get(0).get("PLAZOESPERA"));
			logger.debug("Paso 2.- Obtenemos el plazo de espera : {}", plazoEsperaCobertura);
			
			List<Map<String,String>> periodoEsperaAsegurado = siniestrosManager.listaPeriodoEsperaAsegurado(params.get("cdunieco"), params.get("cdramo"),params.get("estado"),
															params.get("nmpoliza"), params.get("nmsituac"),renderFechas.parse(params.get("feocurre")));
			double diasAsegurado = Double.parseDouble(periodoEsperaAsegurado.get(0).get("DIAS"));
			logger.debug("Paso 3.- Obtenemos el plazo de espera Asegurado : {}", diasAsegurado);
			
			if(diasAsegurado >= plazoEsperaCobertura){
				mensaje = null;
				success = true;
			}else{
				mensaje = "La intervenci&oacute;n quir&uacute;rgica  no cubre con el periodo de espera : "+datosAdicionales.get(0).get("PLAZOESPERA")+" d&iacute;as";
				success = false;
			}
			logger.debug("Paso 4.- Mesaje : {}", mensaje);
			
		}
		catch(Exception ex){
			success=false;
			logger.error("error al obtener el periodo de espera : {}", ex.getMessage(), ex);
			mensaje = ex.getMessage();
		}
		logger.debug(""
			+ "\n######			obtenerPeriodoEspera	  ######"
			+ "\n###############################################"
		);
		return SUCCESS;
	}
	
	/**
	* Funcion para obtener los montos de los pagos del siniestro
	* @param ntramite
	* @return el monto total de las facturas y el monto de los pagos del Siniestro
	*/
	public String obtieneMontoPagoSiniestro(){
		logger.debug(""
			+ "\n#######################################"
			+ "\n###### obtieneMontoPagoSiniestro ######"
		);
		logger.debug("params de entrada :{}",params);
		try {
			mensaje ="";
			double impFactura = 0d;
			double impxpagar = 0d;
			
			String ntramite = params.get("ntramite");
			String cdramo = params.get("cdramo");
			String tipoPago = params.get("tipoPago");
			if(cdramo.equalsIgnoreCase("1")){ // Recupera
				mensaje = "";
				success = true;
			}else{
				if(!tipoPago.equalsIgnoreCase("3")){
					datosInformacionAdicional = siniestrosManager.obtieneMontoPagoSiniestro(params.get("ntramite"));
					logger.debug("Montos : {}", datosInformacionAdicional);
					if(datosInformacionAdicional.get(0).get("IMPORTEFACTURA")!=null){
						impFactura = Double.parseDouble(datosInformacionAdicional.get(0).get("IMPORTEFACTURA"));
					}
					if(datosInformacionAdicional.get(0).get("MONTOXPAGAR")!=null){
						impxpagar = Double.parseDouble(datosInformacionAdicional.get(0).get("MONTOXPAGAR"));
					}
				}
				if(impFactura >= impxpagar){
					//validamos la informaci�n del cdpresta
					List<Map<String,String>> facturasAux = siniestrosManager.obtenerFacturasTramite(params.get("ntramite"));
					boolean provPendiente = true;
					logger.debug("Valor : {}", provPendiente);
					for(int i = 0; i < facturasAux.size(); i++)
	    			{
						if(facturasAux.get(i).get("CDPRESTA").equalsIgnoreCase("0")){
							provPendiente = false;
						}
	    			}
					
					if(provPendiente){ //true
						mensaje = "";
						success = true;
					}else{
						mensaje = "Proveedor Pendiente - Favor de configurar la informaci&oacute;n.";
						success = false;
					}
				}else{
					mensaje = "Verifica los C&aacute;lculos - El importe total de las facturas es menor al total a pagar.";
					success = false;
				}
			}
		}catch( Exception e){
			logger.error("Error en el metodo obtieneMontoPagoSiniestro : {}", e.getMessage(), e);
			return SUCCESS;
		}
		//success = true;
		return SUCCESS;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	public String calculoSiniestros(){
		logger.debug(""
			+ "\n###############################"
			+ "\n###### calculoSiniestros ######"
		);
		logger.debug("params de entrada :{}",params);
		try{
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
			logger.debug("TRAMITE : {}", tramite);
			smap = tramite;
			//Se obtiene el listado de las facturas
			List<Map<String,String>> facturasAux = siniestrosManager.obtenerFacturasTramite(ntramite);
			logger.debug("LISTADO DE FACTURAS : {}", facturasAux);

			if(tramite==null||facturasAux==null){
				throw new Exception("No se encontro tramite/facturas para el tramite");
			}
			
			boolean esPagoDirecto = false;
			if(tramite.get("OTVALOR02").equals("1")){
				esPagoDirecto = true;
			}
			logger.debug("TIPO DE PAGO ES DIRECTO : {}", esPagoDirecto);
			
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
			if(esPagoDirecto){
				//Verificamos la informacion del proveedor
				smap.put("PAGODIRECTO","S");
				smap2     = facturasAux.get(0);
				
				proveedor = siniestrosManager.obtenerDatosProveedor(facturasAux.get(0).get("CDPRESTA"));
				logger.debug("PROVEEDOR : {}", proveedor);
				smap3     = proveedor;
				double ivaprov = Double.parseDouble(proveedor.get("IVA")); 
				double cedprov = Double.parseDouble(proveedor.get("CEDULAR"));
				double isrprov = Double.parseDouble(proveedor.get("ISR"));
				
				//RECORREMOS LAS FACTURAS DEL TRAMITE
				for(int i = 0; i < facturasAux.size(); i++){
    				factura = facturasAux.get(i);
    				logger.debug("FACTURA PROCESANDO : {}", factura.get("NFACTURA"));
    				//Se grega la factura c/u
    				Map<String,Object>facturaObj=new HashMap<String,Object>();
        			facturaObj.putAll(factura);
        			this.facturasxSiniestro.add(facturaObj);
        			
        			//Se agrega los Asegurados o siniestros por factura
        			List<Map<String,String>> siniestros = siniestrosManager.listaSiniestrosMsiniesTramite(ntramite,factura.get("NFACTURA"),null);
        			logger.debug("VALOR DE SINIESTROS POR FACTURAS : {}", siniestros);
        			
        			conceptos = siniestrosManager.P_GET_CONCEPTOS_FACTURA(
    					null, tramite.get("CDRAMO"), null, null, null, null, null, null, null, factura.get("NFACTURA"),tramite.get("CDTIPSIT"));
    				logger.debug("OBTENEMOS LA INFORMACION DE LOS CONCEPTOS POR FACTURA : {}", conceptos);
        			
    				/*LINEA PARA IR GUARDANDA A CADA UNO DE LOS ASEGURADOS
        			 * 					facturaObj.put("siniestroPD", siniestros);
        			 */
        			
    				//RECORREMOS LOS SINIESTROS
    				this.aseguradosxSiniestro=new ArrayList<Map<String,Object>>();
    				for( int j= 0; j < siniestros.size();j++){
    					String	aplicaPenalCircHosp		  = "S";
						String	aplicaPenalZonaHosp		  = "S";
    					//Se realiza la asignacion del primer asegurado
        				siniestroIte    = siniestros.get(j);
        				
        				if(StringUtils.isNotBlank(siniestroIte.get("NMAUTSER"))){
							List<AutorizacionServicioVO> lista = siniestrosManager.getConsultaAutorizacionesEsp(siniestroIte.get("NMAUTSER"));
							logger.debug("VALOR DE RESPUESTA : {}", lista);
							aplicaPenalCircHosp		  = lista.get(0).getAplicaCirHos()+"";
							aplicaPenalZonaHosp		  = lista.get(0).getAplicaZonaHosp()+"";
						}
        				
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
        				String cdtipsit = siniestroIte.get("CDTIPSIT");
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
        				logger.debug("AUTORIACION DE LAS FACTURAS : {}", autorizacionesFactura);
        				
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
        						cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura, tramite.get("OTVALOR02"),cdtipsit);
        				logger.debug("INFORMACION DEDUCIBLE/COPAGO ASEGURADO : {}", copagoDeducibleSiniestroIte);
        				
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
            		   		if(cdramo.equalsIgnoreCase("2") || cdramo.equalsIgnoreCase("7")){
            		   			
            		   			if(aplicaPenalZonaHosp.equalsIgnoreCase("N")){
									penalizacionCambioZona = 0d;
								}else{
									existePenalizacion = siniestrosManager.validaExclusionPenalizacion(paramExclusion);
	                		   		//4.2.- Obtenemos la penalizaci�n por cambio de Zona
	                		   		penalizacionCambioZona = penalizacionCambioZona(existePenalizacion,informacionGral.get(0).get("CDCAUSA"),informacionGral.get(0).get("CIRHOSPI"),
	                						informacionGral.get(0).get("DSZONAT"),informacionGral.get(0).get("CDPROVEE"), cdramo);
	                		   		
								}
								if(aplicaPenalCircHosp.equalsIgnoreCase("N")){
									penalizacionCirculoHosp = 0d;
								}else{
									//4.3.- Obtenemos la penalizaci�n por circulo Hospitalario
	                				penalizacionCirculoHosp = calcularPenalizacionCirculo(informacionGral.get(0).get("CIRHOSPI"), informacionGral.get(0).get("CIRHOPROV"),informacionGral.get(0).get("CDCAUSA"), cdramo);
								}
								
                		   		aseguradoObj.put("PENALIZACIONCAMBIOZONA",""+penalizacionCambioZona);
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
        				List<Map<String, String>> listaFactura = siniestrosManager.P_GET_FACTURAS_SINIESTRO(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies,cdtipsit);
        				logger.debug("VALOR DE listaFactura : {}", listaFactura);
        				
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
        							logger.debug("concepto importe : {}", PTIMPORT);
        							logger.debug("concepto desto : {}", DESTOPOR);
        							logger.debug("concepto destoimp : {}", DESTOIMP);
        							logger.debug("usando iva proveedor : {}", ivaprov);
        							boolean copagoPorc = false;
        							String scopago     = concepto.get("COPAGO");
        							
        							if(scopago.equalsIgnoreCase("no") ||scopago.equalsIgnoreCase("na"));
        							{
        								scopago="0";
        							}
        							logger.debug("procesar copago : {}", scopago);
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
        							logger.debug("concepto desto + copago % : {}", DESTOPOR);
        							logger.debug("concepto destoimp + copago $ : {}", DESTOIMP);
        							
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
        							logger.debug("hPTIMPORT :   {}", hPTIMPORT);
        							logger.debug("hDESTO :      {}", hDESTO);
        							logger.debug("hIVA :        {}", hIVA);
        							logger.debug("hISR :        {}", hISR);
        							logger.debug("hPRECIO :     {}", hPRECIO);
        							logger.debug("hDESCPRECIO : {}", hDESCPRECIO);
        							logger.debug("hICED :       {}", hICED);
        							
        							logger.debug("<<HOSPITALIZACION");
        						}
        						else
        						{
        							//logger.debug("######  COBERTURA DIFERENTE DE HOSPITALIZACI�N Y AYUDA DE MATERNIDAD  ######");
        							logger.debug(">>PAGO DIRECTO DIFERENTE A HOSPITALIZACION");
        							Map<String,String>row=new HashMap<String,String>();
        							row.putAll(concepto);
        							
        							double cantidad = Double.valueOf(row.get("CANTIDAD"));
        							logger.debug("cantidad : {}", cantidad);
        							double precioArancel = 0d;
        							//Obtenemos el valor original del arancel
        							if(StringUtils.isNotBlank(row.get("IMP_ARANCEL")))
        							{
        								precioArancel = Double.valueOf(row.get("IMP_ARANCEL"));
        							}
        							row.put("IMP_ARANCEL",precioArancel+"");
        							logger.debug("precioArancel : {}", precioArancel);
        							double subtotalArancel = cantidad*precioArancel;//++
        							logger.debug("subtotalArancel : {}", subtotalArancel);
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
        							logger.debug("descuentoAplicado : {}", descuentoAplicado);
        							row.put("DESTOAPLICA",descuentoAplicado+"");
        							double subtotalDescuento=subtotalArancel-descuentoAplicado;//++
        							logger.debug("subtotalDescuento : {}", subtotalDescuento);
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
        							logger.debug("copagoAplicado : {}", copagoAplicado);
        							double subtotalCopago    = subtotalDescuento - copagoAplicado;//++
        							logger.debug("subtotalCopago : {}", subtotalCopago);
        							row.put("SUBTTCOPAGO",subtotalCopago+"");
        							double israplicado       = subtotalCopago*(isrprov/100d);//++
        							logger.debug("israplicado : {}", israplicado);
        							row.put("ISRAPLICA",israplicado+"");
        							double subtotalImpuestos = subtotalCopago-(israplicado+0d);//cedularaplicado);//++
        							logger.debug("subtotalImpuestos : {}", subtotalImpuestos);
        							
        							double totalISRMostrar = Double.parseDouble(mpdir.get("iSRMostrar"));
        							logger.debug("base totalISRMostrar : {}", totalISRMostrar);
        							totalISRMostrar += israplicado;
        							//logger.debug("new totalISRMostrar : {}", totalISRMostrar);
        							mpdir.put("iSRMostrar",totalISRMostrar+"");
        							
        							////// modificado
        							double cedularaplicado   = subtotalCopago*(cedprov/100d);//++
        							logger.debug("cedularaplicado : {}", cedularaplicado);
        							row.put("CEDUAPLICA",cedularaplicado+"");
        							////// modificado
        							
        							subtotalImpuestos = subtotalImpuestos - cedularaplicado;
        							row.put("SUBTTIMPUESTOS",subtotalImpuestos+"");
        							
        							double ivaaplicado =0d;
        							double ivaRetenidos =0d;
        							double ptimportauto =0d;
        							//logger.debug("AplicaIVA : {}", aplicaIVA);
        							
        							if(aplicaIVA.equalsIgnoreCase("S")){
        								if(seleccionAplica.equalsIgnoreCase("D")){
        									ivaaplicado       = subtotalCopago*(ivaprov/100d);//++
        									row.put("IVAAPLICA",ivaaplicado+"");
        									//logger.debug("IvaRetenido : {}", ivaRetenido);
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
        							logger.debug("base totalIVAMostrar : {}", totalIVAMostrar);
        							totalIVAMostrar += ivaaplicado;
        							logger.debug("new totalIVAMostrar : {}", totalIVAMostrar);
        							mpdir.put("ivaTotalMostrar",totalIVAMostrar+"");
        							
        							
        							double totalIVARetenidoMostrar = Double.parseDouble(mpdir.get("ivaRetenidoMostrar"));
        							logger.debug("base totalIVARetenidoMostrar : {}", totalIVARetenidoMostrar);
        							totalIVARetenidoMostrar += ivaRetenidos;
        							logger.debug("new totalIVAMostrar : {}", totalIVARetenidoMostrar);
        							mpdir.put("ivaRetenidoMostrar",totalIVARetenidoMostrar+"");
        							
        							double ptimport = Double.parseDouble(row.get("PTIMPORT"));
        							logger.debug("ptimport : {}", ptimport);
        							
        							double valorusado        = ptimportauto;//++
        							
        							String autmedic = row.get("AUTMEDIC");
        							if(StringUtils.isNotBlank(autmedic)&&autmedic.equalsIgnoreCase("S"))
        							{
        								valorusado = ptimport;
        							}
        							logger.debug("valorusado : {}", valorusado);
        							row.put("VALORUSADO",valorusado+"");
        							
        							double totalGrupo = Double.parseDouble(mpdir.get("total"));
        							logger.debug("base totalGrupo : {}", totalGrupo);
        							totalGrupo += valorusado;
        							logger.debug("new totalGrupo : {}", totalGrupo);
        							mpdir.put("total",totalGrupo+"");
        							
        							double totalGrupoCedular = Double.parseDouble(mpdir.get("totalcedular"));
        							logger.debug("base totalGrupoCedular : {}", totalGrupoCedular);
        							totalGrupoCedular += cedularaplicado;
        							logger.debug("new totalGrupoCedular : {}", totalGrupoCedular);
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
    						logger.debug("deducible siniestro iterado : {}", sDeducibleSiniestroIte);
    						logger.debug("copago siniestro iterado : {}", sCopagoSiniestroIte);
    						logger.debug("tipo copago siniestro iterado : {}", tipoCopagoSiniestroIte);
    						
    						logger.debug("hosp.get(PTIMPORT) : {}", hosp.get("PTIMPORT"));
    						logger.debug("hosp.get(DESTO) : {}", hosp.get("DESTO"));
    						logger.debug("hosp.get(IVA) : {}", hosp.get("IVA"));
    						logger.debug("deducibleSiniestroIte : {}", deducibleSiniestroIte);
    						
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
    						logger.debug("Iva a ocupar despues de copago : {}",hIVADesCopago);
    						
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
    						logger.debug("mapa WS siniestro iterado : {}", importesWSSiniestroIte);
    						logger.debug("<<WS del siniestro iterado");
    						//logger.debug("###### HOSPITALIZACION Y AYUDA DE MATERNIDA WS ######");
    					}
    					else//pago directo
    					{
    						//logger.debug("######  COBERTURA DIFERENTE DE HOSPITALIZACI�N Y AYUDA DE MATERNIDAD  ######");
    						logger.debug(">>WS del siniestro iterado");
    						logger.debug("deducible siniestro iterado : {}", sDeducibleSiniestroIte);
    						logger.debug("copago siniestro iterado : {}", sCopagoSiniestroIte);
    						logger.debug("tipo copago siniestro iterado : {}", tipoCopagoSiniestroIte);
    						
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
    						logger.debug("mapa WS siniestro iterado : {}", importesWSSiniestroIte);
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
        		logger.debug("Siniestros : {} ",siniestros);
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
    					null,
    					siniestro.get("CDTIPSIT"));
    			logger.debug("VALOR DEL CONCEPTOS : {}",conceptos);
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
    				String cdtipsit = siniestro.get("CDTIPSIT");
    				String nfactura = facturaIte.get("NFACTURA");
    				
    				Map<String,String>copagoDeducibleFacturaIte =siniestrosManager.obtenerCopagoDeducible(
    						cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura,tramite.get("OTVALOR02"),cdtipsit);
    				
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
    						siniestro.get("AAAPERTU"),siniestro.get("STATUS"),siniestro.get("NMSINIES") ,facturaIte.get("NFACTURA"),tramite.get("OTVALOR02"),siniestro.get("CDTIPSIT"));
    						
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
        						penalizacionCirculoHosp = calcularPenalizacionCirculo(informacionGral.get(0).get("CIRHOSPI"), medicos.get(0).getCirculo(),informacionGral.get(0).get("CDCAUSA"),siniestro.get("CDRAMO"));
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
    							logger.debug("ptimport : {}", ptimport);
    							
    							double ajusteaplica = 0d;
    							if(StringUtils.isNotBlank(row.get("PTIMPORT_AJUSTADO")))
    							{
    								ajusteaplica = Double.parseDouble(row.get("PTIMPORT_AJUSTADO"));
    							}
    							logger.debug("ajusteaplica : {}", ajusteaplica);
    							
    							double subtotal = ptimport-ajusteaplica;
    							logger.debug("subtotal : {}", subtotal);
    							row.put("SUBTOTAL",subtotal+"");
    							
    							double gtotalneto = Double.parseDouble(mprem.get("TOTALNETO"));
    							double gsubtotal  = Double.parseDouble(mprem.get("SUBTOTAL"));
    							logger.debug("base totalneto : {}", gtotalneto);
    							logger.debug("base subtotal : {}", gsubtotal);
    							gtotalneto += ptimport;
    							gsubtotal  += subtotal;
    							logger.debug("new totalneto : {}", gtotalneto);
    							logger.debug("new subtotal : {}", gsubtotal);
    							
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
    				}else{
						String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
						if(causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
							deducibleFacturaIte = 0d;
    				}
					}
    				
    				logger.debug(">>Calculando total factura iterada para WS");
    				logger.debug("deducible : {}", deducibleFacturaIte);
    				logger.debug("scopago : {}", sCopagoFacturaIte);
    				logger.debug("tipocopago : {}", tipoCopagoFacturaIte);
    				logger.debug("facturaIte.get(DESCPORC) : {}", facturaIte.get("DESCPORC"));
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
    				logger.debug("subtotal : {}", totalFactura);
    				totalFactura -= destoAplicado;
    				logger.debug("subtotal desto : {}", totalFactura);
    				totalFactura -= deducibleFacturaIte;
    				logger.debug("subtotal deducible : {}", totalFactura);
    				
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
    						if(!causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
    							if(tipoCopagoFacturaIte.equalsIgnoreCase("$")){
    								copagoAplicadoFacturaIte = cantidadCopagoFacturaIte;
    							}
    							if(tipoCopagoFacturaIte.equalsIgnoreCase("%"))
    							{
    								copagoAplicadoFacturaIte = totalFactura * ( cantidadCopagoFacturaIte / 100d );
    							}
							}else{
								copagoAplicadoFacturaIte = 0d;
    							}
    					}
    				}
    				totalFactura -= copagoAplicadoFacturaIte;
    				logger.debug("total copago (final) : {}", totalFactura);
    				logger.debug("<<Calculando total factura iterada para WS");
    				
    				importeSiniestroUnico += totalFactura;
    			}
    			
    			logger.debug(">>WS del siniestro unico");
    			importesWSSiniestroUnico.put(IMPORTE_WS_IMPORTE , (new Double(importeSiniestroUnico)).toString());
    			importesWSSiniestroUnico.put(IMPORTE_WS_IVA     , (new Double(ivaSiniestroUnico)    ).toString());
    			importesWSSiniestroUnico.put(IMPORTE_WS_IVR     , (new Double(ivrSiniestroUnico)    ).toString());
    			importesWSSiniestroUnico.put(IMPORTE_WS_ISR     , (new Double(isrSiniestroUnico)    ).toString());
    			importesWSSiniestroUnico.put(IMPORTE_WS_CEDULAR , (new Double(cedularSiniestroUnico)).toString());
    			logger.debug("mapa WS siniestro unico : {}", importesWSSiniestroUnico);
    			logger.debug("<<WS del siniestro unico");
    			
    		}
    		
    		if(conceptos!=null&&conceptos.size()>0)
    		{
    			logger.debug("conceptos[0] : {}", conceptos);
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
    		logger.error("error al cargar pantalla de calculo de siniestros : {}", ex.getMessage(), ex);
    	}
    	logger.debug(""
    		+ "\n###### calculoSiniestros ######"
    		+ "\n###############################"
   		);
    	return SUCCESS;
    }


/****************************GETTER Y SETTER *****************************************/
	public List<GenericVO> getListaTipoAtencion() {
		return listaTipoAtencion;
	}

	public void setListaTipoAtencion(List<GenericVO> listaTipoAtencion) {
		this.listaTipoAtencion = listaTipoAtencion;
	}

	public String getMsgResult() {
		return msgResult;
	}

	public void setMsgResult(String msgResult) {
		this.msgResult = msgResult;
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

	public void setListaCPTICD(List<GenericVO> listaCPTICD) {
		this.listaCPTICD = listaCPTICD;
	}

	public List<GenericVO> getListaSubcobertura() {
		return listaSubcobertura;
	}

	public void setListaSubcobertura(List<GenericVO> listaSubcobertura) {
		this.listaSubcobertura = listaSubcobertura;
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
	

	public List<GenericVO> getListaAsegurado() {
		return listaAsegurado;
	}

	public Date getDate(String date){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return df.parse(date);
		} catch (ParseException ex) {
		}
		return null;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setSiniestrosManager(SiniestrosManager siniestrosManager) {
		this.siniestrosManager = siniestrosManager;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public void setListaAsegurado(List<GenericVO> listaAsegurado) {
		this.listaAsegurado = listaAsegurado;
	}

	public List<ConsultaTTAPVAATVO> getListaConsultaTTAPVAATV() {
		return listaConsultaTTAPVAATV;
	}

	public void setListaConsultaTTAPVAATV(
		List<ConsultaTTAPVAATVO> listaConsultaTTAPVAATV) {
		this.listaConsultaTTAPVAATV = listaConsultaTTAPVAATV;
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

	public Map<String, String> getSmap() {
		return smap;
	}

	public void setSmap(Map<String, String> smap) {
		this.smap = smap;
	}

	public String getSmapJson(){
		String r=null;
		try{
			r=JSONUtil.serialize(smap);
		}
		catch (JSONException ex){
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

	public String getSmap2Json(){
		String r=null;
		try{
			r=JSONUtil.serialize(smap2);
		}
		catch (JSONException ex){
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

	public String getSmap3Json(){
		String r=null;
		try{
			r=JSONUtil.serialize(smap3);
		}
		catch (JSONException ex){
			logger.error("error al convertir smap3 a json",ex);
		}
		return r;
	}

	public List<Map<String, String>> getSlist2() {
		return slist2;
	}

	public String getSlist1Json() {
		String r=null;
		try{
			r=JSONUtil.serialize(slist1);
		}
		catch (JSONException ex){
			logger.error("error al convertir slist1 a json",ex);
		}
		return r;
	}

	public String getSlist2Json() {
		String r=null;
		try{
			r=JSONUtil.serialize(slist2);
		}
		catch (JSONException ex){
			logger.error("error al convertir slist2 a json",ex);
		}
		return r;
	}

	public String getLlist1Json() {
		String r=null;
		try{
			r=JSONUtil.serialize(llist1);
		}
		catch (JSONException ex){
			logger.error("error al convertir llist1 a json",ex);
		}
		return r;
	}

	public String getSlist3Json() {
		String r=null;
		try{
			r=JSONUtil.serialize(slist3);
		}
		catch (JSONException ex){
			logger.error("error al convertir slist3 a json",ex);
		}
		return r;
	}

	public String getLhospJson() {
		String r=null;
		try{
			r=JSONUtil.serialize(lhosp);
		}
		catch (JSONException ex){
			logger.error("error al convertir lhosp a json",ex);
		}
		return r;
	}

	public String getLpdirJson() {
		String r=null;
		try{
			r=JSONUtil.serialize(lpdir);
		}
		catch (JSONException ex){
			logger.error("error al convertir lpdir a json",ex);
		}
		return r;
	}

	public String getLpremJson() {
		String r=null;
		try{
			r=JSONUtil.serialize(lprem);
		}
		catch (JSONException ex){
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
		try{
			r=JSONUtil.serialize(datosPenalizacion);
		}
		catch (JSONException ex){
			logger.error("error al convertir datosPenalizacion a json",ex);
		}
		return r;
	}

	public String getDatosCoberturaxCalJson() {
		String r=null;
		try{
			r=JSONUtil.serialize(datosCoberturaxCal);
		}
		catch (JSONException ex){
			logger.error("error al convertir datosCoberturaxCal a json",ex);
		}
		return r;
	}

	public String getMontoArancel() {
		return montoArancel;
	}

	public void setMontoArancel(String montoArancel) {
		this.montoArancel = montoArancel;
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

	public void setDatosInformacionAdicional(List<Map<String, String>> datosInformacionAdicional) {
		this.datosInformacionAdicional = datosInformacionAdicional;
	}

	public void setMap1(Map<String, String> map1) {
		this.map1 = map1;
	}

	public List<Map<String, String>> getDatosValidacion() {
		return datosValidacion;
	}

	public void setDatosValidacion(List<Map<String, String>> datosValidacion) {
		this.datosValidacion = datosValidacion;
	}

	public List<Map<String, Object>> getFacturasxSiniestro() {
		return facturasxSiniestro;
	}

	public void setFacturasxSiniestro(List<Map<String, Object>> facturasxSiniestro) {
		this.facturasxSiniestro = facturasxSiniestro;
	}

	public String getFacturasxSiniestroJson() {
		String r=null;
		try{
			r=JSONUtil.serialize(facturasxSiniestro);
		}
		catch (JSONException ex){
			logger.error("error al convertir slist1 a json",ex);
		}
		return r;
	}

	public String getUsuarioTurnadoSiniestro() {
		return usuarioTurnadoSiniestro;
	}

	public void setUsuarioTurnadoSiniestro(String usuarioTurnadoSiniestro) {
		this.usuarioTurnadoSiniestro = usuarioTurnadoSiniestro;
	}	
}