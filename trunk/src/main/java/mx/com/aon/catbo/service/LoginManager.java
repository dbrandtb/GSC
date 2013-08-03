package mx.com.aon.catbo.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.UsuarioVO;


public interface LoginManager {

	public UsuarioVO login (String user, String pwd) throws ApplicationException;

}
