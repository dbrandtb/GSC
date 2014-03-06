package mx.com.gseguros.portal.general.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javassist.expr.NewArray;

import javax.sql.DataSource;

import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.general.dao.ReportesDAO;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.support.lob.OracleLobHandler;

public class ReportesDAOImpl extends AbstractManagerDAO implements ReportesDAO {

	@Override
	public InputStream obtieneReporteExcel(HashMap<String,Object> params) throws DaoException{
		
		InputStream archivo =  null;
		try {
			Map<String, Object> resultado = ejecutaSP(new ObtieneReporteExcel(getDataSource()), params);
			ArrayList<InputStream> inputList = (ArrayList<InputStream>) resultado.get("pv_registro_o");
			archivo = inputList.get(0);
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
		
		return archivo;
	}
	
	protected class ObtieneReporteExcel extends StoredProcedure {
    	protected ObtieneReporteExcel(DataSource dataSource) {
            super(dataSource,"PKG_EXTRACC_EXCEL.P_CONS_EXTRACC_REP");
            declareParameter(new SqlParameter("pv_idreporte_i",   OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_codusr_i",      OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneReporteExcelMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o",   OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",    OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    protected class ObtieneReporteExcelMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	OracleLobHandler lobHandler = new OracleLobHandler();
            return new ByteArrayInputStream(lobHandler.getBlobAsBytes(rs, "data"));
        }
    }
    
}