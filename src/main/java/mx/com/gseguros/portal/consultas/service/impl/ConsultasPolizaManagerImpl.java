package mx.com.gseguros.portal.consultas.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.com.gseguros.portal.consultas.dao.IConsultasPolizaDAO;
import mx.com.gseguros.portal.consultas.model.AseguradoDetalleVO;
import mx.com.gseguros.portal.consultas.model.AseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSuplementoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaReciboAgenteVO;
import mx.com.gseguros.portal.consultas.model.CopagoVO;
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
		
		//Si iCodPoliza es nulo, es información de ICE, sino es de SISA:
		if(StringUtils.isBlank(polizaAsegurado.getIcodpoliza())){
			suplementos = consultasPolizaDAOICE.obtieneHistoricoPoliza(polizaAsegurado);
		} else {
			suplementos = consultasPolizaDAOSISA.obtieneHistoricoPoliza(polizaAsegurado);
		}
				
		return suplementos;
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
	public List<CopagoVO> obtieneCopagosPoliza(PolizaVO poliza)
			throws Exception {
		//List<CopagoVO> copagos;
		
		//Si iCodPoliza no es nulo, es información de SISA.
		//En copagos no se distingue si son de SISA actualmente.
		//copagos = consultasPolizaDAOSISA.obtieneCopagosPoliza(poliza);
		return consultasPolizaDAOSISA.obtieneCopagosPoliza(poliza);
		//return copagos;
	}

	@Override
	public List<AseguradoVO> obtieneAsegurados(PolizaVO poliza)
			throws Exception {
		List<AseguradoVO> asegurados;
		
		//Si iCodPoliza no es nulo, es información de SISA.
		//En asegurados no se distingue si son de SISA actualmente.
		asegurados = consultasPolizaDAOSISA.obtieneAsegurados(poliza);
		return asegurados;
	}

	@Override
	public List<ClausulaVO> obtieneEndososPoliza(PolizaVO poliza,
			AseguradoVO asegurado) throws Exception {
		//TODO: agregar la invocacion a ICE
		return consultasPolizaDAOSISA.obtieneEndososPoliza(poliza, asegurado);
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