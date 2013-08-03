package mx.com.aon.catweb.configuracion.producto.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.catweb.configuracion.producto.atributosVariables.model.AtributosVariablesVO;
import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class AtributoDAO extends AbstractDAO {

	private static Logger logger = Logger.getLogger(AtributoDAO.class);
	
	public static final String OBTIENE_VALORES_ATRIBUTOS = "OBTIENE_VALORES_ATRIBUTOS";
	public static final String ATRIBUTO_PADRE_PRODUCTO 	= "ATRIBUTO_PADRE_PRODUCTO";
	public static final String ATRIBUTO_PADRE_COBERTURA = "ATRIBUTO_PADRE_COBERTURA";
	public static final String ATRIBUTO_PADRE_OBJETO 	= "ATRIBUTO_PADRE_OBJETO";
	//public static final String ATRIBUTO_PADRE_RIESGO 	= "ATRIBUTO_PADRE_RIESGO";
	public static final String ATRIBUTO_PADRE_ROL 	= "ATRIBUTO_PADRE_ROL";
	public static final String GUARDA_ATRIBUTOS_VARIABLES = "GUARDA_ATRIBUTOS_VARIABLES";
	
	protected void initDao() throws Exception {

		addStoredProcedure(OBTIENE_VALORES_ATRIBUTOS, new ObtieneValoresAtributo(getDataSource()));
		addStoredProcedure(ATRIBUTO_PADRE_PRODUCTO, new AtributosPadresProducto(getDataSource()));
		addStoredProcedure(ATRIBUTO_PADRE_COBERTURA, new AtributosPadresCobertura(getDataSource()));
		addStoredProcedure(ATRIBUTO_PADRE_OBJETO, new AtributosPadresObjeto(getDataSource()));
		//addStoredProcedure(ATRIBUTO_PADRE_RIESGO, new AtributosPadresRiesgo(getDataSource()));
		addStoredProcedure(ATRIBUTO_PADRE_ROL, new AtributosPadresRol(getDataSource()));
		addStoredProcedure(GUARDA_ATRIBUTOS_VARIABLES, new GuardarAtributoProducto(getDataSource()));

	}

    protected class ObtieneValoresAtributo extends CustomStoredProcedure {

        protected ObtieneValoresAtributo(DataSource dataSource) {
            super(dataSource, "PKG_WIZARD.LOAD_TABLA_APOYO");
           
            declareParameter(new SqlParameter("pv_ottipotb_i", OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ValoresAtributoMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();

          }

        @Override
        public WrapperResultados mapWrapperResultados(Map map) throws Exception {
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build(map);
              List result = (List) map.get("pv_registro_o");
              wrapperResultados.setItemList(result);
              return wrapperResultados;
        }
      }
	
    protected class ValoresAtributoMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AtributosVariablesVO atributosVariablesVO = new AtributosVariablesVO();
        	
        	atributosVariablesVO.setCdTabla(rs.getString("CDTABLA"));
        	atributosVariablesVO.setDsTabla(rs.getString("DSTABLA"));
        	atributosVariablesVO.setNumeroTabla(rs.getString("NMTABLA"));

            return atributosVariablesVO;
        }
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////
    
    
	protected class AtributosPadresProducto extends CustomStoredProcedure {

		protected AtributosPadresProducto(DataSource dataSource) {
			super(dataSource, "PKG_WIZARD.P_ATRIBVARPOL_PADRE");

			declareParameter(new SqlParameter("pv_cdramo_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdatribu_i",
					OracleTypes.NUMERIC));

			declareParameter(new SqlOutParameter("pv_registro_o",
					OracleTypes.CURSOR, new ListaValores()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",
					OracleTypes.VARCHAR));
			compile();
		}

		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("pv_registro_o");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
		}

	}

	protected class AtributosPadresRiesgo extends CustomStoredProcedure {

		protected AtributosPadresRiesgo(DataSource dataSource) {
			super(dataSource, "");
		}

		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

	}

	protected class AtributosPadresRol extends CustomStoredProcedure {

		protected AtributosPadresRol(DataSource dataSource) {
			super(dataSource, "PKG_WIZARD.P_ATRIBVARROL_PADRE");

			declareParameter(new SqlParameter("pv_cdrol_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdatribu_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i",
					OracleTypes.NUMERIC));

			declareParameter(new SqlOutParameter("pv_registro_o",
					OracleTypes.CURSOR, new ListaValores()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",
					OracleTypes.VARCHAR));
			compile();

		}

		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("pv_registro_o");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
		}

	}

	protected class AtributosPadresCobertura extends CustomStoredProcedure {

		protected AtributosPadresCobertura(DataSource dataSource) {
			super(dataSource, "PKG_WIZARD.P_ATRIBVARGAR_PADRE");

			declareParameter(new SqlParameter("pv_cdramo_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdtipsit_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdatribu_i",
					OracleTypes.NUMERIC));

			declareParameter(new SqlOutParameter("pv_registro_o",
					OracleTypes.CURSOR, new ListaValores()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",
					OracleTypes.VARCHAR));
			compile();

		}

		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("pv_registro_o");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
		}

	}

	protected class AtributosPadresObjeto extends CustomStoredProcedure {

		protected AtributosPadresObjeto(DataSource dataSource) {
			super(dataSource, "PKG_WIZARD.P_ATRIBVAROBJ_PADRE");

			declareParameter(new SqlParameter("pv_cdtipobj_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdatribu_i",
					OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_registro_o",
					OracleTypes.CURSOR, new ListaValores()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",
					OracleTypes.VARCHAR));
			compile();
		}

		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            List result = (List) map.get("pv_registro_o");
            wrapperResultados.setItemList(result);
            return wrapperResultados;
		}

	}

	protected class GuardarAtributoProducto extends CustomStoredProcedure {
		
		protected GuardarAtributoProducto(DataSource dataSource) {
			super(dataSource, "PKG_WIZARD.P_INSERTATRIBPOL");
			
			declareParameter(new SqlParameter("pv_cdramo_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_clavecampo_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_descripcion_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdformato_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_obligatorio_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_maximo_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_minimo_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdtabla_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_emision_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_endosos_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_retarificacion_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cotizador_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swdatcom_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swcomobl_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swcomupd_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swendoso_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swendobl_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdatribu_padre_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmorden_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmagrupa_i",
					OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdcondicvis_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swinsert_i",
					OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdexpres_i",
					OracleTypes.NUMERIC));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o",
					OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",
					OracleTypes.VARCHAR));

            compile();
		}
		
		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			
			return mapper.build(map);
		}
	}
	
	protected class ListaValores implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			LlaveValorVO valores = new LlaveValorVO();
			
			valores.setKey(rs.getString(1));
			valores.setValue(rs.getString(2));
			
			return valores;
		}
	}
}