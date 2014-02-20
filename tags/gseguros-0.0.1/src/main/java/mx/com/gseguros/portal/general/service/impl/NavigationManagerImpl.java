package mx.com.gseguros.portal.general.service.impl;

import java.util.List;

import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.ItemVO;
import mx.com.aon.portal.model.RamaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.general.dao.UsuarioDAO;
import mx.com.gseguros.portal.general.service.NavigationManager;

public class NavigationManagerImpl implements NavigationManager {
	
	private UsuarioDAO usuarioDAO;
	
	
	@Override
	public List<ItemVO> getMenuNavegacion(String perfil) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public List<UserVO> getAttributesUser(String user) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public int getNumRegistro(String user) throws ApplicationException {
		// TODO Auto-generated method stub
		return 0;
	}

	//TODO: Implementar el uso de este metodo, ya que actualmente se utiliza la forma anterior
	@Override
	public List<RamaVO> getClientesRoles(String user) throws ApplicationException {
		//TODO: Completar codigo
		/*
		List<RamaVO> listaRolesClientes = null;
		try{
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("PV_CDUSUARIO_I", user);
			
			usuarioDAO.obtieneRolesCliente(user)
			
			WrapperResultados respuesta = returnResult(params, "CARGA_ROLES_CLIENTES");
			
			
			listaRolesClientes=(List<RamaVO>) respuesta.getItemList();
		}catch (Exception bae) {
			throw new ApplicationException("Error retrieving data");
		}
		return(List<RamaVO>) listaRolesClientes;
		*/
		return null;
	}
	
	//TODO: Implementar el uso de este metodo, ya que actualmente se utiliza la forma anterior
	@Override
	public IsoVO getVariablesIso(String user) throws ApplicationException {
		IsoVO isoVO = null;
		try {
			isoVO = usuarioDAO.obtieneVariablesIso(user);
		} catch (Exception e) {
			new ApplicationException(e.getMessage(), e);
		}
		return isoVO;
	}


	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
	

}