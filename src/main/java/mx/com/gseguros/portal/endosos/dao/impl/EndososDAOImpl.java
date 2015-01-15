package mx.com.gseguros.portal.endosos.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.ObtieneTatrisitMapper;
import mx.com.gseguros.portal.cotizacion.model.ParametroEndoso;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.Utilerias;
import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class EndososDAOImpl extends AbstractManagerDAO implements EndososDAO
{

	private static final Logger logger = Logger.getLogger(EndososDAOImpl.class);
	
	protected class ObtenerEndosos extends StoredProcedure
	{
		String[] columnas=new String[]{
				"CDUNIECO" 
	            ,"CDRAMO" 
	            ,"ESTADO" 
	            ,"NMPOLIZA" 
	            ,"NMSUPLEM" 
	            ,"NMPOLIEX" 
	            ,"NSUPLOGI" 
	            ,"FEEMISIO" 
	            ,"FEINIVAL" 
	            ,"DSCOMENT" 
	            ,"CDTIPSIT" 
	            ,"DSTIPSIT" 
                ,"PRIMA_TOTAL"
                ,"NTRAMITE"
		};

		protected ObtenerEndosos(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_ENDOSOS_G");
			declareParameter(new SqlParameter("pv_nmpoliex_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdrfc_i"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nombre_i"    , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(columnas)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String, String>> obtenerEndosos(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n******************************************")
				.append("\n****** PKG_CONSULTA.P_GET_ENDOSOS_G ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerEndosos(this.getDataSource()), params);
		return (List<Map<String, String>>) resultadoMap.get("pv_registro_o");
	}
	
	@Override
	public Map<String, String> guardarEndosoNombres(Map<String, Object> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*****************************************")
				.append("\n****** PKG_ENDOSOS.P_ENDOSO_INICIA ******")
				.append("\n****** params=").append(params)
				.append("\n*****************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new IniciarEndoso(this.getDataSource()), params);
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utilerias.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		return map;
	}
	
	@Override
	public Map<String, String> guardarEndosoDomicilio(Map<String, Object> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*****************************************")
				.append("\n****** PKG_ENDOSOS.P_ENDOSO_INICIA ******")
				.append("\n****** params=").append(params)
				.append("\n*****************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new IniciarEndoso(this.getDataSource()), params);
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utilerias.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		return map;
	}
	
	protected class ConfirmarEndosoB extends StoredProcedure
	{
		protected ConfirmarEndosoB(DataSource dataSource)
		{
			/*
		    pv_cdunieco_i
		    pv_cdramo_i
		    pv_estado_i
		    pv_nmpoliza_i
		    pv_nmsuplem_i
		    pv_nsuplogi_i
		    pv_cdtipsup_i
		    pv_dscoment_i
		    pv_msg_id_o
		    pv_title_o
			*/
			super(dataSource, "PKG_ENDOSOS.P_CONFIRMAR_ENDOSOB");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nsuplogi_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dscoment_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void confirmarEndosoB(
    		String cdunieco
    		,String cdramo
    		,String estado
    		,String nmpoliza
    		,String nmsuplem
    		,String nsuplogi
    		,String cdtipsup
    		,String dscoment
    		)throws Exception
    {
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_nsuplogi_i" , nsuplogi);
		params.put("pv_cdtipsup_i" , cdtipsup);
		params.put("pv_dscoment_i" , dscoment);
		logger.debug(
				new StringBuilder()
				.append("\n*********************************************")
				.append("\n****** PKG_ENDOSOS.P_CONFIRMAR_ENDOSOB ******")
				.append("\n****** params=").append(params)
				.append("\n*********************************************")
				.toString()
				);
		ejecutaSP(new ConfirmarEndosoB(this.getDataSource()), params);
    }
	
	@Override
	@Deprecated
	public Map<String, String> confirmarEndosoB(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*********************************************")
				.append("\n****** PKG_ENDOSOS.P_CONFIRMAR_ENDOSOB ******")
				.append("\n****** params=").append(params)
				.append("\n*********************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ConfirmarEndosoB(this.getDataSource()), params);
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utilerias.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		return map;
	}
	
	protected class ReimprimeDocumentos extends StoredProcedure
	{
		String columnas[]=new String[]{
				"nmsolici"
				,"nmsituac"
				,"descripc"
				,"descripl"
				,"ntramite"
		};
		protected ReimprimeDocumentos(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_reImp_documentos");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipmov_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
		}
	}
	
	/**
	 * PKG_CONSULTA.P_reImp_documentos
	 */
	@Override
	@Deprecated
	public List<Map<String,String>> reimprimeDocumentos(Map<String,String>params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*********************************************")
				.append("\n****** PKG_CONSULTA.P_reImp_documentos ******")
				.append("\n****** params=").append(params)
				.append("\n*********************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ReimprimeDocumentos(this.getDataSource()), params);
		return (List<Map<String, String>>) resultadoMap.get("pv_registro_o");
	}
	
	@Override
	public List<Map<String,String>> reimprimeDocumentos(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsup)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_tipmov_i"   , cdtipsup);
		logger.debug(
				new StringBuilder()
				.append("\n*********************************************")
				.append("\n****** PKG_CONSULTA.P_reImp_documentos ******")
				.append("\n****** params=").append(params)
				.append("\n*********************************************")
				.toString()
				);
		Map<String,Object> procResult=this.ejecutaSP(new ReimprimeDocumentos(this.getDataSource()), params);
		List<Map<String,String>> lista = (List<Map<String, String>>) procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class ObtieneCoberturasDisponibles extends StoredProcedure
	{
		
		String columnas[]=new String[]{"GARANTIA","NOMBRE_GARANTIA","SWOBLIGA","SUMA_ASEGURADA","CDCAPITA",
				"status","cdtipbca","ptvalbas","swmanual","swreas","cdagrupa",
				"ptreduci","fereduci","swrevalo"};
		
		protected ObtieneCoberturasDisponibles(DataSource dataSource)
		{
			super(dataSource,"PKG_COTIZA.P_GET_COBERTURAS_DISP");
			declareParameter(new SqlParameter("pv_cdunieco_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
	}
	
	@Override
	public List<Map<String,String>> obtieneCoberturasDisponibles(Map<String,String>params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n**********************************************")
				.append("\n****** PKG_COTIZA.P_GET_COBERTURAS_DISP ******")
				.append("\n****** params=").append(params)
				.append("\n**********************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtieneCoberturasDisponibles(this.getDataSource()), params);
		return (List<Map<String, String>>) resultadoMap.get("pv_registro_o");
	}
	
	@Override
	public Map<String, String> guardarEndosoCoberturas(Map<String, Object> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*****************************************")
				.append("\n****** PKG_ENDOSOS.P_ENDOSO_INICIA ******")
				.append("\n****** params=").append(params)
				.append("\n*****************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new IniciarEndoso(this.getDataSource()), params);
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utilerias.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		return map;
	}
	
	protected class ObtenerAtributosCoberturas extends StoredProcedure
	{
		
		String columnas[]=new String[]{
				"OTVALOR08"
				,"OTVALOR09"
				,"OTVALOR10"      
				,"OTVALOR14"      
				,"OTVALOR15"      
				,"OTVALOR16"};
		
		/*
		pv_cdunieco_i
		pv_cdramo_i
        pv_estado_i
        pv_nmpoliza_i
        pv_nmsuplem_i
		 */
		protected ObtenerAtributosCoberturas(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_GET_ATRI_COBER");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));			
			declareParameter(new SqlParameter("pv_cdramo_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_messages_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			
		}
	}
	
	@Override
	public List<Map<String,String>> obtenerAtributosCoberturas(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n********************************************")
				.append("\n****** PKG_SATELITES.P_GET_ATRI_COBER ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerAtributosCoberturas(this.getDataSource()), params);
		return (List<Map<String,String>>) resultadoMap.get("pv_registro_o");
	}
	
	protected class EjecutarSIGSVALIPOL_END extends StoredProcedure
	{

		protected EjecutarSIGSVALIPOL_END(DataSource dataSource)
		{
			super(dataSource, "PKG_COTIZA.P_EJECUTA_SIGSVALIPOL_END");

			declareParameter(new SqlParameter("pv_cdusuari_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdelemen_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));

	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
	}
	
	@Override
	public Map<String,Object> sigsvalipolEnd(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_COTIZA.P_EJECUTA_SIGSVALIPOL_END ******")
				.append("\n****** params=").append(params)
				.append("\n**************************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new EjecutarSIGSVALIPOL_END(this.getDataSource()), params);
		return resultadoMap;
	}
	
	@Override
	public Map<String, String> guardarEndosoClausulas(Map<String, Object> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*****************************************")
				.append("\n****** PKG_ENDOSOS.P_ENDOSO_INICIA ******")
				.append("\n****** params=").append(params)
				.append("\n*****************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new IniciarEndoso(this.getDataSource()), params);
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utilerias.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		return map;
	}
	
	@Override
	public Map<String,String> calcularValorEndoso(Map<String, Object> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*********************************************")
				.append("\n****** PKG_ENDOSOS.P_CALC_VALOR_ENDOSO ******")
				.append("\n****** params=").append(params)
				.append("\n*********************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new CalcularValorEndoso(this.getDataSource()), params);
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utilerias.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		return map;
	}
	
	protected class CalcularValorEndoso extends StoredProcedure
	{
		protected CalcularValorEndoso(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_CALC_VALOR_ENDOSO"); 
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feinival_i" , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	@Deprecated
	public Map<String, String> iniciarEndoso(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*****************************************")
				.append("\n****** PKG_ENDOSOS.P_ENDOSO_INICIA ******")
				.append("\n****** params=").append(params)
				.append("\n*****************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new IniciarEndoso(this.getDataSource()), Utilerias.ponFechas(params));
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utilerias.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		logger.debug(
				new StringBuilder()
				.append("\n*****************************************")
				.append("\n****** salida=").append(map)
				.append("\n****** PKG_ENDOSOS.P_ENDOSO_INICIA ******")
				.append("\n*****************************************")
				.toString()
				);
		return map;
	}
	
	@Override
	public Map<String, String>      iniciarEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,Date fecha
			,String cdelemen
			,String cdusuari
			,String proceso
			,String cdtipsup)throws Exception
	{
		Map<String,Object>params=new LinkedHashMap<String,Object>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_fecha_i"    , fecha);
		params.put("pv_cdelemen_i" , cdelemen);
		params.put("pv_cdusuari_i" , cdusuari);
		params.put("pv_proceso_i"  , proceso);
		params.put("pv_cdtipsup_i" , cdtipsup);
		logger.debug(
				new StringBuilder()
				.append("\n*****************************************")
				.append("\n****** PKG_ENDOSOS.P_ENDOSO_INICIA ******")
				.append("\n****** params=").append(params)
				.append("\n*****************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new IniciarEndoso(this.getDataSource()), params);
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utilerias.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		logger.debug(
				new StringBuilder()
				.append("\n*****************************************")
				.append("\n****** salida=").append(map)
				.append("\n****** PKG_ENDOSOS.P_ENDOSO_INICIA ******")
				.append("\n*****************************************")
				.toString()
				);
		return map;
	}
	
	protected class IniciarEndoso extends StoredProcedure
	{
		protected IniciarEndoso(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_ENDOSO_INICIA");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fecha_i"    , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cdelemen_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdusuari_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_proceso_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nsuplogi_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_fesolici_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_feinival_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void insertarTworksupEnd(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_SATELITES.P_INSERTA_TWORKSUP_END ******")
				.append("\n****** params=").append(params)
				.append("\n**************************************************")
				.toString()
				);
		this.ejecutaSP(new InsertarTworksupEnd(this.getDataSource()), Utilerias.ponFechas(params));
	}
	
	protected class InsertarTworksupEnd extends StoredProcedure
	{
		protected InsertarTworksupEnd(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.P_INSERTA_TWORKSUP_END");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void insertarTworksupSitTodas(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n********************************************************")
				.append("\n****** PKG_SATELITES.P_INSERTA_TWORKSUP_SIT_TODAS ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************************")
				.toString()
				);
		this.ejecutaSP(new InsertarTworksupSitTodas(this.getDataSource()), Utilerias.ponFechas(params));
	}
	
	protected class InsertarTworksupSitTodas extends StoredProcedure
	{
		protected InsertarTworksupSitTodas(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.P_INSERTA_TWORKSUP_SIT_TODAS");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String, String> obtieneDatosMpolisit(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n****************************************************")
				.append("\n****** PKG_SATELITES.P_OBTIENE_DATOS_MPOLISIT ******")
				.append("\n****** params=").append(params)
				.append("\n****************************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtieneDatosMpolisit(this.getDataSource()), params);
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utilerias.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		return map;
	}
	
	protected class ObtieneDatosMpolisit extends StoredProcedure
	{
		protected ObtieneDatosMpolisit(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.P_OBTIENE_DATOS_MPOLISIT");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_nmsituac_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdplan_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> obtenerNombreEndosos(String cdsisrol) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdsisrol",cdsisrol);
		logger.debug(
				new StringBuilder()
				.append("\n***************************************")
				.append("\n****** PKG_LISTAS.P_GET_TTIPSUPL ******")
				.append("\n****** params=").append(params)
				.append("\n***************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerNombreEndosos(this.getDataSource()), params);
		return (List<Map<String,String>>) resultadoMap.get("pv_registro_o");
	}
	
	protected class ObtenerNombreEndosos extends StoredProcedure
	{
		protected ObtenerNombreEndosos(DataSource dataSource)
		{
			super(dataSource, "PKG_LISTAS.P_GET_TTIPSUPL");
			declareParameter(new SqlParameter("cdsisrol"         , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"CDTIPSUP" , "DSTIPSUP"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_messages_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizarFenacimi(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n****************************************")
				.append("\n****** PKG_ENDOSOS.P_UPD_FENACIMI ******")
				.append("\n****** params=").append(params)
				.append("\n****************************************")
				.toString()
				);
		this.ejecutaSP(new ActualizarFenacimi(this.getDataSource()), Utilerias.ponFechas(params));
	}
	
	protected class ActualizarFenacimi extends StoredProcedure
	{
		protected ActualizarFenacimi(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_UPD_FENACIMI");
			declareParameter(new SqlParameter("pv_cdperson_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fenacimi_i"   , OracleTypes.DATE));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizarSexo(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n************************************")
				.append("\n****** PKG_ENDOSOS.P_UPD_SEXO ******")
				.append("\n****** params=").append(params)
				.append("\n************************************")
				.toString()
				);
		this.ejecutaSP(new ActualizarSexo(this.getDataSource()), params);
	}
	
	protected class ActualizarSexo extends StoredProcedure
	{
		protected ActualizarSexo(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_UPD_SEXO");
			declareParameter(new SqlParameter("pv_cdperson_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_sexo_i"     , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String,String>> obtenerCdpersonMpoliper(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n******************************************************")
				.append("\n****** PKG_SATELITES.P_OBTIENE_CDPERSON_POLIPER ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerCdpersonMpoliper(this.getDataSource()), params);
		return (List<Map<String,String>>) resultadoMap.get("pv_registro_o");
	}
	
	protected class ObtenerCdpersonMpoliper extends StoredProcedure
	{

		protected ObtenerCdpersonMpoliper(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.P_OBTIENE_CDPERSON_POLIPER");

			declareParameter(new SqlParameter("pv_cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac" , OracleTypes.VARCHAR));
			String[] cols = new String[]{ "CDPERSON" };
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new GenericMapper(cols)));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
	}
	
	@Override
	@Deprecated
	public List<Map<String,String>> obtenerNtramiteEmision(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_NTRAMITE_EMISION ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerNtramiteEmision(this.getDataSource()), params);
		return (List<Map<String,String>>) resultadoMap.get("pv_registro_o");
	}
	
	@Override
	public String obtenerNtramiteEmision(String cdunieco,String cdramo,String estado,String nmpoliza)throws ApplicationException,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		logger.debug(
				new StringBuilder()
				.append("\n*************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_NTRAMITE_EMISION ******")
				.append("\n****** params=").append(params)
				.toString()
				);
		Map<String,Object>procResult=this.ejecutaSP(new ObtenerNtramiteEmision(this.getDataSource()), params);
		List<Map<String,String>>lista=(List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No hay tramite de emision");
		}
		if(lista.size()>1)
		{
			throw new ApplicationException("Tramite de emision duplicado");
		}
		return lista.get(0).get("NTRAMITE");
	}
	
	protected class ObtenerNtramiteEmision extends StoredProcedure
	{

		protected ObtenerNtramiteEmision(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_NTRAMITE_EMISION");

			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			String[] cols = new String[]{ "NTRAMITE" };
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new GenericMapper(cols)));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
	}
	
	@Override
	@Deprecated
	public void validaEndosoAnterior(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_ENDOSOS.P_VALIDA_ENDOSO_ANTERIOR ******")
				.append("\n****** params=").append(params)
				.append("\n**************************************************")
				.toString()
				);
		this.ejecutaSP(new ValidaEndosoAnterior(this.getDataSource()), params);
	}
	
	@Override
	public void validaEndosoAnterior(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_cdtipsup_i" , cdtipsup);
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_ENDOSOS.P_VALIDA_ENDOSO_ANTERIOR ******")
				.append("\n****** params=").append(params)
				.append("\n**************************************************")
				.toString()
				);
		ejecutaSP(new ValidaEndosoAnterior(this.getDataSource()), params);
	}
	
	protected class ValidaEndosoAnterior extends StoredProcedure
	{
		protected ValidaEndosoAnterior(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_VALIDA_ENDOSO_ANTERIOR");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizaDeducibleValosit(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n******************************************************")
				.append("\n****** PKG_ENDOSOS.P_INS_NEW_DEDUCIBLE_TVALOSIT ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************************")
				.toString()
				);
		this.ejecutaSP(new ActualizaDeducibleValosit(this.getDataSource()), params);
	}
	
	protected class ActualizaDeducibleValosit extends StoredProcedure
	{
		protected ActualizaDeducibleValosit(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_INS_NEW_DEDUCIBLE_TVALOSIT");
			declareParameter(new SqlParameter("pv_cdunieco_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_deducible_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizaCopagoValosit(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n***************************************************")
				.append("\n****** PKG_ENDOSOS.P_INS_NEW_COPAGO_TVALOSIT ******")
				.append("\n****** params=").append(params)
				.append("\n***************************************************")
				.toString()
				);
		this.ejecutaSP(new ActualizaCopagoValosit(this.getDataSource()), params);
	}
	
	protected class ActualizaCopagoValosit extends StoredProcedure
	{
		protected ActualizaCopagoValosit(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_INS_NEW_COPAGO_TVALOSIT");
			declareParameter(new SqlParameter("pv_cdunieco_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_deducible_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String, String> pClonarPolizaReexped(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*************************************")
				.append("\n****** P_CLONAR_POLIZA_REEXPED ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new PClonarPolizaReexped(this.getDataSource()), Utilerias.ponFechas(params));
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utilerias.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		return map;
	}
	
	protected class PClonarPolizaReexped extends StoredProcedure
	{
		protected PClonarPolizaReexped(DataSource dataSource)
		{
			super(dataSource, "P_CLONAR_POLIZA_REEXPED");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feinival_i" , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cdplan_i"   , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_nmpolnew_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_ntramite_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	public List<Map<String,String>> obtenerValositUltimaImagen(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			) throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("PV_CDUNIECO_I" , cdunieco);
		params.put("PV_CDRAMO_I"   , cdramo);
		params.put("PV_ESTADO_I"   , estado);
		params.put("PV_NMPOLIZA_I" , nmpoliza);
		params.put("PV_NMSUPLEM_I" , nmsuplem);
		logger.debug(
				new StringBuilder()
				.append("\n*****************************************************")
				.append("\n****** PKG_CONSULTA.P_OBT_VALOSIT_ULTIMA_IMAGEN ******")
				.append("\n****** params=").append(params)
				.append("\n*****************************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerValositUltimaImagen(this.getDataSource()), params);
		return (List<Map<String,String>>) resultadoMap.get("PV_REGISTRO_O");
	}
		
	protected class ObtenerValositUltimaImagen extends StoredProcedure
	{

		protected ObtenerValositUltimaImagen(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_OBT_VALOSIT_ULTIMA_IMAGEN");

			declareParameter(new SqlParameter("PV_CDUNIECO_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDRAMO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_ESTADO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_NMPOLIZA_I" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
				"CDUNIECO"   , "CDRAMO"    , "ESTADO"    , "NMPOLIZA"  , "NMSITUAC"  ,"NMSUPLEM" , "STATUS" , "CDTIPSIT"
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
				,"OTVALOR51" , "OTVALOR52" , "OTVALOR53" , "OTVALOR54" , "OTVALOR55"
				,"OTVALOR56" , "OTVALOR57" , "OTVALOR58" , "OTVALOR59" , "OTVALOR60"
				,"OTVALOR61" , "OTVALOR62" , "OTVALOR63" , "OTVALOR64" , "OTVALOR65"
				,"OTVALOR66" , "OTVALOR67" , "OTVALOR68" , "OTVALOR69" , "OTVALOR70"
				,"OTVALOR71" , "OTVALOR72" , "OTVALOR73" , "OTVALOR74" , "OTVALOR75"
				,"OTVALOR76" , "OTVALOR77" , "OTVALOR78" , "OTVALOR79" , "OTVALOR80"
				,"OTVALOR81" , "OTVALOR82" , "OTVALOR83" , "OTVALOR84" , "OTVALOR85"
				,"OTVALOR86" , "OTVALOR87" , "OTVALOR88" , "OTVALOR89" , "OTVALOR90"
				,"OTVALOR91" , "OTVALOR92" , "OTVALOR93" , "OTVALOR94" , "OTVALOR95"
				,"OTVALOR96" , "OTVALOR97" , "OTVALOR98" , "OTVALOR99"
			};
			declareParameter(new SqlOutParameter("PV_REGISTRO_O", OracleTypes.CURSOR, new GenericMapper(cols)));
	        declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
			
			compile();
		}
	}
	
	@Override
	public void actualizaExtraprimaValosit(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************************")
				.append("\n****** PKG_ENDOSOS.P_INS_NEW_EXTRAPRIMA_TVALOSIT ******")
				.append("\n****** params=").append(params)
				.append("\n*******************************************************")
				.toString()
				);
		this.ejecutaSP(new ActualizaExtraprimaValosit(this.getDataSource()), params);
	}
	
	protected class ActualizaExtraprimaValosit extends StoredProcedure
	{
		protected ActualizaExtraprimaValosit(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_INS_NEW_EXTRAPRIMA_TVALOSIT");
			declareParameter(new SqlParameter("pv_cdunieco_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_extraprima_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void insertarPolizaCdperpag(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*************************************************")
				.append("\n****** PKG_ENDOSOS.P_INS_MPOLIZAS_CDPERPAG ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************************")
				.toString()
				);
		this.ejecutaSP(new InsertarPolizaCdperpag(this.getDataSource()), params);
	}
	
	protected class InsertarPolizaCdperpag extends StoredProcedure
	{
		protected InsertarPolizaCdperpag(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_INS_MPOLIZAS_CDPERPAG");
			declareParameter(new SqlParameter("pv_cdunieco_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperpag_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	/**
	 * PKG_ENDOSOS.P_GET_FEINIVAL_END_FP
	 */
	@Override
	public Date obtenerFechaEndosoFormaPago(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n***********************************************")
				.append("\n****** PKG_ENDOSOS.P_GET_FEINIVAL_END_FP ******")
				.append("\n****** params=").append(params)
				.append("\n***********************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerFechaEndosoFormaPago(this.getDataSource()), params);
		SimpleDateFormat renderFechas=new SimpleDateFormat("dd/MM/yyyy");
		Date fecha=renderFechas.parse(Utilerias.formateaFecha((String)resultadoMap.get("pv_feinival_o")));
		return fecha;
	}
	
	protected class ObtenerFechaEndosoFormaPago extends StoredProcedure
	{
		protected ObtenerFechaEndosoFormaPago(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_GET_FEINIVAL_END_FP");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_feinival_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	/**
	 * P_CALC_RECIBOS_SUB_ENDOSO_FP
	 */
	@Override
	public void calcularRecibosEndosoFormaPago(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n******************************************")
				.append("\n****** P_CALC_RECIBOS_SUB_ENDOSO_FP ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************")
				.toString()
				);
		this.ejecutaSP(new CalcularRecibosEndosoFormaPago(this.getDataSource()), params);
	}
	
	protected class CalcularRecibosEndosoFormaPago extends StoredProcedure
	{
		protected CalcularRecibosEndosoFormaPago(DataSource dataSource)
		{
			super(dataSource, "P_CALC_RECIBOS_SUB_ENDOSO_FP");
			declareParameter(new SqlParameter("pv_cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem" , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	/**
	 * P_CALCULA_COMISION_BASE
	 */
	@Override
	public void calcularComisionBase(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*************************************")
				.append("\n****** P_CALCULA_COMISION_BASE ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************")
				.toString()
				);
		this.ejecutaSP(new CalcularComisionBase(this.getDataSource()), params);
	}
	
	protected class CalcularComisionBase extends StoredProcedure
	{
		protected CalcularComisionBase(DataSource dataSource)
		{
			super(dataSource, "P_CALCULA_COMISION_BASE");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	/**
	 * PKG_CONSULTA.P_GET_AGENTE_POLIZA
	 * @return a.cdunieco,
			a.cdramo,
			a.estado,
			a.nmpoliza,
			a.cdagente,
			a.nmsuplem,
			a.status,
			a.cdtipoag,
			porredau,
			a.porparti
	 */
	@Override
	public List<Map<String,String>> obtenerAgentesEndosoAgente(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n**********************************************")
				.append("\n****** PKG_CONSULTA.P_GET_AGENTE_POLIZA ******")
				.append("\n****** params=").append(params)
				.append("\n**********************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerAgentesEndosoAgente(this.getDataSource()), params);
		return (List<Map<String,String>>) resultadoMap.get("PV_REGISTRO_O");
	}
	
	protected class ObtenerAgentesEndosoAgente extends StoredProcedure
	{

		protected ObtenerAgentesEndosoAgente(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_AGENTE_POLIZA");
			declareParameter(new SqlParameter("PV_CDUNIECO_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDRAMO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_ESTADO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_NMPOLIZA_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_NMSUPLEM_I" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"CDUNIECO"  , "CDRAMO"
					,"ESTADO"   , "NMPOLIZA"
					,"CDAGENTE" , "NMSUPLEM"
					,"STATUS"   , "CDTIPOAG"
					,"PORREDAU" , "PORPARTI"
					,"NOMBRE"   , "CDSUCURS"
					,"NMCUADRO" , "DESCRIPL"
			};
			declareParameter(new SqlOutParameter("PV_REGISTRO_O" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
	}
	
	/**
	 * PKG_SATELITES.P_MOV_MPOLIAGE
	 */
	@Override
	@Deprecated
	public void pMovMpoliage(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n******************************************")
				.append("\n****** PKG_SATELITES.P_MOV_MPOLIAGE ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************")
				.toString()
				);
		this.ejecutaSP(new PMovMpoliage(this.getDataSource()), params);
	}
	
	protected class PMovMpoliage extends StoredProcedure
	{
		protected PMovMpoliage(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.P_MOV_MPOLIAGE");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdagente_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipoag_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_porredau_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmcuadro_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsucurs_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_accion_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ntramite_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_porparti_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String pGetSuplemEmision(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_SATELITES.P_GET_NMSUPLEM_EMISION ******")
				.append("\n****** params=").append(params)
				.append("\n**************************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new PGetSuplemEmision(this.getDataSource()), params);
		return (String) resultadoMap.get("pv_nmsuplem_o");
	}
	
	protected class PGetSuplemEmision extends StoredProcedure
	{

		protected PGetSuplemEmision(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.P_GET_NMSUPLEM_EMISION");

			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));

			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
	}
	
	public String obtieneFechaInicioVigenciaPoliza(String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		logger.debug("obtieneFechaInicioVigenciaPoliza params: "+params);
		logger.debug(
				new StringBuilder()
				.append("\n****************************************************")
				.append("\n****** PKG_CONSULTA.P_OBTIENE_FEINIVAL_POLIZA ******")
				.append("\n****** params=").append(params)
				.append("\n****************************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtieneFechaInicioVigenciaPoliza(this.getDataSource()), params);
		String sfecha = (String)resultadoMap.get("pv_fecha_o");
		sfecha = Utilerias.formateaFecha(sfecha);
		logger.debug("obtieneFechaInicioVigenciaPoliza resultado: "+sfecha);
		return sfecha;
	}
	
	protected class ObtieneFechaInicioVigenciaPoliza extends StoredProcedure
	{

		protected ObtieneFechaInicioVigenciaPoliza(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_OBTIENE_FEINIVAL_POLIZA");

			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));

			declareParameter(new SqlOutParameter("pv_fecha_o"  , OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
	}

	@Override
	public boolean validaEndosoSimple
	(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza
			) throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		logger.debug("validaEndosoSimple params: "+params);
		logger.debug(
				new StringBuilder()
				.append("\n*************************************************")
				.append("\n****** PKG_CONSULTA.P_VALIDA_ENDOSO_SIMPLE ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ValidaEndosoSimple(this.getDataSource()), params);
		String svalido = (String)resultadoMap.get("pv_valido_o");
		boolean bvalido = StringUtils.isNotBlank(svalido)&&svalido.equalsIgnoreCase(Constantes.SI);
		logger.debug("validaEndosoSimple resultado: "+bvalido);
		return bvalido;
	}
	
	protected class ValidaEndosoSimple extends StoredProcedure
	{
		protected ValidaEndosoSimple(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_VALIDA_ENDOSO_SIMPLE");

			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));

			declareParameter(new SqlOutParameter("pv_valido_o" , OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
	}
	
	@Override
	public void validaNuevaCobertura(String cdgarant, Date fenacimi) throws Exception
	{
		Map<String,Object>params = new LinkedHashMap<String,Object>();
		params.put("fenacimi",fenacimi);
		params.put("cdgarant",cdgarant);
		logger.info("params :"+params);
		logger.debug(
				new StringBuilder()
				.append("\n*********************************************")
				.append("\n****** PKG_ENDOSOS.P_VALIDA_FEC_ENDOSO ******")
				.append("\n****** params=").append(params)
				.append("\n*********************************************")
				.toString()
				);
		ejecutaSP(new ValidaNuevaCobertura(getDataSource()),params);
	}
	
	protected class ValidaNuevaCobertura extends StoredProcedure
	{
		protected ValidaNuevaCobertura(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_VALIDA_FEC_ENDOSO");

			declareParameter(new SqlParameter("fenacimi" , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdgarant" , OracleTypes.VARCHAR));

	        declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
	}
	
	@Override
	public void calcularRecibosCambioAgente(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdagente) throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdagente" , cdagente);
		logger.info("params :"+params);
		logger.debug(
				new StringBuilder()
				.append("\n*************************************")
				.append("\n****** P_CALC_RECIBOS_CAM_AGTE ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************")
				.toString()
				);
		ejecutaSP(new CalcularRecibosCambioAgente(getDataSource()),params);
	}
	
	protected class CalcularRecibosCambioAgente extends StoredProcedure
	{

		protected CalcularRecibosCambioAgente(DataSource dataSource)
		{
			super(dataSource, "P_CALC_RECIBOS_CAM_AGTE");

			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdagente" , OracleTypes.VARCHAR));
			
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
	}
	
	@Override
	public List<Map<String,String>> habilitaRecibosSubsecuentes(
			Date fechaDeInicio
			,Date fechaDeFin
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza) throws Exception
	{
		Map<String,Object>params=new LinkedHashMap<String,Object>();
		params.put("fechaDeInicio" , fechaDeInicio);
		params.put("fechaDeFin"    , fechaDeFin);
		params.put("cdunieco"      , cdunieco);
		params.put("cdramo"        , cdramo);
		params.put("estado"        , estado);
		params.put("nmpoliza"      , nmpoliza);
		logger.info("dao params: "+params);
		logger.debug(
				new StringBuilder()
				.append("\n***************************************************")
				.append("\n****** PKG_SATELITES.P_HABILITAR_RECIBOS_SUB ******")
				.append("\n****** params=").append(params)
				.append("\n***************************************************")
				.toString()
				);
		Map<String,Object>salida=ejecutaSP(new HabilitaRecibosSubsecuentes(getDataSource()),params);
		List<Map<String,String>> lista = (List<Map<String,String>>)salida.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class HabilitaRecibosSubsecuentes extends StoredProcedure
	{
		protected HabilitaRecibosSubsecuentes(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.P_HABILITAR_RECIBOS_SUB");
			declareParameter(new SqlParameter("fechaDeInicio" , OracleTypes.DATE));
			declareParameter(new SqlParameter("fechaDeFin"    , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdunieco"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"      , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"CDUNIECO"  , "CDRAMO"   , "ESTADO" , "NMPOLIZA"
					,"NMSUPLEM" , "NMRECIBO" , "DSDOCUME"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
	}
	
	@Override
	public void validaEstadoCodigoPostal(Map<String, String> params) throws Exception{
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************")
				.append("\n****** PKG_SATELITES.P_VALIDA_CODPOS ******")
				.append("\n****** params=").append(params)
				.append("\n*******************************************")
				.toString()
				);
		ejecutaSP(new ValidaEstadoCodigoPostal(getDataSource()),params);
	}
	
	protected class ValidaEstadoCodigoPostal extends StoredProcedure
	{
		protected ValidaEstadoCodigoPostal(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.P_VALIDA_CODPOS");
			declareParameter(new SqlParameter("pv_estado_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_codpos_i"   , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_messages_o"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizaTvalositCoberturasAdicionales(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,String cdtipsup) throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdtipsup" , cdtipsup);
		logger.debug(
				new StringBuilder()
				.append("\n*************************************************")
				.append("\n****** PKG_ENDOSOS.P_ACT_TVALOSIT_COB_ADIC ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************************")
				.toString()
				);
		ejecutaSP(new ActualizaTvalositCoberturasAdicionales(getDataSource()),params);
	}
	
	protected class ActualizaTvalositCoberturasAdicionales extends StoredProcedure
	{
		protected ActualizaTvalositCoberturasAdicionales(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_ACT_TVALOSIT_COB_ADIC");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public ComponenteVO obtenerComponenteSituacionCobertura(String cdramo,String cdtipsit,String cdtipsup,String cdgarant)throws ApplicationException,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdtipsup" , cdtipsup);
		params.put("cdgarant" , cdgarant);
		logger.debug(
				new StringBuilder()
				.append("\n*****************************************************")
				.append("\n****** PKG_LISTAS.P_RECUPERA_TATRISIT_COB_ADIC ******")
				.append("\n****** params=").append(params)
				.append("\n*****************************************************")
				.toString()
				);
		Map<String,Object>procResult=ejecutaSP(new ObtenerComponenteSituacionCobertura(getDataSource()),params);
		List<ComponenteVO>lista=(List<ComponenteVO>)procResult.get("pv_registro_o");
		ComponenteVO comp = null;
		if(lista!=null&&lista.size()==1)
		{
			comp = lista.get(0);
		}
		else if(lista!=null&&lista.size()>1)
		{
			throw new ApplicationException("Hay atributos repetidos");
		}
		return comp;
	}
	
	protected class ObtenerComponenteSituacionCobertura extends StoredProcedure
	{
		protected ObtenerComponenteSituacionCobertura(DataSource dataSource)
		{
			super(dataSource, "PKG_LISTAS.P_RECUPERA_TATRISIT_COB_ADIC");
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgarant" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtieneTatrisitMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizaTvalositSitaucionCobertura(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdatribu
			,String otvalor)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdatribu" , cdatribu);
		params.put("otvalor"  , otvalor);
		logger.debug(
				new StringBuilder()
				.append("\n************************************************")
				.append("\n****** PKG_ENDOSOS.P_ACT_TVALOSIT_X_ATRIB ******")
				.append("\n****** params=").append(params)
				.append("\n************************************************")
				.toString()
				);
		ejecutaSP(new ActualizaTvalositSitaucionCobertura(getDataSource()),params);
	}
	
	protected class ActualizaTvalositSitaucionCobertura extends StoredProcedure
	{
		protected ActualizaTvalositSitaucionCobertura(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_ACT_TVALOSIT_X_ATRIB");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdatribu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>obtenerParametrosEndoso(
			ParametroEndoso parametro
			,String cdramo
			,String cdtipsit
			,String cdtipsup
			,String clave5)throws ApplicationException,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("parametro" , parametro.getParametro());
		params.put("cdramo"    , cdramo);
		params.put("cdtipsit"  , cdtipsit);
		params.put("cdtipsup"  , cdtipsup);
		params.put("clave5"    , clave5);
		logger.debug(
				new StringBuilder()
				.append("\n********************************************")
				.append("\n****** PKG_LISTAS.P_GET_PARAMS_ENDOSO ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************")
				.toString()
				);
		Map<String,Object>procedureResult = ejecutaSP(new ObtenerParametrosEndoso(getDataSource()),params);
		List<Map<String,String>>listaAux  = (List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(listaAux==null||listaAux.size()==0)
		{
			throw new ApplicationException("No hay parametros");
		}
		if(listaAux.size()>1)
		{
			throw new ApplicationException("Parametros duplicados");
		}
		logger.debug(
				new StringBuilder()
				.append("\n********************************************")
				.append("\n****** registro=").append(listaAux.get(0))
				.append("\n****** PKG_LISTAS.P_GET_PARAMS_ENDOSO ******")
				.append("\n********************************************")
				.toString()
				);
		return listaAux.get(0);
	}
	
	protected class ObtenerParametrosEndoso extends StoredProcedure
	{
		protected ObtenerParametrosEndoso(DataSource dataSource)
		{
			super(dataSource,"PKG_LISTAS.P_GET_PARAMS_ENDOSO");
			declareParameter(new SqlParameter("parametro" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("clave5"    , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"P1CLAVE"
					,"P1VALOR"
					,"P2CLAVE"
					,"P2VALOR"
					,"P3CLAVE"
					,"P3VALOR"
					,"P4CLAVE"
					,"P4VALOR"
					,"P5CLAVE"
					,"P5VALOR"
					,"P6CLAVE"
					,"P6VALOR"
					,"P7CLAVE"
					,"P7VALOR"
					,"P8CLAVE"
					,"P8VALOR"
					,"P9CLAVE"
					,"P9VALOR"
					,"P10CLAVE"
					,"P10VALOR"
					,"P11CLAVE"
					,"P11VALOR"
					,"P12CLAVE"
					,"P12VALOR"
					,"P13CLAVE"
					,"P13VALOR"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void guardarAtributosSituacionGeneral(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,Map<String,String>tvalosit)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		
		for(int i=1;i<=99;i++)
		{
			params.put(new StringBuilder("otvalor").append(StringUtils.leftPad(String.valueOf(i),2,"0")).toString(),null);
		}
		
		params.putAll(tvalosit);
		
		logger.debug(
				new StringBuilder()
				.append("\n*****************************************************")
				.append("\n****** PKG_SATELITES2.P_INSERTA_TVALOSIT_DINAM ******")
				.append("\n****** params=").append(params)
				.append("\n*****************************************************")
				.toString()
				);
		ejecutaSP(new GuardarAtributosSituacionGeneral(getDataSource()),params);
	}
	
	protected class GuardarAtributosSituacionGeneral extends StoredProcedure
	{
		protected GuardarAtributosSituacionGeneral(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_INSERTA_TVALOSIT_DINAM");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			for(int i=1;i<=99;i++)
			{
				declareParameter(new SqlParameter(new StringBuilder("otvalor").append(
						StringUtils.leftPad(String.valueOf(i),2,"0")
						).toString() , OracleTypes.VARCHAR));
			}
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	
	@Override
	public void insertarMpolicap(Map<String, String> params) throws Exception {
		
		logger.debug(
				new StringBuilder()
				.append("\n********************************************************")
				.append("\n****** PKG_ENDOSOS.P_INSERTA_MPOLICAP ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************************")
				.toString()
				);
		this.ejecutaSP(new InsertaMPolicapSP(this.getDataSource()), params);
	}
	
	protected class InsertaMPolicapSP extends StoredProcedure {
		protected InsertaMPolicapSP(DataSource dataSource) {
			super(dataSource,"PKG_ENDOSOS.P_INSERTA_MPOLICAP");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptcapita_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoMpoliperBeneficiario(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdrol
			,String cdperson
			,String nmsuplem
			,String status
			,String nmorddom
			,String swreclam
			,String swexiper
			,String cdparent
			,String porbenef
			,String accion)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsituac" , nmsituac);
		params.put("cdrol"    , cdrol);
		params.put("cdperson" , cdperson);
		params.put("nmsuplem" , nmsuplem);
		params.put("status"   , status);
		params.put("nmorddom" , nmorddom);
		params.put("swreclam" , swreclam);
		params.put("swexiper" , swexiper);
		params.put("cdparent" , cdparent);
		params.put("porbenef" , porbenef);
		params.put("accion"   , accion);
		Utilerias.debugPrecedure(logger, "PKG_SATELITES2.P_MOV_MPOLIPER_BENEFIC", params);
		ejecutaSP(new MovimientoMpoliperBeneficiario(getDataSource()),params);
	}
	
	protected class MovimientoMpoliperBeneficiario extends StoredProcedure {
		protected MovimientoMpoliperBeneficiario(DataSource dataSource) {
			super(dataSource,"PKG_SATELITES2.P_MOV_MPOLIPER_BENEFIC");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdrol"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperson" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmorddom" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swreclam" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swexiper" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdparent" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("porbenef" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
}