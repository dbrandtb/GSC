package mx.com.gseguros.portal.emision.service;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;

public interface EmisionManager
{
	public void setSession(Map<String,Object>session);
	
	public ManagerRespuestaImapVO construirPantallaClausulasPoliza();
	
	public ManagerRespuestaVoidVO guardarClausulasPoliza(List<Map<String,String>>clausulas);
	
	@Deprecated
	public String insercionDocumentosParametrizados(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			)throws Exception;
}