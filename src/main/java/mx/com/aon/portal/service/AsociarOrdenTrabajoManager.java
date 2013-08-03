package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.AsociarOrdenTrabajoVO;

/**
 * Interface con servicios de abm para la pantalla de Asociar orden de trabajo.
 *
 */
public interface AsociarOrdenTrabajoManager {
	
	/**Metodo que busca y obtiene un unico registro con datos de formato por cuenta.
	 * 
	 * @param cdAsocia
	 * 
	 * @return AsociarOrdenTrabajoVO
	 * 
	 * @throws ApplicationException
	 */
	public AsociarOrdenTrabajoVO obtenerFormatoxCuenta(String cdAsocia) throws ApplicationException;
	
	/**
	 * Metodo que realiza la actualizacion o insercion de una asociacion de orden de trabajo.
	 * 
	 * @param asociarOrdenTrabajoVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
    public String guardarAsociacionOrdenTrabajo(AsociarOrdenTrabajoVO asociarOrdenTrabajoVO) throws ApplicationException;

}
