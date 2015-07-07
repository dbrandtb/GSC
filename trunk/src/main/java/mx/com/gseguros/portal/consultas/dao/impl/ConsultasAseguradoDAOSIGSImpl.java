package mx.com.gseguros.portal.consultas.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.consultas.dao.IConsultasAseguradoDAO;
import mx.com.gseguros.portal.consultas.model.AseguradoDetalleVO;
import mx.com.gseguros.portal.consultas.model.AseguradoVO;
import mx.com.gseguros.portal.consultas.model.AvisoHospitalizacionVO;
import mx.com.gseguros.portal.consultas.model.CoberturaBasicaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosComplementariosVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosGeneralesPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosTitularVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaActualVO;
import mx.com.gseguros.portal.consultas.model.ConsultaResultadosAseguradoVO;
import mx.com.gseguros.portal.consultas.model.ContratanteVO;
import mx.com.gseguros.portal.consultas.model.CopagoVO;
import mx.com.gseguros.portal.consultas.model.EndosoVO;
import mx.com.gseguros.portal.consultas.model.EnfermedadVO;
import mx.com.gseguros.portal.consultas.model.HistoricoFarmaciaVO;
import mx.com.gseguros.portal.consultas.model.HistoricoVO;
import mx.com.gseguros.portal.consultas.model.PeriodoVigenciaVO;
import mx.com.gseguros.portal.consultas.model.PlanVO;
import mx.com.gseguros.portal.consultas.model.PolizaAseguradoVO;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.general.model.BaseVO;
import mx.com.gseguros.portal.general.model.PolizaVO;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;


public class ConsultasAseguradoDAOSIGSImpl extends AbstractManagerDAO implements
		IConsultasAseguradoDAO {

	@Override
	public List<ConsultaResultadosAseguradoVO> obtieneResultadosAsegurado(
			String rfc, String cdperson, String nombre) throws Exception {
		return null;
	}

	@Override
	public List<ConsultaPolizaActualVO> obtienePolizaActual(
			PolizaAseguradoVO polizaAsegurado) throws Exception {
		return null;
	}

	@Override
	public List<ConsultaDatosComplementariosVO> obtieneDatosComplementarios(
			PolizaVO poliza, AseguradoVO asegurado) throws Exception {
		return null;
	}

	@Override
	public List<ConsultaDatosGeneralesPolizaVO> obtieneDatosPoliza(
			PolizaAseguradoVO polizaAsegurado) throws Exception {
		return null;
	}

	@Override
	public List<AseguradoDetalleVO> obtieneAseguradoDetalle(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		return null;
	}

	@Override
	public List<ConsultaDatosTitularVO> obtieneDatosTitular(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		return null;
	}

	@Override
	public List<ContratanteVO> obtieneDatosContratante(PolizaVO poliza)
			throws Exception {
		return null;
	}

	@Override
	public List<AseguradoVO> obtieneAsegurados(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		return null;
	}

	@Override
	public List<EndosoVO> obtieneEndososPoliza(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		return null;
	}

	@Override
	public List<EnfermedadVO> obtieneEnfermedades(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		return null;
	}

	@Override
	public List<PlanVO> obtieneDatosPlan(PolizaVO poliza) throws Exception {
		return null;
	}

	@Override
	public List<CopagoVO> obtieneCopagosPoliza(PolizaVO poliza)
			throws Exception {
		return null;
	}

	@Override
	public List<CoberturaBasicaVO> obtieneCoberturasPoliza(PolizaVO poliza)
			throws Exception {
		return null;
	}

	@Override
	public List<CoberturaBasicaVO> obtieneCoberturasBasicas(PolizaVO poliza)
			throws Exception {
		return null;
	}

	@Override
	public List<HistoricoVO> obtieneHistoricoAsegurado(
			PolizaAseguradoVO polizaAsegurado, AseguradoVO asegurado)
			throws Exception {
		return null;
	}

	@Override
	public List<HistoricoFarmaciaVO> obtieneHistoricoFarmacia(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		return null;
	}

	@Override
	public List<PeriodoVigenciaVO> obtienePeriodosVigencia(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		return null;
	}

	@Override
	public List<BaseVO> obtieneHospitales(String filtro) throws Exception {
		return null;
	}

	@Override
	public List<AvisoHospitalizacionVO> obtieneAvisosAnteriores(
			AseguradoVO asegurado) throws Exception {
		return null;
	}

	@Override
	public String enviarAvisoHospitalizacion(AvisoHospitalizacionVO aviso)
			throws Exception {
				return null;
	}

	@Override
	public String consultaTelefonoAgente(String cdagente) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cdagente", cdagente);
		Map<String, Object> mapResult = ejecutaSP(new consultaTelefonoAgenteSP(getDataSource()), params);
		return (String) mapResult.get("rs");
	}
	
	public class consultaTelefonoAgenteSP extends StoredProcedure{
		protected consultaTelefonoAgenteSP(DataSource dataSource){
			super(dataSource, "sp_obtener_telefono_agente");
			declareParameter(new SqlParameter("cdagente", Types.INTEGER));
//			declareParameter(new SqlOutParameter("rs", Types.VARCHAR));
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<String>(){  
				@Override  
				public String extractData(ResultSet rs) throws SQLException, DataAccessException {  
					String result = null;
					while(rs.next()){  
						result = rs.getString(1);
					}  
					return result;  
				}
			}));
			compile();
		}
	}

	@Override
	public void actualizaEstatusEnvio(String iCodAviso) throws Exception {
	}
	
}
