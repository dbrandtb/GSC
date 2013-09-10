package mx.com.gseguros.portal.cotizacion.controller;

import mx.com.aon.core.web.PrincipalCoreAction;

public class ScreenInterceptor {

	public static final int PANTALLA_COMPLEMENTARIOS_GENERAL=			0;
        public static final int PANTALLA_COMPLEMENTARIOS_ASEGURADOS=	1;
	
	public String intercept(PrincipalCoreAction action, int screen)
	{
		/**
		 * pantalla de datos complemetarios generales
                 * @requiere:
                    cdunieco
                    cdramo
                    estado
                    nmpoliza
		 * @return:
                    mostrarPantallaGeneral();
		 */
		if(screen==PANTALLA_COMPLEMENTARIOS_GENERAL)
		{
			ComplementariosAction a=(ComplementariosAction)action;
			if(a.getSession()==null
                            ||a.getSession().get("USUARIO")==null
                            ||a.getCdunieco()==null
                            ||a.getCdunieco().isEmpty()
                            ||a.getCdramo()==null
                            ||a.getCdramo().isEmpty()
                            ||a.getEstado()==null
                            ||a.getNmpoliza().isEmpty()
                            ||a.getNmpoliza()==null
                            ||a.getNmpoliza().isEmpty()
                            )
			{
				return "denied";
			}
			else
			{
				return a.mostrarPantallaGeneral();
			}
		}
                
                /**
		 * pantalla de datos complemetarios de asegurados
                 * @requiere:
                    cdunieco
                    cdramo
                    estado
                    nmpoliza
		 * @return:
                    mostrarPantallaAsegurados();
		 */
                else if(screen==PANTALLA_COMPLEMENTARIOS_ASEGURADOS)
		{
			ComplementariosAction a=(ComplementariosAction)action;
			if(a.getSession()==null
                            ||a.getSession().get("USUARIO")==null
                            ||a.getMap1()==null
                    		||a.getMap1().isEmpty()
                    		||!a.getMap1().containsKey("cdunieco")
                            ||!a.getMap1().containsKey("cdramo")
                            ||!a.getMap1().containsKey("estado")
                            ||!a.getMap1().containsKey("nmpoliza")
                            )
			{
				return "denied";
			}
			else
			{
				return a.mostrarPantallaAsegurados();
			}
		}
		return "denied";
	}
	
}
