package mx.com.gseguros.portal.endosos.service;

import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;

public interface EndososAutoManager
{
	public void setSession(Map<String,Object>session);
	
	public ManagerRespuestaImapVO construirMarcoEndosos();
	
	public ManagerRespuestaSmapVO recuperarColumnasIncisoRamo(String cdramo);
}