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
import mx.com.gseguros.portal.endosos.model.PropiedadesDeEndosoParaWS;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.model.PolizaVO;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.Utils;
import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class EndososDAOImpl extends AbstractManagerDAO implements EndososDAO
{
	private static final Logger logger = LoggerFactory.getLogger(EndososDAOImpl.class);
	
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
				map.put(col,Utils.formateaFecha(en.getValue()+""));
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
				map.put(col,Utils.formateaFecha(en.getValue()+""));
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
				map.put(col,Utils.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		return map;
	}
	
	/*
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
	
	**
	 * PKG_CONSULTA.P_reImp_documentos
	 *
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
	*/
	
	protected class ObtieneCoberturasDisponibles extends StoredProcedure
	{
		
		String columnas[]=new String[]{"GARANTIA","NOMBRE_GARANTIA","SWOBLIGA","SUMA_ASEGURADA","CDCAPITA",
				"status","cdtipbca","ptvalbas","swmanual","swreas","cdagrupa",
				"ptreduci","fereduci","swrevalo","nmsituac", "cdtipsit"};
		
		protected ObtieneCoberturasDisponibles(DataSource dataSource)
		{
			super(dataSource,"PKG_COTIZA.P_GET_COBERTURAS_DISP");
			declareParameter(new SqlParameter("pv_cdunieco_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsisrol_i",    OracleTypes.VARCHAR));
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

	@Deprecated
	@Override
	public Map<String, String> iniciaEndoso(Map<String, Object> params) throws Exception
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
				map.put(col,Utils.formateaFecha(en.getValue()+""));
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
			//declareParameter(new SqlParameter("pv_cdtipsit_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));

	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			
			compile();
		}
	}
	
	@Override
	public Map<String,Object> sigsvalipolEnd(
			String cdusuari
			,String cdelemen
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdtipsup
			) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("pv_cdusuari_i" , cdusuari);
		params.put("pv_cdelemen_i" , cdelemen);
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsituac_i" , nmsituac);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_cdtipsup_i" , cdtipsup);
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
				map.put(col,Utils.formateaFecha(en.getValue()+""));
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
				map.put(col,Utils.formateaFecha(en.getValue()+""));
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
		Map<String,Object> resultadoMap=this.ejecutaSP(new IniciarEndoso(this.getDataSource()), Utils.ponFechas(params));
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utils.formateaFecha(en.getValue()+""));
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
				map.put(col,Utils.formateaFecha(en.getValue()+""));
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
	public void movimientoTworksupEnd(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			,String nmsuplem
			,String nmsituac
			,String accion
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_cdtipsup_i" , cdtipsup);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_nmsituac_i" , nmsituac);
		params.put("pv_accion_i"   , accion);
		logger.debug(
				new StringBuilder()
				.append("\n***************************************************")
				.append("\n****** PKG_SATELITES2.P_INSERTA_TWORKSUP_END ******")
				.append("\n****** params=").append(params)
				.append("\n***************************************************")
				.toString()
				);
		this.ejecutaSP(new InsertarTworksupEnd(this.getDataSource()), Utils.ponFechas(params));
	}
	
	protected class InsertarTworksupEnd extends StoredProcedure
	{
		protected InsertarTworksupEnd(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_INSERTA_TWORKSUP_END");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_accion_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
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
		this.ejecutaSP(new InsertarTworksupSitTodas(this.getDataSource()), Utils.ponFechas(params));
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
				map.put(col,Utils.formateaFecha(en.getValue()+""));
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
	public String obtieneDescripcionEndoso(String cdtipsup) throws Exception
	{
		Map<String, Object> params = new LinkedHashMap<String,Object>();
		params.put("pv_cdtipsup_i", cdtipsup);
		logger.debug(
				new StringBuilder()
				.append("\n***************************************")
				.append("\n****** PKG_CONSULTA.P_GET_DSTIPSUP ******")
				.append("\n****** params=").append(params)
				.append("\n***************************************")
				.toString()
				);
		Map<String,Object> result = this.ejecutaSP(new ObtieneDescripcionEndosoSP(this.getDataSource()), params);
		return (String)result.get("pv_dstipsup_o");
	}
	
	protected class ObtieneDescripcionEndosoSP extends StoredProcedure {
		protected ObtieneDescripcionEndosoSP(DataSource dataSource) {
			super(dataSource, "PKG_CONSULTA.P_GET_DSTIPSUP");
			declareParameter(new SqlParameter("pv_cdtipsup_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_dstipsup_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public String obtieneNumeroAtributo(String cdtipsit, String nombreAtributo) throws Exception
	{
		Map<String, Object> params = new LinkedHashMap<String,Object>();
		params.put("pv_cdtipsit_i", cdtipsit);
		params.put("pv_dsatribu_i", nombreAtributo);
		
		logger.debug(
				new StringBuilder()
				.append("\n***************************************")
				.append("\n****** PKG_CONSULTA.P_OBT_CDATRIBU_DE_TATRISIT ******")
				.append("\n****** params=").append(params)
				.append("\n***************************************")
				.toString()
				);
		
		Map<String,Object> result = this.ejecutaSP(new ObtieneNumeroAtributo(this.getDataSource()), params);
		List<Map<String,String>> lista = (List<Map<String,String>>) result.get("pv_registro_o");
		
		Map<String,String> mapaRes = lista.get(0);
		
		logger.debug("P_OBT_CDATRIBU_DE_TATRISIT :::: Resultado: "+mapaRes.get("CDATRIBU"));
		
		return mapaRes.get("CDATRIBU");
	}
	
	protected class ObtieneNumeroAtributo extends StoredProcedure {
		protected ObtieneNumeroAtributo(DataSource dataSource) {
			
			super(dataSource, "PKG_CONSULTA.P_OBT_CDATRIBU_DE_TATRISIT");
			
			declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsatribu_i", OracleTypes.VARCHAR));
			
			String[] cols = new String[]{"CDATRIBU"};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
			
			compile();
		}
	}
	
	@Override
	public void actualizaNombreCliente(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************************")
				.append("\n****** PKG_SATELITES2.P_ACTUALIZA_NOMBRE_PERSONA ******")
				.append("\n****** params=").append(params)
				.append("\n*******************************************************")
				.toString()
				);
		this.ejecutaSP(new ActualizaNombreCliente
				(this.getDataSource()), Utils.ponFechas(params));
	}
	
	protected class ActualizaNombreCliente extends StoredProcedure
	{
		protected ActualizaNombreCliente(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_ACTUALIZA_NOMBRE_PERSONA");
			declareParameter(new SqlParameter("pv_cdperson_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsnombre_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsnombre1_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsapellido_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsapellido1_i"   , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	@Override
	public void actualizaRfcCliente(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n****************************************************")
				.append("\n****** PKG_SATELITES2.P_ACTUALIZA_RFC_PERSONA ******")
				.append("\n****** params=").append(params)
				.append("\n****************************************************")
				.toString()
				);
		this.ejecutaSP(new ActualizaRfcCliente(this.getDataSource()), Utils.ponFechas(params));
	}
	
	protected class ActualizaRfcCliente extends StoredProcedure
	{
		protected ActualizaRfcCliente(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_ACTUALIZA_RFC_PERSONA");
			declareParameter(new SqlParameter("pv_cdperson_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdrfc_i"   , OracleTypes.VARCHAR));
			
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
		this.ejecutaSP(new ActualizarFenacimi(this.getDataSource()), Utils.ponFechas(params));
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
	public void validaEndosoPagados(
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
				.append("\n***********************************************************")
				.append("\n****** PKG_SATELITES2.P_VAL_ENDOSO_X_RECIBOS_PAGADOS ******")
				.append("\n****** params=").append(params)
				.append("\n***********************************************************")
				.toString()
				);
		ejecutaSP(new ValidaEndosoPagados(this.getDataSource()), params);
	}
	
	protected class ValidaEndosoPagados extends StoredProcedure
	{
		protected ValidaEndosoPagados(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_VAL_ENDOSO_X_RECIBOS_PAGADOS");
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
	public void actualizaVigenciaPoliza(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n********************************************************")
				.append("\n****** PKG_SATELITES2.P_INSERTA_VIGENCIA_MPOLIZAS ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************************")
				.toString()
				);
		this.ejecutaSP(new ActualizaVigenciaPoliza(this.getDataSource()), params);
	}
	
	protected class ActualizaVigenciaPoliza extends StoredProcedure
	{
		protected ActualizaVigenciaPoliza(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_INSERTA_VIGENCIA_MPOLIZAS");
			declareParameter(new SqlParameter("pv_cdunieco_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feefecto_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feproren_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public void insertaTextoLibre(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n********************************************************")
				.append("\n****** PKG_SATELITES.P_MOV_MPOLICOT ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************************")
				.toString()
				);
		this.ejecutaSP(new InsertaTextoLibre(this.getDataSource()), params);
	}
	
	protected class InsertaTextoLibre extends StoredProcedure
	{
		protected InsertaTextoLibre(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.P_MOV_MPOLICOT");
			declareParameter(new SqlParameter("pv_cdunieco_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdclausu_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipcla_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swmodi_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dslinea_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_accion_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
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
		Map<String,Object> resultadoMap=this.ejecutaSP(new PClonarPolizaReexped(this.getDataSource()), Utils.ponFechas(params));
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utils.formateaFecha(en.getValue()+""));
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
			declareParameter(new SqlParameter("pv_cduser_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco_new_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmpolnew_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_ntramite_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public boolean clonaGrupoReexp(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*************************************")
				.append("\n****** P_CLONAR_GAR_COLEC ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************")
				.toString()
				);
		this.ejecutaSP(new ClonaGrupoReexp(this.getDataSource()), params);

		return true;
	}
	
	protected class ClonaGrupoReexp extends StoredProcedure
	{
		protected ClonaGrupoReexp(DataSource dataSource)
		{
			super(dataSource, "P_CLONAR_GAR_COLEC");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgrupo_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco_new_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpolnew_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public boolean actualizaGrupoReexp(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*************************************")
				.append("\n****** PKG_SATELITES2.P_CAMBIA_PLAN_VALORES_REEXP ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************")
				.toString()
				);
		this.ejecutaSP(new ActualizaGrupoReexp(this.getDataSource()), params);
		
		return true;
	}
	
	protected class ActualizaGrupoReexp extends StoredProcedure
	{
		protected ActualizaGrupoReexp(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_CAMBIA_PLAN_VALORES_REEXP");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgrupo_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdplan_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdcadena_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o" , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public boolean valoresDefectoGrupoReexp(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*****************************************************")
				.append("\n****** PKG_SATELITES2.P_INSERTA_TWORKSUP_GRUPO ******")
				.append("\n****** params=").append(params)
				.append("\n*****************************************************")
				.toString()
				);
		this.ejecutaSP(new ValoresDefectoGrupoReexp(this.getDataSource()), params);
		
		return true;
	}
	
	protected class ValoresDefectoGrupoReexp extends StoredProcedure
	{
		protected ValoresDefectoGrupoReexp(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_INSERTA_TWORKSUP_GRUPO");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgrupo_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));
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
				"CDUNIECO"   , "CDRAMO"    , "ESTADO"    , "NMPOLIZA"  , "NMSITUAC"  ,"NMSUPLEM" , "STATUS" , "CDTIPSIT", "CDATRIBU"
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
			super(dataSource, "PKG_ENDOSOS.P_INS_NEW_EXTRAPRIMA_TVALOSIT2");
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
		Date fecha=renderFechas.parse(Utils.formateaFecha((String)resultadoMap.get("pv_feinival_o")));
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

	@Override
	public void cancelaRecibosCambioCliente(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n******************************************")
				.append("\n****** P_CANC_RECIBOS_X_CAM_CLIENTE ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************")
				.toString()
				);
		this.ejecutaSP(new CancelaRecibosCambioCliente(this.getDataSource()), params);
	}
	
	protected class CancelaRecibosCambioCliente extends StoredProcedure
	{
		protected CancelaRecibosCambioCliente(DataSource dataSource)
		{
			super(dataSource, "P_CANC_RECIBOS_X_CAM_CLIENTE");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
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

	@Override
	public List<Map<String,String>> obtenerAseguradosPoliza(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n**********************************************")
				.append("\n****** PKG_CONSULTA.P_Get_Datos_Aseg ******")
				.append("\n****** params=").append(params)
				.append("\n**********************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerAseguradosPoliza(this.getDataSource()), params);
		return (List<Map<String,String>>) resultadoMap.get("PV_REGISTRO_O");
	}
	
	protected class ObtenerAseguradosPoliza extends StoredProcedure
	{
		
		protected ObtenerAseguradosPoliza(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_Get_Datos_Aseg");
			declareParameter(new SqlParameter("PV_CDUNIECO_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDRAMO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_ESTADO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_NMPOLIZA_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_NMSUPLEM_I" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"CDPERSON"  , "NMSITUAC"
					,"CDROL"   , "DSROL"
					,"PARENTESCO" , "CDPARENTESCO"
					,"FECANTIG","TITULAR"
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
		sfecha = Utils.formateaFecha(sfecha);
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
	public void validaNuevaCobertura(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdgarant
			) throws Exception
	{
		Map<String,String>params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsituac" , nmsituac);
		params.put("cdgarant" , cdgarant);
		Utils.debugProcedure(logger, "PKG_ENDOSOS.P_VALIDA_FEC_ENDOSO", params);
		ejecutaSP(new ValidaNuevaCobertura(getDataSource()),params);
	}
	
	protected class ValidaNuevaCobertura extends StoredProcedure
	{
		protected ValidaNuevaCobertura(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_VALIDA_FEC_ENDOSO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
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
			,String cdtipsup) throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdtipsit" , null);
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
	public List<ComponenteVO> obtenerComponentesSituacionCobertura(String cdramo,String cdtipsit,String cdtipsup,String cdgarant)throws ApplicationException,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdtipsup" , cdtipsup);
		params.put("cdgarant" , cdgarant);
		Utils.debugProcedure(logger, "PKG_LISTAS.P_RECUPERA_TATRISIT_COB_ADIC", params);
		Map<String,Object>procResult=ejecutaSP(new ObtenerComponenteSituacionCobertura(getDataSource()),params);
		List<ComponenteVO>lista=(List<ComponenteVO>)procResult.get("pv_registro_o");
		Utils.debugProcedure(logger, "PKG_LISTAS.P_RECUPERA_TATRISIT_COB_ADIC", params, lista);
		ComponenteVO comp = null;
		if(lista==null)
		{
			lista = new ArrayList<ComponenteVO>();
		}
		return lista;
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
			,String nmsituac
			,String cdatribu
			,String otvalor)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("nmsituac" , nmsituac);
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
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
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
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_MOV_MPOLIPER_BENEFIC", params);
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
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_INS_INCISO_EVAL_ENDOSO", params);
		ejecutaSP(new InsertarIncisoEvaluacion(this.getDataSource()), params);
	}
	
	protected class InsertarIncisoEvaluacion extends StoredProcedure {

		protected InsertarIncisoEvaluacion(DataSource dataSource) {
			super(dataSource, "PKG_SATELITES2.P_INS_INCISO_EVAL_ENDOSO");
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
			,String cdusuari
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("stamp"    , stamp);
		params.put("cdramo"   , cdramo);
		params.put("nivel"    , nivel);
		params.put("multiple" , multiple);
		params.put("tipoflot" , tipoflot);
		params.put("cdsisrol" , cdsisrol);
		params.put("cdusuari" , cdusuari);
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_ENDOSOS_CLASIFICADOS", params);
		Map<String,Object>procResult  = ejecutaSP(new RecuperarEndososClasificados(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_ENDOSOS_CLASIFICADOS", params, lista);
		return lista;
	}
	
	protected class RecuperarEndososClasificados extends StoredProcedure
	{
		protected RecuperarEndososClasificados(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_GET_ENDOSOS_CLASIFICADOS");
			declareParameter(new SqlParameter("stamp"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nivel"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("multiple" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipoflot" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"CDTIPSUP"
					,"DSTIPSUP"
					,"LIGA"
					,"TIPO_VALIDACION"
					,"DSTIPSUP2"
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
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_INS_TVALOSIT_ENDOSO", params);
		ejecutaSP(new GuardarTvalositEndoso(getDataSource()),params);
	}
	
	protected class GuardarTvalositEndoso extends StoredProcedure
	{
		protected GuardarTvalositEndoso(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_INS_TVALOSIT_ENDOSO");
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
	public Map<String,Object> confirmarEndosoTvalositAuto(
			String cdtipsup
			,String tstamp
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,Date   fechaEndoso
			,String cdusuari
			,String cdsisrol
			,String cdelemen
			)throws Exception
	{
		Map<String,Object>params=new LinkedHashMap<String,Object>();
		params.put("cdtipsup" , cdtipsup);
		params.put("tstamp"   , tstamp);
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("feefecto" , fechaEndoso);
		params.put("cdusuari" , cdusuari);
		params.put("cdsisrol" , cdsisrol);
		params.put("cdelemen" , cdelemen);
		params.put("idproces" , Utils.generaTimestamp());
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_ENDOSO_ATRIBUTOS_AUTO", params);
		Map<String,Object> resParams = ejecutaSP(new ConfirmarEndosoTvalositAuto(getDataSource()),params);
		
		String errores = (String)resParams.get("pv_error_o");
		if(StringUtils.isNotBlank(errores))
		{
			throw new ApplicationException(errores);
		}
		
		return resParams;
	}
	
	protected class ConfirmarEndosoTvalositAuto extends StoredProcedure
	{
		protected ConfirmarEndosoTvalositAuto(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_ENDOSO_ATRIBUTOS_AUTO");
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tstamp"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feefecto" , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdelemen" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("idproces" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_ntramite_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tipoflot_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nsuplogi_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_error_o"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> obtieneDatosEndPlacasMotor(Map<String, String> params)throws Exception
	{
		List<Map<String,String>>lista = null;
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DAT_SP_SIGS_CAM_PLAC_MOT", params);
		Map<String,Object>procResult  = ejecutaSP(new ObtieneDatosEndPlacasMotor(getDataSource()),params);
		lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DAT_SP_SIGS_CAM_PLAC_MOT", params, lista);
		return lista;
	}
	
	protected class ObtieneDatosEndPlacasMotor extends StoredProcedure
	{
		protected ObtieneDatosEndPlacasMotor(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DAT_SP_SIGS_CAM_PLAC_MOT");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"IdMotivo"
					,"Sucursal"
					,"Ramo"
					,"Poliza"
					,"TEndoso"
					,"Endoso"
					,"Inciso"
					,"Placas"
					,"Motor"
					,"EndosoB"
					,"FEndoso"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String,String>> obtieneDatosEndTipoServicio(Map<String, String> params)throws Exception
	{
		List<Map<String,String>>lista = null;
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DAT_SP_SIGS_CAM_TIP_SERV", params);
		Map<String,Object>procResult  = ejecutaSP(new ObtieneDatosEndTipoServicio(getDataSource()),params);
		lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DAT_SP_SIGS_CAM_TIP_SERV", params, lista);
		return lista;
	}
	
	protected class ObtieneDatosEndTipoServicio extends StoredProcedure
	{
		protected ObtieneDatosEndTipoServicio(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DAT_SP_SIGS_CAM_TIP_SERV");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"IdMotivo"
					,"Sucursal"
					,"Ramo"
					,"Poliza"
					,"TEndoso"
					,"Endoso"
					,"Inciso"
					,"Servicio"
					,"TipoUso"
					,"EndosoB"
					,"FEndoso"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String,String>> obtieneDatosEndVigenciaPol(Map<String, String> params)throws Exception
	{
		List<Map<String,String>>lista = null;
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DATOS_SP_SIGS_CAM_VIG", params);
		Map<String,Object>procResult  = ejecutaSP(new ObtieneDatosEndVigenciaPol(getDataSource()),params);
		lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DATOS_SP_SIGS_CAM_VIG", params, lista);
		return lista;
	}
	
	protected class ObtieneDatosEndVigenciaPol extends StoredProcedure
	{
		protected ObtieneDatosEndVigenciaPol(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_SP_SIGS_CAM_VIG");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"IdMotivo"
					,"Sucursal"
					,"Ramo"
					,"Poliza"
					,"TEndoso"
					,"Endoso"
					,"Recibo"
					,"FIniRec"
					,"FFinRec"
					,"FIniPol"
					,"FFinPol"
					,"FEndoso"
					,"EndoB"
					
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> obtieneDatosEndSerie(Map<String, String> params)throws Exception
	{
		List<Map<String,String>>lista = null;
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DATOS_SP_SIGS_CAM_SERIE", params);
		Map<String,Object>procResult  = ejecutaSP(new ObtieneDatosEndSerie(getDataSource()),params);
		lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DATOS_SP_SIGS_CAM_SERIE", params, lista);
		return lista;
	}
	
	protected class ObtieneDatosEndSerie extends StoredProcedure
	{
		protected ObtieneDatosEndSerie(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_SP_SIGS_CAM_SERIE");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"IdMotivo"
					,"Sucursal"
					,"Ramo"
					,"Poliza"
					,"TEndoso"
					,"Endoso"
					,"Inciso"
					,"Serie"
					,"EndosoB"
					,"FEndoso"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String,String>> obtieneDatosEndBeneficiario(Map<String, String> params)throws Exception
	{
		List<Map<String,String>>lista = null;
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DATOS_SP_SIGS_CAM_BENEF", params);
		Map<String,Object>procResult  = ejecutaSP(new ObtieneDatosEndBeneficiario(getDataSource()),params);
		lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DATOS_SP_SIGS_CAM_BENEF", params, lista);
		return lista;
	}
	
	protected class ObtieneDatosEndBeneficiario extends StoredProcedure
	{
		protected ObtieneDatosEndBeneficiario(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_SP_SIGS_CAM_BENEF");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"IdMotivo"
					,"Sucursal"
					,"Ramo"
					,"Poliza"
					,"Inciso"
					,"Beneficiario"
					,"FEndoso"
					,"Clausula"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String,String>> obtieneDatosEndAseguradoAlterno(Map<String, String> params)throws Exception
	{
		List<Map<String,String>>lista = null;
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DATOS_SP_SIGS_ASEG_ALTER", params);
		Map<String,Object>procResult  = ejecutaSP(new ObtieneDatosEndAseguradoAlterno(getDataSource()),params);
		lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DATOS_SP_SIGS_ASEG_ALTER", params, lista);
		return lista;
	}
	
	protected class ObtieneDatosEndAseguradoAlterno extends StoredProcedure
	{
		protected ObtieneDatosEndAseguradoAlterno(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_SP_SIGS_ASEG_ALTER");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"IdMotivo"
					,"Sucursal"
					,"Ramo"
					,"Poliza"
					,"AsegAlterno"
					,"FEndoso"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> obtieneDatosEndAdaptacionesRC(Map<String, String> params)throws Exception
	{
		List<Map<String,String>>lista = null;
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DATOS_SP_SIGS_ADAPTAC_RC", params);
		Map<String,Object>procResult  = ejecutaSP(new ObtieneDatosEndAdaptacionesRC(getDataSource()),params);
		lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DATOS_SP_SIGS_ADAPTAC_RC", params, lista);
		return lista;
	}
	
	protected class ObtieneDatosEndAdaptacionesRC extends StoredProcedure
	{
		protected ObtieneDatosEndAdaptacionesRC(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_SP_SIGS_ADAPTAC_RC");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"IdMotivo"
					,"Sucursal"
					,"Ramo"
					,"Poliza"
					,"Inciso"
					,"vTexto"
					,"FEndoso"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> obtieneDatosEndVigencia(Map<String, String> params)throws Exception
	{
		List<Map<String,String>>lista = null;
		Utils.debugProcedure(logger, "PKG_CONSULTA.", params);
		Map<String,Object>procResult  = ejecutaSP(new ObtieneDatosEndVigencia(getDataSource()),params);
		lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		Utils.debugProcedure(logger, "PKG_CONSULTA.", params, lista);
		return lista;
	}
	
	protected class ObtieneDatosEndVigencia extends StoredProcedure
	{
		protected ObtieneDatosEndVigencia(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"IdMotivo"
					,"Sucursal"
					,"Ramo"
					,"Poliza"
					,"FEndoso"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String,String>> obtieneDatosEndTextoLibre(Map<String, String> params)throws Exception
	{
		List<Map<String,String>>lista = null;
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DAT_SP_SIGS_TEXTO_LIBRE", params);
		Map<String,Object>procResult  = ejecutaSP(new ObtieneDatosEndTextoLibre(getDataSource()),params);
		lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DAT_SP_SIGS_TEXTO_LIBRE", params, lista);
		return lista;
	}
	
	protected class ObtieneDatosEndTextoLibre extends StoredProcedure
	{
		protected ObtieneDatosEndTextoLibre(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DAT_SP_SIGS_TEXTO_LIBRE");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"IdMotivo"
					,"Sucursal"
					,"Ramo"
					,"Poliza"
					,"Texto"
					,"FEndoso"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public void actualizaNumeroEndosSigs(Map<String, String> params)throws Exception
	{
		List<Map<String,String>>lista = null;
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_ACTUALIZA_NUM_ENDOSOB_SIGS", params);
		ejecutaSP(new ActualizaNumeroEndosSigs(getDataSource()),params);
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_ACTUALIZA_NUM_ENDOSOB_SIGS", params, lista);
		return;
	}
	
	protected class ActualizaNumeroEndosSigs extends StoredProcedure
	{
		protected ActualizaNumeroEndosSigs(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_ACTUALIZA_NUM_ENDOSOB_SIGS");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_numend_sigs_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,Object> confirmarEndosoAltaIncisoAuto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String tstamp
			,String cdusuri
			,String cdelemen
			,String cdtipsup
			,Date   fechaEfecto
			)throws Exception
	{
		Map<String,Object>params = new LinkedHashMap<String,Object>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("tstamp"   , tstamp);
		params.put("cdusuri"  , cdusuri);
		params.put("cdelemen" , cdelemen);
		params.put("cdtipsup" , cdtipsup);
		params.put("feefecto" , fechaEfecto);
		params.put("idproces" , Utils.generaTimestamp());
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_ENDOSO_ALTA_AUTO", params);
		Map<String,Object> resParams = ejecutaSP(new ConfirmarEndosoAltaIncisoAuto(getDataSource()),params);
		
		return resParams;
	}
	
	protected class ConfirmarEndosoAltaIncisoAuto extends StoredProcedure
	{
		protected ConfirmarEndosoAltaIncisoAuto(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_ENDOSO_ALTA_AUTO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tstamp"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuri"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdelemen" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feefecto" , OracleTypes.DATE));
			declareParameter(new SqlParameter("idproces" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_ntramite_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tipoflot_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nsuplogi_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,Object> confirmarEndosoBajaIncisos(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String tstamp
			,String cdusuri
			,String cdelemen
			,String cdtipsup
			,Date   fechaEfecto
			)throws Exception
	{
		Map<String,Object>params = new LinkedHashMap<String,Object>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("tstamp"   , tstamp);
		params.put("cdusuri"  , cdusuri);
		params.put("cdelemen" , cdelemen);
		params.put("cdtipsup" , cdtipsup);
		params.put("feefecto" , fechaEfecto);
		params.put("idproces" , Utils.generaTimestamp());
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_ENDOSO_BAJA_INCISOS", params);
		Map<String,Object> resParams = ejecutaSP(new ConfirmarEndosoBajaIncisos(getDataSource()),params);
		
		return resParams;
	}
	
	protected class ConfirmarEndosoBajaIncisos extends StoredProcedure
	{
		protected ConfirmarEndosoBajaIncisos(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_ENDOSO_BAJA_INCISOS");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tstamp"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuri"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdelemen" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feefecto" , OracleTypes.DATE));
			declareParameter(new SqlParameter("idproces" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_ntramite_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tipoflot_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nsuplogi_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public Map<String,Object> guardaEndosoDespago(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmrecibo
			,String nmimpres
			,String cdusuari
			,String cdtipsup
			)throws Exception
			{
		Map<String,Object>params = new LinkedHashMap<String,Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i"  , cdramo);
		params.put("pv_estado_i"  , estado);
		params.put("pv_nmpoliza_i", nmpoliza);
//		params.put("pv_nmsuplem_i", nmsuplem);
		params.put("pv_nmrecibo_i", nmrecibo);
		params.put("pv_nmimpres_i", nmimpres);
		params.put("pv_cduser_i"  , cdusuari);
		params.put("pv_cdtipsup_i"  , cdtipsup);
		Utils.debugProcedure(logger, "P_CALC_RECIBOS_DESPAGOS", params);
		Map<String,Object> resParams = ejecutaSP(new GuardaEndosoDespago(getDataSource()),params);
		
		return resParams;
			}
	
	protected class GuardaEndosoDespago extends StoredProcedure
	{
		protected GuardaEndosoDespago(DataSource dataSource)
		{
			super(dataSource,"P_CALC_RECIBOS_DESPAGOS");
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
//			declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmrecibo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmimpres_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cduser_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_ntramite_o"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tipoflot_o"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String, String>> obtenerRetroactividad(String cdsisrol, String cdramo,
			String cdtipsup, String fechaProceso) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n***************************************************")
				.append("\n****** PKG_CONSULTA.P_OBTIENE_RETROACTIVIDAD ******")
				.append("\n****** cdsisrol =").append(cdsisrol)
				.append("\n****** cdramo =").append(cdramo)
				.append("\n****** cdtipsup =").append(cdtipsup)
				.append("\n****** feautori =").append(fechaProceso)
				.append("\n***************************************************")
				.toString()
				);
		Map<String,String>params = new LinkedHashMap<String,String>();
		params.put("pv_cdsisrol_i" , cdsisrol);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_cdtipsup_i" , cdtipsup);
		params.put("pv_feautori_i" , fechaProceso);
		
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerRetroActividad(this.getDataSource()), params);
		return (List<Map<String, String>>) resultadoMap.get("pv_registro_o");
	}
	protected class ObtenerRetroActividad extends StoredProcedure
	{
		String[] columnas=new String[]{
				"DIASMINIMO" 
	            ,"DIASMAXIMO"
		};

		protected ObtenerRetroActividad(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_OBTIENE_RETROACTIVIDAD");
			declareParameter(new SqlParameter("pv_cdsisrol_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"       ,OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feautori_i"    , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(columnas)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String, String>> obtieneRecibosPagados(String cdunieco, String cdramo ,String estado ,String nmpoliza) throws Exception
			{
		Map<String,String>params = new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i" , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtieneRecibosPagados(this.getDataSource()), params);
		return (List<Map<String, String>>) resultadoMap.get("pv_registro_o");
			}
	protected class ObtieneRecibosPagados extends StoredProcedure
	{
		String[] columnas=new String[]{
			"NMRECIBO","NMSUPLEM","NMIMPRES","CDDEVCIA","CDGESTOR","PTIMPORT","FEEMISIO","FEINICIO","FEFINAL","FEESTADO"
		};
		
		protected ObtieneRecibosPagados(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_REC_A_DESPAGAR_X_POLIZA");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"       ,OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,Object> guardarEndosoClaveAuto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,Date feefecto
			,String tstamp
			,String cdusuri
			,String cdelemen
			,String cdtipsup
			)throws Exception
	{
		Map<String,Object>params = new LinkedHashMap<String,Object>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("feefecto" , feefecto);
		params.put("tstamp"   , tstamp);
		params.put("cdusuri"  , cdusuri);
		params.put("cdelemen" , cdelemen);
		params.put("cdtipsup" , cdtipsup);
		params.put("idproces" , Utils.generaTimestamp());
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_ENDOSO_CLAVE_AUTO", params);
		Map<String,Object> resParams = ejecutaSP(new GuardarEndosoClaveAuto(getDataSource()),params);
		return resParams;
	}
	
	protected class GuardarEndosoClaveAuto extends StoredProcedure
	{
		protected GuardarEndosoClaveAuto(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_ENDOSO_CLAVE_AUTO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feefecto" , OracleTypes.DATE));
			declareParameter(new SqlParameter("tstamp"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuri"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdelemen" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("idproces" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_ntramite_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tipoflot_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nsuplogi_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarCoberturasEndosoDevolucionPrimas(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String tstamp
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsituac" , nmsituac);
		params.put("tstamp"   , tstamp);
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_COBER_ENDOSO_DEV_PRI", params);
		Map<String,Object>procResult   = ejecutaSP(new RecuperarCoberturasEndosoDevolucionPrimas(getDataSource()),params);
		List<Map<String,String>> lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No se encontraron coberturas");
		}
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_COBER_ENDOSO_DEV_PRI", params, lista);
		return lista;
	}
	
	protected class RecuperarCoberturasEndosoDevolucionPrimas extends StoredProcedure
	{
		protected RecuperarCoberturasEndosoDevolucionPrimas(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_GET_COBER_ENDOSO_DEV_PRI");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tstamp"   , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"CDUNIECO"
					,"CDRAMO"
					,"ESTADO"
					,"NMPOLIZA"
					,"NMSITUAC"
					,"CDGARANT"
					,"DSGARANT"
					,"DEVOLVER"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public int recuperarDiasDiferenciaEndosoValidos(String cdramo,String cdtipsup)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdramo"   , cdramo);
		params.put("cdtipsup" , cdtipsup);
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_DIAS_ENDOSO_AUTORIZA", params);
		Map<String,Object> procResult = ejecutaSP(new RecuperarDiasDiferenciaEndosoValidos(getDataSource()),params);
		int dias = Integer.parseInt((String)procResult.get("pv_dias_endoso_o"));
		logger.debug(Utils.log("PKG_CONSULTA.P_GET_DIAS_ENDOSO_AUTORIZA dias=",dias));
		return dias;
	}
	
	protected class RecuperarDiasDiferenciaEndosoValidos extends StoredProcedure
	{
		protected RecuperarDiasDiferenciaEndosoValidos(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DIAS_ENDOSO_AUTORIZA");
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			//declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_dias_endoso_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"      , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"       , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public boolean revierteEndosoFallido(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nsuplogi
			,String nmsuplem
			)
			{
				try {
					Map<String,String>params=new LinkedHashMap<String,String>();
					params.put("p_CDUNIECO" , cdunieco);
					params.put("p_CDRAMO"   , cdramo);
					params.put("p_ESTADO"   , estado);
					params.put("p_NMPOLIZA" , nmpoliza);
					params.put("p_NSUPLOGI" , nsuplogi);
					params.put("p_NMSUPLEM" , nmsuplem);
					Utils.debugProcedure(logger, "P_SACAENDOSO", params);
					ejecutaSP(new RevierteEndosoFallido(getDataSource()),params);
					Utils.debugProcedure(logger, "P_SACAENDOSO", params);
				} catch (Exception e) {
					logger.error("Error al revertir el endoso. " ,e);
					return false;
				}
				return true;
			}
	
	protected class RevierteEndosoFallido extends StoredProcedure
	{
		protected RevierteEndosoFallido(DataSource dataSource)
		{
			super(dataSource,"P_SACAENDOSO");
			declareParameter(new SqlParameter("p_CDUNIECO" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("p_CDRAMO"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("p_ESTADO"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("p_NMPOLIZA" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("p_NSUPLOGI" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("p_NMSUPLEM"   , OracleTypes.VARCHAR));
			compile();
		}
	}

	
	@Override
	public boolean revierteDomicilio(Map<String, String> params)
	{
		try {
			Utils.debugProcedure(logger, "P_SACAENDOSO_DAT_MDOMICIL", params);
			ejecutaSP(new RevierteDomicilio(getDataSource()),params);
			Utils.debugProcedure(logger, "P_SACAENDOSO_DAT_MDOMICIL", params);
		} catch (Exception e) {
			logger.error("Error al revertir el domicilio. " ,e);
			return false;
		}
		return true;
	}
	
	protected class RevierteDomicilio extends StoredProcedure
	{
		protected RevierteDomicilio(DataSource dataSource)
		{
			super(dataSource,"P_SACAENDOSO_DAT_MDOMICIL");
			declareParameter(new SqlParameter("pv_cdperson_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsdomici_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdpostal_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmnumero_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmnumint_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdedo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdmunici_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdcoloni_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public boolean revierteNombrePersona(Map<String, String> params)
	{
		try {
			Utils.debugProcedure(logger, "P_SACAENDOSO_DAT_MPERSONA", params);
			ejecutaSP(new RevierteNombrePersona(getDataSource()),params);
			Utils.debugProcedure(logger, "P_SACAENDOSO_DAT_MPERSONA", params);
		} catch (Exception e) {
			logger.error("Error al revertir el nombre de persona. " ,e);
			return false;
		}
		return true;
	}
	
	protected class RevierteNombrePersona extends StoredProcedure
	{
		protected RevierteNombrePersona(DataSource dataSource)
		{
			super(dataSource,"P_SACAENDOSO_DAT_MPERSONA");
			declareParameter(new SqlParameter("pv_cdperson_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdrfc_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsnombre_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsnombre1_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsapellido_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsapellido1_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_title_o" , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public void reasignaParentescoTitular(Map<String, String> params)throws Exception
	{
			Utils.debugProcedure(logger, "PKG_SATELITES2.P_REASIGNA_PARENTESCO_TIT", params);
			ejecutaSP(new ReasignaParentescoTitular(getDataSource()),params);
			Utils.debugProcedure(logger, "PKG_SATELITES2.P_REASIGNA_PARENTESCO_TIT", params);
	}
	
	protected class ReasignaParentescoTitular extends StoredProcedure
	{
		protected ReasignaParentescoTitular(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_REASIGNA_PARENTESCO_TIT");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_title_o" , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public void guardarMpolicot(String cdunieco, String cdramo, String estado,
			String nmpoliza, String nmsituac, String cdclausu, String nmsuplem,
			String status, String cdtipcla, String swmodi, String dslinea,
			String accion) throws Exception {
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i"  , cdramo);
		params.put("pv_estado_i"  , estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsituac_i", nmsituac);
		params.put("pv_cdclausu_i", cdclausu);
		params.put("pv_nmsuplem_i", nmsuplem);
		params.put("pv_status_i"  , status);
		params.put("pv_cdtipcla_i", cdtipcla);
		params.put("pv_swmodi_i"  , swmodi);
		params.put("pv_dslinea_i" , dslinea);
		params.put("pv_accion_i"  , accion);
		Utils.debugProcedure(logger, "PKG_SATELITES.P_MOV_MPOLICOT", params);
		ejecutaSP(new PMovMpolicot(getDataSource()),params);
	}
	
	protected class PMovMpolicot extends StoredProcedure {
		
		protected PMovMpolicot(DataSource dataSource) {
			super(dataSource,"PKG_SATELITES.P_MOV_MPOLICOT");
			declareParameter(new SqlParameter("pv_cdunieco_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdclausu_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipcla_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swmodi_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dslinea_i",   OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_accion_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",  OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,Object> confirmarEndosoRehabilitacion(
			String cdusuari
			,String cdsisrol
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			,String nsuplogi
			,String cddevcia
			,String cdgestor
			,Date   feemisio
			,Date   feinival
			,Date   fefinval
			,Date   feefecto
			,Date   feproren
			,String cdmoneda
			,String nmsuplem
			,String cdelemen
			)throws Exception
	{
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("cdusuari" , cdusuari);
		params.put("cdsisrol" , cdsisrol);
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdtipsup" , cdtipsup);
		params.put("nsuplogi" , nsuplogi);
		params.put("cddevcia" , cddevcia);
		params.put("cdgestor" , cdgestor);
		params.put("feemisio" , feemisio);
		params.put("feinival" , feinival);
		params.put("fefinval" , fefinval);
		params.put("feefecto" , feefecto);
		params.put("feproren" , feproren);
		params.put("cdmoneda" , cdmoneda);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdelemen" , cdelemen);
		params.put("idproces" , Utils.generaTimestamp());
		Utils.debugProcedure(logger, "PKG_ENDOSOS.P_ENDOSO_REHABILITA", params);
		Map<String,Object> resParams = ejecutaSP(new ConfirmarEndosoRehabilitacion(getDataSource()),params);
		
		return resParams;
	}
	
	protected class ConfirmarEndosoRehabilitacion extends StoredProcedure
	{
		protected ConfirmarEndosoRehabilitacion(DataSource dataSource)
		{
			super(dataSource,"PKG_ENDOSOS.P_ENDOSO_REHABILITA");
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nsuplogi" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cddevcia" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgestor" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feemisio" , OracleTypes.DATE));
			declareParameter(new SqlParameter("feinival" , OracleTypes.DATE));
			declareParameter(new SqlParameter("fefinval" , OracleTypes.DATE));
			declareParameter(new SqlParameter("feefecto" , OracleTypes.DATE));
			declareParameter(new SqlParameter("feproren" , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdmoneda" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdelemen" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("idproces" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_ntramite_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tipoflot_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void insertaRecibosNvaVigencia(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n********************************************************")
				.append("\n****** PKG_SATELITES2.P_INSERTA_VIGENCIA_MPOLIZAS ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************************")
				.toString()
				);
		this.ejecutaSP(new InsertaRecibosNvaVigencia(this.getDataSource()), params);
	}
	
	protected class InsertaRecibosNvaVigencia extends StoredProcedure
	{
		protected InsertaRecibosNvaVigencia(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_INSERTA_RECIBOS_NVA_VIGENCIA");
			declareParameter(new SqlParameter("pv_cdunieco_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feefecto_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feproren_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,Object> confirmarEndosoCancelacionAuto(
			String cdusuari
			,String cdsisrol
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			,String nsuplogi
			,String cddevcia
			,String cdgestor
			,Date   feemisio
			,Date   feinival
			,Date   fefinval
			,Date   feefecto
			,Date   feproren
			,String cdmoneda
			,String nmsuplem
			,String cdelemen
			,Date   feinicio
			)throws Exception
	{
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("cdusuari" , cdusuari);
		params.put("cdsisrol" , cdsisrol);
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdtipsup" , cdtipsup);
		params.put("nsuplogi" , nsuplogi);
		params.put("cddevcia" , cddevcia);
		params.put("cdgestor" , cdgestor);
		params.put("feemisio" , feemisio);
		params.put("feinival" , feinival);
		params.put("fefinval" , fefinval);
		params.put("feefecto" , feefecto);
		params.put("feproren" , feproren);
		params.put("cdmoneda" , cdmoneda);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdelemen" , cdelemen);
		params.put("feinicio" , feinicio);
		params.put("idproces" , Utils.generaTimestamp());
		Utils.debugProcedure(logger, "PKG_ENDOSOS.P_ENDOSO_CANCELA_AUTO", params);
		Map<String,Object> resParams = ejecutaSP(new ConfirmarEndosoCancelacionAuto(getDataSource()),params);
		
		return resParams;
	}
	
	protected class ConfirmarEndosoCancelacionAuto extends StoredProcedure
	{
		protected ConfirmarEndosoCancelacionAuto(DataSource dataSource)
		{
			super(dataSource,"PKG_ENDOSOS.P_ENDOSO_CANCELA_AUTO");
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nsuplogi" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cddevcia" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgestor" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feemisio" , OracleTypes.DATE));
			declareParameter(new SqlParameter("feinival" , OracleTypes.DATE));
			declareParameter(new SqlParameter("fefinval" , OracleTypes.DATE));
			declareParameter(new SqlParameter("feefecto" , OracleTypes.DATE));
			declareParameter(new SqlParameter("feproren" , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdmoneda" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdelemen" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feinicio" , OracleTypes.DATE));
			declareParameter(new SqlParameter("idproces" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_ntramite_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tipoflot_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,Object> guardarEndosoDevolucionPrimas(
			String cdusuari
			,String cdsisrol
			,String cdelemen
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			,String tstamp
			,Date   feefecto
			)throws Exception
	{
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("cdusuari" , cdusuari);
		params.put("cdsisrol" , cdsisrol);
		params.put("cdelemen" , cdelemen);
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdtipsup" , cdtipsup);
		params.put("tstamp"   , tstamp);
		params.put("feefecto" , feefecto);
		params.put("idproces" , Utils.generaTimestamp());
		Utils.debugProcedure(logger, "PKG_ENDOSOS.P_ENDOSO_DEVOLUCION_PRIMAS", params);
		Map<String,Object> resParams = ejecutaSP(new GuardarEndosoDevolucionPrimas(getDataSource()),params);
		return resParams;
	}
	
	protected class GuardarEndosoDevolucionPrimas extends StoredProcedure
	{
		protected GuardarEndosoDevolucionPrimas(DataSource dataSource)
		{
			super(dataSource,"PKG_ENDOSOS.P_ENDOSO_DEVOLUCION_PRIMAS");
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdelemen" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tstamp"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feefecto" , OracleTypes.DATE));
			declareParameter(new SqlParameter("idproces" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_ntramite_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tipoflot_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void validaEndosoCambioVigencia(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		logger.debug(
				new StringBuilder()
				.append("\n*********************************************************")
				.append("\n****** PKG_SATELITES2.P_VAL_ENDOSO_CAMBIO_VIGENCIA ******")
				.append("\n****** params=").append(params)
				.append("\n*********************************************************")
				.toString()
				);
		ejecutaSP(new ValidaEndosoCambioVigencia(this.getDataSource()), params);
	}
	
	protected class ValidaEndosoCambioVigencia extends StoredProcedure
	{
		protected ValidaEndosoCambioVigencia(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_VAL_ENDOSO_CAMBIO_VIGENCIA");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	public PropiedadesDeEndosoParaWS confirmarEndosoValositFormsAuto(
			String cdusuari
			,String cdsisrol
			,String cdelemen
			,String cdtipsup
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,Date feinival
			,String tstamp
			)throws Exception
	{
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("cdusuari" , cdusuari);
		params.put("cdsisrol" , cdsisrol);
		params.put("cdelemen" , cdelemen);
		params.put("cdtipsup" , cdtipsup);
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("feinival" , feinival);
		params.put("tstamp"   , tstamp);
		params.put("idproces" , tstamp);
		Utils.debugProcedure(logger, "PKG_ENDOSOS.P_ENDOSO_VALOSIT_FORM", params);
		Map<String,Object> procResult = ejecutaSP(new ConfirmarEndosoValositFormsAuto(getDataSource()),params);
		String tipoflot = (String)procResult.get("pv_tipoflot_o");
		String ntramite = (String)procResult.get("pv_ntramite_o");
		String nmsuplem = (String)procResult.get("pv_nmsuplem_o");
		String nsuplogi = (String)procResult.get("pv_nsuplogi_o");
		PropiedadesDeEndosoParaWS prop = new PropiedadesDeEndosoParaWS();
		prop.setTipoflot(tipoflot);
		prop.setNtramite(ntramite);
		prop.setNmsuplem(nmsuplem);
		prop.setNsuplogi(nsuplogi);
		return prop;
	}
	
	protected class ConfirmarEndosoValositFormsAuto extends StoredProcedure
	{
		protected ConfirmarEndosoValositFormsAuto(DataSource dataSource)
		{
			super(dataSource,"PKG_ENDOSOS.P_ENDOSO_VALOSIT_FORM");
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdelemen" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feinival" , OracleTypes.DATE));
			declareParameter(new SqlParameter("tstamp"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("idproces" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tipoflot_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_ntramite_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nsuplogi_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void modificarNmsuplemSatelites(
		String cdunieco,
		String cdramo,
		String estado,
		String nmpoliza,
		String nmsuplemOriginal,
		Date feEfecto,
		Date feproren)throws Exception
	{
		Map<String,Object>params=new LinkedHashMap<String,Object>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsuplem_i" , nmsuplemOriginal);
		params.put("pv_feefecto_i" , feEfecto);
		params.put("pv_feproren_i" , feproren);
		logger.debug(
				new StringBuilder()
				.append("\n*********************************************************")
				.append("\n******    P_ACTUALIZA_NMSUPLEM_SATELITES			 *******")
				.append("\n****** params=").append(params)
				.append("\n*********************************************************")
				.toString()
				);
		ejecutaSP(new ModificaNmsuplemSatelites(this.getDataSource()), params);
	}
		
	protected class ModificaNmsuplemSatelites extends StoredProcedure
	{
		protected ModificaNmsuplemSatelites(DataSource dataSource)
		{
			super(dataSource, "P_ACTUALIZA_NMSUPLEM_SATELITES");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feefecto_i" , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_feproren_i" , OracleTypes.DATE));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o" , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> obtieneEndososPoliza(
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
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_ENDOSOS_PARA_WS_AUTOS", params);
		Map<String,Object>procResult   = ejecutaSP(new ObtieneEndososPoliza(getDataSource()),params);
		List<Map<String,String>> lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No se encontraron endosos");
		}
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_ENDOSOS_PARA_WS_AUTOS", params, lista);
		return lista;
	}
	
	protected class ObtieneEndososPoliza extends StoredProcedure
	{
		protected ObtieneEndososPoliza(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_ENDOSOS_PARA_WS_AUTOS");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"TIPOEND"
					,"NUMEND"
					,"SUCURSAL"
					,"RAMO"
					,"NMPOLIEX"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String,String>> obtieneDatosEndososB(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsuplem_i" , nmsuplem);
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_ENDOSOS_B", params);
		Map<String,Object>procResult   = ejecutaSP(new ObtieneDatosEndososB(getDataSource()),params);
		List<Map<String,String>> lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No se encontraron endosos");
		}
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_ENDOSOS_B", params, lista);
		return lista;
	}
	
	protected class ObtieneDatosEndososB extends StoredProcedure
	{
		protected ObtieneDatosEndososB(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_ENDOSOS_B");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"SUCURSAL"
					,"RAMO"
					,"NMPOLIEX"
					,"NUMEND"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public boolean esMismaPersonaContratante(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			)
	{
		boolean esMismo = false;
		try
		{
			Map<String,String> params = new LinkedHashMap<String,String>();
			params.put("cdunieco" , cdunieco);
			params.put("cdramo"   , cdramo);
			params.put("estado"   , estado);
			params.put("nmpoliza" , nmpoliza);
			params.put("nmsituac" , nmsituac);
			Map<String,Object>procRes = ejecutaSP(new EsMismaPersonaContratante(getDataSource()),params);
			esMismo = "S".equals((String)procRes.get("pv_contrat_o"));
			logger.debug(Utils.log("Es el contratante: ",esMismo));
		}
		catch(Exception ex)
		{
			logger.error("Error al verificar si un inciso es el mismo que el contratante, inofensivo, regresa false",ex);
			esMismo = false;
		}
		return esMismo;
	}
	
	protected class EsMismaPersonaContratante extends StoredProcedure
	{
		protected EsMismaPersonaContratante(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_VERIFICA_SITUAC_CONTRATANTE");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_contrat_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarCdtipsitInciso1(
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
		Map<String,Object> procRes  = ejecutaSP(new RecuperarCdtipsitInciso1(getDataSource()),params);
		String             cdtipsit = (String)procRes.get("pv_cdtipsit_o");
		if(StringUtils.isBlank(cdtipsit))
		{
			throw new ApplicationException("No hay tipo de situaci\u00F3n para el primero inciso");
		}
		logger.debug(Utils.log("****** PKG_CONSULTA.P_GET_CDTIPSIT_PRIMER_INCISO {"
				,cdunieco , ","
				,cdramo   , ","
				,estado   , ","
				,nmpoliza , "}="
				,cdtipsit
				));
		return cdtipsit;
	}
	
	protected class RecuperarCdtipsitInciso1 extends StoredProcedure
	{
		protected RecuperarCdtipsitInciso1(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_CDTIPSIT_PRIMER_INCISO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdtipsit_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String> recuperarNmsuplemNsuplogiEndosoValidando(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdtipsup" , cdtipsup);
		Map<String,Object> procRes  = ejecutaSP(new RecuperarNmsuplemEndosoValidando(getDataSource()),params);
		String             error    = (String)procRes.get("pv_dserror_o");
		String             nmsuplem = (String)procRes.get("pv_nmsuplem_o");
		String             nsuplogi = (String)procRes.get("pv_nsuplogi_o");
		if(StringUtils.isNotBlank(error))
		{
			throw new ApplicationException(error);
		}
		Map<String,String> res = new HashMap<String,String>();
		res.put("nmsuplem" , nmsuplem);
		res.put("nsuplogi" , nsuplogi);
		logger.debug("****** PKG_CONSULTA.P_GET_NMSUPLEM_ENDOSO_VAL res={}",res);
		return res;
	}
	
	protected class RecuperarNmsuplemEndosoValidando extends StoredProcedure
	{
		protected RecuperarNmsuplemEndosoValidando(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_NMSUPLEM_ENDOSO_VAL");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nsuplogi_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_dserror_o"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void sacaEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nsuplogi
			,String nmsuplem
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nsuplogi" , nsuplogi);
		params.put("nmsuplem" , nmsuplem);
		ejecutaSP(new SacaEndoso(getDataSource()),params);
	}
	
	protected class SacaEndoso extends StoredProcedure
	{
		protected SacaEndoso(DataSource dataSource)
		{
			super(dataSource, "P_SACAENDOSO_TMP");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nsuplogi" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> recuperarCorreoElectronicoSucursal(
			String codigo
			,String cdunieco
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("codigo"   , codigo);
		params.put("cdunieco" , cdunieco);
		Map<String,Object>       procRes = ejecutaSP(new RecuperarCorreoElectronicoSucursal(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_OBTIENE_EMAIL", params, lista);
		return lista;
	}
	
	protected class RecuperarCorreoElectronicoSucursal extends StoredProcedure
	{
		protected RecuperarCorreoElectronicoSucursal(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_OBTIENE_EMAIL");
			declareParameter(new SqlParameter("codigo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(new String[]{"DESCRIPL"})));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	
	
	
	@Override
	public void validaDuplicidadParentesco(String cdunieco, String cdramo, String estado, 
			String nmpoliza, String nmsuplem) throws Exception {
		
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsuplem_i" , nmsuplem);
		ejecutaSP(new ValidaDuplicidadParentescoSP(getDataSource()),params);
	}
	
	protected class ValidaDuplicidadParentescoSP extends StoredProcedure {
		protected ValidaDuplicidadParentescoSP(DataSource dataSource) {
			super(dataSource, "PKG_SATELITES2.P_VALIDA_DUP_PARENTESCO");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String regeneraSuplemento(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nsuplogi
			,Date fecha
			)throws Exception
	{
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("nsuplogi" , nsuplogi);
		params.put("fecha"    , fecha);
		Map<String,Object> procRes = ejecutaSP(new RegeneraSuplemento(getDataSource()),params);
		String nmsuplemNuevo = (String)procRes.get("pv_nmsuplem_o");
		if(StringUtils.isBlank(nmsuplemNuevo))
		{
			throw new ApplicationException("No se gener\u00F3 el suplemento nuevo");
		}
		logger.debug("\nNuevo suplemento: {}",nmsuplemNuevo);
		return nmsuplemNuevo;
	}
	
	protected class RegeneraSuplemento extends StoredProcedure {
		protected RegeneraSuplemento(DataSource dataSource) {
			super(dataSource, "PKG_ENDOSOS.P_REGENERA_SUPLEMENTO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("nsuplogi" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("fecha"    , OracleTypes.DATE));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String, String>> obtieneRecibosDespagados(String cdunieco, String cdramo ,String estado ,String nmpoliza) throws Exception
			{
		Map<String,String>params = new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i" , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtieneRecibosDespagados(this.getDataSource()), params);
		return (List<Map<String, String>>) resultadoMap.get("pv_registro_o");
			}
	protected class ObtieneRecibosDespagados extends StoredProcedure
	{
		String[] columnas=new String[]{
			"NMRECIBO","NMSUPLEM","NMIMPRES","CDDEVCIA","CDGESTOR","PTIMPORT","FEEMISIO","FEINICIO","FEFINAL","FEESTADO"
		};
		
		protected ObtieneRecibosDespagados(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_REC_REHAB_DESPAG_X_POL");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"       ,OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void guardarEndosoNombreRFCFecha(Map<String, Object> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n******************************************")
				.append("\n****** PKG_SATELITES.P_MOV_MPERSONA ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************")
				.toString()
				);
		this.ejecutaSP(new GuardarEndosoNombreRFCFecha(this.getDataSource()), params);
	}
	
	protected class GuardarEndosoNombreRFCFecha extends StoredProcedure
	{
		protected GuardarEndosoNombreRFCFecha(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_MOV_MPERSONA");
			declareParameter(new SqlParameter("pv_cdperson_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipide_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdideper_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsnombre_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipper_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otfisjur_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otsexo_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fenacimi_i"  , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cdrfc_i"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsemail_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsnombre1_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsapellido_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsapellido1_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feingreso_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdnacion_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_canaling_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_conducto_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptcumupr_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_residencia_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nongrata_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdideext_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdestciv_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsucemi_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdusuario_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_accion_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String, String>> obtieneInformacionCliente(String cdunieco,String cdramo,String estado,
			String nmpoliza,String nmsuplem,String motivo) throws Exception {
		Map<String,String>params = new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_idmotivo_i" , motivo);
		
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtieneInformacionCliente(this.getDataSource()), params);
		return (List<Map<String, String>>) resultadoMap.get("pv_registro_o");
			}
	protected class  ObtieneInformacionCliente extends StoredProcedure
	{
		String[] columnas=new String[]{
			"IDMOTIVO",		"NUMSUC",		"RAMO",			"POLIZA",			"FENDOSO",			"CLIENTE",
			"NOMCLI",		"APEPAT",		"APEMAT",		"RASONSOCIAL",		"FECNAC",			"TIPPER",
			"RFCCLI",		"CVEELE",		"CURPCLI",		"CALLECLI",			"NUMCLI",			"NUMINT",
			"CODPOS",		"COLONIA",		"MUNICIPIO",	"CVEEDO",			"POBLACION",		"TELEFONO1",
			"TELEFONO2",	"TELEFONO3"
		};
		
		protected ObtieneInformacionCliente(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_DAT_CAM_NOM_RFC_FECNAC");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_idmotivo_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<Map<String, String>> CambioClientenombreRFCfechaNacimiento(Map<String, String> params)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void actualizaMpolisitNuevaVigencia(String cdunieco, String cdramo, String estado, String nmpoliza, 
			String nmsuplem, String feefecto) throws Exception {
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i"  , cdramo);
		params.put("pv_estado_i"  , estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		params.put("pv_feefecto_i", feefecto);
		ejecutaSP(new ActualizaMpolisitNuevaVigenciaSP(getDataSource()), params);
	}
	
	protected class ActualizaMpolisitNuevaVigenciaSP extends StoredProcedure {
		protected ActualizaMpolisitNuevaVigenciaSP(DataSource dataSource) {
			super(dataSource, "PKG_SATELITES2.P_ACTUALIZA_MPOLISIT_NVA_VIG");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_feefecto_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o" , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> obtenerInfoFamiliaEndoso(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n****************************************************")
				.append("\n****** PKG_CONSULTA.P_Get_Datos_InfoFamEndoso ******")
				.append("\n****** params=").append(params)
				.append("\n****************************************************")
				.toString()
				);
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerInfoFamiliaEndoso(this.getDataSource()), params);
		return (List<Map<String,String>>) resultadoMap.get("PV_REGISTRO_O");
	}
	
	protected class ObtenerInfoFamiliaEndoso extends StoredProcedure
	{
		
		protected ObtenerInfoFamiliaEndoso(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_Get_Datos_InfoFamEndoso");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_ntramite_i" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"CDTIPSIT"  , "EXISTEREGISTRO"
                    ,"CDAGENTE" , "NTRAMITE"
                    ,"PRODUCTO"
			};
			declareParameter(new SqlOutParameter("PV_REGISTRO_O" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String regeneraSuplementoFamiliaEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nsuplogi
			,Date fecha
			)throws Exception
	{
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("nsuplogi" , nsuplogi);
		params.put("fecha"    , fecha);
		Map<String,Object> procRes = ejecutaSP(new RegeneraSuplementoFamiliaEndoso(getDataSource()),params);
		String nmsuplemNuevo = (String)procRes.get("pv_nmsuplem_o");
		if(StringUtils.isBlank(nmsuplemNuevo))
		{
			throw new ApplicationException("No se gener\u00F3 el suplemento nuevo");
		}
		logger.debug("\nNuevo suplemento: {}",nmsuplemNuevo);
		return nmsuplemNuevo;
	}
	
	protected class RegeneraSuplementoFamiliaEndoso extends StoredProcedure {
		protected RegeneraSuplementoFamiliaEndoso(DataSource dataSource) {
			super(dataSource, "P_REGENERA_SUPLEMENTOALTA");
			//super(dataSource, "PKG_ENDOSOS.P_REGENERA_SUPLEMENTOALTA");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("nsuplogi" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("fecha"    , OracleTypes.DATE));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void clonarGarantiaCapitales(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String cdgrupo
			,String cdplan
			,String sexo
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_nmsituac_i" , nmsituac);
		params.put("pv_cdgrupo_i" , cdgrupo);
		params.put("pv_cdplan_i" , cdplan);
		params.put("pv_sexo_i" , sexo);
		logger.debug(
				new StringBuilder()
				.append("\n***********************************************************")
				.append("\n****** PKG_SATELITES2.P_CLONAR_GARANTIAS_Y_CAPITALES ******")
				.append("\n****** params=").append(params)
				.append("\n***********************************************************")
				.toString()
				);
		this.ejecutaSP(new ClonarGarantiaCapitales(this.getDataSource()), Utils.ponFechas(params));
	}
	
	protected class ClonarGarantiaCapitales extends StoredProcedure
	{
		protected ClonarGarantiaCapitales(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_CLONAR_GARANTIAS_Y_CAPITALES");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgrupo_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdplan_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_sexo_i"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o" , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizaExtraprimaValosit2(Map<String, String> params) throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************************")
				.append("\n****** PKG_ENDOSOS.P_INS_NEW_EXTRAPRIMA_TVALOSIT2 ******")
				.append("\n****** params=").append(params)
				.append("\n*******************************************************")
				.toString()
				);
		this.ejecutaSP(new ActualizaExtraprimaValosit2(this.getDataSource()), params);
	}
	
	protected class ActualizaExtraprimaValosit2 extends StoredProcedure
	{
		protected ActualizaExtraprimaValosit2(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_INS_NEW_EXTRAPRIMA_TVALOSIT2");
			declareParameter(new SqlParameter("pv_cdunieco_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_extraprima_sob_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_extraprima_ocu_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
}