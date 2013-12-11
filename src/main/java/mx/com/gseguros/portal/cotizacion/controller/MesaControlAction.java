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
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;

public class MesaControlAction extends PrincipalCoreAction
{
	
	private static final long serialVersionUID = -3398140781812652316L;
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MesaControlAction.class);
	private KernelManagerSustituto kernelManager;
	private Map<String,String>smap1;
	private Map<String,String>smap2;
	private List<Map<String,String>>slist1;
	private List<Map<String,String>>slist2;
	private List<GenericVO> lista;
	private String msgResult;
	private boolean success;
	
	public String principal()
	{
		log.debug(""
				+ "\n#######################################"
				+ "\n#######################################"
				+ "\n###### mesa de control principal ######"
				+ "\n######                           ######"
				);
		UserVO usu=(UserVO) session.get("USUARIO");
		String dsrol="";
		if(usu!=null
		    &&usu.getRolActivo()!=null
		    &&usu.getRolActivo().getObjeto()!=null
		    &&usu.getRolActivo().getObjeto().getValue()!=null)
		{
		    dsrol=usu.getRolActivo().getObjeto().getValue();
		}
		if(dsrol.equalsIgnoreCase("mesadecontrol")||dsrol.equalsIgnoreCase("suscriptor")||dsrol.equalsIgnoreCase("medico"))
		{
			if(smap1==null)
			{
				smap1=new HashMap<String,String>(0);
			}
			if((!smap1.containsKey("pv_status_i")))
			{
				log.debug("pv_status_i: "+smap1.get("pv_status_i"));
				smap1.put("pv_status_i","-1");//valor default
			}
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
	
	public String guardarTramiteManual()
	{
		log.debug(""
				+ "\n##################################################"
				+ "\n##################################################"
				+ "\n###### guardarTramiteManual                 ######"
				+ "\n######                                      ######"
				);
		try
		{
			UserVO usu=(UserVO)session.get("USUARIO");
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
        	parDmesCon.put("pv_cdusuari_i"   , usu.getUser());
        	kernelManager.movDmesacontrol(parDmesCon);
					
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al guardar tramite manual",ex);
			success=false;
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
				+ "\n##################################################"
				+ "\n##################################################"
				+ "\n###### actualizarStatusTramite              ######"
				+ "\n######                                      ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			UserVO usu=(UserVO)session.get("USUARIO");
			DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(usu.getUser());
			
			String statusNuevo=smap1.get("status");
			String ntramite=smap1.get("ntramite");
			String comments=smap1.get("comments");
			kernelManager.mesaControlUpdateStatus(ntramite,statusNuevo);
			
			if(statusNuevo.equals("1"))
			{
				log.debug("se inserta detalle nuevo");
            	Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
            	parDmesCon.put("pv_ntramite_i"   , ntramite);
            	parDmesCon.put("pv_feinicio_i"   , new Date());
            	parDmesCon.put("pv_cdclausu_i"   , null);
            	parDmesCon.put("pv_comments_i"   , "<p>El tr&aacute;mite fue turnado a revisi&oacute;n m&eacute;dica con las siguientes observaciones:</p>"+comments);
            	parDmesCon.put("pv_cdusuari_i"   , datUsu.getCdusuari());
            	kernelManager.movDmesacontrol(parDmesCon);
			}
			else if(statusNuevo.equals("4"))
			{
				log.debug("se inserta detalle nuevo");
            	Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
            	parDmesCon.put("pv_ntramite_i"   , ntramite);
            	parDmesCon.put("pv_feinicio_i"   , new Date());
            	parDmesCon.put("pv_cdclausu_i"   , null);
            	parDmesCon.put("pv_comments_i"   , "<p>La p&oacute;liza fue rechazada con los siguientes detalles:</p>"+comments);
            	parDmesCon.put("pv_cdusuari_i"   , datUsu.getCdusuari());
            	kernelManager.movDmesacontrol(parDmesCon);
			}
			else if(statusNuevo.equals("5"))
			{
				log.debug("se inserta detalle nuevo");
            	Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
            	parDmesCon.put("pv_ntramite_i"   , ntramite);
            	parDmesCon.put("pv_feinicio_i"   , new Date());
            	parDmesCon.put("pv_cdclausu_i"   , null);
            	parDmesCon.put("pv_comments_i"   , "<p>El m&eacute;dico revis&oacute; el tr&aacute;mite con las siguientes observaciones:</p>"+comments);
            	parDmesCon.put("pv_cdusuari_i"   , datUsu.getCdusuari());
            	kernelManager.movDmesacontrol(parDmesCon);
			}
			else if(statusNuevo.equals("6"))
			{
				log.debug("se inserta detalle nuevo");
            	Map<String,Object>parDmesCon=new LinkedHashMap<String,Object>(0);
            	parDmesCon.put("pv_ntramite_i"   , ntramite);
            	parDmesCon.put("pv_feinicio_i"   , new Date());
            	parDmesCon.put("pv_cdclausu_i"   , null);
            	parDmesCon.put("pv_comments_i"   , "<p>El m&eacute;dico revis&oacute; el tr&aacute;mite con las siguientes observaciones:</p>"+comments);
            	parDmesCon.put("pv_cdusuari_i"   , datUsu.getCdusuari());
            	kernelManager.movDmesacontrol(parDmesCon);
			}
			
			success=true;
		}
		catch(Exception ex)
		{
			success=false;
			log.error("error al actualizar status de tramite de mesa de control",ex);
		}
		log.debug(""
				+ "\n######                                      ######"
				+ "\n###### actualizarStatusTramite              ######"
				+ "\n##################################################"
				+ "\n##################################################"
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
	
	
	////////////////////////////////////////////////////////////////
	////// obtiene agentes por codigo o nombre                //////
	/*////////////////////////////////////////////////////////////*/
	public String obtieneAgentes(){
        try {
        	WrapperResultados result = kernelManager.obtieneAgentes(smap1.get("pv_cdagente_i"));
        	lista = (List<GenericVO>) result.getItemList(); 
             
        } catch(Exception ex) {
        	lista=new ArrayList<GenericVO>(0);
            success=false;
            logger.error("Error al obtener los agentes",ex);
        }
        success=true;
        return SUCCESS;
    }
	////////////////////////////////////////////////////////////////
	////// obtiene agentes por codigo o nombre                //////
	/*////////////////////////////////////////////////////////////*/
	
	//////////////////////////////////
	////// marco del supervisor //////
	/*//////////////////////////////*/
	public String marcoSupervisor()
	{
		log.debug(""
				+ "\n####################################################"
				+ "\n####################################################"
				+ "\n###### marco de supervisor de mesa de control ######"
				+ "\n######                                        ######"
				);
		success=true;
		log.debug(""
				+ "\n######                                        ######"
				+ "\n###### marco de supervisor de mesa de control ######"
				+ "\n####################################################"
				+ "\n####################################################"
				);
		return SUCCESS;
	}
	//////////////////////////////////
	////// marco del supervisor //////
	/*//////////////////////////////*/
	
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
	
}