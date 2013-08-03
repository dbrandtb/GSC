
package mx.com.aon.portal.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;
import mx.com.aon.portal.dao.EstructuraDAO.EstructuraMapperExport;
import mx.com.aon.portal.dao.EstructuraDAO.ObtieneEstructurasExport;
import mx.com.aon.portal.model.ConsultaPolizasCanceladasVO;
import mx.com.aon.portal.model.EquivalenciaCatalogosVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.util.ConvertUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EquivalenciaCatalogosDAO extends AbstractDAO{

    private static Logger logger = Logger.getLogger(EquivalenciaCatalogosDAO.class);


    protected void initDao() throws Exception {
        addStoredProcedure("BUSCAR_EQUIVALENCIA_CATALOGOS",new BuscarEquivalenciaCatalogos(getDataSource()));
        addStoredProcedure("BUSCAR_EQUIVALENCIA_CATALOGOS_EXPORT",new BuscarEquivalenciaCatalogosExport(getDataSource()));
        addStoredProcedure("BORRA_EQUIVALENCIA_CATALOGOS",new BorrarEquivalenciaCatalogos(getDataSource()));
        addStoredProcedure("GUARDA_EQUIVALENCIA_CATALOGO",new AgregarEquivalenciaCatalogo(getDataSource()));
        
        
                
    }


    protected class BuscarEquivalenciaCatalogos extends CustomStoredProcedure {

      protected BuscarEquivalenciaCatalogos(DataSource dataSource) {
          super(dataSource, "PKG_EQUIVALENCIA.P_OBTIENE_MEQUVTAB");

          declareParameter(new SqlParameter("pv_country_name_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cdtablaacw_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cdsistema_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cdtablaext_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_induso_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_dsusoacw_i", OracleTypes.VARCHAR));
  
          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new BuscarEquivalenciaCatalogosMapper()));
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
    
    
    protected class BuscarEquivalenciaCatalogosMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	EquivalenciaCatalogosVO equivalenciaCatalogosVO = new EquivalenciaCatalogosVO();

        	equivalenciaCatalogosVO.setCountryName(rs.getString("COUNTRY_NAME"));
        	equivalenciaCatalogosVO.setCdSistema(rs.getString("CDSISTEMA"));
        	equivalenciaCatalogosVO.setCdTablaAcw(rs.getString("CDTABLAACW"));
        	equivalenciaCatalogosVO.setCdTablaExt(rs.getString("CDTABLAEXT"));
        	equivalenciaCatalogosVO.setDescripc(rs.getString("DESCRIPC"));
        	equivalenciaCatalogosVO.setDsUsoAcw(rs.getString("DSUSOACW"));
        	equivalenciaCatalogosVO.setIndUso(rs.getString("INDUSO"));
        	equivalenciaCatalogosVO.setNmColumna(rs.getString("NMCOLUMNA"));
        	equivalenciaCatalogosVO.setNmTabla(rs.getString("NMTABLA"));
        	equivalenciaCatalogosVO.setCountryCode(rs.getString("COUNTRY_CODE"));
        	

            return equivalenciaCatalogosVO;
        }
    }
    
    
    protected class BuscarEquivalenciaCatalogosExport extends CustomStoredProcedure {

        protected BuscarEquivalenciaCatalogosExport(DataSource dataSource) {
            
            super(dataSource, "PKG_EQUIVALENCIA.P_OBTIENE_MEQUVTAB");
            
            declareParameter(new SqlParameter("pv_country_name_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdtablaacw_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdsistema_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdtablaext_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_induso_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_dsusoacw_i", OracleTypes.VARCHAR));

            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new BuscarEquivalenciaCatalogosMapperExport()));
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
    
    protected class BuscarEquivalenciaCatalogosMapperExport  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	 ArrayList lista =  new ArrayList(8);
             
             lista.add(rs.getString("COUNTRY_NAME"));  
             lista.add(rs.getString("CDSISTEMA"));  
             lista.add(rs.getString("CDTABLAACW")); 
             lista.add(rs.getString("CDTABLAEXT")); 
             lista.add(rs.getString("DESCRIPC"));    
             lista.add(rs.getString("DSUSOACW"));  
             lista.add(rs.getString("INDUSO"));    
             lista.add(rs.getString("NMCOLUMNA"));
                       
             return lista;
       }
    }
    
    protected class BorrarEquivalenciaCatalogos extends CustomStoredProcedure{
	   	 protected BorrarEquivalenciaCatalogos(DataSource dataSource) {
	            super(dataSource, "PKG_EQUIVALENCIA.P_BORRA_MEQUVTAB");
	            
	            declareParameter(new SqlParameter("pv_country_code_i",OracleTypes.VARCHAR));
	            declareParameter(new SqlParameter("pv_cdsistema_i",OracleTypes.VARCHAR));
	            declareParameter(new SqlParameter("pv_nmtabla_i",OracleTypes.VARCHAR));
	                        
	            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
	            compile();
	          }

	          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	              return mapper.build(map);
	          }
	   }
    
    protected class AgregarEquivalenciaCatalogo extends CustomStoredProcedure{
    	   protected AgregarEquivalenciaCatalogo(DataSource dataSource) {
    	       super(dataSource,"PKG_EQUIVALENCIA.P_GUARDA_MEQUVTAB");
    	       
    	       declareParameter(new SqlParameter("pv_country_code_i",OracleTypes.VARCHAR));
    	       declareParameter(new SqlParameter("pv_cdsistema_i",OracleTypes.VARCHAR));
    	       declareParameter(new SqlParameter("pv_nmtabla_i",OracleTypes.NUMERIC));
    	       declareParameter(new SqlParameter("pv_cdtablaacw_i",OracleTypes.VARCHAR));
    	       declareParameter(new SqlParameter("pv_cdtablaext_i",OracleTypes.VARCHAR));
    	       declareParameter(new SqlParameter("pv_induso_i",OracleTypes.VARCHAR));
    	       declareParameter(new SqlParameter("pv_nmcolumna_i",OracleTypes.NUMERIC));
    	       declareParameter(new SqlParameter("pv_dsusoacw_i",OracleTypes.VARCHAR));
    	          	          	           	       
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