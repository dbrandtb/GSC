package mx.com.aon.portal.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.model.CotizacionMasivaVO;
import mx.com.aon.portal.model.AlertaUsuarioVO;
import mx.com.aon.portal.model.MecanismoAlertaVO;

import java.util.Map;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MecanismoAlertaDAO extends AbstractDAO{

    private static Logger logger = Logger.getLogger(MecanismoAlertaDAO.class);


    protected void initDao() throws Exception {
        addStoredProcedure("OBTIENE_MENSAJES_ALERTA_USUARIO",new BuscarMensajesAlerta(getDataSource()));
        addStoredProcedure("OBTIENE_MENSAJES_ALERTA_PANTALLA_USUARIO",new BuscarMensajesAlertaPantalla(getDataSource()));

    }


    protected class BuscarMensajesAlerta extends CustomStoredProcedure {

      protected BuscarMensajesAlerta(DataSource dataSource) {
          super(dataSource, "PKG_ALERTAS.P_OBTIENE_ALERTA");

          declareParameter(new SqlParameter("PV_CDUSUARIO_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_CDSISROL_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_CDELEMENTO_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_CDPROCESO_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_FEFECHA_i", OracleTypes.DATE));
          declareParameter(new SqlParameter("pv_CDRAMO_i", OracleTypes.VARCHAR)); 

          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new BuscarMensajesAlertaMapper()));
          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
          
                 
          compile();

        }

        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("pv_registro_o");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
        }
    }


    protected class BuscarMensajesAlertaPantalla extends CustomStoredProcedure {
    	
    	protected BuscarMensajesAlertaPantalla(DataSource dataSource) {
            super(dataSource, "PKG_ALERTAS.P_OBTIENE_ALERTAP");

            declareParameter(new SqlParameter("PV_CDUSUARIO_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_CDSISROL_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_CDELEMENTO_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_CDPROCESO_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_FEFECHA_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_CDRAMO_i", OracleTypes.VARCHAR)); 

            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new BuscarMensajesAlertaMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            
            compile();
    	}

    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		WrapperResultados wrapperResultados = mapper.build(map);
    		List result = (List) map.get("pv_registro_o");
    		wrapperResultados.setItemList(result);
    		return wrapperResultados;
    	}
	}
    

    protected class BuscarMensajesAlertaMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	MecanismoAlertaVO mecanismoAlertaVO = new MecanismoAlertaVO();

        	mecanismoAlertaVO.setDsMensaje(rs.getString("DSMENSAJE"));
           

            return mecanismoAlertaVO;
        }
    }

}
