package mx.com.gseguros.portal.cancelacion.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.portal.cancelacion.service.CancelacionManager;
import mx.com.gseguros.portal.general.util.ConstantesCatalogos;

public class CancelacionAction extends PrincipalCoreAction implements ConstantesCatalogos
{
	
	private static final long serialVersionUID = 3337342608259982346L;
	private static org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(CancelacionAction.class);
	private Map<String,String>smap1;
	private boolean success=false;
	private CancelacionManager cancelacionManager;
	private List<Map<String,String>>slist1;
	
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
			slist1=cancelacionManager.buscarPolizas(smap1);
		}
		catch(Exception ex)
		{
			log.error("error al obtener polizas",ex);
			slist1=null;
			success=false;
		}
		success=true;
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
	
	/////////////////////////////////
	////// getters and setters //////
	/*/////////////////////////////*/
	@Override
	public String getCON_CAT_POL_ESTADO() {
		// TODO Auto-generated method stub
		return CON_CAT_POL_ESTADO;
	}

	@Override
	public String getCON_CAT_POL_TIPO_POLIZA() {
		// TODO Auto-generated method stub
		return CON_CAT_POL_TIPO_POLIZA;
	}

	@Override
	public String getCON_CAT_POL_TIPO_PAGO() {
		// TODO Auto-generated method stub
		return CON_CAT_POL_TIPO_PAGO;
	}

	@Override
	public String getCON_CAT_POL_ROL() {
		// TODO Auto-generated method stub
		return CON_CAT_POL_ROL;
	}

	@Override
	public String getCON_CAT_MESACONTROL_SUCUR_ADMIN() {
		// TODO Auto-generated method stub
		return CON_CAT_MESACONTROL_SUCUR_ADMIN;
	}

	@Override
	public String getCON_CAT_MESACONTROL_SUCUR_DOCU() {
		// TODO Auto-generated method stub
		return CON_CAT_MESACONTROL_SUCUR_DOCU;
	}

	@Override
	public String getCON_CAT_MESACONTROL_TIP_TRAMI() {
		// TODO Auto-generated method stub
		return CON_CAT_MESACONTROL_TIP_TRAMI;
	}

	@Override
	public String getCON_CAT_MESACONTROL_ESTAT_TRAMI() {
		// TODO Auto-generated method stub
		return CON_CAT_MESACONTROL_ESTAT_TRAMI;
	}

	@Override
	public String getCON_CAT_TPERSONA() {
		// TODO Auto-generated method stub
		return CON_CAT_TPERSONA;
	}

	@Override
	public String getCON_CAT_NACIONALIDAD() {
		// TODO Auto-generated method stub
		return CON_CAT_NACIONALIDAD;
	}

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

	public String getCON_CAT_CANCELA_MOTIVOS() {
		return CON_CAT_CANCELA_MOTIVOS;
	}
		
}