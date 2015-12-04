package mx.com.gseguros.mesacontrol.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.mesacontrol.dao.FlujoMesaControlDAO;
import mx.com.gseguros.mesacontrol.dao.impl.FlujoMesaControlDAOImplAlvaro.MovimientoTtipflumcSP;
import mx.com.gseguros.mesacontrol.dao.impl.FlujoMesaControlDAOImplAlvaro.RecuperaTflujomcSP;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import oracle.jdbc.driver.OracleTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class FlujoMesaControlDAOImplRafa extends AbstractManagerDAO implements FlujoMesaControlDAO {
	
	private final static Logger logger = LoggerFactory.getLogger(FlujoMesaControlDAOImplRafa.class);
	
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
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDCOMPMC","WEBID","XPOS","YPOS","SUBRAYADO" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
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
			
			String[] cols=new String[]{ "CDTIPFLU","CDFLUJOMC","CDACCION","CDSISROL","SWPERMISO","SUBRAYADO" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	///////
	
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
			declareParameter(new SqlParameter("cdflujomc"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdestadomc"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swver"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swtrabajo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swcompra"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swreasig"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdrolasig"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	
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


	@Override
	public List<Map<String, String>> recuperaTflupant(String cdtipflu,
			String cdtipflumc, String cdpantmc, String webid, String xpos,
			String ypos, String subrayado) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> recuperaTfluacc(String cdtipflu,
			String cdtipflumc, String cdaccion, String dsaccion,
			String cdicono, String cdvalor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void movimientoTfluest(String cdtipflu, String cdflujomc,
			String cdestadomc, String webid, String xpos, String ypos,
			String width, String height, String timemax, String timewrn1,
			String timewrn2, String swescala, String cdtipasig, String accion)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movimientoTflucomp(String cdtipflu, String cdflujomc,
			String cdcompmc, String webid, String xpos, String ypos,
			String subrayado, String accion) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movimientoTflurevdoc(String cdtipflu, String cdflujomc,
			String cdrevisi, String cddocume, String swobliga,
			String subrayado, String accion) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
