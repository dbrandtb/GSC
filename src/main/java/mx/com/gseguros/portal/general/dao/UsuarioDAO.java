package mx.com.gseguros.portal.general.dao;

import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.UsuarioRolEmpresaVO;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.DaoException;

public interface UsuarioDAO {

	public GenericVO guardaUsuario(Map params) throws DaoException;
	
	public List<UsuarioRolEmpresaVO> obtieneRolesCliente(String user) throws DaoException;

	public IsoVO obtieneVariablesIso(String user) throws DaoException;

}