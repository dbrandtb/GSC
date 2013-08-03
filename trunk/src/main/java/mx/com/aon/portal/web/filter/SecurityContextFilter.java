package mx.com.aon.portal.web.filter;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import mx.com.aon.portal.service.LoginManager;
import mx.com.aon.portal.service.ConsultaActividadUsuarioManager;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.ConnectionCallInterceptor;
import mx.com.aon.core.ApplicationException;

import java.io.IOException;

public class SecurityContextFilter implements Filter {

    @SuppressWarnings("unused")
    private Logger logger = Logger.getLogger(SecurityContextFilter.class);
    private LoginManager loginManager;

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)request).getSession();
        logger.debug("request: "+((HttpServletRequest)request).getRequestURI());
        logger.debug("Obteniendo el usuario de la session");
        UserVO userVO = (UserVO)session.getAttribute("USUARIO");
        if (userVO != null) {

            doCheckSecurity(userVO,request);

        } else {
            logger.error("Usuario logueado es Nulo.");
            throw new ServletException("No se encontro Remote User(HttpServletRequest)request).getRemoteUser() ");
        }

        chain.doFilter(request, response);
    }


    public void destroy() {
    }

    public void doCheckSecurity(UserVO userVO, ServletRequest request) throws IOException, ServletException {
            String requestUri = ((HttpServletRequest)request).getRequestURI();
            if (requestUri.indexOf(".action")>0) {
                if (requestUri.indexOf("export")>0) {
                    logger.debug("action de exportar : "+ requestUri);
                    if (userVO.isAuthorizedExport()) {
                        logger.debug("el usuario "+ userVO.getUser() + "esta autorizado a ejecutar la accion de exportar : "+ requestUri);
                    }
                    else {
                        logger.debug("el usuario "+ userVO.getUser() + " NO esta autorizado a ejecutar la accion de exportar : "+ requestUri);
                        throw new ServletException("el usuario "+ userVO.getUser() + " NO esta autorizado a ejecutar la accion de exportar : "+ requestUri);
                    }
                }

            }
    }

}
