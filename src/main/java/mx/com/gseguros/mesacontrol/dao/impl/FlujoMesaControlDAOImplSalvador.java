package mx.com.gseguros.mesacontrol.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.mesacontrol.dao.FlujoMesaControlDAO;
import mx.com.gseguros.mesacontrol.dao.impl.FlujoMesaControlDAOImpl.RecuperaTtiptramcSP;
import mx.com.gseguros.mesacontrol.dao.impl.FlujoMesaControlDAOImplAlvaro.MovimientoTtipflumcSP;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import oracle.jdbc.driver.OracleTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class FlujoMesaControlDAOImplSalvador extends AbstractManagerDAO implements FlujoMesaControlDAO {
	
	private final static Logger logger = LoggerFactory.getLogger(FlujoMesaControlDAOImplSalvador.class);
	
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

// ***** 4 (CATALOGO) ***** //
	@Override
	public List<Map<String,String>> recuperaTestadomc() throws Exception
	{
		Map<String,Object>       procRes = ejecutaSP(new RecuperaTtiptramcSP(getDataSource()),new HashMap<String,String>());
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
			String[] cols=new String[]{ "CDESTADOMC" , "DSESTADOMC" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	

// ***** 6 (POR LLAVE) ***** //
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
	
// ***** 16 (POR LLAVE) ***** //
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
	
// ***** 3 (MOV) ***** //
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
	
// ***** 9 (MOV) ***** //
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
	
// ***** 15 (MOV) ***** //
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

}
