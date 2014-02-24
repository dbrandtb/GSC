package mx.com.gseguros.portal.rehabilitacion.dao.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.DinamicMapper;
import mx.com.gseguros.portal.rehabilitacion.dao.RehabilitacionDAO;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.Utilerias;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class RehabilitacionDAOImpl extends AbstractManagerDAO implements RehabilitacionDAO
{

	protected class BuscarPolizas extends StoredProcedure
	{
		protected BuscarPolizas(DataSource dataSource) {
			super(dataSource, "pkg_cancela.p_polizas_canc_a_rehabilitar");
			declareParameter(new SqlParameter("pv_asegurado_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i"  , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new DinamicMapper()));
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
	
	@Override
	public void rehabilitarPoliza(Map<String,String>params) throws Exception
	{
		this.ejecutaSP(new RehabilitarPoliza(this.getDataSource()), Utilerias.ponFechas(params));
	}
	
	protected class RehabilitarPoliza extends StoredProcedure
	{
		protected RehabilitarPoliza(DataSource dataSource)
		{
			super(dataSource, "pkg_cancela.p_rehabilita_poliza");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feefecto_i" , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_fevencim_i" , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_fecancel_i" , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_fereinst_i" , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cdrazon_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdmoneda_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmcancel_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_comments_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	/**
	 * pkg_satelites.p_valida_antiguedad
	 */
	@Override
	public boolean validaAntiguedad(Map<String,String>params) throws Exception
	{
		boolean validacion = false; 
		Map<String,Object>mapa=this.ejecutaSP(new ValidaAntiguedad(this.getDataSource()), Utilerias.ponFechas(params));
		validacion = mapa!=null && mapa.containsKey("pv_valor_o") 
				&& mapa.get("pv_valor_o")!=null && ((String)mapa.get("pv_valor_o")).equalsIgnoreCase(Constantes.SI);
		return validacion;
	}
	
	protected class ValidaAntiguedad extends StoredProcedure
	{
		protected ValidaAntiguedad(DataSource dataSource)
		{
			super(dataSource, "pkg_satelites.p_valida_antiguedad");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
            
			declareParameter(new SqlOutParameter("pv_valor_o"  , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	/**
	 * pkg_satelites.p_borra_antiguedad
	 */
	@Override
	public void borraAntiguedad(Map<String,String>params) throws Exception
	{
		this.ejecutaSP(new BorraAntiguedad(this.getDataSource()), Utilerias.ponFechas(params));
	}
	
	protected class BorraAntiguedad extends StoredProcedure
	{
		protected BorraAntiguedad(DataSource dataSource)
		{
			super(dataSource, "pkg_satelites.p_borra_antiguedad");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fereinst_i" , OracleTypes.DATE));
            
            declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}

}
