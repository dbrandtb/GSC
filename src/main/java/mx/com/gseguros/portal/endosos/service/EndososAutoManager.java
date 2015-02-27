package mx.com.gseguros.portal.endosos.service;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;

public interface EndososAutoManager
{
	public void setSession(Map<String,Object>session);
	
	public ManagerRespuestaImapVO construirMarcoEndosos(String cdsisrol);
	
	public ManagerRespuestaSmapVO recuperarColumnasIncisoRamo(String cdramo);
	
	public ManagerRespuestaSlistSmapVO recuperarEndososClasificados(
			String cdramo
			,String nivel
			,String multiple
			,String tipoflot
			,List<Map<String,String>>incisos
			);
}