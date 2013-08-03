package mx.com.aon.catbo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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

public class EnvioMailNotificacionDAO extends AbstractDAO {
	
	@Override
	protected void initDao() throws Exception {
		addStoredProcedure("OBTIENE_FORMATONOTIF", new ObtenerNotificacionMail(getDataSource()));
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
	
	/*
	 * Metodo que obtiene el asunto y el cuerpo del mail como notificacion
	 * */
	protected class ObtenerNotificacionMail extends CustomStoredProcedure {
		protected ObtenerNotificacionMail (DataSource dataSource) {
			super(dataSource, "PKG_NOTIFICACIONES_CATBO.P_OBTIENE_FORMATONOTIF");
			declareParameter(new SqlParameter("pv_cdproceso_i", OracleTypes.NUMERIC));
			
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerNotificacionMailMapper()));
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
	
	protected class ObtenerNotificacionMailMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			NotificacionVO notificacionVO = new NotificacionVO();
			notificacionVO.setDsMensaje(rs.getString("DSMENSAJE"));
			notificacionVO.setDsNotificacion(rs.getString("DSNOTIFICACION"));
			return notificacionVO;
		}
		
	}
}
