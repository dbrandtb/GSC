package mx.com.aon.catbo.dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;

import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.catbo.dao.ArchivosFaxesDAO.ObtieneArchivoExport;
import mx.com.aon.catbo.dao.ArchivosFaxesDAO.ObtieneArchivoMapperExport;

import mx.com.aon.catbo.model.ActividadUsuarioVO;
import mx.com.aon.catbo.model.UsuariosVO;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.util.ConvertUtil;

public class ActividadUsuarioDAO extends AbstractDAO{


	 protected void initDao() throws Exception {
         addStoredProcedure("BUSCAR_ACTIVIDAD_USUARIO",new ObtenerActividadUsuario(getDataSource()));
         addStoredProcedure("INSERTAR_ACTIVIDAD_USUARIO",new InsertarActividadUsuario(getDataSource()));
         addStoredProcedure("BUSCAR_ACTIVIDAD_USUARIO_EXPORT",new ObtieneActividadExport(getDataSource()));
         addStoredProcedure("BUSCAR_FECHA_INICIAL_ACTIVIDAD_USUARIO",new ObtenerFechaInicialActividadUsuario(getDataSource()));
         addStoredProcedure("BUSCAR_FECHA_FINAL_ACTIVIDAD_USUARIO",new ObtenerFechaFinalActividadUsuario(getDataSource()));
         
      }
       
    protected class ObtenerActividadUsuario extends CustomStoredProcedure {
    
    protected ObtenerActividadUsuario(DataSource dataSource) {
        super(dataSource, "PKG_AUDITORIA.P_OBTIENE_SEC_REQ_AUDIT");
        declareParameter(new SqlParameter("pv_dsusuari_i", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("pv_url_i", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("pv_fe_ini_i", OracleTypes.TIMESTAMP));
        declareParameter(new SqlParameter("pv_fe_fin_i", OracleTypes.TIMESTAMP));
       // declareParameter(new SqlParameter("pv_sort_i", OracleTypes.VARCHAR));
       // declareParameter(new SqlParameter("pv_dir_i", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("pv_start", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("pv_limit", OracleTypes.NUMBER));
        
        
        declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerActividadUsuarioMapper()));
        declareParameter(new SqlOutParameter("pv_cantidad_recs_o", OracleTypes.NUMERIC));
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
          wrapperResultados.setResultado(map.get("pv_cantidad_recs_o").toString());
          //wrapperResultados.setSubTotal(map.get("pv_cantidad_recs_o").toString());
          return wrapperResultados;
      }
  }

    protected class InsertarActividadUsuario extends CustomStoredProcedure {

    protected InsertarActividadUsuario(DataSource dataSource) {
        super(dataSource, "PKG_AUDITORIA.P_INSERT_SEC_REQ_AUDIT");

        declareParameter(new SqlParameter("pv_reqid_i", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("pv_url_i", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("pv_reqtype_i", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("pv_cdusuario_i", OracleTypes.VARCHAR));
        //declareParameter(new SqlParameter("pv_timestamp_i", OracleTypes.DATE));
        declareParameter(new SqlParameter("pv_timestamp_i", OracleTypes.TIMESTAMP));
        declareParameter(new SqlParameter("pv_cdrol_i", OracleTypes.VARCHAR));

        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
        compile();
      }

      public WrapperResultados mapWrapperResultados(Map map) throws Exception {
          WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
          WrapperResultados wrapperResultados = mapper.build(map);
          return wrapperResultados;
      }
  }
        
    protected class ObtieneActividadExport extends CustomStoredProcedure {

        protected ObtieneActividadExport(DataSource dataSource) {
            super(dataSource, "PKG_AUDITORIA.P_SECREQAUDIT_EXPORTAR");
            declareParameter(new SqlParameter("pv_dsusuari_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_url_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_fe_ini_i", OracleTypes.TIMESTAMP));
            declareParameter(new SqlParameter("pv_fe_fin_i", OracleTypes.TIMESTAMP));
            
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneActividadMapperExport()));
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
  
    protected class ObtenerActividadUsuarioMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ActividadUsuarioVO actividadUsuarioVO = new ActividadUsuarioVO();
        	//logger.debug("AquiTa: Entro Al Mapper");
        	actividadUsuarioVO.setDsUsuari(rs.getString("DSUSUARI"));
        	actividadUsuarioVO.setTimeStamp(rs.getString("TIMESTAMP"));
        	//actividadUsuarioVO.setTimeStamp(ConvertUtil.convertToString(rs.getDate("TIMESTAMP")).toString());
        	actividadUsuarioVO.setUrl(rs.getString("URL"));
        	//logger.debug("AquiTa: Salio del Mapper:"+actividadUsuarioVO.getTimeStamp());
            return actividadUsuarioVO;
        }
    }
    
    
    
    protected class ObtieneActividadMapperExport  implements RowMapper {
		@SuppressWarnings("unchecked")
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArrayList lista =  new ArrayList(3);
            lista.add(rs.getString("DSUSUARI"));
            lista.add(rs.getString("TIMESTAMP"));
            //lista.add(ConvertUtil.convertToString(rs.getDate("TIMESTAMP")).toString());
            lista.add(rs.getString("URL"));
            return lista;
        }
    }
    
    protected class ObtenerFechaInicialActividadUsuario extends CustomStoredProcedure {
        
        protected ObtenerFechaInicialActividadUsuario(DataSource dataSource) {
            super(dataSource, "PKG_AUDITORIA.P_OBTIENE_SEC_REQ_AUDIT_LIMINF");
            declareParameter(new SqlParameter("pv_dsusuari_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_url_i", OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerFechaInicialActividadUsuarioMapper()));
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
    
    protected class ObtenerFechaInicialActividadUsuarioMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ActividadUsuarioVO actividadUsuarioVO = new ActividadUsuarioVO();

        	actividadUsuarioVO.setFechaInicial(rs.getString("FEINICIAL"));
        	
            return actividadUsuarioVO;
        }
    }
    
protected class ObtenerFechaFinalActividadUsuario extends CustomStoredProcedure {
        
        protected ObtenerFechaFinalActividadUsuario(DataSource dataSource) {
            super(dataSource, "PKG_AUDITORIA.P_OBTIENE_SEC_REQ_AUDIT_LIMSUP");
            declareParameter(new SqlParameter("pv_dsusuari_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_url_i", OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerFechaFinalActividadUsuarioMapper()));
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
    
    protected class ObtenerFechaFinalActividadUsuarioMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ActividadUsuarioVO actividadUsuarioVO = new ActividadUsuarioVO();

        	actividadUsuarioVO.setFechaFinal(rs.getString("FEFINAL"));
        	
            return actividadUsuarioVO;
        }
    }
    

}
