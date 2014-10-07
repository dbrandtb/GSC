package mx.com.gseguros.wizard.dao;

import java.util.List;
import java.util.Map;


public interface TablasApoyoDAO {
	
	public List<Map<String, String>> obtieneValoresTablaApoyo5claves(Map<String,String> params) throws Exception;
	
}