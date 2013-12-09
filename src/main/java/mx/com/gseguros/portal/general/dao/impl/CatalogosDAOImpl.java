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
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class CatalogosDAOImpl extends AbstractManagerDAO implements CatalogosDAO {

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
    

	
	
}