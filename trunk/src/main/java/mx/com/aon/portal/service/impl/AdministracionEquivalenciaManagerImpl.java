package mx.com.aon.portal.service.impl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.OperacionCATVO;
import mx.com.aon.catbo.service.OperacionCATManager;
import mx.com.aon.portal.model.Tabla_EquivalenciaVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.service.AdministracionEquivalenciaManager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import mx.com.aon.catbo.service.*;
/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements NotificacionesManager
 * 
 * @extends AbstractManager
 */
public class AdministracionEquivalenciaManagerImpl extends AbstractManagerJdbcTemplateInvoke implements  AdministracionEquivalenciaManager {
	   
      
    @SuppressWarnings("unchecked")
	public String borrarEquivalencia(String pv_country_code_i,String pv_nmtabla_i, String pv_cdsistema_i,String pv_otclave01acw_i,String pv_otclave01ext_i)throws ApplicationException {
   
    HashMap map = new HashMap();
	
    map.put("pv_country_code_i",pv_country_code_i);
    map.put("pv_nmtabla_i",pv_nmtabla_i);
    map.put("pv_cdsistema_i",pv_cdsistema_i);
    map.put("pv_otclave01acw_i",pv_otclave01acw_i);   
    map.put("pv_otclave01ext_i",pv_otclave01ext_i);
		
    WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_EQUIVALENCIA");
    return res.getMsgText();
}
    
    //      GUARDA LA EQUIVALENCIA ENTRE EL SISTEMA EXTERNO Y EL AON CAT WEB
    
  public String GuardarEquiv( Tabla_EquivalenciaVO tabla_EquivalenciaVO ) throws ApplicationException {
	  
		HashMap map = new HashMap();

		map.put("pv_country_code_i",tabla_EquivalenciaVO.getCountry_code());
	    map.put("pv_cdsistema_i", tabla_EquivalenciaVO.getCdsistema());
	    map.put("pv_cdtablaacw_i",tabla_EquivalenciaVO.getCdtablaacw());
	    map.put("pv_nmtabla_i", tabla_EquivalenciaVO.getNmtabla());
	    
	    map.put("pv_otclave01acw_i",tabla_EquivalenciaVO.getOtclave01acw());
	    map.put("pv_otclave01ext_i",tabla_EquivalenciaVO.getOtclave01ext());
	    map.put("pv_otclave02acw_i", tabla_EquivalenciaVO.getOtclave02acw());
	    map.put("pv_otclave02ext_i", tabla_EquivalenciaVO.getOtclave02ext());
	    
	    map.put("pv_otclave03acw_i", tabla_EquivalenciaVO.getOtclave03acw());
	    map.put("pv_otclave03ext_i", tabla_EquivalenciaVO.getOtclave03ext());
	    map.put("pv_otclave04acw_i", tabla_EquivalenciaVO.getOtclave04acw());
	    map.put("pv_otclave04ext_i", tabla_EquivalenciaVO.getOtclave04ext() );
	    
	    map.put("pv_otclave05acw_i", tabla_EquivalenciaVO.getOtclave05acw());
	    map.put("pv_otclave05ext_i", tabla_EquivalenciaVO.getOtclave05ext() );
	    
	    WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_EQUIVALENCIA");
	    return res.getMsgText();
		
  } 

// OBTIENE LA EQUIVALENCIA ENTRE SISTEMA Y LO MUESTRA EN LAS GRILLAS
  
@SuppressWarnings("unchecked")
public PagedList obtenerEquivalencia (String pv_country_code_i, String pv_cdsistema_i,String pv_cdtablaacw_i, int start, int limit) throws ApplicationException {

	HashMap map = new HashMap();
	
	map.put("pv_country_code_i",pv_country_code_i);
	map.put("pv_cdsistema_i",pv_cdsistema_i);
	map.put("pv_cdtablaacw_i", pv_cdtablaacw_i);
	
	return pagedBackBoneInvoke(map,"OBTIENE_EQUIVALENCIA", start, limit);
}


//OBTIENE LA TABLA DEL CATALOGO DE SISTEMA EXTERNO

public Tabla_EquivalenciaVO getTablaEquivalente(String pv_country_code_i, String pv_cdsistema_i, String pv_cdtablaacw_i) throws ApplicationException{
	
    HashMap map= new HashMap();
	map.put("pv_country_code_i",pv_country_code_i);
	map.put("pv_cdsistema_i",pv_cdsistema_i);
	map.put("pv_cdtablaacw_i",pv_cdtablaacw_i);
	
	return (Tabla_EquivalenciaVO)getBackBoneInvoke(map,"OBTIENE_TABLA_EQUIVALENCIA");

}


public PagedList obtenerCatExterno (String pv_country_code_i, String pv_cdsistema_i,String pv_otclave01_i, String pv_otvalor_i,String pv_cdtablaext_i, int start, int limit) throws ApplicationException {

	HashMap map = new HashMap();
	
	map.put("pv_country_code_i",pv_country_code_i);
	map.put("pv_cdsistema_i",pv_cdsistema_i);
	map.put("pv_otclave01_i",pv_otclave01_i);
	map.put("pv_otvalor_i",pv_otvalor_i);
	map.put("pv_cdtablaext_i",pv_cdtablaext_i);
	
	return pagedBackBoneInvoke(map,"OBTIENE_CAT_EXTERNO", start, limit);
}


@SuppressWarnings("unchecked")
public PagedList obtenerReporte ( int start, int limit) throws ApplicationException {

	HashMap map = new HashMap();
		
	return pagedBackBoneInvoke(map,"OBTENER_REPORTE", start, limit);
}


@SuppressWarnings("unchecked")
public PagedList obtieneReporte ( int start, int limit) throws ApplicationException {

	HashMap map = new HashMap();
		
	return pagedBackBoneInvoke(map,"OBTIENE_REPORTE", start, limit);
}

public WrapperResultados generarReporteTablasNoConciliadas ( ) throws ApplicationException {

	HashMap map = new HashMap();
		
	return returnBackBoneInvoke(map,"GENERAR_REPORTE");
}

}	    
   
    
  
  
    
    
	