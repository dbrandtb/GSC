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
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.service.ServiciosManager;
import mx.com.gseguros.portal.general.util.CausaSiniestro;
import mx.com.gseguros.portal.general.util.EstatusTramite;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.Ramo;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.general.util.TipoPago;
import mx.com.gseguros.portal.general.util.TipoPrestadorServicio;
import mx.com.gseguros.portal.mesacontrol.service.MesaControlManager;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaProveedorVO;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

public class MesaControlAction extends PrincipalCoreAction
{
	
	private static final long              serialVersionUID = -3398140781812652316L;
	private static org.apache.log4j.Logger log              = org.apache.log4j.Logger.getLogger(MesaControlAction.class);
	private static SimpleDateFormat        renderFechas     = new SimpleDateFormat("dd/MM/yyyy");
	private static final String IMPORTE_WS_IMPORTE = "importe";
	private static final String IMPORTE_WS_IVA     = "iva";
	private static final String IMPORTE_WS_IVR     = "ivr";
	private static final String IMPORTE_WS_ISR     = "isr";
	private static final String IMPORTE_WS_CEDULAR = "cedular";
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
	private MesaControlManager             mesaControlManager;
	
	private Map<String,Object>             params;
	private String						   tmpNtramite;
	
	private String                         respuesta;
	
	@Autowired
	private ServiciosManager serviciosManager;
	
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
			String cdrol="";
			if(usu!=null
			    &&usu.getRolActivo()!=null
			    &&usu.getRolActivo().getClave()!=null)
			{
			    cdrol=usu.getRolActivo().getClave();
			}
			log.debug("rol activo: "+cdrol);
			//!obtener el rol activo
			
			/////////////////////////////////////////////////////////
			////// para la nueva lectura de tareas con filtros //////
			/*/////////////////////////////////////////////////////*/
			if(smap1==null)
			{
				smap1=new LinkedHashMap<String,String>(0);
			}
			smap1.put("pv_cdrol_i",cdrol);
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
			String cdsisrol = usu.getRolActivo().getClave();
			String cdusuari = usu.getUser();
			String filtro   = smap1.get("filtro");
			smap1.put("pv_cdrol_i",cdsisrol);
			smap1.put("pv_cdusuari_i",cdusuari);
			if(cdsisrol.equalsIgnoreCase(RolSistema.OPERADOR_SINIESTROS.getCdsisrol())
				     || cdsisrol.equalsIgnoreCase(RolSistema.MEDICO_AJUSTADOR.getCdsisrol())
				     || cdsisrol.equalsIgnoreCase(RolSistema.MESA_DE_CONTROL_SINIESTROS.getCdsisrol())
				     || cdsisrol.equalsIgnoreCase(RolSistema.COORDINADOR_MEDICO.getCdsisrol())
				     || cdsisrol.equalsIgnoreCase(RolSistema.COORDINADOR_MEDICO_MULTIREGIONAL.getCdsisrol())
				     || cdsisrol.equalsIgnoreCase(RolSistema.GERENTE_MEDICO_MULTIREGIONAL.getCdsisrol()))
			{
				slist1=kernelManager.loadMesaControlUsuario(smap1);
			}
			else
			{
				slist1=kernelManager.loadMesaControl(smap1);
			}
			olist1=new ArrayList<Map<String,Object>>();
			
			if(StringUtils.isNotBlank(filtro))
			{
				List<Map<String,String>> aux = new ArrayList<Map<String,String>>();
				for(Map<String,String>rec:slist1)
				{
					for(Entry<String,String>en:rec.entrySet())
					{
						String value = en.getValue();
						if(value==null)
						{
							value = "";
						}
						if(value.toUpperCase().indexOf(filtro.toUpperCase())!=-1)
						{
							aux.add(rec);
							break;
						}
					}
				}
				slist1 = aux;
			}
			
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
			omap.put("cdusuari" , user.getUser());
			omap.put("cdsisrol" , user.getRolActivo().getClave());
			
			//Validamos el usuario contra la sucursal:
			kernelManager.validaUsuarioSucursal(omap.get("pv_cdsucdoc_i").toString(), null, null, user.getUser());
			
			//WrapperResultados res = kernelManager.PMovMesacontrol(omap);
			String ntramiteGenerado = mesaControlManager.movimientoTramite(
					(String)omap.get("pv_cdsucdoc_i")
					,(String)omap.get("pv_cdramo_i")
					,(String)omap.get("pv_estado_i")
					,(String)omap.get("pv_nmpoliza_i")
					,(String)omap.get("pv_nmsuplem_i")
					,(String)omap.get("pv_cdsucadm_i")
					,(String)omap.get("pv_cdsucdoc_i")
					,(String)omap.get("pv_cdtiptra_i")
					,new Date()
					,(String)omap.get("pv_cdagente_i")
					,(String)omap.get("pv_referencia_i")
					,(String)omap.get("pv_nombre_i")
					,new Date()
					,(String)omap.get("pv_status_i")
					,(String)omap.get("pv_comments_i")
					,(String)omap.get("pv_nmsolici_i")
					,(String)omap.get("pv_cdtipsit_i")
					,user.getUser()
					,user.getRolActivo().getClave()
					,null //swimpres
					,null //cdtipflu
					,null //cdflujomc
					,smap1, null
					);
			//if(res.getItemMap() == null)log.error("Sin mensaje respuesta de nmtramite!!");
			if(ntramiteGenerado==null)log.error("Sin mensaje respuesta de nmtramite!!");
			//else msgResult = (String) res.getItemMap().get("ntramite");
			else msgResult = ntramiteGenerado;
					log.debug("TRAMITE RESULTADO: "+msgResult);
					
			log.debug("se inserta detalle nuevo");
        	/*Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
        	parDmesCon.put("pv_ntramite_i"   , ntramiteGenerado);
        	parDmesCon.put("pv_feinicio_i"   , new Date());
        	parDmesCon.put("pv_cdclausu_i"   , null);
        	parDmesCon.put("pv_comments_i"   , "Se guard\u00f3 un nuevo tr\u00e1mite manual desde mesa de control");
        	parDmesCon.put("pv_cdusuari_i"   , user.getUser());
        	parDmesCon.put("pv_cdmotivo_i"   , null);
        	parDmesCon.put("pv_cdsisrol_i"   , user.getRolActivo().getClave());
        	kernelManager.movDmesacontrol(parDmesCon);*/
			mesaControlManager.movimientoDetalleTramite(
					ntramiteGenerado
					,new Date()
					,null//lcdclausu
					,"Se guard\u00f3 un nuevo tr\u00e1mite manual desde mesa de control"
					,user.getUser()
					,null//cdmotivo
					,user.getRolActivo().getClave()
					,"S"
					);
					
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
			
			String statusNuevo=smap1.get("status");
			String ntramite=smap1.get("ntramite");
			String comments=smap1.get("comments");
			String cdmotivo=smap1.get("cdmotivo");
			String mostrarAgente=smap1.get("swagente");
			
			String rolDestino     = smap1.get("rol_destino");
			String usuarioDestino = smap1.get("usuario_destino");
			//boolean paraUsuario = StringUtils.isNotBlank(rolDestino);
			
			String cdusuariSesion = usu.getUser();
			String cdsisrolSesion = usu.getRolActivo().getClave();
			String cdclausu       = null;
			
			Map<String,Object> res = siniestrosManager.moverTramite(
					ntramite
					,statusNuevo
					,comments
					,cdusuariSesion
					,cdsisrolSesion
					,usuarioDestino
					,rolDestino
					,cdmotivo
					,cdclausu
					,mostrarAgente
					);
			
			Boolean escalado  = (Boolean)res.get("ESCALADO");
			String  statusEsc = (String)res.get("STATUS");
			
			smap1.put("nombreUsuarioDestino" , (String)res.get("NOMBRE"));
			smap1.put("ESCALADO"             , escalado ? "S" : "N" );
			if(escalado)
			{
				smap1.put("status" , statusEsc);
			}
			
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

	////////////////////////////////////////////////
	////// actualizar status de tramite de mc //////
	/*////////////////////////////////////////////*/
	public String turnarAutorizacionServicio()
	{
		log.debug(""
				+ "\n########################################"
				+ "\n########################################"
				+ "\n###### turnarAutorizacionServicio ######"
				+ "\n######                            ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			UserVO usu=(UserVO)session.get("USUARIO");
			
			String ntramite=smap1.get("ntramite");
			String statusNuevo=smap1.get("status");
			String comments=smap1.get("comments");
			String cdmotivo=smap1.get("cdmotivo");
			
			String rolDestino     = smap1.get("rol_destino");
			String usuarioDestino = smap1.get("usuario_destino");
			
			String cdusuariSesion = usu.getUser();
			String cdsisrolSesion = usu.getRolActivo().getClave();
			String cdclausu       = null;
			
			siniestrosManager.turnarAutServicio(ntramite, statusNuevo, comments, cdusuariSesion, cdsisrolSesion, usuarioDestino, rolDestino, cdmotivo, cdclausu);
			success=true;
			
		} catch(Exception ex) {
			success=false;
			log.error("error al actualizar status de tramite de mesa de control",ex);
			mensaje=ex.getMessage();
		}
		log.debug(""
				+ "\n######                            ######"
				+ "\n###### turnarAutorizacionServicio ######"
				+ "\n########################################"
				+ "\n########################################"
				);
		return SUCCESS;
	}
	////////////////////////////////////////////////
	////// actualizar status de tramite de mc //////
	/*////////////////////////////////////////////*/
	public String turnarAOperadorReclamacion()
	{
		log.debug(""
				+ "\n################################################"
				+ "\n################################################"
				+ "\n###### turnar a Operador de Reclamaciones ######"
				+ "\n######                         		   ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			UserVO usu=(UserVO)session.get("USUARIO");
			//Se obtienen los datos del usuario:
			String statusNuevo=smap1.get("status");
			String ntramite=smap1.get("ntramite");
			String comments=smap1.get("comments");
			String cdmotivo=smap1.get("cdmotivo");
			
			String rolDestino     = smap1.get("rol_destino");
			String usuarioDestino = smap1.get("usuario_destino");
			
			String cdusuariSesion = usu.getUser();
			String cdsisrolSesion = "COORDINASINI";
			String cdclausu       = null;
			siniestrosManager.moverTramite(ntramite, statusNuevo, comments, cdusuariSesion, cdsisrolSesion, usuarioDestino, rolDestino, cdmotivo, cdclausu,null);
			success=true;
			
		} catch(Exception ex) {
			success=false;
			log.error("error al actualizar status de tramite de mesa de control",ex);
			mensaje=ex.getMessage();
		}
		log.debug(""
				+ "\n######                         		   ######"
				+ "\n###### Turnar a operador de Reclamaciones ######"
				+ "\n################################################"
				+ "\n################################################"
				);
		return SUCCESS;
	}
	public String actualizaComentariosTramite()
	{
		log.debug(""
				+ "\n#########################################"
				+ "\n#########################################"
				+ "\n###### actualizaComentariosTramite ######"
				+ "\n######                             ######"
				);
		log.debug("params: "+params);
		try
		{
			HashMap<String,Object> temp =  new HashMap<String, Object>();
			temp.put("pv_ntramite_i", tmpNtramite);
			temp.put("pv_comments_i", mensaje);
			
			siniestrosManager.actualizaOTValorMesaControl(temp);
			
		} catch(Exception ex) {
			success=false;
			log.error("error al actualizar status de tramite de mesa de control",ex);
			mensaje=ex.getMessage();
		}
		log.debug(""
				+ "\n######                             ######"
				+ "\n###### actualizaComentariosTramite ######"
				+ "\n#########################################"
				+ "\n#########################################"
				);
		
		success=true;
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
			smap1.put("pv_cdusuari_fin_i",usu.getUser());
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
			smap1.put("cdsisrol" , usuario.getRolActivo().getClave());
			smap1.put("cdusuari" , usuario.getUser());
			
			String cdtiptra                  = smap2.get("pv_cdtiptra_i");
			String cdramo                    = smap1.get("cdramo");
			String cdtipsit                  = smap1.get("cdtipsit");
			String pantalla                  = "TATRIMC";
			String seccionForm               = "FORMULARIO";
			String seccionGrid               = "TATRIMC";
			String seccionFiltro             = "FILTRO";
			String seccionActionColumn       = "ACTIONCOLUMN";
			String seccionActionColumnStatus = "STATUSCOLUMNS";
			String seccionGridButtons        = "GRIDBUTTONS";
			String seccionBotonesTramite     = "BOTONES_TRAMITE";
			
			rol = usuario.getRolActivo().getClave();
			
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
			
			////// obtener botones del action column por status//////
			List<ComponenteVO>ltactioncolumnstatus=pantallasManager.obtenerComponentes(
					cdtiptra, null, cdramo,
					cdtipsit, null, rol,
					pantalla, seccionActionColumnStatus, null);
			
			////// obtener botones del grid //////
			List<ComponenteVO>ltgridbuttons=pantallasManager.obtenerComponentes(
					cdtiptra, null, cdramo,
					cdtipsit, null, rol,
					pantalla, seccionGridButtons, null);
			
			List<ComponenteVO>ltBotonesTramite=pantallasManager.obtenerComponentes(
					cdtiptra, null, cdramo,
					cdtipsit, null, rol,
					pantalla, seccionBotonesTramite, null);
			
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
			
			gc.generaComponentes(ltactioncolumnstatus, true, false, false, true, false, false);
			imap1.put("statusColumns",gc.getColumns());
			
			gc.generaComponentes(ltgridbuttons, true, false, false, false, false, true);
			imap1.put("gridbuttons",gc.getButtons());
			
			gc.generaComponentes(ltBotonesTramite, true, false, false, false, false, true);
			imap1.put("botonesTramite",gc.getButtons());
			
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
			if(rol.equals(RolSistema.AGENTE.getCdsisrol()))
			{
				smap2.put("pv_cdagente_i",mesaControlManager.cargarCdagentePorCdusuari(username));
			}
			////// para poner -1 por defecto //////
			///////////////////////////////////////
			
			//Realizamos el llamado del proceso de pago automatico cdtiptra = 19
			try{
				if(cdtiptra.equalsIgnoreCase("19")){
					slist1 = siniestrosManager.procesaPagoAutomaticoSisco();
					for(int i=0;i<slist1.size();i++){
						//SiniestrosAction mca = new SiniestrosAction();
						HashMap<String,String> params = new HashMap<String, String>();
						params.put("ntramite",slist1.get(i).get("NTRAMITE"));
						this.smap2 = params;
						String respuesta = generarCalculoSiniestros();
					}
				}
			}catch(Exception ex)
			{
				log.error("error en la generacion de calculos",ex);
			}
				
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
			
			omap.put("cdusuari" , user.getUser());
			omap.put("cdsisrol" , user.getRolActivo().getClave());
			
			//WrapperResultados res = kernelManager.PMovMesacontrol(omap);
			String ntramiteGenerado = mesaControlManager.movimientoTramite(
					(String)omap.get("pv_cdsucdoc_i")
					,(String)omap.get("pv_cdramo_i")
					,(String)omap.get("pv_estado_i")
					,(String)omap.get("pv_nmpoliza_i")
					,(String)omap.get("pv_nmsuplem_i")
					,(String)omap.get("pv_cdsucadm_i")
					,(String)omap.get("pv_cdsucdoc_i")
					,(String)omap.get("pv_cdtiptra_i")
					,(Date)omap.get("pv_ferecepc_i")
					,(String)omap.get("pv_cdagente_i")
					,(String)omap.get("pv_referencia_i")
					,(String)omap.get("pv_nombre_i")
					,(Date)omap.get("pv_festatus_i")
					,(String)omap.get("pv_status_i")
					,(String)omap.get("pv_comments_i")
					,(String)omap.get("pv_nmsolici_i")
					,(String)omap.get("pv_cdtipsit_i")
					,user.getUser()
					,user.getRolActivo().getClave()
					,null //swimpres
					,null //cdtipflu
					,null //cdflujomc
					,smap1, null
					);
			////// Se guarda el tramite //////
			//////////////////////////////////
			
			////////////////////////////////////////////
			////// se verifica que se guarde bien //////
			//if(res.getItemMap() == null)
			if(ntramiteGenerado==null)
			{
				log.error("Sin mensaje respuesta de nmtramite!!");
			}
			else
			{
				//msgResult = (String) res.getItemMap().get("ntramite");
				msgResult = ntramiteGenerado;
			}
			log.debug("TRAMITE RESULTADO: "+msgResult);
			////// se verifica que se guarde bien //////
			////////////////////////////////////////////

			//////////////////////////////////
			////// se guarda el detalle //////
			UserVO usu=(UserVO)session.get("USUARIO");
			log.debug("se inserta detalle nuevo");
        	/*Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
        	//parDmesCon.put("pv_ntramite_i"   , res.getItemMap().get("ntramite"));
        	parDmesCon.put("pv_ntramite_i"   , ntramiteGenerado);
        	parDmesCon.put("pv_feinicio_i"   , new Date());
        	parDmesCon.put("pv_cdclausu_i"   , null);
        	parDmesCon.put("pv_comments_i"   , "Se guard\u00f3 un nuevo tr\u00e1mite manual desde mesa de control");
        	parDmesCon.put("pv_cdusuari_i"   , usu.getUser());
        	parDmesCon.put("pv_cdmotivo_i"   , null);
        	parDmesCon.put("pv_cdsisrol_i"   , usu.getRolActivo().getClave());
        	kernelManager.movDmesacontrol(parDmesCon);*/
			mesaControlManager.movimientoDetalleTramite(
					ntramiteGenerado
					,new Date()
					,null
					,"Se guard\u00f3 un nuevo tr\u00e1mite manual desde mesa de control"
					,usu.getUser()
					,null
					,usu.getRolActivo().getClave()
					,"S"
					);
			////// se guarda el detalle //////
        	//////////////////////////////////
        	
        	try
            {
            	serviciosManager.grabarEvento(new StringBuilder("\nNuevo tramite")
            	    ,"GENERAL"
            	    ,"NUETRAMITMC"
            	    ,new Date()
            	    ,((UserVO)session.get("USUARIO")).getUser()
            	    ,((UserVO)session.get("USUARIO")).getRolActivo().getClave()
            	    //,(String)res.getItemMap().get("ntramite")
            	    ,ntramiteGenerado
            	    ,smap1.get("pv_cdsucdoc_i")
            	    ,(String)omap.get("pv_cdramo_i")
            	    ,null
            	    ,null
            	    ,null
            	    ,(String)omap.get("pv_cdagente_i")
            	    ,null
            	    ,null
            	    );
            }
            catch(Exception ex)
            {
            	logger.error("Error al grabar evento, sin impacto",ex);
            }
        	
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
	
	public String regresarEmisionEnAutori()
	{
		this.session=ActionContext.getContext().getSession();
		log.info(""
				+ "\n#####################################"
				+ "\n###### regresarEmisionEnAutori ######"
				);
		log.info("smap1: "+smap1);
		String ntramiteAuto = smap1.get("ntramiteAuto");
		String ntramiteEmi  = smap1.get("ntramiteEmi");
		String comentario   = smap1.get("comentario");
		log.info("ntramiteAuto:"+ntramiteAuto);
		log.info("ntramiteEmi:"+ntramiteEmi);
		log.info("comentario:"+comentario);
		String cdusuari;
		String cdsisrol;
		{
			UserVO usuario = (UserVO)session.get("USUARIO");
			cdusuari = usuario.getUser();
			cdsisrol = usuario.getRolActivo().getClave();
		}
		log.info("cdusuari:"+cdusuari);
		try
		{
			kernelManager.mesaControlUpdateStatus(ntramiteEmi  , EstatusTramite.PENDIENTE.getCodigo());
			kernelManager.mesaControlUpdateStatus(ntramiteAuto , EstatusTramite.CONFIRMADO.getCodigo());
			/*Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
        	parDmesCon.put("pv_ntramite_i"   , ntramiteEmi);
        	parDmesCon.put("pv_feinicio_i"   , new Date());
        	parDmesCon.put("pv_cdclausu_i"   , null);
        	parDmesCon.put("pv_comments_i"   , "El gerente regres&oacute; el tr&aacute;mite con las siguientes observaciones:<br/>"+comentario);
        	parDmesCon.put("pv_cdusuari_i"   , cdusuari);
        	parDmesCon.put("pv_cdmotivo_i"   , null);
        	parDmesCon.put("pv_cdsisrol_i"   , cdsisrol);
        	kernelManager.movDmesacontrol(parDmesCon);*/
			
			mesaControlManager.movimientoDetalleTramite(
					ntramiteEmi
					,new Date()
					,null
					,"El gerente regres\u00f3 el tr\u00e1mite con las siguientes observaciones:<br/>"+comentario
					,cdusuari
					,null
					,cdsisrol
					,"N"
					);
			
        	mensaje = "Tr&aacute;mite regresado";
        	success = true;
		}
		catch(Exception ex)
		{
			log.error("error al regresar emision de autorizacion",ex);
			success = false;
			mensaje = ex.getMessage();
		}
		log.info(""
				+ "\n###### regresarEmisionEnAutori ######"
				+ "\n#####################################"
				);
		return SUCCESS;
	}
	
	public String movimientoDetalleTramite()
	{
		log.info(Utils.join(
				 "\n######################################"
				,"\n###### movimientoDetalleTramite ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			UserVO user = Utils.validateSession(session);
			
			Utils.validate(smap1 , "No se recibieron datos");
			
			String ntramite = smap1.get("ntramite");
			String dscoment = smap1.get("dscoment");
			
			Utils.validate(ntramite , "No se recibio el numero de tramite");
			
			mesaControlManager.movimientoDetalleTramite(ntramite, new Date(), null, dscoment, user.getUser(), null, user.getRolActivo().getClave(),null);
		}
		catch(Exception ex)
		{
			mensaje = Utils.manejaExcepcion(ex);
		}
		
		log.info(Utils.join(
				 "\n###### movimientoDetalleTramite ######"
				,"\n######################################"
				));
		return SUCCESS;
	}
	
	public String validarAntesDeTurnar()
	{
		logger.debug(Utils.log(
				 "\n##################################"
				,"\n###### validarAntesDeTurnar ######"
				,"\n###### smap1=",smap1
				));
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(smap1 , "No se recibieron datos");
			
			String ntramite = smap1.get("ntramite");
			String status   = smap1.get("status");
			
			mesaControlManager.validarAntesDeTurnar(ntramite,status,usuario.getUser(),usuario.getRolActivo().getClave());
			
			success = true;
		}
		catch(Exception ex)
		{
			respuesta = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### validarAntesDeTurnar ######"
				,"\n##################################"
				));
		return SUCCESS;
	}
	
	public String generarCalculoSiniestros() {
		//log.debug("Entra a generarCalculoSiniestros smap2 de entrada :{}"+smap2);
		try {
			Map<String,String> factura        = null;
			Map<String,String> siniestroIte   = null;
			Map<String,String> proveedor      = null;
			Map<String,String> siniestro      = null;
			List<Map<String,String>>conceptos = null;
			
			List<Map<String,String>> lhosp        	= new ArrayList<Map<String,String>>();
			List<Map<String,String>>lpdir         	= new ArrayList<Map<String,String>>();
			List<Map<String,String>> lprem        	= new ArrayList<Map<String,String>>();
			List<Map<String,String>>listaImportesWS = new ArrayList<Map<String,String>>();
			
			List<Map<String, Object>> facturasxSiniestro 	= new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> aseguradosxSiniestro  = new ArrayList<Map<String,Object>>();
			List<Map<String, String>> conceptosxSiniestro   = new ArrayList<Map<String,String>>();
			
			
			String ntramite					  = smap2.get("ntramite");
			UserVO usuario					  = (UserVO)session.get("USUARIO");
			String cdrol					  = usuario.getRolActivo().getClave();
			
			
			// Obtenemos el tramite completo de MC
			Map<String,String> tramite     = siniestrosManager.obtenerTramiteCompleto(ntramite);
			//log.debug("Paso 1.- Tramite : {}",tramite);
			Map<String,String> smap = tramite;
			// Obtenemos las facturas del tramite
			List<Map<String,String>> facturasAux = siniestrosManager.obtenerFacturasTramite(ntramite);
			//log.debug("Paso 2.- Listado Factura : {}",facturasAux.size());
			if(tramite==null||facturasAux==null){
				throw new Exception("No se encontro tramite/facturas para el tramite");
			}

			siniestrosManager.movTimpsini(Constantes.DELETE_MODE, null, null, null, null,
					null, null, null, null, null,
					ntramite, null, null, null, null, null, false,null);
			//log.debug("Paso 3.- Eliminacion de TIMPSINI");
			boolean esPagoDirecto			  = false;
			// Validamos con el otvalor02 que es el tipo de pago
			if(TipoPago.DIRECTO.getCodigo().equals(tramite.get("OTVALOR02"))){
				esPagoDirecto = true;
			}
			//log.debug("Paso 4.- Es pago Directo : {} ",esPagoDirecto);
			
			/***************************** 		P A G O		D I R E C T O  		*************************/
			if(TipoPago.DIRECTO.getCodigo().equals(tramite.get("OTVALOR02"))) {
				//log.debug("Paso 5.- EL PROCESO DE PAGO ES DIRECTO ");
				smap.put("PAGODIRECTO","S");
				smap2     = facturasAux.get(0);
				// Obtenemos los datos del proveedor de acuerdo del CDPRESTA
				proveedor = siniestrosManager.obtenerDatosProveedor(facturasAux.get(0).get("CDPRESTA"));
				//log.debug("Paso 6.- Datos del Proveedor : {}",proveedor);
				Map<String,String> smap3     = proveedor;
				double ivaprov = Double.parseDouble(proveedor.get("IVA")); 
				double cedprov = Double.parseDouble(proveedor.get("CEDULAR"));
				double isrprov = Double.parseDouble(proveedor.get("ISR"));
				
				// Recorremos las facturas
				for(int i = 0; i < facturasAux.size(); i++){
					aseguradosxSiniestro = new ArrayList<Map<String,Object>>();
					factura = facturasAux.get(i);
					//log.debug("Paso 7.- Recorremos las Facturas  - El proceso i : {} de la factura : {}",i,factura.get("NFACTURA"));

					Map<String,Object>facturaObj = new HashMap<String,Object>();
					facturaObj.putAll(factura);
					facturasxSiniestro.add(facturaObj);
					
					// Obtenemos los ASEGURADOS por medio del numero de tramite y factura
					List<Map<String,String>> siniestros = siniestrosManager.listaSiniestrosMsiniesTramite(ntramite,factura.get("NFACTURA"),null);
					//log.debug("Paso 8.- Obtenemos los Asegurados (MSINIEST) : {}",siniestros);
					
					// Obtenemos los conceptos de la factura
					conceptos = siniestrosManager.P_GET_CONCEPTOS_FACTURA(null, tramite.get("CDRAMO"), null, null, null, null, null, 
							null, null, factura.get("NFACTURA"),tramite.get("CDTIPSIT"));
					//log.debug("Paso 9.- Obtenemos la informacion de los conceptos de la factura : {}", conceptos);
					
					// Recorremos el arreglo de asegurados
						for( int j= 0; j < siniestros.size();j++){
							// Asignacion de los variables a ocupar
							String	aplicaPenalCircHosp			= "S";
							String	aplicaPenalZonaHosp			= "S";
							
							//log.debug("Paso 10.- Recorremos los Siniestros - El proceso j : {} Siniestro : {}",j,siniestros.get(j));
							siniestroIte    = siniestros.get(j);
							
							//Validamos si tenemos autorizacion de servicio
							if(StringUtils.isNotBlank(siniestroIte.get("NMAUTSER"))){
								List<AutorizacionServicioVO> lista = siniestrosManager.getConsultaAutorizacionesEsp(siniestroIte.get("NMAUTSER"));
								//log.debug("Paso 10.1.- Verificamos la informacion si tiene Autorizacion de servicio : {} ",lista.size());
								aplicaPenalCircHosp		  = lista.get(0).getAplicaCirHos()+"";
								aplicaPenalZonaHosp		  = lista.get(0).getAplicaZonaHosp()+"";
							}
							
							Map<String,Object>aseguradoObj = new HashMap<String,Object>();
							aseguradoObj.putAll(siniestroIte);
							aseguradosxSiniestro.add(aseguradoObj);
							
							String cdunieco 					= siniestroIte.get("CDUNIECO");
							String cdramo   					= siniestroIte.get("CDRAMO");
							String estado   					= siniestroIte.get("ESTADO");
							String nmpoliza 					= siniestroIte.get("NMPOLIZA");
							String nmsuplem 					= siniestroIte.get("NMSUPLEM");
							String nmsituac 					= siniestroIte.get("NMSITUAC");
							String aaapertu 					= siniestroIte.get("AAAPERTU");
							String status   					= siniestroIte.get("STATUS");
							String cdtipsit 					= siniestroIte.get("CDTIPSIT");
							String nmsinies 					= siniestroIte.get("NMSINIES");
							String nfactura 					= factura.get("NFACTURA");
							
							double penalizacionCambioZona		= 0d;
							double penalizacionCirculoHosp		= 0d;
							String aplicaIVA					= "S";
							String seleccionAplica				= "D";
							String ivaRetenido					= "N";
							double deducibleSiniestroIte		= 0d;
							double copagoAplicadoSiniestroIte	= 0d;
							double cantidadCopagoSiniestroIte	= 0d;
							String penalizacionPesos 			= "0";
							String penalizacionPorcentaje		= "0";
							boolean existeCobertura				= false;
							
							Map<String,String>autorizacionesFactura = siniestrosManager.obtenerAutorizacionesFactura(
									cdunieco, cdramo, estado, nmpoliza, nmsuplem,
									nmsituac, aaapertu, status, nmsinies, nfactura);
									facturaObj.put("AUTMEDIC"+nmsinies,autorizacionesFactura.get("AUTMEDIC"));
									facturaObj.put("COMMENME"+nmsinies,autorizacionesFactura.get("COMMENME"));
									facturaObj.put("AUTRECLA"+nmsinies,autorizacionesFactura.get("AUTRECLA"));
									facturaObj.put("COMMENAR"+nmsinies,autorizacionesFactura.get("COMMENAR"));
									//log.debug("Paso 11.- Autorizacion de la informacion de la factura : {}", autorizacionesFactura);
									
							// Obtenemos los datos generales del siniestro
							List<Map<String,String>> informacionGral = siniestrosManager.obtieneDatosGeneralesSiniestro(cdunieco, cdramo,
									estado, nmpoliza,nmsituac, nmsuplem, status, aaapertu, nmsinies, factura.get("NTRAMITE"));
							//log.debug("Paso 12.- Datos generales del Siniestro : {} ",informacionGral);
							
							if(informacionGral.size()> 0){
								aseguradoObj.put("CAUSASINIESTRO", informacionGral.get(0).get("CDCAUSA"));
								if(cdramo.toString().equalsIgnoreCase(Ramo.GASTOS_MEDICOS_MAYORES.getCdramo())){
									if(informacionGral.get(0).get("CDCAUSA").toString().equalsIgnoreCase(CausaSiniestro.ENFERMEDAD.getCodigo())){
										// Verificamos la Cobertura que tiene el asegurado
										HashMap<String, Object> paramCobertura = new HashMap<String, Object>();
										paramCobertura.put("pv_ntramite_i",ntramite);
										paramCobertura.put("pv_tipopago_i",tramite.get("OTVALOR02"));
										paramCobertura.put("pv_nfactura_i",nfactura);
										paramCobertura.put("pv_cdunieco_i",cdunieco);
										paramCobertura.put("pv_estado_i",estado);
										paramCobertura.put("pv_cdramo_i",cdramo);
										paramCobertura.put("pv_nmpoliza_i",nmpoliza);
										paramCobertura.put("pv_nmsituac_i",nmsituac);
										paramCobertura.put("pv_cdgarant_i",null);
										List<CoberturaPolizaVO> listaCobertura = siniestrosManager.getConsultaCoberturaAsegurado(paramCobertura);
										//log.debug("Paso 13.- Listado de Coberturas  : {} ",listaCobertura);
										for(int j1 = 0 ;j1 < listaCobertura.size();j1++){
											if(listaCobertura.get(j1).getCdgarant().toString().equalsIgnoreCase("7EDA")){
												existeCobertura = true;
											}
										}
									}
								}
							}else{
								aseguradoObj.put("CAUSASINIESTRO", CausaSiniestro.ENFERMEDAD.getCodigo());
							}
							// Obtenemos la informacion del Deducible y Copago
							Map<String,String>copagoDeducibleSiniestroIte =siniestrosManager.obtenerCopagoDeducible(
									ntramite, cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies,nfactura,tramite.get("OTVALOR02"), cdtipsit);
							//log.debug("Paso 13.- Informacion Deducible/Copago : {}",copagoDeducibleSiniestroIte);
							
							String tipoFormatoCalculo			= copagoDeducibleSiniestroIte.get("FORMATOCALCULO");
							String calculosPenalizaciones		= copagoDeducibleSiniestroIte.get("PENALIZACIONES");
							
							facturaObj.put("TIPOFORMATOCALCULO",""+tipoFormatoCalculo);
							facturaObj.put("CALCULOSPENALIZACIONES",""+calculosPenalizaciones);
							//log.debug("Paso 14.- Aplica Penalizacion : {} ",calculosPenalizaciones);
							// Verificamos si aplica penalizaciones
							if(calculosPenalizaciones.equalsIgnoreCase("1")){
								// Generamos el map para pasar la informacion
								HashMap<String, Object> paramExclusion = new HashMap<String, Object>();
								paramExclusion.put("pv_cdunieco_i",cdunieco);
								paramExclusion.put("pv_estado_i",estado);
								paramExclusion.put("pv_cdramo_i",cdramo);
								paramExclusion.put("pv_nmpoliza_i",nmpoliza);
								paramExclusion.put("pv_nmsituac_i",nmsituac);
								
								if(cdramo.equalsIgnoreCase(Ramo.SALUD_VITAL.getCdramo()) || cdramo.equalsIgnoreCase(Ramo.GASTOS_MEDICOS_MAYORES.getCdramo())){
									//log.debug("Valor de aplicaPenalZonaHosp : {} ",aplicaPenalZonaHosp);
									//log.debug("Valor de aplicaPenalCircHosp : {} ",aplicaPenalCircHosp);
									// Validacion por Cambio de Zona
									if(aplicaPenalZonaHosp.equalsIgnoreCase("N")){
										penalizacionCambioZona = 0d;
									}else{
										String existePenalizacion = siniestrosManager.validaExclusionPenalizacion(paramExclusion);
										penalizacionCambioZona = penalizacionCambioZona(existePenalizacion,informacionGral.get(0).get("CDCAUSA"),informacionGral.get(0).get("CIRHOSPI"),
										informacionGral.get(0).get("DSZONAT"),informacionGral.get(0).get("CDPROVEE"),cdramo);
									}
									
									// Validacion por Circulo Hospitalario
									if(aplicaPenalCircHosp.equalsIgnoreCase("N")){
										penalizacionCirculoHosp = 0d;
									}else{
										// Obtenemos la validacion de Circulo Hospitalario
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
							
							//log.debug("Paso 15.- Existe Exclusion de Penalizacion : {} ",existePenalizacion);
							//log.debug("Paso 16.- Penalizacion por Cambio de Zona : {} ",penalizacionCambioZona);
							//log.debug("Paso 17.- Penalizacion por Circulo Hospitalario : {} ",penalizacionCirculoHosp);
							
							String calcularTotalPenalizacion = calcularTotalPenalizacion(penalizacionCambioZona,penalizacionCirculoHosp,informacionGral.get(0).get("CDCAUSA"),
									copagoDeducibleSiniestroIte.get("COPAGO"),copagoDeducibleSiniestroIte.get("TIPOCOPAGO"),
									informacionGral.get(0).get("CDPROVEE"),cdramo, informacionGral.get(0).get("FEOCURRE"));
							//log.debug("Paso 18.- Total Penalizacion : {} ",calcularTotalPenalizacion);
							
							aseguradoObj.put("TOTALPENALIZACIONGLOBAL",""+calcularTotalPenalizacion);
							
							// Obtenemos los valores de los Copago en Porcentaje y Copago en Pesos
							String penalizacionT[] = calcularTotalPenalizacion.split("\\|");
							penalizacionPorcentaje = penalizacionT[0].toString();
							penalizacionPesos = penalizacionT[1].toString();
							aseguradoObj.put("COPAGOPORCENTAJES",penalizacionPorcentaje);
							aseguradoObj.put("COPAGOPESOS",penalizacionPesos);
							
							List<Map<String, String>> listaFactura = siniestrosManager.P_GET_FACTURAS_SINIESTRO(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, cdtipsit);
							//log.debug("Paso 19.- Informacion de la Factura aplicacion de IVA's : {} ",listaFactura);
							
							if(listaFactura.get(0).get("APLICA_IVA") != null){
								aplicaIVA       = listaFactura.get(0).get("APLICA_IVA");
								seleccionAplica = listaFactura.get(0).get("ANTES_DESPUES");
								ivaRetenido     = listaFactura.get(0).get("IVARETENIDO");
								if(!StringUtils.isNotBlank(ivaRetenido)){
									ivaRetenido = "N";
								}
							}
							
							String sDeducibleSiniestroIte		= copagoDeducibleSiniestroIte.get("DEDUCIBLE").replace(",","");
							String sCopagoSiniestroIte			= copagoDeducibleSiniestroIte.get("COPAGO").replace(",", "");
							String tipoCopagoSiniestroIte		= copagoDeducibleSiniestroIte.get("TIPOCOPAGO");
							
							if(StringUtils.isNotBlank(sDeducibleSiniestroIte)
									&&(!sDeducibleSiniestroIte.equalsIgnoreCase("na"))
									&&(!sDeducibleSiniestroIte.equalsIgnoreCase("no"))
							){
								try{
									deducibleSiniestroIte = Double.valueOf(sDeducibleSiniestroIte);
								}
								catch(Exception ex){
									log.debug(""
											+ "\n### ERROR ##################################################"
											+ "\n### no es numero deducible: '"+sDeducibleSiniestroIte+"' ###"
											+ "\n############################################################"
									);
									deducibleSiniestroIte = 0d;
								}
							}
							
							if(cdramo.toString().equalsIgnoreCase(Ramo.GASTOS_MEDICOS_MAYORES.getCdramo())){//GMMI
								if(existeCobertura == true){
									deducibleSiniestroIte = 0d;
								}
							}
							//log.debug("Paso 20.- Valor de deducibleSiniestroIte : {} ",deducibleSiniestroIte);
							
							if(StringUtils.isNotBlank(sCopagoSiniestroIte)
									&&(!sCopagoSiniestroIte.equalsIgnoreCase("na"))
									&&(!sCopagoSiniestroIte.equalsIgnoreCase("no"))
							){
								try {
									cantidadCopagoSiniestroIte = Double.valueOf(sCopagoSiniestroIte);
								}
								catch(Exception ex){
									log.debug(""
											+ "\n### ERROR ############################################"
											+ "\n### no es numero copago: '"+sCopagoSiniestroIte+"' ###"
											+ "\n######################################################"
									);
									cantidadCopagoSiniestroIte = 0d;
								}
							}
							//log.debug("Paso 21.- Valor de cantidadCopagoSiniestroIte : {} ",cantidadCopagoSiniestroIte);
							
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
							hosp.put("PTIMPORT"		, "0");
							hosp.put("DESTO"		, "0");
							hosp.put("IVA"			, "0");
							hosp.put("PRECIO"		, "0");
							hosp.put("DESCPRECIO"	, "0");
							hosp.put("IMPISR"		, "0");
							hosp.put("IMPCED"		, "0");
							
							//reembolso
							Map<String,String>mprem=new HashMap<String,String>();
							mprem.put("dummy","dummy");
							lprem.add(mprem);
							
							//pago directo
							Map<String,String> mpdir = new HashMap<String,String>();
							mpdir.put("total",				"0");
							mpdir.put("totalcedular",		"0");
							mpdir.put("ivaTotalMostrar",	"0");
							mpdir.put("ivaRetenidoMostrar",	"0");
							mpdir.put("iSRMostrar",			"0");
							lpdir.add(mpdir);
							conceptosxSiniestro = new ArrayList<Map<String,String>>();
							
							// Recorremos los conceptos de la factura
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
									//log.debug("Datos de los conceptos  contador k: {} ",k);
									//log.debug("Datos de los conceptos  concepto : {}  COPAGO: {} ",concepto,concepto.get("COPAGO"));
									
									if(tipoFormatoCalculo.equalsIgnoreCase("1")) {
										log.debug("--->>>>>>> HOSPITALIZACION");
										double PTIMPORT    = Double.parseDouble(concepto.get("PTIMPORT"));
										double DESTOPOR    = Double.parseDouble(concepto.get("DESTOPOR"));
										double DESTOIMP    = Double.parseDouble(concepto.get("DESTOIMP"));
										double PTPRECIO    = Double.parseDouble(concepto.get("PTPRECIO")) * Double.parseDouble(concepto.get("CANTIDAD"));
										boolean copagoPorc = false;
										String scopago     = concepto.get("COPAGO");
										
										if(scopago.equalsIgnoreCase("no") ||scopago.equalsIgnoreCase("na")){
											scopago="0";
										}
										//log.debug("Valor de respuesta :{}",StringUtils.isNotBlank(scopago));
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
										
										//Cuando nos den el VoBo. lo tendremos que decomentar
										if(aplicaIVA.equalsIgnoreCase("S")){
											//verificamos si aplica para el concepto 
											if(concepto.get("APLICIVA").equalsIgnoreCase("S")){
												hIVA      	+= PTIMPORT*(ivaprov/100d);
											}
										}else{
											hIVA      	+= PTIMPORT*(ivaprov/100d);
										}
										//hIVA      	+= PTIMPORT*(ivaprov/100d);
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
										
										/*log.debug("Concepto importe 			    : {} ",PTIMPORT);
										log.debug("Concepto desto 			    : {} ",DESTOPOR);
										log.debug("Concepto destoimp 		    : {} ",DESTOIMP);
										log.debug("Usando iva proveedor          : {} ",ivaprov);
										log.debug("Concepto copago               : {} ",scopago);
										log.debug("Concepto desto + copago %     : {} ",DESTOPOR);
										log.debug("Concepto destoimp + copago $  : {} ",DESTOIMP);
										
										log.debug("#### VALORES DEL VECTOR #####");
										log.debug("Concepto hPTIMPORT total      : {} ",hPTIMPORT);
										log.debug("Concepto hDESTO total         : {} ",hDESTO);
										log.debug("Concepto hIVA total           : {} ",hIVA);
										log.debug("Concepto hISR total 		    : {} ",hISR);
										log.debug("Concepto hPRECIO total        : {} ",hPRECIO);
										log.debug("Concepto hDESCPRECIO total    : {} ",hDESCPRECIO);
										log.debug("Concepto hICED total          : {} ",hICED);*/
										log.debug("<<<<<<<--- HOSPITALIZACION");
									}else {
										log.debug("--->>>>>>> PAGO DIFERENTE DE HOSPITALIZACION Y AYUDA DE MATERNIDAD  ---->>>>>>>>>> ");
										double precioArancel		= 0d;
										double descuentoPorc		= 0d;
										double descuentoImpo		= 0d;
										boolean copagoPorc			= false;
										double  copago				= 0d;
										double  copagoAplicado		= 0d;
										
										Map<String,String>row = new HashMap<String,String>();
										row.putAll(concepto);
										double cantidad				= Double.valueOf(row.get("CANTIDAD"));
										
										if(concepto.get("CDCONCEP").equalsIgnoreCase("-1")){
											String scopago 			 = concepto.get("COPAGO");
											scopago=scopago.replace("%", "").replace("$", "").replaceAll(",", "");
											copago=Double.valueOf(scopago);
											precioArancel  = copago;
										}else{
											precioArancel 		= Double.valueOf(row.get("IMP_ARANCEL"));
										}
										row.put("IMP_ARANCEL",precioArancel+"");
										
										double subtotalArancel 	= cantidad*precioArancel;
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
										
										/*log.debug("Concepto cantidad 			 : {} ",cantidad);
										log.debug("Concepto precioArancel 		 : {} ",precioArancel);
										log.debug("Concepto subtotalArancel 		 : {} ",subtotalArancel);
										log.debug("Concepto descuentoAplicado 	 : {} ",descuentoAplicado);
										log.debug("Concepto subtotalDescuento 	 : {} ",subtotalDescuento);
										log.debug("Concepto copagoAplicado        : {} ",copagoAplicado);
										log.debug("Concepto subtotalCopago 		 : {} ",subtotalCopago);
										log.debug("Concepto israplicado 			 : {} ",israplicado);
										log.debug("Concepto subtotalImpuestos 	 : {} ",subtotalImpuestos);
										log.debug("Concepto base totalISRMostrar  : {} ",totalISRMostrar);
										log.debug("Concepto cedularaplicado 		 : {} ",cedularaplicado);*/
										
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
										//log.debug("Concepto base totalIVAMostrar : {} ",totalIVAMostrar);
										totalIVAMostrar += ivaaplicado;
										//log.debug("Sumatoria totalIVAMostrar : {} ",totalIVAMostrar);
										mpdir.put("ivaTotalMostrar",totalIVAMostrar+"");
										
										double totalIVARetenidoMostrar = Double.parseDouble(mpdir.get("ivaRetenidoMostrar"));
										//log.debug("Concepto base totalIVARetenidoMostrar : {} ",totalIVARetenidoMostrar);
										
										totalIVARetenidoMostrar += ivaRetenidos;
										//log.debug("Sumatoria totalIVAMostrar      : {} ",totalIVARetenidoMostrar);
										mpdir.put("ivaRetenidoMostrar",totalIVARetenidoMostrar+"");
										
										double ptimport = Double.parseDouble(row.get("PTIMPORT"));
										double valorusado      = ptimportauto;
										
										String autmedic = row.get("AUTMEDIC");
										if(StringUtils.isNotBlank(autmedic)&&autmedic.equalsIgnoreCase("S")){
											valorusado = ptimport;
										}
										row.put("VALORUSADO",valorusado+"");
										
										//log.debug("Concepto ptimport              : {} ",ptimport);
										//log.debug("Concepto valorusado            : {} ",valorusado);
										
										double totalGrupo = Double.parseDouble(mpdir.get("total"));
										//log.debug("Concepto base totalGrupo       : {} ",totalGrupo);
										totalGrupo += valorusado;
										//log.debug("Sumatoria totalGrupo           : {} ",totalGrupo);
										mpdir.put("total",totalGrupo+"");
										
										double totalGrupoCedular = Double.parseDouble(mpdir.get("totalcedular"));
										//log.debug("Concepto base totalGrupoCedular  : {} ",totalGrupoCedular);
										totalGrupoCedular += cedularaplicado;
										//log.debug("Sumatoria totalGrupoCedular      : {} ",totalGrupoCedular);
										mpdir.put("totalcedular",totalGrupoCedular+"");
										
										concepto.putAll(row);
										log.debug("<<PAGO DIRECTO DIFERENTE A HOSPITALIZACION Y AYUDA DE MATERNIDAD");
									}
								}
							}//FIN DE CONCEPTOS
							aseguradoObj.put("conceptosAsegurado", conceptosxSiniestro);
							
							//hospitalizacion
							// log.debug("######  HOSPITALIZACI�N Y AYUDA DE MATERNIDAD WS ######");
							if(tipoFormatoCalculo.equalsIgnoreCase("1")){
								log.debug("--->>>> WS del siniestro iterado Hospitalizacion y Ayuda de Maternidad");
								/*log.debug("deducible siniestro iterado     : {} ",sDeducibleSiniestroIte);
								log.debug("copago siniestro iterado        : {} ",sCopagoSiniestroIte);
								log.debug("tipo copago siniestro iterado   : {} ",tipoCopagoSiniestroIte);
								
								log.debug("Hospitalizacion Importe    : {} ",hosp.get("PTIMPORT"));
								log.debug("Hospitalizacion Descuento  : {} ",hosp.get("DESTO"));
								log.debug("Hospitalizacion IVA        : {} ",hosp.get("IVA"));
								log.debug("Hospitalizacion Deducible  : {} ",deducibleSiniestroIte);*/
								
								double hPTIMPORT = Double.valueOf(hosp.get("PTIMPORT"));
								double DESTOIMP  = Double.valueOf(hosp.get("DESTO"));
								double hIVA      = Double.valueOf(hosp.get("IVA"));
								
								String causadelSiniestro = informacionGral.get(0).get("CDCAUSA");
								double subttDesto =0d;
								
								if(!causadelSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){ // Diferente de Accidente
									subttDesto = (hPTIMPORT + DESTOIMP) - (DESTOIMP + deducibleSiniestroIte );
								}else{
									if(cdramo.equalsIgnoreCase(Ramo.GASTOS_MEDICOS_MAYORES.getCdramo())){
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
								//Cambiarlo cuando nos del el Vo.Bo. de Siniestros
								double hIVADesCopago  = Double.valueOf(hosp.get("IVA"));
								//double hIVADesCopago  = importeSiniestroIte*(ivaprov/100d);
								//log.debug("IVA despues de Copago  : {} ",hIVADesCopago);
								
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
								//log.debug("mapa WS siniestro iterado : {} ",importesWSSiniestroIte);
								log.debug("<<<-- WS del siniestro iterado Hospitalizacion y ayuda de Maternidad");
							}else{
								log.debug("-->>>> WS del siniestro iterado Diferente de Hospitalizacion y Ayuda de Maternidad ");
								//log.debug("deducible siniestro iterado : {} ",sDeducibleSiniestroIte);
								//log.debug("copago siniestro iterado : {} ",sCopagoSiniestroIte);
								//log.debug("tipo copago siniestro iterado : {} ",tipoCopagoSiniestroIte);
								
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
								
								//log.debug(message);
								
								if(aplicaIVA.equalsIgnoreCase("S")){
									importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(subttcopagototalSiniestroIte)).toString());
									/*if(seleccionAplica.equalsIgnoreCase("D")){
										importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(subttcopagototalSiniestroIte)).toString());
									}else{
										importesWSSiniestroIte.put(IMPORTE_WS_IMPORTE , (new Double(subttDescuentoSiniestroIte)).toString());
									}*/
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
								//log.debug("mapa WS siniestro iterado: {} ",importesWSSiniestroIte);
								log.debug("<<WS del siniestro iterado Diferente de Hospitalizacion y Ayuda de Maternidad ");
							}
						}
						facturaObj.put("siniestroPD", aseguradosxSiniestro);
				}
			}
			
			if(conceptos!=null&&conceptos.size()>0){
				//log.debug("conceptos[0] :{}",conceptos);
			}
			//	aqui verificamos toda la informaci�n del WS
			log.debug("###VALOR A IMPRIMIR#####");
			//log.debug("listaImportesWS :{}",listaImportesWS);
			//log.debug("Tipo de pago :{}",esPagoDirecto);
			//log.debug("facturasxSiniestro :{}",facturasxSiniestro);
			try{
				int nmsecsin= 1;
				for(Map<String,String> importe : listaImportesWS){
					//log.debug("Valor de Importe :{}",importe);
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
							,false
							,nmsecsin+"");
						nmsecsin++;
				}
				log.debug("VALORES DE LAS FACTURAS---->");
				//log.debug("Total de registros :{}",facturasxSiniestro.size());
				//log.debug("facturasxSiniestro :{}",facturasxSiniestro);
				int nmSecSinFac= 1;
				for(Map<String, Object> totalFacturaIte : facturasxSiniestro){
					//log.debug("VALOR DE LAS FACTURAS :{}",totalFacturaIte);
					String ntramiteA     = (String) totalFacturaIte.get("NTRAMITE");
					String nfacturaA     = (String) totalFacturaIte.get("NFACTURA");
					String totalFacturaA = (String) totalFacturaIte.get("TOTALFACTURAIND");
					siniestrosManager.guardarTotalProcedenteFactura(ntramiteA,nfacturaA,totalFacturaA, nmSecSinFac+"");
					nmSecSinFac++;
					try{
						HashMap<String, Object> paramsPagoDirecto = new HashMap<String, Object>();
						paramsPagoDirecto.put("pv_ntramite_i",ntramiteA);
						String montoTramite = siniestrosManager.obtieneMontoTramitePagoDirecto(paramsPagoDirecto);
						
						log.debug("Valor del Monto del Arancel ===>>>> "+montoTramite);
						Map<String,Object> otvalor = new HashMap<String,Object>();
						otvalor.put("pv_ntramite_i" , ntramiteA);
						otvalor.put("pv_otvalor03_i"  , montoTramite);
						siniestrosManager.actualizaOTValorMesaControl(otvalor);
					}catch(Exception ex){
						//log.debug("error al actualizar el importe de la mesa de control : {}", ex.getMessage(), ex);
					}
					
				}
				success = true; 
				mensaje = "Datos guardados";
			}catch(Exception ex){
				//logger.error("Error al guardaar calculos : {}", ex.getMessage(), ex);
				success = false;
				mensaje = ex.getMessage();
			}
		}catch(Exception ex){
			//log.debug("error al cargar pantalla de calculo de siniestros : {}", ex.getMessage(), ex);
		}
		return SUCCESS;
	}
	
	private String calcularTotalPenalizacion(double penalizacionCambioZona, double penalizacionCirculoHosp, String causaSiniestro, String copagoOriginal, String tipoCopago,String proveedor,String ramo, String fechaOcurrencia) {
		//logger.debug("Entra a calcularPenalizacionCirculo penalizacionCambioZona : {} penalizacionCirculoHosp : {} causaSiniestro : {} copagoOriginal : {} tipoCopago : {} proveedor : {} ramo : {} fechaOcurrencia : {} ",
		//		penalizacionCambioZona, penalizacionCirculoHosp, causaSiniestro, copagoOriginal, tipoCopago, proveedor,ramo, fechaOcurrencia);
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
				if(ramo.equalsIgnoreCase(Ramo.SALUD_VITAL.getCdramo()) || ramo.equalsIgnoreCase(Ramo.GASTOS_MEDICOS_MAYORES.getCdramo())){ //SALUD VITAL
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
						List<Map<String,String>> datosInformacionAdicional = siniestrosManager.listaConsultaCirculoHospitalarioMultisalud(proveedor,ramo,renderFechas.parse(fechaOcurrencia));
						//logger.debug("Total de registros {}",datosInformacionAdicional.size());
						if(datosInformacionAdicional.size() > 0){
							//logger.debug("Multi Incremento :{} ",datosInformacionAdicional.get(0).get("MULTINCREMENTO"));
							//logger.debug("Hospital Plus : {}   ",datosInformacionAdicional.get(0).get("HOSPITALPLUS"));
							//logger.debug("% Incremento : {}    ",datosInformacionAdicional.get(0).get("PORCINCREMENTO"));

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
						//logger.error("error al obtener los datos del proveedor : {}", e.getMessage(), e);
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
		return copagoFinal;
	}
	

	private double calcularPenalizacionCirculo(String circuloHospAsegurado, String circuloHospProveedor, String causaSiniestro,String ramo){
		//logger.debug("Entra a calcularPenalizacionCirculo circuloHospAsegurado : {} circuloHospProveedor : {}  causaSiniestro : {}  ramo : {} ",
		//			circuloHospAsegurado, circuloHospProveedor, causaSiniestro, ramo);
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
				String porcentajePenalizacion = siniestrosManager.penalizacionCirculoHospitalario(paramPenalizacion);
				penaliCirculoHosp      = Double.parseDouble(porcentajePenalizacion);
			} catch (Exception ex) {
				//logger.debug("Error en la obtencion de la consulta : {}", ex.getMessage(), ex);
				penaliCirculoHosp =  Double.parseDouble("0");
			}
		}
		return penaliCirculoHosp;
	}
	

	/**
	* Funcion para obtener el total de Penalizacion por cambio de Zona
	* @param existePenalizacion,causaSiniestro,circuloHospAsegurado,zonaTarifiAsegurado,idProveedor,cdRamo
	* @return penalizacionCambioZona
	*/
	private double penalizacionCambioZona(String existePenalizacion, String causaSiniestro, String circuloHospAsegurado,
			String zonaTarifiAsegurado, String idProveedor, String cdRamo) {
		//logger.debug("Entra a penalizacionCambioZona existePenalizacion : {}  causaSiniestro :{}  circuloHospAsegurado :{}  zonaTarifiAsegurado :{}  idProveedor :{}  cdRamo :{} ",
		//			existePenalizacion,causaSiniestro,circuloHospAsegurado,zonaTarifiAsegurado,idProveedor,cdRamo);
		
		double penalizacionCambioZona = 0;
		if(causaSiniestro!= null){
			if(!causaSiniestro.equalsIgnoreCase(CausaSiniestro.ACCIDENTE.getCodigo())){
				if(!existePenalizacion.equalsIgnoreCase("S")){
					try{
						List<ConsultaProveedorVO> medicos = siniestrosManager.getConsultaListaProveedorMedico(TipoPrestadorServicio.MEDICO.getCdtipo(),idProveedor);
						String porcentajePenalizacion = siniestrosManager.validaPorcentajePenalizacion(zonaTarifiAsegurado, medicos.get(0).getZonaHospitalaria(), cdRamo);
						penalizacionCambioZona =  Double.parseDouble(porcentajePenalizacion);
					}catch(Exception ex){
						//logger.debug("Error en la obtencion de la consulta : {}", ex.getMessage(), ex);
						penalizacionCambioZona =  Double.parseDouble("0");
					}
				}
			}
		}
		return penalizacionCambioZona;
	}
	
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

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getTmpNtramite() {
		return tmpNtramite;
	}

	public void setTmpNtramite(String tmpNtramite) {
		this.tmpNtramite = tmpNtramite;
	}

	public void setMesaControlManager(MesaControlManager mesaControlManager) {
		this.mesaControlManager = mesaControlManager;
	}

	public void setServiciosManager(ServiciosManager serviciosManager) {
		this.serviciosManager = serviciosManager;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
}