package mx.com.gseguros.wizard.dao.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.DinamicMapper;
import mx.com.gseguros.wizard.dao.TablasApoyoDAO;
import mx.com.gseguros.wizard.dao.impl.WizardDAOImpl.ObtieneClavesTablaApoyo;

public class TablasApoyoDAOImpl extends AbstractManagerDAO implements TablasApoyoDAO {
	
	
	@Override
	public List<Map<String, String>> obtieneValoresTablaApoyo5claves(Map<String,String> params) throws Exception {
		Map<String, Object> resultado = ejecutaSP(new ObtieneValoresTablaApoyo5claves(getDataSource()), params);
		return (List<Map<String, String>>) resultado.get("PV_REGISTRO_O");
	}
	
	protected class ObtieneValoresTablaApoyo5claves extends StoredProcedure {
		protected ObtieneValoresTablaApoyo5claves(DataSource dataSource) {
			super(dataSource,"PKG_TABAPOYO.P_OBTIENE_TTAPVAAT");
			declareParameter(new SqlParameter("PV_NMTABLA_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_REGISTRO_O" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter(" PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}

}