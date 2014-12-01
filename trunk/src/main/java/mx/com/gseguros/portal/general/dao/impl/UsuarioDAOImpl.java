package mx.com.gseguros.portal.general.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.UsuarioRolEmpresaVO;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.DinamicMapper;
import mx.com.gseguros.portal.general.dao.UsuarioDAO;
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
	public String creaEditaRolSistema(Map params) throws Exception {
		try {
			Map<String, Object> result = ejecutaSP(new CreaEditaRolSistema(getDataSource()), params);
			return (String) result.get("pv_title_o");
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	protected class CreaEditaRolSistema extends StoredProcedure {
		
		protected CreaEditaRolSistema(DataSource dataSource){
			super(dataSource, "PKG_GENERA_USUARIO.P_MOV_TSISROL");
			declareParameter(new SqlParameter("pv_accion_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsisrol_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dssisrol_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public GenericVO guardaUsuario(Map params) throws Exception {
		try {
			HashMap<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("PV_CDUSUARI_I", params.get("cdusuari"));
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
			parametros.put("PV_CDMODGRA_I", params.get("cdmodgra"));
			parametros.put("PV_CDUNIECO_I", params.get("cdunieco"));
			parametros.put("PV_CDRAMO_I", params.get("cdramo"));
			parametros.put("PV_FEDESDE_I", params.get("feini"));
			parametros.put("PV_FEVENLICV_I", params.get("fefin"));
			parametros.put("PV_ACCION_I", params.get("accion"));
			
			Map<String, Object> result = ejecutaSP(new GuardaUsuario(getDataSource()), parametros);

			GenericVO respuestaVO = new GenericVO();
			respuestaVO.setKey(result.get("msg_id").toString());
			respuestaVO.setValue(result.get("msg_title").toString());
	    	return respuestaVO;
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	protected class GuardaUsuario extends StoredProcedure {
		
		protected GuardaUsuario(DataSource dataSource){
			super(dataSource, "PKG_GENERA_USUARIO.P_MOV_USUARIO");
			declareParameter(new SqlParameter("PV_CDUSUARI_I", OracleTypes.VARCHAR));
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
			declareParameter(new SqlParameter("PV_CDMODGRA_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDUNIECO_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDRAMO_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_FEDESDE_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_FEVENLICV_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_ACCION_I", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public void cambiaEstatusUsuario(Map params) throws Exception {
		try {
			ejecutaSP(new CambiaEstatusUsuario(getDataSource()), params);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	protected class CambiaEstatusUsuario extends StoredProcedure {
		
		protected CambiaEstatusUsuario(DataSource dataSource){
			super(dataSource, "PKG_GENERA_USUARIO.P_CAMBIA_ESTATUS");
			declareParameter(new SqlParameter("PV_CDUSUARI_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_SWACTIVO_I", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public List<UsuarioRolEmpresaVO> obtieneRolesCliente(String user) throws Exception {
		
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

    protected class UsuarioRolEmpresaMapper implements RowMapper<UsuarioRolEmpresaVO> {
		public UsuarioRolEmpresaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
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
    
    /*
    protected class UsuarioRolEmpresaMapper2 implements RowMapper<RamaVO> {
		public RamaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			RamaVO rama = new RamaVO();
			
			rama.setText(rs.getString("DSELEMEN"));
			rama.setCodigoObjeto(rs.getString("CDELEMENTO"));
			rama.setCdElemento(rs.getString("CDELEMENTO"));
			rama.setNick(rs.getString("CDUSUARIO"));
			rama.setName(rs.getString("DSUSUARI"));
			rama.setDsRol(rs.getString("DSSISROL"));
			rama.setClaveRol(rs.getString("CDSISROL"));
			//rama.setAllowDelete(allowDelete);
			//rama.setChildren(children);
			//rama.setExpanded(expanded);
			//rama.setId(id);
			//rama.setLeaf(leaf);
			return rama;
        }
    }
    */
    
    
    @Override
    public IsoVO obtieneVariablesIso(String user) throws Exception {
    	
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
	public List<UsuarioVO> obtieneUsuarios(Map params) throws Exception {
		Map<String, Object> resultado = ejecutaSP(new ObtieneUsuarios(getDataSource()), params);
		return (List<UsuarioVO>) resultado.get("PV_REGISTRO_O");
	}
	
	protected class ObtieneUsuarios extends StoredProcedure {

		protected ObtieneUsuarios(DataSource dataSource) {
			super(dataSource, "PKG_GENERA_USUARIO.P_GET_USUARIOS_POR_PRIVILEGIOS");
			declareParameter(new SqlParameter("pv_cdsisrol_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDUSUARI_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_NOMBRE_I", OracleTypes.VARCHAR));
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
			usuarioVO.setDsNombre(rs.getString("DSNOMBRE"));
			usuarioVO.setDsNombre1(rs.getString("DSNOMBRE1"));
			usuarioVO.setDsApellido(rs.getString("DSAPELLIDO"));
			usuarioVO.setDsApellido1(rs.getString("DSAPELLIDO1"));
			usuarioVO.setOtSexo(rs.getString("OTSEXO"));
			usuarioVO.setdSexo(rs.getString("DSEXO"));
			usuarioVO.setFeNacimi(rs.getString("FENACIMI"));
			usuarioVO.setCdrfc(rs.getString("CDRFC"));
			usuarioVO.setDsEmail(rs.getString("DSEMAIL"));
			usuarioVO.setCurp(rs.getString("CURP"));

			usuarioVO.setCdrol(rs.getString("CDSISROL"));
			usuarioVO.setEsAdmin(rs.getString("CDMODGRA"));
			usuarioVO.setCdunieco(rs.getString("CDUNIECO"));
			usuarioVO.setFeini(rs.getString("FEDESDE"));
			usuarioVO.setFefinlic(rs.getString("FEVENLICV"));
			usuarioVO.setSwActivo(rs.getString("SWACTIVO"));
			
			return usuarioVO;
        }
    }

    @Override
	public List<GenericVO> obtienerRolesPorPrivilegio(Map params) throws Exception {
		Map<String, Object> resultado = ejecutaSP(new ObtienerRolesPorPrivilegio(getDataSource()), params);
		return (List<GenericVO>) resultado.get("PV_REGISTRO_O");
	}
	
	protected class ObtienerRolesPorPrivilegio extends StoredProcedure {
		protected ObtienerRolesPorPrivilegio(DataSource dataSource) {
			super(dataSource, "PKG_GENERA_USUARIO.P_BUSCA_ROL_POR_PRIVILEGIOS");
			declareParameter(new SqlParameter("pv_cdsisrol_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_REGISTRO_O", OracleTypes.CURSOR, new ObtieneRolesSistemaMapper()));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class ObtieneRolesSistemaMapper implements RowMapper {
		
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		return new GenericVO(rs.getString("CDSISROL"), rs.getString("DSSISROL"));
    	}
    }
    
    @Override
    public List<Map<String, String>> obtieneRolesUsuario(Map params) throws Exception {
    	Map<String, Object> resultado = ejecutaSP(new ObtieneRolesUsuario(getDataSource()), params);
    	return (List<Map<String, String>>) resultado.get("PV_REGISTRO_O");
    }
    
    protected class ObtieneRolesUsuario extends StoredProcedure {
    	
    	protected ObtieneRolesUsuario(DataSource dataSource) {
    		super(dataSource, "PKG_GENERA_USUARIO.P_GET_ROLES_SIST_USUARIO");
    		declareParameter(new SqlParameter("PV_CDUSUARIO_I", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("PV_REGISTRO_O", OracleTypes.CURSOR, new DinamicMapper()));
    		declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
    		compile();
    	}
    }
    
    @Override
	public String guardaRolUsuario(Map params)
			throws Exception {
		logger.debug("Params guardaRolUsuario: "+ params);
		Map<String, Object> mapResult = ejecutaSP(new GuardaRolUsuario(getDataSource()), params);
		
		return (String) mapResult.get("PV_TITLE_O");
	}
	
	protected class GuardaRolUsuario extends StoredProcedure {

		protected GuardaRolUsuario(DataSource dataSource) {
			super(dataSource, "PKG_GENERA_USUARIO.P_MOV_SISROL_USUARIO");
			
			declareParameter(new SqlParameter("PV_ACCION_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDUSUARI_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSISROL_I", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String, String>> obtieneProductosAgente(Map params) throws Exception {
		Map<String, Object> resultado = ejecutaSP(new ObtieneProductosAgente(getDataSource()), params);
		return (List<Map<String, String>>) resultado.get("PV_REGISTRO_O");
	}
	
	protected class ObtieneProductosAgente extends StoredProcedure {
		
		protected ObtieneProductosAgente(DataSource dataSource) {
			super(dataSource, "PKG_GENERA_USUARIO.P_GET_RAMOS_AGENTE");
			declareParameter(new SqlParameter("PV_CDAGENTE_I", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_REGISTRO_O", OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String guardaProductoAgente(Map params)
			throws Exception {
		logger.debug("Params guardaProductoAgente: "+ params);
		Map<String, Object> mapResult = ejecutaSP(new GuardaProductoAgente(getDataSource()), params);
		
		return (String) mapResult.get("PV_TITLE_O");
	}
	
	protected class GuardaProductoAgente extends StoredProcedure {
		
		protected GuardaProductoAgente(DataSource dataSource) {
			super(dataSource, "PKG_GENERA_USUARIO.P_MOV_MAGECOM");
			
			declareParameter(new SqlParameter("PV_CDRAMO_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDAGENTE_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_ACCION_I", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void guardarSesion(String idSesion,String cdusuari,String cdsisrol,String userAgent,boolean esMovil,Date fecha) throws Exception
	{
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("idSesion"  , idSesion);
		params.put("cdusuari"  , cdusuari);
		params.put("cdsisrol"  , cdsisrol);
		params.put("userAgent" , userAgent);
		params.put("esMovil" , esMovil?"S":"N");
		params.put("fecha"     , fecha);
		ejecutaSP(new GuardarSesion(getDataSource()),params);
	}
	
	protected class GuardarSesion extends StoredProcedure {

		protected GuardarSesion(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.P_INSERTA_SESION");
			
			declareParameter(new SqlParameter("idSesion"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("userAgent" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("esMovil"   , OracleTypes.CHAR));
			declareParameter(new SqlParameter("fecha"     , OracleTypes.TIMESTAMP));
	        declareParameter(new SqlOutParameter("PV_MSG_ID_O" , OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("PV_TITLE_O"  , OracleTypes.VARCHAR));
			compile();
		}
	}
    
}