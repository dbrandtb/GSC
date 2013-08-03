package mx.com.aon.catbo.web;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.UsuarioVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.RolesManager;

public class RolesAction extends AbstractListAction {
	private transient RolesManager rolesManager;
	
	private boolean success;

	private String grupo;

	private List<UsuarioVO> listaUsuarios;

	public String cmdObtenerUsuarios () throws ApplicationException {
		try {
			PagedList pagedList = rolesManager.getUsersFromGroup(grupo, start, limit);
			listaUsuarios = pagedList.getItemsRangeList();
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
}
