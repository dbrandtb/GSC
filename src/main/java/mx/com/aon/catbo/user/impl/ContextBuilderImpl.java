package mx.com.aon.catbo.user.impl;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.RolesVO;
import mx.com.aon.catbo.model.UsuarioVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.RolesManager;
import mx.com.aon.catbo.service.UsuariosManager;
import mx.com.aon.catbo.user.ContextBuilder;

public class ContextBuilderImpl implements ContextBuilder {

	private transient UsuariosManager usuariosManager;
	
	private transient RolesManager rolesManager;


	@SuppressWarnings("unchecked")
	public UsuarioVO buildContext(String userId, int start, int limit) throws ApplicationException {
		try {
			List<RolesVO> listaRoles;
			UsuarioVO usuarioVO = usuariosManager.getDatos(userId);
			PagedList pagedList = usuariosManager.getRoles(userId, start, limit);
			listaRoles = pagedList.getItemsRangeList();
			usuarioVO.setListaRoles(listaRoles);
			return usuarioVO;
		} catch (ApplicationException ae) {
			return null;
		}
	}


	public void setUsuariosManager(UsuariosManager usuariosManager) {
		this.usuariosManager = usuariosManager;
	}

	public void setRolesManager(RolesManager rolesManager) {
		this.rolesManager = rolesManager;
	}

}
