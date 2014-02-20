package mx.com.gseguros.portal.rehabilitacion.service;

import java.util.List;
import java.util.Map;

public interface RehabilitacionManager
{
	public List<Map<String,String>> buscarPolizas(Map<String,String> params)      throws Exception;
	public void                     rehabilitarPoliza(Map<String, String> params) throws Exception;
}