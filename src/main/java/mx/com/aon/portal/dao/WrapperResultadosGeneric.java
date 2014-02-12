package mx.com.aon.portal.dao;

import java.sql.SQLException;
import java.util.Map;

import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;

import org.apache.log4j.Logger;
@Deprecated
public class WrapperResultadosGeneric implements WrapperResultadosBuilder {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AbstractManagerDAO.class);
	
    @SuppressWarnings("rawtypes")
	public WrapperResultados build(Map map) throws SQLException {
        WrapperResultados wrapperResultados = new WrapperResultados();
        String msgId = "";
        String title = "";
         if (map.get("pv_msg_id_o") != null && map.get("pv_title_o") != null) {
              msgId = (map.get("pv_msg_id_o")).toString();
              title = (map.get("pv_title_o")).toString();
         }
         wrapperResultados.setMsgId(msgId);
         wrapperResultados.setMsgTitle(title);
         return wrapperResultados;
     }

}