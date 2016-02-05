package mx.com.gseguros.portal.mesacontrol.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.documentos.model.Documento;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import mx.com.gseguros.utils.Utils;
import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class MesaControlDAOImpl extends AbstractManagerDAO implements MesaControlDAO
{
	private static final Logger logger = Logger.getLogger(MesaControlDAOImpl.class);
	
	public String cargarCdagentePorCdusuari(String cdusuari)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdusuari" , cdusuari);
		logger.debug(
				new StringBuilder()
				.append("\n****************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_CDAGENTE_X_CDUSUARI ******")
				.append("\n****** params=").append(params)
				.append("\n****************************************************")
				.toString()
				);
		Map<String,Object>resultado=this.ejecutaSP(new CargarCdagentePorCdusuari(getDataSource()), params);
		return (String)resultado.get("pv_cdagente_o");
	}
	
	protected class CargarCdagentePorCdusuari extends StoredProcedure
	{
		protected CargarCdagentePorCdusuari(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_CDAGENTE_X_CDUSUARI");
			declareParameter(new SqlParameter("cdusuari"    , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_cdagente_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String movimientoMesaControl(
			String cdunieco  , String cdramo   , String estado     , String nmpoliza
			,String nmsuplem , String cdsucadm , String cdsucdoc   , String cdtiptra
			,Date ferecepc   , String cdagente , String referencia , String nombre
			,Date festatus   , String status   , String comments   , String nmsolici
			,String cdtipsit , String cdusuari , String cdsisrol   , String swimpres
			,String cdtipflu , String cdflujomc
			,Map<String, String> valores, String cdtipsup
			)throws Exception
	{
		Map<String,Object>params=new LinkedHashMap<String,Object>();
		params.put("cdunieco"  , cdunieco);
		params.put("cdramo"    , cdramo);
		params.put("estado"    , estado);
		params.put("nmpoliza"  , nmpoliza);
		params.put("nmsuplem"  , nmsuplem);
		params.put("cdsucadm"  , cdsucadm);
		params.put("cdsucdoc"  , cdsucdoc);
		params.put("cdtiptra"  , cdtiptra);
		params.put("ferecepc"  , ferecepc);
		params.put("cdagente"  , cdagente);
		params.put("referencia", referencia);
		params.put("nombre"    , nombre);
		params.put("festatus"  , festatus);
		params.put("status"    , status);
		params.put("comments"  , comments);
		params.put("nmsolici"  , nmsolici);
		params.put("cdtipsit"  , cdtipsit);
		params.put("cdusuari"  , cdusuari);
		params.put("cdsisrol"  , cdsisrol);
		params.put("swimpres"  , swimpres);
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("cdtipsup"  , cdtipsup);
		
		if(valores==null)
		{
			valores = new LinkedHashMap<String,String>();
		}
		
		for(int i=1; i <= 50; i++)
		{
			String key    = Utils.join("otvalor",StringUtils.leftPad(String.valueOf(i),2,"0"));
			String pv_key = Utils.join("pv_",key);
			if(!valores.containsKey(key))
			{
				valores.put(key,valores.get(pv_key));
			}
		}
		
		params.putAll(valores);

		logger.debug(
				new StringBuilder()
				.append("\n**********************************************")
				.append("\n****** PKG_SATELITES2.P_MOV_MESACONTROL ******")
				.append("\n****** params=").append(params)
				.append("\n**********************************************")
				.toString()
				);
		Map<String,Object>procResult=ejecutaSP(new MovimientoMesaControl(getDataSource()),params);
		return String.valueOf(procResult.get("pv_tramite_o"));
	}
	
	protected class MovimientoMesaControl extends StoredProcedure
	{
		protected MovimientoMesaControl(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_MOV_MESACONTROL");
			declareParameter(new SqlParameter("cdunieco"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsucadm"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsucdoc"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtiptra"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ferecepc"   , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdagente"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("referencia" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nombre"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("festatus"   , OracleTypes.DATE));
			declareParameter(new SqlParameter("status"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("comments"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsolici"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor01"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor02"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor03"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor04"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor05"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor06"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor07"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor08"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor09"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor10"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor11"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor12"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor13"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor14"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor15"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor16"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor17"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor18"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor19"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor20"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor21"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor22"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor23"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor24"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor25"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor26"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor27"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor28"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor29"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor30"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor31"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor32"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor33"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor34"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor35"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor36"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor37"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor38"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor39"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor40"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor41"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor42"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor43"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor44"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor45"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor46"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor47"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor48"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor49"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor50"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swimpres"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipflu"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tramite_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoDetalleTramite(
			String ntramite
			,Date feinicio
			,String cdclausu
			,String comments
			,String cdusuari
			,String cdmotivo
			,String cdsisrol
			,String swagente
			,String cdusuariDest
			,String cdsisrolDest
			)throws Exception
	{
		Map<String,Object>params=new LinkedHashMap<String,Object>();
		params.put("ntramite"     , ntramite);
		params.put("feinicio"     , feinicio);
		params.put("cdclausu"     , cdclausu);
		params.put("comments"     , comments);
		params.put("cdusuari"     , cdusuari);
		params.put("cdmotivo"     , cdmotivo);
		params.put("cdsisrol"     , cdsisrol);
		params.put("swagente"     , swagente);
		params.put("cdusuariDest" , cdusuariDest);
		params.put("cdsisrolDest" , cdsisrolDest);
		logger.debug(
				new StringBuilder()
				.append("\n**********************************************")
				.append("\n****** PKG_SATELITES2.P_MOV_DMESACONTROL ******")
				.append("\n****** params=").append(params)
				.append("\n**********************************************")
				.toString()
				);
		ejecutaSP(new MovimientoDetalleTramite(getDataSource()),params);
	}
	
	protected class MovimientoDetalleTramite extends StoredProcedure
	{
		protected MovimientoDetalleTramite(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_MOV_DMESACONTROL");
			declareParameter(new SqlParameter("ntramite"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feinicio"     , OracleTypes.TIMESTAMP));
			declareParameter(new SqlParameter("cdclausu"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("comments"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdmotivo"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swagente"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuariDest" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrolDest" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizarNmsoliciTramite(String ntramite,String nmsolici)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("ntramite" , ntramite);
		params.put("nmsolici" , nmsolici);
		logger.debug(
				new StringBuilder()
				.append("\n*********************************************")
				.append("\n****** PKG_SATELITES.P_UPDATE_NMSOLICI ******")
				.append("\n****** params=").append(params)
				.append("\n*********************************************")
				.toString()
				);
		ejecutaSP(new ActualizarNmsoliciTramite(getDataSource()),params);
	}
	
	protected class ActualizarNmsoliciTramite extends StoredProcedure
	{
		protected ActualizarNmsoliciTramite(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_UPDATE_NMSOLICI");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsolici" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizaValoresTramite(
			String ntramite
			,String cdramo
			,String cdtipsit
			,String cdsucadm
			,String cdsucdoc
			,String comments
			,Map<String,String>valores)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("ntramite" , ntramite);
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdsucadm" , cdsucadm);
		params.put("cdsucdoc" , cdsucdoc);
		params.put("comments" , comments);
		
		for(int i=1;i<=50;i++)
		{
			params.put(new StringBuilder("otvalor").append(StringUtils.leftPad(String.valueOf(i),2,"0")).toString(),null);
		}
		
		params.putAll(valores);

		logger.debug(
				new StringBuilder()
				.append("\n**********************************************")
				.append("\n****** PKG_SATELITES.p_upd_tmesacontrol ******")
				.append("\n****** params=").append(params)
				.append("\n**********************************************")
				.toString()
				);
		
		ejecutaSP(new ActualizaValoresTramite(getDataSource()),params);
	}
	
	protected class ActualizaValoresTramite extends StoredProcedure
	{
		protected ActualizaValoresTramite(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.p_upd_tmesacontrol");
			declareParameter(new SqlParameter("ntramite"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsucadm"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsucdoc"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("comments"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor01" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor02" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor03" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor04" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor05" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor06" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor07" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor08" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor09" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor10" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor11" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor12" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor13" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor14" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor15" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor16" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor17" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor18" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor19" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor20" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor21" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor22" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor23" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor24" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor25" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor26" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor27" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor28" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor29" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor30" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor31" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor32" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor33" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor34" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor35" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor36" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor37" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor38" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor39" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor40" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor41" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor42" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor43" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor44" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor45" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor46" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor47" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor48" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor49" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor50" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarTramitesPorParametrosVariables(
			String cdtiptra
			,String ntramite
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsolici
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdtiptra" , cdtiptra);
		params.put("ntramite" , ntramite);
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("nmsolici" , nmsolici);
		logger.debug(Utils.log(
				 "\n*********************************************************"
				,"\n****** PKG_SATELITES2.P_GET_TMESACONTROL_X_PAR_VAR ******"
				,"\n****** params=",params
				,"\n*********************************************************"
				));
		Map<String,Object>procResult     = ejecutaSP(new CargarTramitesPorParametrosVariables(getDataSource()),params);
		List<Map<String,String>>registro = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(registro==null)
		{
			registro=new ArrayList<Map<String,String>>();
		}
		logger.debug(Utils.log(
				 "\n*********************************************************"
				,"\n****** params="   , params
				,"\n****** registro=" , registro
				,"\n****** PKG_SATELITES2.P_GET_TMESACONTROL_X_PAR_VAR ******"
				,"\n*********************************************************"
				));
		return registro;
	}
	
	protected class CargarTramitesPorParametrosVariables extends StoredProcedure
	{
		protected CargarTramitesPorParametrosVariables(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_GET_TMESACONTROL_X_PAR_VAR");
			declareParameter(new SqlParameter("cdtiptra" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsolici" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"NTRAMITE"  ,"CDUNIECO" ,"CDRAMO"   ,"ESTADO"   ,"NMPOLIZA" ,"NMSUPLEM"  ,"NMSOLICI" ,"CDSUCADM"
					,"CDSUCDOC" ,"CDSUBRAM" ,"CDTIPTRA" ,"FERECEPC" ,"CDAGENTE" ,"REFERENCIA","NOMBRE"   ,"FECSTATU"
					,"STATUS"   ,"COMMENTS" ,"CDTIPSIT"
					,"OTVALOR01","OTVALOR02","OTVALOR03","OTVALOR04","OTVALOR05","OTVALOR06" ,"OTVALOR07","OTVALOR08","OTVALOR09","OTVALOR10"
					,"OTVALOR11","OTVALOR12","OTVALOR13","OTVALOR14","OTVALOR15","OTVALOR16" ,"OTVALOR17","OTVALOR18","OTVALOR19","OTVALOR20"
					,"OTVALOR21","OTVALOR22","OTVALOR23","OTVALOR24","OTVALOR25","OTVALOR26" ,"OTVALOR27","OTVALOR28","OTVALOR29","OTVALOR30"
					,"OTVALOR31","OTVALOR32","OTVALOR33","OTVALOR34","OTVALOR35","OTVALOR36" ,"OTVALOR37","OTVALOR38","OTVALOR39","OTVALOR40"
					,"OTVALOR41","OTVALOR42","OTVALOR43","OTVALOR44","OTVALOR45","OTVALOR46" ,"OTVALOR47","OTVALOR48","OTVALOR49","OTVALOR50"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void guardarRegistroContrarecibo(String ntramite,String cdusuari)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("ntramite" , ntramite);
		params.put("cdusuari" , cdusuari);
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_INSERTA_CONTRARECIBO", params);
		ejecutaSP(new GuardarRegistroContrarecibo(getDataSource()),params);
	}
	
	protected class GuardarRegistroContrarecibo extends StoredProcedure
	{
		protected GuardarRegistroContrarecibo(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_INSERTA_CONTRARECIBO");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizarNombreDocumento(String ntramite,String cddocume,String nuevo)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("ntramite" , ntramite);
		params.put("cddocume" , cddocume);
		params.put("nuevo"    , nuevo);
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_UPD_TDOCUPOL_DSDOCUME", params);
		ejecutaSP(new ActualizarNombreDocumento(getDataSource()),params);
	}
	
	protected class ActualizarNombreDocumento extends StoredProcedure
	{
		protected ActualizarNombreDocumento(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_UPD_TDOCUPOL_DSDOCUME");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cddocume" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nuevo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void borrarDocumento(String ntramite,String cddocume)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("ntramite" , ntramite);
		params.put("cddocume" , cddocume);
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_BORRAR_TDOCUPOL", params);
		ejecutaSP(new BorrarDocumento(getDataSource()),params);
	}
	
	protected class BorrarDocumento extends StoredProcedure
	{
		protected BorrarDocumento(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_BORRAR_TDOCUPOL");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cddocume" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public void guardarDocumento(String cdunieco, String cdramo, String estado,
			String nmpoliza, String nmsuplem, Date feinici, String cddocume,
			String dsdocume, String nmsolici, String ntramite, String tipmov,
			String swvisible, String codidocu, String cdtiptra, String cdorddoc
			,Documento documento, String cdusuari, String cdsisrol) throws Exception {
		
		HashMap<String, Object> params =  new HashMap<String, Object>();
		params.put("pv_cdunieco_i"  , cdunieco);
		params.put("pv_cdramo_i"    , cdramo);
		params.put("pv_estado_i"    , estado);
		params.put("pv_nmpoliza_i"  , nmpoliza);
		params.put("pv_nmsuplem_i"  , nmsuplem);
		params.put("pv_feinici_i"   , feinici);
		params.put("pv_cddocume_i"  , cddocume);
		params.put("pv_dsdocume_i"  , dsdocume);
		params.put("pv_nmsolici_i"  , nmsolici);
		params.put("pv_ntramite_i"  , ntramite);
		params.put("pv_tipmov_i"    , tipmov);
		params.put("pv_swvisible_i" , swvisible);
		params.put("pv_codidocu_i"  , codidocu);
		params.put("pv_cdtiptra_i"  , cdtiptra);
		params.put("cdorddoc"       , cdorddoc);
		params.put("cdmoddoc"       , documento!=null ? documento.getCdmoddoc() : null);
		params.put("cdusuari"       , cdusuari);
		params.put("cdsisrol"       , cdsisrol);
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_MOV_DOCUMENTOS", params);
		ejecutaSP(new GuardarDocumentoPolizaSP(getDataSource()), params);
	}
	
	protected class GuardarDocumentoPolizaSP extends StoredProcedure {
		protected GuardarDocumentoPolizaSP(DataSource dataSource) {
			super(dataSource, "PKG_SATELITES2.P_MOV_DOCUMENTOS");
			declareParameter(new SqlParameter("pv_cdunieco_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feinici_i"   , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cddocume_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsdocume_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ntramite_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsolici_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipmov_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swvisible_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_codidocu_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtiptra_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdorddoc"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdmoddoc"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol"       , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String turnaPorCargaTrabajo(
			String ntramite
			,String cdsisrol
			,String status
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("ntramite" , ntramite);
		params.put("cdsisrol" , cdsisrol);
		params.put("status"   ,status);
		Map<String,Object> procRes = ejecutaSP(new TurnaPorCargaTrabajo(getDataSource()),params);
		return (String)procRes.get("pv_nombre_o");
	}
	
	protected class TurnaPorCargaTrabajo extends StoredProcedure {
		protected TurnaPorCargaTrabajo(DataSource dataSource) {
			super(dataSource, "PKG_SATELITES2.P_MUEVE_TRAMITE_CARGA");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nombre_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void validarAntesDeTurnar(
    		String ntramite
    		,String status
    		,String cdusuari
    		,String cdsisrol
    		)throws Exception
    {
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("ntramite" , ntramite);
		params.put("status"   , status);
		params.put("cdusuari" , cdusuari);
		params.put("cdsisrol" , cdsisrol);
		Map<String,Object> procRes = ejecutaSP(new ValidarAntesDeTurnar(getDataSource()),params);
		String error = (String)procRes.get("pv_dserror_o");
		if(StringUtils.isNotBlank(error))
		{
			throw new ApplicationException(error);
		}
    }
	
	protected class ValidarAntesDeTurnar extends StoredProcedure {
		protected ValidarAntesDeTurnar(DataSource dataSource) {
			super(dataSource, "PKG_CONSULTA.P_VALIDA_ANTES_TURNADO");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_dserror_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public void actualizaStatusMesaControl(String ntramite, String status) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("ntramite", ntramite);
		params.put("status"  , status);
		ejecutaSP(new ActualizaStatusMesaControlSP(getDataSource()), params);
    }
	
	protected class ActualizaStatusMesaControlSP extends StoredProcedure {
		protected ActualizaStatusMesaControlSP(DataSource dataSource) {
			
			super(dataSource,"PKG_SATELITES.P_UPDATE_STATUS_MC");
    		declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_status_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
    		compile();
		}
	}
	
	@Override
	public void actualizarStatusRemesa(
			String ntramite
			,String status
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("ntramite" , ntramite);
		params.put("status"   , status);
		Map<String,Object> procRes = ejecutaSP(new ActualizarStatusRemesa(getDataSource()),params);
		String error = (String)procRes.get("pv_error_o");
		if(StringUtils.isNotBlank(error))
		{
			throw new ApplicationException(error);
		}
	}
	
	protected class ActualizarStatusRemesa extends StoredProcedure {
		protected ActualizarStatusRemesa(DataSource dataSource) {
			
			super(dataSource,"PKG_SATELITES2.P_ACT_ESTATUS_REMESA");
    		declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_error_o"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("PV_MSG_ID_O" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("PV_TITLE_O"  , OracleTypes.VARCHAR));
    		compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarTramites(
			String cdunieco
			,String ntramite
			,String cdramo
			,String nmpoliza
			,String estado
			,String cdagente
			,String status
			,String cdtipsit
			,Date fedesde
			,Date fehasta
			,String cdsisrol
			,String cdtiptra
			,String contrarecibo
			,String tipoPago
			,String nfactura
			,String cdpresta
			,String cdusuari
			,String cdtipram
			,String lote
			,String tipolote
			,String tipoimpr
			,String cdusuari_busq
			)throws Exception
	{
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("cdunieco"      , cdunieco);
		params.put("ntramite"      , ntramite);
		params.put("cdramo"        , cdramo);
		params.put("nmpoliza"      , nmpoliza);
		params.put("estado"        , estado);
		params.put("cdagente"      , cdagente);
		params.put("status"        , status);
		params.put("cdtipsit"      , cdtipsit);
		params.put("fedesde"       , fedesde);
		params.put("fehasta"       , fehasta);
		params.put("cdsisrol"      , cdsisrol);
		params.put("cdtiptra"      , cdtiptra);
		params.put("contrarecibo"  , contrarecibo);
		params.put("tipoPago"      , tipoPago);
		params.put("nfactura"      , nfactura);
		params.put("cdpresta"      , cdpresta);
		params.put("cdusuari"      , cdusuari);
		params.put("cdtipram"      , cdtipram);
		params.put("lote"          , lote);
		params.put("tipolote"      , tipolote);
		params.put("tipoimpr"      , tipoimpr);
		params.put("cdusuari_busq" , cdusuari_busq);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarTramites(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utils.log("****** PKG_SATELITES2.P_OBTIENE_MESACONTROL lista=",lista));
		return lista;
	}
	
	protected class RecuperarTramites extends StoredProcedure
	{
		protected RecuperarTramites(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_OBTIENE_MESACONTROL");
			declareParameter(new SqlParameter("cdunieco"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdagente"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("fedesde"       , OracleTypes.DATE));
			declareParameter(new SqlParameter("fehasta"       , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdsisrol"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtiptra"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("contrarecibo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipoPago"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nfactura"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdpresta"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipram"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("lote"          , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipolote"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipoimpr"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari_busq" , OracleTypes.VARCHAR));
			String cols[]=new String[]{
					"ntramite"     , "cdunieco" , "cdramo"   , "dsramo"        , "estado"     , "nmpoliza"
					,"nmsolici"    , "cdsucadm" , "dssucadm" , "cdsucdoc"      , "dssucdoc"   , "cdsubram"
					,"cdtiptra"    , "ferecepc" , "cdagente" , "Nombre_agente" , "referencia" , "nombre"
					,"fecstatu"    , "status"   , "comments" , "cdtipsit"      , "comi"       , "prima_neta"
					,"prima_total" , "nmsuplem"
					,"otvalor01","otvalor02","otvalor03","otvalor04","otvalor05","otvalor06","otvalor07","otvalor08","otvalor09","otvalor10"
					,"otvalor11","otvalor12","otvalor13","otvalor14","otvalor15","otvalor16","otvalor17","otvalor18","otvalor19","otvalor20"
					,"otvalor21","otvalor22","otvalor23","otvalor24","otvalor25","otvalor26","otvalor27","otvalor28","otvalor29","otvalor30"
					,"otvalor31","otvalor32","otvalor33","otvalor34","otvalor35","otvalor36","otvalor37","otvalor38","otvalor39","otvalor40"
					,"otvalor41","otvalor42","otvalor43","otvalor44","otvalor45","otvalor46","otvalor47","otvalor48","otvalor49","otvalor50"
					,"contratante" , "cdusuari"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizarHijosRemesa(
			String lote
			,String ntramite
			,String status
			)throws Exception
	{
		if(StringUtils.isBlank(lote)&&StringUtils.isBlank(ntramite))
		{
			throw new ApplicationException("No puede ir vacios ambos valores");
		}
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("lote"     , lote);
		params.put("ntramite" , ntramite);
		params.put("status"   , status);
		ejecutaSP(new ActualizarHijosRemesa(getDataSource()),params);
	}
	
	protected class ActualizarHijosRemesa extends StoredProcedure
	{
		protected ActualizarHijosRemesa(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_ACTUALIZA_HIJOS_REMESA");
    		declareParameter(new SqlParameter("lote"     , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("PV_MSG_ID_O" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("PV_TITLE_O"  , OracleTypes.VARCHAR));
    		compile();
		}
	}
	
	@Override
	public Map<String,Boolean> marcarImpresionOperacion(
			String cdsisrol
			,String ntramite
			,String marcar
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdsisrol" , cdsisrol);
		params.put("ntramite" , ntramite);
		params.put("marcar"   , marcar);
		Map<String,Object>  procRes   = ejecutaSP(new MarcarImpresionOperacion(getDataSource()),params);
		String              preguntar = (String)procRes.get("pv_preguntar_o");
		String              marcado   = (String)procRes.get("pv_marcado_o");
		Map<String,Boolean> result    = new HashMap<String,Boolean>();
		result.put("preguntar" , "S".equals(preguntar));
		result.put("marcado"   , "S".equals(marcado));
		logger.debug(Utils.log("\n****** PKG_SATELITES2.P_MARCA_IMPRESION_OPE result=",result));
		return result;
	}
	
	protected class MarcarImpresionOperacion extends StoredProcedure
	{
		protected MarcarImpresionOperacion(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_MARCA_IMPRESION_OPE");
    		declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("marcar"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_preguntar_o" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_marcado_o"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("PV_MSG_ID_O"    , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("PV_TITLE_O"     , OracleTypes.VARCHAR));
    		compile();
		}
	}
	
	@Override
	public void marcarTramiteVistaPrevia(String ntramite) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("ntramite", ntramite);
		ejecutaSP(new MarcarTramiteVistaPreviaSP(getDataSource()),params);
	}
	
	protected class MarcarTramiteVistaPreviaSP extends StoredProcedure
	{
		protected MarcarTramiteVistaPreviaSP(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_MARCA_TRAMITE_VISTA_PREVIA");
    		declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("PV_MSG_ID_O" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("PV_TITLE_O"  , OracleTypes.VARCHAR));
    		compile();
		}
	}
	
	@Override
	public String recuperarSwvispreTramite(String ntramite) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("ntramite", ntramite);
		Map<String,Object> procRes = ejecutaSP(new RecuperarSwvispreTramiteSP(getDataSource()),params);
		String swvispre = (String) procRes.get("pv_swvispre_o");
		if(StringUtils.isBlank(swvispre))
		{
			swvispre = "N";
		}
		return swvispre;
	}
	
	protected class RecuperarSwvispreTramiteSP extends StoredProcedure
	{
		protected RecuperarSwvispreTramiteSP(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_SWVISPRE_TRAMITE");
    		declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_swvispre_o" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
    		compile();
		}
	}
	
}