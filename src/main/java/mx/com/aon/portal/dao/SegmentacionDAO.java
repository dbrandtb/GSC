package mx.com.aon.portal.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;
//import mx.com.aon.catbo.dao.AdministracionTipoArchivoDAO.AtributoArchivoMapperExport;
import mx.com.aon.catbo.model.ArchivoVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.model.PersonaVO;
import mx.com.aon.portal.model.PersonaCorporativoVO;
import mx.com.aon.portal.model.AdministraCatalogoVO;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SegmentacionDAO extends AbstractDAO {

    private static Logger logger = Logger.getLogger(SegmentacionDAO.class);


    protected void initDao() throws Exception {
      //  addStoredProcedure("OBTIENE_TCATAEX",new obtieneDatosTcataex(getDataSource()));
    	
        addStoredProcedure("OBTIENE_TCATAEX",new ObtieneTcataex(getDataSource()));
        addStoredProcedure("GUARDAR_TCATAEX",new GuardarTcataex(getDataSource()));
        addStoredProcedure("BORRAR_TCATAEX", new BorrarTcataex(getDataSource()));
        addStoredProcedure("TCATAEX_EXPORT", new TcataexExport(getDataSource()));
                
      //  addStoredProcedure("PERSONAS_GUARDAR_CORPORATIVO",new GuardarCorporativo(getDataSource()));
       // addStoredProcedure("OBTENER_CORPORATIVO_PERSONA",new ObtieneCorporativo(getDataSource()));
        
    }

    
    /**
     * Obtiene DATOS de la TCATAEX
     */
    
    protected class ObtieneTcataex extends CustomStoredProcedure {

        protected ObtieneTcataex(DataSource dataSource) {
            super(dataSource, "PKG_EQUIVALENCIA.P_OBTIENE_TCATAEXT");
            declareParameter(new SqlParameter("pv_country_code_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdsistema_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdtablaext_i", OracleTypes.VARCHAR));

            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new TcataexMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
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
    
     /**
        Salida de Tcataex
      */

    protected class TcataexMapper  implements RowMapper {
    	 public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		 AdministraCatalogoVO administraCatalogoVO = new AdministraCatalogoVO();
    		 
    		 administraCatalogoVO.setCodPais(rs.getString("COUNTRY_CODE"));//.setDsArchivo(rs.getString("DSARCHIVO"));//(rs.getString("DSARCHIVO"));
    		 administraCatalogoVO.setCdSistema(rs.getString("CDSISTEMA"));
    		 administraCatalogoVO.setOtClave(rs.getString("OTCLAVE01"));//.setIndArchivo(rs.getString("INDARCHIVO"));//(rs.getString("INDARCHIVO"));
    		 administraCatalogoVO.setOtValor(rs.getString("OTVALOR"));
    		 administraCatalogoVO.setOtClave2(rs.getString("OTCLAVE02"));//.setDsIndArchivo(rs.getString("DSINDARCHIVO"));//(rs.getString("DSINDARCHIVO"));
    		 administraCatalogoVO.setOtClave3(rs.getString("OTCLAVE03")) ;
    		 administraCatalogoVO.setOtClave4(rs.getString("OTCLAVE04"));
    		 administraCatalogoVO.setOtClave5(rs.getString("OTCLAVE05"));
    		 return administraCatalogoVO;
         }

    }
    
    
    
    /**
     Guardar un Registro en la tabla TCATAEX
    */
            

    protected class GuardarTcataex extends CustomStoredProcedure {

      protected GuardarTcataex(DataSource dataSource) {
          super(dataSource, "PKG_EQUIVALENCIA.P_GUARDA_TCATAEXT");
          //declareParameter(new SqlOutParameter("pi_otfisjur", OracleTypes.VARCHAR));
          //declareParameter(new SqlInOutParameter("pi_cdperson", OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_country_code_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cdsistema_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_otclave01_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_otvalor_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_otclave02_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_otclave03_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_otclave04_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_otclave05_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cdtablaext_i", OracleTypes.VARCHAR));
          

          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
          compile();
        }




        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }

    }
    
  
    
    /**
     * Borra un Registro de la tabla Tcataex
     */
    
    protected class BorrarTcataex extends CustomStoredProcedure {

    	public BorrarTcataex (DataSource dataSource) {
    		super(dataSource, "PKG_EQUIVALENCIA.P_BORRA_TCATAEXT");
    		
    		declareParameter(new SqlParameter("pv_country_code_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_nmtabla_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_cdsistema_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_otclave01_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_otvalor_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_cdtablaext_i", OracleTypes.VARCHAR));
    		
    		
    		 declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
	         declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
    		
    		compile();
    	}
		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric res = new WrapperResultadosGeneric();
			return res.build(map);
		}
    	
    }
    
    
    //Export Tcataex
    
    protected class TcataexExport extends CustomStoredProcedure {

        protected TcataexExport(DataSource dataSource) {
            
        	   super(dataSource, "PKG_EQUIVALENCIA.P_OBTIENE_TCATAEXT");
               declareParameter(new SqlParameter("pv_country_code_i", OracleTypes.VARCHAR));
               declareParameter(new SqlParameter("pv_cdsistema_i", OracleTypes.VARCHAR));
               declareParameter(new SqlParameter("pv_cdtablaext_i", OracleTypes.VARCHAR));

               declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new TcataexMapperExport()));
               declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
               declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
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
    
    protected class TcataexMapperExport  implements RowMapper {
        @SuppressWarnings("unchecked")
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
          ArrayList lista =  new ArrayList(6);
                    
        //  lista.add(rs.getString("COUNTRY_CODE"));//.setDsArchivo(rs.getString("DSARCHIVO"));//(rs.getString("DSARCHIVO"));
        //  lista.add(rs.getString("CDSISTEMA"));
          lista.add(rs.getString("OTCLAVE01"));//.setIndArchivo(rs.getString("INDARCHIVO"));//(rs.getString("INDARCHIVO"));
          lista.add(rs.getString("OTVALOR"));
          lista.add(rs.getString("OTCLAVE02"));//.setDsIndArchivo(rs.getString("DSINDARCHIVO"));//(rs.getString("DSINDARCHIVO"));
          lista.add(rs.getString("OTCLAVE03")) ;
          lista.add(rs.getString("OTCLAVE04"));
          lista.add(rs.getString("OTCLAVE05"));
         // lista.add(rs.getString("CDTABLAEXT"));
          
          
          return lista;
       }
    }
    
    /*
     *pv_country_code_i   IN TCATAEXT.COUNTRY_CODE%TYPE,
                                pv_cdsistema_i      IN TCATAEXT.CDSISTEMA%TYPE,
                                pv_otclave01_i      IN TCATAEXT.OTCLAVE01%TYPE,
                                pv_otvalor_i        IN TCATAEXT.OTVALOR%TYPE,
                                pv_cdtablaext_i  ;*/ 
    
  /*
         pv_country_code_i   IN TCATAEXT.COUNTRY_CODE%TYPE,
                                pv_nmtabla_i        IN MEQUVTAB.NMTABLA%TYPE,
                                pv_cdsistema_i      IN TCATAEXT.CDSISTEMA%TYPE,
                                pv_otclave01_i      IN TCATAEXT.OTCLAVE01%TYPE,
                                pv_otvalor_i        IN TCATAEXT.OTVALOR%TYPE,
                                pv_cdtablaext_i */
}

