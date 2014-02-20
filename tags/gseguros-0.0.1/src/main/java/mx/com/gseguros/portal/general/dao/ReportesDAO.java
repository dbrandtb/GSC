package mx.com.gseguros.portal.general.dao;

import java.io.InputStream;
import java.util.HashMap;

import mx.com.gseguros.exception.DaoException;

public interface ReportesDAO {
	
	public InputStream obtieneReporteExcel(HashMap<String,Object> params) throws DaoException;

}
