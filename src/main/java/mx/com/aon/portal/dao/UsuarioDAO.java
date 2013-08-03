package mx.com.aon.portal.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.model.EstructuraVO;
import mx.com.aon.portal.model.RamaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.model.UsuarioRolEmpresaVO;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

public class UsuarioDAO extends AbstractDAO {

    private static Logger logger = Logger.getLogger(UsuarioDAO.class);


    protected void initDao() throws Exception {
        addStoredProcedure("CARGA_ROLES_CLIENTES",new CargaRolesClientes(getDataSource()));
        addStoredProcedure("CARGA_ROLES_CLIENTES_USER",new CargaRolesClientes(getDataSource()));
        addStoredProcedure("AUTHORIZED_EXPORT",new AuthorizedUserExport(getDataSource()));
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


    protected class AuthorizedUserExport extends CustomStoredProcedure {

      protected AuthorizedUserExport(DataSource dataSource) {
          super(dataSource, "Pkg_SEGURIDAD.P_AUTHORIZED_EXPORT");
          declareParameter(new SqlParameter("pv_cdelemento_i", OracleTypes.NUMERIC));
          declareParameter(new SqlParameter("pv_cdsisrol_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cdusuario_i", OracleTypes.VARCHAR));
          declareParameter(new SqlOutParameter("pv_resultado_o", OracleTypes.INTEGER));
          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
          compile();
        }



        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            Integer  resultado = (Integer) map.get("pv_resultado_o");
            wrapperResultados.setResultado(resultado.intValue() > 0?"true":"false");
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




}
