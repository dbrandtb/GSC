package mx.com.gseguros.portal.cancelacion.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.portal.cancelacion.service.CancelacionManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;

import org.apache.struts2.ServletActionContext;

public class CancelacionAction extends PrincipalCoreAction
{
	private static final long              serialVersionUID   = 3337342608259982346L;
	private static org.apache.log4j.Logger log                = org.apache.log4j.Logger.getLogger(CancelacionAction.class);
	private boolean                        success            = false;
	
	private CancelacionManager       cancelacionManager;
	private PantallasManager         pantallasManager;
	private Map<String,String>       smap1;
	private List<Map<String,String>> slist1;
	private Map<String,Item>         imap;
	
	//////////////////////////////////
	////// marco de cancelacion //////
	/*//////////////////////////////*/
	public String marcoCancelacion()
	{
		log.debug(""
				+ "\n##############################"
				+ "\n##############################"
				+ "\n###### marcoCancelacion ######"
				+ "\n######                  ######"
				);
		try
		{
			imap=new HashMap<String,Item>(0);
			
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			gc.generaParcial(pantallasManager.obtenerCamposPantalla(
					 null,null,null
					,null,null,null
					,"MARCOCANCELACION",null,null
					,"FILTRO"));
			
			imap.put("itemsFiltro",gc.getItems());
			
			gc.generaParcial(pantallasManager.obtenerCamposPantalla(
					 null,null,null
					,null,null,null
					,"MARCOCANCELACION",null,null
					,"MODELOCANDIDATA"));
			
			imap.put("fieldsCandidata",gc.getFields());
			imap.put("columnsCandidata",gc.getColumns());
			
		}
		catch(Exception ex)
		{
			log.error("error al generar el marco de cancelacion");
		}
		log.debug(""
				+ "\n######                  ######"
				+ "\n###### marcoCancelacion ######"
				+ "\n##############################"
				+ "\n##############################"
				);
		return SUCCESS;
	}
	/*//////////////////////////////*/
	////// marco de cancelacion //////
	//////////////////////////////////
	
	/////////////////////////////////////////////
	////// manda a la pantalla de cancelar //////
	/*/////////////////////////////////////////*/
	public String pantallaCancelar()
	{
		log.debug(""
				+ "\n##############################"
				+ "\n##############################"
				+ "\n###### pantallaCancelar ######"
				+ "\n######                  ######"
				);
		log.debug(""
				+ "\n######                  ######"
				+ "\n###### pantallaCancelar ######"
				+ "\n##############################"
				+ "\n##############################"
				);
		return SUCCESS;
	}
	/*/////////////////////////////////////////*/
	////// manda a la pantalla de cancelar //////
	/////////////////////////////////////////////

	////////////////////////////
	////// buscar polizas //////
	/*////////////////////////*/
	public String buscarPolizas()
	{
		log.debug(""
				+ "\n###########################"
				+ "\n###########################"
				+ "\n###### buscarPolizas ######"
				+ "\n######               ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			smap1.put("pv_cdunieco_i",smap1.get("pv_dsuniage_i"));
			cancelacionManager.seleccionaPolizas(smap1);
			slist1=cancelacionManager.obtenerPolizasCandidatas(smap1);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al obtener polizas",ex);
			slist1=null;
			success=false;
		}
		log.debug(""
				+ "\n######               ######"
				+ "\n###### buscarPolizas ######"
				+ "\n###########################"
				+ "\n###########################"
				);
		return SUCCESS;
	}
	/*////////////////////////*/
	////// buscar polizas //////
	////////////////////////////
	
	/////////////////////////////////////////
	////// cancelacion unica de poliza //////
	/*/////////////////////////////////////*/
	public String cancelacionUnica()
	{
		log.debug(""
				+ "\n##############################"
				+ "\n##############################"
				+ "\n###### cancelacionUnica ######"
				+ "\n######                  ######"
				);
		log.debug("smap1: "+smap1);
		success=true;
		log.debug(""
				+ "\n######                  ######"
				+ "\n###### cancelacionUnica ######"
				+ "\n##############################"
				+ "\n##############################"
				);
		return SUCCESS;
	}
	/*/////////////////////////////////////*/
	////// cancelacion unica de poliza //////
	/////////////////////////////////////////
	
	////////////////////////////////////////////////
	////// pantalla de cancelacion automatica //////
	/*////////////////////////////////////////////*/
	public String pantallaCancelarAuto()
	{
		log.debug(""
				+ "\n##################################"
				+ "\n##################################"
				+ "\n###### pantallaCancelarAuto ######"
				+ "\n######                      ######"
				);
		log.debug("slist1 :"+slist1);
		log.debug(""
				+ "\n######                      ######"
				+ "\n###### pantallaCancelarAuto ######"
				+ "\n##################################"
				+ "\n##################################"
				);
		return SUCCESS;
	}
	/*////////////////////////////////////////////*/
	////// pantalla de cancelacion automatica //////
	////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////
	////// proceso de cancelacion automatica manual //////
	/*//////////////////////////////////////////////////*/
	public String cancelacionAutoManual()
	{
		log.debug(""
				+ "\n###################################"
				+ "\n###################################"
				+ "\n###### cancelacionAutoManual ######"
				+ "\n######                       ######"
				);
		log.debug("slist1: "+slist1);
		success=true;
		log.debug(""
				+ "\n######                       ######"
				+ "\n###### cancelacionAutoManual ######"
				+ "\n###################################"
				+ "\n###################################"
				);
		return SUCCESS;
	}
	////// proceso de cancelacion automatica manual //////
	/*//////////////////////////////////////////////////*/
	//////////////////////////////////////////////////////
	
	/////////////////////////////////////////////
	////// obtener detalles de cancelacion //////
	/*/////////////////////////////////////////*/
	public String obtenerDetalleCancelacion()
	{
		log.debug(""
				+ "\n#######################################"
				+ "\n#######################################"
				+ "\n###### obtenerDetalleCancelacion ######"
				+ "\n######                           ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			smap1.putAll(cancelacionManager.obtenerDetalleCancelacion(smap1)); 
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al obtener detalle de una cancelacion",ex);
			success=false;
		}
		log.debug(""
				+ "\n######                           ######"
				+ "\n###### obtenerDetalleCancelacion ######"
				+ "\n#######################################"
				+ "\n#######################################"
				);
		return SUCCESS;
	}
	/*/////////////////////////////////////////*/
	////// obtener detalles de cancelacion //////
	/////////////////////////////////////////////
	
	
	/////////////////////////////////
	////// getters and setters //////
	/*/////////////////////////////*/
	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setCancelacionManager(CancelacionManager cancelacionManager) {
		this.cancelacionManager = cancelacionManager;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
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