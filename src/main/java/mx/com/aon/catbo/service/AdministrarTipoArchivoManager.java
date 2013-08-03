package mx.com.aon.catbo.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.ArchivosFaxesVO;
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
public interface AdministrarTipoArchivoManager {	
	
	public PagedList obtenerArchivo(String pv_dsarchivo_i, int start, int limit) throws ApplicationException;
	
	public String guardaArchivos(String pv_cdtipoar_i, String pv_dsarchivo_i,String pv_indarchivo_i)throws ApplicationException;
	
	
	public TableModelExport getModel(String pv_dsmodulo_i, String pv_dsusuario_i) throws ApplicationException;
	public String borrarUsuarios(String pv_cdmodulo_i, String pv_cdusuario_i) throws ApplicationException;
    public String guardarUsuarios(String pv_cdmodulo_i, String pv_cdusuario_i)throws ApplicationException;
    public PagedList obtenerUsuariosAsignar(String pv_dsusuario_i, int start, int limit ) throws ApplicationException;
    
	public TableModelExport getModel2(String pv_dsarchivo_i) throws ApplicationException;
	public String borraArchivo(String pv_cdtipoar_i) throws ApplicationException;
    
}
