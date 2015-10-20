package mx.com.gseguros.portal.consultas.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.consultas.dao.ConsultasPolizaDAO;
import mx.com.gseguros.portal.consultas.model.AseguradoDetalleVO;
import mx.com.gseguros.portal.consultas.model.AseguradoVO;
import mx.com.gseguros.portal.consultas.model.CoberturaBasicaVO;
import mx.com.gseguros.portal.consultas.model.ContratanteVO;
import mx.com.gseguros.portal.consultas.model.CopagoVO;
import mx.com.gseguros.portal.consultas.model.DatosComplementariosVO;
import mx.com.gseguros.portal.consultas.model.HistoricoFarmaciaVO;
import mx.com.gseguros.portal.consultas.model.HistoricoVO;
import mx.com.gseguros.portal.consultas.model.PeriodoVigenciaVO;
import mx.com.gseguros.portal.consultas.model.PlanVO;
import mx.com.gseguros.portal.consultas.model.PolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.PolizaDTO;
import mx.com.gseguros.portal.consultas.model.ReciboAgenteVO;
import mx.com.gseguros.portal.consultas.model.SuplementoVO;
import mx.com.gseguros.portal.consultas.model.TarifaVO;
import mx.com.gseguros.portal.consultas.service.ConsultasPolizaManager;
import mx.com.gseguros.portal.cotizacion.model.AgentePolizaVO;
import mx.com.gseguros.portal.general.model.ClausulaVO;
import mx.com.gseguros.portal.general.model.DetalleReciboVO;
import mx.com.gseguros.portal.general.model.PolizaVO;
import mx.com.gseguros.portal.general.model.ReciboVO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ConsultasPolizaManagerImpl implements ConsultasPolizaManager {
	
	private org.apache.log4j.Logger logger =org.apache.log4j.Logger.getLogger(ConsultasPolizaManagerImpl.class);
	
	@Autowired
	@Qualifier("consultasDAOICEImpl")
	private ConsultasPolizaDAO consultasPolizaDAOICE;
	
//	@Autowired
//	@Qualifier("consultasDAOSISAImpl")
//	private ConsultasPolizaDAO consultasPolizaDAOSISA;
	
	@Override
	public List<PolizaAseguradoVO> obtienePolizasAsegPromotor(String user,
			String rfc, String cdperson, String nombre) throws Exception {
		
		List<PolizaAseguradoVO> polizasICE  = consultasPolizaDAOICE.obtienePolizasAsegPromotor(user, rfc, cdperson, nombre);
//		List<PolizaAseguradoVO> polizasSISA = consultasPolizaDAOSISA.obtienePolizasAsegurado(user, rfc, cdperson, nombre);
		
		// Fusionamos las listas:
        List<PolizaAseguradoVO> polizasFusionadas = polizasICE;
//        if(polizasFusionadas == null) {
//        	polizasFusionadas = new ArrayList<PolizaAseguradoVO>();
//        }
//        polizasFusionadas.addAll(polizasSISA);
        
		return polizasFusionadas;
	}
	
	@Override
	public List<PolizaAseguradoVO> obtienePolizasAsegurado(String user,
			String rfc, String cdperson, String nombre) throws Exception {
		
		List<PolizaAseguradoVO> polizasICE  = consultasPolizaDAOICE.obtienePolizasAsegurado(user, rfc, cdperson, nombre);
//		List<PolizaAseguradoVO> polizasSISA = consultasPolizaDAOSISA.obtienePolizasAsegurado(user, rfc, cdperson, nombre);
		
		// Fusionamos las listas:
        List<PolizaAseguradoVO> polizasFusionadas = polizasICE;
//        if(polizasFusionadas == null) {
//        	polizasFusionadas = new ArrayList<PolizaAseguradoVO>();
//        }
//        polizasFusionadas.addAll(polizasSISA);
        
		return polizasFusionadas;
	}
	
	@Override
	public List<SuplementoVO> obtieneHistoricoPoliza(
			PolizaAseguradoVO polizaAsegurado) throws Exception {
		
		List<SuplementoVO> suplementos;  
		
		// Si iCodPoliza viene vacio, es información de ICE, sino es de SISA:
//		if(StringUtils.isBlank(polizaAsegurado.getIcodpoliza())){
			suplementos = consultasPolizaDAOICE.obtieneHistoricoPoliza(polizaAsegurado);
//		} else {
//			suplementos = consultasPolizaDAOSISA.obtieneHistoricoPoliza(polizaAsegurado);
//		}
				
		return suplementos;
	}

	@Override
	public List<SuplementoVO> obtieneHistoricoPolizaCorto(String sucursal, String producto, String polizacorto) 
			throws Exception{
		
		List<SuplementoVO> suplementos;  
		
		// Si iCodPoliza viene vacio, es información de ICE, sino es de SISA:
//		if(StringUtils.isBlank(polizaAsegurado.getIcodpoliza())){
		suplementos = consultasPolizaDAOICE.obtieneHistoricoPolizaCorto(sucursal, producto, polizacorto);
//		} else {
//			suplementos = consultasPolizaDAOSISA.obtieneHistoricoPoliza(polizaAsegurado);
//		}
		
		return suplementos;
	}
	
	@Override
	public List<HistoricoVO> obtieneHistoricoPolizaSISA(
			PolizaAseguradoVO polizaAsegurado) throws Exception {
		
		List<HistoricoVO> historico = new ArrayList<HistoricoVO>();  
		
		//Si iCodPoliza es nulo, es información de ICE, sino es de SISA:
//		if(StringUtils.isBlank(polizaAsegurado.getIcodpoliza()) == false){			
//			historico = consultasPolizaDAOSISA.obtieneHistoricoPolizaSISA(polizaAsegurado);
//		}
				
		return historico;
	}

	@Override
	public List<PolizaDTO> obtieneDatosPoliza(
			PolizaAseguradoVO polizaAsegurado) throws Exception {
		 List<PolizaDTO> datosPolizas;
		 
		 //Si iCodPoliza no es nulo, es información de SISA.
//		 if(StringUtils.isBlank(polizaAsegurado.getIcodpoliza())){
			 datosPolizas = consultasPolizaDAOICE.obtieneDatosPoliza(polizaAsegurado);
//		 } else {
//			 datosPolizas = consultasPolizaDAOSISA.obtieneDatosPoliza(polizaAsegurado);
//		 }
		 
		 return datosPolizas;
	}

	@Override
	public List<Map<String, String>> obtieneDatosPolizaTvalopol(
			PolizaAseguradoVO polizaAsegurado) throws Exception {
		List<Map<String, String>> datosPolizas;
		
		//Si iCodPoliza no es nulo, es información de SISA.
//		 if(StringUtils.isBlank(polizaAsegurado.getIcodpoliza())){
		datosPolizas = consultasPolizaDAOICE.obtieneDatosPolizaTvalopol(polizaAsegurado);
//		 } else {
//			 datosPolizas = consultasPolizaDAOSISA.obtieneDatosPoliza(polizaAsegurado);
//		 }
		
		return datosPolizas;
	}
	
	@Override
	public List<DatosComplementariosVO> obtieneDatosComplementarios(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		List<DatosComplementariosVO> datosComplementarios = new ArrayList<DatosComplementariosVO>();
		
		//Si iCodPoliza no es nulo, es información de SISA.
//		 if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){		
//			 datosComplementarios = consultasPolizaDAOSISA.obtieneDatosComplementarios(poliza, asegurado);
//		 }
		 return datosComplementarios;
		
	}

	
	@Override
	public List<CopagoVO> obtieneCopagosPoliza(PolizaVO poliza, String nmsituac) throws Exception {
		
		List<CopagoVO> copagos;
		// Si iCodPoliza viene vacio, es información de ICE:
//		if(StringUtils.isBlank(poliza.getIcodpoliza())){
			
			copagos = consultasPolizaDAOICE.obtieneCopagosPoliza(poliza,nmsituac);
			
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
//		} else {
//			copagos = consultasPolizaDAOSISA.obtieneCopagosPoliza(poliza);
//		}
		return copagos;
	}
	
	@Override
	public List<CoberturaBasicaVO> obtieneCoberturasPoliza(PolizaVO poliza) throws Exception {
		
		List<CoberturaBasicaVO> coberturasPoliza;
		// Si iCodPoliza viene vacio, es información de ICE:
//		if(StringUtils.isBlank(poliza.getIcodpoliza())){
			coberturasPoliza = new ArrayList<CoberturaBasicaVO>();			
//		} else {
//			coberturasPoliza = consultasPolizaDAOSISA.obtieneCoberturasPoliza(poliza);
//		}
		return coberturasPoliza;
	}
	
	@Override
	public List<CoberturaBasicaVO> obtieneCoberturasBasicas(PolizaVO poliza) throws Exception {
		
		List<CoberturaBasicaVO> coberturasBasicas;
		// Si iCodPoliza viene vacio, es información de ICE:
//		if(StringUtils.isBlank(poliza.getIcodpoliza())){
			coberturasBasicas = new ArrayList<CoberturaBasicaVO>();			
//		} else {
//			coberturasBasicas = consultasPolizaDAOSISA.obtieneCoberturasBasicas(poliza);
//		}
		return coberturasBasicas;
	}
	
	@Override
	public List<PlanVO> obtieneDatosPlan(PolizaVO poliza) throws Exception {
		List<PlanVO> datosPlan = new ArrayList<PlanVO>();
		
		//Si iCodPoliza no es nulo, es información de SISA.
//		 if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){		
//			 datosPlan = consultasPolizaDAOSISA.obtieneDatosPlan(poliza);
//		 }
		 return datosPlan;
		
	}
	

	@Override
	public List<ContratanteVO> obtieneDatosContratante(PolizaVO poliza) throws Exception {
		List<ContratanteVO> datosContratante = new ArrayList<ContratanteVO>();
		
		//Si iCodPoliza no es nulo, es información de SISA.
//		 if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){		
//			 datosContratante = consultasPolizaDAOSISA.obtieneDatosContratante(poliza);
//		 }
		 return datosContratante;
		
	}
	
		
	@Override
	public List<AseguradoVO> obtieneAsegurados(PolizaVO poliza) throws Exception {
		
		List<AseguradoVO> asegurados;
		// Si iCodPoliza viene vacio, es información de ICE:
//		if(StringUtils.isBlank(poliza.getIcodpoliza())){
			asegurados = consultasPolizaDAOICE.obtieneAsegurados(poliza);
//		} else {
//			asegurados = consultasPolizaDAOSISA.obtieneAsegurados(poliza);
//		}
		return asegurados;
	}
	
	
	@Override
	public List<ClausulaVO> obtieneEndososPoliza(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		
		List<ClausulaVO> clausulas;
		
		// Si iCodPoliza viene vacio, es información de ICE:
//		  if(StringUtils.isBlank(poliza.getIcodpoliza())){
			  logger.debug("Clausulas de ICE");
			  clausulas=consultasPolizaDAOICE.obtieneEndososPoliza(poliza, asegurado);
//		  } else {
//			  logger.debug("Clausulas de SISA");
//			  clausulas=consultasPolizaDAOSISA.obtieneEndososPoliza(poliza, asegurado);
//		}
		  if(clausulas != null && clausulas.size()>0) {
			  logger.debug("Clausulas encontradas:" + clausulas.size());
			  logger.debug("Clausulas:" + clausulas);
		  } else {
			  logger.debug("No hay clausulas");
		  }
		  
		 return clausulas;
	}
	
	
	@Override
	public List<HistoricoFarmaciaVO> obtieneHistoricoFarmacia(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		List<HistoricoFarmaciaVO> historicoFarmacia;
		// Si iCodPoliza viene vacio, es información de ICE:
//		if(StringUtils.isBlank(poliza.getIcodpoliza())){
			  logger.debug("Historico Farmacia de ICE");
			  historicoFarmacia = new ArrayList<HistoricoFarmaciaVO>();
//		} else {
//			  logger.debug("Historico Farmacia de SISA");
//			  
//			  historicoFarmacia = consultasPolizaDAOSISA.obtieneHistoricoFarmacia(poliza, asegurado);
//		}
//		  if(historicoFarmacia != null && historicoFarmacia.size()>0) {
//			  logger.debug("Historico Farmacia registros:" + historicoFarmacia.size());
//			  logger.debug("Historico Farmacia:" + historicoFarmacia);
//		  } else {
//			  logger.debug("No se encontraron registros de Historico Farmacia");
//		  }
		  return historicoFarmacia;
	}
	
	@Override
	public List<PeriodoVigenciaVO> obtienePeriodosVigencia(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		List<PeriodoVigenciaVO> periodosVigencia;
		// Si iCodPoliza viene vacio, es información de ICE:
//		if(StringUtils.isBlank(poliza.getIcodpoliza())){
			  logger.debug("Periodos Vigencia de ICE");
			  
			  periodosVigencia = new ArrayList<PeriodoVigenciaVO>();
//		} else {
//			  logger.debug("Historico Farmacia de SISA");
//			  
//			  periodosVigencia = consultasPolizaDAOSISA.obtienePeriodosVigencia(poliza, asegurado);
//		}
//		  if(periodosVigencia != null && periodosVigencia.size()>0) {
//			  logger.debug("Periodos Vigencia registros:" + periodosVigencia.size());
//			  logger.debug("Periodos Vigencia:" + periodosVigencia);
//		  } else {
//			  logger.debug("No se encontraron registros de Historico Farmacia");
//		  }
		  return periodosVigencia;
	}
	
	@Override
	public List<ClausulaVO> obtieneClausulasPoliza(PolizaVO poliza, AseguradoVO asegurado) throws Exception {
		return consultasPolizaDAOICE.obtieneEndososPoliza(poliza, asegurado);
	}
	
	
	@Override
	public String obtieneMensajeAgente(PolizaVO poliza) throws Exception {
		return consultasPolizaDAOICE.obtieneMensajeAgente(poliza);
	}
	
	
	@Override
	public List<AseguradoDetalleVO> obtieneAseguradoDetalle(
			AseguradoVO asegurado) throws Exception {
//		return consultasPolizaDAOSISA.obtieneAseguradoDetalle(asegurado);
		return null;
	}
	

	@Override
	public List<ReciboVO> obtieneRecibosPoliza(PolizaVO poliza)
			throws Exception {
		return null;
	}
	
	
	@Override
	public List<DetalleReciboVO> obtieneDetalleRecibo(PolizaVO poliza,
			ReciboVO recibo) throws Exception {
		return null;
	}
	
	
	@Override
	public List<AgentePolizaVO> obtieneAgentesPoliza(PolizaVO poliza) throws Exception {
		List<AgentePolizaVO> agentes;
		// Si iCodPoliza viene vacio, es información de ICE:
//		if(StringUtils.isBlank(poliza.getIcodpoliza())){
			agentes = consultasPolizaDAOICE.obtieneAgentesPoliza(poliza);
//		} else {
//			agentes = consultasPolizaDAOSISA.obtieneAgentesPoliza(poliza);
//		}
		return agentes;
	}
	
	
	@Override
	public List<ReciboAgenteVO> obtieneRecibosAgente(PolizaVO poliza)
			throws Exception {
		List<ReciboAgenteVO> recibosAgente;
		// Si iCodPoliza viene vacio, es información de ICE:
//		if(StringUtils.isBlank(poliza.getIcodpoliza())){
			recibosAgente = consultasPolizaDAOICE.obtieneRecibosAgente(poliza);
//		} else {
//			recibosAgente = consultasPolizaDAOSISA.obtieneRecibosAgente(poliza);
//		}
		return recibosAgente;
	}


	@Override
	public List<TarifaVO> obtieneTarifasPoliza(PolizaVO poliza) throws Exception {
		return consultasPolizaDAOICE.obtieneTarifasPoliza(poliza);
	}


}