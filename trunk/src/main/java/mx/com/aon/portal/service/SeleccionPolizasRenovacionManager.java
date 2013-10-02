package mx.com.aon.portal.service;

import mx.com.aon.portal.model.PolizasRenovacionVO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para SeleccionPolizasRenovacionAction.
 *
 */
public interface SeleccionPolizasRenovacionManager {
	
	/**
	 * Metodo que realiza la seleccion las polizas que son candidatas para el proceso de renovacion.
	 * 
	 * @param polizasVO
	 * 
	 * @return
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String ejecutarSeleccionPolizasParaRenovacion(PolizasRenovacionVO polizasVO) throws ApplicationException;

}
