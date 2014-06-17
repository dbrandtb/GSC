package mx.com.gseguros.portal.consultas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.dao.ConsultasPolizaDAO;
import mx.com.gseguros.portal.consultas.model.CopagoVO;
import mx.com.gseguros.portal.consultas.service.ConsultasPolizaManager;

import org.apache.commons.lang.StringUtils;

public class ConsultasPolizaManagerImpl extends
		AbstractManagerJdbcTemplateInvoke implements ConsultasPolizaManager {

	public WrapperResultados consultaPoliza(String cdunieco, String cdramo,
			String estado, String nmpoliza) throws ApplicationException {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);

		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.OBTIENE_DATOS_POLIZA);

		return result;
	}

	public WrapperResultados consultaSuplemento(String nmpoliex)
			throws ApplicationException {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmpoliex_i", nmpoliex);

		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.OBTIENE_DATOS_SUPLEMENTO);
		return result;
	}

	public WrapperResultados consultaSituacion(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String nmsituac)
			throws ApplicationException {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		params.put("pv_nmsituac_i", nmsituac);

		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.OBTIENE_DATOS_SITUACION);

		return result;
	}

	public WrapperResultados consultaCoberturas(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String nmsituac)
			throws ApplicationException {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		params.put("pv_nmsituac_i", nmsituac);

		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.OBTIENE_DATOS_COBERTURAS);

		return result;
	}

	public WrapperResultados obtienePolizasAsegurado(String rfc, String cdPerson, String nombre)
			throws ApplicationException {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdrfc", rfc);
		params.put("pv_cdperson", cdPerson);
		params.put("pv_nombre", nombre);

		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.OBTIENE_POLIZAS_ASEGURADO);

		return result;
	}

	public WrapperResultados consultaDatosTarifa(String cdunieco,
			String cdramo, String estado, String nmpoliza, String nmsuplem)
			throws ApplicationException {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);

		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.OBTIENE_DATOS_TARIFA);

		return result;
	}

	public WrapperResultados consultaPolizasAgente(String cdagente)
			throws ApplicationException {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdagente_i", cdagente);

		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.OBTIENE_POLIZAS_AGENTE);

		return result;
	}

	public WrapperResultados consultaRecibosAgente(String cdunieco,
			String cdramo, String estado, String nmpoliza)
			throws ApplicationException {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);

		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.OBTIENE_RECIBOS_AGENTE);

		return result;
	}

	public WrapperResultados consultaAgente(String cdagente)
			throws ApplicationException {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdagente_i", cdagente);

		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.OBTIENE_DATOS_AGENTE);

		return result;
	}

	public WrapperResultados consultaDatosAsegurado(String cdunieco,
			String cdramo, String estado, String nmpoliza, String nmsuplem)
			throws ApplicationException {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);

		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.OBTIENE_DATOS_ASEGURADO);

		return result;
	}
	
	public WrapperResultados consultaCopagosPoliza(String cdunieco,
			String cdramo, String estado, String nmpoliza, String nmsuplem)
			throws ApplicationException {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		
		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.OBTIENE_COPAGOS);
		
		//Agregamos un campo que agrupe los resultados:
		ArrayList<CopagoVO> copagos = (ArrayList<CopagoVO>) result.getItemList();
		String agrupador = null;
		
		Iterator<CopagoVO> itCopagos = copagos.iterator();
		while (itCopagos.hasNext()) {
			CopagoVO copagoVO = itCopagos.next();
			if( StringUtils.isBlank(agrupador) || StringUtils.isBlank(copagoVO.getValor()) ) {
				agrupador = copagoVO.getDescripcion();
			}
			if(StringUtils.isBlank(copagoVO.getValor())) {
				itCopagos.remove();
			}
			copagoVO.setAgrupador(agrupador);
		}

		return result;
	}

}