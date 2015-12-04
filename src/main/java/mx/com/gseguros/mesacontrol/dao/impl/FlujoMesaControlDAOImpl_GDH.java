package mx.com.gseguros.mesacontrol.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.mesacontrol.dao.FlujoMesaControlDAO;
import mx.com.gseguros.mesacontrol.dao.impl.FlujoMesaControlDAOImplAlvaro.MovimientoTtipflumcSP;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import oracle.jdbc.driver.OracleTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class FlujoMesaControlDAOImpl_GDH extends AbstractManagerDAO implements FlujoMesaControlDAO {
	
	private final static Logger logger = LoggerFactory.getLogger(FlujoMesaControlDAOImpl_GDH.class);
	
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
	
	//TABLA 2 CATALOGO
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
	
	//TABLA 2 CATALOGO
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
	
	//TABLA12 CATALOGO
	@Override
	public List<Map<String,String>> recuperaTprocmc() throws Exception
	{
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTprocmcSP(getDataSource()),new HashMap<String,String>());
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	//TABLA12 CATALOGO
	protected class RecuperaTprocmcSP extends StoredProcedure
	{
		protected RecuperaTprocmcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TPROCMC");
			String[] cols=new String[]{ "CDPROCMC" , "DSPROCMC", "URLPROCMC" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	//TABLA 5 X LLAVE
	@Override
	public List<Map<String, String>> recuperaTfluest(String cdtipflu, String cdflujomc) throws Exception 
	{
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("cdtipflu", cdtipflu);
		params.put("cdflujomc", cdflujomc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTfluestSP(getDataSource()), params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	//TABLA 5 X LLAVE
	protected class RecuperaTfluestSP extends StoredProcedure
	{
		protected RecuperaTfluestSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUEST");
			declareParameter(new SqlParameter("cdtipflu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU" , "CDFLUJOMC", "CDESTADOMC", "WEBID", "XPOS", "YPOS", "TIMEMAX", "TIMEWRN1", "TIMEWRN2", "SWESCALA", "CDTIPASIG" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	//TABLA 14 X LLAVE
	@Override
	public List<Map<String, String>> recuperaTfluval(String cdtipflu, String cdflujomc) throws Exception 
	{
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("cdtipflu", cdtipflu);
		params.put("cdflujomc", cdflujomc);
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTfluvalSP(getDataSource()), params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	//TABLA 14 X LLAVE
	protected class RecuperaTfluvalSP extends StoredProcedure
	{
		protected RecuperaTfluvalSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUVAL");
			declareParameter(new SqlParameter("cdtipflu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU", "CDFLUJOMC", "CDVALIDA", "DSVALIDA", "CDEXPRES", "WEBID", "XPOS", "YPOS" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	//TABLA 8 X MOV
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
	
	//TABLA 8 X MOV
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
	
	//TABLA 14 X MOV
	@Override
	public void movimientoTfluval(String cdtipflu, String cdflujomc,
			String cdvalida, String dsvalida, String cdexpres, String webid,
			String xpos, String ypoS, String accion) throws Exception 
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"   , cdtipflu);
		params.put("cdflujomc"   , cdflujomc);
		params.put("cdvalida"   , cdvalida);
		params.put("dsvalida" , dsvalida);
		params.put("cdexpres" , cdexpres);
		params.put("webid" , webid);
		params.put("xpos" , xpos);
		params.put("ypoS" , ypoS);
		params.put("accion"     , accion);
		ejecutaSP(new MovimientoTfluvalSP(getDataSource()),params);
	}
	
	//TABLA 14 X MOV
	protected class MovimientoTfluvalSP extends StoredProcedure
	{
		protected MovimientoTfluvalSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUVAL");
			declareParameter(new SqlParameter("cdtipflu"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdvalida"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsvalida" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdexpres"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("webid"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("xpos"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ypoS"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	//TABLA 20 X MOV
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
	
	//TABLA 20 X MOV
	protected class MovimientoTfluaccrolSP extends StoredProcedure
	{
		protected MovimientoTfluaccrolSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TPANTMC");
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

}
