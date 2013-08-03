package mx.com.aon.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.catweb.configuracion.producto.expresiones.model.RamaVO;
import mx.com.aon.portal.model.reporte.ReporteVO;
import mx.com.aon.portal.model.reporte.PlantillaVO;
import mx.com.aon.portal.model.reporte.ComboGraficoVo;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class ReporteDAO extends AbstractDAO {

    private static Logger logger = Logger.getLogger(ReporteDAO.class);

    public static final String BUSCAR_REPORTES = "BUSCAR_REPORTES";
    public static final String BUSCAR_REPORTES_ADMINISTRACION = "BUSCAR_REPORTES_ADMINISTRACION";
    public static final String BUSCAR_PLANTILLAS = "BUSCAR_PLANTILLAS";
    public static final String BUSCAR_PLANTILLAS_ASOCIAR = "BUSCAR_PLANTILLAS_ASOCIAR";
    public static final String COMBO_ASEGURADORA = "COMBO_ASEGURADORA";
    public static final String COMBO_PRODUCTO = "COMBO_PRODUCTO";
    public static final String COMBO_CUENTA = "COMBO_CUENTA";


    protected void initDao() throws Exception {
        logger.info("Entrado a init...");
        addStoredProcedure(BUSCAR_REPORTES, new BuscarReportes(getDataSource()));
        addStoredProcedure(BUSCAR_REPORTES_ADMINISTRACION, new BuscarReportesAdministracion(getDataSource()));
        addStoredProcedure(BUSCAR_PLANTILLAS, new BuscarPlantilla(getDataSource()));
        addStoredProcedure(COMBO_ASEGURADORA, new ComboAseguradora(getDataSource()));
        addStoredProcedure(COMBO_PRODUCTO, new ComboProducto(getDataSource()));
        addStoredProcedure(COMBO_CUENTA, new ComboCuenta(getDataSource()));

    }

    protected class BuscarReportes extends CustomStoredProcedure {

        protected BuscarReportes(DataSource dataSource) {
            super(dataSource, "PKG_REPORTES.P_OBTIENE_MREPORTE_EJECUTA");

            declareParameter(new SqlParameter("p_vdsreporte",
                    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdramo_i",
                    OracleTypes.NUMERIC));

            declareParameter(new SqlOutParameter("p_cu_reportes",
                    OracleTypes.CURSOR, new ListaReportes()));
            compile();

        }

        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("p_cu_reportes");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
        }

    }

    protected class BuscarReportesAdministracion extends CustomStoredProcedure {

        protected BuscarReportesAdministracion(DataSource dataSource) {
            super(dataSource, "PKG_REPORTES.P_OBTIENE_MREPORTE");

            declareParameter(new SqlParameter("p_vdsreporte",
                    OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("p_cu_reportes",
                    OracleTypes.CURSOR, new ListaReportesAdministracion()));
            compile();

        }

        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("p_cu_reportes");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
        }

    }

    protected class BuscarPlantilla extends CustomStoredProcedure {

        protected BuscarPlantilla(DataSource dataSource) {
            super(dataSource, "PKG_REPORTES.P_OBTIENE_MPLANTIL");

            declareParameter(new SqlParameter("p_vdsplantilla",
                    OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("p_cu_plantillas",
                    OracleTypes.CURSOR, new ListaPlantillas()));
            compile();

        }

        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("p_cu_plantillas");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
        }

    }

    protected class ComboAseguradora extends CustomStoredProcedure {

        protected ComboAseguradora(DataSource dataSource) {
            super(dataSource, "PKG_REPORTES.P_LISTA_ASEGURADORA");

            declareParameter(new SqlParameter("pv_cdelemento_i",
                    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdramo_i",
                    OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o",
                    OracleTypes.CURSOR, new ListaComboAseguradora()));
            declareParameter(new SqlOutParameter("pv_msg_id_o",
                    OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",
                    OracleTypes.VARCHAR));
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

    protected class ComboProducto extends CustomStoredProcedure {

        protected ComboProducto(DataSource dataSource) {
            super(dataSource, "PKG_REPORTES.P_LISTA_PRODUCTOS");
            declareParameter(new SqlParameter("pv_cdunieco_i",
                    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdelemento_i",
                    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdramo_i",
                    OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o",
                    OracleTypes.CURSOR, new ListaComboProducto()));
            declareParameter(new SqlOutParameter("pv_msg_id_o",
                    OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",
                    OracleTypes.VARCHAR));

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


    protected class ComboCuenta extends CustomStoredProcedure {

        protected ComboCuenta(DataSource dataSource) {
            super(dataSource, "PKG_REPORTES.P_LISTA_CUENTA");
            declareParameter(new SqlParameter("pv_cdunieco_i",
                    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdramo_i",
                    OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdelemento_i",
                    OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o",
                    OracleTypes.CURSOR, new ListaComboCuenta()));
            declareParameter(new SqlOutParameter("pv_msg_id_o",
                    OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",
                    OracleTypes.VARCHAR));

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




    protected class ListaReportesAdministracion implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReporteVO valores = new ReporteVO();

            valores.setCdReporte(rs.getInt("CDREPORTE"));
            valores.setDsReporte(rs.getString("DSREPORTE"));
            valores.setNmReporte(rs.getString("NMREPORTE"));

            return valores;
        }
    }

    protected class ListaReportes implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReporteVO valores = new ReporteVO();

            valores.setCdReporte(rs.getInt("CDREPORTE"));
            valores.setDsReporte(rs.getString("DSREPORTE"));
            valores.setNmReporte(rs.getString("NMREPORTE"));
            valores.setNumAtributos(rs.getString("NUMATRIBUTOS"));

            return valores;
        }
    }

    protected class ListaPlantillas implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            PlantillaVO valores = new PlantillaVO();

            valores.setCdPlantilla(rs.getString("CDPLANTI"));
            valores.setDsPlantilla(rs.getString("DSPLANTI"));
            valores.setStatus(rs.getString("STATUS"));


            return valores;
        }
    }

    protected class ListaComboAseguradora implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ComboGraficoVo valores = new ComboGraficoVo();
            valores.setCodigo(rs.getString("cdunieco"));
            valores.setDescripC(rs.getString("dsunieco"));
            return valores;
        }
    }

    protected class ListaComboProducto implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ComboGraficoVo valores = new ComboGraficoVo();
            valores.setCodigo(rs.getString("CDRAMO"));
            valores.setDescripC(rs.getString("DSRAMO"));
            return valores;
        }
    }

    protected class ListaComboCuenta implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ComboGraficoVo valores = new ComboGraficoVo();
            valores.setCodigo(rs.getString("CDELEMENTO"));
            valores.setDescripC(rs.getString("DSELEMEN"));
            return valores;
        }
    }

}