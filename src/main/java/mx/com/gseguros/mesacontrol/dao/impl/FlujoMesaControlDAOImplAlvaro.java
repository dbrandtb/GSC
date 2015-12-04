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

public class FlujoMesaControlDAOImplAlvaro extends AbstractManagerDAO implements FlujoMesaControlDAO {
	
	private final static Logger logger = LoggerFactory.getLogger(FlujoMesaControlDAOImplAlvaro.class);
	
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
	public List<Map<String,String>> recuperaTiconos() throws Exception
	{
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTiconosSP(getDataSource()),new HashMap<String,String>());
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTiconosSP extends StoredProcedure
	{
		protected RecuperaTiconosSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TICONOS");
			String[] cols=new String[]{ "CDICONO" , "DSICONO" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperaTflujomc(String cdtipflu) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		params.put("cdtipflu" , cdtipflu);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTflujomcSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTflujomcSP extends StoredProcedure
	{
		protected RecuperaTflujomcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUJOMC");
			declareParameter(new SqlParameter("cdtipflu" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","DSFLUJOMC","SWFINAL" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoTtipflumc(
			String cdtipflu
			,String dstipflu
			,String cdtiptra
			,String swmultipol
			,String swreqpol
			,String accion
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"   , cdtipflu);
		params.put("dstipflu"   , dstipflu);
		params.put("cdtiptra"   , cdtiptra);
		params.put("swmultipol" , swmultipol);
		params.put("swreqpol"   , swreqpol);
		params.put("accion"     , accion);
		ejecutaSP(new MovimientoTtipflumcSP(getDataSource()),params);
	}
	
	protected class MovimientoTtipflumcSP extends StoredProcedure
	{
		protected MovimientoTtipflumcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TTIPFLUMC");
			declareParameter(new SqlParameter("cdtipflu"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dstipflu"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtiptra"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swmultipol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swreqpol"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

}
