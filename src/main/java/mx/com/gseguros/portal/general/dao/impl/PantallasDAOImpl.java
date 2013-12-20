package mx.com.gseguros.portal.general.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.cotizacion.model.Tatri;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.DinamicMapper;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class PantallasDAOImpl extends AbstractManagerDAO implements PantallasDAO
{	
	///////////////////////////////////////////////
	////// obtener los campos de la pantalla //////
	/*///////////////////////////////////////////*/
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
	
	///////////////////////////////////////////////////
	////// obtener los parametros de la pantalla //////
	/*///////////////////////////////////////////////*/
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
		}
	}
	/*///////////////////////////////////////////////*/
	////// obtener los parametros de la pantalla //////
	///////////////////////////////////////////////////
	
	//////////////////////////////////////////////////
	////// borrar los parametros de la pantalla //////
	/*//////////////////////////////////////////////*/
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
		}
	}
	/*//////////////////////////////////////////////*/
	////// borrar los parametros de la pantalla //////
	//////////////////////////////////////////////////
	
	/////////////////////////////////////////////
	////// insertar parametros de pantalla //////
	/*/////////////////////////////////////////*/
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
		}
	}
	/*/////////////////////////////////////////*/
	////// insertar parametros de pantalla //////
	/////////////////////////////////////////////
	
}