package mx.com.gseguros.portal.dao;

import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.general.model.BaseVO;
import mx.com.gseguros.utils.Constantes;

import org.apache.commons.lang3.StringUtils;
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> ejecutaSP(StoredProcedure storedProcedure, Map parameters) throws DaoException {
		try {
			Map<String, Object> mapResult = storedProcedure.execute(parameters);
			
	        BaseVO mensajeRespuesta = traduceMensaje(mapResult);
	        mapResult.put("msg_id", mensajeRespuesta.getKey());
	        mapResult.put("msg_title", mensajeRespuesta.getValue());
	        
	        return mapResult;
	        
		} catch (Exception ex) {
			throw new DaoException(ex.getMessage(), ex);
		}
    }
    
    
    /**
     * Obtiene el mensaje asociado al msgId del servicio de datos
     * @param mapResult
     * @return 
     * @throws Exception
     */
    private BaseVO traduceMensaje(Map<String, Object> mapResult) throws Exception {
    	
    	String msgId    = mapResult.get("pv_msg_id_o") != null ? mapResult.get("pv_msg_id_o").toString() : "";  
        String msgTitle = mapResult.get("pv_title_o")  != null ? mapResult.get("pv_title_o").toString()  : "";
        
        if(StringUtils.isBlank(msgId)){
        	msgId = mapResult.get("PV_MSG_ID_O") != null ? mapResult.get("PV_MSG_ID_O").toString() : "";  
        }
        if(StringUtils.isBlank(msgTitle)){
        	msgTitle = mapResult.get("PV_TITLE_O")  != null ? mapResult.get("PV_TITLE_O").toString()  : "";  
        }
        
        logger.info(new StringBuilder("MsgId=").append(msgId).append(" ").append("MsgTitle=").append(msgTitle));
        
        // Obtenemos el msgText a partir del msgId:
        String msgText = "";
        if(StringUtils.isNotBlank(msgId) ) {
        	// Buscamos el msgText en properties, sino lo buscamos en BD:
        	ActionSupport actionSupport = new ActionSupport();
            if (!actionSupport.getText(msgId).equals(msgId)) {
            	msgText = actionSupport.getText(msgId);
            } else {
            	msgText = procesoResultadoDAO.obtieneMensaje(msgId, "0", null, null);
            }
            
            if (StringUtils.isBlank(msgText)) {
    			String msgException = "No se encontró el mensaje de respuesta del servicio de datos, verifique los parámetros de salida";
    			logger.error(msgException);
    			throw new ApplicationException(msgException);
    		}
            
            logger.info( new StringBuilder("MsgText=").append(msgText) );
        }
        
        // Si msgTitle es de tipo ERROR, lanzamos la excepción con el msgText obtenido:
 		if (msgTitle.equals(Constantes.MSG_TITLE_ERROR)) {
 			logger.error(new StringBuilder("Error de SP: ").append(msgText));
 			throw new ApplicationException(msgText);
 		}
        
		return new BaseVO(msgId, msgText);
    }
    
    
    /**
     * 
     * @param procesoResultadoDAO
     */
	public void setProcesoResultadoDAO(ProcesoResultadoDAO procesoResultadoDAO) {
		this.procesoResultadoDAO = procesoResultadoDAO;
	}

}