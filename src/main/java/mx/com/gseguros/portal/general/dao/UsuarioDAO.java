package mx.com.gseguros.portal.general.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.UsuarioRolEmpresaVO;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.general.model.UsuarioVO;

public interface UsuarioDAO {

	public String creaEditaRolSistema(Map params) throws Exception;
	
	public GenericVO guardaUsuario(Map params) throws Exception;

	public void cambiaEstatusUsuario(Map params) throws Exception;

	public List<UsuarioVO> obtieneUsuarios(Map params) throws Exception;
	
	public List<GenericVO> obtienerRolesPorPrivilegio(Map params) throws Exception;
	
	public List<Map<String, String>> obtieneRolesUsuario(Map params) throws Exception;
	
	public String guardaRolUsuario(Map params) throws Exception;

	public List<Map<String, String>> obtieneProductosAgente(Map params) throws Exception;
	
	public String guardaProductoAgente(Map params) throws Exception;
	
	public List<UsuarioRolEmpresaVO> obtieneRolesCliente(String user) throws Exception;

	public IsoVO obtieneVariablesIso(String user) throws Exception;
	
	public void guardarSesion(String idSesion,String cdusuari,String cdsisrol,String userAgent,boolean esMovil,Date fecha) throws Exception;

}