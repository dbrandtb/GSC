package mx.com.aon.portal.dao;

import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class ConfiguradorPantallasDAO extends AbstractDAO {

	protected void initDao() throws Exception {                
		addStoredProcedure("GUARDA_PANTALLA_JDBCTEMPL", new GuardarPantalla(getDataSource()));
    }
    
	protected class GuardarPantalla extends CustomStoredProcedure {
		protected GuardarPantalla(DataSource dataSource) {
			super(dataSource, "PKG_CONFIGURA_PANTALLA2.P_GUARDA_PANTALLA");
			
			declareParameter(new SqlParameter("pv_cdconjunto_i", OracleTypes.NUMERIC));
			declareParameter(new SqlInOutParameter("pv_cdpantalla_io", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_dsnombrepantalla_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdmaster_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_dsdescpantalla_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsarchivo_i", OracleTypes.CLOB));
			declareParameter(new SqlParameter("PV_DSARCHIVOSEC_I", OracleTypes.CLOB));
			declareParameter(new SqlParameter("PV_DSCAMPOS_I", OracleTypes.CLOB));
			declareParameter(new SqlParameter("pv_dslabel_i", OracleTypes.CLOB));
			declareParameter(new SqlParameter("PV_CDSISROL_I", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("p_out_title", OracleTypes.VARCHAR));
			compile();
		}
		
		@SuppressWarnings("unchecked")
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
	}
	
}