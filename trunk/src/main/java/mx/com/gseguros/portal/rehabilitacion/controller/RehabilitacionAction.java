package mx.com.gseguros.portal.rehabilitacion.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.portal.rehabilitacion.service.RehabilitacionManager;

public class RehabilitacionAction extends PrincipalCoreAction
{

	private static final long              serialVersionUID      = 5306848466443116067L;
	private static org.apache.log4j.Logger log                   = org.apache.log4j.Logger.getLogger(RehabilitacionAction.class);
	private Map<String,String>             smap1;
	private List<Map<String,String>>       slist1;
	private boolean                        success               = false;
	private RehabilitacionManager          rehabilitacionManager;
	
	/////////////////////////////
	////// marco principal //////
	/*/////////////////////////*/
	public String marco()
	{
		log.debug(""
				+ "\n#####################################"
				+ "\n#####################################"
				+ "\n###### marco de rehabilitacion ######"
				+ "\n######                         ######"
				);
		success=true;
		log.debug(""
				+ "\n######                         ######"
				+ "\n###### marco de rehabilitacion ######"
				+ "\n#####################################"
				+ "\n#####################################"
				);
		return SUCCESS;
	}
	/*/////////////////////////*/
	////// marco principal //////
	/////////////////////////////
	
	/////////////////////////////////////////////
	////// buscar polizas para rehabilitar //////
	/*/////////////////////////////////////////*/
	public String buscarPolizas()
	{
		log.debug(""
				+ "\n#######################################"
				+ "\n#######################################"
				+ "\n###### buscar polizas canceladas ######"
				+ "\n######                           ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			slist1=rehabilitacionManager.buscarPolizas(smap1);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al obtener polizas",ex);
			slist1=null;
			success=false;
		}
		log.debug(""
				+ "\n######                           ######"
				+ "\n###### buscar polizas canceladas ######"
				+ "\n#######################################"
				+ "\n#######################################"
				);
		return SUCCESS;
	}
	/*/////////////////////////////////////////*/
	////// buscar polizas para rehabilitar //////
	/////////////////////////////////////////////
	
	//////////////////////////////////////////////
	////// pantalla de rehabilitacion unica //////
	/*//////////////////////////////////////////*/
	public String pantallaRehabilitacionUnica()
	{
		log.debug(""
				+ "\n###########################################"
				+ "\n###########################################"
				+ "\n###### pantalla rehabilitacion unica ######"
				+ "\n######                               ######"
				);
		log.debug("smap1: "+smap1);
		log.debug(""
				+ "\n######                               ######"
				+ "\n###### pantalla rehabilitacion unica ######"
				+ "\n###########################################"
				+ "\n###########################################"
				);
		return SUCCESS;
	}
	/*//////////////////////////////////////////*/
	////// pantalla de rehabilitacion unica //////
	//////////////////////////////////////////////
	
	/////////////////////////////////
	////// rehabilitacionUnica //////
	/*/////////////////////////////*/
	public String rehabilitacionUnica()
	{
		log.debug(""
				+ "\n##########################################"
				+ "\n##########################################"
				+ "\n###### rehabilitacion unica proceso ######"
				+ "\n######                              ######"
				);
		log.debug("smap1: "+smap1);
		success=true;
		log.debug(""
				+ "\n######                              ######"
				+ "\n###### rehabilitacion unica proceso ######"
				+ "\n##########################################"
				+ "\n##########################################"
				);
		return SUCCESS;
	}
	/*/////////////////////////////*/
	////// rehabilitacionUnica //////
	/////////////////////////////////
	
	
	/////////////////////////////////
	////// getters and setters //////
	/*/////////////////////////////*/
	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setRehabilitacionManager(RehabilitacionManager rehabilitacionManager) {
		this.rehabilitacionManager = rehabilitacionManager;
	}

}