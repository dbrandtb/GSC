package mx.com.gseguros.portal.renovacion.dao;

import java.util.List;
import java.util.Map;

public interface RenovacionDAO
{
	public List<Map<String,String>>buscarPolizasRenovables(String anio,String mes)throws Exception;
}