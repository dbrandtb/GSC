package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.RequisitoRehabilitacionVO;

/**
 * Interface con servicios de abm para la pantalla RequisitosRehabilitacion.
 *
 */
public interface RequisitosRehabilitacionManager {

	/**
	 *  Obtiene un conjunto de requisistos de rehabilitacion.
	 * 
	 *  @param dsElemen
	 *  @param dsUnieco
	 *  @param dsRamo
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos RequisitoRehabilitacionVO
	 *  
	 *  @throws ApplicationException
	 */
    public PagedList buscarRequisitosRehabilitacion(String dsElemen, String dsUnieco, String dsRamo, int start, int limit ) throws ApplicationException;

	/**
	 *  Inserta o actualiza datos de los requisistos de rehabilitacion
	 * 
	 *  @param requisitoRehabilitacionVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
    public String agregarGuardarRequisitoRehabilitacion(RequisitoRehabilitacionVO requisitoRehabilitacionVO) throws ApplicationException;

	/**
	 *  Elimina datos de un requisistos de rehabilitacion
	 * 
	 *  @param cdRequisito
	 *  @param cdUnieco
	 *  @param cdRamo
	 *  @param cdElemento
	 *  @param cdDocXcta
	 *   
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
    public String borrarRequisitoRehabilitacion(String cdRequisito, String cdUnieco, String cdRamo, String cdElemento, String cdDocXcta) throws ApplicationException;

	/**
	 *  Obtiene configuracion de requisistos de rehabilitacion en base a un parametro de entrada
	 * 
	 *  @param cdRequisito
	 *  
	 *  @return Objeto RequisitoRehabilitacionVO
	 *  
	 *  @throws ApplicationException
	 */
    public RequisitoRehabilitacionVO getRequisitoRehabilitacion(String cdRequisito) throws ApplicationException;
    
    /**
	 *  Obtiene una lista requisistos de rehabilitacion para exportar a un formato predeterminado
	 * 
	 *  @param dsElemen
	 *  @param dsUnieco
	 *  @param dsRamo
	 *      
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
    public TableModelExport getModel(String dsElemen, String dsUnieco, String dsRamo) throws ApplicationException;
}
