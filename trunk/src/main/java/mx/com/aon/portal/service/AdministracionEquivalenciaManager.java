package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.Tabla_EquivalenciaVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;


/**
 * Interface de servicios para Agrupacion de Polizas.
 *
 */
public interface AdministracionEquivalenciaManager{

	/**
	 *  @param start, limit: marcan el rango de la lista a obtener para la paginacion del grid.

	 *  @return un objeto PagedLis con un conjunto de registros.
	 *  
	 *  @throws ApplicationException
	 */
 //   public PagedList buscarAgrupacionPolizas(String cliente, String tipoRamo, String tipoAgrupacion, String aseguradora, String producto, int start, int limit ) throws ApplicationException;
    
 //   public String agregarAgrupacionPoliza(AgrupacionPolizaVO agrupacionPolizaVO) throws ApplicationException;
    
    /**
	 *  Actualiza una Tabla de Equivalencia.
	 * 
	 *  @param Tabla_EquivalenciaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */

    
    public String GuardarEquiv(Tabla_EquivalenciaVO tabla_EquivalenciaVO)throws ApplicationException;

    
    public String borrarEquivalencia(String pv_country_code_i,String pv_nmtabla_i, String pv_cdsistema_i,String pv_otclave01acw_i,String pv_otclave01ext_i)throws ApplicationException;
    
    public PagedList obtenerEquivalencia (String pv_country_code_i, String pv_cdsistema_i,String pv_cdtablaacw_i, int start, int limit) throws ApplicationException;
    
    public PagedList obtenerReporte ( int start, int limit) throws ApplicationException;
    
    public PagedList obtieneReporte ( int start, int limit) throws ApplicationException;
    
    public PagedList obtenerCatExterno (String pv_country_code_i, String pv_cdsistema_i,String pv_otclave01_i, String pv_otvalor_i,String pv_cdtablaext_i, int start, int limit) throws ApplicationException;
    
    public Tabla_EquivalenciaVO getTablaEquivalente(String pv_country_code_i, String pv_cdsistema_i, String pv_cdtablaacw_i) throws ApplicationException;
    
    public WrapperResultados generarReporteTablasNoConciliadas ( ) throws ApplicationException;
 
}
