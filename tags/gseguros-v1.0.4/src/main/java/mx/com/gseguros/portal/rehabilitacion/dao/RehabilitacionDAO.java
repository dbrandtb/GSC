package mx.com.gseguros.portal.rehabilitacion.dao;

import java.util.List;
import java.util.Map;

public interface RehabilitacionDAO
{
	public List<Map<String,String>> buscarPolizas(Map<String,String> params)      throws Exception;
	public Map<String,Object>       rehabilitarPoliza(Map<String, String> params) throws Exception;
	
	
	/**
	 * pkg_satelites.p_valida_antiguedad
	 */
	public boolean validaAntiguedad(Map<String,String>params)    throws Exception;
	/**
	 * pkg_satelites.p_borra_antiguedad
	 */
	public void    borraAntiguedad(Map<String,String>params)    throws Exception;
}