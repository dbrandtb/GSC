package mx.com.gseguros.portal.catalogos.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.consultas.dao.IConsultasPolizaDAO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSuplementoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaAseguradoVO;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;

public class ConsultaPolizasDAOSISAImpl extends AbstractManagerDAO implements IConsultasPolizaDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsultaDatosPolizaVO> obtieneDatosPoliza(String cdunieco, String cdramo, String estado, String nmpoliza) throws Exception {
    	return null;
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsultaPolizaAseguradoVO> obtienePolizasAsegurado(String rfc, String cdperson, String nombre) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdrfc_i", rfc);
		params.put("pv_cdperson_i", null);
		params.put("pv_nombre_i", nombre);
		Map<String, Object> mapResult = ejecutaSP(new ConsultaPolizasAseguradoSP(getDataSource()), params);
		return (List<ConsultaPolizaAseguradoVO>) mapResult.get("rs");
	}
	
	protected class ConsultaPolizasAseguradoSP extends StoredProcedure {
		protected ConsultaPolizasAseguradoSP(DataSource dataSource) {
			super(dataSource, "P_Get_Polizas_Asegurado");
    		declareParameter(new SqlParameter("pv_cdrfc_i", Types.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdperson_i", Types.NUMERIC));
    		declareParameter(new SqlParameter("pv_nombre_i", Types.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", Types.INTEGER));
    		declareParameter(new SqlOutParameter("pv_title_o", Types.VARCHAR));
    		declareParameter(new SqlReturnResultSet("rs", new PolizaAseguradoMapper()));
    		compile();
		}
	}
	
    protected class PolizaAseguradoMapper  implements RowMapper<ConsultaPolizaAseguradoVO> {
    	
    	public ConsultaPolizaAseguradoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
    		
    		ConsultaPolizaAseguradoVO polizaAsegurado = new ConsultaPolizaAseguradoVO();
    		polizaAsegurado.setCdramo(rs.getString("codigo_ramo"));
    		polizaAsegurado.setCdunieco(rs.getString("compania"));
    		polizaAsegurado.setDsramo(rs.getString("descripcion_ramo"));
    		polizaAsegurado.setDsunieco(rs.getString("descripcion"));
    		polizaAsegurado.setEstado(rs.getString("estado"));
    		polizaAsegurado.setNmpoliex(rs.getString("nmpoliex"));
    		polizaAsegurado.setNmpoliza(rs.getString("nmpoliza"));
    		polizaAsegurado.setNombreAsegurado(rs.getString("nombre"));
    		return polizaAsegurado;
    	}
    }

	@Override
	public List<ConsultaDatosSuplementoVO> obtieneHistoricoPoliza(
			String nmpoliex) throws Exception {		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliex_i", nmpoliex);
		Map<String, Object> mapResult = ejecutaSP(new ConsultaHistoricoPolizaSP(getDataSource()), params);
		return (List<ConsultaDatosSuplementoVO>) mapResult.get("rs");		
	}
		
	protected class ConsultaHistoricoPolizaSP extends StoredProcedure{
		protected  ConsultaHistoricoPolizaSP(DataSource dataSource){
			super(dataSource, "P_Get_Datos_Suplem");
			declareParameter(new SqlParameter("pv_nmpoliex_i", Types.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_title_o", Types.VARCHAR));
			declareParameter(new SqlReturnResultSet("rs", new HistoricoPolizaMapper()));
			compile();			
		}
	}
		
	protected class HistoricoPolizaMapper implements RowMapper<ConsultaDatosSuplementoVO>{

		public ConsultaDatosSuplementoVO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			ConsultaDatosSuplementoVO datosSuplemento = new ConsultaDatosSuplementoVO();

			datosSuplemento.setCdunieco(rs.getString("cdunieco"));
			datosSuplemento.setCdramo(rs.getString("cdramo"));
			datosSuplemento.setEstado(rs.getString("estado"));
			datosSuplemento.setNmpoliza(rs.getString("nmpoliza"));
			datosSuplemento.setNmsuplem(rs.getString("nmsuplem"));
			datosSuplemento.setFeinival(rs.getString("feinival"));
			datosSuplemento.setNsuplogi(rs.getString("nsuplogi"));
			datosSuplemento.setFeemisio(rs.getString("feemisio"));
			datosSuplemento.setNlogisus(rs.getString("nlogisus"));
			datosSuplemento.setDstipsup(rs.getString("dstipsup"));
			datosSuplemento.setPtpritot(rs.getString("ptpritot"));
    		return datosSuplemento;				
		}
		
	}
	


}