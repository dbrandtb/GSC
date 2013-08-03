package mx.com.aon.portal.dao;

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

import mx.com.aon.portal.util.WrapperResultados;


public class ConfiguracionAlertasAutomaticosDAO extends AbstractDAO{


	 protected void initDao() throws Exception {
        
       
         
         addStoredProcedure("INSERTA_CONF_ALERTA_AUTO",new GuardarConfiguracionAlerta(getDataSource()));
         
 
     }
           
  protected class GuardarConfiguracionAlerta extends CustomStoredProcedure{
	    protected GuardarConfiguracionAlerta(DataSource dataSource) {
	        super(dataSource,"PKG_ALERTAS.P_GUARDA_CONFALERTA");
	        
	        declareParameter(new SqlParameter("pv_cdidunico_i",OracleTypes.NUMERIC)); 
	        declareParameter(new SqlParameter("pv_usuario_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_cve_cliente_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_nombre_alerta_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_cve_proceso_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_cve_temporalidad_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_mensaje_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_frecuencia_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_fec_inic_i",OracleTypes.DATE));
	        declareParameter(new SqlParameter("pv_dias_antic_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_duracion_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_mandar_email_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_popup_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_pantalla_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_rol_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_cve_ramo_i",OracleTypes.VARCHAR));
	        declareParameter(new SqlParameter("pv_cve_aseguradora_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_cve_producto_i",OracleTypes.NUMERIC));
	        declareParameter(new SqlParameter("pv_region_i",OracleTypes.VARCHAR));
	        
	        	   
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
