package mx.com.aon.catbo.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.UsuarioVO;
import mx.com.aon.portal.service.PagedList;

public interface RolesManager {
	public PagedList getUsersFromGroup (String grupo, int start, int limit) throws ApplicationException;
}
