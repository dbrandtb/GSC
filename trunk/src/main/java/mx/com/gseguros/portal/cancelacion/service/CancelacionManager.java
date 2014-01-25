package mx.com.gseguros.portal.cancelacion.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CancelacionManager
{	
	public List<Map<String,String>> buscarPolizas             (Map<String,String> params) throws Exception;
	public Map<String,String>       obtenerDetalleCancelacion (Map<String,String> params) throws Exception;
	public List<Map<String,String>> obtenerPolizasCandidatas  (Map<String,String> params) throws Exception;
	public void                     seleccionaPolizas         (Map<String,Object> params) throws Exception;
	public void                     cancelaPoliza             (Map<String,String> params) throws Exception;
	public void                     cancelaPoliza             (
			String cdunieco
			,String cdramo
			,String cduniage
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdrazon
			,String comenta
			,String feefecto
			,String fevencim
			,String fecancel
			,String cdusuari) throws Exception;
	public void                     seleccionaPolizaUnica     (Map<String,Object> params) throws Exception;
	public void                     seleccionaPolizaUnica (
			String cdunieco
			,String cdramo
			,String nmpoliza
			,String agencia
			,Date   fecha) throws Exception;
	public void                     actualizarTagrucan        (Map<String,String> params) throws Exception;
	public void                     cancelacionMasiva         (Map<String,String> params) throws Exception;
}