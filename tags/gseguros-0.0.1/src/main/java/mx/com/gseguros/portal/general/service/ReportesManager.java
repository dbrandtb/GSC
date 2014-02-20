package mx.com.gseguros.portal.general.service;

import java.io.InputStream;
import java.util.HashMap;


public interface ReportesManager {
	public InputStream obtieneReporteExcel(HashMap<String,Object> params) throws Exception;
	
}