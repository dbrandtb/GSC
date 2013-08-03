package mx.com.aon.portal.dao;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.portal.model.AtributosVariablesInstPagoVO;
import mx.com.aon.portal.model.InstrumentoPagoAtributosVO;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class InstrumentoPagoDAO extends AbstractDAO {
	 private static Logger logger = Logger.getLogger(InstrumentoPagoDAO.class);
	 
	 public static final String OBTIENE_INSTRUMENTOS_PAGO_CLIENTE = "OBTIENE_INSTRUMENTOS_PAGO_CLIENTE";
	 public static final String GUARDA_INSTRUMENTO_PAGO_CLIENTE = "GUARDA_INSTRUMENTO_PAGO_CLIENTE";
	 public static final String BORRA_INSTRUMENTO_PAGO_CLIENTE = "BORRA_INSTRUMENTO_PAGO_CLIENTE";
	 
	 /*
	  * PARA LA VENTANA DE CONFIGURACION DE ATRIBUTOS VARIABLES POR INSTRUMENTO DE PAGO
	  */
	 
	 public static final String OBTIENE_ATRIBUTOS_INSTRUMENTO_PAGO = "OBTIENE_ATRIBUTOS_INSTRUMENTO_PAGO";
	 public static final String GUARDA_ATRIBUTO_INSTRUMENTO_PAGO = "GUARDA_ATRIBUTO_INSTRUMENTO_PAGO";
	 public static final String BORRA_ATRIBUTO_INSTRUMENTO_PAGO = "BORRA_ATRIBUTO_INSTRUMENTO_PAGO";
	 
	 /*
	  * PARA GUARDAR LOS ATRIBUTOS YA ASIGNADOS A UNA POLIZA EN EMISION
	  * */
	 public static final String GUARDA_ATRIBUTOS_INSPAGO_POLIZA = "GUARDA_ATRIBUTOS_INSPAGO_POLIZA";
	 
	 
	 protected void initDao() throws Exception {
	       addStoredProcedure(OBTIENE_INSTRUMENTOS_PAGO_CLIENTE, new ObtenerInstrumentosPagoCliente(getDataSource()));
	       addStoredProcedure(GUARDA_INSTRUMENTO_PAGO_CLIENTE, new AgregarInstrumentoPagoCliente(getDataSource()));
	       addStoredProcedure(BORRA_INSTRUMENTO_PAGO_CLIENTE, new BorrarInstrumentoPagoCliente(getDataSource()));
	       
	       addStoredProcedure(OBTIENE_ATRIBUTOS_INSTRUMENTO_PAGO, new ObtenerAtributosInstrumentoPago(getDataSource()));
	       addStoredProcedure(GUARDA_ATRIBUTO_INSTRUMENTO_PAGO, new GuardarAtributoInstrumentoPago(getDataSource()));
	       addStoredProcedure(BORRA_ATRIBUTO_INSTRUMENTO_PAGO, new BorrarAtributoInstrumentoPago(getDataSource()));
	       
	       addStoredProcedure(GUARDA_ATRIBUTOS_INSPAGO_POLIZA, new GuardarAtributosInsPagPol(getDataSource()));
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

			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
				WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            WrapperResultados wrapperResultados = mapper.build(map);
	            List result = (List) map.get("pv_registro_o");
	            wrapperResultados.setItemList(result);
	            return wrapperResultados;
			}
		}
	 
	 	protected class AgregarInstrumentoPagoCliente extends CustomStoredProcedure {

			protected AgregarInstrumentoPagoCliente(DataSource dataSource) {
				super(dataSource, "PKG_INSTPAGO.P_GUARDA_INSTPAGO");
				declareParameter(new SqlParameter("pv_cdinscte_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdelemento_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdforpag_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cduniage_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdramo_i",OracleTypes.VARCHAR));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
				
				compile();

			}

			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            return mapper.build(map);
	        }
		}
	 	
	 	
	 	protected class BorrarInstrumentoPagoCliente extends CustomStoredProcedure {

			protected BorrarInstrumentoPagoCliente(DataSource dataSource) {
				super(dataSource, "PKG_INSTPAGO.P_BORRA_INSTPAGO");
				declareParameter(new SqlParameter("pv_cdinscte_i",OracleTypes.VARCHAR));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
				
				compile();

			}

			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            return mapper.build(map);
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
	 	
	 	/*
	 	 * PARA MANEJO DE LOS ATRIBUTOS PARA LOS INSTRUMENTOS DE PAGO 
	 	 */
	 	
	 	
	 	protected class ObtenerAtributosInstrumentoPago extends CustomStoredProcedure {

			protected ObtenerAtributosInstrumentoPago(DataSource dataSource) {
				super(dataSource, "PKG_INSTPAGO.P_OBTIENE_ATRIBUTO_REG");

				declareParameter(new SqlParameter("pv_cdunica_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_dsatribu_i",OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListaAtributosInstrumentoPagoMapper()));
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
	 	
	 	protected class GuardarAtributoInstrumentoPago extends CustomStoredProcedure {

			protected GuardarAtributoInstrumentoPago(DataSource dataSource) {
				super(dataSource, "PKG_INSTPAGO.P_GUARDA_ATRIBUTO");
		        
				declareParameter(new SqlParameter("pv_cdunica_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdatribu_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_swformat_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_nmlmin_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_nmlmax_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_dsatribu_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_ottabval_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_swemisi_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_swemiobl_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_swemiupd_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_swendoso_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_swendobl_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_swendupd_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdatribu_padre_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_nmorden_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_nmagrupa_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdexpress_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdcondicvis_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_swdatcom_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_swcomobl_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_swcomupd_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_swlegend_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_dslegend_i", OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
		        
				compile();

			}

			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            return mapper.build(map);
	        }
		}
	 	
	 	
	 	protected class BorrarAtributoInstrumentoPago extends CustomStoredProcedure {

			protected BorrarAtributoInstrumentoPago(DataSource dataSource) {
				super(dataSource, "PKG_INSTPAGO.P_BORRA_ATRIBUTO");
				declareParameter(new SqlParameter("pv_cdunica_i",OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdatribu_i",OracleTypes.VARCHAR));
		        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
		        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
				
				compile();

			}

			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            return mapper.build(map);
	        }
		}
	 	
	 	
	 	
	 	protected class ListaAtributosInstrumentoPagoMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	AtributosVariablesInstPagoVO atrVO = new AtributosVariablesInstPagoVO();
	        	atrVO.setCdInsCte(rs.getString("CDUNICA"));
	        	atrVO.setCdAtribu(rs.getString("CDATRIBU"));
	        	atrVO.setDsAtribu(rs.getString("DSATRIBU"));
	        	atrVO.setSwformat(rs.getString("SWFORMAT"));
	        	atrVO.setDsFormat(rs.getString("DSFORMATO"));
	        	atrVO.setNmMin(rs.getString("NMLMIN"));
	        	atrVO.setNmMax(rs.getString("NMLMAX"));
	        	atrVO.setCdTabla(rs.getString("OTTABVAL"));
	        	atrVO.setDsTabla(rs.getString("dstabla"));
	        	//atrVO.setNmTabla(rs.getString("XXXXXXX"));
	        	atrVO.setSwemisi(rs.getString("SWEMISI"));
	        	atrVO.setSwemiobl(rs.getString("SWEMIOBL"));
	        	atrVO.setSwemiupd(rs.getString("SWEMIUPD"));
	        	atrVO.setSwendoso(rs.getString("SWENDOSO"));
	        	atrVO.setSwendobl(rs.getString("SWENDOBL"));
	        	atrVO.setSwendupd(rs.getString("SWENDUPD"));
	        	atrVO.setCdatribu_padre(rs.getString("CDATRIBU_PADRE"));
	        	atrVO.setNmorden(rs.getString("NMORDEN"));
	        	atrVO.setNmagrupa(rs.getString("NMAGRUPA"));
	        	atrVO.setCdexpress(rs.getString("CDEXPRESS"));
	        	atrVO.setCdcondicvis(rs.getString("CDCONDICVIS"));
	        	atrVO.setSwdatcom(rs.getString("SWDATCOM"));
	        	atrVO.setSwcomobl(rs.getString("SWCOMOBL"));
	        	atrVO.setSwcomupd(rs.getString("SWCOMUPD"));
	        	atrVO.setSwlegend(rs.getString("SWLEGEND"));
	        	atrVO.setDslegend(rs.getString("DSLEGEND"));
	        	
	            return atrVO;
	        }
	    }
	 	
	 	
	 	/*
	 	 * PARA EL GUARDADO DE LOS ATRIBUTOS A LA POLIZA
	 	 * */
	 	
	 	protected class GuardarAtributosInsPagPol extends CustomStoredProcedure {

			protected GuardarAtributosInsPagPol(DataSource dataSource) {
				super(dataSource, "PKG_INSTPAGO.P_MOV_TVALOIPA");
		        
				declareParameter(new SqlParameter("pv_cdunieco", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdramo", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_estado", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_nmpoliza", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdunica", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_nmsuplem", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdgrupa", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_status", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdforpag", OracleTypes.VARCHAR));
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

			public WrapperResultados mapWrapperResultados(Map map) throws Exception {
	            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
	            return mapper.build(map);
	        }
		}

	 	
	
}
