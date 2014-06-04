package mx.com.gseguros.portal.emision.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.externo.service.StoredProceduresManager;
import mx.com.gseguros.portal.consultas.service.ConsultasManager;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.ObjetoBD;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.ws.nada.client.axis2.VehicleStub.VehicleValue_Struc;
import mx.com.gseguros.ws.nada.service.NadaService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class CotizacionAction extends PrincipalCoreAction
{

	private static final long       serialVersionUID = 3237792502541753915L;
	private static Logger           log              = Logger.getLogger(CotizacionAction.class);
	private static SimpleDateFormat renderFechas     = new SimpleDateFormat("dd/MM/yyyy"); 
	
	private transient CatalogosManager       catalogosManager;
	private ConsultasManager                 consultasManager;
	private String                           error;
	private Map<String,Item>                 imap;
	private transient KernelManagerSustituto kernelManager;
	private PantallasManager                 pantallasManager;
	private List<Map<String,String>>         slist1;
	private List<Map<String,String>>         slist2;
	private Map<String,String>               smap1;
	private Map<String,String>               params;
	private StoredProceduresManager          storedProceduresManager;
	private NadaService          			 nadaService;
	private boolean                          success;
	
	/////////////////////////////////
	////// cotizacion dinamica //////
	/*/////////////////////////////*/
	public String pantallaCotizacion()
	{
		this.session=ActionContext.getContext().getSession();
		log.debug("\n"
				+ "\n################################"
				+ "\n################################"
				+ "\n###### pantallaCotizacion ######"
				+ "\n######                    ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("session: "+session);
		
		GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
		gc.setEsMovil(session!=null&&session.containsKey("ES_MOVIL")&&((Boolean)session.get("ES_MOVIL"))==true);
		if(!gc.isEsMovil() && smap1.containsKey("movil"))
		{
			gc.setEsMovil(true);
		}
		
		UserVO usuario  = (UserVO) session.get("USUARIO");
		String cdtipsit = smap1.get("cdtipsit");
		
		String ntramite;
		String cdunieco=null;
		String cdramo=null;
		
		smap1.put("user",usuario.getUser());
		
		/////////////////////////////////////////////////////////
		////// poner valores a ntramite, cdunieco y cdramo //////
        if(smap1.get("ntramite")!=null)
        //cuando viene ntramite tambien vienen cdunieco y cdramo
        {
        	ntramite = smap1.get("ntramite");
        	cdunieco = smap1.get("cdunieco");
        	cdramo   = smap1.get("cdramo");
        }
        else
        //cuando no hay ntramite es porque esta cotizando un agente por fuera,
        //y se obtiene cdunieco y cdramo por medio de ese agente
        {
        	try
        	{
        		DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usuario.getUser(),cdtipsit);//cdramo
        		ntramite="";
        		cdunieco=datUsu.getCdunieco();
        		if(StringUtils.isBlank(smap1.get("cdramo")))
        		{
        			cdramo=datUsu.getCdramo();
        		}
        		else
        		{
        			cdramo=smap1.get("cdramo");
        		}
        		smap1.put("ntramite","");
        		smap1.put("cdunieco",cdunieco);
        		smap1.put("cdramo",cdramo);
        	}
        	catch(Exception ex)
        	{
        		log.error("error al obtener los datos del agente",ex);
        	}
        }
		////// poner valores a ntramite, cdunieco y cdramo //////
        /////////////////////////////////////////////////////////
        
        ////////////////////////////////////////
        ////// obtener campos de tatrisit //////
        gc.setCdtipsit(cdtipsit);
        
        List<ComponenteVO>camposAgrupados    = new ArrayList<ComponenteVO>(0);
        List<ComponenteVO>camposIndividuales = new ArrayList<ComponenteVO>(0);
        
        imap = new HashMap<String,Item>();
        
        try
        {
	        List<ComponenteVO>tatrisit=kernelManager.obtenerTatrisit(cdtipsit,usuario.getUser());
        	
	        List<ComponenteVO>temp=new ArrayList<ComponenteVO>();
	        for(ComponenteVO tatriIte:tatrisit)
			{
	        	if(tatriIte.getSwpresen().equalsIgnoreCase("S"))
	        	{
	        		temp.add(tatriIte);
	        	}
			}
	        tatrisit=temp;
	        
			for(ComponenteVO tatriIte:tatrisit)
			{
				////////////////////
				////// custom //////
				if(cdtipsit.equalsIgnoreCase("SL")||cdtipsit.equalsIgnoreCase("SN"))
				{
					if(tatriIte.getCatalogo()!=null&&
							(tatriIte.getCatalogo().equalsIgnoreCase("2CODPOS")
									||tatriIte.getCatalogo().equalsIgnoreCase("2CODPOSN")))//codigo postal
					{
						tatriIte.setCatalogo("");
					}
				}
				////// custom //////
				////////////////////
					
				if(tatriIte.getSwsuscri().equalsIgnoreCase("S"))//S=individual
				{
					tatriIte.setColumna(Constantes.SI);
					camposIndividuales.add(tatriIte);
				}
				else
				{
					camposAgrupados.add(tatriIte);
				}
			}

			gc.generaParcial(camposAgrupados);
			imap.put("camposAgrupados" , gc.getItems());
			imap.put("fieldsAgrupados" , gc.getFields());
			
			if(camposIndividuales.size()>0)
			{
				gc.generaParcialConEditor(camposIndividuales);
				imap.put("itemsIndividuales"  , gc.getItems());
				imap.put("camposIndividuales" , gc.getColumns());
				imap.put("fieldsIndividuales" , gc.getFields());
			}
			else
			{
				imap.put("itemsIndividuales"  , null);
				imap.put("camposIndividuales" , null);
				imap.put("fieldsIndividuales" , null);
			}
			
			List<ComponenteVO>validaciones=pantallasManager.obtenerComponentes(
					null, null, cdramo, cdtipsit, null, null, "VALIDACIONES_COTIZA", gc.isEsMovil()?"MOVIL":"DESKTOP", null);
			if(validaciones.size()>0)
			{
				gc.generaComponentes(validaciones, true, false, false, false, false, true);
				imap.put("validacionCustomButton" , gc.getButtons());
			}
			else
			{
				imap.put("validacionCustomButton" , null);
			}
			
			List<ComponenteVO>modeloExtra = pantallasManager.obtenerComponentes(
					null, null, cdramo, cdtipsit, null, null, "VALIDACIONES_COTIZA", "MODELO", null);
			gc.generaComponentes(modeloExtra, true, true, true, true, true, false);
			if(modeloExtra.size()>0)
			{
				imap.put("modeloExtraFields"  , gc.getFields());
				imap.put("modeloExtraColumns" , gc.getColumns());
				imap.put("modeloExtraItems"   , gc.getItems());
			}
			else
			{
				imap.put("modeloExtraFields"  , null);
				imap.put("modeloExtraColumns" , null);
				imap.put("modeloExtraItems"   , null);
			}
        }
        catch(Exception ex)
        {
        	log.error("error al obtener los campos de cotizacion",ex);
        }
        
		log.debug("camposAgrupados: "+camposAgrupados);
		log.debug("camposIndividuales: "+camposIndividuales);
        ////// obtener campos de tatrisit //////
        ////////////////////////////////////////
        
		////// parches por situacion //////
		if(cdtipsit.equalsIgnoreCase("AF"))
		{
			try
			{
				List<ComponenteVO>cdatribusDerechos=pantallasManager.obtenerComponentes(null, null, cdramo, cdtipsit, null, null, "COTIZACION_CUSTOM", "CDATRIBU_DERECHO", null);
				if(cdatribusDerechos.size()>0)
				{
					String cdatribusConcatenados="";
					for(int i=0;i<cdatribusDerechos.size();i++)
					{
						cdatribusConcatenados=cdatribusConcatenados+cdatribusDerechos.get(i).getNameCdatribu();
						if(i<cdatribusDerechos.size()-1)
						{
							cdatribusConcatenados=cdatribusConcatenados+",";
						}
					}
					smap1.put("CDATRIBU_DERECHO",cdatribusConcatenados);
				}
				else
				{
					smap1.put("CDATRIBU_DERECHO",null);
				}
			}
			catch(Exception ex)
			{
				log.error("error al obtener CDATRIBU_DERECHO para AF",ex);
				smap1.put("CDATRIBU_DERECHO",null);
			}
		}
		////// parches por situacion //////
		
		log.debug("\n"
				+ "\n######                    ######"
				+ "\n###### pantallaCotizacion ######"
				+ "\n################################"
				+ "\n################################"
				);
		return gc.isEsMovil() ? "success_mobile" : SUCCESS;
	}
	
	public String webServiceNada()
	{
		log.info(""
				+ "\n############################"
				+ "\n###### webServiceNada ######"
				);
		log.info("smap1: "+smap1);
		
		String  vim                  = null;
		success                      = true;
		VehicleValue_Struc datosAuto = null;
		
		//revisar numero de serie
		if(success)
		{
			
			success = smap1!=null&&StringUtils.isNotBlank(vim=smap1.get("vim"));
			if(!success)
			{
				error="No se recibi&oacute; el n&uacute;mero de serie";
				log.error(error);
			}
		}
		
		//llamar web service
		if(success)
		{
			datosAuto = nadaService.obtieneDatosAutomovilNADA(vim);
			success     = datosAuto!=null;
			if(!success)
			{
				error="No se encontr&oacute; informaci&oacute;n para el n&uacute;mero de serie";
				log.error(error);
			}
		}
		
		//datos regresados
		if(success)
		{
			smap1.put("AUTO_ANIO"        , datosAuto.getVehicleYear()+"");
			smap1.put("AUTO_DESCRIPCION" , datosAuto.getSeriesDescr());
			smap1.put("AUTO_PRECIO"      , datosAuto.getAvgTradeIn().toString());
			smap1.put("AUTO_MARCA"       , datosAuto.getMakeDescr());
		}
		
		log.info(""
				+ "\n###### webServiceNada ######"
				+ "\n############################"
				);
		return SUCCESS;
	}

	public String pantallaCotizacionDemo() {
		
		/**
		 * TODO: Eliminar codigo de ejemplo para el WS de NADA
		 * 1GCHK23275F895304
		 
		VehicleValue_Struc datosAuto= nadaService.obtieneDatosAutomovilNADA("1GCHK23275F895304");
		if(datosAuto != null){
			logger.debug("Resultados....");	
			logger.debug("getVehicleYear: "+datosAuto.getVehicleYear());	
			logger.debug("getMakeDescr: "+datosAuto.getMakeDescr());	
			logger.debug("getSeriesDescr: "+datosAuto.getSeriesDescr());	
			logger.debug("getWeight: "+datosAuto.getWeight());	
		}
		*/
		
		this.session=ActionContext.getContext().getSession();
		log.debug("\n"
				+ "\n####################################"
				+ "\n####################################"
				+ "\n###### pantallaCotizacionDemo ######"
				+ "\n######                        ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("session: "+session);
		
		GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
		gc.setEsMovil(session!=null&&session.containsKey("ES_MOVIL")&&((Boolean)session.get("ES_MOVIL"))==true);
		if(!gc.isEsMovil() && smap1.containsKey("movil"))
		{
			gc.setEsMovil(true);
		}
		
		UserVO usuario  = (UserVO) session.get("USUARIO");
		String cdtipsit = smap1.get("cdtipsit");
		
		String ntramite=null;
		String cdunieco=null;
		String cdramo=null;
		
		smap1.put("user",usuario.getUser());
		
		/////////////////////////////////////////////////////////
		////// poner valores a ntramite, cdunieco y cdramo //////
        if(smap1.get("ntramite")!=null)
        //cuando viene ntramite tambien vienen cdunieco y cdramo
        {
        	ntramite = smap1.get("ntramite");
        	cdunieco = smap1.get("cdunieco");
        	cdramo   = smap1.get("cdramo");
        }
        else
        //cuando no hay ntramite es porque esta cotizando un agente por fuera,
        //y se obtiene cdunieco y cdramo por medio de ese agente
        {
        	try
        	{
        		DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usuario.getUser(),cdtipsit);//cdramo
        		ntramite="";
        		cdunieco=datUsu.getCdunieco();
        		if(StringUtils.isBlank(smap1.get("cdramo")))
        		{
        			cdramo=datUsu.getCdramo();
        		}
        		else
        		{
        			cdramo=smap1.get("cdramo");
        		}
        		smap1.put("ntramite","");
        		smap1.put("cdunieco",cdunieco);
        		smap1.put("cdramo",cdramo);
        	}
        	catch(Exception ex)
        	{
        		log.error("error al obtener los datos del agente",ex);
        	}
        }
		////// poner valores a ntramite, cdunieco y cdramo //////
        /////////////////////////////////////////////////////////
        
        ////////////////////////////////////////
        ////// obtener campos de tatrisit //////
        gc.setCdtipsit(cdtipsit);
        
        //List<ComponenteVO>camposAgrupados    = new ArrayList<ComponenteVO>(0);
        List<ComponenteVO>camposIndividuales = new ArrayList<ComponenteVO>(0);
        
        imap = new HashMap<String,Item>();
        
		params =  new HashMap<String,String>();
		params.put("PV_CDPANTALLA_I", "1");
		params.put("PV_CDRAMO_I", "16");
		params.put("PV_CDTIPSIT_I", "AF");
		
		Map<String,String> result = null;
		try {
			result = pantallasManager.obtienePantalla(params);
		} catch (Exception e) {
			log.error("Error al obtener codigo de pantalla para pantalla: " + params, e);
		}
		
		logger.debug("variablesGeneradas: " + result.get("DSSTORES"));
		logger.debug("panelGenerado: " + result.get("DSARCHIVOSEC"));
		
		smap1.put("variablesGeneradas", result.get("DSSTORES"));
		smap1.put("panelGenerado", result.get("DSARCHIVOSEC"));
        try
        {
			if(camposIndividuales.size()>0)
			{
				gc.generaParcialConEditor(camposIndividuales);
				imap.put("itemsIndividuales"  , gc.getItems());
				imap.put("camposIndividuales" , gc.getColumns());
				imap.put("fieldsIndividuales" , gc.getFields());
			}
			else
			{
				imap.put("itemsIndividuales"  , null);
				imap.put("camposIndividuales" , null);
				imap.put("fieldsIndividuales" , null);
			}
			
			List<ComponenteVO>validaciones=pantallasManager.obtenerComponentes(
					null, null, cdramo, cdtipsit, null, null, "VALIDACIONES_COTIZA", gc.isEsMovil()?"MOVIL":"DESKTOP", null);
			if(validaciones.size()>0)
			{
				gc.generaComponentes(validaciones, true, false, false, false, false, true);
				imap.put("validacionCustomButton" , gc.getButtons());
			}
			else
			{
				imap.put("validacionCustomButton" , null);
			}
			
			List<ComponenteVO>modeloExtra = pantallasManager.obtenerComponentes(
					null, null, cdramo, cdtipsit, null, null, "VALIDACIONES_COTIZA", "MODELO", null);
			gc.generaComponentes(modeloExtra, true, true, true, true, true, false);
			if(modeloExtra.size()>0)
			{
				imap.put("modeloExtraFields"  , gc.getFields());
				imap.put("modeloExtraColumns" , gc.getColumns());
				imap.put("modeloExtraItems"   , gc.getItems());
			}
			else
			{
				imap.put("modeloExtraFields"  , null);
				imap.put("modeloExtraColumns" , null);
				imap.put("modeloExtraItems"   , null);
			}
        }
        catch(Exception ex)
        {
        	log.error("error al obtener los campos de cotizacion",ex);
        }
        
		log.debug("camposIndividuales: "+camposIndividuales);
        ////// obtener campos de tatrisit //////
        ////////////////////////////////////////
		
		//Obtenemos la edad m�xima para la cotizacion:
		/*
        try {
        	smap1.put("edadMaximaCotizacion",
        			catalogosManager.obtieneCantidadMaxima(cdramo, cdtipsit, TipoTramite.POLIZA_NUEVA, Rango.ANIOS, Validacion.EDAD_MAX_COTIZACION));
        } catch(Exception e) {
        	log.error("Error al obtener la edad m�xima de cotizaci�n", e);
        	smap1.put("edadMaximaCotizacion", "0");
        }
        */
        
		log.debug("\n"
				+ "\n######                        ######"
				+ "\n###### pantallaCotizacionDemo ######"
				+ "\n####################################"
				+ "\n####################################"
				);
		return gc.isEsMovil() ? "success_mobile" : SUCCESS;
	}
	/*/////////////////////////////*/
	////// cotizacion dinamica //////
	/////////////////////////////////

	/////////////////////
	////// cotizar //////
	/*/////////////////*/
	public String cotizar()
	{
		log.debug("\n"
				+ "\n###############################"
				+ "\n###############################"
				+ "\n######      cotizar      ######"
				+ "\n######                   ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("slist1: "+slist1);
		
		this.session=ActionContext.getContext().getSession();
		
		try
		{
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String cdtipsit = smap1.get("cdtipsit");
			
			UserVO usuario    = (UserVO)session.get("USUARIO");
			String user       = usuario.getUser();
			String cdelemento = usuario.getEmpresa().getElementoId();
			
			String nmpoliza = slist1.get(0).get("nmpoliza");
			String feini    = slist1.get(0).get("feini");
			String fefin    = slist1.get(0).get("fefin");
			
			Date fechaHoy = new Date();
			
			boolean conIncisos = StringUtils.isNotBlank(smap1.get("conincisos"))&&smap1.get("conincisos").equals("si");
			
			////////////////////////////////
			////// si no hay nmpoliza //////
			if(nmpoliza==null||nmpoliza.length()==0)
			{
				WrapperResultados wrapperNumeroPoliza = kernelManager.calculaNumeroPoliza(cdunieco,cdramo,"W");
				nmpoliza = (String)wrapperNumeroPoliza.getItemMap().get("NUMERO_POLIZA");
			}
			////// si no hay nmpoliza //////
			////////////////////////////////
			
			smap1.put("nmpoliza",nmpoliza);//salida
			
			//////////////////////
            ////// mpolizas //////
            Map<String,String>mapaMpolizas=new HashMap<String,String>(0);
            mapaMpolizas.put("pv_cdunieco"  , cdunieco);
            mapaMpolizas.put("pv_cdramo"    , cdramo);
            mapaMpolizas.put("pv_estado"    , "W");
            mapaMpolizas.put("pv_nmpoliza"  , nmpoliza);
            mapaMpolizas.put("pv_nmsuplem"  , "0");
            mapaMpolizas.put("pv_status"    , "V");
            mapaMpolizas.put("pv_swestado"  , "0");
            mapaMpolizas.put("pv_nmsolici"  , null);
            mapaMpolizas.put("pv_feautori"  , null);
            mapaMpolizas.put("pv_cdmotanu"  , null);
            mapaMpolizas.put("pv_feanulac"  , null);
            mapaMpolizas.put("pv_swautori"  , "N");
            mapaMpolizas.put("pv_cdmoneda"  , "001");
            mapaMpolizas.put("pv_feinisus"  , null);
            mapaMpolizas.put("pv_fefinsus"  , null);
            mapaMpolizas.put("pv_ottempot"  , "R");
            mapaMpolizas.put("pv_feefecto"  , feini);
            mapaMpolizas.put("pv_hhefecto"  , "12:00");
            mapaMpolizas.put("pv_feproren"  , fefin);
            mapaMpolizas.put("pv_fevencim"  , null);
            mapaMpolizas.put("pv_nmrenova"  , "0");
            mapaMpolizas.put("pv_ferecibo"  , null);
            mapaMpolizas.put("pv_feultsin"  , null);
            mapaMpolizas.put("pv_nmnumsin"  , "0");
            mapaMpolizas.put("pv_cdtipcoa"  , "N");
            mapaMpolizas.put("pv_swtarifi"  , "A");
            mapaMpolizas.put("pv_swabrido"  , null);
            mapaMpolizas.put("pv_feemisio"  , renderFechas.format(fechaHoy));
            mapaMpolizas.put("pv_cdperpag"  , "12");
            mapaMpolizas.put("pv_nmpoliex"  , null);
            mapaMpolizas.put("pv_nmcuadro"  , "P1");
            mapaMpolizas.put("pv_porredau"  , "100");
            mapaMpolizas.put("pv_swconsol"  , "S");
            mapaMpolizas.put("pv_nmpolant"  , null);
            mapaMpolizas.put("pv_nmpolnva"  , null);
            mapaMpolizas.put("pv_fesolici"  , renderFechas.format(fechaHoy));
            mapaMpolizas.put("pv_cdramant"  , null);
            mapaMpolizas.put("pv_cdmejred"  , null);
            mapaMpolizas.put("pv_nmpoldoc"  , null);
            mapaMpolizas.put("pv_nmpoliza2" , null);
            mapaMpolizas.put("pv_nmrenove"  , null);
            mapaMpolizas.put("pv_nmsuplee"  , null);
            mapaMpolizas.put("pv_ttipcamc"  , null);
            mapaMpolizas.put("pv_ttipcamv"  , null);
            mapaMpolizas.put("pv_swpatent"  , null);
            mapaMpolizas.put("pv_accion"    , "U");
            kernelManager.insertaMaestroPolizas(mapaMpolizas);
            ////// mpolizas //////
            //////////////////////
			
            String llaveRol="";
            String llaveSexo="";
            String llaveFenacimi="DATE";
            String llaveCodPostal="";
            
            if(conIncisos)
            {
	            ////////////////////////////////
	            ////// ordenar al titular //////
	            
	            ////// 1. indicar para la situacion el indice //////
	            try {
	            	LinkedHashMap<String,Object>p=new LinkedHashMap<String,Object>();
	            	p.put("cdtipsit",cdtipsit);
	            	Map<String,String>atributos=consultasManager.consultaDinamica(ObjetoBD.OBTIENE_ATRIBUTOS, p).get(0);
	            	if(atributos.get("PARENTESCO") != null) {
	            		llaveRol=atributos.get("PARENTESCO");
	                	if(llaveRol.length()==1) {
	                		llaveRol="0"+llaveRol;
	                	}
	                	llaveRol="parametros.pv_otvalor"+llaveRol;
	            	}
	            	if(atributos.get("SEXO") != null) {
	            		llaveSexo=atributos.get("SEXO");
	            		if(llaveSexo.length()==1) {
	                		llaveSexo="0"+llaveSexo;
	                	}
	                	llaveSexo="parametros.pv_otvalor"+llaveSexo;
	            	}
	            	if(atributos.get("FENACIMI") != null) {
	            		llaveFenacimi=atributos.get("FENACIMI");
	                	if(llaveFenacimi.length()==1) {
	                		llaveFenacimi="0"+llaveFenacimi;
	                	}
	                	llaveFenacimi="parametros.pv_otvalor"+llaveFenacimi;
	            	}
	            	if(atributos.get("CODPOSTAL") != null) {
	            		llaveCodPostal=atributos.get("CODPOSTAL");
	                	if(llaveCodPostal.length()==1) {
	                		llaveCodPostal="0"+llaveCodPostal;
	                	}
	                	llaveCodPostal="parametros.pv_otvalor"+llaveCodPostal;
	            	}
	            } catch(Exception ex){
	            	log.error("error al obtener atributos", ex);
	            }
	            ////// 1. indicar para la situacion el indice //////
	            
	            ////// parche. Validar codigo postal //////
	            if(StringUtils.isNotBlank(llaveCodPostal)&&StringUtils.isNotBlank(slist1.get(0).get(llaveCodPostal)))
	            {
	            	LinkedHashMap<String,Object>paramsValues=new LinkedHashMap<String,Object>();
	            	paramsValues.put("param1",slist1.get(0).get(llaveCodPostal));
	            	paramsValues.put("param2",cdtipsit);
	            	storedProceduresManager.procedureVoidCall(ObjetoBD.VALIDA_CODPOSTAL_TARIFA.getNombre(), paramsValues, null);
	            }
	            //// parche. Validar codigo postal //////
	            
	            ////// 2. ordenar //////
	            int indiceTitular=-1;
	            for(int i=0;i<slist1.size();i++)
	            {
	            	if(slist1.get(i).get(llaveRol).equalsIgnoreCase("T"))
	            	{
	            		indiceTitular=i;
	            	}
	            }
	            List<Map<String,String>> temp    = new ArrayList<Map<String,String>>(0);
	            Map<String,String>       titular = slist1.get(indiceTitular);
	            temp.add(titular);
	            slist1.remove(indiceTitular);
	            temp.addAll(slist1);
	            slist1=temp;
	            ////// 2. ordenar //////
	            
	            ////// ordenar al titular //////
	            ////////////////////////////////
            }
            
            //////////////////////////////////////////
            ////// mpolisit y tvalosit iterados //////
            int contador=1;
            for(Map<String,String>inciso:slist1)
            {
            	//////////////////////////////
            	////// mpolisit iterado //////
            	Map<String,Object>mapaPolisitIterado=new HashMap<String,Object>(0);
                mapaPolisitIterado.put("pv_cdunieco_i"   , cdunieco);
                mapaPolisitIterado.put("pv_cdramo_i"     , cdramo);
                mapaPolisitIterado.put("pv_estado_i"     , "W");
                mapaPolisitIterado.put("pv_nmpoliza_i"   , nmpoliza);
                mapaPolisitIterado.put("pv_nmsituac_i"   , contador+"");
                mapaPolisitIterado.put("pv_nmsuplem_i"   , "0");
                mapaPolisitIterado.put("pv_status_i"     , "V");
                mapaPolisitIterado.put("pv_cdtipsit_i"   , cdtipsit);
                mapaPolisitIterado.put("pv_swreduci_i"   , null);
                mapaPolisitIterado.put("pv_cdagrupa_i"   , "1");
                mapaPolisitIterado.put("pv_cdestado_i"   , "0");
                mapaPolisitIterado.put("pv_fefecsit_i"   , renderFechas.parse(feini));
                mapaPolisitIterado.put("pv_fecharef_i"   , renderFechas.parse(feini));
                mapaPolisitIterado.put("pv_cdgrupo_i"    , null);
                mapaPolisitIterado.put("pv_nmsituaext_i" , null);
                mapaPolisitIterado.put("pv_nmsitaux_i"   , null);
                mapaPolisitIterado.put("pv_nmsbsitext_i" , null);
                mapaPolisitIterado.put("pv_cdplan_i"     , "1");
                mapaPolisitIterado.put("pv_cdasegur_i"   , "30");
                mapaPolisitIterado.put("pv_accion_i"     , "I");
                kernelManager.insertaPolisit(mapaPolisitIterado);
            	////// mpolisit iterado //////
            	//////////////////////////////
                
                //////////////////////////////
                ////// tvalosit iterado //////
                
                ////// 1. tvalosit base //////
                Map<String,String>mapaValositIterado=new HashMap<String,String>(0);
                mapaValositIterado.put("pv_cdunieco" , cdunieco);
                mapaValositIterado.put("pv_cdramo"   , cdramo);
                mapaValositIterado.put("pv_estado"   , "W");
                mapaValositIterado.put("pv_nmpoliza" , nmpoliza);
                mapaValositIterado.put("pv_nmsituac" , contador+"");
                mapaValositIterado.put("pv_nmsuplem" , "0");
                mapaValositIterado.put("pv_status"   , "V");
                mapaValositIterado.put("pv_cdtipsit" , cdtipsit);
                mapaValositIterado.put("pv_accion_i" , "I");
                ////// 1. tvalosit base //////
                
                ////// 2. tvalosit desde form //////
                for(Entry<String,String>en:inciso.entrySet())
                {
                	// p a r a m e t r o s . p v _ o t v a l o r 
                	//0 1 2 3 4 5 6 7 8 9 0 1
                	String key=en.getKey();
                	String value=en.getValue();
                	if(key.length()>11&&key.substring(0,11).equalsIgnoreCase("parametros."))
                	{
                		mapaValositIterado.put(key.substring(11),value);
                	}
                }
                ////// 2. tvalosit desde form //////
                
                ////// 3. completar faltantes //////
                for(int i=1;i<=50;i++)
                {
                	String key="pv_otvalor"+i;
                	if(i<10)
                	{
                		key="pv_otvalor0"+i;
                	}
                	if(!mapaValositIterado.containsKey(key))
                	{
                		mapaValositIterado.put(key,null);
                	}
                }
                ////// 3. completar faltantes //////
                
                ////// 4. custom //////
                if(cdtipsit.equals("SL"))
                {
                	mapaValositIterado.put("pv_otvalor11","S");
                	mapaValositIterado.put("pv_otvalor12","0");
                	mapaValositIterado.put("pv_otvalor13","21000");
                }
                else if(cdtipsit.equals("SN"))
                {
                	mapaValositIterado.put("pv_otvalor09","N");
                	mapaValositIterado.put("pv_otvalor10","N");
                	mapaValositIterado.put("pv_otvalor11","S");
                	mapaValositIterado.put("pv_otvalor12","0");
                	mapaValositIterado.put("pv_otvalor13","21000");
                	mapaValositIterado.put("pv_otvalor15","N");
                }
                else if(cdtipsit.equals("GB"))
                {
                	mapaValositIterado.put("pv_otvalor16",mapaValositIterado.get("pv_otvalor01"));
                }
                ////// 4. custom //////
                
                kernelManager.insertaValoresSituaciones(mapaValositIterado);
                ////// tvalosit iterado //////
                //////////////////////////////
                
                contador++;
            }
            ////// mpolisit y tvalosit iterados //////
            //////////////////////////////////////////
	        
            /////////////////////////////
            ////// clonar personas //////
            contador=1;
            for(Map<String,String> inciso : slist1)
            {
                Map<String,Object> mapaClonPersonaIterado=new HashMap<String,Object>(0);
                mapaClonPersonaIterado.put("pv_cdelemen_i"  , cdelemento);
                mapaClonPersonaIterado.put("pv_cdunieco_i"  , cdunieco);
                mapaClonPersonaIterado.put("pv_cdramo_i"    , cdramo);
                mapaClonPersonaIterado.put("pv_estado_i"    , "W");
                mapaClonPersonaIterado.put("pv_nmpoliza_i"  , nmpoliza);
                mapaClonPersonaIterado.put("pv_nmsituac_i"  , contador+"");
                mapaClonPersonaIterado.put("pv_cdtipsit_i"  , cdtipsit);
                mapaClonPersonaIterado.put("pv_fecha_i"     , fechaHoy);
                mapaClonPersonaIterado.put("pv_cdusuario_i" , user);
                mapaClonPersonaIterado.put("pv_p_nombre"    , inciso.get("nombre"));
                mapaClonPersonaIterado.put("pv_s_nombre"    , inciso.get("nombre2"));
                mapaClonPersonaIterado.put("pv_apellidop"   , inciso.get("apat"));
                mapaClonPersonaIterado.put("pv_apellidom"   , inciso.get("amat"));
                mapaClonPersonaIterado.put("pv_sexo"        , inciso.containsKey(llaveSexo)?inciso.get(llaveSexo):llaveSexo);
                mapaClonPersonaIterado.put("pv_fenacimi"    , inciso.containsKey(llaveFenacimi)?
                		renderFechas.parse(inciso.get(llaveFenacimi)):(
                				llaveFenacimi.equalsIgnoreCase("DATE")?
                						fechaHoy :
                							renderFechas.parse(llaveFenacimi)));
                mapaClonPersonaIterado.put("pv_parentesco"  , inciso.containsKey(llaveRol)?inciso.get(llaveRol):llaveRol);
                kernelManager.clonaPersonas(mapaClonPersonaIterado);
                contador++;
            }
            ////// clonar personas //////
            /////////////////////////////
            
            ////////////////////////
            ////// coberturas //////
            /*////////////////////*/
            Map<String,String> mapCoberturas=new HashMap<String,String>(0);
            mapCoberturas.put("pv_cdunieco_i" , cdunieco);
            mapCoberturas.put("pv_cdramo_i"   , cdramo);
            mapCoberturas.put("pv_estado_i"   , "W");
            mapCoberturas.put("pv_nmpoliza_i" , nmpoliza);
            mapCoberturas.put("pv_nmsituac_i" , "0");
            mapCoberturas.put("pv_nmsuplem_i" , "0");
            mapCoberturas.put("pv_cdgarant_i" , "TODO");
            mapCoberturas.put("pv_cdtipsup_i" , "1");
            kernelManager.coberturas(mapCoberturas);
            /*////////////////////*/
            ////// coberturas //////
            ////////////////////////
            
            //////////////////////////
            ////// TARIFICACION //////
            /*//////////////////////*/
            Map<String,String> mapaTarificacion=new HashMap<String,String>(0);
            mapaTarificacion.put("pv_cdusuari_i" , user);
            mapaTarificacion.put("pv_cdelemen_i" , cdelemento);
            mapaTarificacion.put("pv_cdunieco_i" , cdunieco);
            mapaTarificacion.put("pv_cdramo_i"   , cdramo);
            mapaTarificacion.put("pv_estado_i"   , "W");
            mapaTarificacion.put("pv_nmpoliza_i" , nmpoliza);
            mapaTarificacion.put("pv_nmsituac_i" , "0");
            mapaTarificacion.put("pv_nmsuplem_i" , "0");
            mapaTarificacion.put("pv_cdtipsit_i" , cdtipsit);
            kernelManager.ejecutaASIGSVALIPOL(mapaTarificacion);
            /*//////////////////////*/
            ////// TARIFICACION //////
            //////////////////////////
            
            ///////////////////////////////////
            ////// Generacion cotizacion //////
            /*///////////////////////////////*/
            Map<String,String> mapaDuroResultados=new HashMap<String,String>(0);
            mapaDuroResultados.put("pv_cdusuari_i" , user);
            mapaDuroResultados.put("pv_cdunieco_i" , cdunieco);
            mapaDuroResultados.put("pv_cdramo_i"   , cdramo);
            mapaDuroResultados.put("pv_estado_i"   , "W");
            mapaDuroResultados.put("pv_nmpoliza_i" , nmpoliza);
            mapaDuroResultados.put("pv_cdelemen_i" , cdelemento);
            mapaDuroResultados.put("pv_cdtipsit_i" , cdtipsit);
            List<Map<String,String>> listaResultados=kernelManager.obtenerResultadosCotizacion2(mapaDuroResultados);
            log.debug("listaResultados: "+listaResultados);
            /*///////////////////////////////*/
            ////// Generacion cotizacion //////
            ///////////////////////////////////
            
            ////////////////////////////////
            ////// Agrupar resultados //////
            /*
            NMSUPLEM=0,
			FEFECSIT=13/01/2014,
			NMPOLIZA=3853,
			MNPRIMA=4571.92,           <--2
			CDPERPAG=7,                <--1
			DSPLAN=Plus 500,           <--3
			FEVENCIM=13/01/2015,
			STATUS=V,
			NMSITUAC=3,
			ESTADO=W,
			DSPERPAG=DXN Catorcenal,   <--(1)
			CDCIAASEG=20,
			CDIDENTIFICA=2,
			CDTIPSIT=SL,
			FEEMISIO=13/01/2014,
			CDUNIECO=1,
			CDRAMO=2,
			CDPLAN=M,                  <--(3)
			DSUNIECO=PUEBLA
             */
            
            ////// 1. encontrar planes, formas de pago y algun nmsituac//////
            Map<String,String>formasPago = new LinkedHashMap<String,String>();
            Map<String,String>planes     = new LinkedHashMap<String,String>();
            String nmsituac="";
            for(Map<String,String>res:listaResultados)
            {
            	String cdperpag = res.get("CDPERPAG");
            	String dsperpag = res.get("DSPERPAG");
            	String cdplan   = res.get("CDPLAN");
            	String dsplan   = res.get("DSPLAN");
            	if(!formasPago.containsKey(cdperpag))
            	{
            		formasPago.put(cdperpag,dsperpag);
            	}
            	if(!planes.containsKey(cdplan))
            	{
            		planes.put(cdplan,dsplan);
            	}
            	nmsituac=res.get("NMSITUAC");
            }
            log.debug("formas de pago: "+formasPago);
            log.debug("planes: "+planes);
            ////// 1. encontrar planes y formas de pago //////
            
            ////// 2. crear formas de pago //////
            List<Map<String,String>>tarifas=new ArrayList<Map<String,String>>();
            for(Entry<String,String>formaPago:formasPago.entrySet())
            {
            	Map<String,String>tarifa=new HashMap<String,String>();
            	tarifa.put("CDPERPAG",formaPago.getKey());
            	tarifa.put("DSPERPAG",formaPago.getValue());
            	tarifa.put("NMSITUAC",nmsituac);
            	tarifas.add(tarifa);
            }
            log.debug("tarifas despues de formas de pago: "+tarifas);
            ////// 2. crear formas de pago //////
            
            ////// 3. crear planes //////
            for(Map<String,String>tarifa:tarifas)
            {
            	for(Entry<String,String>plan:planes.entrySet())
                {
                	tarifa.put("CDPLAN"+plan.getKey(),plan.getKey());
                	tarifa.put("DSPLAN"+plan.getKey(),plan.getValue());
                }
            }
            log.debug("tarifas despues de planes: "+tarifas);
            ////// 3. crear planes //////
            
            ////// 4. crear primas //////
            for(Map<String,String>res:listaResultados)
            {
            	String cdperpag = res.get("CDPERPAG");
            	String mnprima  = res.get("MNPRIMA");
            	String cdplan   = res.get("CDPLAN");
            	for(Map<String,String>tarifa:tarifas)
                {
            		if(tarifa.get("CDPERPAG").equals(cdperpag))
            		{
            			if(tarifa.containsKey("MNPRIMA"+cdplan))
            			{
            				log.debug("ya hay prima para "+cdplan+" en "+cdperpag+": "+tarifa.get("MNPRIMA"+cdplan));
            				tarifa.put("MNPRIMA"+cdplan,((Double)Double.parseDouble(tarifa.get("MNPRIMA"+cdplan))+(Double)Double.parseDouble(mnprima))+"");
            				log.debug("nueva: "+tarifa.get("MNPRIMA"+cdplan));
            			}
            			else
            			{
            				log.debug("primer prima para "+cdplan+" en "+cdperpag+": "+mnprima);
            				tarifa.put("MNPRIMA"+cdplan,mnprima);
            			}
            		}
                }
            }
            log.debug("tarifas despues de primas: "+tarifas);
            
            slist2=tarifas;
            ////// 4. crear primas //////
            
            ////// Agrupar resultados //////
            ////////////////////////////////
            
            ///////////////////////////////////
            ////// columnas para el grid //////
            List<ComponenteVO>tatriPlanes=new ArrayList<ComponenteVO>();
            
            ////// 1. forma de pago //////
            ComponenteVO tatriCdperpag=new ComponenteVO();
        	tatriCdperpag.setType(ComponenteVO.TIPO_GENERICO);
        	tatriCdperpag.setLabel("CDPERPAG");
        	tatriCdperpag.setTipoCampo(ComponenteVO.TIPOCAMPO_NUMERICO);
        	tatriCdperpag.setNameCdatribu("CDPERPAG");
        	
        	/*Map<String,String>mapaCdperpag=new HashMap<String,String>();
        	mapaCdperpag.put("OTVALOR10","CDPERPAG");
        	tatriCdperpag.setMapa(mapaCdperpag);*/
        	tatriPlanes.add(tatriCdperpag);
        	
        	ComponenteVO tatriDsperpag=new ComponenteVO();
        	tatriDsperpag.setType(ComponenteVO.TIPO_GENERICO);
        	tatriDsperpag.setLabel("Forma de pago");
        	tatriDsperpag.setTipoCampo(ComponenteVO.TIPOCAMPO_ALFANUMERICO);
        	tatriDsperpag.setNameCdatribu("DSPERPAG");
        	tatriDsperpag.setColumna(Constantes.SI);
        	
        	/*Map<String,String>mapaDsperpag=new HashMap<String,String>();
        	mapaDsperpag.put("OTVALOR08","S");
        	mapaDsperpag.put("OTVALOR10","DSPERPAG");
        	tatriDsperpag.setMapa(mapaDsperpag);*/
        	tatriPlanes.add(tatriDsperpag);
        	////// 1. forma de pago //////
        	
        	////// 2. nmsituac //////
        	ComponenteVO tatriNmsituac=new ComponenteVO();
        	tatriNmsituac.setType(ComponenteVO.TIPO_GENERICO);
        	tatriNmsituac.setLabel("NMSITUAC");
        	tatriNmsituac.setTipoCampo(ComponenteVO.TIPOCAMPO_NUMERICO);
        	tatriNmsituac.setNameCdatribu("NMSITUAC");
        	
        	/*Map<String,String>mapaNmsituac=new HashMap<String,String>();
        	mapaNmsituac.put("OTVALOR10","NMSITUAC");
        	tatriNmsituac.setMapa(mapaNmsituac);*/
        	tatriPlanes.add(tatriNmsituac);
        	////// 2. nmsituac //////
        	
        	////// 2. planes //////
            for(Entry<String,String>plan:planes.entrySet())
            {
            	////// prima
            	ComponenteVO tatriPrima=new ComponenteVO();
            	tatriPrima.setType(ComponenteVO.TIPO_GENERICO);
            	tatriPrima.setLabel(plan.getValue());
            	tatriPrima.setTipoCampo(ComponenteVO.TIPOCAMPO_PORCENTAJE);
            	tatriPrima.setColumna(Constantes.SI);
            	tatriPrima.setNameCdatribu("MNPRIMA"+plan.getKey());
            	tatriPrima.setRenderer("function(v)"
            			+ "{"
            			+ "    debug('valor:',v);"
            			+ "    v=v.toFixed(2);"
            			+ "    debug('valor fixed:',v);"
            			+ "    var v2='';"
            			+ "    var ultimoPunto=-3;"
            			+ "    for(var i=(v+'').length-1;i>=0;i--)"
            			+ "    {"
            			+ "        var digito=(v+'').charAt(i);"
            			+ "        if(digito=='.')"
            			+ "        {"
            			+ "            ultimoPunto=-2;"
            			+ "        }"
            			+ "        if(ultimoPunto>-3)"
            			+ "        {"
            			+ "            ultimoPunto=ultimoPunto+1;"
            			+ "        }"
            			+ "        if(ultimoPunto%3==0&&ultimoPunto>0)"
            			+ "        {"
            			+ "            digito=digito+',';"
            			+ "        }"
            			+ "        v2=digito+v2;"
            			+ "        if(i==0)"
            			+ "        {"
            			+ "            v2='$ '+v2;"
            			+ "        }"
            			+ "    }"
            			+ "    return v2;"
            			+ "}");
            	
            	/*Map<String,String>mapaPlan=new HashMap<String,String>();
            	mapaPlan.put("OTVALOR08","S");
            	mapaPlan.put("OTVALOR09","MONEY");
            	mapaPlan.put("OTVALOR10","MNPRIMA"+plan.getKey());
            	tatriPrima.setMapa(mapaPlan);*/
            	tatriPlanes.add(tatriPrima);
            	
            	////// cdplan
            	ComponenteVO tatriCdplan=new ComponenteVO();
             	tatriCdplan.setType(ComponenteVO.TIPO_GENERICO);
             	tatriCdplan.setLabel("CDPLAN"+plan.getKey());
             	tatriCdplan.setTipoCampo(ComponenteVO.TIPOCAMPO_ALFANUMERICO);
             	tatriCdplan.setNameCdatribu("CDPLAN"+plan.getKey());
             	tatriCdplan.setColumna(ComponenteVO.COLUMNA_OCULTA);
             	
             	/*Map<String,String>mapaCdplan=new HashMap<String,String>();
             	//mapaCdplan.put("OTVALOR08","H");
             	mapaCdplan.put("OTVALOR10","CDPLAN"+plan.getKey());
             	tatriCdplan.setMapa(mapaCdplan);*/
             	tatriPlanes.add(tatriCdplan);
             	
             	////// dsplan
             	ComponenteVO tatriDsplan=new ComponenteVO();
             	tatriDsplan.setType(ComponenteVO.TIPO_GENERICO);
             	tatriDsplan.setLabel("DSPLAN"+plan.getKey());
             	tatriDsplan.setTipoCampo(ComponenteVO.TIPOCAMPO_ALFANUMERICO);
             	tatriDsplan.setNameCdatribu("DSPLAN"+plan.getKey());
             	
             	/*Map<String,String>mapaDsplan=new HashMap<String,String>();
             	//mapaDsplan.put("OTVALOR08","H");
             	mapaDsplan.put("OTVALOR10","DSPLAN"+plan.getKey());
             	tatriDsplan.setMapa(mapaDsplan);*/
             	tatriPlanes.add(tatriDsplan);
            }
            ////// 2. planes //////
            
            GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
            gc.setEsMovil(session!=null&&session.containsKey("ES_MOVIL")&&((Boolean)session.get("ES_MOVIL"))==true);
            if(!gc.isEsMovil()&&smap1.containsKey("movil"))
            {
            	gc.setEsMovil(true);
            }
            gc.genera(tatriPlanes);
            
            String columnas = gc.getColumns().toString();
            // c o l u m n s : [
            //0 1 2 3 4 5 6 7 8
            smap1.put("columnas",columnas.substring(8));
            
            String fields = gc.getFields().toString();
            // f i e l d s : [
            //0 1 2 3 4 5 6 7
            smap1.put("fields",fields.substring(7));
            ////// columnas para el grid //////
            ///////////////////////////////////
            
			success=true;
		}
		catch(Exception ex)
		{
			log.debug("error al cotizar",ex);
			success=false;
			error=ex.getMessage();
		}
		
		log.debug("\n"
				+ "\n######                   ######"
				+ "\n######      cotizar      ######"
				+ "\n###############################"
				+ "\n###############################"
				);
		return SUCCESS;
	}
	/*/////////////////*/
	////// cotizar //////
	/////////////////////
	
	public String cargarCotizacion()
	{
		log.info(""
				+ "\n##############################"
				+ "\n###### cargarCotizacion ######"
				);
		log.info("smap1: "+smap1);
		success = true;
		
		String cdramo   = smap1.get("cdramo");
		String cdtipsit = smap1.get("cdtipsit");
		String nmpoliza = smap1.get("nmpoliza");
		log.info("cdramo: "+cdramo);
		log.info("cdtipsit: "+cdtipsit);
		log.info("nmpoliza: "+nmpoliza);
		
		//validar nmpoliza contra producto y situacion
		if(success)
		{
			try
			{
				LinkedHashMap<String,Object>paramsValidaCargarCotizacion=new LinkedHashMap<String,Object>();
				paramsValidaCargarCotizacion.put("param1" , cdramo);
				paramsValidaCargarCotizacion.put("param2" , cdtipsit);
				paramsValidaCargarCotizacion.put("param3" , nmpoliza);
				storedProceduresManager.procedureVoidCall(ObjetoBD.VALIDA_CARGAR_COTIZACION.getNombre(), paramsValidaCargarCotizacion, null);
			}
			catch(Exception ex)
			{
				log.error("error obtenido al validar carga de cotizacion",ex);
				error=ex.getMessage();
				success = false;
			}
		}
		//validar nmpoliza contra producto y situacion
		
		//recupera tvalosit
		if(success)
		{
			try
			{
				LinkedHashMap<String,Object>paramsObtenerTvalosit=new LinkedHashMap<String,Object>();
				paramsObtenerTvalosit.put("param1" , cdramo);
				paramsObtenerTvalosit.put("param2" , cdtipsit);
				paramsObtenerTvalosit.put("param3" , nmpoliza);
				slist1 = storedProceduresManager.procedureListCall(ObjetoBD.OBTIENE_TVALOSIT_COTIZACION.getNombre(), paramsObtenerTvalosit, null);
				for(Map<String,String>iInciso:slist1)
				{
					String iCdunieco = iInciso.get("CDUNIECO");
					String iEstado   = iInciso.get("ESTADO");
					String iNmsituac = iInciso.get("NMSITUAC");
					log.info("iCdunieco: "+iCdunieco);
					log.info("iEstado: "+iEstado);
					log.info("iNmsituac: "+iNmsituac);
					LinkedHashMap<String,Object>paramsObtenerMpersonaCotizacion=new LinkedHashMap<String,Object>();
					paramsObtenerMpersonaCotizacion.put("param1",iCdunieco);
					paramsObtenerMpersonaCotizacion.put("param2",cdramo);
					paramsObtenerMpersonaCotizacion.put("param3",iEstado);
					paramsObtenerMpersonaCotizacion.put("param4",nmpoliza);
					paramsObtenerMpersonaCotizacion.put("param5",iNmsituac);
					iInciso.putAll(storedProceduresManager.procedureMapCall(ObjetoBD.OBTIENE_MPERSONA_COTIZACION.getNombre(),
							paramsObtenerMpersonaCotizacion, null));
					//copiar OTVALORXX a PARAMETROS.PV_OTVALORXX
					Map<String,String>iIncisoOtvalor=new HashMap<String,String>();
					for(Entry<String,String>en:iInciso.entrySet())
					{
						String key   = en.getKey();
						String value = en.getValue();
						if(key.length()>"otvalor".length()
								&&key.substring(0, "otvalor".length()).equalsIgnoreCase("otvalor"))
						{
							iIncisoOtvalor.put(("PARAMETROS.PV_"+key).toLowerCase(),value);
						}
						else
						{
							iIncisoOtvalor.put(key.toLowerCase(),value);
						}
					}
					iInciso.putAll(iIncisoOtvalor);
				}
			}
			catch(Exception ex)
			{
				log.error("error al recuperar tvalosit",ex);
				error   = ex.getMessage();
				success = false;
			}
		}
		//recupera tvalosit
		
		log.info(""
				+ "\n###### cargarCotizacion ######"
				+ "\n##############################"
				);
		return SUCCESS;
	}
	
	///////////////////////////////
	////// getters y setters //////
	/*///////////////////////////*/
	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}

	public void setCatalogosManager(CatalogosManager catalogosManager) {
		this.catalogosManager = catalogosManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public Map<String, Item> getImap() {
		return imap;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<Map<String, String>> getSlist2() {
		return slist2;
	}

	public void setSlist2(List<Map<String, String>> slist2) {
		this.slist2 = slist2;
	}

	public void setConsultasManager(ConsultasManager consultasManager) {
		this.consultasManager = consultasManager;
	}

	public void setStoredProceduresManager(
			StoredProceduresManager storedProceduresManager) {
		this.storedProceduresManager = storedProceduresManager;
	}
	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public void setNadaService(NadaService nadaService) {
		this.nadaService = nadaService;
	}

}