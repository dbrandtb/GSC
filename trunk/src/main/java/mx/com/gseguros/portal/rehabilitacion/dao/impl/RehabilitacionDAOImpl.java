package mx.com.gseguros.portal.rehabilitacion.dao.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.rehabilitacion.dao.RehabilitacionDAO;

public class RehabilitacionDAOImpl extends AbstractManagerDAO implements RehabilitacionDAO
{

	protected class BuscarPolizas extends StoredProcedure
	{
		String[] columnas=new String[]{"NOMBRE", "FEMISION", "FEINICOV", "FEFINIV", "PRITOTAL","DSRAMO"
	            ,"CDRAMO"
	            ,"DSTIPSIT"
	            ,"CDTIPSIT"
	            ,"DSUNIECO"
	            ,"CDUNIECO"
	            ,"NMPOLIZA"
	            ,"NMPOLIEX"
	            ,"NMSOLICI"
	            ,"ESTADO"
	            ,"FERECIBO"};

		protected BuscarPolizas(DataSource dataSource) {
			super(dataSource, "PKG_CONSULTA.P_CONSUL_CANS_POLIZA");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliex_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdagente_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fereferen_i"  , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(columnas)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String, String>> buscarPolizas(Map<String,String> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new BuscarPolizas(this.getDataSource()), params);
		return (List<Map<String, String>>) resultadoMap.get("pv_registro_o");
	}

}
