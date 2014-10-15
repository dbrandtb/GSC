package mx.com.gseguros.portal.cotizacion.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal.dao.ObtieneTatripolMapper;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.ObtieneTatrigarMapper;
import mx.com.gseguros.portal.cotizacion.model.ObtieneTatrisitMapper;
import mx.com.gseguros.portal.cotizacion.model.ParametroCotizacion;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.DinamicMapper;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
		params.put("cdmoneda" , cdmoneda);
		params.put("accion"   , accion);
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
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgarant" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdmoneda" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"   , OracleTypes.VARCHAR));
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
					,"otvalor50"
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
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
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
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
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
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
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
	public void guardarCensoCompleto(Map<String,String>params)throws Exception
	{
		ejecutaSP(new GuardarCensoCompleto(getDataSource()),params);
	}
	
	protected class GuardarCensoCompleto extends StoredProcedure
	{
		protected GuardarCensoCompleto(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_LAYOUT_CENSO_MS_COLEC_DEF");
			declareParameter(new SqlParameter("censo"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor04" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor05" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan1"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan2"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan3"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan4"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan5"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
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
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
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
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
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
	public Map<String,String>cargarTipoSituacion(Map<String,String>params)throws Exception
	{
		Map<String,Object>respuestaProcedure=ejecutaSP(new CargarTipoSituacion(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)respuestaProcedure.get("pv_registro_o");
		Map<String,String>respuesta=null;
		if(lista!=null&&lista.size()>0)
		{
			respuesta=lista.get(0);
		}
		return respuesta;
	}
	
	protected class CargarTipoSituacion extends StoredProcedure
	{
		protected CargarTipoSituacion(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TIPO_SITUACION");
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
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
			throw new Exception("No hay datos del agente");
		}
		else if(listaAux.size()>1)
		{
			throw new Exception("Datos repetidos");
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
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
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
			,String clave5)throws Exception,Exception
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
			throw new Exception("No hay parametros");
		}
		if(listaAux.size()>1)
		{
			throw new Exception("Parametros duplicados");
		}
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
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
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
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
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
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
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
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
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
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
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
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
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
		logger.debug(
				new StringBuilder()
				.append("\n*********************************************")
				.append("\n****** PKG_LISTAS.P_GET_ATRI_SITUACION ******")
				.append("\n****** params=").append(params)
				.append("\n*********************************************")
				.toString()
				);
		Map<String,Object>procResult = ejecutaSP(new CargarTatrisit(getDataSource()),params);
		List<ComponenteVO>lista      = (List<ComponenteVO>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new Exception("No hay tatrisit");
		}
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
	public List<ComponenteVO>cargarTatripol(String cdramo,String cdtipsit)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
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
			throw new Exception("No hay datos de usuario");
		}
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
				.append("\n*******************************************************")
				.append("\n****** PKG_COTIZA.P_OBTIENE_CPTOS_GOBALES_COLECT ******")
				.append("\n****** params=").append(params)
				.append("\n*******************************************************")
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
            super(dataSource,"PKG_COTIZA.P_OBTIENE_CPTOS_GOBALES_COLECT");
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
		
		for(int i=1;i<=50;i++)
		{
			params.put(new StringBuilder("otvalor").append(StringUtils.leftPad(String.valueOf(i),2,"0")).toString(),null);
		}
		
		params.putAll(valores);
		
		logger.debug(
				new StringBuilder()
				.append("\n*********************************************************")
				.append("\n****** PKG_SATELITES.P_ACT_MPOLISIT_TVALOSIT_DINAM ******")
				.append("\n****** params=").append(params)
				.append("\n*********************************************************")
				.toString()
				);
		ejecutaSP(new ActualizaMpolisitTvalositGrupo(getDataSource()),params);
	}
	
	protected class ActualizaMpolisitTvalositGrupo extends StoredProcedure
	{
		protected ActualizaMpolisitTvalositGrupo(DataSource dataSource)
		{
    		super(dataSource,"PKG_SATELITES.P_ACT_MPOLISIT_TVALOSIT_DINAM");	
    		declareParameter(new SqlParameter("cdunieco"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdramo"      , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("estado"      , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nmpoliza"    , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdgrupo"     , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("nombreGrupo" , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("cdplan"      , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor01"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor02"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor03"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor04"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor05"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor06"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor07"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor08"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor09"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor10"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor11"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor12"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor13"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor14"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor15"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor16"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor17"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor18"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor19"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor20"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor21"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor22"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor23"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor24"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor25"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor26"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor27"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor28"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor29"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor30"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor31"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor32"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor33"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor34"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor35"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor36"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor37"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor38"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor39"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor40"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor41"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor42"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor43"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor44"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor45"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor46"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor47"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor48"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor49"   , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("otvalor50"   , OracleTypes.VARCHAR));
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
					,"otvalor50"
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
		
		for(int i=1;i<=50;i++)
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
			for(int i=1;i<=50;i++)
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
}