package mx.com.gseguros.portal.dao;

import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.general.model.BaseVO;

/**
 * 
 * @author Ricardo
 * 
 */
public interface ProcesoResultadoDAO {

	/**
	 * Obtiene el mensaje de respuesta de los servicios de Acceso a Datos
	 * 
	 * @param msgId
	 * @param log
	 * @param cdUsuario
	 * @param dsPrograma
	 * @return Objeto con la clave y descripcion del mensaje
	 */
	public BaseVO obtieneMensaje(String msgId, String log, String cdUsuario, String dsPrograma) 
			throws DaoException;

}