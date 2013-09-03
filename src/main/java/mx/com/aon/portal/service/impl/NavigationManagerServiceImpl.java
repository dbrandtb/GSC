package mx.com.aon.portal.service.impl;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.dao.UsuarioDAO;
import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.ItemVO;
import mx.com.aon.portal.model.RamaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.NavigationManager;
import mx.com.gseguros.exception.DaoException;

public class NavigationManagerServiceImpl implements NavigationManager {
	
	private UsuarioDAO usuarioDao;

	public List<ItemVO> getMenuNavegacion(String perfil)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RamaVO> getClientesRoles(String user)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNumRegistro(String user) throws ApplicationException {
		// TODO Auto-generated method stub
		return 0;
	}

	public IsoVO getVariablesIso(String user) throws ApplicationException {
		IsoVO isoVO = null;
		try {
			isoVO = (IsoVO)usuarioDao.invoke("OBTIENE_VARIABLES_ISO", user);
		} catch (DaoException e) {
			new ApplicationException(e.getMessage(), e);
		}
		return isoVO;
	}

	public List<UserVO> getAttributesUser(String user)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUsuarioDao(UsuarioDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	

}
