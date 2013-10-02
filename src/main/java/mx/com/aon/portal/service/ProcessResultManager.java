/**
 * 
 */
package mx.com.aon.portal.service;

import java.util.HashMap;
import java.util.Map;

import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

/**
 * @author eflores
 * @date 07/07/2008
 *
 */
public class ProcessResultManager {

    private  static String ERROR_MSG_ID = "100000";
    private  static String ERROR_MSG_TITLE = "1";
    private  static String ERROR_STORE_PROCEDURE = "OBTIENE_MENSAJE";//"PKG_TRADUC.P_OBTIENE_MENSAJE";

    /**
     * Logger de la clase para monitoreo y registro de comportamiento
     */
    protected static Logger logger = Logger.getLogger(ProcessResultManager.class);
    /**
     * Mapa en el cual se introducen los Manager's para ser extraidos y utilizados como servicios
     */
    protected Map<String, Endpoint> endpoints;

    public void setEndpoints(Map<String, Endpoint> endpoints) {this.endpoints = endpoints;}


    public  WrapperResultados processResultMessageId(WrapperResultados res) throws ApplicationException {

            String msgId = String.valueOf(res.getMsgId());

            if (msgId != null && !msgId.equals("") && !msgId.equals("null")) {
                WrapperResultados messageResult = getResultMessage(msgId);

                if (messageResult==null || messageResult.getMsgTitle()== null || messageResult.getMsgTitle().equals("")  ||
                       messageResult.getMsgText()== null || messageResult.getMsgText().equals("") ) {
                    logger.error("No se puede procesar el mensaje de error ");
                    throw new ApplicationException("No se encuentra el mensaje de error asociado");
                }

                if (messageResult.getMsgTitle().equals(ERROR_MSG_TITLE)) {
                    throw new ApplicationException(messageResult.getMsgText());
                }
                //no es un error, si no es un tipo de mensaje de para el usuario
                res.setMsgTitle(messageResult.getMsgTitle());
                res.setMsgText(messageResult.getMsgText());
            }
            //todo ver que hacemos con estos casos de msgId en ""
            return res;
            

    }

    private  WrapperResultados getResultMessage(String msgId) throws ApplicationException {
        WrapperResultados res;
        Endpoint endpoint =  endpoints.get(ERROR_STORE_PROCEDURE);
        HashMap map = new HashMap();
        map.put("pv_msg_id_i",msgId);
        map.put("pv_log_i","0");
        try {

            res = (WrapperResultados) endpoint.invoke(map);
            return res;

        } catch (BackboneApplicationException e) {
            logger.error("Error inesperado al invocar el procedimiento de lectura del mensaje de error" + ERROR_STORE_PROCEDURE, e);
            throw new ApplicationException("Error inesperado al invocar el procedimiento de lectura del mensaje de error");
        }


    }

}
