package mx.com.gseguros.portal.general.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.general.dao.IndicadoresDAO;
import oracle.jdbc.driver.OracleTypes;

public class IndicadoresDAOImpl extends AbstractManagerDAO implements IndicadoresDAO {
	
	private static Logger logger = LoggerFactory.getLogger(IndicadoresDAOImpl.class);
    
	@Override
	public Map<String, String> obtieneDashInicial(String cdunieco, String lineanegocio, String cdramo,
			String tipotramite, String cdagente) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_linea_negocio_i", lineanegocio);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_tipotramite_i", tipotramite);
		params.put("pv_cdagente_i", cdagente);
		Map<String, Object> mapResult = ejecutaSP(new ObtieneDashInicialSP(getDataSource()), params);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("ingresados", (String)mapResult.get("pv_tramites_ingr_o"));
		map.put("procesados", (String)mapResult.get("pv_tramites_proc_o"));
		map.put("pendientes", (String)mapResult.get("pv_tramites_pend_o"));
		map.put("eficacia",   (String)mapResult.get("pv_eficacia_o"));
		return map;
	}
	
	protected class ObtieneDashInicialSP extends StoredProcedure {

		protected ObtieneDashInicialSP(DataSource dataSource) {
			super(dataSource, "p_dash_inicial");
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_linea_negocio_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipotramite_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdagente_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tramites_ingr_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tramites_proc_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tramites_pend_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_eficacia_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public Map<String, String> obtieneDashPendientes(String cdunieco, String lineanegocio, String cdramo,
			String tipotramite, String cdagente) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_linea_negocio_i", lineanegocio);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_tipotramite_i", tipotramite);
		params.put("pv_cdagente_i", cdagente);
		Map<String, Object> mapResult = ejecutaSP(new ObtieneDashPendientesSP(getDataSource()), params);
		Map<String, String> map = new HashMap<String, String>();
		map.put("total",       (String)mapResult.get("pv_Tramites_Pend_o"));
		map.put("undia",       (String)mapResult.get("pv_Tramites_1_dia_o"));
		map.put("dosdias",     (String)mapResult.get("pv_Tramites_2_dia_o"));
		map.put("tresdias",    (String)mapResult.get("pv_Tramites_3_dia_o"));
		map.put("cuatrodias",  (String)mapResult.get("pv_Tramites_4_dia_o"));
		map.put("cincodias",   (String)mapResult.get("pv_Tramites_5_dia_o"));
		map.put("mascincodias",(String)mapResult.get("pv_Tramites_mas_dia_o"));
		return map;
	}
	
	protected class ObtieneDashPendientesSP extends StoredProcedure {

		protected ObtieneDashPendientesSP(DataSource dataSource) {
			super(dataSource, "p_dash_cuenta_pend");
			declareParameter(new SqlParameter("pv_cdunieco_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_linea_negocio_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipotramite_i",   OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdagente_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_Tramites_Pend_o",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_Tramites_1_dia_o",   OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_Tramites_2_dia_o",   OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_Tramites_3_dia_o",   OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_Tramites_4_dia_o",   OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_Tramites_5_dia_o",   OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_Tramites_mas_dia_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o",  OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public List<Map<String, String>> obtieneTramitesPorLineaNegocio(String cdetapa, String cdunieco,
			String lineaNegocio, String cdramo, String tipoTramite, String cdagente) throws Exception {
		
		Map<String,String> params  = new LinkedHashMap<String,String>();
		params.put("pv_cdetapa" ,        cdetapa);
		params.put("pv_cdunieco_i" ,     cdunieco);
		params.put("pv_linea_negocio_i", lineaNegocio);
		params.put("pv_cdramo_i" ,       cdramo);
		params.put("pv_tipotramite_i",   tipoTramite);
		params.put("pv_cdagente_i",      cdagente);
		Map<String,Object>       procRes = ejecutaSP(new ObtieneTramitesPorLineaNegocioSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null) {
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class ObtieneTramitesPorLineaNegocioSP extends StoredProcedure {

		protected ObtieneTramitesPorLineaNegocioSP(DataSource dataSource) {
			super(dataSource, "p_dash_tram_x_lob");
			declareParameter(new SqlParameter("pv_cdetapa",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco_i",     OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_linea_negocio_i",OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",   	   OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipotramite_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdagente_i",     OracleTypes.VARCHAR));
			String[] cols=new String[]{
            		"CD_LINEA_NEGOCIO"
            		,"DS_LINEA_NEGOCIO"
            		,"CANTIDAD"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o",  OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public List<Map<String, String>> obtieneTramitesLineaNegocioPorRamo(String cdetapa, String cdunieco,
			String lineaNegocio, String cdramo, String tipoTramite, String cdagente) throws Exception {
		
		Map<String,String> params  = new LinkedHashMap<String,String>();
		params.put("pv_cdetapa" ,        cdetapa);
		params.put("pv_cdunieco_i" ,     cdunieco);
		params.put("pv_linea_negocio_i", lineaNegocio);
		params.put("pv_cdramo_i" ,       cdramo);
		params.put("pv_tipotramite_i",   tipoTramite);
		params.put("pv_cdagente_i",      cdagente);
		Map<String,Object>       procRes = ejecutaSP(new ObtieneTramitesLineaNegocioPorRamoSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null) {
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class ObtieneTramitesLineaNegocioPorRamoSP extends StoredProcedure {

		protected ObtieneTramitesLineaNegocioPorRamoSP(DataSource dataSource) {
			super(dataSource, "p_dash_tram_lob_ramo");
			declareParameter(new SqlParameter("pv_cdetapa",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco_i",     OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_linea_negocio_i",OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",   	   OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipotramite_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdagente_i",     OracleTypes.VARCHAR));
			String[] cols=new String[]{
            		"CDRAMO"
            		,"DSRAMO"
            		,"CANTIDAD"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o",  OracleTypes.VARCHAR));
			compile();
		}
	}
	

	@Override
	public List<Map<String, String>> obtieneDetalleLineaNegocio(String cdetapa, String cdunieco,
			String lineaNegocio, String cdramo, String tipoTramite, String cdagente) throws Exception {
		
		Map<String,String> params  = new LinkedHashMap<String,String>();
		params.put("pv_cdetapa" ,        cdetapa);
		params.put("pv_cdunieco_i" ,     cdunieco);
		params.put("pv_linea_negocio_i", lineaNegocio);
		params.put("pv_cdramo_i" ,       cdramo);
		params.put("pv_tipotramite_i",   tipoTramite);
		params.put("pv_cdagente_i",      cdagente);
		Map<String,Object>       procRes = ejecutaSP(new ObtieneDetalleLineaNegocioSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null) {
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
		
	protected class ObtieneDetalleLineaNegocioSP extends StoredProcedure {

		protected ObtieneDetalleLineaNegocioSP(DataSource dataSource) {
			super(dataSource, "p_dash_lob_detalle");
			declareParameter(new SqlParameter("pv_cdetapa",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco_i",     OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_linea_negocio_i",OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",   	   OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipotramite_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdagente_i",     OracleTypes.VARCHAR));
			String[] cols=new String[]{
            		"CDETAPA"
            		,"ETAPA"
            		,"NTRAMITE"
            		,"FECHA_RECEP_TRAMITE"
            		,"FECHA_DESDE"
            		,"CDUNIECO"
            		,"DSUNIECO"
            		,"CD_TIPO_TRAMITE"
            		,"TIPO_TRAMITE"
            		,"CD_LINEA_NEGOCIO"
            		,"DS_LINEA_NEGOCIO"
            		,"STATUS_TRAMITE"
            		,"DS_STATUS_TRAMITE"
            		,"CDRAMO"
            		,"DSRAMO"
            		,"NOMBRE_TRAMITE"
            		,"CDAGENTE"
            		,"NOMBRE_AGENTE"
            		,"NMPOLIZA"
            		,"NMSOLICI"
            		,"CDUSUARI_CREA"
            		,"DSUSUARI_CREA"
            		,"CDUSUARI_ACT"
            		,"DSUSUARI_ACT"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o",  OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public List<Map<String, String>> obtieneLineaNegocioPorSucursal(String cdetapa, String cdunieco,
			String lineaNegocio, String cdramo, String tipoTramite, String cdagente) throws Exception {
		
		Map<String,String> params  = new LinkedHashMap<String,String>();
		params.put("pv_cdetapa" ,        cdetapa);
		params.put("pv_cdunieco_i" ,     cdunieco);
		params.put("pv_linea_negocio_i", lineaNegocio);
		params.put("pv_cdramo_i" ,       cdramo);
		params.put("pv_tipotramite_i",   tipoTramite);
		params.put("pv_cdagente_i",      cdagente);
		Map<String,Object>       procRes = ejecutaSP(new ObtieneLineaNegocioPorSucursalSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null) {
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class ObtieneLineaNegocioPorSucursalSP extends StoredProcedure {

		protected ObtieneLineaNegocioPorSucursalSP(DataSource dataSource) {
			super(dataSource, "p_dash_suc_x_lob");
			declareParameter(new SqlParameter("pv_cdetapa",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco_i",     OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_linea_negocio_i",OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",   	   OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipotramite_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdagente_i",     OracleTypes.VARCHAR));
			String[] cols=new String[]{
            		"CDUNIECO"
            		,"DSUNIECO"
            		,"SALUD"
            		,"AUTOS"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o",  OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public List<Map<String, String>> obtieneLineaNegocioPorUsuario(String cdetapa, String cdunieco,
			String lineaNegocio, String cdramo, String tipoTramite, String cdagente) throws Exception {
		
		Map<String,String> params  = new LinkedHashMap<String,String>();
		params.put("pv_cdetapa" ,        cdetapa);
		params.put("pv_cdunieco_i" ,     cdunieco);
		params.put("pv_linea_negocio_i", lineaNegocio);
		params.put("pv_cdramo_i" ,       cdramo);
		params.put("pv_tipotramite_i",   tipoTramite);
		params.put("pv_cdagente_i",      cdagente);
		Map<String,Object>       procRes = ejecutaSP(new ObtieneLineaNegocioPorUsuarioSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null) {
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class ObtieneLineaNegocioPorUsuarioSP extends StoredProcedure {

		protected ObtieneLineaNegocioPorUsuarioSP(DataSource dataSource) {
			super(dataSource, "p_dash_lob_usuario");
			declareParameter(new SqlParameter("pv_cdetapa",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco_i",     OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_linea_negocio_i",OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",   	   OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipotramite_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdagente_i",     OracleTypes.VARCHAR));
			String[] cols=new String[]{
            		"CDUSUARI_ACT"
            		,"DSUSUARI_ACT"
            		,"SALUD"
            		,"AUTOS"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o",  OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public List<Map<String, String>> obtieneTramitesPorTipo(String cdetapa, String cdunieco,
			String lineaNegocio, String cdramo, String tipoTramite, String cdagente) throws Exception {
		
		Map<String,String> params  = new LinkedHashMap<String,String>();
		params.put("pv_cdetapa" ,        cdetapa);
		params.put("pv_cdunieco_i" ,     cdunieco);
		params.put("pv_linea_negocio_i", lineaNegocio);
		params.put("pv_cdramo_i" ,       cdramo);
		params.put("pv_tipotramite_i",   tipoTramite);
		params.put("pv_cdagente_i",      cdagente);
		Map<String,Object>       procRes = ejecutaSP(new ObtieneTramitesPorTipoSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null) {
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class ObtieneTramitesPorTipoSP extends StoredProcedure {

		protected ObtieneTramitesPorTipoSP(DataSource dataSource) {
			super(dataSource, "p_dash_lob_tipo_tram");
			declareParameter(new SqlParameter("pv_cdetapa",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco_i",     OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_linea_negocio_i",OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",   	   OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipotramite_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdagente_i",     OracleTypes.VARCHAR));
			String[] cols=new String[]{
            		"CD_TIPO_TRAMITE"
            		,"TIPO_TRAMITE"
            		,"CANTIDAD"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o",  OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public List<Map<String, String>> obtieneTramitesPendientesPorDia(String cdunieco,
			String lineaNegocio, String cdramo, String tipoTramite, String cdagente, String numDias) throws Exception {
		
		Map<String,String> params  = new LinkedHashMap<String,String>();
		params.put("pv_cdunieco_i" ,     cdunieco);
		params.put("pv_linea_negocio_i", lineaNegocio);
		params.put("pv_cdramo_i" ,       cdramo);
		params.put("pv_tipotramite_i",   tipoTramite);
		params.put("pv_cdagente_i",      cdagente);
		params.put("pv_nrodias_i" ,        numDias);
		Map<String,Object>       procRes = ejecutaSP(new ObtieneTramitesPendientesPorDiasSP(getDataSource()),params);
		List<Map<String,String>> lista   = (List<Map<String,String>>)procRes.get("pv_registro_o");
		if(lista==null) {
			lista = new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class ObtieneTramitesPendientesPorDiasSP extends StoredProcedure {

		protected ObtieneTramitesPendientesPorDiasSP(DataSource dataSource) {
			super(dataSource, "p_dash_tram_pen_dias");
			declareParameter(new SqlParameter("pv_cdunieco_i",     OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_linea_negocio_i",OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",   	   OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipotramite_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdagente_i",     OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nrodias_i",      OracleTypes.VARCHAR));
			String[] cols=new String[]{
            		"CDETAPA"
            		,"ETAPA"
            		,"NTRAMITE"
            		,"FECHA_RECEP_TRAMITE"
            		,"FECHA_DESDE"
            		,"CDUNIECO"
            		,"DSUNIECO"
            		,"CD_TIPO_TRAMITE"
            		,"TIPO_TRAMITE"
            		,"CD_LINEA_NEGOCIO"
            		,"DS_LINEA_NEGOCIO"
            		,"STATUS_TRAMITE"
            		,"DS_STATUS_TRAMITE"
            		,"CDRAMO"
            		,"DSRAMO"
            		,"CDPERSON"
            		,"CLIENTE"
            		,"CDAGENTE"
            		,"NOMBRE_AGENTE"
            		,"NMPOLIZA"
            		,"NMSOLICI"
            		,"CDUSUARI_CREA"
            		,"CDUSUARI_ACT"
            		,"DSUSUARI_ACT"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o",  OracleTypes.VARCHAR));
			compile();
		}
	}

}