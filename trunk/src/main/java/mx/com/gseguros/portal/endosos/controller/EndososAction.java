package mx.com.gseguros.portal.endosos.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.controller.ComplementariosCoberturasAction;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.Tatri;
import mx.com.gseguros.portal.endosos.service.EndososManager;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.TipoEndoso;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.ws.client.Ice2sigsWebServices;
import mx.com.gseguros.ws.client.Ice2sigsWebServices.Estatus;
import mx.com.gseguros.ws.client.Ice2sigsWebServices.Operacion;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSalud;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.Recibo;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ReciboRespuesta;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class EndososAction extends PrincipalCoreAction
{
	private static final long        serialVersionUID = 84257834070419933L;
	private static Logger            log              = Logger.getLogger(EndososAction.class);
	private boolean                  success          = false;
	private SimpleDateFormat         renderFechas     = new SimpleDateFormat("dd/MM/yyyy");
	
	private List<Map<String,String>> slist1;
	private List<Map<String,String>> slist2;
	private Map<String,String>       smap1;
	private Map<String,String>       smap2;
	private Map<String,String>       smap3;
	private Map<String,Object>       omap1;
	private EndososManager           endososManager;
	private KernelManagerSustituto   kernelManager;
	private PantallasManager         pantallasManager;
	private transient Ice2sigsWebServices ice2sigsWebServices;
	private Item                     item1;
	private Item                     item2;
	private Item                     item3;
	private String                   mensaje;
	private Map<String,String>       parametros;
	private Map<String,Item>         imap1;
	private String                   error;

	//////////////////////////////
	////// marco de endosos //////
	/*//////////////////////////*/
	public String marco()
	{
		log.debug(""
				+ "\n##############################"
				+ "\n##############################"
				+ "\n###### marco de endosos ######"
				+ "\n######                  ######"
				);
		success=true;
		log.debug(""
				+ "\n######                  ######"
				+ "\n###### marco de endosos ######"
				+ "\n##############################"
				+ "\n##############################"
				);
		return SUCCESS;
	}
	/*//////////////////////////*/
	////// marco de endosos //////
	//////////////////////////////
	
	/////////////////////////////
	////// obtener endosos //////
	/*/////////////////////////*/
	public String obtenerEndosos()
	{
		log.debug(""
				+ "\n############################"
				+ "\n############################"
				+ "\n###### obtenerEndosos ######"
				+ "\n######                ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			slist1=endososManager.obtenerEndosos(smap1);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al obtener los endosos",ex);
			success=false;
		}
		log.debug(""
				+ "\n######                ######"
				+ "\n###### obtenerEndosos ######"
				+ "\n############################"
				+ "\n############################"
				);
		return SUCCESS;
	}
	/*/////////////////////////*/
	////// obtener endosos //////
	/////////////////////////////
	
	//////////////////////////////////////////////
	////// pantalla de endoso de coberturas //////
	/*
	smap1.pv_cdunieco:1006
	smap1.pv_cdramo:2
	smap1.pv_estado:M
	smap1.pv_nmpoliza:18
	smap1.pv_nmsituac:2
	smap1.pv_cdperson:512022
	smap1.cdrfc:MAVA900817
	smap1.pv_cdrol:2
	smap1.nombreAsegurado:NOMBRE  PATERNO MATERNO
	smap1.ntramite:662
	smap1.botonCopiar:0
	smap1.altabaja:alta
	smap1.cdtipsit:SL
	*/
	/*//////////////////////////////////////////*/
	public String pantallaEndosoCoberturas()
	{
		log.debug(""
				+ "\n######################################"
				+ "\n######################################"
				+ "\n###### pantallaEndosoCoberturas ######"
				+ "\n######                          ######"
				);
		log.debug("smap1: "+smap1);
		String respuesta=this.validaEndosoAnterior(
				smap1.get("pv_cdunieco")
				,smap1.get("pv_cdramo")
				,smap1.get("pv_estado")
				,smap1.get("pv_nmpoliza")
				,smap1.get("altabaja").equalsIgnoreCase("alta")?
						TipoEndoso.ALTA_COBERTURAS.getCdTipSup().toString():
						TipoEndoso.BAJA_COBERTURAS.getCdTipSup().toString());
		log.debug(""
				+ "\n######                          ######"
				+ "\n###### pantallaEndosoCoberturas ######"
				+ "\n######################################"
				+ "\n######################################"
				);
		return respuesta;
	}
	/*//////////////////////////////////////////*/
	////// pantalla de endoso de coberturas //////
	//////////////////////////////////////////////

	//////////////////////////////////////////////
	////// pantalla de endoso de domicilio  //////
	/*
	smap1.pv_cdunieco:1006
	smap1.pv_cdramo:2
	smap1.pv_estado:M
	smap1.pv_nmpoliza:18
	smap1.pv_nmsituac:2
	smap1.pv_cdperson:512022
	smap1.cdrfc:MAVA900817
	smap1.pv_cdrol:2
	smap1.nombreAsegurado:NOMBRE  PATERNO MATERNO
	smap1.botonCopiar:0
	smap1.cdtipsit:SL
	smap1.ntramite:662
	*/
	/*//////////////////////////////////////////*/
	public String pantallaEndosoDomicilio()
	{
		log.debug(""
				+ "\n#####################################"
				+ "\n#####################################"
				+ "\n###### pantallaEndosoDomicilio ######"
				+ "\n######                         ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("session: "+session);
		String respuesta=this.validaEndosoAnterior(
				smap1.get("pv_cdunieco")
				,smap1.get("pv_cdramo")
				,smap1.get("pv_estado")
				,smap1.get("pv_nmpoliza")
				,TipoEndoso.CAMBIO_DOMICILIO.getCdTipSup().toString());
		if(respuesta.equals(SUCCESS))
		{
			ComplementariosCoberturasAction actionDomicilio=new ComplementariosCoberturasAction();
			actionDomicilio.setSession(session);
			actionDomicilio.setSmap1(smap1);
			actionDomicilio.setKernelManager(kernelManager);
			actionDomicilio.mostrarPantallaDomicilio();
			item1=actionDomicilio.getItem1();
			item2=actionDomicilio.getItem2();
		}
		log.debug(""
				+ "\n######                         ######"
				+ "\n###### pantallaEndosoDomicilio ######"
				+ "\n#####################################"
				+ "\n#####################################"
				);
		return respuesta;
	}
	/*//////////////////////////////////////////*/
	////// pantalla de endoso de domicilio  //////
	//////////////////////////////////////////////
	
	///////////////////////////////////////////
	////// pantalla de endoso de nombres //////
	/*
	smap1:
		cdramo: "2"
		cdtipsit: "SL"
		cdunieco: "1006"
		estado: "M"
		nmpoliza: "18"
		ntramite: "662"
	slist1:
		[{Apellido_Materno: "MATERNO"
		Apellido_Paterno: "PATERNO"
		Parentesco: "D"
		cdperson: "512022"
		cdrfc: "MAVA900817"
		cdrol: "2"
		fenacimi: "17/08/1990"
		nacional: "001"
		nmsituac: "2"
		nombre: "NOMBRE"
		segundo_nombre: null
		sexo: "M"
		swexiper: "S"
		tpersona: "F"}]
	*/
	/*///////////////////////////////////////*/
	public String pantallaEndosoNombres()
	{
		log.debug(""
				+ "\n###################################"
				+ "\n###################################"
				+ "\n###### pantallaEndosoNombres ######"
				+ "\n######                       ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("slist1: "+slist1);
		String respuesta=this.validaEndosoAnterior(
				smap1.get("cdunieco")
				,smap1.get("cdramo")
				,smap1.get("estado")
				,smap1.get("nmpoliza")
				,TipoEndoso.CORRECCION_NOMBRE_Y_RFC.getCdTipSup().toString());
		log.debug(""
				+ "\n######                       ######"
				+ "\n###### pantallaEndosoNombres ######"
				+ "\n###################################"
				+ "\n###################################"
				);
		return respuesta;
	}
	/*///////////////////////////////////////*/
	////// pantalla de endoso de nombres //////
	///////////////////////////////////////////
	
	////////////////////////////////////////////////////
	////// generar el endoso de cambio de nombres //////
	/*////////////////////////////////////////////////*/
	public String guardarEndosoNombres()
	{
		/*
		 * se obtiene la sesion manualmente por el enableSMD de struts...xml
		 */
		this.session=ActionContext.getContext().getSession();
        UserVO usuario=(UserVO) session.get("USUARIO");
		log.debug(""
				+ "\n##################################"
				+ "\n##################################"
				+ "\n###### guardarEndosoNombres ######"
				+ "\n######                      ######"
				);
		log.debug("omap1: "+omap1);
		log.debug("slist1: "+slist1);
		try
		{
			/*
			 * ya viene en el omap1 desde el jsp:
			 * pv_cdunieco_i
			 * pv_cdramo_i
			 * pv_estado_i
			 * pv_nmpoliza_i
			 * pv_fecha_i
			 * 
			 * hay que poner:
			 * pv_cdelemen_i
			 * pv_cdusuari_i
			 * pv_proceso_i
			 * pv_cdtipsup_i
			 */
			omap1.put("pv_cdelemen_i" , usuario.getEmpresa().getElementoId());
			omap1.put("pv_cdusuari_i" , usuario.getUser());
			omap1.put("pv_proceso_i"  , "END");
			omap1.put("pv_cdtipsup_i", TipoEndoso.CORRECCION_NOMBRE_Y_RFC.getCdTipSup().toString());
			
			/*
			 * sobreescribir la fecha con object
			 */
			omap1.put("pv_fecha_i",renderFechas.parse((String)omap1.get("pv_fecha_i")));
			
			Map<String,String> respuestaEndosoNombres=endososManager.guardarEndosoNombres(omap1);
			
			for(Map<String,String>persona:slist1)
			{
				Map<String,Object>paramPersona=new LinkedHashMap<String,Object>(0);
				paramPersona.put("pv_cdperson_i"    , persona.get("cdperson"));
				paramPersona.put("pv_cdtipide_i"    , null);
				paramPersona.put("pv_cdideper_i"    , null);
				paramPersona.put("pv_dsnombre_i"    , persona.get("nombre"));
				paramPersona.put("pv_cdtipper_i"    , null);
				paramPersona.put("pv_otfisjur_i"    , null);
				paramPersona.put("pv_otsexo_i"      , null);
				paramPersona.put("pv_fenacimi_i"    , null);
				paramPersona.put("pv_cdrfc_i"       , persona.get("rfc"));
				paramPersona.put("pv_dsemail_i"     , null);
				paramPersona.put("pv_dsnombre1_i"   , persona.get("nombre2"));
				paramPersona.put("pv_dsapellido_i"  , persona.get("apat"));
				paramPersona.put("pv_dsapellido1_i" , persona.get("amat"));
				paramPersona.put("pv_feingreso_i"   , null);
				paramPersona.put("pv_cdnacion_i"    , null);
				paramPersona.put("pv_accion_i"      , "M");
				kernelManager.movMpersona(paramPersona);
			}
			
			String confirmado=this.confirmarEndoso((String)omap1.get("pv_cdunieco_i"),
					(String)omap1.get("pv_cdramo_i"),
					(String)omap1.get("pv_estado_i"),
					(String)omap1.get("pv_nmpoliza_i"),
					respuestaEndosoNombres.get("pv_nmsuplem_o"),
					respuestaEndosoNombres.get("pv_nsuplogi_o"),
					TipoEndoso.CORRECCION_NOMBRE_Y_RFC.getCdTipSup().toString(),
					"",
					(Date)omap1.get("pv_fecha_i"),
					"SL"
					);
		    
			if(confirmado==null||confirmado.length()==0)
			{
			    ///////////////////////////////////////
			    ////// re generar los documentos //////
			    /*///////////////////////////////////*/
			    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
				paramsGetDoc.put("pv_cdunieco_i" , (String)omap1.get("pv_cdunieco_i"));
				paramsGetDoc.put("pv_cdramo_i"   , (String)omap1.get("pv_cdramo_i"));
				paramsGetDoc.put("pv_estado_i"   , (String)omap1.get("pv_estado_i"));
				paramsGetDoc.put("pv_nmpoliza_i" , (String)omap1.get("pv_nmpoliza_i"));
				paramsGetDoc.put("pv_nmsuplem_i" , respuestaEndosoNombres.get("pv_nmsuplem_o"));
				paramsGetDoc.put("pv_tipmov_i"   , TipoEndoso.CORRECCION_NOMBRE_Y_RFC.getCdTipSup().toString());
			    List<Map<String,String>>listaDocu=endososManager.reimprimeDocumentos(paramsGetDoc);
			    log.debug("documentos que se regeneran: "+listaDocu);
			    
			    String rutaCarpeta=this.getText("ruta.documentos.poliza")+"/"+listaDocu.get(0).get("ntramite");
			    
				//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
				for(Map<String,String> docu:listaDocu)
				{
					log.debug("docu iterado: "+docu);
					String nmsolici=docu.get("nmsolici");
					String nmsituac=docu.get("nmsituac");
					String descripc=docu.get("descripc");
					String descripl=docu.get("descripl");
					String url=this.getText("ruta.servidor.reports")
							+ "?destype=cache"
							+ "&desformat=PDF"
							+ "&userid="+this.getText("pass.servidor.reports")
							+ "&report="+descripl
							+ "&paramform=no"
							+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
							+ "&p_unieco="+(String)omap1.get("pv_cdunieco_i")
							+ "&p_ramo="+(String)omap1.get("pv_cdramo_i")
							+ "&p_estado="+(String)omap1.get("pv_estado_i")
							+ "&p_poliza="+(String)omap1.get("pv_nmpoliza_i")
							+ "&p_suplem="+respuestaEndosoNombres.get("pv_nmsuplem_o")
							+ "&desname="+rutaCarpeta+"/"+descripc;
					if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
					{
						// C R E D E N C I A L _ X X X X X X . P D F
						//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
						url+="&p_cdperson="+descripc.substring(11, descripc.lastIndexOf("_"));
					}
					log.debug(""
							+ "\n#################################"
							+ "\n###### Se solicita reporte ######"
							+ "\na "+url+""
							+ "\n#################################");
					HttpUtil.generaArchivo(url,rutaCarpeta+"/"+descripc);
					log.debug(""
							+ "\n######                    ######"
							+ "\n###### reporte solicitado ######"
							+ "\na "+url+""
							+ "\n################################"
							+ "\n################################"
							+ "");
				}
			    /*///////////////////////////////////*/
				////// re generar los documentos //////
			    ///////////////////////////////////////
				
				ejecutaWSclienteSaludEndoso((String)omap1.get("pv_cdunieco_i"), (String)omap1.get("pv_cdramo_i"), (String)omap1.get("pv_estado_i"), (String)omap1.get("pv_nmpoliza_i"), respuestaEndosoNombres.get("pv_nmsuplem_o"), "ACTUALIZA");
				
				/**
				 * TODO: Poner variable el cdTipSitGS de la poliza y la sucursal
				 
				String cdtipsitGS = "213";
				String sucursal = (String)omap1.get("pv_cdunieco_i");
				if(StringUtils.isNotBlank(sucursal) && "1".equals(sucursal)) sucursal = "1000";
				
				String nmsolici = listaDocu.get(0).get("nmsolici");
				String nmtramite = listaDocu.get(0).get("ntramite");
				
				insertaURLrecibosEndoso((String)omap1.get("pv_cdunieco_i"), (String)omap1.get("pv_cdramo_i"), (String)omap1.get("pv_estado_i"), (String)omap1.get("pv_nmpoliza_i"), respuestaEndosoNombres.get("pv_nmsuplem_o"), cdtipsitGS, sucursal, 
						nmsolici, nmtramite, TipoEndoso.CORRECCION_NOMBRE_Y_RFC.getCdTipSup().toString());
				*/
				mensaje="Se ha guardado el endoso "+respuestaEndosoNombres.get("pv_nsuplogi_o");
			}
			else
			{
				mensaje="El endoso "+respuestaEndosoNombres.get("pv_nsuplogi_o")
						+" se guard&oacute; en mesa de control para autorizaci&oacute;n "
						+ "con n&uacute;mero de tr&aacute;mite "+confirmado;
			}
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al generar endoso de nombres",ex);
			success=false;
			error=ex.getMessage();
		}
		log.debug(""
				+ "\n######                      ######"
				+ "\n###### guardarEndosoNombres ######"
				+ "\n##################################"
				+ "\n##################################"
				);
		return SUCCESS;
	}
	/*////////////////////////////////////////////////*/
	////// generar el endoso de cambio de nombres //////
	////////////////////////////////////////////////////
	
	//////////////////////////////////////////
	////// guardar endosos de clausulas //////
	//////                              //////
	////// smap1.pv_cdunieco_i          //////
    ////// smap1.pv_cdramo_i            //////
    ////// smap1.pv_estado_i            //////
    ////// smap1.pv_nmpoliza_i          //////
    ////// smap1.pv_nmsituac_i          //////
    ////// smap1.pv_cdclausu_i          //////
    ////// smap1.pv_nmsuplem_i          //////
    ////// smap1.pv_ntramite_i          //////
    ////// smap1.pv_cdtipsit_i          //////
    ////// smap1.pv_status_i            //////
    ////// smap1.pv_cdtipcla_i          //////
    ////// smap1.pv_swmodi_i            //////
    ////// smap1.pv_accion_i            //////
    ////// smap1.confirmar              //////
    ////// smap1.pv_dslinea_i           //////
    ////// smap1.fecha_endoso           //////
	/*//////////////////////////////////////*/
	public String guardarEndosoClausulaPaso()
	{
		log.debug(""
				+ "\n#######################################"
				+ "\n#######################################"
				+ "\n###### guardarEndosoClausulaPaso ######"
				+ "\n######                           ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			UserVO usuario=(UserVO)session.get("USUARIO");
			
			String fechaEndoso  = smap1.get("fecha_endoso");
			Date   fechaEndosoD = renderFechas.parse(fechaEndoso);
			
			////////////////////////////
			////// iniciar endoso //////
			/*
			 * pv_cdunieco_i-
			 * pv_cdramo_i-
			 * pv_estado_i-
			 * pv_nmpoliza_i-
			 * pv_fecha_i
			 * pv_cdelemen_i
			 * pv_cdusuari_i
			 * pv_proceso_i
			 * pv_cdtipsup_i
			 */
			omap1=new LinkedHashMap<String,Object>(0);
			omap1.put("pv_cdunieco_i" , smap1.get("pv_cdunieco_i"));
			omap1.put("pv_cdramo_i"   , smap1.get("pv_cdramo_i"));
			omap1.put("pv_estado_i"   , smap1.get("pv_estado_i"));
			omap1.put("pv_nmpoliza_i" , smap1.get("pv_nmpoliza_i"));
			omap1.put("pv_fecha_i"    , fechaEndosoD);
			omap1.put("pv_cdelemen_i" , usuario.getEmpresa().getElementoId());
			omap1.put("pv_cdusuari_i" , usuario.getUser());
			omap1.put("pv_proceso_i"  , "END");
			omap1.put("pv_cdtipsup_i", TipoEndoso.CAMBIO_ENDOSOS_EXCLUSION_O_TEXTOS.getCdTipSup().toString());
			
			Map<String,String> resEnd=endososManager.guardarEndosoClausulas(omap1);
			////// iniciar endoso //////
			////////////////////////////
			
			if(!(smap1.get("confirmar")!=null&&smap1.get("confirmar").equalsIgnoreCase("si")))
			{
				
				/////////////////////////////////
				////// modificar clausulas //////
				kernelManager.PMovMpolicot(smap1);
				////// modificar clausulas //////
				/////////////////////////////////
			
			}
			else
			{
				
				String tramiteGenerado=this.confirmarEndoso(
						smap1.get("pv_cdunieco_i"), 
						smap1.get("pv_cdramo_i"), 
						smap1.get("pv_estado_i"), 
						smap1.get("pv_nmpoliza_i"), 
						resEnd.get("pv_nmsuplem_o"), 
						resEnd.get("pv_nsuplogi_o"), 
						TipoEndoso.CAMBIO_ENDOSOS_EXCLUSION_O_TEXTOS.getCdTipSup().toString(),
						"",
						fechaEndosoD,
						smap1.get("pv_cdtipsit_i")
						);
			    
				if(tramiteGenerado==null||tramiteGenerado.length()==0)
				{

				    ///////////////////////////////////////
				    ////// re generar los documentos //////
				    /*///////////////////////////////////*/
				    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
					paramsGetDoc.put("pv_cdunieco_i" , smap1.get("pv_cdunieco_i"));
					paramsGetDoc.put("pv_cdramo_i"   , smap1.get("pv_cdramo_i"));
					paramsGetDoc.put("pv_estado_i"   , smap1.get("pv_estado_i"));
					paramsGetDoc.put("pv_nmpoliza_i" , smap1.get("pv_nmpoliza_i"));
					paramsGetDoc.put("pv_nmsuplem_i" , resEnd.get("pv_nmsuplem_o"));
					paramsGetDoc.put("pv_tipmov_i"   , TipoEndoso.CAMBIO_ENDOSOS_EXCLUSION_O_TEXTOS.getCdTipSup().toString());
				    List<Map<String,String>>listaDocu=endososManager.reimprimeDocumentos(paramsGetDoc);
				    log.debug("documentos que se regeneran: "+listaDocu);
				    
				    String rutaCarpeta=this.getText("ruta.documentos.poliza")+"/"+listaDocu.get(0).get("ntramite");
				    
					//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
					for(Map<String,String> docu:listaDocu)
					{
						log.debug("docu iterado: "+docu);
						String nmsolici=docu.get("nmsolici");
						String nmsituac=docu.get("nmsituac");
						String descripc=docu.get("descripc");
						String descripl=docu.get("descripl");
						String url=this.getText("ruta.servidor.reports")
								+ "?destype=cache"
								+ "&desformat=PDF"
								+ "&userid="+this.getText("pass.servidor.reports")
								+ "&report="+descripl
								+ "&paramform=no"
								+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
								+ "&p_unieco="+smap1.get("pv_cdunieco_i")
								+ "&p_ramo="+smap1.get("pv_cdramo_i")
								+ "&p_estado="+smap1.get("pv_estado_i")
								+ "&p_poliza="+smap1.get("pv_nmpoliza_i")
								+ "&p_suplem="+resEnd.get("pv_nmsuplem_o")
								+ "&desname="+rutaCarpeta+"/"+descripc;
						if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
						{
							// C R E D E N C I A L _ X X X X X X . P D F
							//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
							url+="&p_cdperson="+descripc.substring(11, descripc.lastIndexOf("_"));
						}
						log.debug(""
								+ "\n#################################"
								+ "\n###### Se solicita reporte ######"
								+ "\na "+url+""
								+ "\n#################################");
						HttpUtil.generaArchivo(url,rutaCarpeta+"/"+descripc);
						log.debug(""
								+ "\n######                    ######"
								+ "\n###### reporte solicitado ######"
								+ "\na "+url+""
								+ "\n################################"
								+ "\n################################"
								+ "");
					}
				    /*///////////////////////////////////*/
					////// re generar los documentos //////
				    ///////////////////////////////////////
					
					////// confirmar endoso //////
					//////////////////////////////
					
					mensaje="Se ha confirmado el endoso "+resEnd.get("pv_nsuplogi_o");
				}
				else
				{
					mensaje="El endoso "+resEnd.get("pv_nsuplogi_o")
							+" se guard&oacute; en mesa de control para autorizaci&oacute;n "
							+ "con n&uacute;mero de tr&aacute;mite "+tramiteGenerado;
				}
			}
			
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar endoso de clausula paso",ex);
			success=false;
			error=ex.getMessage();
		}
		log.debug(""
				+ "\n######                           ######"
				+ "\n###### guardarEndosoClausulaPaso ######"
				+ "\n#######################################"
				+ "\n#######################################"
				);
		return SUCCESS;
	}
	/*//////////////////////////////////////*/
	////// guardar endosos de clausulas //////
	//////////////////////////////////////////
	
	//////////////////////////////////////////////////////
	////// generar el endoso de cambio de domicilio //////
	//////                                          //////
    ////// smap1:                                   //////
	////// pv_cdunieco                              //////
	////// pv_cdramo                                //////
    ////// pv_estado                                //////
	////// pv_nmpoliza                              //////
    ////// pv_nmsituac                              //////
	////// pv_cdrol                                 //////
	////// pv_cdperson                              //////
    ////// CODPOSTAL                                //////
    ////// NMORDDOM                                 //////
    ////// NMNUMINT                                 //////
    ////// asegurado                                //////
    ////// Municipio                                //////
    ////// NMNUMERO                                 //////
    ////// rfc                                      //////
    ////// NMTELEFO                                 //////
    ////// CDMUNICI                                 //////
    ////// estado                                   //////
    ////// CDEDO                                    //////
    ////// CDCOLONI                                 //////
    ////// DSDOMICI                                 //////
    //////                                          //////
    ////// smap2:                                   //////
    ////// pv_fecha_i                               //////
    ////// cdtipsit                                 //////
    //////                                          //////
	/*//////////////////////////////////////////////////*/
	public String guardarEndosoDomicilio()
	{
		log.debug("\n#########################################"
				+ "\n#########################################"
				+ "\n###### guardar endoso de domicilio ######"
				+ "\n######                             ######"
				+ "\n######                             ######");
		try
		{
			log.debug("smap1: "+smap1);
			log.debug("smap2: "+smap2);
			log.debug("parametros: "+parametros);
			
			UserVO usuario=(UserVO) session.get("USUARIO");
			/*
			 * pv_cdunieco_i
			 * pv_cdramo_i
			 * pv_estado_i
			 * pv_nmpoliza_i
			 * pv_fecha_i
			 * pv_cdelemen_i
			 * pv_cdusuari_i
			 * pv_proceso_i
			 * pv_cdtipsup_i
			 */
			Map<String,Object> mapGuaEnd=new LinkedHashMap<String,Object>(0);
			mapGuaEnd.put("pv_cdunieco_i" , smap1.get("pv_cdunieco"));
			mapGuaEnd.put("pv_cdramo_i"   , smap1.get("pv_cdramo"));
			mapGuaEnd.put("pv_estado_i"   , smap1.get("pv_estado"));
			mapGuaEnd.put("pv_nmpoliza_i" , smap1.get("pv_nmpoliza"));
			mapGuaEnd.put("pv_fecha_i"    , renderFechas.parse((String)smap2.get("pv_fecha_i")));
			mapGuaEnd.put("pv_cdelemen_i" , usuario.getEmpresa().getElementoId());
			mapGuaEnd.put("pv_cdusuari_i" , usuario.getUser());
			mapGuaEnd.put("pv_proceso_i"  , "END");
			mapGuaEnd.put("pv_cdtipsup_i", TipoEndoso.CAMBIO_DOMICILIO.getCdTipSup().toString());
			Map<String,String> resEndDomi=endososManager.guardarEndosoNombres(mapGuaEnd);
			
			/*
			pv_cdunieco    smap1  ready!
			pv_cdramo      smap1  ready!
			pv_estado      smap1  ready!
			pv_nmpoliza    smap1  ready!
			pv_nmsituac    smap1  ready!
			pv_nmsuplem    #0
			pv_status      #V
			pv_cdrol       smap1  ready!
			pv_cdperson    smap1  ready!
			pv_cdatribu    #null
			pv_cdtipsit    session
			pv_otvalor01   parametros ready!
			...
			*/
			if(parametros!=null&&parametros.size()>0)
			{
				parametros.putAll(smap1);
				parametros.put("pv_nmsuplem" , resEndDomi.get("pv_nmsuplem_o"));
				parametros.put("pv_status"   , "V");
				parametros.put("pv_cdatribu" , null);
				parametros.put("pv_cdtipsit" , smap2.get("cdtipsit"));
				kernelManager.pMovTvaloper(parametros);
			}
			
			
			/////////////////////////////////////////
			////// guardar persona datos fijos //////
			/*/////////////////////////////////////*/
			/*
			pv_cdperson_i smap1.pv_cdperson
			pv_nmorddom_i smap1.NMORDDOM
			pv_msdomici_i smap1.DSDOMICI
			pv_nmtelefo_i smap1.NMTELEFO
			pv_cdpostal_i smap1.CODPOSTAL
			pv_cdedo_i    smap1.CDEDO
			pv_cdmunici_i smap1.CDMUNICI
			pv_cdcoloni_i smap1.CDCOLONI
			pv_nmnumero_i smap1.NMNUMERO
			pv_nmnumint_i smap1.NMNUMINT
			pv_accion_i   #U
			pv_msg_id_o   -
			pv_title_o    -
			*/
			Map<String,String>paramDomicil=new LinkedHashMap<String,String>(0);
			paramDomicil.put("pv_cdperson_i" , smap1.get("pv_cdperson"));
			paramDomicil.put("pv_nmorddom_i" , smap1.get("NMORDDOM"));
			paramDomicil.put("pv_msdomici_i" , smap1.get("DSDOMICI"));
			paramDomicil.put("pv_nmtelefo_i" , smap1.get("NMTELEFO"));
			paramDomicil.put("pv_cdpostal_i" , smap1.get("CODPOSTAL"));
        	paramDomicil.put("pv_cdedo_i"    , smap1.get("CDEDO"));
			paramDomicil.put("pv_cdmunici_i" , smap1.get("CDMUNICI"));
			paramDomicil.put("pv_cdcoloni_i" , smap1.get("CDCOLONI"));
			paramDomicil.put("pv_nmnumero_i" , smap1.get("NMNUMERO"));
			paramDomicil.put("pv_nmnumint_i" , smap1.get("NMNUMINT"));
			paramDomicil.put("pv_accion_i"   , "U");			
			kernelManager.pMovMdomicil(paramDomicil);
			/*/////////////////////////////////////*/
			////// guardar persona datos fijos //////
			/////////////////////////////////////////
			
			String tramiteGenerado=this.confirmarEndoso(
					(String)mapGuaEnd.get("pv_cdunieco_i"),
					(String)mapGuaEnd.get("pv_cdramo_i"),
					(String)mapGuaEnd.get("pv_estado_i"),
					(String)mapGuaEnd.get("pv_nmpoliza_i"),
					resEndDomi.get("pv_nmsuplem_o"),
					resEndDomi.get("pv_nsuplogi_o"),
					TipoEndoso.CAMBIO_DOMICILIO.getCdTipSup().toString(),
					"",
					renderFechas.parse((String)smap2.get("pv_fecha_i")),
					smap2.get("cdtipsit")
					);
		    
			if(tramiteGenerado==null||tramiteGenerado.length()==0)
			{
				
			    ///////////////////////////////////////
			    ////// re generar los documentos //////
			    /*///////////////////////////////////*/
			    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
				paramsGetDoc.put("pv_cdunieco_i" , smap1.get("pv_cdunieco"));
				paramsGetDoc.put("pv_cdramo_i"   , smap1.get("pv_cdramo"));
				paramsGetDoc.put("pv_estado_i"   , smap1.get("pv_estado"));
				paramsGetDoc.put("pv_nmpoliza_i" , smap1.get("pv_nmpoliza"));
				paramsGetDoc.put("pv_nmsuplem_i" , resEndDomi.get("pv_nmsuplem_o"));
				paramsGetDoc.put("pv_tipmov_i"   , TipoEndoso.CAMBIO_DOMICILIO.getCdTipSup().toString());
			    List<Map<String,String>>listaDocu=endososManager.reimprimeDocumentos(paramsGetDoc);
			    log.debug("documentos que se regeneran: "+listaDocu);
			    
			    String rutaCarpeta=this.getText("ruta.documentos.poliza")+"/"+listaDocu.get(0).get("ntramite");
			    
				//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
				for(Map<String,String> docu:listaDocu)
				{
					log.debug("docu iterado: "+docu);
					String nmsolici=docu.get("nmsolici");
					String nmsituac=docu.get("nmsituac");
					String descripc=docu.get("descripc");
					String descripl=docu.get("descripl");
					String url=this.getText("ruta.servidor.reports")
							+ "?destype=cache"
							+ "&desformat=PDF"
							+ "&userid="+this.getText("pass.servidor.reports")
							+ "&report="+descripl
							+ "&paramform=no"
							+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
							+ "&p_unieco="+smap1.get("pv_cdunieco")
							+ "&p_ramo="+smap1.get("pv_cdramo")
							+ "&p_estado="+smap1.get("pv_estado")
							+ "&p_poliza="+smap1.get("pv_nmpoliza")
							+ "&p_suplem="+resEndDomi.get("pv_nmsuplem_o")
							+ "&desname="+rutaCarpeta+"/"+descripc;
					if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
					{
						// C R E D E N C I A L _ X X X X X X . P D F
						//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
						url+="&p_cdperson="+descripc.substring(11, descripc.lastIndexOf("_"));
					}
					log.debug(""
							+ "\n#################################"
							+ "\n###### Se solicita reporte ######"
							+ "\na "+url+""
							+ "\n#################################");
					HttpUtil.generaArchivo(url,rutaCarpeta+"/"+descripc);
					log.debug(""
							+ "\n######                    ######"
							+ "\n###### reporte solicitado ######"
							+ "\na "+url+""
							+ "\n################################"
							+ "\n################################"
							+ "");
				}
			    /*///////////////////////////////////*/
				////// re generar los documentos //////
			    ///////////////////////////////////////
				
				ejecutaWSclienteSaludEndoso(smap1.get("pv_cdunieco"), smap1.get("pv_cdramo"), smap1.get("pv_estado"), smap1.get("pv_nmpoliza"), resEndDomi.get("pv_nmsuplem_o"), "ACTUALIZA");
				

				/**
				 * TODO: Poner variable el cdTipSitGS de la poliza y la sucursal
				 
				String cdtipsitGS = "213";
				String sucursal = smap1.get("pv_cdunieco");
				if(StringUtils.isNotBlank(sucursal) && "1".equals(sucursal)) sucursal = "1000";
				
				String nmsolici = listaDocu.get(0).get("nmsolici");
				String nmtramite = listaDocu.get(0).get("ntramite");
				
				insertaURLrecibosEndoso(smap1.get("pv_cdunieco"), smap1.get("pv_cdramo"), smap1.get("pv_estado"), 
						smap1.get("pv_nmpoliza"), resEndDomi.get("pv_nmsuplem_o"), cdtipsitGS, sucursal, nmsolici, nmtramite, TipoEndoso.CAMBIO_DOMICILIO.getCdTipSup().toString());
				*/
				
			    mensaje="Se ha guardado el endoso "+resEndDomi.get("pv_nsuplogi_o");
			    
			}
			else
			{
				mensaje="El endoso "+resEndDomi.get("pv_nsuplogi_o")
						+" se guard&oacute; en mesa de control para autorizaci&oacute;n "
						+ "con n&uacute;mero de tr&aacute;mite "+tramiteGenerado;
			}
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar los datos de endoso de domicilio",ex);
			success=false;
			error=ex.getMessage();
		}
		log.debug("\n######                             ######"
				+ "\n######                             ######"
				+ "\n###### guardar endoso de domicilio ######"
				+ "\n#########################################"
				+ "\n#########################################");
		return SUCCESS;
	}
	/*//////////////////////////////////////////////////*/
	////// generar el endoso de cambio de domicilio //////
	//////////////////////////////////////////////////////
	
	////////////////////////////////////////////
	////// obtener coberturas disponibles //////
	/*////////////////////////////////////////*/
	public String obtenerCoberturasDisponibles()
	{
		log.debug(""
				+ "\n##########################################"
				+ "\n##########################################"
				+ "\n###### obtenerCoberturasDisponibles ######"
				+ "\n######                              ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			slist1=endososManager.obtieneCoberturasDisponibles(smap1);
		}
		catch(Exception ex)
		{
			log.error("error al cargar las coberturas disponibles");
		}
		success=true;
		log.debug(""
				+ "\n######                              ######"
				+ "\n###### obtenerCoberturasDisponibles ######"
				+ "\n##########################################"
				+ "\n##########################################"
				);
		return SUCCESS;
	}
	/*////////////////////////////////////////*/
	////// obtener coberturas disponibles //////
	////////////////////////////////////////////
	
	/////////////////////////////////////
	////// guardarEndosoCoberturas //////
	////// smap1:                  //////
	//////     nmsituac            //////
	//////     cdperson            //////
    //////     altabaja            //////
	//////     cdtipsit            //////
	//////     confirmar           //////
	////// omap1:                  //////
	//////     pv_cdunieco_i       //////
	//////     pv_cdramo_i         //////
	//////     pv_estado_i         //////
	//////     pv_nmpoliza_i       //////
	//////     pv_fecha_i          //////
	////// slist1: quitar cober.   //////
	////// slist2: agregar cober.  //////
	////// [garantia,cdcapita,     //////
    ////// status,ptcapita,        //////
	////// ptreduci,fereduci       //////
	//////,swrevalo,cdagrupa]      //////
	/*/////////////////////////////////*/
	public String guardarEndosoCoberturas()
	{
		log.debug(""
				+ "\n#####################################"
				+ "\n#####################################"
				+ "\n###### guardarEndosoCoberturas ######"
				+ "\n######                         ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("omap1: "+omap1);
		log.debug("slist1: "+slist1);
		log.debug("slist2: "+slist2);
		this.session=ActionContext.getContext().getSession();
        UserVO usuario=(UserVO) session.get("USUARIO");
		try
		{
			/*
			 * pv_cdunieco_i
			 * pv_cdramo_i
			 * pv_estado_i
			 * pv_nmpoliza_i
			 * pv_fecha_i
			 * pv_cdelemen_i
			 * pv_cdusuari_i
			 * pv_proceso_i
			 * pv_cdtipsup_i
			 */
			omap1.put("pv_fecha_i",renderFechas.parse((String)omap1.get("pv_fecha_i")));
			omap1.put("pv_cdelemen_i" , usuario.getEmpresa().getElementoId());
			omap1.put("pv_cdusuari_i" , usuario.getUser());
			omap1.put("pv_proceso_i"  , "END");
			if(smap1.get("altabaja").equalsIgnoreCase("alta"))
			{
				omap1.put("pv_cdtipsup_i", TipoEndoso.ALTA_COBERTURAS.getCdTipSup().toString());
			}
			else
			{
				omap1.put("pv_cdtipsup_i", TipoEndoso.BAJA_COBERTURAS.getCdTipSup().toString());
			}
			Map<String,String> respEndCob=endososManager.guardarEndosoCoberturas(omap1);
			
			for(Map<String,String> nuevo:slist2)
			{
				/*
				pv_cdunieco_i
				pv_cdramo_i
				pv_estado_i
				pv_nmpoliza_i
				pv_nmsituac_i
				pv_cdcapita_i
				pv_nmsuplem_i
				pv_status_i
				pv_ptcapita_i
				pv_ptreduci_i
				pv_fereduci_i
				pv_swrevalo_i
				pv_cdagrupa_i
				pv_accion_i
				*
				Map<String,String>paramAgregaCober=new LinkedHashMap<String,String>(0);
				paramAgregaCober.put("pv_cdunieco_i" , (String)omap1.get("pv_cdunieco_i"));
				paramAgregaCober.put("pv_cdramo_i"   , (String)omap1.get("pv_cdramo_i"));
				paramAgregaCober.put("pv_estado_i"   , (String)omap1.get("pv_estado_i"));
				paramAgregaCober.put("pv_nmpoliza_i" , (String)omap1.get("pv_nmpoliza_i"));
				paramAgregaCober.put("pv_nmsituac_i" , smap1.get("nmsituac"));
				paramAgregaCober.put("pv_cdcapita_i" , nuevo.get("cdcapita"));
				paramAgregaCober.put("pv_nmsuplem_i" , respEndCob.get("pv_nmsuplem_o"));
				paramAgregaCober.put("pv_status_i"   , nuevo.get("status"));
				paramAgregaCober.put("pv_ptcapita_i" , nuevo.get("ptcapita"));
				paramAgregaCober.put("pv_ptreduci_i" , nuevo.get("ptreduci"));
				paramAgregaCober.put("pv_fereduci_i" , nuevo.get("fereduci"));
				paramAgregaCober.put("pv_swrevalo_i" , nuevo.get("swrevalo"));
				paramAgregaCober.put("pv_cdagrupa_i" , nuevo.get("cdagrupa"));
				paramAgregaCober.put("pv_accion_i"   , "I");
				kernelManager.movPolicap(paramAgregaCober);
				/**/
				
				/*
				pv_cdunieco_i
			    pv_cdramo_i
			    pv_estado_i
			    pv_nmpoliza_i
			    pv_nmsituac_i
			    pv_cdgarant_i
			    pv_nmsuplem_i
			    pv_cdcapita_i
			    pv_status_i
			    pv_cdtipbca_i
			    pv_ptvalbas_i
			    pv_swmanual_i
			    pv_swreas_i
			    pv_cdagrupa_i
			    PV_ACCION
				*/
				Map<String,String>paramQuitaCober=new LinkedHashMap<String,String>(0);
				paramQuitaCober.put("pv_cdunieco_i" , (String)omap1.get("pv_cdunieco_i"));
				paramQuitaCober.put("pv_cdramo_i"   , (String)omap1.get("pv_cdramo_i"));
				paramQuitaCober.put("pv_estado_i"   , (String)omap1.get("pv_estado_i"));
				paramQuitaCober.put("pv_nmpoliza_i" , (String)omap1.get("pv_nmpoliza_i"));
				paramQuitaCober.put("pv_nmsituac_i" , smap1.get("nmsituac"));
				paramQuitaCober.put("pv_cdgarant_i" , nuevo.get("garantia"));
				paramQuitaCober.put("pv_nmsuplem_i" , respEndCob.get("pv_nmsuplem_o"));
				paramQuitaCober.put("pv_cdcapita_i" , nuevo.get("cdcapita"));
				paramQuitaCober.put("pv_status_i"   , nuevo.get("status"));
				paramQuitaCober.put("pv_cdtipbca_i" , nuevo.get("cdtipbca"));
				paramQuitaCober.put("pv_ptvalbas_i" , nuevo.get("ptvalbas"));
				paramQuitaCober.put("pv_swmanual_i" , nuevo.get("swmanual"));
				paramQuitaCober.put("pv_swreas_i"   , nuevo.get("swreas"));
				paramQuitaCober.put("pv_cdagrupa_i" , nuevo.get("cdagrupa"));
				paramQuitaCober.put("PV_ACCION"     , "I");
				paramQuitaCober.put("pv_cdtipsup_i" , TipoEndoso.ALTA_COBERTURAS.getCdTipSup().toString());
				kernelManager.movPoligar(paramQuitaCober);
				/**/
				
				/*
				pv_cdunieco_i
	    		pv_cdramo_i
	    		pv_estado_i
	    		pv_nmpoliza_i
	    		pv_nmsituac_i
	    		pv_nmsuplem_i
	    		pv_cdgarant_i
	    		*/
				Map<String,String>paramSigsvdef=new LinkedHashMap<String,String>(0);
				paramSigsvdef.put("pv_cdunieco_i" , (String)omap1.get("pv_cdunieco_i"));
				paramSigsvdef.put("pv_cdramo_i"   , (String)omap1.get("pv_cdramo_i"));
				paramSigsvdef.put("pv_estado_i"   , (String)omap1.get("pv_estado_i"));
				paramSigsvdef.put("pv_nmpoliza_i" , (String)omap1.get("pv_nmpoliza_i"));
				paramSigsvdef.put("pv_nmsituac_i" , smap1.get("nmsituac"));
				paramSigsvdef.put("pv_nmsuplem_i" , respEndCob.get("pv_nmsuplem_o"));
				paramSigsvdef.put("pv_cdgarant_i" , nuevo.get("garantia"));
				paramSigsvdef.put("pv_cdtipsup_i", TipoEndoso.ALTA_COBERTURAS.getCdTipSup().toString());
				kernelManager.coberturas(paramSigsvdef);
			}
			
			for(Map<String,String> borrar:slist1)
			{
				/*
				pv_cdunieco_i
			    pv_cdramo_i
			    pv_estado_i
			    pv_nmpoliza_i
			    pv_nmsituac_i
			    pv_cdgarant_i
			    pv_nmsuplem_i
			    pv_cdcapita_i
			    pv_status_i
			    pv_cdtipbca_i
			    pv_ptvalbas_i
			    pv_swmanual_i
			    pv_swreas_i
			    pv_cdagrupa_i
			    PV_ACCION
				*/
				Map<String,String>paramQuitaCober=new LinkedHashMap<String,String>(0);
				paramQuitaCober.put("pv_cdunieco_i" , (String)omap1.get("pv_cdunieco_i"));
				paramQuitaCober.put("pv_cdramo_i"   , (String)omap1.get("pv_cdramo_i"));
				paramQuitaCober.put("pv_estado_i"   , (String)omap1.get("pv_estado_i"));
				paramQuitaCober.put("pv_nmpoliza_i" , (String)omap1.get("pv_nmpoliza_i"));
				paramQuitaCober.put("pv_nmsituac_i" , smap1.get("nmsituac"));
				paramQuitaCober.put("pv_cdgarant_i" , borrar.get("garantia"));
				paramQuitaCober.put("pv_nmsuplem_i" , respEndCob.get("pv_nmsuplem_o"));
				paramQuitaCober.put("pv_cdcapita_i" , borrar.get("cdcapita"));
				paramQuitaCober.put("pv_status_i"   , borrar.get("status"));
				paramQuitaCober.put("pv_cdtipbca_i" , borrar.get("cdtipbca"));
				paramQuitaCober.put("pv_ptvalbas_i" , borrar.get("ptvalbas"));
				paramQuitaCober.put("pv_swmanual_i" , borrar.get("swmanual"));
				paramQuitaCober.put("pv_swreas_i"   , borrar.get("swreas"));
				paramQuitaCober.put("pv_cdagrupa_i" , borrar.get("cdagrupa"));
				paramQuitaCober.put("PV_ACCION"     , "B");
				paramQuitaCober.put("pv_cdtipsup_i" , TipoEndoso.BAJA_COBERTURAS.getCdTipSup().toString());
				kernelManager.movPoligar(paramQuitaCober);
			}
			
			/*
			pv_cdunieco_i
			pv_cdramo_i
	        pv_estado_i
	        pv_nmpoliza_i
	        pv_nmsuplem_i
			 */
			Map<String,String>paramsObtAtriCober=new LinkedHashMap<String,String>(0);
			paramsObtAtriCober.put("pv_cdunieco_i" , (String)omap1.get("pv_cdunieco_i"));
			paramsObtAtriCober.put("pv_cdramo_i"   , (String)omap1.get("pv_cdramo_i"));
	        paramsObtAtriCober.put("pv_estado_i"   , (String)omap1.get("pv_estado_i"));
	        paramsObtAtriCober.put("pv_nmpoliza_i" , (String)omap1.get("pv_nmpoliza_i"));
	        paramsObtAtriCober.put("pv_nmsituac_i" , smap1.get("nmsituac"));
	        paramsObtAtriCober.put("pv_nmsuplem_i" , respEndCob.get("pv_nmsuplem_o"));
	        List<Map<String,String>>listaTempAtributosCober=endososManager.obtenerAtributosCoberturas(paramsObtAtriCober);
	        Map<String,String>atriCober=listaTempAtributosCober.get(0);

        	//cargar anterior valosit
			Map<String,String>paramsValositAsegurado=new LinkedHashMap<String,String>(0);
			paramsValositAsegurado.put("pv_cdunieco_i" , (String)omap1.get("pv_cdunieco_i"));
			paramsValositAsegurado.put("pv_cdramo_i"   , (String)omap1.get("pv_cdramo_i"));
			paramsValositAsegurado.put("pv_estado_i"   , (String)omap1.get("pv_estado_i"));
			paramsValositAsegurado.put("pv_nmpoliza_i" , (String)omap1.get("pv_nmpoliza_i"));
			paramsValositAsegurado.put("pv_nmsituac_i" , smap1.get("nmsituac"));
			Map<String,Object>valositAsegurado=kernelManager.obtieneValositSituac(paramsValositAsegurado);
			log.debug("valosit anterior: "+valositAsegurado);
			
			//poner pv_ al anterior
			Map<String,Object>valositAseguradoIterado=new LinkedHashMap<String,Object>(0);
			Iterator it=valositAsegurado.entrySet().iterator();
			while(it.hasNext())
			{
				Entry en=(Entry)it.next();
				valositAseguradoIterado.put("pv_"+(String)en.getKey(),en.getValue());//agregar pv_ a los anteriores
			}
			valositAsegurado=valositAseguradoIterado;
			log.debug("se puso pv_ en el anterior");
			
			//agregar los nuevos al leido
			if(atriCober.get("OTVALOR09")!=null&&atriCober.get("OTVALOR09").length()>0)
			{
				valositAsegurado.put("pv_otvalor09",atriCober.get("OTVALOR09"));
			}
			if(atriCober.get("OTVALOR10")!=null&&atriCober.get("OTVALOR10").length()>0)
			{
				valositAsegurado.put("pv_otvalor10",atriCober.get("OTVALOR10"));
			}
			if(atriCober.get("OTVALOR14")!=null&&atriCober.get("OTVALOR14").length()>0)
			{
				valositAsegurado.put("pv_otvalor14",atriCober.get("OTVALOR14"));
			}
			if(atriCober.get("OTVALOR15")!=null&&atriCober.get("OTVALOR15").length()>0)
			{
				valositAsegurado.put("pv_otvalor15",atriCober.get("OTVALOR15"));
			}
			log.debug("se agregaron los nuevos");
			
			//convertir a string el total que es object
			Map<String,String>paramsNuevos=new LinkedHashMap<String,String>(0);
			it=valositAsegurado.entrySet().iterator();
			while(it.hasNext())
			{
				Entry en=(Entry)it.next();
				paramsNuevos.put((String)en.getKey(),(String)en.getValue());
			}
			log.debug("se pasaron a string");
			
			/*
			pv_cdunieco
    		pv_cdramo
    		pv_estado
    		pv_nmpoliza
    		pv_nmsituac
    		pv_nmsuplem
    		pv_status
    		pv_cdtipsit
    		...pv_otvalor[01-50]
    		pv_accion_i
			*/
			paramsNuevos.put("pv_cdunieco" , (String)omap1.get("pv_cdunieco_i"));
			paramsNuevos.put("pv_cdramo"   , (String)omap1.get("pv_cdramo_i"));
			paramsNuevos.put("pv_estado"   , (String)omap1.get("pv_estado_i"));
			paramsNuevos.put("pv_nmpoliza" , (String)omap1.get("pv_nmpoliza_i"));
			paramsNuevos.put("pv_nmsituac" , smap1.get("nmsituac"));
			
			paramsNuevos.put("pv_nmsuplem" , respEndCob.get("pv_nmsuplem_o"));
			paramsNuevos.put("pv_status"   , "V");
			paramsNuevos.put("pv_cdtipsit" , smap1.get("cdtipsit"));
			paramsNuevos.put("pv_accion_i" , "I");
			log.debug("los actualizados seran: "+paramsNuevos);
			kernelManager.insertaValoresSituaciones(paramsNuevos);
			
			/*
			pv_cdusuari_i
			pv_cdelemen_i
			pv_cdunieco_i
			pv_cdramo_i
			pv_estado_i
			pv_nmpoliza_i
			pv_nmsituac_i
			pv_nmsuplem_i
			pv_cdtipsit_i
			*/
			Map<String,String>paramSigsvdefEnd=new LinkedHashMap<String,String>(0);
			paramSigsvdefEnd.put("pv_cdusuari_i" , usuario.getEmpresa().getElementoId());
			paramSigsvdefEnd.put("pv_cdelemen_i" , usuario.getUser());
			paramSigsvdefEnd.put("pv_cdunieco_i" , (String)omap1.get("pv_cdunieco_i"));
			paramSigsvdefEnd.put("pv_cdramo_i"   , (String)omap1.get("pv_cdramo_i"));
			paramSigsvdefEnd.put("pv_estado_i"   , (String)omap1.get("pv_estado_i"));
			paramSigsvdefEnd.put("pv_nmpoliza_i" , (String)omap1.get("pv_nmpoliza_i"));
			paramSigsvdefEnd.put("pv_nmsituac_i" , smap1.get("nmsituac"));
			paramSigsvdefEnd.put("pv_nmsuplem_i" , respEndCob.get("pv_nmsuplem_o"));
			paramSigsvdefEnd.put("pv_cdtipsit_i" , smap1.get("cdtipsit"));
			if(smap1.get("altabaja").equalsIgnoreCase("alta"))
			{
				paramSigsvdefEnd.put("pv_cdtipsup_i", TipoEndoso.ALTA_COBERTURAS.getCdTipSup().toString());
			}
			else
			{
				paramSigsvdefEnd.put("pv_cdtipsup_i", TipoEndoso.BAJA_COBERTURAS.getCdTipSup().toString());
			}
			endososManager.sigsvalipolEnd(paramSigsvdefEnd);
			
			if(smap1.get("confirmar").equalsIgnoreCase("si"))
			{
				Map<String,Object>paramCalcValorEndoso=new LinkedHashMap<String,Object>(0);
				paramCalcValorEndoso.put("pv_cdunieco_i" , (String)omap1.get("pv_cdunieco_i"));
				paramCalcValorEndoso.put("pv_cdramo_i"   , (String)omap1.get("pv_cdramo_i"));
				paramCalcValorEndoso.put("pv_estado_i"   , (String)omap1.get("pv_estado_i"));
				paramCalcValorEndoso.put("pv_nmpoliza_i" , (String)omap1.get("pv_nmpoliza_i"));
				paramCalcValorEndoso.put("pv_nmsituac_i" , smap1.get("nmsituac"));
				paramCalcValorEndoso.put("pv_nmsuplem_i" , respEndCob.get("pv_nmsuplem_o"));
				paramCalcValorEndoso.put("pv_feinival_i" , (Date)omap1.get("pv_fecha_i"));
				if(smap1.get("altabaja").equalsIgnoreCase("alta"))
				{
					paramCalcValorEndoso.put("pv_cdtipsup_i", TipoEndoso.ALTA_COBERTURAS.getCdTipSup().toString());
				}
				else
				{
					paramCalcValorEndoso.put("pv_cdtipsup_i", TipoEndoso.BAJA_COBERTURAS.getCdTipSup().toString());
				}
				endososManager.calcularValorEndoso(paramCalcValorEndoso);
				
				String tramiteGenerado=this.confirmarEndoso(
						(String)omap1.get("pv_cdunieco_i"), 
						(String)omap1.get("pv_cdramo_i"), 
						(String)omap1.get("pv_estado_i"), 
						(String)omap1.get("pv_nmpoliza_i"), 
						respEndCob.get("pv_nmsuplem_o"), 
						respEndCob.get("pv_nsuplogi_o"), 
						smap1.get("altabaja").equalsIgnoreCase("alta")
							? TipoEndoso.ALTA_COBERTURAS.getCdTipSup().toString()
							: TipoEndoso.BAJA_COBERTURAS.getCdTipSup().toString(),
						"", 
						(Date)omap1.get("pv_fecha_i"), 
						smap1.get("cdtipsit")
						);
			    
				if(tramiteGenerado==null||tramiteGenerado.length()==0)
				{
				
				    ///////////////////////////////////////
				    ////// re generar los documentos //////
				    /*///////////////////////////////////*/
				    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
					paramsGetDoc.put("pv_cdunieco_i" , (String)omap1.get("pv_cdunieco_i"));
					paramsGetDoc.put("pv_cdramo_i"   , (String)omap1.get("pv_cdramo_i"));
					paramsGetDoc.put("pv_estado_i"   , (String)omap1.get("pv_estado_i"));
					paramsGetDoc.put("pv_nmpoliza_i" , (String)omap1.get("pv_nmpoliza_i"));
					paramsGetDoc.put("pv_nmsuplem_i" , respEndCob.get("pv_nmsuplem_o"));
					if(smap1.get("altabaja").equalsIgnoreCase("alta"))
					{
						paramsGetDoc.put("pv_tipmov_i"   , TipoEndoso.ALTA_COBERTURAS.getCdTipSup().toString());
					}
					else
					{
						paramsGetDoc.put("pv_tipmov_i"   , TipoEndoso.BAJA_COBERTURAS.getCdTipSup().toString());
					}
				    List<Map<String,String>>listaDocu=endososManager.reimprimeDocumentos(paramsGetDoc);
				    log.debug("documentos que se regeneran: "+listaDocu);
				    
				    String rutaCarpeta=this.getText("ruta.documentos.poliza")+"/"+listaDocu.get(0).get("ntramite");
				    
					//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
					for(Map<String,String> docu:listaDocu)
					{
						log.debug("docu iterado: "+docu);
						String nmsolici=docu.get("nmsolici");
						String nmsituac=docu.get("nmsituac");
						String descripc=docu.get("descripc");
						String descripl=docu.get("descripl");
						String url=this.getText("ruta.servidor.reports")
								+ "?destype=cache"
								+ "&desformat=PDF"
								+ "&userid="+this.getText("pass.servidor.reports")
								+ "&report="+descripl
								+ "&paramform=no"
								+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
								+ "&p_unieco="+(String)omap1.get("pv_cdunieco_i")
								+ "&p_ramo="+(String)omap1.get("pv_cdramo_i")
								+ "&p_estado="+(String)omap1.get("pv_estado_i")
								+ "&p_poliza="+(String)omap1.get("pv_nmpoliza_i")
								+ "&p_suplem="+respEndCob.get("pv_nmsuplem_o")
								+ "&desname="+rutaCarpeta+"/"+descripc;
						if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
						{
							// C R E D E N C I A L _ X X X X X X . P D F
							//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
							url+="&p_cdperson="+descripc.substring(11, descripc.lastIndexOf("_"));
						}
						log.debug(""
								+ "\n#################################"
								+ "\n###### Se solicita reporte ######"
								+ "\na "+url+""
								+ "\n#################################");
						HttpUtil.generaArchivo(url,rutaCarpeta+"/"+descripc);
						log.debug(""
								+ "\n######                    ######"
								+ "\n###### reporte solicitado ######"
								+ "\na "+url+""
								+ "\n################################"
								+ "\n################################"
								+ "");
					}
				    /*///////////////////////////////////*/
					////// re generar los documentos //////
				    ///////////////////////////////////////
					
					/**
					 * TODO: Poner variable el cdTipSitGS de la poliza y la sucursal
					 */
					String cdtipsitGS = "213";
					String sucursal = (String)omap1.get("pv_cdunieco_i");
					if(StringUtils.isNotBlank(sucursal) && "1".equals(sucursal)) sucursal = "1000";
					
					String nmsolici = listaDocu.get(0).get("nmsolici");
					String nmtramite = listaDocu.get(0).get("ntramite");
					
					String tipomov = "";
					if(smap1.get("altabaja").equalsIgnoreCase("alta")) tipomov = "6";
					else tipomov = "7";
					
					
					ejecutaWSrecibosEndoso((String)omap1.get("pv_cdunieco_i"), (String)omap1.get("pv_cdramo_i"),
							(String)omap1.get("pv_estado_i"), (String)omap1.get("pv_nmpoliza_i"),
							respEndCob.get("pv_nmsuplem_o"), respEndCob.get("pv_nsuplogi_o"), rutaCarpeta,
							cdtipsitGS, sucursal, nmsolici, nmtramite,
							true, "INSERTA", tipomov );
					
					
					mensaje="Se ha confirmado el endoso "+respEndCob.get("pv_nsuplogi_o");
				}
				else
				{
					mensaje="El endoso "+respEndCob.get("pv_nsuplogi_o")
							+" se guard&oacute; en mesa de control para autorizaci&oacute;n "
							+ "con n&uacute;mero de tr&aacute;mite "+tramiteGenerado;
				}
			}
		    else
			{
				mensaje="Se ha guardado el endoso "+respEndCob.get("pv_nsuplogi_o");
			}
			
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar el endoso de coberturas",ex);
			success=false;
			error=ex.getMessage();
		}
		log.debug(""
				+ "\n######                         ######"
				+ "\n###### guardarEndosoCoberturas ######"
				+ "\n#####################################"
				+ "\n#####################################"
				);
		return SUCCESS;
	}
	/*/////////////////////////////////*/
	////// guardarEndosoCoberturas //////
	/////////////////////////////////////
	
	/////////////////////////////////
	////// endoso B de valosit //////
	////// smap1:              //////
	//////     cdunieco        //////
	//////     cdramo          //////
	//////     estado          //////
	//////     nmpoliza        //////
	//////     cdtipsit        //////
	//////     nmsituac        //////
	//////     ntramite        //////
	//////     nmsuplem        //////
	/*/////////////////////////////*/
	public String endosoValositBasico()
	{
		log.debug(""
				+ "\n#################################"
				+ "\n#################################"
				+ "\n###### endosoValositBasico ######"
				+ "\n######                     ######"
				);
		log.debug("smap1: "+smap1);
		String respuesta=this.validaEndosoAnterior(
				smap1.get("cdunieco")
				,smap1.get("cdramo")
				,smap1.get("estado")
				,smap1.get("nmpoliza")
				,TipoEndoso.CORRECCION_ANTIGUEDAD_Y_PARENTESCO.getCdTipSup().toString());
		if(respuesta.equals(SUCCESS))
		{
			try
			{
				List<Tatri>tatrisit=kernelManager.obtenerTatrisit(smap1.get("cdtipsit"));
				GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gc.setCdtipsit(smap1.get("cdtipsit"));
				List<Tatri>tatriTemp=new ArrayList<Tatri>(0);
				for(Tatri t:tatrisit)
				//si es agrupado solo dejar los atributos con N, si es individual solo los que tengan S
				{
					if(t.getSwsuscri().equalsIgnoreCase("S")&&t.getSwtarifi().equalsIgnoreCase("N"))//S=individual
					{
						tatriTemp.add(t);
					}
				}
				tatrisit=tatriTemp;
				gc.genera(tatrisit);
				item1=gc.getFields();
				item2=gc.getItems();
			}
			catch(Exception ex)
			{
				log.error("error al mostrar la pantalla de valosit",ex);
			}
		}
		log.debug(""
				+ "\n######                     ######"
				+ "\n###### endosoValositBasico ######"
				+ "\n#################################"
				+ "\n#################################"
				);
		return respuesta;
	}
	/*/////////////////////////////*/
	////// endoso B de valosit //////
	/////////////////////////////////
	
	///////////////////////////////////////////
	////// guardar endoso valosit basico //////
	////// smap1:                        //////
	//////     fecha_endoso              //////
	//////     cdunieco                  //////
	//////     cdramo                    //////
	//////     estado                    //////
	//////     nmpoliza                  //////
	//////     cdtipsit                  //////
	//////     nmsituac                  //////
	//////     confirmar                 //////
	////// parametros: tvalosit          //////
	/*///////////////////////////////////////*/
	public String guardarEndosoValositBasico()
	{
		log.debug(""
				+ "\n########################################"
				+ "\n########################################"
				+ "\n###### guardarEndosoValositBasico ######"
				+ "\n######                            ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("parametros: "+parametros);
		
		try
		{
			UserVO usuario=(UserVO)session.get("USUARIO");
			/*
			 * pv_cdunieco_i
			 * pv_cdramo_i
			 * pv_estado_i
			 * pv_nmpoliza_i
			 * pv_fecha_i
			 * pv_cdelemen_i
			 * pv_cdusuari_i
			 * pv_proceso_i
			 * pv_cdtipsup_i
			 */
			omap1=new LinkedHashMap<String,Object>();
			omap1.put("pv_cdunieco_i" , smap1.get("cdunieco"));
			omap1.put("pv_cdramo_i"   , smap1.get("cdramo"));
			omap1.put("pv_estado_i"   , smap1.get("estado"));
			omap1.put("pv_nmpoliza_i" , smap1.get("nmpoliza"));
			omap1.put("pv_fecha_i"    , renderFechas.parse(smap1.get("fecha_endoso")));
			omap1.put("pv_cdelemen_i" , usuario.getEmpresa().getElementoId());
			omap1.put("pv_cdusuari_i" , usuario.getUser());
			omap1.put("pv_proceso_i"  , "END");
			omap1.put("pv_cdtipsup_i", TipoEndoso.CORRECCION_ANTIGUEDAD_Y_PARENTESCO.getCdTipSup().toString());
			Map<String,String> respEnd=endososManager.guardarEndosoCoberturas(omap1);

			//////////////////////////
			////// datos poliza //////
			/*
			Map<String,String>paramsObtenerDatosMpolisit=new HashMap<String,String>();
			paramsObtenerDatosMpolisit.put("pv_cdunieco_i" , smap1.get("cdunieco"));
			paramsObtenerDatosMpolisit.put("pv_cdramo_i"   , smap1.get("cdramo"));
			paramsObtenerDatosMpolisit.put("pv_estado_i"   , smap1.get("estado"));
			paramsObtenerDatosMpolisit.put("pv_nmpoliza_i" , smap1.get("nmpoliza"));
			Map<String,String>respuestaObtenerDatosMpolisit=endososManager.obtieneDatosMpolisit(paramsObtenerDatosMpolisit);
			//String nmsituacNuevo=respuestaObtenerDatosMpolisit.get("pv_nmsituac_o");
			String cdplan=respuestaObtenerDatosMpolisit.get("pv_cdplan_o");
			*/
			////// datos poliza //////
			//////////////////////////
			
			/////////////////////
			////// polisit //////
			/*
			Map<String,Object>mapaPolisit=new HashMap<String,Object>(0);
            mapaPolisit.put("pv_cdunieco_i",    smap1.get("cdunieco"));
            mapaPolisit.put("pv_cdramo_i",      smap1.get("cdramo"));
            mapaPolisit.put("pv_estado_i",      smap1.get("estado"));
            mapaPolisit.put("pv_nmpoliza_i",    smap1.get("nmpoliza"));
            mapaPolisit.put("pv_nmsituac_i",    smap1.get("nmsituac"));
            mapaPolisit.put("pv_nmsuplem_i",    respEnd.get("pv_nmsuplem_o"));
            mapaPolisit.put("pv_status_i",      "V");
            mapaPolisit.put("pv_cdtipsit_i",    smap1.get("cdtipsit"));
            mapaPolisit.put("pv_swreduci_i",    null);
            mapaPolisit.put("pv_cdagrupa_i",    "1");
            mapaPolisit.put("pv_cdestado_i",    "0");
            mapaPolisit.put("pv_fefecsit_i",    renderFechas.parse(smap1.get("fecha_endoso")));
            mapaPolisit.put("pv_fecharef_i",    renderFechas.parse(smap1.get("fecha_endoso")));
            mapaPolisit.put("pv_cdgrupo_i",     null);
            mapaPolisit.put("pv_nmsituaext_i",  null);
            mapaPolisit.put("pv_nmsitaux_i",    null);
            mapaPolisit.put("pv_nmsbsitext_i",  null);
            mapaPolisit.put("pv_cdplan_i",      cdplan);
            mapaPolisit.put("pv_cdasegur_i",    "30");
            mapaPolisit.put("pv_accion_i",      "I");
            kernelManager.insertaPolisit(mapaPolisit);
            */
			////// polisit //////
            /////////////////////
			
			//cargar anterior valosit
			Map<String,String>paramsValositAsegurado=new LinkedHashMap<String,String>(0);
			paramsValositAsegurado.put("pv_cdunieco_i", smap1.get("cdunieco"));
			paramsValositAsegurado.put("pv_cdramo_i",   smap1.get("cdramo"));
			paramsValositAsegurado.put("pv_estado_i",   smap1.get("estado"));
			paramsValositAsegurado.put("pv_nmpoliza_i", smap1.get("nmpoliza"));
			paramsValositAsegurado.put("pv_nmsituac_i", smap1.get("nmsituac"));
			Map<String,Object>valositAsegurado=kernelManager.obtieneValositSituac(paramsValositAsegurado);
			log.debug("valosit anterior: "+valositAsegurado);
			
			//poner pv_ al anterior
			Map<String,Object>valositAseguradoIterado=new LinkedHashMap<String,Object>(0);
			Iterator it=valositAsegurado.entrySet().iterator();
			while(it.hasNext())
			{
				Entry en=(Entry)it.next();
				valositAseguradoIterado.put("pv_"+(String)en.getKey(),en.getValue());//agregar pv_ a los anteriores
			}
			valositAsegurado=valositAseguradoIterado;
			log.debug("se puso pv_ en el anterior");
			
			//agregar los del form al leido
			Iterator it2=parametros.entrySet().iterator();
			while(it2.hasNext())
			{
				Entry en=(Entry)it2.next();
				valositAsegurado.put((String)en.getKey(),en.getValue());//tienen pv_ los del form
				//ya agregamos todos los nuevos en el mapa
			}
			log.debug("se agregaron los nuevos");
			
			//convertir a string el total
			Map<String,String>paramsNuevos=new LinkedHashMap<String,String>(0);
			it=valositAsegurado.entrySet().iterator();
			while(it.hasNext())
			{
				Entry en=(Entry)it.next();
				paramsNuevos.put((String)en.getKey(),(String)en.getValue());
			}
			log.debug("se pasaron a string");
			
			/*
			pv_cdunieco
    		pv_cdramo
    		pv_estado
    		pv_nmpoliza
    		pv_nmsituac
    		pv_nmsuplem
    		pv_status
    		pv_cdtipsit
    		...pv_otvalor[01-50]
    		pv_accion_i
			*/
			paramsNuevos.put("pv_cdunieco" , smap1.get("cdunieco"));
			paramsNuevos.put("pv_cdramo"   , smap1.get("cdramo"));
			paramsNuevos.put("pv_estado"   , smap1.get("estado"));
			paramsNuevos.put("pv_nmpoliza" , smap1.get("nmpoliza"));
			paramsNuevos.put("pv_nmsituac" , smap1.get("nmsituac"));
			paramsNuevos.put("pv_nmsuplem" , respEnd.get("pv_nmsuplem_o"));
			paramsNuevos.put("pv_status"   , "V");
			paramsNuevos.put("pv_cdtipsit" , smap1.get("cdtipsit"));
			paramsNuevos.put("pv_accion_i" , "I");
			log.debug("los actualizados seran: "+paramsNuevos);
			kernelManager.insertaValoresSituaciones(paramsNuevos);
            
			//////////////////////
			////// cdperson //////
			Map<String,String>mapCdperson=new HashMap<String,String>(0);
			mapCdperson.put("pv_cdunieco" , smap1.get("cdunieco"));
			mapCdperson.put("pv_cdramo"   , smap1.get("cdramo"));
			mapCdperson.put("pv_estado"   , smap1.get("estado"));
			mapCdperson.put("pv_nmpoliza" , smap1.get("nmpoliza"));
			mapCdperson.put("pv_nmsituac" , smap1.get("nmsituac"));
			List<Map<String,String>>listCdperson=endososManager.obtenerCdpersonMpoliper(mapCdperson);
			////// cdperson //////
			//////////////////////
			
            //////////////////////
			////// mpoliper //////
			Map<String,Object>mapaMpoliper=new LinkedHashMap<String,Object>(0);
			mapaMpoliper.put("pv_cdunieco_i" , smap1.get("cdunieco"));
			mapaMpoliper.put("pv_cdramo_i"   , smap1.get("cdramo"));
			mapaMpoliper.put("pv_estado_i"   , smap1.get("estado"));
			mapaMpoliper.put("pv_nmpoliza_i" , smap1.get("nmpoliza"));
			mapaMpoliper.put("pv_nmsituac_i" , smap1.get("nmsituac"));
			mapaMpoliper.put("pv_cdrol_i"    , "2");
			mapaMpoliper.put("pv_cdperson_i" , listCdperson.get(0).get("CDPERSON"));
			mapaMpoliper.put("pv_nmsuplem_i" , respEnd.get("pv_nmsuplem_o"));
			mapaMpoliper.put("pv_status_i"   , "V");
			mapaMpoliper.put("pv_nmorddom_i" , "1");
			mapaMpoliper.put("pv_swreclam_i" , null);
			mapaMpoliper.put("pv_accion_i"   , "I");
			mapaMpoliper.put("pv_swexiper_i" , Constantes.SI);
			kernelManager.movMpoliper(mapaMpoliper);
			////// mpoliper //////
			//////////////////////
			
			if(smap1.get("confirmar").equalsIgnoreCase("si"))
			{
				
				String tramiteGenerado=this.confirmarEndoso(
						smap1.get("cdunieco"),
						smap1.get("cdramo"),
						smap1.get("estado"),
						smap1.get("nmpoliza"),
						respEnd.get("pv_nmsuplem_o"),
						respEnd.get("pv_nsuplogi_o"),
						TipoEndoso.CORRECCION_ANTIGUEDAD_Y_PARENTESCO.getCdTipSup().toString(),
						"",
						renderFechas.parse(smap1.get("fecha_endoso")),
						smap1.get("cdtipsit")
						);	    
		    
				if(tramiteGenerado==null||tramiteGenerado.length()==0)
				{
				
				    ///////////////////////////////////////
				    ////// re generar los documentos //////
				    /*///////////////////////////////////*/
				    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
					paramsGetDoc.put("pv_cdunieco_i" , smap1.get("cdunieco"));
					paramsGetDoc.put("pv_cdramo_i"   , smap1.get("cdramo"));
					paramsGetDoc.put("pv_estado_i"   , smap1.get("estado"));
					paramsGetDoc.put("pv_nmpoliza_i" , smap1.get("nmpoliza"));
					paramsGetDoc.put("pv_nmsuplem_i" , respEnd.get("pv_nmsuplem_o"));
					paramsGetDoc.put("pv_tipmov_i"   , TipoEndoso.CORRECCION_ANTIGUEDAD_Y_PARENTESCO.getCdTipSup().toString());
				    List<Map<String,String>>listaDocu=endososManager.reimprimeDocumentos(paramsGetDoc);
				    log.debug("documentos que se regeneran: "+listaDocu);
				    
				    String rutaCarpeta=this.getText("ruta.documentos.poliza")+"/"+listaDocu.get(0).get("ntramite");
				    
					//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
					for(Map<String,String> docu:listaDocu)
					{
						log.debug("docu iterado: "+docu);
						String nmsolici=docu.get("nmsolici");
						String nmsituac=docu.get("nmsituac");
						String descripc=docu.get("descripc");
						String descripl=docu.get("descripl");
						String url=this.getText("ruta.servidor.reports")
								+ "?destype=cache"
								+ "&desformat=PDF"
								+ "&userid="+this.getText("pass.servidor.reports")
								+ "&report="+descripl
								+ "&paramform=no"
								+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
								+ "&p_unieco="+smap1.get("cdunieco")
								+ "&p_ramo="+smap1.get("cdramo")
								+ "&p_estado="+smap1.get("estado")
								+ "&p_poliza="+smap1.get("nmpoliza")
								+ "&p_suplem="+respEnd.get("pv_nmsuplem_o")
								+ "&desname="+rutaCarpeta+"/"+descripc;
						if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
						{
							// C R E D E N C I A L _ X X X X X X . P D F
							//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
							url+="&p_cdperson="+descripc.substring(11, descripc.lastIndexOf("_"));
						}
						log.debug(""
								+ "\n#################################"
								+ "\n###### Se solicita reporte ######"
								+ "\na "+url+""
								+ "\n#################################");
						HttpUtil.generaArchivo(url,rutaCarpeta+"/"+descripc);
						log.debug(""
								+ "\n######                    ######"
								+ "\n###### reporte solicitado ######"
								+ "\na "+url+""
								+ "\n################################"
								+ "\n################################"
								+ "");
					}
				    /*///////////////////////////////////*/
					////// re generar los documentos //////
				    ///////////////////////////////////////
					
					mensaje="Se ha confirmado el endoso "+respEnd.get("pv_nsuplogi_o");
					
				}
				else
				{
					mensaje="El endoso "+respEnd.get("pv_nsuplogi_o")
							+" se guard&oacute; en mesa de control para autorizaci&oacute;n "
							+ "con n&uacute;mero de tr&aacute;mite "+tramiteGenerado;
				}
			}
			else
			{				
				mensaje="Se ha guardado el endoso "+respEnd.get("pv_nsuplogi_o");
			}
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar endosos de valosit basico",ex);
			success=false;
			error=ex.getMessage();
		}
		
		log.debug(""
				+ "\n######                            ######"
				+ "\n###### guardarEndosoValositBasico ######"
				+ "\n########################################"
				+ "\n########################################"
				);
		return SUCCESS;
	}
	/*///////////////////////////////////////*/
	////// guardar endoso valosit basico //////
	///////////////////////////////////////////

	/////////////////////////////////
	////// editor de pantallas //////
	/*/////////////////////////////*/
	public String editorPantallas()
	{
		log.debug(""
				+ "\n#############################"
				+ "\n#############################"
				+ "\n###### editorPantallas ######"
				+ "\n######                 ######"
				);
		try
		{
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			imap1=new LinkedHashMap<String,Item>(0);
			
			List<Tatri>tatriFiltro=pantallasManager.obtenerCamposPantalla(null,null,null,null,null,null,"EDITORPANTALLAS",null,null,"FILTRO");
			gc.generaParcial(tatriFiltro);
			imap1.put("itemsFiltro"  , gc.getItems());
			imap1.put("fieldsFiltro" , gc.getFields());
			
			List<Tatri>tatriModelo=pantallasManager.obtenerCamposPantalla(null,null,null,null,null,null,"EDITORPANTALLAS",null,null,"MODELO");
			gc.generaParcial(tatriModelo);
			imap1.put("itemsModelo"   , gc.getItems());
			imap1.put("fieldsModelo"  , gc.getFields());
			imap1.put("columnsModelo" , gc.getColumns());
			
			imap1.put("storeArbol",pantallasManager.obtenerArbol());
		}
		catch(Exception ex)
		{
			log.error("error al cargar la pantalla de alvaro",ex);
		}
		log.debug(""
				+ "\n######                 ######"
				+ "\n###### editorPantallas ######"
				+ "\n#############################"
				+ "\n#############################"
				);
		return SUCCESS;
	}
	/*/////////////////////////////*/
	////// editor de pantallas //////
	/////////////////////////////////
	
	////////////////////////////////
	////// visor de pantallas //////
	/*////////////////////////////*/
	public String visorPantallas()
	{
		log.debug(""
				+ "\n############################"
				+ "\n############################"
				+ "\n###### visorPantallas ######"
				+ "\n######                ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			List<Tatri>lt=pantallasManager.obtenerCamposPantalla(
					smap1.get("cduno")
					,smap1.get("cddos")
					,smap1.get("cdtres")
					,smap1.get("cdcuatro")
					,smap1.get("cdcinco")
					,smap1.get("cdseis")
					,smap1.get("cdsiete")
					,smap1.get("cdocho")
					,smap1.get("cdnueve")
					,smap1.get("cddiez")
					);
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaParcial(lt);
			item1=gc.getFields();
			item2=gc.getItems();
			item3=gc.getColumns();
		}
		catch(Exception ex)
		{
			log.error("error al visualizar pantalla",ex);
		}
		log.debug(""
				+ "\n######                ######"
				+ "\n###### visorPantallas ######"
				+ "\n############################"
				+ "\n############################"
				);
		return SUCCESS;
	}
	/*////////////////////////////*/
	////// visor de pantallas //////
	////////////////////////////////
	
	////////////////////////////////////////////
	////// obtener parametros de pantalla //////
	/*////////////////////////////////////////*/
	public String obtenerParametrosPantalla()
	{
		log.debug(""
				+ "\n#######################################"
				+ "\n#######################################"
				+ "\n###### obtenerParametrosPantalla ######"
				+ "\n######                           ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			slist1=pantallasManager.obtenerParametrosPantalla(
					smap1.get("cduno")
					,smap1.get("cddos")
					,smap1.get("cdtres")
					,smap1.get("cdcuatro")
					,smap1.get("cdcinco")
					,smap1.get("cdseis")
					,smap1.get("cdsiete")
					,smap1.get("cdocho")
					,smap1.get("cdnueve")
					,smap1.get("cddiez")
					);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al obtener parametros pantalla",ex);
			success=false;
		}
		log.debug(""
				+ "\n######                           ######"
				+ "\n###### obtenerParametrosPantalla ######"
				+ "\n#######################################"
				+ "\n#######################################"
				);
		return SUCCESS;
	}
	/*////////////////////////////////////////*/
	////// obtener parametros de pantalla //////
	////////////////////////////////////////////
	
	////////////////////////////////////////////
	////// guardar parametros de pantalla //////
	/*////////////////////////////////////////*/
	public String guardarParametrosPantalla()
	{
		log.debug(""
				+ "\n#######################################"
				+ "\n#######################################"
				+ "\n###### guardarParametrosPantalla ######"
				+ "\n######                           ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("slist1: "+slist1);
		try
		{
			if(slist1!=null)
			{
				pantallasManager.borrarParametrosPantalla(
				smap1.get("cduno")
				,smap1.get("cddos")
				,smap1.get("cdtres")
				,smap1.get("cdcuatro")
				,smap1.get("cdcinco")
				,smap1.get("cdseis")
				,smap1.get("cdsiete")
				,smap1.get("cdocho")
				,smap1.get("cdnueve")
				,smap1.get("cddiez")
						);
				for(Map<String,String>nuevo:slist1)
				{
					pantallasManager.insertarParametrosPantalla(nuevo);
				}
			}
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar parametros pantalla",ex);
			success=false;
		}
		log.debug(""
				+ "\n######                           ######"
				+ "\n###### guardarParametrosPantalla ######"
				+ "\n#######################################"
				+ "\n#######################################"
				);
		return SUCCESS;
	}
	/*////////////////////////////////////////*/
	////// guardar parametros de pantalla //////
	////////////////////////////////////////////
	
	//////////////////////////////////////////////
	////// pantalla de endosos de clausulas //////
	/*
	smap1.cdunieco:1006
	smap1.cdramo:2
	smap1.estado:M
	smap1.nmpoliza:18
	smap1.cdtipsit:SL
	smap1.nmsituac:2
	smap1.ntramite:662
	smap1.nmsuplem:245668011510000012
	*/
	/*//////////////////////////////////////////*/
	public String pantallaEndosoClausulas()
	{
		log.debug(""
				+ "\n##############################################"
				+ "\n##############################################"
				+ "\n###### pantalla de endosos de clausulas ######"
				+ "\n######                                  ######"
				);
		log.debug("smap1: "+smap1);
		String respuesta=this.validaEndosoAnterior(
				smap1.get("cdunieco")
				,smap1.get("cdramo")
				,smap1.get("estado")
				,smap1.get("nmpoliza")
				,TipoEndoso.CAMBIO_ENDOSOS_EXCLUSION_O_TEXTOS.getCdTipSup().toString()); 
		log.debug(""
				+ "\n######                                  ######"
				+ "\n###### pantalla de endosos de clausulas ######"
				+ "\n##############################################"
				+ "\n##############################################"
				);
		return respuesta;
	}
	/*//////////////////////////////////////////*/
	////// pantalla de endosos de clausulas //////
	//////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	////// pantalla de endoso de alta y/o baja de asegurados //////
	/*
	smap1:
		NMSUPLEM=245665412050000000,
		DSTIPSIT=SALUD VITAL,
		FEINIVAL=27/12/2013,
		NMPOLIZA=1,
		PRIMA_TOTAL=10830.45,
		NMPOLIEX=1904213000001000000,
		NSUPLOGI=0,
		DSCOMENT=EMISIÒN DE LA POLIZA,
		ESTADO=M,
		CDTIPSIT=SL,
		NTRAMITE=396,
		CDUNIECO=1904,
		FEEMISIO=27/12/2013,
		CDRAMO=2
	smap2
		alta=si
	*/
	/*///////////////////////////////////////////////////////////*/
	public String pantallaEndosoAltaBajaAsegurado()
	{
		this.session=ActionContext.getContext().getSession();
		log.debug("\n"
				+ "\n###############################################################"
				+ "\n###############################################################"
				+ "\n###### pantalla de endoso de alta y/o baja de asegurados ######"
				+ "\n######                                                   ######"
				);
		log.debug("smap1: "+smap1);
		String respuesta=this.validaEndosoAnterior(
				smap1.get("CDUNIECO")
				,smap1.get("CDRAMO")
				,smap1.get("ESTADO")
				,smap1.get("NMPOLIZA")
				,smap2.get("alta").equalsIgnoreCase("si")?
						TipoEndoso.ALTA_ASEGURADOS.getCdTipSup().toString():
						TipoEndoso.BAJA_ASEGURADOS.getCdTipSup().toString());
		if(respuesta.equals(SUCCESS))
		{
			try
			{
				String cdtipsit=smap1.get("CDTIPSIT");
				
				imap1=new HashMap<String,Item>();
				
				GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				
				gc.generaParcial(pantallasManager.obtenerCamposPantalla(
						 null,null,null
						,null,null,null
						,"ENDOSOABASEGU",null,null
						,"MODELO"));
				
				imap1.put("modelo"   , gc.getFields());
				imap1.put("columnas" , gc.getColumns());
				
				gc.generaParcial(pantallasManager.obtenerCamposPantalla(
						 null,null,null
						,null,null,null
						,"ENDOSOABASEGU",null,null
						,"PANELLECTURA"));
				
				imap1.put("panelLectura" , gc.getItems());
				
				////////////////////////////////////////////////
				////// campos de tatrisit para individual //////
				List<Tatri>tatrisit=kernelManager.obtenerTatrisit(cdtipsit);
				gc.setCdtipsit(cdtipsit);
				
				List<String>exclusiones=new ArrayList<String>();
				/*
				if(cdtipsit.equals("SL")||cdtipsit.equals("SN"))
				{
					exclusiones.add("3");//codigo postal
					exclusiones.add("4");//,"estado");
					exclusiones.add("5");//,"deducible");
					exclusiones.add("6");//,"copago");
					exclusiones.add("7");//,"suma asegurada");
					exclusiones.add("17");//,"municipio");
				}
				log.debug("exclusiones: "+exclusiones);
				*/
				
				List<Tatri>tatriTemp=new ArrayList<Tatri>(0);
				for(Tatri t:tatrisit)
				//solo dejar los atributos si es individual, los que tengan S
				{
					if(t.getSwsuscri().equalsIgnoreCase("S"))//S=individual
					{
						String name=t.getCdatribu();
						log.debug("se busca "+name+" en excluciones");
						if(!exclusiones.contains(name))
						{
							log.debug("no encontrado");
							tatriTemp.add(t);
						}
					}
				}
				tatrisit=tatriTemp;
				
				tatriTemp=pantallasManager.obtenerCamposPantalla(
						 null,null,null
						,null,null,null
						,"ENDOSOABASEGU",null,null
						,"FORMULARIO");
				tatriTemp.addAll(tatrisit);
				tatrisit=tatriTemp;
				
				gc.generaParcial(tatrisit);
				
				imap1.put("formulario" , gc.getItems());
				////// campos de tatrisit para individual //////
				////////////////////////////////////////////////
			}
			catch(Exception ex)
			{
				log.error("error al mostrar pantalla de alta y/o baja de asegurados",ex);
			}
		}
		log.debug("\n"
				+ "\n###### pantalla de endoso de alta y/o baja de asegurados ######"
				+ "\n######                                                   ######"
				+ "\n###############################################################"
				+ "\n###############################################################"
				);
		return respuesta;
	}
	/*///////////////////////////////////////////////////////////*/
	////// pantalla de endoso de alta y/o baja de asegurados //////
	///////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////
	////// guardar endoso de alta o baja de asegurados //////
	/*
	smap1: (cuando es por baja)
	    cdrfc,
	    cdperson,
	    fenacimi,
        sexo=H,
        swexiper,
        Apellido_Materno,
        nacional,
        nombre,
        nmsituac,
        cdrol,
        segundo_nombre,
        Parentesco=T,
        tpersona=F,
        Apellido_Paterno
    smap1: (cuando es por alta)
        amat,
        nmsituac,
        nombre,
        nombre2,
        tpersona,
        rfc,
        apat,
        cdnacion,
        parametros.pv_otvalor01, //generico
        parametros.pv_otvalor02, //generico
        parametros.pv_otvalor16, //generico
        parametros.pv_otvalor18, //generico
        parametros.pv_otvalor19  //generico
    smap2:
        NMSUPLEM,
        DSTIPSIT,
        FEINIVAL,
        NMPOLIZA,
        PRIMA_TOTAL,
        NMPOLIEX,
        NSUPLOGI,
        DSCOMENT,
        ESTADO,
        CDTIPSIT,
        NTRAMITE,
        FEEMISIO,
        CDRAMO,
        CDUNIECO
    slist1:[
        {dsclausu=ENDOSO LIBRE, linea_usuario=TEXTO LIBREasd, linea_general=, cdclausu=END215, merged=, cdtipcla=}
        ]
	*/
	/*/////////////////////////////////////////////////////*/
	public String guardarEndosoAltaBajaAsegurado()
	{
		this.session=ActionContext.getContext().getSession();
		
		log.debug("\n"
				+ "\n############################################"
				+ "\n############################################"				
				+ "\n###### guardarEndosoAltaBajaAsegurado ######"
				+ "\n######                                ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("smap2: "+smap2);
		log.debug("smap3: "+smap3);
		log.debug("slist1: "+slist1);
		try
		{
			UserVO usuario=(UserVO) session.get("USUARIO");
			
			boolean alta=smap1.containsKey("apat");
			
			String cdunieco = smap2.get("CDUNIECO");
			String cdramo   = smap2.get("CDRAMO");
			String estado   = smap2.get("ESTADO");
			String nmpoliza = smap2.get("NMPOLIZA");
			String cdtipsit = smap2.get("CDTIPSIT");
			String nmsituac = smap1.get("nmsituac");
			String rfc      = smap1.get("rfc");
			String nombre   = smap1.get("nombre");
			String nombre2  = smap1.get("nombre2");
			String tpersona = smap1.get("tpersona");
			String sexo     = smap1.get("parametros.pv_otvalor01");
			String fenacimi = smap1.get("parametros.pv_otvalor02");
			String apat     = smap1.get("apat");
			String amat     = smap1.get("amat");
			String cdnacion = smap1.get("cdnacion");
			String cdelemen = usuario.getEmpresa().getElementoId();
			String cdusuari = usuario.getUser();
			Date fechaHoy   = new Date();
			String ntramite = smap2.get("NTRAMITE");
			
			String fechaEndoso  = smap3.get("fecha_endoso");
			Date   fechaEndosoD = renderFechas.parse(fechaEndoso);
			
			Map<String,String>paramsIniciarEndoso=new HashMap<String,String>(0);
			paramsIniciarEndoso.put("pv_cdunieco_i" , cdunieco);
			paramsIniciarEndoso.put("pv_cdramo_i"   , cdramo);
			paramsIniciarEndoso.put("pv_estado_i"   , estado);
			paramsIniciarEndoso.put("pv_nmpoliza_i" , nmpoliza);
			paramsIniciarEndoso.put("pv_fecha_i"    , fechaEndoso);
			paramsIniciarEndoso.put("pv_cdelemen_i" , cdelemen);
			paramsIniciarEndoso.put("pv_cdusuari_i" , cdusuari);
			paramsIniciarEndoso.put("pv_proceso_i"  , "END");
			paramsIniciarEndoso.put("pv_cdtipsup_i", alta 
					? TipoEndoso.ALTA_ASEGURADOS.getCdTipSup().toString()
					: TipoEndoso.BAJA_ASEGURADOS.getCdTipSup().toString());
			Map<String,String>respuestaIniciarEndoso=endososManager.iniciarEndoso(paramsIniciarEndoso);
			
			String nmsuplem=respuestaIniciarEndoso.get("pv_nmsuplem_o");
			String nsuplogi=respuestaIniciarEndoso.get("pv_nsuplogi_o");
			
			Map<String,String>paramsObtenerDatosMpolisit=new HashMap<String,String>();
			paramsObtenerDatosMpolisit.put("pv_cdunieco_i" , cdunieco);
			paramsObtenerDatosMpolisit.put("pv_cdramo_i"   , cdramo);
			paramsObtenerDatosMpolisit.put("pv_estado_i"   , estado);
			paramsObtenerDatosMpolisit.put("pv_nmpoliza_i" , nmpoliza);
			Map<String,String>respuestaObtenerDatosMpolisit=endososManager.obtieneDatosMpolisit(paramsObtenerDatosMpolisit);
			String nmsituacNuevo=respuestaObtenerDatosMpolisit.get("pv_nmsituac_o");
			String cdplan=respuestaObtenerDatosMpolisit.get("pv_cdplan_o");
			
			if(alta)
			{
				nmsituac=nmsituacNuevo;
				
				//////////////////////
        		////// cdperson //////
				String cdperson=kernelManager.generaCdperson();
				////// cdperson //////
				//////////////////////
				
				/////////////////////
				////// polisit //////
				Map<String,Object>mapaPolisit=new HashMap<String,Object>(0);
                mapaPolisit.put("pv_cdunieco_i",    cdunieco);
                mapaPolisit.put("pv_cdramo_i",      cdramo);
                mapaPolisit.put("pv_estado_i",      estado);
                mapaPolisit.put("pv_nmpoliza_i",    nmpoliza);
                mapaPolisit.put("pv_nmsituac_i",    nmsituac);
                mapaPolisit.put("pv_nmsuplem_i",    nmsuplem);
                mapaPolisit.put("pv_status_i",      "V");
                mapaPolisit.put("pv_cdtipsit_i",    cdtipsit);
                mapaPolisit.put("pv_swreduci_i",    null);
                mapaPolisit.put("pv_cdagrupa_i",    "1");
                mapaPolisit.put("pv_cdestado_i",    "0");
                mapaPolisit.put("pv_fefecsit_i",    fechaHoy);
                mapaPolisit.put("pv_fecharef_i",    fechaHoy);
                mapaPolisit.put("pv_cdgrupo_i",     null);
                mapaPolisit.put("pv_nmsituaext_i",  null);
                mapaPolisit.put("pv_nmsitaux_i",    null);
                mapaPolisit.put("pv_nmsbsitext_i",  null);
                mapaPolisit.put("pv_cdplan_i",      cdplan);
                mapaPolisit.put("pv_cdasegur_i",    "30");
                mapaPolisit.put("pv_accion_i",      "I");
                kernelManager.insertaPolisit(mapaPolisit);
				////// polisit //////
                /////////////////////
                
                /////////////////////
                ////// valosit //////                
                
                ////// 1. mapa valosit base //////
                Map<String,String>mapaValosit=new HashMap<String,String>(0);
                mapaValosit.put("pv_cdunieco",    cdunieco);
                mapaValosit.put("pv_cdramo",      cdramo);
                mapaValosit.put("pv_estado",      estado);
                mapaValosit.put("pv_nmpoliza",    nmpoliza);
                mapaValosit.put("pv_nmsituac",    nmsituac);
                mapaValosit.put("pv_nmsuplem",    nmsuplem);
                mapaValosit.put("pv_status",      "V");
                mapaValosit.put("pv_cdtipsit",    cdtipsit);
                mapaValosit.put("pv_accion_i",   "I");
                ////// 1. mapa valosit base //////
                
                ////// 2. obtener el titular //////
                Map<String,String>mapaObtenerValosit=new HashMap<String,String>(0);
                mapaObtenerValosit.put("pv_cdunieco_i" , cdunieco);
                mapaObtenerValosit.put("pv_nmpoliza_i" , nmpoliza);
                mapaObtenerValosit.put("pv_cdramo_i"   , cdramo);
                mapaObtenerValosit.put("pv_estado_i"   , estado);
                mapaObtenerValosit.put("pv_nmsituac_i" , "1");
                Map<String,Object>valositTitular=kernelManager.obtieneValositSituac(mapaObtenerValosit);
                ////// 2. obtener el titular //////
                
                ////// 3. copiar los otvalor del titular a la base //////
                for(Entry<String,Object> en:valositTitular.entrySet())
                {
                	String key=en.getKey();
                	if(key.substring(0,3).equalsIgnoreCase("otv"))
                	{
                		mapaValosit.put("pv_"+key,(String)en.getValue());
                	}
                }
                ////// 3. copiar los otvalor del titular a la base //////
                
                ////// 4. sustituir los otvalor por los nuevos del form //////
                for(Entry<String,String> en:smap1.entrySet())
                {
                	String key=en.getKey();
                	if(key.substring(0,3).equalsIgnoreCase("par"))
                	{
                		// p a r a m e t r o s . pv_otvalorXX
                		//0 1 2 3 4 5 6 7 8 9 0 1
                		mapaValosit.put(key.substring(11),en.getValue());
                	}
                }
                ////// 4. sustituir los otvalor por los nuevos del form //////
                
                kernelManager.insertaValoresSituaciones(mapaValosit);
                
                ////// valosit //////
                /////////////////////
                
                //////////////////////
                ////// mpersona //////
                Map<String,Object> mapaMpersona=new LinkedHashMap<String,Object>(0);
				mapaMpersona.put("pv_cdperson_i"    , cdperson); 
				mapaMpersona.put("pv_cdtipide_i"    , "1");
				mapaMpersona.put("pv_cdideper_i"    , rfc);
				mapaMpersona.put("pv_dsnombre_i"    , nombre);
				mapaMpersona.put("pv_cdtipper_i"    , "1");
				mapaMpersona.put("pv_otfisjur_i"    , tpersona);
				mapaMpersona.put("pv_otsexo_i"      , sexo);
				mapaMpersona.put("pv_fenacimi_i"    , renderFechas.parse(fenacimi));
				mapaMpersona.put("pv_cdrfc_i"       , rfc);
				mapaMpersona.put("pv_dsemail_i"     , "");
				mapaMpersona.put("pv_dsnombre1_i"   , nombre2);
				mapaMpersona.put("pv_dsapellido_i"  , apat);
				mapaMpersona.put("pv_dsapellido1_i" , amat);
				mapaMpersona.put("pv_feingreso_i"   , fechaHoy);
				mapaMpersona.put("pv_cdnacion_i"    , cdnacion);
				mapaMpersona.put("pv_accion_i"      , "I");
				kernelManager.movMpersona(mapaMpersona);
                ////// mpersona //////
                //////////////////////
				
				//////////////////////
				////// mpoliper //////
				Map<String,Object>mapaMpoliper=new LinkedHashMap<String,Object>(0);
				mapaMpoliper.put("pv_cdunieco_i" , cdunieco);
				mapaMpoliper.put("pv_cdramo_i"   , cdramo);
				mapaMpoliper.put("pv_estado_i"   , estado);
				mapaMpoliper.put("pv_nmpoliza_i" , nmpoliza);
				mapaMpoliper.put("pv_nmsituac_i" , nmsituac);
				mapaMpoliper.put("pv_cdrol_i"    , "2");
				mapaMpoliper.put("pv_cdperson_i" , cdperson);
				mapaMpoliper.put("pv_nmsuplem_i" , nmsuplem);
				mapaMpoliper.put("pv_status_i"   , "V");
				mapaMpoliper.put("pv_nmorddom_i" , "1");
				mapaMpoliper.put("pv_swreclam_i" , null);
				mapaMpoliper.put("pv_accion_i"   , "I");
				mapaMpoliper.put("pv_swexiper_i" , Constantes.NO);
				kernelManager.movMpoliper(mapaMpoliper);
				////// mpoliper //////
				//////////////////////
                
				///////////////////////
				////// clausulas //////
				/*///////////////////*/
				for(Map<String,String>cla:slist1)
				{
					//{dsclausu=ENDOSO LIBRE, linea_usuario=TEXTO LIBREasd, linea_general=, cdclausu=END215, merged=, cdtipcla=}
					String cdclausu = cla.get("cdclausu");
					String dslinea  = cla.get("linea_usuario");
					String cdtipcla = cla.get("cdtipcla");					
					
					Map<String,String>policot=new HashMap<String,String>();
					policot.put("pv_cdunieco_i" , cdunieco);
					policot.put("pv_cdramo_i"   , cdramo);
					policot.put("pv_estado_i"   , estado);
					policot.put("pv_nmpoliza_i" , nmpoliza);
					policot.put("pv_nmsituac_i" , nmsituac);
					policot.put("pv_cdclausu_i" , cdclausu);
					policot.put("pv_nmsuplem_i" , nmsuplem);
					policot.put("pv_status_i"   , "V");
					policot.put("pv_cdtipcla_i" , cdtipcla);
					policot.put("pv_swmodi_i"   , null);
					policot.put("pv_dslinea_i"  , dslinea);
					policot.put("pv_accion_i"   , "I");
					kernelManager.PMovMpolicot(policot);
				}
				/*///////////////////*/
				////// clausulas //////
				///////////////////////
				
				///////////////////////////////////
				////// validacion extraprima //////
				/*///////////////////////////////*/
				Map<String,String>paramValExtraprima=new LinkedHashMap<String,String>(0);
				paramValExtraprima.put("pv_cdunieco_i" , cdunieco);
				paramValExtraprima.put("pv_cdramo_i"   , cdramo);
				paramValExtraprima.put("pv_estado_i"   , estado);
				paramValExtraprima.put("pv_nmpoliza_i" , nmpoliza);
				paramValExtraprima.put("pv_nmsituac_i" , nmsituac);
				String statusValidacionExtraprimas=(String) kernelManager.validarExtraprimaSituac(paramValExtraprima).getItemMap().get("status");
				log.debug("tiene status la extraprima: "+statusValidacionExtraprimas);
				if(statusValidacionExtraprimas.equalsIgnoreCase("N"))
				{
					error="Favor de verificar las extraprimas y los endosos de extraprima";
					throw new Exception(error);
				}
				/*///////////////////////////////*/
				////// validacion extraprima //////
				///////////////////////////////////
				
				///////////////////////
				////// domicilio //////
				
				////// 1. obtener asegurados //////
				Map<String,String> mapaObtenerAsegurados=new HashMap<String,String>(0);
				mapaObtenerAsegurados.put("pv_cdunieco" , cdunieco);
				mapaObtenerAsegurados.put("pv_cdramo"   , cdramo);
				mapaObtenerAsegurados.put("pv_estado"   , estado);
				mapaObtenerAsegurados.put("pv_nmpoliza" , nmpoliza);
				mapaObtenerAsegurados.put("pv_nmsuplem" , nmsuplem);
				List<Map<String,Object>>asegurados=kernelManager.obtenerAsegurados(mapaObtenerAsegurados);
				////// 1. obtener asegurados //////
				
				////// 2. obtener cdperson titular //////
				String cdpersonTitular = "";
				for(Map<String,Object>aseguradoIterado:asegurados)
				{
					if(((String)aseguradoIterado.get("nmsituac")).equalsIgnoreCase("0"))
					{
						cdpersonTitular=(String)aseguradoIterado.get("cdperson");
					}
				}
				////// 2. obtener cdperson titular //////
				
				////// 3. obtener el domicilio del titular //////
				Map<String,String> mapaObtenerDomicilio=new HashMap<String,String>(0);
				mapaObtenerDomicilio.put("pv_cdunieco_i" , cdunieco);
				mapaObtenerDomicilio.put("pv_cdramo_i"   , cdramo);
				mapaObtenerDomicilio.put("pv_estado_i"   , estado);
				mapaObtenerDomicilio.put("pv_nmpoliza_i" , nmpoliza);
				mapaObtenerDomicilio.put("pv_nmsituac_i" , nmsituac);
				mapaObtenerDomicilio.put("pv_nmsuplem_i" , nmsuplem);
				mapaObtenerDomicilio.put("pv_cdperson_i" , cdpersonTitular);
				mapaObtenerDomicilio.put("pv_cdtipsit_i" , cdtipsit);
				Map<String,String>domicilioTitular=kernelManager.obtenerDomicilio(mapaObtenerDomicilio);
				////// 3. obtener el domicilio del titular //////
				
				////// 4. mdomicil //////
				Map<String,String>mapaDomicilio=new HashMap<String,String>(0);
				mapaDomicilio.put("pv_cdperson_i" , cdperson);
				mapaDomicilio.put("pv_nmorddom_i" , domicilioTitular.get("NMORDDOM"));
				mapaDomicilio.put("pv_msdomici_i" , domicilioTitular.get("DSDOMICI"));
				mapaDomicilio.put("pv_nmtelefo_i" , domicilioTitular.get("NMTELEFO"));
				mapaDomicilio.put("pv_cdpostal_i" , domicilioTitular.get("CODPOSTAL"));
				mapaDomicilio.put("pv_cdedo_i"    , domicilioTitular.get("CDEDO"));
				mapaDomicilio.put("pv_cdmunici_i" , domicilioTitular.get("CDMUNICI"));
				mapaDomicilio.put("pv_cdcoloni_i" , domicilioTitular.get("CDCOLONI"));
				mapaDomicilio.put("pv_nmnumero_i" , domicilioTitular.get("NMNUMERO"));
				mapaDomicilio.put("pv_nmnumint_i" , domicilioTitular.get("NMNUMINT"));
				mapaDomicilio.put("pv_accion_i"   , "I");
				kernelManager.pMovMdomicil(mapaDomicilio);
				////// 4. mdomicil //////
				
				////// domicilio //////
				///////////////////////
				
                /////////////////////////////////
                ////// valores por defecto //////
                Map<String,String> mapaCoberturas=new HashMap<String,String>(0);
                mapaCoberturas.put("pv_cdunieco_i",   cdunieco);//se agrega desde el formulario
                mapaCoberturas.put("pv_cdramo_i",     cdramo);//se agrega desde el formulario
                mapaCoberturas.put("pv_estado_i",     estado);
                mapaCoberturas.put("pv_nmpoliza_i",   nmpoliza);
                mapaCoberturas.put("pv_nmsituac_i",   nmsituac);
                mapaCoberturas.put("pv_nmsuplem_i",   nmsuplem);
                mapaCoberturas.put("pv_cdgarant_i",   "TODO");
				mapaCoberturas.put("pv_cdtipsup_i", TipoEndoso.ALTA_ASEGURADOS.getCdTipSup().toString());
                kernelManager.coberturas(mapaCoberturas);
                ////// valores por defecto //////
                /////////////////////////////////
                
                //////////////////////////////
                ////// inserta tworksup //////
                Map<String,String>mapaTworksupEnd=new LinkedHashMap<String,String>(0);
                mapaTworksupEnd.put("pv_cdunieco_i" , cdunieco);
                mapaTworksupEnd.put("pv_cdramo_i"   , cdramo);
                mapaTworksupEnd.put("pv_estado_i"   , estado);
                mapaTworksupEnd.put("pv_nmpoliza_i" , nmpoliza);
                mapaTworksupEnd.put("pv_cdtipsup_i", TipoEndoso.ALTA_ASEGURADOS.getCdTipSup().toString());
                mapaTworksupEnd.put("pv_nmsuplem_i" , nmsuplem);
                mapaTworksupEnd.put("pv_nmsituac_i" , nmsituac);
                endososManager.insertarTworksupEnd(mapaTworksupEnd);
                ////// inserta tworksup //////
                //////////////////////////////
                
                //////////////////////////
                ////// tarificacion //////
                Map<String,String>mapaSigsvalipolEnd=new LinkedHashMap<String,String>(0);
    			mapaSigsvalipolEnd.put("pv_cdusuari_i" , cdusuari);
    			mapaSigsvalipolEnd.put("pv_cdelemen_i" , cdelemen);
    			mapaSigsvalipolEnd.put("pv_cdunieco_i" , cdunieco);
    			mapaSigsvalipolEnd.put("pv_cdramo_i"   , cdramo);
    			mapaSigsvalipolEnd.put("pv_estado_i"   , estado);
    			mapaSigsvalipolEnd.put("pv_nmpoliza_i" , nmpoliza);
    			mapaSigsvalipolEnd.put("pv_nmsituac_i" , nmsituac);
    			mapaSigsvalipolEnd.put("pv_nmsuplem_i" , nmsuplem);
    			mapaSigsvalipolEnd.put("pv_cdtipsit_i" , cdtipsit);
    			mapaSigsvalipolEnd.put("pv_cdtipsup_i" , TipoEndoso.ALTA_ASEGURADOS.getCdTipSup().toString());
    			endososManager.sigsvalipolEnd(mapaSigsvalipolEnd);
                ////// tarificacion //////
    			//////////////////////////
    			
    			//////////////////////////
    			////// valor endoso //////
    			Map<String,Object>mapaValorEndoso=new LinkedHashMap<String,Object>(0);
				mapaValorEndoso.put("pv_cdunieco_i" , cdunieco);
				mapaValorEndoso.put("pv_cdramo_i"   , cdramo);
				mapaValorEndoso.put("pv_estado_i"   , estado);
				mapaValorEndoso.put("pv_nmpoliza_i" , nmpoliza);
				mapaValorEndoso.put("pv_nmsituac_i" , nmsituac);
				mapaValorEndoso.put("pv_nmsuplem_i" , nmsuplem);
				mapaValorEndoso.put("pv_feinival_i" , fechaEndosoD);
				mapaValorEndoso.put("pv_cdtipsup_i" , TipoEndoso.ALTA_ASEGURADOS.getCdTipSup().toString());
				endososManager.calcularValorEndoso(mapaValorEndoso);
    			////// valor endoso //////
    			//////////////////////////
			}
			else
			{
				String cdperson = smap1.get("cdperson");
				
				////////////////////////////
				////// polisit muerto //////
				Map<String,Object>mapaPolisit=new HashMap<String,Object>(0);
                mapaPolisit.put("pv_cdunieco_i",    cdunieco);
                mapaPolisit.put("pv_cdramo_i",      cdramo);
                mapaPolisit.put("pv_estado_i",      estado);
                mapaPolisit.put("pv_nmpoliza_i",    nmpoliza);
                mapaPolisit.put("pv_nmsituac_i",    nmsituac);
                mapaPolisit.put("pv_nmsuplem_i",    nmsuplem);
                mapaPolisit.put("pv_status_i",      "V");
                mapaPolisit.put("pv_cdtipsit_i",    cdtipsit);
                mapaPolisit.put("pv_swreduci_i",    null);
                mapaPolisit.put("pv_cdagrupa_i",    "1");
                mapaPolisit.put("pv_cdestado_i",    "0");
                mapaPolisit.put("pv_fefecsit_i",    fechaHoy);
                mapaPolisit.put("pv_fecharef_i",    fechaHoy);
                mapaPolisit.put("pv_cdgrupo_i",     null);
                mapaPolisit.put("pv_nmsituaext_i",  null);
                mapaPolisit.put("pv_nmsitaux_i",    null);
                mapaPolisit.put("pv_nmsbsitext_i",  null);
                mapaPolisit.put("pv_cdplan_i",      cdplan);
                mapaPolisit.put("pv_cdasegur_i",    "30");
                mapaPolisit.put("pv_accion_i",      "D");
                kernelManager.insertaPolisit(mapaPolisit);
				////// polisit muerto //////
                ////////////////////////////
                
                ////////////////////////////
                ////// valosit muerto //////                
                
                ////// 1. mapa valosit base //////
                Map<String,String>mapaValosit=new HashMap<String,String>(0);
                mapaValosit.put("pv_cdunieco",    cdunieco);
                mapaValosit.put("pv_cdramo",      cdramo);
                mapaValosit.put("pv_estado",      estado);
                mapaValosit.put("pv_nmpoliza",    nmpoliza);
                mapaValosit.put("pv_nmsituac",    nmsituac);
                mapaValosit.put("pv_nmsuplem",    nmsuplem);
                mapaValosit.put("pv_status",      "V");
                mapaValosit.put("pv_cdtipsit",    cdtipsit);
                mapaValosit.put("pv_accion_i",   "D");
                ////// 1. mapa valosit base //////
                
                ////// 2. obtener el original //////
                Map<String,String>mapaObtenerValosit=new HashMap<String,String>(0);
                mapaObtenerValosit.put("pv_cdunieco_i" , cdunieco);
                mapaObtenerValosit.put("pv_nmpoliza_i" , nmpoliza);
                mapaObtenerValosit.put("pv_cdramo_i"   , cdramo);
                mapaObtenerValosit.put("pv_estado_i"   , estado);
                mapaObtenerValosit.put("pv_nmsituac_i" , nmsituac);
                Map<String,Object>valositOriginal=kernelManager.obtieneValositSituac(mapaObtenerValosit);
                ////// 2. obtener el original //////
                
                ////// 3. copiar los otvalor del original a la base //////
                for(Entry<String,Object> en:valositOriginal.entrySet())
                {
                	String key=en.getKey();
                	if(key.substring(0,3).equalsIgnoreCase("otv"))
                	{
                		mapaValosit.put("pv_"+key,(String)en.getValue());
                	}
                }
                ////// 3. copiar los otvalor del original a la base //////
                
                kernelManager.insertaValoresSituaciones(mapaValosit);
                
                ////// valosit muerto//////
                ///////////////////////////
                
                /////////////////////////////
				////// mpoliper muerto //////
				Map<String,Object>mapaMpoliper=new LinkedHashMap<String,Object>(0);
				mapaMpoliper.put("pv_cdunieco_i" , cdunieco);
				mapaMpoliper.put("pv_cdramo_i"   , cdramo);
				mapaMpoliper.put("pv_estado_i"   , estado);
				mapaMpoliper.put("pv_nmpoliza_i" , nmpoliza);
				mapaMpoliper.put("pv_nmsituac_i" , nmsituac);
				mapaMpoliper.put("pv_cdrol_i"    , "2");
				mapaMpoliper.put("pv_cdperson_i" , cdperson);
				mapaMpoliper.put("pv_nmsuplem_i" , nmsuplem);
				mapaMpoliper.put("pv_status_i"   , "V");
				mapaMpoliper.put("pv_nmorddom_i" , "1");
				mapaMpoliper.put("pv_swreclam_i" , null);
				mapaMpoliper.put("pv_accion_i"   , "D");
				mapaMpoliper.put("pv_swexiper_i" , Constantes.SI);
				kernelManager.movMpoliper(mapaMpoliper);
				////// mpoliper muerto //////
				/////////////////////////////
                
                //////////////////////////////
                ////// inserta tworksup //////
                Map<String,String>mapaTworksupEnd=new LinkedHashMap<String,String>(0);
                mapaTworksupEnd.put("pv_cdunieco_i" , cdunieco);
                mapaTworksupEnd.put("pv_cdramo_i"   , cdramo);
                mapaTworksupEnd.put("pv_estado_i"   , estado);
                mapaTworksupEnd.put("pv_nmpoliza_i" , nmpoliza);
                mapaTworksupEnd.put("pv_cdtipsup_i" , TipoEndoso.BAJA_ASEGURADOS.getCdTipSup().toString());
                mapaTworksupEnd.put("pv_nmsuplem_i" , nmsuplem);
                mapaTworksupEnd.put("pv_nmsituac_i" , nmsituac);
                endososManager.insertarTworksupEnd(mapaTworksupEnd);
                ////// inserta tworksup //////
                //////////////////////////////
                
                //////////////////////////
		        ////// tarificacion //////
		        Map<String,String>mapaSigsvalipolEnd=new LinkedHashMap<String,String>(0);
				mapaSigsvalipolEnd.put("pv_cdusuari_i" , cdusuari);
				mapaSigsvalipolEnd.put("pv_cdelemen_i" , cdelemen);
				mapaSigsvalipolEnd.put("pv_cdunieco_i" , cdunieco);
				mapaSigsvalipolEnd.put("pv_cdramo_i"   , cdramo);
				mapaSigsvalipolEnd.put("pv_estado_i"   , estado);
				mapaSigsvalipolEnd.put("pv_nmpoliza_i" , nmpoliza);
				mapaSigsvalipolEnd.put("pv_nmsituac_i" , nmsituac);
				mapaSigsvalipolEnd.put("pv_nmsuplem_i" , nmsuplem);
				mapaSigsvalipolEnd.put("pv_cdtipsit_i" , cdtipsit);
				mapaSigsvalipolEnd.put("pv_cdtipsup_i" , TipoEndoso.BAJA_ASEGURADOS.getCdTipSup().toString());
				endososManager.sigsvalipolEnd(mapaSigsvalipolEnd);
		        ////// tarificacion //////
				//////////////////////////
				
				//////////////////////////
				////// valor endoso //////
				Map<String,Object>mapaValorEndoso=new LinkedHashMap<String,Object>(0);
				mapaValorEndoso.put("pv_cdunieco_i" , cdunieco);
				mapaValorEndoso.put("pv_cdramo_i"   , cdramo);
				mapaValorEndoso.put("pv_estado_i"   , estado);
				mapaValorEndoso.put("pv_nmpoliza_i" , nmpoliza);
				mapaValorEndoso.put("pv_nmsituac_i" , nmsituac);
				mapaValorEndoso.put("pv_nmsuplem_i" , nmsuplem);
				mapaValorEndoso.put("pv_feinival_i" , fechaEndosoD);
				mapaValorEndoso.put("pv_cdtipsup_i" , TipoEndoso.BAJA_ASEGURADOS.getCdTipSup().toString());
				endososManager.calcularValorEndoso(mapaValorEndoso);
				////// valor endoso //////
				//////////////////////////
			}
			
			String tramiteGenerado=this.confirmarEndoso(
					cdunieco, 
					cdramo, 
					estado,
					nmpoliza,
					nmsuplem,
					nsuplogi, 
					//alta?"9":"10",
					alta ? TipoEndoso.ALTA_ASEGURADOS.getCdTipSup().toString()
					     : TipoEndoso.BAJA_ASEGURADOS.getCdTipSup().toString(),
					"",
					fechaEndosoD,
					cdtipsit);
		    
			if(tramiteGenerado==null||tramiteGenerado.length()==0)
			{
			
			    ///////////////////////////////////////
			    ////// re generar los documentos //////
			    /*///////////////////////////////////*/
			    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
				paramsGetDoc.put("pv_cdunieco_i" , cdunieco);
				paramsGetDoc.put("pv_cdramo_i"   , cdramo);
				paramsGetDoc.put("pv_estado_i"   , estado);
				paramsGetDoc.put("pv_nmpoliza_i" , nmpoliza);
				paramsGetDoc.put("pv_nmsuplem_i" , nmsuplem);
				paramsGetDoc.put("pv_tipmov_i"   , alta 
						? TipoEndoso.ALTA_ASEGURADOS.getCdTipSup().toString()
						: TipoEndoso.BAJA_ASEGURADOS.getCdTipSup().toString());
			    List<Map<String,String>>listaDocu=endososManager.reimprimeDocumentos(paramsGetDoc);
			    log.debug("documentos que se regeneran: "+listaDocu);
			    
			    String rutaCarpeta=this.getText("ruta.documentos.poliza")+"/"+ntramite;
			    
				//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
				for(Map<String,String> docu:listaDocu)
				{
					log.debug("docu iterado: "+docu);
					String descripc=docu.get("descripc");
					String descripl=docu.get("descripl");
					String url=this.getText("ruta.servidor.reports")
							+ "?destype=cache"
							+ "&desformat=PDF"
							+ "&userid="+this.getText("pass.servidor.reports")
							+ "&report="+descripl
							+ "&paramform=no"
							+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
							+ "&p_unieco="+cdunieco
							+ "&p_ramo="+cdramo
							+ "&p_estado="+estado
							+ "&p_poliza="+nmpoliza
							+ "&p_suplem="+nmsuplem
							+ "&desname="+rutaCarpeta+"/"+descripc;
					if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
					{
						// C R E D E N C I A L _ X X X X X X . P D F
						//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
						url+="&p_cdperson="+descripc.substring(11, descripc.lastIndexOf("_"));
					}
					log.debug(""
							+ "\n#################################"
							+ "\n###### Se solicita reporte ######"
							+ "\na "+url+""
							+ "\n#################################");
					HttpUtil.generaArchivo(url,rutaCarpeta+"/"+descripc);
					log.debug(""
							+ "\n######                    ######"
							+ "\n###### reporte solicitado ######"
							+ "\na "+url+""
							+ "\n################################"
							+ "\n################################"
							+ "");
				}
			    /*///////////////////////////////////*/
				////// re generar los documentos //////
			    ///////////////////////////////////////
				
				/**
				 * TODO: Poner variable el cdTipSitGS de la poliza y la sucursal
				 */
				String cdtipsitGS = "213";
				String sucursal = cdunieco;
				if(StringUtils.isNotBlank(sucursal) && "1".equals(sucursal)) sucursal = "1000";
				
				String nmsolici = listaDocu.size()>0?listaDocu.get(0).get("nmsolici"):nmpoliza;
				//String nmtramite = listaDocu.get(0).get("ntramite");
				
				String tipomov = alta?"9":"10";
				
				ejecutaWSrecibosEndoso(cdunieco, cdramo,
						estado, nmpoliza,
						nmsuplem, nsuplogi, rutaCarpeta,
						cdtipsitGS, sucursal, nmsolici, ntramite,
						true, "INSERTA", tipomov );
				
				mensaje="Se ha guardado el endoso "+nsuplogi;
				
			}
			else
			{
				mensaje="El endoso "+nsuplogi
						+" se guard&oacute; en mesa de control para autorizaci&oacute;n "
						+ "con n&uacute;mero de tr&aacute;mite "+tramiteGenerado;
			}
			
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar endoso de alta o baja de asegurado", ex);
			success=false;
			error=ex.getMessage();
		}
		log.debug("\n"
				+ "\n######                                ######"
				+ "\n###### guardarEndosoAltaBajaAsegurado ######"
				+ "\n############################################"
				+ "\n############################################"				
				);
		return SUCCESS;
	}
	/*/////////////////////////////////////////////////////*/
	////// guardar endoso de alta o baja de asegurados //////
	/////////////////////////////////////////////////////////
	
	/////////////////////////////////////////
	////// prueba de pantalla dinamica //////
	/*/////////////////////////////////////*/
	public String testpantalla()
	{
		try
		{
			UserVO usuario=(UserVO)session.get("USUARIO");
			
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			gc.generaParcial(pantallasManager.obtenerCamposPantalla(
					 null,null,null
					,null,null,null
					,"TESTPANTALLA",usuario.getRolActivo().getObjeto().getValue(),null
					,"TEST"));
			
			item1=gc.getFields();
			item2=gc.getColumns();
			item3=gc.getItems();
		}
		catch(Exception ex)
		{
			log.error("error en la prueba de pantalla dinamica",ex);
		}
		return SUCCESS;
	}
	/*/////////////////////////////////////*/
	////// prueba de pantalla dinamica //////
	/////////////////////////////////////////
	
	private boolean ejecutaWSrecibosEndoso(String cdunieco, String cdramo, String estado, String nmpoliza,
			String nmsuplem, String numendoso, String rutaPoliza, String cdtipsitGS, String sucursal, String nmsolici,String ntramite, boolean async, String Op, String tipoMov){
		boolean allInserted = true;
		
		logger.debug("*** Entrando a metodo Actualiza Recibos WS ice2sigs ENDOSO, para la poliza: " + nmpoliza + " sucursal: " + sucursal + "***");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		
		if(StringUtils.isBlank(Op)) Op = "INSERTA";
		Operacion Operation = Operacion.valueOf(Op);
		
		WrapperResultados result = null;
		ArrayList<Recibo> recibos =  null;
		try {
			result = kernelManager.obtenDatosRecibos(params);
			recibos = (ArrayList<Recibo>) result.getItemList();
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de RECIBOS",e1);
			return false;
		}

		String usuario = "SIN USUARIO";
		if(session.containsKey("USUARIO") && session.get("USUARIO") != null){
			UserVO usuarioSesion=(UserVO) session.get("USUARIO");
			usuario = usuarioSesion.getUser();
		}
		
		if(async){
			params.put("MANAGER", kernelManager);
			params.put("USUARIO", usuario);
		}
		
		for(Recibo recibo: recibos){
			try{
				if(async){
					// Se crea un HashMap por cada invocacion asincrona del WS, para evitar issue (sobreescritura de valores):
					HashMap<String, Object> paramsBitacora = new HashMap<String, Object>();
					paramsBitacora.putAll(params);
					paramsBitacora.put("NumRec", recibo.getNumRec());
					
					ice2sigsWebServices.ejecutaReciboGS(Operation, recibo, this.getText("url.ws.ice2sigs"), paramsBitacora, async);
				}else{
					ReciboRespuesta respuesta = ice2sigsWebServices.ejecutaReciboGS(Operation, recibo, this.getText("url.ws.ice2sigs"), null, async);
					logger.debug("Resultado al ejecutar el WS Recibo: " + recibo.getNumRec() + " >>>"
							+ respuesta.getCodigo() + " - " + respuesta.getMensaje());

					if (Estatus.EXITO.getCodigo() != respuesta.getCodigo()) {
						logger.error("Guardando en bitacora el estatus");

						try {
							kernelManager.movBitacobro((String) params.get("pv_cdunieco_i"),
									(String) params.get("pv_cdramo_i"),
									(String) params.get("pv_estado_i"),
									(String) params.get("pv_nmpoliza_i"), "ErrWSrec",
									"Error en Recibo " + params.get("NumRec")
											+ " >>> " + respuesta.getCodigo() + " - "
											+ respuesta.getMensaje(),
									 usuario);
						} catch (ApplicationException e1) {
							logger.error("Error en llamado a PL", e1);
						}
					}
				}
			}catch(Exception e){
				logger.error("Error al insertar endoso recibo: "+recibo.getNumRec()+" tramite: "+ntramite);
				try {
					kernelManager.movBitacobro(
							(String) params.get("pv_cdunieco_i"),
							(String) params.get("pv_cdramo_i"),
							(String) params.get("pv_estado_i"),
							(String) params.get("pv_nmpoliza_i"),
							"ErrWSrecCx",
							"Error en Recibo " + recibo.getNumRec()
									+ " Msg: " + e.getMessage() + " ***Cause: "
									+ e.getCause(),
							 usuario);
				} catch (Exception e1) {
					logger.error("Error en llamado a PL", e1);
				}
			}
		}
		
		/**
		 * PARA EL GUARDADO CADA PDF DE RECIBO
		 */
		logger.debug("*** Empieza generacion de URLs para Recibos ***");
		
		String visible = null;
		for(Recibo recibo: recibos){
			
			visible = (1 == recibo.getNumRec()) ? Constantes.SI : Constantes.NO;
			
			try{
//				Parametro1:  9999: Recibo
//				Parametro2:  Siempre va en 0
//				Parametro3:  Sucursal
//				Parametro4:  Ramo (213 o 214)
//				Parametro5:  Poliza
//				Parametro6:  Tramite(poner 0)
//				Parametro7:  Numero de endoso (Cuando es poliza nueva poner 0)
//				Parametro8:  Tipo de endoso (Si es vacio no enviar nada en otro caso poner A o D segun sea el caso)
//				Parametro9:  Numero de recibo (1,2,3..segun la forma de pago) Para nuestro caso es siempre el 1
				//if( 1 == recibo.getNumRec()){
					String parametros = "?9999,0,"+sucursal+","+cdtipsitGS+","+nmpoliza+",0,"+recibo.getNumEnd()+","+recibo.getTipEnd()+","+recibo.getNumRec();
					logger.debug("URL Generada para Recibo: "+ this.getText("url.imp.recibos")+parametros);
					//HttpRequestUtil.generaReporte(this.getText("url.imp.recibos")+parametros, rutaPoliza+"/Recibo_"+recibo.getRmdbRn()+"_"+recibo.getNumRec()+".pdf");
					
					HashMap<String, Object> paramsR =  new HashMap<String, Object>();
					paramsR.put("pv_cdunieco_i", cdunieco);
					paramsR.put("pv_cdramo_i", cdramo);
					paramsR.put("pv_estado_i", estado);
					paramsR.put("pv_nmpoliza_i", nmpoliza);
					paramsR.put("pv_nmsuplem_i", nmsuplem);
					paramsR.put("pv_feinici_i", new Date());
					paramsR.put("pv_cddocume_i", this.getText("url.imp.recibos")+parametros);
					paramsR.put("pv_dsdocume_i", "Recibo "+recibo.getNumRec());
					paramsR.put("pv_nmsolici_i", nmsolici);
					paramsR.put("pv_ntramite_i", ntramite);
					paramsR.put("pv_tipmov_i", tipoMov);
					paramsR.put("pv_swvisible_i", visible);
					
					kernelManager.guardarArchivo(paramsR);
				//}
			}catch(Exception e){
				logger.error("Error al guardar indexaxion de recibo: " + recibo.getRmdbRn(), e);
			}
		}

		return allInserted;
	}
	
	@Deprecated
	private boolean insertaURLrecibosEndoso(String cdunieco, String cdramo, String estado, String nmpoliza,
			String nmsuplem, String cdtipsitGS, String sucursal, String nmsolici,String ntramite, String tipoMov){
		boolean allInserted = true;
		
		logger.debug("*** Entrando a metodo Insertar TDOCUPOL para Recibos ENDOSO, para la poliza: " + nmpoliza + " sucursal: " + sucursal + "***");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		
		WrapperResultados result = null;
		ArrayList<Recibo> recibos =  null;
		try {
			result = kernelManager.obtenDatosRecibos(params);
			recibos = (ArrayList<Recibo>) result.getItemList();
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de RECIBOS",e1);
			return false;
		}


		/**
		 * PARA EL GUARDADO CADA PDF DE RECIBO
		 */
		logger.debug("*** Empieza generacion de URLs para Recibos ***");
		
		String visible = null;
		for(Recibo recibo: recibos){
			
			visible = (1 == recibo.getNumRec()) ? Constantes.SI : Constantes.NO;
			
			try{
//				Parametro1:  9999: Recibo
//				Parametro2:  Siempre va en 0
//				Parametro3:  Sucursal
//				Parametro4:  Ramo (213 o 214)
//				Parametro5:  Poliza
//				Parametro6:  Tramite(poner 0)
//				Parametro7:  Numero de endoso (Cuando es poliza nueva poner 0)
//				Parametro8:  Tipo de endoso (Si es vacio no enviar nada en otro caso poner A o D segun sea el caso)
//				Parametro9:  Numero de recibo (1,2,3..segun la forma de pago) Para nuestro caso es siempre el 1
				//if( 1 == recibo.getNumRec()){
					String parametros = "?9999,0,"+sucursal+","+cdtipsitGS+","+nmpoliza+",0,"+recibo.getNumEnd()+","+recibo.getTipEnd()+","+recibo.getNumRec();
					logger.debug("URL Generada para Recibo: "+ this.getText("url.imp.recibos")+parametros);
					
					HashMap<String, Object> paramsR =  new HashMap<String, Object>();
					paramsR.put("pv_cdunieco_i", cdunieco);
					paramsR.put("pv_cdramo_i", cdramo);
					paramsR.put("pv_estado_i", estado);
					paramsR.put("pv_nmpoliza_i", nmpoliza);
					paramsR.put("pv_nmsuplem_i", nmsuplem);
					paramsR.put("pv_feinici_i", new Date());
					paramsR.put("pv_cddocume_i", this.getText("url.imp.recibos")+parametros);
					paramsR.put("pv_dsdocume_i", "Recibo "+recibo.getNumRec());
					paramsR.put("pv_nmsolici_i", nmsolici);
					paramsR.put("pv_ntramite_i", ntramite);
					paramsR.put("pv_tipmov_i", tipoMov);
					paramsR.put("pv_swvisible_i", visible);
					
					kernelManager.guardarArchivo(paramsR);
				//}
			}catch(Exception e){
				logger.error("Error al guardar indexaxion de recibo: " + recibo.getRmdbRn(), e);
			}
		}

		return allInserted;
	}
	
	private boolean ejecutaWSclienteSaludEndoso(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem, String Op){
		boolean exito = true;
		
		logger.debug("********************* Entrando a Ejecuta WSclienteSalud ENDOSO ******************************");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		
		if(StringUtils.isBlank(Op)) Op = "ACTUALIZA";
		Operacion Operation = Operacion.valueOf(Op);
		
		WrapperResultados result = null;
		ClienteSalud cliente =  null;
		try {
			result = kernelManager.obtenDatosClienteWS(params);
			if(result.getItemList() != null && result.getItemList().size() > 0){
				cliente = ((ArrayList<ClienteSalud>) result.getItemList()).get(0);
			}
			
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de ejecutaWSclienteSalud",e1);
			return false;
		}
		
		
		if(cliente != null){
			
			String usuario = "SIN USUARIO";
			if(session.containsKey("USUARIO") && session.get("USUARIO") != null){
				UserVO usuarioSesion=(UserVO) session.get("USUARIO");
				usuario = usuarioSesion.getUser();
			}
			
			params.put("USUARIO", usuario);
			
			try{
				logger.debug("Ejecutando WS TEST para WS Cliente");
				ice2sigsWebServices.ejecutaClienteSaludGS(Operacion.INSERTA, null, this.getText("url.ws.ice2sigs"), params, false);
			}catch(Exception e){
				logger.error("Error al ejecutar WS TEST para cliente: " + cliente.getClaveCli(), e);
			}
			try{
				logger.debug(">>>>>>> Enviando el Cliente: " + cliente.getClaveCli());
				params.put("MANAGER", kernelManager);
				ice2sigsWebServices.ejecutaClienteSaludGS(Operation, cliente, this.getText("url.ws.ice2sigs"), params, true);
			}catch(Exception e){
				logger.error("Error al insertar endoso del cliente: " + cliente.getClaveCli(), e);
				exito = false;
			}
		}

		return exito;
	} 
	
	////////////////////////////
	////// endoso de edad //////
	/*
	smap1:
	    'cdunieco' , 'cdramo'   , 'cdtipsit'
	    ,'estado'  , 'nmpoliza' , 'ntramite'
	    ,'masedad(si,no)
	*/
	/*////////////////////////*/
	public String endosoEdad()
	{
		this.session=ActionContext.getContext().getSession();
		log.debug("\n"
				+ "\n############################"
				+ "\n############################"
				+ "\n###### endoso de edad ######"
				+ "\n######                ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("slist1: "+slist1);
		String respuesta=this.validaEndosoAnterior(
				smap1.get("cdunieco")
				,smap1.get("cdramo")
				,smap1.get("estado")
				,smap1.get("nmpoliza")
				,smap1.get("masedad").equalsIgnoreCase("si")?
						TipoEndoso.INCREMENTO_EDAD_ASEGURADO.getCdTipSup().toString():
						TipoEndoso.DECREMENTO_EDAD_ASEGURADO.getCdTipSup().toString());
		if(respuesta.equals(SUCCESS))
		{
			try
			{
				imap1=new HashMap<String,Item>();
				GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gc.generaParcial(pantallasManager.obtenerCamposPantalla(
						null,null,null
						,null,null,null
						,"ENDOSO_EDAD",null,null
						,"MODELO"
						));
				imap1.put("modelo"   , gc.getFields());
				imap1.put("columnas" , gc.getColumns());
			}
			catch(Exception ex)
			{
				log.error("error al cargar la pantalla de endoso de edad",ex);
			}
		}
		log.debug("\n"
				+ "\n######                ######"
				+ "\n###### endoso de edad ######"
				+ "\n############################"
				+ "\n############################"
				);
		return respuesta;
	}
	/*////////////////////////*/
	////// endoso de edad //////
	////////////////////////////
	
	////////////////////////////////////
	////// guardar endoso de edad //////
	/*
	smap1: {cdramo=2, nmpoliza=23, estado=M, masedad=si, cdtipsit=SL, ntramite=480, cdunieco=1205}
	smap2: {fecha_endoso=15/01/2014}
	slist1: [{cdperson=511783, nmsituac=1, fenacimi=01/01/1969}
	        ,{cdperson=511782, nmsituac=2, fenacimi=01/01/1974}
	        ,{cdperson=511785, nmsituac=3, fenacimi=01/01/1996}
	        ,{cdperson=511786, nmsituac=4, fenacimi=01/01/1950}]
	*/
	/*////////////////////////////////*/
	public String guardarEndosoEdad()
	{
		this.session=ActionContext.getContext().getSession();
		log.debug("\n"
				+ "\n####################################"
				+ "\n####################################"
				+ "\n###### guardar endoso de edad ######"
				+ "\n######                        ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("smap2: "+smap2);
		log.debug("slist1: "+slist1);
		try
		{
			String  cdunieco    = smap1.get("cdunieco");
			String  cdramo      = smap1.get("cdramo");
			String  cdtipsit    = smap1.get("cdtipsit");
			String  estado      = smap1.get("estado");
			String  nmpoliza    = smap1.get("nmpoliza");
			String  ntramite    = smap1.get("ntramite");
			String  cdelemen    = ((UserVO)session.get("USUARIO")).getEmpresa().getElementoId();
			String  cdusuari    = ((UserVO)session.get("USUARIO")).getUser();
			Date    fechaHoy    = new Date();
			String  fechaEndoso = smap2.get("fecha_endoso");
			boolean incremento  = smap1.get("masedad").equalsIgnoreCase("si");
			String cdtipsup = incremento 
					? TipoEndoso.INCREMENTO_EDAD_ASEGURADO.getCdTipSup().toString()
					: TipoEndoso.DECREMENTO_EDAD_ASEGURADO.getCdTipSup().toString();
			
			Map<String,String>paramsIniciarEndoso=new HashMap<String,String>(0);
			paramsIniciarEndoso.put("pv_cdunieco_i" , cdunieco);
			paramsIniciarEndoso.put("pv_cdramo_i"   , cdramo);
			paramsIniciarEndoso.put("pv_estado_i"   , estado);
			paramsIniciarEndoso.put("pv_nmpoliza_i" , nmpoliza);
			paramsIniciarEndoso.put("pv_fecha_i"    , fechaEndoso);
			paramsIniciarEndoso.put("pv_cdelemen_i" , cdelemen);
			paramsIniciarEndoso.put("pv_cdusuari_i" , cdusuari);
			paramsIniciarEndoso.put("pv_proceso_i"  , "END");
			paramsIniciarEndoso.put("pv_cdtipsup_i" , cdtipsup);
			Map<String,String>respuestaIniciarEndoso=endososManager.iniciarEndoso(paramsIniciarEndoso);
			
			String nmsuplem=respuestaIniciarEndoso.get("pv_nmsuplem_o");
			String nsuplogi=respuestaIniciarEndoso.get("pv_nsuplogi_o");

			/*
			Map<String,String>paramsObtenerDatosMpolisit=new HashMap<String,String>();
			paramsObtenerDatosMpolisit.put("pv_cdunieco_i" , cdunieco);
			paramsObtenerDatosMpolisit.put("pv_cdramo_i"   , cdramo);
			paramsObtenerDatosMpolisit.put("pv_estado_i"   , estado);
			paramsObtenerDatosMpolisit.put("pv_nmpoliza_i" , nmpoliza);
			Map<String,String>respuestaObtenerDatosMpolisit=endososManager.obtieneDatosMpolisit(paramsObtenerDatosMpolisit);
			String cdplan=respuestaObtenerDatosMpolisit.get("pv_cdplan_o");
			*/
			
			for(Map<String,String>inciso:slist1)
			{
				String nmsituacIte=inciso.get("nmsituac");
				String cdpersonIte=inciso.get("cdperson");
				String fenacimiIte=inciso.get("fenacimi");
				
				/*
				Map<String,Object>mapaPolisit=new HashMap<String,Object>(0);
                mapaPolisit.put("pv_cdunieco_i",    cdunieco);
                mapaPolisit.put("pv_cdramo_i",      cdramo);
                mapaPolisit.put("pv_estado_i",      estado);
                mapaPolisit.put("pv_nmpoliza_i",    nmpoliza);
                mapaPolisit.put("pv_nmsituac_i",    nmsituacIte);
                mapaPolisit.put("pv_nmsuplem_i",    nmsuplem);
                mapaPolisit.put("pv_status_i",      "V");
                mapaPolisit.put("pv_cdtipsit_i",    cdtipsit);
                mapaPolisit.put("pv_swreduci_i",    null);
                mapaPolisit.put("pv_cdagrupa_i",    "1");
                mapaPolisit.put("pv_cdestado_i",    "0");
                mapaPolisit.put("pv_fefecsit_i",    fechaHoy);
                mapaPolisit.put("pv_fecharef_i",    fechaHoy);
                mapaPolisit.put("pv_cdgrupo_i",     null);
                mapaPolisit.put("pv_nmsituaext_i",  null);
                mapaPolisit.put("pv_nmsitaux_i",    null);
                mapaPolisit.put("pv_nmsbsitext_i",  null);
                mapaPolisit.put("pv_cdplan_i",      cdplan);
                mapaPolisit.put("pv_cdasegur_i",    "30");
                mapaPolisit.put("pv_accion_i",      "I");
                kernelManager.insertaPolisit(mapaPolisit);
                */
                
                /////////////////////
                ////// valosit //////
                
                ////// 1. mapa valosit base //////
                Map<String,String>mapaValosit=new HashMap<String,String>(0);
                mapaValosit.put("pv_cdunieco",    cdunieco);
                mapaValosit.put("pv_cdramo",      cdramo);
                mapaValosit.put("pv_estado",      estado);
                mapaValosit.put("pv_nmpoliza",    nmpoliza);
                mapaValosit.put("pv_nmsituac",    nmsituacIte);
                mapaValosit.put("pv_nmsuplem",    nmsuplem);
                mapaValosit.put("pv_status",      "V");
                mapaValosit.put("pv_cdtipsit",    cdtipsit);
                mapaValosit.put("pv_accion_i",   "I");
                ////// 1. mapa valosit base //////
                
                ////// 2. obtener el original //////
                Map<String,String>mapaObtenerValosit=new HashMap<String,String>(0);
                mapaObtenerValosit.put("pv_cdunieco_i" , cdunieco);
                mapaObtenerValosit.put("pv_nmpoliza_i" , nmpoliza);
                mapaObtenerValosit.put("pv_cdramo_i"   , cdramo);
                mapaObtenerValosit.put("pv_estado_i"   , estado);
                mapaObtenerValosit.put("pv_nmsituac_i" , nmsituacIte);
                Map<String,Object>valositOriginal=kernelManager.obtieneValositSituac(mapaObtenerValosit);
                ////// 2. obtener el original //////
                
                ////// 3. copiar los otvalor del original a la base //////
                for(Entry<String,Object> en:valositOriginal.entrySet())
                {
                	String key=en.getKey();
                	if(key.substring(0,3).equalsIgnoreCase("otv"))
                	{
                		mapaValosit.put("pv_"+key,(String)en.getValue());
                	}
                }
                ////// 3. copiar los otvalor del original a la base //////
                
                ////// 4. sustituir los otvalor por los nuevos del form //////
                mapaValosit.put("pv_otvalor02",fenacimiIte);
                ////// 4. sustituir los otvalor por los nuevos del form //////
                
                kernelManager.insertaValoresSituaciones(mapaValosit);
                
                ////// valosit //////
                /////////////////////
                
                //////////////////////
                ////// mpersona //////
                Map<String,String> mapaMpersona=new LinkedHashMap<String,String>(0);
				mapaMpersona.put("pv_cdperson_i" , cdpersonIte); 
				mapaMpersona.put("pv_fenacimi_i" , fenacimiIte);
				endososManager.actualizarFenacimi(mapaMpersona);
                ////// mpersona //////
                //////////////////////

				//////////////////////
				////// mpoliper //////
				/*
				Map<String,Object>mapaMpoliper=new LinkedHashMap<String,Object>(0);
				mapaMpoliper.put("pv_cdunieco_i" , cdunieco);
				mapaMpoliper.put("pv_cdramo_i"   , cdramo);
				mapaMpoliper.put("pv_estado_i"   , estado);
				mapaMpoliper.put("pv_nmpoliza_i" , nmpoliza);
				mapaMpoliper.put("pv_nmsituac_i" , nmsituacIte);
				mapaMpoliper.put("pv_cdrol_i"    , "2");
				mapaMpoliper.put("pv_cdperson_i" , cdpersonIte);
				mapaMpoliper.put("pv_nmsuplem_i" , nmsuplem);
				mapaMpoliper.put("pv_status_i"   , "V");
				mapaMpoliper.put("pv_nmorddom_i" , "1");
				mapaMpoliper.put("pv_swreclam_i" , null);
				mapaMpoliper.put("pv_accion_i"   , "I");
				mapaMpoliper.put("pv_swexiper_i" , Constantes.SI);
				kernelManager.movMpoliper(mapaMpoliper);
				*/
				////// mpoliper //////
				//////////////////////
				
				/////////////////////////////////
                ////// valores por defecto //////
                Map<String,String> mapaCoberturas=new HashMap<String,String>(0);
                mapaCoberturas.put("pv_cdunieco_i",   cdunieco);//se agrega desde el formulario
                mapaCoberturas.put("pv_cdramo_i",     cdramo);//se agrega desde el formulario
                mapaCoberturas.put("pv_estado_i",     estado);
                mapaCoberturas.put("pv_nmpoliza_i",   nmpoliza);
                mapaCoberturas.put("pv_nmsituac_i",   nmsituacIte);
                mapaCoberturas.put("pv_nmsuplem_i",   nmsuplem);
                mapaCoberturas.put("pv_cdgarant_i",   "TODO");
                mapaCoberturas.put("pv_cdtipsup_i",   cdtipsup);
                kernelManager.coberturas(mapaCoberturas);
                ////// valores por defecto //////
                /////////////////////////////////
                
                //////////////////////////////
                ////// inserta tworksup //////
                Map<String,String>mapaTworksupEnd=new LinkedHashMap<String,String>(0);
                mapaTworksupEnd.put("pv_cdunieco_i" , cdunieco);
                mapaTworksupEnd.put("pv_cdramo_i"   , cdramo);
                mapaTworksupEnd.put("pv_estado_i"   , estado);
                mapaTworksupEnd.put("pv_nmpoliza_i" , nmpoliza);
                mapaTworksupEnd.put("pv_cdtipsup_i" , cdtipsup);
                mapaTworksupEnd.put("pv_nmsuplem_i" , nmsuplem);
                mapaTworksupEnd.put("pv_nmsituac_i" , nmsituacIte);
                endososManager.insertarTworksupEnd(mapaTworksupEnd);
                ////// inserta tworksup //////
                //////////////////////////////
                
                //////////////////////////
                ////// tarificacion //////
                Map<String,String>mapaSigsvalipolEnd=new LinkedHashMap<String,String>(0);
    			mapaSigsvalipolEnd.put("pv_cdusuari_i" , cdusuari);
    			mapaSigsvalipolEnd.put("pv_cdelemen_i" , cdelemen);
    			mapaSigsvalipolEnd.put("pv_cdunieco_i" , cdunieco);
    			mapaSigsvalipolEnd.put("pv_cdramo_i"   , cdramo);
    			mapaSigsvalipolEnd.put("pv_estado_i"   , estado);
    			mapaSigsvalipolEnd.put("pv_nmpoliza_i" , nmpoliza);
    			mapaSigsvalipolEnd.put("pv_nmsituac_i" , nmsituacIte);
    			mapaSigsvalipolEnd.put("pv_nmsuplem_i" , nmsuplem);
    			mapaSigsvalipolEnd.put("pv_cdtipsit_i" , cdtipsit);
    			mapaSigsvalipolEnd.put("pv_cdtipsup_i" , cdtipsup);
    			endososManager.sigsvalipolEnd(mapaSigsvalipolEnd);
                ////// tarificacion //////
    			//////////////////////////
    			
    			//////////////////////////
    			////// valor endoso //////
    			Map<String,Object>mapaValorEndoso=new LinkedHashMap<String,Object>(0);
    			mapaValorEndoso.put("pv_cdunieco_i" , cdunieco);
    			mapaValorEndoso.put("pv_cdramo_i"   , cdramo);
    			mapaValorEndoso.put("pv_estado_i"   , estado);
    			mapaValorEndoso.put("pv_nmpoliza_i" , nmpoliza);
    			mapaValorEndoso.put("pv_nmsituac_i" , nmsituacIte);
    			mapaValorEndoso.put("pv_nmsuplem_i" , nmsuplem);
    			mapaValorEndoso.put("pv_feinival_i" , renderFechas.parse(fechaEndoso));
    			mapaValorEndoso.put("pv_cdtipsup_i" , cdtipsup);
    			endososManager.calcularValorEndoso(mapaValorEndoso);
    			////// valor endoso //////
    			//////////////////////////
			}
			
			String tramiteGenerado=this.confirmarEndoso(
					cdunieco,
					cdramo,
					estado,
					nmpoliza,
					nmsuplem,
					nsuplogi,
					cdtipsup,
					"",
					renderFechas.parse(fechaEndoso),
					cdtipsit);
			
			if(tramiteGenerado==null||tramiteGenerado.length()==0)
			{

			    ///////////////////////////////////////
			    ////// re generar los documentos //////
			    /*///////////////////////////////////*/
			    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
				paramsGetDoc.put("pv_cdunieco_i" , cdunieco);
				paramsGetDoc.put("pv_cdramo_i"   , cdramo);
				paramsGetDoc.put("pv_estado_i"   , estado);
				paramsGetDoc.put("pv_nmpoliza_i" , nmpoliza);
				paramsGetDoc.put("pv_nmsuplem_i" , nmsuplem);
				paramsGetDoc.put("pv_tipmov_i"   , cdtipsup);
			    List<Map<String,String>>listaDocu=endososManager.reimprimeDocumentos(paramsGetDoc);
			    log.debug("documentos que se regeneran: "+listaDocu);
			    
			    String rutaCarpeta=this.getText("ruta.documentos.poliza")+"/"+ntramite;
			    
				//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
				for(Map<String,String> docu:listaDocu)
				{
					log.debug("docu iterado: "+docu);
					String descripc=docu.get("descripc");
					String descripl=docu.get("descripl");
					String url=this.getText("ruta.servidor.reports")
							+ "?destype=cache"
							+ "&desformat=PDF"
							+ "&userid="+this.getText("pass.servidor.reports")
							+ "&report="+descripl
							+ "&paramform=no"
							+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
							+ "&p_unieco="+cdunieco
							+ "&p_ramo="+cdramo
							+ "&p_estado="+estado
							+ "&p_poliza="+nmpoliza
							+ "&p_suplem="+nmsuplem
							+ "&desname="+rutaCarpeta+"/"+descripc;
					if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
					{
						// C R E D E N C I A L _ X X X X X X . P D F
						//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
						url+="&p_cdperson="+descripc.substring(11, descripc.lastIndexOf("_"));
					}
					log.debug(""
							+ "\n#################################"
							+ "\n###### Se solicita reporte ######"
							+ "\na "+url+""
							+ "\n#################################");
					HttpUtil.generaArchivo(url,rutaCarpeta+"/"+descripc);
					log.debug(""
							+ "\n######                    ######"
							+ "\n###### reporte solicitado ######"
							+ "\na "+url+""
							+ "\n################################"
							+ "\n################################"
							+ "");
				}
			    /*///////////////////////////////////*/
				////// re generar los documentos //////
			    ///////////////////////////////////////
				
				ejecutaWSclienteSaludEndoso(cdunieco, cdramo, estado, nmpoliza, nmsuplem, "ACTUALIZA");
				
				/**
				 * TODO: Poner variable el cdTipSitGS de la poliza y la sucursal
				 */
				String cdtipsitGS = "213";
				String sucursal = cdunieco;
				if(StringUtils.isNotBlank(sucursal) && "1".equals(sucursal)) sucursal = "1000";
				
				String nmsolici = listaDocu.get(0).get("nmsolici");
				String nmtramite = listaDocu.get(0).get("ntramite");
				
				ejecutaWSrecibosEndoso(cdunieco, cdramo,
				estado, nmpoliza,
				nmsuplem, nsuplogi, rutaCarpeta,
						cdtipsitGS, sucursal, nmsolici, nmtramite,
						true, "INSERTA", cdtipsup );
			
				mensaje="Endoso confirmado "+nsuplogi;
			}
			else
			{
				mensaje="El endoso "+nsuplogi
						+" se guard&oacute; en mesa de control para autorizaci&oacute;n "
						+ "con n&uacute;mero de tr&aacute;mite "+tramiteGenerado;
			}
			
			success=true;
		}
		catch(Exception ex)
		{
			log.debug("error al guardar endoso de edad",ex);
			success=false;
			error=ex.getMessage();
		}
		log.debug("\n"
				+ "\n######                        ######"
				+ "\n###### guardar endoso de edad ######"
				+ "\n####################################"
				+ "\n####################################"
				);
		return SUCCESS;
	}
	/*////////////////////////////////*/
	////// guardar endoso de edad //////
	////////////////////////////////////
	
	//////////////////////////////////////
	////// endoso de cambio de sexo //////
	/*
	smap1:
		cdramo: "2"
		cdtipsit: "SL"
		cdunieco: "1006"
		estado: "M"
		hombremujer: "no"
		nmpoliza: "18"
		ntramite: "662"
	*/
	/*//////////////////////////////////*/
	public String endosoSexo()
	{
		this.session=ActionContext.getContext().getSession();
		log.debug("\n"
				+ "\n#######################################"
				+ "\n#######################################"
				+ "\n###### endoso de camibio de sexo ######"
				+ "\n######                           ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("smap2: "+smap2);
		log.debug("slist1: "+slist1);
		String respuesta=this.validaEndosoAnterior(
				smap1.get("cdunieco")
				,smap1.get("cdramo")
				,smap1.get("estado")
				,smap1.get("nmpoliza")
				,smap1.get("hombremujer").equalsIgnoreCase("si")?
						TipoEndoso.MODIFICACION_SEXO_H_A_M.getCdTipSup().toString():
						TipoEndoso.MODIFICACION_SEXO_M_A_H.getCdTipSup().toString());
		if(respuesta.equals(SUCCESS))
		{
			try
			{
				imap1=new HashMap<String,Item>();
				GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gc.generaParcial(pantallasManager.obtenerCamposPantalla(
						null,null,null
						,null,null,null
						,"ENDOSO_SEXO",null,null
						,"MODELO"
						));
				imap1.put("modelo"   , gc.getFields());
				imap1.put("columnas" , gc.getColumns());
			}
			catch(Exception ex)
			{
				log.error("error al mostrar pantalla de endoso de cambio de sexo",ex);
			}
		}
		log.debug("\n"
				+ "\n######                           ######"
				+ "\n###### endoso de camibio de sexo ######"
				+ "\n#######################################"
				+ "\n#######################################"
				);
		return respuesta;
	}
	/*//////////////////////////////////*/
	////// endoso de cambio de sexo //////
	//////////////////////////////////////
	
	//////////////////////////////////////////////
	////// guardar endoso de cambio de sexo //////
	/*
	smap1: {cdramo=2, nmpoliza=23, estado=M, cdtipsit=SL, ntramite=480, cdunieco=1205, hombremujer=si}
	smap2: {fecha_endoso=16/01/2014}
    slist1:
           [
                {cdperson=511816, nmsituac=1}
                ,{cdperson=511811, nmsituac=2}
           ]
	*/
	/*//////////////////////////////////////////*/
	public String guardarEndosoSexo()
	{
		this.session=ActionContext.getContext().getSession();
		log.debug("\n"
				+ "\n###############################################"
				+ "\n###############################################"
				+ "\n###### guardar endoso de camibio de sexo ######"
				+ "\n######                                   ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("smap2: "+smap2);
		log.debug("slist1: "+slist1);
		try
		{
			String  cdunieco    = smap1.get("cdunieco");
			String  cdramo      = smap1.get("cdramo");
			String  cdtipsit    = smap1.get("cdtipsit");
			String  estado      = smap1.get("estado");
			String  nmpoliza    = smap1.get("nmpoliza");
			String  ntramite    = smap1.get("ntramite");
			String  cdelemen    = ((UserVO)session.get("USUARIO")).getEmpresa().getElementoId();
			String  cdusuari    = ((UserVO)session.get("USUARIO")).getUser();
			Date    fechaHoy    = new Date();
			String  fechaEndoso = smap2.get("fecha_endoso");
			boolean hombremujer = smap1.get("hombremujer").equalsIgnoreCase("si");
			String cdtipsup = hombremujer 
					? TipoEndoso.MODIFICACION_SEXO_H_A_M.getCdTipSup().toString()
					: TipoEndoso.MODIFICACION_SEXO_M_A_H.getCdTipSup().toString();
			String sexo         = hombremujer?"M":"H";
			
			Map<String,String>paramsIniciarEndoso=new HashMap<String,String>(0);
			paramsIniciarEndoso.put("pv_cdunieco_i" , cdunieco);
			paramsIniciarEndoso.put("pv_cdramo_i"   , cdramo);
			paramsIniciarEndoso.put("pv_estado_i"   , estado);
			paramsIniciarEndoso.put("pv_nmpoliza_i" , nmpoliza);
			paramsIniciarEndoso.put("pv_fecha_i"    , fechaEndoso);
			paramsIniciarEndoso.put("pv_cdelemen_i" , cdelemen);
			paramsIniciarEndoso.put("pv_cdusuari_i" , cdusuari);
			paramsIniciarEndoso.put("pv_proceso_i"  , "END");
			paramsIniciarEndoso.put("pv_cdtipsup_i" , cdtipsup);
			Map<String,String>respuestaIniciarEndoso=endososManager.iniciarEndoso(paramsIniciarEndoso);
			
			String nmsuplem=respuestaIniciarEndoso.get("pv_nmsuplem_o");
			String nsuplogi=respuestaIniciarEndoso.get("pv_nsuplogi_o");

			/*
			Map<String,String>paramsObtenerDatosMpolisit=new HashMap<String,String>();
			paramsObtenerDatosMpolisit.put("pv_cdunieco_i" , cdunieco);
			paramsObtenerDatosMpolisit.put("pv_cdramo_i"   , cdramo);
			paramsObtenerDatosMpolisit.put("pv_estado_i"   , estado);
			paramsObtenerDatosMpolisit.put("pv_nmpoliza_i" , nmpoliza);
			Map<String,String>respuestaObtenerDatosMpolisit=endososManager.obtieneDatosMpolisit(paramsObtenerDatosMpolisit);
			String cdplan=respuestaObtenerDatosMpolisit.get("pv_cdplan_o");
			*/
			
			for(Map<String,String>inciso:slist1)
			{
				String nmsituacIte=inciso.get("nmsituac");
				String cdpersonIte=inciso.get("cdperson");
				
				/*
				Map<String,Object>mapaPolisit=new HashMap<String,Object>(0);
                mapaPolisit.put("pv_cdunieco_i",    cdunieco);
                mapaPolisit.put("pv_cdramo_i",      cdramo);
                mapaPolisit.put("pv_estado_i",      estado);
                mapaPolisit.put("pv_nmpoliza_i",    nmpoliza);
                mapaPolisit.put("pv_nmsituac_i",    nmsituacIte);
                mapaPolisit.put("pv_nmsuplem_i",    nmsuplem);
                mapaPolisit.put("pv_status_i",      "V");
                mapaPolisit.put("pv_cdtipsit_i",    cdtipsit);
                mapaPolisit.put("pv_swreduci_i",    null);
                mapaPolisit.put("pv_cdagrupa_i",    "1");
                mapaPolisit.put("pv_cdestado_i",    "0");
                mapaPolisit.put("pv_fefecsit_i",    fechaHoy);
                mapaPolisit.put("pv_fecharef_i",    fechaHoy);
                mapaPolisit.put("pv_cdgrupo_i",     null);
                mapaPolisit.put("pv_nmsituaext_i",  null);
                mapaPolisit.put("pv_nmsitaux_i",    null);
                mapaPolisit.put("pv_nmsbsitext_i",  null);
                mapaPolisit.put("pv_cdplan_i",      cdplan);
                mapaPolisit.put("pv_cdasegur_i",    "30");
                mapaPolisit.put("pv_accion_i",      "I");
                kernelManager.insertaPolisit(mapaPolisit);
                */
                
                /////////////////////
                ////// valosit //////
                
                ////// 1. mapa valosit base //////
                Map<String,String>mapaValosit=new HashMap<String,String>(0);
                mapaValosit.put("pv_cdunieco",    cdunieco);
                mapaValosit.put("pv_cdramo",      cdramo);
                mapaValosit.put("pv_estado",      estado);
                mapaValosit.put("pv_nmpoliza",    nmpoliza);
                mapaValosit.put("pv_nmsituac",    nmsituacIte);
                mapaValosit.put("pv_nmsuplem",    nmsuplem);
                mapaValosit.put("pv_status",      "V");
                mapaValosit.put("pv_cdtipsit",    cdtipsit);
                mapaValosit.put("pv_accion_i",   "I");
                ////// 1. mapa valosit base //////
                
                ////// 2. obtener el original //////
                Map<String,String>mapaObtenerValosit=new HashMap<String,String>(0);
                mapaObtenerValosit.put("pv_cdunieco_i" , cdunieco);
                mapaObtenerValosit.put("pv_nmpoliza_i" , nmpoliza);
                mapaObtenerValosit.put("pv_cdramo_i"   , cdramo);
                mapaObtenerValosit.put("pv_estado_i"   , estado);
                mapaObtenerValosit.put("pv_nmsituac_i" , nmsituacIte);
                Map<String,Object>valositOriginal=kernelManager.obtieneValositSituac(mapaObtenerValosit);
                ////// 2. obtener el original //////
                
                ////// 3. copiar los otvalor del original a la base //////
                for(Entry<String,Object> en:valositOriginal.entrySet())
                {
                	String key=en.getKey();
                	if(key.substring(0,3).equalsIgnoreCase("otv"))
                	{
                		mapaValosit.put("pv_"+key,(String)en.getValue());
                	}
                }
                ////// 3. copiar los otvalor del original a la base //////
                
                ////// 4. sustituir los otvalor por los nuevos del form //////
                mapaValosit.put("pv_otvalor01",sexo);
                ////// 4. sustituir los otvalor por los nuevos del form //////
                
                kernelManager.insertaValoresSituaciones(mapaValosit);
                
                ////// valosit //////
                /////////////////////
                
                //////////////////////
                ////// mpersona //////
                Map<String,String> mapaMpersona=new LinkedHashMap<String,String>(0);
				mapaMpersona.put("pv_cdperson_i" , cdpersonIte); 
				mapaMpersona.put("pv_sexo_i"     , sexo);
				endososManager.actualizarSexo(mapaMpersona);
                ////// mpersona //////
                //////////////////////

				//////////////////////
				////// mpoliper //////
				/*
				Map<String,Object>mapaMpoliper=new LinkedHashMap<String,Object>(0);
				mapaMpoliper.put("pv_cdunieco_i" , cdunieco);
				mapaMpoliper.put("pv_cdramo_i"   , cdramo);
				mapaMpoliper.put("pv_estado_i"   , estado);
				mapaMpoliper.put("pv_nmpoliza_i" , nmpoliza);
				mapaMpoliper.put("pv_nmsituac_i" , nmsituacIte);
				mapaMpoliper.put("pv_cdrol_i"    , "2");
				mapaMpoliper.put("pv_cdperson_i" , cdpersonIte);
				mapaMpoliper.put("pv_nmsuplem_i" , nmsuplem);
				mapaMpoliper.put("pv_status_i"   , "V");
				mapaMpoliper.put("pv_nmorddom_i" , "1");
				mapaMpoliper.put("pv_swreclam_i" , null);
				mapaMpoliper.put("pv_accion_i"   , "I");
				mapaMpoliper.put("pv_swexiper_i" , Constantes.SI);
				kernelManager.movMpoliper(mapaMpoliper);
				*/
				////// mpoliper //////
				//////////////////////
				
				/////////////////////////////////
                ////// valores por defecto //////
                Map<String,String> mapaCoberturas=new HashMap<String,String>(0);
                mapaCoberturas.put("pv_cdunieco_i",   cdunieco);//se agrega desde el formulario
                mapaCoberturas.put("pv_cdramo_i",     cdramo);//se agrega desde el formulario
                mapaCoberturas.put("pv_estado_i",     estado);
                mapaCoberturas.put("pv_nmpoliza_i",   nmpoliza);
                mapaCoberturas.put("pv_nmsituac_i",   nmsituacIte);
                mapaCoberturas.put("pv_nmsuplem_i",   nmsuplem);
                mapaCoberturas.put("pv_cdgarant_i",   "TODO");
                mapaCoberturas.put("pv_cdtipsup_i",   cdtipsup);
                kernelManager.coberturas(mapaCoberturas);
                ////// valores por defecto //////
                /////////////////////////////////
                
                //////////////////////////////
                ////// inserta tworksup //////
                Map<String,String>mapaTworksupEnd=new LinkedHashMap<String,String>(0);
                mapaTworksupEnd.put("pv_cdunieco_i" , cdunieco);
                mapaTworksupEnd.put("pv_cdramo_i"   , cdramo);
                mapaTworksupEnd.put("pv_estado_i"   , estado);
                mapaTworksupEnd.put("pv_nmpoliza_i" , nmpoliza);
                mapaTworksupEnd.put("pv_cdtipsup_i" , cdtipsup);
                mapaTworksupEnd.put("pv_nmsuplem_i" , nmsuplem);
                mapaTworksupEnd.put("pv_nmsituac_i" , nmsituacIte);
                endososManager.insertarTworksupEnd(mapaTworksupEnd);
                ////// inserta tworksup //////
                //////////////////////////////
                
                //////////////////////////
                ////// tarificacion //////
                Map<String,String>mapaSigsvalipolEnd=new LinkedHashMap<String,String>(0);
    			mapaSigsvalipolEnd.put("pv_cdusuari_i" , cdusuari);
    			mapaSigsvalipolEnd.put("pv_cdelemen_i" , cdelemen);
    			mapaSigsvalipolEnd.put("pv_cdunieco_i" , cdunieco);
    			mapaSigsvalipolEnd.put("pv_cdramo_i"   , cdramo);
    			mapaSigsvalipolEnd.put("pv_estado_i"   , estado);
    			mapaSigsvalipolEnd.put("pv_nmpoliza_i" , nmpoliza);
    			mapaSigsvalipolEnd.put("pv_nmsituac_i" , nmsituacIte);
    			mapaSigsvalipolEnd.put("pv_nmsuplem_i" , nmsuplem);
    			mapaSigsvalipolEnd.put("pv_cdtipsit_i" , cdtipsit);
    			mapaSigsvalipolEnd.put("pv_cdtipsup_i" , cdtipsup);
    			endososManager.sigsvalipolEnd(mapaSigsvalipolEnd);
                ////// tarificacion //////
    			//////////////////////////
    			
    			//////////////////////////
    			////// valor endoso //////
    			Map<String,Object>mapaValorEndoso=new LinkedHashMap<String,Object>(0);
    			mapaValorEndoso.put("pv_cdunieco_i" , cdunieco);
    			mapaValorEndoso.put("pv_cdramo_i"   , cdramo);
    			mapaValorEndoso.put("pv_estado_i"   , estado);
    			mapaValorEndoso.put("pv_nmpoliza_i" , nmpoliza);
    			mapaValorEndoso.put("pv_nmsituac_i" , nmsituacIte);
    			mapaValorEndoso.put("pv_nmsuplem_i" , nmsuplem);
    			mapaValorEndoso.put("pv_feinival_i" , renderFechas.parse(fechaEndoso));
    			mapaValorEndoso.put("pv_cdtipsup_i" , cdtipsup);
    			endososManager.calcularValorEndoso(mapaValorEndoso);
    			////// valor endoso //////
    			//////////////////////////
			}
			
			String tramiteGenerado=this.confirmarEndoso(
					cdunieco,
					cdramo,
					estado,
					nmpoliza,
					nmsuplem,
					nsuplogi,
					cdtipsup,
					"",
					renderFechas.parse(fechaEndoso),
					cdtipsit);
		    
			if(tramiteGenerado==null||tramiteGenerado.length()==0)
			{
				
			    ///////////////////////////////////////
			    ////// re generar los documentos //////
			    /*///////////////////////////////////*/
			    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
				paramsGetDoc.put("pv_cdunieco_i" , cdunieco);
				paramsGetDoc.put("pv_cdramo_i"   , cdramo);
				paramsGetDoc.put("pv_estado_i"   , estado);
				paramsGetDoc.put("pv_nmpoliza_i" , nmpoliza);
				paramsGetDoc.put("pv_nmsuplem_i" , nmsuplem);
				paramsGetDoc.put("pv_tipmov_i"   , cdtipsup);
			    List<Map<String,String>>listaDocu=endososManager.reimprimeDocumentos(paramsGetDoc);
			    log.debug("documentos que se regeneran: "+listaDocu);
			    
			    String rutaCarpeta=this.getText("ruta.documentos.poliza")+"/"+ntramite;
			    
				//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
				for(Map<String,String> docu:listaDocu)
				{
					log.debug("docu iterado: "+docu);
					String descripc=docu.get("descripc");
					String descripl=docu.get("descripl");
					String url=this.getText("ruta.servidor.reports")
							+ "?destype=cache"
							+ "&desformat=PDF"
							+ "&userid="+this.getText("pass.servidor.reports")
							+ "&report="+descripl
							+ "&paramform=no"
							+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
							+ "&p_unieco="+cdunieco
							+ "&p_ramo="+cdramo
							+ "&p_estado="+estado
							+ "&p_poliza="+nmpoliza
							+ "&p_suplem="+nmsuplem
							+ "&desname="+rutaCarpeta+"/"+descripc;
					if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
					{
						// C R E D E N C I A L _ X X X X X X . P D F
						//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
						url+="&p_cdperson="+descripc.substring(11, descripc.lastIndexOf("_"));
					}
					log.debug(""
							+ "\n#################################"
							+ "\n###### Se solicita reporte ######"
							+ "\na "+url+""
							+ "\n#################################");
					HttpUtil.generaArchivo(url,rutaCarpeta+"/"+descripc);
					log.debug(""
							+ "\n######                    ######"
							+ "\n###### reporte solicitado ######"
							+ "\na "+url+""
							+ "\n################################"
							+ "\n################################"
							+ "");
				}
			    /*///////////////////////////////////*/
				////// re generar los documentos //////
			    ///////////////////////////////////////
				
				ejecutaWSclienteSaludEndoso(cdunieco, cdramo, estado, nmpoliza, nmsuplem, "ACTUALIZA");
				
				/**
				 * TODO: Poner variable el cdTipSitGS de la poliza y la sucursal
				 */
				String cdtipsitGS = "213";
				String sucursal = cdunieco;
				if(StringUtils.isNotBlank(sucursal) && "1".equals(sucursal)) sucursal = "1000";
				
				String nmsolici = listaDocu.get(0).get("nmsolici");
				String nmtramite = listaDocu.get(0).get("ntramite");
				
				ejecutaWSrecibosEndoso(cdunieco, cdramo,
				estado, nmpoliza,
				nmsuplem, nsuplogi, rutaCarpeta,
						cdtipsitGS, sucursal, nmsolici, nmtramite,
						true, "INSERTA", cdtipsup );
				
				mensaje="Endoso confirmado "+nsuplogi;
			}
			else
			{
				mensaje="El endoso "+nsuplogi
						+" se guard&oacute; en mesa de control para autorizaci&oacute;n "
						+ "con n&uacute;mero de tr&aacute;mite "+tramiteGenerado;
			}
			success=true;
		}
		catch(Exception ex)
		{
			success=false;
			error=ex.getMessage();
			log.error("error al guardar endoso de cambio de sexo",ex);
		}
		log.debug("\n"
				+ "\n######                                   ######"
				+ "\n###### guardar endoso de camibio de sexo ######"
				+ "\n###############################################"
				+ "\n###############################################"
				);
		return SUCCESS;
	}
	/*//////////////////////////////////////////*/
	////// guardar endoso de cambio de sexo //////
	//////////////////////////////////////////////
	
	//////////////////////////////
	////// confirmar endoso //////
	/*//////////////////////////*/
	private String confirmarEndoso(String cdunieco,String cdramo,String estado,String nmpoliza,
			String nmsuplem, String nsuplogi, String cdtipsup, String dscoment, Date fechaEndoso,
			String cdtipsit)throws Exception
	{
		String ntramiteEndoso="0";
		String ntramite=endososManager.obtenerNtramiteEmision(cdunieco, cdramo, estado, nmpoliza);
		
		Date fechaHoy=new Date();
		long hoym=fechaHoy.getTime();
		long endm=fechaEndoso.getTime();
		long dif=hoym-endm;
		dif=Math.abs(dif);
		long max=30l*24l*60l*60l*1000l;
		
		if(dif>max)
		{
			String dssuplem="";
			List<Map<String,String>>endosos=endososManager.obtenerNombreEndosos();
			for(Map<String,String>endoso:endosos)
			{
				if(endoso.get("CDTIPSUP").equalsIgnoreCase(cdtipsup))
				{
					dssuplem=endoso.get("DSTIPSUP");
				}
			}
			
			Map<String,Object>paramsMesaControl=new HashMap<String,Object>();
			paramsMesaControl.put("pv_cdunieco_i"   , cdunieco);
			paramsMesaControl.put("pv_cdramo_i"     , cdramo);
			paramsMesaControl.put("pv_estado_i"     , estado);
			paramsMesaControl.put("pv_nmpoliza_i"   , nmpoliza);
			paramsMesaControl.put("pv_nmsuplem_i"   , nmsuplem);
			paramsMesaControl.put("pv_cdsucadm_i"   , cdunieco);
			paramsMesaControl.put("pv_cdsucdoc_i"   , ntramite);
			paramsMesaControl.put("pv_cdtiptra_i"   , "15");
			paramsMesaControl.put("pv_ferecepc_i"   , fechaEndoso);
			paramsMesaControl.put("pv_cdagente_i"   , "100");
			paramsMesaControl.put("pv_referencia_i" , dssuplem);
			paramsMesaControl.put("pv_nombre_i"     , cdtipsup);
			paramsMesaControl.put("pv_festatus_i"   , fechaEndoso);
			paramsMesaControl.put("pv_status_i"     , "8");
			paramsMesaControl.put("pv_comments_i"   , dscoment);
			paramsMesaControl.put("pv_nmsolici_i"   , nsuplogi);
			paramsMesaControl.put("pv_cdtipsit_i"   , cdtipsit);
			WrapperResultados wr=kernelManager.PMovMesacontrol(paramsMesaControl);
			ntramiteEndoso=(String) wr.getItemMap().get("ntramite");
		}
		else
		{
			Map<String,String>paramConfirmarEndosoB=new LinkedHashMap<String,String>(0);
			paramConfirmarEndosoB.put("pv_cdunieco_i" , cdunieco);
			paramConfirmarEndosoB.put("pv_cdramo_i"   , cdramo);
			paramConfirmarEndosoB.put("pv_estado_i"   , estado);
			paramConfirmarEndosoB.put("pv_nmpoliza_i" , nmpoliza);
			paramConfirmarEndosoB.put("pv_nmsuplem_i" , nmsuplem);
			paramConfirmarEndosoB.put("pv_nsuplogi_i" , nsuplogi);
			paramConfirmarEndosoB.put("pv_cdtipsup_i" , cdtipsup);
			paramConfirmarEndosoB.put("pv_dscoment_i" , dscoment);
			endososManager.confirmarEndosoB(paramConfirmarEndosoB);
			ntramiteEndoso="";
		}
	    
	    return ntramiteEndoso;
	}
	/*//////////////////////////*/
	////// confirmar endoso //////
	//////////////////////////////
	
	//////////////////////////////
	////// autorizar endoso //////
	/*//////////////////////////*/
	public String autorizarEndoso()
	{
		log.debug("\n"
				+ "\n##############################"
				+ "\n##############################"
				+ "\n###### autorizar endoso ######"
				+ "\n######                  ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			String cdunieco    = smap1.get("cdunieco");
			String cdramo      = smap1.get("cdramo");
			String estado      = smap1.get("estado");
			String nmpoliza    = smap1.get("nmpoliza");
			String nmsuplem    = smap1.get("nmsuplem");
			String nsuplogi    = smap1.get("nsuplogi");
			String cdtipsup    = smap1.get("cdtipsup");
			String ntramiteEmi = smap1.get("ntramiteemi");
			String ntramiteEnd = smap1.get("ntramiteend");
			String status      = smap1.get("status");
			String coment      = smap1.get("observacion");
			
			kernelManager.mesaControlUpdateStatus(ntramiteEnd, status);
			
			Map<String,String>paramConfirmarEndosoB=new LinkedHashMap<String,String>(0);
			paramConfirmarEndosoB.put("pv_cdunieco_i" , cdunieco);
			paramConfirmarEndosoB.put("pv_cdramo_i"   , cdramo);
			paramConfirmarEndosoB.put("pv_estado_i"   , estado);
			paramConfirmarEndosoB.put("pv_nmpoliza_i" , nmpoliza);
			paramConfirmarEndosoB.put("pv_nmsuplem_i" , nmsuplem);
			paramConfirmarEndosoB.put("pv_nsuplogi_i" , nsuplogi);
			paramConfirmarEndosoB.put("pv_cdtipsup_i" , cdtipsup);
			paramConfirmarEndosoB.put("pv_dscoment_i" , coment);
			endososManager.confirmarEndosoB(paramConfirmarEndosoB);
			
			///////////////////////////////////////
		    ////// re generar los documentos //////
		    /*///////////////////////////////////*/
		    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
			paramsGetDoc.put("pv_cdunieco_i" , cdunieco);
			paramsGetDoc.put("pv_cdramo_i"   , cdramo);
			paramsGetDoc.put("pv_estado_i"   , estado);
			paramsGetDoc.put("pv_nmpoliza_i" , nmpoliza);
			paramsGetDoc.put("pv_nmsuplem_i" , nmsuplem);
			paramsGetDoc.put("pv_tipmov_i"   , cdtipsup);
		    List<Map<String,String>>listaDocu=endososManager.reimprimeDocumentos(paramsGetDoc);
		    log.debug("documentos que se regeneran: "+listaDocu);
		    
		    String rutaCarpeta = this.getText("ruta.documentos.poliza")+"/"+ntramiteEmi;
		    String nmsolici    = listaDocu.size()>0?listaDocu.get(0).get("nmsolici"):nmpoliza;
		    
			//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
			for(Map<String,String> docu:listaDocu)
			{
				log.debug("docu iterado: "+docu);
				String descripc=docu.get("descripc");
				String descripl=docu.get("descripl");
				String url=this.getText("ruta.servidor.reports")
						+ "?destype=cache"
						+ "&desformat=PDF"
						+ "&userid="+this.getText("pass.servidor.reports")
						+ "&report="+descripl
						+ "&paramform=no"
						+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
						+ "&p_unieco="+cdunieco
						+ "&p_ramo="+cdramo
						+ "&p_estado="+estado
						+ "&p_poliza="+nmpoliza
						+ "&p_suplem="+nmsuplem
						+ "&desname="+rutaCarpeta+"/"+descripc;
				if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
				{
					// C R E D E N C I A L _ X X X X X X . P D F
					//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
					url+="&p_cdperson="+descripc.substring(11, descripc.lastIndexOf("_"));
				}
				log.debug(""
						+ "\n#################################"
						+ "\n###### Se solicita reporte ######"
						+ "\na "+url+""
						+ "\n#################################");
				HttpUtil.generaArchivo(url,rutaCarpeta+"/"+descripc);
				log.debug(""
						+ "\n######                    ######"
						+ "\n###### reporte solicitado ######"
						+ "\na "+url+""
						+ "\n################################"
						+ "\n################################"
						+ "");
			}
		    /*///////////////////////////////////*/
			////// re generar los documentos //////
		    ///////////////////////////////////////
			
			String cdtipsitGS = "213";
			String sucursal = cdunieco;
			if(StringUtils.isNotBlank(sucursal) && "1".equals(sucursal)) sucursal = "1000";
			
			//Creamos un enum en base al tipo de endoso enviado: 
			TipoEndoso enumTipoEndoso = null;
			for (TipoEndoso te : TipoEndoso.values()) {
			    if( cdtipsup.equals(te.getCdTipSup().toString()) ) {
			    	enumTipoEndoso = te;
			    	break;
			    }
			}
			
			switch (enumTipoEndoso) {
			case CORRECCION_NOMBRE_Y_RFC:
			case CAMBIO_DOMICILIO:
				
				ejecutaWSclienteSaludEndoso(cdunieco, cdramo, estado, nmpoliza, nmsuplem, "ACTUALIZA");
				
				//insertaURLrecibosEndoso(cdunieco, cdramo, estado, nmpoliza, nmsuplem, cdtipsitGS, sucursal, nmsolici, ntramiteEmi, cdtipsup);
				
				break;
							
			case ALTA_COBERTURAS:
			case BAJA_COBERTURAS:
			case ALTA_ASEGURADOS:
			case BAJA_ASEGURADOS:
			case DEDUCIBLE_MAS:
			case DEDUCIBLE_MENOS:
			case COPAGO_MAS:
			case COPAGO_MENOS:
				ejecutaWSrecibosEndoso(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nsuplogi, 
						rutaCarpeta, cdtipsitGS, sucursal, nmsolici, ntramiteEmi, true, "INSERTA", cdtipsup);
				break;
				
			case INCREMENTO_EDAD_ASEGURADO:
			case DECREMENTO_EDAD_ASEGURADO:
			case MODIFICACION_SEXO_H_A_M:
			case MODIFICACION_SEXO_M_A_H:
			case CAMBIO_DOMICILIO_ASEGURADO_TITULAR:
				
				ejecutaWSclienteSaludEndoso(cdunieco, cdramo, estado, nmpoliza, nmsuplem, "ACTUALIZA");
				
				ejecutaWSrecibosEndoso(cdunieco, cdramo,
				estado, nmpoliza,
				nmsuplem, nsuplogi, rutaCarpeta,
						cdtipsitGS, sucursal, nmsolici, ntramiteEmi,
						true, "INSERTA", cdtipsup );
				
				break;

			default:
				log.debug("**** NO HAY WEB SERVICE PARA CDTIPSUP " + cdtipsup + " ******");
				break;
		}
			
			success=true;
		}
		catch(Exception ex)
		{
			success=false;
			error=ex.toString();
			log.error("error al autorizar el endoso: ",ex);
		}
		log.debug("\n"
				+ "\n######                  ######"
				+ "\n###### autorizar endoso ######"
				+ "\n##############################"
				+ "\n##############################"
				);
		return SUCCESS;
	}
	/*//////////////////////////*/
	////// autorizar endoso //////
	//////////////////////////////
	
	///////////////////////////////////
	////// endoso domicilio full //////
	/*
	smap1:
	    cdrfc=MAVA900817001,
	    cdperson=511965,
	    fenacimi=1990-08-17T00:00:00,
	    sexo=H,
	    Apellido_Materno=MAT,
	    nombre=NOMBRE1,
	    nombrecompleto=NOMBRE1  PAT MAT,
	    nmsituac=1,
	    segundo_nombre=null,
	    Parentesco=T,
	    CDTIPSIT=SL,
	    NTRAMITE=615,
	    CDUNIECO=1006,
	    CDRAMO=2,
	    NMSUPLEM=245673812540000005,
	    NMPOLIZA=14,
	    swexiper=S,
	    NMPOLIEX=1006213000014000000,
	    nacional=001,
	    activo=true,
	    NSUPLOGI=8,
	    ESTADO=M,
	    cdrol=2,
	    tpersona=F,
	    Apellido_Paterno=PAT
	*/
	/*///////////////////////////////*/
	public String endosoDomicilioFull()
	{
		this.session=ActionContext.getContext().getSession();
		log.info("endosoDomicilioFull()");
		log.debug("\n"
				+ "\n#################################"
				+ "\n#################################"
				+ "\n###### endosoDomicilioFull ######"
				+ "\n######                     ######"
				);
		log.debug("smap1: "+smap1);
		String respuesta=this.validaEndosoAnterior(
				smap1.get("CDUNIECO")
				,smap1.get("CDRAMO")
				,smap1.get("ESTADO")
				,smap1.get("NMPOLIZA")
				,TipoEndoso.CAMBIO_DOMICILIO_ASEGURADO_TITULAR.getCdTipSup().toString());
		if(respuesta.equals(SUCCESS))
		{
			try
			{
				UserVO usuario=(UserVO)session.get("USUARIO");
				
				String cdunieco = smap1.get("CDUNIECO");
				String cdramo   = smap1.get("CDRAMO");
				String cdtipsit = smap1.get("CDTIPSIT");
				String estado   = smap1.get("ESTADO");
				String nmpoliza = smap1.get("NMPOLIZA");
				String nmsuplem = smap1.get("NMSUPLEM");
				String rol      = usuario.getRolActivo().getObjeto().getValue();
				String rolAsegu = smap1.get("cdrol");
				
				GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				
				imap1=new HashMap<String,Item>();
				
				gc.generaParcial(pantallasManager.obtenerCamposPantalla(
						cdunieco  , cdramo                  , cdtipsit , estado , nmpoliza
						,nmsuplem , "ENDOSO_DOMICILIO_FULL" , rol      , null   , "PANEL_LECTURA"));
							
				imap1.put("itemsLectura",gc.getItems());
				
				gc.generaParcial(pantallasManager.obtenerCamposPantalla(
						cdunieco  , cdramo                  , cdtipsit , estado , nmpoliza
						,nmsuplem , "ENDOSO_DOMICILIO_FULL" , rol      , null   , "ITEMS_DOMICIL"));
				
				imap1.put("itemsDomicil"  , gc.getItems());
				imap1.put("fieldsDomicil" , gc.getFields());
				
				/*
				Map<String,String>mapaTatriper=new HashMap<String,String>();
				mapaTatriper.put("pv_cdramo_i"   , cdramo);
				mapaTatriper.put("pv_cdrol_i"    , rolAsegu);
				mapaTatriper.put("pv_cdtipsit_i" , cdtipsit);
				gc.setCdramo(cdramo);
				gc.setCdrol(rolAsegu);
				gc.setCdtipsit(cdtipsit);
				gc.generaParcial(kernelManager.obtenerTatriper(mapaTatriper));
				
				imap1.put("itemsTatriper",gc.getItems());
				*/
				
			}
			catch(Exception ex)
			{
				log.error("error al cargar la pantalla de domicilio full",ex);
			}
		}
		log.debug("\n"
				+ "\n######                     ######"
				+ "\n###### endosoDomicilioFull ######"
				+ "\n#################################"
				+ "\n#################################"
				);
		return respuesta;
	}
	///////////////////////////////////
	////// endoso domicilio full //////
	/*///////////////////////////////*/
	
	////////////////////////////////////////
	////// guardarEndosoDomicilioFull //////
	/*
	smap1:
	    cdrfc=MAVA900817001,
	    cdperson=511965,
	    fenacimi=1990-08-17T00:00:00,
	    sexo=H,
	    Apellido_Materno=MAT,
	    nombre=NOMBRE1,
	    nombrecompleto=NOMBRE1  PAT MAT,
	    nmsituac=1,
	    segundo_nombre=null,
	    Parentesco=T,
	    CDTIPSIT=SL,
	    NTRAMITE=615,
	    CDUNIECO=1006,
	    CDRAMO=2,
	    NMSUPLEM=245673812540000005,
	    NMPOLIZA=14,
	    swexiper=S,
	    NMPOLIEX=1006213000014000000,
	    nacional=001,
	    activo=true,
	    NSUPLOGI=8,
	    ESTADO=M,
	    cdrol=2,
	    tpersona=F,
	    Apellido_Paterno=PAT
	smap2:
	    CDCOLONI: "137617"
	    CDEDO: "9600030"
	    CDMUNICI: "9600030003"
	    CODPOSTAL: "96000"
	    DSDOMICI: "FLORES"
	    NMNUMERO: "ROSAS"
	    NMNUMINT: "1"
	    NMORDDOM: "1"
	    NMTELEFO: "AA"
	*/
	/*////////////////////////////////////*/
	public String guardarEndosoDomicilioFull()
	{
		this.session=ActionContext.getContext().getSession();
		log.debug("\n"
				+ "\n########################################"
				+ "\n########################################"
				+ "\n###### guardarEndosoDomicilioFull ######"
				+ "\n######                            ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("smap2: "+smap2);
		log.debug("smap3: "+smap3);
		log.debug("parametros: "+parametros);
		try
		{
			UserVO usuario      = (UserVO) session.get("USUARIO");
			String cdelemento   = usuario.getEmpresa().getElementoId();
			String cdusuari     = usuario.getUser();
			String cdunieco     = smap1.get("CDUNIECO");
			String cdramo       = smap1.get("CDRAMO");
			String estado       = smap1.get("ESTADO");
			String nmpoliza     = smap1.get("NMPOLIZA");
			String nmsituac     = smap1.get("nmsituac");
			String sFechaEndoso = smap3.get("fecha_endoso");
			Date   dFechaEndoso = renderFechas.parse(sFechaEndoso);
			String cdtipsit     = smap1.get("CDTIPSIT");
			String cdperson     = smap1.get("cdperson");
			String cdrol        = smap1.get("cdrol");
			String nmordom      = smap2.get("NMORDDOM");
			String dsdomici     = smap2.get("DSDOMICI");
			String nmtelefo     = smap2.get("NMTELEFO");
			String cdpostal     = smap2.get("CODPOSTAL");
			String cdestado     = smap2.get("CDEDO");
			String cdmunici     = smap2.get("CDMUNICI");
			String cdcoloni     = smap2.get("CDCOLONI");
			String nmnumext     = smap2.get("NMNUMERO");
			String nmnumint     = smap2.get("NMNUMINT");
			String cdtipsup     = TipoEndoso.CAMBIO_DOMICILIO_ASEGURADO_TITULAR.getCdTipSup().toString();
			String proceso      = "END";
			String ntramite     = smap1.get("NTRAMITE");
			
			//PKG_ENDOSOS.P_ENDOSO_INICIA
			Map<String,String> resIniEnd=endososManager.iniciarEndoso(cdunieco, cdramo, estado
					,nmpoliza, sFechaEndoso, cdelemento, cdusuari, proceso, cdtipsup);
			
			String nmsuplem = resIniEnd.get("pv_nmsuplem_o");
			String nsuplogi = resIniEnd.get("pv_nsuplogi_o");
			
			//PKG_SATELITES.P_OBTIENE_DATOS_MPOLISIT
			/*
			Map<String,String>datosMpolisit=endososManager.obtieneDatosMpolisit(cdunieco,cdramo,estado,nmpoliza);
			
			String cdplan=datosMpolisit.get("pv_cdplan_o");
			
			//PKG_SATELITES.P_MOV_MPOLISIT
			kernelManager.insertaPolisit(cdunieco,cdramo,estado,nmpoliza,nmsituac
					,nmsuplem,Constantes.STATUS_VIVO,cdtipsit,null,"1","0",dFechaEndoso
					,dFechaEndoso,null,null,null,null,cdplan,"30",Constantes.INSERT_MODE);
			*/
			
			//PKG_COTIZA.P_OBTIENE_TVALOSIT
			Map<String,Object>valositOriginal=kernelManager.obtieneValositSituac(cdunieco, cdramo, estado, nmpoliza, nmsituac);
			/*
			"otvalor01" ... "otvalor50",
			"nmsuplem",
			"status",
			"cdtipsit"
			*/
			
			//otvalor05 -> pv_otvalor05
			Map<String,String>otvalorValosit=new HashMap<String,String>();
			for(Entry<String,Object>en:valositOriginal.entrySet())
			{
				String key   = en.getKey();
				String value = (String)en.getValue();
				
				if(key.substring(0,5).equalsIgnoreCase("otval"))
				{
					otvalorValosit.put("pv_"+key,value);
				}
			}
			
			String keyCodPostal = "";
			String keyEstado    = "";
			String keyMunicipio = "";
			if(cdtipsit.equalsIgnoreCase("SL")||cdtipsit.equalsIgnoreCase("SN"))
			{
				keyCodPostal = "pv_otvalor03";
				keyEstado    = "pv_otvalor04";
				keyMunicipio = "pv_otvalor17";
			}
			otvalorValosit.put(keyCodPostal , cdpostal);
			otvalorValosit.put(keyEstado    , cdestado);
			otvalorValosit.put(keyMunicipio , cdmunici);
			
			//PKG_SATELITES.P_MOV_TVALOSIT
			kernelManager.insertaValoresSituaciones(cdunieco, cdramo, estado, nmpoliza
					,nmsituac, nmsuplem, Constantes.STATUS_VIVO, cdtipsit, Constantes.INSERT_MODE, otvalorValosit);
			
			//////////////////////
			////// mdomicil //////
			/*//////////////////*/
			Map<String,String>paramDomicil=new LinkedHashMap<String,String>(0);
			paramDomicil.put("pv_cdperson_i" , cdperson);
			paramDomicil.put("pv_nmorddom_i" , nmordom);
			paramDomicil.put("pv_msdomici_i" , dsdomici);
			paramDomicil.put("pv_nmtelefo_i" , nmtelefo);
			paramDomicil.put("pv_cdpostal_i" , cdpostal);
	    	paramDomicil.put("pv_cdedo_i"    , cdestado);
			paramDomicil.put("pv_cdmunici_i" , cdmunici);
			paramDomicil.put("pv_cdcoloni_i" , cdcoloni);
			paramDomicil.put("pv_nmnumero_i" , nmnumext);
			paramDomicil.put("pv_nmnumint_i" , nmnumint);
			paramDomicil.put("pv_accion_i"   , Constantes.UPDATE_MODE);			
			kernelManager.pMovMdomicil(paramDomicil);
			/*//////////////////*/
			////// mdomicil //////
			//////////////////////
			
			//////////////////////////////
            ////// inserta tworksup //////
            Map<String,String>mapaTworksupEnd=new LinkedHashMap<String,String>(0);
            mapaTworksupEnd.put("pv_cdunieco_i" , cdunieco);
            mapaTworksupEnd.put("pv_cdramo_i"   , cdramo);
            mapaTworksupEnd.put("pv_estado_i"   , estado);
            mapaTworksupEnd.put("pv_nmpoliza_i" , nmpoliza);
            mapaTworksupEnd.put("pv_cdtipsup_i" , cdtipsup);
            mapaTworksupEnd.put("pv_nmsuplem_i" , nmsuplem);
            endososManager.insertarTworksupSitTodas(mapaTworksupEnd);
            ////// inserta tworksup //////
            //////////////////////////////
            
            //////////////////////////
            ////// tarificacion //////
            Map<String,String>mapaSigsvalipolEnd=new LinkedHashMap<String,String>(0);
			mapaSigsvalipolEnd.put("pv_cdusuari_i" , cdusuari);
			mapaSigsvalipolEnd.put("pv_cdelemen_i" , cdelemento);
			mapaSigsvalipolEnd.put("pv_cdunieco_i" , cdunieco);
			mapaSigsvalipolEnd.put("pv_cdramo_i"   , cdramo);
			mapaSigsvalipolEnd.put("pv_estado_i"   , estado);
			mapaSigsvalipolEnd.put("pv_nmpoliza_i" , nmpoliza);
			mapaSigsvalipolEnd.put("pv_nmsituac_i" , "0");
			mapaSigsvalipolEnd.put("pv_nmsuplem_i" , nmsuplem);
			mapaSigsvalipolEnd.put("pv_cdtipsit_i" , cdtipsit);
			mapaSigsvalipolEnd.put("pv_cdtipsup_i" , cdtipsup);
			endososManager.sigsvalipolEnd(mapaSigsvalipolEnd);
            ////// tarificacion //////
			//////////////////////////
			
			//////////////////////////
			////// valor endoso //////
			Map<String,Object>mapaValorEndoso=new LinkedHashMap<String,Object>(0);
			mapaValorEndoso.put("pv_cdunieco_i" , cdunieco);
			mapaValorEndoso.put("pv_cdramo_i"   , cdramo);
			mapaValorEndoso.put("pv_estado_i"   , estado);
			mapaValorEndoso.put("pv_nmpoliza_i" , nmpoliza);
			mapaValorEndoso.put("pv_nmsituac_i" , "1");
			mapaValorEndoso.put("pv_nmsuplem_i" , nmsuplem);
			mapaValorEndoso.put("pv_feinival_i" , dFechaEndoso);
			mapaValorEndoso.put("pv_cdtipsup_i" , cdtipsup);
			endososManager.calcularValorEndoso(mapaValorEndoso);
			////// valor endoso //////
			//////////////////////////
			
			String tramiteGenerado=this.confirmarEndoso(
					cdunieco,
					cdramo,
					estado,
					nmpoliza,
					nmsuplem,
					nsuplogi,
					cdtipsup,
					"",
					dFechaEndoso,
					cdtipsit
					);
		    
			if(tramiteGenerado==null||tramiteGenerado.length()==0)
			{
				///////////////////////////////////////
			    ////// re generar los documentos //////
			    /*///////////////////////////////////*/
			    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
				paramsGetDoc.put("pv_cdunieco_i" , cdunieco);
				paramsGetDoc.put("pv_cdramo_i"   , cdramo);
				paramsGetDoc.put("pv_estado_i"   , estado);
				paramsGetDoc.put("pv_nmpoliza_i" , nmpoliza);
				paramsGetDoc.put("pv_nmsuplem_i" , nmsuplem);
				paramsGetDoc.put("pv_tipmov_i"   , TipoEndoso.CAMBIO_DOMICILIO_ASEGURADO_TITULAR.getCdTipSup().toString());
			    List<Map<String,String>>listaDocu=endososManager.reimprimeDocumentos(paramsGetDoc);
			    log.debug("documentos que se regeneran: "+listaDocu);
			    
			    String rutaCarpeta=this.getText("ruta.documentos.poliza")+"/"+ntramite;
			    
				//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
				for(Map<String,String> docu:listaDocu)
				{
					log.debug("docu iterado: "+docu);
					String nmsolici=docu.get("nmsolici");
					//String nmsituac=docu.get("nmsituac");
					String descripc=docu.get("descripc");
					String descripl=docu.get("descripl");
					String url=this.getText("ruta.servidor.reports")
							+ "?destype=cache"
							+ "&desformat=PDF"
							+ "&userid="+this.getText("pass.servidor.reports")
							+ "&report="+descripl
							+ "&paramform=no"
							+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
							+ "&p_unieco="+cdunieco
							+ "&p_ramo="+cdramo
							+ "&p_estado="+estado
							+ "&p_poliza="+nmpoliza
							+ "&p_suplem="+nmsuplem
							+ "&desname="+rutaCarpeta+"/"+descripc;
					if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
					{
						// C R E D E N C I A L _ X X X X X X . P D F
						//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
						url+="&p_cdperson="+descripc.substring(11, descripc.lastIndexOf("_"));
					}
					log.debug(""
							+ "\n#################################"
							+ "\n###### Se solicita reporte ######"
							+ "\na "+url+""
							+ "\n#################################");
					HttpUtil.generaArchivo(url,rutaCarpeta+"/"+descripc);
					log.debug(""
							+ "\n######                    ######"
							+ "\n###### reporte solicitado ######"
							+ "\na "+url+""
							+ "\n################################"
							+ "\n################################"
							+ "");
				}
			    /*///////////////////////////////////*/
				////// re generar los documentos //////
			    ///////////////////////////////////////

				ejecutaWSclienteSaludEndoso(cdunieco, cdramo, estado, nmpoliza, nmsuplem, "ACTUALIZA");
				
				String cdtipsitGS = "213";
				String sucursal = cdunieco;
				if(StringUtils.isNotBlank(sucursal) && "1".equals(sucursal)) sucursal = "1000";
				
				String nmsolici = listaDocu.get(0).get("nmsolici");
				String nmtramite = ntramite;
				
				String tipomov = TipoEndoso.CAMBIO_DOMICILIO_ASEGURADO_TITULAR.getCdTipSup().toString();
				
				ejecutaWSrecibosEndoso(cdunieco, cdramo,
						estado, nmpoliza,
						nmsuplem, nsuplogi, rutaCarpeta,
						cdtipsitGS, sucursal, nmsolici, nmtramite,
						true, "INSERTA", tipomov );
				
			    mensaje="Se ha guardado el endoso "+nsuplogi;
			    
			}
			else
			{
				mensaje="El endoso "+nsuplogi
						+" se guard&oacute; en mesa de control para autorizaci&oacute;n "
						+ "con n&uacute;mero de tr&aacute;mite "+tramiteGenerado;
			}
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar los datos de endoso de domicilio full",ex);
			success=false;
			error=ex.getMessage();
		}
		
		log.debug("\n"
				+ "\n######                            ######"
				+ "\n###### guardarEndosoDomicilioFull ######"
				+ "\n########################################"
				+ "\n########################################"
				);
		return SUCCESS;
	}
	/*////////////////////////////////////*/
	////// guardarEndosoDomicilioFull //////
	////////////////////////////////////////
	
	private String validaEndosoAnterior(String cdunieco,String cdramo,String estado,String nmpoliza,String cdtipsup)
	{
		String respuesta=ERROR;
		try
		{
			Map<String,String>params=new HashMap<String,String>();
			params.put("pv_cdunieco_i" , cdunieco);
			params.put("pv_cdramo_i"   , cdramo);
			params.put("pv_estado_i"   , estado);
			params.put("pv_nmpoliza_i" , nmpoliza);
			params.put("pv_cdtipsup_i" , cdtipsup);
			endososManager.validaEndosoAnterior(params);
			respuesta=SUCCESS;
		}
		catch(Exception ex)
		{
			log.error("error tratando de acceder a pantalla de endoso: "+cdtipsup,ex);
			error=ex.getMessage();
			respuesta=ERROR;
		}
		return respuesta;
	}
	
	/////////////////////////////
	////// endosoDeducible //////
	/*
	smap1:
		CDRAMO: "2"
		CDTIPSIT: "SL"
		CDUNIECO: "1006"
		DSCOMENT: ""
		DSTIPSIT: "SALUD VITAL"
		ESTADO: "M"
		FEEMISIO: "22/01/2014"
		FEINIVAL: "22/01/2014"
		NMPOLIEX: "1006213000024000000"
		NMPOLIZA: "24"
		NMSUPLEM: "245668019180000000"
		NSUPLOGI: "1"
		NTRAMITE: "678"
		PRIMA_TOTAL: "12207.37"
	smap2:
		masdeducible: "si"
	*/
	/*/////////////////////////*/
	public String endosoDeducible()
	{
		this.session=ActionContext.getContext().getSession();
		log.debug("\n"
				+ "\n#############################"
				+ "\n#############################"
				+ "\n###### endosoDeducible ######"
				+ "\n######                 ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("smap2: "+smap2);
		
		String cdunieco = smap1.get("CDUNIECO");
		String cdramo   = smap1.get("CDRAMO");
		String estado   = smap1.get("ESTADO");
		String nmpoliza = smap1.get("NMPOLIZA");
		String cdtipsit = smap1.get("CDTIPSIT");
		String cdtipsup = smap2.get("masdeducible").equalsIgnoreCase("si")?
				TipoEndoso.DEDUCIBLE_MAS.getCdTipSup().toString():
				TipoEndoso.DEDUCIBLE_MENOS.getCdTipSup().toString();
				
		UserVO usuario    = (UserVO)session.get("USUARIO");
		String cdelemento = usuario.getEmpresa().getElementoId();
		String cdusuari   = usuario.getUser();
		String rol        = usuario.getRolActivo().getObjeto().getValue();
		
		String respuesta=this.validaEndosoAnterior(cdunieco, cdramo, estado, nmpoliza, cdtipsup);
		
		String nmsituacTitular   = "1";
		String llaveDeducible    = "";
		String cdatribuDeducible = "";
		
		String nombreItemDeducibleOriginal = "DEDUCIBLE ORIGINAL";
		String nombreItemNuevoDeducible    = "NUEVO DEDUCIBLE";
		
		String llaveItemDeducibleOriginal = "itemDeducibleLectura";
		String llaveItemNuevoDeducible    = "itemDeducible";
		String llavePanelLectura          = "itemsLectura";
		
		String pantalla = "ENDOSO_DEDUCIBLE";
		
		if(cdtipsit.equals("SL")||cdtipsit.equals("SN"))
		{
			llaveDeducible    = "otvalor05";
			cdatribuDeducible = "5";
		}
		
		if(respuesta.equals(SUCCESS))
		{
			try
			{
				Map<String,Object>valositTitular=kernelManager.obtieneValositSituac(cdunieco,cdramo,estado,nmpoliza,nmsituacTitular);
				if(llaveDeducible.length()>0
						&&valositTitular.containsKey(llaveDeducible)
						&&((String)valositTitular.get(llaveDeducible))!=null)
				{
					String deducible=(String)valositTitular.get(llaveDeducible);
					log.debug("deducible de la poliza: "+deducible);
					smap1.put("deducible"    , deducible);
					smap1.put("masdeducible" , smap2.get("masdeducible"));
				}
				else
				{
					throw new Exception("No hay deducible definido para este producto");
				}
				
				List<Tatri>tatrisit = kernelManager.obtenerTatrisit(cdtipsit);
				List<Tatri>temp     = new ArrayList<Tatri>();
				for(Tatri tatrisitIte:tatrisit)
				{
					if(tatrisitIte.getCdatribu().equalsIgnoreCase(cdatribuDeducible))
					{
						temp.add(tatrisitIte);
					}
				}
				tatrisit=temp;
				
				GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gc.setCdtipsit(cdtipsit);
				
				imap1=new HashMap<String,Item>();
				tatrisit.get(0).setDsatribu(nombreItemNuevoDeducible);
				
				gc.generaParcial(tatrisit);
				
				imap1.put(llaveItemNuevoDeducible,gc.getItems());
				
				tatrisit.get(0).setReadOnly(true);
				tatrisit.get(0).setDsatribu(nombreItemDeducibleOriginal);
				
				gc.generaParcial(tatrisit);
				
				imap1.put(llaveItemDeducibleOriginal,gc.getItems());
				
				gc.generaParcial(pantallasManager.obtenerCamposPantalla(
						cdunieco  , cdramo
						,cdtipsit , estado
						,nmpoliza , null
						,pantalla , rol
						,null     , "PANEL_LECTURA"));
				
				imap1.put(llavePanelLectura,gc.getItems());
				
			}
			catch(Exception ex)
			{
				log.error("error al mostrar pantalla endoso deducible",ex);
				error=ex.getMessage();
				respuesta=ERROR;
			}
		}
		
		log.debug("\n"
				+ "\n######                 ######"
				+ "\n###### endosoDeducible ######"
				+ "\n#############################"
				+ "\n#############################"
				);
		return respuesta;
	}
	/*/////////////////////////*/
	////// endosoDeducible //////
	/////////////////////////////
	
	////////////////////////////////////
	////// guardarEndosoDeducible //////
	/*
	smap1:
		NMSUPLEM=245668111370000000,
		DSTIPSIT=SALUD VITAL,
		FEINIVAL=23/01/2014,
		NMPOLIZA=24,
		deducible=10000,
		PRIMA_TOTAL=16039.29,
		NMPOLIEX=1006213000024000000,
		NSUPLOGI=2,
		DSCOMENT=,
		ESTADO=M,
		masdeducible=si,
		CDTIPSIT=SL,
		NTRAMITE=678,
		CDUNIECO=1006,
		FEEMISIO=22/01/2014,
		CDRAMO=2
	smap2:
		deducible=10000,
		fecha_endoso=23/01/2014
	*/
	/*////////////////////////////////*/
	public String guardarEndosoDeducible()
	{
		this.session=ActionContext.getContext().getSession();
		log.debug("\n"
				+ "\n####################################"
				+ "\n####################################"
				+ "\n###### guardarEndosoDeducible ######"
				+ "\n######                        ######"
				);
		log.debug("smap1:"+smap1);
		log.debug("smap2:"+smap2);
		
		try
		{
			UserVO usuario    = (UserVO)session.get("USUARIO");
			String cdunieco   = smap1.get("CDUNIECO");
			String cdramo     = smap1.get("CDRAMO");
			String estado     = smap1.get("ESTADO");
			String nmpoliza   = smap1.get("NMPOLIZA");
			String fecha      = smap2.get("fecha_endoso");
			Date   dFecha     = renderFechas.parse(fecha);
			String cdelemento = usuario.getEmpresa().getElementoId();
			String cdusuari   = usuario.getUser();
			String proceso    = "END";
			String cdtipsup   = smap1.get("masdeducible").equalsIgnoreCase("si")?
					TipoEndoso.DEDUCIBLE_MAS.getCdTipSup().toString():
					TipoEndoso.DEDUCIBLE_MENOS.getCdTipSup().toString();
			String deducible  = smap2.get("deducible");
			String cdtipsit   = smap1.get("CDTIPSIT");
			String ntramite   = smap1.get("NTRAMITE");
			
			//PKG_ENDOSOS.P_ENDOSO_INICIA
			Map<String,String>resIniEnd=endososManager.iniciarEndoso(cdunieco, cdramo, estado, nmpoliza, fecha, cdelemento, cdusuari, proceso, cdtipsup);
			
			String nmsuplem = resIniEnd.get("pv_nmsuplem_o");
			String nsuplogi = resIniEnd.get("pv_nsuplogi_o");
			
			//PKG_ENDOSOS.P_INS_NEW_DEDUCIBLE_TVALOSIT
			endososManager.actualizaDeducibleValosit(cdunieco, cdramo, estado, nmpoliza, nmsuplem, deducible);
			
			//////////////////////////////
            ////// inserta tworksup //////
			//PKG_SATELITES.P_INSERTA_TWORKSUP_SIT_TODAS
            Map<String,String>mapaTworksupEnd=new LinkedHashMap<String,String>(0);
            mapaTworksupEnd.put("pv_cdunieco_i" , cdunieco);
            mapaTworksupEnd.put("pv_cdramo_i"   , cdramo);
            mapaTworksupEnd.put("pv_estado_i"   , estado);
            mapaTworksupEnd.put("pv_nmpoliza_i" , nmpoliza);
            mapaTworksupEnd.put("pv_cdtipsup_i" , cdtipsup);
            mapaTworksupEnd.put("pv_nmsuplem_i" , nmsuplem);
            endososManager.insertarTworksupSitTodas(mapaTworksupEnd);
            ////// inserta tworksup //////
            //////////////////////////////
            
            //////////////////////////
            ////// tarificacion //////
            //PKG_COTIZA.P_EJECUTA_SIGSVALIPOL_END
            Map<String,String>mapaSigsvalipolEnd=new LinkedHashMap<String,String>(0);
			mapaSigsvalipolEnd.put("pv_cdusuari_i" , cdusuari);
			mapaSigsvalipolEnd.put("pv_cdelemen_i" , cdelemento);
			mapaSigsvalipolEnd.put("pv_cdunieco_i" , cdunieco);
			mapaSigsvalipolEnd.put("pv_cdramo_i"   , cdramo);
			mapaSigsvalipolEnd.put("pv_estado_i"   , estado);
			mapaSigsvalipolEnd.put("pv_nmpoliza_i" , nmpoliza);
			mapaSigsvalipolEnd.put("pv_nmsituac_i" , "0");
			mapaSigsvalipolEnd.put("pv_nmsuplem_i" , nmsuplem);
			mapaSigsvalipolEnd.put("pv_cdtipsit_i" , cdtipsit);
			mapaSigsvalipolEnd.put("pv_cdtipsup_i" , cdtipsup);
			endososManager.sigsvalipolEnd(mapaSigsvalipolEnd);
            ////// tarificacion //////
			//////////////////////////
			
			//////////////////////////
			////// valor endoso //////
			//PKG_ENDOSOS.P_CALC_VALOR_ENDOSO
			Map<String,Object>mapaValorEndoso=new LinkedHashMap<String,Object>(0);
			mapaValorEndoso.put("pv_cdunieco_i" , cdunieco);
			mapaValorEndoso.put("pv_cdramo_i"   , cdramo);
			mapaValorEndoso.put("pv_estado_i"   , estado);
			mapaValorEndoso.put("pv_nmpoliza_i" , nmpoliza);
			mapaValorEndoso.put("pv_nmsituac_i" , "1");
			mapaValorEndoso.put("pv_nmsuplem_i" , nmsuplem);
			mapaValorEndoso.put("pv_feinival_i" , dFecha);
			mapaValorEndoso.put("pv_cdtipsup_i" , cdtipsup);
			endososManager.calcularValorEndoso(mapaValorEndoso);
			////// valor endoso //////
			//////////////////////////
			
			//+- 30 dias ? PKG_SATELITES.P_MOV_MESACONTROL : PKG_ENDOSOS.P_CONFIRMAR_ENDOSOB
			String tramiteGenerado=this.confirmarEndoso(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nsuplogi, cdtipsup, "", dFecha, cdtipsit);
			
			if(tramiteGenerado==null||tramiteGenerado.length()==0)
			{
				//PKG_CONSULTA.P_reImp_documentos
				String nmsolici = this.regeneraDocumentos(cdunieco, cdramo, estado, nmpoliza, nmsuplem, cdtipsup, ntramite);
				
				String cdtipsitGS = "213";
				String sucursal = cdunieco;
				if(StringUtils.isNotBlank(sucursal) && "1".equals(sucursal)) sucursal = "1000";
				
				ejecutaWSrecibosEndoso(cdunieco, cdramo,
						estado, nmpoliza,
						nmsuplem, nsuplogi, null,
						cdtipsitGS, sucursal, nmsolici, ntramite,
						true, "INSERTA", cdtipsup );
				
				mensaje="Se ha guardado el endoso "+nsuplogi;
			}
			else
			{
				mensaje="El endoso "+nsuplogi
						+" se guard&oacute; en mesa de control para autorizaci&oacute;n "
						+ "con n&uacute;mero de tr&aacute;mite "+tramiteGenerado;
			}
			
			success=true;
		}
		catch(Exception ex)
		{
			error=ex.getMessage();
			success=false;
			log.error("error al guardar endoso de deducible",ex);
		}
		
		log.debug("\n"
				+ "\n######                        ######"
				+ "\n###### guardarEndosoDeducible ######"
				+ "\n####################################"
				+ "\n####################################"
				);
		return SUCCESS;
	}
	/*////////////////////////////////*/
	////// guardarEndosoDeducible //////
	////////////////////////////////////
	
	//////////////////////////
	////// endosoCopago //////
	/*
	smap1:
		CDRAMO: "2"
		CDTIPSIT: "SL"
		CDUNIECO: "1006"
		DSCOMENT: ""
		DSTIPSIT: "SALUD VITAL"
		ESTADO: "M"
		FEEMISIO: "22/01/2014"
		FEINIVAL: "22/01/2014"
		NMPOLIEX: "1006213000024000000"
		NMPOLIZA: "24"
		NMSUPLEM: "245668019180000000"
		NSUPLOGI: "1"
		NTRAMITE: "678"
		PRIMA_TOTAL: "12207.37"
	smap2:
		mascopago: "si"
	*/
	/*//////////////////////*/
	public String endosoCopago()
	{
		this.session=ActionContext.getContext().getSession();
		log.debug("\n"
				+ "\n##########################"
				+ "\n##########################"
				+ "\n###### endosoCopago ######"
				+ "\n######              ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("smap2: "+smap2);
		
		String cdunieco = smap1.get("CDUNIECO");
		String cdramo   = smap1.get("CDRAMO");
		String estado   = smap1.get("ESTADO");
		String nmpoliza = smap1.get("NMPOLIZA");
		String cdtipsit = smap1.get("CDTIPSIT");
		String cdtipsup = smap2.get("mascopago").equalsIgnoreCase("si")?
				TipoEndoso.COPAGO_MAS.getCdTipSup().toString():
				TipoEndoso.COPAGO_MENOS.getCdTipSup().toString();
				
		UserVO usuario    = (UserVO)session.get("USUARIO");
		String cdelemento = usuario.getEmpresa().getElementoId();
		String cdusuari   = usuario.getUser();
		String rol        = usuario.getRolActivo().getObjeto().getValue();
		
		String respuesta=this.validaEndosoAnterior(cdunieco, cdramo, estado, nmpoliza, cdtipsup);
		
		String nmsituacTitular = "1";
		String llaveCopago     = "";
		String cdatribuCopago  = "";
		
		String nombreItemCopagoOriginal = "COPAGO ORIGINAL";
		String nombreItemNuevoCopago    = "NUEVO COPAGO";
		
		String llaveItemCopagoOriginal = "itemCopagoLectura";
		String llaveItemNuevoCopago    = "itemCopago";
		String llavePanelLectura          = "itemsLectura";
		
		String pantalla = "ENDOSO_COPAGO";
		
		if(cdtipsit.equals("SL")||cdtipsit.equals("SN"))
		{
			llaveCopago    = "otvalor06";
			cdatribuCopago = "6";
		}
		
		if(respuesta.equals(SUCCESS))
		{
			try
			{
				Map<String,Object>valositTitular=kernelManager.obtieneValositSituac(cdunieco,cdramo,estado,nmpoliza,nmsituacTitular);
				if(llaveCopago.length()>0
						&&valositTitular.containsKey(llaveCopago)
						&&((String)valositTitular.get(llaveCopago))!=null)
				{
					String copago=(String)valositTitular.get(llaveCopago);
					log.debug("copago de la poliza: "+copago);
					smap1.put("copago"    , copago);
					smap1.put("mascopago" , smap2.get("mascopago"));
				}
				else
				{
					throw new Exception("No hay copago definido para este producto");
				}
				
				List<Tatri>tatrisit = kernelManager.obtenerTatrisit(cdtipsit);
				List<Tatri>temp     = new ArrayList<Tatri>();
				for(Tatri tatrisitIte:tatrisit)
				{
					if(tatrisitIte.getCdatribu().equalsIgnoreCase(cdatribuCopago))
					{
						temp.add(tatrisitIte);
					}
				}
				tatrisit=temp;
				
				GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gc.setCdtipsit(cdtipsit);
				
				imap1=new HashMap<String,Item>();
				tatrisit.get(0).setDsatribu(nombreItemNuevoCopago);
				
				gc.generaParcial(tatrisit);
				
				imap1.put(llaveItemNuevoCopago,gc.getItems());
				
				tatrisit.get(0).setReadOnly(true);
				tatrisit.get(0).setDsatribu(nombreItemCopagoOriginal);
				
				gc.generaParcial(tatrisit);
				
				imap1.put(llaveItemCopagoOriginal,gc.getItems());
				
				gc.generaParcial(pantallasManager.obtenerCamposPantalla(
						cdunieco  , cdramo
						,cdtipsit , estado
						,nmpoliza , null
						,pantalla , rol
						,null     , "PANEL_LECTURA"));
				
				imap1.put(llavePanelLectura,gc.getItems());
				
			}
			catch(Exception ex)
			{
				log.error("error al mostrar pantalla endoso copago",ex);
				error=ex.getMessage();
				respuesta=ERROR;
			}
		}
		
		log.debug("\n"
				+ "\n######              ######"
				+ "\n###### endosoCopago ######"
				+ "\n##########################"
				+ "\n##########################"
				);
		return respuesta;
	}
	/*//////////////////////*/
	////// endosoCopago //////
	//////////////////////////
	
	/////////////////////////////////
	////// guardarEndosoCopago //////
	/*
	smap1:
		NMSUPLEM=245668111370000000,
		DSTIPSIT=SALUD VITAL,
		FEINIVAL=23/01/2014,
		NMPOLIZA=24,
		copago=10000,
		PRIMA_TOTAL=16039.29,
		NMPOLIEX=1006213000024000000,
		NSUPLOGI=2,
		DSCOMENT=,
		ESTADO=M,
		mascopago=si,
		CDTIPSIT=SL,
		NTRAMITE=678,
		CDUNIECO=1006,
		FEEMISIO=22/01/2014,
		CDRAMO=2
	smap2:
		copago=10000,
		fecha_endoso=23/01/2014
	*/
	/*////////////////////////////////*/
	public String guardarEndosoCopago()
	{
		this.session=ActionContext.getContext().getSession();
		log.debug("\n"
				+ "\n#################################"
				+ "\n#################################"
				+ "\n###### guardarEndosoCopago ######"
				+ "\n######                     ######"
				);
		log.debug("smap1:"+smap1);
		log.debug("smap2:"+smap2);
		
		try
		{
			UserVO usuario    = (UserVO)session.get("USUARIO");
			String cdunieco   = smap1.get("CDUNIECO");
			String cdramo     = smap1.get("CDRAMO");
			String estado     = smap1.get("ESTADO");
			String nmpoliza   = smap1.get("NMPOLIZA");
			String fecha      = smap2.get("fecha_endoso");
			Date   dFecha     = renderFechas.parse(fecha);
			String cdelemento = usuario.getEmpresa().getElementoId();
			String cdusuari   = usuario.getUser();
			String proceso    = "END";
			String cdtipsup   = smap1.get("mascopago").equalsIgnoreCase("si")?
					TipoEndoso.COPAGO_MAS.getCdTipSup().toString():
					TipoEndoso.COPAGO_MENOS.getCdTipSup().toString();
			String copago     = smap2.get("copago");
			String cdtipsit   = smap1.get("CDTIPSIT");
			String ntramite   = smap1.get("NTRAMITE");
			
			//PKG_ENDOSOS.P_ENDOSO_INICIA
			Map<String,String>resIniEnd=endososManager.iniciarEndoso(cdunieco, cdramo, estado, nmpoliza, fecha, cdelemento, cdusuari, proceso, cdtipsup);
			
			String nmsuplem = resIniEnd.get("pv_nmsuplem_o");
			String nsuplogi = resIniEnd.get("pv_nsuplogi_o");
			
			//PKG_ENDOSOS.P_INS_NEW_COPAGO_TVALOSIT
			endososManager.actualizaCopagoValosit(cdunieco, cdramo, estado, nmpoliza, nmsuplem, copago);
			
			//////////////////////////////
            ////// inserta tworksup //////
			//PKG_SATELITES.P_INSERTA_TWORKSUP_SIT_TODAS
            Map<String,String>mapaTworksupEnd=new LinkedHashMap<String,String>(0);
            mapaTworksupEnd.put("pv_cdunieco_i" , cdunieco);
            mapaTworksupEnd.put("pv_cdramo_i"   , cdramo);
            mapaTworksupEnd.put("pv_estado_i"   , estado);
            mapaTworksupEnd.put("pv_nmpoliza_i" , nmpoliza);
            mapaTworksupEnd.put("pv_cdtipsup_i" , cdtipsup);
            mapaTworksupEnd.put("pv_nmsuplem_i" , nmsuplem);
            endososManager.insertarTworksupSitTodas(mapaTworksupEnd);
            ////// inserta tworksup //////
            //////////////////////////////
            
            //////////////////////////
            ////// tarificacion //////
            //PKG_COTIZA.P_EJECUTA_SIGSVALIPOL_END
            Map<String,String>mapaSigsvalipolEnd=new LinkedHashMap<String,String>(0);
			mapaSigsvalipolEnd.put("pv_cdusuari_i" , cdusuari);
			mapaSigsvalipolEnd.put("pv_cdelemen_i" , cdelemento);
			mapaSigsvalipolEnd.put("pv_cdunieco_i" , cdunieco);
			mapaSigsvalipolEnd.put("pv_cdramo_i"   , cdramo);
			mapaSigsvalipolEnd.put("pv_estado_i"   , estado);
			mapaSigsvalipolEnd.put("pv_nmpoliza_i" , nmpoliza);
			mapaSigsvalipolEnd.put("pv_nmsituac_i" , "0");
			mapaSigsvalipolEnd.put("pv_nmsuplem_i" , nmsuplem);
			mapaSigsvalipolEnd.put("pv_cdtipsit_i" , cdtipsit);
			mapaSigsvalipolEnd.put("pv_cdtipsup_i" , cdtipsup);
			endososManager.sigsvalipolEnd(mapaSigsvalipolEnd);
            ////// tarificacion //////
			//////////////////////////
			
			//////////////////////////
			////// valor endoso //////
			//PKG_ENDOSOS.P_CALC_VALOR_ENDOSO
			Map<String,Object>mapaValorEndoso=new LinkedHashMap<String,Object>(0);
			mapaValorEndoso.put("pv_cdunieco_i" , cdunieco);
			mapaValorEndoso.put("pv_cdramo_i"   , cdramo);
			mapaValorEndoso.put("pv_estado_i"   , estado);
			mapaValorEndoso.put("pv_nmpoliza_i" , nmpoliza);
			mapaValorEndoso.put("pv_nmsituac_i" , "1");
			mapaValorEndoso.put("pv_nmsuplem_i" , nmsuplem);
			mapaValorEndoso.put("pv_feinival_i" , dFecha);
			mapaValorEndoso.put("pv_cdtipsup_i" , cdtipsup);
			endososManager.calcularValorEndoso(mapaValorEndoso);
			////// valor endoso //////
			//////////////////////////
			
			//+- 30 dias ? PKG_SATELITES.P_MOV_MESACONTROL : PKG_ENDOSOS.P_CONFIRMAR_ENDOSOB
			String tramiteGenerado=this.confirmarEndoso(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nsuplogi, cdtipsup, "", dFecha, cdtipsit);
			
			if(tramiteGenerado==null||tramiteGenerado.length()==0)
			{
				//PKG_CONSULTA.P_reImp_documentos
				String nmsolici = this.regeneraDocumentos(cdunieco, cdramo, estado, nmpoliza, nmsuplem, cdtipsup, ntramite);
				
				String cdtipsitGS = "213";
				String sucursal = cdunieco;
				if(StringUtils.isNotBlank(sucursal) && "1".equals(sucursal)) sucursal = "1000";
				
				ejecutaWSrecibosEndoso(cdunieco, cdramo,
						estado, nmpoliza,
						nmsuplem, nsuplogi, null,
						cdtipsitGS, sucursal, nmsolici, ntramite,
						true, "INSERTA", cdtipsup );
				
				mensaje="Se ha guardado el endoso "+nsuplogi;
			}
			else
			{
				mensaje="El endoso "+nsuplogi
						+" se guard&oacute; en mesa de control para autorizaci&oacute;n "
						+ "con n&uacute;mero de tr&aacute;mite "+tramiteGenerado;
			}
			
			success=true;
		}
		catch(Exception ex)
		{
			error=ex.getMessage();
			success=false;
			log.error("error al guardar endoso de copago",ex);
		}
		
		log.debug("\n"
				+ "\n######                     ######"
				+ "\n###### guardarEndosoCopago ######"
				+ "\n#################################"
				+ "\n#################################"
				);
		return SUCCESS;
	}
	/*/////////////////////////////*/
	////// guardarEndosoCopago //////
	/////////////////////////////////
	
	/////////////////////////////////
	////// regenera documentos //////
	/*/////////////////////////////*/
	private String regeneraDocumentos(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsup
			,String ntramite) throws Exception
	{
		String nmsolici = null;
		///////////////////////////////////////
	    ////// re generar los documentos //////
	    /*///////////////////////////////////*/
	    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
		paramsGetDoc.put("pv_cdunieco_i" , cdunieco);
		paramsGetDoc.put("pv_cdramo_i"   , cdramo);
		paramsGetDoc.put("pv_estado_i"   , estado);
		paramsGetDoc.put("pv_nmpoliza_i" , nmpoliza);
		paramsGetDoc.put("pv_nmsuplem_i" , nmsuplem);
		paramsGetDoc.put("pv_tipmov_i"   , cdtipsup);
		
	    List<Map<String,String>>listaDocu=endososManager.reimprimeDocumentos(paramsGetDoc);
	    log.debug("documentos que se regeneran: "+listaDocu);
	    
	    String rutaCarpeta=this.getText("ruta.documentos.poliza")+"/"+ntramite;
	    
		//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
		for(Map<String,String> docu:listaDocu)
		{
			log.debug("docu iterado: "+docu);
			nmsolici = docu.get("nmsolici");
			String descripc=docu.get("descripc");
			String descripl=docu.get("descripl");
			String url=this.getText("ruta.servidor.reports")
					+ "?destype=cache"
					+ "&desformat=PDF"
					+ "&userid="+this.getText("pass.servidor.reports")
					+ "&report="+descripl
					+ "&paramform=no"
					+ "&ACCESSIBLE=YES" //parametro que habilita salida en PDF
					+ "&p_unieco="+cdunieco
					+ "&p_ramo="+cdramo
					+ "&p_estado="+estado
					+ "&p_poliza="+nmpoliza
					+ "&p_suplem="+nmsuplem
					+ "&desname="+rutaCarpeta+"/"+descripc;
			if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
			{
				// C R E D E N C I A L _ X X X X X X . P D F
				//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
				url+="&p_cdperson="+descripc.substring(11, descripc.lastIndexOf("_"));
			}
			log.debug(""
					+ "\n#################################"
					+ "\n###### Se solicita reporte ######"
					+ "\na "+url+""
					+ "\n#################################");
			HttpUtil.generaArchivo(url,rutaCarpeta+"/"+descripc);
			log.debug(""
					+ "\n######                    ######"
					+ "\n###### reporte solicitado ######"
					+ "\na "+url+""
					+ "\n################################"
					+ "\n################################"
					+ "");
		}
		
		return nmsolici;
	    /*///////////////////////////////////*/
		////// re generar los documentos //////
	    ///////////////////////////////////////
	}
	/*/////////////////////////////*/
	////// regenera documentos //////
	/////////////////////////////////
	
	////////////////////////////////
	////// endosoReexpedicion //////
	/*
	smap1:
		CDRAMO: "2"
		CDTIPSIT: "SL"
		CDUNIECO: "1006"
		DSCOMENT: ""
		DSTIPSIT: "SALUD VITAL"
		ESTADO: "M"
		FEEMISIO: "22/01/2014"
		FEINIVAL: "22/01/2014"
		NMPOLIEX: "1006213000024000000"
		NMPOLIZA: "24"
		NMSUPLEM: "245668019180000000"
		NSUPLOGI: "1"
		NTRAMITE: "678"
		PRIMA_TOTAL: "12207.37"
	*/
	/*////////////////////////////*/
	public String endosoReexpedicion()
	{
		this.session=ActionContext.getContext().getSession();
		log.debug("\n"
				+ "\n################################"
				+ "\n################################"
				+ "\n###### endosoReexpedicion ######"
				+ "\n######                    ######"
				);
		log.debug("smap1: "+smap1);
		
		String cdunieco = smap1.get("CDUNIECO");
		String cdramo   = smap1.get("CDRAMO");
		String estado   = smap1.get("ESTADO");
		String nmpoliza = smap1.get("NMPOLIZA");
		String cdtipsit = smap1.get("CDTIPSIT");
		String cdtipsup = TipoEndoso.CANCELACION_POR_REEXPEDICION.getCdTipSup().toString();
		
		String cdPantalla           = "ENDOSO_REEXPEDICION";
		String cdPanelLectura       = "PANEL_LECTURA";
		String keyItemsPanelLectura = "itemsPanelLectura";
				
		UserVO usuario    = (UserVO)session.get("USUARIO");
		String cdelemento = usuario.getEmpresa().getElementoId();
		String cdusuari   = usuario.getUser();
		String rol        = usuario.getRolActivo().getObjeto().getValue();
		
		String respuesta=this.validaEndosoAnterior(cdunieco, cdramo, estado, nmpoliza, cdtipsup);
		
		if(respuesta.equals(SUCCESS))
		{
			try
			{	
				GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				imap1=new HashMap<String,Item>();
				
				gc.generaParcial(pantallasManager.obtenerCamposPantalla(
						cdunieco    , cdramo
						,cdtipsit   , estado
						,nmpoliza   , null
						,cdPantalla , rol
						,null       , cdPanelLectura));
				
				imap1.put(keyItemsPanelLectura,gc.getItems());				
				
			}
			catch(Exception ex)
			{
				log.error("error al mostrar pantalla endoso reexpedicion",ex);
				error=ex.getMessage();
				respuesta=ERROR;
			}
		}
		
		log.debug("\n"
				+ "\n######                    ######"
				+ "\n###### endosoReexpedicion ######"
				+ "\n################################"
				+ "\n################################"
				);
		return respuesta;
	}
	/*////////////////////////////*/
	////// endosoReexpedicion //////
	////////////////////////////////
	
	///////////////////////////////
	////// getters y setters //////
	/*///////////////////////////*/
	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setEndososManager(EndososManager endososManager) {
		this.endososManager = endososManager;
	}

	public Item getItem1() {
		return item1;
	}

	public void setItem1(Item item1) {
		this.item1 = item1;
	}

	public Item getItem2() {
		return item2;
	}

	public void setItem2(Item item2) {
		this.item2 = item2;
	}

	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}

	public Map<String, Object> getOmap1() {
		return omap1;
	}

	public void setOmap1(Map<String, Object> omap1) {
		this.omap1 = omap1;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Map<String, String> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, String> parametros) {
		this.parametros = parametros;
	}

	public Map<String, String> getSmap2() {
		return smap2;
	}

	public void setSmap2(Map<String, String> smap2) {
		this.smap2 = smap2;
	}

	public List<Map<String, String>> getSlist2() {
		return slist2;
	}

	public void setSlist2(List<Map<String, String>> slist2) {
		this.slist2 = slist2;
	}

	public Item getItem3() {
		return item3;
	}

	public void setItem3(Item item3) {
		this.item3 = item3;
	}

	public Map<String, Item> getImap1() {
		return imap1;
	}

	public void setImap1(Map<String, Item> imap1) {
		this.imap1 = imap1;
	}

	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}
	
	public void setIce2sigsWebServices(Ice2sigsWebServices ice2sigsWebServices) {
		this.ice2sigsWebServices = ice2sigsWebServices;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Map<String, String> getSmap3() {
		return smap3;
	}

	public void setSmap3(Map<String, String> smap3) {
		this.smap3 = smap3;
	}
	
}