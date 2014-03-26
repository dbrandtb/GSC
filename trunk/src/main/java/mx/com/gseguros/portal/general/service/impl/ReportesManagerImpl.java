package mx.com.gseguros.portal.general.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.general.dao.ReportesDAO;
import mx.com.gseguros.portal.general.model.ParamReporteVO;
import mx.com.gseguros.portal.general.model.ReporteVO;
import mx.com.gseguros.portal.general.service.ReportesManager;

import org.apache.log4j.Logger;

public class ReportesManagerImpl implements ReportesManager {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ReportesManagerImpl.class);
	
	private ReportesDAO reportesDAO;


	@Override
	public List<ReporteVO> obtenerListaReportes() throws DaoException {
		return reportesDAO.obtenerListaReportes();
	}


	@Override
	public List<ParamReporteVO> obtenerParametrosReportes(String cdreporte) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdreporte_i", cdreporte);
		return reportesDAO.obtenerParametrosReporte(params);
	}
	
	
	@Override
	public InputStream obtenerDatosReporte(String cdreporte, String username, List<ParamReporteVO> paramsReporte) throws Exception {
		
		// Se actualizan los valores de los parametros del reporte:
		for(ParamReporteVO paramReporte : paramsReporte) {
			reportesDAO.actualizarParametroReporte(cdreporte, username, paramReporte);
		}
		
		// Se arma el reporte:
		reportesDAO.armarReporte(cdreporte, username);
		
		// Se obtienen los bytes del reporte:
		return reportesDAO.obtenerReporte(cdreporte, username);
	}
	
	
	public void setReportesDAO(ReportesDAO reportesDAO) {
		this.reportesDAO = reportesDAO;
	}

}