package mx.com.aon.catbo.service;

import java.util.HashMap;

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
public interface ArchivosFaxesManager {	
	public PagedList obtenerArchivos(String pv_dsarchivo_i, int start, int limit) throws ApplicationException;
	public String borrarArchivos(String pv_cdtipoar_i,String pv_cdatribu_i)throws ApplicationException;
	 public ArchivosFaxesVO getObtenerArchivo(String pv_cdtipoar_i, String pv_cdatribu_i) throws ApplicationException;
	public String guardarArchivos(ArchivosFaxesVO archivosFaxesVO)throws ApplicationException;	
	public FaxesVO getObtenerFax(String pv_nmfax_i) throws ApplicationException;	
	public String guardarFax(FaxesVO faxesVO)throws ApplicationException;
	public String guardarValoresFax (FaxesVO faxesVO)throws ApplicationException;
	public PagedList obtenerArchivosInterfaz(String pv_dsarchivo_i,String pv_nmtiparc_i, int start, int limit) throws ApplicationException;
	public String validaNumeroCaso(String pv_nmcaso_i) throws ApplicationException;
	public PagedList obtenerFaxes (String pv_dsarchivo_i, String pv_nmcaso_i,String pv_nmfax_i, String pv_nmpoliex_i, int start, int limit) throws ApplicationException;
	public String BorrarFax(String pv_nmfax_i)throws ApplicationException;
	public String BorrarValoresFax(String pv_nmfax_i)throws ApplicationException;
	public PagedList ObtenerValoresFaxes(String pv_nmcaso_i,String pv_nmfax_i,int start, int limit) throws ApplicationException;
	public TableModelExport getModel(String cdtipoar, String nmcaso,String nmfax, String nmpoliex)throws ApplicationException;
	public String BorrarDetalleFax(String pv_nmfax_i, String pv_nmcaso_i)throws ApplicationException;
	
	
	public HashMap obtenerDetalleFaxParaExportar(String dsarchivo, String nmcaso, String nmfax, String nmpoliex,String archivoNombre)
	throws ApplicationException;
	
	
}
