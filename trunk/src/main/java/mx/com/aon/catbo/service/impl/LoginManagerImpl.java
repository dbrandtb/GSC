package mx.com.aon.catbo.service.impl;

import java.util.HashMap;


import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.UsuarioVO;
import mx.com.aon.catbo.service.LoginManager;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;

import com.opensymphony.xwork2.ActionSupport;

public class LoginManagerImpl extends AbstractManager implements LoginManager {

	@SuppressWarnings("unchecked")
	public UsuarioVO login(String user, String pwd) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("user", user);
		map.put("pwd", pwd);

        UsuarioVO usuarioVO = new UsuarioVO();
        usuarioVO.setNombre("Juan");
        usuarioVO.setIdUsuario(user);
        return usuarioVO;
        //return(UsuarioVO)getBackBoneInvoke(map, "LOGIN");
	}

}
