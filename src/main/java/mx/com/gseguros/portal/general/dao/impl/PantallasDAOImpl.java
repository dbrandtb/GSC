package mx.com.gseguros.portal.general.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.DinamicMapper;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.utils.Constantes;
import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class PantallasDAOImpl extends AbstractManagerDAO implements PantallasDAO
{
	/////////////////////////////////
	////// obtener componentes //////
	/*/////////////////////////////*/
	/**
	 * PKG_CONF_PANTALLAS.P_GET_TCONFCMP
	 */
	@Override
	public List<ComponenteVO> obtenerComponentes(Map<String, String> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerComponentes(this.getDataSource()), params);
		return (List<ComponenteVO>) resultadoMap.get("PV_REGISTRO_O");
	}
	
	protected class ObtenerComponentes extends StoredProcedure
	{
		
		protected ObtenerComponentes(DataSource dataSource)
		{
			super(dataSource,"PKG_CONF_PANTALLAS.P_GET_TCONFCMP");
			
			declareParameter(new SqlParameter("PV_CDUNIECO_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDRAMO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTIPSIT_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_ESTADO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_PANTALLA_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSISROL_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTIPTRA_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_ORDEN_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_SECCION_I"  , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("PV_REGISTRO_O" , OracleTypes.CURSOR, new TatriComponenteMapper()));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class TatriComponenteMapper implements RowMapper
	{
		String llaveLabel       = "LABEL";
		String llaveTipoCampo   = "TIPOCAMPO";
		String llaveCatalogo    = "CATALOGO";
		String llaveDependiente = "SWDEPEND";
		String llaveMinLength   = "MINLENGTH";
		String llaveMaxLength   = "MAXLENGTH";
		String llaveObligatorio = "SWOBLIGA";
		String llaveColumna     = "SWCOLUMN";
		String llaveRenderer    = "RENDERER";
		String llaveName        = "NAME_CDATRIBU";
		String llaveSoloLectura = "SWLECTURA";
		String llaveQueryParam  = "QUERYPARAM";
		String llaveValue       = "VALUE";
		String llaveOculto      = "SWOCULTO";
		String llaveParam1      = "PARAM1";
		String llaveValue1      = "VALUE1";
		String llaveParam2      = "PARAM2";
		String llaveValue2      = "VALUE2";
		String llaveParam3      = "PARAM3";
		String llaveValue3      = "VALUE3";
		String llaveParam4      = "PARAM4";
		String llaveValue4      = "VALUE4";
		String llaveParam5      = "PARAM5";
		String llaveValue5      = "VALUE5";
		
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String  label        = rs.getString(llaveLabel);
			String  tipoCampo    = rs.getString(llaveTipoCampo);
			String  catalogo     = rs.getString(llaveCatalogo);
			
			String  sDependiente  = rs.getString(llaveDependiente);
			boolean isDependiente = false;
			if(StringUtils.isNotBlank(sDependiente)&&sDependiente.equalsIgnoreCase(Constantes.SI))
			{
				isDependiente = true;
			}
			
			String  sMinLength    = rs.getString(llaveMinLength);
			int     minLength     = -1;
			boolean flagMinLength = false;
			if(StringUtils.isNotBlank(sMinLength))
			{
				minLength     = (Integer)Integer.parseInt(sMinLength);
				flagMinLength = true;
			}
			
			String  sMaxLength    = rs.getString(llaveMaxLength);
			int     maxLength     = -1;
			boolean flagMaxLength = false;
			if(StringUtils.isNotBlank(sMaxLength))
			{
				maxLength     = (Integer)Integer.parseInt(sMaxLength);
				flagMaxLength = true;
			}
			
			String  sObligatorio  = rs.getString(llaveObligatorio);
			boolean isObligatorio = false;
			if(StringUtils.isNotBlank(sObligatorio)&&sObligatorio.equalsIgnoreCase(Constantes.SI))
			{
				isObligatorio = true;
			}
			
			String  columna  = rs.getString(llaveColumna);
			
			String sRenderer = rs.getString(llaveRenderer);
			String renderer  = null;
			if(StringUtils.isNotBlank(sRenderer))
			{
				if(sRenderer.equalsIgnoreCase(ComponenteVO.RENDERER_MONEY))
				{
					renderer = ComponenteVO.RENDERER_MONEY_EXT;
				}
				else if(!sRenderer.equalsIgnoreCase(Constantes.NO))
				{
					renderer = sRenderer;
				}
			}
			
			String  nameCdatribu = rs.getString(llaveName);
			boolean flagEsAtribu = false;
			if(StringUtils.isNotBlank(nameCdatribu))
			{
				flagEsAtribu = true;
				try
				{
					int aux = (Integer)Integer.parseInt(nameCdatribu);
				}
				catch(Exception ex)
				{
					flagEsAtribu = false;
				}
			}
			
			String sSoloLectura   = rs.getString(llaveSoloLectura);
			boolean isSoloLectura = false;
			if(StringUtils.isNotBlank(sSoloLectura)&&sSoloLectura.equalsIgnoreCase(Constantes.SI))
			{
				isSoloLectura = true;
			}
			
			String queryParam = rs.getString(llaveQueryParam);
			String value      = rs.getString(llaveValue);
			
			String  sOculto  = rs.getString(llaveOculto);
			boolean isOculto = false;
			if(StringUtils.isNotBlank(sOculto)&&sOculto.equalsIgnoreCase(Constantes.SI))
			{
				isOculto = true;
			}
			
			String  paramName1   = rs.getString(llaveParam1);
			String  paramValue1  = rs.getString(llaveValue1);
			String  paramName2   = rs.getString(llaveParam2);
			String  paramValue2  = rs.getString(llaveValue2);
			String  paramName3   = rs.getString(llaveParam3);
			String  paramValue3  = rs.getString(llaveValue3);
			String  paramName4   = rs.getString(llaveParam4);
			String  paramValue4  = rs.getString(llaveValue4);
			String  paramName5   = rs.getString(llaveParam5);
			String  paramValue5  = rs.getString(llaveValue5);
			
			ComponenteVO comp = new ComponenteVO(
					ComponenteVO.TIPO_GENERICO,
					label         , tipoCampo     , catalogo,
					isDependiente , minLength     , flagMinLength,
					maxLength     , flagMaxLength , isObligatorio,
					columna       , renderer      , nameCdatribu,
					flagEsAtribu  , isSoloLectura , queryParam,
					value         , isOculto      , paramName1,
					paramValue1   , paramName2    , paramValue2,
					paramName3    , paramValue3   , paramName4,
					paramValue4   , paramName5    , paramValue5);
			
			return comp;
		}
	}
	/*/////////////////////////////*/
	////// obtener componentes //////
	/////////////////////////////////
	
	///////////////////////////////////////////////
	////// obtener los campos de la pantalla //////
	/*///////////////////////////////////////////*
	@Override
	public List<Tatri> obtenerCamposPantalla(Map<String, Object> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerCamposPantalla(this.getDataSource()), params);
		return (List<Tatri>) resultadoMap.get("PV_REGISTRO_O");
	}
	
	protected class ObtenerCamposPantalla extends StoredProcedure
	{
		
		protected ObtenerCamposPantalla(DataSource dataSource)
		{
			super(dataSource,"ALVARO_PKG.P_GET_ALVARO");
			
			declareParameter(new SqlParameter("PV_CDUNO_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDDOS_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTRES_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDCUATRO_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDCINCO_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSEIS_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSIETE_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDOCHO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDNUEVE_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDDIEZ_I"   , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("PV_REGISTRO_O" , OracleTypes.CURSOR, new TatriPantallaMapper()));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class TatriPantallaMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Tatri result=new Tatri();
			result.setType(Tatri.TATRIGEN);
			result.setCdatribu(rs.getString("CDATRIBU"));
			result.setSwformat(rs.getString("SWFORMAT"));
			result.setNmlmin  (rs.getString("NMLMIN"));
			result.setNmlmax  (rs.getString("NMLMAX"));
			result.setSwobliga(rs.getString("SWOBLIGA"));
			result.setDsatribu(rs.getString("DSATRIBU"));
			result.setOttabval(rs.getString("OTTABVAL"));
			result.setCdtablj1(rs.getString("CDTABLJ1"));
			result.setReadOnly(rs.getString("OTVALOR11")!=null&&rs.getString("OTVALOR11").equalsIgnoreCase("S"));
			
			Map<String,String>mapa=new LinkedHashMap<String,String>(0);
			String cols[]=new String[]{
					 "OTVALOR01","OTVALOR02","OTVALOR03","OTVALOR04","OTVALOR05","OTVALOR06","OTVALOR07","OTVALOR08","OTVALOR09","OTVALOR10"
					,"OTVALOR11","OTVALOR12","OTVALOR13","OTVALOR14","OTVALOR15","OTVALOR16","OTVALOR17","OTVALOR18","OTVALOR19","OTVALOR20"
					,"OTVALOR21","OTVALOR22","OTVALOR23","OTVALOR24","OTVALOR25","OTVALOR26","OTVALOR27","OTVALOR28","OTVALOR29","OTVALOR30"
					,"OTVALOR31","OTVALOR32","OTVALOR33","OTVALOR34","OTVALOR35","OTVALOR36","OTVALOR37","OTVALOR38","OTVALOR39","OTVALOR40"
					,"OTVALOR41","OTVALOR42","OTVALOR43","OTVALOR44","OTVALOR45","OTVALOR46","OTVALOR47","OTVALOR48","OTVALOR49","OTVALOR50"
			};
			for(String col:cols)
			{
				mapa.put(col,rs.getString(col));
			}
			result.setMapa(mapa);
			return result;
		}
	}
	/*///////////////////////////////////////////*/
	////// obtener los campos de la pantalla //////
	///////////////////////////////////////////////
	
	////////////////////////////////////
	////// obtener los parametros //////
	/*////////////////////////////////*/
	/**
	 * PKG_CONF_PANTALLAS.P_GET_TCONFCMP
	 */
	@Override
	public List<Map<String,String>> obtenerParametros(Map<String, String> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerParametros(this.getDataSource()), params);
		return (List<Map<String,String>>) resultadoMap.get("PV_REGISTRO_O");
	}
	
	protected class ObtenerParametros extends StoredProcedure
	{
		
		protected ObtenerParametros(DataSource dataSource)
		{
			super(dataSource,"PKG_CONF_PANTALLAS.P_GET_TCONFCMP");
			
			declareParameter(new SqlParameter("PV_CDUNIECO_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDRAMO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTIPSIT_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_ESTADO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_PANTALLA_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSISROL_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTIPTRA_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_ORDEN_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_SECCION_I"  , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("PV_REGISTRO_O" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	/*////////////////////////////////*/
	////// obtener los parametros //////
	////////////////////////////////////
	
	///////////////////////////////////////////////////
	////// obtener los parametros de la pantalla //////
	/*///////////////////////////////////////////////*
	@Override
	public List<Map<String,String>> obtenerParametrosPantalla(Map<String, Object> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerParametrosPantalla(this.getDataSource()), params);
		return (List<Map<String,String>>) resultadoMap.get("PV_REGISTRO_O");
	}
	
	protected class ObtenerParametrosPantalla extends StoredProcedure
	{
		protected ObtenerParametrosPantalla(DataSource dataSource)
		{
			super(dataSource,"ALVARO_PKG.P_GET_ALVARO");
			
			declareParameter(new SqlParameter("PV_CDUNO_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDDOS_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTRES_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDCUATRO_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDCINCO_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSEIS_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSIETE_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDOCHO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDNUEVE_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDDIEZ_I"   , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("PV_REGISTRO_O" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	/*///////////////////////////////////////////////*/
	////// obtener los parametros de la pantalla //////
	///////////////////////////////////////////////////
	
	//////////////////////////////////////////////////
	////// borrar los parametros de la pantalla //////
	/*//////////////////////////////////////////////*
	@Override
	public void borrarParametrosPantalla(Map<String, Object> params) throws Exception
	{
		this.ejecutaSP(new BorrarParametrosPantalla(this.getDataSource()), params);
	}
	
	protected class BorrarParametrosPantalla extends StoredProcedure
	{
		
		protected BorrarParametrosPantalla(DataSource dataSource)
		{
			super(dataSource,"ALVARO_PKG.P_BORRAR_ALVARO");
			
			declareParameter(new SqlParameter("PV_CDUNO_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDDOS_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTRES_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDCUATRO_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDCINCO_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSEIS_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSIETE_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDOCHO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDNUEVE_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDDIEZ_I"   , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	/*//////////////////////////////////////////////*/
	////// borrar los parametros de la pantalla //////
	//////////////////////////////////////////////////
	
	///////////////////////////////
	////// borrar parametros //////
	/*///////////////////////////*/
	/**
	 * PKG_CONF_PANTALLAS.P_BORRAR_TCONFCMP
	 */
	@Override
	public void borrarParametros(Map<String, String> params) throws Exception
	{
		this.ejecutaSP(new BorrarParametros(this.getDataSource()), params);
	}
	
	protected class BorrarParametros extends StoredProcedure
	{
		
		protected BorrarParametros(DataSource dataSource)
		{
			super(dataSource,"PKG_CONF_PANTALLAS.P_BORRAR_TCONFCMP");
			
			declareParameter(new SqlParameter("PV_CDUNIECO_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDRAMO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTIPSIT_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_ESTADO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_PANTALLA_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSISROL_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTIPTRA_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_ORDEN_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_SECCION_I"  , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	/*///////////////////////////*/
	////// borrar parametros //////
	///////////////////////////////
	
	/////////////////////////////////////////////
	////// insertar parametros de pantalla //////
	/*/////////////////////////////////////////*
	@Override
	public void insertarParametrosPantalla(Map<String, String> params) throws Exception
	{
		this.ejecutaSP(new InsertarParametrosPantalla(this.getDataSource()), params);
	}
	
	protected class InsertarParametrosPantalla extends StoredProcedure
	{
		protected InsertarParametrosPantalla(DataSource dataSource)
		{
			super(dataSource,"ALVARO_PKG.P_INSERTA_ALVARO");
			
			declareParameter(new SqlParameter("CDUNO"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDDOS"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDTRES"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDCUATRO" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDCINCO"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDSEIS"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDSIETE"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDOCHO"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDNUEVE"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDDIEZ"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("OTVALOR01"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR02"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR03"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR04"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR05"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR06"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR07"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR08"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR09"  , OracleTypes.VARCHAR));
    		
    		for(int i=10;i<=50;i++)
    		{
    			declareParameter(new SqlParameter("OTVALOR"+i , OracleTypes.VARCHAR));    			
    		}
			
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	/*/////////////////////////////////////////*/
	////// insertar parametros de pantalla //////
	/////////////////////////////////////////////
	
	/**
	 * PKG_CONF_PANTALLAS.P_INSERTA_TCONFCMP
	 */
	@Override
	public void insertarParametros(Map<String, String> params) throws Exception
	{
		this.ejecutaSP(new InsertarParametros(this.getDataSource()), params);
	}
	
	protected class InsertarParametros extends StoredProcedure
	{
		protected InsertarParametros(DataSource dataSource)
		{
			super(dataSource,"PKG_CONF_PANTALLAS.P_INSERTA_TCONFCMP");
			
			declareParameter(new SqlParameter("PV_PANTALLA_I"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_SECCION_I"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTIPTRA_I"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDUNIECO_I"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDRAMO_I"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTIPSIT_I"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_ESTADO_I"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSISROL_I"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_ORDEN_I"         , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_LABEL_I"         , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_TIPOCAMPO_I"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CATALOGO_I"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_SWDEPEND_I"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_MINLENGTH_I"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_MAXLENGTH_I"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_SWOBLIGA_I"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_SWCOLUMN_I"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_RENDERER_I"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_NAME_CDATRIBU_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_SWLECTURA_I"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_QUERYPARAM_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_VALUE_I"         , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_SWOCULTO_I"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_PARAM1_I"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_VALUE1_I"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_PARAM2_I"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_VALUE2_I"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_PARAM3_I"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_VALUE3_I"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_PARAM4_I"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_VALUE4_I"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_PARAM5_I"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_VALUE5_I"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_SWFINAL_I"       , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("PV_MSG_ID_O" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	///////////////////////////
	////// obtener arbol //////
	/*///////////////////////*
	@Override
	public List<Map<String,String>> obtenerArbol() throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerArbol(this.getDataSource()), new HashMap<String,String>());
		return (List<Map<String,String>>) resultadoMap.get("PV_REGISTRO_O");
	}
	
	protected class ObtenerArbol extends StoredProcedure
	{
		protected ObtenerArbol(DataSource dataSource)
		{
			super(dataSource,"ALVARO_PKG.P_OBT_ARBOL");
			declareParameter(new SqlOutParameter("PV_REGISTRO_O" , OracleTypes.CURSOR, new DinamicMapper()));//CDSIETE,CDDIEZ
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	/*///////////////////////*/
	////// obtener arbol //////
	///////////////////////////
	
	///////////////////////////
	////// obtener arbol //////
	/*///////////////////////*/
	/**
	 * PKG_CONF_PANTALLAS.P_OBT_ARBOL_TCONFCMP
	 */
	@Override
	public List<Map<String,String>> obtenerArbol() throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerArbol(this.getDataSource()), new HashMap<String,String>());
		return (List<Map<String,String>>) resultadoMap.get("PV_REGISTRO_O");
	}
	
	protected class ObtenerArbol extends StoredProcedure
	{
		protected ObtenerArbol(DataSource dataSource)
		{
			super(dataSource,"PKG_CONF_PANTALLAS.P_OBT_ARBOL_TCONFCMP");
			declareParameter(new SqlOutParameter("PV_REGISTRO_O" , OracleTypes.CURSOR, new DinamicMapper()));//PANTALLA,SECCION
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	/*///////////////////////*/
	////// obtener arbol //////
	///////////////////////////
}