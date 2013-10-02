package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface con los servicios para la pantalla de Configuracion de Endosos
 *
 */
public interface ConfiguracionEndososManager {

	/**
	 * Metodo que busca y obtiene un conjunto de registros con tipos de suplemento para la 
	 * pantalla de Configuracion de Endosos
	 * 
	 * @param start
	 * @param limit
	 * @param cdTipSup
	 * @param dsTipSup
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	public PagedList obtenerTiposSuplementos(int start, int limit,String cdTipSup, String dsTipSup) throws ApplicationException;
	
	/**
	 * Metodo que realiza la eliminacion de un tipo de suplemento seleccionado en pantalla
	 * 
	 * @param cdTipSup
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.

	 * @throws ApplicationException
	 */
	public String borrarTipoSuplemento(String cdTipSup) throws ApplicationException;
	
	/**
	 * Metodo que inserta un nuevo tipo de suplemento o actualiza un tipo de suplemento
	 * editado en pantalla.
	 * 
	 * @param cdTipSup
	 * @param dsTipSup
	 * @param swTariFi
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String guardarOActualizarTipoSuplemento(String cdTipSup, String dsTipSup, String swTariFi) throws ApplicationException;
	
	/**
	 * Metodo que obtiene un conjunto de registros de tipos de suplementos para ser 
	 * exportados en formato PDF, XSL, TXT, etc.
	 * 
	 * @param dsTipSup
	 * 
	 * @return TableModelExport
	 * 
	 * @throws ApplicationException
	 */
	public TableModelExport getModel(String dsTipSup) throws ApplicationException;
}