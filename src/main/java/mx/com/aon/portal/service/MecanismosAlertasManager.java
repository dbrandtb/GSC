package mx.com.aon.portal.service;

import java.util.List;

import mx.com.aon.portal.model.MecanismoAlertaVO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para el action de la pantalla Alertas de Usuarios
 */
public interface MecanismosAlertasManager {
	
	

	/**
	 * Metodo que busca y obtiene un conjunto de registros de alertas de usuarios.
	 * 
	 * @param dsUsuario
	 * @param dsNombre
	 * @param dsProceso
	 * @param dsTipRam
	 * @param dsUniEco
	 * @param dsRol
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	public PagedList buscarMensajesAlertas (String cdUsuario, String cdRol,
			String cdElemento, String cdProceso, String fecha, String cdRamo, int start, int limit) throws ApplicationException;
	
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros de alertas de usuarios para PANTALLA.
	 * 
	 * @param cdUsuario
	 * @param cdRol
	 * @param cdElemento
	 * @param cdProceso
	 * @param fecha
	 * @param cdRamo
	 * @return
	 * @throws ApplicationException
	 */
	public List<MecanismoAlertaVO> buscarMensajesAlertasPantalla (String cdUsuario, String cdRol,
			String cdElemento, String cdProceso, String fecha, String cdRamo) throws ApplicationException;
	
	
}
