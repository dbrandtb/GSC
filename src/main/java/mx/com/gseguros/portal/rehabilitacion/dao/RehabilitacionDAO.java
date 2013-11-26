package mx.com.gseguros.portal.rehabilitacion.dao;

import java.util.List;
import java.util.Map;

public interface RehabilitacionDAO
{
	
	public List<Map<String,String>> buscarPolizas(Map<String,String> params) throws Exception;
	
}