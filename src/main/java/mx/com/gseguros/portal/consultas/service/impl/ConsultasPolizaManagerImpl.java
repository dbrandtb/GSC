package mx.com.gseguros.portal.consultas.service.impl;

import static mx.com.gseguros.portal.consultas.dao.ConsultasPolizaDAO.OBTIENE_DATOS_POLIZA;
import static mx.com.gseguros.portal.consultas.dao.ConsultasPolizaDAO.OBTIENE_DATOS_SUPLEMENTO;
import static mx.com.gseguros.portal.consultas.dao.ConsultasPolizaDAO.OBTIENE_DATOS_SITUACION;
import static mx.com.gseguros.portal.consultas.dao.ConsultasPolizaDAO.OBTIENE_DATOS_COBERTURAS;

import java.util.HashMap;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.portal.consultas.service.ConsultasPolizaManager;

public class ConsultasPolizaManagerImpl extends AbstractManagerJdbcTemplateInvoke implements ConsultasPolizaManager {

	public WrapperResultados consultaPoliza(String cdunieco, String cdramo,
			String estado, String nmpoliza, String idper, String nmclient)
			throws ApplicationException {
		
		HashMap<String,Object> params = new HashMap<String,Object>();  
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_cdideper_i", idper);
		params.put("pv_nmclient_i", nmclient);
		
		WrapperResultados result = this.returnBackBoneInvoke(params, OBTIENE_DATOS_POLIZA);
		
		return result;
	}
	public WrapperResultados consultaSuplemento(String cdunieco, String cdramo, String estado, String nmpoliza)
					throws ApplicationException {
		
		HashMap<String,Object> params = new HashMap<String,Object>();  
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		
		WrapperResultados result = this.returnBackBoneInvoke(params, OBTIENE_DATOS_SUPLEMENTO);
		
		return result;
	}
	public WrapperResultados consultaSituacion(String cdunieco, String cdramo, String estado, String nmpoliza, String suplemento, String nmsituac)
					throws ApplicationException {
		
		HashMap<String,Object> params = new HashMap<String,Object>();  
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", suplemento);
		params.put("pv_nmsituac_i", nmsituac);
		
		WrapperResultados result = this.returnBackBoneInvoke(params, OBTIENE_DATOS_SITUACION);
		
		return result;
	}
	public WrapperResultados consultaCoberturas(String cdunieco, String cdramo, String estado, String nmpoliza, String suplemento, String nmsituac)
					throws ApplicationException {
		
		HashMap<String,Object> params = new HashMap<String,Object>();  
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", suplemento);
		params.put("pv_nmsituac_i", nmsituac);
		
		WrapperResultados result = this.returnBackBoneInvoke(params, OBTIENE_DATOS_COBERTURAS);
		
		return result;
	}
	
	
	
	
	
}