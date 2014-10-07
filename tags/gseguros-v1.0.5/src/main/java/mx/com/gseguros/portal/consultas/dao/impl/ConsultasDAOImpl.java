package mx.com.gseguros.portal.consultas.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.DinamicMapper;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class ConsultasDAOImpl extends AbstractManagerDAO implements ConsultasDAO
{
	@Override
	public List<Map<String,String>> consultaDinamica(String storedProcedure,LinkedHashMap<String,Object>params) throws Exception
	{
		Map<String,Object>result = this.ejecutaSP(new ConsultaDinamica(storedProcedure, params, getDataSource()), params);
		return (List<Map<String,String>>) result.get("pv_registro_o");
	}
	
	protected class ConsultaDinamica extends StoredProcedure
	{

		protected ConsultaDinamica(String storedProcedure, LinkedHashMap<String,Object> params, DataSource dataSource)
		{
			super(dataSource, storedProcedure);

			if(params!=null)
			{
				for(Entry<String,Object>param : params.entrySet())
				{
					declareParameter(new SqlParameter(param.getKey() , OracleTypes.VARCHAR));
				}
			}

			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
	}
}