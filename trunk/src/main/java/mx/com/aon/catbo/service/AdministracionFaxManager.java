package mx.com.aon.catbo.service;

import java.util.HashMap;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.CasoDetalleVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.FormatoOrdenFaxVO;
import mx.com.aon.catbo.model.FormatoOrdenVO;
import mx.com.aon.catbo.model.ItemVO;
import mx.com.aon.catbo.model.PolizaFaxVO;
import mx.com.aon.catbo.model.ReasignacionCasoVO;
import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

/**
 * User: gabrielforradellas
 * Date: Sep 2, 2008
 * Time: 10:42:12 PM
 */
public interface AdministracionFaxManager {
	
	public PagedList obtenerAdministracionFax(String pv_dsarchivo_i,String pv_nmcaso_i,String pv_nmfax_i,String pv_nmpoliex_i,int start, int limit) throws ApplicationException;
	
	public PagedList obtenerAdministracionValorFax(String pv_nmcaso_i,String pv_nmfax_i,int start, int limit) throws ApplicationException ;
	
	public WrapperResultados guardarAdministracionFax(String pv_nmcaso_i, String pv_nmfax_i,String  pv_cdtipoar_i,String  pv_feingreso_i, String  pv_ferecepcion_i,String pv_nmpoliex_i , String pv_cdusuari_i ,String pv_blarchivo_i )throws ApplicationException;
		
	public String guardarAdministracionValorFax(String pv_nmcaso_i, String pv_nmfax_i,String  pv_cdtipoar_i,String  pv_cdatribu_i, String  pv_otvalor_i)throws ApplicationException;
	
	public String borrarAdministracionFax(String pv_nmfax_i, String pv_nmcaso_i) throws ApplicationException;
	
	public PagedList obtenerTatriarcFax(String pv_cdtipoar_i,int start, int limit) throws ApplicationException ;
	
	public PagedList obtenerValoresFax(String pv_cdtipoar_i,String pv_nmcaso_i,String pv_nmfax_i,int start, int limit) throws ApplicationException;
	
	public PolizaFaxVO obtenerPolizaFax(String pv_nmcaso_i) throws ApplicationException; 
	
	public String validaNmcasoFaxes(String pv_nmcaso_i) throws ApplicationException;
	
	public WrapperResultados guardarNuevoCasoFax(FaxesVO faxesVO, FormatoOrdenFaxVO formatoOrdenFaxVO)throws Exception;
	
	//public HashMap obtenerDetalleFaxParaExportar(String dsarchivo, String nmcaso, String nmfax, String nmpoliex)throws ApplicationException;
	
	public HashMap<String, ItemVO> obtenerAtributosVariablesFaxParaExportar(String cdtipoar, String nmcaso, String nmfax)throws ApplicationException;
	
	
}

