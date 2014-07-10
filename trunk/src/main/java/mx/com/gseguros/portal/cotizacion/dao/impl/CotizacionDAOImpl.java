package mx.com.gseguros.portal.cotizacion.dao.impl;

import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class CotizacionDAOImpl extends AbstractManagerDAO implements CotizacionDAO
{
	@Override
	public void movimientoTvalogarGrupo(Map<String,String>params)throws Exception
	{
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
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoMpoligarGrupo(Map<String,String>params)throws Exception
	{
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
}