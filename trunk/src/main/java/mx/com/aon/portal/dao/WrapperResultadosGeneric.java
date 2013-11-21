package mx.com.aon.portal.dao;

import mx.com.aon.portal.util.WrapperResultados;

import java.util.Map;
import java.sql.SQLException;
@Deprecated
public class WrapperResultadosGeneric implements WrapperResultadosBuilder {

	public static String MSG_ID_OK = "200000";
	public static String TITLE_OK = "2";

    public WrapperResultados build(Map map) throws SQLException {
        WrapperResultados wrapperResultados = new WrapperResultados();
        // en caso de que no venga ningun valor, retornamos por default el valor exitoso
        String msgId = MSG_ID_OK;
        String title = TITLE_OK;

         if (map.get("pv_msg_id_o") != null && map.get("pv_title_o") != null) {
              msgId = (map.get("pv_msg_id_o")).toString();
              title = (map.get("pv_title_o")).toString();
         }

         wrapperResultados.setMsgId(msgId);
         wrapperResultados.setMsgTitle(title);
         return wrapperResultados;
     }

}