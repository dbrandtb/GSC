package mx.com.gseguros.wizard.service;

import java.util.List;
import java.util.Map;

public interface TablasApoyoManager {

	public List<Map<String, String>> obtieneValoresTablaApoyo5claves(Map<String,String> params) throws Exception;
	
}