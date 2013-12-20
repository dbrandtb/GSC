package mx.com.gseguros.portal.cancelacion.service;

import java.util.List;
import java.util.Map;

public interface CancelacionManager
{	
	public List<Map<String,String>> buscarPolizas             (Map<String,String> params) throws Exception;
	public Map<String,String>       obtenerDetalleCancelacion (Map<String,String> params) throws Exception;
	public List<Map<String,String>> obtenerPolizasCandidatas  (Map<String,String> params) throws Exception;
	public void                     seleccionaPolizas         (Map<String,String> params) throws Exception;
}