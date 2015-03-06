package mx.com.gseguros.portal.endosos.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.ObtieneTatrisitMapper;
import mx.com.gseguros.portal.cotizacion.model.ParametroEndoso;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.model.PolizaVO;
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
	public List<Map<String,String>> obtenerNombreEndosos(String cdsisrol, Integer cdramo, String cdtipsit) throws Exception
	{
		Map<String, Object> params = new LinkedHashMap<String,Object>();
		params.put("pv_cdrol_i", cdsisrol);
		params.put("pv_cdramo_i"  , cdramo);
		params.put("pv_cdtipsit_i", cdtipsit);
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
			declareParameter(new SqlParameter("pv_cdrol_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"  , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
			String[] cols = new String[]{"CDTIPSUP", "DSTIPSUP"};
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
	}

	
	@Override
	public void calcularRecibosCambioContratante(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem) throws Exception
			{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		logger.info("params :"+params);
		logger.debug(
				new StringBuilder()
				.append("\n*************************************")
				.append("\n****** P_CALC_RECIBOS_CAM_CONTRATANTE ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************")
				.toString()
				);
		ejecutaSP(new CalcularRecibosCambioContratante(getDataSource()),params);
			}
	
	protected class CalcularRecibosCambioContratante extends StoredProcedure
	{
		
		protected CalcularRecibosCambioContratante(DataSource dataSource)
		{
			super(dataSource, "P_CALC_RECIBOS_CAM_CONTRATANTE");
			
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			compile();
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
		Utilerias.debugProcedure(logger, "PKG_SATELITES2.P_MOV_MPOLIPER_BENEFIC", params);
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
	
	
	
	@Override
	public List<Map<String,String>> obtenerListaDocumentosEndosos(PolizaVO poliza) throws Exception {
		
		logger.debug(new StringBuilder().append("PKG_CONSULTA.P_GET_SUPL_TDOCUPOL params=").append(poliza).toString());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", poliza.getCdunieco());
		params.put("pv_cdramo_i",   poliza.getCdramo());
		params.put("pv_estado_i",   poliza.getEstado());
		params.put("pv_nmpoliza_i", poliza.getNmpoliza());
		Map<String,Object> resultadoMap = this.ejecutaSP(new ObtenerListaDocumentosEndososSP(this.getDataSource()), params);
		logger.debug("resultado map= "+ resultadoMap);
		return (List<Map<String,String>>) resultadoMap.get("pv_registro_o");
	}
	
	protected class ObtenerListaDocumentosEndososSP extends StoredProcedure {

		protected ObtenerListaDocumentosEndososSP(DataSource dataSource) {
			super(dataSource, "PKG_CONSULTA.P_GET_SUPL_TDOCUPOL");
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i"  , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
			String[] cols = new String[]{"NMSUPLEM", "CDTIPSUP"};
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public void insertarIncisoEvaluacion(
			String stamp
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String cdtipsit
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("stamp"    , stamp);
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsituac" , nmsituac);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdtipsit" , cdtipsit);
		Utilerias.debugProcedure(logger, "PKG_DESARROLLO.P_INS_INCISO_EVAL_ENDOSO", params);
		ejecutaSP(new InsertarIncisoEvaluacion(this.getDataSource()), params);
	}
	
	protected class InsertarIncisoEvaluacion extends StoredProcedure {

		protected InsertarIncisoEvaluacion(DataSource dataSource) {
			super(dataSource, "PKG_DESARROLLO.P_INS_INCISO_EVAL_ENDOSO");
			declareParameter(new SqlParameter("stamp"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>recuperarEndososClasificados(
			String stamp
			,String cdramo
			,String nivel
			,String multiple
			,String tipoflot
			,String cdsisrol
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("stamp"    , stamp);
		params.put("cdramo"   , cdramo);
		params.put("nivel"    , nivel);
		params.put("multiple" , multiple);
		params.put("tipoflot" , tipoflot);
		params.put("cdsisrol" , cdsisrol);
		Utilerias.debugProcedure(logger, "PKG_DESARROLLO.P_GET_ENDOSOS_CLASIFICADOS", params);
		Map<String,Object>procResult  = ejecutaSP(new RecuperarEndososClasificados(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		Utilerias.debugProcedure(logger, "PKG_DESARROLLO.P_GET_ENDOSOS_CLASIFICADOS", params, lista);
		return lista;
	}
	
	protected class RecuperarEndososClasificados extends StoredProcedure
	{
		protected RecuperarEndososClasificados(DataSource dataSource)
		{
			super(dataSource,"PKG_DESARROLLO.P_GET_ENDOSOS_CLASIFICADOS");
			declareParameter(new SqlParameter("stamp"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nivel"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("multiple" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipoflot" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"CDTIPSUP"
					,"DSTIPSUP"
					,"LIGA"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void guardarTvalositEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String status
			,String cdtipsit
			,String otvalor01,String otvalor02,String otvalor03,String otvalor04,String otvalor05
			,String otvalor06,String otvalor07,String otvalor08,String otvalor09,String otvalor10
			,String otvalor11,String otvalor12,String otvalor13,String otvalor14,String otvalor15
			,String otvalor16,String otvalor17,String otvalor18,String otvalor19,String otvalor20
			,String otvalor21,String otvalor22,String otvalor23,String otvalor24,String otvalor25
			,String otvalor26,String otvalor27,String otvalor28,String otvalor29,String otvalor30
			,String otvalor31,String otvalor32,String otvalor33,String otvalor34,String otvalor35
			,String otvalor36,String otvalor37,String otvalor38,String otvalor39,String otvalor40
			,String otvalor41,String otvalor42,String otvalor43,String otvalor44,String otvalor45
			,String otvalor46,String otvalor47,String otvalor48,String otvalor49,String otvalor50
			,String otvalor51,String otvalor52,String otvalor53,String otvalor54,String otvalor55
			,String otvalor56,String otvalor57,String otvalor58,String otvalor59,String otvalor60
			,String otvalor61,String otvalor62,String otvalor63,String otvalor64,String otvalor65
			,String otvalor66,String otvalor67,String otvalor68,String otvalor69,String otvalor70
			,String otvalor71,String otvalor72,String otvalor73,String otvalor74,String otvalor75
			,String otvalor76,String otvalor77,String otvalor78,String otvalor79,String otvalor80
			,String otvalor81,String otvalor82,String otvalor83,String otvalor84,String otvalor85
			,String otvalor86,String otvalor87,String otvalor88,String otvalor89,String otvalor90
			,String otvalor91,String otvalor92,String otvalor93,String otvalor94,String otvalor95
			,String otvalor96,String otvalor97,String otvalor98,String otvalor99
			,String tstamp)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco"  , cdunieco);
		params.put("cdramo"    , cdramo);
		params.put("estado"    , estado);
		params.put("nmpoliza"  , nmpoliza);
		params.put("nmsituac"  , nmsituac);
		params.put("nmsuplem"  , nmsuplem);
		params.put("status"    , status);
		params.put("cdtipsit"  , cdtipsit);
		params.put("otvalor01" , otvalor01);
		params.put("otvalor02" , otvalor02);
		params.put("otvalor03" , otvalor03);
		params.put("otvalor04" , otvalor04);
		params.put("otvalor05" , otvalor05);
		params.put("otvalor06" , otvalor06);
		params.put("otvalor07" , otvalor07);
		params.put("otvalor08" , otvalor08);
		params.put("otvalor09" , otvalor09);
		params.put("otvalor10" , otvalor10);
		params.put("otvalor11" , otvalor11);
		params.put("otvalor12" , otvalor12);
		params.put("otvalor13" , otvalor13);
		params.put("otvalor14" , otvalor14);
		params.put("otvalor15" , otvalor15);
		params.put("otvalor16" , otvalor16);
		params.put("otvalor17" , otvalor17);
		params.put("otvalor18" , otvalor18);
		params.put("otvalor19" , otvalor19);
		params.put("otvalor20" , otvalor20);
		params.put("otvalor21" , otvalor21);
		params.put("otvalor22" , otvalor22);
		params.put("otvalor23" , otvalor23);
		params.put("otvalor24" , otvalor24);
		params.put("otvalor25" , otvalor25);
		params.put("otvalor26" , otvalor26);
		params.put("otvalor27" , otvalor27);
		params.put("otvalor28" , otvalor28);
		params.put("otvalor29" , otvalor29);
		params.put("otvalor30" , otvalor30);
		params.put("otvalor31" , otvalor31);
		params.put("otvalor32" , otvalor32);
		params.put("otvalor33" , otvalor33);
		params.put("otvalor34" , otvalor34);
		params.put("otvalor35" , otvalor35);
		params.put("otvalor36" , otvalor36);
		params.put("otvalor37" , otvalor37);
		params.put("otvalor38" , otvalor38);
		params.put("otvalor39" , otvalor39);
		params.put("otvalor40" , otvalor40);
		params.put("otvalor41" , otvalor41);
		params.put("otvalor42" , otvalor42);
		params.put("otvalor43" , otvalor43);
		params.put("otvalor44" , otvalor44);
		params.put("otvalor45" , otvalor45);
		params.put("otvalor46" , otvalor46);
		params.put("otvalor47" , otvalor47);
		params.put("otvalor48" , otvalor48);
		params.put("otvalor49" , otvalor49);
		params.put("otvalor50" , otvalor50);
		params.put("otvalor51" , otvalor51);
		params.put("otvalor52" , otvalor52);
		params.put("otvalor53" , otvalor53);
		params.put("otvalor54" , otvalor54);
		params.put("otvalor55" , otvalor55);
		params.put("otvalor56" , otvalor56);
		params.put("otvalor57" , otvalor57);
		params.put("otvalor58" , otvalor58);
		params.put("otvalor59" , otvalor59);
		params.put("otvalor60" , otvalor60);
		params.put("otvalor61" , otvalor61);
		params.put("otvalor62" , otvalor62);
		params.put("otvalor63" , otvalor63);
		params.put("otvalor64" , otvalor64);
		params.put("otvalor65" , otvalor65);
		params.put("otvalor66" , otvalor66);
		params.put("otvalor67" , otvalor67);
		params.put("otvalor68" , otvalor68);
		params.put("otvalor69" , otvalor69);
		params.put("otvalor70" , otvalor70);
		params.put("otvalor71" , otvalor71);
		params.put("otvalor72" , otvalor72);
		params.put("otvalor73" , otvalor73);
		params.put("otvalor74" , otvalor74);
		params.put("otvalor75" , otvalor75);
		params.put("otvalor76" , otvalor76);
		params.put("otvalor77" , otvalor77);
		params.put("otvalor78" , otvalor78);
		params.put("otvalor79" , otvalor79);
		params.put("otvalor80" , otvalor80);
		params.put("otvalor81" , otvalor81);
		params.put("otvalor82" , otvalor82);
		params.put("otvalor83" , otvalor83);
		params.put("otvalor84" , otvalor84);
		params.put("otvalor85" , otvalor85);
		params.put("otvalor86" , otvalor86);
		params.put("otvalor87" , otvalor87);
		params.put("otvalor88" , otvalor88);
		params.put("otvalor89" , otvalor89);
		params.put("otvalor90" , otvalor90);
		params.put("otvalor91" , otvalor91);
		params.put("otvalor92" , otvalor92);
		params.put("otvalor93" , otvalor93);
		params.put("otvalor94" , otvalor94);
		params.put("otvalor95" , otvalor95);
		params.put("otvalor96" , otvalor96);
		params.put("otvalor97" , otvalor97);
		params.put("otvalor98" , otvalor98);
		params.put("otvalor99" , otvalor99);
		params.put("tstamp"    , tstamp);
		Utilerias.debugProcedure(logger, "PKG_DESARROLLO.P_INS_TVALOSIT_ENDOSO", params);
		ejecutaSP(new GuardarTvalositEndoso(getDataSource()),params);
	}
	
	protected class GuardarTvalositEndoso extends StoredProcedure
	{
		protected GuardarTvalositEndoso(DataSource dataSource)
		{
			super(dataSource,"PKG_DESARROLLO.P_INS_TVALOSIT_ENDOSO");
			declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"  , OracleTypes.VARCHAR));
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
			declareParameter(new SqlParameter("otvalor51" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor52" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor53" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor54" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor55" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor56" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor57" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor58" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor59" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor60" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor61" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor62" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor63" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor64" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor65" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor66" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor67" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor68" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor69" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor70" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor71" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor72" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor73" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor74" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor75" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor76" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor77" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor78" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor79" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor80" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor81" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor82" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor83" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor84" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor85" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor86" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor87" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor88" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor89" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor90" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor91" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor92" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor93" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor94" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor95" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor96" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor97" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor98" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor99" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tstamp"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void confirmarEndosoTvalositAuto(
			String cdtipsup
			,String tstamp
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdtipsup" , cdtipsup);
		params.put("tstamp"   , tstamp);
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		Utilerias.debugProcedure(logger, "PKG_DESARROLLO.P_ENDOSO_ATRIBUTOS_AUTO", params);
		ejecutaSP(new ConfirmarEndosoTvalositAuto(getDataSource()),params);
	}
	
	protected class ConfirmarEndosoTvalositAuto extends StoredProcedure
	{
		protected ConfirmarEndosoTvalositAuto(DataSource dataSource)
		{
			super(dataSource,"PKG_DESARROLLO.P_ENDOSO_ATRIBUTOS_AUTO");
			declareParameter(new SqlParameter("cdtipsup"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tstamp"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
}