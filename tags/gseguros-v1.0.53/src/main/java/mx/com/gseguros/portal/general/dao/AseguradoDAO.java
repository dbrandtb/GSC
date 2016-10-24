package mx.com.gseguros.portal.general.dao;

import java.util.List;
import java.util.Map;

public interface AseguradoDAO {
	
	/**
	 * Valida la edad de los asegurados en una poliza
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @return Lista de los asegurados de edad invalida
	 * @throws Exception
	 */
	public List<Map<String,String>> validaEdadAsegurados(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem) throws Exception;
	

}