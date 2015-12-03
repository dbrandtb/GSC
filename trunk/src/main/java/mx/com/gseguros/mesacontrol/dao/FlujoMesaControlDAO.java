package mx.com.gseguros.mesacontrol.dao;

import java.util.List;
import java.util.Map;

public interface FlujoMesaControlDAO {

	public List<Map<String,String>> recuperaTtiptramc() throws Exception;

	public List<Map<String,String>> recuperaTtipflumc() throws Exception;
}
