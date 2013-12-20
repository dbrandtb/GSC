package mx.com.gseguros.portal.cancelacion.dao.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.cancelacion.dao.CancelacionDAO;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.DinamicMapper;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class CancelacionDAOImpl extends AbstractManagerDAO implements CancelacionDAO
{

	////////////////////////////
	////// buscar polizas //////
	/*////////////////////////*/
	@Override
	@Deprecated
	public List<Map<String, String>> buscarPolizas(Map<String,String> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new BuscarPolizas(this.getDataSource()), params);
		return (List<Map<String, String>>) resultadoMap.get("pv_registro_o");
	}
	
	@Deprecated
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

		protected BuscarPolizas(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_CONSUL_POLIZA");
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
	/*////////////////////////*/
	////// buscar polizas //////
	////////////////////////////

	////////////////////////////////////////////
	////// obtener detalle de cancelacion //////
	/*////////////////////////////////////////*/
	@Override
	public Map<String, String> obtenerDetalleCancelacion(Map<String, String> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerDetalleCancelacion(this.getDataSource()), params);
		List<Map<String, String>>listaTemp=(List<Map<String, String>>) resultadoMap.get("pv_registro_o");
		Map<String,String> respuesta=null;
		if(!listaTemp.isEmpty())
			respuesta=listaTemp.get(0);
		return respuesta;
	}

	protected class ObtenerDetalleCancelacion extends StoredProcedure
	{
		String[] columnas=new String[]{"CDMOTANU", "DSMOTANU", "FEANULAC", "DSCANCEL"};

		protected ObtenerDetalleCancelacion(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_DETALLE_CANC");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"    , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(columnas)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	/*////////////////////////////////////////*/
	////// obtener detalle de cancelacion //////
	////////////////////////////////////////////
	
	////////////////////////////////////////
	////// obtener polizas candidatas //////
	/*////////////////////////////////////*/
	@Override
	public List<Map<String,String>> obtenerPolizasCandidatas(Map<String,String> params) throws Exception
	{
		
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerPolizasCandidatas(this.getDataSource()), params);
		return (List<Map<String, String>>) resultadoMap.get("pv_registro_o");
	}
	
	protected class ObtenerPolizasCandidatas extends StoredProcedure
	{

		protected ObtenerPolizasCandidatas(DataSource dataSource)
		{
			super(dataSource, "pkg_cancela.p_obtiene_poliza_a_cancelar");
			declareParameter(new SqlParameter("pv_asegurado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsuniage_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsramo_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i"    , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new DinamicMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	/*////////////////////////////////////*/
	////// obtener polizas candidatas //////
	////////////////////////////////////////
	
	////////////////////////////////////////
	////// grabar polizas en tagrucan //////
	/*////////////////////////////////////*/
	public void seleccionaPolizas(Map<String,String> params) throws Exception
	{
		ejecutaSP(new SeleccionaPolizas(this.getDataSource()), params);
	}
	
	protected class SeleccionaPolizas extends StoredProcedure
	{
		public SeleccionaPolizas(DataSource dataSource)
		{
			super(dataSource, "pkg_cancela.p_selecciona_polizas");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_agencia_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fechapro_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	/*////////////////////////////////////*/
	////// grabar polizas en tagrucan //////
	////////////////////////////////////////

}