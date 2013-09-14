package mx.com.gseguros.portal.cotizacion.controller;

import org.apache.log4j.Logger;

import mx.com.aon.core.web.PrincipalCoreAction;

public class ScreenInterceptor {

	public static final int PANTALLA_COMPLEMENTARIOS_GENERAL              = 0;
    public static final int PANTALLA_COMPLEMENTARIOS_ASEGURADOS           = 1;
    public static final int PANTALLA_COMPLEMENTARIOS_COBERTURAS_ASEGURADO = 2;
    public static final int PANTALLA_COMPLEMENTARIOS_DOMICILIO_ASEGURADO  = 3;
    private Logger log=Logger.getLogger(ScreenInterceptor.class);
	
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
                            ||a.getEstado().isEmpty()
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
		
		/**
		 * pantalla de coberturas de asegurado
                 * @requiere:
		 * @return:
                    mostrarPantallaAsegurados();
		 */
                else if(screen==PANTALLA_COMPLEMENTARIOS_COBERTURAS_ASEGURADO)
		{
			ComplementariosCoberturasAction a=(ComplementariosCoberturasAction)action;
			if(a.getSession()==null
                            ||a.getSession().get("USUARIO")==null
                            ||a.getSmap1()==null
                    		||a.getSmap1().isEmpty()
                    		||!a.getSmap1().containsKey("pv_cdunieco")
                            ||!a.getSmap1().containsKey("pv_cdramo")
                            ||!a.getSmap1().containsKey("pv_estado")
                            ||!a.getSmap1().containsKey("pv_nmpoliza")
                            ||!a.getSmap1().containsKey("pv_nmsituac")
                            ||!a.getSmap1().containsKey("pv_cdperson")
                            )
			{
				return "denied";
			}
			else
			{
				return a.mostrarPantallaCoberturas();
			}
		}
		
		/**
		pantalla de domicilio
        requiere:  
           smap1.pv_cdunieco
           smap1.pv_cdramo
           smap1.pv_estado
           smap1.pv_nmpoliza
           smap1.pv_nmsituac
           smap1.pv_cdperson          
		return:
        mostrarPantallaDomicilio();
		*/
        else if(screen==PANTALLA_COMPLEMENTARIOS_DOMICILIO_ASEGURADO)
		{
			ComplementariosCoberturasAction a=(ComplementariosCoberturasAction)action;
			if(a.getSession()==null
                            ||a.getSession().get("USUARIO")==null
                            ||a.getSmap1()==null
                    		||a.getSmap1().isEmpty()
                    		||!a.getSmap1().containsKey("pv_cdunieco")
                            ||!a.getSmap1().containsKey("pv_cdramo")
                            ||!a.getSmap1().containsKey("pv_estado")
                            ||!a.getSmap1().containsKey("pv_nmpoliza")
                            ||!a.getSmap1().containsKey("pv_nmsituac")
                            ||!a.getSmap1().containsKey("pv_cdperson")
                            )
			{
				return "denied";
			}
			else
			{
				return a.mostrarPantallaDomicilio();
			}
		}
		
		return "denied";
	}
	
}
