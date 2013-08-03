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

public class SumaAseguradaDAO extends AbstractDAO{
	
    private static Logger logger = Logger.getLogger(SumaAseguradaDAO.class);


    protected void initDao() throws Exception {
    	addStoredProcedure("AGREGAR_SUMA_ASEGURADA_INCISO_JDBC_TMPL",new GuardaSumaAseguradaInciso(getDataSource()));
    }

    
    protected class GuardaSumaAseguradaInciso extends CustomStoredProcedure {

        protected GuardaSumaAseguradaInciso(DataSource dataSource) {
            super(dataSource, "PKG_WIZARD.P_INSERTA_SUMA_ASEGURADA");
            declareParameter(new SqlParameter("PV_CDRAMO_I",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("PV_CDCAPITA_I",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("PV_CDTIPCAP_I",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("PV_DSCAPITA_I",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("PV_CDGARANT_I",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("PV_CDPRESEN_I",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("PV_CDTIPSIT_I",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("PV_OTTABVAL_I",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("PV_SWREAUTO_I",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdexpdef_i",OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("PV_CDCAPITA_O", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
            compile();
          }

        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            wrapperResultados.setResultado((map.get("PV_CDCAPITA_O")).toString());
            if(logger.isDebugEnabled()){
            	logger.debug("P_INSERTA_SUMA_ASEGURADA PV_CDCAPITA_O=" + map.get("PV_CDCAPITA_O"));
            	logger.debug("P_INSERTA_SUMA_ASEGURADA PV_MSG_ID_O=" + map.get("PV_MSG_ID_O"));
            	logger.debug("P_INSERTA_SUMA_ASEGURADA PV_TITLE_O=" + map.get("PV_TITLE_O"));
            }
            return wrapperResultados;
        }
    }
    
    
}
