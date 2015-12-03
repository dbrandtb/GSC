package mx.com.gseguros.mesacontrol.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.mesacontrol.dao.FlujoMesaControlDAO;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import oracle.jdbc.driver.OracleTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class FlujoMesaControlDAOImplRIC extends AbstractManagerDAO implements FlujoMesaControlDAO {
	
	private final static Logger logger = LoggerFactory.getLogger(FlujoMesaControlDAOImplRIC.class);
	
	@Override
	public List<Map<String,String>> recuperaTtiptramc() throws Exception
	{
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTtiptramcSP(getDataSource()),new HashMap<String,String>());
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTtiptramcSP extends StoredProcedure
	{
		protected RecuperaTtiptramcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TTIPTRAMC");
			String[] cols=new String[]{ "CDTIPTRA" , "DSTIPTRA" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public List<Map<String,String>> recuperaTtipflumc() throws Exception
	{
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTtipflumcSP(getDataSource()),new HashMap<String,String>());
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTtipflumcSP extends StoredProcedure
	{
		protected RecuperaTtipflumcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TTIPFLUMC");
			String[] cols=new String[]{ "CDTIPFLU" , "DSTIPFLU", "CDTIPTRA","SWMULTIPOL","SWREQPOL" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public List<Map<String,String>> recuperaTdocume() throws Exception {
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTtiptramcSP(getDataSource()),new HashMap<String,String>());
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null) {
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTdocumeSP extends StoredProcedure {
		protected RecuperaTdocumeSP(DataSource dataSource) {
			super(dataSource,"PKG_MESACONTROL.P_GET_TDOCUME");
			String[] cols=new String[]{ "CDDOCUME","DSDOCUME","CDTIPTRA" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> RecuperaTfluprocSP(String cdtipflu, String cdflujomc) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("cdtipflu", cdtipflu);
		params.put("cdflujomc", cdflujomc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTfluprocSP(getDataSource()), params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null) {
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTfluprocSP extends StoredProcedure {
		protected RecuperaTfluprocSP(DataSource dataSource) {
			super(dataSource,"PKG_MESACONTROL.P_FLU_PROC");
			declareParameter(new SqlParameter("cdtipflu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc", OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDPROCMC","WEBID","XPOS","YPOS","WIDTH","HEIGHT" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	

}
