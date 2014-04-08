package mx.com.gseguros.portal.general.service.impl;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.general.dao.MenuDAO;
import mx.com.gseguros.portal.general.service.MenuManager;

import org.apache.log4j.Logger;

public class MenuManagerImpl implements MenuManager {
	
	private Logger logger = Logger.getLogger(MenuManagerImpl.class);
	
	
	private MenuDAO menuDAO;

	@Override
	public List<Map<String, String>> obtieneOpcionesLiga(Map<String, String> params) throws ApplicationException{
		try {
			return menuDAO.obtieneOpcionesLiga(params);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public List<Map<String, String>> obtieneMenusPorRol(Map<String, String> params) throws ApplicationException{
		try {
			return menuDAO.obtieneMenusPorRol(params);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public String guardaOpcionLiga(Map<String, String> params) throws ApplicationException{
		try {
			return menuDAO.guardaOpcionLiga(params);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public String guardaMenu(Map<String, String> params) throws ApplicationException{
		try {
			return menuDAO.guardaMenu(params);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public String eliminaOpcionLiga(Map<String, String> params) throws ApplicationException{
		try {
			return menuDAO.eliminaOpcionLiga(params);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public String eliminaMenu(Map<String, String> params) throws ApplicationException{
		try {
			return menuDAO.eliminaMenu(params);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}
		
}