package mx.com.aon.portal.service;

import mx.com.aon.portal.model.AgrupacionPapel_AgrupacionVO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interfaz de servicios que atiende requerimientos de los Action de
 *  agrupacion por papel.
 *
 */
public interface AgrupacionPapelManager {
	
	/**
	 * Metodo que realiza la busqueda y obtiene un conjunto de registros.
	 * 
	 * @param codConfiguracion
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	public PagedList buscarPapeles (String codConfiguracion, int start, int limit) throws ApplicationException;
	
	/**
	 * Metodo que realiza la busqueda y obtiene un unico registro de agrupacion por papel.
	 * 
	 * @param codConfiguracion
	 * 
	 * @return AgrupacionPapel_AgrupacionVO
	 * 
	 * @throws ApplicationException
	 */
	public AgrupacionPapel_AgrupacionVO getAgrupacionPapel (String codigoConfiguracion) throws ApplicationException;
	
	/**
	 * Metodo que realiza la actualizacion de un registro modificado de Agrupacion por papel.
	 * 
	 * @param codigoConfiguracion
	 * @param codigoAgrRol
	 * @param codigoNivel
	 * @param codigoRol
	 * @param codigoAseguradora
	 * @param codigoProducto
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String guardarAgrupacionPapel(String codigoConfiguracion, String codigoAgrRol, String codigoNivel, String codigoRol, String codigoAseguradora, String codigoProducto, String cdPolMtra) throws ApplicationException;
	 
	public String borrarRol(String pv_agrupacion_i, String pv_cdagrrol_i)throws ApplicationException;
	    	
	
	
}
