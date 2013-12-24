package mx.com.gseguros.portal.general.service;

import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.RamaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal2.web.GenericVO;

public interface UsuarioManager {
	
	public GenericVO guardaUsuario(Map<String, String> params) throws Exception;

    public List<RamaVO> getClientesRoles(String user)throws Exception;

    public List<UserVO> getAttributesUser(String user) throws Exception;
}
