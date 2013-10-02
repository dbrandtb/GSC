package mx.com.aon.portal.service;

import java.util.List;

import mx.com.aon.portal.model.CheckListCuentaPreRequisitosVO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para la configuracion de los pre-requisitos de configuracion de cuenta.
 *
 */
public interface CheckListCuentaPreRequisitosManager {

	/**
	 *  Obtiene el encabezado  a partir de los parametros de entrada.
	 * 
	 *  @param codigoConfiguracion
	 *  @param codigoCliente
	 *  
	 *  @return Objeto Lista
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerEncabezados(String codigoConfiguracion, String codigoCliente) throws ApplicationException;

	/**
	 *  Obtiene tareas de secciones a partir de los parametros de entrada.
	 * 
	 *  @param codigoConfiguracion
	 *  @param codigoCliente
	 *  
	 *  @return Objeto Lista
	 */
	@SuppressWarnings("unchecked")
	public List obtenerTareasSeccion (String codigoConfiguracion, String codigoSeccion) throws ApplicationException;

	/**
	 *  Obtiene secciones a partir del codigo de configuracion.
	 * 
	 *  @param codigoConfiguracion

	 *  @return Objeto Lista
	 */
	@SuppressWarnings("unchecked")
	public List obtenerSecciones(String codigoConfiguracion) throws ApplicationException;
	
	/**
	 * Inserta Nueva o actualiza Configuración de CheckList de Cuenta
	 * @param codigoConfiguracion
	 * @param codigoCliente
	 * @param codigoSeccion
	 * @param descripcionConf
	 * @param lineaOperacion
	 * @param checkListCuentaPreRequisitosVO
	 * @throws ApplicationException
	 */
	public String guardaPreRequisito(String codigoConfiguracion,String cdPerson, String codigoCliente, String codigoSeccion, String descripcionConf, String lineaOperacion, List<CheckListCuentaPreRequisitosVO>listaTareas) throws ApplicationException;


	/**
	 *  Devuelve las tareas disponibles para esa configuracion
	 * 
	 *  @param codigoConfiguracion
	 *  
	 *  @return Objeto Lista
	 */
	public boolean isConfiguracionTareasCompleta(String codigoConfiguracion) throws ApplicationException;


}
