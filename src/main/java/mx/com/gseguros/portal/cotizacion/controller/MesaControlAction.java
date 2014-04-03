package mx.com.gseguros.portal.cotizacion.controller;

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
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;

import org.apache.struts2.ServletActionContext;

public class MesaControlAction extends PrincipalCoreAction
{
	
	private static final long              serialVersionUID = -3398140781812652316L;
	private static org.apache.log4j.Logger log              = org.apache.log4j.Logger.getLogger(MesaControlAction.class);
	private static SimpleDateFormat        renderFechas     = new SimpleDateFormat("dd/MM/yyyy");
	private KernelManagerSustituto         kernelManager;
	private SiniestrosManager              siniestrosManager;
	private Map<String,String>             smap1;
	private Map<String,String>             smap2;
	private List<Map<String,String>>       slist1;
	private List<Map<String,String>>       slist2;
	private List<Map<String,Object>>       olist1;
	private List<GenericVO>                lista;
	private String                         msgResult;
	private boolean                        success;
	private Map<String,Item>               imap1;
	private String                         username;
	private String                         rol;
	private PantallasManager               pantallasManager;
	private String                         mensaje;
	private String                         errorMessage;
	
	public String principal()
	{
		log.debug(""
				+ "\n#######################################"
				+ "\n#######################################"
				+ "\n###### mesa de control principal ######"
				+ "\n######                           ######"
				);
		if(smap1==null)
		{
			smap1=new HashMap<String,String>(0);
		}
		if((!smap1.containsKey("pv_status_i")))
		{
			log.debug("pv_status_i: "+smap1.get("pv_status_i"));
			smap1.put("pv_status_i","-1");//valor default
		}
		log.debug(""
				+ "\n######                           ######"
				+ "\n###### mesa de control principal ######"
				+ "\n#######################################"
				+ "\n#######################################"
				);
		return SUCCESS;
	}
	
	public String loadTareas()
	{
		log.debug(""
				+ "\n########################################"
				+ "\n########################################"
				+ "\n###### mesa de control loadTareas ######"
				+ "\n######                            ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			//obtener el rol activo
			UserVO usu=(UserVO) session.get("USUARIO");
			String dsrol="";
			if(usu!=null
			    &&usu.getRolActivo()!=null
			    &&usu.getRolActivo().getObjeto()!=null
			    &&usu.getRolActivo().getObjeto().getValue()!=null)
			{
			    dsrol=usu.getRolActivo().getObjeto().getValue();
			}
			log.debug("rol activo: "+dsrol);
			//!obtener el rol activo
			
			/////////////////////////////////////////////////////////
			////// para la nueva lectura de tareas con filtros //////
			/*/////////////////////////////////////////////////////*/
			if(smap1==null)
			{
				smap1=new LinkedHashMap<String,String>(0);
			}
			smap1.put("pv_dsrol_i",dsrol);
			/*/////////////////////////////////////////////////////*/
			////// para la nueva lectura de tareas con filtros //////
			/////////////////////////////////////////////////////////
			
			//////////////////////////////////////////////
			////// para filtrar solo polizas nuevas //////
			smap1.put("pv_cdtiptra_i","1");
			////// para filtrar solo polizas nuevas //////
			//////////////////////////////////////////////
			
			slist1=kernelManager.loadMesaControl(smap1);
			if(slist1!=null&&slist1.size()>0)
			{
				for(int i=0;i<slist1.size();i++)
				{
					String unieco = slist1.get(i).get("cdunieco");
					String ramo   = slist1.get(i).get("cdramo");
					String estado = slist1.get(i).get("estado");
					String poliza = slist1.get(i).get("nmpoliza");
					String solici = slist1.get(i).get("nmsolici");
					if(unieco==null||unieco.length()==0)
						unieco="x";
					if(ramo==null||ramo.length()==0)
						ramo="x";
					if(estado==null||estado.length()==0)
						estado="x";
					if(poliza==null||poliza.length()==0)
						poliza="x";
					if(solici==null||solici.length()==0)
						solici="x";
					slist1.get(i).put("merged",unieco+"#_#"+ramo+"#_#"+estado+"#_#"+poliza+"#_#"+solici);
				}
			}
			success=true;
		}
		catch(Exception ex)
		{
			success=false;
			log.error("error al load tareas",ex);
		}
		log.debug(""
				+ "\n######                            ######"
				+ "\n###### mesa de control loadTareas ######"
				+ "\n########################################"
				+ "\n########################################"
				);
		return SUCCESS;
	}
	
	////////////////////////////////
	////// loadTareasDinamico //////
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
	public String loadTareasDinamico()
	{
		log.debug(""
				+ "\n################################################"
				+ "\n################################################"
				+ "\n###### mesa de control loadTareasDinamico ######"
				+ "\n######                                    ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			UserVO usu=(UserVO) session.get("USUARIO");
			String cdsisrol = usu.getRolActivo().getObjeto().getValue();
			String cdusuari = usu.getUser();
			smap1.put("pv_dsrol_i",cdsisrol);
			smap1.put("pv_cdusuari_i",cdusuari);
			if(cdsisrol.equalsIgnoreCase("operadorsini")||cdsisrol.equalsIgnoreCase("medajustador"))
			{
				slist1=kernelManager.loadMesaControlUsuario(smap1);
			}
			else
			{
				slist1=kernelManager.loadMesaControl(smap1);
			}
			olist1=new ArrayList<Map<String,Object>>();
			
			if(slist1!=null&&slist1.size()>0)
			{
				for(Map<String,String> tramite:slist1)
				{
					Map<String,Object>aux=new HashMap<String,Object>();
					for(Entry<String,String> en:tramite.entrySet())
					{
						String key   = en.getKey();
						String value = en.getValue();
						aux.put(key,value);
					}
					olist1.add(aux);
				}
				
				for(Map<String,Object> tramite:olist1)
				{
					Map<String,String>parametros=new HashMap<String,String>();
					for(Entry<String,Object> en:tramite.entrySet())
					{
						String key   = en.getKey();
						String value = (String)en.getValue();
						if(key!=null&&key.length()>6&&key.substring(0, 7).equalsIgnoreCase("otvalor"))
						{
							parametros.put("pv_"+key,value);
						}
					}
					tramite.put("parametros",parametros);
				}
			}
			
			slist1=null;
			success=true;
		}
		catch(Exception ex)
		{
			success=false;
			log.error("error al load tareas dinamico",ex);
		}
		log.debug(""
				+ "\n######                                    ######"
				+ "\n###### mesa de control loadTareasDinamico ######"
				+ "\n################################################"
				+ "\n################################################"
				);
		return SUCCESS;
	}
	/*////////////////////////////*/
	////// loadTareasDinamico //////
	////////////////////////////////
	
	public String guardarTramiteManual() {
		log.debug(""
				+ "\n##################################################"
				+ "\n##################################################"
				+ "\n###### guardarTramiteManual                 ######"
				+ "\n######                                      ######"
				);
		try
		{
			UserVO user = (UserVO)session.get("USUARIO");
			//DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usu.getUser());
			
			Map<String,Object>omap=new LinkedHashMap<String,Object>(0);
			Iterator it=smap1.entrySet().iterator();
			while(it.hasNext())
			{
				Entry entry=(Entry)it.next();
				omap.put((String)entry.getKey(),entry.getValue());
			}
			omap.put("pv_ferecepc_i",new Date());
			omap.put("pv_festatus_i",new Date());
			omap.put("pv_cdunieco_i",omap.get("pv_cdsucdoc_i"));
			
			//Validamos el usuario contra la sucursal:
			kernelManager.validaUsuarioSucursal(omap.get("pv_cdsucdoc_i").toString(), null, null, user.getUser());
			
			WrapperResultados res = kernelManager.PMovMesacontrol(omap);
			if(res.getItemMap() == null)log.error("Sin mensaje respuesta de nmtramite!!");
			else msgResult = (String) res.getItemMap().get("ntramite");
					log.debug("TRAMITE RESULTADO: "+msgResult);
					
			log.debug("se inserta detalle nuevo");
        	Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
        	parDmesCon.put("pv_ntramite_i"   , res.getItemMap().get("ntramite"));
        	parDmesCon.put("pv_feinicio_i"   , new Date());
        	parDmesCon.put("pv_cdclausu_i"   , null);
        	parDmesCon.put("pv_comments_i"   , "Se guard&oacute; un nuevo tr&aacute;mite manual desde mesa de control");
        	parDmesCon.put("pv_cdusuari_i"   , user.getUser());
        	parDmesCon.put("pv_cdmotivo_i"   , null);
        	kernelManager.movDmesacontrol(parDmesCon);
					
			success=true;
			
		} catch(ApplicationException ae) {
			log.error("Error al guardar tramite manual", ae);
			errorMessage = ae.getMessage();
		} catch(Exception ex) {
			log.error("error al guardar tramite manual",ex);
		}
		log.debug(""
				+ "\n######                                      ######"
				+ "\n###### guardarTramiteManual                 ######"
				+ "\n##################################################"
				+ "\n##################################################"
				);
		return SUCCESS;
	}
	
	////////////////////////////////////////////////
	////// actualizar status de tramite de mc //////
	/*////////////////////////////////////////////*/
	public String actualizarStatusTramite()
	{
		log.debug(""
				+ "\n#####################################"
				+ "\n#####################################"
				+ "\n###### actualizarStatusTramite ######"
				+ "\n######                         ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			//Se obtienen los datos del usuario:
			UserVO usu=(UserVO)session.get("USUARIO");
			//DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usu.getUser());
			
			String statusNuevo=smap1.get("status");
			String ntramite=smap1.get("ntramite");
			String comments=smap1.get("comments");
			String cdmotivo=smap1.get("cdmotivo");
			
			String rolDestino     = smap1.get("rol_destino");
			String usuarioDestino = smap1.get("usuario_destino");
			//boolean paraUsuario = StringUtils.isNotBlank(rolDestino);
			
			String cdusuariSesion = usu.getUser();
			String cdsisrolSesion = usu.getRolActivo().getObjeto().getValue();
			String cdclausu       = null;
			
			siniestrosManager.moverTramite(ntramite, statusNuevo, comments, cdusuariSesion, cdsisrolSesion, usuarioDestino, rolDestino, cdmotivo, cdclausu);
			
			/*
			// Se actualiza el estatus en la mesa de control:
			//kernelManager.mesaControlUpdateStatus(ntramite,statusNuevo);
			
			// Creamos un enum en base al tipo de tramite
			EstatusTramite enumEstatusTramite = null;
			for(EstatusTramite statTra : EstatusTramite.values()) {
				if(statTra.getCodigo().equals(statusNuevo)) {
					enumEstatusTramite = statTra;
					break;
				}
			}
			String comentarioPrevio = "";
	    	switch(enumEstatusTramite) {
	    		case EN_REVISION_MEDICA:
	    			comentarioPrevio = "<p>El tr&aacute;mite fue turnado a revisi&oacute;n m&eacute;dica con las siguientes observaciones:</p>";
	    			break;
	    		case RECHAZADO:
	    			comentarioPrevio = "<p>La p&oacute;liza fue rechazada con los siguientes detalles:</p>";
	    			break;
	    		case VO_BO_MEDICO:
	    		case SOLICITUD_MEDICA:
	    			comentarioPrevio = "<p>El m&eacute;dico revis&oacute; el tr&aacute;mite con las siguientes observaciones:</p>";
	    			break;
	    		case EN_ESPERA_DE_AUTORIZACION:
	    			comentarioPrevio = "<p>El coordinador m&eacute;dico multiregional remiti&oacute; las siguientes observaciones:</p>";
	    			break;
				default:
					break;
			}
	    	log.debug("se inserta detalle nuevo");
        	Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
        	parDmesCon.put("pv_ntramite_i"   , ntramite);
        	parDmesCon.put("pv_feinicio_i"   , new Date());
        	parDmesCon.put("pv_cdclausu_i"   , null);
        	parDmesCon.put("pv_comments_i"   , new StringBuilder(comentarioPrevio).append(comments));
        	parDmesCon.put("pv_cdusuari_i"   , datUsu.getCdusuari());
        	parDmesCon.put("pv_cdmotivo_i"   , cdmotivo);
        	// Se inserta el detalle de la mesa de control:
        	//kernelManager.movDmesacontrol(parDmesCon);
        	
        	if(paraUsuario)
        	{
        		siniestrosManager.turnarTramite(ntramite, rolDestino, usuarioDestino);
        	}
			*/
			
			success=true;
			
		} catch(Exception ex) {
			success=false;
			log.error("error al actualizar status de tramite de mesa de control",ex);
			mensaje=ex.getMessage();
		}
		log.debug(""
				+ "\n######                         ######"
				+ "\n###### actualizarStatusTramite ######"
				+ "\n#####################################"
				+ "\n#####################################"
				);
		return SUCCESS;
	}
	/*////////////////////////////////////////////*/
	////// actualizar status de tramite de mc //////
	////////////////////////////////////////////////
	
	////////////////////////////////////////////////
	////// obtener los detalles de un tramite //////
	/*////////////////////////////////////////////*/
	public String obtenerDetallesTramite()
	{
		log.debug(""
				+ "\n################################################"
				+ "\n################################################"
				+ "\n###### obtener los detalles de un tramite ######"
				+ "\n######                                    ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			slist1=kernelManager.obtenerDetalleMC(smap1);
		}
		catch(Exception ex)
		{
			log.error("error al obtener el detalle de mesa de control",ex);
		}
		log.debug(""
				+ "\n######                                    ######"
				+ "\n###### obtener los detalles de un tramite ######"
				+ "\n################################################"
				+ "\n################################################"
				);
		success=true;
		return SUCCESS;
	}
	/*////////////////////////////////////////////*/
	////// obtener los detalles de un tramite //////
	////////////////////////////////////////////////
	
	////////////////////////////////////////////////////////////////
	////// finalizar un detalle de tramite de mesa de control //////
	/*////////////////////////////////////////////////////////////*/
	public String finalizarDetalleTramiteMC()
	{
		log.debug(""
				+ "\n################################################################"
				+ "\n################################################################"
				+ "\n###### finalizar un detalle de tramite de mesa de control ######"
				+ "\n######                                                    ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			UserVO usu=(UserVO)session.get("USUARIO");
			DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usu.getUser());
			smap1.put("pv_cdusuari_fin_i",datUsu.getCdusuari());
			smap1.put("pv_cdmotivo_i", null);
			kernelManager.mesaControlFinalizarDetalle(smap1);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al finalizar detalle de tramite de mesa de control",ex);
			success=false;
		}
		log.debug(""
				+ "\n######                                                    ######"
				+ "\n###### finalizar un detalle de tramite de mesa de control ######"
				+ "\n################################################################"
				+ "\n################################################################"
				);
		return SUCCESS;
	}
	/*////////////////////////////////////////////////////////////*/
	////// finalizar un detalle de tramite de mesa de control //////
	////////////////////////////////////////////////////////////////
	
	
	/////////////////////////////////////////////
	////// cargar tramites para supervisor //////
	/*/////////////////////////////////////////*/
	public String loadTareasSuper()
	{
		log.debug(""
				+ "\n###################################################"
				+ "\n###################################################"
				+ "\n###### mesa de control loadTareas supervisor ######"
				+ "\n######                                       ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			slist1=kernelManager.loadMesaControlSuper(smap1);
			if(slist1!=null&&slist1.size()>0)
			{
				for(int i=0;i<slist1.size();i++)
				{
					String unieco = slist1.get(i).get("cdunieco");
					String ramo   = slist1.get(i).get("cdramo");
					String estado = slist1.get(i).get("estado");
					String poliza = slist1.get(i).get("nmpoliza");
					String solici = slist1.get(i).get("nmsolici");
					if(unieco==null||unieco.length()==0)
						unieco="x";
					if(ramo==null||ramo.length()==0)
						ramo="x";
					if(estado==null||estado.length()==0)
						estado="x";
					if(poliza==null||poliza.length()==0)
						poliza="x";
					if(solici==null||solici.length()==0)
						solici="x";
					slist1.get(i).put("merged",unieco+"#_#"+ramo+"#_#"+estado+"#_#"+poliza+"#_#"+solici);
				}
			}
			success=true;
		}
		catch(Exception ex)
		{
			success=false;
			log.error("error al load tareas",ex);
		}
		log.debug(""
				+ "\n######                                       ######"
				+ "\n###### mesa de control loadTareas supervisor ######"
				+ "\n###################################################"
				+ "\n###################################################"
				);
		return SUCCESS;
	}
	/*/////////////////////////////////////////*/
	////// cargar tramites para supervisor //////
	/////////////////////////////////////////////
	
	/////////////////////////////////////////////////
	//////      mesa de control dinamica       //////
	/*
	smap1:
		(URL)gridTitle=Endosos en espera
		(URL)cdramo=
		(URL)cdtipsit=
	smap2:
		(URL)pv_cdtiptra_i=15
		pv_fehasta_i=,
		pv_cdagente_i=,
		pv_estado_i=,
		pv_cdtipsit_i=,
		pv_status_i=-1,
		pv_cdunieco_i=,
		pv_fedesde_i=,
		pv_nmpoliza_i=,
		pv_ntramite_i=,
		pv_cdramo_i=
	*/
	/*/////////////////////////////////////////////*/
	public String mcdinamica()
	{
		log.debug(""
				+ "\n######################################"
				+ "\n######################################"
				+ "\n###### mesa de control dinamica ######"
				+ "\n######                          ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("smap2: "+smap2);
		
		try
		{
			UserVO usuario=(UserVO) this.session.get("USUARIO");
			username=usuario.getUser();
			
			String cdtiptra      = smap2.get("pv_cdtiptra_i");
			String cdramo        = smap1.get("cdramo");
			String cdtipsit      = smap1.get("cdtipsit");
			rol           = usuario.getRolActivo().getObjeto().getValue();
			String pantalla            = "TATRIMC";
			String seccionForm         = "FORMULARIO";
			String seccionGrid         = "TATRIMC";
			String seccionFiltro       = "FILTRO";
			String seccionActionColumn = "ACTIONCOLUMN";
			String seccionGridButtons   = "GRIDBUTTONS";
			
			////// obtener valores del formulario //////
			List<ComponenteVO>ltFormulario=pantallasManager.obtenerComponentes(
					cdtiptra, null, cdramo,
					cdtipsit, null, rol,
					pantalla, seccionForm, null);
			
			////// obtener valores del grid //////
			List<ComponenteVO>ltgridpanel=pantallasManager.obtenerComponentes(
					cdtiptra, null, cdramo,
					cdtipsit, null, rol,
					pantalla, seccionGrid, null);
			
			////// obtener valores del filtro //////
			List<ComponenteVO>ltfiltro=pantallasManager.obtenerComponentes(
					cdtiptra, null, cdramo,
					cdtipsit, null, rol,
					pantalla, seccionFiltro, null);
			
			////// obtener botones del action column //////
			List<ComponenteVO>ltactioncolumn=pantallasManager.obtenerComponentes(
					cdtiptra, null, cdramo,
					cdtipsit, null, rol,
					pantalla, seccionActionColumn, null);
			
			////// obtener botones del grid //////
			List<ComponenteVO>ltgridbuttons=pantallasManager.obtenerComponentes(
					cdtiptra, null, cdramo,
					cdtipsit, null, rol,
					pantalla, seccionGridButtons, null);
			
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			////// generar grid //////
			gc.generaComponentes(ltgridpanel, true, true, false, true, false, false);
			imap1=new HashMap<String,Item>(0);
			imap1.put("modelFields",gc.getFields());
			imap1.put("gridColumns",gc.getColumns());
			
			////// generar formulario //////
			gc.generaComponentes(ltFormulario, true, false, true, false, false, false);
			imap1.put("formItems",gc.getItems());
			
			////// generar filtro //////
			gc.generaComponentes(ltfiltro, true, false, true, false, false, false);
			imap1.put("itemsFiltro",gc.getItems());
			
			gc.generaComponentes(ltactioncolumn, true, false, false, false, false, true);
			imap1.put("actionColumns",gc.getButtons());
			
			gc.generaComponentes(ltgridbuttons, true, false, false, false, false, true);
			imap1.put("gridbuttons",gc.getButtons());
			
			///////////////////////////////////////
			////// para poner -1 por defecto //////
			if(smap2==null)
			{
				smap2=new HashMap<String,String>(0);
			}
			if((!smap2.containsKey("pv_status_i")))
			{
				smap2.put("pv_status_i","-1");
			}
			////// para poner -1 por defecto //////
			///////////////////////////////////////
		}
		catch(Exception ex)
		{
			log.error("error al cargar mesa de control dinamica",ex);
		}
		
		log.debug(""
				+ "\n######                          ######"
				+ "\n###### mesa de control dinamica ######"
				+ "\n######################################"
				+ "\n######################################"
				);
		return SUCCESS;
	}
	/*/////////////////////////////////////////////////*/ 
	//////         mesa de control dinamica        //////
	/////////////////////////////////////////////////////
	
	/////////////////////////////////////////////
	////// guardar tramite manual dinamico //////
	/*/////////////////////////////////////////*/
	public String guardarTramiteDinamico()
	{
		log.debug(""
				+ "\n####################################"
				+ "\n####################################"
				+ "\n###### guardarTramiteDinamico ######"
				+ "\n######                        ######"
				);
		try
		{
			//////////////////////////////////
			////// Se guarda el tramite //////
			Map<String,Object>omap=new LinkedHashMap<String,Object>(0);
			for(Entry<String,String> entry:smap1.entrySet())
			{
				omap.put((String)entry.getKey(),entry.getValue());//se pasa de smap1 a omap
			}
			omap.put("pv_cdunieco_i",smap1.get("pv_cdsucdoc_i"));//se parcha porque requiere el mismo valor
			omap.put("pv_ferecepc_i",renderFechas.parse((String)omap.get("pv_ferecepc_i")));//se convierte String a Date
			omap.put("pv_festatus_i",renderFechas.parse((String)omap.get("pv_festatus_i")));//se convierte String a Date
			
			//Validamos el usuario contra la sucursal:
			UserVO user=(UserVO)session.get("USUARIO");
			WrapperResultados result = kernelManager.validaUsuarioSucursal(smap1.get("pv_cdsucdoc_i").toString(), null, null, user.getUser());
			
			WrapperResultados res = kernelManager.PMovMesacontrol(omap);
			////// Se guarda el tramite //////
			//////////////////////////////////
			
			////////////////////////////////////////////
			////// se verifica que se guarde bien //////
			if(res.getItemMap() == null)
			{
				log.error("Sin mensaje respuesta de nmtramite!!");
			}
			else
			{
				msgResult = (String) res.getItemMap().get("ntramite");
			}
			log.debug("TRAMITE RESULTADO: "+msgResult);
			////// se verifica que se guarde bien //////
			////////////////////////////////////////////

			//////////////////////////////////
			////// se guarda el detalle //////
			UserVO usu=(UserVO)session.get("USUARIO");
			log.debug("se inserta detalle nuevo");
        	Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
        	parDmesCon.put("pv_ntramite_i"   , res.getItemMap().get("ntramite"));
        	parDmesCon.put("pv_feinicio_i"   , new Date());
        	parDmesCon.put("pv_cdclausu_i"   , null);
        	parDmesCon.put("pv_comments_i"   , "Se guard&oacute; un nuevo tr&aacute;mite manual desde mesa de control");
        	parDmesCon.put("pv_cdusuari_i"   , usu.getUser());
        	parDmesCon.put("pv_cdmotivo_i"   , null);
        	kernelManager.movDmesacontrol(parDmesCon);
			////// se guarda el detalle //////
        	//////////////////////////////////
					
			success=true;
			
		} catch(ApplicationException ae) {
			
			log.error("Error al guardar tramite dinamico", ae);
			errorMessage = ae.getMessage();
			
	    } catch(Exception ex) {
			log.error("error al guardar tramite dinamico",ex);
			success=false;
		}
		log.debug(""
				+ "\n######                        ######"
				+ "\n###### guardarTramiteDinamico ######"
				+ "\n####################################"
				+ "\n####################################"
				);
		return SUCCESS;
	}
	/*/////////////////////////////////////////*/
	////// guardar tramite manual dinamico //////
	/////////////////////////////////////////////
	
	/////////////////////////////////
	////// getters ans setters //////
	/*/////////////////////////////*/
	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsgResult() {
		return msgResult;
	}

	public void setMsgResult(String msgResult) {
		this.msgResult = msgResult;
	}

	public List<GenericVO> getLista() {
		return lista;
	}

	public void setLista(List<GenericVO> lista) {
		this.lista = lista;
	}

	public Map<String, Item> getImap1() {
		return imap1;
	}

	public void setImap1(Map<String, Item> imap1) {
		this.imap1 = imap1;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}

	public List<Map<String, Object>> getOlist1() {
		return olist1;
	}

	public void setOlist1(List<Map<String, Object>> olist1) {
		this.olist1 = olist1;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public void setSiniestrosManager(SiniestrosManager siniestrosManager) {
		this.siniestrosManager = siniestrosManager;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}