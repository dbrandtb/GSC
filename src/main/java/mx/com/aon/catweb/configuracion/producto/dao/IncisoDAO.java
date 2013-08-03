package mx.com.aon.catweb.configuracion.producto.dao;

import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class IncisoDAO extends AbstractDAO {

	private static Logger logger = Logger.getLogger(IncisoDAO.class);
	
	public static final String AGREGAR_INCISO = "AGREGAR_INCISO";
	
	protected void initDao() throws Exception {
		logger.info("Entrado a init en IncisoDAO...");
		addStoredProcedure(AGREGAR_INCISO, new AgregarInciso(getDataSource()));
	}
	
	protected class AgregarInciso extends CustomStoredProcedure {
		
		protected AgregarInciso(DataSource dataSource) {
			super(dataSource, "PKG_WIZARD.p_insertinciso");
			
			declareParameter(new SqlParameter("P_CDTIPSIT", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("P_DSTIPSIT", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("P_SWSITDEC", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("PO_MSG_ID", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
			
			compile();
		}
		
		@SuppressWarnings("unchecked")
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              if (map.get("PO_MSG_ID") != null) {
            	  wrapperResultados.setMsgId( (map.get("PO_MSG_ID")).toString() );
              }
              if (map.get("PV_TITLE_O") != null) {
            	  wrapperResultados.setMsgTitle( (String)map.get("PV_TITLE_O") );
              }
              
              return wrapperResultados;
          }
	}
	
}
