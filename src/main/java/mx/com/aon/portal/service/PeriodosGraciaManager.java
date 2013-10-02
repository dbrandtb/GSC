package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.PeriodosGraciaVO;
import mx.com.gseguros.exception.ApplicationException;
/**
 * Interface de servicios para el Peridos de Gracia
 *
 */
public interface PeriodosGraciaManager {

	/**
	 *  Obtiene un conjunto de periodos de gracia
	 * 
	 *  @param descripcion
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos PeriodosGraciaVO
	 *  
	 *  @throws ApplicationException
	 */
    public PagedList buscarPeriodosGracia(String descripcion, int start, int limit ) throws ApplicationException;

	/**
	 *  Elimina datos de un periodo de gracia
	 * 
	 *  @param cdTramo
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
    public String borrarPeriodosGracia(String cdTramo) throws ApplicationException;
    
	/**
	 *  Inserta o actualiza datos de los periodos de gracia
	 * 
	 *  @param periodosGraciaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
    public String agregarGuardarPeriodosGracia(PeriodosGraciaVO periodosGraciaVO) throws ApplicationException;

	/**
	 *  Obtiene configuracion de un periodo gracia en base a un parametro de entrada
	 * 
	 *  @param cdTramo
	 *  
	 *  @return Objeto PeriodosGraciaVO
	 *  
	 *  @throws ApplicationException
	 */
    public PeriodosGraciaVO getPeriodosGracia(String cdTramo) throws ApplicationException;
	
    /**
	 *  Obtiene una lista de periodos de gracia para la exportar a un formato predeterminado.
	 * 
	 *  @param descripcion
	 *  @param minimo
	 *  @param maximo
	 *  @param diasGracias
	 *  @param diasCancela
	 *      
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
    public TableModelExport getModel(String descripcion, String minimo, String maximo, String diasGracias, String diasCancela ) throws ApplicationException;

}
