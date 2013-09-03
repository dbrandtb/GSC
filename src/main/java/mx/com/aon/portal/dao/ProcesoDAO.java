package mx.com.aon.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import mx.com.aon.flujos.cotizacion.model.AyudaCoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.CoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.DatosEntradaCotizaVO;
import mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.SituacionVO;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

/**
 * Clase que contiene los accesos a datos que se encuentran en Base De Datos de
 * los procesos del sistema (Cotizacion, Emision, Endoso, etc.)
 * 
 * @author Adolfo
 * @see AbstractDAO
 * 
 */
public class ProcesoDAO extends AbstractDAO {

	private static Logger logger = Logger.getLogger(ProcesoDAO.class);

	public static final String PERMISO_EJECUCION_PROCESO = "PERMISO_EJECUCION_PROCESO";
	public static final String GENERAR_ORDEN_TRABAJO = "GENERAR_ORDEN_TRABAJO";
	public static final String PROCESO_EMISION_DAO = "PROCESO_EMISION_DAO";
	public static final String BUSCAR_MATRIZ_ASIGNACION = "BUSCAR_MATRIZ_ASIGNACION";
	public static final String EJECUTA_SIGSVALIPOL = "EJECUTA_SIGSVALIPOL"; 
	
	
	
	/* PARA EL REMPLAZO DE VELOCITY POR JDBCTEMPLATE */
	
	public static final String CALCULA_NUMERO_POLIZA = "CALCULA_NUMERO_POLIZA";
	public static final String GENERA_SUPLEMENTO_FISICO = "GENERA_SUPLEMENTO_FISICO";
	public static final String GENERA_SUPLEMENTO_LOGICO = "GENERA_SUPLEMENTO_LOGICO";
	public static final String GENERA_SUPLEMENTOS = "GENERA_SUPLEMENTOS";
	public static final String MOV_T_DESC_SUP = "MOV_T_DESC_SUP";
	public static final String MOV_M_SUPLEME = "MOV_M_SUPLEME";
	public static final String MOV_MPOLAGEN = "MOV_MPOLAGEN";
	public static final String MOV_MPOLIAGR = "MOV_MPOLIAGR";
	public static final String MOV_MPOLIAGE = "MOV_MPOLIAGE";
	public static final String CLONAR_SITUACION = "CLONAR_SITUACION";
	public static final String EJECUTA_P_EXEC_SIGSVDEF = "EJECUTA_P_EXEC_SIGSVDEF";
	public static final String PROC_INCISO_DEF = "PROC_INCISO_DEF";
	public static final String PROCESO_EMISION = "PROCESO_EMISION";
	public static final String OBTIENE_DESCRIPCION = "OBTIENE_DESCRIPCION";
	public static final String CALCULA_NUMERO_OBJETO = "CALCULA_NUMERO_OBJETO";
	public static final String OBTIENE_TVALOSIT_COTIZA = "OBTIENE_TVALOSIT_COTIZA";
	public static final String EXEC_VALIDADOR = "EXEC_VALIDADOR";
	public static final String P_MOV_MPOLIZAS = "P_MOV_MPOLIZAS";
	public static final String P_MOV_TVALOSIT = "P_MOV_TVALOSIT";
        public static final String P_MOV_MPOLISIT = "P_MOV_MPOLISIT";
        public static final String P_CLONAR_PERSONAS="P_CLONAR_PERSONAS";
        public static final String OBTENER_RESULTADOS_COTIZACION="OBTENER_RESULTADOS_COTIZACION";
        public static final String OBTENER_COBERTURAS="OBTENER_COBERTURAS";
        public static final String OBTENER_AYUDA_COBERTURA="OBTENER_AYUDA_COBERTURA";

	protected void initDao() throws Exception {
		addStoredProcedure(PERMISO_EJECUCION_PROCESO,
				new PermisoEjecucionProceso(getDataSource()));
		addStoredProcedure(GENERAR_ORDEN_TRABAJO, new GenerarOrdenTrabajo(
				getDataSource()));
		addStoredProcedure(PROCESO_EMISION_DAO, new ProcesoEmision(getDataSource()));
		addStoredProcedure(BUSCAR_MATRIZ_ASIGNACION, new BuscarMatrizAsignacion(getDataSource()));
		addStoredProcedure(EJECUTA_SIGSVALIPOL, new EjecutarSIGSVALIPOL(getDataSource()));
	
		/* PARA EL REMPLAZO DE VELOCITY POR JDBCTEMPLATE */
		
		addStoredProcedure(CALCULA_NUMERO_POLIZA, new CalculaNumeroPoliza(getDataSource()));
		addStoredProcedure(GENERA_SUPLEMENTO_FISICO, new GeneraSuplementoFisico(getDataSource()));
		addStoredProcedure(GENERA_SUPLEMENTO_LOGICO, new GeneraSuplementoLogico(getDataSource()));
		addStoredProcedure(MOV_T_DESC_SUP, new MovTDescSup(getDataSource()));
		addStoredProcedure(MOV_M_SUPLEME, new MovMsupleme(getDataSource()));
		addStoredProcedure(MOV_MPOLIAGR, new MovMpoliagr(getDataSource()));
		addStoredProcedure(MOV_MPOLIAGE, new MovMpoliage(getDataSource()));
		
		addStoredProcedure(EXEC_VALIDADOR, new EjecutaValidador(getDataSource()));
		addStoredProcedure(OBTIENE_TVALOSIT_COTIZA, new ObtenerTvalositCotiza(getDataSource()));
		addStoredProcedure(CLONAR_SITUACION, new ClonaSituacion(getDataSource()));
		addStoredProcedure(EJECUTA_P_EXEC_SIGSVDEF, new EjecutaSIGSVDEF(getDataSource()));
		
		addStoredProcedure(P_MOV_MPOLIZAS, new InsertaMaestroPolizas(getDataSource()));
		addStoredProcedure(P_MOV_TVALOSIT, new InsertaValoresSituaciones(getDataSource()));
                addStoredProcedure(P_MOV_MPOLISIT, new InsertaMPolisit(getDataSource()));
                addStoredProcedure(P_CLONAR_PERSONAS, new ClonaPersonas(getDataSource()));
		addStoredProcedure(OBTENER_RESULTADOS_COTIZACION, new ObtieneResultadosCotiza(getDataSource()));
                addStoredProcedure(OBTENER_COBERTURAS, new ObtieneCoberturas(getDataSource()));
                addStoredProcedure(OBTENER_AYUDA_COBERTURA, new ObtieneAyudaCoberturas(getDataSource()));
	}

	protected class BuscarMatrizAsignacion extends CustomStoredProcedure {
		
		protected BuscarMatrizAsignacion(DataSource dataSource) {
			super(dataSource, "PKG_CATBO.P_OBTIENE_CDMATRIZ");
			
			
            declareParameter(new SqlParameter("pv_cdunieco_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdelemento_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdproceso_i",
					OracleTypes.NUMERIC));

			declareParameter(new SqlOutParameter("pv_registro_o",
					OracleTypes.CURSOR, new MatrizAsignacion()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",
					OracleTypes.VARCHAR));
            
            
			compile();
		}
		
		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_registro_o");
            wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class PermisoEjecucionProceso extends CustomStoredProcedure {

		protected PermisoEjecucionProceso(DataSource dataSource) {
			super(dataSource, "PKG_CATBO.P_Valida_TFPRONEG");

			declareParameter(new SqlParameter("pv_CDUNIECO_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_CDRAMO_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_CDELEMENTO_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_CDPROCESO_i",
					OracleTypes.NUMERIC));

			declareParameter(new SqlOutParameter("pv_registro_o",
					OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",
					OracleTypes.VARCHAR));

			compile();
		}

		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			String _pvRegistro = ((map.get("pv_registro_o")) == null) ? ""
					: (map.get("pv_registro_o")).toString();
			wrapperResultados.setResultado(_pvRegistro);

			return wrapperResultados;
		}
	}

	protected class GenerarOrdenTrabajo extends CustomStoredProcedure {

		protected GenerarOrdenTrabajo(DataSource dataSource) {
			super(dataSource, "PKG_CATBO.P_GUARDA_CASOS_AUTOMATICOS");

			declareParameter(new SqlParameter("pv_CDELEMENTO_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_CDUNIECO_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_CDUNIAGE_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_CDRAMO_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_ESTADO_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_NMPOLIZA_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_NMSUPLEM_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_CDTIPSIT_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_NMSITUAC_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_CDPROCESO_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_CDUSUARI_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_CDPERSON_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_NMCASO_i",
					OracleTypes.VARCHAR));

			declareParameter(new SqlOutParameter("pv_msg_id_o",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",
					OracleTypes.VARCHAR));

			compile();
		}

		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
	}

	protected class ProcesoEmision extends CustomStoredProcedure {
		
		protected ProcesoEmision(DataSource dataSource) {
			super(dataSource, "PKG_EMISION.P_PROCESO_EMISION");
			
            declareParameter(new SqlParameter("pv_cdusuari",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmsituac",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmsuplem",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdelement",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdcia",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdplan",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperpag",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdperson",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_fecha",
					OracleTypes.VARCHAR));

			declareParameter(new SqlOutParameter("pv_nmpoliza_o",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_nmpoliex_o",
					OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_message",
					OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",
					OracleTypes.VARCHAR));
			
			compile();
		}

		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			
			String numeroPolizaInterno = map.get("pv_nmpoliza_o").toString();
			String numeroPolizaExterno = map.get("pv_nmpoliex_o").toString();
			
			wrapperResultados.setItemMap(new HashMap<String, Object>());
			wrapperResultados.getItemMap().put("NUMERO_EXTERNO", numeroPolizaExterno);
			wrapperResultados.getItemMap().put("NUMERO_INTERNO", numeroPolizaInterno);
			
			String _pvRegistro = ((map.get("pv_message")) == null) ? ""
					: (map.get("pv_message")).toString();
			wrapperResultados.setResultado(_pvRegistro);

			return wrapperResultados;
		}
		
	}
	
	
	protected class EjecutarSIGSVALIPOL extends CustomStoredProcedure {

		protected EjecutarSIGSVALIPOL(DataSource dataSource) {
			super(dataSource, "PKG_COTIZA.P_EJECUTA_SIGSVALIPOL");

			declareParameter(new SqlParameter("pv_cdusuari_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdelemen_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));

	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			
			compile();

		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
	}
	protected class MatrizAsignacion implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	return rs.getString("cdmatriz");
        }
	}
	
	
	
	/*
	 * PARA LOS PROCESOS COMUNES DEL KERNEL MANAGER (migracion de velocity backbone a JdbcTemplate
	 * 
	 */
	
	
	
	protected class CalculaNumeroPoliza extends CustomStoredProcedure {

		protected CalculaNumeroPoliza(DataSource dataSource) {
			super(dataSource, "PKG_SATELITES.P_CALC_NUMPOLIZA");

			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_nmpoliza_o", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			
			compile();

		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			
			String numeroPoliza = null;
			if(map.get("pv_nmpoliza_o") != null) numeroPoliza = map.get("pv_nmpoliza_o").toString();
			wrapperResultados.setItemMap(new HashMap<String, Object>());
			wrapperResultados.getItemMap().put("NUMERO_POLIZA", numeroPoliza);

			return wrapperResultados;
		}
	}
	protected class GeneraSuplementoFisico extends CustomStoredProcedure {
		
		protected GeneraSuplementoFisico(DataSource dataSource) {
			super(dataSource, "PKG_SATELITES.P_GENERA_SUPL_FISICO");
			
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_fecha_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_hhinival_i", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_nmsuplem_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			
			
			compile();
			
		}
		
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			
			String numeroSuplemento = null;

			if(map.get("pv_nmsuplem_o") != null) numeroSuplemento = map.get("pv_nmsuplem_o").toString();
			
			wrapperResultados.setItemMap(new HashMap<String, Object>());
			wrapperResultados.getItemMap().put("SUPLEMENTO_FISICO", numeroSuplemento);
			
			return wrapperResultados;
		}
	}
	protected class GeneraSuplementoLogico extends CustomStoredProcedure {
		
		protected GeneraSuplementoLogico(DataSource dataSource) {
			super(dataSource, "PKG_SATELITES.P_GENERA_SUPL_LOGICO");
			
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
			
			declareParameter(new SqlOutParameter("pv_nsuplogi_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			
			compile();
			
		}
		
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			
			String numeroSuplemento = null;

			if(map.get("pv_nsuplogi_o") != null) numeroSuplemento = map.get("pv_nsuplogi_o").toString();
			
			wrapperResultados.setItemMap(new HashMap<String, Object>());
			wrapperResultados.getItemMap().put("SUPLEMENTO_LOGICO", numeroSuplemento);
			
			return wrapperResultados;
		}
	}
	
	protected class MovTDescSup extends CustomStoredProcedure {
		
		protected MovTDescSup(DataSource dataSource) {
			super(dataSource, "PKG_SATELITES.P_MOV_TDESCSUP");
			
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nsuplogi_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdtipsup_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_feemisio_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsolici_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_fesolici_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ferefere_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdseqpol_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cduser_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nusuasus_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nlogisus_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_accion_i", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			
			compile();
			
		}
		
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
	}
	
	protected class MovMsupleme extends CustomStoredProcedure {
		
		protected MovMsupleme(DataSource dataSource) {
			super(dataSource, "PKG_SATELITES.P_MOV_MSUPLEME");
			
			
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feINival_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_hhinival_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fefINval_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_hhfinval_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swanula_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nsuplogi_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nsupusua_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nsupsess_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_fesessio_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swconfir_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmrenova_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nsuplori_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdorddoc_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_swpolfro_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_pocofron_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_swpoldec_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tippodec_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_accion_i", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			
			compile();
			
		}
		
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
	}
	
	protected class MovMpoliagr extends CustomStoredProcedure {
		
		protected MovMpoliagr(DataSource dataSource) {
			super(dataSource, "PKG_SATELITES.P_MOV_MPOLIAGR");
			
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdagrupa_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmorddom_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdforpag_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdbanco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsucurs_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdcuenta_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdrazon_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_swregula_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperreg_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_feultreg_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgestor_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdrol_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdbanco2_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsucurs2_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdcuenta2_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipcta_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipcta2_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdpagcom_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmpresta_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpresta2_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdbanco3_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsucurs3_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdcuenta3_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipcta3_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpresta3_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmcuenta_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_accion_i", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			
			compile();
			
		}
		
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
	}
	
	protected class MovMpoliage extends CustomStoredProcedure {
		
		protected MovMpoliage(DataSource dataSource) {
			super(dataSource, "PKG_SATELITES.P_MOV_MPOLIAGE");
			
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdagente_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipoag_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_porredau_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmcuadro_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsucurs_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_accion_i", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			
			compile();
			
		}
		
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
	}
	
	protected class EjecutaValidador extends CustomStoredProcedure {
    	
    	protected EjecutaValidador(DataSource dataSource) {
    		super(dataSource,"P_EXEC_VALIDADOR");
    		
    		declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC)); 
    		declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_cdbloque_i", OracleTypes.VARCHAR));
    		
    		declareParameter(new SqlOutParameter("pv_msgerror_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		WrapperResultados wrapperResultados = mapper.build(map);
    		
    		if(map.get("pv_msg_id_o") != null){
    			wrapperResultados.setMsgId(map.get("pv_msg_id_o").toString());
    		}else{
    			wrapperResultados.setMsgId("");
    		}
    		
    		if(map.get("pv_msgerror_o") != null){
    			wrapperResultados.setMsgText(map.get("pv_msgerror_o").toString());
    		}else{
    			wrapperResultados.setMsgText("");
    		}
			
    		return wrapperResultados;
    	}
    }
	
	protected class ObtenerTvalositCotiza extends CustomStoredProcedure {

        protected ObtenerTvalositCotiza(DataSource dataSource) {
            super(dataSource,"PKG_COTIZA.P_OBTIENE_TVALOSIT_COTIZA");
            
            declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new TvalositCotizaMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
          }

        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
         
            List result = (List) map.get("pv_registro_o");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
        }
      }
    
    
    protected class TvalositCotizaMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DatosEntradaCotizaVO tvalositVO = new DatosEntradaCotizaVO();
        	
        	tvalositVO.setDsAtribu(rs.getString("DSATRIBU"));
        	tvalositVO.setOtValor(rs.getString("OTVALOR")); 
        	tvalositVO.setDsNombre(rs.getString("DSNOMBRE"));
        	tvalositVO.setDsValor(rs.getString("DSVALOR")); 
         
            return tvalositVO;
        }
    }
    
    protected class ClonaSituacion extends CustomStoredProcedure {
    	
    	protected ClonaSituacion(DataSource dataSource) {
    		super(dataSource,"PKG_COTIZA.P_CLONAR_SITUACION");
    		
    		declareParameter(new SqlParameter("pv_cdelemen_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_fecha_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdusuario_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cadena_i", OracleTypes.VARCHAR));
    		    		
    		declareParameter(new SqlOutParameter("pv_record1_o", OracleTypes.CURSOR, new ClonaSituacionMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		WrapperResultados wrapperResultados = mapper.build(map);
    		
    		List result = (List) map.get("pv_record1_o");
    		wrapperResultados.setItemList(result);
    		return wrapperResultados;
    	}
    }
    
    
    protected class ClonaSituacionMapper  implements RowMapper {
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		SituacionVO clonVO = new SituacionVO();
    		
    		clonVO.setCdTipsit(rs.getString("CDTIPSIT"));
    		clonVO.setCompAsegur(rs.getString("CDASEGUR"));
    		clonVO.setCdPlan(rs.getString("CDPLAN"));
    		clonVO.setNmSituac(rs.getString("NMSITUAC"));
    		
    		return clonVO;
    	}
    }
    protected class EjecutaSIGSVDEF extends CustomStoredProcedure {
    	
    	protected EjecutaSIGSVDEF(DataSource dataSource) {
    		super(dataSource,"P_EXEC_SIGSVDEF");
    		
    		declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdgarant_i", OracleTypes.VARCHAR));
    		
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		return mapper.build(map);
    		
    	}
    }
    
    protected class InsertaMaestroPolizas extends CustomStoredProcedure {
    	
    	protected InsertaMaestroPolizas(DataSource dataSource) {
    		super(dataSource,"PKG_SATELITES.P_MOV_MPOLIZAS");
    		
    		declareParameter(new SqlParameter("pv_cdunieco", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_cdramo", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_estado", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpoliza", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_nmsuplem", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_status", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_swestado", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmsolici", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_feautori", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdmotanu", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_feanulac", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_swautori", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdmoneda", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_feinisus", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_fefinsus", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_ottempot", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_feefecto", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_hhefecto", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_feproren", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_fevencim", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmrenova", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_ferecibo", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_feultsin", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmnumsin", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_cdtipcoa", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_swtarifi", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_swabrido", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_feemisio", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdperpag", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_nmpoliex", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmcuadro", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_porredau", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_swconsol", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpolant", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpolnva", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_fesolici", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdramant", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdmejred", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpoldoc", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_nmpoliza2", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_nmrenove", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_nmsuplee", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_ttipcamc", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_ttipcamv", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_swpatent", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_accion", OracleTypes.VARCHAR));
    		
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
    }
    
    
    protected class InsertaValoresSituaciones extends CustomStoredProcedure {
    	
    	protected InsertaValoresSituaciones(DataSource dataSource) {
    		super(dataSource,"PKG_SATELITES.P_MOV_TVALOSIT");
    		
    		declareParameter(new SqlParameter("pv_cdunieco", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_cdramo", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_estado", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpoliza", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_nmsituac", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_nmsuplem", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_status", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdtipsit", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor01", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor02", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor03", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor04", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor05", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor06", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor07", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor08", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor09", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor10", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor11", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor12", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor13", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor14", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor15", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor16", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor17", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor18", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor19", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor20", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor21", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor22", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor23", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor24", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor25", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor26", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor27", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor28", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor29", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor30", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor31", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor32", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor33", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor34", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor35", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor36", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor37", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor38", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor39", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor40", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor41", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor42", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor43", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor44", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor45", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor46", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor47", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor48", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor49", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_otvalor50", OracleTypes.VARCHAR));
    		
    		declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
    		
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
    }
    
    
    protected class InsertaMPolisit extends CustomStoredProcedure
    {
    	protected InsertaMPolisit(DataSource dataSource)
        {
            super(dataSource,"PKG_SATELITES.P_MOV_MPOLISIT");
            declareParameter(new SqlParameter("pv_cdunieco_i",      OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo_i",        OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_estado_i",        OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza_i",      OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_nmsituac_i",      OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_nmsuplem_i",      OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_status_i",        OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdtipsit_i",      OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_swreduci_i",      OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdagrupa_i",      OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdestado_i",      OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_fefecsit_i",      OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_fecharef_i",      OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_cdgrupo_i",       OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmsituaext_i",    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmsitaux_i",      OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_nmsbsitext_i",    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdplan_i",        OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdasegur_i",      OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_accion_i",        OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
            compile();
    	}
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception
        {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }
    
    protected class ClonaPersonas extends CustomStoredProcedure
    {
    	protected ClonaPersonas(DataSource dataSource)
        {
            super(dataSource,"PKG_COTIZA.P_CLONAR_PERSONAS");
            declareParameter(new SqlParameter("pv_cdelemen_i",  OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdunieco_i",  OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo_i",    OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_estado_i",    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza_i",  OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_nmsituac_i",  OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdtipsit_i",  OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_fecha_i",     OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_cdusuario_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_p_nombre",    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_s_nombre",    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_apellidop",   OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_apellidom",   OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_sexo",        OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_fenacimi",    OracleTypes.DATE));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",  OracleTypes.VARCHAR));
            compile();
    	}
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception
        {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }
    
    ////////////////////////////////////////////
    ////// Override de obtener cotizacion //////
    /*////////////////////////////////////////*/
    protected class ObtieneResultadosCotiza extends CustomStoredProcedure
    {
    	protected ObtieneResultadosCotiza(DataSource dataSource)
        {
            super(dataSource,"PKG_COTIZA.P_GEN_TARIFICACION");
            declareParameter(new SqlParameter("pv_cdusuari_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdelemen_i", OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_record_o", OracleTypes.CURSOR, new ResultadosCotizaMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("pv_record_o");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
    	}
    }
    
    protected class ResultadosCotizaMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            DecimalFormat formatter = new DecimalFormat("#,##0.00");
            ResultadoCotizacionVO result = new ResultadoCotizacionVO();
            result.setCdIdentifica(rs.getString("CDIDENTIFICA"));
            result.setCdUnieco(rs.getString("CDUNIECO"));
            result.setDsUnieco(	rs.getString("DSUNIECO"));
            result.setCdRamo(rs.getString("CDRAMO"));
            result.setEstado(rs.getString("ESTADO"));
            result.setNmPoliza(rs.getString("NMPOLIZA"));
            result.setNmSuplem(rs.getString("NMSUPLEM"));
            result.setStatus(rs.getString("STATUS"));
            result.setCdPlan(rs.getString("CDPLAN"));
            result.setDsPlan(rs.getString("DSPLAN"));
            result.setCdCiaaseg(rs.getString("CDCIAASEG"));
            result.setCdPerpag(rs.getString("CDPERPAG"));
            result.setDsPerpag(rs.getString("DSPERPAG"));
            result.setCdTipsit(rs.getString("CDTIPSIT"));
            //result.setDsTipsit(rs.getString("DSTIPSIT"));
            result.setFeEmisio(rs.getString("FEEMISIO"));
            result.setFeVencim(rs.getString("FEVENCIM"));
            result.setNumeroSituacion(rs.getString("NMSITUAC"));
            try{
                //if(logger.isDebugEnabled())logger.debug("Antes de parseo mnprima: " + rs.getDouble("MNPRIMA"));
                result.setMnPrima(formatter.format(rs.getDouble("MNPRIMA")));
                //if(logger.isDebugEnabled())logger.debug("Despues de parseo mnprima: " + result.getMnPrima());
            }catch(NumberFormatException nfe){
                logger.error("Error al parsear el valor de la mnprima "+ rs.getString("MNPRIMA"), nfe);
                result.setMnPrima(rs.getString("MNPRIMA"));
            }
            return result;
        }
    }
    /*////////////////////////////////////////*/
    ////// Override de obtener cotizacion //////
    ////////////////////////////////////////////
    
    ////////////////////////////////////////////
    ////// Override de obtener coberturas //////
    /*////////////////////////////////////////*/
    protected class ObtieneCoberturas extends CustomStoredProcedure
    {
    	protected ObtieneCoberturas(DataSource dataSource)
        {
            super(dataSource,"PKG_COTIZA.P_COBERTURAS");
            declareParameter(new SqlParameter("pv_usuario_i",   OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdunieco_i",  OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_estado_i",    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza_i",  OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_nmsituac_i",  OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_nmsuplem_i",  OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdplan_i",    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdramo_i",    OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdcia_i",     OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_region_i",    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_pais_i",      OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_idioma_i",    OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_record_o", OracleTypes.CURSOR, new ObtieneCoberturasMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("pv_record_o");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
    	}
    }
    
    protected class ObtieneCoberturasMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            CoberturaCotizacionVO result=new CoberturaCotizacionVO();
            result.setDeducible(        rs.getString("deducible"));
            result.setCdGarant(         rs.getString("CDGARANT"));
            result.setDsGarant(         rs.getString("DSGARANT"));
            result.setSumaAsegurada(    rs.getString("SUMASEG"));
            result.setCdCiaaseg(null);
            result.setCdRamo(null);
            return result;
        }
    }
    /*////////////////////////////////////////*/
    ////// Override de obtener coberturas //////
    ////////////////////////////////////////////
    
    //////////////////////////////////////
    ////// Obtener ayuda coberturas //////
    /*//////////////////////////////////*/
    protected class ObtieneAyudaCoberturas extends CustomStoredProcedure
    {
    	protected ObtieneAyudaCoberturas(DataSource dataSource)
        {
            super(dataSource,"PKG_COTIZA.P_AYUDA_COBERTURAS");
            declareParameter(new SqlParameter("pv_ciaaseg_i",   OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo_i",    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_ciaaseg_i",   OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("po_registro", OracleTypes.CURSOR, new ObtieneAyudaCoberturasMapper()));
            declareParameter(new SqlOutParameter("po_msg_id",   OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("p_out_title", OracleTypes.VARCHAR));
            compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("po_registro");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
    	}
    }
    
    protected class ObtieneAyudaCoberturasMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            AyudaCoberturaCotizacionVO ayuda=new AyudaCoberturaCotizacionVO();
            ayuda.setCdGarant(rs.getString("cdgarant"));
            ayuda.setDsAyuda(rs.getString("dsayuda"));
            ayuda.setDsGarant("dsgarant");
            return ayuda;
        }
    }
    /*//////////////////////////////////*/
    ////// Obtener ayuda coberturas //////
    //////////////////////////////////////
    
}