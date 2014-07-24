package mx.com.gseguros.portal.general.dao;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.DaoException;

public interface MenuDAO {

		
	public List<Map<String, String>> obtieneOpcionesLiga(Map params) throws DaoException;

	public List<Map<String, String>> obtieneMenusPorRol(Map params) throws DaoException;

	public List<Map<String, String>> obtieneOpcionesMenu(Map params) throws DaoException;

	public List<Map<String, String>> obtieneOpcionesSubMenu(Map params) throws DaoException;

	public String guardaOpcionLiga(Map params) throws DaoException;

	public String guardaMenu(Map params) throws DaoException;

	public String guardaOpcionMenu(Map params) throws DaoException;

	public String eliminaOpcionLiga(Map params) throws DaoException;

	public String eliminaMenu(Map params) throws DaoException;

	public String eliminaOpcionMenu(Map params) throws DaoException;
	
}