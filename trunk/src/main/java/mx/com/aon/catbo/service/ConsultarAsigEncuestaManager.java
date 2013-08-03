package mx.com.aon.catbo.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.ArchivosFaxesVO;
import mx.com.aon.catbo.model.AsigEncuestaVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.MatrizAsignacionVO;
import mx.com.aon.catbo.model.ResponsablesVO;
import mx.com.aon.catbo.model.TiemposVO;
import mx.com.aon.portal.service.PagedList;

/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Sep 2, 2008
 * Time: 10:44:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ConsultarAsigEncuestaManager {	
	
	public PagedList obtenerAsigEncuesta(String pv_nmpoliex_i, String pv_dsperson_i, String pv_dsusuario_i, int start, int limit) throws ApplicationException;
	
	public String borrarAsigEncuesta(String pv_nmconfig_i,String pv_cdunieco_i,String pv_cdramo_i, String pv_estado_i)throws ApplicationException;
	
	public String guardarAsigEncuesta(AsigEncuestaVO asigEncuestaVO) throws ApplicationException;
	
	public TableModelExport obtieneArchivoExport(String nmPoliza, String nombreCliente, String nombreUsuario) throws ApplicationException;
	
	public AsigEncuestaVO getObtenerAsigEncuesta(String pv_nmconfig_i,String pv_nmpoliza_i, String pv_cdperson_i, String pv_cdusuario_i) throws ApplicationException;
	
}
