package mx.com.aon.portal.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlParameter;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.DescuentoDetVolumenVO;
import mx.com.aon.portal.model.DescuentoProductoVO;
import mx.com.aon.portal.model.DescuentoVO;
import mx.com.aon.portal.model.DetalleProductoVO;
import mx.com.aon.portal.service.AdministrarCatalogSistemaExternoManager;
import mx.com.aon.portal.service.DescuentosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.model.AdministraCatalogoVO;


/**
 * Implementa la interface de servicios para descuento.
 *
 */
public class AdministrarCatalogSistemaExternoManagerImpl extends AbstractManagerJdbcTemplateInvoke implements AdministrarCatalogSistemaExternoManager {
	

	/**
	 *  Obtiene Datos Tcataex.
	 *  Usa el Store Procedure PKG_EQUIVALENCIA.P_OBTIENE_TCATAEXT.
	 *  @return Objeto Lista
	 */	
	@SuppressWarnings({ "unchecked", "unchecked" })
	public PagedList buscarDatosTcataex(int start, int limit, String pv_country_code_i, String pv_cdsistema_i,String pv_cdtablaext_i)throws ApplicationException
	{
		HashMap map = new HashMap();
		map.put("pv_country_code_i", pv_country_code_i);
		map.put("pv_cdsistema_i", pv_cdsistema_i);
		map.put("pv_cdtablaext_i", pv_cdtablaext_i);
		
		String endpointName = "OBTIENE_TCATAEX";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	/**
	 *  Obtiene Datos Tcataex.
	 *  Usa el Store Procedure PKG_EQUIVALENCIA.P_OBTIENE_TCATAEXT.
	 *  @return Objeto Lista
	 */	
	@SuppressWarnings({ "unchecked", "unchecked" })
	public PagedList buscarDatosTcataex(int start, int limit, String pv_country_code_i, String pv_cdsistema_i,String pv_cdtablaext_i,String otvalorExt)throws ApplicationException
	{
		if (logger.isDebugEnabled()) {
			logger.debug("-> AdministrarCatalogSistemaExternoManagerImpl.buscarDatosTcataex");
		}
		HashMap map = new HashMap();
		map.put("pv_country_code_i", pv_country_code_i);
		map.put("pv_cdsistema_i", pv_cdsistema_i);
		map.put("pv_cdtablaext_i", pv_cdtablaext_i);
		map.put("pv_otvalorext_i", otvalorExt);
		
		String endpointName = "OBTIENE_TCATAEX";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
    /*
     *  declareParameter(new SqlParameter("pv_country_code_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdsistema_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_otclave01_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_otvalor_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdtablaext_i", OracleTypes.VARCHAR));
     * */
	
	
	/**
	 *  
	 *  Usa el Store Procedure PKG_DSCTO.P_GUARDA_VOLUMEN_DET.
	 * 
	 *  @param administraCatalogoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */			
	@SuppressWarnings("unchecked")
	public String guardarTcataex(AdministraCatalogoVO administraCatalogoVO)  throws ApplicationException{
		HashMap map = new HashMap();
		
		map.put("pv_operacion_i", administraCatalogoVO.getOperacion());
		map.put("pv_country_code_i", administraCatalogoVO.getCodPais());
		map.put("pv_cdsistema_i", administraCatalogoVO.getCdSistema());
		map.put("pv_otclave01_i",administraCatalogoVO.getOtClave() );
		map.put("pv_otvalor_i", administraCatalogoVO.getOtValor());
		map.put("pv_otclave02_i", administraCatalogoVO.getOtClave2());
		map.put("pv_otclave03_i", administraCatalogoVO.getOtClave3());
		map.put("pv_otclave04_i", administraCatalogoVO.getOtClave4());
		map.put("pv_otclave05_i", administraCatalogoVO.getOtClave5());
		map.put("pv_cdtablaext_i", administraCatalogoVO.getCdTablaExt());
	
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_TCATAEX");
        return res.getMsgText();
	}
/*
 *   declareParameter(new SqlParameter("pv_country_code_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cdsistema_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_otclave01_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_otvalor_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_otclave02_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_otclave03_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_otclave04_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_otclave05_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cdtablaext_i", OracleTypes.VARCHAR));
          
 * */
	
	/**
	 *  Realiza la eliminacion de un registro de la tabla Tcataex .
	 *  Usa el Store Procedure PKG_EQUIVALENCIA.P_BORRA_TCATAEXT.
	 * 
	 *  @param 
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */			
    @SuppressWarnings("unchecked")
	public String borrarTcataex(String pv_country_code_i, String pv_nmtabla_i,String pv_cdsistema_i,String pv_otclave01_i,String pv_otvalor_i,String pv_cdtablaext_i) throws ApplicationException
    {
			HashMap map = new HashMap();
			map.put("pv_country_code_i", pv_country_code_i);
			map.put("pv_nmtabla_i", pv_nmtabla_i);
			map.put("pv_cdsistema_i", pv_cdsistema_i);
			map.put("pv_otclave01_i", pv_otclave01_i);
			map.put("pv_otvalor_i", pv_otvalor_i);
			map.put("pv_cdtablaext_i", pv_cdtablaext_i);
			
            WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_TCATAEX");
            return res.getMsgText();
    }
	
    /*
     * pv_country_code_i   IN TCATAEXT.COUNTRY_CODE%TYPE,
                                pv_nmtabla_i        IN MEQUVTAB.NMTABLA%TYPE,
                                pv_cdsistema_i      IN TCATAEXT.CDSISTEMA%TYPE,
                                pv_otclave01_i      IN TCATAEXT.OTCLAVE01%TYPE,
                                pv_otvalor_i        IN TCATAEXT.OTVALOR%TYPE,
                                pv_cdtablaext_i
     **/
		

	/**
	  * Obtiene un conjunto de descuento y exporta el resultado en Formato PDF, Excel, CSV, etc.
	  * Usa el Store Procedure PKG_DSCTO.P_OBTIENE_DESCTOS
	  * 
	  * @return success
	  * 
	  * @throws Exception
	  */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String pv_country_code_i, String pv_cdsistema_i,String pv_cdtablaext_i) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("pv_country_code_i", pv_country_code_i);
		map.put("pv_cdsistema_i", pv_cdsistema_i);
		map.put("pv_cdtablaext_i", pv_cdtablaext_i);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "TCATAEX_EXPORT");// OBTIENE_DESCUENTOS_EXPORT

		model.setInformation(lista);
		model.setColumnName(new String[]{"Clave 01","OTVALOR","Clave 02","Clave 03","Clave 04","Clave 05"});
		
		return model;
	}
	
	/**
	  * Obtiene un conjunto de descuento y exporta el resultado en Formato PDF, Excel, CSV, etc.
	  * Usa el Store Procedure PKG_DSCTO.P_OBTIENE_DESCTOS
	  * 
	  * @param dsDscto
	  * @param otValor
	  * @param dsCliente
	  * @param otvalorExt
	  * @return success
	  * 
	  * @throws Exception
	  */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String pv_country_code_i, String pv_cdsistema_i,String pv_cdtablaext_i,String otvalorExt) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("pv_country_code_i", pv_country_code_i);
		map.put("pv_cdsistema_i", pv_cdsistema_i);
		map.put("pv_cdtablaext_i", pv_cdtablaext_i);
		map.put("pv_otvalorext_i", otvalorExt);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "TCATAEX_EXPORT");// OBTIENE_DESCUENTOS_EXPORT

		model.setInformation(lista);
		model.setColumnName(new String[]{"Clave 01","OTVALOR","Clave 02","Clave 03","Clave 04","Clave 05"});
		
		return model;
	}

	//  codPais, cdSistem,  otClave, otValor, cdTabla

}

