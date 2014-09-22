package mx.com.gseguros.portal.general.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.UsuarioRolEmpresaVO;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.general.model.UsuarioVO;

public interface UsuarioDAO {

	public String creaEditaRolSistema(Map params) throws DaoException;
	
	public GenericVO guardaUsuario(Map params) throws DaoException;

	public void cambiaEstatusUsuario(Map params) throws DaoException;

	public List<UsuarioVO> obtieneUsuarios(Map params) throws DaoException;
	
	public List<GenericVO> obtienerRolesPorPrivilegio(Map params) throws DaoException;
	
	public List<Map<String, String>> obtieneRolesUsuario(Map params) throws DaoException;
	
	public String guardaRolUsuario(Map params) throws DaoException;

	public List<Map<String, String>> obtieneProductosAgente(Map params) throws DaoException;
	
	public String guardaProductoAgente(Map params) throws DaoException;
	
	public List<UsuarioRolEmpresaVO> obtieneRolesCliente(String user) throws DaoException;

	public IsoVO obtieneVariablesIso(String user) throws DaoException;
	
	public void guardarSesion(String idSesion,String cdusuari,String cdsisrol,String userAgent,boolean esMovil,Date fecha) throws Exception;

}