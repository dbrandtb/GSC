package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.ValorDefectoAtributosOrdenVO;

/**
 * Interface con servicios para Valores por defectos
 *
 */
public interface ValoresDefectoAtributosManager {
	
	/**
	 * Metodo que obtiene un unico registro valor por defecto para mostrar en el encabezado de la pantalla.
	 * 
	 * @param cdFormatoOrden
	 * @param cdSeccion
	 * @param cdAtribu
	 * 
	 * @return ValorDefectoAtributosOrdenVO
	 * 
	 * @throws ApplicationException
	 */
	public ValorDefectoAtributosOrdenVO obtenerValorDefectoAtributosOrden(String cdFormatoOrden, String cdSeccion, String cdAtribu) throws ApplicationException;
	
	/**
	 * Metodo que realiza la actualizacion de un valor por defecto.
	 * 
	 * @param cdFormatoOrden
	 * @param cdSeccion
	 * @param cdAtribu
	 * @param cdExpres
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String guardarValorDefectoAtributosOrden(String cdFormatoOrden, String cdSeccion, String cdAtribu, String cdExpres) throws ApplicationException;
}
