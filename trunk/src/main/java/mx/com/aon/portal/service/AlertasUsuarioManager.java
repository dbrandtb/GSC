package mx.com.aon.portal.service;

import java.util.List;

import mx.com.aon.portal.model.AlertaUsuarioVO;
import mx.com.aon.portal.model.EmailVO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para el action de la pantalla Alertas de Usuarios
 */
public interface AlertasUsuarioManager {
	
	/**
	 * Metodo que busca y obtiene un unico registro de alertas de usuarios.
	 * 
	 * @param cdIdUnico
	 * @param cdIdConfAlerta
	 * 
	 * @return AlertaUsuarioVO
	 * 
	 * @throws ApplicationException
	 */
	public AlertaUsuarioVO getAlertaUsuario (String cdIdUnico, String cdIdConfAlerta) throws ApplicationException;

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
	public PagedList buscarAlertasUsuario (String dsUsuario, String dsNombre, String dsProceso, String dsTipRam, String dsUniEco, String dsRol, int start, int limit) throws ApplicationException;
	
	/**
	 * Metodo que permite insertar una nueva configuracion de alertas automatico o actualizar
	 *  una editada por el usuario.
	 *  
	 * @param cdIdUnico
	 * @param cdIdConfAlerta
	 * @param cdElemento
	 * @param cdUsuario
	 * @param cdUniEco
	 * @param nmPoliza
	 * @param nmRecibo
	 * @param cdProceso
	 * @param feUltimoEvento
	 * @param feSiguienteEvento
	 * @param feVencimiento
	 * @param dsCorreo
	 * @param dsMensaje
	 * @param fgMandarPantalla
	 * @param fgPermanecePantalla
	 * @param nmFrecuencia
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	public String guardarAlertaUsuario (String cdIdUnico, String cdIdConfAlerta, String cdElemento, String cdUsuario, String cdUniEco, String nmPoliza, String nmRecibo, String cdProceso, String feUltimoEvento, String feSiguienteEvento, String feVencimiento, String dsCorreo, String dsMensaje, String fgMandarPantalla, String fgPermanecePantalla, String nmFrecuencia) throws ApplicationException;

	public String borrarAlerta(String cdIdUnico, String cdIdConfAlerta)throws ApplicationException;
	
	/**
	 * Metodo que se utiliza para obtener las alertas que seran enviadas por email.
	 * 
	 * @throws ApplicationException
	 */
	public void getAlertasEmail() throws ApplicationException;
}
