package mx.com.aon.catbo.dao;

import org.apache.log4j.Logger;
import oracle.jdbc.driver.OracleTypes;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.List;

//import mx.com.aon.catbo.model.EstructuraVO;
import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.catbo.model.GuionLlamadasVO;



public class GuionLlamadasDAO extends AbstractDAO {

    private static Logger logger = Logger.getLogger(GuionLlamadasDAO.class);


   protected void initDao() throws Exception {
        addStoredProcedure("OBTENER_GUION_LLAMADAS",new BuscarGuionLlamadas(getDataSource()));
        addStoredProcedure("GUARDAR_GUION_LLAMADAS",new GuardarGuionLlamadas(getDataSource()));
        addStoredProcedure("OBTENER_DIALOGO_LLAMADAS",new BuscarDialogoLlamadas(getDataSource()));
        addStoredProcedure("GUARDAR_DIALOGO_LLAMADAS",new GuardarDialogoLlamadas(getDataSource()));
        
    }


    protected class BuscarGuionLlamadas extends CustomStoredProcedure {

      protected BuscarGuionLlamadas(DataSource dataSource) {
          super(dataSource, "PKG_NOTIFICACIONES_CATBO.P_OBTIENE_TGUIONES");
          declareParameter(new SqlParameter("pv_dsunieco_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dselemento_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dsguion_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dsproceso_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dstipgui_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dsramo_i",OracleTypes.VARCHAR));
          
          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new GuionMapper()));
          declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
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


    
    protected class GuardarGuionLlamadas extends CustomStoredProcedure{

    	protected GuardarGuionLlamadas(DataSource dataSource) {
            super(dataSource, "PKG_NOTIFICACIONES_CATBO.P_GUARDA_TGUION");

            declareParameter(new SqlParameter("pv_cdunieco_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdelemento_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdproceso_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdguion_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_dsguion_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdtipguion_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_indinicial_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_status_i",OracleTypes.VARCHAR));    
            
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
          }
    	 public WrapperResultados mapWrapperResultados(Map map) throws Exception {
             WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
             return mapper.build(map);
         }
    }

    
    protected class BuscarDialogoLlamadas extends CustomStoredProcedure {

        protected BuscarDialogoLlamadas(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_OBTIENE_TDIALOGO");
            declareParameter(new SqlParameter("pv_cdunieco_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdramo_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdelemento_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdproceso_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdguion_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter(" pv_cddialogo_i ",OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DialogoMapper()));
            declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
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
    
    protected class GuardarDialogoLlamadas extends CustomStoredProcedure{

    	protected GuardarDialogoLlamadas(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_GUARDA_TDIALOGO");

            declareParameter(new SqlParameter("pv_cdunieco_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdelemento_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdproceso_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdguion_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cddialogo_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_dsdialogo_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdsecuencia_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_ottapval_i",OracleTypes.VARCHAR));    
            
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
          }
    	 public WrapperResultados mapWrapperResultados(Map map) throws Exception {
             WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
             return mapper.build(map);
         }
    }
    
    
    protected class GuionMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            GuionLlamadasVO  guionLlamadasVO = new GuionLlamadasVO();
            guionLlamadasVO.setCdUnieco(rs.getString("CDUNIECO"));
            guionLlamadasVO.setCdUnieco(rs.getString("DSUNIECO"));
            guionLlamadasVO.setCdElemento(rs.getString("CDELEMENTO"));
            guionLlamadasVO.setDsElemen(rs.getString("DSELEMEN"));
            guionLlamadasVO.setCdRamo(rs.getString("CDRAMO"));
            guionLlamadasVO.setDsRamo(rs.getString("DSRAMO"));
            guionLlamadasVO.setCdProceso(rs.getString("CDPROCESO"));
            guionLlamadasVO.setDsProceso(rs.getString("DSPROCESO"));
            guionLlamadasVO.setCdGuion(rs.getString("CDGUION"));
            guionLlamadasVO.setDsGuion(rs.getString("DSGUION"));
            guionLlamadasVO.setCdTipGuion(rs.getString("CDTIPGUI"));
            guionLlamadasVO.setDsTipGuion(rs.getString("DSTIPGUI"));
            guionLlamadasVO.setEstado(rs.getString("STATUS"));
            
            return guionLlamadasVO;
        }
    }

    protected class DialogoMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            GuionLlamadasVO  guionLlamadasVO = new GuionLlamadasVO();
            guionLlamadasVO.setCdUnieco(rs.getString("CDUNIECO"));
            guionLlamadasVO.setCdUnieco(rs.getString("DSUNIECO"));
            guionLlamadasVO.setCdElemento(rs.getString("CDELEMENTO"));
            guionLlamadasVO.setDsElemen(rs.getString("DSELEMEN"));
            guionLlamadasVO.setCdRamo(rs.getString("CDRAMO"));
            guionLlamadasVO.setDsRamo(rs.getString("DSRAMO"));
            guionLlamadasVO.setCdProceso(rs.getString("CDPROCESO"));
            guionLlamadasVO.setDsProceso(rs.getString("DSPROCESO"));
            guionLlamadasVO.setCdGuion(rs.getString("CDGUION"));
            guionLlamadasVO.setDsGuion(rs.getString("DSGUION"));
            guionLlamadasVO.setCdDialogo(rs.getString("CDDIALOGO"));
            guionLlamadasVO.setDsDialogo(rs.getString("DSDIALOGO"));
            guionLlamadasVO.setCdSecuencia(rs.getString("CDSECUENCIA"));
            guionLlamadasVO.setOtTapVal(rs.getString("OTTAPVAL"));
            
            return guionLlamadasVO;
        }
    }
}


