package mx.com.aon.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class CatalogosDAO extends AbstractDAO{

    private static Logger logger = Logger.getLogger(CatalogosDAO.class);


    protected void initDao() throws Exception {
        addStoredProcedure("COMBO_OBTIENE_PERSONA_USUARIO",new ComboObtienePersonaUsuario(getDataSource()));
        addStoredProcedure("COMBO_OBTIENE_LISTA",new ComboObtieneLista(getDataSource()));
    }


    protected class ComboObtienePersonaUsuario extends CustomStoredProcedure {

        protected ComboObtienePersonaUsuario(DataSource dataSource) {
            super(dataSource, "PKG_LISTAS.P_PERSONA_USUARIO");
            
            declareParameter(new SqlParameter("pv_cdusuario",OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("po_registro", OracleTypes.CURSOR, new ComboPersonaUsuarioMapper()));
            declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("po_title", OracleTypes.VARCHAR));
            compile();
          }

        @SuppressWarnings("unchecked")
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("po_registro");
              wrapperResultados.setItemList(result);
              return wrapperResultados;
          }
    }
    
    protected class ComboObtieneLista extends CustomStoredProcedure {

        protected ComboObtieneLista(DataSource dataSource) {
            super(dataSource, "PKG_LISTAS.P_GET_LISTAS");
            declareParameter(new SqlParameter("pv_cdtabla_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_valanter_i",OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_valantant_i",OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("po_registro", OracleTypes.CURSOR, new ComboObtieneListaMapper()));
            declareParameter(new SqlOutParameter("po_msg_id", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("p_out_title", OracleTypes.VARCHAR));
            compile();
          }

        @SuppressWarnings("unchecked")
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("po_registro");
              wrapperResultados.setItemList(result);
              return wrapperResultados;
          }
    }
    
    
    protected class ComboPersonaUsuarioMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			BaseObjectVO baseObjectVO = new BaseObjectVO();

			baseObjectVO.setValue(rs.getString(1));
			baseObjectVO.setLabel(rs.getString(2));
			
			return baseObjectVO;
		}
    }
    
    protected class ComboObtieneListaMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			BaseObjectVO baseObjectVO = new BaseObjectVO();

			baseObjectVO.setValue(rs.getString(1));
			baseObjectVO.setLabel(rs.getString(2));
			
			return baseObjectVO;
		}
    }
    
}


