package mx.com.aon.portal.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.model.EstructuraVO;
import mx.com.aon.portal.model.OrdenDeCompraVO;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarritoComprasDAO extends AbstractDAO {

    private static Logger logger = Logger.getLogger(CarritoComprasDAO.class);


    protected void initDao() throws Exception {
/*
        addStoredProcedure("OBTIENERREG_ORDEN_DE_COMPRAS_PERSONAS",new CarritoComprasDAO.BuscarOrdenCompra(getDataSource()));
        addStoredProcedure("FINALIZAR_ORDEN_DE_COMPRAS",new CarritoComprasDAO.ObtieneEstructura(getDataSource()));
*/
        addStoredProcedure("BUSCAR_ORDEN_DE_COMPRAS",new BuscarOrdenCompra(getDataSource()));
    }


    protected class BuscarOrdenCompra extends CustomStoredProcedure {

      protected BuscarOrdenCompra(DataSource dataSource) {
          super(dataSource, "PKG_COTIZA.P_OBTIENE_HISTORDEN");
          declareParameter(new SqlParameter("pv_nmorden_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_feregistro_de_i", OracleTypes.DATE));
          declareParameter(new SqlParameter("pv_feregistro_a_i", OracleTypes.DATE));

          declareParameter(new SqlOutParameter("lcur_registros_i", OracleTypes.CURSOR, new BuscarOrdenCompraMapper()));
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


/*
    protected class ObtieneEstructura extends CustomStoredProcedure {

      protected ObtieneEstructura(DataSource dataSource) {
          super(dataSource, "PKG_CONFG_CUENTA.P_OBTIENE_ESTRUCT_REG");
          declareParameter(new SqlParameter("pv_cdestruc_i",OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("lcur_registros_i", OracleTypes.CURSOR, new CarritoComprasDAO.EstructuraMapper()));
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
          declareParameter(new SqlOutParameter("lcur_registros_i", OracleTypes.CURSOR, new CarritoComprasDAO.EstructuraMapperExport()));
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
*/

    protected class BuscarOrdenCompraMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrdenDeCompraVO ordenDeCompraVO  = new OrdenDeCompraVO();
            ordenDeCompraVO.setCdCarro(rs.getString("CDCARRO"));
            ordenDeCompraVO.setFeInicio(ConvertUtil.convertToString(rs.getDate("FEINICIO")));
            ordenDeCompraVO.setNmTarj(rs.getString("NMTARJ"));
            ordenDeCompraVO.setCdContra(rs.getString("CDCONTRA"));
            ordenDeCompraVO.setCdAsegur(rs.getString("CDASEGUR"));
            ordenDeCompraVO.setNmSubTot(rs.getString("NMSUBTOT"));
            ordenDeCompraVO.setNmDsc(rs.getString("NMDSC"));
            ordenDeCompraVO.setNmTotal(rs.getString("NMTOTAL"));
            ordenDeCompraVO.setNmOrden(rs.getString("NMORDEN"));
            ordenDeCompraVO.setCdEstado(rs.getString("CDESTADO"));
            ordenDeCompraVO.setDsEstado(rs.getString("DSESTADO"));
            ordenDeCompraVO.setFeEstado(ConvertUtil.convertToString(rs.getDate("FEESTADO")));
            ordenDeCompraVO.setCdTipDom(rs.getString("CDTIPDOM"));
            ordenDeCompraVO.setNmOrdDon(rs.getString("NMORDDOM"));
            ordenDeCompraVO.setCdClient(rs.getString("CDCLIENT"));
            ordenDeCompraVO.setCdForPag(rs.getString("CDFORPAG"));
            return ordenDeCompraVO;
        }
    }




/*
    protected class EstructuraMapperExport  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArrayList lista =  new ArrayList(2);
            lista.add(rs.getString("CDESTRUC"));
            lista.add(rs.getString("DSESTRUC"));
            return lista;
        }
    }
*/
}
