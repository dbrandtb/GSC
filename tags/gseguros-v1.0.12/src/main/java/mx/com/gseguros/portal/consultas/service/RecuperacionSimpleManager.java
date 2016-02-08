package mx.com.gseguros.portal.consultas.service;

import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.consultas.model.RecuperacionSimple;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlist2VO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;

public interface RecuperacionSimpleManager
{
	public void setSession(Map<String,Object>session);
	
	public ManagerRespuestaSmapVO recuperacionSimple(
			RecuperacionSimple procedimiento
			,Map<String,String>parametros
			,String cdsisrol
			,String cdusuari
			);
	
	public ManagerRespuestaSlist2VO recuperacionSimpleLista(
			RecuperacionSimple procedimiento
			,Map<String,String>parametros
			,String cdsisrol
			,String cdusuari
			);
	
	public Map<String,String> recuperarMapa(
			String cdusuari
			,String cdsisrol
			,RecuperacionSimple consulta
			,Map<String,String> params
			,UserVO usuario
			)throws Exception;
	
	public List<Map<String,String>> recuperarLista(
			String cdusuari
			,String cdsisrol
			,RecuperacionSimple consulta
			,Map<String,String> params
			,UserVO usuario
			)throws Exception;
}