package mx.com.gseguros.portal.general.service;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;

public interface WebServicesManager {
	
		
	public List<Map<String, String>> obtienePeticionesFallidasWS(Map<String, String> params) throws ApplicationException;

	public List<Map<String, String>> obtieneDetallePeticionWS(Map<String, String> params) throws ApplicationException;

	public boolean eliminaPeticionWS(List<Map<String, String>> listaEliminar) throws ApplicationException;

}
