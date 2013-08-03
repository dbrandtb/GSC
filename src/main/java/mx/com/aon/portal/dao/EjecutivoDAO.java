package mx.com.aon.portal.dao;

import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal.dao.EstructuraDAO.AgregarEstructura;
import mx.com.aon.portal.dao.EstructuraDAO.BorrarEstructura;
import mx.com.aon.portal.dao.EstructuraDAO.BuscarEstructuras;
import mx.com.aon.portal.dao.EstructuraDAO.CopiarEstructura;
import mx.com.aon.portal.dao.EstructuraDAO.GuardarEstructura;
import mx.com.aon.portal.dao.EstructuraDAO.ObtieneEstructura;
import mx.com.aon.portal.dao.EstructuraDAO.ObtieneEstructurasExport;
import mx.com.aon.portal.util.WrapperResultados;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class EjecutivoDAO extends AbstractDAO{
	private static Logger logger = Logger.getLogger(EstructuraDAO.class);


    protected void initDao() throws Exception {
       
        addStoredProcedure("MANTENIMIENTO_EJECUTIVOS_CUENTA_GUARDAR_NUEVO",new AgregarEjecutivo(getDataSource()));
        addStoredProcedure("INSERTA_GUARDA_EJECUTIVO_CUENTA",new GuardarEjecutivo(getDataSource()));
        addStoredProcedure("MANTENIMIENTO_EJECUTIVOS_CUENTA_ATRIBUTOS_GUARDAR",new GuardarAtributosEjecutivo(getDataSource()));
        
    }
    
    protected class AgregarEjecutivo extends CustomStoredProcedure {

        protected AgregarEjecutivo(DataSource dataSource) {
            super(dataSource, "PKG_CATALOGO.P_INSERTA_AGENTES");
            declareParameter(new SqlParameter("pv_cdagente_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdperson_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_fedesde_i",OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_fehasta_i",OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_swactivo_i",OracleTypes.VARCHAR));
                        
            declareParameter(new SqlOutParameter("pv_cdagente_o", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
          }


          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }
      }
    
    protected class GuardarEjecutivo extends CustomStoredProcedure {

        protected GuardarEjecutivo(DataSource dataSource) {
            super(dataSource, "PKG_CONFIGURA.P_GUARDA_EJECUTIVO");
            declareParameter(new SqlParameter("p_cve_agente",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("p_cve_cliente",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("p_cve_estado",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("p_fecha_inic",OracleTypes.DATE));
            declareParameter(new SqlParameter("p_fecha_fin",OracleTypes.DATE));
            declareParameter(new SqlParameter("p_cve_tipram",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("p_cve_ramo",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("p_linea_agen",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("p_niv_post",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("p_grupo",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("p_cdelemento",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("p_cdtipage",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("p_accion",OracleTypes.VARCHAR));
                        
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
          }


          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }
      }
    
    protected class GuardarAtributosEjecutivo extends CustomStoredProcedure {
        protected GuardarAtributosEjecutivo(DataSource dataSource) {
            super(dataSource, "PKG_CATALOGO.P_INSERTA_AGENTE_VARIABLES");
            
            declareParameter(new SqlParameter("pv_cdagente_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdatribu_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_otvalor_i",OracleTypes.VARCHAR));
                        
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
          }


          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }
      }
}
