package mx.com.aon.catweb.configuracion.producto.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

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

public class TablaCincoClavesDAO extends AbstractDAO {

	private static Logger logger = Logger.getLogger(TablaCincoClavesDAO.class);

	public static final String OBTIENE_VALORES_CLAVE = "OBTIENE_VALORES_CLAVE";
	public static final String BORRA_VALORES_CLAVE = "BORRA_VALORES_CLAVE";
	
	protected void initDao() throws Exception {
		logger.info("Entrado a init...");
		addStoredProcedure(OBTIENE_VALORES_CLAVE, new ObtenerValoresClave(getDataSource()));
		addStoredProcedure(BORRA_VALORES_CLAVE, new BorraValoresClave(getDataSource()));
	}

	protected class ObtenerValoresClave extends CustomStoredProcedure {

		protected ObtenerValoresClave(DataSource dataSource) {
			super(dataSource, "PKG_TABAPOYO.P_OBTIENE_VALORES_CLAVES");

			declareParameter(new SqlParameter("PV_NMTABLA_I",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ValoresClaveMapper()));
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
	
	protected class BorraValoresClave extends CustomStoredProcedure {

		protected BorraValoresClave(DataSource dataSource) {
			super(dataSource, "PKG_TABAPOYO.P_BORRA_VALORATT");

			declareParameter(new SqlParameter("pi_tabla", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pi_clave01", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pi_clave02", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pi_clave03", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pi_clave04", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pi_clave05", OracleTypes.VARCHAR));

	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			
			compile();

		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
	}

    protected class ValoresClaveMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DescripcionCincoClavesVO clavesVO = new DescripcionCincoClavesVO();
        	clavesVO.setDescripcionClave1(rs.getString("OTCLAVE1"));
        	clavesVO.setDescripcionClave2(rs.getString("OTCLAVE2"));
        	clavesVO.setDescripcionClave3(rs.getString("OTCLAVE3"));
        	clavesVO.setDescripcionClave4(rs.getString("OTCLAVE4"));
        	clavesVO.setDescripcionClave5(rs.getString("OTCLAVE5"));
        	try {
				clavesVO.setFechaDesde(WizardUtils.parseDateBaseCincoClaves(rs.getString("FEDESDE")));
				clavesVO.setFechaHasta(WizardUtils.parseDateBaseCincoClaves(rs.getString("FEHASTA")));
			} catch (ParseException e) {
				logger.error("ERROR al parsear las fechas para la tabla de cinco claves", e);
				clavesVO.setFechaDesde(rs.getString("FEDESDE"));
				clavesVO.setFechaHasta(rs.getString("FEHASTA"));
			}
        	
        	
            return clavesVO;
        }
    }
    
}
