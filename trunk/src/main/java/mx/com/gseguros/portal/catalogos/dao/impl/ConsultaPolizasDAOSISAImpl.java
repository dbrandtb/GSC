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
	public List<ConsultaPolizaAseguradoVO> obtienePolizasAsegurado(String rfc, String cdperson, String nombre) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdrfc_i", rfc);
		params.put("pv_cdperson_i", cdperson);
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
    		//Recupera el iCodPoliza de SISA
    		polizaAsegurado.setIcodpoliza(rs.getString("icodpoliza"));
    		return polizaAsegurado;
    	}
    }
        
    @Override
	public List<ConsultaDatosSuplementoVO> obtieneHistoricoPoliza(String nmpoliex, String icodpoliza) throws Exception {    	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliex_i", icodpoliza);
		Map<String, Object> mapResult = ejecutaSP(new ConsultaHistoricoPolizaSP(getDataSource()), params);
		return (List<ConsultaDatosSuplementoVO>) mapResult.get("rs");
	}
    
    @Override
	public List<ConsultaDatosSuplementoVO> obtieneHistoricoPoliza(ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliex_i", polizaAsegurado.getIcodpoliza());
		Map<String, Object> mapResult = ejecutaSP(new ConsultaHistoricoPolizaSP(getDataSource()), params);
    	return (List<ConsultaDatosSuplementoVO>) mapResult.get("rs");
	}
    
    protected class ConsultaHistoricoPolizaSP extends StoredProcedure{
    	protected ConsultaHistoricoPolizaSP(DataSource dataSource){
    		super(dataSource, "P_Get_Datos_Suplem");
    		declareParameter(new SqlParameter("pv_nmpoliex_i", Types.INTEGER));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", Types.INTEGER));
    		declareParameter(new SqlOutParameter("pv_title_o", Types.VARCHAR));
    		declareParameter(new SqlReturnResultSet("rs", new DatosSuplementoMapper()));
    		compile();
    	}
    }
    
    protected class DatosSuplementoMapper implements RowMapper<ConsultaDatosSuplementoVO>{
    	public ConsultaDatosSuplementoVO mapRow(ResultSet rs, int rowNum) throws SQLException{
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
    
    @Override
	public List<ConsultaDatosPolizaVO> obtieneDatosPoliza(String cdunieco, String cdramo, String estado, String nmpoliza, String icodpoliza)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliza_i", icodpoliza);
		Map<String, Object> mapResult = ejecutaSP(new ConsultaDatosPolizaSP(getDataSource()), params);
		return (List<ConsultaDatosPolizaVO>) mapResult.get("rs");		
	}
    
    @Override
	public List<ConsultaDatosPolizaVO> obtieneDatosPoliza(ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliza_i", polizaAsegurado.getIcodpoliza());
		Map<String, Object> mapResult = ejecutaSP(new ConsultaDatosPolizaSP(getDataSource()), params);
		return (List<ConsultaDatosPolizaVO>) mapResult.get("rs");
	}
    
    public class ConsultaDatosPolizaSP extends StoredProcedure{
    	protected ConsultaDatosPolizaSP(DataSource dataSource){
    		super(dataSource, "P_Get_Datos_Poliza");
    		declareParameter(new SqlParameter("pv_nmpoliza_i", Types.INTEGER));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", Types.INTEGER));
    		declareParameter(new SqlOutParameter("pv_title_o", Types.VARCHAR));
    		declareParameter(new SqlReturnResultSet("rs", new DatosPolizaMapper()));
    		compile();
    	}
    }
    
    protected class DatosPolizaMapper implements RowMapper<ConsultaDatosPolizaVO>{
    	public ConsultaDatosPolizaVO mapRow(ResultSet rs, int rowNum) throws SQLException{
    		ConsultaDatosPolizaVO datosPoliza = new ConsultaDatosPolizaVO();
    		datosPoliza.setNmpoliex(rs.getString("nmpoliex"));
			datosPoliza.setNmsolici(rs.getString("nmsolici"));
			datosPoliza.setFeemisio(rs.getString("feemisio"));
			datosPoliza.setFeefecto(rs.getString("feefecto"));
			datosPoliza.setCdmoneda(rs.getString("cdmoneda"));
			datosPoliza.setDsmoneda(rs.getString("dsmoneda"));
			datosPoliza.setOttempot(rs.getString("ottempot"));
			datosPoliza.setDstempot(rs.getString("dstempot"));
			datosPoliza.setFeproren(rs.getString("feproren"));
			datosPoliza.setFevencim(rs.getString("fevencim"));
			datosPoliza.setNmrenova(rs.getString("nmrenova"));
			datosPoliza.setCdperpag(rs.getString("cdperpag"));
			datosPoliza.setDsperpag(rs.getString("dsperpag"));
			datosPoliza.setSwtarifi(rs.getString("swtarifi"));
			datosPoliza.setDstarifi(rs.getString("dstarifi"));
			datosPoliza.setCdtipcoa(rs.getString("cdtipcoa"));
			datosPoliza.setDstipcoa(rs.getString("dstipcoa"));
			datosPoliza.setNmcuadro(rs.getString("nmcuadro"));
			datosPoliza.setPorredau(rs.getString("porredau"));
			datosPoliza.setPtpritot(rs.getString("ptpritot"));
			datosPoliza.setCdmotanu(rs.getString("cdmotanu"));
			datosPoliza.setDsmotanu(rs.getString("dsmotanu"));
			datosPoliza.setCdperson(rs.getString("cdperson"));
			datosPoliza.setTitular(rs.getString("titular"));
			datosPoliza.setCdrfc(rs.getString("cdrfc"));
			datosPoliza.setCdagente(rs.getString("cdagente"));
			datosPoliza.setStatuspoliza(rs.getString("status_poliza"));
			datosPoliza.setCdplan(rs.getString("cdplan"));
			datosPoliza.setDsplan(rs.getString("dsplan"));
			datosPoliza.setCdramo(rs.getString("cdramo"));
			datosPoliza.setDsramo(rs.getString("dsramo"));
			datosPoliza.setCdtipsit(rs.getString("cdtipsit"));
			datosPoliza.setCdunieco(rs.getString("cdunieco"));
			datosPoliza.setDsunieco(rs.getString("dsunieco"));
			datosPoliza.setNmpolant(rs.getString("nmpolant"));
    		return datosPoliza;
    	}
    }
  
    
}