package mx.com.gseguros.portal.general.dao;

import java.util.List;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.DaoException;

public interface CatalogosDAO {
	
	public List<GenericVO> obtieneTmanteni(String cdTabla) throws DaoException;
	
	public List<GenericVO> obtieneColonias(String codigoPostal) throws DaoException;

}
