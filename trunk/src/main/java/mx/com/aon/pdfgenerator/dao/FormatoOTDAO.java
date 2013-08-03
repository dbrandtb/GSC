package mx.com.aon.pdfgenerator.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class FormatoOTDAO extends AbstractDAO {

	private static Logger logger = Logger.getLogger(FormatoOTDAO.class);

	protected void initDao() throws Exception {

		addStoredProcedure("DATOS_POLIZA", new ObtenerDatosPoliza(
				getDataSource()));
		addStoredProcedure("DATOS_RIESGOS", new ObtenerDatosRiesgos(
				getDataSource()));
		addStoredProcedure("COBERTURAS_AMPARADAS",
				new ObtenerCoberturasAmparadas(getDataSource()));
		addStoredProcedure("COSTO_SEGURO", new ObtenerCostoSeguro(
				getDataSource()));
		addStoredProcedure("NOMBRE_PDF", new NombrePdf(
				getDataSource()));
	}

	protected class ObtenerDatosPoliza extends CustomStoredProcedure {

		protected ObtenerDatosPoliza(DataSource dataSource) {
			super(dataSource, "PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_ENCABEZA");
			
			declareParameter(new SqlParameter("pv_cdunieco_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_registro_o",
					OracleTypes.CURSOR, new DatosEncabezadoMapper()));
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

	protected class ObtenerDatosRiesgos extends CustomStoredProcedure {

		protected ObtenerDatosRiesgos(DataSource dataSource) {
			super(dataSource, "PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_RIESGO");

			declareParameter(new SqlParameter("pv_cdunieco_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_registro_o",
					OracleTypes.CURSOR, new DatosPolizaMapper()));
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

	protected class ObtenerCoberturasAmparadas extends CustomStoredProcedure {

		protected ObtenerCoberturasAmparadas(DataSource dataSource) {
			super(dataSource, "PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_COBERTUR");

			declareParameter(new SqlParameter("pv_cdunieco_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_registro_o",
					OracleTypes.CURSOR, new DatosCoberturaMapper()));
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

	protected class ObtenerCostoSeguro extends CustomStoredProcedure {

		protected ObtenerCostoSeguro(DataSource dataSource) {
			super(dataSource, "PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_PRIMA");

			declareParameter(new SqlParameter("pv_cdunieco_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_registro_o",
					OracleTypes.CURSOR, new DatosPrimaMapper()));
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
	
	
	protected class NombrePdf extends CustomStoredProcedure {

		protected NombrePdf(DataSource dataSource) {
			super(dataSource, "PKG_CARATULAS2.P_GUARDA_TCOLAS");

			declareParameter(new SqlParameter("pv_cdunieco_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",
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
			return wrapperResultados;
		}
	}

	protected class DatosPolizaMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString(2);
		}
	}

	protected class DatosEncabezadoMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			List<String> lista = new ArrayList<String>();
			
			lista.add(rs.getString(1));
			lista.add(rs.getString(2));
			lista.add(rs.getString(3));
			lista.add(rs.getString(4));
			lista.add(rs.getString(5));
			lista.add(rs.getString(6));
			lista.add(rs.getString(7));
			lista.add(rs.getString(8));
			//lista.add(rs.getString(9));
			
			return lista;
		}
	}

	protected class DatosCoberturaMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Map<String, String> mapa = new HashMap<String, String>();

			mapa.put("COBERTURA", rs.getString(1));
			mapa.put("DEDUCIBLE", rs.getString(2));
			mapa.put("SUMA_ASEGURADA", rs.getString(3));

			return mapa;
		}
	}

	protected class DatosPrimaMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString(1);
		}
	}
}