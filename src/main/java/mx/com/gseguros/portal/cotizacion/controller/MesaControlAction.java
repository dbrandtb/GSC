package mx.com.gseguros.portal.cotizacion.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.portal.general.util.ConstantesCatalogos;

public class MesaControlAction extends PrincipalCoreAction implements ConstantesCatalogos
{
	
	private static final long serialVersionUID = -3398140781812652316L;
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MesaControlAction.class);
	private KernelManagerSustituto kernelManager;
	private Map<String,String>smap1;
	private Map<String,String>smap2;
	private List<Map<String,String>>slist1;
	private List<Map<String,String>>slist2;
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
			
			slist1=kernelManager.loadMesaControl(dsrol);
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
			Map<String,Object>omap=new LinkedHashMap<String,Object>(0);
			Iterator it=smap1.entrySet().iterator();
			while(it.hasNext())
			{
				Entry entry=(Entry)it.next();
				omap.put((String)entry.getKey(),entry.getValue());
			}
			omap.put("pv_ferecepc_i",new Date());
			omap.put("pv_festatus_i",new Date());
			WrapperResultados res = kernelManager.PMovMesacontrol(omap);
			if(res.getItemMap() == null)log.error("Sin mensaje respuesta de nmtramite!!");
			else msgResult = (String) res.getItemMap().get("ntramite");
					log.debug("TRAMITE RESULTADO: "+msgResult);
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
			kernelManager.mesaControlUpdateStatus(smap1.get("ntramite"),smap1.get("status"));
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

	public String getCON_CAT_POL_ESTADO() {
		// TODO Auto-generated method stub
		return CON_CAT_POL_ESTADO;
	}

	public String getCON_CAT_POL_TIPO_POLIZA() {
		// TODO Auto-generated method stub
		return CON_CAT_POL_TIPO_POLIZA;
	}

	public String getCON_CAT_POL_TIPO_PAGO() {
		// TODO Auto-generated method stub
		return CON_CAT_POL_TIPO_PAGO;
	}

	public String getCON_CAT_POL_ROL() {
		// TODO Auto-generated method stub
		return CON_CAT_POL_ROL;
	}

	public String getCON_CAT_MESACONTROL_SUCUR_ADMIN() {
		// TODO Auto-generated method stub
		return CON_CAT_MESACONTROL_SUCUR_ADMIN;
	}

	public String getCON_CAT_MESACONTROL_SUCUR_DOCU() {
		// TODO Auto-generated method stub
		return CON_CAT_MESACONTROL_SUCUR_DOCU;
	}

	public String getCON_CAT_MESACONTROL_TIP_TRAMI() {
		// TODO Auto-generated method stub
		return CON_CAT_MESACONTROL_TIP_TRAMI;
	}

	public String getCON_CAT_MESACONTROL_ESTAT_TRAMI() {
		// TODO Auto-generated method stub
		return CON_CAT_MESACONTROL_ESTAT_TRAMI;
	}

	public String getCON_CAT_TPERSONA() {
		return CON_CAT_TPERSONA;
	}

	public String getCON_CAT_NACIONALIDAD() {
		return CON_CAT_NACIONALIDAD;
	}

	public String getMsgResult() {
		return msgResult;
	}

	public void setMsgResult(String msgResult) {
		this.msgResult = msgResult;
	}
	
}