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
public interface GuionLlamadasManager {
	
	public PagedList obtenerGuionLlamadas(String pv_dsunieco_i, String pv_dselemento_i, String pv_dsguion_i, String pv_dsproceso_i, String pv_dstipgui_i,String pv_dsramo_i,int start, int limit) throws ApplicationException;
	
	public String guardarGuionLlamadas(String pv_cdunieco_i, String pv_cdramo_i, String pv_cdelemento_i, String pv_cdproceso_i, String pv_cdguion_i, String pv_dsguion_i, String pv_cdtipguion_i, String pv_indinicial_i, String pv_status_i) throws ApplicationException;
	
	public PagedList obtenerDialogoLlamadas(String pv_cdunieco_i, String pv_cdramo_i, String pv_cdelemento_i, String pv_cdproceso_i, String pv_cdguion_i,String pv_cddialogo_i,int start, int limit) throws ApplicationException; 
	
	public String guardarDialogoLlamadas(String pv_cdunieco_i, String pv_cdramo_i, String pv_cdelemento_i, String pv_cdproceso_i, String pv_cdguion_i, String pv_cddialogo_i, String pv_dsdialogo_i, String pv_cdsecuencia_i, String pv_ottapval_i) throws ApplicationException;
}

