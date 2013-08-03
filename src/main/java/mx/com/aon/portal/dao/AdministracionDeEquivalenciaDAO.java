package mx.com.aon.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.sql.DataSource;

//import mx.com.aon.catbo.dao.ArchivosFaxesDAO.ObtenerValoresFaxesMapper;
import mx.com.aon.catbo.model.ExtJSAtributosFaxVO;

import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.PolizaDAO;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;

import mx.com.aon.portal.model.Tabla_EquivalenciaVO;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class AdministracionDeEquivalenciaDAO extends AbstractDAO{

	private static Logger logger = Logger.getLogger(PolizaDAO.class);


    protected void initDao() throws Exception {

        addStoredProcedure("GUARDA_EQUIVALENCIA",new GuardarEquivalencia(getDataSource()));
        addStoredProcedure("OBTIENE_EQUIVALENCIA",new ObtenerEquivalencia(getDataSource()));
        addStoredProcedure("BORRA_EQUIVALENCIA",new BorrarEquivalencia(getDataSource()));
        addStoredProcedure("OBTENER_REPORTE",new ObtenerReporte(getDataSource()));
        addStoredProcedure("OBTIENE_REPORTE",new ObtieneReporte(getDataSource()));
        addStoredProcedure("OBTIENE_TABLA_EQUIVALENCIA",new ObtenerEquivalencia2(getDataSource()));
        addStoredProcedure("GENERAR_REPORTE",new GenerarReporte(getDataSource()));
     
   }


 
     protected class GuardarEquivalencia extends CustomStoredProcedure{

    	 protected GuardarEquivalencia(DataSource dataSource) {
             super(dataSource, "PKG_EQUIVALENCIA.P_GUARDA_TCATEQUV");

             declareParameter(new SqlParameter("pv_country_code_i",OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_cdsistema_i",OracleTypes.VARCHAR));
             
             declareParameter(new SqlParameter("pv_otclave01acw_i",OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_otclave01ext_i",OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_nmtabla_i",OracleTypes.NUMERIC));
             
             declareParameter(new SqlParameter("pv_otclave02acw_i",OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_otclave02ext_i",OracleTypes.VARCHAR));
             
             declareParameter(new SqlParameter("pv_otclave03acw_i",OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_otclave03ext_i",OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_otclave04acw_i",OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_otclave04ext_i",OracleTypes.VARCHAR));
             
             declareParameter(new SqlParameter("pv_otclave05acw_i",OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_otclave05ext_i",OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_cdtablaacw_i",OracleTypes.VARCHAR));
             
             declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
             declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
           
             compile();
           }

           public WrapperResultados mapWrapperResultados(Map map) throws Exception {
               WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
               return mapper.build(map);
           }
       }
     
     protected class ObtenerEquivalencia extends CustomStoredProcedure {
         
         protected ObtenerEquivalencia(DataSource dataSource) {
             super(dataSource, "PKG_EQUIVALENCIA.P_OBTIENE_TCATEQUV");
             
             declareParameter(new SqlParameter("pv_country_code_i", OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_cdsistema_i", OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_cdtablaacw_i", OracleTypes.VARCHAR));
                         
             declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerEquivalenciaMapper()));
             declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
             declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
             compile();
           }

           public WrapperResultados mapWrapperResultados(Map map) throws Exception {
               WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
               WrapperResultados wrapperResultados = mapper.build(map);
               List result = (List) map.get("pv_registro_o");
               wrapperResultados.setItemList(result);
               logger.debug("result P_OBTIENE_TCATEQUV=" + result);
               return wrapperResultados;
           }
       }
         
        protected class ObtenerEquivalenciaMapper  implements RowMapper {
             public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            	 Tabla_EquivalenciaVO tabla_EquivalenciaVO = new Tabla_EquivalenciaVO();
                 
            	 tabla_EquivalenciaVO.setCountry_code(rs.getString("COUNTRY_CODE"));
            	 tabla_EquivalenciaVO.setCdsistema(rs.getString("CDSISTEMA"));
            	 tabla_EquivalenciaVO.setOtclave01acw(rs.getString("OTCLAVE01ACW"));
            	 tabla_EquivalenciaVO.setOtclave01ext(rs.getString("OTCLAVE01EXT"));
            	 tabla_EquivalenciaVO.setOtclave02acw(rs.getString("OTCLAVE02ACW"));
            	 tabla_EquivalenciaVO.setOtclave02ext(rs.getString("OTCLAVE02EXT"));
            	 tabla_EquivalenciaVO.setOtclave03acw(rs.getString("OTCLAVE03ACW"));
            	 tabla_EquivalenciaVO.setOtclave03ext(rs.getString("OTCLAVE03EXT"));
            	 tabla_EquivalenciaVO.setOtclave04acw(rs.getString("OTCLAVE04ACW"));
            	 tabla_EquivalenciaVO.setOtclave04ext(rs.getString("OTCLAVE04EXT"));
            	 tabla_EquivalenciaVO.setOtclave05acw(rs.getString("OTCLAVE05ACW"));
            	 tabla_EquivalenciaVO.setOtclave05ext(rs.getString("OTCLAVE05EXT"));
            	 tabla_EquivalenciaVO.setOtvalorExt(rs.getString("OTVALOREXT"));
             	             	                
                 return tabla_EquivalenciaVO;
             }
         }     
        
        protected class ObtenerEquivalencia2 extends CustomStoredProcedure {
            
            protected ObtenerEquivalencia2(DataSource dataSource) {
                super(dataSource, "PKG_EQUIVALENCIA.p_obtiene_tcataext2");
                
                declareParameter(new SqlParameter("pv_country_code_i", OracleTypes.VARCHAR));
                declareParameter(new SqlParameter("pv_cdsistema_i", OracleTypes.VARCHAR));
                declareParameter(new SqlParameter("pv_cdtablaacw_i", OracleTypes.VARCHAR));
                
                declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerEquivalencia2Mapper()));
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
            
           protected class ObtenerEquivalencia2Mapper  implements RowMapper {
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
               	 Tabla_EquivalenciaVO tabla_EquivalenciaVO = new Tabla_EquivalenciaVO();
               	 
               	  tabla_EquivalenciaVO.setCdTablaExt(rs.getString("CDTABLAEXT"));
                  tabla_EquivalenciaVO.setNmcolumna(rs.getString ("NMCOLUMNA"));
               	  tabla_EquivalenciaVO.setNmcolumnatcataext(rs.getString("NMCOLUMNATCATAEXT"));
               	  tabla_EquivalenciaVO.setNmtabla(rs.getString("NMTABLA"));
               	
                    return tabla_EquivalenciaVO;
                }
            }
        
     protected class BorrarEquivalencia extends CustomStoredProcedure{
       	 protected BorrarEquivalencia(DataSource dataSource) {
                super(dataSource, "PKG_EQUIVALENCIA.P_BORRA_TCATEQUV");
                declareParameter(new SqlParameter("pv_country_code_i",OracleTypes.VARCHAR));
                declareParameter(new SqlParameter("pv_nmtabla_i",OracleTypes.NUMERIC));
                declareParameter(new SqlParameter("pv_cdsistema_i",OracleTypes.VARCHAR));
                declareParameter(new SqlParameter("pv_otclave01acw_i",OracleTypes.VARCHAR));
                declareParameter(new SqlParameter("pv_otclave01ext_i",OracleTypes.VARCHAR));
                            
                declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
                declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
                compile();
              }

              public WrapperResultados mapWrapperResultados(Map map) throws Exception {
                  WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
                  return mapper.build(map);
              }
       }
     
     
  protected class ObtenerReporte extends CustomStoredProcedure {
         
         protected ObtenerReporte(DataSource dataSource) {
             super(dataSource, "PKG_EQUIVALENCIA.P_REPORT_TABLAS_NO_CONCILIADAS");
             
             declareParameter(new SqlOutParameter("pv_registroacw_o", OracleTypes.CURSOR, new ObtenerReporteMapper()));
             declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
             declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
             compile();
           }

           public WrapperResultados mapWrapperResultados(Map map) throws Exception {
               WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
               WrapperResultados wrapperResultados = mapper.build(map);
               List result = (List) map.get("pv_registroacw_o");
               wrapperResultados.setItemList(result);
               return wrapperResultados;
           }
       }
         
        protected class ObtenerReporteMapper  implements RowMapper {
             public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            	 Tabla_EquivalenciaVO tabla_EquivalenciaVO = new Tabla_EquivalenciaVO();
                 
            	 tabla_EquivalenciaVO.setCdtablaacw(rs.getString("CDTABLAACW"));
               	 tabla_EquivalenciaVO.setCdTablaExt(rs.getString("CDTABLAEXT"));
               	 tabla_EquivalenciaVO.setNmcolumna(rs.getString("NMCOLUMNA"));
               	 tabla_EquivalenciaVO.setCdsistema(rs.getString("CDSISTEMA"));
            	              	             	                
                 return tabla_EquivalenciaVO;
             }
         }     
       
        protected class ObtieneReporte extends CustomStoredProcedure {
            
            protected ObtieneReporte(DataSource dataSource) {
                super(dataSource, "PKG_EQUIVALENCIA.P_REPORT_TABLAS_NO_CONCILIADAS");
                
                declareParameter(new SqlOutParameter("pv_registroext_o", OracleTypes.CURSOR, new ObtenerReporteMapper()));
                declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
                declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
                compile();
              }

              public WrapperResultados mapWrapperResultados(Map map) throws Exception {
                  WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
                  WrapperResultados wrapperResultados = mapper.build(map);
                  List result = (List) map.get("pv_registroext_o");
                  wrapperResultados.setItemList(result);
                  return wrapperResultados;
              }
          }
            
           protected class ObtieneReporteMapper  implements RowMapper {
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
               	 Tabla_EquivalenciaVO tabla_EquivalenciaVO = new Tabla_EquivalenciaVO();
                    
               	 tabla_EquivalenciaVO.setCdtablaacw(rs.getString("CDTABLAACW"));
               	 tabla_EquivalenciaVO.setCdTablaExt(rs.getString("CDTABLAEXT"));
               	 tabla_EquivalenciaVO.setNmcolumna(rs.getString("NMCOLUMNA"));
               	 tabla_EquivalenciaVO.setCdsistema(rs.getString("CDSISTEMA"));
               	              	             	                
                    return tabla_EquivalenciaVO;
                }
            }
           
           protected class GenerarReporte extends CustomStoredProcedure {
               
               protected GenerarReporte(DataSource dataSource) {
                   super(dataSource, "PKG_EQUIVALENCIA.P_REPORT_TABLAS_NO_CONCILIADAS");
                   
                   declareParameter(new SqlOutParameter("pv_registroacw_o", OracleTypes.CURSOR, new GenerarReporteMapper()));
                   declareParameter(new SqlOutParameter("pv_registroext_o", OracleTypes.CURSOR, new GenerarReporteMapper()));
                   declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
                   declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
                   compile();
                 }

                 public WrapperResultados mapWrapperResultados(Map map) throws Exception {
                     WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
                     WrapperResultados wrapperResultados = mapper.build(map);
                     //creamos mapa
                     HashMap itemMap = new HashMap();
                     itemMap.put("pv_registroacw_o",map.get("pv_registroacw_o"));
                     itemMap.put("pv_registroext_o",map.get("pv_registroext_o"));
                     //seteamos mapa
                     wrapperResultados.setItemMap(itemMap);
                     return wrapperResultados;
                 }
             }
               
              protected class GenerarReporteMapper  implements RowMapper {
                   public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                  	 Tabla_EquivalenciaVO tabla_EquivalenciaVO = new Tabla_EquivalenciaVO();
                       
                  	 tabla_EquivalenciaVO.setCdtablaacw(rs.getString("CDTABLAACW"));
                  	 tabla_EquivalenciaVO.setCdTablaExt(rs.getString("CDTABLAEXT"));
                  	 tabla_EquivalenciaVO.setNmcolumna(rs.getString("NMCOLUMNA"));
                  	 tabla_EquivalenciaVO.setCdsistema(rs.getString("CDSISTEMA"));
                  	              	             	                
                       return tabla_EquivalenciaVO;
                   }
               } 
        
        
	
}
