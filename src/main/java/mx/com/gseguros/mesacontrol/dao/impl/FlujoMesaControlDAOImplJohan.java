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

public class FlujoMesaControlDAOImplJohan extends AbstractManagerDAO implements FlujoMesaControlDAO {
	
	private final static Logger logger = LoggerFactory.getLogger(FlujoMesaControlDAOImplAlvaro.class);
	
	
	@Override
	public List<Map<String,String>> recuperaTflupant(String cdtipflu,String cdtipflumc,String cdpantmc, String webid,String xpos,String ypos,String subrayado) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		params.put("cdtipflu" , cdtipflu);
	    params.put("cdtipflumc",cdtipflumc);
	    params.put("cdpantmc",cdpantmc);
	    params.put("webid",webid);
	    params.put("xpos",xpos);
	    params.put("ypos",ypos);
	    params.put("subrayado",subrayado);

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
			declareParameter(new SqlParameter("cdtipflu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipflumc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdpantmc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("webid" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("xpos" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ypos" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("subrayado" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDPANTMC","WEBID","XPOS","YPOS","SUBRAYADO" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	
	
	@Override
	public List<Map<String,String>> recuperaTfluacc(String cdtipflu,String cdtipflumc,String cdaccion, String dsaccion,String cdicono,String cdvalor) throws Exception
	{
		Map<String,String>       params  = new LinkedHashMap<String,String>();
		params.put("cdtipflu" , cdtipflu);
	    params.put("cdtipflumc",cdtipflumc);
	    params.put("cdaccion",cdaccion);
	    params.put("dsaccion",dsaccion);
	    params.put("cdicono",cdicono);
	    params.put("cdvalor",cdvalor);

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
			super(dataSource,"PKG_MESACONTROL.P_GET_TFLUPANT");
			declareParameter(new SqlParameter("cdtipflu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipflumc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdaccion" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("dsaccion" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdicono" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdvalor" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDPANTMC","WEBID","XPOS","YPOS","SUBRAYADO" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String, String>> recuperaTtiptramc() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> recuperaTtipflumc() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void movimientoTfluest(
			String cdtipflu
			,String cdflujomc
			,String cdestadomc
			,String webid
			,String xpos
			,String ypos
			,String width
			,String height
			,String timemax
			,String timewrn1
			,String timewrn2
			,String swescala
			,String cdtipasig
			,String accion
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"   , cdtipflu);
		params.put("cdflujomc"   , cdflujomc);
		params.put("cdestadomc"   , cdestadomc);
		params.put("webid" , webid);
		params.put("xpos"   , xpos);
		params.put("ypos"     , ypos);
		params.put("width"     , width);
		params.put("height"     , height);
		params.put("timemax"     , timemax);
		params.put("timewrn1"     , timewrn1);
		params.put("timewrn2"     , timewrn2);
		params.put("swescala"     , swescala);
		params.put("cdtipasig"     , cdtipasig);
		ejecutaSP(new MovimientoTtipflumcSP(getDataSource()),params);
	}
	
	protected class MovimientoTtipflumcSP extends StoredProcedure
	{
		protected MovimientoTtipflumcSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUEST");
			declareParameter(new SqlParameter("cdtipflu"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdestadomc"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("webid" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("xpos"   , OracleTypes.VARCHAR));		
			declareParameter(new SqlParameter("ypos"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("width"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("height"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("timemax"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("timewrn1"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("timewrn2"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swescala"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipasig"   , OracleTypes.VARCHAR));	
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	
	
	@Override
	public void movimientoTflucomp(
			String cdtipflu
			,String cdflujomc
			,String cdcompmc
			,String webid
			,String xpos
			,String ypos
			,String subrayado
			,String accion
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"   , cdtipflu);
		params.put("cdflujomc"   , cdflujomc);
		params.put("cdcompmc"   , cdcompmc);
		params.put("webid" , webid);
		params.put("xpos"   , xpos);
		params.put("ypos"     , ypos);
		params.put("subrayado"     , subrayado);
		ejecutaSP(new MovimientoTtipflumcSP(getDataSource()),params);
	}
	
	protected class MovimientoTflucompSP extends StoredProcedure
	{
		protected MovimientoTflucompSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUCOMP");
			declareParameter(new SqlParameter("cdtipflu"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdestadomc"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("webid" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("xpos"   , OracleTypes.VARCHAR));		
			declareParameter(new SqlParameter("ypos"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("subrayado"   , OracleTypes.VARCHAR));	
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	
	
	@Override
	public void movimientoTflurevdoc(
			String cdtipflu
			,String cdflujomc
			,String cdrevisi
			,String cddocume
			,String swobliga
			,String subrayado
			,String accion
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipflu"   , cdtipflu);
		params.put("cdflujomc"   , cdflujomc);
		params.put("cdrevisi"   , cdrevisi);
		params.put("cddocume" , cddocume);
		params.put("swobliga"   , swobliga);
		params.put("subrayado"     , subrayado);
		ejecutaSP(new MovimientoTtipflumcSP(getDataSource()),params);
	}
	
	protected class MovimientoTflurevdocSP extends StoredProcedure
	{
		protected MovimientoTflurevdocSP(DataSource dataSource)
		{
			super(dataSource,"PKG_MESACONTROL.P_MOV_TFLUCOMP");
			declareParameter(new SqlParameter("cdtipflu"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdrevisi"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cddocume" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swobliga"   , OracleTypes.VARCHAR));		
			declareParameter(new SqlParameter("subrayado"   , OracleTypes.VARCHAR));	
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
}
