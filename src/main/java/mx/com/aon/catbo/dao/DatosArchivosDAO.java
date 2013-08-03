package mx.com.aon.catbo.dao;

import org.apache.log4j.Logger;
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
import mx.com.aon.catbo.model.DatosArchivosVO;
import mx.com.aon.catbo.model.FaxesVO;


public class DatosArchivosDAO extends AbstractDAO{

	 private static Logger logger = Logger.getLogger(DatosArchivosDAO.class);
	 
	 protected void initDao() throws Exception {
         
         addStoredProcedure("OBTENER_DATOS_ARCHIVOS",new ObtenerArchivos(getDataSource()));
        
         addStoredProcedure("OBTIENE_ARCHIVO_EXPORT",new ObtieneArchivoExport(getDataSource()));
                
       }
       
    protected class ObtenerArchivos extends CustomStoredProcedure {
    
    protected ObtenerArchivos(DataSource dataSource) {
        super(dataSource,"PKG_CATBO.P_OBTENER_TVALOARC");
        declareParameter(new SqlParameter("pv_dsarchivo_i", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("pv_nmtiparc_i", OracleTypes.VARCHAR));
                
        declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerArchivosMapper()));
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
    
    protected class ObtenerArchivosMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DatosArchivosVO datosArchivosVO = new DatosArchivosVO();
            
        	datosArchivosVO.setCdtipoar(rs.getString("CDTIPOAR"));
        	datosArchivosVO.setDsarchivo(rs.getString("DSARCHIVO"));
        	datosArchivosVO.setCdatribu(rs.getString("CDATRIBU"));
        	datosArchivosVO.setDsatribu(rs.getString("DSATRIBU"));
        	datosArchivosVO.setOtvalor(rs.getString("OTVALOR"));
        	//datosArchivosVO.setFeingreso(rs.getString("FEINGRESO"));
        	datosArchivosVO.setFeingreso(ConvertUtil.convertToString(rs.getDate("FEINGRESO")));           
            return datosArchivosVO;
        }
    }
    
    
             
      protected class ObtieneArchivoExport extends CustomStoredProcedure {

      protected ObtieneArchivoExport(DataSource dataSource) {
          super(dataSource, "PKG_CATBO.P_OBTENER_TVALOARC");
          declareParameter(new SqlParameter("pv_dsarchivo_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_nmtiparc_i", OracleTypes.VARCHAR));
                  
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
        	
            
        DatosArchivosVO datosArchivosVO = new DatosArchivosVO();
            
        	datosArchivosVO.setCdtipoar(rs.getString("CDTIPOAR"));
        	datosArchivosVO.setDsarchivo(rs.getString("DSARCHIVO"));
        	datosArchivosVO.setCdatribu(rs.getString("CDATRIBU"));
        	datosArchivosVO.setDsatribu(rs.getString("DSATRIBU"));
        	datosArchivosVO.setOtvalor(rs.getString("OTVALOR"));
        	datosArchivosVO.setFeingreso(rs.getString("FEINGRESO"));
        	           
            return datosArchivosVO;
        }

    }

    protected class ObtieneArchivoMapperExport  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArrayList lista =  new ArrayList(4);
            lista.add(rs.getString("CDTIPOAR"));
            lista.add(rs.getString("DSATRIBU"));
            lista.add(rs.getString("OTVALOR"));
           // lista.add(rs.getString("FEINGRESO"));
            lista.add(ConvertUtil.convertToString(rs.getDate("FEINGRESO")));
            return lista;
        }
    }


}
