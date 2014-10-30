package mx.com.gseguros.portal.consultas.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.com.gseguros.portal.consultas.controller.ConsultasPolizaAction;
import mx.com.gseguros.portal.consultas.dao.IConsultasPolizaDAO;
import mx.com.gseguros.portal.consultas.model.AseguradoDetalleVO;
import mx.com.gseguros.portal.consultas.model.AseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosComplementariosVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosHistoricoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSuplementoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaReciboAgenteVO;
import mx.com.gseguros.portal.consultas.model.CopagoVO;
import mx.com.gseguros.portal.consultas.model.HistoricoFarmaciaVO;
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

@Service("consultasPolizaManagerImpl")
public class ConsultasPolizaManagerImpl implements ConsultasPolizaManager {
	
	private org.apache.log4j.Logger logger =org.apache.log4j.Logger.getLogger(ConsultasPolizaManagerImpl.class);
	
	@Autowired
	@Qualifier("consultasDAOICEImpl")
	private IConsultasPolizaDAO consultasPolizaDAOICE;
	
	@Autowired
	@Qualifier("consultasDAOSISAImpl")
	private IConsultasPolizaDAO consultasPolizaDAOSISA;
	
	
	@Override
	public List<ConsultaPolizaAseguradoVO> obtienePolizasAsegurado(String rfc,
			String cdperson, String nombre) throws Exception {
		
		List<ConsultaPolizaAseguradoVO> polizasICE  = consultasPolizaDAOICE.obtienePolizasAsegurado(rfc, cdperson, nombre);
		List<ConsultaPolizaAseguradoVO> polizasSISA = consultasPolizaDAOSISA.obtienePolizasAsegurado(rfc, cdperson, nombre);
		
		// Fusionamos las listas:
        List<ConsultaPolizaAseguradoVO> polizasFusionadas = polizasICE;
        if(polizasFusionadas == null) {
        	polizasFusionadas = new ArrayList<ConsultaPolizaAseguradoVO>();
        }
        polizasFusionadas.addAll(polizasSISA);
        
		return polizasFusionadas;
	}
	
	
	@Override
	public List<ConsultaDatosSuplementoVO> obtieneHistoricoPoliza(
			ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception {
		
		List<ConsultaDatosSuplementoVO> suplementos;  
		
		// Si iCodPoliza viene vacio, es información de ICE, sino es de SISA:
		if(StringUtils.isBlank(polizaAsegurado.getIcodpoliza())){
			suplementos = consultasPolizaDAOICE.obtieneHistoricoPoliza(polizaAsegurado);
		} else {
			suplementos = consultasPolizaDAOSISA.obtieneHistoricoPoliza(polizaAsegurado);
		}
				
		return suplementos;
	}
	
	@Override
	public List<ConsultaDatosHistoricoVO> obtieneHistoricoPolizaSISA(
			ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception {
		
		List<ConsultaDatosHistoricoVO> historico = new ArrayList<ConsultaDatosHistoricoVO>();  
		
		//Si iCodPoliza es nulo, es información de ICE, sino es de SISA:
		if(StringUtils.isBlank(polizaAsegurado.getIcodpoliza()) == false){			
			historico = consultasPolizaDAOSISA.obtieneHistoricoPolizaSISA(polizaAsegurado);
		} /*else {
			historico = consultasPolizaDAOSISA.obtieneHistoricoPoliza(polizaAsegurado);
		}*/
				
		return historico;
	}

	@Override
	public List<ConsultaDatosPolizaVO> obtieneDatosPoliza(
			ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception {
		 List<ConsultaDatosPolizaVO> datosPolizas;
		 
		 //Si iCodPoliza no es nulo, es información de SISA.
		 if(StringUtils.isBlank(polizaAsegurado.getIcodpoliza())){
			 datosPolizas = consultasPolizaDAOICE.obtieneDatosPoliza(polizaAsegurado);
		 } else {
			 datosPolizas = consultasPolizaDAOSISA.obtieneDatosPoliza(polizaAsegurado);
		 }
		 
		 return datosPolizas;
	}
	
	@Override
	public List<ConsultaDatosComplementariosVO> obtieneDatosComplementarios(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		List<ConsultaDatosComplementariosVO> datosComplementarios = new ArrayList<ConsultaDatosComplementariosVO>();
		
		//Si iCodPoliza no es nulo, es información de SISA.
		 if(StringUtils.isBlank(poliza.getIcodpoliza()) == false){		
			 datosComplementarios = consultasPolizaDAOSISA.obtieneDatosComplementarios(poliza, asegurado);
		 }
		 return datosComplementarios;
		
	}

	
	@Override
	public List<CopagoVO> obtieneCopagosPoliza(PolizaVO poliza) throws Exception {
		
		List<CopagoVO> copagos;
		// Si iCodPoliza viene vacio, es información de ICE:
		if(StringUtils.isBlank(poliza.getIcodpoliza())){
			
			copagos = consultasPolizaDAOICE.obtieneCopagosPoliza(poliza);
			
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
		} else {
			copagos = consultasPolizaDAOSISA.obtieneCopagosPoliza(poliza);
		}
		return copagos;
	}

	
	@Override
	public List<AseguradoVO> obtieneAsegurados(PolizaVO poliza) throws Exception {
		
		List<AseguradoVO> asegurados;
		// Si iCodPoliza viene vacio, es información de ICE:
		if(StringUtils.isBlank(poliza.getIcodpoliza())){
			asegurados = consultasPolizaDAOICE.obtieneAsegurados(poliza);
		} else {
			asegurados = consultasPolizaDAOSISA.obtieneAsegurados(poliza);
		}
		return asegurados;
	}
	
	
	@Override
	public List<ClausulaVO> obtieneEndososPoliza(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		
		List<ClausulaVO> clausulas;
		
		// Si iCodPoliza viene vacio, es información de ICE:
		  if(StringUtils.isBlank(poliza.getIcodpoliza())){
			  logger.debug("Clausulas de ICE");
			  clausulas=consultasPolizaDAOICE.obtieneEndososPoliza(poliza, asegurado);
		  } else {
			  logger.debug("Clausulas de SISA");
			  clausulas=consultasPolizaDAOSISA.obtieneEndososPoliza(poliza, asegurado);
		}
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
		if(StringUtils.isBlank(poliza.getIcodpoliza())){
			  logger.debug("Historico Farmacia de ICE");
			  
			  historicoFarmacia = new ArrayList<HistoricoFarmaciaVO>();
		} else {
			  logger.debug("Historico Farmacia de SISA");
			  
			  historicoFarmacia = consultasPolizaDAOSISA.obtieneHistoricoFarmacia(poliza, asegurado);
		}
		  if(historicoFarmacia != null && historicoFarmacia.size()>0) {
			  logger.debug("Historico Farmacia registros:" + historicoFarmacia.size());
			  logger.debug("Historico Farmacia:" + historicoFarmacia);
		  } else {
			  logger.debug("No se encontraron registros de Historico Farmacia");
		  }
		  return historicoFarmacia;
	}
	
	
	@Override
	public List<ClausulaVO> obtieneClausulasPoliza(PolizaVO poliza, AseguradoVO asegurado) throws Exception {
		return consultasPolizaDAOICE.obtieneEndososPoliza(poliza, asegurado);
	}
	
	
	
	
	@Override
	public List<AseguradoDetalleVO> obtieneAseguradoDetalle(
			AseguradoVO asegurado) throws Exception {
		return consultasPolizaDAOSISA.obtieneAseguradoDetalle(asegurado);
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