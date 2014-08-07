package mx.com.gseguros.portal.mesacontrol.dao.impl;

import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class MesaControlDAOImpl extends AbstractManagerDAO implements MesaControlDAO
{
	public String cargarCdagentePorCdusuari(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=this.ejecutaSP(new CargarCdagentePorCdusuari(getDataSource()), params);
		return (String)resultado.get("pv_cdagente_o");
	}
	
	protected class CargarCdagentePorCdusuari extends StoredProcedure
	{
		protected CargarCdagentePorCdusuari(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_CDAGENTE_X_CDUSUARI");
			declareParameter(new SqlParameter("cdusuari"    , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_cdagente_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
}