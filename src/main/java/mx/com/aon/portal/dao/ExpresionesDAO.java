package mx.com.aon.portal.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;

import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.portal.util.WrapperResultados;

public class ExpresionesDAO extends AbstractDAO{
	
	private static Logger logger = Logger.getLogger(ExpresionesDAO.class);
	
	public static final String OBTIENE_TABLA_CINCO_CLAVES = "OBTIENE_TABLA_CINCO_CLAVES";
	
	protected void initDao() throws Exception {
		addStoredProcedure( OBTIENE_TABLA_CINCO_CLAVES, new BuscarValoresTablas(getDataSource()));
	}
	
	protected class BuscarValoresTablas extends CustomStoredProcedure {
		
		protected BuscarValoresTablas(DataSource dataSource) {
			super(dataSource, "PKG_LISTAS.P_TABLAS");
			
			logger.debug("%%% Entro a método BuscarValoresTablas sin parámetros para ejecutar PKG_LISTAS.P_TABLAS %%%");
		
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ValoresTablaMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
        
		@SuppressWarnings("unchecked")
        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List<LlaveValorVO> result = (List<LlaveValorVO>) map.get("pv_registro_o");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
        }
		
	}

    protected class ValoresTablaMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	LlaveValorVO llaveValorVO = new LlaveValorVO();
        	
        	llaveValorVO.setKey( 	rs.getString("CDTABLA")	);
        	llaveValorVO.setValue(  rs.getString("TABLA")   );
        	llaveValorVO.setNick(	rs.getString("NMTABLA")	);
        	
            return llaveValorVO;
        }
    }

}
