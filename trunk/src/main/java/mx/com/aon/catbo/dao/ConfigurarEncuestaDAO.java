package mx.com.aon.catbo.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import mx.com.aon.catbo.dao.ConsultarAsigEncuestaDAO.GuardarAsigEncuesta;
import mx.com.aon.catbo.dao.ConsultarAsigEncuestaDAO.ObtenerAsigEncuestaMapper;
import mx.com.aon.catbo.model.AsigEncuestaVO;
import mx.com.aon.catbo.model.EncuestaVO;
import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;
//import mx.com.aon.portal.dao.PersonaDAO.ObtienePersona;
//import mx.com.aon.portal.dao.PersonaDAO.PersonaMapper;
import mx.com.aon.portal.model.PersonaVO;
import mx.com.aon.portal.util.ConvertUtil;
//import mx.com.aon.portal.util.c
import mx.com.aon.portal.util.WrapperResultados;

public class ConfigurarEncuestaDAO extends AbstractDAO{
	
	private static Logger logger = Logger.getLogger(ConfigurarEncuestaDAO.class);
	
	protected void initDao() throws Exception {        
		addStoredProcedure("GUARDACONFIGURACION_ENCUESTA",new GuardarConfigurarEncuesta(getDataSource()));        
		addStoredProcedure("OBTIENE_POLIZA",new ObtienePoliza(getDataSource()));  
		 addStoredProcedure("GUARDAR_ASIGNACION_ENCUESTA",new GuardarAsigEncuesta(getDataSource()));
		 addStoredProcedure("GUARDACONFIGURACION_ENCUESTA_EDITAR",new GuardarConfigurarEncuestaEditar(getDataSource()));
		 addStoredProcedure("OBTENERREG_ENCUESTA_PREGUNTAS",new ObtieneTencuestaReg(getDataSource()));
		 
		 
		
    }

	protected class GuardarConfigurarEncuesta extends CustomStoredProcedure{

     	protected GuardarConfigurarEncuesta(DataSource dataSource) {
             super(dataSource, "PKG_ENCUESTAS.P_GUARDA_TCONFIGENCUESTA");

             declareParameter(new SqlParameter("pv_nmconfig_i",OracleTypes.NUMERIC));
             declareParameter(new SqlParameter("pv_cdunieco_i",OracleTypes.NUMERIC));
             declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
             declareParameter(new SqlParameter("pv_cdelemento_i",OracleTypes.NUMERIC));
             declareParameter(new SqlParameter("pv_cdproceso_i",OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_cdcampan_i",OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("pv_cdmodulo_i",OracleTypes.NUMERIC));
             declareParameter(new SqlParameter("pv_cdencuesta_i",OracleTypes.NUMERIC));
             declareParameter(new SqlParameter("pv_fedesde_i",OracleTypes.DATE));
             declareParameter(new SqlParameter("pv_fehasta_i",OracleTypes.DATE));
             
             declareParameter(new SqlOutParameter("pv_nmconfig_o", OracleTypes.NUMERIC));  
             declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
             declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
           
             compile();
           }

     	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            
            
            BigDecimal bigInteger = (BigDecimal)map.get("pv_nmconfig_o");
           
            if(bigInteger==null){
            	
            	wrapperResultados.setResultado("");
            	//wrapperResultados.setMsgTitle("2");
            }
            else {
            	 wrapperResultados.setResultado(String.valueOf(bigInteger.intValue()));
            }
           
            //wrapperResultados.setResultado((String)map.get("pv_nmconfig_o"));
                       
            return wrapperResultados;
        }
       }
	
	
	protected class ObtienePoliza extends CustomStoredProcedure{

     	protected ObtienePoliza(DataSource dataSource) {
             super(dataSource, "PKG_ENCUESTAS.P_OBTIENE_POLIZA");

             declareParameter(new SqlParameter("pv_nmconfig_i",OracleTypes.NUMERIC));
                         
            // declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerPolizaMapper()));
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
	
	 protected class ObtenerPolizaMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	AsigEncuestaVO asigEncuestaVO = new AsigEncuestaVO();
	            
	        	asigEncuestaVO.setNmConfig(rs.getString("NMCONFIG"));
	        	asigEncuestaVO.setCdUnieco(rs.getString("CDUNIECO"));
	        	asigEncuestaVO.setCdRamo(rs.getString("CDRAMO"));
	        	asigEncuestaVO.setCdElemento(rs.getString("CDELEMENTO"));
	        	asigEncuestaVO.setEstado(rs.getString("ESTADO"));
	         	asigEncuestaVO.setNmPoliza(rs.getString("NMPOLIZA")); 
	           	asigEncuestaVO.setCdPerson(rs.getString("CDPERSON"));
	          	asigEncuestaVO.setFeRegistro(rs.getString("FEREGISTRO"));
	           	asigEncuestaVO.setStatus(rs.getString("ESTADOENCUESTA"));
	            asigEncuestaVO.setCdUsuario(rs.getString("USUARIORES"));//setCdUsusari(rs.getString("CDUSUARI"));//setCdUsuario(rs.getString("CDUSUARI"));////
	                    
	        	            
	            return asigEncuestaVO;
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
	 
	 protected class GuardarConfigurarEncuestaEditar extends CustomStoredProcedure{

	     	protected GuardarConfigurarEncuestaEditar(DataSource dataSource) {
	             super(dataSource, "PKG_ENCUESTAS.P_GUARDA_TCONFIGENCUESTA");

	             declareParameter(new SqlParameter("pv_nmconfig_i",OracleTypes.NUMERIC));
	             declareParameter(new SqlParameter("pv_cdunieco_i",OracleTypes.NUMERIC));
	             declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
	             declareParameter(new SqlParameter("pv_cdelemento_i",OracleTypes.NUMERIC));
	             declareParameter(new SqlParameter("pv_cdproceso_i",OracleTypes.VARCHAR));
	             declareParameter(new SqlParameter("pv_cdcampan_i",OracleTypes.VARCHAR));
	             declareParameter(new SqlParameter("pv_cdmodulo_i",OracleTypes.VARCHAR));
	             declareParameter(new SqlParameter("pv_cdencuesta_i",OracleTypes.NUMERIC));
	             declareParameter(new SqlParameter("pv_fedesde_i",OracleTypes.DATE));
	             declareParameter(new SqlParameter("pv_fehasta_i",OracleTypes.DATE));
	             
	             declareParameter(new SqlOutParameter("pv_nmconfig_o", OracleTypes.NUMERIC));  
	             declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	             declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
	           
	             compile();
	           }

	     	 public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	              return mapper.build(map);
	          }
	     	/*public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            BigDecimal bigInteger = (BigDecimal)map.get("pv_nmconfig_o");
	            wrapperResultados.setResultado(String.valueOf(bigInteger.intValue()));
	            //wrapperResultados.setResultado((String)map.get("pv_nmconfig_o"));
	            return wrapperResultados;
	        }*/
	       }
	 
	 
	 
	 
	 //Obtener registro
	 protected class ObtieneTencuestaReg extends CustomStoredProcedure {

	      protected ObtieneTencuestaReg(DataSource dataSource) {
	          super(dataSource, "PKG_ENCUESTAS.P_OBTIENE_TENCUEST_REG");
	         //declareParameter(new SqlParameter("pv_cdencuesta_i",OracleTypes.NUMERIC));
	          
	         declareParameter(new SqlParameter("pv_cdencuesta_i",OracleTypes.VARCHAR));

	          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new TencuestaRegMapper()));
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
	 	 
	 protected class TencuestaRegMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	
	        	EncuestaVO encuestaVO = new EncuestaVO();
	            
	        	encuestaVO.setCdEncuesta(rs.getString("CDENCUESTA")); //.setNmConfig(rs.getString("NMCONFIG"));
	        	encuestaVO.setDsEncuesta(rs.getString("DSENCUESTA"));
	        	encuestaVO.setSwEstado(rs.getString("SWESTADO"));
	        	//encuestaVO.setFeRegistro(rs.getDate("FEREGISTRO"));//setFeRegistro();
	        	encuestaVO.setFeRegistro(ConvertUtil.convertToString(rs.getDate("FEREGISTRO"))); //.setFeInicio(ConvertUtil.convertToString(rs.getDate("FEINICIO")));
	        	
	                  	        	            
	            return encuestaVO;
	        }
	    }
	 
	 
	 

}
