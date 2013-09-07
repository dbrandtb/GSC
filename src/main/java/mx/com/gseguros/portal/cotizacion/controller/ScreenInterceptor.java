package mx.com.gseguros.portal.cotizacion.controller;

import mx.com.aon.core.web.PrincipalCoreAction;

public class ScreenInterceptor {

	public static final int PANTALLA_COMPLEMENTARIOS_GENERAL=0;
	
	public String intercept(PrincipalCoreAction action, int screen)
	{
		/**
		 * pantalla de datos complemetarios generales
		 * return mostrarPantallaGeneral();
		 */
		if(screen==PANTALLA_COMPLEMENTARIOS_GENERAL)
		{
			ComplementariosAction a=(ComplementariosAction)action;
			if(a.getSession()==null
					||a.getSession().get("USUARIO")==null
					||a.getNmpoliza()==null
					||a.getNmpoliza().isEmpty())
			{
				return "denied";
			}
			else
			{
				return a.mostrarPantallaGeneral();
			}
		}
		return "denied";
	}
	
}
