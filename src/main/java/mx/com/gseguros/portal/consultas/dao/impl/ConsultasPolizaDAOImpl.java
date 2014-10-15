package mx.com.gseguros.portal.consultas.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.consultas.dao.IConsultasPolizaDAO;
import mx.com.gseguros.portal.consultas.model.AseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSuplementoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaReciboAgenteVO;
import mx.com.gseguros.portal.consultas.model.CopagoVO;
import mx.com.gseguros.portal.cotizacion.model.AgentePolizaVO;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.general.model.ClausulaVO;
import mx.com.gseguros.portal.general.model.DetalleReciboVO;
import mx.com.gseguros.portal.general.model.PolizaVO;
import mx.com.gseguros.portal.general.model.ReciboVO;
import mx.com.gseguros.utils.Utilerias;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class ConsultasPolizaDAOImpl extends AbstractManagerDAO implements IConsultasPolizaDAO {

	
	@SuppressWarnings("unchecked")
	//@Override
	public List<ConsultaDatosPolizaVO> obtieneDatosPoliza(String cdunieco, String cdramo, String estado, String nmpoliza, String icodPoliza) throws Exception {
    	
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		Map<String, Object> mapResult = ejecutaSP(new ConsultaPolizasAseguradoSP(getDataSource()), params);
		return (List<ConsultaDatosPolizaVO>) mapResult.get("pv_registro_o");
	}
	
	protected class ObtieneDatosPolizaSP extends StoredProcedure {

		protected ObtieneDatosPolizaSP(DataSource dataSource) {
			
			super(dataSource, "PKG_CONSULTA.P_Get_Datos_Poliza");
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosPolizaMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
    protected class DatosPolizaMapper  implements RowMapper<ConsultaDatosPolizaVO> {
    	
        public ConsultaDatosPolizaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        	
        	ConsultaDatosPolizaVO consulta = new ConsultaDatosPolizaVO();
        	consulta.setNmsolici(rs.getString("nmsolici"));
        	consulta.setFeefecto(Utilerias.formateaFecha(rs.getString("feefecto")));
        	consulta.setNmpoliex(rs.getString("nmpoliex"));
        	consulta.setFeemisio(Utilerias.formateaFecha(rs.getString("feemisio")));
        	consulta.setCdmoneda(rs.getString("cdmoneda"));
        	consulta.setDsmoneda(rs.getString("dsmoneda"));
        	consulta.setOttempot(rs.getString("ottempot"));
        	consulta.setDstempot(rs.getString("dstempot"));
        	consulta.setFeproren(Utilerias.formateaFecha(rs.getString("feproren")));
        	consulta.setFevencim(Utilerias.formateaFecha(rs.getString("fevencim")));
        	consulta.setNmrenova(rs.getString("nmrenova"));
        	consulta.setCdperpag(rs.getString("cdperpag"));
        	consulta.setDsperpag(rs.getString("dsperpag"));
        	consulta.setSwtarifi(rs.getString("swtarifi"));
        	consulta.setDstarifi(rs.getString("dstarifi"));
        	consulta.setCdtipcoa(rs.getString("cdtipcoa"));
        	consulta.setDstipcoa(rs.getString("dstipcoa"));
        	consulta.setNmcuadro(rs.getString("nmcuadro"));
        	consulta.setDscuadro(rs.getString("dscuadro"));
        	consulta.setPorredau(rs.getString("porredau"));
        	consulta.setPtpritot(rs.getString("ptpritot"));
        	consulta.setCdmotanu(rs.getString("cdmotanu"));
        	consulta.setDsmotanu(rs.getString("dsmotanu"));
        	consulta.setCdperson(rs.getString("cdperson"));
        	consulta.setTitular(rs.getString("titular"));
        	consulta.setCdrfc(rs.getString("cdrfc"));
        	consulta.setCdagente(rs.getString("cdagente"));
        	consulta.setStatuspoliza(rs.getString("status_poliza"));
        	consulta.setCdplan(rs.getString("cdplan"));
        	consulta.setCdtipsit(rs.getString("cdtipsit"));
        	consulta.setCdramo(rs.getString("cdramo"));
        	consulta.setCdunieco(rs.getString("cdunieco"));
        	consulta.setNmpolant(rs.getString("nmpolant"));
        	consulta.setDsunieco(rs.getString("dsunieco"));
        	consulta.setDsramo(rs.getString("dsramo"));
        	consulta.setDsplan(rs.getString("dsplan"));
        	consulta.setDstipsit(rs.getString("dstipsit"));
            return consulta;
        }
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsultaPolizaAseguradoVO> obtienePolizasAsegurado(String rfc, String cdperson, String nombre) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdrfc_i", rfc);
		params.put("pv_cdperson_i", cdperson);
		params.put("pv_nombre_i", nombre);
		Map<String, Object> mapResult = ejecutaSP(new ConsultaPolizasAseguradoSP(getDataSource()), params);
		return (List<ConsultaPolizaAseguradoVO>) mapResult.get("pv_registro_o");
	}
	
	protected class ConsultaPolizasAseguradoSP extends StoredProcedure {
		protected ConsultaPolizasAseguradoSP(DataSource dataSource) {
			super(dataSource, "PKG_CONSULTA.P_Get_Polizas_Asegurado");
    		declareParameter(new SqlParameter("pv_cdrfc_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nombre_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new PolizaAseguradoMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
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
    		polizaAsegurado.setIcodpoliza(null); // No utilizado para ICE
    		return polizaAsegurado;
    	}
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<ConsultaDatosSuplementoVO> obtieneHistoricoPoliza(ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliex_i", polizaAsegurado.getNmpoliex());
		Map<String, Object> mapResult = ejecutaSP(new ConsultaSuplementosSP(getDataSource()), params);
		return (List<ConsultaDatosSuplementoVO>) mapResult.get("pv_registro_o");
	}
	
	protected class ConsultaSuplementosSP extends StoredProcedure {
		protected ConsultaSuplementosSP(DataSource dataSource) {
			super(dataSource, "PKG_CONSULTA.p_get_datos_suplem");
			declareParameter(new SqlParameter("pv_nmpoliex_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosSuplementoMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
		}
	}
	
    protected class DatosSuplementoMapper  implements RowMapper<ConsultaDatosSuplementoVO> {
    	
    	public ConsultaDatosSuplementoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
    		ConsultaDatosSuplementoVO suplemento = new ConsultaDatosSuplementoVO();
    		suplemento.setCdunieco(rs.getString("cdunieco"));
    		suplemento.setCdramo(rs.getString("cdramo"));
    		suplemento.setEstado(rs.getString("estado"));
    		suplemento.setNmpoliza(rs.getString("nmpoliza"));
    		suplemento.setFeinival(Utilerias.formateaFecha(rs.getString("feinival")));
    		suplemento.setNsuplogi(rs.getString("nsuplogi"));
    		suplemento.setFeemisio(Utilerias.formateaFecha(rs.getString("feemisio")));
    		suplemento.setNlogisus(rs.getString("nlogisus"));
    		suplemento.setDstipsup(rs.getString("dstipsup"));
    		suplemento.setPtpritot(rs.getString("ptpritot"));
    		suplemento.setNmsuplem(rs.getString("nmsuplem"));
    		suplemento.setOrigen(rs.getString("origen"));
    		return suplemento;
    	}
    }
    

	@Override
	public List<ConsultaDatosPolizaVO> obtieneDatosPoliza(
			ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CopagoVO> obtieneCopagosPoliza(PolizaVO poliza)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AseguradoVO> obtieneAsegurados(PolizaVO poliza)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClausulaVO> obtieneExclusionesPoliza(PolizaVO poliza, AseguradoVO asegurado) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReciboVO> obtieneRecibosPoliza(PolizaVO poliza)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DetalleReciboVO> obtieneDetalleRecibo(PolizaVO poliza,
			ReciboVO recibo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AgentePolizaVO> obtieneAgentesPoliza(PolizaVO poliza)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsultaReciboAgenteVO> obtieneRecibosAgente(PolizaVO poliza)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}







}