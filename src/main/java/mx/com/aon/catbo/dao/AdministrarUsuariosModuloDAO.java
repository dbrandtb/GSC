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

import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.catbo.dao.ArchivosFaxesDAO.ObtieneArchivoExport;
import mx.com.aon.catbo.dao.ArchivosFaxesDAO.ObtieneArchivoMapperExport;

import mx.com.aon.catbo.model.UsuariosVO;
import mx.com.aon.portal.dao.CustomStoredProcedure;

public class AdministrarUsuariosModuloDAO extends AbstractDAO{


	 protected void initDao() throws Exception {
         
         addStoredProcedure("OBTIENE_USUARIOS",new ObtenerUsuarios(getDataSource()));
         addStoredProcedure("BORRA_USUARIOS",new BorrarUsuarios(getDataSource()));
         addStoredProcedure("OBTIENE_USUARIOS_EXPORT",new ObtieneUsuariosExport(getDataSource()));
         addStoredProcedure("GUARDA_USUARIOS",new GuardaUsuarios(getDataSource()));
         addStoredProcedure("OBTIENE_USUARIOS_ASIGNAR",new ObtenerUsuariosAsignar(getDataSource()));

      }
       
    protected class ObtenerUsuarios extends CustomStoredProcedure {
    
    protected ObtenerUsuarios(DataSource dataSource) {
        super(dataSource, "PKG_CATBO.P_OBTIENE_CATBOUSUAR");
        declareParameter(new SqlParameter("pv_dsmodulo_i", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("pv_dsusuario_i", OracleTypes.VARCHAR));
                
        declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerFaxesMapper()));
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
    
    protected class ObtenerFaxesMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	UsuariosVO usuariosVO = new UsuariosVO();
            
        	usuariosVO.setDsUsuario(rs.getString("DSUSUARI"));
        	usuariosVO.setCdUsuario(rs.getString("CDUSUARIO"));
        	usuariosVO.setCdModulo(rs.getString("CDMODULO"));
        	usuariosVO.setDsModulo(rs.getString("DSMODULO"));
        	            
            return usuariosVO;
        }
    }
        
    protected class ObtieneUsuariosExport extends CustomStoredProcedure {

    protected ObtieneUsuariosExport(DataSource dataSource) {
        super(dataSource, "PKG_CATBO.P_OBTIENE_CATBOUSUAR");
        
        declareParameter(new SqlParameter("pv_dsmodulo_i", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("pv_dsusuario_i", OracleTypes.VARCHAR));
        
        declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneUsuariosMapperExport()));
        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
        compile();
      }

      public WrapperResultados mapWrapperResultados(Map map) throws Exception {
          WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
          WrapperResultados wrapperResultados = mapper.build(map);
          List result = (List) map.get("pv_registro_o");// pv_registro_o?????
          wrapperResultados.setItemList(result);
          return wrapperResultados;
      }
  }

  protected class ObtieneUsuariosExportMapper  implements RowMapper {
      public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	UsuariosVO usuariosVO = new UsuariosVO();
          
      	usuariosVO.setDsUsuario(rs.getString("DSUSUARI"));
      	usuariosVO.setCdUsuario(rs.getString("CDUSUARIO"));
      	usuariosVO.setDsModulo(rs.getString("DSMODULO"));
      	            
        return usuariosVO;
        }
  }

  protected class ObtieneUsuariosMapperExport  implements RowMapper {
      public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
          ArrayList lista =  new ArrayList(2);
          lista.add(rs.getString("DSUSUARI"));
          lista.add(rs.getString("DSMODULO"));
         
          return lista;
      }
  }
  
  
  protected class BorrarUsuarios extends CustomStoredProcedure{
	   	 protected BorrarUsuarios(DataSource dataSource) {
	            super(dataSource, "PKG_CATBO.P_BORRA_CATBOUSUAR");
	            declareParameter(new SqlParameter("pv_cdmodulo_i",OracleTypes.NUMERIC));
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
  
  //Obtener Usuarios a Asignar    
  protected class ObtenerUsuariosAsignar extends CustomStoredProcedure {
  
  protected ObtenerUsuariosAsignar(DataSource dataSource) {
      super(dataSource, "PKG_CATBO.P_OBTIENE_TUSUARIO");
      declareParameter(new SqlParameter("pv_dsusuario_i", OracleTypes.VARCHAR));
                      
      declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerUsuariosMapper()));
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
  
  protected class ObtenerUsuariosMapper  implements RowMapper {
      public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
      	UsuariosVO usuariosVO = new UsuariosVO();
          
      	usuariosVO.setDsUsuario(rs.getString("DSUSUARI"));
      	usuariosVO.setCdUsuario(rs.getString("CDUSUARI"));
      	
      	            
          return usuariosVO;
      }
  }
  
  //Fin de Obtener Usuarios 
  

  protected class GuardaUsuarios extends CustomStoredProcedure {
	    
	    protected GuardaUsuarios(DataSource dataSource) {
	        super(dataSource, "PKG_CATBO.P_GUARDA_CATBOUSUAR");
	        declareParameter(new SqlParameter("pv_cdmodulo_i", OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_cdusuario_i", OracleTypes.VARCHAR));
	                
	        //declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerFaxesMapper()));
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
