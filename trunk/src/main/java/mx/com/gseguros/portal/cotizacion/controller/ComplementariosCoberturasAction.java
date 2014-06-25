package mx.com.gseguros.portal.cotizacion.controller;

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
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.TipoEndoso;
import mx.com.gseguros.portal.general.util.TipoSituacion;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class ComplementariosCoberturasAction extends PrincipalCoreAction{

	private KernelManagerSustituto kernelManager;
	private PantallasManager pantallasManager;
	private Map<String,String>smap1;
	private Map<String,String>smap2;
	private Map<String,String>smap3;
	private Map<String,Object>omap1;
	private Map<String,Object>omap2;
	private Map<String,Object>omap3;
	private List<Map<String,String>>slist1;
	private List<Map<String,String>>slist2;
	private List<Map<String,Object>>olist1;
	private List<Map<String,Object>>olist2;
	private Item item1;
	private Item item2;
	private Item item3;
	private Item item4;
	private Map<String,String> spanel1;
	private Map<String,String> spanel2;
	private Map<String,String> spanel3;
	private Map<String,Object> opanel1;
	private Map<String,Object> opanel2;
	private Map<String,Object> opanel3;
	private Logger log=Logger.getLogger(ComplementariosCoberturasAction.class);
	private boolean success=false;
	private boolean exito  =false;
	private Map<String,String>parametros;
	private String str1;
	private String str2;
	
	public String pantallaCoberturas()
	{
		log.debug("smap1: "+smap1);
		ScreenInterceptor scrInt=new ScreenInterceptor();
		return scrInt.intercept(this, ScreenInterceptor.PANTALLA_COMPLEMENTARIOS_COBERTURAS_ASEGURADO);
	}
	
	public String mostrarPantallaCoberturas()
	{
		try
		{
			//modelo para cargar datos
			item1=new Item("fields",null,Item.ARR);
			item1.add(Item.crear(null,null,Item.OBJ).add("name","spanel1.asegurado"));
			item1.add(Item.crear(null,null,Item.OBJ).add("name","spanel1.producto"));
			
			//campos de formulario
			item2=new Item("items",null,Item.ARR);
			item2.add(Item.crear(null,null,Item.OBJ)
					.add(Item.crear("xtype",			"textfield"))
					.add(Item.crear("readOnly",			true))
					.add(Item.crear("name",				"spanel1.asegurado"))
					.add(Item.crear("fieldLabel",		"Asegurado"))
					.add(Item.crear("style",		    "margin:5px;"))
					);
			item2.add(Item.crear(null,null,Item.OBJ)
					.add(Item.crear("xtype",			"textfield"))
					.add(Item.crear("readOnly",			true))
					.add(Item.crear("name",				"spanel1.producto"))
					.add(Item.crear("fieldLabel",		"Producto"))
					.add(Item.crear("style",		    "margin:5px;"))
					);
		}
		catch(Exception ex)
		{
			log.error("error al armar la pantalla de coberturas",ex);
			item1=null;
			item2=null;
		}
		return SUCCESS;
	}
	
	public String cargarPantallaCoberturas()
	{
		try
		{
			/*
			PL:
			pv_cdunieco_i   input
			pv_cdramo_i     input
			pv_estado_i     input
			pv_nmpoliza_i   input
			pv_nmsituac_i   input
			*/
			slist1=kernelManager.obtenerCoberturasUsuario(smap1);
			success=true;
		}
		catch(Exception ex)
		{
			spanel1=null;
			log.error("error al cargar la pantalla de coberturas",ex);
			success=false;
		}
		return SUCCESS;
	}
	
	public String guardarCoberturasUsuario()
	{
		log.debug(smap1);
		log.debug(slist1);
		try
		{
			int i=1;
			for(Map<String,String> cob:slist1)
			{
				/**poligar
				PL:
				pv_cdunieco_i   smap1 ready!
	            pv_cdramo_i     smap1 ready!
				pv_estado_i     smap1 ready!
				pv_nmpoliza_i   smap1 ready!
				pv_nmsituac_i   smap1 ready!
				pv_cdgarant_i   iterado GARANTIA
				pv_nmsuplem_i   #0
				pv_cdcapita_i   iterado CDCAPITA
				pv_status_i     iterado status
				pv_cdtipbca_i   iterado cdtipbca
				pv_ptvalbas_i   iterado ptvalbas
				pv_swmanual_i   iterado swmanual
				pv_swreas_i     iterado swreas
				pv_cdagrupa_i   iterado cdagrupa
				PV_ACCION       #I
				*
				Map<String,String>mapPoligarIterado=new HashMap<String,String>(0);
				mapPoligarIterado.putAll(smap1);
				mapPoligarIterado.put("pv_cdgarant_i", cob.get("GARANTIA"));
				mapPoligarIterado.put("pv_nmsuplem_i", "0");
				mapPoligarIterado.put("pv_cdcapita_i", cob.get("CDCAPITA"));
				mapPoligarIterado.put("pv_status_i",   cob.get("status"));
				mapPoligarIterado.put("pv_cdtipbca_i", cob.get("cdtipbca"));
				mapPoligarIterado.put("pv_ptvalbas_i", cob.get("ptvalbas"));
				mapPoligarIterado.put("pv_swmanual_i", cob.get("swmanual"));
				mapPoligarIterado.put("pv_swreas_i",   cob.get("swreas"));
				mapPoligarIterado.put("pv_cdagrupa_i", cob.get("cdagrupa"));
				mapPoligarIterado.put("PV_ACCION",     "I");
				log.debug("Iteracion #"+i+" de movPoligar");
				kernelManager.movPoligar(mapPoligarIterado);
				/**/
				
				/**policap
				PL:
				pv_cdunieco_i   smap1 ready!
	            pv_cdramo_i     smap1 ready!
	            pv_estado_i     smap1 ready!
	            pv_nmpoliza_i   smap1 ready!
	            pv_nmsituac_i   smap1 ready!
	            pv_cdcapita_i   iterado CDCAPITA
	            pv_nmsuplem_i   #0
	            pv_status_i     iterado status
	            pv_ptcapita_i   SOLO NUMERICO iterado SUMA_ASEGURADA
	            pv_ptreduci_i   iterado ptreduci
	            pv_fereduci_i   iterado fereduci
	            pv_swrevalo_i   iterado swrevalo
	            pv_cdagrupa_i   iterado cdagrupa
	            pv_accion_i     #I
				*/
				Map<String,String>mapPolicapIterado=new HashMap<String,String>(0);
				mapPolicapIterado.putAll(smap1);
				mapPolicapIterado.put("pv_cdcapita_i", cob.get("CDCAPITA"));
				mapPolicapIterado.put("pv_nmsuplem_i", "0");
				mapPolicapIterado.put("pv_status_i", cob.get("status"));
				String ptcapita;
				try
				{
					Double aux=Double.parseDouble(cob.get("SUMA_ASEGURADA"));
					ptcapita=aux.toString();
				}
				catch(Exception ex)
				{
					ptcapita=null;
				}
				mapPolicapIterado.put("pv_ptcapita_i",ptcapita);
				mapPolicapIterado.put("pv_ptreduci_i", cob.get("ptreduci"));
				mapPolicapIterado.put("pv_fereduci_i", cob.get("fereduci"));
				mapPolicapIterado.put("pv_swrevalo_i", cob.get("swrevalo"));
				mapPolicapIterado.put("pv_cdagrupa_i", cob.get("cdagrupa"));
				mapPolicapIterado.put("pv_accion_i", "I");
				log.debug("Iteracion #"+i+" de movPolicap");
				kernelManager.movPolicap(mapPolicapIterado);
				
				i++;
			}
			
			//////////////////////////////////////////////////////
			////// borrar coberturas que vienen en la lista //////
			////// de borrar slist2                         //////
			/*//////////////////////////////////////////////////*/
			i=1;
			for(Map<String,String> cob:slist2)
			{
				/**poligar
				PL:
				pv_cdunieco_i   smap1 ready!
	            pv_cdramo_i     smap1 ready!
				pv_estado_i     smap1 ready!
				pv_nmpoliza_i   smap1 ready!
				pv_nmsituac_i   smap1 ready!
				pv_cdgarant_i   iterado GARANTIA
				pv_nmsuplem_i   #0
				pv_cdcapita_i   iterado CDCAPITA
				pv_status_i     iterado status
				pv_cdtipbca_i   iterado cdtipbca
				pv_ptvalbas_i   iterado ptvalbas
				pv_swmanual_i   iterado swmanual
				pv_swreas_i     iterado swreas
				pv_cdagrupa_i   iterado cdagrupa
				PV_ACCION       #I
				*/
				Map<String,String>mapPoligarIterado=new HashMap<String,String>(0);
				mapPoligarIterado.putAll(smap1);
				mapPoligarIterado.put("pv_cdgarant_i", cob.get("GARANTIA"));
				mapPoligarIterado.put("pv_nmsuplem_i", "0");
				mapPoligarIterado.put("pv_cdcapita_i", cob.get("CDCAPITA"));
				mapPoligarIterado.put("pv_status_i",   cob.get("status"));
				mapPoligarIterado.put("pv_cdtipbca_i", cob.get("cdtipbca"));
				mapPoligarIterado.put("pv_ptvalbas_i", cob.get("ptvalbas"));
				mapPoligarIterado.put("pv_swmanual_i", cob.get("swmanual"));
				mapPoligarIterado.put("pv_swreas_i",   cob.get("swreas"));
				mapPoligarIterado.put("pv_cdagrupa_i", cob.get("cdagrupa"));
				mapPoligarIterado.put("PV_ACCION",     "B");
				mapPoligarIterado.put("pv_cdtipsup_i", TipoEndoso.EMISION_POLIZA.getCdTipSup().toString());
				log.debug("Iteracion #"+i+" de movPoligar");
				kernelManager.movPoligar(mapPoligarIterado);
				
				i++;
			}
			/*//////////////////////////////////////////////////*/
			////// borrar coberturas que vienen en la lista //////
			////// de borrar slist2                         //////
			//////////////////////////////////////////////////////
			
			success=true;
		}
		catch(Exception ex)
		{
			log.error("Error al guardar las coberturas",ex);
			success=false;
		}
		return SUCCESS;
	}
	
	public String obtenerCamposTatrigar()
	{
		log.debug(smap1);
		try
		{
			UserVO usuSes=(UserVO)session.get("USUARIO");
			DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usuSes.getUser(),null);//cdtipsit
			
			/*
			pv_cdramo_i       smap1 ready!
            pv_cdtipsit_i     NOT!
            pv_cdgarant_i     smap1 ready!
			*/
			smap1.put("pv_cdtipsit_i",datUsu.getCdtipsit());
			List<ComponenteVO>listTatri=kernelManager.obtenerTatrigar(smap1);
			GeneradorCampos genCam=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			genCam.setCdgarant(smap1.get("pv_cdgarant_i"));
			genCam.genera(listTatri);
			str1=genCam.getFields().toString();
			str2=genCam.getItems().toString();
			// f i e l d s : [ ... ]
			//0 1 2 3 4 5 6 7
			str1=str1.substring(7).replace("\n", "");
			// i t e m s : [ ... ]
			//0 1 2 3 4 5 6 7
			str2=str2.substring(6).replace("\n", "");
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al obtener los campos tatrigar",ex);
			success=false;
		}
		return SUCCESS;
	}
	
	public String obtenerValoresTatrigar()
	{
		log.debug(smap1);
		try
		{
			/*
			pv_cdunieco_i      smap1 ready!
            pv_cdramo_i        smap1 ready!
            pv_estado_i        smap1 ready!
            pv_nmpoliza_i      smap1 ready!
            pv_nmsituac_i      smap1 ready!
            pv_cdgarant_i      smap1 ready!
			*/
			parametros=new HashMap<String,String>(0);
			Map<String,Object>parametrosCargados=kernelManager.obtenerValoresTatrigar(smap1);
			Iterator<Entry<String, Object>> it=parametrosCargados.entrySet().iterator();
			while(it.hasNext())
			{
				Entry<String,Object> entry=it.next();
				parametros.put("pv_"+entry.getKey(), (String)entry.getValue());
			}
			log.debug(parametros);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al obtener los valores de tatrigar",ex);
			success=true;
		}
		return SUCCESS;
	}
	
	public String guardarValoresTatrigar()
	{
		log.debug("\n##############################"
				+ "\n###### Guardar tvalogar ######");
		try
		{
			/*
			pv_cdunieco  smap1 ready!
	        pv_cdramo    smap1 ready!
	        pv_estado    smap1 ready!
	        pv_nmpoliza  smap1 ready!
	        pv_nmsituac  smap1 ready!
	        pv_cdgarant  smap1 ready!
	        pv_nmsuplem  #0
	        pv_status    #V
	        */
			smap1.putAll(parametros);
			smap1.put("pv_status", "V");
			smap1.put("pv_nmsuplem", "0");
			log.debug("smap1"+smap1);
	        log.debug("parametros"+parametros);
	        kernelManager.pMovTvalogar(smap1);
	        success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar tvalogar",ex);
			success=false;
		}
		log.debug("\n###### Guardar tvalogar ######"
				+ "\n##############################");
		return SUCCESS; 
	}
	
	public String pantallaDomicilio()
	{
		log.debug("\n###################################"
				+ "\n###################################"
				+ "\n###### pantalla de domicilio ######"
				+ "\n######                       ######"
				+ "\n######                       ######");
		log.debug("smap1: "+smap1);
		ScreenInterceptor scrInt=new ScreenInterceptor();
		return scrInt.intercept(this, ScreenInterceptor.PANTALLA_COMPLEMENTARIOS_DOMICILIO_ASEGURADO);
	}
	
	public String pantallaExclusion()
	{
		log.debug("\n###################################"
				+ "\n###################################"
				+ "\n###### pantalla de exclusion ######"
				+ "\n######                       ######"
				+ "\n######                       ######");
		log.debug("smap1: "+smap1);
		ScreenInterceptor scrInt=new ScreenInterceptor();
		return scrInt.intercept(this, ScreenInterceptor.PANTALLA_COMPLEMENTARIOS_EXCLUSION_ASEGURADO);
	}
	
	public String mostrarPantallaExclusion()
	{
		try
		{
			
		}
		catch(Exception ex)
		{
			log.error("error al mostrar la pantalla de exclusion",ex);
		}
		log.debug("\n######                       ######"
				+ "\n######                       ######"
				+ "\n###### pantalla de exclusion ######"
				+ "\n###################################"
				+ "\n###################################");
		return SUCCESS;
	}
	
	public String cargarPantallaExclusion()
	{
		log.debug("\n###################################"
				+ "\n###################################"
				+ "\n###### pantalla de exclusion ######"
				+ "\n###### load                  ######"
				+ "\n######                       ######");
		log.debug("smap1: "+smap1);
		try
		{
			slist1=kernelManager.obtenerPolicot(smap1);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al cargar las excepciones",ex);
			success=false;
		}
		log.debug("slist1: "+slist1);
		log.debug("\n######                       ######"
				+ "\n###### load                  ######"
				+ "\n###### pantalla de exclusion ######"
				+ "\n###################################"
				+ "\n###################################");
		return SUCCESS;
	}
	
	public String agregarExclusion()
	{
		log.debug("\n###################################"
				+ "\n###################################"
				+ "\n###### pantalla de exclusion ######"
				+ "\n###### agregar exclusion     ######"
				+ "\n######                       ######");
		log.debug("smap1: "+smap1);
		try
		{
			/*Map<String,String>paramObtenerHtml=new HashMap<String,String>(0);
			paramObtenerHtml.put("pv_cdclausu_i",smap1.get("pv_cdclausu_i"));
			Map<String,String>clausulaConHtml=kernelManager.obtenerHtmlClausula(paramObtenerHtml);
			/*
			pv_cdunieco_i
            pv_cdramo_i
            pv_estado_i
            pv_nmpoliza_i
            pv_nmsituac_i
            pv_cdclausu_i
            pv_nmsuplem_i #0
            pv_status_i
            pv_cdtipcla_i
            pv_swmodi_i   null
            pv_dslinea_i
            pv_accion_i   #I
            */
			//smap1.put("pv_dslinea_i",clausulaConHtml.get("dslinea"));
			kernelManager.PMovMpolicot(smap1);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al agregar la exclusion",ex);
			success=false;
		}
		log.debug("\n######                       ######"
				+ "\n###### agregar exclusion     ######"
				+ "\n###### pantalla de exclusion ######"
				+ "\n###################################"
				+ "\n###################################");
		return SUCCESS;
	}
	
	public String obtenerExclusionesPorTipo()
	{
		log.debug(""
				+ "\n####################################################"
				+ "\n####################################################"
				+ "\n###### cargar clausulas de exclusion por tipo ######"
				+ "\n######                                        ######"
				+ "\n######                                        ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			slist1=kernelManager.obtenerExclusionesPorTipo(smap1);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al obtener las clausulas de exlusion por tipo",ex);
			success=false;
		}
		log.debug(""
				+ "\n######                                        ######"
				+ "\n######                                        ######"
				+ "\n###### cargar clausulas de exclusion por tipo ######"
				+ "\n####################################################"
				+ "\n####################################################"
				);
		return SUCCESS;
	}
	
	public String cargarTiposClausulasExclusion()
	{
		log.debug(""
				+ "\n####################################################"
				+ "\n####################################################"
				+ "\n###### cargar tipos de clausulas de exclusion ######"
				+ "\n###### sin parametros de entrada              ######"
				+ "\n######                                        ######"
				);
		try
		{
			slist1=kernelManager.cargarTiposClausulasExclusion();
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al cargar los tipos de clausulas de exclusion",ex);
			success=false;
		}
		log.debug(""
				+ "\n######                                        ######"
				+ "\n###### cargar tipos de clausulas de exclusion ######"
				+ "\n###### sin parametros de entrada              ######"
				+ "\n####################################################"
				+ "\n####################################################"
				);
		return SUCCESS;
	}
	
	public String guardarHtmlExclusion()
	{
		log.debug("\n###################################"
				+ "\n###################################"
				+ "\n###### pantalla de exclusion ######"
				+ "\n###### guardarHtmlExclusion  ######"
				+ "\n######                       ######");
		log.debug("smap1: "+smap1);
		try
		{
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar html de la exclusion",ex);
			success=false;
		}
		log.debug("\n######                       ######"
				+ "\n###### guardarHtmlExclusion  ######"
				+ "\n###### pantalla de exclusion ######"
				+ "\n###################################"
				+ "\n###################################");
		return SUCCESS;
	}
	
	public String cargarHtmlExclusion()
	{
		log.debug("\n###################################"
				+ "\n###################################"
				+ "\n###### pantalla de exclusion ######"
				+ "\n###### cargar html de exclu  ######"
				+ "\n######                       ######");
		log.debug("smap1: "+smap1);
		try
		{
			smap1=kernelManager.obtenerHtmlClausula(smap1);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al cargar el html de exclusion",ex);
			success=false;
		}
		log.debug("\n######                       ######"
				+ "\n###### cargar html de exclu  ######"
				+ "\n###### pantalla de exclusion ######"
				+ "\n###################################"
				+ "\n###################################");
		return SUCCESS;
	}
	
	public String guardarExclusiones()
	{
		log.debug("\n###################################"
				+ "\n###################################"
				+ "\n###### pantalla de exclusion ######"
				+ "\n###### save                  ######"
				+ "\n######                       ######");
		log.debug("slist1: "+slist1);
		log.debug("smap1: "+smap1);
		try
		{
			success=true;
		}
		catch(Exception ex)
		{
			log.debug("error al guardar las excluciones",ex);
			success=false;
		}
		log.debug("\n######                       ######"
				+ "\n###### save                  ######"
				+ "\n###### pantalla de exclusion ######"
				+ "\n###################################"
				+ "\n###################################");
		return SUCCESS;
	}
	
	public String mostrarPantallaDomicilio()
	{
		try
		{
			Map<String,String>paramTatriper=new HashMap<String,String>(0);
			paramTatriper.put("pv_cdramo_i"   , smap1.get("pv_cdramo"));
			paramTatriper.put("pv_cdrol_i"    , smap1.get("pv_cdrol"));
			paramTatriper.put("pv_cdtipsit_i" , smap1.get("cdtipsit"));
			List<ComponenteVO>tatriper=kernelManager.obtenerTatriper(paramTatriper);
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.setCdrol(smap1.get("pv_cdrol"));
			gc.setCdramo(smap1.get("pv_cdramo"));
			gc.setCdtipsit(smap1.get("cdtipsit"));
			gc.genera(tatriper);
			item1=gc.getFields();
			item2=gc.getItems();
			
			gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			List<ComponenteVO>listaCamposDomicilio = pantallasManager.obtenerComponentes(
					null, null, smap1.get("pv_cdramo"), smap1.get("cdtipsit"), null, null, "ASEGURADO_DOMICILIO", "FORMULARIO", null);
			
			gc.generaComponentes(listaCamposDomicilio, true, false, true, false, false, false);
			
			item3=gc.getItems();
			
			/*item1=Item.crear("fields",null,Item.ARR)//quitame
					.add(Item.crear(null,null,Item.OBJ).add("name","parametros.pv_otvalor01"))//quitame
					.add(Item.crear(null,null,Item.OBJ).add("name","parametros.pv_otvalor02"))//quitame
					.add(Item.crear(null,null,Item.OBJ)//quitame
							.add("name"       , "parametros.pv_otvalor03")//quitame
							.add("type"       , "date")//quitame
							.add("dateFormat" , "d/m/Y")//quitame
							)//generador campos*/
			item1
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.asegurado"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.rfc"))
					/*
					map.put("CDPERSON" , rs.getString("CDPERSON"));
					map.put("NMORDDOM" , rs.getString("NMORDDOM"));
					map.put("DSDOMICI" , rs.getString("DSDOMICI"));
					map.put("NMTELEFO" , rs.getString("NMTELEFO"));
					map.put("CDPOSTAL" , rs.getString("CDPOSTAL"));
					map.put("CDEDO"    , rs.getString("CDEDO"));
					map.put("CDMUNICI" , rs.getString("CDMUNICI"));
					map.put("CDCOLONI" , rs.getString("CDCOLONI"));
					map.put("NMNUMERO" , rs.getString("NMNUMERO"));
					map.put("NMNUMINT" , rs.getString("NMNUMINT"));
					*/
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.NMTELEFO"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.NMORDDOM"))//1ero o segundo o tercero
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.DSDOMICI"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.NMTELEFO"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.CODPOSTAL"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.CDEDO"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.estado"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.CDMUNICI"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.Municipio"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.CDCOLONI"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.NMNUMERO"))
					.add(Item.crear(null,null,Item.OBJ).add("name","smap1.NMNUMINT"))
					;
			/*
			item2=Item.crear("items",null,Item.ARR)//generador campos
					.add(Item.crear(null,null,Item.OBJ)//quitame
							.add("name"       , "parametros.pv_otvalor01")//quitame
							.add("xtype"       , "textfield")//quitame
							.add("allowBlank" , false)//quitame
							)//generador campos
					.add(Item.crear(null,null,Item.OBJ)//quitame
							.add("name"       , "parametros.pv_otvalor02")//quitame
							.add("xtype"       , "textfield")//quitame
							.add("allowBlank" , true)//quitame
							)//generador campos
					.add(Item.crear(null,null,Item.OBJ)//quitame
							.add("name"       , "parametros.pv_otvalor03")//quitame
							.add("xtype"       , "datefield")//quitame
							.add("format"       , "d/m/Y")//quitame
							.add("allowBlank" , false)//quitame
							)//generador campos
					;*/
		}
		catch(Exception ex)
		{
			log.error("error al mostrar la pantalla de domicilio",ex);
		}
		log.debug("\n######                       ######"
				+ "\n######                       ######"
				+ "\n###### pantalla de domicilio ######"
				+ "\n###################################"
				+ "\n###################################");
		return SUCCESS;
	}
	
	public String cargarPantallaDomicilio()
	{
		log.debug("\n##########################################"
				+ "\n##########################################"
				+ "\n###### cargar pantalla de domicilio ######"
				+ "\n######                              ######"
				+ "\n######                              ######");
		try
		{
			
			UserVO usuSes=(UserVO)session.get("USUARIO");
			
			log.debug(smap1);
			/*
			pv_cdunieco_i  smap1 ready!
            pv_cdramo_i    smap1 ready!
            pv_estado_i    smap1 ready!
            pv_nmpoliza_i  smap1 ready!
            pv_nmsituac_i  smap1 ready!
            pv_cdrol_i     smap1 ready!
            pv_cdperson_i  smap1 ready!
            pv_nmsumplem_i #0
            pv_status_i    #V    QUITADO X(
            pv_cdtipsit_i  session
            */
			smap1.put("pv_nmsuplem_i" , "99999999999999999999");
			//smap1.put("pv_status_i"   , "V");
			//smap1.put("pv_cdtipsit_i" ,  datUsu.getCdtipsit());
			Map<String,Object>parametrosCargados=null;
			try
			{
				parametrosCargados=kernelManager.obtenerValoresTatriper(smap1);
			}
			catch(Exception ex)
			{
				log.debug("no hay valores para tatriper",ex);
				parametrosCargados=new HashMap<String,Object>(0);
			}
			parametros=new HashMap<String,String>(0);
			Iterator<Entry<String, Object>> it=parametrosCargados.entrySet().iterator();
			while(it.hasNext())
			{
				Entry<String,Object> entry=it.next();
				parametros.put("pv_"+entry.getKey(), (String)entry.getValue());
			}
			log.debug(parametros);
			/*parametros=new HashMap<String,String>(0);//quitame
			parametros.put("pv_otvalor01", "valor1");//quitame
			parametros.put("pv_otvalor02", "valor2");//quitame
			parametros.put("pv_otvalor03", "17/08/1990");//quitame
			
			String nombreAsegurado = smap1.get("nombreAsegurado");
			String rfcAsegurado    = smap1.get("cdrfc");
			smap1=new HashMap<String,String>(0);
			smap1.put("asegurado"    , nombreAsegurado);
			smap1.put("rfc"          , rfcAsegurado);
			smap1.put("telefono"     , "012464666589");
			smap1.put("codigoPostal" , "99999");
			smap1.put("ciudad"       , "Tlaxcala");
			smap1.put("delegacion"   , "tlaxcala");
			smap1.put("colonia"      , "Acuitlapilco");
			smap1.put("calle"        , "Zaragoza");
			smap1.put("exterior"     , "B");
			smap1.put("interior"     , "3");*/
			
			/*
			pv_cdunieco_i  smap1 ready!
            pv_cdramo_i    smap1 ready!
            pv_estado_i    smap1 ready!
            pv_nmpoliza_i  smap1 ready!
            pv_nmsituac_i  smap1 ready!
            pv_cdrol_i     smap1 ready!
            pv_cdperson_i  smap1 ready!
            pv_nmsumplem_i #0
            pv_status_i    #V    QUITADO X(
            pv_cdtipsit_i  session
            */
			
			/*
			pv_cdunieco_i  smap1 ready!
			pv_cdramo_i    smap1 ready!
			pv_estado_i    smap1 ready!
			pv_nmpoliza_i  smap1 ready!
			pv_nmsituac_i  smap1 ready!
			pv_nmsuplem_i  #0     ->se puso arriba
			pv_cdperson_i  smap1 ready!
			pv_cdtipsit_i  session->se puso arriba
		    */
			/*
			map.put("CDPERSON" , rs.getString("CDPERSON"));
			map.put("NMORDDOM" , rs.getString("NMORDDOM"));
			map.put("DSDOMICI" , rs.getString("DSDOMICI"));
			map.put("NMTELEFO" , rs.getString("NMTELEFO"));
			map.put("CDPOSTAL" , rs.getString("CDPOSTAL"));
			map.put("CDEDO"    , rs.getString("CDEDO"));
			map.put("CDMUNICI" , rs.getString("CDMUNICI"));
			map.put("CDCOLONI" , rs.getString("CDCOLONI"));
			map.put("NMNUMERO" , rs.getString("NMNUMERO"));
			map.put("NMNUMINT" , rs.getString("NMNUMINT"));
			*/
			smap1=kernelManager.obtenerDomicilio(smap1);
			
			
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al cargar los datos de domicilio",ex);
			success=false;
		}
		
		log.debug("\n######                              ######"
				+ "\n######                              ######"
				+ "\n###### cargar pantalla de domicilio ######"
				+ "\n##########################################"
				+ "\n##########################################");
		return SUCCESS;
	}
	
	public String guardarPantallaDomicilio()
	{
		log.debug("\n###########################################"
				+ "\n###########################################"
				+ "\n###### guardar pantalla de domicilio ######"
				+ "\n######                               ######"
				+ "\n######                               ######");
		try
		{
			success=true;
			log.debug(smap1);
			log.debug(parametros);
			UserVO usuSes=(UserVO)session.get("USUARIO");
			
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
				parametros.put("pv_cdtipsit" , smap1.get("cdtipsit"));
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
			
			exito=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar los datos de domicilio",ex);
			str1=ex.getMessage();
			exito=false;
		}
		log.debug("\n######                               ######"
				+ "\n######                               ######"
				+ "\n###### guardar pantalla de domicilio ######"
				+ "\n###########################################"
				+ "\n###########################################");
		return SUCCESS;
	}
	
	
	
	public String agregarExclusionDetalle()
	{
		log.debug("\n#############################################"
				+ "\n#############################################"
				+ "\n###### pantalla de exclusion Detalle   ######"
				+ "\n###### agregar exclusion   Detalle     ######"
				+ "\n######                                 ######");
		log.debug("omap1: "+omap1);
		try
		{
			UserVO usuarioSesion=(UserVO) this.session.get("USUARIO");
            log.debug("se inserta detalle nuevo");
            log.debug(omap1);
            
            Iterator<Entry<String,String >> it=smap1.entrySet().iterator();
            omap1= new HashMap<String,Object>(0);
			while(it.hasNext())
			{
				Entry<String,String> entry=it.next();
				omap1.put(entry.getKey(), entry.getValue());
			}
			
			
            omap1.put("pv_feinicio_i"   , new Date());
            omap1.put("pv_cdusuari_i"   , usuarioSesion.getUser());
            omap1.put("pv_cdmotivo_i"   , null);
            kernelManager.movDmesacontrol(omap1);
            
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al agregar la exclusion",ex);
			success=false;
		}
		log.debug("\n######                                ######"
				+ "\n###### agregar exclusion  Detalle     ######"
				+ "\n###### pantalla de exclusion  Detalle ######"
				+ "\n############################################"
				+ "\n############################################");
		return SUCCESS;
	}
	
	public String pantallaValosit()
	{
		log.debug(""
				+ "\n##############################"
				+ "\n##############################"
				+ "\n###### pantalla valosit ######"
				+ "\n######                  ######"
				);
		try
		{
			log.debug("smap1: "+smap1);
			smap1.put("timestamp",""+System.currentTimeMillis());
			String cdusuari;
			{
				UserVO usuario=(UserVO)session.get("USUARIO");
				cdusuari=usuario.getUser();
			}
			String cdtipsit = smap1.get("cdtipsit");
			List<ComponenteVO>tatrisit=kernelManager.obtenerTatrisit(cdtipsit,cdusuari);
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.setCdtipsit(cdtipsit);
			List<ComponenteVO>tatriTemp=new ArrayList<ComponenteVO>(0);
			boolean agrupado=smap1.containsKey("agrupado")&&smap1.get("agrupado").equalsIgnoreCase("SI");
			
			/*
			 * Estos atributos no pueden modificarse
			 */
			Map<String,String>mapaAtributosReadonly=new HashMap<String,String>();
			mapaAtributosReadonly.put("1" , "DUMMY");
			mapaAtributosReadonly.put("2" , "DUMMY");
			mapaAtributosReadonly.put("3" , "DUMMY");
			mapaAtributosReadonly.put("4" , "DUMMY");
			mapaAtributosReadonly.put("5" , "DUMMY");
			mapaAtributosReadonly.put("6" , "DUMMY");
			mapaAtributosReadonly.put("7" , "DUMMY");
			
			/*
			 * Estos atributos no deben verse
			 */
			Map<String,String>mapaAtributosOcultos=new HashMap<String,String>();
			mapaAtributosOcultos.put("24" , "DUMMY");
			mapaAtributosOcultos.put("26" , "DUMMY");
			
			for(ComponenteVO t:tatrisit)
			//si es agrupado solo dejar los atributos con N, si es individual solo los que tengan S
			{
				if(agrupado)
				{
					if(t.getSwsuscri().equalsIgnoreCase("N"))//N=Agrupado
					{
						tatriTemp.add(t);
						if(
								cdtipsit.equalsIgnoreCase(TipoSituacion.AUTOS_FRONTERIZOS.getCdtipsit())
								 ||cdtipsit.equalsIgnoreCase(TipoSituacion.AUTOS_PICK_UP.getCdtipsit()) 
								)
						{
							if(mapaAtributosReadonly.containsKey(t.getNameCdatribu()))
							{
								t.setSoloLectura(true);
							}
							if(mapaAtributosOcultos.containsKey(t.getNameCdatribu()))
							{
								t.setOculto(true);
							}
						}
					}
				}
				else
				{
					if(t.getSwsuscri().equalsIgnoreCase("S"))//S=individual
					{
						tatriTemp.add(t);
					}
				}
			}
			tatrisit=tatriTemp;
			if(agrupado&&
					(
							cdtipsit.equals(TipoSituacion.SALUD_VITAL)
							||cdtipsit.equals(TipoSituacion.SALUD_NOMINA)
							||cdtipsit.equals(TipoSituacion.MULTISALUD)
					)
				)
			//reordenar cp, estado, municipio
			{
				List<ComponenteVO>tatriTemp2=new ArrayList<ComponenteVO>(0);
				//buscar cp
				for(ComponenteVO t:tatrisit) if(t.getNameCdatribu().equals("3")) tatriTemp2.add(t);
				//buscar estado
				for(ComponenteVO t:tatrisit) if(t.getNameCdatribu().equals("4")) tatriTemp2.add(t);
				//buscar municipio
				for(ComponenteVO t:tatrisit) if(t.getNameCdatribu().equals("17")) tatriTemp2.add(t);
				//agregar todos los demas
				for(ComponenteVO t:tatrisit)
				{
					if(!t.getNameCdatribu().equals("3")&&!t.getNameCdatribu().equals("4")&&!t.getNameCdatribu().equals("17"))
					{
						tatriTemp2.add(t);
					}
				}
				tatrisit=tatriTemp2;
			}
			gc.genera(tatrisit);
			item1=gc.getFields();
			item2=gc.getItems();
		}
		catch(Exception ex)
		{
			log.error("error al mostrar la pantalla de valosit",ex);
		}
		log.debug(""
				+ "\n######                  ######"
				+ "\n###### pantalla valosit ######"
				+ "\n##############################"
				+ "\n##############################"
				);
		return SUCCESS;
	}
	
	///////////////////////////////////////////////////////
	////// cargar valosit para datos complementarios //////
	/*///////////////////////////////////////////////////*/
	public String pantallaValositLoad()
	{
		log.debug(""
				+ "\n###################################"
				+ "\n###################################"
				+ "\n###### pantalla valosit load ######"
				+ "\n######                       ######"
				);
		try
		{
			log.debug("smap1: "+smap1);
			omap1=kernelManager.obtieneValositSituac(smap1);
			parametros=new LinkedHashMap<String,String>(0);
			Iterator it=omap1.entrySet().iterator();
			while(it.hasNext())
			{
				Entry en=(Entry)it.next();
				parametros.put("pv_"+(String)en.getKey(),(String)en.getValue());
			}
			omap1=null;
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al cargar valosit",ex);
			success=false;
		}
		log.debug(""
				+ "\n######                       ######"
				+ "\n###### pantalla valosit load ######"
				+ "\n###################################"
				+ "\n###################################"
				);
		return SUCCESS;
	}
	/*///////////////////////////////////////////////////*/
	////// cargar valosit para datos complementarios //////
	///////////////////////////////////////////////////////
	
	/////////////////////////////////////////////
	////// guardar valosit situac o grupal //////
	/*/////////////////////////////////////////*/
	public String pantallaValositSave()
	{
		log.debug(""
				+ "\n###################################"
				+ "\n###################################"
				+ "\n###### pantalla valosit save ######"
				+ "\n######                       ######"
				);
		try
		{
			log.debug("smap1: "+smap1);
			log.debug("parametros: "+parametros);
			
			if(smap1.get("agrupado").equalsIgnoreCase("si"))
			//actualizar todos
			{
				log.debug("se tienen agrupados");
				Map<String,String>paramsAsegurados=new LinkedHashMap<String,String>(0);
				paramsAsegurados.put("pv_cdunieco", smap1.get("cdunieco"));
				paramsAsegurados.put("pv_cdramo",   smap1.get("cdramo"));
				paramsAsegurados.put("pv_estado",   smap1.get("estado"));
				paramsAsegurados.put("pv_nmpoliza", smap1.get("nmpoliza"));
				paramsAsegurados.put("pv_nmsuplem", "0");
				log.debug("paramsAsegurados: "+paramsAsegurados);
				List<Map<String, Object>>asegurados=kernelManager.obtenerAsegurados(paramsAsegurados);
				log.debug("asegurados: "+asegurados);
				int i=1;
				for(Map<String,Object> asegurado:asegurados)
				{
					log.debug("iterando asegurado "+i+": ");
					log.debug("asegurado: "+asegurado);
					if((Integer)Integer.parseInt((String)asegurado.get("nmsituac"))>0)
					{
						log.debug("es asegurado (situac>0)");
						Map<String,String>paramsValositAseguradoIterado=new LinkedHashMap<String,String>(0);
						paramsValositAseguradoIterado.put("pv_cdunieco_i", smap1.get("cdunieco"));
						paramsValositAseguradoIterado.put("pv_nmpoliza_i", smap1.get("nmpoliza"));
						paramsValositAseguradoIterado.put("pv_cdramo_i",   smap1.get("cdramo"));
						paramsValositAseguradoIterado.put("pv_estado_i",   smap1.get("estado"));
						paramsValositAseguradoIterado.put("pv_nmsituac_i", (String)asegurado.get("nmsituac"));
						log.debug("paramsValositAseguradoIterado: "+paramsValositAseguradoIterado);
						Map<String,Object>valositAseguradoIterado=kernelManager.obtieneValositSituac(paramsValositAseguradoIterado);
						log.debug("valositAseguradoIterado: "+valositAseguradoIterado);
						
						Map<String,Object>valositAseguradoIteradoTemp=new LinkedHashMap<String,Object>(0);
						//poner pv_ a los leidos
						Iterator it=valositAseguradoIterado.entrySet().iterator();
						while(it.hasNext())
						{
							Entry en=(Entry)it.next();
							valositAseguradoIteradoTemp.put("pv_"+(String)en.getKey(),en.getValue());//agregar pv_ a los anteriores
						}
						valositAseguradoIterado=valositAseguradoIteradoTemp;
						log.debug("se puso pv_");
						
						try
						{
							if(smap1.get("cdramo").equals("2"))
							{
								String cpanterior = (String) valositAseguradoIterado.get("pv_otvalor03");
								String cpnuevo    = parametros.get("pv_otvalor03");
								log.debug("compara "+cpanterior+" con "+cpnuevo
										+" para cdperson "+asegurado.get("cdperson"));
								if(!cpanterior.equalsIgnoreCase(cpnuevo))
								{
									String cdpersonAfectadoValosit = (String) asegurado.get("cdperson");
									log.debug("mdomicil borrar para cdperson "+cdpersonAfectadoValosit);
									
									Map<String,String> paramBorrarDomicil=new LinkedHashMap<String,String>(0);
									paramBorrarDomicil.put("pv_cdperson_i" , cdpersonAfectadoValosit);
									paramBorrarDomicil.put("pv_nmorddom_i" , null);
									paramBorrarDomicil.put("pv_msdomici_i" , null);
									paramBorrarDomicil.put("pv_nmtelefo_i" , null);
									paramBorrarDomicil.put("pv_cdpostal_i" , null);
									paramBorrarDomicil.put("pv_cdedo_i"    , null);
									paramBorrarDomicil.put("pv_cdmunici_i" , null);
									paramBorrarDomicil.put("pv_cdcoloni_i" , null);
									paramBorrarDomicil.put("pv_nmnumero_i" , null);
									paramBorrarDomicil.put("pv_nmnumint_i" , null);
									paramBorrarDomicil.put("pv_accion_i"   , "B");//borrar
									kernelManager.pMovMdomicil(paramBorrarDomicil);
								}
							}
							else if(smap1.get("cdramo").equals("4"))
							{
								String estadoanterior = (String) valositAseguradoIterado.get("pv_otvalor04");
								String ciudadanterior = (String) valositAseguradoIterado.get("pv_otvalor05");
								String estadonuevo    = parametros.get("pv_otvalor04");
								String ciudadnueva    = parametros.get("pv_otvalor05");
								log.debug("compara estado"+estadoanterior+" con "+estadonuevo
										+" para cdperson "+asegurado.get("cdperson"));
								log.debug("compara ciudad"+ciudadanterior+" con "+ciudadnueva
										+" para cdperson "+asegurado.get("cdperson"));
								if(!estadoanterior.equalsIgnoreCase(estadonuevo)
										||!ciudadanterior.equalsIgnoreCase(ciudadnueva)
										)
								{
									String cdpersonAfectadoValosit = (String) asegurado.get("cdperson");
									log.debug("mdomicil borrar para cdperson "+cdpersonAfectadoValosit);
									
									Map<String,String> paramBorrarDomicil=new LinkedHashMap<String,String>(0);
									paramBorrarDomicil.put("pv_cdperson_i" , cdpersonAfectadoValosit);
									paramBorrarDomicil.put("pv_nmorddom_i" , null);
									paramBorrarDomicil.put("pv_msdomici_i" , null);
									paramBorrarDomicil.put("pv_nmtelefo_i" , null);
									paramBorrarDomicil.put("pv_cdpostal_i" , null);
									paramBorrarDomicil.put("pv_cdedo_i"    , null);
									paramBorrarDomicil.put("pv_cdmunici_i" , null);
									paramBorrarDomicil.put("pv_cdcoloni_i" , null);
									paramBorrarDomicil.put("pv_nmnumero_i" , null);
									paramBorrarDomicil.put("pv_nmnumint_i" , null);
									paramBorrarDomicil.put("pv_accion_i"   , "B");//borrar
									kernelManager.pMovMdomicil(paramBorrarDomicil);
								}
							}
						}
						catch(Exception ex)
						{
							log.error("Error sin impacto funcional al comparar codigos postales",ex);
						}
						
						//agregar los del form a los leidos
						Iterator it2=parametros.entrySet().iterator();
						while(it2.hasNext())
						{
							Entry en=(Entry)it2.next();
							valositAseguradoIterado.put((String)en.getKey(),en.getValue());//tienen pv_ los del form
							//ya agregamos todos los nuevos en el mapa
						}
						log.debug("se agregaron los nuevos");
						
						//convertir a string el total
						Map<String,String>paramsNuevos=new LinkedHashMap<String,String>(0);
						it=valositAseguradoIterado.entrySet().iterator();
						while(it.hasNext())
						{
							Entry en=(Entry)it.next();
							paramsNuevos.put((String)en.getKey(),(String)en.getValue());
						}
						log.debug("se pasaron a string");
						
						paramsNuevos.put("pv_cdunieco", smap1.get("cdunieco"));
						paramsNuevos.put("pv_nmpoliza", smap1.get("nmpoliza"));
						paramsNuevos.put("pv_cdramo",   smap1.get("cdramo"));
						paramsNuevos.put("pv_estado",   smap1.get("estado"));
						paramsNuevos.put("pv_nmsituac", (String)asegurado.get("nmsituac"));
						log.debug("los actualizados seran: "+paramsNuevos);
						
						kernelManager.actualizaValoresSituaciones(paramsNuevos);
						
					}
					else
					{
						log.debug("no es asegurado (situac<=0)");
					}
					i++;
				}
			}
			else
			//actualizar uno
			{
				log.debug("se tiene individual");
				Map<String,String>paramsValositAsegurado=new LinkedHashMap<String,String>(0);
				paramsValositAsegurado.put("pv_cdunieco_i", smap1.get("cdunieco"));
				paramsValositAsegurado.put("pv_nmpoliza_i", smap1.get("nmpoliza"));
				paramsValositAsegurado.put("pv_cdramo_i",   smap1.get("cdramo"));
				paramsValositAsegurado.put("pv_estado_i",   smap1.get("estado"));
				paramsValositAsegurado.put("pv_nmsituac_i", smap1.get("nmsituac"));
				log.debug("paramsValositAsegurado: "+paramsValositAsegurado);
				Map<String,Object>valositAsegurado=kernelManager.obtieneValositSituac(paramsValositAsegurado);
				log.debug("valositAsegurado: "+valositAsegurado);
				
				Map<String,Object>valositAseguradoIterado=new LinkedHashMap<String,Object>(0);
				//poner pv_ al leido
				Iterator it=valositAsegurado.entrySet().iterator();
				while(it.hasNext())
				{
					Entry en=(Entry)it.next();
					valositAseguradoIterado.put("pv_"+(String)en.getKey(),en.getValue());//agregar pv_ a los anteriores
				}
				valositAsegurado=valositAseguradoIterado;
				log.debug("se puso pv_");
				
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
				
				paramsNuevos.put("pv_cdunieco", smap1.get("cdunieco"));
				paramsNuevos.put("pv_nmpoliza", smap1.get("nmpoliza"));
				paramsNuevos.put("pv_cdramo",   smap1.get("cdramo"));
				paramsNuevos.put("pv_estado",   smap1.get("estado"));
				paramsNuevos.put("pv_nmsituac", smap1.get("nmsituac"));
				log.debug("los actualizados seran: "+paramsNuevos);
				
				kernelManager.actualizaValoresSituaciones(paramsNuevos);
			}
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar valosit",ex);
			success=false;
		}
		log.debug(""
				+ "\n######                       ######"
				+ "\n###### pantalla valosit save ######"
				+ "\n###################################"
				+ "\n###################################"
				);
		return SUCCESS;
	}
	/*/////////////////////////////////////////*/
	////// guardar valosit situac o grupal //////
	/////////////////////////////////////////////
	
	/////////////////////////////////
	////// getters and setters //////
	/*/////////////////////////////*/
	public void setKernelManager(KernelManagerSustituto km)
	{
		this.kernelManager=km;
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

	public Map<String, Object> getOmap1() {
		return omap1;
	}

	public void setOmap1(Map<String, Object> omap1) {
		this.omap1 = omap1;
	}

	public Map<String, Object> getOmap2() {
		return omap2;
	}

	public void setOmap2(Map<String, Object> omap2) {
		this.omap2 = omap2;
	}

	public Map<String, Object> getOmap3() {
		return omap3;
	}

	public void setOmap3(Map<String, Object> omap3) {
		this.omap3 = omap3;
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

	public List<Map<String, Object>> getOlist1() {
		return olist1;
	}

	public void setOlist1(List<Map<String, Object>> olist1) {
		this.olist1 = olist1;
	}

	public List<Map<String, Object>> getOlist2() {
		return olist2;
	}

	public void setOlist2(List<Map<String, Object>> olist2) {
		this.olist2 = olist2;
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

	public Item getItem3() {
		return item3;
	}

	public void setItem3(Item item3) {
		this.item3 = item3;
	}

	public Item getItem4() {
		return item4;
	}

	public void setItem4(Item item4) {
		this.item4 = item4;
	}

	public Map<String, String> getSpanel1() {
		return spanel1;
	}

	public void setSpanel1(Map<String, String> spanel1) {
		this.spanel1 = spanel1;
	}

	public Map<String, String> getSpanel2() {
		return spanel2;
	}

	public void setSpanel2(Map<String, String> spanel2) {
		this.spanel2 = spanel2;
	}

	public Map<String, String> getSpanel3() {
		return spanel3;
	}

	public void setSpanel3(Map<String, String> spanel3) {
		this.spanel3 = spanel3;
	}

	public Map<String, Object> getOpanel1() {
		return opanel1;
	}

	public void setOpanel1(Map<String, Object> opanel1) {
		this.opanel1 = opanel1;
	}

	public Map<String, Object> getOpanel2() {
		return opanel2;
	}

	public void setOpanel2(Map<String, Object> opanel2) {
		this.opanel2 = opanel2;
	}

	public Map<String, Object> getOpanel3() {
		return opanel3;
	}

	public void setOpanel3(Map<String, Object> opanel3) {
		this.opanel3 = opanel3;
	}

	public boolean isSuccess() {
		return success;
	}

	public Map<String, String> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, String> parametros) {
		this.parametros = parametros;
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}
	
}