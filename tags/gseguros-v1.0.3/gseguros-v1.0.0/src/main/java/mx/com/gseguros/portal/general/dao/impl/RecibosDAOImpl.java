package mx.com.gseguros.portal.general.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.general.dao.RecibosDAO;
import mx.com.gseguros.portal.general.model.DetalleReciboVO;
import mx.com.gseguros.portal.general.model.ReciboVO;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class RecibosDAOImpl extends AbstractManagerDAO implements RecibosDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<ReciboVO> obtieneRecibos(Map<String, Object> params) throws DaoException {
		Map<String, Object> result = ejecutaSP(new ObtieneRecibos(getDataSource()), params);
		return (List<ReciboVO>) result.get("pv_registro_o");
	}
	
	protected class ObtieneRecibos extends StoredProcedure {
    	protected ObtieneRecibos(DataSource dataSource) {
    		super(dataSource, "Pkg_Consulta.P_OBTIENE_RECIBOS");
    		declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new RecibosMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    }
	
	protected class RecibosMapper implements RowMapper<ReciboVO> {
		@Override
		public ReciboVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ReciboVO recibo = new ReciboVO();
			recibo.setNmrecibo(rs.getString("NMRECIBO"));
			recibo.setFeinicio(rs.getString("FEINICIO"));
			recibo.setFefinal( rs.getString("FEFINAL"));
			recibo.setFeemisio(rs.getString("FEEMISIO"));
			recibo.setCdestado(rs.getString("CDESTADO"));
			recibo.setPtimport(rs.getString("PTIMPORT"));
			recibo.setDsestado(rs.getString("DSESTADO"));
			recibo.setTiporeci(rs.getString("TIPORECI"));
			recibo.setDstipore(rs.getString("DSTIPORE"));
			return recibo;
		}
	}

	
	@Override
	public List<DetalleReciboVO> obtieneDetalleRecibo(Map<String, Object> params) throws DaoException {
		Map<String, Object> result = ejecutaSP(new ConsultaDetalleRecibo(getDataSource()), params); 
		return (List<DetalleReciboVO>) result.get("pv_RegDatos_o");
	}
	
	protected class ConsultaDetalleRecibo extends StoredProcedure {
		protected ConsultaDetalleRecibo(DataSource dataSource) {
			super(dataSource, "Pkg_Consulta.P_OBTIENE_MRECIDET");
    		declareParameter(new SqlParameter("pv_cdUnieco_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdRamo_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_Estado_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_NmPoliza_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmRecibo_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_RegDatos_o", OracleTypes.CURSOR, new DetalleReciboMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
		}
	}
	
	protected class DetalleReciboMapper implements RowMapper<DetalleReciboVO> {
		@Override
		public DetalleReciboVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			DetalleReciboVO detRecibo = new DetalleReciboVO();
			detRecibo.setCdtipcon(rs.getString("CdTipCon"));
			detRecibo.setDstipcon(rs.getString("DsTipCon"));
			detRecibo.setPtimport(rs.getString("ptimport")); 
			return detRecibo;
		}
	}
	
	

}