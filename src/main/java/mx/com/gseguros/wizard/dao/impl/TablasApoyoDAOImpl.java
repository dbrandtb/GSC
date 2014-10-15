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
import mx.com.gseguros.wizard.dao.impl.WizardDAOImpl.GuardaAtributosTablaApoyo;
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
			declareParameter(new SqlParameter("PV_OTCLAVE1_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTCLAVE2_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTCLAVE3_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTCLAVE4_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTCLAVE5_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_FEDESDE_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_FEHASTA_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_LIMITE_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_REGISTRO_O" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter(" PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String guardaValoresTablaApoyo(Map<String,String> params) throws Exception {
		Map<String, Object> resultado = ejecutaSP(new GuardaValoresTablaApoyo(getDataSource()), params);
		return (String) resultado.get("PV_TITLE_O");
	}
	
	protected class GuardaValoresTablaApoyo extends StoredProcedure {
		protected GuardaValoresTablaApoyo(DataSource dataSource) {
			super(dataSource,"PKG_TABAPOYO.P_MOV_TTAPVAAT");
			declareParameter(new SqlParameter("PV_ACCION_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_SWCOMMIT_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_NMTABLA_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTCLAVE1_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTCLAVE2_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTCLAVE3_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTCLAVE4_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTCLAVE5_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_FEDESDE_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_FEHASTA_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR01_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR02_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR03_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR04_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR05_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR06_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR07_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR08_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR09_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR10_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR11_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR12_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR13_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR14_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR15_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR16_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR17_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR18_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR19_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR20_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR21_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR22_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR23_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR24_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR25_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR26_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}

}