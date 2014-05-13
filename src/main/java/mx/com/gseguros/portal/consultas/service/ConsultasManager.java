package mx.com.gseguros.portal.consultas.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.general.util.ObjetoBD;

public interface ConsultasManager
{

	/**
	 * Obtiene datos ejecutando un objeto de BD
	 * @param objetoBD Objeto de BD a ejecutar
	 * @param params   Parametros que recibe el objeto de BD a ejecutar
	 * @return lista de elementos obtenidos de la consulta
	 * @throws Exception
	 */
	public List<Map<String,String>> consultaDinamica(ObjetoBD objetoBD, LinkedHashMap<String,Object>params) throws Exception;
	
}