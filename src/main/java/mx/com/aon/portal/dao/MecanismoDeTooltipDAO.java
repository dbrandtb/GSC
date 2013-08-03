package mx.com.aon.portal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal.model.MecanismoDeTooltipVO;
import mx.com.aon.portal.util.WrapperResultados;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class MecanismoDeTooltipDAO extends AbstractDAO {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(MecanismoDeTooltipDAO.class);
	
    protected void initDao() throws Exception {
		addStoredProcedure("AGREGAR_GUARDAR_MECANISMO_TOOLTIP", new AgregarMecanismoDeTooltip(getDataSource()));
		addStoredProcedure("BORRAR_MECANISMO_TOOLTIP", new BorrarMecanismoDeTooltip(getDataSource()));
		addStoredProcedure("BUSCAR_MECANISMO_TOOLTIP", new BuscarMecanismoDeTooltip(getDataSource()));
		addStoredProcedure("COPIAR_MECANISMO_TOOLTIP", new CopiarMecanismoDeTooltip(getDataSource()));
		addStoredProcedure("OBTIENE_MECANISMO_TOOLTIP", new ObtenerMecanismoDeTooltip(getDataSource()));
		addStoredProcedure("BUSCAR_MECANISMO_TOOLTIP_PAGE", new ObtenerTooltipsPagina(getDataSource()));
	}

    protected class AgregarMecanismoDeTooltip extends CustomStoredProcedure{

		protected AgregarMecanismoDeTooltip (DataSource dataSource) {
			super (dataSource, "PKG_TRADUC.P_INSERTA_PROPIEDADES");
			declareParameter(new SqlParameter("pv_idobjeto_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nbobjeto_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_idtitulo_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_tipoobj_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_etiqueta_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tooltp_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_gayuda_i", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_dayuda_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_lang_code_i", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

			compile();
		}
    	@SuppressWarnings("unchecked")
		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
    		WrapperResultadosGeneric mapper = new WrapperResultadosGeneric ();
			return mapper.build(map);
		}
    	
    }
    
    protected class BorrarMecanismoDeTooltip extends CustomStoredProcedure {
    	protected BorrarMecanismoDeTooltip (DataSource dataSource) {
    		super(dataSource, "PKG_TRADUC.P_BORRA_PROPIEDADES");
    		declareParameter(new SqlParameter("pv_idobjeto_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_lang_code_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));

    		compile();
    	}

		@SuppressWarnings("unchecked")
		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			return mapper.build(map);
		}
    }
    
    protected class BuscarMecanismoDeTooltip extends CustomStoredProcedure {
    	protected BuscarMecanismoDeTooltip (DataSource dataSource) {
			super(dataSource, "PKG_TRADUC.P_OBTIENE_PROPIEDADES");
			declareParameter(new SqlParameter("pv_nbobjeto", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_lang_code", OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_dstitulo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new BuscarMecanismoDeTooltipMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
		@SuppressWarnings("unchecked")
		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados res = mapper.build(map);
			List result = (List)map.get("pv_registro_o");
			res.setItemList(result);
			return res;
		}
    	
    }
    
    protected class BuscarMecanismoDeTooltipMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			MecanismoDeTooltipVO mecanismoDeTooltipVO = new MecanismoDeTooltipVO ();
			mecanismoDeTooltipVO.setIdTitulo(rs.getString("IDTITULO"));
			mecanismoDeTooltipVO.setDsTitulo(rs.getString("DSTITULO"));
			mecanismoDeTooltipVO.setIdObjeto(rs.getString("IDOBJETO"));
			mecanismoDeTooltipVO.setNbObjeto(rs.getString("NBOBJETO"));
			mecanismoDeTooltipVO.setFgTipoObjeto(rs.getString("FGTIPOBJ"));
			mecanismoDeTooltipVO.setNbEtiqueta(rs.getString("NBETIQUE"));
			mecanismoDeTooltipVO.setDsTooltip(rs.getString("DSTOOLTP"));
			mecanismoDeTooltipVO.setFgAyuda(rs.getString("FGAYUDA"));
			mecanismoDeTooltipVO.setDsAyuda(rs.getString("DSAYUDA"));
			mecanismoDeTooltipVO.setLang_Code(rs.getString("LANG_CODE"));
			mecanismoDeTooltipVO.setNbCajaIzquieda(rs.getString("NBCAJAIZQUIERDA"));
			mecanismoDeTooltipVO.setNbCajaDerecha(rs.getString("NBCAJADERECHA"));

			return mecanismoDeTooltipVO;
		}
    	
    }
    
    protected class CopiarMecanismoDeTooltip extends CustomStoredProcedure {
    	protected CopiarMecanismoDeTooltip (DataSource dataSource) {
    		super(dataSource, "PKG_TRADUC.P_COPIA_PROPIEDADES");
    		declareParameter(new SqlParameter("pv_idobjeto_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_lang_code_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_idobjeto_new_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}

		@SuppressWarnings("unchecked")
		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric ();
			return mapper.build(map);
		}
    	
    }
    
    protected class ObtenerMecanismoDeTooltip extends CustomStoredProcedure {
    	protected ObtenerMecanismoDeTooltip (DataSource dataSource) {
    		super(dataSource, "PKG_TRADUC.P_OBTIENE_PROPIEDADES_REG");
    		declareParameter(new SqlParameter("pv_idobjeto_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlParameter("pv_lang_code_i", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("po_registro", OracleTypes.CURSOR, new ObtenerMecanismoDeTooltipMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
		@SuppressWarnings("unchecked")
		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados res = mapper.build(map);
			List result = (List) map.get("po_registro");
			res.setItemList(result);
			return res;
		}
    	
    }
    
    protected class ObtenerMecanismoDeTooltipMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			MecanismoDeTooltipVO mecanismoDeTooltipVO = new MecanismoDeTooltipVO();
			mecanismoDeTooltipVO.setIdTitulo(rs.getString("IDTITULO"));
			mecanismoDeTooltipVO.setDsTitulo(rs.getString("DSTITULO"));
			mecanismoDeTooltipVO.setIdObjeto(rs.getString("IDOBJETO"));
			mecanismoDeTooltipVO.setNbObjeto(rs.getString("NBOBJETO"));
			mecanismoDeTooltipVO.setFgTipoObjeto(rs.getString("FGTIPOBJ"));
			mecanismoDeTooltipVO.setNbEtiqueta(rs.getString("NBETIQUE"));
			mecanismoDeTooltipVO.setDsTooltip(rs.getString("DSTOOLTP"));
			mecanismoDeTooltipVO.setFgAyuda(rs.getString("FGAYUDA"));
			mecanismoDeTooltipVO.setDsAyuda(rs.getString("DSAYUDA"));
			mecanismoDeTooltipVO.setLang_Code(rs.getString("LANG_CODE"));
			mecanismoDeTooltipVO.setNbCajaIzquieda(rs.getString("NBCAJAIZQUIERDA"));
			mecanismoDeTooltipVO.setNbCajaDerecha(rs.getString("NBCAJADERECHA"));
			return mecanismoDeTooltipVO;
		}
    	
    }
    
    protected class ObtenerTooltipsPagina extends CustomStoredProcedure {
    	protected ObtenerTooltipsPagina (DataSource dataSource) {
    		super(dataSource, "PKG_TRADUC.P_OBTIENE_PROPIEDADES");
    		declareParameter(new SqlParameter("pv_dstitulo_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("pv_lang_code", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new BuscarMecanismoDeTooltipMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.NUMERIC));
    		compile();
    	}
		@SuppressWarnings("unchecked")
		@Override
		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
			WrapperResultados res = mapper.build(map);
			List result = (List)map.get("pv_registro_o");
			res.setItemList(result);
			return res;
		}
    	
    }
    
}