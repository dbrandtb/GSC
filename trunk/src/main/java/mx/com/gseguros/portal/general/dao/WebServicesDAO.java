package mx.com.gseguros.portal.general.dao;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.DaoException;

public interface WebServicesDAO {

	public List<Map<String, String>> obtienePeticionesFallidasWS(Map params) throws DaoException;

	public List<Map<String, String>> obtieneDetallePeticionWS(Map params) throws DaoException;

	public String eliminaPeticionWS(Map params) throws DaoException;

}