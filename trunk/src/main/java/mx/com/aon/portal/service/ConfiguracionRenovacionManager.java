package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfiguracionClienteVO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para el Action de Configuracion de la Renovacion.
 *
 */
public interface ConfiguracionRenovacionManager {
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros con los datos a usar en la 
	 * pantalla de Configuracion de la renovacion.
	 * 
	 * @param dsElemen
	 * @param dsTipoRenova
	 * @param dsUniEco
	 * @param dsRamo
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	public PagedList obtenerClientesYTiposDeRenovacion(String dsElemen, String dsTipoRenova, String dsUniEco, String dsRamo, int start, int limit) throws ApplicationException;
	
	/**
	 * Metodo que elimina un registro de datos de Configuracion de la Renovacion.
	 * 
	 * @param cdRenova
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String eliminarConfiguracion(String cdRenova) throws ApplicationException;
	
	/**
	 * Metodo que obtiene un conjunto de registros de configuracion de la renovacion
	 *  y exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * 
	 * @return TableModelExport
	 * 
	 * @throws Exception
	 */
	public TableModelExport getModel(String dsElemen, String dsTipoRenova, String dsUniEco, String dsRamo) throws ApplicationException;
	
	/**
	 * Metodo que busca y obtiene un unico registro con los datos a usar en la 
	 * pantalla de Configuracion de la renovacion.
	 * 
	 * @param cdRenova
	 * 
	 * @return ConfiguracionClienteVO
	 * 
	 * @throws ApplicationException
	 */
	public ConfiguracionClienteVO getConfiguracionCliente(String cdRenova) throws ApplicationException;
	
	/**
	 * Metodo que inserta un nuevo registro o actualiza un registro editado en pantalla.
	 * 
	 * @param cdRenova
	 * @param cdPerson
	 * @param cdElemento
	 * @param cdUniEco
	 * @param cdRamo
	 * @param cdTipoRenova
	 * @param cdDiasAnticipacion
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String guardaConfiguracion(String cdRenova, String cdPerson, String cdElemento, String cdUniEco, String cdRamo,String cdTipoRenova, String cdDiasAnticipacion, String continuaNum) throws ApplicationException;
	
	/**
	 * Metodo que valida la existencia de roles en acciones.
	 * 
	 * @param cdRenova
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String getValidacionRoles(String cdRenova) throws ApplicationException;
}