package mx.com.gseguros.portal.renovacion.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.renovacion.dao.RenovacionDAO;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class RenovacionDAOImpl extends AbstractManagerDAO implements RenovacionDAO
{
	@Override
	public List<Map<String,String>>buscarPolizasRenovables(String anio,String mes)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("anio" , anio);
		params.put("mes"  , mes);
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
			super(dataSource, "PKG");
			declareParameter(new SqlParameter("anio" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("mes"  , OracleTypes.VARCHAR));
			String[] columnas=new String[]{
					"cdunieco"
					,"cdramo"
					,"estado"
					,"nmpoliza"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
}