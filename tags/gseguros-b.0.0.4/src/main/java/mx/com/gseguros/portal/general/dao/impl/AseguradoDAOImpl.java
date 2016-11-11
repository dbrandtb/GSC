package mx.com.gseguros.portal.general.dao.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.general.dao.AseguradoDAO;
import oracle.jdbc.driver.OracleTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class AseguradoDAOImpl extends AbstractManagerDAO implements AseguradoDAO {

	private Logger logger = LoggerFactory.getLogger(AseguradoDAOImpl.class);
	
	private static SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");	
	
	@Override
	public List<Map<String,String>> validaEdadAsegurados(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem) throws Exception {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("PV_CDUNIECO_I", cdunieco);
		params.put("PV_CDRAMO_I"  , cdramo);
		params.put("PV_ESTADO_I"  , estado);
		params.put("PV_NMPOLIZA_I", nmpoliza);
		params.put("PV_NMSUPLEM_I", nmsuplem);
		Map<String, Object> resultado = ejecutaSP(new ValidaEdadAseguradosSP(getDataSource()), params);
		return (List<Map<String, String>>) resultado.get("pv_registro_o");
	}
	
	protected class ValidaEdadAseguradosSP extends StoredProcedure {
		
		protected ValidaEdadAseguradosSP(DataSource dataSource) {
			
			super(dataSource, "PKG_CONSULTA_PRE.P_VALIDA_EDAD_ASEGURADOS");
            
			String[] cols = new String[]{"NOMBRE","PARENTESCO","EDAD","EDADMINI","EDADMAXI","SUPERAMINI","SUPERAMAXI"};
            
			declareParameter(new SqlParameter("PV_CDUNIECO_I"   , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("PV_CDRAMO_I"     , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("PV_ESTADO_I"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_NMPOLIZA_I"   , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("PV_NMSUPLEM_I"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new GenericMapper(cols)));
	        declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
			compile();
		}
	}
	
}