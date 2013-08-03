package mx.com.aon.portal.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.InstrumentoPagoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

public class InstrumentoPagoManagerImpl extends
		AbstractManagerJdbcTemplateInvoke implements InstrumentoPagoManager {

	@SuppressWarnings("unchecked")
	public PagedList getInstrumetosPagoCliente(String cdElemento, String cdForPag, String cdUnieco, String cdRamo, int start , int limit)throws ApplicationException {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("pv_cdelemento_i", cdElemento);
        params.put("pv_cdforpag_i", cdForPag);
        params.put("pv_cduniage_i", cdUnieco);
        params.put("pv_cdramo_i", cdRamo);
        
        return pagedBackBoneInvoke(params, "OBTIENE_INSTRUMENTOS_PAGO_CLIENTE", start, limit);
	}
	
	public String agregarInstrumetoPagoCliente(String cdElemento, String cdForPag, String cdUnieco, String cdRamo)throws ApplicationException{
		
		String mensaje = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdinscte_i", null);
        params.put("pv_cdelemento_i", cdElemento);
        params.put("pv_cdforpag_i", cdForPag);
        params.put("pv_cduniage_i", cdUnieco);
        params.put("pv_cdramo_i", cdRamo);
        
        WrapperResultados resultado = returnBackBoneInvoke(params, "GUARDA_INSTRUMENTO_PAGO_CLIENTE");
        mensaje = resultado.getMsgText();
		
		return mensaje;
	}
	
	
	public String borrarInstrumetoPagoCliente(String cdInsCte)throws ApplicationException{
		
		String mensaje = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdinscte_i", cdInsCte);
        
        WrapperResultados resultado = returnBackBoneInvoke(params, "BORRA_INSTRUMENTO_PAGO_CLIENTE");
        mensaje = resultado.getMsgText();
		
		return mensaje;
	}
	
	@SuppressWarnings("unchecked")
	public PagedList getAtributosInstrumetoPago(String cdInsCte, String dsAtribu, int start , int limit)throws ApplicationException {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("pv_cdunica_i", cdInsCte);
        params.put("pv_dsatribu_i", dsAtribu);
        
        return pagedBackBoneInvoke(params, "OBTIENE_ATRIBUTOS_INSTRUMENTO_PAGO", start, limit);
	}
	
	@SuppressWarnings("unchecked")
	public String guardarAtributoInstrumetoPagoCliente(Map<String, Object> params)throws ApplicationException {
		String mensaje = null;
        WrapperResultados resultado = returnBackBoneInvoke(params, "GUARDA_ATRIBUTO_INSTRUMENTO_PAGO");
        mensaje = resultado.getMsgText();
		
		return mensaje;
	}
	
	public String borrarAtributoInstrumetoPago(String cdInsCte, String cdAtribu)throws ApplicationException{
		
		String mensaje = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunica_i", cdInsCte);
		params.put("pv_cdatribu_i", cdAtribu);
        
        WrapperResultados resultado = returnBackBoneInvoke(params, "BORRA_ATRIBUTO_INSTRUMENTO_PAGO");
        mensaje = resultado.getMsgText();
		
		return mensaje;
	}
	
	@SuppressWarnings("unchecked")
	public String guardaAtributosInstPagoPoliza(Map<String, String> params)throws ApplicationException {
		
		String mensaje = null;
        WrapperResultados resultado = returnBackBoneInvoke(params, "GUARDA_ATRIBUTOS_INSPAGO_POLIZA");
        mensaje = resultado.getMsgText();
		
		return mensaje;
	}
	
	
}
