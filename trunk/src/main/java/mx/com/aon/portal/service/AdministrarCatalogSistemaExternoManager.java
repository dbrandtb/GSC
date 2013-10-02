package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.AdministraCatalogoVO;
import mx.com.gseguros.exception.ApplicationException;


/**
 * Interface de servicios para la configuracion de descuentos.
 *
 */
public interface AdministrarCatalogSistemaExternoManager {

	
	/**
	 *  Obtiene Datos Tcataex.
	 */	
	public PagedList buscarDatosTcataex(int start, int limit, String pv_country_code_i, String pv_cdsistema_i,String pv_cdtablaext_i)throws ApplicationException;
	
	/**
	 *  Obtiene Datos Tcataex.
	 */	
	public PagedList buscarDatosTcataex(int start, int limit, String pv_country_code_i, String pv_cdsistema_i,String pv_cdtablaext_i,String otvalorExt)throws ApplicationException;
		
	/**
	 *  Inserta registros en la tabla Tcataex.
	
	 */		
	
	public String guardarTcataex(AdministraCatalogoVO administraCatalogoVO) throws ApplicationException;
	
	/**
	 *  Realiza la eliminacion de un registro de la tabla Tcataex .

	 */		
	public String borrarTcataex(String pv_country_code_i, String pv_nmtabla_i,String pv_cdsistema_i,String pv_otclave01_i,String pv_otvalor_i,String pv_cdtablaext_i) throws ApplicationException;
		

	/**
	  * @param dsDscto
	  * @param otValor
	  * @param dsCliente
	  * 
	  * @return success
	  */
	public TableModelExport getModel(String pv_country_code_i, String pv_cdsistema_i,String pv_cdtablaext_i) throws ApplicationException ;
	
	/**
	  * @param dsDscto
	  * @param otValor
	  * @param dsCliente
	  * @param otvalorExt
	  * 
	  * @return success
	  */
	public TableModelExport getModel(String pv_country_code_i, String pv_cdsistema_i,String pv_cdtablaext_i,String otvalorExt) throws ApplicationException ;
}
