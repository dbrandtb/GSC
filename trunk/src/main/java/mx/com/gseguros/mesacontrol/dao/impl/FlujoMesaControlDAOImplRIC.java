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
	

	//////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void movimientoTfluestavi(
			String cdtipflu,
			String cdflujomc,
			String cdestadomc,
			String webid,
			String xpos,
			String ypos,
			String timemax,
			String timewrn1,
			String timewrn2,
			String swescala,
			String cdtipasig,
			String accion) throws Exception {
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu", cdtipflu);
		params.put("cdflujomc", cdflujomc);
		params.put("cdestadomc", cdestadomc);
		params.put("webid", webid);
		params.put("xpos", xpos);
		params.put("ypos", ypos);
		params.put("timemax", timemax);
		params.put("timewrn1", timewrn1);
		params.put("timewrn2", timewrn2);
		params.put("swescala", swescala);
		params.put("cdtipasig", cdtipasig);
		params.put("accion", accion);
		ejecutaSP(new MovimientoTfluestaviSP(getDataSource()),params);
	}
	
	protected class MovimientoTfluestaviSP extends StoredProcedure {
		protected MovimientoTfluestaviSP(DataSource dataSource) {
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUESTAVI");
			declareParameter(new SqlParameter("cdtipflu", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdestadomc", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("webid", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("xpos", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ypos", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("timemax", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("timewrn1", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("timewrn2", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swescala", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipasig", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public void movimientoTfloproc(
			String cdtipflu,
			String cdflujomc,
			String cdprocmc,
			String webid,
			String xpos,
			String ypos,
			String width,
			String height,
			String accion) throws Exception {
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu", cdtipflu);
		params.put("cdflujomc", cdflujomc);
		params.put("cdprocmc", cdprocmc);
		params.put("webid", webid);
		params.put("xpos", xpos);
		params.put("ypos", ypos);
		params.put("width", width);
		params.put("height", height);
		params.put("accion", accion);
		ejecutaSP(new MovimientoTfluprocSP(getDataSource()),params);
	}
	
	protected class MovimientoTfluprocSP extends StoredProcedure {
		protected MovimientoTfluprocSP(DataSource dataSource) {
			super(dataSource,"PKG_MESACONTROL.P_MOV_FLUPROC");
			declareParameter(new SqlParameter("cdtipflu", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdprocmc", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("webid", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("xpos", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ypos", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("width", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("height", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public void movimientoTfluacc(
			String cdtipflu,
			String cdflujomc,
			String cdaccion,
			String dsaccion,
			String cdicono,
			String cdvalor,
			String idorigen,
			String iddestin,
			String accion) throws Exception {
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu", cdtipflu);
		params.put("cdflujomc", cdflujomc);
		params.put("cdaccion", cdaccion);
		params.put("dsaccion", dsaccion);
		params.put("cdicono", cdicono);
		params.put("cdvalor", cdvalor);
		params.put("idorigen", idorigen);
		params.put("iddestin", iddestin);
		params.put("accion", accion);
		ejecutaSP(new MovimientoTfluprocSP(getDataSource()),params);
	}
	
	protected class MovimientoTfluaccSP extends StoredProcedure {
		protected MovimientoTfluaccSP(DataSource dataSource) {
			super(dataSource,"PKG_MESACONTROL.P_MOV_FLUACC");
			declareParameter(new SqlParameter("cdtipflu", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdaccion", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsaccion", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdicono", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdvalor", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("idorigen", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("iddestin", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}


	@Override
	public void movimientoTtipflumc(String cdtipflu, String dstipflu,
			String cdtiptra, String swmultipol, String swreqpol, String accion)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
}