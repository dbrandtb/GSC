package mx.com.aon.catbo.service;

import java.io.InputStream;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.MediaTO;
import mx.com.aon.portal.service.PagedList;

/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Oct 16, 2008
 * Time: 5:32:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface  ArchivosManager {

    public PagedList obtenerArchivos(String pv_nmcaso_i, String pv_nmovimiento_i, int start, int limit) throws ApplicationException;
    public String borrarArchivos(String pv_nmcaso_i, String pv_nmovimiento_i, String pv_nmarchivo_i) throws ApplicationException;
    public String guardaArchivos(String pv_nmcaso_i, String pv_nmovimiento_i, String pv_nmarchivo_i , String pv_dsarchivo_i, String pv_cdtipoar_i, String pv_blarchivo_i, String pv_cdusuario_i) throws ApplicationException;
    
    public PagedList obtieneAtributosFax(String pv_dsarchivo_i, int start, int limit)throws ApplicationException;
    public FaxesVO getObtieneAtributosFax(String pv_cdtipoar_i, String pv_cdatribu_i) throws ApplicationException;
    public String borrarAtributosFax(String pv_cdtipoar_i, String pv_cdatribu_i) throws ApplicationException;
    public String guardarAtributosFax(String pv_cdtipoar_i, String pv_cdatribu_i,String pv_dsatribu_i, String pv_swformat_i, String pv_nmlmax_i, String pv_nmlmin_i, String pv_swobliga_i,String pv_ottabval_i, String pv_status_i)throws ApplicationException;
    public String editarGuardarAtributosFax(String pv_cdtipoar_i, String pv_cdatribu_i,String pv_dsatribu_i, String pv_swformat_i, String pv_nmlmax_i, String pv_nmlmin_i, String pv_swobliga_i,String pv_ottabval_i, String pv_status_i)throws ApplicationException; 
    public TableModelExport getModel(String pv_dsarchivo_i) throws ApplicationException;
    
	public PagedList buscarAtributosArchivos(String pv_dsarchivo_i, int start, int limit) throws ApplicationException;
    public String guardaAtributosArchivos(String pv_cdtipoar_i, String pv_cdatribu_i, String pv_swformat_i , String pv_nmlmax_i, String pv_nmlmin_i, String pv_swobliga_i, String pv_status_i) throws ApplicationException;
    public TableModelExport getModel2(String pv_dsarchivo_i) throws ApplicationException;
    
    
    public String guardaArchivosCaso (final MediaTO mediaTO, final InputStream inputStream, final int contentLength) throws Exception;
   
    public String actualizarAtributosFax(String pv_cdtipoar_i, String pv_cdatribu_i,String pv_dsatribu_i, String pv_swformat_i, String pv_nmlmax_i, String pv_nmlmin_i, String pv_swobliga_i,String pv_ottabval_i, String pv_status_i)throws ApplicationException;
}	



