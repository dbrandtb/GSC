package mx.com.aon.kernel.listener;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import mx.com.ice.kernel.core.Global;
import mx.com.ice.services.business.ServiciosGeneralesSistema;

import org.apache.log4j.Logger;

/**
 * <PRE>
 *   Empresa: Royal&SunAlliance
 *            Fecha de creación: 07/12/2004
 *            Fecha de última actualización:  08/02/2008
 *   @author: Jose R Marcano
 *
 *   reference. e-Alea Application
 * </PRE>
 */
public class ListenerSession extends HttpServlet implements HttpSessionListener{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3549217857979700030L;


	static Logger logger = Logger.getLogger(ListenerSession.class);

	
	
	
    /**
     * Este método se ejecuta cuando se destruye una sesión, ya sea por Timeout o por
     * invalidar la sesión manualmente.
     *
     * @param event HttpSessionEvent evento de sesión
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        logger.info(Global.SEPARADOR_PANTALLA+"INICIO :::::  Finalizando Session con Id [" + session.getId() + "]  ::::: ");
        
        logger.info(Global.SEPARADOR+"MEMORIA TOTAL="+Runtime.getRuntime().totalMemory());
                     
        
        //Eliminando sesion WEB-Negocio
        //ServiciosGeneralesSistema.salirSesionNegocio(event.getSession().getId());       
        //Remover de la sesion  banderas de status de Pantallas        
        
        ServiciosGeneralesSistema serviciosGeneralesSistema = new ServiciosGeneralesSistema();
        serviciosGeneralesSistema.eliminarEntidad(event.getSession().getId());
        
        //ManejoSesionesWEBNegocio.imprimirRegistroSesiones();
        //invokeGC(session);        
        serviciosGeneralesSistema.limpiarExpresiones(event.getSession().getId());

        serviciosGeneralesSistema.eliminarBloques(event.getSession().getId());

        logger.debug("antes de eliminar sesion");
        serviciosGeneralesSistema.eliminarSesion(event.getSession().getId());
        logger.debug("despues de eliminar sesion");
        															//userDB, String cdramo
        //CacheLog.cerrarLogExpresiones(event.getSession().getId(), variables[0].getValor(), variables[1].getValor());


        if (logger.isInfoEnabled())
            logger.info(Global.SEPARADOR_PROCESO_TAB + "FIN ::: ELIMINAR DATA DE SESION DE NEGOCIO DE USUARIO " + event.getSession().getId()
                    + Global.SEPARADOR_PROCESO);

        
        
        Runtime runtime = Runtime.getRuntime();
        runtime.runFinalization();
        runtime.gc();    
       
        logger.info(Global.SEPARADOR_TAB+"MEMORIA TOTAL="+Runtime.getRuntime().totalMemory()+Global.SEPARADOR);
         
        logger.info(Global.SEPARADOR_PANTALLA+"FIN :::::  Finalizando Session con Id [" + session.getId() + "]  ::::: ");
     
        
     }

    /**
     * Este método se ejecuta cuando se crea una sesión dentro de la aplicación.
     * No tiene funcionalidad para la aplicación
     * @param event HttpSessionEvent evento de sesión
         */
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        logger.debug(" :::::  Iniciando Session con Id [" + session.getId() + "]  ::::: ");
    }

    /*private void invokeGC(HttpSession session) {
        invokeGC iGC = new invokeGC();
        iGC.execGC(session, true, true);
    }*/

}
