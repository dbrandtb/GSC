package mx.com.gseguros.portal.consultas.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.cotizacion.model.ParametroGeneral;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.Utils;
import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class ConsultasDAOImpl extends AbstractManagerDAO implements ConsultasDAO
{
	private static final org.apache.log4j.Logger logger  = org.apache.log4j.Logger.getLogger(ConsultasDAOImpl.class);
	private static final org.slf4j.Logger        logger2 = org.slf4j.LoggerFactory.getLogger(ConsultasDAOImpl.class);
	
	@Override
	public List<Map<String,String>> consultaDinamica(String storedProcedure,LinkedHashMap<String,Object>params) throws Exception
	{
		Map<String,Object>result = this.ejecutaSP(new ConsultaDinamica(storedProcedure, params, getDataSource()), params);
		return (List<Map<String,String>>) result.get("pv_registro_o");
	}
	
	protected class ConsultaDinamica extends StoredProcedure
	{
		protected ConsultaDinamica(String storedProcedure, LinkedHashMap<String,Object> params, DataSource dataSource)
		{
			super(dataSource, storedProcedure);

			if(params!=null)
			{
				for(Entry<String,Object>param : params.entrySet())
				{
					declareParameter(new SqlParameter(param.getKey() , OracleTypes.VARCHAR));
				}
			}

			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
		
		private class DinamicMapper implements RowMapper
		{
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				String cols="";
				Map<String,String> map=new LinkedHashMap<String,String>(0);
				ResultSetMetaData metaData = rs.getMetaData();
				int numCols=metaData.getColumnCount();
				for (int i=1;i<=numCols;i++)
				{
					String col=metaData.getColumnName(i);
					if(rowNum==0)
					{
						cols=cols+col+",";
					}
					if(col!=null&&(col.substring(0,2).equalsIgnoreCase("fe")||col.substring(0,2).equalsIgnoreCase("ff")))
					{
						map.put(col,Utils.formateaFecha(rs.getString(col)));
					}
					else
					{
						map.put(col,rs.getString(col));
					}			
				}
				if(rowNum==0)
				{
					logger.info("Columnas: "+cols);
				}
				return map;
			}
		}
	}
	
	@Override
	public List<Map<String,String>>cargarMpolizasPorParametrosVariables(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsolici
			,String cdramant
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("nmsolici" , nmsolici);
		params.put("cdramant" , cdramant);
		logger.debug(Utils.log(
				 "\n*****************************************************"
				,"\n****** PKG_SATELITES2.P_GET_MPOLIZAS_X_PAR_VAR ******"
				,"\n****** params=",params
				,"\n*****************************************************"
				));
		Map<String,Object>procResult  = ejecutaSP(new CargarMpolizasPorParametrosVariables(getDataSource()), params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utils.log(
				 "\n*****************************************************"
				,"\n****** params="   , params
				,"\n****** registro=" , lista
				,"\n****** PKG_SATELITES2.P_GET_MPOLIZAS_X_PAR_VAR ******"
				,"\n*****************************************************"
				));
		return lista;
	}
	
	protected class CargarMpolizasPorParametrosVariables extends StoredProcedure
    {
    	protected CargarMpolizasPorParametrosVariables(DataSource dataSource)
        {
            super(dataSource,"PKG_SATELITES2.P_GET_MPOLIZAS_X_PAR_VAR");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsolici" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramant" , OracleTypes.VARCHAR));
            String[] cols = new String[]{
            		"CDUNIECO"  , "CDRAMO"   , "ESTADO"   , "NMPOLIZA" , "NMSUPLEM"
            		,"STATUS"   , "SWESTADO" , "NMSOLICI" , "FEAUTORI" , "CDMOTANU"
            		,"FEANULAC" , "SWAUTORI" , "CDMONEDA" , "FEINISUS" , "FEFINSUS"
            		,"OTTEMPOT" , "FEEFECTO" , "HHEFECTO" , "FEPROREN" , "FEVENCIM"
            		,"NMRENOVA" , "FERECIBO" , "FEULTSIN" , "NMNUMSIN" , "CDTIPCOA"
            		,"SWTARIFI" , "SWABRIDO" , "FEEMISIO" , "CDPERPAG" , "NMPOLIEX"
            		,"NMCUADRO" , "PORREDAU" , "SWCONSOL" , "NMPOLANT" , "NMPOLNVA"
            		,"FESOLICI" , "CDRAMANT" , "CDMEJRED" , "NMPOLDOC" , "NMPOLIZA2"
            		,"NMRENOVE" , "NMSUPLEE" , "TTIPCAMC" , "TTIPCAMV" , "SWPATENT"
            		,"NMPOLMST" , "PCPGOCTE" , "TIPOFLOT"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public List<Map<String,String>>cargarTconvalsit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		logger.debug(Utils.log(
				 "\n*********************************************"
				,"\n****** PKG_SATELITES2.P_GET_TCONVALSIT ******"
				,"\n****** params=",params
				,"\n*********************************************"
				));
		Map<String,Object>procResult  = ejecutaSP(new CargarTconvalsit(getDataSource()), params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utils.log(
				 "\n*********************************************"
				,"\n****** params="   , params
				,"\n****** registro=" , lista
				,"\n****** PKG_SATELITES2.P_GET_TCONVALSIT ******"
				,"\n*********************************************"
				));
		return lista;
	}
	
	protected class CargarTconvalsit extends StoredProcedure
    {
    	protected CargarTconvalsit(DataSource dataSource)
        {
            super(dataSource,"PKG_SATELITES2.P_GET_TCONVALSIT");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
            String[] cols = new String[]{
            		"CDUNIECO"
            		,"CDRAMO"
            		,"ESTADO"
            		,"NMPOLIZA"
            		,"NMSITUAC"
            		,"NMSUPLEM"
            		,"STATUS"
            		,"CDTIPSIT"
            		,"OTVALOR01","OTVALOR02","OTVALOR03","OTVALOR04","OTVALOR05","OTVALOR06","OTVALOR07","OTVALOR08","OTVALOR09","OTVALOR10"
            		,"OTVALOR11","OTVALOR12","OTVALOR13","OTVALOR14","OTVALOR15","OTVALOR16","OTVALOR17","OTVALOR18","OTVALOR19","OTVALOR20"
            		,"OTVALOR21","OTVALOR22","OTVALOR23","OTVALOR24","OTVALOR25","OTVALOR26","OTVALOR27","OTVALOR28","OTVALOR29","OTVALOR30"
            		,"OTVALOR31","OTVALOR32","OTVALOR33","OTVALOR34","OTVALOR35","OTVALOR36","OTVALOR37","OTVALOR38","OTVALOR39","OTVALOR40"
            		,"OTVALOR41","OTVALOR42","OTVALOR43","OTVALOR44","OTVALOR45","OTVALOR46","OTVALOR47","OTVALOR48","OTVALOR49","OTVALOR50"
            		,"OTVALOR51","OTVALOR52","OTVALOR53","OTVALOR54","OTVALOR55","OTVALOR56","OTVALOR57","OTVALOR58","OTVALOR59","OTVALOR60"
            		,"OTVALOR61","OTVALOR62","OTVALOR63","OTVALOR64","OTVALOR65","OTVALOR66","OTVALOR67","OTVALOR68","OTVALOR69","OTVALOR70"
            		,"OTVALOR71","OTVALOR72","OTVALOR73","OTVALOR74","OTVALOR75","OTVALOR76","OTVALOR77","OTVALOR78","OTVALOR79","OTVALOR80"
            		,"OTVALOR81","OTVALOR82","OTVALOR83","OTVALOR84","OTVALOR85","OTVALOR86","OTVALOR87","OTVALOR88","OTVALOR89","OTVALOR90"
            		,"OTVALOR91","OTVALOR92","OTVALOR93","OTVALOR94","OTVALOR95","OTVALOR96","OTVALOR97","OTVALOR98","OTVALOR99"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public List<Map<String,String>>cargarTbasvalsit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		logger.debug(Utils.log(
				 "\n*********************************************"
				,"\n****** PKG_SATELITES2.P_GET_TBASVALSIT ******"
				,"\n****** params=",params
				,"\n*********************************************"
				));
		Map<String,Object>procResult  = ejecutaSP(new CargarTbasvalsit(getDataSource()), params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utils.log(
				 "\n*********************************************"
				,"\n****** params="   , params
				,"\n****** registro=" , lista
				,"\n****** PKG_SATELITES2.P_GET_TBASVALSIT ******"
				,"\n*********************************************"
				));
		return lista;
	}
	
	protected class CargarTbasvalsit extends StoredProcedure
    {
    	protected CargarTbasvalsit(DataSource dataSource)
        {
            super(dataSource,"PKG_SATELITES2.P_GET_TBASVALSIT");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
            String[] cols = new String[]{
            		"CDUNIECO"
            		,"CDRAMO"
            		,"ESTADO"
            		,"NMPOLIZA"
            		,"NMSITUAC"
            		,"NMSUPLEM"
            		,"STATUS"
            		,"CDTIPSIT"
            		,"OTVALOR01","OTVALOR02","OTVALOR03","OTVALOR04","OTVALOR05","OTVALOR06","OTVALOR07","OTVALOR08","OTVALOR09","OTVALOR10"
            		,"OTVALOR11","OTVALOR12","OTVALOR13","OTVALOR14","OTVALOR15","OTVALOR16","OTVALOR17","OTVALOR18","OTVALOR19","OTVALOR20"
            		,"OTVALOR21","OTVALOR22","OTVALOR23","OTVALOR24","OTVALOR25","OTVALOR26","OTVALOR27","OTVALOR28","OTVALOR29","OTVALOR30"
            		,"OTVALOR31","OTVALOR32","OTVALOR33","OTVALOR34","OTVALOR35","OTVALOR36","OTVALOR37","OTVALOR38","OTVALOR39","OTVALOR40"
            		,"OTVALOR41","OTVALOR42","OTVALOR43","OTVALOR44","OTVALOR45","OTVALOR46","OTVALOR47","OTVALOR48","OTVALOR49","OTVALOR50"
            		,"OTVALOR51","OTVALOR52","OTVALOR53","OTVALOR54","OTVALOR55","OTVALOR56","OTVALOR57","OTVALOR58","OTVALOR59","OTVALOR60"
            		,"OTVALOR61","OTVALOR62","OTVALOR63","OTVALOR64","OTVALOR65","OTVALOR66","OTVALOR67","OTVALOR68","OTVALOR69","OTVALOR70"
            		,"OTVALOR71","OTVALOR72","OTVALOR73","OTVALOR74","OTVALOR75","OTVALOR76","OTVALOR77","OTVALOR78","OTVALOR79","OTVALOR80"
            		,"OTVALOR81","OTVALOR82","OTVALOR83","OTVALOR84","OTVALOR85","OTVALOR86","OTVALOR87","OTVALOR88","OTVALOR89","OTVALOR90"
            		,"OTVALOR91","OTVALOR92","OTVALOR93","OTVALOR94","OTVALOR95","OTVALOR96","OTVALOR97","OTVALOR98","OTVALOR99"
            		//MPOLISIT
            		,"SWREDUCI"    , "CDAGRUPA" , "CDESTADO"   , "FEFECSIT" , "FECHAREF" , "CDGRUPO"
            		, "NMSITUAEXT" , "NMSITAUX" , "NMSBSITEXT" , "CDPLAN"   , "CDASEGUR" , "DSGRUPO"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public Map<String,String>cargarMpoliperSituac(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("nmsituac" , nmsituac);
		logger.debug(Utils.log(
				 "\n**************************************************"
				,"\n****** PKG_SATELITES2.P_GET_MPOLIPER_SITUAC ******"
				,"\n****** params=",params
				,"\n**************************************************"
				));
		Map<String,Object>procResult  = ejecutaSP(new CargarMpoliperSituac(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		Map<String,String>mpoliper    = null;
		if(lista!=null&&lista.size()==1)
		{
			mpoliper = lista.get(0);
		}
		else if(lista!=null&&lista.size()>1)
		{
			throw new ApplicationException("Registro de relacion poliza-persona duplicado");
		}
		logger.debug(Utils.log(
				 "\n**************************************************"
				,"\n****** params="   , params
				,"\n****** registro=" , mpoliper
				,"\n****** PKG_SATELITES2.P_GET_MPOLIPER_SITUAC ******"
				,"\n**************************************************"
				));
		return mpoliper;
	}
	
	protected class CargarMpoliperSituac extends StoredProcedure
    {
    	protected CargarMpoliperSituac(DataSource dataSource)
        {
            super(dataSource,"PKG_SATELITES2.P_GET_MPOLIPER_SITUAC");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
            String[] cols = new String[]{
            		"CDUNIECO"  , "CDRAMO"   , "ESTADO"   , "NMPOLIZA"
            		,"NMSITUAC" , "CDROL"    , "CDPERSON" , "NMSUPLEM"
            		,"STATUS"   , "NMORDDOM" , "SWRECLAM" , "SWEXIPER"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public Map<String,String>cargarMpolisitSituac(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("nmsituac" , nmsituac);
		logger.debug(Utils.log(
				 "\n**************************************************"
				,"\n****** PKG_SATELITES2.P_GET_MPOLISIT_SITUAC ******"
				,"\n****** params=",params
				,"\n**************************************************"
				));
		Map<String,Object>procResult  = ejecutaSP(new CargarMpolisitSituac(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		Map<String,String>mpoliper    = null;
		if(lista!=null&&lista.size()==1)
		{
			mpoliper = lista.get(0);
		}
		else if(lista!=null&&lista.size()>1)
		{
			throw new ApplicationException("Registro de relacion poliza-situacion duplicado");
		}
		logger.debug(Utils.log(
				 "\n**************************************************"
				,"\n****** params="   , params
				,"\n****** registro=" , mpoliper
				,"\n****** PKG_SATELITES2.P_GET_MPOLISIT_SITUAC ******"
				,"\n**************************************************"
				));
		return mpoliper;
	}
	
	protected class CargarMpolisitSituac extends StoredProcedure
    {
    	protected CargarMpolisitSituac(DataSource dataSource)
        {
            super(dataSource,"PKG_SATELITES2.P_GET_MPOLISIT_SITUAC");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
            String[] cols = new String[]{
            		"CDUNIECO"  , "CDRAMO"     , "ESTADO"   , "NMPOLIZA" , "NMSITUAC"
            		,"NMSUPLEM" , "STATUS"     , "CDTIPSIT" , "SWREDUCI" , "CDAGRUPA"
            		,"CDESTADO" , "FEFECSIT"   , "FECHAREF" , "CDGRUPO"  , "NMSITUAEXT"
            		,"NMSITAUX" , "NMSBSITEXT" , "CDPLAN"   , "CDASEGUR" , "DSGRUPO"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public List<Map<String,String>>cargarTvalosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_TVALOSIT", params);
		Map<String,Object>procResult  = ejecutaSP(new CargarTvalosit(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_TVALOSIT", params,lista);
		return lista;
	}
	
	protected class CargarTvalosit extends StoredProcedure
    {
    	protected CargarTvalosit(DataSource dataSource)
        {
            super(dataSource,"PKG_SATELITES2.P_GET_TVALOSIT");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
            String[] cols = new String[]{
            		"CDUNIECO"  ,"CDRAMO"   ,"ESTADO"   ,"NMPOLIZA" ,"NMSITUAC" ,"NMSUPLEM" ,"STATUS"   ,"CDTIPSIT"
            		,"OTVALOR01","OTVALOR02","OTVALOR03","OTVALOR04","OTVALOR05","OTVALOR06","OTVALOR07","OTVALOR08","OTVALOR09","OTVALOR10"
            		,"OTVALOR11","OTVALOR12","OTVALOR13","OTVALOR14","OTVALOR15","OTVALOR16","OTVALOR17","OTVALOR18","OTVALOR19","OTVALOR20"
            		,"OTVALOR21","OTVALOR22","OTVALOR23","OTVALOR24","OTVALOR25","OTVALOR26","OTVALOR27","OTVALOR28","OTVALOR29","OTVALOR30"
            		,"OTVALOR31","OTVALOR32","OTVALOR33","OTVALOR34","OTVALOR35","OTVALOR36","OTVALOR37","OTVALOR38","OTVALOR39","OTVALOR40"
            		,"OTVALOR41","OTVALOR42","OTVALOR43","OTVALOR44","OTVALOR45","OTVALOR46","OTVALOR47","OTVALOR48","OTVALOR49","OTVALOR50"
            		,"OTVALOR51","OTVALOR52","OTVALOR53","OTVALOR54","OTVALOR55","OTVALOR56","OTVALOR57","OTVALOR58","OTVALOR59","OTVALOR60"
            		,"OTVALOR61","OTVALOR62","OTVALOR63","OTVALOR64","OTVALOR65","OTVALOR66","OTVALOR67","OTVALOR68","OTVALOR69","OTVALOR70"
            		,"OTVALOR71","OTVALOR72","OTVALOR73","OTVALOR74","OTVALOR75","OTVALOR76","OTVALOR77","OTVALOR78","OTVALOR79","OTVALOR80"
            		,"OTVALOR81","OTVALOR82","OTVALOR83","OTVALOR84","OTVALOR85","OTVALOR86","OTVALOR87","OTVALOR88","OTVALOR89","OTVALOR90"
            		,"OTVALOR91","OTVALOR92","OTVALOR93","OTVALOR94","OTVALOR95","OTVALOR96","OTVALOR97","OTVALOR98","OTVALOR99"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
	public List<Map<String,String>>cargarMpoliage(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	params.put("cdagente" , null);
    	Utils.debugProcedure(logger, "PKG_SATELITES.P_OBTIENE_MPOLIAGE2", params);
    	Map<String,Object>procResult  = ejecutaSP(new CargarMpoliage(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null||lista.size()==0)
    	{
    		throw new ApplicationException("No hay agentes para la poliza");
    	}
    	Utils.debugProcedure(logger, "PKG_SATELITES.P_OBTIENE_MPOLIAGE2", params, lista);
    	return lista;
	}
    
    protected class CargarMpoliage extends StoredProcedure
    {
    	protected CargarMpoliage(DataSource dataSource)
    	{
    		super(dataSource,"PKG_SATELITES.P_OBTIENE_MPOLIAGE2");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdagente" , OracleTypes.VARCHAR));
            String[] cols = new String[]{
            		"CDUNIECO" , "CDRAMO"   , "ESTADO"   , "NMPOLIZA" , "CDAGENTE" , "NMSUPLEM"
            		,"STATUS"  , "CDTIPOAG" , "PORREDAU" , "NMCUADRO" , "CDSUCURS"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public void validarDatosCliente(
    		String cdunieco
    		,String cdramo
    		,String estado
    		,String nmpoliza)throws Exception
    		{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	Utils.debugProcedure(logger, "PKG_SATELITES2.P_VALIDAR_CODIGO_EXTERNO_CTE", params);
    	ejecutaSP(new ValidarDatosCliente(getDataSource()),params);
    		}
    
    protected class ValidarDatosCliente extends StoredProcedure
    {
    	protected ValidarDatosCliente(DataSource dataSource)
    	{
    		super(dataSource,"PKG_SATELITES2.P_VALIDAR_CODIGO_EXTERNO_CTE");
    		declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
    		compile();
    	}
    }

    @Override
    public void validarDatosObligatoriosPrevex(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	Utils.debugProcedure(logger, "PKG_SATELITES.P_VALIDA_DATOS_OBLIG_PREVEX", params);
    	ejecutaSP(new ValidarDatosObligatoriosPrevex(getDataSource()),params);
	}
    
    protected class ValidarDatosObligatoriosPrevex extends StoredProcedure
    {
    	protected ValidarDatosObligatoriosPrevex(DataSource dataSource)
    	{
    		super(dataSource,"PKG_SATELITES.P_VALIDA_DATOS_OBLIG_PREVEX");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public void validarAtributosDXN(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	params.put("nmsuplem" , nmsuplem);
    	Utils.debugProcedure(logger, "PKG_SATELITES.P_VALIDA_ATRIB_FP_DXN", params);
    	ejecutaSP(new ValidarAtributosDXN(getDataSource()),params);
	}
    
    protected class ValidarAtributosDXN extends StoredProcedure
    {
    	protected ValidarAtributosDXN(DataSource dataSource)
    	{
    		super(dataSource,"PKG_SATELITES.P_VALIDA_ATRIB_FP_DXN");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_swexito_o", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
	public Map<String,String>cargarUltimoNmsuplemPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_MAX_SUPLEMENTO", params);
    	Map<String,Object>procResult = ejecutaSP(new CargarUltimoNmsuplemPoliza(getDataSource()),params);
    	Map<String,String>salida = new LinkedHashMap<String,String>();
    	salida.put("nmsuplem" , (String)procResult.get("pv_nmsuplem_o"));
    	return salida;
	}
    
    protected class CargarUltimoNmsuplemPoliza extends StoredProcedure
    {
    	protected CargarUltimoNmsuplemPoliza(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CONSULTA.P_GET_MAX_SUPLEMENTO");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public List<Map<String,String>>cargarMpoliperOtrosRolesPorNmsituac(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String rolesPipes)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	params.put("nmsuplem" , nmsuplem);
    	params.put("nmsituac" , nmsituac);
    	params.put("roles"    , rolesPipes);
    	Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_MPOLIPER_OTROS_ROLES", params);
    	Map<String,Object>procResult  = ejecutaSP(new CargarMpoliperOtrosRolesPorNmsituac(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_MPOLIPER_OTROS_ROLES", params, lista);
    	return lista;
	}
    
    protected class CargarMpoliperOtrosRolesPorNmsituac extends StoredProcedure
    {
    	protected CargarMpoliperOtrosRolesPorNmsituac(DataSource dataSource)
    	{
    		super(dataSource , "PKG_SATELITES2.P_GET_MPOLIPER_OTROS_ROLES");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("roles"    , OracleTypes.VARCHAR));
            String[] cols=new String[]{
            		"CDUNIECO"    , "CDRAMO"
            		,"ESTADO"     , "NMPOLIZA"
            		,"NMSITUAC"   , "CDROL"
                    ,"CDPERSON"   , "NMSUPLEM"
                    ,"STATUS"     , "NMORDDOM"
                    ,"SWRECLAM"   , "SWEXIPER"
                    ,"CDPARENT"   , "PORBENEF"
                    ,"CDTIPIDE"   , "CDIDEPER"
                    ,"DSNOMBRE"   , "CDTIPPER"
                    ,"OTFISJUR"   , "OTSEXO"
                    ,"FENACIMI"   , "CDRFC"
                    ,"DSEMAIL"    , "DSNOMBRE1"
                    ,"DSAPELLIDO" , "DSAPELLIDO1"
                    ,"CDNACION"   , "DSCOMNOM"
                    ,"DSRAZSOC"   , "FEINGRESO"
                    ,"FEACTUAL"   , "DSNOMUSU"
                    ,"CDESTCIV"   , "CDGRUECO"
                    ,"CDSTIPPE"   , "NMNUMNOM"
                    ,"CURP"       , "CANALING"
                    ,"CONDUCTO"   , "PTCUMUPR"
                    ,"STATUS_PER" , "RESIDENCIA"
                    ,"NONGRATA"   , "CDIDEEXT"
                    ,"CDSUCEMI"
            };
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public List<Map<String,String>>cargarTiposSituacionPorRamo(String cdramo)throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdramo" , cdramo);
    	Utils.debugProcedure(logger, "PKG_CONSULTA.P_OBTIENE_SITUACION", params);
    	Map<String,Object>procResult  = ejecutaSP(new CargarTiposSituacionPorRamo(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utils.debugProcedure(logger, "PKG_CONSULTA.P_OBTIENE_SITUACION", params, lista);
    	return lista;
    }
    
    protected class CargarTiposSituacionPorRamo extends StoredProcedure
    {
    	protected CargarTiposSituacionPorRamo(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CONSULTA.P_OBTIENE_SITUACION");
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            String[] cols=new String[]{"CDTIPSIT","DSTIPSIT"};
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public boolean verificarCodigoPostalFronterizo(String cdpostal)throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdpostal" , cdpostal);
    	Utils.debugProcedure(logger, "PKG_SATELITES2.P_VERIFICA_CDPOSTAL_FRONTER", params);
    	Map<String,Object>procResult = ejecutaSP(new VerificarCodigoPostalFronterizo(getDataSource()),params);
    	boolean esFront = ((String)procResult.get("pv_fronterizo_o")).equals("S");
    	logger.debug(Utils.log("verificarCodigoPostalFronterizo=",esFront));
    	return esFront;
    }
    
    protected class VerificarCodigoPostalFronterizo extends StoredProcedure
    {
    	protected VerificarCodigoPostalFronterizo(DataSource dataSource)
    	{
    		super(dataSource , "PKG_SATELITES2.P_VERIFICA_CDPOSTAL_FRONTER");
            declareParameter(new SqlParameter("cdpostal"   , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_fronterizo_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"     , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"      , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public Map<String,String>cargarAtributosBaseCotizacion(String cdtipsit)throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdtipsit" , cdtipsit);
    	Utils.debugProcedure(logger, "PKG_SATELITES.P_OBT_ATRIBUTOS", params);
    	Map<String,Object>procResult   = ejecutaSP(new CargarAtributosBaseCotizacion(getDataSource()),params);
    	List<Map<String,String>> lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null||lista.size()==0)
    	{
    		throw new ApplicationException("No hay atributos base de cotizacion para la modalidad");
    	}
    	if(lista.size()>1)
    	{
    		throw new ApplicationException("Atributos base de cotizacion duplicados para la modalidad");
    	}
    	Utils.debugProcedure(logger, "PKG_SATELITES.P_OBT_ATRIBUTOS", params, lista);
    	return lista.get(0);
    }
    
    protected class CargarAtributosBaseCotizacion extends StoredProcedure
    {
    	protected CargarAtributosBaseCotizacion(DataSource dataSource)
    	{
    		super(dataSource , "PKG_SATELITES.P_OBT_ATRIBUTOS");
            declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
            String[] cols=new String[]{"SEXO","FENACIMI","PARENTESCO","CODPOSTAL"};
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public Map<String,String>cargarInformacionPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdusuari
			)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	params.put("cdusuari" , cdusuari);
    	Utils.debugProcedure(logger, "PKG_SATELITES.P_GET_INFO_MPOLIZAS", params);
    	Map<String,Object>procResult  = ejecutaSP(new CargarInformacionPoliza(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null||lista.size()==0)
    	{
    		throw new ApplicationException("No hay informacion de poliza");
    	}
    	if(lista.size()>1)
    	{
    		throw new ApplicationException("Informacion de poliza duplicada");
    	}
    	Utils.debugProcedure(logger, "PKG_SATELITES.P_GET_INFO_MPOLIZAS", params, lista);
    	return lista.get(0);
	}
    
    protected class CargarInformacionPoliza extends StoredProcedure
    {
    	protected CargarInformacionPoliza(DataSource dataSource)
    	{
    		super(dataSource , "PKG_SATELITES.P_GET_INFO_MPOLIZAS");
    		declareParameter(new SqlParameter("cdunieco" , OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            String[] cols=new String[]{
            		"status"    , "swestado" , "nmsolici" , "feautori" , "cdmotanu" , "feanulac" , "swautori"
            		,"cdmoneda" , "feinisus" , "fefinsus" , "ottempot" , "feefecto" , "hhefecto" , "feproren"
            		,"fevencim" , "nmrenova" , "ferecibo" , "feultsin" , "nmnumsin" , "cdtipcoa" , "swtarifi"
            		,"swabrido" , "feemisio" , "cdperpag" , "nmpoliex" , "nmcuadro" , "porredau" , "swconsol"
            		,"nmpolant" , "nmpolnva" , "fesolici" , "cdramant" , "cdmejred" , "nmpoldoc" , "nmpoliza2"
            		,"nmrenove" , "nmsuplee" , "ttipcamc" , "ttipcamv" , "swpatent" , "cdagente"
    	            };
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_messages_o" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public String recuperarPorcentajeRecargoPorProducto(String cdramo,String cdperpag)throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdramo"   , cdramo);
    	params.put("cdperpag" , cdperpag);
    	Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_PORC_RECARGO", params);
    	Map<String,Object>procResult=ejecutaSP(new RecuperarPorcentajeRecargoPorProducto(getDataSource()), params);
    	double recargo;
    	try
    	{
    		recargo=Double.parseDouble((String)procResult.get("pv_porcrcgo_o"));
    	}
    	catch(Exception ex)
    	{
    		logger.error(ex);
    		throw new ApplicationException("Error al obtener recargo por forma de pago");
    	}
    	String sRecargo=String.format("%.2f", recargo);
    	logger.debug(Utils.log("PKG_CONSULTA.P_GET_PORC_RECARGO result=",sRecargo));
    	return sRecargo;
    }
    
    protected class RecuperarPorcentajeRecargoPorProducto extends StoredProcedure
    {
    	protected RecuperarPorcentajeRecargoPorProducto(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CONSULTA.P_GET_PORC_RECARGO");
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("cdperpag" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_porcrcgo_o" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public List<Map<String,String>>recuperarValoresPantalla(
			String pantalla
			,String cdramo
			,String cdtipsit
			)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("pantalla" , pantalla);
    	params.put("cdramo"   , cdramo);
    	params.put("cdtipsit" , cdtipsit);
    	Utils.debugProcedure(logger,"PKG_SATELITES2.P_GET_VALORES_PANTALLA",params);
    	Map<String,Object>procResult  = ejecutaSP(new RecuperarValoresPantalla(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utils.debugProcedure(logger,"PKG_SATELITES2.P_GET_VALORES_PANTALLA",params,lista);
    	return lista;
	}
    
    protected class RecuperarValoresPantalla extends StoredProcedure
    {
    	protected RecuperarValoresPantalla(DataSource dataSource)
    	{
    		super(dataSource , "PKG_SATELITES2.P_GET_VALORES_PANTALLA");
    		declareParameter(new SqlParameter("pantalla" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
            String[] cols=new String[]{
            		"NAME","VALOR"
    	            };
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public List<Map<String,String>>recuperarValoresAtributosFactores(String cdramo,String cdtipsit)throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdramo"   , cdramo);
    	params.put("cdtipsit" , cdtipsit);
    	Utils.debugProcedure(logger,"PKG_SATELITES2.P_GET_VALORES_DEFECTO_FACTORES",params);
    	Map<String,Object>procResult  = ejecutaSP(new RecuperarValoresAtributosFactores(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utils.debugProcedure(logger,"PKG_SATELITES2.P_GET_VALORES_DEFECTO_FACTORES",params,lista);
    	return lista;
	}
    
    protected class RecuperarValoresAtributosFactores extends StoredProcedure
    {
    	protected RecuperarValoresAtributosFactores(DataSource dataSource)
    	{
    		super(dataSource , "PKG_SATELITES2.P_GET_VALORES_DEFECTO_FACTORES");
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
            String[] cols=new String[]{
            		"NAME","VALOR"
    	            };
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public List<Map<String,String>>obtieneContratantePoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdrol
			,String cdperson
			)throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	params.put("nmsituac" , nmsituac);
    	params.put("cdrol"    , cdrol);
    	params.put("cdperson" , cdperson);
    	Utils.debugProcedure(logger,"PKG_SATELITES.P_OBTIENE_MPOLIPER",params);
    	Map<String,Object>procResult  = ejecutaSP(new ObtieneContratantePoliza(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utils.debugProcedure(logger,"PKG_SATELITES.P_OBTIENE_MPOLIPER",params,lista);
    	return lista;
    }
    
    protected class ObtieneContratantePoliza extends StoredProcedure
    {
    	protected ObtieneContratantePoliza(DataSource dataSource)
    	{
    		super(dataSource , "PKG_SATELITES.P_OBTIENE_MPOLIPER");
    		declareParameter(new SqlParameter("cdunieco"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmpoliza"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdrol" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdperson" , OracleTypes.VARCHAR));
    		String[] cols=new String[]{
    				"NOMBRE","CDRFC","CDPERSON","CDIDEPER","CDIDEEXT", "NMSITUAC", "CDROL", "STATUS", "NMORDDOM", "SWRECLAM", "OTFISJUR", "DSNOMBRE","DSNOMBRE1","DSAPELLIDO","DSAPELLIDO1"
    		};
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
    		compile();
    	}
    }
    
    @Override
    public List<Map<String,String>>recuperarPolizasEndosables(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmpoliex
			,String ramo
			,String cdagente
			,String statusVig
			,String finicio //Se agrega campo fecha de fin
			,String ffin//Se agrega campo fecha de fin
			)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco"  , cdunieco);
    	params.put("cdramo"    , cdramo);
    	params.put("estado"    , estado);
    	params.put("nmpoliza"  , nmpoliza);
    	params.put("nmpoliex"  , nmpoliex);
    	params.put("ramo"      , ramo);
    	params.put("cdagente"  , cdagente);
    	params.put("statusVig" , statusVig); 
    	params.put("finicio"   , finicio);//Se agrega campo fecha de inicio
    	params.put("ffin"      , ffin);//Se agrega campo fecha de fin
    	Utils.debugProcedure(logger,"PKG_CONSULTA.P_GET_POLIZAS_PARA_ENDOSOS",params);
    	Map<String,Object>procResult  = ejecutaSP(new RecuperarPolizasEndosables(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utils.debugProcedure(logger,"PKG_CONSULTA.P_GET_POLIZAS_PARA_ENDOSOS",params,lista);
    	return lista;
	}
    
    protected class RecuperarPolizasEndosables extends StoredProcedure
    {
    	protected RecuperarPolizasEndosables(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CONSULTA.P_GET_POLIZAS_PARA_ENDOSOS");
            declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliex"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("ramo"      , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdagente"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("statusVig" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("finicio"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("ffin"      , OracleTypes.VARCHAR));
            String[] cols=new String[]{
            		//MPOLIZAS
            		"CDUNIECO"  , "CDRAMO"   , "ESTADO"   , "NMPOLIZA"
            		,"NMSUPLEM" , "STATUS"   , "SWESTADO" , "NMSOLICI"
            		,"FEAUTORI" , "CDMOTANU" , "FEANULAC" , "SWAUTORI"
            		,"CDMONEDA" , "FEINISUS" , "FEFINSUS" , "OTTEMPOT"
            		,"FEEFECTO" , "HHEFECTO" , "FEPROREN" , "FEVENCIM"
            		,"NMRENOVA" , "FERECIBO" , "FEULTSIN" , "NMNUMSIN"
            		,"CDTIPCOA" , "SWTARIFI" , "SWABRIDO" , "FEEMISIO"
            		,"CDPERPAG" , "NMPOLIEX" , "NMCUADRO" , "PORREDAU"
            		,"SWCONSOL" , "NMPOLANT" , "NMPOLNVA" , "FESOLICI"
            		,"CDRAMANT" , "CDMEJRED" , "NMPOLDOC" , "NMPOLIZA2"
            		,"NMRENOVE" , "NMSUPLEE" , "TTIPCAMC" , "TTIPCAMV"
            		,"SWPATENT" , "NMPOLMST" , "PCPGOCTE" , "TIPOFLOT"
            		//MPERSONA
            		,"CDPERSON"   , "CDTIPIDE"    , "CDIDEPER" , "DSNOMBRE"
            		,"CDTIPPER"   , "OTFISJUR"    , "OTSEXO"   , "FENACIMI"
            		,"CDRFC"      , "FOTO"        , "DSEMAIL"  , "DSNOMBRE1"
            		,"DSAPELLIDO" , "DSAPELLIDO1" , "CDNACION" , "DSCOMNOM"
            		,"DSRAZSOC"   , "FEINGRESO"   , "FEACTUAL" , "DSNOMUSU"
            		,"CDESTCIV"   , "CDGRUECO"    , "CDSTIPPE" , "NMNUMNOM"
            		,"CURP"       , "CANALING"    , "CONDUCTO" , "PTCUMUPR"
            		,"STATUSPER"  , "RESIDENCIA"  , "NONGRATA" , "CDIDEEXT"
            		,"CDSUCEMI"
            		//TMESACONTROL
            		,"NTRAMITE"
            		//TVALOPOL
            		,"OTVALOR01" , "OTVALOR02" , "OTVALOR03" , "OTVALOR04" , "OTVALOR05"
            		,"OTVALOR06" , "OTVALOR07" , "OTVALOR08" , "OTVALOR09" , "OTVALOR10"
            		,"OTVALOR11" , "OTVALOR12" , "OTVALOR13" , "OTVALOR14" , "OTVALOR15"
            		,"OTVALOR16" , "OTVALOR17" , "OTVALOR18" , "OTVALOR19" , "OTVALOR20"
            		,"OTVALOR21" , "OTVALOR22" , "OTVALOR23" , "OTVALOR24" , "OTVALOR25"
            		,"OTVALOR26" , "OTVALOR27" , "OTVALOR28" , "OTVALOR29" , "OTVALOR30"
            		,"OTVALOR31" , "OTVALOR32" , "OTVALOR33" , "OTVALOR34" , "OTVALOR35"
            		,"OTVALOR36" , "OTVALOR37" , "OTVALOR38" , "OTVALOR39" , "OTVALOR40"
            		,"OTVALOR41" , "OTVALOR42" , "OTVALOR43" , "OTVALOR44" , "OTVALOR45"
            		,"OTVALOR46" , "OTVALOR47" , "OTVALOR48" , "OTVALOR49" , "OTVALOR50"
            		,"DSVALOR01" , "DSVALOR02" , "DSVALOR03" , "DSVALOR04" , "DSVALOR05"
            		,"DSVALOR06" , "DSVALOR07" , "DSVALOR08" , "DSVALOR09" , "DSVALOR10"
            		,"DSVALOR11" , "DSVALOR12" , "DSVALOR13" , "DSVALOR14" , "DSVALOR15"
            		,"DSVALOR16" , "DSVALOR17" , "DSVALOR18" , "DSVALOR19" , "DSVALOR20"
            		,"DSVALOR21" , "DSVALOR22" , "DSVALOR23" , "DSVALOR24" , "DSVALOR25"
            		,"DSVALOR26" , "DSVALOR27" , "DSVALOR28" , "DSVALOR29" , "DSVALOR30"
            		,"DSVALOR31" , "DSVALOR32" , "DSVALOR33" , "DSVALOR34" , "DSVALOR35"
            		,"DSVALOR36" , "DSVALOR37" , "DSVALOR38" , "DSVALOR39" , "DSVALOR40"
            		,"DSVALOR41" , "DSVALOR42" , "DSVALOR43" , "DSVALOR44" , "DSVALOR45"
            		,"DSVALOR46" , "DSVALOR47" , "DSVALOR48" , "DSVALOR49" , "DSVALOR50"
            		//OTROS
            		,"RAMO"
    	            };
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public List<Map<String,String>>recuperarHistoricoPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	Utils.debugProcedure(logger,"PKG_CONSULTA.P_GET_HISTORICO_POLIZA",params);
    	Map<String,Object>procResult  = ejecutaSP(new RecuperarHistoricoPoliza(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utils.debugProcedure(logger,"PKG_CONSULTA.P_GET_HISTORICO_POLIZA",params,lista);
    	return lista;
	}
    
    protected class RecuperarHistoricoPoliza extends StoredProcedure
    {
    	protected RecuperarHistoricoPoliza(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CONSULTA.P_GET_HISTORICO_POLIZA");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            String[] cols=new String[]{
            		"NSUPLOGI" , "DSTIPSUP" , "FEEMISIO" , "FEINIVAL" , "FEFINVAL"
    	            };
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public List<Map<String,String>>recuperarIncisosPolizaGrupoFamilia(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo
			,String nmfamili
			,String nivel
			)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	params.put("cdgrupo"  , cdgrupo);
    	params.put("nmfamili" , nmfamili);
    	params.put("nivel"    , nivel);
    	Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DATOS_INCISOS", params);
    	Map<String,Object>procResult  = ejecutaSP(new RecuperarIncisosPolizaGrupoFamilia(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	construirClavesAtributos(lista);
    	Utils.debugProcedure(logger,"PKG_CONSULTA.P_GET_DATOS_INCISOS",params,lista);
    	return lista;
	}
    
    protected class RecuperarIncisosPolizaGrupoFamilia extends StoredProcedure
    {
    	protected RecuperarIncisosPolizaGrupoFamilia(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CONSULTA.P_GET_DATOS_INCISOS");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmfamili" , OracleTypes.VARCHAR));
            String[] cols=new String[]{
            		//MPOLISIT
            		"CDUNIECO"    , "CDRAMO"   , "ESTADO"     , "NMPOLIZA"
            		,"NMSITUAC"   , "NMSUPLEM" , "STATUS"     , "CDTIPSIT"
            		,"SWREDUCI"   , "CDAGRUPA" , "CDESTADO"   , "FEFECSIT"
            		,"FECHAREF"   , "CDGRUPO"  , "NMSITUAEXT" , "NMSITAUX"
            		,"NMSBSITEXT" , "CDPLAN"   , "CDASEGUR"   , "DSGRUPO"
            		//TVALOSIT
            		,"NMSUPLEM_TVAL"
            		,"OTVALOR01" , "OTVALOR02" , "OTVALOR03" , "OTVALOR04" , "OTVALOR05" , "OTVALOR06" , "OTVALOR07" , "OTVALOR08" , "OTVALOR09" , "OTVALOR10"
            		,"OTVALOR11" , "OTVALOR12" , "OTVALOR13" , "OTVALOR14" , "OTVALOR15" , "OTVALOR16" , "OTVALOR17" , "OTVALOR18" , "OTVALOR19" , "OTVALOR20"
            		,"OTVALOR21" , "OTVALOR22" , "OTVALOR23" , "OTVALOR24" , "OTVALOR25" , "OTVALOR26" , "OTVALOR27" , "OTVALOR28" , "OTVALOR29" , "OTVALOR30"
            		,"OTVALOR31" , "OTVALOR32" , "OTVALOR33" , "OTVALOR34" , "OTVALOR35" , "OTVALOR36" , "OTVALOR37" , "OTVALOR38" , "OTVALOR39" , "OTVALOR40"
            		,"OTVALOR41" , "OTVALOR42" , "OTVALOR43" , "OTVALOR44" , "OTVALOR45" , "OTVALOR46" , "OTVALOR47" , "OTVALOR48" , "OTVALOR49" , "OTVALOR50"
            		,"OTVALOR51" , "OTVALOR52" , "OTVALOR53" , "OTVALOR54" , "OTVALOR55" , "OTVALOR56" , "OTVALOR57" , "OTVALOR58" , "OTVALOR59" , "OTVALOR60"
            		,"OTVALOR61" , "OTVALOR62" , "OTVALOR63" , "OTVALOR64" , "OTVALOR65" , "OTVALOR66" , "OTVALOR67" , "OTVALOR68" , "OTVALOR69" , "OTVALOR70"
            		,"OTVALOR71" , "OTVALOR72" , "OTVALOR73" , "OTVALOR74" , "OTVALOR75" , "OTVALOR76" , "OTVALOR77" , "OTVALOR78" , "OTVALOR79" , "OTVALOR80"
            		,"OTVALOR81" , "OTVALOR82" , "OTVALOR83" , "OTVALOR84" , "OTVALOR85" , "OTVALOR86" , "OTVALOR87" , "OTVALOR88" , "OTVALOR89" , "OTVALOR90"
            		,"OTVALOR91" , "OTVALOR92" , "OTVALOR93" , "OTVALOR94" , "OTVALOR95" , "OTVALOR96" , "OTVALOR97" , "OTVALOR98" , "OTVALOR99"
            		,"DSVALOR01" , "DSVALOR02" , "DSVALOR03" , "DSVALOR04" , "DSVALOR05" , "DSVALOR06" , "DSVALOR07" , "DSVALOR08" , "DSVALOR09" , "DSVALOR10"
            		,"DSVALOR11" , "DSVALOR12" , "DSVALOR13" , "DSVALOR14" , "DSVALOR15" , "DSVALOR16" , "DSVALOR17" , "DSVALOR18" , "DSVALOR19" , "DSVALOR20"
            		,"DSVALOR21" , "DSVALOR22" , "DSVALOR23" , "DSVALOR24" , "DSVALOR25" , "DSVALOR26" , "DSVALOR27" , "DSVALOR28" , "DSVALOR29" , "DSVALOR30"
            		,"DSVALOR31" , "DSVALOR32" , "DSVALOR33" , "DSVALOR34" , "DSVALOR35" , "DSVALOR36" , "DSVALOR37" , "DSVALOR38" , "DSVALOR39" , "DSVALOR40"
            		,"DSVALOR41" , "DSVALOR42" , "DSVALOR43" , "DSVALOR44" , "DSVALOR45" , "DSVALOR46" , "DSVALOR47" , "DSVALOR48" , "DSVALOR49" , "DSVALOR50"
            		,"DSVALOR51" , "DSVALOR52" , "DSVALOR53" , "DSVALOR54" , "DSVALOR55" , "DSVALOR56" , "DSVALOR57" , "DSVALOR58" , "DSVALOR59" , "DSVALOR60"
            		,"DSVALOR61" , "DSVALOR62" , "DSVALOR63" , "DSVALOR64" , "DSVALOR65" , "DSVALOR66" , "DSVALOR67" , "DSVALOR68" , "DSVALOR69" , "DSVALOR70"
            		,"DSVALOR71" , "DSVALOR72" , "DSVALOR73" , "DSVALOR74" , "DSVALOR75" , "DSVALOR76" , "DSVALOR77" , "DSVALOR78" , "DSVALOR79" , "DSVALOR80"
            		,"DSVALOR81" , "DSVALOR82" , "DSVALOR83" , "DSVALOR84" , "DSVALOR85" , "DSVALOR86" , "DSVALOR87" , "DSVALOR88" , "DSVALOR89" , "DSVALOR90"
            		,"DSVALOR91" , "DSVALOR92" , "DSVALOR93" , "DSVALOR94" , "DSVALOR95" , "DSVALOR96" , "DSVALOR97" , "DSVALOR98" , "DSVALOR99"
            		//MPERSONA
            		,"CDPERSON"   , "CDTIPIDE"    , "CDIDEPER" , "DSNOMBRE"
            		,"CDTIPPER"   , "OTFISJUR"    , "OTSEXO"   , "FENACIMI"
            		,"CDRFC"      , "FOTO"        , "DSEMAIL"  , "DSNOMBRE1"
            		,"DSAPELLIDO" , "DSAPELLIDO1" , "CDNACION" , "DSCOMNOM"
            		,"DSRAZSOC"   , "FEINGRESO"   , "FEACTUAL" , "DSNOMUSU"
            		,"CDESTCIV"   , "CDGRUECO"    , "CDSTIPPE" , "NMNUMNOM"
            		,"CURP"       , "CANALING"    , "CONDUCTO" , "PTCUMUPR"
            		,"STATUSPER"  , "RESIDENCIA"  , "NONGRATA" , "CDIDEEXT"
            		,"CDSUCEMI"
            		//MPOLIPER
            		,"CDROL" , "NMORDDOM" , "SWRECLAM" , "SWEXIPER" , "CDPARENT" , "PORBENEF"
            		//TATRISIT:
            		,"DSATRIBU01" , "DSATRIBU02" , "DSATRIBU03" , "DSATRIBU04" , "DSATRIBU05" , "DSATRIBU06" , "DSATRIBU07" , "DSATRIBU08" , "DSATRIBU09" , "DSATRIBU10"
            		,"DSATRIBU11" , "DSATRIBU12" , "DSATRIBU13" , "DSATRIBU14" , "DSATRIBU15" , "DSATRIBU16" , "DSATRIBU17" , "DSATRIBU18" , "DSATRIBU19" , "DSATRIBU20"
            		,"DSATRIBU21" , "DSATRIBU22" , "DSATRIBU23" , "DSATRIBU24" , "DSATRIBU25" , "DSATRIBU26" , "DSATRIBU27" , "DSATRIBU28" , "DSATRIBU29" , "DSATRIBU30"
            		,"DSATRIBU31" , "DSATRIBU32" , "DSATRIBU33" , "DSATRIBU34" , "DSATRIBU35" , "DSATRIBU36" , "DSATRIBU37" , "DSATRIBU38" , "DSATRIBU39" , "DSATRIBU40"
            		,"DSATRIBU41" , "DSATRIBU42" , "DSATRIBU43" , "DSATRIBU44" , "DSATRIBU45" , "DSATRIBU46" , "DSATRIBU47" , "DSATRIBU48" , "DSATRIBU49" , "DSATRIBU50"
            		,"DSATRIBU51" , "DSATRIBU52" , "DSATRIBU53" , "DSATRIBU54" , "DSATRIBU55" , "DSATRIBU56" , "DSATRIBU57" , "DSATRIBU58" , "DSATRIBU59" , "DSATRIBU60"
            		,"DSATRIBU61" , "DSATRIBU62" , "DSATRIBU63" , "DSATRIBU64" , "DSATRIBU65" , "DSATRIBU66" , "DSATRIBU67" , "DSATRIBU68" , "DSATRIBU69" , "DSATRIBU70"
            		,"DSATRIBU71" , "DSATRIBU72" , "DSATRIBU73" , "DSATRIBU74" , "DSATRIBU75" , "DSATRIBU76" , "DSATRIBU77" , "DSATRIBU78" , "DSATRIBU79" , "DSATRIBU80"
            		,"DSATRIBU81" , "DSATRIBU82" , "DSATRIBU83" , "DSATRIBU84" , "DSATRIBU85" , "DSATRIBU86" , "DSATRIBU87" , "DSATRIBU88" , "DSATRIBU89" , "DSATRIBU90"
            		,"DSATRIBU91" , "DSATRIBU92" , "DSATRIBU93" , "DSATRIBU94" , "DSATRIBU95" , "DSATRIBU96" , "DSATRIBU97" , "DSATRIBU98" , "DSATRIBU99"
            		//MPLANES
            		,"DSPLAN"
    	            };
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public String recuperarValorAtributoUnico(
			String cdtipsit
			,String cdatribu
			,String otclave
			)throws Exception
	{
		
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdtipsit" , cdtipsit);
    	params.put("cdatribu" , cdatribu);
    	params.put("otclave"  , otclave);
    	Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_OTVALOR_CAT_TATRISIT", params);
    	Map<String,Object>procResult = ejecutaSP(new RecuperarValorAtributoUnico(getDataSource()),params);
    	String otvalor               = (String)procResult.get("pv_otvalor_o");
    	if(otvalor==null)
    	{
    		otvalor="";
    	}
    	logger.debug(Utils.log("PKG_SATELITES2.P_GET_OTVALOR_CAT_TATRISIT result=",otvalor));
    	return otvalor;
	}
    
    protected class RecuperarValorAtributoUnico extends StoredProcedure
    {
    	protected RecuperarValorAtributoUnico(DataSource dataSource)
    	{
    		super(dataSource , "PKG_SATELITES2.P_GET_OTVALOR_CAT_TATRISIT");
            declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdatribu" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("otclave"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_otvalor_o" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public List<Map<String,String>>recuperarGruposPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_GRUPOS_POLIZA", params);
    	Map<String,Object>procResult  = ejecutaSP(new RecuperarGruposPoliza(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utils.debugProcedure(logger,"PKG_CONSULTA.P_GET_GRUPOS_POLIZA",params,lista);
    	return lista;
	}
    
    protected class RecuperarGruposPoliza extends StoredProcedure
    {
    	protected RecuperarGruposPoliza(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CONSULTA.P_GET_GRUPOS_POLIZA");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            String[] cols=new String[]{
            		"CDGRUPO" , "DSGRUPO"
    	            };
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public List<Map<String,String>>recuperarFamiliasPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_FAMILIAS_POLIZA", params);
    	Map<String,Object>procResult  = ejecutaSP(new RecuperarFamiliasPoliza(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utils.debugProcedure(logger,"PKG_CONSULTA.P_GET_FAMILIAS_POLIZA",params,lista);
    	return lista;
	}
    
    protected class RecuperarFamiliasPoliza extends StoredProcedure
    {
    	protected RecuperarFamiliasPoliza(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CONSULTA.P_GET_FAMILIAS_POLIZA");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            String[] cols=new String[]{
            		"NMSITAUX" , "TITULAR"
    	            };
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    
    @Override
    public boolean esProductoSalud(String cdramo) throws Exception {
    	boolean esSalud = false;
    	Map<String,Object> params = new HashMap<String, Object>();
    	params.put("pv_cdramo_i" , cdramo);
    	Utils.debugProcedure(logger, "PKG_CONSULTA.P_VALIDA_PRODUCTO_SALUD", params);
    	Map<String,Object> result = ejecutaSP(new ValidaProductoSaludSP(getDataSource()),params);
    	if(Constantes.SI.equals(result.get("pv_swprosalud_o"))) {
    		esSalud = true;
    	}
    	return esSalud;
    }
    
    protected class ValidaProductoSaludSP extends StoredProcedure {
    	protected ValidaProductoSaludSP(DataSource dataSource) {
    		super(dataSource , "PKG_CONSULTA.P_VALIDA_PRODUCTO_SALUD");
            declareParameter(new SqlParameter("pv_cdramo_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_swprosalud_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   ,  OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    ,  OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public List<String> recuperarDescripcionAtributosSituacionPorRamo(String cdramo) throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdramo" , cdramo);
    	Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DSATRIBUS_TATRISIT", params);
    	Map<String,Object> procResult = ejecutaSP(new RecuperarDescripcionAtributosSituacionPorRamo(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		throw new ApplicationException("No se encontraron nombres de atributos de situacion");
    	}
    	List<String>listaNombres = new ArrayList<String>();
    	for(Map<String,String>elem:lista)
    	{
    		listaNombres.add(elem.get("ATRIBUTO"));
    	}
    	Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DSATRIBUS_TATRISIT", params, listaNombres);
    	return listaNombres;
    }
    
    protected class RecuperarDescripcionAtributosSituacionPorRamo extends StoredProcedure
    {
    	protected RecuperarDescripcionAtributosSituacionPorRamo(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CONSULTA.P_GET_DSATRIBUS_TATRISIT");
            declareParameter(new SqlParameter("cdramo" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(new String[]{"ATRIBUTO"})));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   ,  OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    ,  OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public Map<String,String> recuperarFechasLimiteEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdsisrol
			,String cdusuari
			,String cdtipsup
			)throws Exception
	{
    	Map<String,String> params = new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	params.put("cdsisrol" , cdsisrol);
    	params.put("cdusuari" , cdusuari);
    	params.put("cdtipsup" , cdtipsup);
    	Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_FECHAS_ENDOSO", params);
    	Map<String,Object> procResult = ejecutaSP(new RecuperarFechasLimiteEndoso(getDataSource()),params);
    	Map<String,String> result     = new HashMap<String,String>();
    	result.put("FECHA_MINIMA" , (String)procResult.get("pv_fechamin_o"));
    	result.put("FECHA_MAXIMA" , (String)procResult.get("pv_fechamax_o"));
    	result.put("FECHA_REFERENCIA" , (String)procResult.get("pv_fecharef_o"));
    	result.put("EDITABLE"     , (String)procResult.get("pv_editable_o"));
    	logger.debug(Utils.log("PKG_CONSULTA.P_GET_FECHAS_ENDOSO mapa=",result));
    	return result;
	}
    
    protected class RecuperarFechasLimiteEndoso extends StoredProcedure
    {
    	protected RecuperarFechasLimiteEndoso(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CONSULTA.P_GET_FECHAS_ENDOSO");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_fechamin_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_fechamax_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_fecharef_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_editable_o" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public List<Map<String,String>> recuperarEndososRehabilitables(
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
    	Utils.debugProcedure(logger, "PKG_CANCELA.P_GET_ENDOSOS_X_POLIZA_A_REHAB", params);
    	Map<String,Object>       procResult = ejecutaSP(new RecuperarEndososRehabilitables(getDataSource()),params);
    	List<Map<String,String>> lista      = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista = new ArrayList<Map<String,String>>();
    	}
    	Utils.debugProcedure(logger, "PKG_CANCELA.P_GET_ENDOSOS_X_POLIZA_A_REHAB", params, lista);
    	return lista;
	}
    
    protected class RecuperarEndososRehabilitables extends StoredProcedure
    {
    	protected RecuperarEndososRehabilitables(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CANCELA.P_GET_ENDOSOS_X_POLIZA_A_REHAB");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            String[] cols = new String[]{
            		"NSUPLOGI"  , "CDDEVCIA" , "CDGESTOR" , "FEEMISIO" , "FEINIVAL" , "FEFINVAL"
            		,"FEEFECTO" , "FEPROREN" , "CDMONEDA" , "NMSUPLEM"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public List<Map<String,String>> recuperarEndososCancelables(
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
    	Utils.debugProcedure(logger, "PKG_CANCELA.P_GET_ENDOSOS_X_POLIZA_A_CANC", params);
    	Map<String,Object>       procResult = ejecutaSP(new RecuperarEndososCancelables(getDataSource()),params);
    	List<Map<String,String>> lista      = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista = new ArrayList<Map<String,String>>();
    	}
    	Utils.debugProcedure(logger, "PKG_CANCELA.P_GET_ENDOSOS_X_POLIZA_A_CANC", params, lista);
    	return lista;
	}
    
    protected class RecuperarEndososCancelables extends StoredProcedure
    {
    	protected RecuperarEndososCancelables(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CANCELA.P_GET_ENDOSOS_X_POLIZA_A_CANC");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            String[] cols = new String[]{
            		"NSUPLOGI"  , "CDDEVCIA" , "CDGESTOR" , "FEEMISIO" , "FEINIVAL" , "FEFINVAL"
            		,"FEEFECTO" , "FEPROREN" , "CDMONEDA" , "NMSUPLEM" , "FEINICIO"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public boolean recuperarPermisoDevolucionPrimasUsuario(String cdusuari) throws Exception
    {
    	Map<String,String> params = new LinkedHashMap<String,String>();
    	params.put("cdusuari" , cdusuari);
    	Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_PERM_DEVOL_PRI_X_USUA", params);
    	Map<String,Object> procResult = ejecutaSP(new RecuperarPermisoDevolucionPrimasUsuario(getDataSource()),params);
    	logger.debug(Utils.log("****** PKG_CONSULTA.P_GET_PERM_DEVOL_PRI_X_USUA permiso=",procResult.get("pv_permiso_o")));
    	return "S".equals((String)procResult.get("pv_permiso_o"));
    }
    
    protected class RecuperarPermisoDevolucionPrimasUsuario extends StoredProcedure
    {
    	protected RecuperarPermisoDevolucionPrimasUsuario(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CONSULTA.P_GET_PERM_DEVOL_PRI_X_USUA");
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_permiso_o" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public String recuperarValorMaximoSituacionPorRol(String cdtipsit,String cdsisrol) throws Exception
    {
    	Map<String,String> params = new LinkedHashMap<String,String>();
    	params.put("cdtipsit" , cdtipsit);
    	params.put("cdsisrol" , cdsisrol);
    	Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_VALMAX_X_ROL", params);
    	Map<String,Object> procResult = ejecutaSP(new RecuperarValorMaximoSituacionPorRol(getDataSource()),params);
    	String valor = (String)procResult.get("pv_valor_o");
    	if(valor==null)
    	{
    		valor = "9999999";
    	}
    	logger.debug(Utils.log("\n****** PKG_CONSULTA.P_GET_VALMAX_X_ROL valor=",valor));
    	return valor;
    }
    
    protected class RecuperarValorMaximoSituacionPorRol extends StoredProcedure
    {
    	protected RecuperarValorMaximoSituacionPorRol(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CONSULTA.P_GET_VALMAX_X_ROL");
            declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_valor_o"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public String obtieneSubramoGS(String cdramo,String cdtipsit) throws Exception{
    	
    	Map<String,String> params = new LinkedHashMap<String,String>();
    	params.put("pv_cdramo_i" , cdramo);
    	params.put("pv_cdtipsit_i" , cdtipsit);
    	Utils.debugProcedure(logger, "Pkg_Consulta.P_OBTIENE_SUBRAMO_X_CDTIPSIT", params);
    	Map<String,Object> procResult = ejecutaSP(new ObtieneSubramoGS(getDataSource()),params);
    	String valor = (String)procResult.get("pv_cdsubram_o");
    	logger.debug(Utils.log("\n****** Pkg_Consulta.P_OBTIENE_SUBRAMO_X_CDTIPSIT=",valor));
    	return valor;
	}
    
    protected class ObtieneSubramoGS extends StoredProcedure
    {
    	protected ObtieneSubramoGS(DataSource dataSource)
    	{
    		super(dataSource , "Pkg_Consulta.P_OBTIENE_SUBRAMO_X_CDTIPSIT");
            declareParameter(new SqlParameter("pv_cdramo_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdtipsit_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_cdsubram_o"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public String recuperarCdtipsitExtraExcel(
			int fila
			,String proc
			,String param1
			,String param2
			,String param3
			)throws Exception
    {
    	Map<String,String> params = new LinkedHashMap<String,String>();
    	params.put("param1" , param1);
    	params.put("param2" , param2);
    	params.put("param3" , param3);
    	Map<String,Object> procResult = ejecutaSP(new RecuperarCdtipsitExtraExcel(getDataSource(),proc),params);
    	logger.debug(Utils.log("\n****** procedimiento=",proc,"resultado=",procResult));
    	String valor = (String) procResult.get("pv_cdtipsit_o");
    	if(StringUtils.isBlank(valor))
    	{
    		throw new ApplicationException(
    				Utils.join("No se encuentra tipo de situacion con ",param1,", ",param2," y ",param3," en la fila ",fila)
    				);
    	}
    	return valor;
    }
    
    protected class RecuperarCdtipsitExtraExcel extends StoredProcedure
    {
    	protected RecuperarCdtipsitExtraExcel(DataSource dataSource, String proc)
    	{
    		super(dataSource , proc);
            declareParameter(new SqlParameter("param1" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("param2" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("param3" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_cdtipsit_o" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public Map<String,String>recuperarCotizacionFlotillas(String cdramo,String nmpoliza,String cdusuari,String cdsisrol) throws Exception
    {
    	Map<String,String> params = new LinkedHashMap<String,String>();
    	params.put("cdramo"   , cdramo);
    	params.put("nmpoliza" , nmpoliza);
    	params.put("cdusuari" , cdusuari);
    	params.put("cdsisrol" , cdsisrol);
    	Map<String,Object> procResult = ejecutaSP(new RecuperarCotizacionFlotillas(getDataSource()),params);
    	Map<String,String> result     = new LinkedHashMap<String,String>();
    	result.put("cdunieco" , (String)procResult.get("pv_cdunieco_o"));
    	result.put("estado"   , (String)procResult.get("pv_estado_o"));
    	result.put("nmpoliza" , (String)procResult.get("pv_nmpoliza_o"));
    	result.put("nmsuplem" , (String)procResult.get("pv_nmsuplem_o"));
    	result.put("tipoflot" , (String)procResult.get("pv_tipoflot_o"));
    	result.put("fesolici" , (String)procResult.get("pv_fesolici_o"));
    	result.put("feini"    , (String)procResult.get("pv_feini_o"));
    	result.put("fefin"    , (String)procResult.get("pv_fefin_o"));
    	logger.debug(Utils.log("\npoliza=",result));
    	return result;
    }
    
    protected class RecuperarCotizacionFlotillas extends StoredProcedure
    {
    	protected RecuperarCotizacionFlotillas(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CONSULTA.P_GET_DATOS_POLIZA_FLOTILLAS");
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_cdunieco_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_estado_o"   , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_nmpoliza_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_tipoflot_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_fesolici_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_feini_o"    , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_fefin_o"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public Map<String,List<Map<String,String>>> recuperarEstadisticasCotizacionEmision(
			Date feinicio
			,Date fefin
			,String cdunieco
			,String cdramo
			,String cdusuari
			,String cdagente
			) throws Exception
	{
    	Map<String,Object> params = new LinkedHashMap<String,Object>();
    	params.put("feinicio" , feinicio);
    	params.put("fefin"    , fefin);
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("cdagente" , cdagente);
    	params.put("cdusuari" , cdusuari);
    	Utils.debugProcedure(logger, "pkg_estadistica.pr_estadistica_4", params);
    	Map<String,Object> procResult = ejecutaSP(new RecuperarEstadisticasCotizacionEmision(getDataSource()),params);
    	
    	Map<String,List<Map<String,String>>> mapa = new HashMap<String,List<Map<String,String>>>();
    	
    	List<Map<String,String>> listaUnieco = (List<Map<String,String>>)procResult.get("pv_reg_unieco_o");
    	if(listaUnieco==null)
    	{
    		listaUnieco=new ArrayList<Map<String,String>>();
    	}
    	logger.debug(Utils.log("\nlista unieco=",listaUnieco));
    	mapa.put("unieco",listaUnieco);
    	
    	List<Map<String,String>> listaRamo = (List<Map<String,String>>)procResult.get("pv_reg_ramo_o");
    	if(listaRamo==null)
    	{
    		listaRamo=new ArrayList<Map<String,String>>();
    	}
    	logger.debug(Utils.log("\nlista ramo=",listaRamo));
    	mapa.put("ramo",listaRamo);
    	
    	List<Map<String,String>> listaUsuario = (List<Map<String,String>>)procResult.get("pv_reg_usuario_o");
    	if(listaUsuario==null)
    	{
    		listaUsuario=new ArrayList<Map<String,String>>();
    	}
    	logger.debug(Utils.log("\nlista usuario=",listaUsuario));
    	mapa.put("usuario",listaUsuario);
    	
    	List<Map<String,String>> listaAgente = (List<Map<String,String>>)procResult.get("pv_reg_agente_o");
    	if(listaAgente==null)
    	{
    		listaAgente=new ArrayList<Map<String,String>>();
    	}
    	logger.debug(Utils.log("\nlista agente=",listaAgente));
    	mapa.put("agente",listaAgente);
    	
    	return mapa;
	}
    
    protected class RecuperarEstadisticasCotizacionEmision extends StoredProcedure
    {
    	protected RecuperarEstadisticasCotizacionEmision(DataSource dataSource)
    	{
    		super(dataSource , "pkg_estadistica.pr_estadistica_4");
            declareParameter(new SqlParameter("feinicio" , OracleTypes.TIMESTAMP));
            declareParameter(new SqlParameter("fefin"    , OracleTypes.TIMESTAMP));
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdagente" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o"   , OracleTypes.CURSOR));
            declareParameter(new SqlOutParameter("pv_reg_unieco_o" , OracleTypes.CURSOR
            		,new GenericMapper(new String[]{"CDUNIECO" , "DSUNIECO" , "SUCURSAL" , "COTIZACIONES" , "EMISIONES"})));
            declareParameter(new SqlOutParameter("pv_reg_ramo_o"   , OracleTypes.CURSOR
            		,new GenericMapper(new String[]{"CDRAMO" , "DSRAMO" , "PRODUCTO" , "COTIZACIONES" , "EMISIONES"})));
            declareParameter(new SqlOutParameter("pv_reg_usuario_o"   , OracleTypes.CURSOR
            		,new GenericMapper(new String[]{"CDUSUARI" , "DSUSUARI" , "USUARIO" , "COTIZACIONES" , "EMISIONES"})));
            declareParameter(new SqlOutParameter("pv_reg_agente_o"   , OracleTypes.CURSOR
            		,new GenericMapper(new String[]{"CDAGENTE" , "NOMBRE" , "AGENTE" , "COTIZACIONES" , "EMISIONES"})));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"     , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"      , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public Map<String,List<Map<String,String>>> recuperarEstadisticasTareas(
			Date feinicio
			,Date fefin
			,String cdmodulo
			,String cdtarea
			,String cdunieco
			,String cdramo
			,String cdusuari
			,String cdsisrol
			) throws Exception
	{
    	Map<String,Object> params = new LinkedHashMap<String,Object>();
    	params.put("feinicio" , feinicio);
    	params.put("fefin"    , fefin);
    	params.put("cdmodulo" , cdmodulo);
    	params.put("cdtarea"  , cdtarea);
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("cdsisrol" , cdsisrol);
    	params.put("cdusuari" , cdusuari);
    	Utils.debugProcedure(logger, "pkg_estadistica.P_GET_ESTADISTICA_TAREAS", params);
    	Map<String,Object> procResult = ejecutaSP(new RecuperarEstadisticasTareas(getDataSource()),params);
    	
    	Map<String,List<Map<String,String>>> mapa = new HashMap<String,List<Map<String,String>>>();
    	
    	List<Map<String,String>> listaTarea = (List<Map<String,String>>)procResult.get("pv_reg_tarea_o");
    	if(listaTarea==null)
    	{
    		listaTarea=new ArrayList<Map<String,String>>();
    	}
    	logger.debug(Utils.log("\nlista tarea=",listaTarea));
    	mapa.put("tarea",listaTarea);
    	
    	List<Map<String,String>> listaUnieco = (List<Map<String,String>>)procResult.get("pv_reg_unieco_o");
    	if(listaUnieco==null)
    	{
    		listaUnieco=new ArrayList<Map<String,String>>();
    	}
    	logger.debug(Utils.log("\nlista unieco=",listaUnieco));
    	mapa.put("unieco",listaUnieco);
    	
    	List<Map<String,String>> listaRamo = (List<Map<String,String>>)procResult.get("pv_reg_ramo_o");
    	if(listaRamo==null)
    	{
    		listaRamo=new ArrayList<Map<String,String>>();
    	}
    	logger.debug(Utils.log("\nlista ramo=",listaRamo));
    	mapa.put("ramo",listaRamo);
    	
    	List<Map<String,String>> listaUsuario = (List<Map<String,String>>)procResult.get("pv_reg_usuari_o");
    	if(listaUsuario==null)
    	{
    		listaUsuario=new ArrayList<Map<String,String>>();
    	}
    	logger.debug(Utils.log("\nlista usuario=",listaUsuario));
    	mapa.put("usuario",listaUsuario);
    	
    	List<Map<String,String>> listaRol = (List<Map<String,String>>)procResult.get("pv_reg_sisrol_o");
    	if(listaRol==null)
    	{
    		listaRol=new ArrayList<Map<String,String>>();
    	}
    	logger.debug(Utils.log("\nlista rol=",listaRol));
    	mapa.put("rol",listaRol);
    	
    	return mapa;
	}
    
    protected class RecuperarEstadisticasTareas extends StoredProcedure
    {
    	protected RecuperarEstadisticasTareas(DataSource dataSource)
    	{
    		super(dataSource , "pkg_estadistica.P_GET_ESTADISTICA_TAREAS");
            declareParameter(new SqlParameter("feinicio" , OracleTypes.TIMESTAMP));
            declareParameter(new SqlParameter("fefin"    , OracleTypes.TIMESTAMP));
            declareParameter(new SqlParameter("cdmodulo" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdtarea"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_reg_tarea_o"  , OracleTypes.CURSOR
            		,new GenericMapper(new String[]{"CDTAREA" , "DSTAREA" , "TAREA" , "TODAS" , "TIEMPO" , "ESCALA"})));
            declareParameter(new SqlOutParameter("pv_reg_unieco_o" , OracleTypes.CURSOR
            		,new GenericMapper(new String[]{"CDUNIECO" , "DSUNIECO" , "SUCURSAL" , "TODAS" , "TIEMPO" , "ESCALA"})));
            declareParameter(new SqlOutParameter("pv_reg_ramo_o"   , OracleTypes.CURSOR
            		,new GenericMapper(new String[]{"CDRAMO" , "DSRAMO" , "PRODUCTO" , "TODAS" , "TIEMPO" , "ESCALA"})));
            declareParameter(new SqlOutParameter("pv_reg_sisrol_o" , OracleTypes.CURSOR
            		,new GenericMapper(new String[]{"CDSISROL" , "DSSISROL" , "ROL" , "TODAS" , "TIEMPO" , "ESCALA"})));
            declareParameter(new SqlOutParameter("pv_reg_usuari_o" , OracleTypes.CURSOR
            		,new GenericMapper(new String[]{"CDUSUARI" , "DSUSUARI" , "USUARIO" , "TODAS" , "TIEMPO" , "ESCALA"})));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"     , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"      , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public String obtieneConteoSituacionCoberturaAmparada(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdtipsit
			,String cdatribu
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsituac" , nmsituac);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdatribu" , cdatribu);
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_TATRISIT_AMPARADO", params);
		Map<String,Object> procResult = ejecutaSP(new ObtieneAtributosSituacionCoberturaAmparada(getDataSource()),params);
		String             conteo     = (String)procResult.get("pv_conteo_o");
		if(StringUtils.isBlank(conteo))
		{
			conteo="0";
		}
		logger.debug(Utils.log(
				 "\n**************************************************"
				,"\n****** PKG_CONSULTA.P_GET_TATRISIT_AMPARADO ******"
				,"\n****** params=" , params
				,"\n****** conteo=" , conteo
				,"\n**************************************************"
				));
		return conteo;
	}
	
	protected class ObtieneAtributosSituacionCoberturaAmparada extends StoredProcedure
	{
		protected ObtieneAtributosSituacionCoberturaAmparada(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TATRISIT_AMPARADO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdatribu" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_conteo_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String validacionesSuplemento(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdtipsup
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsituac" , nmsituac);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdtipsup" , cdtipsup);
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_VALIDA_SUPLEMENTO", params);
		Map<String,Object> procResult = ejecutaSP(new ValidacionesSuplemento(getDataSource()),params);
		String             error      = (String)procResult.get("pv_error_o");
		logger.debug(Utils.log(
				 "\n************************************************"
				,"\n****** params=" , params
				,"\n****** error="  , error
				,"\n****** PKG_SATELITES2.P_VALIDA_SUPLEMENTO ******"
				,"\n************************************************"
				));
		return error;
	}
	
	protected class ValidacionesSuplemento extends StoredProcedure
	{
		protected ValidacionesSuplemento(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_VALIDA_SUPLEMENTO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_error_o"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarRevisionColectivos(
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
		Map<String,Object> procResult  = ejecutaSP(new RecuperarRevisionColectivos(getDataSource()),params);
		List<Map<String,String>> lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperarRevisionColectivos extends StoredProcedure
	{
		protected RecuperarRevisionColectivos(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_REVISION_COLECTIVO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
            		"CDUNIECO"  , "CDRAMO"     , "ESTADO" , "NMPOLIZA" , "CDGRUPO"
            		,"NMSITUAC" , "PARENTESCO" , "NOMBRE" , "EDAD"     , "SEXO"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public boolean copiaDocumentosTdocupol(
			 String cduniecoOrig
			,String cdramoOrig
			,String estadoOrig
			,String nmpolizaOrig
			,String ntramiteDestino
			)throws Exception
			{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" , cduniecoOrig);
		params.put("pv_cdramo_i"   , cdramoOrig);
		params.put("pv_estado_i"   , estadoOrig);
		params.put("pv_nmpoliza_i" , nmpolizaOrig);
		params.put("pv_ntramite_i" , ntramiteDestino);
		ejecutaSP(new CopiaDocumentosTdocupol(getDataSource()),params);
		return true;
	}
	
	protected class CopiaDocumentosTdocupol extends StoredProcedure
	{
		protected CopiaDocumentosTdocupol(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_INS_TDOCUPOL_DOCTOS_USUARIO");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ntramite_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarDerechosPolizaPorPaqueteRamo1(String paquete) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("paquete" , paquete);
		Map<String,Object> procRes = ejecutaSP(new RecuperarDerechosPolizaPorPaqueteRamo1(getDataSource()),params);
		String derechos = (String) procRes.get("pv_derechos_o");
		if(StringUtils.isBlank(derechos))
		{
			throw new ApplicationException("No hay derechos parametrizados para el paquete");
		}
		logger2.debug("\nRecuperar derechos con paquete {}\nDerechos: {}" , paquete , derechos);
		return derechos;
	}
	
	protected class RecuperarDerechosPolizaPorPaqueteRamo1 extends StoredProcedure
	{
		protected RecuperarDerechosPolizaPorPaqueteRamo1(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DERPOL_X_PAQ_RAMO1");
			declareParameter(new SqlParameter("paquete" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_derechos_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public boolean validaPagoPolizaRepartido(
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
		Map<String,Object> procRes = ejecutaSP(new ValidaPagoPolizaRepartido(getDataSource()),params);
		boolean pagoRepartido = "S".equals((String)procRes.get("pv_repartido_o"));
		logger2.debug("\nPKG_CONSULTA.P_GET_SWCONTRIBUTORIO pagoRepartido: {}",pagoRepartido);
		return pagoRepartido;
	}
	
	protected class ValidaPagoPolizaRepartido extends StoredProcedure
	{
		protected ValidaPagoPolizaRepartido(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_SWCONTRIBUTORIO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_repartido_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"    , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"     , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarAtributosPorRol(String cdtipsit,String cdsisrol) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipsit" , cdtipsit);
		params.put("cdsisrol" , cdsisrol);
		Map<String,Object> procRes = ejecutaSP(new RecuperarAtributosPorRol(getDataSource()),params);
		List<Map<String,String>> lista = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperarAtributosPorRol extends StoredProcedure
	{
		protected RecuperarAtributosPorRol(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_ATRIXROL_AUTOS");
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR
					,new GenericMapper(new String[]{"CDATRIBU","APLICA","VALOR"})));
			declareParameter(new SqlOutParameter("pv_msg_id_o"    , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"     , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public boolean validaClientePideNumeroEmpleado(
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
		Map<String,Object> procRes = ejecutaSP(new ValidaClientePideNumeroEmpleado(getDataSource()),params);
		boolean pide = "S".equals((String)procRes.get("pv_swempleado_o"));
		logger2.debug("\nPKG_CONSULTA.P_VALIDA_CLIENTE_NEMP pide= {}",pide);
		return pide;
	}
	
	protected class ValidaClientePideNumeroEmpleado extends StoredProcedure
	{
		protected ValidaClientePideNumeroEmpleado(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_VALIDA_CLIENTE_NEMP");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_swempleado_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"     , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"      , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>recuperarUsuariosReasignacionTramite(String ntramite) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("ntramite" , ntramite);
		Map<String,Object> procRes = ejecutaSP(new RecuperarUsuariosReasignacionTramite(getDataSource()),params);
		List<Map<String,String>> lista = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No se encontraron usuarios");
		}
		return lista;
	}
	
	protected class RecuperarUsuariosReasignacionTramite extends StoredProcedure
	{
		protected RecuperarUsuariosReasignacionTramite(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_USUARIOS_REASIGNA");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR
					,new GenericMapper(new String[]{"CDUSUARI","NOMBRE","CDSISROL","TOTAL"})));
			declareParameter(new SqlOutParameter("pv_msg_id_o"     , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"      , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public boolean validarVentanaDocumentosBloqueada(
			String ntramite
			,String cdtiptra
			,String cdusuari
			,String cdsisrol
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("ntramite" , ntramite);
		params.put("cdtiptra" , cdtiptra);
		params.put("cdusuari" , cdusuari);
		params.put("cdsisrol" , cdsisrol);
		Map<String,Object> procRes = ejecutaSP(new ValidarVentanaDocumentosBloqueada(getDataSource()),params);
		return "S".equals((String)procRes.get("pv_swbloqueo_o"));
	}
	
	protected class ValidarVentanaDocumentosBloqueada extends StoredProcedure
	{
		protected ValidarVentanaDocumentosBloqueada(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_VALIDA_VENT_DOCS_BLOQUEADA");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtiptra" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_swbloqueo_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"    , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"     , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarMovimientosEndosoAltaBajaAsegurados(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarMovimientosEndosoAltaBajaAsegurados(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		construirClavesAtributos(lista);
		return lista;
	}
	
	protected class RecuperarMovimientosEndosoAltaBajaAsegurados extends StoredProcedure
	{
		protected RecuperarMovimientosEndosoAltaBajaAsegurados(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_MOV_ALTA_BAJA_ASEG");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
            String[] cols=new String[]{
            		//MPOLISIT
            		"CDUNIECO"    , "CDRAMO"   , "ESTADO"     , "NMPOLIZA"
            		,"NMSITUAC"   , "NMSUPLEM" , "STATUS"     , "CDTIPSIT"
            		,"SWREDUCI"   , "CDAGRUPA" , "CDESTADO"   , "FEFECSIT"
            		,"FECHAREF"   , "CDGRUPO"  , "NMSITUAEXT" , "NMSITAUX"
            		,"NMSBSITEXT" , "CDPLAN"   , "CDASEGUR"   , "DSGRUPO"
            		//TVALOSIT
            		,"NMSUPLEM_TVAL"
            		,"OTVALOR01" , "OTVALOR02" , "OTVALOR03" , "OTVALOR04" , "OTVALOR05" , "OTVALOR06" , "OTVALOR07" , "OTVALOR08" , "OTVALOR09" , "OTVALOR10"
            		,"OTVALOR11" , "OTVALOR12" , "OTVALOR13" , "OTVALOR14" , "OTVALOR15" , "OTVALOR16" , "OTVALOR17" , "OTVALOR18" , "OTVALOR19" , "OTVALOR20"
            		,"OTVALOR21" , "OTVALOR22" , "OTVALOR23" , "OTVALOR24" , "OTVALOR25" , "OTVALOR26" , "OTVALOR27" , "OTVALOR28" , "OTVALOR29" , "OTVALOR30"
            		,"OTVALOR31" , "OTVALOR32" , "OTVALOR33" , "OTVALOR34" , "OTVALOR35" , "OTVALOR36" , "OTVALOR37" , "OTVALOR38" , "OTVALOR39" , "OTVALOR40"
            		,"OTVALOR41" , "OTVALOR42" , "OTVALOR43" , "OTVALOR44" , "OTVALOR45" , "OTVALOR46" , "OTVALOR47" , "OTVALOR48" , "OTVALOR49" , "OTVALOR50"
            		,"OTVALOR51" , "OTVALOR52" , "OTVALOR53" , "OTVALOR54" , "OTVALOR55" , "OTVALOR56" , "OTVALOR57" , "OTVALOR58" , "OTVALOR59" , "OTVALOR60"
            		,"OTVALOR61" , "OTVALOR62" , "OTVALOR63" , "OTVALOR64" , "OTVALOR65" , "OTVALOR66" , "OTVALOR67" , "OTVALOR68" , "OTVALOR69" , "OTVALOR70"
            		,"OTVALOR71" , "OTVALOR72" , "OTVALOR73" , "OTVALOR74" , "OTVALOR75" , "OTVALOR76" , "OTVALOR77" , "OTVALOR78" , "OTVALOR79" , "OTVALOR80"
            		,"OTVALOR81" , "OTVALOR82" , "OTVALOR83" , "OTVALOR84" , "OTVALOR85" , "OTVALOR86" , "OTVALOR87" , "OTVALOR88" , "OTVALOR89" , "OTVALOR90"
            		,"OTVALOR91" , "OTVALOR92" , "OTVALOR93" , "OTVALOR94" , "OTVALOR95" , "OTVALOR96" , "OTVALOR97" , "OTVALOR98" , "OTVALOR99"
            		,"DSVALOR01" , "DSVALOR02" , "DSVALOR03" , "DSVALOR04" , "DSVALOR05" , "DSVALOR06" , "DSVALOR07" , "DSVALOR08" , "DSVALOR09" , "DSVALOR10"
            		,"DSVALOR11" , "DSVALOR12" , "DSVALOR13" , "DSVALOR14" , "DSVALOR15" , "DSVALOR16" , "DSVALOR17" , "DSVALOR18" , "DSVALOR19" , "DSVALOR20"
            		,"DSVALOR21" , "DSVALOR22" , "DSVALOR23" , "DSVALOR24" , "DSVALOR25" , "DSVALOR26" , "DSVALOR27" , "DSVALOR28" , "DSVALOR29" , "DSVALOR30"
            		,"DSVALOR31" , "DSVALOR32" , "DSVALOR33" , "DSVALOR34" , "DSVALOR35" , "DSVALOR36" , "DSVALOR37" , "DSVALOR38" , "DSVALOR39" , "DSVALOR40"
            		,"DSVALOR41" , "DSVALOR42" , "DSVALOR43" , "DSVALOR44" , "DSVALOR45" , "DSVALOR46" , "DSVALOR47" , "DSVALOR48" , "DSVALOR49" , "DSVALOR50"
            		,"DSVALOR51" , "DSVALOR52" , "DSVALOR53" , "DSVALOR54" , "DSVALOR55" , "DSVALOR56" , "DSVALOR57" , "DSVALOR58" , "DSVALOR59" , "DSVALOR60"
            		,"DSVALOR61" , "DSVALOR62" , "DSVALOR63" , "DSVALOR64" , "DSVALOR65" , "DSVALOR66" , "DSVALOR67" , "DSVALOR68" , "DSVALOR69" , "DSVALOR70"
            		,"DSVALOR71" , "DSVALOR72" , "DSVALOR73" , "DSVALOR74" , "DSVALOR75" , "DSVALOR76" , "DSVALOR77" , "DSVALOR78" , "DSVALOR79" , "DSVALOR80"
            		,"DSVALOR81" , "DSVALOR82" , "DSVALOR83" , "DSVALOR84" , "DSVALOR85" , "DSVALOR86" , "DSVALOR87" , "DSVALOR88" , "DSVALOR89" , "DSVALOR90"
            		,"DSVALOR91" , "DSVALOR92" , "DSVALOR93" , "DSVALOR94" , "DSVALOR95" , "DSVALOR96" , "DSVALOR97" , "DSVALOR98" , "DSVALOR99"
            		//MPERSONA
            		,"CDPERSON"   , "CDTIPIDE"    , "CDIDEPER" , "DSNOMBRE"
            		,"CDTIPPER"   , "OTFISJUR"    , "OTSEXO"   , "FENACIMI"
            		,"CDRFC"      , "FOTO"        , "DSEMAIL"  , "DSNOMBRE1"
            		,"DSAPELLIDO" , "DSAPELLIDO1" , "CDNACION" , "DSCOMNOM"
            		,"DSRAZSOC"   , "FEINGRESO"   , "FEACTUAL" , "DSNOMUSU"
            		,"CDESTCIV"   , "CDGRUECO"    , "CDSTIPPE" , "NMNUMNOM"
            		,"CURP"       , "CANALING"    , "CONDUCTO" , "PTCUMUPR"
            		,"STATUSPER"  , "RESIDENCIA"  , "NONGRATA" , "CDIDEEXT"
            		,"CDSUCEMI"
            		//MPOLIPER
            		,"CDROL" , "NMORDDOM" , "SWRECLAM" , "SWEXIPER" , "CDPARENT" , "PORBENEF"
            		//TATRISIT:
            		,"DSATRIBU01" , "DSATRIBU02" , "DSATRIBU03" , "DSATRIBU04" , "DSATRIBU05" , "DSATRIBU06" , "DSATRIBU07" , "DSATRIBU08" , "DSATRIBU09" , "DSATRIBU10"
            		,"DSATRIBU11" , "DSATRIBU12" , "DSATRIBU13" , "DSATRIBU14" , "DSATRIBU15" , "DSATRIBU16" , "DSATRIBU17" , "DSATRIBU18" , "DSATRIBU19" , "DSATRIBU20"
            		,"DSATRIBU21" , "DSATRIBU22" , "DSATRIBU23" , "DSATRIBU24" , "DSATRIBU25" , "DSATRIBU26" , "DSATRIBU27" , "DSATRIBU28" , "DSATRIBU29" , "DSATRIBU30"
            		,"DSATRIBU31" , "DSATRIBU32" , "DSATRIBU33" , "DSATRIBU34" , "DSATRIBU35" , "DSATRIBU36" , "DSATRIBU37" , "DSATRIBU38" , "DSATRIBU39" , "DSATRIBU40"
            		,"DSATRIBU41" , "DSATRIBU42" , "DSATRIBU43" , "DSATRIBU44" , "DSATRIBU45" , "DSATRIBU46" , "DSATRIBU47" , "DSATRIBU48" , "DSATRIBU49" , "DSATRIBU50"
            		,"DSATRIBU51" , "DSATRIBU52" , "DSATRIBU53" , "DSATRIBU54" , "DSATRIBU55" , "DSATRIBU56" , "DSATRIBU57" , "DSATRIBU58" , "DSATRIBU59" , "DSATRIBU60"
            		,"DSATRIBU61" , "DSATRIBU62" , "DSATRIBU63" , "DSATRIBU64" , "DSATRIBU65" , "DSATRIBU66" , "DSATRIBU67" , "DSATRIBU68" , "DSATRIBU69" , "DSATRIBU70"
            		,"DSATRIBU71" , "DSATRIBU72" , "DSATRIBU73" , "DSATRIBU74" , "DSATRIBU75" , "DSATRIBU76" , "DSATRIBU77" , "DSATRIBU78" , "DSATRIBU79" , "DSATRIBU80"
            		,"DSATRIBU81" , "DSATRIBU82" , "DSATRIBU83" , "DSATRIBU84" , "DSATRIBU85" , "DSATRIBU86" , "DSATRIBU87" , "DSATRIBU88" , "DSATRIBU89" , "DSATRIBU90"
            		,"DSATRIBU91" , "DSATRIBU92" , "DSATRIBU93" , "DSATRIBU94" , "DSATRIBU95" , "DSATRIBU96" , "DSATRIBU97" , "DSATRIBU98" , "DSATRIBU99"
            		//MPLANES
            		,"DSPLAN"
            		//MOVIMIENTOS
            		,"MOV"
    	            };
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	private void construirClavesAtributos(List<Map<String,String>>lista)
	{
		for (Map<String,String> map : lista)
		{
			for(int i=1;i<=99;i++)
			{
				String dsatribu = map.get("DSATRIBU"+StringUtils.leftPad(String.valueOf(i), 2, "0"));
				if(StringUtils.isNotBlank(dsatribu))
				{
					map.put("CVE_"+dsatribu, map.get("OTVALOR"+StringUtils.leftPad(String.valueOf(i), 2, "0")));
					map.put("DES_"+dsatribu, map.get("DSVALOR"+StringUtils.leftPad(String.valueOf(i), 2, "0")));
				}
			}
		}
	}
	
	@Override
	public String recuperarConteoTbloqueo(
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
		Map<String,Object> procRes = ejecutaSP(new RecuperarConteoTbloqueo(getDataSource()),params);
		String             conteo  = (String)procRes.get("pv_conteo_o");
		if(StringUtils.isBlank(conteo))
		{
			conteo = "0";
		}
		return conteo;
	}
	
	protected class RecuperarConteoTbloqueo extends StoredProcedure
	{
		protected RecuperarConteoTbloqueo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_COUNT_TBLOQUEO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_conteo_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	public Map<String,String> consultaFeNacContratanteAuto(Map<String,String> params)throws Exception{
		Map<String,String> fechas = null;
		
		Map<String,Object>procResult  = ejecutaSP(new ConsultaFeNacContratanteAuto(getDataSource()), params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		
		if(lista != null && !lista.isEmpty()){
			fechas =  lista.get(0);
		}else{
			fechas =  new HashMap<String, String>();
			fechas.put("APLICA", "N");
			fechas.put("FECHAMIN", "");
			fechas.put("FECHAMAX", "");
		}
		
		return fechas;
	}
	
	protected class ConsultaFeNacContratanteAuto extends StoredProcedure
	{
		protected ConsultaFeNacContratanteAuto(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_OBT_RANGOS_FECNAC");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			 String[] cols = new String[]{
	            		"APLICA"  , "FECHAMIN"   , "FECHAMAX"
            };
	        
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarSubramos(String cdramo) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdramo" , cdramo);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarSubramos(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperarSubramos extends StoredProcedure
	{
		protected RecuperarSubramos(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_OBT_RAMOS_X_PROD");
			declareParameter(new SqlParameter("cdramo" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"CDSUBRAM"  , "DESCRIPCION"
            };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarTparagen(ParametroGeneral paragen) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("nomparam" , paragen.getNomparam());
		Map<String,Object>       procRes = ejecutaSP(new RecuperarTparagen(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException(Utils.join("No existe el parametro general ",paragen.toString()));
		}
		if(lista.size()>1)
		{
			throw new ApplicationException(Utils.join("Parametro general ",paragen.toString()," repetido"));
		}
		String val = lista.get(0).get("VALPARAM");
		logger.debug(Utils.join("\n****** PKG_CONSULTA.P_OBTIENE_TPARAGEN ",paragen.getNomparam()," = ",val," ******"));
		return val;
	}
	
	protected class RecuperarTparagen extends StoredProcedure
	{
		protected RecuperarTparagen(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_OBTIENE_TPARAGEN");
			declareParameter(new SqlParameter("nomparam" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(new String[]{"VALPARAM"})));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarTiposRamo() throws Exception
	{
		Map<String,Object>       procRes = ejecutaSP(new RecuperarTiposRamo(getDataSource()),new LinkedHashMap<String,String>());
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperarTiposRamo extends StoredProcedure
	{
		protected RecuperarTiposRamo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_OBTENER_TIPOS_RAMO");
			String[] cols = new String[]{
					"CDTIPRAM"  , "DSTIPRAM"
            };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarRamosPorTipoRamo(String cdtipram) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipram" , cdtipram);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarRamosPorTipoRamo(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperarRamosPorTipoRamo extends StoredProcedure
	{
		protected RecuperarRamosPorTipoRamo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_OBT_RAMOS_X_CDTIPRAM");
			declareParameter(new SqlParameter("cdtipram" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"CDRAMO"  , "DSRAMO"
            };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarSucursalesPorTipoRamo(String cdtipram) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipram" , cdtipram);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarSucursalesPorTipoRamo(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperarSucursalesPorTipoRamo extends StoredProcedure
	{
		protected RecuperarSucursalesPorTipoRamo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_SUCURSALES_X_CDTIPRAM");
			declareParameter(new SqlParameter("cdtipram" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"CDUNIECO"  , "DSUNIECO"
            };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarPolizasParaImprimir(
			String cdtipram
			,String cduniecos
			,String cdramo
			,String ramo
			,String nmpoliza
			,Date fecha
			,String cdusuariLike
			,String cdagente
			,String cdusuariSesion
			,String cduniecoSesion
			)throws Exception
	{
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("cdtipram"       , cdtipram);
		params.put("cduniecos"      , cduniecos);
		params.put("cdramo"         , cdramo);
		params.put("ramo"           , ramo);
		params.put("nmpoliza"       , nmpoliza);
		params.put("fecha"          , fecha);
		params.put("cdusuari"       , cdusuariLike);
		params.put("cdagente"       , cdagente);
		params.put("cdusuariSesion" , cdusuariSesion);
		params.put("cduniecoSesion" , cduniecoSesion);
		Map<String,Object> procRes = ejecutaSP(new RecuperarPolizasParaImprimir(getDataSource()),params);
		List<Map<String,String>> lista = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utils.log("\n****** PKG_CONSULTA.P_GET_POLIZAS_PARA_IMPRIMIR lista: ",lista.size()," ******"));
		return lista;
	}
	
	protected class RecuperarPolizasParaImprimir extends StoredProcedure
	{
		protected RecuperarPolizasParaImprimir(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_POLIZAS_PARA_IMPRIMIR");
			declareParameter(new SqlParameter("cdtipram"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cduniecos"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"         , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ramo"           , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("fecha"          , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdusuari"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdagente"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuariSesion" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cduniecoSesion" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"cdtipram"
					,"dstipram"
					,"cdunieco"
					,"dsunieco"
					,"cdramo"
					,"dsramo"
					,"estado"
					,"nmpoliza"
					,"nmsuplem"
					,"cdtipsup"
					,"dstipsup"
					,"nsuplogi"
					,"cdgestor"
					,"cddevcia"
					,"feinival"
					,"ntramite"
					,"dssuplog"
					,"ramo"
					,"cdusuari"
					,"cdagente"
					,"dsagente"
					,"nmpoliex"
					,"numincisos"
					,"tiempoimp"
					,"tipoflot"
            };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarUltimoNmsuplem(
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
		Map<String,Object> procRes  = ejecutaSP(new RecuperarUltimoNmsuplem(getDataSource()),params);
		String             nmsuplem = (String)procRes.get("pv_nmsuplem_o");
		if(StringUtils.isBlank(nmsuplem))
		{
			throw new ApplicationException("No se encuentra el suplemento reciente");
		}
		return nmsuplem;
	}
	
	protected class RecuperarUltimoNmsuplem extends StoredProcedure
	{
		protected RecuperarUltimoNmsuplem(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_ULTIMO_NMSUPLEM");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarSecuenciaLote() throws Exception
	{
		Map<String,Object> procRes = ejecutaSP(new RecuperarSecuenciaLote(getDataSource()),new HashMap<String,String>());
		String             lote    = (String)procRes.get("pv_seqlote_o");
		if(StringUtils.isBlank(lote))
		{
			throw new ApplicationException("Error al generar la secuencia de lote");
		}
		return lote;
	}
	
	protected class RecuperarSecuenciaLote extends StoredProcedure
	{
		protected RecuperarSecuenciaLote(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_SEQLOTE");
			declareParameter(new SqlOutParameter("pv_seqlote_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarImpresionesDisponiblesPorTipoRamo(
			String cdtipram
			,String tipolote
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipram" , cdtipram);
		params.put("tipolote" , tipolote);
		Map<String,Object> procRes = ejecutaSP(new RecuperarImpresionesDisponiblesPorTipoRamo(getDataSource()),params);
		String             impdis  = (String)procRes.get("pv_impdis_o");
		if(StringUtils.isBlank(impdis))
		{
			throw new ApplicationException("Error al recuperar impresiones disponibles");
		}
		return impdis;
	}
	
	protected class RecuperarImpresionesDisponiblesPorTipoRamo extends StoredProcedure
	{
		protected RecuperarImpresionesDisponiblesPorTipoRamo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_IMP_DISP_X_CDTIPRAM");
			declareParameter(new SqlParameter("cdtipram" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipolote" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_impdis_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>recuperarDetalleImpresionLote(String lote) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("lote" , lote);
		Map<String,Object> procRes    = ejecutaSP(new RecuperarDetalleImpresionLote(getDataSource()),params);
		String             requeridas = (String)procRes.get("pv_permiso_o");
		String             ejecutadas = (String)procRes.get("pv_suma_o");
		if(StringUtils.isBlank(requeridas)||StringUtils.isBlank(ejecutadas))
		{
			throw new ApplicationException("No se recuper\u00F3 el detalle de impresi\u00F3n de lote");
		}
		Map<String,String> result = new HashMap<String,String>();
		result.put("requeridas" , requeridas);
		result.put("ejecutadas" , ejecutadas);
		logger2.debug("****** PKG_CONSULTA.P_GET_DET_IMP_LOTE salida: {}",result);
		return result;
	}
	
	protected class RecuperarDetalleImpresionLote extends StoredProcedure
	{
		protected RecuperarDetalleImpresionLote(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DET_IMP_LOTE");
			declareParameter(new SqlParameter("lote" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_permiso_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_suma_o"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarImpresorasPorPapelYSucursal(
			String cdunieco
			,String papel
			,String activo
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("papel"    , papel);
		params.put("activo"   , activo);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarImpresorasPorPapelYSucursal(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utils.log("****** PKG_CONSULTA.P_GET_IMPRESORAS lista=",lista));
		return lista;
	}
	
	protected class RecuperarImpresorasPorPapelYSucursal extends StoredProcedure
	{
		protected RecuperarImpresorasPorPapelYSucursal(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_IMPRESORAS");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("papel"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("activo"   , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"cdunieco"
					,"ip"
					,"nmimpres"
					,"nombre"
					,"descrip"
					,"swactivo"
					,"charola1"
					,"charola2"
            };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarComboUsuarios(String cadena) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cadena" , cadena);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarComboUsuarios(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperarComboUsuarios extends StoredProcedure
	{
		protected RecuperarComboUsuarios(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_USUARIOS");
			declareParameter(new SqlParameter("cadena" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"cdusuari"
					,"nombre"
					,"cdunieco"
            };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String,String>> recuperarConfigImpresionSucursales(String cdusuari, String cdunieco, String cdtipram) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdusuari" , cdusuari);
		params.put("cdunieco" , cdunieco);
		params.put("cdtipram" , cdtipram);
		params.put("swaplica" , null);
		Map<String,Object>      procRes = ejecutaSP(new RecuperarConfigImpresionSucursales(getDataSource()),params);
		List<Map<String,String>> lista  = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utils.log("****** PKG_CONSULTA.P_GET_TCNFIMPINCEXCSUC lista=",lista));
		return lista;
	}
	
	protected class RecuperarConfigImpresionSucursales extends StoredProcedure
	{
		protected RecuperarConfigImpresionSucursales(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TCNFIMPINCEXCSUC");
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipram" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swaplica" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"COD_USUARIO"
					,"SUC_USUARIO"
					,"TIPO_RAMO"
					,"SUC_PERMITIDA"
					,"SWAPLICA"
					,"DESCRIP"
            };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String,String>> recuperarConfigImpresionAgentes(String cdusuari, String cdunieco, String cdtipram) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdusuari" , cdusuari);
		params.put("cdunieco" , cdunieco);
		params.put("cdtipram" , cdtipram);
		params.put("swaplica" , null);
		Map<String,Object>      procRes = ejecutaSP(new RecuperarConfigImpresionAgentes(getDataSource()),params);
		List<Map<String,String>> lista  = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utils.log("****** PKG_CONSULTA.P_GET_TCNFIMPINCEXCAGT lista=",lista));
		return lista;
	}
	
	protected class RecuperarConfigImpresionAgentes extends StoredProcedure
	{
		protected RecuperarConfigImpresionAgentes(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TCNFIMPINCEXCAGT");
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipram" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swaplica" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"COD_USUARIO"
					,"SUC_USUARIO"
					,"TIPO_RAMO"
					,"AGENTE"
					,"SWAPLICA"
					,"DESCRIP"
            };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movPermisoImpresionSucursal(
			String cdusuari
			,String cdunieco
			,String cdtipram
			,String cduniecoPer
			,String swaplica
			,String accion
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdusuari"    , cdusuari);
		params.put("cdunieco"    , cdunieco);
		params.put("cdtipram"    , cdtipram);
		params.put("cduniecoPer" , cduniecoPer);
		params.put("swaplica"    , swaplica);
		params.put("accion"      , accion);
		ejecutaSP(new MovPermisoImpresionSucursal(getDataSource()),params);
	}
	
	protected class MovPermisoImpresionSucursal extends StoredProcedure
	{
		protected MovPermisoImpresionSucursal(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_MOV_TCNFIMPINCEXCSUC");
			declareParameter(new SqlParameter("cdusuari"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipram"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cduniecoPer" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swaplica"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"      , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movPermisoImpresionAgente(
			String cdusuari
			,String cdunieco
			,String cdtipram
			,String cdagentePer
			,String swaplica
			,String accion
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdusuari"    , cdusuari);
		params.put("cdunieco"    , cdunieco);
		params.put("cdtipram"    , cdtipram);
		params.put("cdagentePer" , cdagentePer);
		params.put("swaplica"    , swaplica);
		params.put("accion"      , accion);
		ejecutaSP(new MovPermisoImpresionAgente(getDataSource()),params);
	}
	
	protected class MovPermisoImpresionAgente extends StoredProcedure
	{
		protected MovPermisoImpresionAgente(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_MOV_TCNFIMPINCEXCAGT");
			declareParameter(new SqlParameter("cdusuari"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipram"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdagentePer" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swaplica"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"      , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarRecibosLote(
			String cdtipram
			,String cduniecos
			,Date feinicio
			,Date fefin
			,String cdusuari
			,String cdunieco
			)throws Exception
	{
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("cdtipram"  , cdtipram);
		params.put("cduniecos" , cduniecos);
		params.put("feinicio"  , feinicio);
		params.put("fefin"     , fefin);
		params.put("cdusuari"  , cdusuari);
		params.put("cdunieco"  , cdunieco);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarRecibosLote(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utils.log("****** PKG_CONSULTA.P_GET_RECIBOS_PARA_HABILITAR lista=",lista));
		return lista;
	}
	
	protected class RecuperarRecibosLote extends StoredProcedure
	{
		protected RecuperarRecibosLote(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_RECIBOS_PARA_HABILITAR");
			declareParameter(new SqlParameter("cdtipram"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cduniecos" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feinicio"  , OracleTypes.DATE));
			declareParameter(new SqlParameter("fefin"     , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdusuari"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"cdunieco"
					,"dsunieco"
					,"cdramo"
					,"dsramo"
					,"estado"
					,"nmpoliza"
					,"nmsuplem"
					,"nmrecibo"
					,"cdgestor"
					,"cddevcia"
					,"primatot"
					,"feemisio"
					,"feinicio"
					,"fefinal"
					,"nmimpres"
					,"cdagente"
					,"cdtipram"
					,"ramo"
					,"dsagente"
					,"nmpoliex"
            };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarDetalleRemesa(String ntramite, String tipolote) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("ntramite" , ntramite);
		params.put("tipolote" , tipolote);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarDetalleRemesa(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utils.log("****** PKG_CONSULTA.P_GET_DETALLE_REMESAS lista=",lista));
		return lista;
	}
	
	protected class RecuperarDetalleRemesa extends StoredProcedure
	{
		protected RecuperarDetalleRemesa(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DETALLE_REMESAS");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipolote" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"ntramite"
					,"cdunieco"
					,"cdramo"
					,"estado"
					,"nmpoliza"
					,"nmsuplem"
					,"tipotram"
					,"nmtraope"
					,"nmrecibo"
					,"cddevcia"
					,"cdgestor"
					,"nmimpres"
					,"ptimport"
					,"cdagente"
					,"nombagte"
					,"nsuplogi"
					,"descrip"
            };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarArchivosParaImprimirLote(
			String lote
			,String papel
			,String tipolote
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("lote"     , lote);
		params.put("papel"    , papel);
		params.put("tipolote" , tipolote);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarArchivosParaImprimirLote(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utils.log("****** PKG_CONSULTA.P_GET_DOCUMENTOS_X_LOTE lista=",lista));
		return lista;
	}
	
	protected class RecuperarArchivosParaImprimirLote extends StoredProcedure
	{
		protected RecuperarArchivosParaImprimirLote(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DOCUMENTOS_X_LOTE");
			declareParameter(new SqlParameter("lote"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("papel"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipolote" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"cdagente"
					,"ordenimp"
					,"cdsubram"
					,"tipend"
					,"nmpoliza"
					,"cddocume"
					,"dsdocume"
					,"nmcopias"
					,"nmordina"
					,"ntramite"
					,"tipodoc"
					,"remesa"
            };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String> recuperarDatosPolizaParaDocumentos(
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
		Map<String,Object> procRes  = ejecutaSP(new RecuperarDatosPolizaParaDocumentos(getDataSource()),params);
		Map<String,String> result   = new HashMap<String,String>();
		String             ntramite = (String)procRes.get("pv_ntramite_o");
		String             nmsolici = (String)procRes.get("pv_nmsolici_o");
		if(StringUtils.isBlank(ntramite))
		{
			throw new ApplicationException("No hay tramite de emision");
		}
		if(StringUtils.isBlank(ntramite))
		{
			throw new ApplicationException("No hay solicitud");
		}
		result.put("ntramite" , ntramite);
		result.put("nmsolici" , nmsolici);
		logger.debug(Utils.log("****** PKG_CONSULTA.P_RECUPERA_DATOS_EMI result=",result));
		return result;
	}
	
	protected class RecuperarDatosPolizaParaDocumentos extends StoredProcedure
	{
		protected RecuperarDatosPolizaParaDocumentos(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_RECUPERA_DATOS_EMI");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmsolici_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_ntramite_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarTipoRamoPorCdramo(String cdramo) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdramo" , cdramo);
		Map<String,Object> procRes  = ejecutaSP(new RecuperarTipoRamoPorCdramo(getDataSource()),params);
		String             cdtipram = (String)procRes.get("pv_cdtipram_o");
		if(StringUtils.isBlank(cdtipram))
		{
			throw new ApplicationException(Utils.join("Error al recuperar el tipo de ramo para el ramo ",cdramo));
		}
		logger.debug(Utils.log("\n****** PKG_CONSULTA.P_GET_CDTIPRAM_X_CDRAMO cdtipram=",cdtipram," ******"));
		return cdtipram;
	}
	
	protected class RecuperarTipoRamoPorCdramo extends StoredProcedure
	{
		protected RecuperarTipoRamoPorCdramo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_CDTIPRAM_X_CDRAMO");
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdtipram_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarTramitePorNmsuplem(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		Map<String,Object> procRes  = ejecutaSP(new RecuperarTramitePorNmsuplem(getDataSource()),params);
		String             ntramite = (String)procRes.get("pv_ntramite_o");
		if(StringUtils.isBlank(ntramite))
		{
			throw new ApplicationException("No se puedo recuperar el tr\u00e1mite");
		}
		logger.debug(Utils.log("\n****** PKG_CONSULTA.P_GET_TRAMITE_X_NMSUPLEM ntramite=",ntramite," ******"));
		return ntramite;
	}
	
	protected class RecuperarTramitePorNmsuplem extends StoredProcedure
	{
		protected RecuperarTramitePorNmsuplem(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TRAMITE_X_NMSUPLEM");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_ntramite_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String> recuperarRemesaEmisionEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String ntramite
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("ntramite" , ntramite);
		Map<String,Object> procRes  = ejecutaSP(new VerificarRemesaEmisionEndosoAnterior(getDataSource()),params);
		String             lote     = (String)procRes.get("pv_lote_o");
		String             remesa   = (String)procRes.get("pv_remesa_o");
		String             cdtipimp = (String)procRes.get("pv_cdtipimp_o");
		Map<String,String> datos    = null;
		if(StringUtils.isNotBlank(lote)&&StringUtils.isNotBlank(remesa))
		{
			datos = new HashMap<String,String>();
			datos.put("lote"     , lote);
			datos.put("remesa"   , remesa);
			datos.put("cdtipimp" , cdtipimp);
		}
		logger.debug(Utils.log("\n****** PKG_CONSULTA.P_GET_DATOS_REMESA_UNICA datos=",datos));
		return datos;
	}
	
	protected class VerificarRemesaEmisionEndosoAnterior extends StoredProcedure
	{
		protected VerificarRemesaEmisionEndosoAnterior(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_REMESA_UNICA");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_lote_o"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_remesa_o"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdtipimp_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarDstipsupPorCdtipsup(String cdtipsup) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipsup" , cdtipsup);
		Map<String,Object> procRes  = ejecutaSP(new RecuperarDstipsupPorCdtipsup(getDataSource()),params);
		String             dstipsup = (String)procRes.get("pv_dstipsup_o");
		if(StringUtils.isBlank(dstipsup))
		{
			throw new ApplicationException(Utils.join("No hay nombre de suplemento para clave ",cdtipsup));
		}
		return dstipsup;
	}
	
	protected class RecuperarDstipsupPorCdtipsup extends StoredProcedure
	{
		protected RecuperarDstipsupPorCdtipsup(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DSTIPSUP_X_CDTIPSUP");
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_dstipsup_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarSucursalesPermisoImpresion(
			String cdtipram
			,String cdusuari
			,String cdunieco
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdtipram" , cdtipram);
		params.put("cdusuari" , cdusuari);
		params.put("cdunieco" , cdunieco);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarSucursalesPermisoImpresion(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class RecuperarSucursalesPermisoImpresion extends StoredProcedure
	{
		protected RecuperarSucursalesPermisoImpresion(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_SUCURSALES_PARA_IMPRIMIR");
			declareParameter(new SqlParameter("cdtipram" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			String[] cols = new String[]{ "cdunieco" , "dsunieco" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String,String>> recuperarConfigImpresionUsuarios(String cdusuari, String cdunieco, String cdtipram) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdusuari" , cdusuari);
		params.put("cdunieco" , cdunieco);
		params.put("cdtipram" , cdtipram);
		params.put("swaplica" , null);
		Map<String,Object>      procRes = ejecutaSP(new RecuperarConfigImpresionUsuarios(getDataSource()),params);
		List<Map<String,String>> lista  = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utils.log("****** PKG_CONSULTA.P_GET_TCNFIMPINCEXCAGT lista=",lista));
		return lista;
	}
	
	protected class RecuperarConfigImpresionUsuarios extends StoredProcedure
	{
		protected RecuperarConfigImpresionUsuarios(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TCNFIMPINCEXCUSR");
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipram" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swaplica" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"COD_USUARIO"
					,"SUC_USUARIO"
					,"TIPO_RAMO"
					,"CDUSUARI_PERMISO"
					,"SWAPLICA"
					,"DESCRIP"
            };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movPermisoImpresionUsuario(
			String cdusuari
			,String cdunieco
			,String cdtipram
			,String cdusuariPer
			,String swaplica
			,String accion
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdusuari"    , cdusuari);
		params.put("cdunieco"    , cdunieco);
		params.put("cdtipram"    , cdtipram);
		params.put("cdusuariPer" , cdusuariPer);
		params.put("swaplica"    , swaplica);
		params.put("accion"      , accion);
		ejecutaSP(new MovPermisoImpresionUsuario(getDataSource()),params);
	}
	
	protected class MovPermisoImpresionUsuario extends StoredProcedure
	{
		protected MovPermisoImpresionUsuario(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_MOV_TCNFIMPINCEXCUSR");
			declareParameter(new SqlParameter("cdusuari"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipram"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuariPer" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swaplica"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"      , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarRolesTodos() throws Exception
	{
		Map<String,Object>       procRes = ejecutaSP(new RecuperarRolesTodosSP(getDataSource()),new HashMap<String,String>());
		List<Map<String,String>> roles   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(roles==null)
		{
			roles = new ArrayList<Map<String,String>>();
		}
		return roles;
	}
	
	protected class RecuperarRolesTodosSP extends StoredProcedure
	{
		protected RecuperarRolesTodosSP(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TODOS_ROLES");
			String[] cols = new String[]{
					"CDSISROL"
					,"DSSISROL"
            };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String,String>> obtieneBeneficiariosPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
	) throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		
		Map<String,Object>       procRes = ejecutaSP(new ObtieneBeneficiariosPoliza(getDataSource()),params);
		List<Map<String,String>> roles   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(roles==null)
		{
			roles = new ArrayList<Map<String,String>>();
		}
		return roles;
	}
	
	protected class ObtieneBeneficiariosPoliza extends StoredProcedure
	{
		protected ObtieneBeneficiariosPoliza(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_WS_BENEF_AUTOS_COB_VIDA");
			
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"NOMBRE"
					,"APEPAT"
					,"APEMAT"
					,"IDPARENTESCO"
					,"NUMCER"
					,"PORCENTAJE"
					,"TEXTO"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String> recuperarDatosFlujoEmision(String cdramo, String tipoflot) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdramo"   , cdramo);
		params.put("tipoflot" , tipoflot);
		Map<String,Object> procRes = ejecutaSP(new RecuperarDatosFlujoEmisionSP(getDataSource()),params);
		Map<String,String> result  = new HashMap<String,String>();
		result.put("cdtipflu"  , (String)procRes.get("pv_cdtipflu_o"));
		result.put("cdflujomc" , (String)procRes.get("pv_cdflujomc_o"));
		return result;
	}
	
	protected class RecuperarDatosFlujoEmisionSP extends StoredProcedure
	{
		protected RecuperarDatosFlujoEmisionSP(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_FLUJO_EMI");
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("tipoflot" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdtipflu_o"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdflujomc_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarDias(String cdtipsit, String cdsisrol) throws Exception
	{logger.debug(Utils.log("VILS >>> ",cdtipsit,"/",cdsisrol));
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("pv_cdtipsit_i" , cdtipsit);
		params.put("pv_cdsisrol_i" , cdsisrol);
		Map<String,Object> procRes  = ejecutaSP(new RecuperarDias(getDataSource()),params);
		String dias = (String)procRes.get("pv_rangofec_o");
		logger.debug(Utils.log("VILS dias >>> ",dias));
		if(StringUtils.isBlank(dias))
		{
			throw new ApplicationException(Utils.join("Error al consultar los dias para la fecha de facturacion ",cdtipsit,"/",cdsisrol));
		}
		return dias;
	}
	
	
	protected class RecuperarDias extends StoredProcedure
	{ 
		protected RecuperarDias(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA. P_GET_RANGO_FECHA_FACTURA");
			declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsisrol_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_rangofec_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
			
		}
	}
}