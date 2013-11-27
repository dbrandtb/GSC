package mx.com.gseguros.portal.endosos.dao.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class EndososDAOImpl extends AbstractManagerDAO implements EndososDAO
{

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
		};

		protected ObtenerEndosos(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_ENDOSOS_G");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fereferen_i"  , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(columnas)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String, String>> obtenerEndosos(Map<String, String> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerEndosos(this.getDataSource()), params);
		return (List<Map<String, String>>) resultadoMap.get("pv_registro_o");
	}

}