package mx.com.gseguros.mesacontrol.dao;

import java.util.List;
import java.util.Map;

public interface FlujoMesaControlDAO {

	public List<Map<String,String>> recuperaTtiptramc() throws Exception;

	public List<Map<String,String>> recuperaTtipflumc() throws Exception;
	
	public List<Map<String,String>> recuperaTiconos() throws Exception;
	
	public List<Map<String,String>> recuperaTflujomc(String cdtipflu) throws Exception;
	
	public void movimientoTtipflumc(
			String cdtipflu
			,String dstipflu
			,String cdtiptra
			,String swmultipol
			,String swreqpol
			,String accion
			) throws Exception;
	
}
