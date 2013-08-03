package mx.com.aon.catbo.service.impl;

import java.util.HashMap;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.UsuarioVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.RolesManager;
import mx.com.aon.portal.service.impl.AbstractManager;

public class RolesManagerImpl extends AbstractManager implements RolesManager {

	public PagedList getUsersFromGroup(String grupo, int start, int limit)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("grupo", grupo);

		return pagedBackBoneInvoke(map, "ROLES_OBTENER_USUARIOS_POR_ROL", start, limit);
	}

}
