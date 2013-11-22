package mx.com.gseguros.portal.cancelacion.controller;

import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.portal.general.util.ConstantesCatalogos;

public class CancelacionAction extends PrincipalCoreAction implements ConstantesCatalogos
{
	
	private static final long serialVersionUID = 3337342608259982346L;
	private static org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(CancelacionAction.class);
	
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
		
}