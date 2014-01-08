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
		log.debug(""
				+ "\n######                          ######"
				+ "\n###### pantallaEndosoCoberturas ######"
				+ "\n######################################"
				+ "\n######################################"
				);
		return SUCCESS;
	}
	/*//////////////////////////////////////////*/
	////// pantalla de endoso de coberturas //////
	//////////////////////////////////////////////

	//////////////////////////////////////////////
	////// pantalla de endoso de domicilio  //////
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
		ComplementariosCoberturasAction actionDomicilio=new ComplementariosCoberturasAction();
		actionDomicilio.setSession(session);
		actionDomicilio.setSmap1(smap1);
		actionDomicilio.setKernelManager(kernelManager);
		actionDomicilio.mostrarPantallaDomicilio();
		item1=actionDomicilio.getItem1();
		item2=actionDomicilio.getItem2();
		log.debug(""
				+ "\n######                         ######"
				+ "\n###### pantallaEndosoDomicilio ######"
				+ "\n#####################################"
				+ "\n#####################################"
				);
		return SUCCESS;
	}
	/*//////////////////////////////////////////*/
	////// pantalla de endoso de domicilio  //////
	//////////////////////////////////////////////
	
	///////////////////////////////////////////
	////// pantalla de endoso de nombres //////
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
		log.debug(""
				+ "\n######                       ######"
				+ "\n###### pantallaEndosoNombres ######"
				+ "\n###################################"
				+ "\n###################################"
				);
		return SUCCESS;
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
			omap1.put("pv_cdtipsup_i" , "2");
			
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
			
			Map<String,String>paramConfirmarEndosoB=new LinkedHashMap<String,String>(0);
			paramConfirmarEndosoB.put("pv_cdunieco_i" , (String)omap1.get("pv_cdunieco_i"));
			paramConfirmarEndosoB.put("pv_cdramo_i"   , (String)omap1.get("pv_cdramo_i"));
			paramConfirmarEndosoB.put("pv_estado_i"   , (String)omap1.get("pv_estado_i"));
			paramConfirmarEndosoB.put("pv_nmpoliza_i" , (String)omap1.get("pv_nmpoliza_i"));
			paramConfirmarEndosoB.put("pv_nmsuplem_i" , respuestaEndosoNombres.get("pv_nmsuplem_o"));
			paramConfirmarEndosoB.put("pv_nsuplogi_i" , respuestaEndosoNombres.get("pv_nsuplogi_o"));
			paramConfirmarEndosoB.put("pv_cdtipsup_i" , "2");
			paramConfirmarEndosoB.put("pv_dscoment_i" , "");
		    endososManager.confirmarEndosoB(paramConfirmarEndosoB);
		    
		    ///////////////////////////////////////
		    ////// re generar los documentos //////
		    /*///////////////////////////////////*/
		    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
			paramsGetDoc.put("pv_cdunieco_i" , (String)omap1.get("pv_cdunieco_i"));
			paramsGetDoc.put("pv_cdramo_i"   , (String)omap1.get("pv_cdramo_i"));
			paramsGetDoc.put("pv_estado_i"   , (String)omap1.get("pv_estado_i"));
			paramsGetDoc.put("pv_nmpoliza_i" , (String)omap1.get("pv_nmpoliza_i"));
			paramsGetDoc.put("pv_nmsuplem_i" , respuestaEndosoNombres.get("pv_nmsuplem_o"));
			paramsGetDoc.put("pv_tipmov_i"   , "2");
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
			
			mensaje="Se ha guardado el endoso con n&uacute;mero "+respuestaEndosoNombres.get("pv_nsuplogi_o");
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al generar endoso de nombres",ex);
			success=false;
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
			omap1.put("pv_fecha_i"    , new Date());
			omap1.put("pv_cdelemen_i" , usuario.getEmpresa().getElementoId());
			omap1.put("pv_cdusuari_i" , usuario.getUser());
			omap1.put("pv_proceso_i"  , "END");
			omap1.put("pv_cdtipsup_i" , "8");
			
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
				//////////////////////////////
				////// confirmar endoso //////
				Map<String,String>paramConfirmarEndosoB=new LinkedHashMap<String,String>(0);
				paramConfirmarEndosoB.put("pv_cdunieco_i" , smap1.get("pv_cdunieco_i"));
				paramConfirmarEndosoB.put("pv_cdramo_i"   , smap1.get("pv_cdramo_i"));
				paramConfirmarEndosoB.put("pv_estado_i"   , smap1.get("pv_estado_i"));
				paramConfirmarEndosoB.put("pv_nmpoliza_i" , smap1.get("pv_nmpoliza_i"));
				paramConfirmarEndosoB.put("pv_nmsuplem_i" , resEnd.get("pv_nmsuplem_o"));
				paramConfirmarEndosoB.put("pv_nsuplogi_i" , resEnd.get("pv_nsuplogi_o"));
				paramConfirmarEndosoB.put("pv_cdtipsup_i" , "8");
				paramConfirmarEndosoB.put("pv_dscoment_i" , "");
			    endososManager.confirmarEndosoB(paramConfirmarEndosoB);
			    
			    ///////////////////////////////////////
			    ////// re generar los documentos //////
			    /*///////////////////////////////////*/
			    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
				paramsGetDoc.put("pv_cdunieco_i" , smap1.get("pv_cdunieco_i"));
				paramsGetDoc.put("pv_cdramo_i"   , smap1.get("pv_cdramo_i"));
				paramsGetDoc.put("pv_estado_i"   , smap1.get("pv_estado_i"));
				paramsGetDoc.put("pv_nmpoliza_i" , smap1.get("pv_nmpoliza_i"));
				paramsGetDoc.put("pv_nmsuplem_i" , resEnd.get("pv_nmsuplem_o"));
				paramsGetDoc.put("pv_tipmov_i"   , "8");
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
			}
			
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar endoso de clausula paso",ex);
			success=false;
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
			mapGuaEnd.put("pv_cdtipsup_i" , "3");
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
				parametros.put("pv_nmsuplem" , "0");
				parametros.put("pv_status"   , "0");
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
			
			Map<String,String>paramConfirmarEndosoB=new LinkedHashMap<String,String>(0);
			paramConfirmarEndosoB.put("pv_cdunieco_i" , (String)mapGuaEnd.get("pv_cdunieco_i"));
			paramConfirmarEndosoB.put("pv_cdramo_i"   , (String)mapGuaEnd.get("pv_cdramo_i"));
			paramConfirmarEndosoB.put("pv_estado_i"   , (String)mapGuaEnd.get("pv_estado_i"));
			paramConfirmarEndosoB.put("pv_nmpoliza_i" , (String)mapGuaEnd.get("pv_nmpoliza_i"));
			paramConfirmarEndosoB.put("pv_nmsuplem_i" , resEndDomi.get("pv_nmsuplem_o"));
			paramConfirmarEndosoB.put("pv_nsuplogi_i" , resEndDomi.get("pv_nsuplogi_o"));
			paramConfirmarEndosoB.put("pv_cdtipsup_i" , "3");
			paramConfirmarEndosoB.put("pv_dscoment_i" , "");
		    endososManager.confirmarEndosoB(paramConfirmarEndosoB);
		    
		    ///////////////////////////////////////
		    ////// re generar los documentos //////
		    /*///////////////////////////////////*/
		    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
			paramsGetDoc.put("pv_cdunieco_i" , smap1.get("pv_cdunieco"));
			paramsGetDoc.put("pv_cdramo_i"   , smap1.get("pv_cdramo"));
			paramsGetDoc.put("pv_estado_i"   , smap1.get("pv_estado"));
			paramsGetDoc.put("pv_nmpoliza_i" , smap1.get("pv_nmpoliza"));
			paramsGetDoc.put("pv_nmsuplem_i" , resEndDomi.get("pv_nmsuplem_o"));
			paramsGetDoc.put("pv_tipmov_i"   , "3");
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
			
		    mensaje="Se ha guardado el endoso con n&uacute;mero "+resEndDomi.get("pv_nsuplogi_o");
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar los datos de endoso de domicilio",ex);
			success=false;
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
				omap1.put("pv_cdtipsup_i" , "6");
			}
			else
			{
				omap1.put("pv_cdtipsup_i" , "7");
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
				paramSigsvdef.put("pv_cdtipsup_i" , "6");
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
				paramSigsvdefEnd.put("pv_cdtipsup_i" , "6");
			}
			else
			{
				paramSigsvdefEnd.put("pv_cdtipsup_i" , "7");
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
					paramCalcValorEndoso.put("pv_cdtipsup_i" , "6");
				}
				else
				{
					paramCalcValorEndoso.put("pv_cdtipsup_i" , "7");
				}
				endososManager.calcularValorEndoso(paramCalcValorEndoso);
				
				Map<String,String>paramConfirmarEndosoB=new LinkedHashMap<String,String>(0);
				paramConfirmarEndosoB.put("pv_cdunieco_i" , (String)omap1.get("pv_cdunieco_i"));
				paramConfirmarEndosoB.put("pv_cdramo_i"   , (String)omap1.get("pv_cdramo_i"));
				paramConfirmarEndosoB.put("pv_estado_i"   , (String)omap1.get("pv_estado_i"));
				paramConfirmarEndosoB.put("pv_nmpoliza_i" , (String)omap1.get("pv_nmpoliza_i"));
				paramConfirmarEndosoB.put("pv_nmsuplem_i" , respEndCob.get("pv_nmsuplem_o"));
				paramConfirmarEndosoB.put("pv_nsuplogi_i" , respEndCob.get("pv_nsuplogi_o"));
				if(smap1.get("altabaja").equalsIgnoreCase("alta"))
				{
					paramConfirmarEndosoB.put("pv_cdtipsup_i" , "6");
				}
				else
				{
					paramConfirmarEndosoB.put("pv_cdtipsup_i" , "7");
				}
				paramConfirmarEndosoB.put("pv_dscoment_i" , "");
			    endososManager.confirmarEndosoB(paramConfirmarEndosoB);
			    
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
					paramsGetDoc.put("pv_tipmov_i"   , "6");
				}
				else
				{
					paramsGetDoc.put("pv_tipmov_i"   , "7");
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
				
				ejecutaWSrecibosEndoso((String)omap1.get("pv_cdunieco_i"), (String)omap1.get("pv_cdramo_i"),
						(String)omap1.get("pv_estado_i"), (String)omap1.get("pv_nmpoliza_i"),
						respEndCob.get("pv_nmsuplem_o"), respEndCob.get("pv_nsuplogi_o"), rutaCarpeta,
						cdtipsitGS, sucursal, nmsolici, nmtramite,
						true, "ACTUALIZA"
						);
				
				
				mensaje="Se ha confirmado el endoso con n&uacute;mero "+respEndCob.get("pv_nsuplogi_o");
			}
		    else
			{
				mensaje="Se ha guardado el endoso con n&uacute;mero "+respEndCob.get("pv_nsuplogi_o");
			}
			
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar el endoso de coberturas",ex);
			success=false;
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
		try
		{
			log.debug("smap1: "+smap1);
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
		log.debug(""
				+ "\n######                     ######"
				+ "\n###### endosoValositBasico ######"
				+ "\n#################################"
				+ "\n#################################"
				);
		return SUCCESS;
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
			omap1.put("pv_cdtipsup_i" , "4");
			Map<String,String> respEnd=endososManager.guardarEndosoCoberturas(omap1);

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
			
			if(smap1.get("confirmar").equalsIgnoreCase("si"))
			{
				Map<String,String>paramConfirmarEndosoB=new LinkedHashMap<String,String>(0);
				paramConfirmarEndosoB.put("pv_cdunieco_i" , smap1.get("cdunieco"));
				paramConfirmarEndosoB.put("pv_cdramo_i"   , smap1.get("cdramo"));
				paramConfirmarEndosoB.put("pv_estado_i"   , smap1.get("estado"));
				paramConfirmarEndosoB.put("pv_nmpoliza_i" , smap1.get("nmpoliza"));
				paramConfirmarEndosoB.put("pv_nmsuplem_i" , respEnd.get("pv_nmsuplem_o"));
				paramConfirmarEndosoB.put("pv_nsuplogi_i" , respEnd.get("pv_nsuplogi_o"));
				paramConfirmarEndosoB.put("pv_cdtipsup_i" , "4");
				paramConfirmarEndosoB.put("pv_dscoment_i" , "");
				endososManager.confirmarEndosoB(paramConfirmarEndosoB);		    
		    
			    ///////////////////////////////////////
			    ////// re generar los documentos //////
			    /*///////////////////////////////////*/
			    Map<String,String>paramsGetDoc=new LinkedHashMap<String,String>(0);
				paramsGetDoc.put("pv_cdunieco_i" , smap1.get("cdunieco"));
				paramsGetDoc.put("pv_cdramo_i"   , smap1.get("cdramo"));
				paramsGetDoc.put("pv_estado_i"   , smap1.get("estado"));
				paramsGetDoc.put("pv_nmpoliza_i" , smap1.get("nmpoliza"));
				paramsGetDoc.put("pv_nmsuplem_i" , respEnd.get("pv_nmsuplem_o"));
				paramsGetDoc.put("pv_tipmov_i"   , "4");
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
				
				mensaje="Se ha confirmado el endoso con n&uacute;mero "+respEnd.get("pv_nsuplogi_o");
				
			}
			else
			{				
				mensaje="Se ha guardado el endoso con n&uacute;mero "+respEnd.get("pv_nsuplogi_o");
			}
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar endosos de valosit basico",ex);
			success=false;
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
			imap1.put("itemsFiltro" , gc.getItems());
			
			List<Tatri>tatriModelo=pantallasManager.obtenerCamposPantalla(null,null,null,null,null,null,"EDITORPANTALLAS",null,null,"MODELO");
			gc.generaParcial(tatriModelo);
			imap1.put("itemsModelo"   , gc.getItems());
			imap1.put("fieldsModelo"  , gc.getFields());
			imap1.put("columnsModelo" , gc.getColumns());
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
		try
		{
			
		}
		catch(Exception ex)
		{
			log.error("error al mostrar la pantalla de endoso de clausulas",ex);
		}
		log.debug(""
				+ "\n######                                  ######"
				+ "\n###### pantalla de endosos de clausulas ######"
				+ "\n##############################################"
				+ "\n##############################################"
				);
		return SUCCESS;
	}
	/*//////////////////////////////////////////*/
	////// pantalla de endosos de clausulas //////
	//////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	////// pantalla de endoso de alta y/o baja de asegurados //////
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
		try
		{
			log.debug("smap1: "+smap1);
			/* NMSUPLEM=245665412050000000,
			 * DSTIPSIT=SALUD VITAL,
			 * FEINIVAL=27/12/2013,
			 * NMPOLIZA=1,
			 * PRIMA_TOTAL=10830.45,
			 * NMPOLIEX=1904213000001000000,
			 * NSUPLOGI=0,
			 * DSCOMENT=EMISIÒN DE LA POLIZA,
			 * ESTADO=M,
			 * CDTIPSIT=SL,
			 * NTRAMITE=396,
			 * CDUNIECO=1904,
			 * FEEMISIO=27/12/2013,
			 * CDRAMO=2
			 */
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
			
			List<Tatri>tatriTemp=new ArrayList<Tatri>(0);
			for(Tatri t:tatrisit)
			//solo dejar los atributos si es individual, los que tengan S
			{
				if(t.getSwsuscri().equalsIgnoreCase("S"))//S=individual
				{
					tatriTemp.add(t);
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
		log.debug("\n"
				+ "\n###### pantalla de endoso de alta y/o baja de asegurados ######"
				+ "\n######                                                   ######"
				+ "\n###############################################################"
				+ "\n###############################################################"
				);
		return SUCCESS;
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
        parametros.pv_otvalor01, //generico
        parametros.pv_otvalor19, //generico
        parametros.pv_otvalor16, //generico
        parametros.pv_otvalor18, //generico
        parametros.pv_otvalor02  //generico
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
		try
		{
			UserVO usuario=(UserVO) session.get("USUARIO");
			
			boolean alta=smap1.containsKey("apat");
			
			String cdunieco = smap2.get("CDUNIECO");
			String cdramo   = smap2.get("CDRAMO");
			String estado   = smap2.get("ESTADO");
			String nmpoliza = smap2.get("NMPOLIZA");
			String cdelemen = usuario.getEmpresa().getElementoId();
			String usuari   = usuario.getUser();
			
			Map<String,String>paramsIniciarEndoso=new HashMap<String,String>(0);
			paramsIniciarEndoso.put("pv_cdunieco_i" , cdunieco);
			paramsIniciarEndoso.put("pv_cdramo_i"   , cdramo);
			paramsIniciarEndoso.put("pv_estado_i"   , estado);
			paramsIniciarEndoso.put("pv_nmpoliza_i" , nmpoliza);
			paramsIniciarEndoso.put("pv_fecha_i"    , renderFechas.format(new Date()));
			paramsIniciarEndoso.put("pv_cdelemen_i" , cdelemen);
			paramsIniciarEndoso.put("pv_cdusuari_i" , usuari);
			paramsIniciarEndoso.put("pv_proceso_i"  , "END");
			paramsIniciarEndoso.put("pv_cdtipsup_i" , alta?"9":"10");
			Map<String,String>respuestaIniciarEndoso=endososManager.iniciarEndoso(paramsIniciarEndoso);
			
			String nmsuplem=respuestaIniciarEndoso.get("pv_nmsuplem_o");
			String nsuplogi=respuestaIniciarEndoso.get("pv_nsuplogi_o");
			
			Map<String,String>paramConfirmarEndoso=new LinkedHashMap<String,String>(0);
			paramConfirmarEndoso.put("pv_cdunieco_i" , cdunieco);
			paramConfirmarEndoso.put("pv_cdramo_i"   , cdramo);
			paramConfirmarEndoso.put("pv_estado_i"   , estado);
			paramConfirmarEndoso.put("pv_nmpoliza_i" , nmpoliza);
			paramConfirmarEndoso.put("pv_nmsuplem_i" , nmsuplem);
			paramConfirmarEndoso.put("pv_nsuplogi_i" , nsuplogi);
			paramConfirmarEndoso.put("pv_cdtipsup_i" , alta?"9":"10");
			paramConfirmarEndoso.put("pv_dscoment_i" , "");
		    endososManager.confirmarEndosoB(paramConfirmarEndoso);
			
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar endoso de alta o baja de asegurado", ex);
			success=false;
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
			String nmsuplem, String numendoso, String rutaPoliza, String cdtipsitGS, String sucursal, String nmsolici,String ntramite, boolean async, String Op){
		boolean allInserted = true;
		
		logger.debug("*** Entrando a metodo Actualiza Recibos WS ice2sigs ENDOSO, para la poliza: " + nmpoliza + " sucursal: " + sucursal + "***");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		
		if(StringUtils.isBlank(Op)) Op = "ACTUALIZA";
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
				logger.error("Error al actualizar recibo: "+recibo.getNumRec()+" tramite: "+ntramite);
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
		for(Recibo recibo: recibos){
			if( 1 != recibo.getNumRec()) continue;
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
					paramsR.put("pv_dsdocume_i", "Recibo "+recibo.getNumRec()+" Endoso "+numendoso);
					paramsR.put("pv_nmsolici_i", nmsolici);
					paramsR.put("pv_ntramite_i", ntramite);
					paramsR.put("pv_tipmov_i", "1");
					
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
				logger.error("Error al actualizar el cliente: " + cliente.getClaveCli(), e);
				exito = false;
			}
		}

		return exito;
	} 
	
	
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
	
}