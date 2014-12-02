package mx.com.gseguros.wizard.dao.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.wizard.dao.TablasApoyoDAO;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

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
			String[] cols = new String[]{
					"NMTABLA"
					,"OTCLAVE1"
					,"OTCLAVE2"
					,"OTCLAVE3"
					,"OTCLAVE4"
					,"OTCLAVE5"
					,"FEDESDE"
					,"FEHASTA"
					,"OTVALOR01"
					,"OTVALOR02"
					,"OTVALOR03"
					,"OTVALOR04"
					,"OTVALOR05"
					,"OTVALOR06"
					,"OTVALOR07"
					,"OTVALOR08"
					,"OTVALOR09"
					,"OTVALOR10"
					,"OTVALOR11"
					,"OTVALOR12"
					,"OTVALOR13"
					,"OTVALOR14"
					,"OTVALOR15"
					,"OTVALOR16"
					,"OTVALOR17"
					,"OTVALOR18"
					,"OTVALOR19"
					,"OTVALOR20"
					,"OTVALOR21"
					,"OTVALOR22"
					,"OTVALOR23"
					,"OTVALOR24"
					,"OTVALOR25"
					,"OTVALOR26"
			};
			declareParameter(new SqlOutParameter("PV_REGISTRO_O" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String, String>> obtieneValoresTablaApoyo1clave(Map<String,String> params) throws Exception {
		Map<String, Object> resultado = ejecutaSP(new ObtieneValoresTablaApoyo1clave(getDataSource()), params);
		return (List<Map<String, String>>) resultado.get("PV_REGISTRO_O");
	}
	
	protected class ObtieneValoresTablaApoyo1clave extends StoredProcedure {
		protected ObtieneValoresTablaApoyo1clave(DataSource dataSource) {
			super(dataSource,"PKG_TABAPOYO.P_OBTIENE_TTAPVAT1");
			declareParameter(new SqlParameter("PV_NMTABLA_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTCLAVE1_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_LIMITE_I" ,   OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"NMTABLA" , "OTCLAVE1" , "OTVALOR01"
			};
			declareParameter(new SqlOutParameter("PV_REGISTRO_O" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
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

	@Override
	public String guardaValoresTablaApoyo1Clave(Map<String,String> params) throws Exception {
		Map<String, Object> resultado = ejecutaSP(new GuardaValoresTablaApoyo1Clave(getDataSource()), params);
		return (String) resultado.get("PV_TITLE_O");
	}
	
	protected class GuardaValoresTablaApoyo1Clave extends StoredProcedure {
		protected GuardaValoresTablaApoyo1Clave(DataSource dataSource) {
			super(dataSource,"PKG_TABAPOYO.P_MOV_TTAPVAT1");
			declareParameter(new SqlParameter("PV_ACCION_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_SWCOMMIT_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_NMTABLA_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTCLAVE1_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_OTVALOR01_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}

}