package mx.com.gseguros.portal.consultas.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;

public class ConsultaPolizasDAOSISAImpl extends AbstractManagerDAO implements
		IConsultasPolizaDAO {
	// Coincidencias del asegurado según criterios: RFC, código de asegurado y
	// nombre.
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsultaPolizaAseguradoVO> obtienePolizasAsegurado(String rfc,
			String cdperson, String nombre) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdrfc_i", rfc);
		params.put("pv_cdperson_i", cdperson);
		params.put("pv_nombre_i", nombre);
		Map<String, Object> mapResult = ejecutaSP(
				new ConsultaPolizasAseguradoSP(getDataSource()), params);
		return (List<ConsultaPolizaAseguradoVO>) mapResult.get("rs");
	}

	protected class ConsultaPolizasAseguradoSP extends StoredProcedure {
		protected ConsultaPolizasAseguradoSP(DataSource dataSource) {
			super(dataSource, "P_Get_Polizas_Asegurado");
			declareParameter(new SqlParameter("pv_cdrfc_i", Types.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson_i", Types.VARCHAR));
			declareParameter(new SqlParameter("pv_nombre_i", Types.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_title_o", Types.VARCHAR));
			declareParameter(new SqlReturnResultSet("rs",
					new PolizaAseguradoMapper()));
			compile();
		}
	}

	protected class PolizaAseguradoMapper implements
			RowMapper<ConsultaPolizaAseguradoVO> {
		public ConsultaPolizaAseguradoVO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			ConsultaPolizaAseguradoVO polizaAsegurado = new ConsultaPolizaAseguradoVO();
			polizaAsegurado.setCdramo(rs.getString("codigo_ramo"));
			polizaAsegurado.setCdunieco(rs.getString("compania"));
			polizaAsegurado.setDsramo(rs.getString("descripcion_ramo"));
			polizaAsegurado.setDsunieco(rs.getString("descripcion"));
			polizaAsegurado.setEstado(rs.getString("estado"));
			polizaAsegurado.setNmpoliex(rs.getString("nmpoliex"));
			polizaAsegurado.setNmpoliza(rs.getString("nmpoliza"));
			polizaAsegurado.setNombreAsegurado(rs.getString("nombre"));
			// Recupera el iCodPoliza de SISA
			polizaAsegurado.setIcodpoliza(rs.getString("icodpoliza"));
			//Recupera el Origen
			polizaAsegurado.setOrigen(rs.getString("origen"));
			return polizaAsegurado;
		}
	}

	// Histórico de la póliza seleccionada.
	@Override
	public List<ConsultaDatosSuplementoVO> obtieneHistoricoPoliza(
			ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliex_i", polizaAsegurado.getIcodpoliza());
		Map<String, Object> mapResult = ejecutaSP(
				new ConsultaHistoricoPolizaSP(getDataSource()), params);
		return (List<ConsultaDatosSuplementoVO>) mapResult.get("rs");
	}

	protected class ConsultaHistoricoPolizaSP extends StoredProcedure {
		protected ConsultaHistoricoPolizaSP(DataSource dataSource) {
			super(dataSource, "P_Get_Datos_Suplem");
			declareParameter(new SqlParameter("pv_nmpoliex_i", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_msg_id_o", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_title_o", Types.VARCHAR));
			declareParameter(new SqlReturnResultSet("rs",
					new DatosSuplementoMapper()));
			compile();
		}
	}

	protected class DatosSuplementoMapper implements
			RowMapper<ConsultaDatosSuplementoVO> {
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
			datosSuplemento.setIcodpoliza(rs.getString("icodpoliza"));
			return datosSuplemento;
		}
	}

	// Datos generales de la póliza.
	@Override
	public List<ConsultaDatosPolizaVO> obtieneDatosPoliza(
			ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliza_i", polizaAsegurado.getIcodpoliza());
		Map<String, Object> mapResult = ejecutaSP(new ConsultaDatosPolizaSP(
				getDataSource()), params);
		return (List<ConsultaDatosPolizaVO>) mapResult.get("rs");
	}

	public class ConsultaDatosPolizaSP extends StoredProcedure {
		protected ConsultaDatosPolizaSP(DataSource dataSource) {
			super(dataSource, "P_Get_Datos_Poliza");
			declareParameter(new SqlParameter("pv_nmpoliza_i", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_msg_id_o", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_title_o", Types.VARCHAR));
			declareParameter(new SqlReturnResultSet("rs",
					new DatosPolizaMapper()));
			compile();
		}
	}

	protected class DatosPolizaMapper implements
			RowMapper<ConsultaDatosPolizaVO> {
		public ConsultaDatosPolizaVO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
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
			datosPoliza.setDscuadro(rs.getString("dscuadro"));
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
			datosPoliza.setDstipsit(rs.getString("dstipsit"));
			datosPoliza.setIcodpoliza(rs.getString("icodpoliza"));
			return datosPoliza;
		}
	}

	// Copagos de la póliza.
	@Override
	public List<CopagoVO> obtieneCopagosPoliza(PolizaVO poliza)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliza_i", poliza.getIcodpoliza());
		Map<String, Object> mapResult = ejecutaSP(new ConsultaCopagosPolizaSP(
				getDataSource()), params);
		return (List<CopagoVO>) mapResult.get("rs");
	}

	public class ConsultaCopagosPolizaSP extends StoredProcedure {
		protected ConsultaCopagosPolizaSP(DataSource dataSource) {
			super(dataSource, "P_Get_Copagos");
			declareParameter(new SqlParameter("pv_nmpoliza_i", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_msg_id_o", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_title_o", Types.VARCHAR));
			declareParameter(new SqlReturnResultSet("rs",
					new CopagosPolizaMapper()));
			compile();
		}
	}

	public class CopagosPolizaMapper implements RowMapper<CopagoVO> {
		public CopagoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			CopagoVO copago = new CopagoVO();
			copago.setOrden(rs.getInt("ORDEN"));
			copago.setDescripcion(rs.getString("descripcion"));
			copago.setValor(rs.getString("valor"));
			copago.setAgrupador("");
			copago.setNivel(rs.getInt("NIVEL"));
			return copago;
		}
	}

	// Asegurados de la póliza.
	@Override
	public List<AseguradoVO> obtieneAsegurados(PolizaVO poliza)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliza_i", poliza.getIcodpoliza());
		Map<String, Object> mapResult = ejecutaSP(new ConsultaAseguradosSP(
				getDataSource()), params);
		return (List<AseguradoVO>) mapResult.get("rs");
	}

	public class ConsultaAseguradosSP extends StoredProcedure {
		protected ConsultaAseguradosSP(DataSource dataSource) {
			super(dataSource, "P_Get_Datos_Aseg");
			declareParameter(new SqlParameter("pv_nmpoliza_i", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_msg_id_o", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_title_o", Types.VARCHAR));
			declareParameter(new SqlReturnResultSet("rs",
					new AseguradosMapper()));
			compile();
		}
	}

	public class AseguradosMapper implements RowMapper<AseguradoVO> {
		public AseguradoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			AseguradoVO datosAsegurado = new AseguradoVO();
			datosAsegurado.setCdperson(rs.getString("cdperson"));
			datosAsegurado.setNmsituac(rs.getString("nmsituac"));
			datosAsegurado.setCdtipsit(rs.getString("cdtipsit"));
			datosAsegurado.setNombre(rs.getString("titular"));
			datosAsegurado.setCdrfc(rs.getString("cdrfc"));
			datosAsegurado.setCdrol(rs.getString("cdrol"));
			datosAsegurado.setSexo(rs.getString("Sexo"));
			datosAsegurado.setFenacimi(rs.getString("fenacimi"));
			datosAsegurado.setStatus(rs.getString("STATUS"));
			datosAsegurado.setParentesco(rs.getString("parentesco"));
			return datosAsegurado;
		}
	}

	// Endosos de exclusión.
	@Override
	public List<ClausulaVO> obtieneExclusionesPoliza(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliza_i", poliza.getIcodpoliza());
		params.put("pv_cdperson_i", asegurado.getCdperson());
		Map<String, Object> mapResult = ejecutaSP(
				new ConsultaExclusionesPolizaSP(getDataSource()), params);
		return (List<ClausulaVO>) mapResult.get("rs");
	}

	protected class ConsultaExclusionesPolizaSP extends StoredProcedure {
		protected ConsultaExclusionesPolizaSP(DataSource dataSource) {
			super(dataSource, "P_Obtiene_MPolicot");
			declareParameter(new SqlParameter("pv_nmpoliza_i", Types.INTEGER));
			declareParameter(new SqlParameter("pv_cdperson_i", Types.INTEGER));
			declareParameter(new SqlReturnResultSet("rs",
					new ExclusionesPolizaMapper()));
			compile();
		}
	}

	protected class ExclusionesPolizaMapper implements RowMapper<ClausulaVO> {
		public ClausulaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClausulaVO clausulaVO = new ClausulaVO();
			clausulaVO.setCdclausu(rs.getString("cdclausu"));
			clausulaVO.setDsclausu(rs.getString("dsclausu"));
			clausulaVO.setCdtipcla(rs.getString("cdtipcla"));
			clausulaVO.setStatus(rs.getString("status"));
			clausulaVO.setContenidoClausula(rs.getString("linea_general"));
			return clausulaVO;
		}
	}

	// Recibos de la póliza.
	@Override
	public List<ReciboVO> obtieneRecibosPoliza(PolizaVO poliza)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliza_i", poliza.getIcodpoliza());
		Map<String, Object> mapResult = ejecutaSP(new ConsultaRecibosPolizaSP(
				getDataSource()), params);
		return (List<ReciboVO>) mapResult.get("rs");
	}

	protected class ConsultaRecibosPolizaSP extends StoredProcedure {
		protected ConsultaRecibosPolizaSP(DataSource dataSource) {
			super(dataSource, "P_Obtiene_Recibos");
			declareParameter(new SqlParameter("pv_nmpoliza_i", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_msg_id_o", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_title_o", Types.VARCHAR));
			declareParameter(new SqlReturnResultSet("rs",
					new RecibosPolizaMapper()));
			compile();
		}
	}

	protected class RecibosPolizaMapper implements RowMapper<ReciboVO> {
		public ReciboVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ReciboVO reciboVO = new ReciboVO();
			reciboVO.setNmrecibo(rs.getString("nmrecibo"));
			reciboVO.setFeinicio(rs.getString("FEINICIO"));
			reciboVO.setFefinal(rs.getString("FEFINAL"));
			reciboVO.setFeemisio(rs.getString("FEEMISIO"));
			reciboVO.setCdestado(rs.getString("cdestado"));
			reciboVO.setDsestado(rs.getString("dsestado"));
			reciboVO.setPtimport(rs.getString("ptimport"));
			reciboVO.setTiporeci(rs.getString("TIPORECI"));
			reciboVO.setDstipore(rs.getString("DSTIPORE"));
			return reciboVO;
		}
	}

	// Detalle de los recibos de la póliza
	@Override
	public List<DetalleReciboVO> obtieneDetalleRecibo(PolizaVO poliza,
			ReciboVO recibo) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_NmPoliza_i", poliza.getIcodpoliza());
		params.put("pv_cdRecibo_i", recibo.getNmrecibo()); // Debería ser
															// icodrecibo
		Map<String, Object> mapResult = ejecutaSP(new ConsultaDetalleReciboSP(
				getDataSource()), params);
		return (List<DetalleReciboVO>) mapResult.get("rs");
	}

	protected class ConsultaDetalleReciboSP extends StoredProcedure {
		protected ConsultaDetalleReciboSP(DataSource dataSource) {
			super(dataSource, "P_Obtiene_MRECIDET");
			declareParameter(new SqlParameter("pv_NmPoliza_i", Types.INTEGER));
			declareParameter(new SqlParameter("pv_cdRecibo_i", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_msg_id_o", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_title_o", Types.VARCHAR));
			declareParameter(new SqlReturnResultSet("rs",
					new DetalleRecibosMapper()));
			compile();
		}
	}

	protected class DetalleRecibosMapper implements RowMapper<DetalleReciboVO> {
		public DetalleReciboVO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			DetalleReciboVO detalleReciboVO = new DetalleReciboVO();
			detalleReciboVO.setCdtipcon(rs.getString("CdTipCon"));
			detalleReciboVO.setDstipcon(rs.getString("DsTipCon"));
			detalleReciboVO.setPtimport(rs.getString("ptimport"));
			return detalleReciboVO;

		}
	}

	// Agentes de la póliza.
	@Override
	public List<AgentePolizaVO> obtieneAgentesPoliza(PolizaVO poliza)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliza_i", poliza.getIcodpoliza());
		Map<String, Object> mapResult = ejecutaSP(new ConsultaAgentesPolizaSP(
				getDataSource()), params);
		return (List<AgentePolizaVO>) mapResult.get("rs");
	}

	protected class ConsultaAgentesPolizaSP extends StoredProcedure {
		protected ConsultaAgentesPolizaSP(DataSource dataSource) {
			super(dataSource, "P_Get_Agente_Poliza");
			declareParameter(new SqlParameter("pv_nmpoliza_i", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_msg_id_o", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_title_o", Types.VARCHAR));
			declareParameter(new SqlReturnResultSet("rs",
					new AgentesPolizaMapper()));
			compile();
		}
	}

	protected class AgentesPolizaMapper implements RowMapper<AgentePolizaVO> {
		public AgentePolizaVO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			AgentePolizaVO agentePolizaVO = new AgentePolizaVO();
			agentePolizaVO.setCdagente(rs.getString("CDAGENTE"));
			agentePolizaVO.setNombre(rs.getString("nombre"));
			agentePolizaVO.setCdtipoAg(rs.getString("CDTIPOAG"));
			agentePolizaVO.setDescripl(rs.getString("DESCRIPL"));
			agentePolizaVO.setPorparti(rs.getString("PORPARTI"));
			agentePolizaVO.setPorredau(rs.getString("PORREDAU"));
			agentePolizaVO.setNmsuplem(rs.getString("NMSUPLEM"));
			agentePolizaVO.setNmcuadro(rs.getString("NMCUADRO"));
			agentePolizaVO.setCdsucurs(rs.getString("CDSUCURS"));
			return agentePolizaVO;
		}
	}

	// Recibos del Agente
	@Override
	public List<ConsultaReciboAgenteVO> obtieneRecibosAgente(PolizaVO poliza)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliza_i", poliza.getIcodpoliza());
		Map<String, Object> mapResult = ejecutaSP(new ConsultaRecibosAgenteSP(
				getDataSource()), params);
		return (List<ConsultaReciboAgenteVO>) mapResult.get("rs");
	}

	protected class ConsultaRecibosAgenteSP extends StoredProcedure {
		protected ConsultaRecibosAgenteSP(DataSource dataSource) {
			super(dataSource, "P_Get_Recibos_Agente");
			declareParameter(new SqlParameter("pv_nmpoliza_i", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_msg_id_o", Types.INTEGER));
			declareParameter(new SqlOutParameter("pv_title_o", Types.VARCHAR));
			declareParameter(new SqlReturnResultSet("rs",
					new RecibosAgenteMapper()));
			compile();
		}
	}

	protected class RecibosAgenteMapper implements
			RowMapper<ConsultaReciboAgenteVO> {
		public ConsultaReciboAgenteVO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			ConsultaReciboAgenteVO reciboAgenteVO = new ConsultaReciboAgenteVO();
			reciboAgenteVO.setNmrecibo(rs.getString("NMRECIBO"));
			reciboAgenteVO.setFeinicio(rs.getString("Fecha_inicio"));
			reciboAgenteVO.setFefin(rs.getString("Fecha_fin"));
			reciboAgenteVO.setDsgarant(rs.getString("DSGARANT"));
			reciboAgenteVO.setPtimport(rs.getString("PTIMPORT"));
			return reciboAgenteVO;
		}
	}

}