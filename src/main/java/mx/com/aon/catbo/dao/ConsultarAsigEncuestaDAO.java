package mx.com.aon.catbo.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.RowMapper;

import mx.com.aon.portal.dao.AlertaDAO;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;

import mx.com.aon.portal.dao.AbstractDAO;

import mx.com.aon.portal.model.AlertaUsuarioVO;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.catbo.model.ArchivoVO;
import mx.com.aon.catbo.model.ArchivosFaxesVO;
import mx.com.aon.catbo.model.AsigEncuestaVO;
import mx.com.aon.catbo.model.CompraTiempoVO;
import mx.com.aon.catbo.model.FaxesVO;


public class ConsultarAsigEncuestaDAO extends AbstractDAO{

	
	 protected void initDao() throws Exception {
         
         addStoredProcedure("OBTENER_ASIGNACION_ENCUESTA",new ObtenerAsigEncuesta(getDataSource()));
         addStoredProcedure("BORRAR_ASIGNACION_ENCUESTA",new BorrarAsigEncuesta(getDataSource()));
         addStoredProcedure("GUARDAR_ASIGNACION_ENCUESTA",new GuardarAsigEncuesta(getDataSource()));
         addStoredProcedure("OBTIENE_ARCHIVO_EXPORT",new ObtieneArchivoExport(getDataSource()));
         addStoredProcedure("OBTENER_ASIGNACION_ENCUESTA_REG",new GetObtenerAsigEncuesta(getDataSource()));       
       }
       
    protected class ObtenerAsigEncuesta extends CustomStoredProcedure {
    
    protected ObtenerAsigEncuesta(DataSource dataSource) {
        super(dataSource, "PKG_ENCUESTAS.P_OBTIENE_TASIGNAENCUESTA");
        
        declareParameter(new SqlParameter("pv_nmpoliex_i", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("pv_dsperson_i", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("pv_dsusuario_i", OracleTypes.VARCHAR));
              
        declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerAsigEncuestaMapper()));
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
    
    protected class ObtenerAsigEncuestaMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AsigEncuestaVO asigEncuestaVO = new AsigEncuestaVO();
            
        	asigEncuestaVO.setNmConfig(rs.getString("NMCONFIG"));
        	asigEncuestaVO.setCdUnieco(rs.getString("CDUNIECO"));
        	asigEncuestaVO.setCdRamo(rs.getString("CDRAMO"));
        	asigEncuestaVO.setEstado(rs.getString("ESTADO"));
         	asigEncuestaVO.setNmPoliza(rs.getString("NMPOLIZA")); 
         	asigEncuestaVO.setCdPerson(rs.getString("CDPERSON"));
         	asigEncuestaVO.setPoliaExterna(rs.getString("NMPOLIEX"));
         	asigEncuestaVO.setDsPerson(rs.getString("DSPERSON"));
         	asigEncuestaVO.setFeRegistro(rs.getString("FEREGISTRO"));
         	asigEncuestaVO.setStatus(rs.getString("STATUS"));
            asigEncuestaVO.setCdUsuario(rs.getString("CDUSUARIO"));
            asigEncuestaVO.setDsUsuari(rs.getString("DSUSUARIO"));//setCdUsusari(rs.getString("CDUSUARI"));//setCdUsuario(rs.getString("CDUSUARI"));////
         	        	
        	            
            return asigEncuestaVO;
        }
    }
    
    protected class BorrarAsigEncuesta extends CustomStoredProcedure{
   	 protected BorrarAsigEncuesta(DataSource dataSource) {
            super(dataSource, "PKG_ENCUESTAS.P_BORRA_TASIGNAENCUESTA");
            
            declareParameter(new SqlParameter("pv_nmconfig_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdunieco_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_estado_i",OracleTypes.VARCHAR));
                                    
            declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
            compile();
          }


          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }
   	
   }
    
    protected class GuardarAsigEncuesta extends CustomStoredProcedure {

        protected GuardarAsigEncuesta(DataSource dataSource) {
            super(dataSource, "PKG_ENCUESTAS.P_GUARDA_TASIGNAENCUESTA");
                      
            declareParameter(new SqlParameter("pv_nmconfig_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_status_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdusuario_i", OracleTypes.VARCHAR));
                  
          //  declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerAsigEncuestaMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            
            compile();
          }


          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }
      }

       
      protected class ObtieneArchivoExport extends CustomStoredProcedure {

      protected ObtieneArchivoExport(DataSource dataSource) {
          super(dataSource, "PKG_ENCUESTAS.P_OBTIENE_TASIGNAENCUESTA");          
          //super(dataSource, "PKG_CATBO.P_OBTIENE_TASIGNAENCUESTA");
          declareParameter(new SqlParameter("pv_nmpoliex_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dsperson_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dsusuario_i", OracleTypes.VARCHAR));
          
          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneArchivoMapperExport()));
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

    protected class ObtieneArchivoExportMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AsigEncuestaVO asigEncuestaVO = new AsigEncuestaVO();
        	       	
        	/*asigEncuestaVO.setNmConfig(rs.getString("NMCONFIG"));
        	asigEncuestaVO.setCdUnieco(rs.getString("CDUNIECO"));
        	asigEncuestaVO.setCdRamo(rs.getString("CDRAMO"));
        	asigEncuestaVO.setEstado(rs.getString("ESTADO"));
         	asigEncuestaVO.setNmPoliza(rs.getString("NMPOLIZA"));         	
         	asigEncuestaVO.setPoliaExterna(rs.getString("POLIAEXTERNA"));
         	asigEncuestaVO.setNombreCliente(rs.getString("NOMBRECLIENTE"));         	
         	asigEncuestaVO.setNombreUsuario(rs.getString("NOMBREUSUARIO"));*/
            asigEncuestaVO.setNmConfig(rs.getString("NMCONFIG"));
        	asigEncuestaVO.setCdUnieco(rs.getString("CDUNIECO"));
        	asigEncuestaVO.setCdRamo(rs.getString("CDRAMO"));
        	asigEncuestaVO.setEstado(rs.getString("ESTADO"));
         	asigEncuestaVO.setNmPoliza(rs.getString("NMPOLIZA")); 
         	asigEncuestaVO.setCdPerson(rs.getString("CDPERSON"));
         	asigEncuestaVO.setPoliaExterna(rs.getString("NMPOLIEX"));
         	asigEncuestaVO.setDsPerson(rs.getString("DSPERSON"));
         	asigEncuestaVO.setFeRegistro(rs.getString("FEREGISTRO"));
         	asigEncuestaVO.setStatus(rs.getString("STATUS"));
            asigEncuestaVO.setCdUsuario(rs.getString("CDUSUARIO"));
            asigEncuestaVO.setDsUsuari(rs.getString("DSUSUARIO"));	
            return asigEncuestaVO;
        }
    }

    protected class ObtieneArchivoMapperExport  implements RowMapper {
        @SuppressWarnings("unchecked")
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArrayList lista =  new ArrayList(3);
            //lista.add(rs.getString("NMCONFIG"));
            //lista.add(rs.getString("CDUNIECO"));
            //lista.add(rs.getString("CDRAMO"));
            //lista.add(rs.getString("ESTADO"));
            //lista.add(rs.getString("NMPOLIZA"));
            lista.add(rs.getString("NMPOLIZA"));
            lista.add(rs.getString("DSPERSON"));
            lista.add(rs.getString("DSUSUARIO"));
            
            return lista;
                                 
        }
    }
    
    protected class GetObtenerAsigEncuesta extends CustomStoredProcedure {

        protected GetObtenerAsigEncuesta(DataSource dataSource) {
            super(dataSource, "PKG_ENCUESTAS.P_OBTIENE_TASIGNAENCUESTA_REG");
            declareParameter(new SqlParameter("pv_nmconfig_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdusuario_i", OracleTypes.VARCHAR));

            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new GetObtenerAsigEncuestaMapper()));
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

    protected class GetObtenerAsigEncuestaMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AsigEncuestaVO asigEncuestaVO = new AsigEncuestaVO();

        	asigEncuestaVO.setNmConfig(rs.getString("NMCONFIG"));
        	asigEncuestaVO.setCdUnieco(rs.getString("CDUNIECO"));
        	asigEncuestaVO.setDsUnieco(rs.getString("DSUNIECO"));
        	asigEncuestaVO.setCdRamo(rs.getString("CDRAMO"));
        	asigEncuestaVO.setDsRamo(rs.getString("DSRAMO"));
        	asigEncuestaVO.setCdEstado(rs.getString("ESTADO"));
        	asigEncuestaVO.setNmPoliza(rs.getString("NMPOLIZA"));
        	asigEncuestaVO.setCdPerson(rs.getString("CDPERSON"));
        	asigEncuestaVO.setDsNombre(rs.getString("DSNOMBRE"));
        	asigEncuestaVO.setFeRegistro(rs.getString("FEREGISTRO"));
        	asigEncuestaVO.setStatus(rs.getString("STATUS"));
        	asigEncuestaVO.setCdUsuario(rs.getString("CDUSUARIO"));
        	asigEncuestaVO.setCdModulo(rs.getString("CDMODULO"));
        	        	
            return asigEncuestaVO;
        }
    }
    
}
