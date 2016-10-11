package mx.com.gseguros.portal.renovacion.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.model.DocumentoReciboParaMostrarDTO;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.renovacion.dao.RenovacionDAO;
import mx.com.gseguros.utils.Utils;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.support.oracle.SqlArrayValue;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class RenovacionDAOImpl extends AbstractManagerDAO implements RenovacionDAO
{
	private static final Logger logger = Logger.getLogger(RenovacionDAOImpl.class);
	
	@Override
	public List<Map<String,String>>buscarPolizasRenovables(String cdunieco,String cdramo,String anio,String mes)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("anio"     , anio);
		params.put("mes"      , mes);
		logger.debug(
				new StringBuilder()
				.append("\n***************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_POLIZAS_RENOVABLES ******")
				.append("\n****** params=").append(params)
				.append("\n***************************************************")
				.toString()
				);
		Map<String,Object>procedureResult=ejecutaSP(new BuscarPolizasRenovables(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class BuscarPolizasRenovables extends StoredProcedure
	{
		protected BuscarPolizasRenovables(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_POLIZAS_RENOVABLES");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("anio"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("mes"      , OracleTypes.VARCHAR));
			String[] columnas=new String[]{
					"cdunieco"
					,"cdramo"
					,"estado"
					,"nmpoliza"
					,"feefecto"
					,"feproren"
					,"cliente"
					,"anio"
					,"mes"
					,"cdtipopc"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void marcarPoliza(String anio
			,String mes
			,String cdtipopc
			,String cdtipacc
			,String cdunieco
			,String cdramo
			,String nmpoliza
			,Date   feemisio
			,String swrenova
			,String swaproba
			,String nmsituac
			,String cducreno)throws Exception
	{
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("anio"     , anio);
		params.put("mes"      , mes);
		params.put("cdtipopc" , cdtipopc);
		params.put("cdtipacc" , cdtipacc);
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("nmpoliza" , nmpoliza);
		params.put("feemisio" , feemisio);
		params.put("swrenova" , swrenova);
		params.put("swaproba" , swaproba);
		params.put("nmsituac" , nmsituac);
		params.put("cducreno" , cducreno);
		logger.debug(
				new StringBuilder()
				.append("\n****************************************")
				.append("\n****** PKG_RENOVA.P_MARCAR_POLIZA ******")
				.append("\n****** params=").append(params)
				.append("\n****************************************")
				.toString()
				);
		ejecutaSP(new MarcarPoliza(getDataSource()),params);
	}
	
	protected class MarcarPoliza extends StoredProcedure
	{
		protected MarcarPoliza(DataSource dataSource)
		{
			super(dataSource, "PKG_RENOVA.P_MARCAR_POLIZA");
			declareParameter(new SqlParameter("anio"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("mes"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipopc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipacc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feemisio" , OracleTypes.DATE));
			declareParameter(new SqlParameter("swrenova" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swaproba" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cducreno" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> renovarPolizas(String cdusuari, String anio, String mes, String cdtipopc, String cdsisrol) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdusuari" , cdusuari);
		params.put("anio"     , anio);
		params.put("mes"      , mes);
		params.put("cdtipopc" , cdtipopc);
		params.put("cdsisrol" , cdsisrol);
		Map<String,Object> procedureResult        = ejecutaSP(new RenovarPolizas(getDataSource()),params);
		List<Map<String,String>> polizasRenovadas = (List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(polizasRenovadas==null||polizasRenovadas.size()==0)
		{
			throw new ApplicationException("No se renovaron polizas");
		}
		logger.info(new StringBuilder("renovarPolizas lista size=").append(polizasRenovadas.size()).toString());
		return polizasRenovadas;
	}
	
	protected class RenovarPolizas extends StoredProcedure
	{
		protected RenovarPolizas(DataSource dataSource)
		{
			super(dataSource, "PKG_RENOVA.P_RENUEVA_X_LISTA_POLIZAS");
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("anio"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("mes"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipopc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"cdunieco"
					,"cdramo"
					,"estado"
					,"nmpoliza"
					,"nmsuplem"
					,"ntramite"
					,"nmanno"
					,"nmmes"
					,"cdtipopc"
					,"uniecoant"
					,"nmpolant"
					,"SWDXN"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizaRenovacionDocumentos(
			String anio
			,String mes
			,String cdtipopc
			,String cdunieco
			,String cdramo
			,String nmpoliza)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("anio"     , anio);
		params.put("mes"      , mes);
		params.put("cdtipopc" , cdtipopc);
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("nmpoliza" , nmpoliza);
		logger.debug(
				new StringBuilder()
				.append("\n******************************************************")
				.append("\n****** PKG_RENOVA.P_ACTUALIZA_TCARTERA_SWIMPDOC ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************************")
				.toString()
				);
		ejecutaSP(new ActualizaRenovacionDocumentos(getDataSource()),params);
	}
	
	protected class ActualizaRenovacionDocumentos extends StoredProcedure
	{
		protected ActualizaRenovacionDocumentos(DataSource dataSource)
		{
			super(dataSource, "PKG_RENOVA.P_ACTUALIZA_TCARTERA_SWIMPDOC");
			declareParameter(new SqlParameter("anio"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("mes"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipopc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarDocumentosSubidosPorUsuario(String cdunieco,String cdramo,String estado,String nmpoliza)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_EXPEDIENTE_POLIZA ******")
				.append("\n****** params=").append(params)
				.append("\n**************************************************") 
				.toString()
				);
		Map<String,Object>procResult = ejecutaSP(new CargarDocumentosSubidosPorUsuario(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_EXPEDIENTE_POLIZA ******")
				.append("\n****** result=").append(lista)
				.append("\n**************************************************") 
				.toString()
				);
		return lista;
	}
	
	protected class CargarDocumentosSubidosPorUsuario extends StoredProcedure
	{
		protected CargarDocumentosSubidosPorUsuario(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_EXPEDIENTE_POLIZA");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"cddocume"
					,"ntramite"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> busquedaRenovacionIndividual(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_RENOVACION_IND.P_GET_DATOS_POLIZA ******")
				.append("\n****** params=").append(params)
				.append("\n**************************************************") 
				.toString()
				);
		Map<String,Object>procResult = ejecutaSP(new BusquedaRenovacionIndividual(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_RENOVACION_IND.P_GET_DATOS_POLIZA ******")
				.append("\n****** result=").append(lista)
				.append("\n**************************************************") 
				.toString()
				);
		return lista;
	}
	
	protected class BusquedaRenovacionIndividual extends StoredProcedure
	{
		protected BusquedaRenovacionIndividual(DataSource dataSource)
		{
			super(dataSource, "PKG_RENOVACION_IND.P_GET_DATOS_POLIZA");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
						"cdperson",
						"contratante",
						"dsdomici",
						"nmtelefo",
						"cdpostal",
//						"otpoblac",
						"cdpais",
//						"otpiso",
						"nmnumero",
//						"cdprovin",
//						"dszona",
//						"dsdelega",
//						"cdregion",
//						"cdsector",
//						"cdmanzan",
//						"cdedific",
//						"ruta_cart",
//						"puntoent",
//						"chkdigit",
//						"nmapto",
//						"cdkilome",
//						"instespe",
//						"dsdirecc",
						"nmnumint",
						"cdedo",
						"cdmunici",
						"cdcoloni",
						"swactivo",
						"cdunieco",
						"cdramo",
						"nmpoliza",
//						"cdusrcre",
//						"swrecupera",
						"feefecto",
						"feproren",
						"renovada",
						"pagada",
						"dsplan",
						"antiguedad",
						"aseg_edad_val", 
						"cdmoneda",
						"primas",
						"descuento",
						"extra_prima"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> busquedaRenovacionIndividualMasiva(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String cdtipsit,
			String fecini,
			String fecfin,
			String status,
			String cdperson,
			String retenedora
			)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco"  , cdunieco);
		params.put("cdramo"    , cdramo);
		params.put("estado"    , estado);
		params.put("fecini"    , fecini);
		params.put("fecfin"    , fecfin);
		params.put("cdperson"  , cdperson);
		params.put("retenedora", retenedora);
		params.put("use_exec"  , "N");
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_RENOVACION_IND.P_GET_DATOS_POL_MAS ******")
				.append("\n****** params=").append(params)
				.append("\n**************************************************") 
				.toString()
				);
		Map<String,Object>procResult = ejecutaSP(new BusquedaRenovacionIndividualMasiva(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_RENOVACION_IND.P_GET_DATOS_POL_MAS ******")
				.append("\n****** result=").append(lista)
				.append("\n**************************************************") 
				.toString()
				);
		return lista;
	}
	
	protected class BusquedaRenovacionIndividualMasiva extends StoredProcedure
	{
		protected BusquedaRenovacionIndividualMasiva(DataSource dataSource)
		{
//			super(dataSource, "PKG_RENOVACION_IND.P_GET_DATOS_POLIZA_MASIVA");
			super(dataSource, "PKG_RENOVACION_IND.P_GET_DATOS_POL_MAS");
			declareParameter(new SqlParameter("cdunieco"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("fecini"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("fecfin"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperson"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("retenedora" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("use_exec"   , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
						"cdperson",
						"contratante",
						"dsdomici",
						"nmtelefo",
						"cdpostal",
						"cdpais",
						"nmnumero",
						"nmnumint",
						"cdedo",
						"cdmunici",
						"cdcoloni",
						"swactivo",
						"cdunieco",
						"cdramo",
						"nmpoliza",
						"feefecto",
						"feproren",
						"renovada",
						"pagada",
						"dsplan",
						"antiguedad",
						"aseg_edad_val",
						//"cdmoneda",
						"primas",
						"descuento",
						"extra_prima"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String> renuevaPolizaIndividual(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String estadoNew,
			String usuario)throws Exception
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("estadoNew", estadoNew);
		params.put("usuario"  , usuario);
		params.put("cdperpag" , null);
		params.put("cdcontra" , null);
		logger.debug(
				new StringBuilder()
				.append("\n******************************************************")
				.append("\n****** PKG_RENOVACION_IND.P_GENERAR_TRAMITE ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************************")
				.toString()
				);	
		Map<String,Object> procResult = ejecutaSP(new RenuevaPolizaIndividual(getDataSource()),params);
		Map<String,String> map = new HashMap<String, String>();
		map.put("cdtipflu", String.valueOf(procResult.get("pv_cdtipflu_o")));
		map.put("cdflujomc", String.valueOf(procResult.get("pv_cdflujomc_o")));
		map.put("status", String.valueOf(procResult.get("pv_status_o")));
		map.put("ntramite", String.valueOf(procResult.get("pv_ntramite_o")));
		map.put("nmsuplem", String.valueOf(procResult.get("pv_nmsupnew_o")));
		map.put("nmpolnew", String.valueOf(procResult.get("pv_nmpolnew_o")));		
		logger.debug(
				new StringBuilder()
				.append("\n******************************************************")
				.append("\n****** mapa=").append(map)
				.append("\n******************************************************")
				.toString()
				);
		return map;
	}
	
	protected class RenuevaPolizaIndividual extends StoredProcedure
	{
		protected RenuevaPolizaIndividual(DataSource dataSource)
		{
			super(dataSource, "PKG_RENOVACION_IND.P_GENERAR_TRAMITE");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("usuario"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperpag" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdcontra" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estadoNew", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmpolnew_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_nmsupnew_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_ntramite_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_status_o"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdtipflu_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_cdflujomc_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> obtenerPolizaCdpersonTramite(String ntramite)throws Exception{
		Map<String,String>params=new HashMap<String,String>();
		params.put("ntramite" , ntramite);
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_RENOVACION_IND.P_GET_POLIZA_CDPERSON ******")
				.append("\n****** params=").append(params)
				.append("\n**************************************************") 
				.toString()
				);
		Map<String,Object> procResult = ejecutaSP(new ObtenerPolizaCdpersonTramite(getDataSource()),params);
		Map<String, String> result = new HashMap<String, String>();
		List<Map<String,String>> lista = new ArrayList<Map<String,String>>();
		if(!procResult.isEmpty() || procResult.size() > 0){			
			result.put("cdunieco", String.valueOf(procResult.get("pv_cdunieco_o")));
			result.put("cdramo"  , String.valueOf(procResult.get("pv_cdramo_o")));
			result.put("estado"  , String.valueOf(procResult.get("pv_estado_o")));
			result.put("nmpoliza", String.valueOf(procResult.get("pv_nmpoliza_o")));
			result.put("cdpersoncon", String.valueOf(procResult.get("pv_cdperson_o")));
			lista.add(result);
		}
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_RENOVACION_IND.P_GET_POLIZA_CDPERSON ******")
				.append("\n****** result=").append(lista)
				.append("\n**************************************************") 
				.toString()
				);
		return lista;
	}
	
	protected class ObtenerPolizaCdpersonTramite extends StoredProcedure
	{
		protected ObtenerPolizaCdpersonTramite(DataSource dataSource)
		{
			super(dataSource, "PKG_RENOVACION_IND.P_GET_POLIZA_CDPERSON");
			declareParameter(new SqlParameter("ntramite"         , OracleTypes.VARCHAR));			
			declareParameter(new SqlOutParameter("pv_cdunieco_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdramo_o"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_estado_o"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmpoliza_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdperson_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> confirmarPolizaIndividual(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem,
			String ntramite)throws Exception
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("ntramite" , ntramite);
		Map<String,Object> procedureResult        = ejecutaSP(new ConfirmarPolizaIndividual(getDataSource()),params);
		logger.debug(new StringBuilder().append("\n****** procedureResult=").append(procedureResult).toString());	
		List<Map<String,String>> polizasRenovadas = (List<Map<String,String>>) procedureResult.get("pv_registro_o");
		if(polizasRenovadas==null||polizasRenovadas.size()==0)
		{
			throw new ApplicationException("No se renovaron polizas");
		}
		logger.debug(
				new StringBuilder()
				.append("\n******************************************************")
				.append("\n****** PKG_RENOVACION_IND.P_RENOVAR_POLIZA ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************************")
				.toString()
				);	
		return polizasRenovadas;
	}
	
	protected class ConfirmarPolizaIndividual extends StoredProcedure
	{
		protected ConfirmarPolizaIndividual(DataSource dataSource)
		{
			super(dataSource, "PKG_RENOVACION_IND.P_RENOVAR_POLIZA");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"cdunieco"
					,"cdramo"
					,"estado"
					,"nmpoliza"
					,"nmsuplem"
					,"ntramite"
					,"nmanno"
					,"nmmes"
					,"cdtipopc"
					,"uniecoant"
					,"nmpolant"
					,"SWDXN"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void generaTcartera(
			String cdunieco,
			String cdramo,
			String nmpoliza,
			String feefecto,
			String feefecto_ant,
			String nmsuplem,
			String cdagente,
			String cdperpag,
			String cdcontra,
			String cdmoneda)throws Exception
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("cdunieco" 	  , cdunieco);
		params.put("cdramo"   	  , cdramo);
		params.put("nmpoliza" 	  , nmpoliza);
		params.put("feefecto" 	  , feefecto);
		params.put("feefecto_ant" , feefecto_ant);
		params.put("nmsuplem" 	  , nmsuplem);
		params.put("cdagente" 	  , cdagente);
		params.put("cdperpag" 	  , cdperpag);
		params.put("cdcontra" 	  , cdcontra);
		params.put("cdmoneda" 	  , cdmoneda);
		logger.debug(
				new StringBuilder()
				.append("\n******************************************************")
				.append("\n****** PKG_RENOVACION_IND.P_GENERA_TCARTERA ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************************")
				.toString()
				);	
		ejecutaSP(new GeneraTcartera(getDataSource()),params);
	}
	
	protected class GeneraTcartera extends StoredProcedure
	{
		protected GeneraTcartera(DataSource dataSource)
		{
			super(dataSource, "PKG_RENOVACION_IND.P_GENERA_TCARTERA");
			declareParameter(new SqlParameter("cdunieco" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feefecto" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feefecto_ant" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdagente" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperpag" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdcontra" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdmoneda" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizaContratanteFormaPago(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String cdperpag,
			String cdcontra)throws Exception
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("cdunieco" 	  , cdunieco);
		params.put("cdramo"   	  , cdramo);
		params.put("estado"   	  , estado);
		params.put("nmpoliza" 	  , nmpoliza);
		params.put("cdperpag" 	  , cdperpag);
		params.put("cdcontra" 	  , cdcontra);
		logger.debug(
				new StringBuilder()
				.append("\n******************************************************")
				.append("\n****** PKG_RENOVACION_IND.P_ACTUALIZA_MSELCTPI ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************************")
				.toString()
				);	
		ejecutaSP(new ActualizaContratanteFormaPago(getDataSource()),params);
	}
	
	protected class ActualizaContratanteFormaPago extends StoredProcedure
	{
		protected ActualizaContratanteFormaPago(DataSource dataSource)
		{
			super(dataSource, "PKG_RENOVACION_IND.P_ACTUALIZA_MSELCTPI ");
			declareParameter(new SqlParameter("cdunieco" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" 	 , OracleTypes.VARCHAR));			
			declareParameter(new SqlParameter("cdperpag" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdcontra" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String, String> confirmarTramite(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String cdperpag,
			String feefecto)throws Exception
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdperpag" , cdperpag);
		params.put("feefecto" , feefecto);
		logger.debug(
				new StringBuilder()
				.append("\n******************************************************")
				.append("\n****** PKG_RENOVACION_IND.P_CONFIRMAR_TRAMITE ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************************")
				.toString()
				);	
		Map<String,Object> procResult = ejecutaSP(new ConfirmarTramite(getDataSource()),params);
		Map<String,String> result	  = new HashMap<String, String>(); 
		result.put("nmpolizaNew", String.valueOf(procResult.get("pv_nmpolnew_o")));
		result.put("nmsuplemNew", String.valueOf(procResult.get("pv_nmsuplem_o")));
		logger.debug(
				new StringBuilder()
				.append("\n******************************************************")
				.append("\n****** result=").append(result)
				.append("\n******************************************************")
				.toString()
				);
		return result;
	}
	
	protected class ConfirmarTramite extends StoredProcedure
	{
		protected ConfirmarTramite(DataSource dataSource)
		{
			super(dataSource, "PKG_RENOVACION_IND.P_CONFIRMAR_TRAMITE");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperpag" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feefecto" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmpolnew_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void renovarPolizasMasivasIndividuales(List<Map<String, String>> slist)throws Exception{
		String[][] array = new String[slist.size()][];
		int        i     = 0;
		for(Map<String, String> map : slist){
			array[i++]	= new String[] { 
					map.get("cdunieco"), 
					map.get("cdramo"), 
					"M",//map.get("estado"), 
					map.get("nmpoliza"),
					"123456789012345678"//map.get("nmsuplem")
					};
		}
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("array" , new SqlArrayValue(array));
		Utils.debugProcedure(logger, "PKG_RENOVACION_IND.P_RENOVAR_POL_MAS", params);
		ejecutaSP(new RenovarPolizasMasivasIndividuales(getDataSource()),params);
	}
	
	protected class RenovarPolizasMasivasIndividuales extends StoredProcedure
	{
		protected RenovarPolizasMasivasIndividuales(DataSource dataSource)
		{
			super(dataSource,"PKG_RENOVACION_IND.P_RENOVAR_POL_MAS");
			declareParameter(new SqlParameter("array" , OracleTypes.ARRAY , "LISTA_LISTAS_VARCHAR2"));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> obtenerCondicionesRenovacionprogramada(
			String anio,
			String mes)throws Exception
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("anio" , anio);
		params.put("mes"  , mes);
		Map<String,Object> procedureResult        = ejecutaSP(new ObtenerCondicionesRenovacionprogramada(getDataSource()),params);
		logger.debug(new StringBuilder().append("\n****** procedureResult=").append(procedureResult).toString());	
		List<Map<String,String>> polizasRenovadas = (List<Map<String,String>>) procedureResult.get("pv_registro_o");
		if(polizasRenovadas==null||polizasRenovadas.size()==0)
		{
			throw new ApplicationException("No se renovaron polizas");
		}
		logger.debug(
				new StringBuilder()
				.append("\n******************************************************")
				.append("\n****** PKG_RENOVACION_IND.P_GET_TRENOVA_EXC ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************************")
				.toString()
				);	
		return polizasRenovadas;
	}
	
	protected class ObtenerCondicionesRenovacionprogramada extends StoredProcedure
	{
		protected ObtenerCondicionesRenovacionprogramada(DataSource dataSource)
		{
			super(dataSource, "PKG_RENOVACION_IND.P_GET_TRENOVA_EXC");
			declareParameter(new SqlParameter("anio" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("mes"  , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"anio"
					,"mes"
					,"criterio"
					,"campo"
					,"valor"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoCondicionesRenovacionProgramada(
			String anio,
			String mes,
			String criterio,
			String campo,
			String valor,
			String valor2,
			String operacion)throws Exception
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("anio" 	  , anio);
		params.put("mes"   	  , mes);
		params.put("criterio" , criterio);
		params.put("campo" 	  , campo);
		params.put("valor" 	  , valor);
		params.put("valor2"   , valor2);
		params.put("operacion", operacion);
		logger.debug(
				new StringBuilder()
				.append("\n******************************************************")
				.append("\n****** PKG_RENOVACION_IND.P_MOV_TRENOVA_EXC ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************************")
				.toString()
				);	
		ejecutaSP(new InsertaCondicionesRenovacionProgramada(getDataSource()),params);
	}
	
	protected class InsertaCondicionesRenovacionProgramada extends StoredProcedure
	{
		protected InsertaCondicionesRenovacionProgramada(DataSource dataSource)
		{
			super(dataSource, "PKG_RENOVACION_IND.P_MOV_TRENOVA_EXC ");
			declareParameter(new SqlParameter("anio" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("mes"   	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("criterio" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("campo" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("valor" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("valor2" 	 , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("operacion", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
}