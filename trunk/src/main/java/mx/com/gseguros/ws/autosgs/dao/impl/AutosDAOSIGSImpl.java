package mx.com.gseguros.ws.autosgs.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.siniestros.dao.impl.SiniestrosDAOImpl;
import mx.com.gseguros.ws.autosgs.dao.AutosDAOSIGS;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;

public class AutosDAOSIGSImpl extends AbstractManagerDAO implements AutosDAOSIGS {

	private static Logger logger = Logger.getLogger(AutosDAOSIGSImpl.class);
	
	@Override
	public Integer insertaReciboAuto(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new InsertaReciboAuto(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
	
		return resp;
	}
	
	public class InsertaReciboAuto extends StoredProcedure{
		protected InsertaReciboAuto(DataSource dataSource){
			super(dataSource, "spReciboSigs");
			
			declareParameter(new SqlParameter("Sucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("Ramo", Types.SMALLINT));
			declareParameter(new SqlParameter("Poliza", Types.INTEGER));
			declareParameter(new SqlParameter("TipoEndoso", Types.VARCHAR));
			declareParameter(new SqlParameter("NumeroEndoso", Types.INTEGER));
			declareParameter(new SqlParameter("Recibo", Types.SMALLINT));
			declareParameter(new SqlParameter("TotalRecibos", Types.SMALLINT));
			declareParameter(new SqlParameter("PrimaNeta", Types.DECIMAL));
			declareParameter(new SqlParameter("Iva", Types.DECIMAL));
			declareParameter(new SqlParameter("Recargo", Types.DECIMAL));
			declareParameter(new SqlParameter("Derechos", Types.DECIMAL));
			declareParameter(new SqlParameter("CesionComision", Types.DECIMAL));
			declareParameter(new SqlParameter("ComisionPrima", Types.DECIMAL));
			declareParameter(new SqlParameter("ComisionRecargo", Types.DECIMAL));
			declareParameter(new SqlParameter("FechaInicio", Types.DATE));
			declareParameter(new SqlParameter("FechaTermino", Types.DATE));
			
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
			@Override  
		     public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
				Integer result = null;
		        while(rs.next()){  
		        	result = rs.getInt(1);
		        }  
		        return result;  
		        }
		    }));
			
			compile();
		}
	}
	
	@Override
	public Integer confirmaRecibosAuto(Map<String, Object> params) throws Exception {
		Integer resp = null;
		Map<String, Object> mapResult = ejecutaSP(new ConfirmaRecibosAuto(getDataSource()), params);
		resp = (Integer) mapResult.get("rs");
	
		return resp;
	}
	
	public class ConfirmaRecibosAuto extends StoredProcedure{
		protected ConfirmaRecibosAuto(DataSource dataSource){
			super(dataSource, "spValidaEmisionSigs");
			
			declareParameter(new SqlParameter("Sucursal", Types.SMALLINT));
			declareParameter(new SqlParameter("Ramo", Types.SMALLINT));
			declareParameter(new SqlParameter("Poliza", Types.INTEGER));
			declareParameter(new SqlParameter("TipoEndoso", Types.VARCHAR));
			declareParameter(new SqlParameter("NumeroEndoso", Types.INTEGER));
			declareParameter(new SqlReturnResultSet("rs", new ResultSetExtractor<Integer>(){  
				@Override  
			     public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {  
					Integer result = null;
			        while(rs.next()){  
			        	result = rs.getInt(1);
			        }  
			        return result;  
			        }
			    }));
						
			compile();
		}
	}
}
