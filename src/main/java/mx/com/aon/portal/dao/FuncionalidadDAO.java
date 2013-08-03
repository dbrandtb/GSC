package mx.com.aon.portal.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import javax.sql.DataSource;

import mx.com.aon.portal.dao.PolizaDAO.PolizasCanceladasMapperExport;
import mx.com.aon.portal.model.FuncionalidadVO;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oracle.jdbc.driver.OracleTypes;

public class FuncionalidadDAO extends AbstractDAO{

    private static Logger logger = Logger.getLogger(FuncionalidadDAO.class);


    protected void initDao() throws Exception {
        addStoredProcedure("BUSCAR_FUNCIONALIDADES",new BuscarFuncionalidades(getDataSource()));
        addStoredProcedure("AGREGAR_FUNCIONALIDAD",new AgregarFuncionalidad(getDataSource()));
        addStoredProcedure("GET_FUNCIONALIDAD",new GetFuncionalidad(getDataSource()));
        addStoredProcedure("GUARDAR_FUNCIONALIDAD",new GuardarFuncionalidad(getDataSource()));
        addStoredProcedure("BUSCAR_CONFIGURACIONES_FUNCIONALIDAD",new BuscarConfiguracionesFuncionalidad(getDataSource()));
        addStoredProcedure("BORRAR_FUNCIONALIDAD",new BorrarFuncionalidad(getDataSource()));
        addStoredProcedure("OBTIENE_FUNCIONALIDADES_EXPORT",new BuscarFuncionalidadesExportar(getDataSource()));
        addStoredProcedure("OBTIENE_P_FUNCIONALIDADES",new ComboObtieneFuncionalidades(getDataSource()));

        
        
    }


    protected class BuscarFuncionalidades extends CustomStoredProcedure {

      protected BuscarFuncionalidades(DataSource dataSource) {
          super(dataSource, "PKG_CONFG_CUENTA.P_OBTIENE_FUNCIONALIDADES");

          declareParameter(new SqlParameter("pv_nivel_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_sisrol_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_usuario_i", OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_funciona_i", OracleTypes.VARCHAR));
          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new FuncionalidadMapper()));
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


    protected class AgregarFuncionalidad extends CustomStoredProcedure {

      protected AgregarFuncionalidad(DataSource dataSource) {
          super(dataSource, "<nombre del plsql a invocar>");
          /*
          declareParameter(new SqlParameter("pv_cdestruc_i",OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("lcur_registros_i", OracleTypes.CURSOR, new EstructuraMapper()));
          */
          declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
          compile();
        }


        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }


    protected class GetFuncionalidad extends CustomStoredProcedure {

      protected GetFuncionalidad(DataSource dataSource) {
          super(dataSource, "PKG_CONFG_CUENTA.P_OBTIENE_FUNCIONALIDADES_REG");
          
          declareParameter(new SqlParameter("pv_nivel_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_sisrol_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_usuario_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_funciona_i",OracleTypes.VARCHAR));
          
          declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new GetFuncionalidadMapper()));
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


    protected class GuardarFuncionalidad extends CustomStoredProcedure {

      protected GuardarFuncionalidad(DataSource dataSource) {
          super(dataSource, "PKG_CONFG_CUENTA.P_INSERTA_FUNCIONALIDADXCTE");

          declareParameter(new SqlParameter("pv_cdelemento_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cdsisrol_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cdusuario_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cdfunciona_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_cdopera_i",OracleTypes.VARCHAR));
          declareParameter(new SqlParameter("pv_swestado_i",OracleTypes.VARCHAR));
          declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
          compile();
        }


        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
    }


    protected class BuscarConfiguracionesFuncionalidad extends CustomStoredProcedure {

      protected BuscarConfiguracionesFuncionalidad(DataSource dataSource) {
          super(dataSource, "<nombre del plsql a invocar>");
/*
          declareParameter(new SqlParameter("pv_dsestruc_i", OracleTypes.VARCHAR));
          declareParameter(new SqlOutParameter("lcur_registros_i", OracleTypes.CURSOR, new EstructuraMapper()));
          declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
          declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
*/
          compile();

        }


        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
/*
            List result = (List) map.get("lcur_registros_i");
            wrapperResultados.setItemList(result);
*/
            return wrapperResultados;
        }
    }
    
    protected class BorrarFuncionalidad extends CustomStoredProcedure {

        protected BorrarFuncionalidad(DataSource dataSource) {
            super(dataSource, "PKG_CONFG_CUENTA.P_BORRA_FUNCIONALIDADXCTE");
            declareParameter(new SqlParameter("pv_cdelemento_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdsisrol_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdusuario_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdfunciona_i",OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdopera_i",OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
          }


          public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              return mapper.build(map);
          }
      }


    
    protected class FuncionalidadMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            FuncionalidadVO funcionalidadVO = new FuncionalidadVO();
            funcionalidadVO.setCdElemento(rs.getString("CDELEMENTO"));
            funcionalidadVO.setDsElemen(rs.getString("DSELEMEN"));
            funcionalidadVO.setCdSisRol(rs.getString("CDSISROL"));
            funcionalidadVO.setDsSisRol(rs.getString("DSSISROL"));
            funcionalidadVO.setCdUsuario(rs.getString("CDUSUARIO"));
            funcionalidadVO.setDsNombre(rs.getString("DSNOMBRE"));
            funcionalidadVO.setCdFunciona(rs.getString("CDFUNCIONA"));
            funcionalidadVO.setDsFunciona(rs.getString("DSFUNCIONA"));
            funcionalidadVO.setCdOpera(rs.getString("CDOPERA"));
            funcionalidadVO.setDsOpera(rs.getString("DSOPERA"));
            String _swestado=(rs.getString("SWESTADO").equals("S")?"1":"0");
            funcionalidadVO.setSwEstado(_swestado);
            funcionalidadVO.setDsEstado(rs.getString("DSESTADO"));
            
            return funcionalidadVO;
        }
    }
    protected class GetFuncionalidadMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            FuncionalidadVO funcionalidadVO = new FuncionalidadVO();
            funcionalidadVO.setCdElemento(rs.getString("CDELEMENTO"));
            funcionalidadVO.setDsElemen(rs.getString("DSELEMEN"));
            funcionalidadVO.setCdSisRol(rs.getString("CDSISROL"));
            funcionalidadVO.setDsSisRol(rs.getString("DSSISROL"));
            funcionalidadVO.setCdUsuario(rs.getString("CDUSUARIO"));
            funcionalidadVO.setDsNombre(rs.getString("DSNOMBRE"));
            funcionalidadVO.setCdFunciona(rs.getString("CDFUNCIONA"));
            funcionalidadVO.setDsFunciona(rs.getString("DSFUNCIONA")); 
            funcionalidadVO.setSwEstado(rs.getString("SWESTADO"));
            funcionalidadVO.setDsEstado(rs.getString("DSESTADO"));
            funcionalidadVO.setCdOpera(rs.getString("CDOPERA"));
            funcionalidadVO.setDsOpera(rs.getString("DSOPERA"));
            
            return funcionalidadVO;
        }
    }


    
    protected class BuscarFuncionalidadesExportar extends CustomStoredProcedure {

        protected BuscarFuncionalidadesExportar(DataSource dataSource) {
            super(dataSource, "PKG_CONFG_CUENTA.P_OBTIENE_FUNCIONALIDADES");
            
            declareParameter(new SqlParameter("pv_nivel_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_sisrol_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_usuario_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_funciona_i", OracleTypes.VARCHAR));
            
            
         
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new FuncionalidadesMapperExport()));
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
    
    protected class FuncionalidadesMapperExport  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArrayList lista =  new ArrayList(4);
             
            lista.add(rs.getString("DSELEMEN"));         	
        	lista.add(rs.getString("DSSISROL"));
        	lista.add(rs.getString("DSNOMBRE"));
        	lista.add(rs.getString("DSFUNCIONA"));
        	lista.add(rs.getString("DSOPERA"));
        	lista.add(rs.getString("DSESTADO"));
            return lista;
        }
    }
    
    

    protected class ComboObtieneFuncionalidades extends CustomStoredProcedure {

      protected ComboObtieneFuncionalidades(DataSource dataSource) {
                        super(dataSource, "PKG_LISTAS.P_FUNCIONALIDADES" );

                        declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ComboFuncionalidadMapper()));
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
   
    protected class ComboFuncionalidadMapper implements RowMapper{   
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
			FuncionalidadVO funcionalidaVO = new FuncionalidadVO();
			funcionalidaVO.setCdFunciona( rs.getString("CDFUNCIONA"));
			funcionalidaVO.setDsFunciona( rs.getString("DSFUNCIONA"));
			
			
			return funcionalidaVO;
       }
    }
  
}
