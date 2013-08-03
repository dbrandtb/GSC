package mx.com.aon.catbo.dao;

import org.apache.log4j.Logger;
import oracle.jdbc.driver.OracleTypes;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.List;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;

//import mx.com.aon.catbo.model.EstructuraVO;
import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.util.WrapperResultados;

import mx.com.aon.catbo.model.PersonaVO;


public class CasosDAO extends AbstractDAO {

    private static Logger logger = Logger.getLogger(CasosDAO.class);


    protected void initDao() throws Exception {
        addStoredProcedure("OBTIENE_PERSONA_CASO",new ObtienePersona(getDataSource()));
       
    }


    protected class ObtienePersona extends CustomStoredProcedure {

      protected ObtienePersona(DataSource dataSource) {
          super(dataSource, "PKG_CATBO.P_OBTIENE_DATOS_CLIENTE");
          declareParameter(new SqlParameter("pv_cdperson_i",OracleTypes.VARCHAR));
          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new EstructuraMapper()));
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


   protected class EstructuraMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	PersonaVO personaVO = new PersonaVO();
        	personaVO.setCdPerson(rs.getString("CDPERSON"));
        	personaVO.setDsNombre(rs.getString("DSNOMBRE"));
        	personaVO.setCdIdeper(rs.getString("CDIDEPER"));
        	personaVO.setCorpo(rs.getString("CORPO"));
            return personaVO;
        }
    }

}


