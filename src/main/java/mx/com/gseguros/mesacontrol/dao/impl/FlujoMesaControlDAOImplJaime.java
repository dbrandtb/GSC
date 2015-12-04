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

public class FlujoMesaControlDAOImplJaime extends AbstractManagerDAO implements FlujoMesaControlDAO {
	
	private final static Logger logger = LoggerFactory.getLogger(FlujoMesaControlDAOImplJaime.class);
	
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
	public List<Map<String,String>> recuperaTpantamc() throws Exception
	{
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTpantamcSP(getDataSource()),new HashMap<String,String>());
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
			super(dataSource,"PKG_MESACONTROL.P_GET_TPANTAMC");
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDPANTMC","WEBID","XPOS","YPOS"};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperaTcompmc() throws Exception
	{
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTcompmcSP(getDataSource()),new HashMap<String,String>());
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
			super(dataSource,"PKG_MESACONTROL.P_GET_TPANTAMC");
			String[] cols=new String[]{"CDCOMPMC","DSCOMPMC","NOMCOMP"};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperaTfluestavi(String cdtipflu, String cdflujomc, String cdestadomc) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		
		params.put("cdtipflu", cdtipflu);
		params.put("cdflujomc", cdtipflu);
		params.put("cdestadomc", cdtipflu);
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
	
	@Override
	public void movimientoTestadomc(String accion, String	cdestadomc, String dsestadomc) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdestadomc",cdestadomc);
		params.put("dsestadomc",dsestadomc);
		ejecutaSP(new MovimientoTestadomcSP(getDataSource()),params);
	}
	
	protected class MovimientoTestadomcSP extends StoredProcedure
	{
		protected MovimientoTestadomcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TESTADOMC");
			declareParameter(new SqlParameter("cdestadomc", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsestadomc", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoTcompmc(String accion, String cdcompmc, String dscompmc, String	nomcomp) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdcompmc",cdcompmc);
		params.put("dscompmc",dscompmc);
		params.put("nomcomp",nomcomp);
		ejecutaSP(new MovimientoTcompmcSP(getDataSource()),params);
	}
	
	protected class MovimientoTcompmcSP extends StoredProcedure
	{
		protected MovimientoTcompmcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TESTADOMC");
			declareParameter(new SqlParameter("cdcompmc", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dscompmc", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nomcomp", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoTflurev(String accion, String cdtipflu, String cdflujomc, String cdrevisi, String dsrevisi, String webid, String xpos, String ypos) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu", cdtipflu);
		params.put("cdflujomc",cdflujomc);
		params.put("cdrevisi",cdrevisi);
		params.put("dsrevisi",dsrevisi);
		params.put("webid",webid);
		params.put("xpos",xpos);
		params.put("ypos",ypos);
		params.put("accion",accion);
		ejecutaSP(new MovimientoTflurevSP(getDataSource()),params);
	}
	
	protected class MovimientoTflurevSP extends StoredProcedure
	{
		protected MovimientoTflurevSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUREV");
			declareParameter(new SqlParameter("cdtipflu", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdrevisi", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsrevisi", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("webid", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("xpos", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ypos", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
}
