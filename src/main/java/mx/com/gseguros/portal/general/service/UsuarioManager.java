package mx.com.gseguros.portal.general.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.RamaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.general.model.RolVO;
import mx.com.gseguros.portal.general.model.UsuarioVO;

public interface UsuarioManager {
	
	public GenericVO guardaUsuario(Map<String, String> params) throws Exception;

	public List<UsuarioVO> obtieneUsuarios(Map<String, String> params) throws Exception;
	
	public List<Map<String, String>> obtieneRolesUsuario(Map<String, String> params) throws ApplicationException;
	
	public boolean guardaRolesUsuario(Map<String, String> params, List<Map<String, String>> saveList) throws ApplicationException;

    public List<RamaVO> getClientesRoles(String user)throws Exception;

    public List<UserVO> getAttributesUser(String user) throws Exception;
}
