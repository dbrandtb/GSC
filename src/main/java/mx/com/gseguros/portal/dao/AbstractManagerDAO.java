package mx.com.gseguros.portal.dao;

import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.general.model.BaseVO;
import mx.com.gseguros.utils.Constantes;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.StoredProcedure;

import com.opensymphony.xwork2.ActionSupport;

public abstract class AbstractManagerDAO extends JdbcDaoSupport {

	private static Logger logger = Logger.getLogger(AbstractManagerDAO.class);
	
	private ProcesoResultadoDAO procesoResultadoDAO;

	
    /**
     * Invocaci&oacute;n a un Stored Procedure
     * @param storedProcedure Entidad que representa al SP a invocar  
     * @param parameters      Par&aactue;metros para invocar del SP
     * @return Map con los objetos recuperados de la consulta al SP
     * @throws DaoException
     */
    public Map<String, Object> ejecutaSP(StoredProcedure storedProcedure, Map parameters) throws DaoException {
		try {
			Map<String, Object> mapResult = storedProcedure.execute(parameters);
			
	        BaseVO mensajeRespuesta = traduceMensaje(mapResult);
	        mapResult.put("msg_id", mensajeRespuesta.getKey());
	        mapResult.put("msg_title", mensajeRespuesta.getValue());
	        logger.info("MsgTitle="+ mensajeRespuesta.getKey());
	        logger.info("MsgText="+  mensajeRespuesta.getValue());
	        
	        return mapResult;
	        
		} catch (Exception ex) {
			throw new DaoException("Error inesperado en el acceso a los datos", ex);
		}
    }
    
    
    /**
     * 
     * @param mapResult
     * @return
     * @throws Exception
     */
    private BaseVO traduceMensaje(Map<String, Object> mapResult) throws Exception {

    	String msgId = mapResult.get("pv_msg_id_o") != null ? mapResult.get("pv_msg_id_o").toString() : Constantes.MSG_ID_OK;  
        String msgTitle = mapResult.get("pv_title_o")  != null ? mapResult.get("pv_title_o").toString()  : Constantes.MSG_TITLE_OK;
    	
    	// Buscar el mensaje en la BD si la clave no se encuentra en el properties:
    	ActionSupport actionSupport = new ActionSupport();
        if (actionSupport.getText(msgId) != null) {
        	//logger.info("property " + msgId + "=" + actionSupport.getText(msgId));
        	return new BaseVO(msgId, actionSupport.getText(msgId));
        } else {
        	
        	BaseVO mensajeRespuesta = procesoResultadoDAO.obtieneMensaje(msgId, "0", null, null);
        	
        	if (mensajeRespuesta == null || StringUtils.isBlank(mensajeRespuesta.getKey()) || StringUtils.isBlank(mensajeRespuesta.getValue())) {
				String msgException = "No se encontró el mensaje de respuesta del servicio de datos, verifique los parámetros de salida";
				logger.error(msgException);
				throw new ApplicationException(msgException);
			}
			if (mensajeRespuesta.getKey().equals(Constantes.MSG_TITLE_ERROR)) {
				String msgException = "Error de SP: " + mensajeRespuesta.getValue(); 
				logger.error(msgException);
				throw new ApplicationException(msgException);
			}
			return new BaseVO(mensajeRespuesta.getKey(), mensajeRespuesta.getValue());
        }
    }
    
    
    /**
     * 
     * @param procesoResultadoDAO
     */
	public void setProcesoResultadoDAO(ProcesoResultadoDAO procesoResultadoDAO) {
		this.procesoResultadoDAO = procesoResultadoDAO;
	}

}