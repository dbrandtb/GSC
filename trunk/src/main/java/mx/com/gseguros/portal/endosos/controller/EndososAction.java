package mx.com.gseguros.portal.endosos.controller;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.gseguros.portal.cotizacion.controller.ComplementariosCoberturasAction;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.endosos.service.EndososManager;
import mx.com.gseguros.portal.general.util.ConstantesCatalogos;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class EndososAction extends PrincipalCoreAction implements ConstantesCatalogos
{
	private static final long        serialVersionUID = 84257834070419933L;
	private static Logger            log              = Logger.getLogger(EndososAction.class);
	private List<Map<String,String>> slist1;
	private Map<String,String>       smap1;
	private boolean                  success          = false;
	private EndososManager           endososManager;
	private KernelManagerSustituto   kernelManager;
	private Item                     item1;
	private Item                     item2;

	//////////////////////////////
	////// marco de endosos //////
	/*//////////////////////////*/
	public String marco()
	{
		log.debug(""
				+ "\n##############################"
				+ "\n##############################"
				+ "\n###### marco de endosos ######"
				+ "\n######                  ######"
				);
		success=true;
		log.debug(""
				+ "\n######                  ######"
				+ "\n###### marco de endosos ######"
				+ "\n##############################"
				+ "\n##############################"
				);
		return SUCCESS;
	}
	/*//////////////////////////*/
	////// marco de endosos //////
	//////////////////////////////
	
	/////////////////////////////
	////// obtener endosos //////
	/*/////////////////////////*/
	public String obtenerEndosos()
	{
		log.debug(""
				+ "\n############################"
				+ "\n############################"
				+ "\n###### obtenerEndosos ######"
				+ "\n######                ######"
				);
		log.debug("smap1: "+smap1);
		try
		{
			slist1=endososManager.obtenerEndosos(smap1);
			success=true;
		}
		catch(Exception ex)
		{
			log.error("error al obtener los endosos",ex);
			success=false;
		}
		log.debug(""
				+ "\n######                ######"
				+ "\n###### obtenerEndosos ######"
				+ "\n############################"
				+ "\n############################"
				);
		return SUCCESS;
	}
	/*/////////////////////////*/
	////// obtener endosos //////
	/////////////////////////////
	
	//////////////////////////////////////////////
	////// pantalla de endoso de coberturas //////
	/*//////////////////////////////////////////*/
	public String pantallaEndosoCoberturas()
	{
		log.debug(""
				+ "\n######################################"
				+ "\n######################################"
				+ "\n###### pantallaEndosoCoberturas ######"
				+ "\n######                          ######"
				);
		log.debug("smap1: "+smap1);
		log.debug(""
				+ "\n######                          ######"
				+ "\n###### pantallaEndosoCoberturas ######"
				+ "\n######################################"
				+ "\n######################################"
				);
		return SUCCESS;
	}
	/*//////////////////////////////////////////*/
	////// pantalla de endoso de coberturas //////
	//////////////////////////////////////////////

	//////////////////////////////////////////////
	////// pantalla de endoso de domicilio  //////
	/*//////////////////////////////////////////*/
	public String pantallaEndosoDomicilio()
	{
		log.debug(""
				+ "\n#####################################"
				+ "\n#####################################"
				+ "\n###### pantallaEndosoDomicilio ######"
				+ "\n######                         ######"
				);
		log.debug("smap1: "+smap1);
		log.debug("session: "+session);
		ComplementariosCoberturasAction actionDomicilio=new ComplementariosCoberturasAction();
		actionDomicilio.setSession(session);
		actionDomicilio.setSmap1(smap1);
		actionDomicilio.setKernelManager(kernelManager);
		actionDomicilio.mostrarPantallaDomicilio();
		item1=actionDomicilio.getItem1();
		item2=actionDomicilio.getItem2();
		log.debug(""
				+ "\n######                         ######"
				+ "\n###### pantallaEndosoDomicilio ######"
				+ "\n#####################################"
				+ "\n#####################################"
				);
		return SUCCESS;
	}
	/*//////////////////////////////////////////*/
	////// pantalla de endoso de domicilio  //////
	//////////////////////////////////////////////
	
	///////////////////////////////
	////// getters y setters //////
	/*///////////////////////////*/
	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
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

	public void setEndososManager(EndososManager endososManager) {
		this.endososManager = endososManager;
	}
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
	@Override
	public String getCON_CAT_CANCELA_MOTIVOS() {
		// TODO Auto-generated method stub
		return CON_CAT_CANCELA_MOTIVOS;
	}

	public Item getItem1() {
		return item1;
	}

	public void setItem1(Item item1) {
		this.item1 = item1;
	}

	public Item getItem2() {
		return item2;
	}

	public void setItem2(Item item2) {
		this.item2 = item2;
	}

	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}
	
}