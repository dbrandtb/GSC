package mx.com.gseguros.portal.consultas.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mx.com.gseguros.portal.consultas.dao.IConsultasAseguradoDAO;
import mx.com.gseguros.portal.consultas.dao.IConsultasPolizaDAO;
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
import mx.com.gseguros.portal.consultas.service.ConsultasAseguradoManager;
import mx.com.gseguros.portal.general.model.PolizaVO;



@Service("consultasAseguradoManagerImpl")
public class ConsultasAseguradoManagerImpl implements ConsultasAseguradoManager {

	private org.apache.log4j.Logger logger =org.apache.log4j.Logger.getLogger(ConsultasAseguradoManagerImpl.class);
	
	@Autowired
	@Qualifier("consultasAseguradoDAOICEImpl")
	private IConsultasAseguradoDAO consultasAseguradoDAOICE;
	
	@Autowired
	@Qualifier("consultasAseguradoDAOSISAImpl")
	private IConsultasAseguradoDAO consultasAseguradoDAOSISA;
		
	@Override
	public List<ConsultaResultadosAseguradoVO> obtieneResultadosAsegurado(String rfc,
			String cdperson, String nombre) throws Exception {
		List<ConsultaResultadosAseguradoVO> aseguradosSISA;
		aseguradosSISA = new ArrayList<ConsultaResultadosAseguradoVO>();
		aseguradosSISA = consultasAseguradoDAOSISA.obtieneResultadosAsegurado(rfc, cdperson, nombre);
		
		List<ConsultaResultadosAseguradoVO> aseguradosICE;
		aseguradosICE = new ArrayList<ConsultaResultadosAseguradoVO>();
		aseguradosICE = consultasAseguradoDAOICE.obtieneResultadosAsegurado(rfc, cdperson, nombre);
		
		//Fusionamos los asegurados
		List<ConsultaResultadosAseguradoVO> aseguradosFusionados;
		aseguradosFusionados = new ArrayList<ConsultaResultadosAseguradoVO>();
		
		if(aseguradosICE != null){
			if(aseguradosICE.size() > 0){				
				aseguradosFusionados.addAll(aseguradosICE);
			}
		}
		
		if(aseguradosSISA != null){
			if(aseguradosSISA.size() > 0){
				aseguradosFusionados.addAll(aseguradosSISA);
			}
		}
		
		return aseguradosFusionados;
	}
	
	@Override
	public List<ConsultaPolizaActualVO> obtienePolizaActual(
			ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception {
		
		List<ConsultaPolizaActualVO> polizaActual;
		polizaActual = new ArrayList<ConsultaPolizaActualVO>();
		
		// Si iCodPoliza viene vacio, es información de ICE, sino es de SISA:
		if(StringUtils.isBlank(polizaAsegurado.getIcodpoliza())){
			polizaActual = consultasAseguradoDAOICE.obtienePolizaActual(polizaAsegurado);
		} else {
			polizaActual = consultasAseguradoDAOSISA.obtienePolizaActual(polizaAsegurado);
		}
				
		return polizaActual;
	}
	
	@Override
	public List<ConsultaDatosComplementariosVO> obtieneDatosComplementarios(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		List<ConsultaDatosComplementariosVO> datosComplementarios = new ArrayList<ConsultaDatosComplementariosVO>();
		
		//Si iCodPoliza no es nulo, es información de SISA.
		 if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){		
			 datosComplementarios = consultasAseguradoDAOSISA.obtieneDatosComplementarios(poliza, asegurado);
		 } else {
			 datosComplementarios = consultasAseguradoDAOICE.obtieneDatosComplementarios(poliza, asegurado);
		 }
		 return datosComplementarios;
		
	}
	
	@Override
	public List<ConsultaDatosGeneralesPolizaVO> obtieneDatosPoliza(
			ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception {
		 List<ConsultaDatosGeneralesPolizaVO> datosPolizas = new ArrayList<ConsultaDatosGeneralesPolizaVO>();
		 
		//Si iCodPoliza no es nulo, es información de SISA.
		 if(StringUtils.isBlank(polizaAsegurado.getIcodpoliza()) == false){		
			 datosPolizas = consultasAseguradoDAOSISA.obtieneDatosPoliza(polizaAsegurado);
		 } else {
			 datosPolizas = consultasAseguradoDAOICE.obtieneDatosPoliza(polizaAsegurado);
		 }
				 
		 return datosPolizas;
	}
	
	@Override
	public List<AseguradoDetalleVO> obtieneAseguradoDetalle(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		List<AseguradoDetalleVO> datosAseguradoDetalle = new ArrayList<AseguradoDetalleVO>();
		//Si iCodPoliza no es nulo, es información de SISA.
		if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){
			datosAseguradoDetalle = consultasAseguradoDAOSISA.obtieneAseguradoDetalle(poliza,asegurado);
		} else {
			datosAseguradoDetalle = consultasAseguradoDAOICE.obtieneAseguradoDetalle(poliza,asegurado);
		}
		return datosAseguradoDetalle;
	}
	
	@Override
	public List<ConsultaDatosTitularVO> obtieneDatosTitular(PolizaVO poliza, AseguradoVO asegurado) throws Exception {
		List<ConsultaDatosTitularVO> datosTitular = new ArrayList<ConsultaDatosTitularVO>();
		
		//Si iCodPoliza no es nulo, es información de SISA.
		 if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){		
			 datosTitular = consultasAseguradoDAOSISA.obtieneDatosTitular(poliza, asegurado);
		 } else {
			 datosTitular = consultasAseguradoDAOICE.obtieneDatosTitular(poliza, asegurado);
		 }
		 return datosTitular;
		
	}
		
	@Override
	public List<ConsultaDatosContratanteVO> obtieneDatosContratante(PolizaVO poliza) throws Exception {
		List<ConsultaDatosContratanteVO> datosContratante = new ArrayList<ConsultaDatosContratanteVO>();
		
		//Si iCodPoliza no es nulo, es información de SISA.
		 if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){		
			 datosContratante = consultasAseguradoDAOSISA.obtieneDatosContratante(poliza);
		 } else {
			 datosContratante = consultasAseguradoDAOICE.obtieneDatosContratante(poliza);
		 }
		 return datosContratante;
		
	}
	
	@Override
	public List<AseguradoVO> obtieneAsegurados(PolizaVO poliza, AseguradoVO asegurado) throws Exception {
		
		List<AseguradoVO> asegurados = new ArrayList<AseguradoVO>();
		
		//Si iCodPoliza no es nulo, es información de SISA.
		 if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){		
			 asegurados = consultasAseguradoDAOSISA.obtieneAsegurados(poliza, asegurado);
		 } else {
			 asegurados = consultasAseguradoDAOICE.obtieneAsegurados(poliza, asegurado);
		 }
		
		return asegurados;
	}
	
	@Override
	public List<EndosoVO> obtieneEndososPoliza(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		List<EndosoVO> endosos = new ArrayList<EndosoVO>();
		//Si iCodPoliza no es nulo, es información de SISA.
		 if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){		
			 endosos = consultasAseguradoDAOSISA.obtieneEndososPoliza(poliza, asegurado);
		 } else {
			 endosos = consultasAseguradoDAOICE.obtieneEndososPoliza(poliza, asegurado);
		 }
		return endosos;
	}
	
	@Override
	public List<EnfermedadVO> obtieneEnfermedades(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		List<EnfermedadVO> enfermedades = new ArrayList<EnfermedadVO>();
		//Si iCodPoliza no es nulo, es información de SISA.
		 if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){		
			 enfermedades = consultasAseguradoDAOSISA.obtieneEnfermedades(poliza, asegurado);
		 } 
		 /*Por el momento no se registran las enfermedades crónicas en ICE.
		 else {
			 enfermedades = consultasAseguradoDAOICE.obtieneEnfermedades(poliza, asegurado);
		 }*/
		return enfermedades;
	}

	@Override
	public List<ConsultaDatosPlanVO> obtieneDatosPlan(PolizaVO poliza) throws Exception {
		List<ConsultaDatosPlanVO> datosPlan = new ArrayList<ConsultaDatosPlanVO>();
		
		//Si iCodPoliza no es nulo, es información de SISA.
		 if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){		
			 datosPlan = consultasAseguradoDAOSISA.obtieneDatosPlan(poliza);
		 } else {
			 datosPlan = consultasAseguradoDAOICE.obtieneDatosPlan(poliza);
		 }
		 return datosPlan;
		
	}
	
	@Override
	public List<CopagoVO> obtieneCopagosPoliza(PolizaVO poliza) throws Exception {
		
		List<CopagoVO> copagos = new ArrayList<CopagoVO>();
		//Si iCodPoliza no es nulo, es información de SISA.
		 if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){		
			copagos = consultasAseguradoDAOSISA.obtieneCopagosPoliza(poliza);
		 } else {
			copagos = consultasAseguradoDAOICE.obtieneCopagosPoliza(poliza);
			/*						
			// Agregamos un campo que agrupe los resultados:
			String agrupador = null;
			Iterator<CopagoVO> itCopagos = copagos.iterator();
			while (itCopagos.hasNext()) {
				CopagoVO copagoVO = itCopagos.next();
				// Si el copago tiene Nivel Padre se asigna como agrupador:
				if(copagoVO.getNivel() == 1) {
					agrupador = copagoVO.getDescripcion();
				}
				// Si el copago no es visible o no hay descripcion, lo eliminamos:
				if(!copagoVO.isVisible() || StringUtils.isBlank(copagoVO.getDescripcion())) {
					itCopagos.remove();
				}
				copagoVO.setAgrupador(agrupador);
			}
			*/
		 }
		
		return copagos;
	}
	
	@Override
	public List<CoberturasBasicasVO> obtieneCoberturasPoliza(PolizaVO poliza) throws Exception {
		
		List<CoberturasBasicasVO> coberturasPoliza = new ArrayList<CoberturasBasicasVO>();
		//Si iCodPoliza no es nulo, es información de SISA.
		if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){
			coberturasPoliza = consultasAseguradoDAOSISA.obtieneCoberturasPoliza(poliza);
		} else {
			coberturasPoliza = consultasAseguradoDAOICE.obtieneCoberturasPoliza(poliza);
		}
		return coberturasPoliza;
	}
	
	@Override
	public List<CoberturasBasicasVO> obtieneCoberturasBasicas(PolizaVO poliza) throws Exception {
		
		List<CoberturasBasicasVO> coberturasBasicas = new ArrayList<CoberturasBasicasVO>();
		//Si iCodPoliza no es nulo, es información de SISA.
		if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){		
			coberturasBasicas = consultasAseguradoDAOSISA.obtieneCoberturasBasicas(poliza);
		}
		return coberturasBasicas;
	}
	
	@Override
	public List<ConsultaDatosHistoricoVO> obtieneHistoricoAsegurado(
			ConsultaPolizaAseguradoVO polizaAsegurado, AseguradoVO asegurado) throws Exception {
		
		List<ConsultaDatosHistoricoVO> historico = new ArrayList<ConsultaDatosHistoricoVO>();  
		
		//Si iCodPoliza es nulo, es información de ICE, sino es de SISA:
		if(StringUtils.isBlank(polizaAsegurado.getIcodpoliza()) == false){			
			historico = consultasAseguradoDAOSISA.obtieneHistoricoAsegurado(polizaAsegurado, asegurado);
		} else {
			historico = consultasAseguradoDAOICE.obtieneHistoricoAsegurado(polizaAsegurado, asegurado);
		}
				
		return historico;
	}
	
	@Override
	public List<HistoricoFarmaciaVO> obtieneHistoricoFarmacia(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		List<HistoricoFarmaciaVO> historicoFarmacia = new ArrayList<HistoricoFarmaciaVO>();
		//Si iCodPoliza es nulo, es información de ICE, sino es de SISA:
		if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){			  
			  historicoFarmacia = consultasAseguradoDAOSISA.obtieneHistoricoFarmacia(poliza, asegurado);
		}
		  
		return historicoFarmacia;
	}
	
	@Override
	public List<ConsultaPeriodosVigenciaVO> obtienePeriodosVigencia(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		List<ConsultaPeriodosVigenciaVO> periodosVigencia = new ArrayList<ConsultaPeriodosVigenciaVO>();
		// Si iCodPoliza viene vacio, es información de ICE:
		if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){			  
			  periodosVigencia = consultasAseguradoDAOSISA.obtienePeriodosVigencia(poliza, asegurado);
		}
		  
	  return periodosVigencia;
	}

	
}
