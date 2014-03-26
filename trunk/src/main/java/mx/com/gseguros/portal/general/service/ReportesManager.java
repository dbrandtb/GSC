package mx.com.gseguros.portal.general.service;

import java.io.InputStream;
import java.util.List;

import mx.com.gseguros.portal.general.model.ParamReporteVO;
import mx.com.gseguros.portal.general.model.ReporteVO;


public interface ReportesManager {
	
	/**
	 * Obtiene el listado de los reportes disponibles
	 * @return
	 * @throws Exception
	 */
	public List<ReporteVO> obtenerListaReportes() throws Exception;
	
	/**
	 * Obtiene los par&aacute;metros que necesita un reporte
	 * @param cdreporte clave del reporte
	 * @return lista de par&aacute;metros del reporte solicitado
	 * @throws Exception
	 */
	public List<ParamReporteVO> obtenerParametrosReportes(String cdreporte) throws Exception;
	
	/**
	 * Lleva a cabo el proceso para obtener el contenido en bytes de un reporte
	 * @param cdreporte
	 * @param username username dentro de la aplicaci&oacute;n 
	 * @return
	 * @throws Exception
	 */
	public InputStream obtenerDatosReporte(String cdreporte, String username, List<ParamReporteVO> paramReporteVO) throws Exception;
	
}