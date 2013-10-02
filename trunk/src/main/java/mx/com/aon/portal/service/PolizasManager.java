package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConsultaPolizasCanceladasVO;
import mx.com.aon.portal.model.RehabilitacionManual_PolizaVO;
//import mx.com.aon.portal.service.impl.Map;
import mx.com.aon.procesos.emision.model.PolizaMaestraVO;
import mx.com.gseguros.exception.ApplicationException;

import java.util.Map;

import java.util.List;

public interface PolizasManager {
	
    public PagedList buscarPolizasACancelar (String pv_asegurado_i, String pv_dsuniage_i,String pv_dsramo_i,String pv_nmpoliza_i,String pv_nmsituac_i, int start, int limit) throws ApplicationException;

    public String modificarCancelacionPoliza (List<ConsultaPolizasCanceladasVO> polizasCanceladas) throws ApplicationException;

    public String revertirPolizasCanceladas (ConsultaPolizasCanceladasVO consultaPolizasCanceladasVO) throws ApplicationException;
    
    public PagedList buscarPolizasCanceladas(int start, int limit) throws ApplicationException;
    
    public PagedList buscarPolizasCanceladas(String pv_asegurado_i, String pv_dsuniage_i, String pv_dsramo_i, String pv_nmpoliza_i, String pv_nmsituac_i, String pv_dsrazon_i,
    	   String pv_fecancel_ini_i, String pv_fecancel_fin_i, int start, int limit) throws ApplicationException;

    /**
	 *  Obtiene una lista de polizas canceladas para la exportacion a un formato predeterminado.
	 * 
	 *  @param pv_asegurado_i: parametro con el que se realiza la busqueda.
	 *  @param pv_dsuniage_i: parametro con el que se realiza la busqueda.
	 *  @param pv_dsramo_i: parametro con el que se realiza la busqueda.
	 *  @param pv_nmpoliza_i: parametro con el que se realiza la busqueda.
	 *  @param pv_nmsituac_i: parametro con el que se realiza la busqueda.
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModelPolizasCanceladas(String pv_asegurado_i, String pv_dsuniage_i, String pv_dsramo_i, String pv_nmpoliza_i, String pv_nmsituac_i, String pv_dsrazon_i,
	    	   String pv_fecancel_ini_i, String pv_fecancel_fin_i) throws ApplicationException;
	
	
	public TableModelExport getModelPolizasACancelar(String pv_asegurado_i, String pv_dsuniage_i,String pv_dsramo_i,String pv_nmpoliza_i,String pv_nmsituac_i) throws ApplicationException;

    public String calcularPrima (String cdUniEco, String cdRamo, String nmPoliza, String  feEfecto, String feCancel, String feVencim, String cdRazon) throws ApplicationException;

    public String guardarCancelacion (List<RehabilitacionManual_PolizaVO> listaVO) throws ApplicationException;

    public String rehabilitarPoliza (String cdUniEco, String cdRamo, String estado, String nmPoliza, String feEfecto, String feVencim, String feCancel,
            String feReInst, String cdRazon, String cdPerson, String cdMoneda, String nmCancel, String comentarios, String nmSuplem) throws ApplicationException;
    
    public String guardarPolizaMaestra(Map<String,String> param,String endPointName) throws ApplicationException;
    
    public String rehabilitarPolizaMasiva (List<ConsultaPolizasCanceladasVO> rehabGrillaList) throws ApplicationException;

    public PagedList buscarPolizasCanceladasRecibosDetalle( String pv_cdUnieco_i,String pv_cdRamo_i ,String pv_Estado_i ,String  pv_NmPoliza_i,String pv_nmRecibo_i, int start,int  limit) throws ApplicationException;

   
}






