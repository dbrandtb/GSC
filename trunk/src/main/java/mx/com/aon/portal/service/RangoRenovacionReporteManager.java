package mx.com.aon.portal.service;


import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.RangoRenovacionReporteVO;
import mx.com.gseguros.exception.ApplicationException;


public interface RangoRenovacionReporteManager  {
	
	/**
	 *  Obtiene un conjunto de rangos de renovacion
	 * 
	 *  @param cdRenova
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos RangoRenovacionReporteVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarRangosRenovacion(String cdRenova, int start, int limit )throws ApplicationException;
	
	/**
	 *  Obtiene configuracion del encabezado de rango de renovacion
	 * 
	 *  @param cdRenova
	 *  
	 *  @return Objeto RangoRenovacionReporteVO
	 *  
	 *  @throws ApplicationException
	 */
    public RangoRenovacionReporteVO getEncabezadoRangoRenovacion(String cdRenova) throws ApplicationException;
   
	/**
	 *  Elimina datos de un rango de renovacion
	 * 
	 *  @param cdRenova
	 *  @param cdRol
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
    public String borrarRangoRenovacion(String cdRenova, String cdRango) throws ApplicationException;
    
	/**
	 *  Obtiene configuracion de rango de renovacion
	 * 
	 *  @param cdRenova
	 *  @param cdRango
	 *  
	 *  @return Objeto RangoRenovacionReporteVO
	 *  
	 *  @throws ApplicationException
	 */
    public RangoRenovacionReporteVO getRangoRenovacion(String cdRenova, String cdRango) throws ApplicationException;
    
	/**
	 *  Inserta o actualiza datos rangos de renovacion
	 * 
	 *  @param configurarAccionRenovacionVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
    public String agregarGuardarRangoRenovacion(RangoRenovacionReporteVO rangoRenovacionReporteVO) throws ApplicationException;
    
    /**
	 *  Obtiene una lista de atributos formas de calculo variable
	 * 
	 *  @param cdRenova
	 *      
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
    public TableModelExport getModel(String cdRenova) throws ApplicationException;
}
