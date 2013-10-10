package mx.com.gseguros.portal.consultas.service.impl;

import mx.com.gseguros.portal.consultas.dao.ConsultasPolizaDAO;

import java.util.HashMap;

import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.service.ConsultasPolizaManager;

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

	public WrapperResultados obtienePolizasAsegurado(String rfc)
			throws ApplicationException {

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdrfc", rfc);

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

	public WrapperResultados consultaClausulas(String cdclause, String dsclausu)
			throws ApplicationException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdcla_i", cdclause);
		params.put("pv_descrip_i", dsclausu);

		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.OBTIENE_CLAUSULAS);

		return result;
	}

	public WrapperResultados insertaClausula(String dsclausu, String contenido)
			throws ApplicationException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_descrip_i", dsclausu);
		params.put("pv_conten_i", contenido);
		
		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.INSERTA_CLAUSULA);
		
		return result;
	}

	public WrapperResultados actualizaClausula(String cdclausu, String dsclausu, String contenido)
			throws ApplicationException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdtipcla_i", cdclausu);
		params.put("pv_descrip_i", dsclausu);
		params.put("pv_conten_i", contenido);
		
		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.ACTUALIZA_CLAUSULA);
		
		return result;
	}

	public WrapperResultados consultaClausulaDetalle(String cdclausu)
			throws ApplicationException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdcla_i", cdclausu);
		
		WrapperResultados result = this.returnBackBoneInvoke(params,
				ConsultasPolizaDAO.CONSULTA_CLAUSULA_DETALLE);
		
		return result;
	}

}