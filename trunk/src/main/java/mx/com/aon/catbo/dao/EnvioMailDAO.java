package mx.com.aon.catbo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import mx.com.aon.catbo.model.MailVO;
import mx.com.aon.catbo.model.NotificacionVO;
import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.dao.CustomStoredProcedure;
import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;

public class EnvioMailDAO extends AbstractDAO {

	@Override
	protected void initDao() throws Exception {
		addStoredProcedure("OBTENER_DATOS_MAIL", new ObtenerDatosMail(getDataSource()));
		addStoredProcedure("BUSCAR_PARAMETROS", new BuscarParametros(getDataSource()));
	}

	protected class ObtenerDatosMail extends mx.com.aon.portal.dao.CustomStoredProcedure {
		protected ObtenerDatosMail (DataSource dataSource) {
			super(dataSource, "PKG_CATBO.P_Obtiene_Datos_Mail");
			declareParameter(new SqlParameter("pv_nmcaso_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerDatosMailMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception {
			WrapperResultadosGeneric mapper = new WrapperResultadosGeneric ();
			WrapperResultados res = mapper.build(map);
			List result = (List) map.get("pv_registro_o");
			res.setItemList(result);
			return res;
		}
		
	}

	protected class ObtenerDatosMailMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			MailVO mailVO = new MailVO();
			mailVO.setEstado(rs.getString("ESTADO"));
			mailVO.setFechaActualizacion(rs.getString("FECHAACTUALIZACION"));
			mailVO.setFechaRegistro(rs.getString("FECHAREGISTRO"));
			mailVO.setIngresadoPor(rs.getString("INGRESADOPOR"));
			mailVO.setNumeroCaso(rs.getString("NUMEROCASO"));
			mailVO.setObservacion(rs.getString("OBSERVACION"));
			mailVO.setPrioridad(rs.getString("PRIORIDAD"));
			mailVO.setProceso(rs.getString("PROCESO"));
			mailVO.setSolicitante(rs.getString("SOLICITANTE"));
			return mailVO;
		}
		
	}
	
	protected class BuscarParametros extends CustomStoredProcedure {
    	protected BuscarParametros(DataSource dataSource) {
    		
    		super(dataSource, "Pkg_Utility.P_GET_TPARAGEN_SELECTIVE");
            
            declareParameter(new SqlParameter("pi_nbparam", OracleTypes.VARCHAR));
            
            declareParameter(new SqlOutParameter("pi_valor", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
    		
    	}
    	
    	public WrapperResultados mapWrapperResultados(Map map) throws Exception {
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            WrapperResultados wrapperResultados = mapper.build(map);
            wrapperResultados.setItemMap(new HashMap<String, Object>());
            wrapperResultados.getItemMap().put("pi_valor", map.get("pi_valor"));  
            return wrapperResultados;
        }
    }

	
}	
