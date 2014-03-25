package mx.com.gseguros.portal.consultas.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.consultas.dao.ConsultasSiniestroDAO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSiniestrosVO;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.utils.Utilerias;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class ConsultasSiniestroDAOImpl extends AbstractManagerDAO implements ConsultasSiniestroDAO {

	@SuppressWarnings("unchecked")
	public List<ConsultaDatosSiniestrosVO> obtieneConsultaAseguradosPagoReembolso(String cdperson) throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdperson_i", cdperson);
		Map<String, Object> mapResult = ejecutaSP(new ConsultaListaAseguradosPagoReembolso(getDataSource()), params);
		return (List<ConsultaDatosSiniestrosVO>) mapResult.get("pv_registro_o");
	}
	
	protected class ConsultaListaAseguradosPagoReembolso extends StoredProcedure
	{
		protected ConsultaListaAseguradosPagoReembolso(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_GET_SIN_ASEG_RE");
			declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DatosListaAasegurado()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	protected class DatosListaAasegurado  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ConsultaDatosSiniestrosVO consulta = new ConsultaDatosSiniestrosVO();
        	//rs.getString("")
        	consulta.setNtramite(rs.getString("NTRAMITE"));
        	consulta.setNmautser(rs.getString("NMAUTSER"));
        	consulta.setNmsinies(rs.getString("NMSINIES"));
        	consulta.setFeocurre(Utilerias.formateaFecha(rs.getString("FEOCURRE")));
        	consulta.setFeapertu(Utilerias.formateaFecha(rs.getString("FEAPERTU")));
        	return consulta;
        }
    }



}