package mx.com.aon.portal.dao;

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
import java.util.ArrayList;

import mx.com.aon.portal.model.EstructuraVO;
import mx.com.aon.portal.util.WrapperResultados;


public class EstructuraDAO extends AbstractDAO {

    private static Logger logger = Logger.getLogger(EstructuraDAO.class);


    protected void initDao() throws Exception {
        addStoredProcedure("OBTIENE_ESTRUCT",new BuscarEstructuras(getDataSource()));
        addStoredProcedure("OBTIENE_ESTRUCT_REG",new ObtieneEstructura(getDataSource()));
        addStoredProcedure("INSERTA_ESTRUCT",new AgregarEstructura(getDataSource()));
        addStoredProcedure("GUARDA_ESTRUCT",new GuardarEstructura(getDataSource()));
        addStoredProcedure("BORRA_ESTRUCT",new BorrarEstructura(getDataSource()));
        addStoredProcedure("OBTIENE_ESTRUCT_EXPORT",new ObtieneEstructurasExport(getDataSource()));
        addStoredProcedure("COPIA_ESTRUCT",new CopiarEstructura(getDataSource()));
    }


    protected class BuscarEstructuras extends CustomStoredProcedure {

      protected BuscarEstructuras(DataSource dataSource) {
          super(dataSource, "PKG_CONFG_CUENTA.P_OBTIENE_ESTRUCT");
          declareParameter(new SqlParameter("pv_dsestruc_i",OracleTypes.VARCHAR));
          declareParameter(new SqlOutParameter("lcur_registros_i", OracleTypes.CURSOR, new EstructuraMapper()));
          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
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
          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
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
          declareParameter(new SqlParameter("pi_cdregion",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("Pi_cdidioma",OracleTypes.VARCHAR));
          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
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
          declareParameter(new SqlParameter("pi_cdregion",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("Pi_cdidioma",OracleTypes.VARCHAR));
          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
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
          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
          compile();
        }


        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }

    protected class CopiarEstructura extends CustomStoredProcedure {

      protected CopiarEstructura(DataSource dataSource) {
          super(dataSource, "PKG_CONFG_CUENTA.P_COPIA_ESTRUCT");
          declareParameter(new SqlParameter("pi_cdestruc",OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
          compile();
        }


        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }


    protected class ObtieneEstructurasExport extends CustomStoredProcedure {

      protected ObtieneEstructurasExport(DataSource dataSource) {
          super(dataSource, "PKG_CONFG_CUENTA.P_OBTIENE_ESTRUCT");
          declareParameter(new SqlParameter("pv_dsestruc_i",OracleTypes.VARCHAR));
          declareParameter(new SqlOutParameter("lcur_registros_i", OracleTypes.CURSOR, new EstructuraMapperExport()));
          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
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

    protected class EstructuraMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            EstructuraVO estructuraVO = new EstructuraVO();
            estructuraVO.setCodigo(rs.getString("CDESTRUC"));
            estructuraVO.setDescripcion(rs.getString("DSESTRUC"));
            estructuraVO.setCodigoRegion(rs.getString("CDREGION"));
            estructuraVO.setCodigoIdioma(rs.getString("CDIDIOMA"));
            return estructuraVO;
        }
    }

    protected class EstructuraMapperExport  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArrayList lista =  new ArrayList(2);
            lista.add(rs.getString("CDESTRUC"));
            lista.add(rs.getString("DSESTRUC"));
            return lista;
        }
    }
}


