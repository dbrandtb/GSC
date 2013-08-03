package mx.com.aon.catbo.service.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

import mx.com.aon.catbo.dao.AbstractDAO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.MediaTO;
import mx.com.aon.catbo.service.ArchivosManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;

public class ArchivosManagerJdbcTemplateImpl extends
		AbstractManagerJdbcTemplateInvoke implements ArchivosManager {

	private LobHandler lobHandler;

	public String borrarArchivos(String pv_nmcaso_i, String pv_nmovimiento_i,
			String pv_nmarchivo_i) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String borrarAtributosFax(String pv_cdtipoar_i, String pv_cdatribu_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList buscarAtributosArchivos(String pv_dsarchivo_i, int start,
			int limit) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String editarGuardarAtributosFax(String pv_cdtipoar_i,
			String pv_cdatribu_i, String pv_dsatribu_i, String pv_swformat_i,
			String pv_nmlmax_i, String pv_nmlmin_i, String pv_swobliga_i,
			String pv_ottabval_i, String pv_status_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public TableModelExport getModel(String pv_dsarchivo_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public TableModelExport getModel2(String pv_dsarchivo_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public FaxesVO getObtieneAtributosFax(String pv_cdtipoar_i,
			String pv_cdatribu_i) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardaArchivos(String pv_nmcaso_i, String pv_nmovimiento_i,
			String pv_nmarchivo_i, String pv_dsarchivo_i, String pv_cdtipoar_i,
			String pv_blarchivo_i, String pv_cdusuario_i)
			throws ApplicationException {

		return null;
	}

	public String guardaAtributosArchivos(String pv_cdtipoar_i,
			String pv_cdatribu_i, String pv_swformat_i, String pv_nmlmax_i,
			String pv_nmlmin_i, String pv_swobliga_i, String pv_status_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardarAtributosFax(String pv_cdtipoar_i,
			String pv_cdatribu_i, String pv_dsatribu_i, String pv_swformat_i,
			String pv_nmlmax_i, String pv_nmlmin_i, String pv_swobliga_i,
			String pv_ottabval_i, String pv_status_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtenerArchivos(String pv_nmcaso_i,
			String pv_nmovimiento_i, int start, int limit)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtieneAtributosFax(String pv_dsarchivo_i, int start,
			int limit) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardaArchivosCaso(final MediaTO mediaTO, final InputStream inputStream, final int contentLength)
			throws Exception {
		HashMap map = new HashMap ();

		InputStreamReader clobReader = new InputStreamReader(inputStream);
		map.put("pv_nmcaso_i", mediaTO.getIdCaso());
		map.put("pv_nmovimiento_i", mediaTO.getNmMovimiento());
		map.put("pv_nmarchivo_i", mediaTO.getIdDocumento());
		map.put("pv_dsarchivo_i", mediaTO.getDsArchivo());
		map.put("pv_cdtipoar_i", mediaTO.getCdTipoArchivo());
		map.put("pv_blarchivo_i", clobReader);
		map.put("pv_cdusuario_i", "herbe");
		
		WrapperResultados res = returnBackBoneInvoke(map, "GUARDA_ARCHIVOS");
		return res.getMsgText();
	}

	public String actualizarAtributosFax(String pv_cdtipoar_i,
			String pv_cdatribu_i, String pv_dsatribu_i, String pv_swformat_i,
			String pv_nmlmax_i, String pv_nmlmin_i, String pv_swobliga_i,
			String pv_ottabval_i, String pv_status_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
