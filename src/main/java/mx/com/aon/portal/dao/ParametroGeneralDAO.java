package mx.com.aon.portal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class ParametroGeneralDAO extends AbstractDAO {
	
	private static Logger logger = Logger.getLogger(ParametroGeneralDAO.class);

	public static final String BUSCAR_PARAMETROS = "BUSCAR_PARAMETROS";
	
    protected void initDao() throws Exception {
        logger.info("Entrando al init");
        addStoredProcedure(BUSCAR_PARAMETROS, new BuscarParametros(getDataSource()));
    }

    protected class BuscarParametros extends CustomStoredProcedure {
    	protected BuscarParametros(DataSource dataSource) {
    		
    		super(dataSource, "Pkg_Utility.P_GET_TPARAGEN_SELECTIVE");
            
            declareParameter(new SqlParameter("pi_nbparam", OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("pi_valor", OracleTypes.VARCHAR));
            
            compile();
    		
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            wrapperResultados.setItemMap(new HashMap<String, Object>());
            wrapperResultados.getItemMap().put("pi_valor", map.get("pi_valor"));  
            return wrapperResultados;
        }
    }
    
}
