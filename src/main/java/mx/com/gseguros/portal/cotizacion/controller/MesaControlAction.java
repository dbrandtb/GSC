package mx.com.gseguros.portal.cotizacion.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
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
			kernelManager.PMovMesacontrol(omap);
			
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
	
}