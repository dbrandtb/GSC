package mx.com.gseguros.portal.general.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.general.dao.ReportesDAO;
import mx.com.gseguros.portal.general.model.ParamReporteVO;
import mx.com.gseguros.portal.general.model.ReporteVO;
import mx.com.gseguros.utils.Constantes;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.support.lob.OracleLobHandler;

public class ReportesDAOImpl extends AbstractManagerDAO implements ReportesDAO {
	
	private static Logger logger = Logger.getLogger(ReportesDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public InputStream obtieneReporteExcel(HashMap<String,Object> params) throws DaoException{
		
		InputStream archivo =  null;
		try {
			Map<String, Object> resultado = ejecutaSP(new ObtieneReporteExcelSP(getDataSource()), params);
			ArrayList<InputStream> inputList = (ArrayList<InputStream>) resultado.get("pv_registro_o");
			archivo = inputList.get(0);
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
		
		return archivo;
	}
	
	protected class ObtieneReporteExcelSP extends StoredProcedure {
    	protected ObtieneReporteExcelSP(DataSource dataSource) {
            super(dataSource,"PKG_EXTRACC_EXCEL.P_CONS_EXTRACC_REP");
            declareParameter(new SqlParameter("pv_idreporte_i",   OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_codusr_i",      OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneReporteExcelMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o",   OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",    OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    protected class ObtieneReporteExcelMapper implements RowMapper<InputStream> {
        public InputStream mapRow(ResultSet rs, int rowNum) throws SQLException {
        	OracleLobHandler lobHandler = new OracleLobHandler();
            return new ByteArrayInputStream(lobHandler.getBlobAsBytes(rs, "data"));
        }
    }
    
    
	@SuppressWarnings("unchecked")
	@Override
	public List<ReporteVO> obtenerListaReportes() throws DaoException {
    	Map<String, Object> result = ejecutaSP(new ObtieneListaReportesSP(this.getDataSource()), new HashMap<String, Object>());
		return (List<ReporteVO>)result.get("pv_registro_o");
    }
    
    protected class ObtieneListaReportesSP extends StoredProcedure {
    	protected ObtieneListaReportesSP(DataSource dataSource) {
            super(dataSource,"PKG_TAEXTRACCION.GET_REPORTES");
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneListaReportesMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o",   OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",    OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    protected class ObtieneListaReportesMapper implements RowMapper<ReporteVO> {
        public ReporteVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ReporteVO reporte = new ReporteVO();
        	reporte.setCdReporte(rs.getString("CDREPORTE"));
        	reporte.setDsReporte(rs.getString("DSREPORTE"));
        	return reporte;
        }
    }
    
    
    @SuppressWarnings("unchecked")
	@Override
	public List<ParamReporteVO> obtenerParametrosReporte(Map<String, Object> params) throws DaoException {
    	Map<String, Object> result = ejecutaSP(new ObtieneParametrosReportesSP(this.getDataSource()), params);
		return (List<ParamReporteVO>)result.get("pv_registro_o");
    }
    
    protected class ObtieneParametrosReportesSP extends StoredProcedure {
    	protected ObtieneParametrosReportesSP(DataSource dataSource) {
            super(dataSource,"PKG_TAEXTRACCION.GET_PARAMETROS");
            declareParameter(new SqlParameter("pv_cdreporte_i", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneParametrosReportesMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o",   OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",    OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    protected class ObtieneParametrosReportesMapper implements RowMapper<ParamReporteVO> {
        public ParamReporteVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ParamReporteVO reporteParam = new ParamReporteVO();
        	reporteParam.setNombre(rs.getString("NOMPARAM"));
        	reporteParam.setDescripcion(rs.getString("DESCPARAM"));
        	reporteParam.setTipo(rs.getString("TIPOPARAM"));
        	reporteParam.setValor(rs.getString("VALINICIO"));
        	reporteParam.setObligatorio(rs.getString("SWOBLIGA").equals(Constantes.SI) ? true : false);
        	return reporteParam;
        }
    }
    
    
	@Override
	public Map<String, Object> actualizarParametroReporte(String cdreporte, String username, ParamReporteVO paramReporteVO) throws DaoException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdreporte_i", cdreporte);
		params.put("pv_nomparam_i", paramReporteVO.getNombre());
		params.put("pv_valor_i", paramReporteVO.getValor());
		params.put("pv_usuario_i", username);
		
    	return ejecutaSP(new ActualizaParametroReporteSP(this.getDataSource()), params);
    }
    
    protected class ActualizaParametroReporteSP extends StoredProcedure {
    	protected ActualizaParametroReporteSP(DataSource dataSource) {
            super(dataSource,"PKG_TAEXTRACCION.PUT_PARAMETROS");
            declareParameter(new SqlParameter("pv_cdreporte_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nomparam_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_valor_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_usuario_i", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o",   OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",    OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    
	@Override
	public void armarReporte(String cdreporte, String username) throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdreporte_i", cdreporte);
		params.put("pv_usuario_i", username);
    	ejecutaSP(new ArmaReporteSP(this.getDataSource()), params);
    }
    
    protected class ArmaReporteSP extends StoredProcedure {
    	protected ArmaReporteSP(DataSource dataSource) {
            super(dataSource,"PKG_TAEXTRACCION.ARMA_REPORTE");
            declareParameter(new SqlParameter("pv_cdreporte_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_usuario_i", OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    
    @SuppressWarnings("unchecked")
	@Override
	public InputStream obtenerReporte(String cdreporte, String username) throws DaoException {
    	
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdreporte_i", cdreporte);
		params.put("pv_usuario_i", username);
		
		logger.debug("params=" + params);
		
		InputStream archivo =  null;
		try {
			Map<String, Object> resultado = ejecutaSP(new ObtieneReporteSP(getDataSource()), params);
			logger.debug("resultado:"+resultado);
			ArrayList<InputStream> inputList = (ArrayList<InputStream>) resultado.get("pv_registro_o");
			archivo = inputList.get(0);
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
		
		return archivo;
    }
        
    protected class ObtieneReporteSP extends StoredProcedure {
    	protected ObtieneReporteSP(DataSource dataSource) {
            super(dataSource,"PKG_TAEXTRACCION.GET_SALIDA");
            declareParameter(new SqlParameter("pv_cdreporte_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_usuario_i",   OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneReporteMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o",   OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",    OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    protected class ObtieneReporteMapper implements RowMapper<InputStream> {
    	public InputStream mapRow(ResultSet rs, int rowNum) throws SQLException {
        	OracleLobHandler lobHandler = new OracleLobHandler();
            return new ByteArrayInputStream(lobHandler.getBlobAsBytes(rs, "DATA"));
        }
    }
    
}