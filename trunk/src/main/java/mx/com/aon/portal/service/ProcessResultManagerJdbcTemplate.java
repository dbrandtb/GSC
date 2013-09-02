package mx.com.aon.portal.service;

import java.util.HashMap;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.DaoException;

import org.apache.log4j.Logger;

public class ProcessResultManagerJdbcTemplate {

    @SuppressWarnings("unused")
	private  static String ERROR_MSG_ID = "100000";
    private  static String ERROR_MSG_TITLE = "1";
    private  static String ERROR_STORE_PROCEDURE = "OBTIENE_MENSAJE";//"PKG_TRADUC.P_OBTIENE_MENSAJE";

    /**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	protected static Logger logger = Logger.getLogger(ProcessResultManagerJdbcTemplate.class);
    private AbstractDAO abstractDAO;


    public void setAbstractDAO(AbstractDAO abstractDAO) {
        this.abstractDAO = abstractDAO;
    }


    public WrapperResultados processResultMessageId(WrapperResultados res) throws ApplicationException {

            String msgId = res.getMsgId();

            if (msgId != null && !msgId.equals("")) {
                WrapperResultados messageResult = getResultMessage(msgId);

                if (messageResult==null || messageResult.getMsgTitle()== null || messageResult.getMsgTitle().equals("")  ||
                       messageResult.getMsgText()== null || messageResult.getMsgText().equals("") ) {
                    logger.error("No se puede procesar el mensaje de error ");
                    throw new ApplicationException("No se encuentra el mensaje de error asociado");
                }
                
                if(messageResult != null){
                	logger.debug("MsgTitle=" + messageResult.getMsgTitle());
                    logger.debug("MsgText=" + messageResult.getMsgText());
                }

                if (messageResult.getMsgTitle().equals(ERROR_MSG_TITLE)) {
                    throw new ApplicationException(messageResult.getMsgText());
                }
                res.setMsgTitle(messageResult.getMsgTitle());
                res.setMsgText(messageResult.getMsgText());
            }
            return res;


    }

    @SuppressWarnings("unchecked")
    private  WrapperResultados getResultMessage(String msgId) throws ApplicationException {
        WrapperResultados res;
        HashMap map = new HashMap();
        map.put("pv_msg_id_i",msgId);
        map.put("pv_log_i","0");
        map.put("pv_cdusuario_i",null);
        map.put("pv_dsprograma_i",null);
        try {

            res = (WrapperResultados) abstractDAO.invoke(ERROR_STORE_PROCEDURE,map);
            return res;

        } catch (DaoException e) {
            logger.error("Error inesperado al invocar el procedimiento de lectura del mensaje de error" + ERROR_STORE_PROCEDURE, e);
            throw new ApplicationException("Error inesperado al invocar el procedimiento de lectura del mensaje de error");
        }


    }

}
