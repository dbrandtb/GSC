package mx.com.aon.portal.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.model.PersonaVO;
import mx.com.aon.portal.model.PersonaCorporativoVO;

import java.util.Map;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaDAO extends AbstractDAO {

    private static Logger logger = Logger.getLogger(PersonaDAO.class);


    protected void initDao() throws Exception {
        addStoredProcedure("PERSONAS_INSERTAR",new InsertarPersona(getDataSource()));
        addStoredProcedure("PERSONAS_GUARDAR",new GuardarPersona(getDataSource()));
        addStoredProcedure("OBTENER_PERSONA",new ObtienePersona(getDataSource()));
        addStoredProcedure("PERSONAS_GUARDAR_CORPORATIVO",new GuardarCorporativo(getDataSource()));
        addStoredProcedure("OBTENER_CORPORATIVO_PERSONA",new ObtieneCorporativo(getDataSource()));
        addStoredProcedure("PERSONAS_BORRAR", new BorrarPersona(getDataSource()));
    }


    protected class InsertarPersona extends CustomStoredProcedure {

      protected InsertarPersona(DataSource dataSource) {
          super(dataSource, "PKG_PERSONA.P_SALVA_PERSONA");
          declareParameter(new SqlOutParameter("pi_otfisjur", OracleTypes.VARCHAR));
          declareParameter(new SqlInOutParameter("pi_cdperson", OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pi_cdtipide", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_cdideper", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_dsnombre", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_dsapellido", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_dsapellido1", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_otsexo", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_cdestciv", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_fenacimi", OracleTypes.DATE));
          declareParameter(new SqlParameter("pi_cdnacion", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_cdtipper", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("curp", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("cdrfc", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("dsemail", OracleTypes.VARCHAR));

          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
          compile();
        }




        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            if (map.get("pi_cdperson") != null) { //Agregado para evitar exception en QA-Cliente debido a modificaciones en el sp
            	wrapperResultados.setResultado((map.get("pi_cdperson")).toString());
            }
            return wrapperResultados;
        }

    }

    protected class GuardarPersona extends CustomStoredProcedure {

      protected GuardarPersona(DataSource dataSource) {
          super(dataSource, "PKG_PERSONA.P_SALVA_PERSONA");
          declareParameter(new SqlOutParameter("pi_otfisjur", OracleTypes.VARCHAR));
          declareParameter(new SqlInOutParameter("pi_cdperson", OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pi_cdtipide", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_cdideper", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_dsnombre", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_dsapellido", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_dsapellido1", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_otsexo", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_cdestciv", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_fenacimi", OracleTypes.DATE));
          declareParameter(new SqlParameter("pi_cdnacion", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_cdtipper", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("curp", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("cdrfc", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("dsemail", OracleTypes.VARCHAR));

          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
          compile();
        }




        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }

    }

    protected class GuardarCorporativo extends CustomStoredProcedure {

      protected GuardarCorporativo(DataSource dataSource) {
          super(dataSource, "PKG_PERSONA.P_GUARDA_CORPORA");
          declareParameter(new SqlParameter("pi_cdperson", OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pi_cdelemen", OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pi_status", OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pi_feinicio", OracleTypes.DATE));
          declareParameter(new SqlParameter("pi_fefin", OracleTypes.DATE));
          declareParameter(new SqlParameter("pi_cdgrupoper", OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pi_nmnomina", OracleTypes.VARCHAR));


          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
          compile();
        }

        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }

    protected class ObtienePersona extends CustomStoredProcedure {

      protected ObtienePersona(DataSource dataSource) {
          super(dataSource, "PKG_PERSONA.P_OBTIENE_PERSONA");
          declareParameter(new SqlParameter("pi_otfisjur",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pi_cdperson",OracleTypes.NUMERIC));

          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new PersonaMapper()));
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

    protected class ObtieneCorporativo extends CustomStoredProcedure {

      protected ObtieneCorporativo(DataSource dataSource) {
          super(dataSource, "PKG_PERSONA.P_PERSONA_CORPORATIVO");
          declareParameter(new SqlParameter("pi_cdperson",OracleTypes.NUMERIC));

          declareParameter(new SqlOutParameter("po_resulset", OracleTypes.CURSOR, new CorporativoMapper()));
          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
          compile();
        }


        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("po_resulset");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
        }
    }

 
    protected class PersonaMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            PersonaVO personaVO = new PersonaVO();
            String tipo = rs.getString("OTFISJUR");
            personaVO.setOtFisJur(rs.getString("OTFISJUR"));
            if (tipo.equals("M")) {
                personaVO.setDsTipPer(rs.getString("DSTIPPER"));
                //todo  que hacems con este valor ?? rs.getString("CDTIPPER_1"));

            }   else {
                personaVO.setDsFisJur(rs.getString("DSFISJUR"));
                personaVO.setDsApellido(rs.getString("DSAPELLIDO"));
                personaVO.setDsApellido1(rs.getString("DSAPELLIDO1"));
                personaVO.setOtSexo(rs.getString("OTSEXO"));
                personaVO.setCdEstCiv(rs.getString("CDESTCIV"));
             // personaVO.setCdNacion(rs.getString("CDNACION"));

            }
            personaVO.setCdIdePer(rs.getString("CDIDEPER"));
            personaVO.setCdTipIde(rs.getString("CDTIPIDE"));
            personaVO.setFeNacimi(ConvertUtil.convertToString(rs.getDate("FENACIMI")));
            personaVO.setDsNombre(rs.getString("DSNOMBRE"));
            personaVO.setCdTipPer(rs.getString("CDTIPPER"));
            personaVO.setCurp(rs.getString("CURP"));
            personaVO.setCdRfc(rs.getString("CDRFC"));
            personaVO.setDsEmail(rs.getString("DSEMAIL"));
            personaVO.setCdNacion(rs.getString("CDNACION"));

            return personaVO;
        }

/*
Moral
SELECT mp.cdtipper,
                                  Pkg_Traduc.f_globalizacion (tt.cdtipper, tt.dstipper, 'TTIPOPER') dstipper,
                                  mp.cdperson,
                                  mp.dsnombre,
                                  mp.fenacimi,
          mp.cdtipide,
          mp.cdideper,
          mp.cdtipper,
          --mp.feingreso,
          mp.curp,
         mp.CDRFC,
          mp.DSEMAIL






        SELECT mp.otfisjur,
                                  Pkg_Traduc.f_globalizacion (tf.otfisjur, tf.dsfisjur, 'TFISJUR') dsfisjur,
                                  mp.cdperson,
                                  mp.dsnombre,
                                  mp.dsapellido,
                                  mp.dsapellido1,
                                  mp.otsexo,   -- preguntar si es tabla fisica o de apoyo
                                  mp.cdestciv, -- preguntar si es tabla fisica o de apoyo
                                  mp.fenacimi,
                                  mp.cdnacion,   -- preguntar si es tabla fisica o de apoyo
          mp.cdtipide,
          mp.cdideper,
          mp.cdtipper,
          --mp.feingreso,
          mp.CURP,
          mp.CDRFC,
          mp.DSEMAIL
         */

    }

    protected class CorporativoMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            PersonaCorporativoVO personaCorporativoVO = new PersonaCorporativoVO();
            personaCorporativoVO.setCdElemen(rs.getString("CDELEMEN"));
            personaCorporativoVO.setDsElemen(rs.getString("DSELEMEN"));
            personaCorporativoVO.setCdGrupoPer(rs.getString("CDGRUPOPER"));
            personaCorporativoVO.setDsGrupo(rs.getString("DSGRUPO"));
            personaCorporativoVO.setFeInicio(ConvertUtil.convertToString(rs.getDate("FEINICIO")));
            personaCorporativoVO.setFeFin(ConvertUtil.convertToString(rs.getDate("FEFIN")));
            personaCorporativoVO.setCdStatus(rs.getString("STATUS"));
            personaCorporativoVO.setDsStatus(rs.getString("DSSTATUS"));
            personaCorporativoVO.setNmNomina(rs.getString("NMNOMINA"));

            return personaCorporativoVO;
        }



    }
    
    /**
     * Clase para manejo de Borrado de Personas
     */
    
    protected class BorrarPersona extends CustomStoredProcedure {

    	public BorrarPersona (DataSource dataSource) {
    		super(dataSource, "PKG_PERSONA.P_BORRA_PERSONA");
    		
    		declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		
    		compile();
    	}
		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric res = new WrapperResultadosGeneric();
			return res.build(map);
		}
    	
    }
}

