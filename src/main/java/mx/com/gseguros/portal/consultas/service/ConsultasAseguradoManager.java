package mx.com.gseguros.portal.consultas.service;

import java.util.List;

import mx.com.gseguros.portal.consultas.model.AseguradoDetalleVO;
import mx.com.gseguros.portal.consultas.model.AseguradoVO;
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
import mx.com.gseguros.portal.general.model.PolizaVO;

public interface ConsultasAseguradoManager {
	
	public List<ConsultaResultadosAseguradoVO> obtieneResultadosAsegurado(String rfc, String cdperson, String nombre) throws Exception;

	public List<ConsultaPolizaActualVO> obtienePolizaActual(PolizaAseguradoVO polizaAsegurado) throws Exception;
	
	public List<ConsultaDatosComplementariosVO> obtieneDatosComplementarios(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
	
	public List<ConsultaDatosGeneralesPolizaVO> obtieneDatosPoliza(PolizaAseguradoVO polizaAsegurado) throws Exception;
	
	public List<AseguradoDetalleVO> obtieneAseguradoDetalle(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
	
	public List<ConsultaDatosTitularVO> obtieneDatosTitular(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
	
	public List<ContratanteVO> obtieneDatosContratante(PolizaVO poliza) throws Exception;
	
	public List<AseguradoVO> obtieneAsegurados(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
	
	public List<EndosoVO> obtieneEndososPoliza(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
	
	public List<EnfermedadVO> obtieneEnfermedades(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
	
	public List<PlanVO> obtieneDatosPlan(PolizaVO poliza) throws Exception;
	
	public List<CopagoVO> obtieneCopagosPoliza(PolizaVO poliza) throws Exception;
	
	public List<CoberturaBasicaVO> obtieneCoberturasPoliza(PolizaVO poliza) throws Exception;
	
	public List<CoberturaBasicaVO> obtieneCoberturasBasicas(PolizaVO poliza) throws Exception;
	
	public List<HistoricoVO> obtieneHistoricoAsegurado(PolizaAseguradoVO polizaAsegurado, AseguradoVO asegurado) throws Exception;
	
	public List<HistoricoFarmaciaVO> obtieneHistoricoFarmacia(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
	
	public List<PeriodoVigenciaVO> obtienePeriodosVigencia(PolizaVO poliza, AseguradoVO asegurado) throws Exception;	
	
}
