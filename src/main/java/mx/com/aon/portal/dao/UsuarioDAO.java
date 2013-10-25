package mx.com.aon.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.UsuarioRolEmpresaVO;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class UsuarioDAO extends AbstractDAO {

    private static Logger logger = Logger.getLogger(UsuarioDAO.class);


    protected void initDao() throws Exception {
        addStoredProcedure("CARGA_ROLES_CLIENTES",new CargaRolesClientes(getDataSource()));
        addStoredProcedure("CARGA_ROLES_CLIENTES_USER",new CargaRolesClientes(getDataSource()));
        addStoredProcedure("OBTIENE_VARIABLES_ISO",new ObtieneVariablesIso(getDataSource()));
    }


    protected class CargaRolesClientes extends CustomStoredProcedure {

      protected CargaRolesClientes(DataSource dataSource) {
          super(dataSource, "PKG_CONFPAGINA.P_OBTIENE_ROLES_CLIENTE");
          declareParameter(new SqlParameter("PV_CDUSUARIO_I", OracleTypes.VARCHAR));
          declareParameter(new SqlOutParameter("PV_REGISTRO_O", OracleTypes.CURSOR, new UsuarioRolEmpresaMapper()));
          declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
          compile();
        }



        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("PV_REGISTRO_O");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
        }
    }

    protected class UsuarioRolEmpresaMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            UsuarioRolEmpresaVO usuarioRolEmpresaVO  = new UsuarioRolEmpresaVO();
            usuarioRolEmpresaVO.setCdUsuario(rs.getString("CDUSUARIO"));
            usuarioRolEmpresaVO.setCdPerson(rs.getString("CDPERSON"));
            usuarioRolEmpresaVO.setDsUsuario(rs.getString("DSUSUARI"));
            usuarioRolEmpresaVO.setCdElemento(rs.getString("CDELEMENTO"));
            usuarioRolEmpresaVO.setDsElemen(rs.getString("DSELEMEN"));
            usuarioRolEmpresaVO.setCdSisRol(rs.getString("CDSISROL"));
            usuarioRolEmpresaVO.setDsSisRol(rs.getString("DSSISROL"));
            return usuarioRolEmpresaVO;
        }
    }

    protected class ObtieneVariablesIso extends CustomStoredProcedure {

        protected ObtieneVariablesIso(DataSource dataSource) {
            super(dataSource, "PKG_VAR_GLOBAL.P_VARIABLES_OBTIENE_ISO");
            declareParameter(new SqlParameter("P_USUARIO", OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("P_PAIS", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("P_LANGUAGUE_ISO", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("LANGCODE", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("REGIONID", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("P_DSFORMATONUMERICO", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("P_DSFORMATOFECHA", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("P_DSFORMATOFECHAC", OracleTypes.VARCHAR));
            compile();
          }

        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            
    		IsoVO isoVO = new IsoVO();
    		isoVO.setCdIdioma((String)map.get("LANGCODE"));
    		isoVO.setCdRegion((String)map.get("REGIONID"));
    		isoVO.setClientDateFormat((String)map.get("P_DSFORMATOFECHAC"));
    		isoVO.setFormatoFecha((String)map.get("P_DSFORMATOFECHA"));
    		isoVO.setFormatoNumerico((String)map.get("P_DSFORMATONUMERICO"));
    		isoVO.setLanguague((String)map.get("P_LANGUAGUE_ISO"));
    		isoVO.setPais((String)map.get("P_PAIS"));
    		
    		HashMap<String, Object> itemMap = new HashMap<String, Object>();
    		itemMap.put("isovo", isoVO);
    		
    		wrapperResultados.setItemMap(itemMap);
    		
            return wrapperResultados;
        }
      }


}
