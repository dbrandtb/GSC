package mx.com.gseguros.portal.cotizacion.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.ObtieneTatrigarMapper;
import mx.com.gseguros.portal.cotizacion.model.ObtieneTatripolMapper;
import mx.com.gseguros.portal.cotizacion.model.ObtieneTatrisitMapper;
import mx.com.gseguros.portal.cotizacion.model.PInsertaTbasvalsitDTO;
import mx.com.gseguros.portal.cotizacion.model.PInsertaTconvalsitDTO;
import mx.com.gseguros.portal.cotizacion.model.PMovMpolisitDTO;
import mx.com.gseguros.portal.cotizacion.model.PMovTvalositDTO;
import mx.com.gseguros.portal.cotizacion.model.ParametroCotizacion;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.utils.Utils;
import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.jdbc.support.oracle.SqlArrayValue;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class CotizacionDAOImpl extends AbstractManagerDAO implements CotizacionDAO
{
	private final static Logger logger = Logger.getLogger(CotizacionDAOImpl.class);
	
	@Override
	public void movimientoTvalogarGrupoCompleto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,String cdgrupo
			,String cdgarant
			,String status
			,Map<String,String>valores
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdgrupo"  , cdgrupo);
		params.put("cdgarant" , cdgarant);
		params.put("status"   , status);
		
		for(int i=1;i<=640;i++)
		{
			params.put(new StringBuilder("otvalor").append(StringUtils.leftPad(String.valueOf(i),3,"0")).toString(),null);
		}
		
		params.putAll(valores);
		
		logger.debug(
				new StringBuilder()
				.append("\n******************************************************")
				.append("\n****** PKG_SATELITES.P_MOV_TVALOGAR_GRUPO_DINAM ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************************")
				.toString()
				);
		ejecutaSP(new MovimientoTvalogarGrupoCompleto(getDataSource()), params);
	}
	
	protected class MovimientoTvalogarGrupoCompleto extends StoredProcedure
	{
		protected MovimientoTvalogarGrupoCompleto(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_MOV_TVALOGAR_GRUPO_DINAM");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgarant" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			for(int i=1;i<=640;i++)
			{
				declareParameter(
						new SqlParameter
						(
								new StringBuilder("otvalor").append(StringUtils.leftPad(String.valueOf(i),3,"0")).toString()
								,OracleTypes.VARCHAR
						)
				);
			}
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoTvalogarGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,String cdgrupo
			,String cdgarant
			,String status
			,String cdatribu
			,String valor
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdgrupo"  , cdgrupo);
		params.put("cdgarant" , cdgarant);
		params.put("status"   , status);
		params.put("cdatribu" , cdatribu);
		params.put("valor"    , valor);
		logger.debug(
				new StringBuilder()
				.append("\n************************************************")
				.append("\n****** PKG_SATELITES.P_MOV_TVALOGAR_GRUPO ******")
				.append("\n****** params=").append(params)
				.append("\n************************************************")
				.toString()
				);
		ejecutaSP(new MovimientoTvalogarGrupo(getDataSource()), params);
	}
	
	protected class MovimientoTvalogarGrupo extends StoredProcedure
	{
		protected MovimientoTvalogarGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_MOV_TVALOGAR_GRUPO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgarant" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdatribu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("valor"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoMpolisitTvalositGrupo(Map<String,String>params)throws Exception
	{
		ejecutaSP(new MovimientoMpolisitTvalositGrupo(getDataSource()), params);
	}
	
	protected class MovimientoMpolisitTvalositGrupo extends StoredProcedure
	{
		protected MovimientoMpolisitTvalositGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_ACTUALIZA_MPOLISIT_TVALOSIT");
			declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor06" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor07" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor08" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor09" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor10" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor11" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor12" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor13" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor16" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoMpoligarGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,String cdgrupo
			,String cdgarant
			,String status
			,String cdmoneda
			,String accion
			,String respvalogar
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco"    , cdunieco);
		params.put("cdramo"      , cdramo);
		params.put("estado"      , estado);
		params.put("nmpoliza"    , nmpoliza);
		params.put("nmsuplem"    , nmsuplem);
		params.put("cdtipsit"    , cdtipsit);
		params.put("cdgrupo"     , cdgrupo);
		params.put("cdgarant"    , cdgarant);
		params.put("status"      , status);
		params.put("cdmoneda"    , cdmoneda);
		params.put("accion"      , accion);
		params.put("respvalogar" , respvalogar);
		logger.debug(
				new StringBuilder()
				.append("\n************************************************")
				.append("\n****** PKG_SATELITES.P_MOV_MPOLIGAR_GRUPO ******")
				.append("\n****** params=").append(params)
				.append("\n************************************************")
				.toString()
				);
		ejecutaSP(new MovimientoMpoligarGrupo(getDataSource()), params);
	}
	
	protected class MovimientoMpoligarGrupo extends StoredProcedure
	{
		protected MovimientoMpoligarGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_MOV_MPOLIGAR_GRUPO");
			declareParameter(new SqlParameter("cdunieco"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgarant"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdmoneda"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("respvalogar" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarDatosCotizacionGrupo2(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String ntramite
			)throws Exception,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("ntramite" , ntramite);
		logger.debug(
				new StringBuilder()
				.append("\n********************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_DATOS_COTIZACION_GRUPO2 ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************************")
				.toString()
				);
		Map<String,Object>      procResult = ejecutaSP(new CargarDatosCotizacionGrupo2(getDataSource()), params);
		List<Map<String,String>>listaDatos = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(listaDatos==null||listaDatos.size()==0)
		{
			throw new Exception("No se pudo cargar la poliza");
		}
		return listaDatos.get(0);
	}
	
	protected class CargarDatosCotizacionGrupo2 extends StoredProcedure
	{
		protected CargarDatosCotizacionGrupo2(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_COTIZACION_GRUPO2");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"cdrfc"
					,"cdperson"
					,"nombre"
					,"codpostal"
					,"cdedo"
					,"cdmunici"
					,"dsdomici"
					,"nmnumero"
					,"nmnumint"
					            ,"otvalor01","otvalor02","otvalor03","otvalor04","otvalor05","otvalor06","otvalor07","otvalor08","otvalor09"
					,"otvalor10","otvalor11","otvalor12","otvalor13","otvalor14","otvalor15","otvalor16","otvalor17","otvalor18","otvalor19"
					,"otvalor20","otvalor21","otvalor22","otvalor23","otvalor24","otvalor25","otvalor26","otvalor27","otvalor28","otvalor29"
					,"otvalor30","otvalor31","otvalor32","otvalor33","otvalor34","otvalor35","otvalor36","otvalor37","otvalor38","otvalor39"
					,"otvalor40","otvalor41","otvalor42","otvalor43","otvalor44","otvalor45","otvalor46","otvalor47","otvalor48","otvalor49"
					,"otvalor50"
					,"ntramite"
					,"nmpoliza"
					,"cdperpag"
					,"cdagente"
					,"clasif"
					,"pcpgocte"
				};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarDatosCotizacionGrupo(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new CargarDatosCotizacionGrupo(getDataSource()), params);
		List<Map<String,String>>listaDatos=(List<Map<String,String>>)resultado.get("pv_registro_o");
		Map<String,String>datos=new LinkedHashMap<String,String>();
		if(listaDatos!=null&&listaDatos.size()>0)
		{
			datos=listaDatos.get(0);
		}
		return datos;
	}
	
	protected class CargarDatosCotizacionGrupo extends StoredProcedure
	{
		private String[] columnas=new String[]{
			"cdrfc"
			,"cdperson"
			,"nombre"
			,"codpostal"
			,"cdedo"
			,"cdmunici"
			,"dsdomici"
			,"nmnumero"
			,"nmnumint"
			,"cdgiro"
			,"cdrelconaseg"
			,"cdformaseg"
			,"ntramite"
			,"nmpoliza"
			,"cdperpag"
			,"cdagente"
			,"clasif"
			,"pcpgocte"
			,"tipoDerPol"
			,"montoDerPol"
			,"recargoPers"
			,"recargoPago"
			,"dctocmer"
		};
		
		protected CargarDatosCotizacionGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_COTIZACION_GRUPO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarGruposCotizacion2(String cdunieco,String cdramo,String estado,String nmpoliza)throws Exception, Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		logger.debug(
				new StringBuilder()
				.append("\n***************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_GRUPOS_COTIZACION2 ******")
				.append("\n****** params=").append(params)
				.append("\n***************************************************")
				.toString()
				);
		Map<String,Object>      procResult  = ejecutaSP(new CargarGruposCotizacion2(getDataSource()), params);
		List<Map<String,String>>listaGrupos = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(listaGrupos==null||listaGrupos.size()==0)
		{
			throw new Exception("No se encontraron grupos");
		}
		return listaGrupos;
	}
	
	protected class CargarGruposCotizacion2 extends StoredProcedure
	{
		protected CargarGruposCotizacion2(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_GRUPOS_COTIZACION2");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			String[] columnas=new String[]
					{
					"letra"
					,"cdplan"
					,"nombre"
					            ,"otvalor01","otvalor02","otvalor03","otvalor04","otvalor05","otvalor06","otvalor07","otvalor08","otvalor09"
					,"otvalor10","otvalor11","otvalor12","otvalor13","otvalor14","otvalor15","otvalor16","otvalor17","otvalor18","otvalor19"
					,"otvalor20","otvalor21","otvalor22","otvalor23","otvalor24","otvalor25","otvalor26","otvalor27","otvalor28","otvalor29"
					,"otvalor30","otvalor31","otvalor32","otvalor33","otvalor34","otvalor35","otvalor36","otvalor37","otvalor38","otvalor39"
					,"otvalor40","otvalor41","otvalor42","otvalor43","otvalor44","otvalor45","otvalor46","otvalor47","otvalor48","otvalor49"
					,"otvalor50","otvalor51","otvalor52","otvalor53","otvalor54","otvalor55","otvalor56","otvalor57","otvalor58","otvalor59"
					,"otvalor60","otvalor61","otvalor62","otvalor63","otvalor64","otvalor65","otvalor66","otvalor67","otvalor68","otvalor69"
					,"otvalor70","otvalor71","otvalor72","otvalor73","otvalor74","otvalor75","otvalor76","otvalor77","otvalor78","otvalor79"
					,"otvalor80","otvalor81","otvalor82","otvalor83","otvalor84","otvalor85","otvalor86","otvalor87","otvalor88","otvalor89"
					,"otvalor90","otvalor91","otvalor92","otvalor93","otvalor94","otvalor95","otvalor96","otvalor97","otvalor98","otvalor99"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarGruposCotizacion(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new CargarGruposCotizacion(getDataSource()), params);
		List<Map<String,String>>listaGrupos=(List<Map<String,String>>)resultado.get("pv_registro_o");
		return listaGrupos;
	}
	
	protected class CargarGruposCotizacion extends StoredProcedure
	{
		protected CargarGruposCotizacion(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_GRUPOS_COTIZACION");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			String[] columnas=new String[]
					{
					"ptsumaaseg"
					,"incrinfl"
					,"extrreno"
					,"cesicomi"
					,"pondubic"
					,"descbono"
					,"porcgast"
					,"nombre"
					,"ayudamater"
					,"letra"
					,"cdplan"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarDatosGrupoLinea(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new CargarDatosGrupoLinea(getDataSource()), params);
		List<Map<String,String>>listaDatos=(List<Map<String,String>>)resultado.get("pv_registro_o");
		Map<String,String>datos=new LinkedHashMap<String,String>();
		if(listaDatos!=null&&listaDatos.size()>0)
		{
			datos=listaDatos.get(0);
		}
		return datos;
	}
	
	protected class CargarDatosGrupoLinea extends StoredProcedure
	{
		protected CargarDatosGrupoLinea(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_GRUPO_LINEA");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"CDUNIECO"
					,"CDRAMO"
					,"ESTADO"
					,"NMPOLIZA"
					,"CDGRUPO"
					,"NMSITUAC"
					,"DEDUCIBLE"
					,"ASISINTE"
					,"EMEREXTR"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarTvalogarsGrupo(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new CargarTvalogarsGrupo(getDataSource()), params);
		List<Map<String,String>>listaTvalogars=(List<Map<String,String>>)resultado.get("pv_registro_o");
		return listaTvalogars;
	}
	
	protected class CargarTvalogarsGrupo extends StoredProcedure
	{
		private List<String> columnas=new ArrayList<String>();
		protected CargarTvalogarsGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TVALOGARS_GRUPO");
			
			columnas.add("amparada");
			columnas.add("cdgarant");
			columnas.add("swobliga");
			for(int i=1;i<=640;i++)
			{
				columnas.add(
						new StringBuilder()
						    .append("parametros.pv_otvalor")
						    .append(StringUtils.leftPad(String.valueOf(i),3,"0"))
						    .toString()
						    );
			}
			
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarTarifasPorEdad(Map<String,String>params)throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************************")
				.append("\n****** PKG_COTIZA.P_OBTIENE_COTIZACION_COLECTIVO ******")
				.append("\n****** params=").append(params)
				.append("\n*******************************************************")
				.toString()
				);
		Map<String,Object>resultado=ejecutaSP(new CargarTarifasPorEdad(getDataSource()), params);
		List<Map<String,String>>listaTvalogars=(List<Map<String,String>>)resultado.get("pv_registro_o");
		return listaTvalogars;
	}
	
	protected class CargarTarifasPorEdad extends StoredProcedure
	{
		protected CargarTarifasPorEdad(DataSource dataSource)
		{
			super(dataSource,"PKG_COTIZA.P_OBTIENE_COTIZACION_COLECTIVO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperpag" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"EDAD"
					,"HOMBRES"
					,"MUJERES"
					,"TARIFA_UNICA_HOMBRES"
					,"TARIFA_UNICA_MUJERES"
					,"TARIFA_TOTAL_HOMBRES"
					,"TARIFA_TOTAL_MUJERES"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarTarifasPorCobertura(Map<String,String>params)throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************************")
				.append("\n****** PKG_COTIZA.P_OBTIENE_PRIMA_PROM_COBERTURA ******")
				.append("\n****** params=").append(params)
				.append("\n*******************************************************")
				.toString()
				);
		Map<String,Object>resultado=ejecutaSP(new CargarTarifasPorCobertura(getDataSource()), params);
		List<Map<String,String>>listaTvalogars=(List<Map<String,String>>)resultado.get("pv_registro_o");
		return listaTvalogars;
	}
	
	protected class CargarTarifasPorCobertura extends StoredProcedure
	{
		protected CargarTarifasPorCobertura(DataSource dataSource)
		{
			super(dataSource,"PKG_COTIZA.P_OBTIENE_PRIMA_PROM_COBERTURA");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperpag" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"DSGARANT" , "PRIMA_PROMEDIO"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String cargarNombreAgenteTramite(String ntramite)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("ntramite",ntramite);
		Map<String,Object>resultado=ejecutaSP(new CargarNombreAgenteTramite(getDataSource()), params);
		return (String)resultado.get("pv_nombre_o");
	}
	
	protected class CargarNombreAgenteTramite extends StoredProcedure
	{
		protected CargarNombreAgenteTramite(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_NOMBRE_AGENTE_TRAMITE");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nombre_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarPermisosPantallaGrupo(String cdsisrol,String status)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdsisrol" , cdsisrol);
		params.put("status"   , status);
		logger.debug(
				new StringBuilder()
				.append("\n********************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_PERMISOS_PANTALLA_GRUPO ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************************")
				.toString());
		Map<String,Object>resultado=ejecutaSP(new CargarPermisosPantallaGrupo(getDataSource()), params);
		List<Map<String,String>>listaDatos=(List<Map<String,String>>)resultado.get("pv_registro_o");
		Map<String,String>datos=new LinkedHashMap<String,String>();
		if(listaDatos!=null&&listaDatos.size()>0)
		{
			datos=listaDatos.get(0);
		}
		return datos;
	}
	
	protected class CargarPermisosPantallaGrupo extends StoredProcedure
	{
		protected CargarPermisosPantallaGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_PERMISOS_PANTALLA_GRUPO");
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"LINEA_EXTENDIDA"
					,"DETALLE_LINEA"
					,"COBERTURAS"
					,"FACTORES_EN_COBERTURAS"
					,"FACTORES"
					,"BLOQUEO_CONCEPTO"
					,"BLOQUEO_EDITORES"
					,"VENTANA_DOCUMENTOS"
					,"EXTRAPRIMAS"
					,"EXTRAPRIMAS_EDITAR"
					,"ASEGURADOS"
					,"ASEGURADOS_EDITAR"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public Map<String,String>obtieneTipoValorAutomovil(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new ObtieneTipoValorAutomovil(getDataSource()), params);
		
		Map<String,String>datos=new LinkedHashMap<String,String>();
		datos.put("pv_etiqueta_o", (String) resultado.get("pv_etiqueta_o"));
		return datos;
	}
	
	protected class ObtieneTipoValorAutomovil extends StoredProcedure
	{
		protected ObtieneTipoValorAutomovil(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_VALAUT_X_CP");
			declareParameter(new SqlParameter("pv_cdpostal_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipveh_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_etiqueta_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void guardarCensoCompletoMultisalud(
			String nombreArchivo
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdestadoCli
			,String cdmuniciCli
			,String cdplan1
			,String cdplan2
			,String cdplan3
			,String cdplan4
			,String cdplan5
			,String complemento
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("censo"       , nombreArchivo);
		params.put("cdunieco"    , cdunieco);
		params.put("cdramo"      , cdramo);
		params.put("estado"      , estado);
		params.put("nmpoliza"    , nmpoliza);
		params.put("cdestado"    , cdestadoCli);
		params.put("cdmunici"    , cdmuniciCli);
		params.put("cdplan1"     , cdplan1);
		params.put("cdplan2"     , cdplan2);
		params.put("cdplan3"     , cdplan3);
		params.put("cdplan4"     , cdplan4);
		params.put("cdplan5"     , cdplan5);
		params.put("complemento" , complemento);
		ejecutaSP(new GuardarCensoCompletoMultisalud(getDataSource()),params);
	}
	
	protected class GuardarCensoCompletoMultisalud extends StoredProcedure
	{
		protected GuardarCensoCompletoMultisalud(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_LAYOUT_CENSO_MS_COLEC_DEF");
			declareParameter(new SqlParameter("censo"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdestado"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdmunici"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan1"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan2"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan3"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan4"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan5"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("complemento" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarAseguradosExtraprimas(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado   = ejecutaSP(new CargarAseguradosExtraprimas(getDataSource()), params);
		List<Map<String,String>>lista = (List<Map<String,String>>)resultado.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class CargarAseguradosExtraprimas extends StoredProcedure
	{
		protected CargarAseguradosExtraprimas(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TVALOSIT_X_GRUPO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"NOMBRE"   , "NMSITUAC"   , "EDAD"             , "FENACIMI" 
                    ,"SEXO"    , "PARENTESCO" , "OCUPACION"        , "EXTPRI_OCUPACION"
                    ,"PESO"    , "ESTATURA"   , "EXTPRI_SOBREPESO" , "FAMILIA"
                    ,"TITULAR"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void guardarExtraprimaAsegurado(Map<String,String>params)throws Exception
	{
		ejecutaSP(new GuardarExtraprimaAsegurado(getDataSource()),params);
	}
	
	protected class GuardarExtraprimaAsegurado extends StoredProcedure
	{
		protected GuardarExtraprimaAsegurado(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_ACTUALIZA_TVALOSIT_DAT_ADIC");
			declareParameter(new SqlParameter("cdunieco"            , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"              , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"              , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"            , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem"            , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac"            , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ocupacion"           , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("extraprimaOcupacion" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("peso"                , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estatura"            , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("extraprimaSobrepeso" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarAseguradosGrupo(Map<String,String>params)throws Exception
	{
		Map<String,Object>respuesta   = ejecutaSP(new CargarAseguradosGrupo(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)respuesta.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class CargarAseguradosGrupo extends StoredProcedure
	{
		protected CargarAseguradosGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_ASEGURADOS_GRUPO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"CDGRUPO"
					,"NMSITUAC"
					,"CDPERSON"
					,"PARENTESCO"
					,"NOMBRE"
					,"SEGUNDO_NOMBRE"
					,"APELLIDO_PATERNO"
					,"APELLIDO_MATERNO"
					,"FECHA_NACIMIENTO"
					,"SEXO"
					,"NACIONALIDAD"
					,"RFC"
					,"CDROL"
					,"SWEXIPER"
					,"CDIDEPER"
					,"FAMILIA"
					,"TITULAR"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void borrarMpoliperTodos(String cdunieco,String cdramo,String estado,String nmpoliza)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		logger.debug(
				new StringBuilder()
				.append("\n********************************************")
				.append("\n****** PKG_SATELITES.P_BORRA_MPOLIPER ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************")
				.toString()
				);
		ejecutaSP(new BorrarMpoliperTodos(getDataSource()),params);
	}
	
	protected class BorrarMpoliperTodos extends StoredProcedure
	{
		protected BorrarMpoliperTodos(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_BORRA_MPOLIPER");
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
	public void borrarMpoliperGrupo(Map<String,String>params)throws Exception
	{
		ejecutaSP(new BorrarMpoliperGrupo(getDataSource()),params);
	}
	

	
	protected class BorrarMpoliperGrupo extends StoredProcedure
	{
		protected BorrarMpoliperGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_BORRA_MPOLIPER_GRUPO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	@Deprecated
	public Map<String,String>cargarTipoSituacion(Map<String,String>params)throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n***********************************************")
				.append("\n****** PKG_CONSULTA.P_GET_TIPO_SITUACION ******")
				.append("\n****** params=").append(params)
				.append("\n***********************************************")
				.toString()
				);
		Map<String,Object>respuestaProcedure=ejecutaSP(new CargarTipoSituacion(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)respuestaProcedure.get("pv_registro_o");
		Map<String,String>respuesta=null;
		if(lista!=null&&lista.size()>0)
		{
			respuesta=lista.get(0);
		}
		logger.debug(
				new StringBuilder()
				.append("\n***********************************************")
				.append("\n****** registro=").append(respuesta)
				.append("\n****** PKG_CONSULTA.P_GET_TIPO_SITUACION ******")
				.append("\n***********************************************")
				.toString()
				);
		return respuesta;
	}
	
	@Override
	public Map<String,String>cargarTipoSituacion(String cdramo,String cdtipsit)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		logger.debug(
				new StringBuilder()
				.append("\n***********************************************")
				.append("\n****** PKG_CONSULTA.P_GET_TIPO_SITUACION ******")
				.append("\n****** params=").append(params)
				.append("\n***********************************************")
				.toString()
				);
		Map<String,Object>respuestaProcedure=ejecutaSP(new CargarTipoSituacion(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)respuestaProcedure.get("pv_registro_o");
		Map<String,String>respuesta=null;
		if(lista!=null&&lista.size()>0)
		{
			respuesta=lista.get(0);
		}
		logger.debug(
				new StringBuilder()
				.append("\n***********************************************")
				.append("\n****** registro=").append(respuesta)
				.append("\n****** PKG_CONSULTA.P_GET_TIPO_SITUACION ******")
				.append("\n***********************************************")
				.toString()
				);
		return respuesta;
	}
	
	protected class CargarTipoSituacion extends StoredProcedure
	{
		protected CargarTipoSituacion(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TIPO_SITUACION");
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			String cols [] = new String[]{
					"SITUACION" , "AGRUPACION" , "MODALIDAD"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String cargarCduniecoAgenteAuto(Map<String,String>params)throws Exception
	{
		Map<String,Object>respuestaProcedure=ejecutaSP(new CargarCduniecoAgenteAuto(getDataSource()),params);
		return (String)respuestaProcedure.get("pv_cdunieco_o");
	}
	
	protected class CargarCduniecoAgenteAuto extends StoredProcedure
	{
		protected CargarCduniecoAgenteAuto(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_CDUNIECO_X_AGENTE_AUTO");
			declareParameter(new SqlParameter("cdagente" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdunieco_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String> obtenerDatosAgente(String cdagente,String cdramo)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdagente" , cdagente);
		params.put("cdramo"   , cdramo);
		logger.debug(
				new StringBuilder()
				.append("\n********************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_DATOS_AGENTE_X_CDAGENTE ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************************")
				.toString()
				);
		Map<String,Object>procedureResult=ejecutaSP(new ObtenerDatosAgente(getDataSource()),params);
		List<Map<String,String>>listaAux=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(listaAux==null||listaAux.size()==0)
		{
			throw new ApplicationException("No hay datos del agente para este producto");
		}
		else if(listaAux.size()>1)
		{
			throw new ApplicationException("Datos repetidos del agente para este producto");
		}
		return listaAux.get(0);
	}
	
	protected class ObtenerDatosAgente extends StoredProcedure
	{
		protected ObtenerDatosAgente(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_AGENTE_X_CDAGENTE");
			declareParameter(new SqlParameter("cdagente" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"CDAGENTE"
					,"NMCUADRO"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class CargarNumeroPasajerosPorTipoUnidad extends StoredProcedure
	{
		protected CargarNumeroPasajerosPorTipoUnidad(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_NUM_PASAJEROS");
			declareParameter(new SqlParameter("cdtipsit"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipoUnidad" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"NUMPASAJEROS" , "PASAJMIN" , "PASAJMAX" , "CLAVEGS" , "SUMASEG"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String> obtenerParametrosCotizacion(
			ParametroCotizacion parametro
			,String cdramo
			,String cdtipsit
			,String clave4
			,String clave5)throws ApplicationException,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("parametro" , parametro.getParametro());
		params.put("cdramo"    , cdramo);
		params.put("cdtipsit"  , cdtipsit);
		params.put("clave4"    , clave4);
		params.put("clave5"    , clave5);
		logger.debug(
				new StringBuilder()
				.append("\n************************************************")
				.append("\n****** PKG_LISTAS.P_GET_PARAMS_COTIZACION ******")
				.append("\n****** params=").append(params)
				.append("\n************************************************")
				.toString()
				);
		Map<String,Object>procedureResult = ejecutaSP(new ObtenerParametrosCotizacion(getDataSource()),params);
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
				.append("\n************************************************")
				.append("\n****** registro=").append(listaAux.get(0))
				.append("\n****** PKG_LISTAS.P_GET_PARAMS_COTIZACION ******")
				.append("\n************************************************")
				.toString()
				);
		return listaAux.get(0);
	}
	
	protected class ObtenerParametrosCotizacion extends StoredProcedure
	{
		protected ObtenerParametrosCotizacion(DataSource dataSource)
		{
			super(dataSource,"PKG_LISTAS.P_GET_PARAMS_COTIZACION");
			declareParameter(new SqlParameter("parametro" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("clave4"    , OracleTypes.VARCHAR));
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
	public Map<String,String> cargarAutoPorClaveGS(String cdramo,String clavegs,String cdtipsit,String cdsisrol)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdramo"   , cdramo);
		params.put("clavegs"  , clavegs);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdsisrol" , cdsisrol);
		logger.debug(
				new StringBuilder()
				.append("\n******************************************")
				.append("\n****** PKG_CONSULTA.P_GET_VEHICULOS ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************")
				.toString()
				);
		Map<String,Object>procedureResult=ejecutaSP(new CargarAutoPorClaveGS(getDataSource()),params);
		List<Map<String,String>>listaAux=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(listaAux==null||listaAux.size()==0)
		{
			throw new Exception("Auto no encontrado");
		}
		if(listaAux.size()>1)
		{
			logger.debug(
					new StringBuilder()
					.append("\n******************************************")
					.append("\n****** PKG_CONSULTA.P_GET_VEHICULOS ******")
					.append("\n****** lista=").append(listaAux)
					.append("\n******************************************")
					.toString()
					);
			//throw new Exception("Auto duplicado");
		}
		return listaAux.get(0);
	}
	
	protected class CargarAutoPorClaveGS extends StoredProcedure
	{
		protected CargarAutoPorClaveGS(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_VEHICULOS");
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("clavegs"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"TIPUNI"
					,"MARCA"
					,"SUBMARCA"
					,"CLAVEGS"
					,"MODELO"
					,"VERSION"
					,"NUMPASAJEROS"
					,"PASAJMIN"
					,"PASAJMAX"
					,"SUMASEG"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String> cargarClaveGSPorAuto(Map<String,String>params)throws Exception
	{
		Map<String,Object>procedureResult=ejecutaSP(new CargarClaveGSPorAuto(getDataSource()),params);
		List<Map<String,String>>listaAux=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(listaAux==null||listaAux.size()==0)
		{
			throw new Exception("Clave GS no encontrada");
		}
		if(listaAux.size()>1)
		{
			logger.debug("lista: "+listaAux);
			//throw new Exception("Clave GS duplicada");
		}
		return listaAux.get(0);
	}
	
	protected class CargarClaveGSPorAuto extends StoredProcedure
	{
		protected CargarClaveGSPorAuto(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_VEHICULOS_X_MODELO");
			declareParameter(new SqlParameter("cdramo" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("modelo" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "CLAVEGS" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
    public Map<String,String>cargarSumaAseguradaAuto(Map<String,String>params)throws Exception
    {
		Map<String,Object>procedureResult=ejecutaSP(new CargarSumaAseguradaAuto(getDataSource()),params);
		List<Map<String,String>>listaAux=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(listaAux==null||listaAux.size()==0)
		{
			throw new Exception("Suma asegurada no encontrada");
		}
		if(listaAux.size()>1)
		{
			throw new Exception("Suma asegurada duplicada");
		}
		return listaAux.get(0);
	}
	
	protected class CargarSumaAseguradaAuto extends StoredProcedure
	{
		protected CargarSumaAseguradaAuto(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_SUMA_ASEGURADA_AUTOS");
			declareParameter(new SqlParameter("version"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("modelo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"SUMASEG" , "FACREDUC" , "FACINCREM"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoMpolicotICD(Map<String,String>params)throws Exception
	{
		ejecutaSP(new MovimientoMpolicotICD(getDataSource()),params);
	}
	
	protected class MovimientoMpolicotICD extends StoredProcedure
	{
		protected MovimientoMpolicotICD(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_MOV_MPOLICOTICD");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdclausu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("icd"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarMpolicotICD(Map<String,String>params)throws Exception
	{
		Map<String,Object>procedureResult=ejecutaSP(new CargarMpolicotICD(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class CargarMpolicotICD extends StoredProcedure
	{
		protected CargarMpolicotICD(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_MPOLICOTICD");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdclausu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"ICD" , "DESCRIPCION"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarConfiguracionGrupo(Map<String,String>params)throws Exception
	{
		Map<String,Object>procedureResult=ejecutaSP(new CargarConfiguracionGrupo(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class CargarConfiguracionGrupo extends StoredProcedure
	{
		protected CargarConfiguracionGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_LISTAS.P_GET_CONF_GRUPO");
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"NOMBRE","TIPO","BASE","CDATRISIT","TIPOGAR","CDGARANT","CDATRIGAR","ORDEN","ETIQUETA"
			};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<ComponenteVO>cargarTatrisit(String cdtipsit,String cdusuari)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdtipsit" , cdtipsit);
		params.put("cdusuari" , cdusuari);
		Utils.debugProcedure(logger, "PKG_LISTAS.P_GET_ATRI_SITUACION", params);
		Map<String,Object>procResult = ejecutaSP(new CargarTatrisit(getDataSource()),params);
		List<ComponenteVO>lista      = (List<ComponenteVO>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new Exception("No hay tatrisit");
		}
		Utils.debugProcedure(logger, "PKG_LISTAS.P_GET_ATRI_SITUACION", params,lista);
		return lista;
	}
	
	protected class CargarTatrisit extends StoredProcedure
    {
    	protected CargarTatrisit(DataSource dataSource)
        {
            super(dataSource,"PKG_LISTAS.P_GET_ATRI_SITUACION");
            declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtieneTatrisitMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public List<ComponenteVO>cargarTatripol(String cdramo,String cdtipsit,String cdtippol)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdtippol" , cdtippol);
		logger.debug(
				new StringBuilder()
				.append("\n******************************************")
				.append("\n****** PKG_LISTAS.P_GET_ATRI_POLIZA ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************")
				.toString()
				);
		Map<String,Object>procResult = ejecutaSP(new CargarTatripol(getDataSource()),params);
		List<ComponenteVO>lista      = (List<ComponenteVO>)procResult.get("pv_registro_o");
		logger.debug(
				new StringBuilder()
				.append("\n******************************************")
				.append("\n****** PKG_LISTAS.P_GET_ATRI_POLIZA ******")
				.append("\n****** params=").append(params)
				.append("\n****** registro=").append(lista)
				.append("\n******************************************")
				.toString()
				);
		return lista;
	}
	
	protected class CargarTatripol extends StoredProcedure
    {
    	protected CargarTatripol(DataSource dataSource)
        {
            super(dataSource,"PKG_LISTAS.P_GET_ATRI_POLIZA");
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdtippol" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtieneTatripolMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public ComponenteVO cargarComponenteTatrisit(Map<String,String>params)throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n***************************************************")
				.append("\n****** PKG_LISTAS.P_GET_ATRI_UNICO_SITUACION ******")
				.append("\n****** params=").append(params)
				.append("\n***************************************************")
				.toString()
				);
		Map<String,Object>procedureResult=ejecutaSP(new CargarComponenteTatrisit(getDataSource()),params);
		List<ComponenteVO>lista=(List<ComponenteVO>)procedureResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new Exception("No existe el componente");
		}
		else if(lista.size()>1)
		{
			throw new Exception("Componente repetido");
		}
		return lista.get(0);
	}
	
	protected class CargarComponenteTatrisit extends StoredProcedure
    {
    	protected CargarComponenteTatrisit(DataSource dataSource)
        {
            super(dataSource,"PKG_LISTAS.P_GET_ATRI_UNICO_SITUACION");
            declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdatribu" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtieneTatrisitMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public ComponenteVO cargarComponenteTatrigar(Map<String,String>params)throws Exception
	{
		Map<String,Object>procedureResult=ejecutaSP(new CargarComponenteTatrigar(getDataSource()),params);
		List<ComponenteVO>lista=(List<ComponenteVO>)procedureResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new Exception("No existe el componente");
		}
		else if(lista.size()>1)
		{
			throw new Exception("Componente repetido");
		}
		return lista.get(0);
	}

	protected class CargarComponenteTatrigar extends StoredProcedure
	{
		protected CargarComponenteTatrigar(DataSource dataSource)
		{
			super(dataSource,"PKG_LISTAS.P_GET_ATRI_UNICO_GARANTIA");
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgarant" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdatribu" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtieneTatrigarMapper()));
			declareParameter(new SqlOutParameter("pv_messages_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void validarDescuentoAgente(
			String tipoUnidad
			,String uso
			,String zona
			,String promotoria
			,String cdagente
			,String descuento)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("tipoUnidad" , tipoUnidad);
		params.put("uso"        , uso);
		params.put("zona"       , zona);
		params.put("promotoria" , promotoria);
		params.put("cdagente"   , cdagente);
		params.put("descuento"  , descuento);
		ejecutaSP(new ValidarDescuentoAgente(getDataSource()),params);
	}
	
	protected class ValidarDescuentoAgente extends StoredProcedure
	{
		protected ValidarDescuentoAgente(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_VALIDA_DESCUENTO_COMERCIAL");
			declareParameter(new SqlParameter("tipoUnidad" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("uso"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("zona"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("promotoria" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdagente"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("descuento"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>impresionDocumentosPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String ntramite)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("ntramite" , ntramite);
		Map<String,Object>procedureResult=ejecutaSP(new ImpresionDocumentosPoliza(getDataSource()),params);
		List<Map<String,String>>listaDocumentos=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(listaDocumentos==null||listaDocumentos.size()==0)
		{
			throw new Exception("No hay documentos parametrizados");
		}
		logger.debug(new StringBuilder("\n&&&&&& Lista documentos size=").append(listaDocumentos.size()).toString());
		return listaDocumentos;
	}
	
	protected class ImpresionDocumentosPoliza extends StoredProcedure
	{
		protected ImpresionDocumentosPoliza(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_Imp_documentos");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"nmsolici"
					,"nmsituac"
					,"descripc"
					,"descripl"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
		}
	}
	
	@Override
	public void movimientoTdescsup(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nsuplogi
			,String cdtipsup
			,Date feemisio
			,String nmsolici
			,Date fesolici
			,Date ferefere
			,String cdseqpol
			,String cdusuari
			,String nusuasus
			,String nlogisus
			,String cdperson
			,String accion)throws Exception
	{
		Map<String,Object>params=new LinkedHashMap<String,Object>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nsuplogi" , nsuplogi);
		params.put("cdtipsup" , cdtipsup);
		params.put("feemisio" , feemisio);
		params.put("nmsolici" , nmsolici);
		params.put("fesolici" , fesolici);
		params.put("ferefere" , ferefere);
		params.put("cdseqpol" , cdseqpol);
		params.put("cdusuari" , cdusuari);
		params.put("nusuasus" , nusuasus);
		params.put("nlogisus" , nlogisus);
		params.put("cdperson" , cdperson);
		params.put("accion"   , accion);
		ejecutaSP(new MovimientoTdescsup(getDataSource()),params);
	}
	
	protected class MovimientoTdescsup extends StoredProcedure
	{
		protected MovimientoTdescsup(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_MOV_TDESCSUP");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nsuplogi" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feemisio" , OracleTypes.DATE));
			declareParameter(new SqlParameter("nmsolici" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("fesolici" , OracleTypes.DATE));
			declareParameter(new SqlParameter("ferefere" , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdseqpol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nusuasus" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nlogisus" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperson" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public DatosUsuario cargarInformacionUsuario(String cdusuari,String cdtipsit)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdusuari" , cdusuari);
		params.put("cdtipsit" , cdtipsit);
		logger.debug(
				new StringBuilder()
				.append("\n**********************************************")
				.append("\n****** pkg_satelites.p_get_info_usuario ******")
				.append("\n****** params=").append(params)
				.append("\n**********************************************")
				.toString()
				);
		Map<String,Object>procResult = ejecutaSP(new CargarInformacionUsuario(getDataSource()),params);
		List<DatosUsuario>listaAux   = (List<DatosUsuario>)procResult.get("pv_registro_o");
		if(listaAux==null||listaAux.size()==0)
		{
			throw new ApplicationException("No hay datos de usuario");
		}
		logger.debug(
				new StringBuilder()
				.append("\n**********************************************")
				.append("\n****** registro=").append(listaAux.get(0))
				.append("\n****** pkg_satelites.p_get_info_usuario ******")
				.append("\n**********************************************")
				.toString()
				);
		return listaAux.get(0);
	}
	
	protected class CargarInformacionUsuario extends StoredProcedure
    {
    	protected CargarInformacionUsuario(DataSource dataSource)
        {
            super(dataSource,"pkg_satelites.p_get_info_usuario");
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtieneDatosUsuarioMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    protected class ObtieneDatosUsuarioMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            DatosUsuario datosUsuario=new DatosUsuario();
            datosUsuario.setCdagente(rs.getString("cdagente"));
            datosUsuario.setCdperson(rs.getString("cdperson"));
            datosUsuario.setCdramo  (rs.getString("cdramo"));
            datosUsuario.setCdtipsit(rs.getString("cdtipsit"));
            datosUsuario.setCdunieco(rs.getString("cdunieco"));
            datosUsuario.setCdusuari(rs.getString("cdusuari"));
            datosUsuario.setNmcuadro(rs.getString("nmcuadro"));
            datosUsuario.setNombre  (rs.getString("nombre"));
            return datosUsuario;
        }
    }
    
    @Override
    public Map<String,String>cargarClienteCotizacion(String cdunieco,String cdramo,String estado,String nmpoliza)throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	logger.debug(
    			new StringBuilder()
    			.append("\n***************************************************")
    			.append("\n****** PKG_CONSULTA.P_GET_CLIENTE_COTIZACION ******")
    			.append("\n****** params=").append(params)
    			.append("\n***************************************************")
    			.toString()
    			);
    	Map<String,Object>procResult=ejecutaSP(new CargarClienteCotizacion(getDataSource()),params);
    	List<Map<String,String>>lista=(List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null||lista.size()==0)
    	{
    		throw new Exception("No se encontraron datos");
    	}
    	if(lista.size()>1)
    	{
    		throw new Exception("Datos duplicados");
    	}
    	return lista.get(0);
    }
	
	protected class CargarClienteCotizacion extends StoredProcedure
    {
    	protected CargarClienteCotizacion(DataSource dataSource)
        {
            super(dataSource,"PKG_CONSULTA.P_GET_CLIENTE_COTIZACION");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            String[] cols=new String[]
            		{
            		"NOMBRE"
            		,"CDPERSON"
            		};
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public Map<String,String>cargarConceptosGlobalesGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdperpag)throws Exception,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdperpag" , cdperpag);
		logger.debug(
				new StringBuilder()
				.append("\n****************************************************")
				.append("\n****** PKG_COTIZA.P_OBT_CPTOS_GLOBALES_COLECT ******")
				.append("\n****** params=").append(params)
				.append("\n****************************************************")
				.toString()
				);
		Map<String,Object>procResult=ejecutaSP(new CargarConceptosGlobalesGrupo(getDataSource()),params);
		List<Map<String,String>>listaAux=(List<Map<String,String>>)procResult.get("pv_registro_o");
		if(listaAux==null||listaAux.size()==0)
		{
			throw new Exception("No se encontraron conceptos globales");
		}
		if(listaAux.size()>1)
		{
			throw new Exception("Se encontraron conceptos globales repetidos");
		}
		return listaAux.get(0);
	}
	
	protected class CargarConceptosGlobalesGrupo extends StoredProcedure
    {
    	protected CargarConceptosGlobalesGrupo(DataSource dataSource)
        {
            super(dataSource,"PKG_COTIZA.P_OBT_CPTOS_GLOBALES_COLECT");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdperpag" , OracleTypes.VARCHAR));
            String[] cols=new String[]
            		{
            		"PRIMA_NETA"
            		,"DERPOL"
            		,"RECARGOS"
            		,"IVA"
            		};
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public String calculaNumeroPoliza(String cdunieco,String cdramo,String estado)throws Exception,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		logger.debug(
				new StringBuilder()
				.append("\n********************************************")
				.append("\n****** PKG_SATELITES.P_CALC_NUMPOLIZA ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************")
				.toString()
				);
		Map<String,Object>procResult=ejecutaSP(new CalculaNumeroPoliza(getDataSource()),params);
		String nmpoliza = (String)procResult.get("pv_nmpoliza_o");
		if(StringUtils.isBlank(nmpoliza))
		{
			throw new Exception("No se puede calcular el numero de poliza");
		}
		return nmpoliza;
	}
	
	protected class CalculaNumeroPoliza extends StoredProcedure
    {
    	protected CalculaNumeroPoliza(DataSource dataSource)
        {
            super(dataSource,"PKG_SATELITES.P_CALC_NUMPOLIZA");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_nmpoliza_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public void movimientoPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String status
			,String swestado
			,String nmsolici
            ,String feautori
            ,String cdmotanu
            ,String feanulac
            ,String swautori
            ,String cdmoneda
            ,String feinisus
            ,String fefinsus
            ,String ottempot
            ,String feini
            ,String hhefecto
            ,String fefin
            ,String fevencim
            ,String nmrenova
            ,String ferecibo
            ,String feultsin
            ,String nmnumsin
            ,String cdtipcoa
            ,String swtarifi
            ,String swabrido
            ,String feemisio
            ,String cdperpag
            ,String nmpoliex
            ,String nmcuadro
            ,String porredau
            ,String swconsol
            ,String nmpolant
            ,String nmpolnva
            ,String fesolici
            ,String cdramant
            ,String cdmejred
            ,String nmpoldoc
            ,String nmpoliza2
            ,String nmrenove
            ,String nmsuplee
            ,String ttipcamc
            ,String ttipcamv
            ,String swpatent
            ,String pcpgocte
            ,String tipoflot
            ,String accion
			) throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco"  , cdunieco);
		params.put("cdramo"    , cdramo);
		params.put("estado"    , estado);
		params.put("nmpoliza"  , nmpoliza);
		params.put("nmsuplem"  , nmsuplem);
		params.put("status"    , status);
		params.put("swestado"  , swestado);
		params.put("nmsolici"  , nmsolici);
        params.put("feautori"  , feautori);
        params.put("cdmotanu"  , cdmotanu);
        params.put("feanulac"  , feanulac);
        params.put("swautori"  , swautori);
        params.put("cdmoneda"  , cdmoneda);
        params.put("feinisus"  , feinisus);
        params.put("fefinsus"  , fefinsus);
        params.put("ottempot"  , ottempot);
        params.put("feefecto"  , feini);
        params.put("hhefecto"  , hhefecto);
        params.put("feproren"  , fefin);
        params.put("fevencim"  , fevencim);
        params.put("nmrenova"  , nmrenova);
        params.put("ferecibo"  , ferecibo);
        params.put("feultsin"  , feultsin);
        params.put("nmnumsin"  , nmnumsin);
        params.put("cdtipcoa"  , cdtipcoa);
        params.put("swtarifi"  , swtarifi);
        params.put("swabrido"  , swabrido);
        params.put("feemisio"  , feemisio);
        params.put("cdperpag"  , cdperpag);
        params.put("nmpoliex"  , nmpoliex);
        params.put("nmcuadro"  , nmcuadro);
        params.put("porredau"  , porredau);
        params.put("swconsol"  , swconsol);
        params.put("nmpolant"  , nmpolant);
        params.put("nmpolnva"  , nmpolnva);
        params.put("fesolici"  , fesolici);
        params.put("cdramant"  , cdramant);
        params.put("cdmejred"  , cdmejred);
        params.put("nmpoldoc"  , nmpoldoc);
        params.put("nmpoliza2" , nmpoliza2);
        params.put("nmrenove"  , nmrenove);
        params.put("nmsuplee"  , nmsuplee);
        params.put("ttipcamc"  , ttipcamc);
        params.put("ttipcamv"  , ttipcamv);
        params.put("swpatent"  , swpatent);
        params.put("pcpgocte"  , pcpgocte);
        params.put("tipoflot"  , tipoflot);
        params.put("accion"    , accion);
        logger.debug(
        		new StringBuilder()
        		.append("\n******************************************")
        		.append("\n****** PKG_SATELITES.P_MOV_MPOLIZAS ******")
        		.append("\n****** params=").append(params)
        		.append("\n******************************************")
        		.toString()
        		);
        ejecutaSP(new MovimientoPoliza(getDataSource()),params);
	}
	
	protected class MovimientoPoliza extends StoredProcedure
	{
		protected MovimientoPoliza(DataSource dataSource)
		{
    		super(dataSource,"PKG_SATELITES.P_MOV_MPOLIZAS");
    		declareParameter(new SqlParameter("cdunieco"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("cdramo"    , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("nmsuplem"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("status"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("swestado"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmsolici"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("feautori"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdmotanu"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("feanulac"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("swautori"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdmoneda"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("feinisus"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("fefinsus"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("ottempot"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("feefecto"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("hhefecto"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("feproren"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("fevencim"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmrenova"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("ferecibo"  ,  OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("feultsin"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmnumsin"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("cdtipcoa"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("swtarifi"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("swabrido"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("feemisio"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdperpag"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("nmpoliex"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmcuadro"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("porredau"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("swconsol"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmpolant"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmpolnva"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("fesolici"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdramant"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdmejred"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmpoldoc"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("nmpoliza2" , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("nmrenove"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("nmsuplee"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("ttipcamc"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("ttipcamv"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("swpatent"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pcpgocte"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("tipoflot"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("accion"    , OracleTypes.VARCHAR));	
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    }
	
	@Override
	public void movimientoTvalopol(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String status
			,Map<String,String>valores)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("status"   , status);
		for(int i=1;i<=50;i++)
		{
			params.put(new StringBuilder("otvalor").append(StringUtils.leftPad(String.valueOf(i), 2, "0")).toString(),null);
		}
		params.putAll(valores);
		logger.debug(
				new StringBuilder()
				.append("\n******************************************")
				.append("\n****** PKG_SATELITES.P_MOV_TVALOPOL ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************")
				.toString()
				);
		ejecutaSP(new MovimientoTvalopol(getDataSource()),params);
	}
	
	protected class MovimientoTvalopol extends StoredProcedure
	{
		protected MovimientoTvalopol(DataSource dataSource)
		{
    		super(dataSource,"PKG_SATELITES.P_MOV_TVALOPOL");	
    		declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmsuplem"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("status"    , OracleTypes.VARCHAR));
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
	public void procesarCenso(
			String nombreProcedure
			,String cdusuari
			,String cdsisrol
			,String nombreCenso
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsit
			,String cdagente
			,String codpostal
			,String cdedo
			,String cdmunici
			,String complemento
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdusuari"    , cdusuari);
		params.put("cdsisrol"    , cdsisrol);
		params.put("nombreCenso" , nombreCenso);
		params.put("cdunieco"    , cdunieco);
		params.put("cdramo"      , cdramo);
		params.put("estado"      , estado);
		params.put("nmpoliza"    , nmpoliza);
		params.put("cdtipsit"    , cdtipsit);
		params.put("cdagente"    , cdagente);
		params.put("codpostal"   , codpostal);
		params.put("cdedo"       , cdedo);
		params.put("cdmunici"    , cdmunici);
		params.put("complemento" , complemento);
		logger.debug(
				new StringBuilder()
				.append("\n********************************************")
				.append("\n****** EJECUTA PROCEDIMIENTO DE CENSO ******")
				.append("\n****** procedimiento=").append(nombreProcedure)
				.append("\n****** params=").append(params)
				.append("\n********************************************")
				.toString()
				);
		ejecutaSP(new ProcesarCenso(getDataSource(),nombreProcedure),params);
	}
	
	protected class ProcesarCenso extends StoredProcedure
	{
		protected ProcesarCenso(DataSource dataSource,String nombreProcedure)
		{
    		super(dataSource,nombreProcedure);	
    		declareParameter(new SqlParameter("cdusuari"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdsisrol"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nombreCenso" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdunieco"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdramo"      , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("estado"      , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmpoliza"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdtipsit"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdagente"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("codpostal"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdedo"       , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdmunici"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("complemento" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
    		compile();
    	}
    }
	
	@Override
	public void actualizaMpolisitTvalositGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo
			,String nombreGrupo
			,String cdplan
			,Map<String,String>valores)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco"    , cdunieco);
		params.put("cdramo"      , cdramo);
		params.put("estado"      , estado);
		params.put("nmpoliza"    , nmpoliza);
		params.put("cdgrupo"     , cdgrupo);
		params.put("nombreGrupo" , nombreGrupo);
		params.put("cdplan"      , cdplan);
		
		for(int i=1;i<=99;i++)
		{
			params.put(new StringBuilder("otvalor").append(StringUtils.leftPad(String.valueOf(i),2,"0")).toString(),null);
		}
		
		params.putAll(valores);
		
		logger.debug(
				new StringBuilder()
				.append("\n**********************************************************")
				.append("\n****** PKG_SATELITES2.P_ACT_MPOLISIT_TVALOSIT_DINAM ******")
				.append("\n****** params=").append(params)
				.append("\n**********************************************************")
				.toString()
				);
		ejecutaSP(new ActualizaMpolisitTvalositGrupo(getDataSource()),params);
	}
	
	protected class ActualizaMpolisitTvalositGrupo extends StoredProcedure
	{
		protected ActualizaMpolisitTvalositGrupo(DataSource dataSource)
		{
    		super(dataSource,"PKG_SATELITES2.P_ACT_MPOLISIT_TVALOSIT_DINAM");	
    		declareParameter(new SqlParameter("cdunieco"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdramo"      , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("estado"      , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmpoliza"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdgrupo"     , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nombreGrupo" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdplan"      , OracleTypes.VARCHAR));
    		for(int i=1;i<=99;i++)
    		{
    			declareParameter(new SqlParameter(
    					new StringBuilder("otvalor")
    					.append(StringUtils.leftPad(String.valueOf(i),2,"0"))
    					.toString()
    					, OracleTypes.VARCHAR));
    		}
    		declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
    		compile();
    	}
    }
	
	@Override
	public void valoresPorDefecto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdgarant
			,String cdtipsup)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsituac" , nmsituac);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdgarant" , cdgarant);
		params.put("cdtipsup" , cdtipsup);
		logger.debug(
				new StringBuilder()
				.append("\n*****************************")
				.append("\n****** P_EXEC_SIGSVDEF ******")
				.append("\n****** params=").append(params)
				.append("\n*****************************")
				.toString()
				);
		ejecutaSP(new ValoresPorDefecto(getDataSource()),params);
	}
	
	protected class ValoresPorDefecto extends StoredProcedure
	{
		protected ValoresPorDefecto(DataSource dataSource)
		{
    		super(dataSource,"P_EXEC_SIGSVDEF");	
    		declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdgarant" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
    		compile();
    	}
    }
	
	@Override
	public void movimientoMpoliper(
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
			,String accion
			,String swexiper)throws Exception
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
		params.put("accion"   , accion);
		params.put("swexiper" , swexiper);
		logger.debug(
				new StringBuilder()
				.append("\n******************************************")
				.append("\n****** PKG_SATELITES.P_MOV_MPOLIPER ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************")
				.toString()
				);
		ejecutaSP(new MovimientoMpoliper(getDataSource()),params);
	}
	
	protected class MovimientoMpoliper extends StoredProcedure
	{
		protected MovimientoMpoliper(DataSource dataSource)
		{
    		super(dataSource,"PKG_SATELITES.P_MOV_MPOLIPER");	
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
    		declareParameter(new SqlParameter("accion"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("swexiper" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
    		compile();
    	}
    }
	
	@Override
	public void movimientoMpoliage(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdagente
			,String nmsuplem
			,String status
			,String cdtipoag
			,String porredau
			,String nmcuadro
			,String cdsucurs
			,String accion
			,String ntramite
			,String porparti)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdagente" , cdagente);
		params.put("nmsuplem" , nmsuplem);
		params.put("status"   , status);
		params.put("cdtipoag" , cdtipoag);
		params.put("porredau" , porredau);
		params.put("nmcuadro" , nmcuadro);
		params.put("cdsucurs" , cdsucurs);
		params.put("accion"   , accion);
		params.put("ntramite" , ntramite);
		params.put("porparti" , porparti);
		logger.debug(
				new StringBuilder()
				.append("\n******************************************")
				.append("\n****** PKG_SATELITES.P_MOV_MPOLIAGE ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************")
				.toString()
				);
		ejecutaSP(new MovimientoMpoliage(getDataSource()),params);
	}
	
	protected class MovimientoMpoliage extends StoredProcedure
	{
		protected MovimientoMpoliage(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.P_MOV_MPOLIAGE");	
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("cdagente" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipoag" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("porredau" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("nmcuadro" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsucurs" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("porparti" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void tarificaEmi(
			String cdusuari
			,String cdelemen
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdtipsit)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdusuari" , cdusuari);
		params.put("cdelemen" , cdelemen);
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsituac" , nmsituac);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdtipsit" , cdtipsit);
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_COTIZA.P_EJECUTA_SIGSVALIPOL_EMI ******")
				.append("\n****** params=").append(params)
				.append("\n**************************************************")
				.toString()
				);
		ejecutaSP(new TarificaEmi(getDataSource()),params);
	}
	
	protected class TarificaEmi extends StoredProcedure
	{
		protected TarificaEmi(DataSource dataSource)
		{
			super(dataSource, "PKG_COTIZA.P_EJECUTA_SIGSVALIPOL_EMI");	
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdelemen" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>obtenerTiposSituacion()throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		logger.debug(
				new StringBuilder()
				.append("\n************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_TIPOS_SITUACION ******")
				.append("\n****** sin parametros")
				.append("\n************************************************")
				.toString()
				);
		Map<String,Object>procResult=ejecutaSP(new ObtenerTiposSituacion(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new Exception("No hay tipos de situacion");
		}
		return lista;
	}
	
	protected class ObtenerTiposSituacion extends StoredProcedure
	{
		protected ObtenerTiposSituacion(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_TIPOS_SITUACION");
			String[] cols=new String[]
					{
					"CDRAMO"
					,"CDTIPSIT"
					,"SITUACION"
					,"AGRUPACION"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarSituacionesGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdgrupo)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdgrupo"  , cdgrupo);
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_TVALOSIT_X_GRUPO_DINAM ******")
				.append("\n****** params=").append(params)
				.append("\n*******************************************************")
				.toString()
				);
		Map<String,Object>procResult=ejecutaSP(new CargarSituacionesGrupo(getDataSource()),params);
		List<Map<String,String>>situaciones=(List<Map<String,String>>)procResult.get("pv_registro_o");
		if(situaciones==null||situaciones.size()==0)
		{
			throw new Exception("No hay situaciones para el grupo");
		}
		return situaciones;
	}
	
	protected class CargarSituacionesGrupo extends StoredProcedure
	{
		protected CargarSituacionesGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TVALOSIT_X_GRUPO_DINAM");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"nombre"
					,"nmsituac"
					,"familia"
					,"titular"
					,"parentesco"
					            ,"otvalor01","otvalor02","otvalor03","otvalor04","otvalor05","otvalor06","otvalor07","otvalor08","otvalor09"
					,"otvalor10","otvalor11","otvalor12","otvalor13","otvalor14","otvalor15","otvalor16","otvalor17","otvalor18","otvalor19"
					,"otvalor20","otvalor21","otvalor22","otvalor23","otvalor24","otvalor25","otvalor26","otvalor27","otvalor28","otvalor29"
					,"otvalor30","otvalor31","otvalor32","otvalor33","otvalor34","otvalor35","otvalor36","otvalor37","otvalor38","otvalor39"
					,"otvalor40","otvalor41","otvalor42","otvalor43","otvalor44","otvalor45","otvalor46","otvalor47","otvalor48","otvalor49"
					,"otvalor50","otvalor51","otvalor52","otvalor53","otvalor54","otvalor55","otvalor56","otvalor57","otvalor58","otvalor59"
					,"otvalor60","otvalor61","otvalor62","otvalor63","otvalor64","otvalor65","otvalor66","otvalor67","otvalor68","otvalor69"
					,"otvalor70","otvalor71","otvalor72","otvalor73","otvalor74","otvalor75","otvalor76","otvalor77","otvalor78","otvalor79"
					,"otvalor80","otvalor81","otvalor82","otvalor83","otvalor84","otvalor85","otvalor86","otvalor87","otvalor88","otvalor89"
					,"otvalor90","otvalor91","otvalor92","otvalor93","otvalor94","otvalor95","otvalor96","otvalor97","otvalor98","otvalor99"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizaValoresSituacion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,Map<String,String>valores)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("nmsituac" , nmsituac);
		
		for(int i=1;i<=99;i++)
		{
			params.put(new StringBuilder("otvalor").append(StringUtils.leftPad(String.valueOf(i),2,"0")).toString(),null);
		}
		
		params.putAll(valores);
		
		logger.debug(
				new StringBuilder()
				.append("\n******************************************************")
				.append("\n****** PKG_SATELITES.P_ACTUALIZA_TVALOSIT_DINAM ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************************")
				.toString()
				);
		ejecutaSP(new ActualizaValoresSituacion(getDataSource()),params);
	}
	
	protected class ActualizaValoresSituacion extends StoredProcedure
	{
		protected ActualizaValoresSituacion(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_ACTUALIZA_TVALOSIT_DINAM");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			for(int i=1;i<=99;i++)
			{
				declareParameter(
						new SqlParameter(
								new StringBuilder("otvalor").append(StringUtils.leftPad(String.valueOf(i),2,"0")).toString()
								,OracleTypes.VARCHAR
								)
				);
			}
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void validarCambioZonaGMI(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String codpostal)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco"  , cdunieco);
		params.put("cdramo"    , cdramo);
		params.put("cdtipsit"  , cdtipsit);
		params.put("estado"    , estado);
		params.put("nmpoliza"  , nmpoliza);
		params.put("nmsuplem"  , nmsuplem);
		params.put("nmsituac"  , nmsituac);
		params.put("codpostal" , codpostal);
		logger.debug(
				new StringBuilder()
				.append("\n**********************************************************")
				.append("\n****** PKG_SATELITES2.P_VAL_ELIM_PENAL_CAM_ZONA_GMM ******")
				.append("\n****** params=").append(params)
				.append("\n**********************************************************")
				.toString()
				);
		ejecutaSP(new ValidarCambioZonaGMI(getDataSource()),params);
	}
	
	protected class ValidarCambioZonaGMI extends StoredProcedure
	{
		protected ValidarCambioZonaGMI(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_VAL_ELIM_PENAL_CAM_ZONA_GMM");
			declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("codpostal" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void validarEnfermedadCatastGMI(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String circHosp)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("nmsituac" , nmsituac);
		params.put("circHosp" , circHosp);
		logger.debug(
				new StringBuilder()
				.append("\n***********************************************************")
				.append("\n****** PKG_SATELITES2.P_VAL_COB_ENFERM_CATASTROF_GMM ******")
				.append("\n****** params=").append(params)
				.append("\n***********************************************************")
				.toString()
				);
		ejecutaSP(new ValidarEnfermedadCatastGMI(getDataSource()),params);
	}
	
	protected class ValidarEnfermedadCatastGMI extends StoredProcedure
	{
		protected ValidarEnfermedadCatastGMI(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_VAL_COB_ENFERM_CATASTROF_GMM");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("circHosp" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarRetroactividadSuplemento(
			String cdunieco
			,String cdramo
			,String cdtipsup
			,String cdusuari
			,String cdtipsit)throws ApplicationException,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("cdtipsup" , cdtipsup);
		params.put("cdusuari" , cdusuari);
		params.put("cdtipsit" , cdtipsit);
		logger.debug(
				new StringBuilder()
				.append("\n*********************************************")
				.append("\n****** PKG_SATELITES.P_OBT_RETRO_DIFER ******")
				.append("\n****** params=").append(params)
				.append("\n*********************************************")
				.toString()
				);
		Map<String,Object>procResult=ejecutaSP(new CargarRetroactividadSuplemento(getDataSource()),params);
		List<?>lista = (List<?>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("Falta parametrizar la retroactividad");
		}
		if(lista.size()>1)
		{
			throw new ApplicationException("La retroactividad parametrizada se repite");
		}
		Map<?,?>retroactividad=(Map<?,?>)lista.get(0);
		logger.debug(
				new StringBuilder()
				.append("\n*********************************************")
				.append("\n****** salida=").append(retroactividad)
				.append("\n****** PKG_SATELITES.P_OBT_RETRO_DIFER ******")
				.append("\n*********************************************")
				.toString()
				);
		Map<String,String>aux=new LinkedHashMap<String,String>();
		for(Entry<?,?>en:retroactividad.entrySet())
		{
			aux.put((String)en.getKey(),(String)en.getValue());
		}
		return aux;
	}
	
	protected class CargarRetroactividadSuplemento extends StoredProcedure
	{
		protected CargarRetroactividadSuplemento(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_OBT_RETRO_DIFER");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"retroac"
					,"diferi"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarSumaAseguradaRamo5(
			String cdtipsit
			,String clave
			,String modelo
			,String cdsisrol)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdtipsit" , cdtipsit);
		params.put("clave"    , clave);
		params.put("modelo"   , modelo);
		params.put("cdsisrol" , cdsisrol);
		logger.debug(
				new StringBuilder()
				.append("\n*************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_SUMA_ASEG_RAMO_5 ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************************")
				.toString()
				);
		Map<String,Object>procResult=ejecutaSP(new CargarSumaAseguradaRamo5(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No hay suma asegurada para el auto");
		}
		else if(lista.size()>1)
		{
			throw new ApplicationException("Suma asegurada duplicada para el auto");
		}
		Map<String,String>mapa=lista.get(0);
		logger.debug(
				new StringBuilder()
				.append("\n*************************************************")
				.append("\n****** registro=").append(mapa)
				.append("\n****** PKG_CONSULTA.P_GET_SUMA_ASEG_RAMO_5 ******")
				.append("\n*************************************************")
				.toString()
				);
		return mapa;
	}
	
	protected class CargarSumaAseguradaRamo5 extends StoredProcedure
	{
		protected CargarSumaAseguradaRamo5(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_SUMA_ASEG_RAMO_5");
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("clave"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("modelo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"sumaseg"
					,"facreduc"
					,"facincrem"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarDatosComplementariosAutoInd(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws ApplicationException,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		logger.debug(
				new StringBuilder()
				.append("\n****************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_DATOS_COMP_AUTO_IND ******")
				.append("\n****** params=").append(params)
				.append("\n****************************************************")
				.toString()
				);
		Map<String,Object>procResult  = ejecutaSP(new CargarDatosComplementariosAutoInd(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No hay datos de poliza");
		}
		if(lista.size()>1)
		{
			throw new ApplicationException("Datos de poliza repetidos");
		}
		logger.debug(
				new StringBuilder()
				.append("\n****************************************************")
				.append("\n****** params=").append(params)
				.append("\n****** registro=").append(lista.get(0))
				.append("\n****** PKG_CONSULTA.P_GET_DATOS_COMP_AUTO_IND ******")
				.append("\n****************************************************")
				.toString()
				);
		return lista.get(0);
	}
	
	protected class CargarDatosComplementariosAutoInd extends StoredProcedure
	{
		protected CargarDatosComplementariosAutoInd(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_COMP_AUTO_IND");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					    "cdunieco"
					    ,"cdramo"
					    ,"estado"
					    ,"nmpoliza"
					    ,"cdperpag"
					    ,"cdplan"
					    ,"feini"
					    ,"fefin"
					    ,"cdperson"
					    ,"cdideper"
					    ,"cdideext"
					    ,"swexiper"
					    ,"agente_sec"
					    ,"porparti"
					    ,"prima_total"
					    ,"cdpostal"
					    ,"otfisjur"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarTvalopol(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws ApplicationException,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		logger.debug(
				new StringBuilder()
				.append("\n***************************************")
				.append("\n****** PKG_COTIZA.P_GET_TVALOPOL ******")
				.append("\n****** params=").append(params)
				.append("\n***************************************")
				.toString()
				);
		Map<String,Object>procResult  = ejecutaSP(new CargarTvalopol(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		Map<String,String>datos       = new LinkedHashMap<String,String>();
		if(lista!=null&&lista.size()>0)
		{
			datos=lista.get(0);
		}
		Map<String,String>aux=new LinkedHashMap<String,String>();
		for(Entry<String,String>en:datos.entrySet())
		{
			aux.put(new StringBuilder("parametros.pv_").append(en.getKey()).toString(),en.getValue());
		}
		datos=aux;
		logger.debug(
				new StringBuilder()
				.append("\n***************************************")
				.append("\n****** params=").append(params)
				.append("\n****** registro=").append(datos)
				.append("\n****** PKG_COTIZA.P_GET_TVALOPOL ******")
				.append("\n***************************************")
				.toString()
				);
		return datos;
	}
	
	protected class CargarTvalopol extends StoredProcedure
	{
		protected CargarTvalopol(DataSource dataSource)
		{
			super(dataSource,"PKG_COTIZA.P_GET_TVALOPOL");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					    "cdunieco"
					    ,"cdramo"
					    ,"estado"
					    ,"nmpoliza"
					    ,"nmsuplem"
					    ,"status"
					                ,"otvalor01","otvalor02","otvalor03","otvalor04","otvalor05","otvalor06","otvalor07","otvalor08","otvalor09"
					    ,"otvalor10","otvalor11","otvalor12","otvalor13","otvalor14","otvalor15","otvalor16","otvalor17","otvalor18","otvalor19"
					    ,"otvalor20","otvalor21","otvalor22","otvalor23","otvalor24","otvalor25","otvalor26","otvalor27","otvalor28","otvalor29"
					    ,"otvalor30","otvalor31","otvalor32","otvalor33","otvalor34","otvalor35","otvalor36","otvalor37","otvalor38","otvalor39"
					    ,"otvalor40","otvalor41","otvalor42","otvalor43","otvalor44","otvalor45","otvalor46","otvalor47","otvalor48","otvalor49"
					    ,"otvalor50"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarTvalosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			)throws ApplicationException,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmsituac" , nmsituac);
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************")
				.append("\n****** PKG_COTIZA.P_OBTIENE_TVALOSIT ******")
				.append("\n****** params=").append(params)
				.append("\n*******************************************")
				.toString()
				);
		Map<String,Object>procResult  = ejecutaSP(new CargarTvalosit(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No hay valores de situacion"); 
		}
		if(lista.size()>1)
		{
			throw new ApplicationException("Valores de situacion duplicados");
		}
		Map<String,String>datos = lista.get(0);
		Map<String,String>aux   = new LinkedHashMap<String,String>();
		for(Entry<String,String>en:datos.entrySet())
		{
			aux.put(new StringBuilder("parametros.pv_").append(en.getKey()).toString(),en.getValue());
		}
		datos=aux;
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************")
				.append("\n****** params=").append(params)
				.append("\n****** registro=").append(datos)
				.append("\n****** PKG_COTIZA.P_OBTIENE_TVALOSIT ******")
				.append("\n*******************************************")
				.toString()
				);
		return datos;
	}
	
	protected class CargarTvalosit extends StoredProcedure
	{
		protected CargarTvalosit(DataSource dataSource)
		{
			super(dataSource,"PKG_COTIZA.P_OBTIENE_TVALOSIT");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					    "cdtipsit"
					    ,"nmsuplem"
					    ,"status"
					                ,"otvalor01","otvalor02","otvalor03","otvalor04","otvalor05","otvalor06","otvalor07","otvalor08","otvalor09"
					    ,"otvalor10","otvalor11","otvalor12","otvalor13","otvalor14","otvalor15","otvalor16","otvalor17","otvalor18","otvalor19"
					    ,"otvalor20","otvalor21","otvalor22","otvalor23","otvalor24","otvalor25","otvalor26","otvalor27","otvalor28","otvalor29"
					    ,"otvalor30","otvalor31","otvalor32","otvalor33","otvalor34","otvalor35","otvalor36","otvalor37","otvalor38","otvalor39"
					    ,"otvalor40","otvalor41","otvalor42","otvalor43","otvalor44","otvalor45","otvalor46","otvalor47","otvalor48","otvalor49"
					    ,"otvalor50","otvalor51","otvalor52","otvalor53","otvalor54","otvalor55","otvalor56","otvalor57","otvalor58","otvalor59"
					    ,"otvalor60","otvalor61","otvalor62","otvalor63","otvalor64","otvalor65","otvalor66","otvalor67","otvalor68","otvalor69"
					    ,"otvalor70","otvalor71","otvalor72","otvalor73","otvalor74","otvalor75","otvalor76","otvalor77","otvalor78","otvalor79"
					    ,"otvalor80","otvalor81","otvalor82","otvalor83","otvalor84","otvalor85","otvalor86","otvalor87","otvalor88","otvalor89"
					    ,"otvalor90","otvalor91","otvalor92","otvalor93","otvalor94","otvalor95","otvalor96","otvalor97","otvalor98","otvalor99"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizaMpolizas(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem,
			String swestado,
			String nmsolici,
			Date   feautori,
			String cdmotanu,
			Date   feanulac,
			String swautori,
			String cdmoneda,
			Date   feinisus,
			Date   fefinsus,
			String ottempot,
			Date   feefecto,
			String hhefecto,
			Date   feproren,
			Date   fevencim,
			String nmrenova,
			Date   ferecibo,
			Date   feultsin,
			String nmnumsin,
			String cdtipcoa,
			String swtarifi,
			String swabrido,
			Date   feemisio,
			String cdperpag,
			String nmpoliex,
			String nmcuadro,
			String porredau,
			String swconsol,
			String nmpolant,
			String nmpolnva,
			Date   fesolici,
			String cdramant,
			String cdmejred,
			String nmpoldoc,
			String nmpoliza2,
			String nmrenove,
			String nmsuplee,
			String ttipcamc,
			String ttipcamv,
			String swpatent,
			String nmpolmst,
			String pcpgocte)throws Exception
	{
		Map<String,Object>params=new LinkedHashMap<String,Object>();
		params.put("cdunieco"  , cdunieco );
		params.put("cdramo"    , cdramo );
		params.put("estado"    , estado );
		params.put("nmpoliza"  , nmpoliza );
		params.put("nmsuplem"  , nmsuplem );
		params.put("swestado"  , swestado );
		params.put("nmsolici"  , nmsolici );
		params.put("feautori"  , feautori );
		params.put("cdmotanu"  , cdmotanu );
		params.put("feanulac"  , feanulac );
		params.put("swautori"  , swautori );
		params.put("cdmoneda"  , cdmoneda );
		params.put("feinisus"  , feinisus );
		params.put("fefinsus"  , fefinsus );
		params.put("ottempot"  , ottempot );
		params.put("feefecto"  , feefecto );
		params.put("hhefecto"  , hhefecto );
		params.put("feproren"  , feproren );
		params.put("fevencim"  , fevencim );
		params.put("nmrenova"  , nmrenova );
		params.put("ferecibo"  , ferecibo );
		params.put("feultsin"  , feultsin );
		params.put("nmnumsin"  , nmnumsin );
		params.put("cdtipcoa"  , cdtipcoa );
		params.put("swtarifi"  , swtarifi );
		params.put("swabrido"  , swabrido );
		params.put("feemisio"  , feemisio );
		params.put("cdperpag"  , cdperpag );
		params.put("nmpoliex"  , nmpoliex );
		params.put("nmcuadro"  , nmcuadro );
		params.put("porredau"  , porredau );
		params.put("swconsol"  , swconsol );
		params.put("nmpolant"  , nmpolant );
		params.put("nmpolnva"  , nmpolnva );
		params.put("fesolici"  , fesolici );
		params.put("cdramant"  , cdramant );
		params.put("cdmejred"  , cdmejred );
		params.put("nmpoldoc"  , nmpoldoc );
		params.put("nmpoliza2" , nmpoliza2 );
		params.put("nmrenove"  , nmrenove );
		params.put("nmsuplee"  , nmsuplee );
		params.put("ttipcamc"  , ttipcamc );
		params.put("ttipcamv"  , ttipcamv );
		params.put("swpatent"  , swpatent );
		params.put("nmpolmst"  , nmpolmst );
		params.put("pcpgocte"  , pcpgocte );
		logger.debug(
				new StringBuilder()
				.append("\n**********************************************")
				.append("\n****** PKG_SATELITES2.P_UPDATE_MPOLIZAS ******")
				.append("\n****** params=").append(params)
				.append("\n**********************************************")
				.toString()
				);
		ejecutaSP(new ActualizaMpolizas(getDataSource()),params);
	}
	
	protected class ActualizaMpolizas extends StoredProcedure
	{
		protected ActualizaMpolizas(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_UPDATE_MPOLIZAS");
			declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swestado"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsolici"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feautori"  , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdmotanu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feanulac"  , OracleTypes.DATE));
			declareParameter(new SqlParameter("swautori"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdmoneda"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feinisus"  , OracleTypes.DATE));
			declareParameter(new SqlParameter("fefinsus"  , OracleTypes.DATE));
			declareParameter(new SqlParameter("ottempot"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feefecto"  , OracleTypes.DATE));
			declareParameter(new SqlParameter("hhefecto"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feproren"  , OracleTypes.DATE));
			declareParameter(new SqlParameter("fevencim"  , OracleTypes.DATE));
			declareParameter(new SqlParameter("nmrenova"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ferecibo"  , OracleTypes.DATE));
			declareParameter(new SqlParameter("feultsin"  , OracleTypes.DATE));
			declareParameter(new SqlParameter("nmnumsin"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipcoa"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swtarifi"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swabrido"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feemisio"  , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdperpag"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliex"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmcuadro"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("porredau"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swconsol"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpolant"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpolnva"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("fesolici"  , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdramant"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdmejred"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoldoc"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza2" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmrenove"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplee"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ttipcamc"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ttipcamv"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swpatent"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpolmst"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pcpgocte"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void borrarAgentesSecundarios(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco"  , cdunieco );
		params.put("cdramo"    , cdramo );
		params.put("estado"    , estado );
		params.put("nmpoliza"  , nmpoliza );
		params.put("nmsuplem"  , nmsuplem );
		logger.debug(
				new StringBuilder()
				.append("\n********************************************************")
				.append("\n****** PKG_SATELITES2.P_BORRA_AGENTES_SECUNDARIOS ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************************")
				.toString()
				);
		ejecutaSP(new BorrarAgentesSecundarios(getDataSource()),params);
	}
	
	protected class BorrarAgentesSecundarios extends StoredProcedure
	{
		protected BorrarAgentesSecundarios(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_BORRA_AGENTES_SECUNDARIOS");
			declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarConfiguracionCotizacion(
			String cdramo
			,String cdtipsit
			,String cdusuari)throws ApplicationException,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdusuari" , cdusuari);
		logger.debug(
				new StringBuilder()
				.append("\n****************************************************")
				.append("\n****** PKG_SATELITES2.P_GET_CONFIG_COTIZACION ******")
				.append("\n****** params=").append(params)
				.append("\n****************************************************")
				.toString()
				);
		Map<String,Object>procResult  = ejecutaSP(new CargarConfiguracionCotizacion(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No hay configuracion de cotizacion"); 
		}
		Map<String,String>datos = new LinkedHashMap<String,String>();
		for(Map<String,String>valor:lista)
		{
			datos.put(
					new StringBuilder("parametros.pv_otvalor")
					.append(StringUtils.leftPad(valor.get("cdatribu"),2,"0"))
					.toString()
					,valor.get("valor")
					);
		}
		logger.debug(
				new StringBuilder()
				.append("\n****************************************************")
				.append("\n****** params=").append(params)
				.append("\n****** registro=").append(datos)
				.append("\n****** PKG_SATELITES2.P_GET_CONFIG_COTIZACION ******")
				.append("\n****************************************************")
				.toString()
				);
		return datos;
	}
	
	protected class CargarConfiguracionCotizacion extends StoredProcedure
	{
		protected CargarConfiguracionCotizacion(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_GET_CONFIG_COTIZACION");
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					    "cdatribu"
					    ,"valor"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void guardarConfiguracionCotizacion(
			String cdramo
			,String cdtipsit
			,String cdusuari
			,Map<String,String>valores)throws Exception
	{
		Map<String,String>params = new LinkedHashMap<String,String>();
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdusuari" , cdusuari);
		for(int i=1;i<=99;i++)
		{
			params.put(new StringBuilder("valor").append(StringUtils.leftPad(String.valueOf(i),2,"0")).toString(),null);
		}
		params.putAll(valores);

		logger.debug(
				new StringBuilder()
				.append("\n****************************************************")
				.append("\n****** PKG_SATELITES2.P_MOV_CONFIG_COTIZACION ******")
				.append("\n****** params=").append(params)
				.append("\n****************************************************")
				.toString()
				);
		ejecutaSP(new GuardarConfiguracionCotizacion(getDataSource()),params);
	}
	
	protected class GuardarConfiguracionCotizacion extends StoredProcedure
	{
		protected GuardarConfiguracionCotizacion(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_MOV_CONFIG_COTIZACION");
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			for(int i=1;i<=99;i++)
			{
				declareParameter(new SqlParameter(
						new StringBuilder("valor").append(StringUtils.leftPad(String.valueOf(i),2,"0")).toString()
						, OracleTypes.VARCHAR));
			}
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarRangoDescuentoRamo5(
			String cdtipsit
			,String cdagente
			,String negocio
			,String cdsisrol
			,String cdusuari
			)throws ApplicationException,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdtipsit" , cdtipsit);
		params.put("cdagente" , cdagente);
		params.put("negocio"  , negocio);
		params.put("cdsisrol" , cdsisrol);
		params.put("cdusuari" , cdusuari);
		logger.debug(
				new StringBuilder()
				.append("\n***************************************************")
				.append("\n****** PKG_SATELITES2.P_GET_DESC_RECA_RAMO_5 ******")
				.append("\n****** params=").append(params)
				.append("\n***************************************************")
				.toString()
				);
		Map<String,Object>procResult  = ejecutaSP(new CargarRangoDescuentoRamo5(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No hay descuento/recargo para el agente");
		}
		if(lista.size()>1)
		{
			throw new ApplicationException("Descuento/recargo para el agente duplicado");
		}
		logger.debug(
				new StringBuilder()
				.append("\n***************************************************")
				.append("\n****** registro=").append(lista.get(0))
				.append("\n****** params=")  .append(params)
				.append("\n****** PKG_SATELITES2.P_GET_DESC_RECA_RAMO_5 ******")
				.append("\n***************************************************")
				.toString()
				);
		return lista.get(0);
	}
	
	protected class CargarRangoDescuentoRamo5 extends StoredProcedure
	{
		protected CargarRangoDescuentoRamo5(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_GET_DESC_RECA_RAMO_5");
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdagente" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("negocio"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "min" , "max" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<List<Map<String,String>>>cargarParamerizacionConfiguracionCoberturas(
			String cdtipsit
			,String cdsisrol
			,String negocio
			,String tipoServicio
			,String modelo
			,String tipoPersona
			,String submarca
			,String clavegs)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdtipsit"     , cdtipsit);
		params.put("cdsisrol"     , cdsisrol);
		params.put("negocio"      , negocio);
		params.put("tipoServicio" , tipoServicio);
		params.put("modelo"       , modelo);
		params.put("tipoPersona"  , tipoPersona);
		params.put("submarca"     , submarca);
		params.put("clavegs"      , clavegs);
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_PARAMETROS_CONFIG_AUTO ******")
				.append("\n****** params=").append(params)
				.append("\n*******************************************************")
				.toString()
				);
		Map<String,Object>procResult    = ejecutaSP(new CargarParamerizacionConfiguracionCoberturas(getDataSource()),params);
		List<Map<String,String>>tatrist = (List<Map<String,String>>)procResult.get("pv_cur_tatrisit_o");
		if(tatrist==null)
		{
			tatrist=new ArrayList<Map<String,String>>();
		}
		List<Map<String,String>>atrixrol = (List<Map<String,String>>)procResult.get("pv_cur_atrixrol_o");
		if(atrixrol==null)
		{
			atrixrol=new ArrayList<Map<String,String>>();
		}
		List<Map<String,String>>atrixant = (List<Map<String,String>>)procResult.get("pv_cur_atrixant_o");
		if(atrixant==null)
		{
			atrixant=new ArrayList<Map<String,String>>();
		}
		List<Map<String,String>>atrixper = (List<Map<String,String>>)procResult.get("pv_cur_atrixper_o");
		if(atrixper==null)
		{
			atrixper=new ArrayList<Map<String,String>>();
		}
		List<Map<String,String>>atrixcam = (List<Map<String,String>>)procResult.get("pv_cur_atrixcam_o");
		if(atrixcam==null)
		{
			atrixcam=new ArrayList<Map<String,String>>();
		}
		List<Map<String,String>>atrirang = (List<Map<String,String>>)procResult.get("pv_cur_atrirang_o");
		if(atrirang==null)
		{
			atrirang=new ArrayList<Map<String,String>>();
		}
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************************")
				.append("\n****** params=")  .append(params)
				.append("\n****** tatrist=") .append(tatrist)
				.append("\n****** atrixrol=").append(atrixrol)
				.append("\n****** atrixant=").append(atrixant)
				.append("\n****** atrixper=").append(atrixper)
				.append("\n****** atrixcam=").append(atrixcam)
				.append("\n****** atrirang=").append(atrirang)
				.append("\n****** PKG_CONSULTA.P_GET_PARAMETROS_CONFIG_AUTO ******")
				.append("\n*******************************************************")
				.toString()
				);
		List<List<Map<String,String>>>lista=new ArrayList<List<Map<String,String>>>();
		lista.add(tatrist);
		lista.add(atrixrol);
		lista.add(atrixant);
		lista.add(atrixper);
		lista.add(atrixcam);
		lista.add(atrirang);
		return lista;
	}
	
	protected class CargarParamerizacionConfiguracionCoberturas extends StoredProcedure
	{
		protected CargarParamerizacionConfiguracionCoberturas(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_PARAMETROS_CONFIG_AUTO");
			declareParameter(new SqlParameter("cdtipsit"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("negocio"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipoServicio" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("modelo"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipoPersona"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("submarca"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("clavegs"      , OracleTypes.VARCHAR));
			String[] cols  = new String[]{ "cdatribu" , "aplica" , "valor" };
			String[] cols2 = new String[]{ "cdatribu" , "minimo" , "maximo" };
			declareParameter(new SqlOutParameter("pv_cur_tatrisit_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_cur_atrixrol_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_cur_atrixant_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_cur_atrixper_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_cur_atrixcam_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_cur_atrirang_o" , OracleTypes.CURSOR, new GenericMapper(cols2)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"       , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"        , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarDatosVehiculoRamo5(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza)throws ApplicationException, Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_DATOS_AUTO_RAMO_5 ******")
				.append("\n****** params=")  .append(params)
				.append("\n**************************************************")
				.toString()
				);
		Map<String,Object>procResult = ejecutaSP(new CargarDatosVehiculoRamo5(getDataSource()),params);
		List<Map<String,String>>aux  = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(aux==null||aux.size()==0)
		{
			throw new ApplicationException("No se encontraron datos del auto");
		}
		else if(aux.size()>1)
		{
			throw new ApplicationException("Datos del auto duplicados");
		}
		logger.debug(
				new StringBuilder()
				.append("\n**************************************************")
				.append("\n****** params=")  .append(params)
				.append("\n****** registro=").append(aux.get(0))
				.append("\n****** PKG_CONSULTA.P_GET_DATOS_AUTO_RAMO_5 ******")
				.append("\n**************************************************")
				.toString()
				);
		return aux.get(0);
	}
	
	protected class CargarDatosVehiculoRamo5 extends StoredProcedure
	{
		protected CargarDatosVehiculoRamo5(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_AUTO_RAMO_5");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			String[] cols=new String[]{"datos"};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void guardarTbasvalsit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String status
			,String cdtipsit
			,Map<String,String>valores)throws ApplicationException,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsituac" , nmsituac);
		params.put("nmsuplem" , nmsuplem);
		params.put("status"   , status);
		params.put("cdtipsit" , cdtipsit);
		for(int i=1;i<=99;i++)
		{
			params.put(new StringBuilder("otvalor").append(StringUtils.leftPad(String.valueOf(i),2,"0")).toString(),null);
		}
		params.putAll(valores);
		logger.debug(
				new StringBuilder()
				.append("\n*************************************************")
				.append("\n****** PKG_SATELITES2.P_INSERTA_TBASVALSIT ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************************")
				.toString()
				);
		ejecutaSP(new GuardarTbasvalsit(getDataSource()),params);
	}
	
	protected class GuardarTbasvalsit extends StoredProcedure
	{
		protected GuardarTbasvalsit(DataSource dataSource)
		{
    		super(dataSource,"PKG_SATELITES2.P_INSERTA_TBASVALSIT");	
    		declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
    		for(int i=1;i<=99;i++)
    		{
    			declareParameter(new SqlParameter(
    					new StringBuilder("otvalor")
    					.append(StringUtils.leftPad(String.valueOf(i),2,"0"))
    					.toString()
    					, OracleTypes.VARCHAR));
    		}
    		declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
    		compile();
    	}
    }
	
	@Override
	public void guardarTconvalsit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String status
			,String cdtipsit
			,Map<String,String>valores)throws ApplicationException,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsituac" , nmsituac);
		params.put("nmsuplem" , nmsuplem);
		params.put("status"   , status);
		params.put("cdtipsit" , cdtipsit);
		for(int i=1;i<=99;i++)
		{
			params.put(new StringBuilder("otvalor").append(StringUtils.leftPad(String.valueOf(i),2,"0")).toString(),null);
		}
		params.putAll(valores);
		logger.debug(
				new StringBuilder()
				.append("\n*************************************************")
				.append("\n****** PKG_SATELITES2.P_INSERTA_TCONVALSIT ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************************")
				.toString()
				);
		ejecutaSP(new GuardarTconvalsit(getDataSource()),params);
	}
	
	protected class GuardarTconvalsit extends StoredProcedure
	{
		protected GuardarTconvalsit(DataSource dataSource)
		{
    		super(dataSource,"PKG_SATELITES2.P_INSERTA_TCONVALSIT");	
    		declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
    		for(int i=1;i<=99;i++)
    		{
    			declareParameter(new SqlParameter(
    					new StringBuilder("otvalor")
    					.append(StringUtils.leftPad(String.valueOf(i),2,"0"))
    					.toString()
    					, OracleTypes.VARCHAR));
    		}
    		declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
    		compile();
    	}
    }
	
	@Override
	public void borrarTbasvalsit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws Exception
	{
		Map<String,String>params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		logger.debug(
				new StringBuilder()
				.append("\n***********************************************")
				.append("\n****** PKG_SATELITES2.P_BORRA_TBASVALSIT ******")
				.append("\n****** params=").append(params)
				.append("\n***********************************************")
				.toString()
				);
		ejecutaSP(new BorrarTbasvalsit(getDataSource()),params);
	}
	
	protected class BorrarTbasvalsit extends StoredProcedure
	{
		protected BorrarTbasvalsit(DataSource dataSource)
		{
    		super(dataSource,"PKG_SATELITES2.P_BORRA_TBASVALSIT");	
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
	public void borrarTconvalsit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws Exception
	{
		Map<String,String>params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		logger.debug(
				new StringBuilder()
				.append("\n***********************************************")
				.append("\n****** PKG_SATELITES2.P_BORRA_TCONVALSIT ******")
				.append("\n****** params=").append(params)
				.append("\n***********************************************")
				.toString()
				);
		ejecutaSP(new BorrarTconvalsit(getDataSource()),params);
	}
	
	protected class BorrarTconvalsit extends StoredProcedure
	{
		protected BorrarTconvalsit(DataSource dataSource)
		{
    		super(dataSource,"PKG_SATELITES2.P_BORRA_TCONVALSIT");	
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
	public void movimientoMpolisit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String status
			,String cdtipsit
			,String swreduci
			,String cdagrupa
			,String cdestado
			,Date   fefecsit
			,Date   fecharef
			,String cdgrupo
			,String nmsituaext
			,String nmsitaux
			,String nmsbsitext
			,String cdplan
			,String cdasegur
			,String accion)throws Exception
	{
		Map<String,Object>params=new LinkedHashMap<String,Object>();
		params.put("cdunieco"   , cdunieco);
		params.put("cdramo"     , cdramo);
		params.put("estado"     , estado);
		params.put("nmpoliza"   , nmpoliza);
		params.put("nmsituac"   , nmsituac);
		params.put("nmsuplem"   , nmsuplem);
		params.put("status"     , status);
		params.put("cdtipsit"   , cdtipsit);
		params.put("swreduci"   , swreduci);
		params.put("cdagrupa"   , cdagrupa);
		params.put("cdestado"   , cdestado);
		params.put("fefecsit"   , fefecsit);
		params.put("fecharef"   , fecharef);
		params.put("cdgrupo"    , cdgrupo);
		params.put("nmsituaext" , nmsituaext);
		params.put("nmsitaux"   , nmsitaux);
		params.put("nmsbsitext" , nmsbsitext);
		params.put("cdplan"     , cdplan);
		params.put("cdasegur"   , cdasegur);
		params.put("accion"     , accion);
		logger.debug(
				new StringBuilder()
				.append("\n******************************************")
				.append("\n****** PKG_SATELITES.P_MOV_MPOLISIT ******")
				.append("\n****** params=").append(params)
				.append("\n******************************************")
				.toString()
				);
		ejecutaSP(new MovimientoMpolisit(getDataSource()),params);
	}
	
	protected class MovimientoMpolisit extends StoredProcedure
	{
		protected MovimientoMpolisit(DataSource dataSource)
		{
	        super(dataSource,"PKG_SATELITES.P_MOV_MPOLISIT");
		    declareParameter(new SqlParameter("cdunieco"   , OracleTypes.NUMERIC));
		    declareParameter(new SqlParameter("cdramo"     , OracleTypes.NUMERIC));
		    declareParameter(new SqlParameter("estado"     , OracleTypes.VARCHAR));
		    declareParameter(new SqlParameter("nmpoliza"   , OracleTypes.NUMERIC));
		    declareParameter(new SqlParameter("nmsituac"   , OracleTypes.NUMERIC));
		    declareParameter(new SqlParameter("nmsuplem"   , OracleTypes.NUMERIC));
		    declareParameter(new SqlParameter("status"     , OracleTypes.VARCHAR));
		    declareParameter(new SqlParameter("cdtipsit"   , OracleTypes.VARCHAR));
		    declareParameter(new SqlParameter("swreduci"   , OracleTypes.VARCHAR));
		    declareParameter(new SqlParameter("cdagrupa"   , OracleTypes.NUMERIC));
		    declareParameter(new SqlParameter("cdestado"   , OracleTypes.NUMERIC));
		    declareParameter(new SqlParameter("fefecsit"   , OracleTypes.DATE));
		    declareParameter(new SqlParameter("fecharef"   , OracleTypes.DATE));
		    declareParameter(new SqlParameter("cdgrupo"    , OracleTypes.VARCHAR));
		    declareParameter(new SqlParameter("nmsituaext" , OracleTypes.VARCHAR));
		    declareParameter(new SqlParameter("nmsitaux"   , OracleTypes.NUMERIC));
		    declareParameter(new SqlParameter("nmsbsitext" , OracleTypes.VARCHAR));
		    declareParameter(new SqlParameter("cdplan"     , OracleTypes.VARCHAR));
		    declareParameter(new SqlParameter("cdasegur"   , OracleTypes.VARCHAR));
		    declareParameter(new SqlParameter("accion"     , OracleTypes.VARCHAR));
		    declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
		    declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
		    compile();
		}
	}
	
	@Override
	public void movimientoTvalosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String status
			,String cdtipsit
			,Map<String,String>valores
			,String accion
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsituac" , nmsituac);
		params.put("nmsuplem" , nmsuplem);
		params.put("status"   , status);
		params.put("cdtipsit" , cdtipsit);
		for(int i=1;i<=99;i++)
		{
			params.put(new StringBuilder("otvalor").append(StringUtils.leftPad(String.valueOf(i),2,"0")).toString(),null);
		}
		params.put("accion"   , accion);
		params.putAll(valores);
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************")
				.append("\n****** PKG_SATELITES2.P_MOV_TVALOSIT ******")
				.append("\n****** params=").append(params)
				.append("\n*******************************************")
				.toString()
				);
		ejecutaSP(new MovimientoTvalosit(getDataSource()),params);
	}
	
	protected class MovimientoTvalosit extends StoredProcedure
	{
		protected MovimientoTvalosit(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_MOV_TVALOSIT");
			
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			for(int i=1;i<=99;i++)
			{
				declareParameter(new SqlParameter(
						new StringBuilder("otvalor")
						.append(StringUtils.leftPad(String.valueOf(i),2,"0"))
						.toString()
						,OracleTypes.VARCHAR));
			}
			declareParameter(new SqlParameter("accion" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void clonarPersonas(
			String cdelemen
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdtipsit
			,Date   fecha
			,String cdusuari
			,String nombre1
			,String nombre2
			,String apellido1
			,String apellido2
			,String sexo
			,Date   fenacimi
			,String parentesco
			)throws Exception
	{
		Map<String,Object>params = new LinkedHashMap<String,Object>();
		params.put("cdelemen"   , cdelemen);
		params.put("cdunieco"   , cdunieco);
		params.put("cdramo"     , cdramo);
		params.put("estado"     , estado);
		params.put("nmpoliza"   , nmpoliza);
		params.put("nmsituac"   , nmsituac);
		params.put("cdtipsit"   , cdtipsit);
		params.put("fecha"      , fecha);
		params.put("cdusuari"   , cdusuari);
		params.put("nombre1"    , nombre1);
		params.put("nombre2"    , nombre2);
		params.put("apellido1"  , apellido1);
		params.put("apellido2"  , apellido2);
		params.put("sexo"       , sexo);
		params.put("fenacimi"   , fenacimi);
		params.put("parentesco" , parentesco);
		Utils.debugProcedure(logger, "PKG_COTIZA.P_CLONAR_PERSONAS", params);
		ejecutaSP(new ClonarPersonas(getDataSource()),params);
	}
    
    protected class ClonarPersonas extends StoredProcedure
    {
    	protected ClonarPersonas(DataSource dataSource)
        {
            super(dataSource,"PKG_COTIZA.P_CLONAR_PERSONAS");
            declareParameter(new SqlParameter("cdelemen"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdunieco"   , OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("cdramo"     , OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("estado"     , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza"   , OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("nmsituac"   , OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("cdtipsit"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("fecha"      , OracleTypes.DATE));
            declareParameter(new SqlParameter("cdusuari"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nombre1"    , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nombre2"    , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("apellido1"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("apellido2"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("sexo"       , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("fenacimi"   , OracleTypes.DATE));
            declareParameter(new SqlParameter("parentesco" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public List<Map<String,String>>cargarResultadosCotizacion(
			String cdusuari
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdelemen
			,String cdtipsit
			)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdusuari" , cdusuari);
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	params.put("cdelemen" , cdelemen);
    	params.put("cdtipsit" , cdtipsit);
    	logger.debug(
    			new StringBuilder()
    			.append("\n*******************************************")
    			.append("\n****** PKG_COTIZA.P_GEN_TARIFICACION ******")
    			.append("\n****** params=").append(params)
    			.append("\n*******************************************")
    			.toString()
    			);
    	Map<String,Object>procResult  = ejecutaSP(new CargarResultadosCotizacion(getDataSource()),params);
    	logger.debug(procResult);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_record_o");
    	if(lista==null||lista.size()==0)
    	{
    		throw new ApplicationException("No hay resultados de cotizacion");
    	}
    	logger.debug(
    			new StringBuilder()
    			.append("\n*******************************************")
    			.append("\n****** params=")  .append(params)
    			.append("\n****** registro=").append(lista)
    			.append("\n****** PKG_COTIZA.P_GEN_TARIFICACION ******")
    			.append("\n*******************************************")
    			.toString()
    			);
    	return lista;
	}
    
    protected class CargarResultadosCotizacion extends StoredProcedure
    {
    	protected CargarResultadosCotizacion(DataSource dataSource)
        {
            super(dataSource,"PKG_COTIZA.P_GEN_TARIFICACION");
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdelemen" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
            String[] cols = new String[]{
            		"CDIDENTIFICA" , "CDUNIECO" , "CDRAMO"    , "ESTADO"
            		,"NMPOLIZA"    , "NMSUPLEM" , "STATUS"    , "CDPLAN"
            		,"DSPLAN"      , "MNPRIMA"  , "CDCIAASEG" , "DSUNIECO"
            		,"CDPERPAG"    , "DSPERPAG" , "CDTIPSIT"  , "FEEMISIO"
            		,"FEVENCIM"    , "NMSITUAC"
            };
            declareParameter(new SqlOutParameter("pv_record_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
     @Override
     public List<Map<String,String>>cargarParametrizacionExcel(
 			String proceso
 			,String cdramo
 			,String cdtipsit)throws Exception
 	{
    	 Map<String,String>params=new LinkedHashMap<String,String>();
    	 params.put("proceso"  , proceso);
    	 params.put("cdramo"   , cdramo);
    	 params.put("cdtipsit" , cdtipsit);
    	 logger.debug(
    			 new StringBuilder()
    			 .append("\n******************************************************")
    			 .append("\n****** PKG_SATELITES2.P_GET_CONFIGURACION_EXCEL ******")
    			 .append("\n****** params=").append(params)
    			 .append("\n******************************************************")
    			 .toString()
    			 );
    	 Map<String,Object>procResult  = ejecutaSP(new CargarParametrizacionExcel(getDataSource()),params);
    	 List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	 if(lista==null||lista.size()==0)
    	 {
    		 throw new ApplicationException("No hay configuracion de excel para el proceso/producto/modalidad");
    	 }
    	 return lista;
 	}
     
     protected class CargarParametrizacionExcel extends StoredProcedure
     {
     	protected CargarParametrizacionExcel(DataSource dataSource)
         {
             super(dataSource,"PKG_SATELITES2.P_GET_CONFIGURACION_EXCEL");
             declareParameter(new SqlParameter("proceso"  , OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
             String[] cols = new String[]{
             		"COLUMNA"    , "CDTIPSIT"        , "PROPIEDAD"  , "TIPO"
             		,"REQUERIDO" , "DECODE"          , "CDTABLA1"   , "TIPOATRI"
             		,"VALOR"     , "ORIGEN_CDTIPSIT" , "OTRA_PROP1" , "OTRO_VAL1"
             		,"DESCRIPCION"
             };
             declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
             declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
             declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
             compile();
     	}
     }
     
     @Override
     public String cargarClaveTtapvat1(
    		 String cdtabla
    		 ,String otvalor
    		 ,Map<String,List<Map<String,String>>>buffer
    		 )throws Exception
     {
    	 List<Map<String,String>> lista = null;
    	 if(buffer!=null&&buffer.containsKey(cdtabla))
    	 {
    		 lista = buffer.get(cdtabla);
    		 logger.debug(Utils.join(
    				  "\n*****************************************"
    				 ,"\n****** P_GET_CLAVE_TTAPVAT1 buffer ******"
    				 ,"\n****** lista="    , lista
    				 ,"\n****** buscando=" , otvalor
    				 ,"\n*****************************************"
    				 ));
    	 }
    	 else
    	 {
    		 Map<String,String>params=new LinkedHashMap<String,String>();
        	 params.put("cdtabla" , cdtabla);
        	 logger.debug(Utils.join(
        			  "\n*************************************************"
        			 ,"\n****** PKG_SATELITES2.P_GET_CLAVE_TTAPVAT1 ******"
        			 ,"\n****** params=",params
        			 ,"\n*************************************************"
        	 ));
        	 Map<String,Object> procResult = ejecutaSP(new CargarClaveTtapvat1(getDataSource()),params);
        	 lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
        	 Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_CLAVE_TTAPVAT1", params, lista);
        	 if(buffer!=null&&lista!=null&&lista.size()>0)
        	 {
        		 buffer.put(cdtabla,lista);
        	 }
    	 }
    	 if(lista==null||lista.size()==0)
    	 {
    		 throw new ApplicationException(Utils.join(
    				 "No hay registros en la(s) tabla(s) de apoyo"
    				 ));
    	 }
    	 String otclave = null;
    	 for(Map<String,String>elem:lista)
    	 {
    		 if(elem.get("OTVALOR").equals(otvalor))
    		 {
    			 otclave = elem.get("OTCLAVE");
    		 }
    	 }
    	 if(otclave==null)
    	 {
    		 throw new ApplicationException("No existe la clave en los registros de la tabla de apoyo");
    	 }
    	 return otclave;
     }
     
     protected class CargarClaveTtapvat1 extends StoredProcedure
     {
     	protected CargarClaveTtapvat1(DataSource dataSource)
         {
             super(dataSource,"PKG_SATELITES2.P_GET_CLAVE_TTAPVAT1");
             declareParameter(new SqlParameter("cdtabla" , OracleTypes.VARCHAR));
             declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(new String[]{"OTCLAVE","OTVALOR"})));
             declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
             declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
             compile();
     	}
     }
     
     @Override
     public List<Map<String,String>>cargarResultadosCotizacionAutoFlotilla(
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
    	 Utils.debugProcedure(logger,"PKG_SATELITES2.P_GEN_TARIFICA_AUTO_FLOT",params);
    	 Map<String,Object>procResult  = ejecutaSP(new CargarResultadosCotizacionAutoFlotilla(getDataSource()),params);
    	 List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	 if(lista==null||lista.size()==0)
    	 {
    		 throw new ApplicationException("No hay resultados de cotizacion");
    	 }
    	 Utils.debugProcedure(logger,"PKG_SATELITES2.P_GEN_TARIFICA_AUTO_FLOT",params,lista);
    	 return lista;
 	}
     
     protected class CargarResultadosCotizacionAutoFlotilla extends StoredProcedure
     {
     	protected CargarResultadosCotizacionAutoFlotilla(DataSource dataSource)
         {
             super(dataSource,"PKG_SATELITES2.P_GEN_TARIFICA_AUTO_FLOT");
             declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
             String[] cols=new String[]{"CDPERPAG","DSPERPAG","PRIMA"};
             declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
             declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
             declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
             compile();
     	}
     }
     
     @Override
     public List<Map<String,String>>cargarDetallesCotizacionAutoFlotilla(
 			String cdunieco
 			,String cdramo
 			,String estado
 			,String nmpoliza
 			,String cdperpag
 			)throws Exception
 	{
    	 Map<String,String>params=new LinkedHashMap<String,String>();
    	 params.put("cdunieco" , cdunieco);
    	 params.put("cdramo"   , cdramo);
    	 params.put("estado"   , estado);
    	 params.put("nmpoliza" , nmpoliza);
    	 params.put("cdperpag" , cdperpag);
    	 Utils.debugProcedure(logger,"PKG_SATELITES2.P_GET_DETALLE_COTI_AUTO_FLOT", params);
    	 Map<String,Object>procResult  = ejecutaSP(new CargarDetallesCotizacionAutoFlotilla(getDataSource()),params);
    	 List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	 if(lista==null||lista.size()==0)
    	 {
    		 throw new ApplicationException("No hay detalles de cotizacion");
    	 }
    	 Utils.debugProcedure(logger,"PKG_SATELITES2.P_GET_DETALLE_COTI_AUTO_FLOT", params,lista);
    	 return lista;
 	}
     
     protected class CargarDetallesCotizacionAutoFlotilla extends StoredProcedure
     {
     	protected CargarDetallesCotizacionAutoFlotilla(DataSource dataSource)
     	{
     		super(dataSource,"PKG_SATELITES2.P_GET_DETALLE_COTI_AUTO_FLOT");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdperpag" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DetalleCotizacionAutoFLotillaMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
     	}
     }
     
     protected class DetalleCotizacionAutoFLotillaMapper implements RowMapper<Map<String,String>> {
         public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
             Map<String, String> map = new HashMap<String, String>();
             map.put("CDUNIECO", rs.getString("CDUNIECO"));
             map.put("CDRAMO", rs.getString("CDRAMO"));
             map.put("ESTADO", rs.getString("ESTADO"));
             map.put("NMPOLIZA", rs.getString("NMPOLIZA"));
             map.put("NMSITUAC", rs.getString("NMSITUAC"));
             map.put("PRIMA", rs.getString("PRIMA"));
             map.put("COBERTURA", rs.getString("COBERTURA"));
             map.put("TITULO", rs.getString("TITULO"));
             map.put("ORDEN", rs.getString("ORDEN"));
             return map;
         }
     }
     
     @Override
     public List<Map<String,String>>cargarDetallesCoberturasCotizacionAutoFlotilla(
 			String cdunieco
 			,String cdramo
 			,String estado
 			,String nmpoliza
 			,String cdperpag
 			)throws Exception
 	{
    	 Map<String,String>params=new LinkedHashMap<String,String>();
    	 params.put("cdunieco" , cdunieco);
    	 params.put("cdramo"   , cdramo);
    	 params.put("estado"   , estado);
    	 params.put("nmpoliza" , nmpoliza);
    	 params.put("cdperpag" , cdperpag);
    	 Utils.debugProcedure(logger,"PKG_SATELITES2.P_GET_DETALLE_COBER_AUTO_FLOT", params);
    	 Map<String,Object>procResult  = ejecutaSP(new CargarDetallesCoberturasCotizacionAutoFlotilla(getDataSource()),params);
    	 List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	 if(lista==null||lista.size()==0)
    	 {
    		 throw new ApplicationException("No hay detalles de cotizacion");
    	 }
    	 Utils.debugProcedure(logger,"PKG_SATELITES2.P_GET_DETALLE_COBER_AUTO_FLOT", params,lista);
    	 return lista;
 	}
     
    protected class CargarDetallesCoberturasCotizacionAutoFlotilla extends StoredProcedure
     {
     	protected CargarDetallesCoberturasCotizacionAutoFlotilla(DataSource dataSource)
         {
             super(dataSource,"PKG_SATELITES2.P_GET_DETALLE_COBER_AUTO_FLOT");
             declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
             declareParameter(new SqlParameter("cdperpag" , OracleTypes.VARCHAR));
             String[] cols=new String[]{
            		 "CDUNIECO"
            		 ,"CDRAMO"
            		 ,"ESTADO"
            		 ,"NMPOLIZA"
            		 ,"NMSITUAC"
            		 ,"CDGARANT"
            		 ,"NMSUPLEM"
            		 ,"CDCAPITA"
            		 ,"COBERTURA"
            		 ,"SUMASEG"
            		 ,"TITULO"
            		 ,"ORDEN"
            		 };
             declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
             declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
             declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
             compile();
     	}
     }

    @Override
    public String cargarTabuladoresGMIParche(
    		String circulo
    		,String cdatribu)throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("circulo"  , circulo);
    	params.put("cdatribu" , cdatribu);
    	Utils.debugProcedure(logger, "PKG_LISTAS.P_RECUPERA_TABULADORES", params);
    	Map<String,Object>procResult     = ejecutaSP(new CargarTabuladoresGMIParche(getDataSource()),params);
    	List<Map<String,String>>listaAux = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(listaAux==null||listaAux.size()==0)
    	{
    		throw new ApplicationException("No hay tabulador");
    	}
    	if(listaAux.size()>1)
    	{
    		throw new ApplicationException("Tabulador duplicado");
    	}
    	return listaAux.get(0).get("TABULADOR");
    }
    
    protected class CargarTabuladoresGMIParche extends StoredProcedure
    {
    	protected CargarTabuladoresGMIParche(DataSource dataSource)
    	{
    		super(dataSource,"PKG_LISTAS.P_RECUPERA_TABULADORES");
            declareParameter(new SqlParameter("circulo"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdatribu" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(new String[]{ "TABULADOR" })));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public void sigsvdefEnd(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdgarant
			,String cdtipsup)throws Exception
	{
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	params.put("nmsituac" , nmsituac);
    	params.put("nmsuplem" , nmsuplem);
    	params.put("cdgarant" , cdgarant);
    	params.put("cdtipsup" , cdtipsup);
    	Utils.debugProcedure(logger, "P_EXEC_SIGSVDEFEND", params);
    	ejecutaSP(new SigsvdefEnd(getDataSource()),params);
	}
    
    protected class SigsvdefEnd extends StoredProcedure
    {
    	protected SigsvdefEnd(DataSource dataSource)
    	{
    		super(dataSource,"P_EXEC_SIGSVDEFEND");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdgarant" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public void borrarMpoliperSituac0(
    		String cdunieco
    		,String cdramo
    		,String estado
    		,String nmpoliza
    		,String nmsuplem
    		,String cdrol)throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	params.put("nmsituac" , null);
    	params.put("cdrol"    , cdrol);
    	params.put("cdperson" , null);
    	params.put("nmsuplem" , nmsuplem);
    	params.put("status"   , null);
    	params.put("nmorddom" , null);
    	params.put("swreclam" , null);
    	params.put("accion"   , null);
    	Utils.debugProcedure(logger, "PKG_SATELITES.P_BORRA_MPOLIPER", params);
    	ejecutaSP(new BorrarMpoliperSituac0(getDataSource()),params);
    }
    
    protected class BorrarMpoliperSituac0 extends StoredProcedure
    {
    	protected BorrarMpoliperSituac0(DataSource dataSource)
    	{
    		super(dataSource,"PKG_SATELITES.P_BORRA_MPOLIPER");
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
            declareParameter(new SqlParameter("accion"   , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public String cargarTipoVehiculoRamo5(String clave)throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("clave" , clave);
    	Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_TIPOVEHI_RAMO5", params);
    	Map<String,Object>procResult  = ejecutaSP(new CargarTipoVehiculoRamo5(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null||lista.size()==0)
    	{
    		throw new ApplicationException("No se encontro el tipo de vehiculo");
    	}
    	if(lista.size()>1)
    	{
    		throw new ApplicationException("Tipo de vehiculo duplicado");
    	}
    	return lista.get(0).get("TIPOVEHI");
    }
    
    protected class CargarTipoVehiculoRamo5 extends StoredProcedure
    {
    	protected CargarTipoVehiculoRamo5(DataSource dataSource)
    	{
    		super(dataSource,"PKG_SATELITES2.P_GET_TIPOVEHI_RAMO5");
            declareParameter(new SqlParameter("clave" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(new String[]{"TIPOVEHI"})));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    @Override
    public Map<String,String>cargarDetalleNegocioRamo5(String negocio, String cdramo, String cdtipsit, String cdsisrol, String cdusuari) throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("negocio" , negocio);
    	params.put("cdramo" , cdramo);
    	params.put("cdtipsit" , cdtipsit);
    	params.put("cdsisrol" , cdsisrol);
    	params.put("cdusuari" , cdusuari);
    	Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_DETALLE_NEGOCIO_RAMO5", params);
    	Map<String,Object>procResult  = ejecutaSP(new CargarDetalleNegocioRamo5(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null||lista.size()==0)
    	{
    		throw new ApplicationException("No se encontro detalle de negocio");
    	}
    	if(lista.size()>1)
    	{
    		throw new ApplicationException("Detalle de negocio duplicado");
    	}
    	return lista.get(0);
    }
    
    protected class CargarDetalleNegocioRamo5 extends StoredProcedure
    {
    	protected CargarDetalleNegocioRamo5(DataSource dataSource)
    	{
    		super(dataSource,"PKG_SATELITES2.P_GET_DETALLE_NEGOCIO_RAMO5");
    		declareParameter(new SqlParameter("negocio" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdramo"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdtipsit", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdsisrol", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdusuari", OracleTypes.VARCHAR));
    		String[] cols=new String[]{
    				"TARIFA"
    				,"UDI"
    				,"MULTIANUAL"
    				,"PERIODO_GRACIA"
    				,"FONDO_ESPECIAL"
    				,"F1"
    				,"F2"
    				,"F3"
    				,"PORC_BONO"
    				};
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
    		compile();
    	}
    }
    
    @Override
    public Map<String,List<Map<String,String>>>cargarConfiguracionTvalositFlotillas(
    		String cdramo
    		,String cdtipsit
    		,String negocio
    		)throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdramo"   , cdramo);
    	params.put("cdtipsit" , cdtipsit);
    	params.put("negocio"  , negocio);
    	Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_CONFIG_VALOSIT_FLOTILLAS", params);
    	Map<String,Object>procResult   = ejecutaSP(new CargarConfiguracionTvalositFlotillas(getDataSource()),params);
    	List<Map<String,String>>lista  = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	List<Map<String,String>>lista2 = (List<Map<String,String>>)procResult.get("pv_atrirang_o");
    	Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_CONFIG_VALOSIT_FLOTILLAS config", params, lista);
    	Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_CONFIG_VALOSIT_FLOTILLAS rangos", params, lista2);
    	if(lista==null||lista.size()==0)
    	{
    		throw new ApplicationException(Utils.join("No hay configuracion para los valores del ramo ",cdramo," y subramo ",cdtipsit));
    	}
    	if(lista2==null)
    	{
    		lista2 = new ArrayList<Map<String,String>>();
    	}
    	Map<String,List<Map<String,String>>> res = new HashMap<String,List<Map<String,String>>>();
    	res.put("config" , lista);
    	res.put("rangos" , lista2);
    	return res;
    }
    
    protected class CargarConfiguracionTvalositFlotillas extends StoredProcedure
    {
    	protected CargarConfiguracionTvalositFlotillas(DataSource dataSource)
    	{
    		super(dataSource,"PKG_SATELITES2.P_GET_CONFIG_VALOSIT_FLOTILLAS");
    		declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("negocio"  , OracleTypes.VARCHAR));
    		String[] cols=new String[]{
    				"CDATRIBU"
    				,"VALOR"
    				};
    		String[] cols2=new String[]{
    				"CDTIPSIT"
    				,"CDATRIBU"
    				,"MINIMO"
    				,"MAXIMO"
    				};
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_atrirang_o" , OracleTypes.CURSOR, new GenericMapper(cols2)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
    		compile();
    	}
    }
    
    @Override
    public boolean cargarBanderaCambioCuadroPorProducto(String cdramo)throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdramo" , cdramo);
    	Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_BANDERA_CAMBIO_CUACOM", params);
    	Map<String,Object>procResult  = ejecutaSP(new CargarBanderaCambioCuadroPorProducto(getDataSource()),params);
    	String bandera                = (String)procResult.get("pv_bandera_o");
    	return StringUtils.isNotBlank(bandera)&&bandera.equals("S");
    }
    
    protected class CargarBanderaCambioCuadroPorProducto extends StoredProcedure
    {
    	protected CargarBanderaCambioCuadroPorProducto(DataSource dataSource)
    	{
    		super(dataSource,"PKG_CONSULTA.P_GET_BANDERA_CAMBIO_CUACOM");
    		declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_bandera_o" , OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
    		compile();
    	}
    }
    
    @Override
    public Map<String,String>cargarRangoDescuentoRamo5TodasSituaciones(
    		String cdagente
    		,String negocio
    		,String cdsisrol
    		,String cdusuari
    		)throws Exception
    {
    	Map<String,String>params=new LinkedHashMap<String,String>();
    	params.put("cdagente" , cdagente);
    	params.put("negocio"  , negocio);
    	params.put("cdsisrol" , cdsisrol);
    	params.put("cdusuari" , cdusuari);
    	Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_DESCUENTO_RAMO5_TODAS",params);
    	Map<String,Object>procResult  = ejecutaSP(new CargarRangoDescuentoRamo5TodasSituaciones(getDataSource()),params);
    	List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No hay descuento/recargo para el agente");
		}
		if(lista.size()>1)
		{
			throw new ApplicationException("Descuento/recargo para el agente duplicado");
		}
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_GET_DESCUENTO_RAMO5_TODAS",params,lista);
		return lista.get(0);
    }
	
	protected class CargarRangoDescuentoRamo5TodasSituaciones extends StoredProcedure
	{
		protected CargarRangoDescuentoRamo5TodasSituaciones(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_GET_DESCUENTO_RAMO5_TODAS");
			declareParameter(new SqlParameter("cdagente" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("negocio"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "min" , "max" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void validarCodpostalTarifa(String codpostal,String cdtipsit)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("codpostal" , codpostal);
		params.put("cdtipsit"  , cdtipsit);
		Utils.debugProcedure(logger, "PKG_SATELITES.P_VALIDA_TARIFA",params);
		ejecutaSP(new ValidarCodpostalTarifa(getDataSource()),params);
	}
	
	protected class ValidarCodpostalTarifa extends StoredProcedure
	{
		protected ValidarCodpostalTarifa(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_VALIDA_TARIFA");
			declareParameter(new SqlParameter("codpostal" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public boolean validaDomicilioCotizacionTitular(Map<String,String> params)throws Exception{
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_VALIDA_DOMICILIO_TITULAR",params);
		
		Map<String,Object>procResult=ejecutaSP(new ValidaDomicilCotTitular(getDataSource()),params);
		String resVal = (String)procResult.get("pv_swdomici_o");
		
		boolean resValidacion =(StringUtils.isNotBlank(resVal)&&resVal.equalsIgnoreCase("S"));
		
		logger.debug(Utils.join("PKG_SATELITES2.P_VALIDA_DOMICILIO_TITULAR result=",resValidacion));
		return resValidacion;
	}
	
	protected class ValidaDomicilCotTitular extends StoredProcedure
	{
		protected ValidaDomicilCotTitular(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_VALIDA_DOMICILIO_TITULAR");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_swdomici_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public boolean validarCuadroComisionNatural(
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
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_VALIDA_CUADRO_COM_NATURAL",params);
		Map<String,Object>procResult=ejecutaSP(new ValidarCuadroComisionNatural(getDataSource()),params);
		String cuadroNatural=(String)procResult.get("pv_swcamcua_o");
		boolean cuadroNaturalBol=StringUtils.isNotBlank(cuadroNatural)&&cuadroNatural.equalsIgnoreCase("S");
		logger.debug(Utils.join("PKG_SATELITES2.P_VALIDA_CUADRO_COM_NATURAL result=",cuadroNatural));
		return cuadroNaturalBol;
	}
	
	protected class ValidarCuadroComisionNatural extends StoredProcedure
	{
		protected ValidarCuadroComisionNatural(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_VALIDA_CUADRO_COM_NATURAL");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_swcamcua_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void aplicarAjustesCotizacionPorProducto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsit
			,String tipocot
			)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdtipsit" , cdtipsit);
		params.put("tipocot"  , tipocot);
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_AJUSTES_COTIZACION_PRODUCTO", params);
		ejecutaSP(new AplicarAjustesCotizacionPorProducto(getDataSource()),params);
	}
	
	protected class AplicarAjustesCotizacionPorProducto extends StoredProcedure
	{
		protected AplicarAjustesCotizacionPorProducto(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_AJUSTES_COTIZACION_PRODUCTO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipocot"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<List<Map<String,String>>>cargarParamerizacionConfiguracionCoberturasRol(
			String cdtipsit
			,String cdsisrol)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdtipsit"     , cdtipsit);
		params.put("cdsisrol"     , cdsisrol);
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_PARAMS_CONFIG_AUTO_ROL ******")
				.append("\n****** params=").append(params)
				.append("\n*******************************************************")
				.toString()
				);
		Map<String,Object>procResult    = ejecutaSP(new CargarParamerizacionConfiguracionCoberturasRol(getDataSource()),params);
		List<Map<String,String>>tatrist = (List<Map<String,String>>)procResult.get("pv_cur_tatrisit_o");
		if(tatrist==null)
		{
			tatrist=new ArrayList<Map<String,String>>();
		}
		List<Map<String,String>>atrixrol = (List<Map<String,String>>)procResult.get("pv_cur_atrixrol_o");
		if(atrixrol==null)
		{
			atrixrol=new ArrayList<Map<String,String>>();
		}
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************************")
				.append("\n****** params=")  .append(params)
				.append("\n****** tatrist=") .append(tatrist)
				.append("\n****** atrixrol=").append(atrixrol)
				.append("\n****** PKG_CONSULTA.P_GET_PARAMS_CONFIG_AUTO_ROL ******")
				.append("\n*******************************************************")
				.toString()
				);
		List<List<Map<String,String>>>lista=new ArrayList<List<Map<String,String>>>();
		lista.add(tatrist);
		lista.add(atrixrol);
		return lista;
	}
	
	protected class CargarParamerizacionConfiguracionCoberturasRol extends StoredProcedure
	{
		protected CargarParamerizacionConfiguracionCoberturasRol(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_PARAMS_CONFIG_AUTO_ROL");
			declareParameter(new SqlParameter("cdtipsit"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol"     , OracleTypes.VARCHAR));
			String[] cols  = new String[]{ "cdatribu" , "aplica" , "valor" };
			declareParameter(new SqlOutParameter("pv_cur_tatrisit_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_cur_atrixrol_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"       , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"        , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String cargarPorcentajeCesionComisionAutos(
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
		Utils.debugProcedure(logger, "PKG_CONSULTA.P_GET_PORC_CESION_COMISION", params);
		Map<String,Object>procResult = ejecutaSP(new CargarPorcentajeCesionComisionAutos(getDataSource()),params);
		String cesion                = (String)procResult.get("pv_porreadu_o");
		if(StringUtils.isBlank(cesion))
		{
			throw new ApplicationException("No se recupero cesion de comision para la poliza");
		}
		logger.debug(Utils.join("****** PKG_CONSULTA.P_GET_PORC_CESION_COMISION recupera: ",cesion));
		return cesion;
	}
	
	protected class CargarPorcentajeCesionComisionAutos extends StoredProcedure
	{
		protected CargarPorcentajeCesionComisionAutos(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_PORC_CESION_COMISION");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_porreadu_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void ejecutaValoresDefectoTarificacionConcurrente(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String tipotari
			,String cdperpag
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("nmsituac" , nmsituac);
		params.put("tipotari" , tipotari);
		params.put("cdperpag" , cdperpag);
		Utils.debugProcedure(logger, "P_EJECUTA_LANZATARI", params);
		ejecutaSP(new EjecutaValoresDefectoTarificacionConcurrente(getDataSource()),params);
	}
	
	protected class EjecutaValoresDefectoTarificacionConcurrente extends StoredProcedure
	{
		protected EjecutaValoresDefectoTarificacionConcurrente(DataSource dataSource)
		{
			super(dataSource,"P_EJECUTA_LANZATARI");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipotari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperpag" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoMpolisitLote(List<PMovMpolisitDTO> lista)throws Exception
	{
		String[][] array = new String[lista.size()][];
		int        i     = 0;
		for(PMovMpolisitDTO pMovIte : lista)
		{
			array[i++] = pMovIte.indexar();
		}
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("array" , new SqlArrayValue(array));
		Utils.debugProcedure(logger, "PKG_PROCESA_XML.PR_PROC_MPOLISIT_LISTA", params);
		ejecutaSP(new MovimientoMpolisitArray(getDataSource()),params);
	}
	
	protected class MovimientoMpolisitArray extends StoredProcedure
	{
		protected MovimientoMpolisitArray(DataSource dataSource)
		{
			super(dataSource,"PKG_PROCESA_XML.PR_PROC_MPOLISIT_LISTA");
			declareParameter(new SqlParameter("array" , OracleTypes.ARRAY , "LISTA_LISTAS_VARCHAR2"));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoTvalositLote(List<PMovTvalositDTO> lista)throws Exception
	{
		String[][] array = new String[lista.size()][];
		int        i     = 0;
		for(PMovTvalositDTO pMovIte : lista)
		{
			array[i++] = pMovIte.indexar();
		}
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("array" , new SqlArrayValue(array));
		Utils.debugProcedure(logger, "PKG_PROCESA_XML.PR_PROC_TVALOSIT_LISTA", params);
		ejecutaSP(new MovimientoTvalositArray(getDataSource()),params);
	}
	
	protected class MovimientoTvalositArray extends StoredProcedure
	{
		protected MovimientoTvalositArray(DataSource dataSource)
		{
			super(dataSource,"PKG_PROCESA_XML.PR_PROC_TVALOSIT_LISTA");
			declareParameter(new SqlParameter("array" , OracleTypes.ARRAY , "LISTA_LISTAS_VARCHAR2"));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoTbasvalsitLote(List<PInsertaTbasvalsitDTO> lista)throws Exception
	{
		String[][] array = new String[lista.size()][];
		int        i     = 0;
		for(PInsertaTbasvalsitDTO pMovIte : lista)
		{
			array[i++] = pMovIte.indexar();
		}
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("array" , new SqlArrayValue(array));
		Utils.debugProcedure(logger, "PKG_PROCESA_XML.PR_PROC_TBASVALSIT_LISTA", params);
		ejecutaSP(new MovimientoTbasvalsitArray(getDataSource()),params);
	}
	
	protected class MovimientoTbasvalsitArray extends StoredProcedure
	{
		protected MovimientoTbasvalsitArray(DataSource dataSource)
		{
			super(dataSource,"PKG_PROCESA_XML.PR_PROC_TBASVALSIT_LISTA");
			declareParameter(new SqlParameter("array" , OracleTypes.ARRAY , "LISTA_LISTAS_VARCHAR2"));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoTconvalsitLote(List<PInsertaTconvalsitDTO> lista)throws Exception
	{
		String[][] array = new String[lista.size()][];
		int        i     = 0;
		for(PInsertaTconvalsitDTO pMovIte : lista)
		{
			array[i++] = pMovIte.indexar();
		}
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("array" , new SqlArrayValue(array));
		Utils.debugProcedure(logger, "PKG_PROCESA_XML.PR_PROC_TCONVALSIT_LISTA", params);
		ejecutaSP(new MovimientoTconvalsitArray(getDataSource()),params);
	}
	
	protected class MovimientoTconvalsitArray extends StoredProcedure
	{
		protected MovimientoTconvalsitArray(DataSource dataSource)
		{
			super(dataSource,"PKG_PROCESA_XML.PR_PROC_TCONVALSIT_LISTA");
			declareParameter(new SqlParameter("array" , OracleTypes.ARRAY , "LISTA_LISTAS_VARCHAR2"));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoMpoliagr(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdagrupa
			,String nmsuplem
			,String status
			,String cdperson
			,String nmorddom
			,String cdforpag
			,String cdbanco
			,String cdsucurs
			,String cdcuenta
			,String cdrazon
			,String swregula
			,String cdperreg
			,Date feultreg
			,String cdgestor
			,String cdrol
			,String cdbanco2
			,String cdsucurs2
			,String cdcuenta2
			,String cdtipcta
			,String cdtipcta2
			,String cdpagcom
			,String nmpresta
			,String nmpresta2
			,String cdbanco3
			,String cdsucurs3
			,String cdcuenta3
			,String cdtipcta3
			,String nmpresta3
			,String nmcuenta
			,String accion
			)throws Exception
	{
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("cdunieco"  , cdunieco);
		params.put("cdramo"    , cdramo);
		params.put("estado"    , estado);
		params.put("nmpoliza"  , nmpoliza);
		params.put("cdagrupa"  , cdagrupa);
		params.put("nmsuplem"  , nmsuplem);
		params.put("status"    , status);
		params.put("cdperson"  , cdperson);
		params.put("nmorddom"  , nmorddom);
		params.put("cdforpag"  , cdforpag);
		params.put("cdbanco"   , cdbanco);
		params.put("cdsucurs"  , cdsucurs);
		params.put("cdcuenta"  , cdcuenta);
		params.put("cdrazon"   , cdrazon);
		params.put("swregula"  , swregula);
		params.put("cdperreg"  , cdperreg);
		params.put("feultreg"  , feultreg);
		params.put("cdgestor"  , cdgestor);
		params.put("cdrol"     , cdrol);
		params.put("cdbanco2"  , cdbanco2);
		params.put("cdsucurs2" , cdsucurs2);
		params.put("cdcuenta2" , cdcuenta2);
		params.put("cdtipcta"  , cdtipcta);
		params.put("cdtipcta2" , cdtipcta2);
		params.put("cdpagcom"  , cdpagcom);
		params.put("nmpresta"  , nmpresta);
		params.put("nmpresta2" , nmpresta2);
		params.put("cdbanco3"  , cdbanco3);
		params.put("cdsucurs3" , cdsucurs3);
		params.put("cdcuenta3" , cdcuenta3);
		params.put("cdtipcta3" , cdtipcta3);
		params.put("nmpresta3" , nmpresta3);
		params.put("nmcuenta"  , nmcuenta);
		params.put("accion"    , accion);
		Utils.debugProcedure(logger, "PKG_SATELITES.P_MOV_MPOLIAGR", params);
		ejecutaSP(new MovimientoMpoliagr(getDataSource()),params);
	}
	
	protected class MovimientoMpoliagr extends StoredProcedure
	{
		protected MovimientoMpoliagr(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.P_MOV_MPOLIAGR");
			declareParameter(new SqlParameter("cdunieco"  , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("cdagrupa"  , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("nmsuplem"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperson"  , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("nmorddom"  , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("cdforpag"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdbanco"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsucurs"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdcuenta"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdrazon"   , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("swregula"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperreg"  , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("feultreg"  , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdgestor"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdrol"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdbanco2"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsucurs2" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdcuenta2" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipcta"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipcta2" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdpagcom"  , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("nmpresta"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpresta2" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdbanco3"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsucurs3" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdcuenta3" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipcta3" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpresta3" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmcuenta"  , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("accion"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>validarReemplazoDocumentoCotizacion(String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		Map<String,Object> procResult = ejecutaSP(new ValidarReemplazoDocumentoCotizacion(getDataSource()),params);
		Map<String,String> result     = new LinkedHashMap<String,String>();
		result.put("cdtipsit" , (String)procResult.get("pv_cdtipsit_o"));
		result.put("cdplan"   , (String)procResult.get("pv_cdplan_o"));
		result.put("cdperpag" , (String)procResult.get("pv_cdperpag_o"));
		result.put("tipoflot" , (String)procResult.get("pv_tipoflot_o"));
		result.put("ntramite" , (String)procResult.get("pv_ntramite_o"));
		result.put("cdusuari" , (String)procResult.get("pv_cdusuari_o"));
		result.put("reporte"  , (String)procResult.get("pv_reporte_o"));
		return result;
	}
	
	protected class ValidarReemplazoDocumentoCotizacion extends StoredProcedure
	{
		protected ValidarReemplazoDocumentoCotizacion(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_VALIDA_REMPLAZO_DOC_COTI");
			declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdtipsit_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdplan_o"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdperpag_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tipoflot_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_ntramite_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdusuari_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_reporte_o"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void grabarEvento(
			StringBuilder sb
			,String cdmodulo
			,String cdevento
			,Date fecha
			,String cdusuari
			,String cdsisrol
			,String ntramite
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsolici
			,String cdagente
			,String cdusuariDes
			,String cdsisrolDes
			,String status
			)throws Exception
	{
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("cdmodulo"    , cdmodulo);
		params.put("cdevento"    , cdevento);
		params.put("fecha"       , fecha);
		params.put("cdusuari"    , cdusuari);
		params.put("cdsisrol"    , cdsisrol);
		params.put("ntramite"    , ntramite);
		params.put("cdunieco"    , cdunieco);
		params.put("cdramo"      , cdramo);
		params.put("estado"      , estado);
		params.put("nmpoliza"    , nmpoliza);
		params.put("nmsolici"    , nmsolici);
		params.put("cdagente"    , cdagente);
		params.put("cdusuariDes" , cdusuariDes);
		params.put("cdsisrolDes" , cdsisrolDes);
		params.put("status"      , status);
		sb.append(Utils.join(
				 "\n********************************************"
				,"\n****** PKG_ESTADISTICA.P_GRABA_EVENTO ******"
				,"\n****** params=" , params
				,"\n********************************************"
				));
		ejecutaSP(new GrabarEvento(getDataSource()),params);
	}
	
	protected class GrabarEvento extends StoredProcedure
	{
		protected GrabarEvento(DataSource dataSource)
		{
			super(dataSource, "PKG_ESTADISTICA.P_GRABA_EVENTO");
			declareParameter(new SqlParameter("cdmodulo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdevento"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("fecha"       , OracleTypes.TIMESTAMP));
			declareParameter(new SqlParameter("cdusuari"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsolici"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdagente"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuariDes" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrolDes" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"      , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void ejecutaValoresDefectoConcurrente(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String tipotari
			,String cdperpag
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("nmsituac" , nmsituac);
		params.put("tipotari" , tipotari);
		params.put("cdperpag" , cdperpag);
		Utils.debugProcedure(logger, "P_Ejecuta_lanzadef", params);
		ejecutaSP(new EjecutaValoresDefectoConcurrente(getDataSource()),params);
	}
	
	protected class EjecutaValoresDefectoConcurrente extends StoredProcedure
	{
		protected EjecutaValoresDefectoConcurrente(DataSource dataSource)
		{
			super(dataSource,"P_Ejecuta_lanzadef");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipotari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperpag" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void ejecutaTarificacionConcurrente(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String tipotari
			,String cdperpag
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("nmsituac" , nmsituac);
		params.put("tipotari" , tipotari);
		params.put("cdperpag" , cdperpag);
		Utils.debugProcedure(logger, "P_Ejecuta_lanzatar", params);
		ejecutaSP(new EjecutaTarificacionConcurrente(getDataSource()),params);
	}
	
	protected class EjecutaTarificacionConcurrente extends StoredProcedure
	{
		protected EjecutaTarificacionConcurrente(DataSource dataSource)
		{
			super(dataSource,"P_Ejecuta_lanzatar");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipotari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperpag" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void actualizaValoresDefectoSituacion(
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
		ejecutaSP(new ActualizaValoresDefectoSituacion(getDataSource()),params);
	}
	
	protected class ActualizaValoresDefectoSituacion extends StoredProcedure
	{
		protected ActualizaValoresDefectoSituacion(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_ACTUALIZA_VALORES_X_DEF_SIT");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public void procesaLayoutCensoMultisalud(
			String nombreArchivo
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdestadoCli
			,String cdmuniciCli
			,String cdplan1
			,String cdplan2
			,String cdplan3
			,String cdplan4
			,String cdplan5
			,String complemento
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("censo"       , nombreArchivo);
		params.put("cdunieco"    , cdunieco);
		params.put("cdramo"      , cdramo);
		params.put("estado"      , estado);
		params.put("nmpoliza"    , nmpoliza);
		params.put("cdestado"    , cdestadoCli);
		params.put("cdmunici"    , cdmuniciCli);
		params.put("cdplan1"     , cdplan1);
		params.put("cdplan2"     , cdplan2);
		params.put("cdplan3"     , cdplan3);
		params.put("cdplan4"     , cdplan4);
		params.put("cdplan5"     , cdplan5);
		params.put("complemento" , complemento);
		ejecutaSP(new ProcesaLayoutCensoMultisalud(getDataSource()),params);
	}
	
	protected class ProcesaLayoutCensoMultisalud extends StoredProcedure
	{
		protected ProcesaLayoutCensoMultisalud(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_LAYOUT_CENSO_MS_COLECTIVO");
			declareParameter(new SqlParameter("censo"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdestado"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdmunici"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan1"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan2"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan3"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan4"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan5"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("complemento" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
}