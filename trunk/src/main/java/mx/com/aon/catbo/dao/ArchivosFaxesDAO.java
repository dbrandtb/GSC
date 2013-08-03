package mx.com.aon.catbo.dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.RowMapper;
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
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.catbo.model.ArchivoVO;
import mx.com.aon.catbo.model.ArchivosFaxesVO;
import mx.com.aon.catbo.model.CompraTiempoVO;
import mx.com.aon.catbo.model.FaxesVO;


public class ArchivosFaxesDAO extends AbstractDAO{


	 protected void initDao() throws Exception {
         
         addStoredProcedure("OBTENER_FAXES",new ObtenerFaxes(getDataSource()));
         addStoredProcedure("BORRAR_FAX",new BorrarFax(getDataSource()));
         addStoredProcedure("BORRAR_VALORES_FAX",new BorrarValoresFax(getDataSource()));
         addStoredProcedure("OBTENER_VALORES_FAX",new ObtenerValoresFaxes(getDataSource()));
         addStoredProcedure("OBTIENE_ARCHIVO_EXPORT",new ObtieneArchivoExport(getDataSource()));
         addStoredProcedure("BORRAR_DETALLE_FAX",new BorrarDetalleFax(getDataSource()));
         
       }
       
    protected class ObtenerFaxes extends CustomStoredProcedure {
    
    protected ObtenerFaxes(DataSource dataSource) {
        super(dataSource, "PKG_CATBO.P_OBTIENE_MFAXES");
        declareParameter(new SqlParameter("pv_dsarchivo_i", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("pv_nmfax_i", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("pv_nmpoliex_i", OracleTypes.VARCHAR));
        
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
        	FaxesVO faxesVO = new FaxesVO();
            
        	faxesVO.setCdtipoar(rs.getString("CDTIPOAR"));
        	faxesVO.setDsarchivo(rs.getString("DSARCHIVO"));
        	faxesVO.setNmfax(rs.getString("NMFAX"));
        	faxesVO.setNmpoliex(rs.getString("NMPOLIEX"));
        	faxesVO.setNmcaso(rs.getString("NMCASO"));
        	faxesVO.setCdusuario(rs.getString("CDUSUARIO"));
        	faxesVO.setDsusuari(rs.getString("DSUSUARI"));
        	faxesVO.setFerecepcion(ConvertUtil.convertToString(rs.getDate("FERECEPCION")).toString());
        	//faxesVO.setDsusuari(rs.getString("FERECEPCION"));
            
            return faxesVO;
        }
    }
    
    protected class BorrarFax extends CustomStoredProcedure{
   	 protected BorrarFax(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_BORRA_MFAXES");
            declareParameter(new SqlParameter("pv_nmfax_i",OracleTypes.NUMERIC));
                        
            declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
            compile();
          }


          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }
   	
   }
    
    protected class BorrarValoresFax extends CustomStoredProcedure{
      	 protected BorrarValoresFax(DataSource dataSource) {
               super(dataSource, "PKG_CATBO.P_BORRA_TVALOFAX");
               declareParameter(new SqlParameter("pv_nmfax_i",OracleTypes.NUMERIC));
                           
               declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
               declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
               compile();
             }


             public WrapperResultados mapWrapperResultados(Map map) throws Exception {
                 WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
                 return mapper.build(map);
             }
      	
      }
       
    protected class ObtenerValoresFaxes extends CustomStoredProcedure {
        
        protected ObtenerValoresFaxes(DataSource dataSource) {
            super(dataSource, "PKG_CATBO.P_OBTIENE_TVALOFAX");
            
            declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmfax_i", OracleTypes.NUMERIC));
                        
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerValoresFaxesMapper()));
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
        
       protected class ObtenerValoresFaxesMapper  implements RowMapper {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            	FaxesVO faxesVO = new FaxesVO();
                
            	faxesVO.setCdatribu(rs.getString("CDATRIBU"));
            	faxesVO.setCdtipoar(rs.getString("CDTIPOAR"));
            	faxesVO.setNmcaso(rs.getString("NMCASO"));
            	faxesVO.setNmfax(rs.getString("NMFAX"));
            	faxesVO.setOtvalor(rs.getString("OTVALOR"));
            	                
                return faxesVO;
            }
        }

       
      protected class ObtieneArchivoExport extends CustomStoredProcedure {

      protected ObtieneArchivoExport(DataSource dataSource) {
          super(dataSource, "PKG_CATBO.P_OBTIENE_MFAXES");
          declareParameter(new SqlParameter("pv_dsarchivo_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_nmfax_i", OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_nmpoliex_i", OracleTypes.VARCHAR));
          
          
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
        	FaxesVO faxesVO = new FaxesVO();
        	faxesVO.setCdtipoar(rs.getString("CDTIPOAR"));
        	faxesVO.setDsarchivo(rs.getString("DSARCHIVO"));
        	faxesVO.setNmfax(rs.getString("NMFAX"));
        	faxesVO.setNmpoliex(rs.getString("NMPOLIEX"));
        	faxesVO.setNmcaso(rs.getString("NMCASO"));
        	faxesVO.setCdusuari(rs.getString("CDUSUARIO"));
        	faxesVO.setDsusuari(rs.getString("DSUSUARI"));
        	faxesVO.setFerecepcion(rs.getString("FERECEPCION"));
            return faxesVO;
        }

    }

    protected class ObtieneArchivoMapperExport  implements RowMapper {
        @SuppressWarnings("unchecked")
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArrayList lista =  new ArrayList(6);
            lista.add(rs.getString("DSARCHIVO"));
            lista.add(rs.getString("NMFAX"));
            lista.add(rs.getString("NMPOLIEX"));
            lista.add(rs.getString("NMCASO"));
            lista.add(rs.getString("DSUSUARI"));
            lista.add(rs.getString("FERECEPCION"));
            return lista;
        }
    }

    protected class BorrarDetalleFax extends CustomStoredProcedure{
      	 protected BorrarDetalleFax(DataSource dataSource) {
               super(dataSource, "PKG_CATBO.P_BORRA_MFAXES");
               declareParameter(new SqlParameter("pv_nmfax_i",OracleTypes.NUMERIC));
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
