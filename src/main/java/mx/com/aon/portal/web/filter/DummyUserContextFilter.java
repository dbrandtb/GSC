package mx.com.aon.portal.web.filter;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import mx.com.aon.portal.service.LoginManager;
import mx.com.aon.portal.service.ConsultaActividadUsuarioManager;
import mx.com.aon.portal.service.NavigationManager;
import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.ConnectionCallInterceptor;
import mx.com.aon.core.ApplicationException;

import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;

public class DummyUserContextFilter implements Filter {

    @SuppressWarnings("unused")
    private Logger logger = Logger.getLogger(DummyUserContextFilter.class);
    private LoginManager loginManager;

    private NavigationManager navigationManager;
    public static String DEFAULT_DATE_FORMAT_PARAM = "defaultDateFormat";
    public static String DEFAULT_DECIMAL_SEPARATOR_PARAM = "defaultDecimalSeparator";
    public static String DEFAULT_DECIMAL_PRECISION_PARAM = "decimalPrecision";
    private String dateFormat;
    private String decimalSeparator;
    private ServletContext servletContext;
    private FilterConfig filterConfig;

    public void init(FilterConfig config) throws ServletException {
        dateFormat = config.getServletContext().getInitParameter(DEFAULT_DATE_FORMAT_PARAM);
        decimalSeparator = config.getServletContext().getInitParameter(DEFAULT_DECIMAL_SEPARATOR_PARAM);
        this.servletContext = config.getServletContext();
        filterConfig = config;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                    HttpSession session = ((HttpServletRequest)request).getSession();
                    logger.debug("request: "+((HttpServletRequest)request).getRequestURI());
                    logger.debug("Obteniendo el usuario de la session");
                    logger.debug("Estado de sesi�n: " + session);
    	        	UserVO userVO = (UserVO)session.getAttribute("USUARIO");
    	        	try {
    		        if (userVO == null) {
    		        	 
    		            //Prueba con un usuario dummy
    		            String user = "PARAMETRIZA1";
    		
    		            logger.debug("Obteniendo LoginManager en el servletContext");
    		            ServletContext servletContext  = session.getServletContext();
    		            WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    		            loginManager = (LoginManager) context.getBean("loginManager");
    		            navigationManager = (NavigationManager) context.getBean("navigationManager");
    		            try {
    		                userVO = loginManager.obtenerDatosUsuario(user);
    		                userVO.setDecimalSeparator(decimalSeparator);
    		                
    		                IsoVO isoVO = navigationManager.getVariablesIso(userVO.getUser());

    		                userVO.setClientFormatDate(isoVO.getClientDateFormat());
    		                userVO.setFormatDate(dateFormat);
    		                userVO.setDecimalSeparator(isoVO.getFormatoNumerico());
    		                
    		            } catch (ApplicationException ex) {
    		                logger.error("Fallo al ejecutar el obtenerDatosUsuario.",ex);
    		                throw new ServletException("Error al invocar el filtro contexto de Usuario",ex);
    		            }
    		            		/**
    		             * Esta session debe ser borrada.
    		             */
    		            session.setAttribute("userVO", userVO);
    		            /**/
    		
    		            session.setAttribute("USUARIO", userVO);
    		            session.setAttribute("contextUserVO", userVO);
    		
    		            /*Esta session debe ser borrada*/
    		            session.setAttribute("CONTENIDO_USER", userVO.getName());
    		
    		        }
    		        logger.debug("seteando el userVO en el ThreadLocal");
    		        ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();
    		        tl.set(userVO);
    		        /**TODO: PARA quitar la auditoria de quien puede ejecutar servicios BD*/
    		        //doAudit(userVO,request);
    		        chain.doFilter(request, response);
    	        	} catch (Exception e) {
    	        		chain.doFilter(request, response);
    	        	}
    }
    public void destroy() {
    }
    

    public void doAudit(UserVO userVO, ServletRequest request) throws IOException, ServletException {
        try {
            HttpSession session = ((HttpServletRequest)request).getSession();

                logger.debug("Obteniendo consultaActividadUsuarioManager en el servletContext");
                ServletContext servletContext  = session.getServletContext();
                WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                ConsultaActividadUsuarioManager consultaActividadUsuarioManager = (ConsultaActividadUsuarioManager) context.getBean("consultaActividadUsuarioManager");

                String requestUri = ((HttpServletRequest)request).getRequestURI();
                if (requestUri.indexOf(".action")>0) {

                    String method = ((HttpServletRequest)request).getMethod();

                    logger.debug("Insertando el url invocado por el usuario");

                    String reqid = userVO.getUser() + System.currentTimeMillis();
                    logger.debug("reqId : "+ reqid);
                    logger.debug("Usuario "+ userVO.getUser());
                    logger.debug("url invocado "+ requestUri);
                    logger.debug("method "+ method);

                    String rolActivo = (userVO.getRolActivo() != null)?userVO.getRolActivo().getObjeto().getValue():null;
                    consultaActividadUsuarioManager.insertarActividadesUsuario(reqid,requestUri,method,userVO.getUser(), rolActivo);
                }            	
        } catch (ApplicationException ex) {
            logger.error("Fallo al ejecutar el obtenerDatosUsuario.",ex);
            throw new ServletException("Error al invocar el filtro contexto de Usuario",ex);
        }

    }


}
