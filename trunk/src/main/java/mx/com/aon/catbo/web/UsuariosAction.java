package mx.com.aon.catbo.web;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.RolesVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.UsuariosManager;

public class UsuariosAction extends AbstractListAction {
	private transient UsuariosManager usuariosManager;

	private boolean success;

	private String user;
	
	private String grupo;

	private List<RolesVO> listaRoles;

	public String cmdGetRoles () throws ApplicationException {
		try {
			PagedList pagedList = usuariosManager.getRoles(user, start, limit);
			listaRoles = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}

	public String cmdVerificarUsuarioEnGrupo () throws ApplicationException {
		try {
			String msg = usuariosManager.isUserInGroup(user, grupo);
			addActionMessage(msg);
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<RolesVO> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<RolesVO> listaRoles) {
		this.listaRoles = listaRoles;
	}

	public void setUsuariosManager(UsuariosManager usuariosManager) {
		this.usuariosManager = usuariosManager;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
}
