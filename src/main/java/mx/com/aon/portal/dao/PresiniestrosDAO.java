package mx.com.aon.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.flujos.presinietros.model.DocumentoVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.InstrumentoPagoAtributosVO;
import mx.com.aon.portal.model.presiniestros.AutomovilVO;
import mx.com.aon.portal.model.presiniestros.BeneficioVO;
import mx.com.aon.portal.model.presiniestros.DanoVO;
import mx.com.aon.portal.model.presiniestros.PreSiniestroVO;
import mx.com.aon.portal.model.presiniestros.PreSiniestroPorPolizaVO;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class PresiniestrosDAO extends AbstractDAO {

	@SuppressWarnings("unused")
	 private static Logger logger = Logger.getLogger(PresiniestrosDAO.class);
	 
	 public static final String TEST = "TEST";

	 // Endpoints para búsqueda de pólizas para presiniestros
	 	// Endpoints para carga de catálogos en pantalla de búsqueda
	 public static final String OBTIENE_EMPRESAS = "OBTIENE_EMPRESAS";
	 public static final String OBTIENE_ASEGURADORAS = "OBTIENE_ASEGURADORAS";
	 public static final String OBTIENE_RAMOS = "OBTIENE_RAMOS";
	 	// Endpoint para buscar pólizas para presiniestros
	 public static final String CONSULTA_POLIZAS = "CONSULTA_POLIZAS";
	 public static final String CONSULTA_POLIZAS_EXPORT = "CONSULTA_POLIZAS_EXPORT";
	 
	 // Endpoints para consultar presiniestros por póliza
	 // Existe uno por cada tipo de presiniestro (ramo)
	 public static final String OBTIENE_TPREAUTO = "OBTIENE_TPREAUTO";
	 public static final String OBTIENE_TPREAUTO_EXPORT = "OBTIENE_TPREAUTO_EXPORT";
	 public static final String OBTIENE_TPREBEN = "OBTIENE_TPREBEN";
	 public static final String OBTIENE_TPREBEN_EXPORT = "OBTIENE_TPREBEN_EXPORT";
	 public static final String OBTIENE_TPREBEN_GASFUN = "OBTIENE_TPREBEN_GASFUN";
	 public static final String OBTIENE_TPREBEN_GASFUN_EXPORT = "OBTIENE_TPREBEN_GASFUN_EXPORT";
	 public static final String OBTIENE_TPREDANO = "OBTIENE_TPREDANO";
	 public static final String OBTIENE_TPREDANO_EXPORT = "OBTIENE_TPREDANO_EXPORT";
	 
	 // Endpoints para agregar presiniestro
	 // Existe uno por cada tipo de presiniestro (ramo)
	 public static final String EJECUTA_GUARDA_TPREBEN = "EJECUTA_GUARDA_TPREBEN";
	 public static final String EJECUTA_GUARDA_TPREVGFAP = "EJECUTA_GUARDA_TPREVGFAP";
	 public static final String EJECUTA_GUARDA_TPREAUTO = "EJECUTA_GUARDA_TPREAUTO";
	 public static final String EJECUTA_GUARDA_TPREDANIO = "EJECUTA_GUARDA_TPREDANIO";
	 
	 // Endpoints para editar presiniestro
	 // Existe uno por cada tipo de presiniestro (ramo)
	 public static final String OBTIENE_PRE_AUTO = "OBTIENE_PRE_AUTO";
	 public static final String OBTIENE_PRE_BENEFICIO = "OBTIENE_PRE_BENEFICIO";
	 public static final String OBTIENE_PRE_BENEFICIO_GASFUN = "OBTIENE_PRE_BENEFICIO_GASFUN";
	 public static final String OBTIENE_PRE_DANO = "OBTIENE_PRE_DANO";

	 // Endpoints para presiniestros de Beneficios
	 public static final String OBTIENE_TRAMITES = "OBTIENE_TRAMITES";
	 public static final String OBTIENE_PADECIMIENTOS = "OBTIENE_PADECIMIENTOS";
	 public static final String OBTIENE_DOCUMENTOS_PRE = "OBTIENE_DOCUMENTOS_PRE";
	 public static final String GUARDA_ATRIBUTOS_DCTO_PRE = "GUARDA_ATRIBUTOS_DCTO_PRE";
	 public static final String ELIMINA_DCTO_PRE = "ELIMINA_DCTO_PRE";

	 
	 protected void initDao() throws Exception {
		 
		 addStoredProcedure(TEST, new ObtenerInstrumentosPagoCliente(getDataSource()));
		 
		 addStoredProcedure(OBTIENE_EMPRESAS, new ObtenerEmpresas(getDataSource()));
		 addStoredProcedure(OBTIENE_ASEGURADORAS, new ObtenerAseguradoras(getDataSource()));
		 addStoredProcedure(OBTIENE_RAMOS, new ObtenerRamos(getDataSource()));
		 addStoredProcedure(CONSULTA_POLIZAS, new ConsultaPolizas(getDataSource()));
		 addStoredProcedure(CONSULTA_POLIZAS_EXPORT, new ConsultaPolizasExport(getDataSource()));
		 
		 addStoredProcedure(OBTIENE_TPREAUTO, new ConsultaPresiniestrosPorPolizaAutomovil(getDataSource()));
		 addStoredProcedure(OBTIENE_TPREAUTO_EXPORT, new ConsultaPresiniestrosPorPolizaAutomovilExport(getDataSource()));
		 addStoredProcedure(OBTIENE_TPREBEN, new ConsultaPresiniestrosPorPolizaBeneficio(getDataSource()));
		 addStoredProcedure(OBTIENE_TPREBEN_EXPORT, new ConsultaPresiniestrosPorPolizaBeneficioExport(getDataSource()));
		 addStoredProcedure(OBTIENE_TPREBEN_GASFUN, new ConsultaPresiniestrosPorPolizaBeneficioGasFun(getDataSource()));
		 addStoredProcedure(OBTIENE_TPREBEN_GASFUN_EXPORT, new ConsultaPresiniestrosPorPolizaBeneficioGasFunExport(getDataSource()));
		 addStoredProcedure(OBTIENE_TPREDANO, new ConsultaPresiniestrosPorPolizaDanio(getDataSource()));
		 addStoredProcedure(OBTIENE_TPREDANO_EXPORT, new ConsultaPresiniestrosPorPolizaDanioExport(getDataSource()));

		 addStoredProcedure(OBTIENE_PRE_AUTO, new ObtienePreAuto(getDataSource()));
		 addStoredProcedure(OBTIENE_PRE_DANO, new ObtienePreDanio(getDataSource()));
		 addStoredProcedure(EJECUTA_GUARDA_TPREAUTO, new EjecutaGuardaTPreAuto(getDataSource()));
		 addStoredProcedure(EJECUTA_GUARDA_TPREDANIO, new EjecutaGuardaTPreDanio(getDataSource()));
		 
		 addStoredProcedure(OBTIENE_TRAMITES, new ObtieneTramites(getDataSource()));
		 addStoredProcedure(OBTIENE_PADECIMIENTOS, new ObtienePadecimientos(getDataSource()));
		 addStoredProcedure(EJECUTA_GUARDA_TPREBEN, new EjecutaGuardaTPreBen(getDataSource()));
		 addStoredProcedure(EJECUTA_GUARDA_TPREVGFAP, new EjecutaGuardaTPreVGFAP(getDataSource()));
		 addStoredProcedure(OBTIENE_PRE_BENEFICIO, new ObtienePreBen(getDataSource()));
		 addStoredProcedure(OBTIENE_PRE_BENEFICIO_GASFUN, new ObtienePreBenGasFun(getDataSource()));
		 addStoredProcedure(OBTIENE_DOCUMENTOS_PRE, new ObtieneDocumentosPre(getDataSource()));
		 addStoredProcedure(GUARDA_ATRIBUTOS_DCTO_PRE, new GuardaAtrsDocPre(getDataSource()));
		 addStoredProcedure(ELIMINA_DCTO_PRE, new EliminaDocPre(getDataSource()));

	 }
	 
	 
	 protected class ObtenerInstrumentosPagoCliente extends CustomStoredProcedure {

			protected ObtenerInstrumentosPagoCliente(DataSource dataSource) {
				super(dataSource, "PKG_INSTPAGO.P_OBTIENE_ATRIBUTOS");

				declareParameter(new SqlParameter("pv_cdelemento_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdforpag_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cduniage_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdramo_i",OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaInstrumentosPagoClientesMapper()));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
				
				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
		}
	 
	 
	 protected class ListaInstrumentosPagoClientesMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	InstrumentoPagoAtributosVO instrumVO = new InstrumentoPagoAtributosVO();
	        	instrumVO.setCdInsCte(rs.getString("CDINSCTE"));
	        	instrumVO.setCdForPag(rs.getString("CDFORPAG"));
	        	instrumVO.setDsForPag(rs.getString("DSFORPAG"));
	        	instrumVO.setCdElemento(rs.getString("CDELEMENTO"));
	        	instrumVO.setDsElemento(rs.getString("DSELEMEN"));
	        	instrumVO.setCdUnieco(rs.getString("CDUNIAGE"));
	        	instrumVO.setDsUnieco(rs.getString("DSUNIECO"));
	        	instrumVO.setCdRamo(rs.getString("CDRAMO"));
	        	instrumVO.setDsRamo(rs.getString("DSRAMO"));
	        	
	            return instrumVO;
	        }
	    }

	 /* ***************************************************************
	  * INICIA DEFINICION DE CLASES PARA CONSULTA DE PRESINIESTROS
	  *************************************************************** */
	 
	 /* 
	  * Clase para obtener el catálogo de empresas en la Consulta de Presiniestros
	  */
	 protected class ObtenerEmpresas extends CustomStoredProcedure {

			protected ObtenerEmpresas(DataSource dataSource) {

				super(dataSource, "PKG_LISTAS.P_NIVELES_CORPORA");
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaBaseObjectMapper("dselemen", "cdelemento")));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
				
				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
		}
	 
	 /*
	  * Clase genérica para mapear el cursor que se conforma de una columna clave y una columna valor.
	  * Se deberá crear un objeto utilizando el método constructor para setear estas columnas.
	  */
	 protected class ListaBaseObjectMapper  implements RowMapper {
		 	private String columnaLabel = "";
		 	private String columnaValue = "";
		 	
		 	public ListaBaseObjectMapper(String columnaLabel, String columnaValue) {
		 		this.columnaLabel = columnaLabel;
		 		this.columnaValue = columnaValue;
		 	}
		 
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	BaseObjectVO baseObjectVO = new BaseObjectVO();
	        	baseObjectVO.setValue(rs.getString(this.columnaValue));
	        	baseObjectVO.setLabel(rs.getString(this.columnaLabel));
	        	
	            return baseObjectVO;
	        }
	 }

	 /*
	  * Clase para obtener el catálogo de aseguradoras en la Consulta de Presiniestros
	  */
	 protected class ObtenerAseguradoras extends CustomStoredProcedure {

			protected ObtenerAseguradoras(DataSource dataSource) {

				super(dataSource, "PKG_LISTAS.P_ASEGURADORA");
				
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaBaseObjectMapper("dsunieco", "cdunieco")));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

				compile();
				
			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
		}

	 /*
	  * Clase para obtener el catálogo de ramos en la Consulta de Presiniestros
	  */
	 protected class ObtenerRamos extends CustomStoredProcedure {

			protected ObtenerRamos(DataSource dataSource) {

				super(dataSource, "PKG_LISTAS.P_RAMOS");
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaBaseObjectMapper("DSTIPORA", "CDTIPORA")));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
		}
	 
	 /*
	  * Clase para la búsqueda de presiniestros
	  */
	 protected class ConsultaPolizas extends CustomStoredProcedure {

			protected ConsultaPolizas(DataSource dataSource) {

				super(dataSource, "PKG_PRESINIESTRO.CONSULTA_POLIZAS");
				declareParameter(new SqlParameter("pi_CDUNIECO", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pi_CDRAMO", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pi_POLIZA", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pi_INCISO", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pi_SUBINCISO", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pi_ASEGURADORA", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pi_ASEGURADO", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pi_EMPRESA", OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaPreSiniestroMapper()));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
		}

	 /*
	  * Clase que mapea el cursor de la búsqueda de presiniestros 
	  * Invocada por la clase ConsultaPolizas
	  */
	 protected class ListaPreSiniestroMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	PreSiniestroVO psVO = new PreSiniestroVO();
	        	psVO.setTipoPresiniestro(rs.getString("CDTIPORA"));
	        	psVO.setCdunieco(rs.getString("CDUNIECO"));
	        	psVO.setCdramo(rs.getString("CDRAMO"));
	        	psVO.setEstado(rs.getString("ESTADO"));
	        	psVO.setNmpoliza(rs.getString("NMPOLIZA"));
	        	psVO.setPoliza(rs.getString("POLIZA"));
	        	psVO.setInciso(rs.getString("INCISO"));
	        	psVO.setSubinciso(rs.getString("SUBINCISO"));
	        	psVO.setCdEmpresaOCorporativo(rs.getString("CDELEMENTO"));
	        	psVO.setEmpresaOCorporativo(rs.getString("EMPRESA"));
	        	psVO.setCdAseguradora(rs.getString("CDUNIAGE"));
	        	psVO.setAseguradora(rs.getString("ASEGURADORA"));
	        	psVO.setAsegurado(rs.getString("ASEGURADO"));
	        	psVO.setRamo(rs.getString("DSRAMO"));
	        	psVO.setInicioVigencia(rs.getString("FEINICIO"));
	        	psVO.setFinVigencia(rs.getString("FEFIN"));
	        	psVO.setPrimaTotal(rs.getString("PRIMA"));
	        	psVO.setFormaPago(rs.getString("DSPERPAG"));
	        	psVO.setInstrumentoPago(rs.getString("DSFORPAG"));
	        	psVO.setCdTipRam(rs.getString("CDTIPRAM"));
	        	psVO.setNumeroSuplemento(rs.getString("NMSUPLEM"));
	        	
	            return psVO;
	        }
	    }
	 
	 /*
	  * Clase para la búsqueda de presiniestros
	  */
	 protected class ConsultaPolizasExport extends CustomStoredProcedure {

			protected ConsultaPolizasExport(DataSource dataSource) {

				super(dataSource, "PKG_PRESINIESTRO.CONSULTA_POLIZAS");
				declareParameter(new SqlParameter("pi_CDUNIECO", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pi_CDRAMO", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pi_POLIZA", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pi_INCISO", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pi_SUBINCISO", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pi_ASEGURADORA", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pi_ASEGURADO", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pi_EMPRESA", OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaPreSiniestroExportMapper()));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
		}

	 /*
	  * Clase que mapea el cursor de la búsqueda de presiniestros 
	  * Invocada por la clase ConsultaPolizas
	  */
	 protected class ListaPreSiniestroExportMapper  implements RowMapper {
		 	@SuppressWarnings("unchecked")
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	            ArrayList lista =  new ArrayList(12);

	            lista.add(rs.getString("POLIZA"));
	        	lista.add(rs.getString("INCISO"));
	        	lista.add(rs.getString("SUBINCISO"));
	        	lista.add(rs.getString("EMPRESA"));
	        	lista.add(rs.getString("ASEGURADORA"));
	        	lista.add(rs.getString("ASEGURADO"));
	        	lista.add(rs.getString("DSRAMO"));	        	
	        	lista.add(rs.getString("FEINICIO"));
	        	lista.add(rs.getString("FEFIN"));
	        	lista.add(rs.getString("PRIMA"));
	        	lista.add(rs.getString("DSPERPAG"));
	        	lista.add(rs.getString("DSFORPAG"));
	        	
	            return lista;
	        }
	    }
	 
	 /*
	  * Clase para obtener los presiniestros asociados a una póliza para el Presiniestro de tipo Automóviles
	  */
	 protected class ConsultaPresiniestrosPorPolizaAutomovil extends CustomStoredProcedure {

			protected ConsultaPresiniestrosPorPolizaAutomovil(DataSource dataSource) {

				super(dataSource, "PKG_PRESINIESTRO.OBTIENE_TPREAUTO");
				declareParameter(new SqlParameter("p_poliza_i", OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaPreSiniestroPorPolizaAutomovilMapper()));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
	}

	 /*
	  * Clase para obtener los presiniestros asociados a una póliza para el Presiniestro de tipo Automóviles
	  */
	 protected class ConsultaPresiniestrosPorPolizaAutomovilExport extends CustomStoredProcedure {

			protected ConsultaPresiniestrosPorPolizaAutomovilExport(DataSource dataSource) {

				super(dataSource, "PKG_PRESINIESTRO.OBTIENE_TPREAUTO");
				declareParameter(new SqlParameter("p_poliza_i", OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaPreSiniestroPorPolizaAutomovilExportMapper()));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
	}

	 /*
	  * Clase para mapear el cursor de los presiniestros asociados a una póliza para el Presiniestro de tipo Automóviles
	  */
	 protected class ListaPreSiniestroPorPolizaAutomovilMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	PreSiniestroPorPolizaVO psppVO = new PreSiniestroPorPolizaVO();
	        	psppVO.setFolio(rs.getString("NMFOLACW"));
	        	psppVO.setFechaRegistro(rs.getString("FESINIES"));
	        	
	            return psppVO;
	        }
    }
	 
	 /*
	  * Clase para mapear el cursor de los presiniestros asociados a una póliza para el Presiniestro de tipo Automóviles
	  */
	 protected class ListaPreSiniestroPorPolizaAutomovilExportMapper  implements RowMapper {
	        @SuppressWarnings("unchecked")
		 	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	            ArrayList lista =  new ArrayList(2);

	            lista.add(rs.getString("NMFOLACW"));
	        	lista.add(rs.getString("FESINIES"));
	        	
	            return lista;
	        }
    }
	 
	 /*
	  * Clase para obtener los presiniestros asociados a una póliza para el Presiniestro de tipo Beneficio
	  */
	 protected class ConsultaPresiniestrosPorPolizaBeneficio extends CustomStoredProcedure {

			protected ConsultaPresiniestrosPorPolizaBeneficio(DataSource dataSource) {

				super(dataSource, "PKG_PRESINIESTRO.OBTIENE_TPREBEN");
				declareParameter(new SqlParameter("p_poliza", OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaPreSiniestroPorPolizaBeneficioMapper()));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
	}
	 
	 protected class ConsultaPresiniestrosPorPolizaBeneficioGasFun extends CustomStoredProcedure {
		 
		 protected ConsultaPresiniestrosPorPolizaBeneficioGasFun(DataSource dataSource) {
			 
			 super(dataSource, "PKG_PRESINIESTRO.OBTIENE_TPREVGFAP");
			 declareParameter(new SqlParameter("p_poliza", OracleTypes.VARCHAR));
			 declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaPreSiniestroPorPolizaBeneficioMapper()));
			 declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			 declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			 
			 compile();
			 
		 }
		 
		 @SuppressWarnings("unchecked")
		 public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			 WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			 WrapperResultados wrapperResultados = mapper.build(map);
			 List result = (List) map.get("pv_registro_o");
			 wrapperResultados.setItemList(result);
			 return wrapperResultados;
		 }
	 }

	 /*
	  * Clase para obtener los presiniestros asociados a una póliza para el Presiniestro de tipo Beneficio
	  */
	 protected class ConsultaPresiniestrosPorPolizaBeneficioExport extends CustomStoredProcedure {

			protected ConsultaPresiniestrosPorPolizaBeneficioExport(DataSource dataSource) {

				super(dataSource, "PKG_PRESINIESTRO.OBTIENE_TPREBEN");
				declareParameter(new SqlParameter("p_poliza", OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaPreSiniestroPorPolizaBeneficioExportMapper()));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
	}
	 
	 protected class ConsultaPresiniestrosPorPolizaBeneficioGasFunExport extends CustomStoredProcedure {
		 
		 protected ConsultaPresiniestrosPorPolizaBeneficioGasFunExport(DataSource dataSource) {
			 
			 super(dataSource, "PKG_PRESINIESTRO.OBTIENE_TPREVGFAP");
			 declareParameter(new SqlParameter("p_poliza", OracleTypes.VARCHAR));
			 declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaPreSiniestroPorPolizaBeneficioExportMapper()));
			 declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			 declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			 
			 compile();
			 
		 }
		 
		 @SuppressWarnings("unchecked")
		 public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			 WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			 WrapperResultados wrapperResultados = mapper.build(map);
			 List result = (List) map.get("pv_registro_o");
			 wrapperResultados.setItemList(result);
			 return wrapperResultados;
		 }
	 }
	 
	 /*
	  * Clase para mapear el cursor de los presiniestros asociados a una póliza para el Presiniestro de tipo Beneficios
	  */
	 protected class ListaPreSiniestroPorPolizaBeneficioMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	PreSiniestroPorPolizaVO psppVO = new PreSiniestroPorPolizaVO();
	        	psppVO.setFolio(rs.getString("NMFOLACW"));
	        	psppVO.setFechaRegistro(rs.getString("FEREPORTE"));
	        	
	            return psppVO;
	        }
	 }
	 
	 /*
	  * Clase para mapear el cursor de los presiniestros asociados a una póliza para el Presiniestro de tipo Beneficios
	  */
	 protected class ListaPreSiniestroPorPolizaBeneficioExportMapper  implements RowMapper {
		 	@SuppressWarnings("unchecked")
		 	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	            ArrayList lista =  new ArrayList(2);

	            lista.add(rs.getString("NMFOLACW"));
	        	lista.add(rs.getString("FEREPORTE"));
	        	
	            return lista;
	        }
	    }

	 /*
	  * Clase para obtener los presiniestros asociados a una póliza para el Presiniestro de tipo Daños
	  */
	 protected class ConsultaPresiniestrosPorPolizaDanio extends CustomStoredProcedure {

			protected ConsultaPresiniestrosPorPolizaDanio(DataSource dataSource) {

				super(dataSource, "PKG_PRESINIESTRO.OBTIENE_TPREDANIO");
				declareParameter(new SqlParameter("p_poliza_i", OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("p_registro_o", OracleTypes.CURSOR, new ListaPreSiniestroPorPolizaDanioMapper()));
		        declareParameter(new SqlOutParameter("p_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("p_title_o", OracleTypes.VARCHAR));

				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("p_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
	}

	 /*
	  * Clase para obtener los presiniestros asociados a una póliza para el Presiniestro de tipo Daños
	  */
	 protected class ConsultaPresiniestrosPorPolizaDanioExport extends CustomStoredProcedure {

			protected ConsultaPresiniestrosPorPolizaDanioExport(DataSource dataSource) {

				super(dataSource, "PKG_PRESINIESTRO.OBTIENE_TPREDANIO");
				declareParameter(new SqlParameter("p_poliza_i", OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("p_registro_o", OracleTypes.CURSOR, new ListaPreSiniestroPorPolizaDanioExportMapper()));
		        declareParameter(new SqlOutParameter("p_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("p_title_o", OracleTypes.VARCHAR));

				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("p_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
	}

	 /*
	  * Clase para mapear el cursor de los presiniestros asociados a una póliza para el Presiniestro de tipo Daños
	  */
	 protected class ListaPreSiniestroPorPolizaDanioMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	PreSiniestroPorPolizaVO psppVO = new PreSiniestroPorPolizaVO();
	        	psppVO.setFolio(rs.getString("NMFOLACW"));
	        	psppVO.setFechaRegistro(rs.getString("FESINIES"));
	        	
	            return psppVO;
	        }
    }
	 
	 /*
	  * Clase para mapear el cursor de los presiniestros asociados a una póliza para el Presiniestro de tipo Daños
	  */
	 protected class ListaPreSiniestroPorPolizaDanioExportMapper  implements RowMapper {
	        @SuppressWarnings("unchecked")
		 	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	            ArrayList lista =  new ArrayList(2);

	            lista.add(rs.getString("NMFOLACW"));
	        	lista.add(rs.getString("FESINIES"));
	        	
	            return lista;
	        }
    }
	 
	 /* ***************************************************************
	  * TERMINA DEFINICION DE CLASES PARA CONSULTA DE PRESINIESTROS
	  *************************************************************** */
	 
	 /* ***************************************************************
	  * INICIA DEFINICION DE CLASES PARA PRESINIESTROS AUTOMOVILES
	  *************************************************************** */

	 protected class ObtienePreAuto extends CustomStoredProcedure {

		 	protected ObtienePreAuto(DataSource dataSource) {
				super(dataSource, "PKG_PRESINIESTRO.OBTIENE_TPREAUTO_REG");

				declareParameter(new SqlParameter("p_nmfolacw_i",OracleTypes.VARCHAR));
				
				declareParameter(new SqlOutParameter("p_registro_o", OracleTypes.CURSOR, new PreAutoMapper()));
		        declareParameter(new SqlOutParameter("p_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("p_title_o", OracleTypes.VARCHAR));
				
				compile();

			}

		 	@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("p_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
		}

		protected class PreAutoMapper  implements RowMapper {
		        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		        	AutomovilVO automovil = new AutomovilVO();
		        	
		        	automovil.setFecha(rs.getString("FESINIES"));
		        	automovil.setPoliza(rs.getString("NMPOLIEX"));
		        	automovil.setCertificado(rs.getString("NMSITUEXT"));
		        	//automovil.set(rs.getString("CDUNIAGE"));
		        	automovil.setAseguradora(rs.getString("dsunieco"));
		        	//automovil.set(rs.getString("NMTELEF"));
		        	automovil.setAsegurado(rs.getString("DSASEGUR"));
		        	automovil.setTelefono1(rs.getString("NMTELASE"));
		        	automovil.setReportadoPor(rs.getString("DSREPORTA"));
		        	automovil.setTelefono3(rs.getString("NMTELREP"));
		        	automovil.setConductor(rs.getString("DSCONDUC"));
		        	automovil.setMarca(rs.getString("DSMARCA"));
		        	automovil.setModelo(rs.getString("DSMODELO"));
		        	automovil.setNumeroMotor(rs.getString("NMMOTOR"));
		        	automovil.setNumeroSerie(rs.getString("NMSERIE"));
		        	automovil.setNumeroPlacas(rs.getString("NMPLACAS"));
		        	automovil.setColor(rs.getString("DSCOLOR"));
		        	automovil.setLugarVehiculo(rs.getString("DSLUGAR"));
		        	automovil.setColonia(rs.getString("DSCOLONI"));
		        	automovil.setDelegacion(rs.getString("DSMUNICI"));
		        	automovil.setDescripcionAccidente(rs.getString("DSDESACC"));
		        	automovil.setFechaAccidente(rs.getString("FEACCID"));
		        	automovil.setHoraAccidente(rs.getString("NMHORAAC"));
		        	automovil.setTercero(rs.getString("DSTERCERO"));
		        	automovil.setTaller(rs.getString("DSTALLER"));
		        	automovil.setFechaReportada(rs.getString("FEREPASEG"));
		        	automovil.setHoraReportada(rs.getString("NMHORAREP"));
		        	automovil.setNumeroReporte(rs.getString("NMREPASE"));
		        	automovil.setFechaReporte(rs.getString("FEREPAON"));
		        	automovil.setHoraReporte(rs.getString("NMHORAAON"));
		        	//automovil.set(rs.getString("NMREPAON"));
		        	//automovil.set(rs.getString("DSDATOAD"));
		        	//automovil.set(rs.getString("DSBENEFE"));
		        	automovil.setReportoAPersonal(rs.getString("DSBITAREP"));
		        	//automovil.set(rs.getString("swrepase"));
		        	//automovil.set(rs.getString("DSOBSACC"));
		        	//automovil.set(rs.getString("DSCOBERT"));
		        	//automovil.set(rs.getString("CDPOSTAL"));
		        	//automovil.set(rs.getString("DSALTURA"));
		        	//automovil.set(rs.getString("DSCALLE"));
		        	//automovil.set(rs.getString("FEINGTAL"));
		        	automovil.setTelefono2(rs.getString("NMTELTAL"));
		        	automovil.setFolio(rs.getString("NMFOLACW"));
		        	//automovil.set(rs.getString("CDRAMO"));
		        	//automovil.set(rs.getString("DSRECREP"));
		        	//automovil.set(rs.getString("NMREPASEG")); // REPETIDO ARRIBA
		        	
		            return automovil;
		        }
		}

	 	protected class EjecutaGuardaTPreAuto extends CustomStoredProcedure {

			protected EjecutaGuardaTPreAuto(DataSource dataSource) {
				super(dataSource, "PKG_PRESINIESTRO.GUARDA_TPREAUTO");

				declareParameter(new SqlParameter("p_poliza_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmfolacw_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_cdpostal_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_cduniage_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsaltura_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsasegur_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsbenefe_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsbitarep_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dscalle_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dscobert_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dscoloni_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dscolor_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsconduc_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsdatoad_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsdesacc_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dslugar_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsmarca_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsmodelo_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsmunici_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsobsacc_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsreporta_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dstaller_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dstercero_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_feaccid_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_feingtal_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_ferepaon_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_ferepaseg_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_fesinies_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmhoraac_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmhoraaon_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmhorarep_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmmotor_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmplacas_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmpoliex_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmrepaon_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmrepase_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmserie_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmsituex_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmtelase_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmtelef_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmtelrep_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmteltal_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_swrepase_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_cdramo_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsrecrep_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmrepaseg",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_operacion_i",OracleTypes.VARCHAR));
				
				declareParameter(new SqlOutParameter("p_registro_o", OracleTypes.CURSOR, new GuardaTPreAutoMapper()));
		        declareParameter(new SqlOutParameter("p_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("p_title_o", OracleTypes.VARCHAR));
				
				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("p_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
		}
	 
	 
	 protected class GuardaTPreAutoMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	AutomovilVO automovil = new AutomovilVO();
	        	
	        	automovil.setPoliza(rs.getString("NMPOLIEX"));
	        	automovil.setCertificado(rs.getString("CERTIFICADO"));
	        	automovil.setAseguradora(rs.getString("ASEGURADORA"));
	        	automovil.setAsegurado(rs.getString("ASEGURADO"));
	        	automovil.setTelefono1(rs.getString("TELEFONO"));
	        	automovil.setMarca(rs.getString("MARCA"));
	        	automovil.setModelo(rs.getString("MODELO"));
	        	automovil.setNumeroSerie(rs.getString("SERIE"));
	        	automovil.setNumeroMotor(rs.getString("MOTOR"));
	        	automovil.setNumeroPlacas(rs.getString("PLACAS"));
	        	
	            return automovil;
	        }
    }
	 /* ***************************************************************
	  * TERMINA DEFINICION DE CLASES PARA PRESINIESTROS AUTOMOVILES
	  *************************************************************** */

	 
	 /**
	  *  INICIO DE CODIGO PARA PRESINIESTROS DE BENEFICIOS
	  */
	 
	 protected class ObtieneTramites extends CustomStoredProcedure {

			protected ObtieneTramites(DataSource dataSource) {

				super(dataSource, "PKG_PRESINIESTRO.P_TIPO_TRAMITE");
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaBaseObjectMapper("OTVALOR", "OTCLAVE")));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
		}
	 
	 	protected class ObtienePadecimientos extends CustomStoredProcedure {

			protected ObtienePadecimientos(DataSource dataSource) {

				super(dataSource, "PKG_PRESINIESTRO.P_PADECIMIENTOS");
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaBaseObjectMapper("OTVALOR", "OTCLAVE")));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
		        
				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
		}
	 	
	 	
	 	
	 	protected class EjecutaGuardaTPreBen extends CustomStoredProcedure {

			protected EjecutaGuardaTPreBen(DataSource dataSource) {
				super(dataSource, "PKG_PRESINIESTRO.GUARDA_TPREBEN");

				declareParameter(new SqlParameter("p_poliza_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_cdtiptra_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmfolacw_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmfolaona_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmsinaseg_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dspadecimiento_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsasegurado_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsobserv_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_cdusuario_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_feprigas_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsfactura_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dstramite_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmpoliex_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsrecrep_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_fereporte_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsacciden_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmhorarep_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmhoraacc_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_feaccid_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dslugar_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmtelrep_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsreporta_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsciaase_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dstitular_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_fepresin_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmsituext_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_cduniage_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_cdramo_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmsbsitext",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_operacion_i",OracleTypes.VARCHAR));
				
				declareParameter(new SqlOutParameter("p_nmfolacw_o", OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("p_registro_o", OracleTypes.CURSOR, new GuardaTPreBenMapper()));
		        declareParameter(new SqlOutParameter("p_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("p_title_o", OracleTypes.VARCHAR));
				
				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("p_registro_o");
	            wrapperResultados.setItemList(result);
	            
	            String folioPresiniestro = null;
				if(map.get("p_nmfolacw_o") != null) folioPresiniestro = map.get("p_nmfolacw_o").toString();
				else folioPresiniestro = "";
				
				wrapperResultados.setItemMap(new HashMap<String, Object>());
				wrapperResultados.getItemMap().put("NUMERO_FOLIO", folioPresiniestro);
				
	            return wrapperResultados;
			}
		}
	 
	 
	 protected class GuardaTPreBenMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	BeneficioVO beneficio = new BeneficioVO();
	        	
	        	beneficio.setCdUnieco(rs.getString("CDUNIECO"));
	        	beneficio.setCdRamo(rs.getString("CDRAMO"));
	        	beneficio.setCdTipoRamo(rs.getString("CDTIPRAM"));//beneficio.setCdTipoRamo(rs.getString("CDTIPORA"));
	        	logger.debug("PRUEBA TEMPORAL: El valor de cdtipora es (beneficios 2), para subclasificacion cdtiporam se tiene: "+beneficio.getCdTipoRamo());
	        	beneficio.setCdAseguradora(rs.getString("CDUNIAGE"));
	        	beneficio.setPoliza(rs.getString("NMPOLIZA"));
	        	beneficio.setPolizaExt(rs.getString("POLIZA"));
	        	beneficio.setInciso(rs.getString("INCISO"));
	        	beneficio.setSubinciso(rs.getString("SUBINCISO"));
	        	beneficio.setEmpresa(rs.getString("EMPRESA"));
	        	beneficio.setDsAseguradora(rs.getString("ASEGURADORA"));
	        	beneficio.setAsegurado(rs.getString("ASEGURADO"));
	        	beneficio.setTitular(rs.getString("CONTRATANTE"));
	        	beneficio.setDsRamo(rs.getString("DSRAMO"));
	        	
	            return beneficio;
	        }
	    }
	 
	 	protected class EjecutaGuardaTPreVGFAP extends CustomStoredProcedure {
		 
		 protected EjecutaGuardaTPreVGFAP(DataSource dataSource) {
			 super(dataSource, "PKG_PRESINIESTRO.GUARDA_TPREVGFAP");
			 
			 declareParameter(new SqlParameter("p_poliza_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_cdtiptra_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_nmfolacw_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_dspersona_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_dsempresa_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_dstitular_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_dsafectado_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_dsacciden_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_dsciaase_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_dslugar_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_dsrecrep_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_dsreporta_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_feaccid_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_fepresin_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_fereporte_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_nmhoraacc_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_nmhorarep_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_nmpoliex_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_nmtelrep_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_cduniage_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_nmsituext_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_cdramo_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_feprigas_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_nmsinaseg",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_operacion_i",OracleTypes.VARCHAR));

			 declareParameter(new SqlOutParameter("p_registro_o", OracleTypes.CURSOR, new GuardaTPreBenGasFunMapper()));
			 declareParameter(new SqlOutParameter("p_nmfolacw_o", OracleTypes.VARCHAR));
			 declareParameter(new SqlOutParameter("p_msg_id_o", OracleTypes.NUMERIC));
			 declareParameter(new SqlOutParameter("p_title_o", OracleTypes.VARCHAR));
			 
			 compile();
			 
		 }
		 
		 @SuppressWarnings("unchecked")
		 public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			 WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			 WrapperResultados wrapperResultados = mapper.build(map);
			 List result = (List) map.get("p_registro_o");
			 wrapperResultados.setItemList(result);
			 
			 String folioPresiniestro = null;
			 if(map.get("p_nmfolacw_o") != null) folioPresiniestro = map.get("p_nmfolacw_o").toString();
			 else folioPresiniestro = "";
			 
			 wrapperResultados.setItemMap(new HashMap<String, Object>());
			 wrapperResultados.getItemMap().put("NUMERO_FOLIO", folioPresiniestro);
			 
			 return wrapperResultados;
		 }
	 }
	 
	 
	 protected class GuardaTPreBenGasFunMapper  implements RowMapper {
		 public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			 BeneficioVO beneficio = new BeneficioVO();
			 
			 beneficio.setCdUnieco(rs.getString("CDUNIECO"));
			 beneficio.setCdRamo(rs.getString("CDRAMO"));
			 beneficio.setCdTipoRamo(rs.getString("CDTIPRAM"));/*beneficio.setCdTipoRamo(rs.getString("CDTIPORA"));*/ logger.debug("PRUEBA TEMPORAL: El valor de cdtipora es (beneficios 2), para subclasificacion cdtiporam se tiene: "+beneficio.getCdTipoRamo());  
			 beneficio.setCdAseguradora(rs.getString("CDUNIAGE"));
			 beneficio.setPoliza(rs.getString("NMPOLIZA"));
			 beneficio.setPolizaExt(rs.getString("NMPOLIEX"));
			 beneficio.setInciso(rs.getString("INCISO"));
			 	//beneficio.setSubinciso(rs.getString("SUBINCISO"));
			 beneficio.setEmpresa(rs.getString("DSELEMEN"));
			 beneficio.setDsAseguradora(rs.getString("ASEGURADORA"));
			 beneficio.setAsegurado(rs.getString("ASEGURADO"));
			 beneficio.setTitular(rs.getString("CONTRATANTE"));
			 beneficio.setDsRamo(rs.getString("DSRAMO"));
			 
			 beneficio.setCdCorporativo(rs.getString("CDELEMENTO"));
			 beneficio.setFecha(rs.getString("FECHA"));
			 
			 return beneficio;
		 }
	 }

	 	protected class ObtienePreBen extends CustomStoredProcedure {

			protected ObtienePreBen(DataSource dataSource) {
				super(dataSource, "PKG_PRESINIESTRO.OBTIENE_TPREBEN_REG");

				declareParameter(new SqlParameter("p_nmfolacw_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_cdramo_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmpoliza_i",OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new PreBenMapper()));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
				
				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
		}
	 
	 
	 protected class PreBenMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	BeneficioVO beneficio = new BeneficioVO();
	        	
	        	//beneficio.setCdUnieco(rs.getString("CDUNIECO"));
	        	beneficio.setFolio(rs.getString("NMFOLACW"));
	        	beneficio.setFolioAA(rs.getString("NMFOLAONA"));
	        	beneficio.setNoSiniestroAseg(rs.getString("NMSINASEG"));
	        	beneficio.setCdRamo(rs.getString("CDRAMO"));
	        	beneficio.setCdTipoRamo(rs.getString("CDTIPRAM"));//beneficio.setCdTipoRamo(rs.getString("CDTIPORA"));
	        	beneficio.setFecha(rs.getString("FEREPORTE"));
	        	beneficio.setPolizaExt(rs.getString("NMPOLIEX"));
	        	beneficio.setInciso(rs.getString("NMSITUEXT"));
	        	beneficio.setSubinciso(rs.getString("NMSBSITEXT"));
	        	beneficio.setDsAseguradora(rs.getString("DSCIAASE"));
	        	beneficio.setCdTipoTramite(rs.getString("CDTIPTRA"));
	        	beneficio.setFechaPrimerGasto(rs.getString("FEPRIGAS"));
	        	beneficio.setDsPadecimiento(rs.getString("DSPADECIMIENTO"));
	        	beneficio.setTitular(rs.getString("DSTITULAR"));
	        	beneficio.setAsegurado(rs.getString("DSASEGURADO"));
	        	beneficio.setReportadoPor(rs.getString("DSREPORTA"));
	        	beneficio.setTelefonoRep(rs.getString("NMTELREP"));
	        	beneficio.setDescripcionTramite(rs.getString("DSTRAMITE"));
	        	beneficio.setObservaciones(rs.getString("DSOBSERV"));
	        	
	            return beneficio;
	        }
	    }
	 
	 	protected class ObtienePreBenGasFun extends CustomStoredProcedure {
		 
		 protected ObtienePreBenGasFun(DataSource dataSource) {
			 super(dataSource, "PKG_PRESINIESTRO.OBTIENE_TPREVGFAP_REG");
			 
			 declareParameter(new SqlParameter("p_nmfolacw_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_cdramo_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlParameter("p_nmpoliza_i",OracleTypes.VARCHAR));
			 declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new PreBenGasFunMapper()));
			 declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			 declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			 
			 compile();
			 
		 }
		 
		 @SuppressWarnings("unchecked")
		 public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			 WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			 WrapperResultados wrapperResultados = mapper.build(map);
			 List result = (List) map.get("pv_registro_o");
			 wrapperResultados.setItemList(result);
			 return wrapperResultados;
		 }
	 }
	 
	 
	 protected class PreBenGasFunMapper  implements RowMapper {
		 public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			 BeneficioVO beneficio = new BeneficioVO();
			 
			 //beneficio.setCdUnieco(rs.getString("CDUNIECO"));
			 beneficio.setFolio(rs.getString("NMFOLACW"));
			 beneficio.setNoSiniestroAseg(rs.getString("NMSINASEG"));
			 beneficio.setCdRamo(rs.getString("CDRAMO"));
			 beneficio.setCdTipoRamo(rs.getString("CDTIPRAM"));//beneficio.setCdTipoRamo(rs.getString("CDTIPORA"));
			 beneficio.setFecha(rs.getString("FEREPORTE"));
			 beneficio.setPolizaExt(rs.getString("NMPOLIEX"));
			 beneficio.setInciso(rs.getString("NMSITUEXT"));
			 beneficio.setCdAseguradora(rs.getString("CDUNIAGE"));
			 beneficio.setDsAseguradora(rs.getString("DSCIAASEG"));
			 beneficio.setEmpresa(rs.getString("DSEMPRESA"));
			 beneficio.setCdCorporativo(rs.getString("CDELEMENTO"));
			 beneficio.setCdTipoTramite(rs.getString("CDTIPTRA"));
			 beneficio.setTitular(rs.getString("DSTITULAR"));
			 beneficio.setAsegurado(rs.getString("DSPERSONA"));
			 beneficio.setReportadoPor(rs.getString("DSREPORTA"));
			 beneficio.setTelefonoRep(rs.getString("NMTELREP"));
			 beneficio.setObservaciones(rs.getString("DSACCIDEN"));
			 
			 return beneficio;
		 }
	 }
	 
	 
	 
	 /*para documentos de beneficios*/
	 
	 	protected class ObtieneDocumentosPre extends CustomStoredProcedure {

			protected ObtieneDocumentosPre(DataSource dataSource) {
				super(dataSource, "PKG_PRESINIESTRO.P_OBTIENE_TVALODOC");

				declareParameter(new SqlParameter("pv_cdunieco",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdramo",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_estado",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_nmpoliza",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_nmfolacw",OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DocumentoMapper()));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
				
				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
		}
	 
	 
	 protected class DocumentoMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	DocumentoVO doc = new DocumentoVO();
	        	
	        	doc.setCdValoDoc(rs.getString("CDVALODOC"));
	        	doc.setCdTipDoc(rs.getString("CDTIPDOC"));
	        	doc.setDsTipDoc(rs.getString("DSTIPDOC"));
	        	doc.setCdUnica(rs.getString("CDUNICA"));
	        	doc.setAtr1(rs.getString("DESCRIP"));
	        	doc.setAtr2(rs.getString("MONTO"));
	        	doc.setAtr3(rs.getString("OTVALOR03"));
	        	doc.setAtr4(rs.getString("OTVALOR04"));
	        	doc.setAtr5(rs.getString("OTVALOR05"));
	        	doc.setAtr6(rs.getString("OTVALOR06"));
	        	doc.setAtr7(rs.getString("OTVALOR07"));
	        	doc.setAtr8(rs.getString("OTVALOR08"));
	        	doc.setAtr9(rs.getString("OTVALOR09"));
	        	doc.setAtr10(rs.getString("OTVALOR10"));
	        	doc.setAtr11(rs.getString("OTVALOR11"));
	        	doc.setAtr12(rs.getString("OTVALOR12"));
	        	doc.setAtr13(rs.getString("OTVALOR13"));
	        	doc.setAtr14(rs.getString("OTVALOR14"));
	        	doc.setAtr15(rs.getString("OTVALOR15"));
	        	doc.setAtr16(rs.getString("OTVALOR16"));
	        	doc.setAtr17(rs.getString("OTVALOR17"));
	        	doc.setAtr18(rs.getString("OTVALOR18"));
	        	doc.setAtr19(rs.getString("OTVALOR19"));
	        	doc.setAtr20(rs.getString("OTVALOR20"));
	        	doc.setAtr21(rs.getString("OTVALOR21"));
	        	doc.setAtr22(rs.getString("OTVALOR22"));
	        	doc.setAtr23(rs.getString("OTVALOR23"));
	        	doc.setAtr24(rs.getString("OTVALOR24"));
	        	doc.setAtr25(rs.getString("OTVALOR25"));
	        	doc.setAtr26(rs.getString("OTVALOR26"));
	        	doc.setAtr27(rs.getString("OTVALOR27"));
	        	doc.setAtr28(rs.getString("OTVALOR28"));
	        	doc.setAtr29(rs.getString("OTVALOR29"));
	        	doc.setAtr30(rs.getString("OTVALOR30"));
	        	doc.setAtr31(rs.getString("OTVALOR31"));
	        	doc.setAtr32(rs.getString("OTVALOR32"));
	        	doc.setAtr33(rs.getString("OTVALOR33"));
	        	doc.setAtr34(rs.getString("OTVALOR34"));
	        	doc.setAtr35(rs.getString("OTVALOR35"));
	        	doc.setAtr36(rs.getString("OTVALOR36"));
	        	doc.setAtr37(rs.getString("OTVALOR37"));
	        	doc.setAtr38(rs.getString("OTVALOR38"));
	        	doc.setAtr39(rs.getString("OTVALOR39"));
	        	doc.setAtr40(rs.getString("OTVALOR40"));
	        	doc.setAtr41(rs.getString("OTVALOR41"));
	        	doc.setAtr42(rs.getString("OTVALOR42"));
	        	doc.setAtr43(rs.getString("OTVALOR43"));
	        	doc.setAtr44(rs.getString("OTVALOR44"));
	        	doc.setAtr45(rs.getString("OTVALOR45"));
	        	doc.setAtr46(rs.getString("OTVALOR46"));
	        	doc.setAtr47(rs.getString("OTVALOR47"));
	        	doc.setAtr48(rs.getString("OTVALOR48"));
	        	doc.setAtr49(rs.getString("OTVALOR49"));
	        	doc.setAtr50(rs.getString("OTVALOR50"));
	        	
	            return doc;
	        }
	    }
	    
	 	protected class GuardaAtrsDocPre extends CustomStoredProcedure {

			protected GuardaAtrsDocPre(DataSource dataSource) {
				super(dataSource, "PKG_PRESINIESTRO.P_MOV_TVALODOC");
		        
				declareParameter(new SqlParameter("pv_cdunieco", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdramo", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_estado", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_nmpoliza", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdunica", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_nmsuplem", OracleTypes.VARCHAR));
				
				declareParameter(new SqlParameter("pv_cdgrupa", OracleTypes.VARCHAR));// se madna null
				
				declareParameter(new SqlParameter("pv_status", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdtipdoc", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_nmfolacw", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdvalodoc", OracleTypes.VARCHAR));
				
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
				declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
		        
				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            return mapper.build(map);
	        }
		}
	 	
	 	
	 	protected class EliminaDocPre extends CustomStoredProcedure {

			protected EliminaDocPre(DataSource dataSource) {
				super(dataSource, "PKG_PRESINIESTRO.P_BORRA_TVALODOC");
		        
				declareParameter(new SqlParameter("pv_cdramo", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdvalodoc", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_nmfolacw", OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
		        
				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            return mapper.build(map);
	        }
		}
	    
	 
	 /**
	  *  FIN DE CODIGO PARA PRESINIESTROS DE BENEFICIOS
	  */

	 /* ***************************************************************
	  * INICIA DEFINICION DE CLASES PARA PRESINIESTROS DAÑOS
	  *************************************************************** */

		 protected class ObtienePreDanio extends CustomStoredProcedure {

			 	protected ObtienePreDanio(DataSource dataSource) {
					super(dataSource, "PKG_PRESINIESTRO.OBTIENE_TPREDANIO_REG");

					declareParameter(new SqlParameter("p_nmfolacw_i",OracleTypes.VARCHAR));
					
					declareParameter(new SqlOutParameter("p_registro_o", OracleTypes.CURSOR, new PreDanioMapper()));
			        declareParameter(new SqlOutParameter("p_msg_id_o", OracleTypes.NUMERIC));
			        declareParameter(new SqlOutParameter("p_title_o", OracleTypes.VARCHAR));
					
					compile();

				}

			 	@SuppressWarnings("unchecked")
				public WrapperResultados mapWrapperResultados(Map map) throws Exception {
					WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
		            WrapperResultados wrapperResultados = mapper.build(map);
		            List result = (List) map.get("p_registro_o");
		            wrapperResultados.setItemList(result);
		            return wrapperResultados;
				}
			}

			protected class PreDanioMapper  implements RowMapper {
			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	DanoVO dano = new DanoVO();
			        	
			        	dano.setFolio(rs.getString("NMFOLACW"));
			        	dano.setProducto(rs.getString("DSRAMO"));
			        	dano.setFecha(rs.getString("FEREPORTE"));
			        	dano.setPoliza(rs.getString("NMPOLIEX"));
			        	dano.setInciso(rs.getString("NMSITUEXT"));
			        	dano.setAseguradora(rs.getString("DSUNIECO"));
			        	dano.setRamo(rs.getString("DSTIPRAM"));
			        	dano.setNombreAsegurado(rs.getString("DSASEGUR"));
			        	dano.setPersonaRecibeReporte(rs.getString("DSRECREP"));
			        	dano.setEmbarqueBienesDanados(rs.getString("DSBIENES"));
			        	dano.setDescripcionDano(rs.getString("DSACCIDENTE"));
			        	dano.setFechaDano(rs.getString("FESINIES"));
			        	dano.setLugarBienesAfectados(rs.getString("DSLUGBIE"));
			        	dano.setPersonaEntrevista(rs.getString("DSENTREV"));
			        	dano.setTelefono1(rs.getString("NMTELEFONO"));
			        	dano.setEstimacionDano(rs.getString("NMESTIMA"));
			        	dano.setPersonaReporto(rs.getString("DSREPORTE"));
			        	dano.setTelefono2(rs.getString("NMTELREP"));

			            return dano;
			        }
			}
	 	
	 	protected class EjecutaGuardaTPreDanio extends CustomStoredProcedure {

			protected EjecutaGuardaTPreDanio(DataSource dataSource) {
				super(dataSource, "PKG_PRESINIESTRO.GUARDA_TPREDANIO");

				declareParameter(new SqlParameter("p_poliza_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_fereporte_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmpoliex_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmsituext_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_cdramo_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_cduniage_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsrecrep_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsbienes_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsaccidente_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_fesinies_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dslugbie_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsentrev_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsobserva_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmtelefono_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsestiobs_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsreporte_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmtelrep_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmestima_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmrepaon_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsdatoad_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsbenefe_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsbitarep_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dscobert_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmhorarep_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmhoraacc_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_feaccid_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_cdpostal_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dslocal_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsaltura_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dscalle_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsasegur_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_nmfolacw_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dstipram_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_dsperrecibe_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("p_operacion_i",OracleTypes.VARCHAR));
				
				declareParameter(new SqlOutParameter("p_registro_o", OracleTypes.CURSOR, new GuardaTPreDanioMapper()));
		        declareParameter(new SqlOutParameter("p_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("p_title_o", OracleTypes.VARCHAR));
				
				compile();

			}

			@SuppressWarnings("unchecked")
			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("p_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
		}
	 
	 
	 protected class GuardaTPreDanioMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	DanoVO dano = new DanoVO();
	        	
	        	dano.setProducto(rs.getString("DSRAMO"));
	        	dano.setPoliza(rs.getString("NMPOLIEX"));
	        	dano.setInciso(rs.getString("INCISO"));
	        	dano.setAseguradora(rs.getString("ASEGURADORA"));
	        	dano.setRamo(rs.getString("DSTIPRAM"));
	        	dano.setNombreAsegurado(rs.getString("ASEGURADO"));
	        	
	            return dano;
	        }
    }

	 /* ***************************************************************
	  * TERMINA DEFINICION DE CLASES PARA PRESINIESTROS DAÑOS
	  *************************************************************** */
	 
}
