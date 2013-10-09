package mx.com.aon.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.flujos.cotizacion.model.AyudaCoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.CoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.DatosEntradaCotizaVO;
import mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSalud;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.Recibo;
import mx.com.aon.flujos.cotizacion.model.SituacionVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Tatri;
import mx.com.gseguros.utils.Utilerias;
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
	public static final String EJECUTA_SIGSVALIPOL_EMI = "EJECUTA_SIGSVALIPOL_EMI";
	
	
	
	/* PARA EL REMPLAZO DE VELOCITY POR JDBCTEMPLATE */
	
	public static final String CALCULA_NUMERO_POLIZA = "CALCULA_NUMERO_POLIZA";
	public static final String GENERA_SUPLEMENTO_FISICO = "GENERA_SUPLEMENTO_FISICO";
	public static final String GENERA_SUPLEMENTO_LOGICO = "GENERA_SUPLEMENTO_LOGICO";
	public static final String GENERA_SUPLEMENTOS = "GENERA_SUPLEMENTOS";
	public static final String MOV_T_DESC_SUP = "MOV_T_DESC_SUP";
	public static final String INSERTA_MAESTRO_HISTORICO_POLIZAS = "INSERTA_MAESTRO_HISTORICO_POLIZAS";
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
    public static final String OBTENER_TATRISIT="OBTENER_TATRISIT";
    public static final String OBTENER_TATRIPOL="OBTENER_TATRIPOL";
    public static final String OBTENER_DATOS_USUARIO="OBTENER_DATOS_USUARIO";
    public static final String INSERTAR_DETALLE_SUPLEMEN="INSERTAR_DETALLE_SUPLEME";
    public static final String COMPRAR_COTIZACION="COMPRAR_COTIZACION";
    public static final String GET_INFO_MPOLIZAS="GET_INFO_MPOLIZAS";
    public static final String OBTENER_TMANTENI="OBTENER_TMANTENI";
    public static final String OBTENER_ASEGURADOS="OBTENER_ASEGURADOS";
    public static final String OBTENER_DOCUMENTOS_POLIZA="OBTENER_DOCUMENTOS_POLIZA";
    public static final String OBTENER_LISTA_DOC_POLIZA_NUEVA="OBTENER_LISTA_DOC_POLIZA_NUEVA";
    public static final String OBTENER_POLIZA_COMPLETA="OBTENER_POLIZA_COMPLETA";
    public static final String P_MOV_TVALOPOL="P_MOV_TVALOPOL";
    public static final String P_MOV_TVALOGAR="P_MOV_TVALOGAR";
    public static final String P_MOV_TVALOPER="P_MOV_TVALOPER";
    public static final String P_GET_TVALOPOL="P_GET_TVALOPOL";
    public static final String P_GET_TVALOGAR="P_GET_TVALOGAR";
    public static final String P_GET_TVALOPER="P_GET_TVALOPER";
    public static final String GENERA_MPERSON="GENERA_MPERSON";
    public static final String P_MOV_MPERSONA="P_MOV_MPERSONA";
    public static final String P_MOV_MPOLIPER="P_MOV_MPOLIPER";
    public static final String P_BORRA_MPOLIPER="P_BORRA_MPOLIPER";
    public static final String OBTENER_COBERTURAS_USUARIO="OBTENER_COBERTURAS_USUARIO";
    public static final String P_MOV_MPOLIGAR="P_MOV_MPOLIGAR";
    public static final String P_MOV_MPOLICAP="P_MOV_MPOLICAP";
    public static final String OBTENER_DETALLES_COTIZACION="OBTENER_DETALLES_COTIZACION";
    public static final String OBTENER_TATRIGAR="OBTENER_TATRIGAR";
    public static final String OBTENER_TATRIPER="OBTENER_TATRIPER";
    public static final String P_GET_DOMICIL="P_GET_DOMICIL";
    public static final String P_MOV_MDOMICIL="P_MOV_MDOMICIL";
    public static final String EMITIR = "EMITIR";
    public static final String GUARDAR_ARCHIVO_POLIZA = "GUARDAR_ARCHIVO_POLIZA";
    public static final String OBTENER_TIPOS_CLAUSULAS_EXCLUSION = "OBTENER_TIPOS_CLAUSULAS_EXCLUSION";
    public static final String LOAD_MESA_CONTROL = "LOAD_MESA_CONTROL";
    public static final String LOAD_DETALLE_MESA_CONTROL = "LOAD_DETALLE_MESA_CONTROL";
    public static final String OBTENER_EXCLUSIONES_X_TIPO = "OBTENER_EXCLUSIONES_X_TIPO";
    public static final String OBTENER_HTML_CLAUSULA="OBTENER_HTML_CLAUSULA";
    public static final String P_MOV_MPOLICOT="P_MOV_MPOLICOT";
    public static final String OBTENER_POLICOT="OBTENER_POLICOT";
    public static final String P_MOV_MESACONTROL="P_MOV_MESACONTROL";
    public static final String P_MOV_DMESACONTROL="P_MOV_DMESACONTROL";
    public static final String OBTIENE_DATOS_RECIBOS="OBTIENE_DATOS_RECIBOS";
    public static final String OBTIENE_CATALOGO_COLONIAS="OBTIENE_CATALOGO_COLONIAS";
    public static final String OBTIENE_DATOS_CLIENTE="OBTIENE_DATOS_CLIENTE";
    public static final String MESACONTROL_UPDATE_SOLICI="MESACONTROL_UPDATE_SOLICI";
    public static final String MESACONTROL_UPDATE_STATUS="MESACONTROL_UPDATE_STATUS";
    public static final String MESACONTROL_FINALIZAR_DETALLE="MESACONTROL_FINALIZAR_DETALLE";

	protected void initDao() throws Exception {
		addStoredProcedure(PERMISO_EJECUCION_PROCESO,new PermisoEjecucionProceso(getDataSource()));
		addStoredProcedure(GENERAR_ORDEN_TRABAJO, new GenerarOrdenTrabajo(getDataSource()));
		addStoredProcedure(PROCESO_EMISION_DAO, new ProcesoEmision(getDataSource()));
		addStoredProcedure(BUSCAR_MATRIZ_ASIGNACION, new BuscarMatrizAsignacion(getDataSource()));
		addStoredProcedure(EJECUTA_SIGSVALIPOL, new EjecutarSIGSVALIPOL(getDataSource()));
		addStoredProcedure(EJECUTA_SIGSVALIPOL_EMI, new EjecutarSIGSVALIPOL_EMI(getDataSource()));
		/* PARA EL REMPLAZO DE VELOCITY POR JDBCTEMPLATE */
		addStoredProcedure(CALCULA_NUMERO_POLIZA, new CalculaNumeroPoliza(getDataSource()));
		addStoredProcedure(GENERA_SUPLEMENTO_FISICO, new GeneraSuplementoFisico(getDataSource()));
		addStoredProcedure(GENERA_SUPLEMENTO_LOGICO, new GeneraSuplementoLogico(getDataSource()));
		addStoredProcedure(MOV_T_DESC_SUP, new MovTDescSup(getDataSource()));
		addStoredProcedure(INSERTA_MAESTRO_HISTORICO_POLIZAS, new MovMsupleme(getDataSource()));
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
        addStoredProcedure(OBTENER_TATRISIT, new ObtieneTatrisit(getDataSource()));
        addStoredProcedure(OBTENER_TATRIPOL, new ObtieneTatripol(getDataSource()));
        addStoredProcedure(OBTENER_DATOS_USUARIO, new ObtieneDatosUsuario(getDataSource()));
        addStoredProcedure(INSERTAR_DETALLE_SUPLEMEN, new InsertarDetalleSuplemen(getDataSource()));
        addStoredProcedure(COMPRAR_COTIZACION, new ComprarCotizacion(getDataSource()));
        addStoredProcedure(GET_INFO_MPOLIZAS, new GetInfoMpolizas(getDataSource()));
        addStoredProcedure(OBTENER_TMANTENI, new ObtenerTmanteni(getDataSource()));
        addStoredProcedure(OBTENER_ASEGURADOS, new ObtenerAsegurados(getDataSource()));
        addStoredProcedure(OBTENER_DOCUMENTOS_POLIZA, new ObtenerDocumentosPoliza(getDataSource()));
        addStoredProcedure(OBTENER_LISTA_DOC_POLIZA_NUEVA, new ObtenerListaDocPolizaNueva(getDataSource()));
        addStoredProcedure(OBTENER_POLIZA_COMPLETA, new ObtenerPolizaCompleta(getDataSource()));
        addStoredProcedure(P_MOV_TVALOPOL, new PMovTvalopol(getDataSource()));
        addStoredProcedure(P_MOV_TVALOGAR, new PMovTvalogar(getDataSource()));
        addStoredProcedure(P_MOV_TVALOPER, new PMovTvaloper(getDataSource()));
        addStoredProcedure(P_GET_TVALOPOL, new PGetTvalopol(getDataSource()));
        addStoredProcedure(P_GET_TVALOGAR, new PGetTvalogar(getDataSource()));
        addStoredProcedure(P_GET_TVALOPER, new PGetTvaloper(getDataSource()));
        addStoredProcedure(GENERA_MPERSON, new GeneraMperson(getDataSource()));
        addStoredProcedure(P_MOV_MPERSONA, new PMovMpersona(getDataSource()));
        addStoredProcedure(P_MOV_MPOLIPER, new PMovMpoliper(getDataSource()));
        addStoredProcedure(OBTENER_COBERTURAS_USUARIO, new ObtenerCoberturasUsuario(getDataSource()));
        addStoredProcedure(P_MOV_MPOLIGAR, new PMovMpoligar(getDataSource()));
        addStoredProcedure(P_MOV_MPOLICAP, new PMovMpolicap(getDataSource()));
        addStoredProcedure(OBTENER_DETALLES_COTIZACION, new ObtenerDetallesCotizacion(getDataSource()));
        addStoredProcedure(OBTENER_TATRIGAR, new ObtieneTatrigar(getDataSource()));
        addStoredProcedure(OBTENER_TATRIPER, new ObtieneTatriper(getDataSource()));
        addStoredProcedure(P_GET_DOMICIL, new ObtenerDomicilio(getDataSource()));
        addStoredProcedure(P_MOV_MDOMICIL, new PMovMdomicil(getDataSource()));
        addStoredProcedure(P_MOV_MPOLICOT, new PMovMpolicot(getDataSource()));
        addStoredProcedure(EMITIR, new Emitir(getDataSource()));
        addStoredProcedure(GUARDAR_ARCHIVO_POLIZA, new GuardarArchivoPoliza(getDataSource()));
        addStoredProcedure(OBTENER_TIPOS_CLAUSULAS_EXCLUSION, new ObtenerTiposClausulasExclusion(getDataSource()));
        addStoredProcedure(LOAD_MESA_CONTROL, new ObtenerMesaControl(getDataSource()));
        addStoredProcedure(LOAD_DETALLE_MESA_CONTROL, new ObtenerDetalleMesaControl(getDataSource()));
        addStoredProcedure(OBTENER_EXCLUSIONES_X_TIPO, new ObtenerExclusionesXTipo(getDataSource()));
        addStoredProcedure(OBTENER_HTML_CLAUSULA, new ObtenerHtmlClausula(getDataSource()));
        addStoredProcedure(OBTENER_POLICOT, new ObtenerPolicot(getDataSource()));
        addStoredProcedure(P_MOV_MESACONTROL, new PMovMesacontrol(getDataSource()));
        addStoredProcedure(P_MOV_DMESACONTROL, new PMovDmesacontrol(getDataSource()));
        addStoredProcedure(P_BORRA_MPOLIPER, new PBorraMpoliper(getDataSource()));

        addStoredProcedure(OBTIENE_DATOS_RECIBOS, new ObtenDatosRecibos(getDataSource()));
        addStoredProcedure(OBTIENE_CATALOGO_COLONIAS, new ObtenCatalogoColonias(getDataSource()));
        addStoredProcedure(OBTIENE_DATOS_CLIENTE, new ObtenDatosCliente(getDataSource()));
        addStoredProcedure(MESACONTROL_UPDATE_SOLICI, new MesaControlUpdateSolici(getDataSource()));
        addStoredProcedure(MESACONTROL_UPDATE_STATUS, new MesaControlUpdateStatus(getDataSource()));
        addStoredProcedure(MESACONTROL_FINALIZAR_DETALLE, new MesaControlFinalizarDetalle(getDataSource()));
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
	
	protected class EjecutarSIGSVALIPOL_EMI extends CustomStoredProcedure {

		protected EjecutarSIGSVALIPOL_EMI(DataSource dataSource) {
			super(dataSource, "PKG_COTIZA.P_EJECUTA_SIGSVALIPOL_EMI");

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
			
			
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feINival_i", OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_hhinival_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fefINval_i", OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_hhfinval_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swanula_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nsuplogi_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nsupusua_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nsupsess_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fesessio_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swconfir_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmrenova_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nsuplori_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdorddoc_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swpolfro_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_pocofron_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swpoldec_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tippodec_i", OracleTypes.VARCHAR));
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
            declareParameter(new SqlParameter("pv_parentesco",  OracleTypes.VARCHAR));
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
            declareParameter(new SqlParameter("pv_cdramo_i",    OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_garant_i",    OracleTypes.VARCHAR));
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
    
    //////////////////////////////////////////////
    ////// Obtiene campos tatrisit de tabla //////
    /*//////////////////////////////////////////*/
    protected class ObtieneTatrisit extends CustomStoredProcedure
    {
    	protected ObtieneTatrisit(DataSource dataSource)
        {
            super(dataSource,"PKG_LISTAS.P_GET_ATRI_SITUACION");
            declareParameter(new SqlParameter("pv_cdtipsit_i",      OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtieneTatrisitMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o",   OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("pv_registro_o");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
    	}
    }
    
    protected class ObtieneTatrisitMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Tatri result=new Tatri();
            result.setType(Tatri.TATRISIT);
            result.setCdatribu(rs.getString("CDATRIBU"));
            result.setSwformat(rs.getString("SWFORMAT"));
            result.setNmlmin(rs.getString("NMLMIN"));
            result.setNmlmax(rs.getString("NMLMAX"));
            result.setSwobliga(rs.getString("SWOBLIGA"));
            result.setDsatribu(rs.getString("DSATRIBU"));
            result.setOttabval(rs.getString("OTTABVAL"));
            result.setCdtablj1(rs.getString("CDTABLJ1"));
            return result;
        }
    }
    /*//////////////////////////////////////////*/
    ////// Obtiene campos tatrisit de tabla //////
    //////////////////////////////////////////////
    
	//////////////////////////////////////////////
	////// Obtiene campos tatripol de tabla //////
	/*//////////////////////////////////////////*/
	protected class ObtieneTatripol extends CustomStoredProcedure
	{
		protected ObtieneTatripol(DataSource dataSource)
		{
			super(dataSource,"PKG_LISTAS.P_GET_ATRI_POLIZA");
			declareParameter(new SqlParameter("pv_cdramo",      	OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtieneTatripolMapper()));
			declareParameter(new SqlOutParameter("pv_messages_o",   OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_registro_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class ObtieneTatripolMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Tatri result=new Tatri();
			result.setType(Tatri.TATRIPOL);
			result.setCdatribu(rs.getString("CDATRIBU"));
			result.setSwformat(rs.getString("SWFORMAT"));
			result.setNmlmin(rs.getString("NMLMIN"));
			result.setNmlmax(rs.getString("NMLMAX"));
			result.setSwobliga(rs.getString("SWOBLIGA"));
			result.setDsatribu(rs.getString("DSATRIBU"));
			result.setOttabval(rs.getString("OTTABVAL"));
			result.setCdtablj1(rs.getString("CDTABLJ1"));
			return result;
		}
	}
	/*//////////////////////////////////////////*/
	////// Obtiene campos tatripol de tabla //////
	//////////////////////////////////////////////
	
	//////////////////////////////////////////////
	////// Obtiene campos tatripol de tabla //////
	/*//////////////////////////////////////////*/
	protected class ObtieneTatrigar extends CustomStoredProcedure
	{
		protected ObtieneTatrigar(DataSource dataSource)
		{
			super(dataSource,"PKG_LISTAS.P_GET_ATRI_GARANTIA");
			declareParameter(new SqlParameter("pv_cdramo_i",      	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtieneTatrigarMapper()));
			declareParameter(new SqlOutParameter("pv_messages_o",   OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_registro_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class ObtieneTatrigarMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Tatri result=new Tatri();
			result.setType(Tatri.TATRIGAR);
			result.setCdatribu(rs.getString("CDATRIBU"));
			result.setSwformat(rs.getString("SWFORMAT"));
			result.setNmlmin(rs.getString("NMLMIN"));
			result.setNmlmax(rs.getString("NMLMAX"));
			result.setSwobliga(rs.getString("SWOBLIGA"));
			result.setDsatribu(rs.getString("DSATRIBU"));
			result.setOttabval(rs.getString("OTTABVAL"));
			result.setCdtablj1(rs.getString("CDTABLJ1"));
			return result;
		}
	}
	/*//////////////////////////////////////////*/
	////// Obtiene campos tatripol de tabla //////
	//////////////////////////////////////////////
	
	//////////////////////////////////////////////
	////// Obtiene campos tatripol de tabla //////
	/*//////////////////////////////////////////*/
	protected class ObtieneTatriper extends CustomStoredProcedure
	{
		protected ObtieneTatriper(DataSource dataSource)
		{
			super(dataSource,"PKG_LISTAS.P_GET_ATRI_ROL");
			declareParameter(new SqlParameter("pv_cdramo_i",      	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdrol_i",         OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtieneTatriperMapper()));
			declareParameter(new SqlOutParameter("pv_messages_o",   OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_registro_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class ObtieneTatriperMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Tatri result=new Tatri();
			result.setType(Tatri.TATRIPER);
			result.setCdatribu(rs.getString("CDATRIBU"));
			result.setSwformat(rs.getString("SWFORMAT"));
			result.setNmlmin(rs.getString("NMLMIN"));
			result.setNmlmax(rs.getString("NMLMAX"));
			result.setSwobliga(rs.getString("SWOBLIGA"));
			result.setDsatribu(rs.getString("DSATRIBU"));
			result.setOttabval(rs.getString("OTTABVAL"));
			result.setCdtablj1(rs.getString("CDTABLJ1"));
			return result;
		}
	}
	/*//////////////////////////////////////////*/
	////// Obtiene campos tatripol de tabla //////
	//////////////////////////////////////////////
    
    //////////////////////////////////////
    ////// Obtener datos usuario /////////
    /*//////////////////////////////////*/
    protected class ObtieneDatosUsuario extends CustomStoredProcedure
    {
    	protected ObtieneDatosUsuario(DataSource dataSource)
        {
            super(dataSource,"pkg_satelites.p_get_info_usuario");
            declareParameter(new SqlParameter(   "pv_cdusuari_i",   OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtieneDatosUsuarioMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o",   OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
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
    
    protected class ObtieneDatosUsuarioMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            DatosUsuario datosUsuario=new DatosUsuario();
            datosUsuario.setCdagente(rs.getString("cdagente"));
            datosUsuario.setCdperson(rs.getString("cdperson"));
            datosUsuario.setCdramo(rs.getString("cdramo"));
            datosUsuario.setCdtipsit(rs.getString("cdtipsit"));
            datosUsuario.setCdunieco(rs.getString("cdunieco"));
            datosUsuario.setCdusuari(rs.getString("cdusuari"));
            datosUsuario.setNmcuadro(rs.getString("nmcuadro"));
            datosUsuario.setNombre(rs.getString("nombre"));
            return datosUsuario;
        }
    }
    /*//////////////////////////////////*/
    ////// Obtener datos usuario /////////
    //////////////////////////////////////
    
    ///////////////////////////////////////
    ////// insertar detalle suplemen //////
    /*///////////////////////////////////*/
    protected class InsertarDetalleSuplemen extends CustomStoredProcedure
    {
    	protected InsertarDetalleSuplemen(DataSource dataSource)
        {
            super(dataSource,"PKG_SATELITES.P_MOV_TDESCSUP");
            declareParameter(new SqlParameter("pv_cdunieco_i",  OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdramo_i",    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_estado_i",    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza_i",  OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nsuplogi_i",  OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdtipsup_i",  OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_feemisio_i",  OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_nmsolici_i",  OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_fesolici_i",  OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_ferefere_i",  OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_cdseqpol_i",  OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cduser_i",    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nusuasus_i",  OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nlogisus_i",  OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdperson_i",  OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_accion_i",    OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o",	OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",	OracleTypes.VARCHAR));
            compile();
    	}
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception
        {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }
    /*///////////////////////////////////*/
    ////// insertar detalle suplemen //////
    ///////////////////////////////////////
    
    ////////////////////////////////
    ////// comprar cotizacion //////
    /*////////////////////////////*/
    protected class ComprarCotizacion extends CustomStoredProcedure
    {
    	protected ComprarCotizacion(DataSource dataSource)
        {
            super(dataSource,"PKG_COTIZA.P_PROC_INCISO_DEF");
            declareParameter(new SqlParameter("pv_cdunieco",	OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdramo",  	OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_estado",  	OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza",  	OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmsituac",  	OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdelement",	OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdperson",	OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdasegur",	OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdplan",	OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdperpag",	OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o",	OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",	OracleTypes.VARCHAR));
            compile();
    	}
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception
        {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }
    /*////////////////////////////*/
    ////// comprar cotizacion //////
    ////////////////////////////////
    
    //////////////////////////////////////////////
    ////// Obtiene informacion de poliza /////////
    /*//////////////////////////////////////////*/
    protected class GetInfoMpolizas extends CustomStoredProcedure
    {
    	protected GetInfoMpolizas(DataSource dataSource)
        {
            super(dataSource,"PKG_SATELITES.P_GET_INFO_MPOLIZAS");
            declareParameter(new SqlParameter("pv_cdunieco",    OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo",      OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_estado",      OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza",    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdusuari",    OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new GetInfoMpolizasMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o",   OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("pv_registro_o");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
    	}
    }
    
    protected class GetInfoMpolizasMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            List<String>columns=new ArrayList<String>(0);
            columns.add("cdusuari");
            columns.add("cdunieco");
            columns.add("dsunieco");
            columns.add("cdperson");
            columns.add("cdagente");
            columns.add("nombre");
            columns.add("cdramo");
            columns.add("dsramo");
            columns.add("nmcuadro");
            columns.add("cdtipsit");
            columns.add("fesolici");
            columns.add("nmsolici");
            columns.add("feefecto");
            columns.add("feproren");
            columns.add("ottempot");
            columns.add("cdperpag");
            Map<String,Object>map=new HashMap<String,Object>(0);
            for(String columnName:columns)
            {
                if(columnName.substring(0,2).equals("fe"))
                {
                    map.put(columnName, rs.getDate(columnName));
                }
                else
                {
                    map.put(columnName, rs.getString(columnName));
                }
            }
            return map;
        }
    }
    /*//////////////////////////////////////////*/
    ////// Obtiene informacion de poliza /////////
    //////////////////////////////////////////////
    
    //////////////////////////////////////////////
    ////// obtiene tablas de catalogos ///////////
    /*//////////////////////////////////////////*/
    protected class ObtenerTmanteni extends CustomStoredProcedure
    {
    	protected ObtenerTmanteni(DataSource dataSource)
        {
            super(dataSource,"PKG_LISTAS.P_GET_TMANTENI");
            declareParameter(new SqlParameter("pv_cdtabla",         OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtenerTmanteniMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o",   OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("pv_registro_o");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
    	}
    }
    
    protected class ObtenerTmanteniMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            GenericVO generic=new GenericVO();
            generic.setKey(rs.getString("codigo"));
            generic.setValue(rs.getString("DESCRIPC"));
            return generic;
        }
    }
    /*//////////////////////////////////////////*/
    ////// obtiene tablas de catalogos ///////////
    //////////////////////////////////////////////
    
    //////////////////////////////////////////////
    ////// obtiene asegurados de poliza //////////
    /*//////////////////////////////////////////*/
    protected class ObtenerAsegurados extends CustomStoredProcedure
    {
    	protected ObtenerAsegurados(DataSource dataSource)
    	{
    		super(dataSource,"PKG_SATELITES.P_OBT_DATOS_MPOLIPER");
    		declareParameter(new SqlParameter("pv_cdunieco",    OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo",      OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_estado",      OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza",    OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtenerAseguradosMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}

    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		WrapperResultados wrapperResultados = mapper.build(map);
    		List result = (List) map.get("pv_registro_o");
    		wrapperResultados.setItemList(result);
    		return wrapperResultados;
    	}
    }

    protected class ObtenerAseguradosMapper implements RowMapper {
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		Map<String,Object> r=new HashMap<String,Object>(0);
    		/*SELECT distinct a.nmsituac,a.cdrol cdrol,b.fenacimi fenacimi,b.otsexo sexo,
           a.cdperson cdperson,b.dsnombre nombre,b.dsnombre1 segundo_nombre,b.dsapellido Apellido_Paterno,
           b.dsapellido1 Apellido_Materno*/
    		/**
    		 * Problema:
    		 * Cuando cargo asegurados su fenacimi puede venir como 01/12/1990 o como 1990-12-01 00:00:00.0
    		 * Solución:
    		 * Hacer este if
    		 */
    		String fenacimi=rs.getString("fenacimi");
    		if(fenacimi!=null)
    		{
    			System.out.println("#debugfenacimi "+fenacimi);
    			if(fenacimi.length()>10)
    			{
    				// 1 9 9 0 - 1 2 - 0 1 _ 0 0 : 0 0 : 0 0 . 0 <<CADENA
    				//0 1 2 3 4 5 6 7 8 9 k <<INDICE
    				String aux="";
    				aux+=fenacimi.substring(8,10);
    				aux+="/";
    				aux+=fenacimi.substring(5,7);
    				aux+="/";
    				aux+=fenacimi.substring(0,4);
    				fenacimi=aux;
    			}
    		}
    		String otfisjur=rs.getString("otfisjur");
    		if(otfisjur==null||otfisjur.length()==0)
    		{
    			otfisjur="F";
    		}
    		String cdnacion=rs.getString("cdnacion");
    		if(cdnacion==null||cdnacion.length()==0)
    		{
    			cdnacion="001";
    		}
    		r.put("nmsituac",			rs.getString("nmsituac"));
    		r.put("cdrol",				rs.getString("cdrol"));
    		r.put("fenacimi",			fenacimi);
    		r.put("sexo",				rs.getString("sexo"));
    		r.put("cdperson",			rs.getString("cdperson"));
    		r.put("nombre",				rs.getString("nombre"));
    		r.put("segundo_nombre",		rs.getString("segundo_nombre"));
    		r.put("Apellido_Paterno",	rs.getString("Apellido_Paterno"));
    		r.put("Apellido_Materno",	rs.getString("Apellido_Materno"));
    		r.put("cdrfc",				rs.getString("cdrfc"));
    		r.put("Parentesco",		    rs.getString("Parentesco"));
    		r.put("tpersona",           otfisjur);
    		r.put("nacional",           cdnacion);
    		return r;
    	}
    }
	/*//////////////////////////////////////////*/
	////// obtiene tablas de catalogos ///////////
	//////////////////////////////////////////////
    
    ///////////////////////////////
    ////// obtiene   mpoliza //////
    /*///////////////////////////*/
    protected class ObtenerPolizaCompleta extends CustomStoredProcedure
    {
    	protected ObtenerPolizaCompleta(DataSource dataSource)
    	{
    		super(dataSource,"PKG_SATELITES.P_GET_INFO_MPOLIZAS");
    		declareParameter(new SqlParameter("pv_cdunieco",    OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdramo",      OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_estado",      OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza",    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdusuari",    OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtenerPolizaCompletaMapper()));
    		declareParameter(new SqlOutParameter("pv_messages_o",   OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}

    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
    		WrapperResultados wrapperResultados = mapper.build(map);
    		List result = (List) map.get("pv_registro_o");
    		wrapperResultados.setItemList(result);
    		return wrapperResultados;
    	}
    }

    protected class ObtenerPolizaCompletaMapper implements RowMapper {
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		Map<String,Object> m=new HashMap<String,Object>(0);
    		String columnas[]=new String[]{"status","swestado","nmsolici","feautori","cdmotanu","feanulac",
    				"swautori","cdmoneda","feinisus","fefinsus",
    	            "ottempot","feefecto","hhefecto","feproren","fevencim","nmrenova","ferecibo","feultsin","nmnumsin","cdtipcoa",
    	            "swtarifi","swabrido","feemisio","cdperpag","nmpoliex","nmcuadro","porredau","swconsol","nmpolant","nmpolnva",
    	            "fesolici","cdramant","cdmejred","nmpoldoc","nmpoliza2","nmrenove","nmsuplee","ttipcamc","ttipcamv","swpatent"};
    		for(String columna:columnas)
    		{
    			if(columna.substring(0,2).equals("fe"))
    			{
    				m.put(columna,rs.getDate(columna));
    			}
    			else
    			{
    				m.put(columna,rs.getString(columna));
    			}
    		}
    		return m;
    	}
    }
    /*///////////////////////////*/
    ////// obtiene   mpoliza //////
    ///////////////////////////////
    
    ///////////////////////////////////
    ///// movimientos de valopol //////
    /*///////////////////////////////*/
    protected class PMovTvalopol extends CustomStoredProcedure {
    	
    	protected PMovTvalopol(DataSource dataSource) {
    		super(dataSource,"PKG_SATELITES.P_MOV_TVALOPOL");
    		
    		declareParameter(new SqlParameter("pv_cdunieco", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_cdramo", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_estado", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmpoliza", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_nmsuplem", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_status", OracleTypes.VARCHAR));
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
    /*///////////////////////////////*/
	///// movimientos de valopol //////
    ///////////////////////////////////
    
	///////////////////////////////////
	///// movimientos de valopol //////
	/*///////////////////////////////*/
	protected class PMovTvalogar extends CustomStoredProcedure {
	
		protected PMovTvalogar(DataSource dataSource) {
			super(dataSource,"PKG_SATELITES.P_MOV_TVALOGAR");
	
			declareParameter(new SqlParameter("pv_cdunieco",  OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo",    OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza",  OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmsituac",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status",    OracleTypes.VARCHAR));
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
	/*///////////////////////////////*/
	///// movimientos de valopol //////
	///////////////////////////////////
	
	///////////////////////////////////
	///// movimientos de valopol //////
	/*///////////////////////////////*/
	protected class PMovTvaloper extends CustomStoredProcedure {
	
		protected PMovTvaloper(DataSource dataSource) {
			super(dataSource,"PKG_SATELITES.P_MOV_TVALOPER");
	
			declareParameter(new SqlParameter("pv_cdunieco",  OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo",    OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza",  OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmsituac",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem",  OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdrol",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdatribu",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit",    OracleTypes.VARCHAR));
			
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
	/*///////////////////////////////*/
	///// movimientos de valopol //////
	///////////////////////////////////
    
	///////////////////////////////
	////// obtiene   valopol //////
	/*///////////////////////////*/
	protected class PGetTvalopol extends CustomStoredProcedure
	{
		protected PGetTvalopol(DataSource dataSource)
		{
			super(dataSource,"PKG_COTIZA.P_GET_TVALOPOL");
			declareParameter(new SqlParameter("pv_cdunieco",    OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo",      OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_record_o",   OracleTypes.CURSOR, new PGetTvalopolMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
		
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_record_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class PGetTvalopolMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String,Object> m=new HashMap<String,Object>(0);
			String columnas[]=new String[]{
					"cdunieco","cdramo","estado","nmpoliza","nmsuplem","status",
					"otvalor01","otvalor02","otvalor03","otvalor04","otvalor05","otvalor06","otvalor07","otvalor08","otvalor09","otvalor10",
					"otvalor11","otvalor12","otvalor13","otvalor14","otvalor15","otvalor16","otvalor17","otvalor18","otvalor19","otvalor20",
					"otvalor21","otvalor22","otvalor23","otvalor24","otvalor25","otvalor26","otvalor27","otvalor28","otvalor29","otvalor30",
					"otvalor31","otvalor32","otvalor33","otvalor34","otvalor35","otvalor36","otvalor37","otvalor38","otvalor39","otvalor40",
					"otvalor41","otvalor42","otvalor43","otvalor44","otvalor45","otvalor46","otvalor47","otvalor48","otvalor49","otvalor50"
			};
			for(String columna:columnas)
			{
				m.put(columna,rs.getString(columna));
			}
			return m;
		}
	}
	/*///////////////////////////*/
	////// obtiene   valopol //////
	///////////////////////////////
	
	///////////////////////////////
	////// obtiene   valogar //////
	/*///////////////////////////*/
	protected class PGetTvalogar extends CustomStoredProcedure
	{
		protected PGetTvalogar(DataSource dataSource)
		{
			super(dataSource,"PKG_COTIZA.P_GET_TVALOGAR");
			declareParameter(new SqlParameter("pv_cdunieco_i",    OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i",      OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_record_o",   OracleTypes.CURSOR, new PGetTvalogarMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_record_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class PGetTvalogarMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String,Object> m=new HashMap<String,Object>(0);
			String columnas[]=new String[]{
					"cdunieco","cdramo","estado","nmpoliza","nmsituac","cdgarant","nmsuplem","status",
					"otvalor01","otvalor02","otvalor03","otvalor04","otvalor05","otvalor06","otvalor07","otvalor08","otvalor09","otvalor10",
					"otvalor11","otvalor12","otvalor13","otvalor14","otvalor15","otvalor16","otvalor17","otvalor18","otvalor19","otvalor20",
					"otvalor21","otvalor22","otvalor23","otvalor24","otvalor25","otvalor26","otvalor27","otvalor28","otvalor29","otvalor30",
					"otvalor31","otvalor32","otvalor33","otvalor34","otvalor35","otvalor36","otvalor37","otvalor38","otvalor39","otvalor40",
					"otvalor41","otvalor42","otvalor43","otvalor44","otvalor45","otvalor46","otvalor47","otvalor48","otvalor49","otvalor50"
			};
			for(String columna:columnas)
			{
				m.put(columna,rs.getString(columna));
			}
			return m;
		}
	}
	/*///////////////////////////*/
	////// obtiene   valogar //////
	///////////////////////////////
	
	///////////////////////////////
	////// obtiene   valoper //////
	/*///////////////////////////*/
	protected class PGetTvaloper extends CustomStoredProcedure
	{
		protected PGetTvaloper(DataSource dataSource)
		{
			/*
			 pv_cdunieco_i
             pv_cdramo_i
             pv_estado_i
             pv_nmpoliza_i
             pv_nmsituac_i
             pv_cdrol_i
             pv_cdperson_i
            */
			super(dataSource,"PKG_COTIZA.P_GET_TVALOPER");
			declareParameter(new SqlParameter("pv_cdunieco_i",    OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i",      OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i",    OracleTypes.VARCHAR));
			//declareParameter(new SqlParameter("pv_status_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdrol_i",       OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_record_o",   OracleTypes.CURSOR, new PGetTvaloperMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_record_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class PGetTvaloperMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String,Object> m=new HashMap<String,Object>(0);
			String columnas[]=new String[]{
					"cdunieco","cdramo","estado","nmpoliza","nmsituac","cdrol","cdperson","nmsuplem",//"status",
					"otvalor01","otvalor02","otvalor03","otvalor04","otvalor05","otvalor06","otvalor07","otvalor08","otvalor09","otvalor10",
					"otvalor11","otvalor12","otvalor13","otvalor14","otvalor15","otvalor16","otvalor17","otvalor18","otvalor19","otvalor20",
					"otvalor21","otvalor22","otvalor23","otvalor24","otvalor25","otvalor26","otvalor27","otvalor28","otvalor29","otvalor30",
					"otvalor31","otvalor32","otvalor33","otvalor34","otvalor35","otvalor36","otvalor37","otvalor38","otvalor39","otvalor40",
					"otvalor41","otvalor42","otvalor43","otvalor44","otvalor45","otvalor46","otvalor47","otvalor48","otvalor49","otvalor50"
			};
			for(String columna:columnas)
			{
				m.put(columna,rs.getString(columna));
			}
			return m;
		}
	}
	/*///////////////////////////*/
	////// obtiene   valoper //////
	///////////////////////////////
	
	////////////////////////////
	////// genera mperson //////
	/*////////////////////////*/
	protected class GeneraMperson extends CustomStoredProcedure {

		protected GeneraMperson(DataSource dataSource) {
			super(dataSource, "PKG_COTIZA.P_GET_CDPERSON");
			declareParameter(new SqlOutParameter("pv_cdperson_o", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			
			String numeroPoliza = null;
			if(map.get("pv_cdperson_o") != null) numeroPoliza = map.get("pv_cdperson_o").toString();
			wrapperResultados.setItemMap(new HashMap<String, Object>());
			wrapperResultados.getItemMap().put("CDPERSON", numeroPoliza);

			return wrapperResultados;
		}
	}
	/*////////////////////////*/
	////// genera mperson //////
	////////////////////////////
	
	////////////////////////////
	////// p mov mpersona //////
	/*////////////////////////*/
	protected class PMovMpersona extends CustomStoredProcedure {
    	
    	protected PMovMpersona(DataSource dataSource) {
    		super(dataSource,"PKG_SATELITES.P_MOV_MPERSONA");
    		declareParameter(new SqlParameter("pv_cdperson_i", 		OracleTypes.VARCHAR));// IN  MPERSONA.cdperson%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_cdtipide_i", 		OracleTypes.VARCHAR));// IN  MPERSONA.cdtipide%TYPE DEFAULT NULL, Valor por default 1
			declareParameter(new SqlParameter("pv_cdideper_i", 		OracleTypes.VARCHAR));// IN  MPERSONA.cdideper%TYPE DEFAULT NULL, Valor de CDRFC
			declareParameter(new SqlParameter("pv_dsnombre_i", 		OracleTypes.VARCHAR));// IN  MPERSONA.dsnombre%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_cdtipper_i", 		OracleTypes.VARCHAR));// IN  MPERSONA.cdtipper%TYPE DEFAULT NULL, Valor por default 1
			declareParameter(new SqlParameter("pv_otfisjur_i", 		OracleTypes.VARCHAR));// IN  MPERSONA.otfisjur%TYPE DEFAULT NULL, Valor por default F
			declareParameter(new SqlParameter("pv_otsexo_i", 		OracleTypes.VARCHAR));// IN  MPERSONA.otsexo%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_fenacimi_i", 		OracleTypes.DATE));// IN  MPERSONA.fenacimi%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_cdrfc_i", 		OracleTypes.VARCHAR));// IN  MPERSONA.cdrfc%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_dsemail_i", 		OracleTypes.VARCHAR));// IN  MPERSONA.dsemail%TYPE DEFAULT NULL,  Valor de email o nulo,
			declareParameter(new SqlParameter("pv_dsnombre1_i", 	OracleTypes.VARCHAR));// IN  MPERSONA.dsnombre1%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_dsapellido_i", 	OracleTypes.VARCHAR));// IN  MPERSONA.dsapellido%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_dsapellido1_i", 	OracleTypes.VARCHAR));// IN  MPERSONA.dsapellido1%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_feingreso_i", 	OracleTypes.DATE));// IN  MPERSONA.feingreso%TYPE DEFAULT NULL,  Valor por default SYSDATE
			declareParameter(new SqlParameter("pv_cdnacion_i", 		OracleTypes.VARCHAR));//
			declareParameter(new SqlParameter("pv_accion_i", 		OracleTypes.VARCHAR));//
    		//qwe
    		declareParameter(new SqlOutParameter("pv_msg_id_o", 	OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", 		OracleTypes.VARCHAR));
    		
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
    }
	/*////////////////////////*/
	////// p mov mpersona //////
	////////////////////////////
	
	////////////////////////////
	////// p mov mpersona //////
	/*////////////////////////*/
	protected class PMovMpoliper extends CustomStoredProcedure {
	
		protected PMovMpoliper(DataSource dataSource) {
			super(dataSource,"PKG_SATELITES.P_MOV_MPOLIPER");
			declareParameter(new SqlParameter("pv_cdunieco_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.cdunieco%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_cdramo_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.cdramo%TYPE   DEFAULT NULL,
			declareParameter(new SqlParameter("pv_estado_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.estado%TYPE   DEFAULT NULL,
			declareParameter(new SqlParameter("pv_nmpoliza_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.nmpoliza%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_nmsituac_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.nmsituac%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_cdrol_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.cdrol%TYPE   DEFAULT NULL,
			declareParameter(new SqlParameter("pv_cdperson_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.cdperson%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_nmsuplem_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.nmsuplem%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_status_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.status%TYPE   DEFAULT NULL,
			declareParameter(new SqlParameter("pv_nmorddom_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.nmorddom%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_swreclam_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.swreclam%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_accion_i", 		OracleTypes.VARCHAR));//
	
			declareParameter(new SqlOutParameter("pv_msg_id_o", 	OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", 		OracleTypes.VARCHAR));
			
			compile();
		}
		
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
	}
	/*////////////////////////*/
	////// p mov mpersona //////
	////////////////////////////
	
	////////////////////////////
	////// borra mpoliper //////
	/*////////////////////////*/
	protected class PBorraMpoliper extends CustomStoredProcedure {
		
		protected PBorraMpoliper(DataSource dataSource) {
			super(dataSource,"PKG_SATELITES.P_BORRA_MPOLIPER");
			declareParameter(new SqlParameter("pv_cdunieco_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.cdunieco%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_cdramo_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.cdramo%TYPE   DEFAULT NULL,
			declareParameter(new SqlParameter("pv_estado_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.estado%TYPE   DEFAULT NULL,
			declareParameter(new SqlParameter("pv_nmpoliza_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.nmpoliza%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_nmsituac_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.nmsituac%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_cdrol_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.cdrol%TYPE   DEFAULT NULL,
			declareParameter(new SqlParameter("pv_cdperson_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.cdperson%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_nmsuplem_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.nmsuplem%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_status_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.status%TYPE   DEFAULT NULL,
			declareParameter(new SqlParameter("pv_nmorddom_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.nmorddom%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_swreclam_i", 		OracleTypes.VARCHAR));// IN  MPOLIPER.swreclam%TYPE DEFAULT NULL,
			declareParameter(new SqlParameter("pv_accion_i", 		OracleTypes.VARCHAR));//
	
			declareParameter(new SqlOutParameter("pv_msg_id_o", 	OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", 		OracleTypes.VARCHAR));
			
			compile();
		}
		
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
	}
	/*////////////////////////*/
	////// borra mpoliper //////
	////////////////////////////
	
	///////////////////////////////
	////// obtiene   mpoliza //////
	/*///////////////////////////*/
	protected class ObtenerCoberturasUsuario extends CustomStoredProcedure
	{
		protected ObtenerCoberturasUsuario(DataSource dataSource)
		{
			super(dataSource,"PKG_COTIZA.P_GET_COBERTURAS");
			declareParameter(new SqlParameter("pv_cdunieco_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtenerCoberturasUsuarioMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
		
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_registro_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class ObtenerCoberturasUsuarioMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String,String> map=new HashMap<String,String>(0);
			String columnas[]=new String[]{"GARANTIA","NOMBRE_GARANTIA","SWOBLIGA","SUMA_ASEGURADA","CDCAPITA",
					"status","cdtipbca","ptvalbas","swmanual","swreas","cdagrupa",
					"ptreduci","fereduci","swrevalo"};
			for(String columna:columnas)
			{
				String string=rs.getString(columna);
				if(columna.equals("fereduci")&&string!=null&&string.length()>10)
				{
					// 1 9 9 0 - 1 2 - 0 1 _ 0 0 : 0 0 : 0 0 . 0 <<CADENA
    				//0 1 2 3 4 5 6 7 8 9 k <<INDICE
    				String aux="";
    				aux+=string.substring(8,10);
    				aux+="/";
    				aux+=string.substring(5,7);
    				aux+="/";
    				aux+=string.substring(0,4);
    				string=aux;
				}
				map.put(columna,string);
			}
			return map;
		}
	}
	/*///////////////////////////*/
	////// obtiene   mpoliza //////
	///////////////////////////////
	
	////////////////////////////
	////// p mov mpoligar //////
	/*////////////////////////*/
	protected class PMovMpoligar extends CustomStoredProcedure {
	
		protected PMovMpoligar(DataSource dataSource) {
			super(dataSource,"PKG_SATELITES.P_MOV_MPOLIGAR");
			declareParameter(new SqlParameter("pv_cdunieco_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdcapita_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipbca_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptvalbas_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swmanual_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swreas_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdagrupa_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_ACCION", 	        OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o", 	OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", 		OracleTypes.VARCHAR));
			
			compile();
		}
		
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
	}
	/*////////////////////////*/
	////// p mov mpoligar //////
	////////////////////////////
	
	////////////////////////////
	////// p mov mpolicap //////
	/*////////////////////////*/
	protected class PMovMpolicap extends CustomStoredProcedure {
	
		protected PMovMpolicap(DataSource dataSource) {
			super(dataSource,"PKG_SATELITES.P_MOV_MPOLICAP");
			
			declareParameter(new SqlParameter("pv_cdunieco_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdcapita_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptcapita_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptreduci_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fereduci_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swrevalo_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdagrupa_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_accion_i", 		OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o", 	OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", 		OracleTypes.VARCHAR));
			
			compile();
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
	}
	/*////////////////////////*/
	////// p mov mpolicap //////
	////////////////////////////
	
	////////////////////////////
	////// p mov mdomicil //////
	/*////////////////////////*/
	protected class PMovMdomicil extends CustomStoredProcedure {
	
		protected PMovMdomicil(DataSource dataSource) {
			super(dataSource,"PKG_SATELITES.P_MOV_MDOMICIL");
			/*
			pv_cdperson_i smap1.pv_cdperson
			pv_nmorddom_i smap1.NMORDDOM
			pv_msdomici_i smap1.DSDOMICI
			pv_nmtelefo_i smap1.NMTELEFO
			pv_cdpostal_i smap1.CODPOSTAL
			pv_cdedo_i    smap1.CDEDO
			pv_cdmunici_i smap1.CDMUNICI
			pv_cdcoloni_i smap1.CDCOLONI
			pv_nmnumero_i smap1.NMNUMERO
			pv_nmnumint_i smap1.NMNUMINT
			pv_accion_i   #U
			pv_msg_id_o   -
			pv_title_o    -
			*/
			declareParameter(new SqlParameter("pv_cdperson_i", 		OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmorddom_i", 		OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_msdomici_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmtelefo_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdpostal_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdedo_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdmunici_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdcoloni_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmnumero_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmnumint_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_accion_i", 		OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o", 	OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", 		OracleTypes.VARCHAR));
			
			compile();
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
	}
	/*////////////////////////*/
	////// p mov mdomicil //////
	////////////////////////////
	
	////////////////////////////
	////// p mov mpolicot //////
	/*////////////////////////*/
	protected class PMovMpolicot extends CustomStoredProcedure {
		
		protected PMovMpolicot(DataSource dataSource) {
			super(dataSource,"PKG_SATELITES.P_MOV_MPOLICOT");
			declareParameter(new SqlParameter("pv_cdunieco_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdclausu_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipcla_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swmodi_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dslinea_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_accion_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", 	OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", 		OracleTypes.VARCHAR));
			compile();
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
	}
	/*////////////////////////*/
	////// p mov mpolicot //////
	////////////////////////////
	
	///////////////////////////////
	////// p mov mesacontrol //////
	/*///////////////////////////*/
	protected class PMovMesacontrol extends CustomStoredProcedure {
		protected PMovMesacontrol(DataSource dataSource) {
			super(dataSource,"PKG_SATELITES.P_MOV_MESACONTROL");
			declareParameter(new SqlParameter("pv_cdunieco_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsucadm_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsucdoc_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtiptra_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ferecepc_i", 		OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cdagente_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_referencia_i", 	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nombre_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_festatus_i",      OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_status_i",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_comments_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsolici_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_tramite_o", 	OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", 	OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", 		OracleTypes.VARCHAR));
			compile();
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			
			wrapperResultados.setItemMap(new HashMap<String, Object>());
			wrapperResultados.getItemMap().put("ntramite", map.get("pv_tramite_o"));

			return wrapperResultados;
		}
	}
	/*///////////////////////////*/
	////// p mov mesacontrol //////
	///////////////////////////////
	
	/////////////////////////////////
	////// p mov d mesacontrol //////
	/*/////////////////////////////*/
	protected class PMovDmesacontrol extends CustomStoredProcedure {
		protected PMovDmesacontrol(DataSource dataSource) {
			super(dataSource,"PKG_SATELITES.P_MOV_DMESACONTROL");
			declareParameter(new SqlParameter("pv_ntramite_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feinicio_i", 		OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cdclausu_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_comments_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdusuari_i", 		OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o", 	OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", 		OracleTypes.VARCHAR));
			compile();
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			
			wrapperResultados.setItemMap(new HashMap<String, Object>());
			//wrapperResultados.getItemMap().put("ntramite", map.get("pv_tramite_o"));

			return wrapperResultados;
		}
	}
	/*/////////////////////////////*/
	////// p mov d mesacontrol //////
	/////////////////////////////////
	
	////////////////////////////////////////////
	////// obtener detalles de cotizacion //////
	/*////////////////////////////////////////*/
	protected class ObtenerDetallesCotizacion extends CustomStoredProcedure
	{
		protected ObtenerDetallesCotizacion(DataSource dataSource)
		{
			super(dataSource,"PKG_COTIZA.P_GET_DETALLE_COTI");
			declareParameter(new SqlParameter("pv_cdunieco_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdplan_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperpag_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_record_o", OracleTypes.CURSOR, new ObtenerDetallesCotizacionMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",   OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",    OracleTypes.VARCHAR));
		}
		
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_record_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class ObtenerDetallesCotizacionMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String,String> map=new HashMap<String,String>(0);
			String columnas[]=new String[]{"nmsituac","parentesco","orden","Codigo_Garantia","Nombre_garantia","cdtipcon","Importe"};
			for(String columna:columnas)
			{
				map.put(columna,rs.getString(columna));
			}
			return map;
		}
	}
	/*////////////////////////////////////////*/
	////// obtener detalles de cotizacion //////
	////////////////////////////////////////////
	
	//////////////////////////////////////////////
	////// obtiene domicilio            //////////
	/*//////////////////////////////////////////*/
	protected class ObtenerDomicilio extends CustomStoredProcedure
	{
		protected ObtenerDomicilio(DataSource dataSource)
		{
			super(dataSource,"PKG_COTIZA.P_GET_MDOMICIL");
			declareParameter(new SqlParameter("pv_cdunieco_i",    OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i",      OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_record_o",    OracleTypes.CURSOR, new ObtenerDomicilioMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",    OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",     OracleTypes.VARCHAR));
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_record_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class ObtenerDomicilioMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String,String> map=new LinkedHashMap<String,String>(0);
			map.put("CDPERSON"  , rs.getString("CDPERSON"));
			map.put("NMORDDOM"  , rs.getString("NMORDDOM"));
			map.put("DSDOMICI"  , rs.getString("DSDOMICI"));
			map.put("NMTELEFO"  , rs.getString("NMTELEFO"));
			map.put("CODPOSTAL"  , rs.getString("CODPOSTAL"));
			map.put("CDEDO"     , rs.getString("CDEDO"));
			map.put("estado"    , rs.getString("estado"));
			map.put("CDMUNICI"  , rs.getString("CDMUNICI"));
			map.put("Municipio" , rs.getString("Municipio"));
			map.put("CDCOLONI"  , rs.getString("CDCOLONI"));
			map.put("NMNUMERO"  , rs.getString("NMNUMERO"));
			map.put("NMNUMINT"  , rs.getString("NMNUMINT"));
			return map;
		}
	}
	/*//////////////////////////////////////////*/
	////// obtiene domicilio           ///////////
	//////////////////////////////////////////////
	
	//////////////////////////////////////////////
	////// emitir                       //////////
	/*//////////////////////////////////////////*/
	protected class Emitir extends CustomStoredProcedure
	{
		protected Emitir(DataSource dataSource)
		{
			super(dataSource,"PKG_EMISION.P_PROCESO_EMISION_GENERAL");
			declareParameter(new SqlParameter("pv_cdusuari",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdelement",     OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdcia",         OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdplan",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperpag",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fecha",         OracleTypes.DATE));
			declareParameter(new SqlOutParameter("pv_nmpoliza_o",   OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmpoliex_o",   OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o",   OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_message",      OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			
			wrapperResultados.setItemMap(new HashMap<String, Object>());
			wrapperResultados.getItemMap().put("nmpoliza", map.get("pv_nmpoliza_o"));
			wrapperResultados.getItemMap().put("nmpoliex", map.get("pv_nmpoliex_o"));
			wrapperResultados.getItemMap().put("nmsuplem", map.get("pv_nmsuplem_o"));

			return wrapperResultados;
		}
	}
	/*//////////////////////////////////////////*/
	////// emitir                      ///////////
	//////////////////////////////////////////////
	
	/////////////////////////////
	////// guardar archivo //////
	/*/////////////////////////*/
	protected class GuardarArchivoPoliza extends CustomStoredProcedure
	{
	
		protected GuardarArchivoPoliza(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_MOV_DOCUMENTOS");
			declareParameter(new SqlParameter("pv_cdunieco_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feinici_i", 		OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cddocume_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsdocume_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ntramite_i", 		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsolici_i", 		OracleTypes.VARCHAR));
	
			declareParameter(new SqlOutParameter("pv_msg_id_o", 	OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", 		OracleTypes.VARCHAR));
	
			compile();
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
	}
	/*/////////////////////////*/
	////// guardar archivo //////
	/////////////////////////////
	
	//////////////////////////////////////////////
	////// obtiene documentos de poliza //////////
	/*//////////////////////////////////////////*/
	protected class ObtenerDocumentosPoliza extends CustomStoredProcedure
	{
		protected ObtenerDocumentosPoliza(DataSource dataSource)
		{
			/*
			pv_cdunieco_i
			pv_cdramo_i
			pv_estado_i
			pv_nmpoliza_i
			pv_registro_o
			pv_msg_id_o
			pv_title_o
			 */
			super(dataSource,"PKG_CONSULTA.P_Get_documentos");
			declareParameter(new SqlParameter("pv_cdunieco_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ntramite_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtenerDocumentosPolizaMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_registro_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class ObtenerDocumentosPolizaMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			String cols[]=new String[]{"nmsolici","cddocume","dsdocume","feinici","ntramite"};
			Map<String,String> map=new HashMap<String,String>(0);
			for(String col:cols)
			{
				if(col.substring(0, 2).equals("fe"))
				{
					String fecha=rs.getString(col);
					if(fecha!=null&&fecha.length()>10)
					{
						// 1 9 9 0 - 1 2 - 0 1 _ 0 0 : 0 0 : 0 0 . 0 <<CADENA
	    				//0 1 2 3 4 5 6 7 8 9 k <<INDICE
	    				String aux="";
	    				aux+=fecha.substring(8,10);
	    				aux+="/";
	    				aux+=fecha.substring(5,7);
	    				aux+="/";
	    				aux+=fecha.substring(0,4);
	    				fecha=aux;
					}
					map.put(col,fecha);
				}
				else
				{
					map.put(col,rs.getString(col));
				}
			}
			return map;
		}
	}
	/*//////////////////////////////////////////*/
    ////// obtiene documentos de poliza //////////
	//////////////////////////////////////////////
	
	//////////////////////////////////////////////
	////// obtiene documentos de poliza //////////
	/*//////////////////////////////////////////*/
	protected class ObtenerListaDocPolizaNueva extends CustomStoredProcedure
	{
		protected ObtenerListaDocPolizaNueva(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_Imp_documentos");
			declareParameter(new SqlParameter("pv_cdunieco_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ntramite_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtenerListDocPolizaNuevaMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_registro_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class ObtenerListDocPolizaNuevaMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String cols[]=new String[]{"nmsolici","nmsituac","descripc","descripl"};
			Map<String,String> map=new HashMap<String,String>(0);
			for(String col:cols)
			{
				map.put(col,rs.getString(col));
			}
			return map;
		}
	}
	/*//////////////////////////////////////////*/
	////// obtiene documentos de poliza //////////
	//////////////////////////////////////////////
	
	//////////////////////////////////////////////
	////// obtiene documentos de poliza //////////
	/*//////////////////////////////////////////*/
	protected class ObtenerTiposClausulasExclusion extends CustomStoredProcedure
	{
		protected ObtenerTiposClausulasExclusion(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_tipos_clausulas");
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtenerTiposClausulasExclusionMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_registro_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class ObtenerTiposClausulasExclusionMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String cols[]=new String[]{"cdtipcla","dstipcla","swgrapol"};
			Map<String,String> map=new HashMap<String,String>(0);
			for(String col:cols)
			{
				map.put(col,rs.getString(col));
			}	
			return map;
		}
	}
	/*//////////////////////////////////////////*/
	////// obtiene documentos de poliza //////////
	//////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////
	////// obtiene clausulas de exclusion por tipo //////////
	/*/////////////////////////////////////////////////////*/
	protected class ObtenerExclusionesXTipo extends CustomStoredProcedure
	{
		protected ObtenerExclusionesXTipo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_clausulas_x_tipo");
			declareParameter(new SqlParameter("pv_cdtipcla_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_descrip_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtenerExclusionesXTipoMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_registro_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class ObtenerExclusionesXTipoMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String cols[]=new String[]{"cdclausu","dsclausu"};
			Map<String,String> map=new HashMap<String,String>(0);
			for(String col:cols)
			{
				map.put(col,rs.getString(col));
			}	
			return map;
		}
	}
	/*/////////////////////////////////////////////////////*/
	////// obtiene clausulas de exclusion por tipo //////////
	/////////////////////////////////////////////////////////
	
	///////////////////////////////////
	////// obtener html clausula //////
	/*///////////////////////////////*/
	protected class ObtenerHtmlClausula extends CustomStoredProcedure {

		protected ObtenerHtmlClausula(DataSource dataSource) {
			super(dataSource, "PKG_CONSULTA.P_texto_clausula");

			declareParameter(new SqlParameter("pv_cdclausu_i", OracleTypes.VARCHAR));	
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtenerHtmlClausulaMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
			compile();
		}
		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_registro_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	protected class ObtenerHtmlClausulaMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String cols[]=new String[]{"cdclausu","dsclausu","dslinea"};
			Map<String,String> map=new HashMap<String,String>(0);
			for(String col:cols)
			{
				map.put(col,rs.getString(col));
			}	
			return map;
		}
	}
	/*///////////////////////////////*/
	////// obtener html clausula //////
	///////////////////////////////////
    
	/////////////////////////////
	////// obtener policot //////
	/*/////////////////////////*/
	protected class ObtenerPolicot extends CustomStoredProcedure {

		protected ObtenerPolicot(DataSource dataSource) {
			super(dataSource, "PKG_SATELITES.P_OBTIENE_MPOLICOT");
			declareParameter(new SqlParameter("pv_cdunieco", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtenerPolicotMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
			compile();
		}
		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_registro_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	protected class ObtenerPolicotMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String cols[]=new String[]{"cdunieco","cdramo","estado","nmpoliza","nmsituac",
					"cdclausu","dsclausu","nmsuplem","status","cdtipcla","swmodi","linea_usuario","linea_general"};
			Map<String,String> map=new HashMap<String,String>(0);
			for(String col:cols)
			{
				map.put(col,rs.getString(col));
			}
			map.put("merged",rs.getString("cdtipcla")+"#_#"+rs.getString("cdclausu")+"#_#"+rs.getString("dsclausu")+"#_#"+rs.getString("linea_usuario")+"#_#"+rs.getString("linea_general"));
			logger.debug("return "+map);
			return map;
		}
	}
	/*/////////////////////////*/
	////// obtener policot //////
	/////////////////////////////
	
	/////////////////////////////////////
	////// obtener mesa de control //////
	/*/////////////////////////////////*/
	protected class ObtenerMesaControl extends CustomStoredProcedure
	{
		protected ObtenerMesaControl(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_OBTIENE_MESACONTROL");
			declareParameter(new SqlParameter("pv_dsrol_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtenerMesaControlMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_registro_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class ObtenerMesaControlMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String cols[]=new String[]{"ntramite","cdunieco","cdramo","estado","nmpoliza",
					"nmsolici","cdsucadm","cdsucdoc","cdsubram","cdtiptra","ferecepc","cdagente",
					"Nombre_agente","referencia","nombre","fecstatu","status","comments"};
			Map<String,String> map=new HashMap<String,String>(0);
			for(String col:cols)
			{
				if(col!=null&&col.substring(0,2).equals("fe"))
				{
					map.put(col,Utilerias.formateaFecha(rs.getString(col)));
				}
				else
				{
					map.put(col,rs.getString(col));
				}
			}	
			return map;
		}
	}
	/*/////////////////////////////////*/
	////// obtener mesa de control //////
	/////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////
	////// obtener detalles de tramite(s) para mesa de control //////
	/*/////////////////////////////////////////////////////////////*/
	protected class ObtenerDetalleMesaControl extends CustomStoredProcedure
	{
		protected ObtenerDetalleMesaControl(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA. P_cons_det_mesactrl");
			declareParameter(new SqlParameter("pv_ntramite_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtenerDetalleMesaControlMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
	
		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			List result = (List) map.get("pv_registro_o");
			wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class ObtenerDetalleMesaControlMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			String cols[]=new String[]{"NTRAMITE","NMORDINA","CDTIPTRA","CDCLAUSU","FECHAINI","FECHAFIN",
					"COMMENTS","CDUSUARI_INI","CDUSUARI_FIN"};
			Map<String,String> map=new HashMap<String,String>(0);
			for(String col:cols)
			{
				if(col!=null&&col.substring(0,2).equalsIgnoreCase("fe"))
				{
					map.put(col,Utilerias.formateaFecha(rs.getString(col)));
				}
				else
				{
					map.put(col,rs.getString(col));
				}
			}	
			return map;
		}
	}
	/*/////////////////////////////////////////////////////////////*/
	////// obtener detalles de tramite(s) para mesa de control //////
	/////////////////////////////////////////////////////////////////
	
	protected class ObtenDatosRecibos extends CustomStoredProcedure {
		
		protected ObtenDatosRecibos(DataSource dataSource) {
			super(dataSource, "pkg_consulta.P_cons_recibo_pol");

			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));			
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));			
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));			
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));			
			declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));			
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosRecibosMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
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

	
    protected class DatosRecibosMapper  implements RowMapper {
    
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Recibo recibo = new Recibo();	    
        	DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT); 
        	Calendar cal;
        	
        	recibo.setActRec(rs.getInt("actRec"));
        	recibo.setBonific(rs.getDouble("bonific"));
        	recibo.setComPri(rs.getDouble("comPri"));
        	recibo.setComRec(rs.getDouble("comRec"));
        	recibo.setDerecho(rs.getDouble("derecho"));
        	
        	try {
	        	cal = Calendar.getInstance();
				cal.setTime(df.parse(rs.getString("fecEmi")));
	        	recibo.setFecEmi(cal);
        	} catch (Exception e) {
				logger.error("NO SE PUDO PARSEAR LA FECHA fecEmi !!! " + rs.getString("fecEmi"));
				recibo.setFecEmi(null);
			}
        	
        	try {
	        	cal = Calendar.getInstance();
	        	cal.setTime(df.parse(rs.getString("fecIni")));
	        	recibo.setFecIni(cal);
        	} catch (Exception e) {
				logger.error("NO SE PUDO PARSEAR LA FECHA fecIni !!! " + rs.getString("fecIni"));
				recibo.setFecIni(null);
			}
        	
	        try {
	        	cal = Calendar.getInstance();
	        	cal.setTime(df.parse(rs.getString("fecPag")));
	        	recibo.setFecPag(cal);
	        } catch (Exception e) {
				//logger.error("NO SE PUDO PARSEAR LA FECHA fecPag !!! " + rs.getString("fecPag")+" se manda valor default 01/01/1900");
				cal = Calendar.getInstance();
				try {
					cal.setTime(df.parse("01/01/1900"));
				} catch (ParseException e1) {
					logger.error("Error.");
				}
	        	recibo.setFecPag(cal);
			}
	        	
	        try {
	        	cal = Calendar.getInstance();
	        	cal.setTime(df.parse(rs.getString("fecSta")));
	        	recibo.setFecSta(cal);
	        } catch (Exception e) {
				logger.error("NO SE PUDO PARSEAR LA FECHA fecSta !!! " + rs.getString("fecSta"));
				recibo.setFecSta(null);
			}
	        
	        try {
	        	cal = Calendar.getInstance();
	        	cal.setTime(df.parse(rs.getString("fecTer")));
	        	recibo.setFecTer(cal);
        	} catch (Exception e) {
				logger.error("NO SE PUDO PARSEAR LA FECHA fecTer !!! " + rs.getString("fecTer"));
				recibo.setFecTer(null);
			}
	        	
        	recibo.setIva(rs.getDouble("iva"));
        	recibo.setNumAgt(rs.getInt("numAgt"));
        	recibo.setNumCli(rs.getInt("numCli"));
        	recibo.setNumEnd(rs.getInt("numEnd"));
        	recibo.setNumMon(rs.getInt("numMon"));
        	recibo.setNumPol(rs.getInt("numPol"));
        	recibo.setNumRam(rs.getInt("numRam"));
        	recibo.setNumRec(rs.getInt("numRec"));
        	recibo.setNumSuc(rs.getInt("numSuc"));
        	recibo.setPriCom(rs.getDouble("priCom"));
        	recibo.setPriDot(rs.getDouble("priDot"));
        	recibo.setPrima(rs.getDouble("prima"));
        	recibo.setRecargo(rs.getDouble("recargo"));
        	recibo.setRmdbRn(rs.getInt("rmdbRn"));
        	recibo.setSaldo(rs.getDouble("saldo"));
        	recibo.setStatusr(rs.getString("statusr"));
        	
        	String tipoEndoso = rs.getString("tipEnd");
        	if(tipoEndoso == null)tipoEndoso = "";
        	recibo.setTipEnd(tipoEndoso);
        	
        	recibo.setTipRec(rs.getString("tipRec"));
        	recibo.setTotRec(rs.getInt("totRec"));
        	
        	return recibo;
        }
    }

    protected class ObtenCatalogoColonias extends CustomStoredProcedure {
    	
    	protected ObtenCatalogoColonias(DataSource dataSource) {
    		super(dataSource, "PKG_LISTAS.P_GET_COLONIAS");
    		
    		declareParameter(new SqlParameter("pv_codpostal_i", OracleTypes.VARCHAR));			
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ColoniasMapper()));
    		declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
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
    
    
    protected class ColoniasMapper  implements RowMapper {
    	
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		GenericVO base = new GenericVO();	    
    		base.setKey(rs.getString("CODIGO"));
    		base.setValue(rs.getString("NOMBRE"));
    		return base;
    	}
    }

    
    protected class ObtenDatosCliente extends CustomStoredProcedure {
    	
    	protected ObtenDatosCliente(DataSource dataSource) {
    		super(dataSource, "PKG_CONSULTA.P_cons_sal_cli");
    		
    		declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));			
    		declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));			
    		declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));			
    		declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));			
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ClienteMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
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
    
    
    protected class ClienteMapper  implements RowMapper {
    	
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		ClienteSalud cliente = new ClienteSalud();
    		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT); 
        	Calendar cal;
    		
    		cliente.setAgrupaCli(rs.getInt("agrupaCli"));
    		cliente.setApellidomCli(rs.getString("apellidomCli"));
    		cliente.setApellidopCli(rs.getString("apellidopCli"));
    		cliente.setBannomCli(rs.getString("bannomCli"));
    		cliente.setCallecarCli(rs.getString("callecarCli"));
    		cliente.setCalleCli(rs.getString("calleCli"));
    		cliente.setCanconCli(rs.getInt("canconCli"));
    		cliente.setCelularCli(rs.getString("celularCli"));
    		cliente.setCheqdevCli(rs.getInt("cheqdevCli"));
    		cliente.setClaveCli(rs.getInt("claveCli"));
    		cliente.setCodcarCli(rs.getInt("codcarCli"));
    		cliente.setCodposCli(rs.getInt("codposCli"));
    		cliente.setColcarCli(rs.getString("colcarCli"));
    		cliente.setColoniaCli(rs.getString("coloniaCli"));
    		cliente.setEdocarCli(rs.getInt("edocarCli"));
    		cliente.setEdocivilCli(rs.getInt("edocivilCli"));
    		cliente.setEstadoCli(rs.getInt("estadoCli"));
    		cliente.setFaxCli(rs.getString("faxCli"));

    		try {
	        	cal = Calendar.getInstance();
				cal.setTime(df.parse(rs.getString("fecaltaCli")));
				cliente.setFecaltaCli(cal);
        	} catch (Exception e) {
				logger.error("NO SE PUDO PARSEAR LA FECHA fecaltaCli !!! " + rs.getString("fecaltaCli"));
				cliente.setFecaltaCli(null);
			}
        	
        	try {
	        	cal = Calendar.getInstance();
	        	cal.setTime(df.parse(rs.getString("fecnacCli")));
	        	cliente.setFecnacCli(cal);
        	} catch (Exception e) {
				logger.error("NO SE PUDO PARSEAR LA FECHA fecnacCli !!! " + rs.getString("fecnacCli"));
				cliente.setFecnacCli(null);
			}
    		
    		cliente.setFismorCli(rs.getInt("fismorCli"));
    		cliente.setGiroCli(rs.getInt("giroCli"));
    		
    		logger.debug("cliente.getGiroCli(): "+cliente.getGiroCli());
    		
    		cliente.setMuncarCli(rs.getString("muncarCli"));
    		cliente.setMunicipioCli(rs.getString("municipioCli"));
    		cliente.setNombreCli(rs.getString("nombreCli"));
    		cliente.setNumcarCli(rs.getString("numcarCli"));
    		cliente.setNumeroCli(rs.getString("numeroCli"));
    		cliente.setPobcarCli(rs.getString("pobcarCli"));
    		cliente.setPoblacionCli(rs.getString("poblacionCli"));
    		cliente.setRfcCli(rs.getString("rfcCli"));
    		cliente.setRmdbRn(rs.getInt("rmdbRn"));
    		cliente.setSexoCli(rs.getInt("sexoCli"));
    		cliente.setSinocurCli(rs.getInt("sinocurCli"));
    		cliente.setSucursalCli(rs.getInt("sucursalCli"));
    		cliente.setTelefonoCli(rs.getString("telefonoCli"));
    		cliente.setTituloCli(rs.getString("tituloCli"));
    		cliente.setTitulonobCli(rs.getString("titulonobCli"));
    		
    		return cliente;
    	}
    }
    
    ///////////////////////////////////////////////////////////
    ////// actualizar solici de tarea de mesa de control //////
    /*///////////////////////////////////////////////////////*/
    protected class MesaControlUpdateSolici extends CustomStoredProcedure {
    	/*
	    PROCEDURE P_UPDATE_NMSOLICI(pv_ntramite_i IN TDOCUPOL.NTRAMITE%TYPE,
	    pv_nmsolici_i IN TDOCUPOL.NMSOLICI%TYPE,
	    pv_msg_id_o   OUT GB_MESSAGES.msg_id%TYPE,
	    pv_title_o 
        */
    	protected MesaControlUpdateSolici(DataSource dataSource) {
    		super(dataSource,"PKG_SATELITES.P_UPDATE_NMSOLICI");
    		
    		declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_nmsolici_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
    		
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
    }
    /*///////////////////////////////////////////////////////*/
	////// actualizar solici de tarea de mesa de control //////
    ///////////////////////////////////////////////////////////
    
    ///////////////////////////////////////////////////////////
    ////// actualizar status de tarea de mesa de control //////
    /*///////////////////////////////////////////////////////*/
    protected class MesaControlUpdateStatus extends CustomStoredProcedure {
    	/*
	    PROCEDURE P_UPDATE_NMSOLICI(pv_ntramite_i IN TDOCUPOL.NTRAMITE%TYPE,
	    pv_nmsolici_i IN TDOCUPOL.NMSOLICI%TYPE,
	    pv_msg_id_o   OUT GB_MESSAGES.msg_id%TYPE,
	    pv_title_o 
        */
    	protected MesaControlUpdateStatus(DataSource dataSource) {
    		super(dataSource,"PKG_SATELITES.P_UPDATE_STATUS_MC");
    		
    		declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_status_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
    		
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
    }
    /*///////////////////////////////////////////////////////*/
	////// actualizar status de tarea de mesa de control //////
    ///////////////////////////////////////////////////////////
    
    ///////////////////////////////////////////////////////////
    ////// actualizar status de tarea de mesa de control //////
    /*///////////////////////////////////////////////////////*/
    protected class MesaControlFinalizarDetalle extends CustomStoredProcedure {
    	protected MesaControlFinalizarDetalle(DataSource dataSource) {
    		super(dataSource,"PKG_SATELITES.P_UPDATE_TDMESA");
    		
    		declareParameter(new SqlParameter("pv_nmordina_i",     OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_ntramite_i",     OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_cdusuari_fin_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_comments_i",     OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_fechafin_i",     OracleTypes.DATE));
    		declareParameter(new SqlOutParameter("PV_MSG_ID_O",    OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("PV_TITLE_O",     OracleTypes.VARCHAR));
    		
    		compile();
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
    }
    /*///////////////////////////////////////////////////////*/
	////// actualizar status de tarea de mesa de control //////
    ///////////////////////////////////////////////////////////
}