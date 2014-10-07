package mx.com.gseguros.portal.consultas.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ConsultasDAO
{
	public List<Map<String,String>> consultaDinamica(String storedProcedure,LinkedHashMap<String,Object>params) throws Exception;
}