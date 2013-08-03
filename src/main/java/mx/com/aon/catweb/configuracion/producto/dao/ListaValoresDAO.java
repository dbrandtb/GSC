package mx.com.aon.catweb.configuracion.producto.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.catweb.configuracion.producto.model.ListaDeValoresVO;
import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.tablaCincoClaves.model.DescripcionCincoClavesVO;
import mx.com.aon.catweb.configuracion.producto.util.WizardUtils;
import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class ListaValoresDAO extends AbstractDAO {

	private static Logger logger = Logger.getLogger(ListaValoresDAO.class);

	public static final String OBTIENE_CATALOGO_LISTA_VALORES = "OBTIENE_CATALOGO_LISTA_VALORES";
	public static final String OBTIENE_LISTA_CARGA_MANUAL = "OBTIENE_LISTA_CARGA_MANUAL";
	
	
	protected void initDao() throws Exception {
		logger.info("Entrado a init...");
		addStoredProcedure(OBTIENE_CATALOGO_LISTA_VALORES, new ObtenerCatalogoListaValores(getDataSource()));
		addStoredProcedure(OBTIENE_LISTA_CARGA_MANUAL, new ObtenerListaCargaManual(getDataSource()));
	}

	protected class ObtenerCatalogoListaValores extends CustomStoredProcedure {

		protected ObtenerCatalogoListaValores(DataSource dataSource) {
			super(dataSource, "PKG_WIZARD.LOAD_TABLA_APOYO");

			declareParameter(new SqlParameter("PV_OTTIPOTB_I",
					OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("LCUR_REGISTRO_I", OracleTypes.CURSOR, new ListaCatalogoMapper()));
	        declareParameter(new SqlOutParameter("PO_MSG_ID", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
			
			compile();

		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("LCUR_REGISTRO_I");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
		}
	}
	
	protected class ObtenerListaCargaManual extends CustomStoredProcedure {

		protected ObtenerListaCargaManual(DataSource dataSource) {
			super(dataSource, "PKG_TABAPOYO.P_OBTENER_VALORATT_TODO");

			declareParameter(new SqlParameter("PI_TABLA", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PI_ATRIB_DESC", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PI_NOM_CLAVE01", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PI_NOM_CLAVE02", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PI_VAL_CLAVE02", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PI_NOM_CLAVE03", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PI_VAL_CLAVE03", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PI_NOM_CLAVE04", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PI_VAL_CLAVE04", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PI_NOM_CLAVE05", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PI_VAL_CLAVE05", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("PV_REGISTRO_O", OracleTypes.CURSOR, new ListaCargaManualMapper()));
	        declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
			
			compile();

		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("PV_REGISTRO_O");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
		}
	}

    protected class ListaCatalogoMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ListaDeValoresVO valorVO = new ListaDeValoresVO();
        	valorVO.setCdCatalogo1(rs.getString("CDTABLA"));
        	valorVO.setDsCatalogo1(rs.getString("DSTABLA"));
        	
            return valorVO;
        }
    }
    protected class ListaCargaManualMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	LlaveValorVO valorVO = new LlaveValorVO();
        	valorVO.setKey(rs.getString("OTCLAVE"));
        	valorVO.setValue(rs.getString("OTVALOR"));
        	
            return valorVO;
        }
    }
    
}
