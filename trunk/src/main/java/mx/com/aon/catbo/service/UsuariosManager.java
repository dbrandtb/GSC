package mx.com.aon.catbo.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.UsuarioVO;
import mx.com.aon.portal.service.PagedList;

public interface UsuariosManager {
	public UsuarioVO getDatos (String user) throws ApplicationException;
	public PagedList getRoles (String user, int start, int limit) throws ApplicationException;
	public String isUserInGroup (String user, String grupo) throws ApplicationException;
}
