package mx.com.gseguros.portal.consultas.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
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
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.Utilerias;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class ConsultasDAOImpl extends AbstractManagerDAO implements ConsultasDAO
{
	private static final Logger logger = Logger.getLogger(ConsultasDAOImpl.class);
	
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
						map.put(col,Utilerias.formateaFecha(rs.getString(col)));
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
		logger.debug(Utilerias.join(
				 "\n*****************************************************"
				,"\n****** PKG_DESARROLLO.P_GET_MPOLIZAS_X_PAR_VAR ******"
				,"\n****** params=",params
				,"\n*****************************************************"
				));
		Map<String,Object>procResult  = ejecutaSP(new CargarMpolizasPorParametrosVariables(getDataSource()), params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utilerias.join(
				 "\n*****************************************************"
				,"\n****** params="   , params
				,"\n****** registro=" , lista
				,"\n****** PKG_DESARROLLO.P_GET_MPOLIZAS_X_PAR_VAR ******"
				,"\n*****************************************************"
				));
		return lista;
	}
	
	protected class CargarMpolizasPorParametrosVariables extends StoredProcedure
    {
    	protected CargarMpolizasPorParametrosVariables(DataSource dataSource)
        {
            super(dataSource,"PKG_DESARROLLO.P_GET_MPOLIZAS_X_PAR_VAR");
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
		logger.debug(Utilerias.join(
				 "\n*********************************************"
				,"\n****** PKG_DESARROLLO.P_GET_TCONVALSIT ******"
				,"\n****** params=",params
				,"\n*********************************************"
				));
		Map<String,Object>procResult  = ejecutaSP(new CargarTconvalsit(getDataSource()), params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utilerias.join(
				 "\n*********************************************"
				,"\n****** params="   , params
				,"\n****** registro=" , lista
				,"\n****** PKG_DESARROLLO.P_GET_TCONVALSIT ******"
				,"\n*********************************************"
				));
		return lista;
	}
	
	protected class CargarTconvalsit extends StoredProcedure
    {
    	protected CargarTconvalsit(DataSource dataSource)
        {
            super(dataSource,"PKG_DESARROLLO.P_GET_TCONVALSIT");
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
		logger.debug(Utilerias.join(
				 "\n*********************************************"
				,"\n****** PKG_DESARROLLO.P_GET_TBASVALSIT ******"
				,"\n****** params=",params
				,"\n*********************************************"
				));
		Map<String,Object>procResult  = ejecutaSP(new CargarTbasvalsit(getDataSource()), params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug(Utilerias.join(
				 "\n*********************************************"
				,"\n****** params="   , params
				,"\n****** registro=" , lista
				,"\n****** PKG_DESARROLLO.P_GET_TBASVALSIT ******"
				,"\n*********************************************"
				));
		return lista;
	}
	
	protected class CargarTbasvalsit extends StoredProcedure
    {
    	protected CargarTbasvalsit(DataSource dataSource)
        {
            super(dataSource,"PKG_DESARROLLO.P_GET_TBASVALSIT");
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
		logger.debug(Utilerias.join(
				 "\n**************************************************"
				,"\n****** PKG_DESARROLLO.P_GET_MPOLIPER_SITUAC ******"
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
		logger.debug(Utilerias.join(
				 "\n**************************************************"
				,"\n****** params="   , params
				,"\n****** registro=" , mpoliper
				,"\n****** PKG_DESARROLLO.P_GET_MPOLIPER_SITUAC ******"
				,"\n**************************************************"
				));
		return mpoliper;
	}
	
	protected class CargarMpoliperSituac extends StoredProcedure
    {
    	protected CargarMpoliperSituac(DataSource dataSource)
        {
            super(dataSource,"PKG_DESARROLLO.P_GET_MPOLIPER_SITUAC");
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
		logger.debug(Utilerias.join(
				 "\n**************************************************"
				,"\n****** PKG_DESARROLLO.P_GET_MPOLISIT_SITUAC ******"
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
		logger.debug(Utilerias.join(
				 "\n**************************************************"
				,"\n****** params="   , params
				,"\n****** registro=" , mpoliper
				,"\n****** PKG_DESARROLLO.P_GET_MPOLISIT_SITUAC ******"
				,"\n**************************************************"
				));
		return mpoliper;
	}
	
	protected class CargarMpolisitSituac extends StoredProcedure
    {
    	protected CargarMpolisitSituac(DataSource dataSource)
        {
            super(dataSource,"PKG_DESARROLLO.P_GET_MPOLISIT_SITUAC");
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
		Utilerias.debugProcedure(logger, "PKG_DESARROLLO.P_GET_TVALOSIT", params);
		Map<String,Object>procResult  = ejecutaSP(new CargarTvalosit(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		Utilerias.debugProcedure(logger, "PKG_DESARROLLO.P_GET_TVALOSIT", params,lista);
		return lista;
	}
	
	protected class CargarTvalosit extends StoredProcedure
    {
    	protected CargarTvalosit(DataSource dataSource)
        {
            super(dataSource,"PKG_DESARROLLO.P_GET_TVALOSIT");
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
    	Utilerias.debugProcedure(logger, "PKG_SATELITES.P_OBTIENE_MPOLIAGE2", params);
    	Map<String,Object>procResult  = ejecutaSP(new CargarMpoliage(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null||lista.size()==0)
    	{
    		throw new ApplicationException("No hay agentes para la poliza");
    	}
    	Utilerias.debugProcedure(logger, "PKG_SATELITES.P_OBTIENE_MPOLIAGE2", params, lista);
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
    	Utilerias.debugProcedure(logger, "PKG_SATELITES.P_VALIDA_DATOS_OBLIG_PREVEX", params);
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
    	Utilerias.debugProcedure(logger, "PKG_SATELITES.P_VALIDA_ATRIB_FP_DXN", params);
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
    	Utilerias.debugProcedure(logger, "PKG_CONSULTA.P_GET_MAX_SUPLEMENTO", params);
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
    	Utilerias.debugProcedure(logger, "PKG_DESARROLLO.P_GET_MPOLIPER_OTROS_ROLES", params);
    	Map<String,Object>procResult  = ejecutaSP(new CargarMpoliperOtrosRolesPorNmsituac(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utilerias.debugProcedure(logger, "PKG_DESARROLLO.P_GET_MPOLIPER_OTROS_ROLES", params, lista);
    	return lista;
	}
    
    protected class CargarMpoliperOtrosRolesPorNmsituac extends StoredProcedure
    {
    	protected CargarMpoliperOtrosRolesPorNmsituac(DataSource dataSource)
    	{
    		super(dataSource , "PKG_DESARROLLO.P_GET_MPOLIPER_OTROS_ROLES");
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
    	Utilerias.debugProcedure(logger, "PKG_CONSULTA.P_OBTIENE_SITUACION", params);
    	Map<String,Object>procResult  = ejecutaSP(new CargarTiposSituacionPorRamo(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utilerias.debugProcedure(logger, "PKG_CONSULTA.P_OBTIENE_SITUACION", params, lista);
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
    	Utilerias.debugProcedure(logger, "PKG_DESARROLLO.P_VERIFICA_CDPOSTAL_FRONTER", params);
    	Map<String,Object>procResult = ejecutaSP(new VerificarCodigoPostalFronterizo(getDataSource()),params);
    	boolean esFront = ((String)procResult.get("pv_fronterizo_o")).equals("S");
    	logger.debug(Utilerias.join("verificarCodigoPostalFronterizo=",esFront));
    	return esFront;
    }
    
    protected class VerificarCodigoPostalFronterizo extends StoredProcedure
    {
    	protected VerificarCodigoPostalFronterizo(DataSource dataSource)
    	{
    		super(dataSource , "PKG_DESARROLLO.P_VERIFICA_CDPOSTAL_FRONTER");
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
    	Utilerias.debugProcedure(logger, "PKG_SATELITES.P_OBT_ATRIBUTOS", params);
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
    	Utilerias.debugProcedure(logger, "PKG_SATELITES.P_OBT_ATRIBUTOS", params, lista);
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
    	Utilerias.debugProcedure(logger, "PKG_SATELITES.P_GET_INFO_MPOLIZAS", params);
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
    	Utilerias.debugProcedure(logger, "PKG_SATELITES.P_GET_INFO_MPOLIZAS", params, lista);
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
    	Utilerias.debugProcedure(logger, "PKG_CONSULTA.P_GET_PORC_RECARGO", params);
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
    	logger.debug(Utilerias.join("PKG_CONSULTA.P_GET_PORC_RECARGO result=",sRecargo));
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
    	Utilerias.debugProcedure(logger,"PKG_DESARROLLO.P_GET_VALORES_PANTALLA",params);
    	Map<String,Object>procResult  = ejecutaSP(new RecuperarValoresPantalla(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utilerias.debugProcedure(logger,"PKG_DESARROLLO.P_GET_VALORES_PANTALLA",params,lista);
    	return lista;
	}
    
    protected class RecuperarValoresPantalla extends StoredProcedure
    {
    	protected RecuperarValoresPantalla(DataSource dataSource)
    	{
    		super(dataSource , "PKG_DESARROLLO.P_GET_VALORES_PANTALLA");
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
    	Utilerias.debugProcedure(logger,"PKG_DESARROLLO.P_GET_VALORES_DEFECTO_FACTORES",params);
    	Map<String,Object>procResult  = ejecutaSP(new RecuperarValoresAtributosFactores(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utilerias.debugProcedure(logger,"PKG_DESARROLLO.P_GET_VALORES_DEFECTO_FACTORES",params,lista);
    	return lista;
	}
    
    protected class RecuperarValoresAtributosFactores extends StoredProcedure
    {
    	protected RecuperarValoresAtributosFactores(DataSource dataSource)
    	{
    		super(dataSource , "PKG_DESARROLLO.P_GET_VALORES_DEFECTO_FACTORES");
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
    	Utilerias.debugProcedure(logger,"PKG_SATELITES.P_OBTIENE_MPOLIPER",params);
    	Map<String,Object>procResult  = ejecutaSP(new ObtieneContratantePoliza(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utilerias.debugProcedure(logger,"PKG_SATELITES.P_OBTIENE_MPOLIPER",params,lista);
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
    				"NOMBRE","CDRFC","CDPERSON","CDIDEPER","CDIDEEXT", "NMSITUAC", "CDROL", "STATUS", "NMORDDOM", "SWRECLAM"
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
			)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	params.put("nmpoliex" , nmpoliex);
    	Utilerias.debugProcedure(logger,"PKG_CONSULTA.P_GET_POLIZAS_PARA_ENDOSOS",params);
    	Map<String,Object>procResult  = ejecutaSP(new RecuperarPolizasEndosables(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utilerias.debugProcedure(logger,"PKG_CONSULTA.P_GET_POLIZAS_PARA_ENDOSOS",params,lista);
    	return lista;
	}
    
    protected class RecuperarPolizasEndosables extends StoredProcedure
    {
    	protected RecuperarPolizasEndosables(DataSource dataSource)
    	{
    		super(dataSource , "PKG_CONSULTA.P_GET_POLIZAS_PARA_ENDOSOS");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliex" , OracleTypes.VARCHAR));
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
    	Utilerias.debugProcedure(logger,"PKG_CONSULTA.P_GET_HISTORICO_POLIZA",params);
    	Map<String,Object>procResult  = ejecutaSP(new RecuperarHistoricoPoliza(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utilerias.debugProcedure(logger,"PKG_CONSULTA.P_GET_HISTORICO_POLIZA",params,lista);
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
    	Utilerias.debugProcedure(logger, "PKG_CONSULTA.P_GET_DATOS_INCISOS", params);
    	Map<String,Object>procResult  = ejecutaSP(new RecuperarIncisosPolizaGrupoFamilia(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utilerias.debugProcedure(logger,"PKG_CONSULTA.P_GET_DATOS_INCISOS",params,lista);
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
    	Utilerias.debugProcedure(logger, "PKG_DESARROLLO.P_GET_OTVALOR_CAT_TATRISIT", params);
    	Map<String,Object>procResult = ejecutaSP(new RecuperarValorAtributoUnico(getDataSource()),params);
    	String otvalor               = (String)procResult.get("pv_otvalor_o");
    	if(otvalor==null)
    	{
    		otvalor="";
    	}
    	logger.debug(Utilerias.join("PKG_DESARROLLO.P_GET_OTVALOR_CAT_TATRISIT result=",otvalor));
    	return otvalor;
	}
    
    protected class RecuperarValorAtributoUnico extends StoredProcedure
    {
    	protected RecuperarValorAtributoUnico(DataSource dataSource)
    	{
    		super(dataSource , "PKG_DESARROLLO.P_GET_OTVALOR_CAT_TATRISIT");
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
    	Utilerias.debugProcedure(logger, "PKG_CONSULTA.P_GET_GRUPOS_POLIZA", params);
    	Map<String,Object>procResult  = ejecutaSP(new RecuperarGruposPoliza(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utilerias.debugProcedure(logger,"PKG_CONSULTA.P_GET_GRUPOS_POLIZA",params,lista);
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
    	Utilerias.debugProcedure(logger, "PKG_CONSULTA.P_GET_FAMILIAS_POLIZA", params);
    	Map<String,Object>procResult  = ejecutaSP(new RecuperarFamiliasPoliza(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	Utilerias.debugProcedure(logger,"PKG_CONSULTA.P_GET_FAMILIAS_POLIZA",params,lista);
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
    	Utilerias.debugProcedure(logger, "PKG_CONSULTA.P_VALIDA_PRODUCTO_SALUD", params);
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
}