package mx.com.gseguros.wizard.dao;

import java.util.List;
import java.util.Map;


public interface TablasApoyoDAO {
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> obtieneValoresTablaApoyo5claves(Map<String,String> params) throws Exception;

	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> obtieneValoresTablaApoyo1clave(Map<String,String> params) throws Exception;
	
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String guardaValoresTablaApoyo(Map<String,String> params) throws Exception;
	
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String guardaValoresTablaApoyo1Clave(Map<String,String> params) throws Exception;
	
	
	/**
	 * 
	 * @param nmtabla
	 * @param tipoTabla
	 * @param nombreArchivo
	 * @param separador
	 * @return
	 * @throws Exception
	 */
	public String cargaMasiva(Integer nmtabla, Integer tipoTabla, String nombreArchivo, String separador) throws Exception; 
	
}