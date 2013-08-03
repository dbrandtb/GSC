package mx.com.aon.catweb.configuracion.producto.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.catweb.configuracion.producto.expresiones.model.RamaVO;
import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class ProductoDAO extends AbstractDAO {

	private static Logger logger = Logger.getLogger(ProductoDAO.class);

	public static final String CLONAR_PRODUCTO = "CLONAR_PRODUCTO";
	public static final String GENERAR_PRODUCTO = "GENERAR_PRODUCTO";
	public static final String INSERTAR_PRODUCTO = "INSERTAR_PRODUCTO";
	public static final String BUSCAR_PRODUCTOS = "BUSCAR_PRODUCTOS";
	public static final String AGREGAR_CLAUSULA = "AGREGAR_CLAUSULA";	
	
	protected void initDao() throws Exception {
		logger.info("Entrado a init...");
		addStoredProcedure(CLONAR_PRODUCTO, new ClonarProducto(getDataSource()));
		addStoredProcedure(GENERAR_PRODUCTO, new GenerarProducto(getDataSource()));
		addStoredProcedure(INSERTAR_PRODUCTO, new InsertarProcucto(getDataSource()));
		addStoredProcedure(BUSCAR_PRODUCTOS, new BuscarProductos(getDataSource()));
		addStoredProcedure(AGREGAR_CLAUSULA, new AgregarClausula(getDataSource()));
	}

	protected class ClonarProducto extends CustomStoredProcedure {

		protected ClonarProducto(DataSource dataSource) {
			super(dataSource, "PKG_PRODUCTO.p_clona_local");

			declareParameter(new SqlParameter("pv_cdramo_ori_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_cdramo_des_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_dsramo_des_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_lang_code_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_username_i",
					OracleTypes.VARCHAR));

			declareParameter(new SqlOutParameter("pv_msg_id_o",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",
					OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_dsmensaje_o",
					OracleTypes.VARCHAR));
			compile();

		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			
			WrapperResultados wrapperResultados = mapper.build(map);
			return wrapperResultados;
		}
	}

	protected class InsertarProcucto extends CustomStoredProcedure {
		
		protected InsertarProcucto(DataSource dataSource) {
			
			super(dataSource, "PKG_WIZARD.p_insertproduct");
			
			declareParameter(new SqlParameter("pv_cdramo_i",	OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_dsramo_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipora_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipram_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swsuscri_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swinflac_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swinclnt_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ttipcalc_i",	OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_swrescat_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_mmrescat_i",	OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_swreduci_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_mmreduci_i",	OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_swrehabi_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_mmbenefi_i",	OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_swantici_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_mmantici_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swbenefi_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swpriper_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swsinomn_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swfronti_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swreaseg_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swsinsit_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swtarifa_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swreauto_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swpriuni_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swindper_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swpoldec_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swcoaseg_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swprecar_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swtipo_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swreserv_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swcmptdi_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmprerec_i",	OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_swdipaco_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swsubinc_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_traza_i",		OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtippol_i",	OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdcaltippol_i",OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_dslinea_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swcancel_i",	OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swendoso_i",	OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_cdramo_o",	OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_msg_id_o",	OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",	OracleTypes.VARCHAR));
			compile();
		}
		
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados resultados = mapper.build(map);
			
			resultados.setItemMap(new HashMap());
			logger.debug("pv_cdramo_o: " + map.get("pv_cdramo_o"));
			resultados.getItemMap().put("pv_cdramo_o", map.get("pv_cdramo_o") + "");
			
			return resultados;
			
		}
	}
	
	protected class AgregarClausula extends CustomStoredProcedure {
		
		protected AgregarClausula(DataSource dataSource) {
			
			super(dataSource, "PKG_WIZARD.p_insertclausul");
			
			declareParameter(new SqlParameter("P_CDCLAUSU", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("P_DSCLAUSU", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("P_NMLINEA", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("P_DSLINEA", OracleTypes.CLOB));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));	
			compile();
		}
		
		@SuppressWarnings("unchecked")
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados resultados = mapper.build(map);
			
			//resultados.setItemMap(new HashMap());
			//logger.debug("pv_msg_id_o: " + map.get("pv_msg_id_o"));
			//logger.debug("pv_title_o: " + map.get("pv_title_o"));
			//resultados.getItemMap().put("pv_msg_id_o", map.get("pv_msg_id_o") + "");
			//resultados.getItemMap().put("pv_title_o", map.get("pv_title_o") + "");
			
			return resultados;
			
		}
	}
	
	protected class GenerarProducto extends CustomStoredProcedure {
		protected GenerarProducto(DataSource dataSource) {
			super(dataSource, "P_EXEC_SIGSGTTA");

			declareParameter(new SqlParameter("pv_cdramo_i",
					OracleTypes.NUMERIC));

			declareParameter(new SqlOutParameter("pv_msg_id_o",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",
					OracleTypes.VARCHAR));
			compile();

		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados wrapperResultados = mapper.build(map);
			//List result = (List) map.get("pv_registro_o");
			//wrapperResultados.setItemList(result);
			return wrapperResultados;
		}
	}
	
	protected class BuscarProductos extends CustomStoredProcedure {
		protected BuscarProductos(DataSource dataSource) {
			super(dataSource, "PKG_WIZARD.P_LOADPROD");
			
			declareParameter(new SqlOutParameter("pv_registro_o",
					OracleTypes.CURSOR,new ListaProductos()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",
					OracleTypes.VARCHAR));
			
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
	
	protected class ListaProductos implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			RamaVO valores = new RamaVO();
			
			valores.setCodigoObjeto(rs.getString(1));
			valores.setText(rs.getString(2));
			
			return valores;
		}
	}
}
