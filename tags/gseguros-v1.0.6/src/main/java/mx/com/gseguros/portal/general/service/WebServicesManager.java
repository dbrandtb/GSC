package mx.com.gseguros.portal.general.service;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;

public interface WebServicesManager {
	
	/**
	 * Metodo que obtiene la bitacora de WS Fallidos, filtrados por producto, sucursal y poliza
	 * @param params
	 * @return
	 * @throws ApplicationException
	 */
	public List<Map<String, String>> obtienePeticionesFallidasWS(Map<String, String> params) throws ApplicationException;

	/**
	 * Metodo que Otiene el detalle de una peticion Fallida, el cual contiene el cdURL, metodo, Xml a enviar
	 * @param params
	 * @return
	 * @throws ApplicationException
	 */
	public List<Map<String, String>> obtieneDetallePeticionWS(Map<String, String> params) throws ApplicationException;

	/**
	 * Metodo que recibe una lista de peticiones a eliminar
	 * @param listaEliminar
	 * @return
	 * @throws ApplicationException
	 */
	public boolean eliminaPeticionWS(List<Map<String, String>> listaEliminar) throws ApplicationException;

}
