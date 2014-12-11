package mx.com.gseguros.portal.consultas.service;

import java.util.List;

import mx.com.gseguros.portal.consultas.model.AseguradoDetalleVO;
import mx.com.gseguros.portal.consultas.model.AseguradoVO;
import mx.com.gseguros.portal.consultas.model.CoberturasBasicasVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosComplementariosVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosContratanteVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosGeneralesPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosHistoricoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosPlanVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosTitularVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPeriodosVigenciaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaActualVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaResultadosAseguradoVO;
import mx.com.gseguros.portal.consultas.model.CopagoVO;
import mx.com.gseguros.portal.consultas.model.EndosoVO;
import mx.com.gseguros.portal.consultas.model.EnfermedadVO;
import mx.com.gseguros.portal.consultas.model.HistoricoFarmaciaVO;
import mx.com.gseguros.portal.general.model.PolizaVO;

public interface ConsultasAseguradoManager {
	
	public List<ConsultaResultadosAseguradoVO> obtieneResultadosAsegurado(String rfc, String cdperson, String nombre) throws Exception;

	public List<ConsultaPolizaActualVO> obtienePolizaActual(ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception;
	
	public List<ConsultaDatosComplementariosVO> obtieneDatosComplementarios(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
	
	public List<ConsultaDatosGeneralesPolizaVO> obtieneDatosPoliza(ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception;
	
	public List<AseguradoDetalleVO> obtieneAseguradoDetalle(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
	
	public List<ConsultaDatosTitularVO> obtieneDatosTitular(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
	
	public List<ConsultaDatosContratanteVO> obtieneDatosContratante(PolizaVO poliza) throws Exception;
	
	public List<AseguradoVO> obtieneAsegurados(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
	
	public List<EndosoVO> obtieneEndososPoliza(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
	
	public List<EnfermedadVO> obtieneEnfermedades(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
	
	public List<ConsultaDatosPlanVO> obtieneDatosPlan(PolizaVO poliza) throws Exception;
	
	public List<CopagoVO> obtieneCopagosPoliza(PolizaVO poliza) throws Exception;
	
	public List<CoberturasBasicasVO> obtieneCoberturasPoliza(PolizaVO poliza) throws Exception;
	
	public List<CoberturasBasicasVO> obtieneCoberturasBasicas(PolizaVO poliza) throws Exception;
	
	public List<ConsultaDatosHistoricoVO> obtieneHistoricoAsegurado(ConsultaPolizaAseguradoVO polizaAsegurado, AseguradoVO asegurado) throws Exception;
	
	public List<HistoricoFarmaciaVO> obtieneHistoricoFarmacia(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
	
	public List<ConsultaPeriodosVigenciaVO> obtienePeriodosVigencia(PolizaVO poliza, AseguradoVO asegurado) throws Exception;	
	
}
