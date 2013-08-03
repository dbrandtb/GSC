package mx.com.aon.catbo.service;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.CasoDetalleVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.FormatoOrdenVO;
import mx.com.aon.catbo.model.ReasignacionCasoVO;
import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;
import mx.com.aon.portal.service.PagedList;

/**
 * User: gabrielforradellas
 * Date: Sep 2, 2008
 * Time: 10:42:12 PM
 */
public interface AdministracionCasosManager2 {
    
    public PagedList obtenerCasos(String pv_nmcaso_i, String pv_cdorden_i, String pv_dsproceso_i, String pv_feingreso_i, String pv_cdpriord_i, String pv_cdperson_i, String pv_dsperson_i, String pv_cdusuario_i, int start, int limit) throws ApplicationException;
    
    public TableModelExport getModelObtenerCasos(String pv_nmcaso_i, String pv_cdorden_i, String pv_dsproceso_i, String pv_feingreso_i, String pv_cdpriord_i, String pv_cdperson_i, String pv_dsperson_i, String pv_cdusuario_i) throws ApplicationException;
    
    public String guardarModulosUsuariosReasignacion(String cdModulo, String nmCaso)throws ApplicationException;
    
    public TableModelExport obtenerMovimientoCasoExportar(String pv_nmcaso_i)throws ApplicationException;
    
    public String validarTiempoMatriz(String pv_cdmatriz_i) throws ApplicationException ;

}

