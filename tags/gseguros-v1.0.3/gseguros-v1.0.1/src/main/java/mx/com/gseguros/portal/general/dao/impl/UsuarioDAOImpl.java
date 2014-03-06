package mx.com.gseguros.portal.general.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.UsuarioRolEmpresaVO;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.general.dao.UsuarioDAO;
import mx.com.gseguros.portal.general.model.RolVO;
import mx.com.gseguros.portal.general.model.UsuarioVO;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class UsuarioDAOImpl extends AbstractManagerDAO implements UsuarioDAO {

	private Logger logger = Logger.getLogger(UsuarioDAOImpl.class);
	
	
	@Override
	public GenericVO guardaUsuario(Map params) throws DaoException {
		try {
			HashMap<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("PV_TIPO_I", Integer.parseInt(params.get("esAgente").toString()));
			parametros.put("PV_CDUSUARI_I", params.get("cdusuari"));
			parametros.put("PV_CDAGENTE_I", params.get("cdagente"));
			parametros.put("PV_DSNOMBRE_I", params.get("dsnombre"));
			parametros.put("PV_DSNOMBRE1_I", params.get("dsnombre1"));
			parametros.put("PV_DSAPELLIDO_I", params.get("dsapellido"));
			parametros.put("PV_DSAPELLIDO1_I", params.get("dsapellido1"));
			parametros.put("PV_CDSISROL_I", params.get("cdsisrol"));
			parametros.put("PV_OTSEXO_I", params.get("otsexo"));
			parametros.put("PV_FENACIMI_I", params.get("fenacimi"));
			parametros.put("PV_CDRFC_I", params.get("cdrfc"));
			parametros.put("PV_DSEMAIL_I", params.get("dsemail"));
			parametros.put("PV_CURP_I", params.get("curp"));
			parametros.put("pv_cdaccion_i", params.get("accion"));
			
			Map<String, Object> result = ejecutaSP(new GuardaUsuario(getDataSource()), parametros);

			GenericVO respuestaVO = new GenericVO();
			respuestaVO.setKey(result.get("msg_id").toString());
			respuestaVO.setValue(result.get("msg_title").toString());
	    	return respuestaVO;
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}

	protected class GuardaUsuario extends StoredProcedure {
		
		protected GuardaUsuario(DataSource dataSource){
			super(dataSource, "PKG_GENERA_USUARIO.MOV_USUARIO");
			declareParameter(new SqlParameter("PV_TIPO_I", OracleTypes.NUMBER));
			declareParameter(new SqlParameter("PV_CDUSUARI_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDAGENTE_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_DSNOMBRE_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_DSNOMBRE1_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_DSAPELLIDO_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_DSAPELLIDO1_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSISROL_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTSEXO_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_FENACIMI_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDRFC_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_DSEMAIL_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CURP_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdaccion_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public List<UsuarioRolEmpresaVO> obtieneRolesCliente(String user) throws DaoException {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("PV_CDUSUARIO_I", user);
		
		Map<String, Object> resultado = ejecutaSP(new CargaRolesClientes(getDataSource()), params);
		return (List<UsuarioRolEmpresaVO>) resultado.get("PV_REGISTRO_O");
	}
	
	protected class CargaRolesClientes extends StoredProcedure {

		protected CargaRolesClientes(DataSource dataSource) {
			super(dataSource, "PKG_CONFPAGINA.P_OBTIENE_ROLES_CLIENTE");
			declareParameter(new SqlParameter("PV_CDUSUARIO_I", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_REGISTRO_O", OracleTypes.CURSOR, new UsuarioRolEmpresaMapper()));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
			compile();
		}
	}

    protected class UsuarioRolEmpresaMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			UsuarioRolEmpresaVO usuarioRolEmpresaVO = new UsuarioRolEmpresaVO();
			usuarioRolEmpresaVO.setCdUsuario(rs.getString("CDUSUARIO"));
			usuarioRolEmpresaVO.setCdPerson(rs.getString("CDPERSON"));
			usuarioRolEmpresaVO.setDsUsuario(rs.getString("DSUSUARI"));
			usuarioRolEmpresaVO.setCdElemento(rs.getString("CDELEMENTO"));
			usuarioRolEmpresaVO.setDsElemen(rs.getString("DSELEMEN"));
			usuarioRolEmpresaVO.setCdSisRol(rs.getString("CDSISROL"));
			usuarioRolEmpresaVO.setDsSisRol(rs.getString("DSSISROL"));
			return usuarioRolEmpresaVO;
        }
    }
    
    //TODO: Implementar el uso de este metodo, ya que actualmente se utiliza la forma anterior
    @Override
    public IsoVO obtieneVariablesIso(String user) throws DaoException {
    	
    	HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("P_USUARIO", user);
		
		Map<String, Object> resultado = ejecutaSP(new ObtieneVariablesIso(getDataSource()), params);
		
		IsoVO isoVO = new IsoVO();
		isoVO.setCdIdioma((String)resultado.get("LANGCODE"));
		isoVO.setCdRegion((String)resultado.get("REGIONID"));
		isoVO.setClientDateFormat((String)resultado.get("P_DSFORMATOFECHAC"));
		isoVO.setFormatoFecha((String)resultado.get("P_DSFORMATOFECHA"));
		isoVO.setFormatoNumerico((String)resultado.get("P_DSFORMATONUMERICO"));
		isoVO.setLanguague((String)resultado.get("P_LANGUAGUE_ISO"));
		isoVO.setPais((String)resultado.get("P_PAIS"));
		
		return isoVO;
    }
    
    protected class ObtieneVariablesIso extends StoredProcedure {
    	
        protected ObtieneVariablesIso(DataSource dataSource) {
            super(dataSource, "PKG_VAR_GLOBAL.P_VARIABLES_OBTIENE_ISO");
            declareParameter(new SqlParameter("P_USUARIO", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("P_PAIS", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("P_LANGUAGUE_ISO", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("LANGCODE", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("REGIONID", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("P_DSFORMATONUMERICO", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("P_DSFORMATOFECHA", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("P_DSFORMATOFECHAC", OracleTypes.VARCHAR));
            compile();
        }
    }
 
    @Override
	public List<UsuarioVO> obtieneUsuarios(Map params) throws DaoException {
		Map<String, Object> resultado = ejecutaSP(new ObtieneUsuarios(getDataSource()), params);
		return (List<UsuarioVO>) resultado.get("PV_REGISTRO_O");
	}
	
	protected class ObtieneUsuarios extends StoredProcedure {

		protected ObtieneUsuarios(DataSource dataSource) {
			super(dataSource, "PKG_GENERA_USUARIO.p_get_usuarios");
			declareParameter(new SqlParameter("PV_NOMBRE_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSISROL_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDRFC_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_DSEMAIL_I", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_REGISTRO_O", OracleTypes.CURSOR, new ObtieneUsuariosMapper()));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
			compile();
		}
	}

    protected class ObtieneUsuariosMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			UsuarioVO usuarioVO = new UsuarioVO();
			usuarioVO.setCdUsuario(rs.getString("CDUSUARI"));
			usuarioVO.setDsUsuario(rs.getString("DSUSUARI"));
			usuarioVO.setDsNombre(rs.getString("DSNOMBRE"));
			usuarioVO.setDsNombre1(rs.getString("DSNOMBRE1"));
			usuarioVO.setDsApellido(rs.getString("DSAPELLIDO"));
			usuarioVO.setDsApellido1(rs.getString("DSAPELLIDO1"));
			usuarioVO.setOtSexo(rs.getString("OTSEXO"));
			usuarioVO.setdSexo(rs.getString("DSEXO"));
			usuarioVO.setFeNacimi(rs.getString("FENACIMI"));
			usuarioVO.setCdrfc(rs.getString("CDRFC"));
			usuarioVO.setDsEmail(rs.getString("DSEMAIL"));
			
			
			return usuarioVO;
        }
    }

    
    @Override
    public List<RolVO> obtieneRolesUsuario(Map params) throws DaoException {
    	Map<String, Object> resultado = ejecutaSP(new ObtieneRolesUsuario(getDataSource()), params);
    	return (List<RolVO>) resultado.get("PV_REGISTRO_O");
    }
    
    protected class ObtieneRolesUsuario extends StoredProcedure {
    	
    	protected ObtieneRolesUsuario(DataSource dataSource) {
    		super(dataSource, "PKG_GENERA_USUARIO.P_GET_ROLES_USUARIO");
    		declareParameter(new SqlParameter("PV_CDUSUARI_I", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("PV_REGISTRO_O", OracleTypes.CURSOR, new ObtieneRolesUsuarioMapper()));
    		declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
    		compile();
    	}
    }
    
    protected class ObtieneRolesUsuarioMapper implements RowMapper {
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		RolVO rol =  new RolVO();
    		rol.setCodigoRol(rs.getString("CDSISROL"));
    		rol.setDescripcionRol(rs.getString("DSSISROL"));
    		
    		return rol;
    	}
    }

}