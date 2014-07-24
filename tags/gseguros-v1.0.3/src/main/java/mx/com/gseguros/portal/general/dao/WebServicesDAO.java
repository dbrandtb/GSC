package mx.com.gseguros.portal.general.dao;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.DaoException;

public interface WebServicesDAO {

	/**
	 * Consulta a la base de datos las peticiones de WS fallidas
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public List<Map<String, String>> obtienePeticionesFallidasWS(Map params) throws DaoException;

	/**
	 * Metodo que obtiene a detalle el contenido de un registro de la Bitacora de WS
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public List<Map<String, String>> obtieneDetallePeticionWS(Map params) throws DaoException;

	/**
	 * Metodo que elimina un registro de la Bitacora de WS
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public String eliminaPeticionWS(Map params) throws DaoException;

}