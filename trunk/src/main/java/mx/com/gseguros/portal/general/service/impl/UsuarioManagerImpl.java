package mx.com.gseguros.portal.general.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.EmpresaVO;
import mx.com.aon.portal.model.RamaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.model.UsuarioRolEmpresaVO;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.general.dao.UsuarioDAO;
import mx.com.gseguros.portal.general.dao.impl.UsuarioDAOImpl;
import mx.com.gseguros.portal.general.model.RolVO;
import mx.com.gseguros.portal.general.model.UsuarioVO;
import mx.com.gseguros.portal.general.service.UsuarioManager;
import mx.com.gseguros.utils.Constantes;

import org.apache.log4j.Logger;

public class UsuarioManagerImpl implements UsuarioManager {
	
	private Logger logger = Logger.getLogger(UsuarioDAOImpl.class);
	
	
	private UsuarioDAO usuarioDAO;
	
	
	@Override
	public boolean creaEditaRolSistema(Map<String, String> params) throws Exception {
		if(Constantes.MSG_TITLE_OK.equals(usuarioDAO.creaEditaRolSistema(params))){
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public GenericVO guardaUsuario(Map<String, String> params) throws Exception {
		return usuarioDAO.guardaUsuario(params);
	}

	@Override
	public void cambiaEstatusUsuario(Map<String, String> params) throws Exception {
		usuarioDAO.cambiaEstatusUsuario(params);
	}

	@Override
	public List<UsuarioVO> obtieneUsuarios(Map<String, String> params) throws Exception {
		return usuarioDAO.obtieneUsuarios(params);
	}

	@Override
	public List<GenericVO> obtienerRolesPorPrivilegio(Map<String, String> params) throws Exception{
		try {
			return usuarioDAO.obtienerRolesPorPrivilegio(params);
		} catch (DaoException daoExc) {
			throw new Exception(daoExc.getMessage(), daoExc);
		}
		
	}
	
	@Override
	public List<Map<String, String>> obtieneRolesUsuario(Map<String, String> params) throws Exception{
		try {
			return usuarioDAO.obtieneRolesUsuario(params);
		} catch (DaoException daoExc) {
			throw new Exception(daoExc.getMessage(), daoExc);
		}
	
	}
	
	@Override
	public boolean guardaRolesUsuario(Map<String, String> params, List<Map<String, String>> saveList) throws Exception{
		boolean allUpdated = true;
		
		for(Map<String, String> rol : saveList){
			try {
				params.put("PV_ACCION_I", rol.get("EXISTE_ROL"));
				params.put("PV_CDSISROL_I", rol.get("CDSISROL"));
				usuarioDAO.guardaRolUsuario(params);
			} catch (DaoException daoExc) {
				logger.error("Error al guardar Rol ",daoExc);
				allUpdated = false;
			}
		}
		
		return allUpdated;
	}

	@Override
	public List<Map<String, String>> obtieneProductosAgente(Map<String, String> params) throws Exception{
		try {
			return usuarioDAO.obtieneProductosAgente(params);
		} catch (DaoException daoExc) {
			throw new Exception(daoExc.getMessage(), daoExc);
		}
		
	}
	
	@Override
	public boolean guardaProductosAgente(Map<String, String> params, List<Map<String, String>> saveList) throws Exception{
		boolean allUpdated = true;
		
		for(Map<String, String> rol : saveList){
			try {
				params.put("PV_ACCION_I", rol.get("TIENE_CDRAMO"));
				params.put("PV_CDRAMO_I", rol.get("CDRAMO"));
				usuarioDAO.guardaProductoAgente(params);
			} catch (DaoException daoExc) {
				logger.error("Error al guardar Rol ",daoExc);
				allUpdated = false;
			}
		}
		
		return allUpdated;
	}
	
	
	/**
	 * Obtiene la lista de los Nodos y las Hojas para el arbol de Seleccion
	 * de Rol y Cliente
	 * @param User - usuario admitido desde el login
	 * @return Lista con elementos del Arbol.
	 * @throws Exception
	 */
	//TODO: Implementar el uso de este metodo, ya que actualmente se utiliza la forma anterior
	@Override
	public List<RamaVO> getClientesRoles(String user) throws Exception {
        
        List<UsuarioRolEmpresaVO> resultado = usuarioDAO.obtieneRolesCliente(user);

        List<RamaVO> listaRamas = null;
        if (resultado !=null) {
            listaRamas = new ArrayList<RamaVO>();

            RamaVO ramaVO = null;
            RamaVO children = null;

            List<RamaVO> listaChildrens = null;

            UsuarioRolEmpresaVO usuarioRolEmpresaVO = (UsuarioRolEmpresaVO)resultado.get(0);

            ramaVO = new RamaVO();
            ramaVO.setText(usuarioRolEmpresaVO.getDsElemen());
            ramaVO.setCodigoObjeto(usuarioRolEmpresaVO.getCdElemento());
            ramaVO.setNick(usuarioRolEmpresaVO.getCdUsuario());
            ramaVO.setName(usuarioRolEmpresaVO.getDsUsuario());
            ramaVO.setDsRol(usuarioRolEmpresaVO.getDsSisRol());
            ramaVO.setClaveRol(usuarioRolEmpresaVO.getCdSisRol());
            ramaVO.setCdElemento(usuarioRolEmpresaVO.getCdElemento());

            listaChildrens = new ArrayList<RamaVO>();

            listaRamas.add(ramaVO);
            boolean agregado = false;

            for (int i = 0; i < resultado.size(); i++) {
                usuarioRolEmpresaVO  =  (UsuarioRolEmpresaVO)resultado.get(i);
                if (usuarioRolEmpresaVO.getCdElemento().equals(ramaVO.getCodigoObjeto())) {
                    children = new RamaVO();
                    children.setText(usuarioRolEmpresaVO.getDsSisRol());
                    children.setCodigoObjeto(usuarioRolEmpresaVO.getCdSisRol());
                    children.setCdElemento(ramaVO.getCodigoObjeto());
                    listaChildrens.add(children);
                    agregado = true;
                } else {
                    //seteando los hijos de la rama anterior
                    ramaVO.setChildren(listaChildrens.toArray(new RamaVO[listaChildrens.size()]));
                    agregado = true;

                    ramaVO = new RamaVO();
                    ramaVO.setText(usuarioRolEmpresaVO.getDsElemen());
                    ramaVO.setCodigoObjeto(usuarioRolEmpresaVO.getCdElemento());
                    ramaVO.setNick(usuarioRolEmpresaVO.getCdUsuario());
                    ramaVO.setName(usuarioRolEmpresaVO.getDsUsuario());
                    ramaVO.setDsRol(usuarioRolEmpresaVO.getDsSisRol());
                    ramaVO.setClaveRol(usuarioRolEmpresaVO.getCdSisRol());
                    ramaVO.setCdElemento(usuarioRolEmpresaVO.getCdElemento());

                    children = new RamaVO();
                    children.setText(usuarioRolEmpresaVO.getDsSisRol());
                    children.setCodigoObjeto(usuarioRolEmpresaVO.getCdSisRol());
                    children.setCdElemento(usuarioRolEmpresaVO.getCdElemento());
                    listaChildrens = new ArrayList<RamaVO>();
                    listaChildrens.add(children);

                    listaRamas.add(ramaVO);

                }
            }
            if (agregado) {
                ramaVO.setChildren(listaChildrens.toArray(new RamaVO[listaChildrens.size()]));
            }
        }
        return listaRamas;
	}

	//TODO: Implementar el uso de este metodo, ya que actualmente se utiliza la forma anterior
	@Override
	public List<UserVO> getAttributesUser(String user)	throws Exception {
		
		List<UsuarioRolEmpresaVO> resultado = usuarioDAO.obtieneRolesCliente(user);
		
        List<UserVO> listaUsuarios = null;
        
        if (resultado !=null) {
        	
            listaUsuarios = new ArrayList<UserVO>();

            UserVO userVO = null;
            EmpresaVO empresaVO = null;
            RolVO rolVO = null;
            List<RolVO> listaRoles = null;

            UsuarioRolEmpresaVO usuarioRolEmpresaVO = (UsuarioRolEmpresaVO)resultado.get(0);

            userVO = new UserVO();
            userVO.setUser(usuarioRolEmpresaVO.getCdUsuario());
            userVO.setCodigoPersona(usuarioRolEmpresaVO.getCdPerson());
            userVO.setName(usuarioRolEmpresaVO.getDsUsuario());

            empresaVO = new EmpresaVO();
            empresaVO.setElementoId(usuarioRolEmpresaVO.getCdElemento());
            empresaVO.setNombre(usuarioRolEmpresaVO.getDsElemen());
            userVO.setEmpresa(empresaVO);
            listaRoles = new ArrayList<RolVO>();
            userVO.setRoles(listaRoles);

            listaUsuarios.add(userVO);

            for (int i = 0; i < resultado.size(); i++) {
                usuarioRolEmpresaVO  =  (UsuarioRolEmpresaVO)resultado.get(i);
                if (usuarioRolEmpresaVO.getCdElemento().equals(userVO.getEmpresa().getElementoId())) {
                    rolVO = new RolVO();
                    BaseObjectVO baseObjectVO = new BaseObjectVO();
                    baseObjectVO.setValue(usuarioRolEmpresaVO.getCdSisRol());
                    baseObjectVO.setLabel(usuarioRolEmpresaVO.getDsSisRol());
                    rolVO.setObjeto(baseObjectVO);
                    userVO.getRoles().add(rolVO);
                } else {

                    userVO = new UserVO();
                    userVO.setUser(usuarioRolEmpresaVO.getCdUsuario());
                    userVO.setCodigoPersona(usuarioRolEmpresaVO.getCdPerson());
                    userVO.setName(usuarioRolEmpresaVO.getDsUsuario());

                    empresaVO = new EmpresaVO();
                    empresaVO.setElementoId(usuarioRolEmpresaVO.getCdElemento());
                    empresaVO.setNombre(usuarioRolEmpresaVO.getDsElemen());
                    userVO.setEmpresa(empresaVO);
                    listaRoles = new ArrayList<RolVO>();
                    userVO.setRoles(listaRoles);

                    rolVO = new RolVO();
                    BaseObjectVO baseObjectVO = new BaseObjectVO();
                    baseObjectVO.setValue(usuarioRolEmpresaVO.getCdSisRol());
                    baseObjectVO.setLabel(usuarioRolEmpresaVO.getDsSisRol());
                    rolVO.setObjeto(baseObjectVO);
                    userVO.getRoles().add(rolVO);

                    listaUsuarios.add(userVO);

                }
            }
        }
        return listaUsuarios;
	}


	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
	
	
	
}