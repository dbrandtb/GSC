package mx.com.gseguros.portal.cotizacion.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
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
			slist1=kernelManager.loadMesaControl();
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
	
	public String obtenerValoresDefectoInsercionManual()
	{
		log.debug(""
				+ "\n##################################################"
				+ "\n##################################################"
				+ "\n###### obtenerValoresDefectoInsercionManual ######"
				+ "\n######                                      ######"
				);
		/*
		try
		{
			UserVO user=(UserVO) session.get("USUARIO");
			DatosUsuario datUsu=kernelManager.obtenerDatosUsuario(user.getUser());
			smap1=new LinkedHashMap<String,String>(0);
			smap1.put("",datUsu.get)
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al obtener valores por defecto",ex);
			success=false;
		}
		*/
		log.debug(""
				+ "\n######                                      ######"
				+ "\n###### obtenerValoresDefectoInsercionManual ######"
				+ "\n##################################################"
				+ "\n##################################################"
				);
		return SUCCESS;
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
	
}