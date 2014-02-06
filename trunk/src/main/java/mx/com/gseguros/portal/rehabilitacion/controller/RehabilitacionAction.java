package mx.com.gseguros.portal.rehabilitacion.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.rehabilitacion.service.RehabilitacionManager;

public class RehabilitacionAction extends PrincipalCoreAction
{

	private static final long              serialVersionUID      = 5306848466443116067L;
	private static org.apache.log4j.Logger log                   = org.apache.log4j.Logger.getLogger(RehabilitacionAction.class);
	private Map<String,String>             smap1;
	private List<Map<String,String>>       slist1;
	private boolean                        success               = false;
	private RehabilitacionManager          rehabilitacionManager;
	private Map<String,Item>               imap;
	private PantallasManager               pantallasManager;
	
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
		try
		{

			imap=new HashMap<String,Item>(0);
			
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			gc.generaParcial(pantallasManager.obtenerComponentes(
					null, null, null,
					null, null, null,
					"MARCOREHABILITACION", "FILTRO", null));
			
			imap.put("itemsFiltro" , gc.getItems());
			
			gc.generaParcial(pantallasManager.obtenerComponentes(
					null, null, null,
					null, null, null,
					"MARCOREHABILITACION", "MODELO", null));
			
			imap.put("fieldsModelo" , gc.getFields());
			imap.put("columnsGrid"  , gc.getColumns());
			
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al cargar el marco de endosos",ex);
			success=false;
		}
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
		try
		{
			imap=new HashMap<String,Item>(0);
			
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			gc.generaParcial(pantallasManager.obtenerComponentes(
					null, null, null,
					null, null, null,
					"PANTALLAREHABILITARUNICA", "FORM", null));
			
			imap.put("itemsForm" , gc.getItems());
		}
		catch(Exception ex)
		{
			log.error("error al mostrar pantalla de rehabilitacion unica",ex);
		}
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
		try
		{
			rehabilitacionManager.rehabilitarPoliza(smap1);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al rehabilitar poliza",ex);
			success=false;
		}
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

	public Map<String, Item> getImap() {
		return imap;
	}

	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
	}

	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}

}