package mx.com.gseguros.portal.mesacontrol.dao.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class MesaControlDAOImpl extends AbstractManagerDAO implements MesaControlDAO
{
	private static final Logger logger = Logger.getLogger(MesaControlDAOImpl.class);
	
	public String cargarCdagentePorCdusuari(Map<String,String>params)throws Exception
	{
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
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdsucadm
			,String cdsucdoc
			,String cdtiptra
			,Date ferecepc
			,String cdagente
			,String referencia
			,String nombre
			,Date festatus
			,String status
			,String comments
			,String nmsolici
			,String cdtipsit
			,Map<String,String>valores
			)throws Exception
	{
		Map<String,Object>params=new LinkedHashMap<String,Object>();
		params.put("cdunieco"   , cdunieco);
		params.put("cdramo"     , cdramo);
		params.put("estado"     , estado);
		params.put("nmpoliza"   , nmpoliza);
		params.put("nmsuplem"   , nmsuplem);
		params.put("cdsucadm"   , cdsucadm);
		params.put("cdsucdoc"   , cdsucdoc);
		params.put("cdtiptra"   , cdtiptra);
		params.put("ferecepc"   , ferecepc);
		params.put("cdagente"   , cdagente);
		params.put("referencia" , referencia);
		params.put("nombre"     , nombre);
		params.put("festatus"   , festatus);
		params.put("status"     , status);
		params.put("comments"   , comments);
		params.put("nmsolici"   , nmsolici);
		params.put("cdtipsit"   , cdtipsit);
		
		for(int i=1;i<=50;i++)
		{
			params.put(new StringBuilder("otvalor").append(StringUtils.leftPad(String.valueOf(i),2,"0")).toString(),null);
		}
		
		params.putAll(valores);

		logger.debug(
				new StringBuilder()
				.append("\n*********************************************")
				.append("\n****** PKG_SATELITES.P_MOV_MESACONTROL ******")
				.append("\n****** params=").append(params)
				.append("\n*********************************************")
				.toString()
				);
		Map<String,Object>procResult=ejecutaSP(new MovimientoMesaControl(getDataSource()),params);
		return String.valueOf(procResult.get("pv_tramite_o"));
	}
	
	protected class MovimientoMesaControl extends StoredProcedure
	{
		protected MovimientoMesaControl(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_MOV_MESACONTROL");
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
			,String cdmotivo)throws Exception
	{
		Map<String,Object>params=new LinkedHashMap<String,Object>();
		params.put("ntramite" , ntramite);
		params.put("feinicio" , feinicio);
		params.put("cdclausu" , cdclausu);
		params.put("comments" , comments);
		params.put("cdusuari" , cdusuari);
		params.put("cdmotivo" , cdmotivo);
		logger.debug(
				new StringBuilder()
				.append("\n**********************************************")
				.append("\n****** PKG_SATELITES.P_MOV_DMESACONTROL ******")
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
			super(dataSource,"PKG_SATELITES.P_MOV_DMESACONTROL");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feinicio" , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdclausu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("comments" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdmotivo" , OracleTypes.VARCHAR));
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
}