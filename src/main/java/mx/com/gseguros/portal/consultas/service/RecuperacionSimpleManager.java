package mx.com.gseguros.portal.consultas.service;

import java.util.Map;

import mx.com.gseguros.portal.consultas.model.RecuperacionSimple;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;

public interface RecuperacionSimpleManager
{
	public void setSession(Map<String,Object>session);
	
	public ManagerRespuestaSmapVO recuperacionSimple(
			RecuperacionSimple procedimiento
			,Map<String,String>parametros
			);
	
	public ManagerRespuestaSlistVO recuperacionSimpleLista(
			RecuperacionSimple procedimiento
			,Map<String,String>parametros
			);
}