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
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.catbo.model.ClavesVO;


public class GeneracionClavesDAO extends AbstractDAO{


	 protected void initDao() throws Exception {
        
       
         addStoredProcedure("OBTIENE_VALORES",new ObtenerValores(getDataSource()));
         addStoredProcedure("ACTUALIZA_VALORES",new GuardarValores(getDataSource()));
         
 
     }
           
   
   protected class ObtenerValores extends CustomStoredProcedure{
   	 protected ObtenerValores(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_OBTIENE_VALORES");
            declareParameter(new SqlParameter("pv_idegenerador_i",OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerValoresMapper()));
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
  
  protected class ObtenerValoresMapper  implements RowMapper {
      public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
          ClavesVO clavesVO = new ClavesVO();
          
          clavesVO.setIdGenerador(rs.getString("IDGENERADOR"));
          clavesVO.setDescripcion(rs.getString("DESCRIPCION"));
          clavesVO.setIdParam(rs.getString("IDPARAM"));
          clavesVO.setValor(rs.getString("VALOR"));
          
          return clavesVO;
      }
  }

  
  
  protected class GuardarValores extends CustomStoredProcedure{
	    protected GuardarValores(DataSource dataSource) {
	        super(dataSource,"PKG_CATBO.P_ACTUALIZA_VALORES");
	        
	        declareParameter(new SqlParameter("pv_valor_i",OracleTypes.NUMERIC)); 
	        declareParameter(new SqlParameter("pv_idegenerador_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_idparam_i",OracleTypes.VARCHAR));
	        
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
