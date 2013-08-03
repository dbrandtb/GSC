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
import mx.com.aon.portal.model.EmailVO;

import java.util.Map;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlertaDAO extends AbstractDAO{

    private static Logger logger = Logger.getLogger(AlertaDAO.class);

    public static final String OBTIENE_ALERTAS_EMAIL = "OBTIENE_ALERTAS_EMAIL";
    
    
    protected void initDao() throws Exception {
        addStoredProcedure("OBTIENE_ALERTAS_USUARIO",new BuscarAlertas(getDataSource()));
        addStoredProcedure("OBTIENE_ALERTA_USUARIO_REG",new GetAlerta(getDataSource()));
        addStoredProcedure("GUARDA_ALERTA_USUARIO",new GuardarAlerta(getDataSource()));
        addStoredProcedure("BORRA_ALERTA",new BorrarAlerta(getDataSource()));
        
        addStoredProcedure(OBTIENE_ALERTAS_EMAIL, new GetAlertaEmail(getDataSource()));

    }


    protected class BuscarAlertas extends CustomStoredProcedure {

      protected BuscarAlertas(DataSource dataSource) {
          super(dataSource, "PKG_ALERTAS.P_ALERTAS_USUARIO");

          declareParameter(new SqlParameter("pv_dsusuario_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dsnombre_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dsproces_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dstipram_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dsunieco_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dsrol_i", OracleTypes.VARCHAR)); 

          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new BuscarAlertasMapper()));
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



    protected class GetAlerta extends CustomStoredProcedure {

      protected GetAlerta(DataSource dataSource) {
          super(dataSource, "PKG_ALERTAS.P_OBTIENE_ALERTA_USUARIO_REG");
          declareParameter(new SqlParameter("pv_cdidunico_i", OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_cdidconfalerta_i", OracleTypes.NUMERIC));

          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new GetAlertaMapper()));
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



    protected class GuardarAlerta extends CustomStoredProcedure {

      protected GuardarAlerta(DataSource dataSource) {
          super(dataSource, "PKG_ALERTAS.P_GUARDA_ALERTA");
          declareParameter(new SqlParameter("pv_cdidunico_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_cdidconfalerta_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_cve_aseguradora_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_poliza_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_recibo_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_cve_proceso_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_fec_ult_envio_i",OracleTypes.DATE));
          declareParameter(new SqlParameter("pv_fec_sig_envio_i",OracleTypes.DATE));
          declareParameter(new SqlParameter("pv_fec_vencimiento_i",OracleTypes.DATE));
          declareParameter(new SqlParameter("pv_correo_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_mensaje_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_mandar_pantalla_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_perm_pantalla_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cve_frecuencia_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_cdelemento_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_cdusuario_i",OracleTypes.VARCHAR));

          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
          compile();
        }

        
        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }

    protected class BorrarAlerta extends CustomStoredProcedure {

      protected BorrarAlerta(DataSource dataSource) {
          super(dataSource, "PKG_ALERTAS.P_BORRA_ALERTA");
          declareParameter(new SqlParameter("pv_cdidunico_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_cdidconfalerta_i",OracleTypes.NUMERIC));

          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
          compile();
        }

        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }
    
    protected class GetAlertaEmail extends CustomStoredProcedure {

        protected GetAlertaEmail(DataSource dataSource) {
            super(dataSource, "PKG_ALERTAS.P_OBTIENE_ALERTA");
            declareParameter(new SqlParameter("pv_FEFECHA_i", OracleTypes.DATE));
            
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new GetAlertaEmailMapper()));
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

    protected class GetAlertaEmailMapper implements RowMapper {
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		EmailVO emailVO = new EmailVO();
    		
    		emailVO.setAsunto("Prueba");
    		emailVO.setTo(rs.getString("DSEMAIL"));
    		emailVO.setCc(rs.getString("DSCORREO"));
    		emailVO.setMensaje(rs.getString("dsmensaje"));
    		
    		return emailVO;
    	}
    }
    
    protected class GetAlertaMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AlertaUsuarioVO alertaUsuarioVO = new AlertaUsuarioVO();

            alertaUsuarioVO.setCdIdUnico(rs.getString("CDIDUNICO"));
            alertaUsuarioVO.setCdElemento(rs.getString("CDELEMENTO"));
            alertaUsuarioVO.setDsNombre(rs.getString("DSNOMBRE"));
            alertaUsuarioVO.setCdProceso(rs.getString("CDPROCESO"));
            alertaUsuarioVO.setDsProceso(rs.getString("DSPROCES"));
            alertaUsuarioVO.setCdUsuario(rs.getString("CDUSUARIO"));
            alertaUsuarioVO.setCdUniEco(rs.getString("CDUNIECO"));
            alertaUsuarioVO.setDsUniEco(rs.getString("DSUNIECO"));
            alertaUsuarioVO.setCdProducto(rs.getString("CDPRODUCTO"));
            alertaUsuarioVO.setDsProducto(rs.getString("DSPRODUCTO"));
            alertaUsuarioVO.setNmPoliza(rs.getString("NMPOLIZA"));
            alertaUsuarioVO.setNmRecibo(rs.getString("NMRECIBO"));
            alertaUsuarioVO.setFeUltimoEvento(ConvertUtil.convertToString(rs.getDate("FEULTIMOEVENTO")));
            alertaUsuarioVO.setFeSiguienteEnvio(ConvertUtil.convertToString(rs.getDate("FESIGUIENTEENVIO")));
            alertaUsuarioVO.setNmFrecuencia(rs.getString("NMFRECUENCIA"));
            alertaUsuarioVO.setFeVencimiento(rs.getString("FEVENCIMIENTO"));
            alertaUsuarioVO.setDsCorreo(rs.getString("DSCORREO"));
            alertaUsuarioVO.setDsMensaje(rs.getString("DSMENSAJE"));
            alertaUsuarioVO.setFgMandaPantalla(rs.getString("FGMANDARPANTALLA"));
            alertaUsuarioVO.setFgPermPantalla(rs.getString("FGPERMANCEPANTALLA"));
            alertaUsuarioVO.setCdTemporalidad(rs.getString("CDTEMPORALIDAD"));
            alertaUsuarioVO.setDsTemporalidad(rs.getString("DSTEMPORALIDAD"));
            alertaUsuarioVO.setDsUsuario(rs.getString("DSUSUARIO"));

            return alertaUsuarioVO;
        }
    }

    protected class BuscarAlertasMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AlertaUsuarioVO alertaUsuarioVO = new AlertaUsuarioVO();

            alertaUsuarioVO.setCdIdUnico(rs.getString("CDIDUNICO"));
            alertaUsuarioVO.setDsNombre(rs.getString("DSNOMBRE"));
            alertaUsuarioVO.setCdProceso(rs.getString("CDPROCESO"));
            alertaUsuarioVO.setDsProceso(rs.getString("DSPROCES"));
            alertaUsuarioVO.setCdUsuario(rs.getString("CDUSUARIO"));
            alertaUsuarioVO.setDsUsuario(rs.getString("DSUSUARIO"));
            alertaUsuarioVO.setCdUniEco(rs.getString("CDUNIECO"));
            alertaUsuarioVO.setDsUniEco(rs.getString("DSUNIECO"));
            alertaUsuarioVO.setNmPoliza(rs.getString("NMPOLIZA"));
            alertaUsuarioVO.setNmRecibo(rs.getString("NMRECIBO"));
            alertaUsuarioVO.setFeUltimoEvento(ConvertUtil.convertToString(rs.getDate("FEULTIMOEVENTO")));
            alertaUsuarioVO.setFeSiguienteEnvio(ConvertUtil.convertToString(rs.getDate("FESIGUIENTEENVIO")));
            alertaUsuarioVO.setCodigoConfAlerta(rs.getString("CDIDCONFALERTA"));
            alertaUsuarioVO.setDsIsRol(rs.getString("DSISROL"));
            alertaUsuarioVO.setDsRamo(rs.getString("DSRAMO"));

            return alertaUsuarioVO;
        }
    }

}
