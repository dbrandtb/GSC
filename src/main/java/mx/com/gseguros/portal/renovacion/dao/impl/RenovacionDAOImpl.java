package mx.com.gseguros.portal.renovacion.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.renovacion.dao.RenovacionDAO;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
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
			,String nmsituac)throws Exception
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
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>renovarPolizas(String cdusuari,String anio,String mes,String cdtipopc)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdusuari" , cdusuari);
		params.put("anio"     , anio);
		params.put("mes"      , mes);
		params.put("cdtipopc" , cdtipopc);
		Map<String,Object>procedureResult        = ejecutaSP(new RenovarPolizas(getDataSource()),params);
		List<Map<String,String>>polizasRenovadas = (List<Map<String,String>>)procedureResult.get("pv_registro_o");
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
					,"nmpolant"
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
	
}