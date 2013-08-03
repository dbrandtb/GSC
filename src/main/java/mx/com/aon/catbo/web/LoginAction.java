package mx.com.aon.catbo.web;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.service.LoginManager;
import mx.com.aon.catbo.model.UsuarioVO;
import mx.com.aon.portal.model.UserVO;

import java.util.Map;

public class LoginAction extends AbstractListAction implements SessionAware {

	private static Logger logger = Logger.getLogger(LoginAction.class);

	private transient LoginManager loginManager;
	
	private boolean success;

	private String user;
	private String pwd;
    private String cdElemento;
    private Map session;

    public String cmdLogin () throws ApplicationException {
    		logger.debug("Leyendo el usuario que previamente fue seteado en el filter");
            UserVO userVO = (UserVO) session.get("USUARIO");
            if (userVO != null) {
                logger.debug("Usuario antes del cambio: "+ userVO.getUser());
                userVO.setUser(user);
                userVO.setCdElemento(cdElemento);
                if (userVO.getEmpresa()!= null) {
                    userVO.getEmpresa().setElementoId(cdElemento);
                }
                session.put("contextUserVO", userVO);
            } else {
                logger.debug("No se encontro userVO en la session");            	
            }

            success = true;
		    return "loginAccepted";

	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}


    public void setSession(Map map) {
         this.session = map;
    }


    public String getCdElemento() {
        return cdElemento;
    }

    public void setCdElemento(String cdElemento) {
        this.cdElemento = cdElemento;
    }
}
