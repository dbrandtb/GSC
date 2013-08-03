package mx.com.aon.catbo.service.impl;

import java.util.HashMap;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.RolesVO;
import mx.com.aon.catbo.model.UsuarioVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.UsuariosManager;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;

public class UsuariosManagerImpl extends AbstractManager implements UsuariosManager {


	public UsuarioVO getDatos(String user) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("user", user);

		return (UsuarioVO)getBackBoneInvoke(map, "USUARIOS_OBTENER_USUARIO");
	}

	@SuppressWarnings("unchecked")
	public PagedList getRoles(String user, int start, int limit) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("user", user);

		return pagedBackBoneInvoke(map, "USUARIOS_OBTENER_ROLES_USUARIO", start, limit);
	}

	@SuppressWarnings("unchecked")
	public String isUserInGroup(String user, String grupo) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("user", user);
		map.put("grupo", grupo);
		WrapperResultados res = (WrapperResultados)returnBackBoneInvoke(map, "USUARIOS_USUARIO_EN_GRUPO");

		return res.getResultado();
	}

}
