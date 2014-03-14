package mx.com.gseguros.portal.general.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.general.dao.CatalogosDAO;
import mx.com.gseguros.portal.general.util.Rango;
import mx.com.gseguros.portal.general.util.TipoTramite;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class CatalogosDAOImpl extends AbstractManagerDAO implements CatalogosDAO {

	protected final transient Logger logger = Logger.getLogger(CatalogosDAOImpl.class);
	
	@Override
	public List<GenericVO> obtieneTmanteni(String cdTabla) throws DaoException{
		
		try {
			Map<String,Object> params=new HashMap<String,Object>(0);
			params.put("pv_cdtabla", cdTabla);

			Map<String, Object> resultado = ejecutaSP(new ObtenerTmanteni(getDataSource()), params);
			return (List<GenericVO>) resultado.get("pv_registro_o");
			
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}
	
	protected class ObtenerTmanteni extends StoredProcedure {
    	protected ObtenerTmanteni(DataSource dataSource) {
            super(dataSource,"PKG_LISTAS.P_GET_TMANTENI");
            declareParameter(new SqlParameter("pv_cdtabla",       OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerTmanteniMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o",   OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",    OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    protected class ObtenerTmanteniMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            GenericVO generic=new GenericVO();
            generic.setKey(rs.getString("CODIGO"));
            generic.setValue(rs.getString("DESCRIPC"));
            return generic;
        }
    }
    
    
    public List<GenericVO> obtieneColonias(String codigoPostal) throws DaoException {
    	try {
    		HashMap<String,Object> params =  new HashMap<String, Object>();
    		params.put("pv_codpostal_i", codigoPostal);
    		
    		Map<String, Object> resultado = ejecutaSP(new ObtenCatalogoColonias(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
    }
    
    protected class ObtenCatalogoColonias extends StoredProcedure {
    	
    	protected ObtenCatalogoColonias(DataSource dataSource) {
    		super(dataSource, "PKG_LISTAS.P_GET_COLONIAS");
    		
    		declareParameter(new SqlParameter("pv_codpostal_i", OracleTypes.VARCHAR));			
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ColoniasMapper()));
    		declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    }
    
    protected class ColoniasMapper  implements RowMapper {
    	
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		GenericVO base = new GenericVO();	    
    		base.setKey(rs.getString("CODIGO"));
    		base.setValue(rs.getString("NOMBRE"));
    		return base;
    	}
    }


	@Override
	public List<GenericVO> obtieneAtributosSituacion(String cdAtribu, String cdTipSit, String otValor) throws DaoException {
		try {
    		HashMap<String,Object> params = new HashMap<String,Object>();
            params.put("pv_cdatribu_i",cdAtribu);
            params.put("pv_cdtipsit_i",cdTipSit);
            params.put("pv_otvalor_i",otValor);
    		
    		Map<String, Object> resultado = ejecutaSP(new ObtieneAtributosSit(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}
	
	protected class ObtieneAtributosSit extends StoredProcedure {

        protected ObtieneAtributosSit(DataSource dataSource) {
            super(dataSource, "PKG_LISTAS.P_GET_ATRIBUTOS_SIT");
            declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdatribu_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_otvalor_i", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneAtributosSitMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
        }
    }

    protected class ObtieneAtributosSitMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new GenericVO(rs.getString("CODIGO"),rs.getString("DESCRIPCION"));
        }
    }
    

	@Override
	public List<GenericVO> obtieneAtributosPoliza(String cdAtribu, String cdRamo, String otValor) throws DaoException {
		
		try {
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("pv_cdatribu_i", cdAtribu);
			params.put("pv_cdramo_i", cdRamo);
			params.put("pv_otvalor_i", otValor);
    		
    		Map<String, Object> resultado = ejecutaSP(new ObtieneAtributosPol(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}
	
	protected class ObtieneAtributosPol extends StoredProcedure {
	
		protected ObtieneAtributosPol(DataSource dataSource) {
			super(dataSource, "PKG_LISTAS.P_GET_ATRIBUTOS_POL");
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdatribu_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneAtributosPolMapper()));
			declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class ObtieneAtributosPolMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new GenericVO(rs.getString("CODIGO"),rs.getString("DESCRIPCION"));
		}
	}
	

	@Override
	public List<GenericVO> obtieneAtributosGarantia(String cdAtribu, String cdTipSit, String cdRamo, String valAnt, String cdGarant)
			throws DaoException {
		try {
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("pv_cdramo_i"  ,cdRamo);
			params.put("pv_cdtipsit_i",cdTipSit);
			params.put("pv_cdgarant_i",cdGarant);
			params.put("pv_cdatribu_i",cdAtribu);
			params.put("pv_otvalor_i" ,valAnt);
    		
    		Map<String, Object> resultado = ejecutaSP(new ObtieneAtributosGar(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}
	
	protected class ObtieneAtributosGar extends StoredProcedure {

		protected ObtieneAtributosGar(DataSource dataSource) {
			super(dataSource, "PKG_LISTAS.P_GET_ATRIBUTOS_GAR");
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdatribu_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneAtributosGarMapper()));
			declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}

	protected class ObtieneAtributosGarMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new GenericVO(rs.getString("CODIGO"),rs.getString("DESCRIPCION"));
		}
	}

	
	@Override
	public List<GenericVO> obtieneAtributosRol(String cdAtribu,
			String cdTipSit, String cdRamo, String valAnt, String cdRol)
			throws DaoException {
		try {
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("pv_cdramo_i", cdRamo);
			params.put("pv_cdrol_i", cdRol);
			params.put("pv_cdatribu_i", cdAtribu);
			params.put("pv_cdtipsit_i", cdTipSit);
			params.put("pv_otvalor_i", valAnt);
    		
    		Map<String, Object> resultado = ejecutaSP(new ObtieneAtributosRol(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}
    

	protected class ObtieneAtributosRol extends StoredProcedure {
	
		protected ObtieneAtributosRol(DataSource dataSource) {
			super(dataSource, "PKG_LISTAS.P_GET_ATRIBUTOS_ROL");
			declareParameter(new SqlParameter("pv_cdramo_i",   OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdrol_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdatribu_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneAtributosRolMapper()));
			declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class ObtieneAtributosRolMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new GenericVO(rs.getString("CODIGO"),rs.getString("DESCRIPCION"));
		}
	}


	@Override
	public List<GenericVO> obtieneRolesSistema() throws DaoException {
		Map<String, Object> resultado = ejecutaSP(new ObtieneRolesSistema(getDataSource()), new HashMap<String,Object>());
		return (List<GenericVO>) resultado.get("PV_REGISTRO_O");
	}
	
	protected class ObtieneRolesSistema extends StoredProcedure {
		protected ObtieneRolesSistema(DataSource dataSource) {
			super(dataSource, "PKG_GENERA_USUARIO.P_BUSCA_ROL");
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
	public List<GenericVO> obtieneAgentes(String claveONombre) throws DaoException {
		try {
			HashMap<String,Object> params =  new HashMap<String, Object>();
			params.put("pv_nombre_i", claveONombre);
    		
    		Map<String, Object> resultado = ejecutaSP(new ObtieneAgentes(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}
	
	protected class ObtieneAgentes extends StoredProcedure
	{
		protected ObtieneAgentes(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_OBTIENE_AGENTES");
			declareParameter(new SqlParameter("pv_nombre_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtenerAgentesMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class ObtenerAgentesMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
			GenericVO generic=new GenericVO();
            generic.setKey(rs.getString("CDAGENTE"));
            generic.setValue(rs.getString("Nombre_agente"));
            return generic;
		}
	}
	
	@Override
	public List<GenericVO> obtieneStatusTramite(Map<String,String> params) throws Exception
	{
		Map<String, Object> resultado = ejecutaSP(new ObtieneStatusTramite(getDataSource()), params);
		return (List<GenericVO>) resultado.get("pv_registro_o");
	}
	
	protected class ObtieneStatusTramite extends StoredProcedure
	{
    	protected ObtieneStatusTramite(DataSource dataSource)
    	{
            super(dataSource,"PKG_CONSULTA.P_GET_STATUS_TRAMITE");
            declareParameter(new SqlParameter(   "pv_cdtiptra_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtenerTmanteniMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }

	
	@Override
	public String obtieneCantidadMaxima(String cdramo, String cdtipsit, TipoTramite tipoTramite, Rango rango) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_cdtipsit_i", cdtipsit);
		params.put("pv_cdtiptra_i", tipoTramite.getCdtiptra());
		params.put("pv_tipocant_i", rango.getClave());
		
		Map<String, Object> resultado = ejecutaSP(new ObtieneCantidadMaxima(getDataSource()), params);
		return (String) resultado.get("pv_cantidad_o");
	}
	
    protected class ObtieneCantidadMaxima extends StoredProcedure {
    	
    	protected ObtieneCantidadMaxima(DataSource dataSource) {
    		
    		super(dataSource, "PKG_CONSULTA.P_GET_EDAD_MAXIMA");
    		declareParameter(new SqlParameter("pv_cdramo_i",   OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtiptra_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipocant_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_cantidad_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    }
	
}