package mx.com.aon.catbo.dao;

import org.apache.log4j.Logger;
import oracle.jdbc.driver.OracleTypes;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.List;

//import mx.com.aon.catbo.model.EstructuraVO;
import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.catbo.model.ItemVO;


public class EstructuraDAO extends AbstractDAO {

    private static Logger logger = Logger.getLogger(EstructuraDAO.class);


    protected void initDao() throws Exception {
        addStoredProcedure("OBTIENE_ESTRUCT",new BuscarEstructuras(getDataSource()));
        addStoredProcedure("OBTIENE_ESTRUCT_REG",new ObtieneEstructura(getDataSource()));
        addStoredProcedure("INSERTA_ESTRUCT",new AgregarEstructura(getDataSource()));
        addStoredProcedure("GUARDA_ESTRUCT",new GuardarEstructura(getDataSource()));
        addStoredProcedure("BORRA_ESTRUCT",new BorrarEstructura(getDataSource()));
    }


    protected class BuscarEstructuras extends CustomStoredProcedure {

      protected BuscarEstructuras(DataSource dataSource) {
          super(dataSource, "PKG_CONFG_CUENTA.P_OBTIENE_ESTRUCT");
          declareParameter(new SqlParameter("pv_dsestruc_i",OracleTypes.VARCHAR));
          declareParameter(new SqlOutParameter("lcur_registros_i", OracleTypes.CURSOR, new EstructuraMapper()));
          declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
          compile();
        }


        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("lcur_registros_i");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
        }
    }

    

    protected class ObtieneEstructura extends CustomStoredProcedure {

      protected ObtieneEstructura(DataSource dataSource) {
          super(dataSource, "PKG_CONFG_CUENTA.P_OBTIENE_ESTRUCT_REG");
          declareParameter(new SqlParameter("pv_cdestruc_i",OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("lcur_registros_i", OracleTypes.CURSOR, new EstructuraMapper()));
          declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
          compile();
        }


        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("lcur_registros_i");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
        }
    }


    protected class AgregarEstructura extends CustomStoredProcedure {

      protected AgregarEstructura(DataSource dataSource) {
          super(dataSource, "PKG_CONFG_CUENTA.P_INSERTA_ESTRUCT");
          declareParameter(new SqlParameter("pi_dsestruc",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_cdregion",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("Pi_cdidioma",OracleTypes.VARCHAR));
          
          declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
          compile();
        }


        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }


    protected class GuardarEstructura extends CustomStoredProcedure {

      protected GuardarEstructura(DataSource dataSource) {
          super(dataSource, "PKG_CONFG_CUENTA.P_GUARDA_ESTRUCT");
          declareParameter(new SqlParameter("pi_cdestruc",OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pi_dsestruc",OracleTypes.VARCHAR));
          declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
          compile();
        }


        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }

    protected class BorrarEstructura extends CustomStoredProcedure {

      protected BorrarEstructura(DataSource dataSource) {
          super(dataSource, "PKG_CONFG_CUENTA.P_BORRA_ESTRUCT");
          declareParameter(new SqlParameter("pi_cdestruc",OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
          compile();
        }


        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }


    protected class EstructuraMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ItemVO estructuraVO = new ItemVO();
            estructuraVO.setId(rs.getString("CDESTRUC"));
            estructuraVO.setTexto(rs.getString("DSESTRUC"));
            return estructuraVO;
        }
    }

}


