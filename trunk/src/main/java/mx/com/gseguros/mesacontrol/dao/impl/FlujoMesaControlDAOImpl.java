package mx.com.gseguros.mesacontrol.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.mesacontrol.dao.FlujoMesaControlDAO;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.utils.Utils;
import oracle.jdbc.driver.OracleTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class FlujoMesaControlDAOImpl extends AbstractManagerDAO implements FlujoMesaControlDAO {
	
	private final static Logger logger = LoggerFactory.getLogger(FlujoMesaControlDAOImpl.class);
	
	// SP 1
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
	
	// SP 2
	@Override
	public List<Map<String,String>> recuperaTtipflumc(String agrupamc) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("agrupamc" , agrupamc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTtipflumcSP(getDataSource()),params);
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
			declareParameter(new SqlParameter("agrupamc" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU" , "DSTIPFLU", "CDTIPTRA","SWMULTIPOL","SWREQPOL","CDTIPSUP" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 3
	@Override
	public List<Map<String,String>> recuperaTestadomc(String cdestadomc) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdestadomc" , cdestadomc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTestadomcSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTestadomcSP extends StoredProcedure
	{
		protected RecuperaTestadomcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TESTADOMC");
			declareParameter(new SqlParameter("cdestadomc" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDESTADOMC" , "DSESTADOMC" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 4
	@Override
	public List<Map<String,String>> recuperaTpantamc(String cdpantmc) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdpantmc" , cdpantmc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTpantamcSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTpantamcSP extends StoredProcedure
	{
		protected RecuperaTpantamcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TPANTMC");
			declareParameter(new SqlParameter("cdpantmc" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDPANTMC" , "DSPANTMC" , "URLPANTMC" , "SWEXTERNA"};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	//SP 5
	@Override
	public List<Map<String,String>> recuperaTcompmc(String cdcompmc) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		params.put("cdcompmc" , cdcompmc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTcompmcSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTcompmcSP extends StoredProcedure
	{
		protected RecuperaTcompmcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TCOMPMC");
			declareParameter(new SqlParameter("cdcompmc" , OracleTypes.VARCHAR));
			String[] cols=new String[]{"CDCOMPMC","DSCOMPMC","NOMCOMP"};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	//SP 6
	@Override
	public List<Map<String,String>> recuperaTprocmc(String cdprocmc) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		params.put("cdprocmc" , cdprocmc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTprocmcSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTprocmcSP extends StoredProcedure
	{
		protected RecuperaTprocmcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TPROCMC");
			declareParameter(new SqlParameter("cdprocmc", OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDPROCMC" , "DSPROCMC", "URLPROCMC" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 7
	@Override
	public List<Map<String,String>> recuperaTdocume() throws Exception {
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTdocumeSP(getDataSource()),new HashMap<String,String>());
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null) {
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTdocumeSP extends StoredProcedure {
		protected RecuperaTdocumeSP(DataSource dataSource) {
			super(dataSource,"PKG_MESACONTROL.P_GET_TDOCUME");
			String[] cols=new String[]{ "CDDOCUME","DSDOCUME","CDTIPTRA","DSTIPTRA" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 8
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
	
	
	// SP 9
	@Override
	public List<Map<String,String>> recuperaTflujomc(String cdtipflu, String swfinal) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		params.put("cdtipflu" , cdtipflu);
		params.put("swfinal"  , swfinal);
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
			declareParameter(new SqlParameter("swfinal"  , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","DSFLUJOMC","SWFINAL" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 10
	@Override
	public List<Map<String, String>> recuperaTfluest(String cdtipflu, String cdflujomc, String cdestadomc) throws Exception 
	{
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("cdtipflu"   , cdtipflu);
		params.put("cdflujomc"  , cdflujomc);
		params.put("cdestadomc" , cdestadomc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTfluestSP(getDataSource()), params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTfluestSP extends StoredProcedure
	{
		protected RecuperaTfluestSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUEST");
			declareParameter(new SqlParameter("cdtipflu"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdestadomc" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"CDTIPFLU"   , "CDFLUJOMC" , "CDESTADOMC"
					,"WEBID"     , "XPOS"      , "YPOS"
					,"TIMEMAX"   , "TIMEWRN1"  , "TIMEWRN2"
					,"CDTIPASIG" , "DSESTADOMC"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 11
	@Override
	public List<Map<String,String>> recuperaTfluestrol(String cdtipflu, String cdflujomc, String cdestadomc) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		params.put("cdtipflu" , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("cdestadomc" , cdestadomc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTfluestrolSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTfluestrolSP extends StoredProcedure
	{
		protected RecuperaTfluestrolSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUESTROL");
			declareParameter(new SqlParameter("cdtipflu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdestadomc" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDESTADOMC","CDSISROL", "SWVER", "SWTRABAJO", 
										"SWCOMPRA", "SWREASIG", "CDROLASIG" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 12
	@Override
	public List<Map<String,String>> recuperaTfluestavi(String cdtipflu, String cdflujomc, String cdestadomc) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		
		params.put("cdtipflu", cdtipflu);
		params.put("cdflujomc", cdflujomc);
		params.put("cdestadomc", cdestadomc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTfluestaviSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTfluestaviSP extends StoredProcedure
	{
		protected RecuperaTfluestaviSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUESTAVI");
			declareParameter(new SqlParameter("cdtipflu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdestadomc" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDESTADOMC","CDAVISO","CDTIPAVI","DSCOMENT","SWAUTOAVI","DSMAILAVI","CDUSUARIAVI","CDSISROLAVI"};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 13
	@Override
	public List<Map<String,String>> recuperaTflupant(String cdtipflu,String cdflujomc,String cdpantmc) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		params.put("cdtipflu"  , cdtipflu);
	    params.put("cdflujomc" , cdflujomc);
	    params.put("cdpantmc"  , cdpantmc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTflupantSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTflupantSP extends StoredProcedure
	{
		protected RecuperaTflupantSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUPANT");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdpantmc"  , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDPANTMC","WEBID","XPOS","YPOS", "DSPANTMC" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 14
	@Override
	public List<Map<String,String>> recuperaTflucomp(String cdtipflu, String cdflujomc) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		params.put("cdtipflu" , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTflucompSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTflucompSP extends StoredProcedure
	{
		protected RecuperaTflucompSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUCOMP");
			declareParameter(new SqlParameter("cdtipflu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDCOMPMC","WEBID","XPOS","YPOS","DSCOMPMC" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 15
	@Override
	public List<Map<String,String>> recuperaTfluproc(String cdtipflu, String cdflujomc) throws Exception {
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
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUPROC");
			declareParameter(new SqlParameter("cdtipflu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc", OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDPROCMC","WEBID","XPOS","YPOS","DSPROCMC" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 16
	@Override
	public List<Map<String, String>> recuperaTfluval(String cdtipflu, String cdflujomc, String cdvalida) throws Exception 
	{
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("cdvalida"  , cdvalida);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTfluvalSP(getDataSource()), params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTfluvalSP extends StoredProcedure
	{
		protected RecuperaTfluvalSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUVAL");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdvalida"  , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU", "CDFLUJOMC", "CDVALIDA", "DSVALIDA", "CDVALIDAFK", "WEBID", "XPOS", "YPOS" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 17
	@Override
	public List<Map<String,String>> recuperaTflurev(String cdtipflu, String cdflujomc) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		params.put("cdtipflu" , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTflurevSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTflurevSP extends StoredProcedure
	{
		protected RecuperaTflurevSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUREV");
			declareParameter(new SqlParameter("cdtipflu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDREVISI", "DSREVISI", "WEBID", "XPOS", "YPOS" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 18
	@Override
	public List<Map<String,String>> recuperaTflurevdoc(String cdtipflu, String cdflujomc, String cdrevisi) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		
		params.put("cdtipflu",cdtipflu);
		params.put("cdflujomc",cdflujomc);
		params.put("cdrevisi",cdrevisi);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTflurevdocSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTflurevdocSP extends StoredProcedure
	{
		protected RecuperaTflurevdocSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUREVDOC");
			declareParameter(new SqlParameter("cdtipflu", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdrevisi", OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDREVISI","CDDOCUME","SWOBLIGA"};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 19
	@Override
	public List<Map<String,String>> recuperaTfluacc(String cdtipflu,String cdflujomc) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		params.put("cdtipflu"  , cdtipflu);
	    params.put("cdflujomc" , cdflujomc);

		Map<String,Object>       procRes = ejecutaSP(new RecuperaTfluaccSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTfluaccSP extends StoredProcedure
	{
		protected RecuperaTfluaccSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUACC");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDACCION","DSACCION","CDICONO","CDVALOR","IDORIGEN","IDDESTIN","SWESCALA"};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 20
	@Override
	public List<Map<String,String>> recuperaTfluaccrol(String cdtipflu, String cdflujomc, String cdaccion) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		params.put("cdtipflu" , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("cdaccion" , cdaccion);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTfluaccrolSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperaTfluaccrolSP extends StoredProcedure
	{
		protected RecuperaTfluaccrolSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUACCROL");
			declareParameter(new SqlParameter("cdtipflu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdaccion" , OracleTypes.VARCHAR));
			
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDACCION","CDSISROL","SWPERMISO" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 21
	@Override
	public void movimientoTtipflumc(
			String cdtipflu
			,String dstipflu
			,String cdtiptra
			,String swmultipol
			,String swreqpol
			,String cdtipsup
			,String accion
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"   , cdtipflu);
		params.put("dstipflu"   , dstipflu);
		params.put("cdtiptra"   , cdtiptra);
		params.put("swmultipol" , swmultipol);
		params.put("swreqpol"   , swreqpol);
		params.put("cdtipsup"   , cdtipsup);
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
			declareParameter(new SqlParameter("cdtipsup"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 22
	@Override
	public void movimientoTflujomc(
			String cdtipflu
			,String cdflujomc
			,String dsflujomc
			,String swfinal
			,String accion
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("dsflujomc" , dsflujomc);
		params.put("swfinal"   , swfinal);
		params.put("accion"     , accion);
		ejecutaSP(new MovimientoTflujomcSP(getDataSource()),params);
	}
	
	protected class MovimientoTflujomcSP extends StoredProcedure
	{
		protected MovimientoTflujomcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUJOMC");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swfinal"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 23
	@Override
	public void movimientoTestadomc(String accion, String	cdestadomc, String dsestadomc) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdestadomc",cdestadomc);
		params.put("dsestadomc",dsestadomc);
		params.put("accion",accion);
		ejecutaSP(new MovimientoTestadomcSP(getDataSource()),params);
	}
	
	protected class MovimientoTestadomcSP extends StoredProcedure
	{
		protected MovimientoTestadomcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TESTADOMC");
			declareParameter(new SqlParameter("cdestadomc", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsestadomc", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 24
	@Override
	public void movimientoTfluest(
			String cdtipflu
			,String cdflujomc
			,String cdestadomc
			,String webid
			,String xpos
			,String ypos
			,String timemax
			,String timewrn1
			,String timewrn2
			,String cdtipasig
			,String accion
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"   , cdtipflu);
		params.put("cdflujomc"  , cdflujomc);
		params.put("cdestadomc" , cdestadomc);
		params.put("webid"      , webid);
		params.put("xpos"       , xpos);
		params.put("ypos"       , ypos);
		params.put("timemax"    , timemax);
		params.put("timewrn1"   , timewrn1);
		params.put("timewrn2"   , timewrn2);
		params.put("cdtipasig"  , cdtipasig);
		params.put("accion"     , accion);
		ejecutaSP(new MovimientoTfluestSP(getDataSource()),params);
	}
	
	protected class MovimientoTfluestSP extends StoredProcedure
	{
		protected MovimientoTfluestSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUEST");
			declareParameter(new SqlParameter("cdtipflu"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdestadomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("webid"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("xpos"       , OracleTypes.VARCHAR));		
			declareParameter(new SqlParameter("ypos"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("timemax"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("timewrn1"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("timewrn2"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipasig"  , OracleTypes.VARCHAR));	
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 25
	@Override
	public void movimientoTfluestrol(String cdtipflu,String cdflujomc,String cdestadomc,String cdsisrol,String swver,String swtrabajo,String swcompra,String swreasig,String cdrolasig,String accion) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"   , cdtipflu);
		params.put("cdflujomc"   , cdflujomc);
		params.put("cdestadomc"   , cdestadomc);
		params.put("cdsisrol"   , cdsisrol);
		params.put("swver"   , swver);
		params.put("swtrabajo"   , swtrabajo);
		params.put("swcompra"   , swcompra);
		params.put("swreasig"   , swreasig);
		params.put("cdrolasig"   , cdrolasig);
		params.put("accion"     , accion);
		
		ejecutaSP(new MovimientoTfluestrolSP(getDataSource()),params);
	}
	
	protected class MovimientoTfluestrolSP extends StoredProcedure
	{
		protected MovimientoTfluestrolSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUESTROL");
			declareParameter(new SqlParameter("cdtipflu"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdestadomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swcompra"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swreasig"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdrolasig"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swver"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swtrabajo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 26
	@Override
	public void movimientoTfluestavi(
			String cdtipflu,
			String cdflujomc,
			String cdestadomc,
			String cdaviso,
			String cdtipavi,
			String dscoment,
			String swautoavi,
			String dsmailavi,
			String cdusuariavi,
			String cdsisrolavi,
			String accion
			)throws Exception {
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"    , cdtipflu);
		params.put("cdflujomc"   , cdflujomc);
		params.put("cdestadomc"  , cdestadomc);
		params.put("cdaviso"     , cdaviso);
		params.put("cdtipavi"    , cdtipavi);
		params.put("dscoment"    , dscoment);
		params.put("swautoavi"   , swautoavi);
		params.put("dsmailavi"   , dsmailavi);
		params.put("cdusuariavi" , cdusuariavi);
		params.put("cdsisrolavi" , cdsisrolavi);
		params.put("accion"      , accion);
		ejecutaSP(new MovimientoTfluestaviSP(getDataSource()),params);
	}
	
	protected class MovimientoTfluestaviSP extends StoredProcedure {
		protected MovimientoTfluestaviSP(DataSource dataSource) {
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUESTAVI");
			declareParameter(new SqlParameter("cdtipflu"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdestadomc"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdaviso"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipavi"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dscoment"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swautoavi"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsmailavi"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuariavi" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrolavi" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"      , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 27
	@Override
	public void movimientoTpantmc (String cdpantmc, String dspantmc, 
			String urlpantmc, String swexterna, String accion
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdpantmc"   , cdpantmc);
		params.put("dspantmc"   , dspantmc);
		params.put("urlpantmc"   , urlpantmc);
		params.put("swexterna" , swexterna);
		params.put("accion"     , accion);
		ejecutaSP(new MovimientoTpantmcSP(getDataSource()),params);
	}
	
	protected class MovimientoTpantmcSP extends StoredProcedure
	{
		protected MovimientoTpantmcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TPANTMC");
			declareParameter(new SqlParameter("cdpantmc"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dspantmc"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("urlpantmc"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swexterna" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 28
	@Override
	public void movimientoTflupant(
			String cdtipflu
			,String cdflujomc
			,String cdpantmc
			,String webid
			,String xpos
			,String ypos
			,String accion
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("cdpantmc" , cdpantmc);
		params.put("webid"   , webid);
		params.put("xpos"   , xpos);
		params.put("ypos"   , ypos);
		params.put("accion"     , accion);
		ejecutaSP(new MovimientoTflupantSP(getDataSource()),params);
	}
	
	protected class MovimientoTflupantSP extends StoredProcedure
	{
		protected MovimientoTflupantSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUPANT");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdpantmc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("webid"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("xpos"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ypos"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 29
	@Override
	public void movimientoTcompmc(String accion, String cdcompmc, String dscompmc, String	nomcomp) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdcompmc",cdcompmc);
		params.put("dscompmc",dscompmc);
		params.put("nomcomp",nomcomp);
		params.put("accion",accion);
		ejecutaSP(new MovimientoTcompmcSP(getDataSource()),params);
	}
	
	protected class MovimientoTcompmcSP extends StoredProcedure
	{
		protected MovimientoTcompmcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TCOMPMC");
			declareParameter(new SqlParameter("cdcompmc", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dscompmc", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nomcomp", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 30
	@Override
	public void movimientoTflucomp(
			String cdtipflu
			,String cdflujomc
			,String cdcompmc
			,String webid
			,String xpos
			,String ypos
			,String accion
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("cdcompmc"  , cdcompmc);
		params.put("webid"     , webid);
		params.put("xpos"      , xpos);
		params.put("ypos"      , ypos);
		params.put("accion"    , accion);
		ejecutaSP(new MovimientoTflucompSP(getDataSource()),params);
	}
	
	protected class MovimientoTflucompSP extends StoredProcedure
	{
		protected MovimientoTflucompSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUCOMP");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdcompmc"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("webid"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("xpos"      , OracleTypes.VARCHAR));		
			declareParameter(new SqlParameter("ypos"      , OracleTypes.VARCHAR));	
			declareParameter(new SqlParameter("accion"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 31
	@Override
	public void movimientoTprocmc(String cdprocmc,String dsprocmc,String urlprocmc,String accion) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdprocmc"   , cdprocmc);
		params.put("dsprocmc"   , dsprocmc);
		params.put("urlprocmc"   , urlprocmc);
		params.put("accion"     , accion);
		
		ejecutaSP(new MovimientoTprocmcSP(getDataSource()),params);
	}
	
	protected class MovimientoTprocmcSP extends StoredProcedure
	{
		protected MovimientoTprocmcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TPROCMC");
			declareParameter(new SqlParameter("cdprocmc"     , OracleTypes.VARCHAR));			
			declareParameter(new SqlParameter("dsprocmc"     , OracleTypes.VARCHAR));		
			declareParameter(new SqlParameter("urlprocmc"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 32
	@Override
	public void movimientoTfluproc(
			String cdtipflu,
			String cdflujomc,
			String cdprocmc,
			String webid,
			String xpos,
			String ypos,
			String accion) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("cdprocmc"  , cdprocmc);
		params.put("webid"     , webid);
		params.put("xpos"      , xpos);
		params.put("ypos"      , ypos);
		params.put("accion"    , accion);
		ejecutaSP(new MovimientoTfluprocSP(getDataSource()),params);
	}
	
	protected class MovimientoTfluprocSP extends StoredProcedure
	{
		protected MovimientoTfluprocSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUPROC");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdprocmc"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("webid"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("xpos"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ypos"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 33
	@Override
	public String movimientoTfluval(String cdtipflu, String cdflujomc,
			String cdvalida, String dsvalida, String cdvalidafk, String webid,
			String xpos, String ypos, String accion) throws Exception 
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"   , cdtipflu);
		params.put("cdflujomc"  , cdflujomc);
		params.put("cdvalida"   , cdvalida);
		params.put("dsvalida"   , dsvalida);
		params.put("cdvalidafk" , cdvalidafk);
		params.put("webid"      , webid);
		params.put("xpos"       , xpos);
		params.put("ypos"       , ypos);
		params.put("accion"     , accion);
		Map<String,Object> procRes = ejecutaSP(new MovimientoTfluvalSP(getDataSource()),params);
		return (String)procRes.get("cdvalida");
	}
	
	protected class MovimientoTfluvalSP extends StoredProcedure
	{
		protected MovimientoTfluvalSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUVAL");
			declareParameter(new SqlParameter     ("cdtipflu"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("cdflujomc"  , OracleTypes.VARCHAR));
			declareParameter(new SqlInOutParameter("cdvalida"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("dsvalida"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("cdvalidafk" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("webid"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("xpos"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("ypos"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 34
	@Override
	public void movimientoTdocume(
			String cddocume
			,String dsdocume
			,String cdtiptra
			,String accion
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cddocume" , cddocume);
		params.put("dsdocume" , dsdocume);
		params.put("cdtiptra" , cdtiptra);
		params.put("accion"   , accion);
		ejecutaSP(new MovimientoTdocumeSP(getDataSource()),params);
	}
	
	protected class MovimientoTdocumeSP extends StoredProcedure
	{
		protected MovimientoTdocumeSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TDOCUME");
			declareParameter(new SqlParameter("cddocume"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsdocume" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtiptra" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 35
	@Override
	public String movimientoTflurev(
			String cdtipflu
			,String cdflujomc
			,String cdrevisi
			,String dsrevisi
			,String webid
			,String xpos
			,String ypos
			,String accion
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("cdrevisi"  , cdrevisi);
		params.put("dsrevisi"  , dsrevisi);
		params.put("webid"     , webid);
		params.put("xpos"      , xpos);
		params.put("ypos"      , ypos);
		params.put("accion"    , accion);
		Map<String,Object> procRes = ejecutaSP(new MovimientoTflurevSP(getDataSource()),params);
		return (String)procRes.get("cdrevisi");
	}
	
	protected class MovimientoTflurevSP extends StoredProcedure
	{
		protected MovimientoTflurevSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUREV");
			declareParameter(new SqlParameter     ("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlInOutParameter("cdrevisi"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("dsrevisi"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("webid"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("xpos"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("ypos"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("accion"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 36
	@Override
	public void movimientoTflurevdoc(
			String cdtipflu
			,String cdflujomc
			,String cdrevisi
			,String cddocume
			,String swobliga
			,String accion
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("cdrevisi"  , cdrevisi);
		params.put("cddocume"  , cddocume);
		params.put("swobliga"  , swobliga);
		params.put("accion"    , accion);
		ejecutaSP(new MovimientoTflurevdocSP(getDataSource()),params);
	}
	
	protected class MovimientoTflurevdocSP extends StoredProcedure
	{
		protected MovimientoTflurevdocSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUREVDOC");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdrevisi"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cddocume"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swobliga"  , OracleTypes.VARCHAR));		
			declareParameter(new SqlParameter("accion"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 37
	@Override
	public void actualizaIcono(String cdicono,String accion) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdicono"   , cdicono);
		params.put("accion"     , accion);
		
		ejecutaSP(new ActualizaIconoSP(getDataSource()),params);
	}
	
	protected class ActualizaIconoSP extends StoredProcedure
	{
		protected ActualizaIconoSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_UPT_ICONO");
			declareParameter(new SqlParameter("cdicono"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 38
	@Override
	public String movimientoTfluacc(
			String cdtipflu,
			String cdflujomc,
			String cdaccion,
			String dsaccion,
			String cdicono,
			String cdvalor,
			String idorigen,
			String iddestin,
			String swescala,
			String accion) throws Exception {
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("cdaccion"  , cdaccion);
		params.put("dsaccion"  , dsaccion);
		params.put("cdicono"   , cdicono);
		params.put("cdvalor"   , cdvalor);
		params.put("idorigen"  , idorigen);
		params.put("iddestin"  , iddestin);
		params.put("swescala"  , "S".equals(swescala) ? "S" : "N");
		params.put("accion"    , accion);
		Map<String,Object> procRes = ejecutaSP(new MovimientoTfluaccSP(getDataSource()),params);
		return (String)procRes.get("cdaccion");
	}
	
	protected class MovimientoTfluaccSP extends StoredProcedure {
		protected MovimientoTfluaccSP(DataSource dataSource) {
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUACC");
			declareParameter(new SqlParameter     ("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlInOutParameter("cdaccion"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("dsaccion"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("cdicono"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("cdvalor"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("idorigen"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("iddestin"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("swescala"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter     ("accion"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	// SP 39
	@Override
	public void movimientoTfluaccrol(String cdtipflu, String cdflujomc,
			String cdaccion, String cdsisrol, String swpermiso, String accion)
			throws Exception 
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"   , cdtipflu);
		params.put("cdflujomc"   , cdflujomc);
		params.put("cdaccion"   , cdaccion);
		params.put("cdsisrol"   , cdsisrol);
		params.put("swpermiso"   , swpermiso);
		params.put("accion"     , accion);
		ejecutaSP(new MovimientoTfluaccrolSP(getDataSource()),params);
	}
	
	protected class MovimientoTfluaccrolSP extends StoredProcedure
	{
		protected MovimientoTfluaccrolSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUACCROL");
			declareParameter(new SqlParameter("cdtipflu"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdaccion"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swpermiso" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String ejecutaExpresion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdexpres
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsituac" , nmsituac);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdexpres" , cdexpres);
		return (String)ejecutaSP(new EjecutaExpresionSP(getDataSource()),params).get("pv_result_o");
	}
	
	protected class EjecutaExpresionSP extends StoredProcedure
	{
		protected EjecutaExpresionSP(DataSource dataSource)
		{
			super(dataSource,"P_EXEC_EXPRESION");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdexpres" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_result_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizaCoordenadas(
			String cdtipflu
			,String cdflujomc
			,String tipo
			,String clave
			,String webid
			,String xpos
			,String ypos
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("tipo"      , tipo);
		params.put("clave"     , clave);
		params.put("webid"     , webid);
		params.put("xpos"      , xpos);
		params.put("ypos"      , ypos);
		ejecutaSP(new ActualizaCoordenadasSP(getDataSource()),params);
	}
	
	protected class ActualizaCoordenadasSP extends StoredProcedure
	{
		protected ActualizaCoordenadasSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_ACTUALIZA_COORDS");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipo"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("clave"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("webid"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("xpos"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ypos"      , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarTestadomcPorAgrupamc(String agrupamc) throws Exception
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("agrupamc" , agrupamc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarTestadomcPorAgrupamcSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperarTestadomcPorAgrupamcSP extends StoredProcedure
	{
		protected RecuperarTestadomcPorAgrupamcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TESTADOMC_X_AGRUPAMC");
			declareParameter(new SqlParameter("agrupamc" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDESTADOMC" , "DSESTADOMC" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,Object> recuperarTramites(
			String agrupamc
			,String status
			,String cdusuari
			,String cdsisrol
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String cdagente
			,String ntramite
			,String fedesde
			,String fehasta
			,int start
			,int limit
			)throws Exception
	{
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("agrupamc" , agrupamc);
		params.put("status"   , status);
		params.put("cdusuari" , cdusuari);
		params.put("cdsisrol" , cdsisrol);
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdagente" , cdagente);
		params.put("ntramite" , ntramite);
		params.put("fedesde"  , fedesde);
		params.put("fehasta"  , fehasta);
		params.put("start"    , start);
		params.put("limit"    , limit);
		Map<String,Object> procRes = ejecutaSP(new RecuperarTramitesSP(getDataSource()),params);
		Map<String,Object> result  = new HashMap<String,Object>();
		List<Map<String,String>> lista = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		Integer total = 0;
		try
		{
			total = Integer.parseInt((String)procRes.get("pv_total_o"));
		}
		catch(Exception ex)
		{
			total = 0;
		}
		logger.debug(Utils.log("\n******lista=",lista,"\n******total=",total));
		result.put("lista" , lista);
		result.put("total" , total);
		return result;
	}
	
	protected class RecuperarTramitesSP extends StoredProcedure
	{
		protected RecuperarTramitesSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TRAMITES");
			declareParameter(new SqlParameter("agrupamc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdagente" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("fedesde"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("fehasta"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("start"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("limit"    , OracleTypes.VARCHAR));
			String cols[]=new String[]{
					"NTRAMITE"  , "CDTIPFLU" , "DSTIPFLU" , "CDFLUJOMC" , "DSFLUJOMC"
					,"STATUS"   , "DSSTATUS" , "CDUNIECO" , "CDRAMO"    , "CDTIPSIT"
					,"DSTIPSIT" , "ESTADO"   , "NMPOLIZA" , "FECSTATU"  , "FERECEPC"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_total_o"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarTtipsupl(String cdtiptra) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtiptra" , cdtiptra);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarTtipsupl(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperarTtipsupl extends StoredProcedure
	{
		protected RecuperarTtipsupl(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TTIPSUPL");
			declareParameter(new SqlParameter("cdtiptra" , OracleTypes.VARCHAR));
			String cols[]=new String[]{ "CDTIPSUP", "DSTIPSUP" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String> recuperarPolizaUnica(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarPolizaUnicaSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException(Utils.join("No existe la p\u00f3liza para la sucursal ",cdunieco," y el producto ",cdramo));
		}
		else if(lista.size()>1)
		{
			throw new ApplicationException(Utils.join("P\u00f3liza duplicada para la sucursal ",cdunieco," y el producto ",cdramo));
		}
		return lista.get(0);
	}
	
	protected class RecuperarPolizaUnicaSP extends StoredProcedure
	{
		protected RecuperarPolizaUnicaSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_REVISA_POLIZA_PARA_TRAMITE");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			String cols[]=new String[]{ "FEEFECTO", "FEEMISIO", "CONTRATANTE" , "AGENTE" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	//TODO: NO SE ESTA USANDO
	@Override
	public String ejecutaValidacion(
			String ntramite
			,String status
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdvalidafk
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("ntramite"   , ntramite);
		params.put("status"     , status);//<<< NO SE USA
		params.put("cdunieco"   , cdunieco);
		params.put("cdramo"     , cdramo);
		params.put("estado"     , estado);
		params.put("nmpoliza"   , nmpoliza);
		params.put("nmsituac"   , nmsituac);
		params.put("nmsuplem"   , nmsuplem);
		params.put("cdvalidafk" , cdvalidafk);
		return (String)ejecutaSP(new EjecutaValidacionSP(getDataSource()),params).get("pv_result_o");
	}
	
	protected class EjecutaValidacionSP extends StoredProcedure
	{
		protected EjecutaValidacionSP(DataSource dataSource)
		{
			super(dataSource,"P_EXEC_VALIDACION");
			declareParameter(new SqlParameter("cdunieco"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdvalidafk" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_result_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarAccionesEntidad(
			String cdtipflu
			,String cdflujomc
			,String tipoent
			,String cdentidad
			,String webid
			,String cdsisrol
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("tipoent"   , tipoent);
		params.put("cdentidad" , cdentidad);
		params.put("webid"     , webid);
		params.put("cdsisrol"  , cdsisrol);
		Map<String,Object>       procRes = ejecutaSP(new CargarAccionesEntidadSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class CargarAccionesEntidadSP extends StoredProcedure
	{
		protected CargarAccionesEntidadSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUACC_X_ENTIDAD");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipoent"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdentidad" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("webid"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol"  , OracleTypes.VARCHAR));
			String cols[]=new String[]{
					"CDTIPFLU"  , "CDFLUJOMC"  , "CDACCION" , "DSACCION"
					,"CDICONO"  , "CDVALOR"    , "IDORIGEN" , "IDDESTIN"
					,"CDESTADOMC" , "WEBIDESTADO"
					,"CDPANTMC"   , "WEBIDPANT"
					,"CDCOMPMC"   , "WEBIDCOMP"
					,"CDPROCMC"   , "WEBIDPROC"
					,"CDVALIDA"   , "WEBIDVALIDA"
					,"CDREVISI"   , "WEBIDREVISI"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarDocumentosRevisionFaltantes(
			String cdtipflu
			,String cdflujomc
			,String cdrevisi
			,String ntramite
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("cdrevisi"  , cdrevisi);
		params.put("ntramite"  , ntramite);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarDocumentosRevisionFaltantesSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperarDocumentosRevisionFaltantesSP extends StoredProcedure
	{
		protected RecuperarDocumentosRevisionFaltantesSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_DOCS_FALTAN_REVISI");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdrevisi"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite"  , OracleTypes.VARCHAR));
			String cols[]=new String[]{
					"CDDOCUME" , "DSDOCUME"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizarStatusTramite(
			String ntramite
			,String status
			,Date fecstatu
			,String cdusuari
			)throws Exception
	{
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("ntramite" , ntramite);
		params.put("status"   , status);
		params.put("fecstatu" , fecstatu);
		params.put("cdusuari" , cdusuari);
		ejecutaSP(new ActualizarStatusTramiteSP(getDataSource()),params);
	}
	
	protected class ActualizarStatusTramiteSP extends StoredProcedure
	{
		protected ActualizarStatusTramiteSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_ACTUALIZA_STATUS_TRAMITE");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("fecstatu" , OracleTypes.TIMESTAMP));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
}