package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.MensajeErrorVO;

/**
 * Interface de servicios para action de Mensajes de Error
 *
 */
public interface MensajesErrorManager {
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros de Mensajes de error.
	 * 
	 * @param cdError
	 * @param dsMensaje
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	public PagedList buscarMensajes (String cdError, String dsMensaje, int start, int limit) throws ApplicationException;
	
	/**
	 * Metodo que busca y obtiene un unico registro de Mensajes de error.
	 * 
	 * @param cdError
	 * 
	 * @return MensajeErrorVO
	 * 
	 * @throws ApplicationException
	 */
	public MensajeErrorVO getMensajeError (String cdError) throws ApplicationException;
	
	/**
	 * Metodo que inserta un nuevo o actualiza un registro de mensaje de error.
	 * 
	 * @param cdError
	 * @param dsMensaje
	 * @param cdTipo
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String guardarMensajeError (String cdError, String dsMensaje, String cdTipo) throws ApplicationException;
	
	/**
	 * Obtiene un conjunto de registros de mensajes de error y exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * 
	 * @return TableModelExport
	 * 
	 * @throws Exception
	 */
	public TableModelExport getModel(String cdError, String dsMensaje) throws ApplicationException;
}
