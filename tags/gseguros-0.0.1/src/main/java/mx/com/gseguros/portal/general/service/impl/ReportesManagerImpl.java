package mx.com.gseguros.portal.general.service.impl;

import java.io.InputStream;
import java.util.HashMap;

import mx.com.gseguros.portal.general.dao.ReportesDAO;
import mx.com.gseguros.portal.general.service.ReportesManager;

public class ReportesManagerImpl implements ReportesManager {
	
	private ReportesDAO reportesDAO;
	
	
	@Override
	public InputStream obtieneReporteExcel(HashMap<String,Object> params) throws Exception
	{
		return reportesDAO.obtieneReporteExcel(params);
	}


	public void setReportesDAO(ReportesDAO reportesDAO) {
		this.reportesDAO = reportesDAO;
	}

}