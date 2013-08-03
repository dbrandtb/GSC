package mx.com.aon.catbo.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import mx.com.aon.catbo.model.CompraTiempoVO;
import mx.com.aon.catbo.model.ConfigurarCompraTiempoVO;

import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

public class ConfigurarCompraTiempoDAO extends AbstractDAO{


    protected void initDao() throws Exception {
        addStoredProcedure("GUARDA_COMPRA_TIEMPO",new GuardarCompraTiempo(getDataSource()));
        addStoredProcedure("BORRA_COMPRA_TIEMPO",new BorrarCompraTiempo(getDataSource()));
        addStoredProcedure("OBTIENE_COMPRA_TIEMPO_DISPONIBLE",new ObtieneCompraTiempoDisponible(getDataSource()));
        addStoredProcedure("GUARDA_COMPRA",new GuardaCompraTiempo(getDataSource()));
        addStoredProcedure("OBTIENE_COMPRA_TIEMPO",new ObtieneConfigCompraTiempo(getDataSource()));
        addStoredProcedure("VALIDA_STATUS_CASO",new ValidaStatusCaso(getDataSource()));
        
    }

    


    protected class GuardarCompraTiempo extends CustomStoredProcedure {

      protected GuardarCompraTiempo(DataSource dataSource) {
          super(dataSource, "PKG_CATBO.P_GUARDA_TARECTMP");
          declareParameter(new SqlParameter("pv_cdproceso_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_cdnivatn_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_nmcant_desde_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_nmcant_hasta_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_tunidad_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_nmvecescompra_i",OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
          compile();
        }


        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }

    protected class BorrarCompraTiempo extends CustomStoredProcedure {

      protected BorrarCompraTiempo(DataSource dataSource) {
          super(dataSource, "PKG_CATBO.P_BORRA_TARECTMP");
          declareParameter(new SqlParameter("pv_cdproceso_i",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_cdnivatn_i",OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
          compile();
        }


        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }
   
    
    
    protected class ObtieneCompraTiempoDisponible extends CustomStoredProcedure {

        protected ObtieneCompraTiempoDisponible(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_OBTIENE_COMPRA_DISPONIBLE");
            declareParameter(new SqlParameter("pv_cdproceso_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdnivatn_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmcaso_i",OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneCompraTiempoDisponibleMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
          }


          @SuppressWarnings("unchecked")
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("pv_registro_o");
              wrapperResultados.setItemList(result);
              return wrapperResultados;
          }
      }
    protected class ObtieneCompraTiempoDisponibleMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            CompraTiempoVO compraTiempoVO = new CompraTiempoVO();
            compraTiempoVO.setCdProceso(rs.getString("CDPROCESO"));
            compraTiempoVO.setDsProceso(rs.getString("DSPROCESO"));
            compraTiempoVO.setNmCaso(rs.getString("NMCASO"));
            compraTiempoVO.setCdOrdenTrabajo(rs.getString("CDORDENTRABAJO"));
            compraTiempoVO.setNmCompraPer(rs.getString("NMCOMPRA_PER"));
            compraTiempoVO.setNmCompraCon(rs.getString("NMCOMPRA_CON"));
            compraTiempoVO.setNmCompraDis(rs.getString("NMCOMPRA_DIS"));
            compraTiempoVO.setNmCantHasta(rs.getString("NMCANT_HASTA"));
            compraTiempoVO.setTUnidad(rs.getString("DSUNIDAD"));
            return compraTiempoVO;
        }
    }
    
    protected class ObtieneConfigCompraTiempoDisponibleMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ConfigurarCompraTiempoVO configurarCompraTiempoVO = new ConfigurarCompraTiempoVO();
        	configurarCompraTiempoVO.setNmcantDesde(rs.getString("NMCANT_DESDE"));
        	configurarCompraTiempoVO.setNmcant_Hasta(rs.getString("NMCANT_HASTA"));
        	configurarCompraTiempoVO.setDsProceso(rs.getString("DSPROCESO"));
        	configurarCompraTiempoVO.setNmvecesCompra(rs.getString("NMVECESCOMPRA"));
        	configurarCompraTiempoVO.setTUnidad(rs.getString("TUNIDAD"));
        	configurarCompraTiempoVO.setNivAtn(rs.getString("CDNIVATN"));
        	       
            return configurarCompraTiempoVO;
            
        }
    }
    
    
    protected class GuardaCompraTiempo extends CustomStoredProcedure {

        protected GuardaCompraTiempo(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_GUARDA_COMPRA");
            declareParameter(new SqlParameter("pv_nmcaso_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdusuario_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdnivatn_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_nmcompra_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_tunidad_i",OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
          }


          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }
      }
    
    protected class ObtieneConfigCompraTiempo extends CustomStoredProcedure {

        protected ObtieneConfigCompraTiempo(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_OBTIENE_TARECTMP");
            declareParameter(new SqlParameter("pv_cdproceso_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdnivatn_i",OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneConfigCompraTiempoDisponibleMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
          }


          @SuppressWarnings("unchecked")
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("pv_registro_o");
              wrapperResultados.setItemList(result);
              return wrapperResultados;
          }
      }
    
    //valida Status del caso 
    protected class ValidaStatusCaso extends CustomStoredProcedure {

        protected ValidaStatusCaso(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.VALIDA_STATUS_CASO");
            declareParameter(new SqlParameter("pv_nmcaso_i",OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
          }


          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }
      }
    
    
}
