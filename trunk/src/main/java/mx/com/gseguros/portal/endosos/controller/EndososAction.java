package mx.com.gseguros.portal.endosos.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.cotizacion.controller.ComplementariosCoberturasAction;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.Tatri;
import mx.com.gseguros.portal.endosos.service.EndososManager;
import mx.com.gseguros.portal.general.util.ConstantesCatalogos;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.utils.HttpUtil;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.SqlParameter;

import com.opensymphony.xwork2.ActionContext;

public class EndososAction extends PrincipalCoreAction implements ConstantesCatalogos
{
	private static final long        serialVersionUID = 84257834070419933L;
	private static Logger            log              = Logger.getLogger(EndososAction.class);
	private List<Map<String,String>> slist1;
	private List<Map<String,String>> slist2;
	private Map<String,String>       smap1;
	private Map<String,String>       smap2;
	private Map<String,Object>       omap1;
	private boolean                  success          = false;
	private EndososManager           endososManager;
	private KernelManagerSustituto   kernelManager;
	private Item                     item1;
	private Item                     item2;
	private SimpleDateFormat         renderFechas     = new SimpleDateFormat("dd/MM/yyyy");
	private String                   mensaje;
	private Map<String,String>       parametros;

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
			paramsGetDoc.put("pv_tipmov_i"   , getText("endoso.tipo.nombres"));
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
			
			mensaje="Se ha guardado el endozo con n&uacute;mero "+respuestaEndosoNombres.get("pv_nsuplogi_o");
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
			paramsGetDoc.put("pv_tipmov_i"   , getText("endoso.tipo.domicilio"));
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
			
		    mensaje="Se ha guardado el endozo con n&uacute;mero "+resEndDomi.get("pv_nsuplogi_o");
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
			omap1.put("pv_cdtipsup_i" , "6");
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
			
			Map<String,String>paramConfirmarEndosoB=new LinkedHashMap<String,String>(0);
			paramConfirmarEndosoB.put("pv_cdunieco_i" , (String)omap1.get("pv_cdunieco_i"));
			paramConfirmarEndosoB.put("pv_cdramo_i"   , (String)omap1.get("pv_cdramo_i"));
			paramConfirmarEndosoB.put("pv_estado_i"   , (String)omap1.get("pv_estado_i"));
			paramConfirmarEndosoB.put("pv_nmpoliza_i" , (String)omap1.get("pv_nmpoliza_i"));
			paramConfirmarEndosoB.put("pv_nmsuplem_i" , respEndCob.get("pv_nmsuplem_o"));
			paramConfirmarEndosoB.put("pv_nsuplogi_i" , respEndCob.get("pv_nsuplogi_o"));
			paramConfirmarEndosoB.put("pv_cdtipsup_i" , "6");
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
			paramsGetDoc.put("pv_tipmov_i"   , getText("endoso.tipo.coberturas"));
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
		    
		    mensaje="Se ha guardado el endozo con n&uacute;mero "+respEndCob.get("pv_nsuplogi_o");
			
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
				paramsGetDoc.put("pv_tipmov_i"   , getText("endoso.tipo.valosit.basico"));
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
				
				mensaje="Se ha confirmado el endozo con n&uacute;mero "+respEnd.get("pv_nsuplogi_o");
				
			}
			else
			{				
				mensaje="Se ha guardado el endozo con n&uacute;mero "+respEnd.get("pv_nsuplogi_o");
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
	@Override
	public String getCON_CAT_POL_ESTADO() {
		// TODO Auto-generated method stub
		return CON_CAT_POL_ESTADO;
	}
	@Override
	public String getCON_CAT_POL_TIPO_POLIZA() {
		// TODO Auto-generated method stub
		return CON_CAT_POL_TIPO_POLIZA;
	}
	@Override
	public String getCON_CAT_POL_TIPO_PAGO() {
		// TODO Auto-generated method stub
		return CON_CAT_POL_TIPO_PAGO;
	}
	@Override
	public String getCON_CAT_POL_ROL() {
		// TODO Auto-generated method stub
		return CON_CAT_POL_ROL;
	}
	@Override
	public String getCON_CAT_MESACONTROL_SUCUR_ADMIN() {
		// TODO Auto-generated method stub
		return CON_CAT_MESACONTROL_SUCUR_ADMIN;
	}
	@Override
	public String getCON_CAT_MESACONTROL_SUCUR_DOCU() {
		// TODO Auto-generated method stub
		return CON_CAT_MESACONTROL_SUCUR_DOCU;
	}
	@Override
	public String getCON_CAT_MESACONTROL_TIP_TRAMI() {
		// TODO Auto-generated method stub
		return CON_CAT_MESACONTROL_TIP_TRAMI;
	}
	@Override
	public String getCON_CAT_MESACONTROL_ESTAT_TRAMI() {
		// TODO Auto-generated method stub
		return CON_CAT_MESACONTROL_ESTAT_TRAMI;
	}
	@Override
	public String getCON_CAT_TPERSONA() {
		// TODO Auto-generated method stub
		return CON_CAT_TPERSONA;
	}
	@Override
	public String getCON_CAT_NACIONALIDAD() {
		// TODO Auto-generated method stub
		return CON_CAT_NACIONALIDAD;
	}
	@Override
	public String getCON_CAT_CANCELA_MOTIVOS() {
		// TODO Auto-generated method stub
		return CON_CAT_CANCELA_MOTIVOS;
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
	
}