package mx.com.gseguros.portal.despachador.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.despachador.dao.DespachadorDAO;
import mx.com.gseguros.utils.Utils;
import oracle.jdbc.driver.OracleTypes;

public class DespachadorDAOImpl extends AbstractManagerDAO implements DespachadorDAO {
	private final static Logger logger = LoggerFactory.getLogger(DespachadorDAOImpl.class);
	
	@Override
	public Map<String, String> recuperarDatosClasificacionSucursal (String cdunieco) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("cdunieco", cdunieco);
		Map<String, Object> procRes = ejecutaSP(new RecuperarDatosClasificacionSucursalSP(getDataSource()), params);
		List<Map<String, String>> lista = (List<Map<String, String>>) procRes.get("pv_registro_o");
		if (lista == null || lista.size() == 0) {
			throw new ApplicationException(Utils.join("No hay datos de algoritmo para la sucursal ", cdunieco));
		}
		logger.debug(Utils.log("recuperarDatosClasificacionSucursal datos: ", lista.get(0)));
		return lista.get(0);
	}
	
	protected class RecuperarDatosClasificacionSucursalSP extends StoredProcedure {
		protected RecuperarDatosClasificacionSucursalSP (DataSource dataSource) {
			super(dataSource, "PKG_MESACONTROL_PRE.P_GET_TUNICLAS");
			declareParameter(new SqlParameter("cdunieco", OracleTypes.VARCHAR));
			String[] cols = new String[] {
					"CDUNIECO" , "DSUNIECO" , "CDUNIZON" , "DESCRIPC",
					"NMCAPACI" , "CDNIVEL"  , "SWAPOYO"  , "SWACTIVA"};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarRolTrabajoEstatus (String cdtipflu, String cdflujomc, String estatus) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("estatus"   , estatus);
		Map<String, Object> procRes = ejecutaSP(new RecuperarRolTrabajoEstatusSP(getDataSource()), params);
		String cdsisrol = (String) procRes.get("pv_cdsisrol_o"),
               error = (String) procRes.get("pv_error_o");
		if (StringUtils.isNotBlank(error)) {
		    throw new ApplicationException(error);
		}
		logger.debug(Utils.join("recuperarRolTrabajoEstatus cdsisrol: ", cdsisrol));
		return cdsisrol;
	}
	
	protected class RecuperarRolTrabajoEstatusSP extends StoredProcedure {
		protected RecuperarRolTrabajoEstatusSP (DataSource dataSource) {
			super(dataSource, "P_DSPCH_GET_ROL_TRABAJO_STATUS");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estatus"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdsisrol_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_error_o"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String, String>> recuperarPermisosTramite (String cdtipflu, String cdflujomc, String cdramo, String cdtipsit) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("cdramo"    , cdramo);
		params.put("cdtipsit"  , cdtipsit);
		Map<String, Object> procRes = ejecutaSP(new RecuperarPermisosTramiteSP(getDataSource()), params);
		List<Map<String, String>> lista = (List<Map<String, String>>) procRes.get("pv_registro_o");
		if (lista == null) {
			lista = new ArrayList<Map<String, String>>();
		}
		logger.debug(Utils.log("recuperarPermisosTramite lista: ", lista));
		return lista;
	}
	
	protected class RecuperarPermisosTramiteSP extends StoredProcedure {
		protected RecuperarPermisosTramiteSP (DataSource dataSource) {
			super(dataSource, "PKG_MESACONTROL_PRE.P_GET_TUNIPERM");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"  , OracleTypes.VARCHAR));
			String[] cols = new String[] {
					 "CDTIPFLU" , "DSTIPFLU" , "CDFLUJOMC" , "DSFLUJOMC" , "CDRAMO"   , "DSRAMO"   , "CDTIPSIT",
					 "DSTIPSIT" , "SWMATEMI" , "SWMATSUS"  , "SWSUCPRI"  , "SWSUCSEC" , "SWSUCOFI" , "COMMENTS"
		             };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarTipoTurnadoEstatus (String cdtipflu, String cdflujomc, String estatus) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("estatus"   , estatus);
		Map<String, Object> procRes = ejecutaSP(new RecuperarTipoTurnadoEstatusSP(getDataSource()), params);
		String cdtipasig = (String) procRes.get("pv_cdtipasig_o");
		logger.debug(Utils.join("recuperarTipoTurnadoEstatus cdtipasig: ", cdtipasig));
		return cdtipasig;
	}
	
	protected class RecuperarTipoTurnadoEstatusSP extends StoredProcedure {
		protected RecuperarTipoTurnadoEstatusSP (DataSource dataSource) {
			super(dataSource, "P_DSPCH_GET_TIPASIG_STATUS");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estatus"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdtipasig_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"    , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"     , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public boolean esStatusFinal (String ntramite, String status) throws Exception {
		 Map<String, String> params = new LinkedHashMap<String, String>();
		 params.put("ntramite" , ntramite);
		 params.put("status"   , status);
		 Map<String, Object> procRes = ejecutaSP(new EsStatusFinalSP(getDataSource()), params);
		 boolean esStatusFinal = "S".equals((String)procRes.get("pv_swfinnode_o"));
		 logger.debug("esStatusFinal: {}", esStatusFinal);
		 return esStatusFinal;
	}
	
	protected class EsStatusFinalSP extends StoredProcedure {
		protected EsStatusFinalSP (DataSource dataSource) {
			super(dataSource, "P_DSPCH_ES_STATUS_FINAL");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_swfinnode_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"    , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"     , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void borrarUsuarioYSucursalEncargado (String ntramite) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("ntramite" , ntramite);
		ejecutaSP(new BorrarUsuarioYSucursalEncargadoSP(getDataSource()), params);
	}
	
	protected class BorrarUsuarioYSucursalEncargadoSP extends StoredProcedure {
		protected BorrarUsuarioYSucursalEncargadoSP (DataSource dataSource) {
			super(dataSource, "P_DSPCH_BORRAR_USER_ENCARGADO");
			declareParameter(new SqlParameter("ntramite", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public int recuperarConteoTramitesSucursal (String cdunidspch) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("cdunidspch", cdunidspch);
		Map<String, Object> procRes = ejecutaSP(new RecuperarConteoTramitesSucursalSP(getDataSource()), params);
		int conteo = Integer.parseInt((String) procRes.get("pv_count_o"));
		logger.debug("recuperarConteoTramitesSucursal: {}", conteo);
		return conteo;
	}
	
	protected class RecuperarConteoTramitesSucursalSP extends StoredProcedure {
		protected RecuperarConteoTramitesSucursalSP (DataSource dataSource) {
			super(dataSource, "P_DSPCH_GET_COUNT_CDUNIDSPCH");
			declareParameter(new SqlParameter("cdunidspch", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_count_o"  , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	 @Override
	 public String recuperarUsuarioParaRegresarTramite (String ntramite, String cdunidspch, String cdsisrol) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("ntramite"   , ntramite);
		params.put("cdunidspch" , cdunidspch);
		params.put("cdsisrol"   , cdsisrol);
		Map<String, Object> procRes = ejecutaSP(new RecuperarUsuarioParaRegresarTramiteSP(getDataSource()), params);
		String cdusuari = (String) procRes.get("pv_cdusuari_o");
		if (StringUtils.isBlank(cdusuari)) {
			cdusuari = null;
		}
		logger.debug("recuperarUsuarioParaRegresarTramite cdusuari: {}", cdusuari);
		return cdusuari;
	}
	
	protected class RecuperarUsuarioParaRegresarTramiteSP extends StoredProcedure {
		protected RecuperarUsuarioParaRegresarTramiteSP (DataSource dataSource) {
			super(dataSource, "P_DSPCH_GET_USER_REGRESO");
			declareParameter(new SqlParameter("ntramite"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunidspch" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdusuari_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarSiguienteUsuarioCarrusel (String cdunidspch, String cdsisrol, boolean soloUsuariomatriz, String ntramite) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("pv_cdunidspch_i" , cdunidspch);
		params.put("pv_cdsisrol_i"   , cdsisrol);
        params.put("pv_swsusmat_i"   , soloUsuariomatriz ? "S" : "N");
        params.put("pv_ntramite_i"   , ntramite);
		Map<String, Object> procRes = ejecutaSP(new RecuperarSiguienteUsuarioCarruselSP(getDataSource()), params);
		List<Map<String, String>> lista = (List<Map<String, String>>) procRes.get("pv_registro_o");
		if (lista == null) {
			lista = new ArrayList<Map<String, String>>();
		}
		logger.debug(Utils.log("recuperarSiguienteUsuarioCarrusel lista: ", lista));
		String cdusuari = null;
		if (lista.size() == 1) { // solo hay un usuario en el carrusel, se asigna a el
			logger.debug("El primero y unico es el que se toma");
			cdusuari = lista.get(0).get("CDUSUARI");
		} else if (lista.size() > 1) {
			// ciclamos la lista:
			lista.add(lista.get(0));
			// una lista: 1, 2, 3
			// se convierte en: 1, 2, 3, 1
			// esto es por si se encuentra en el 3, le damos next y nos regresa 1 en lugar de indexOutOfBounds
			
			int posicionUltimo = -1;
			for (int i = 0; i < lista.size(); i++) {
				if ("S".equals(lista.get(i).get("SWULTIMO"))) {
					posicionUltimo = i;
					break;
				}
			}
			if (posicionUltimo == -1) { // No hay ultimo
				logger.debug("No hay ultimo, se toma el primero");
				cdusuari = lista.get(0).get("CDUSUARI");
			} else {
				logger.debug("Se toma el siguiente al ultimo (el ultimo fue: {})", lista.get(posicionUltimo));
				cdusuari = lista.get(posicionUltimo + 1).get("CDUSUARI");
			}
		} else {
			logger.debug("La lista esta vacia, no se toma a nadie");
		}
		logger.debug("recuperarSiguienteUsuarioCarrusel cdusuari: {}", cdusuari);
		return cdusuari;
	}
	
	protected class RecuperarSiguienteUsuarioCarruselSP extends StoredProcedure {
		protected RecuperarSiguienteUsuarioCarruselSP (DataSource dataSource) {
			super(dataSource, "P_DSPCH_CARRUSEL");
			declareParameter(new SqlParameter("pv_cdunidspch_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsisrol_i"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_swsusmat_i"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_ntramite_i"   , OracleTypes.VARCHAR));
			String[] cols = new String[] { "CDUSUARI", "SWULTIMO" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarSiguienteUsuarioCarga (String cdunidspch, String cdsisrol, boolean soloUsuariomatriz, String ntramite) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("pv_cdunidspch_i" , cdunidspch);
		params.put("pv_cdsisrol_i"   , cdsisrol);
        params.put("pv_swsusmat_i"   , soloUsuariomatriz ? "S" : "N");
        params.put("pv_ntramite_i"   , ntramite);
		Map<String, Object> procRes = ejecutaSP(new RecuperarSiguienteUsuarioCargaSP(getDataSource()), params);
		List<Map<String, String>> lista = (List<Map<String, String>>) procRes.get("pv_registro_o");
		if (lista == null) {
			lista = new ArrayList<Map<String, String>>();
		}
		logger.debug(Utils.log("recuperarSiguienteUsuarioCarga lista: ", lista));
		String cdusuari = null;
		if (lista.size() == 1) {
			logger.debug("El primero y unico es el que se toma");
			cdusuari = lista.get(0).get("CDUSUARI");
		} else if (lista.size() > 1) {
			logger.debug("Se toma el primero porque vienen ordenados por tareas y luego por cdusuari");
			cdusuari = lista.get(0).get("CDUSUARI");
		} else {
			logger.debug("La lista esta vacia, no se toma a nadie");
		}
		logger.debug("recuperarSiguienteUsuarioCarga cdusuari: {}", cdusuari);
		return cdusuari;
	}
	
	protected class RecuperarSiguienteUsuarioCargaSP extends StoredProcedure {
		protected RecuperarSiguienteUsuarioCargaSP (DataSource dataSource) {
			super(dataSource, "P_DSPCH_CARGA");
			declareParameter(new SqlParameter("pv_cdunidspch_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsisrol_i"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_swsusmat_i"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_ntramite_i"   , OracleTypes.VARCHAR));
			String[] cols = new String[] { "CDUSUARI", "NMTAREAS" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String, String>> recuperarSucursalesParaEscalamiento (String cdtipflu, String cdflujomc, String zona, int nivel,
			String cdsisrol) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("zona"      , zona);
		params.put("nivel"     , String.valueOf(nivel));
		params.put("cdsisrol"  , cdsisrol);
		Map<String, Object> procRes = ejecutaSP(new RecuperarSucursalesParaEscalamientoSP(getDataSource()), params);
		List<Map<String, String>> lista = (List<Map<String, String>>) procRes.get("pv_registro_o");
		if (lista == null) {
			lista = new ArrayList<Map<String, String>>();
		}
		logger.debug(Utils.log("recuperarSucursalesParaEscalamiento lista: ", lista));
		return lista;
	}
	
	protected class RecuperarSucursalesParaEscalamientoSP extends StoredProcedure {
		protected RecuperarSucursalesParaEscalamientoSP (DataSource dataSource) {
			super(dataSource, "P_DSPCH_GET_SUCURSALES_ESCALAR");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("zona"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nivel"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol"  , OracleTypes.VARCHAR));
			String[] cols = new String[] {
					 "CDUNIECO", "ZONA", "NIVEL", "CAPACIDAD", "CAPACIDAD_OCUPADA", "CAPACIDAD_LIBRE"
		             };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String, String> recuperarCualquierSucursalNivel (String cdtipflu, String cdflujomc, String zona, int nivel) throws Exception {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("cdtipflu"  , cdtipflu);
		params.put("cdflujomc" , cdflujomc);
		params.put("zona"      , zona);
		params.put("nivel"     , String.valueOf(nivel));
		Map<String, Object> procRes = ejecutaSP(new RecuperarCualquierSucursalNivelSP(getDataSource()), params);
		List<Map<String, String>> lista = (List<Map<String, String>>) procRes.get("pv_registro_o");
		if (lista == null || lista.size() == 0) {
			throw new ApplicationException(Utils.join("No hay sucursal para la zona ", zona));
		}
		logger.debug(Utils.log("recuperarCualquierSucursalNivel sucursal: ", lista.get(0)));
		return lista.get(0);
	}
	
	protected class RecuperarCualquierSucursalNivelSP extends StoredProcedure {
		protected RecuperarCualquierSucursalNivelSP (DataSource dataSource) {
			super(dataSource, "P_DSPCH_GET_SUCURSAL_ZONA");
			declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("zona"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nivel"     , OracleTypes.VARCHAR));
			String[] cols = new String[] { "CDUNIECO", "NIVEL", "CAPACIDAD", "ZONA"};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String recuperarCdtipramFlujo (String cdtipflu, String cdflujomc) throws Exception {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("cdtipflu"  , cdtipflu);
        params.put("cdflujomc" , cdflujomc);
        Map<String, Object> procRes = ejecutaSP(new RecuperarCdtipramFlujoSP(getDataSource()), params);
        String cdtipram = (String) procRes.get("pv_cdtipram_o");
        if (StringUtils.isBlank(cdtipram)) {
            throw new ApplicationException("No existe tipo de producto para el flujo de proceso");
        }
        logger.debug("recuperarCdtipramFlujo cdtipram: {}", cdtipram);
        return cdtipram;
    }
    
    protected class RecuperarCdtipramFlujoSP extends StoredProcedure {
        protected RecuperarCdtipramFlujoSP (DataSource dataSource) {
            super(dataSource, "P_DSPCH_GET_CDTIPRAM_FLUJO");
            declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_cdtipram_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
        }
    }
    
    @Override
    public List<Map<String, String>> recuperarSucursalesParaDespachar (String cdtipram, String zona, String nivel, String cdunieco,
            String cdsisrol, String ntramite, String cdtipflu, String cdflujomc, String cdramo, String cdtipsit) throws Exception {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("cdtipram"  , cdtipram);
        params.put("zona"      , zona);
        params.put("nivel"     , nivel);
        params.put("cdunieco"  , cdunieco);
        params.put("cdsisrol"  , cdsisrol);
        params.put("ntramite"  , ntramite);
        params.put("cdtipflu"  , cdtipflu);
        params.put("cdflujomc" , cdflujomc);
        params.put("cdramo"    , cdramo);
        params.put("cdtipsit"  , cdtipsit);
        Map<String, Object> procRes = ejecutaSP(new RecuperarSucursalesParaDespacharSP(getDataSource()), params);
        List<Map<String, String>> lista = (List<Map<String, String>>) procRes.get("pv_registro_o");
        if (lista == null) {
            lista = new ArrayList<Map<String, String>>();
        }
        logger.debug(Utils.log("recuperarSucursalesParaDespachar lista = ", lista));
        return lista;
    }
    
    protected class RecuperarSucursalesParaDespacharSP extends StoredProcedure {
        protected RecuperarSucursalesParaDespacharSP (DataSource dataSource) {
            super(dataSource, "P_DSPCH_GET_SUCURSALES_DSPCH");
            declareParameter(new SqlParameter("cdtipram"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("zona"      , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nivel"     , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdsisrol"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("ntramite"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdtipsit"  , OracleTypes.VARCHAR));
            String[] cols = new String[] {
                    "CDUNIECO", "ZONA", "CAPACIDAD", "NIVEL", "SWAPOYO", "SWACTIVA", "CAPACIDAD_OCUPADA"
            };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
        }
    }
    
    @Override
    public Map<String, String> recuperarUsuarioRegreso (String cdtipram, String cdsisrol, String ntramite) throws Exception {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("cdtipram" , cdtipram);
        params.put("cdsisrol" , cdsisrol);
        params.put("ntramite" , ntramite);
        Map<String, Object> procRes = ejecutaSP(new RecuperarUsuarioRegresoSP(getDataSource()), params);
        List<Map<String, String>> lista = (List<Map<String, String>>) procRes.get("pv_registro_o");
        Map<String, String> usuario = null;
        if (lista != null && lista.size() > 0) {
            usuario = lista.get(0);
        }
        logger.debug("recuperarUsuarioRegreso usuario: {}", usuario);
        return usuario;
    }
    
    protected class RecuperarUsuarioRegresoSP extends StoredProcedure {
        protected RecuperarUsuarioRegresoSP (DataSource dataSource) {
            super(dataSource, "P_DSPCH_GET_USUARIO_REGRESO");
            declareParameter(new SqlParameter("cdtipram" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
            String[] cols = new String[] { "CDUSUARI", "CDUNIECO", "ZONA", "NIVEL" };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
        }
    }
    
    @Override
    public String recuperarStatusSustituto (String cdtipflu, String cdflujomc, String zona, String status) throws Exception {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("cdtipflu"  , cdtipflu);
        params.put("cdflujomc" , cdflujomc);
        params.put("zona"      , zona);
        params.put("status"    , status);
        Map<String, Object> procRes = ejecutaSP(new RecuperarStatusSustitutoSP(getDataSource()), params);
        String sustituto = (String) procRes.get("pv_sustituto_o");
        logger.debug("recuperarStatusSustituto sustituto: {}", sustituto);
        return sustituto;
    }
    
    protected class RecuperarStatusSustitutoSP extends StoredProcedure {
        protected RecuperarStatusSustitutoSP (DataSource dataSource) {
            super(dataSource, "P_DSPCH_GET_STATUS_SUSTITUTO");
            declareParameter(new SqlParameter("cdtipflu"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("zona"      , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("status"    , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_sustituto_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"    , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"     , OracleTypes.VARCHAR));
            compile();
        }
    }
    
    @Override
    public void cerrarHistorialTramite (String ntramite, Date fecha, String cdusuari, String cdsisrol, String status) throws Exception {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("ntramite" , ntramite);
        params.put("fecha"    , fecha);
        params.put("cdusuari" , cdusuari);
        params.put("cdsisrol" , cdsisrol);
        params.put("status"   , status);
        ejecutaSP(new CerrarHistorialTramiteSP(getDataSource()), params);
    }
    
    protected class CerrarHistorialTramiteSP extends StoredProcedure {
        protected CerrarHistorialTramiteSP (DataSource dataSource) {
            super(dataSource, "P_DSPCH_CERRAR_THMESACONTROL");
            declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("fecha"    , OracleTypes.TIMESTAMP));
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
            compile();
        }
    }
    
    @Override
    public String recuperarSucursalUsuarioPorTramite (String cdusuari, String ntramite) throws Exception {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("cdusuari" , cdusuari);
        params.put("ntramite" , ntramite);
        Map<String, Object> procRes = ejecutaSP(new RecuperarSucursalUsuarioPorTramiteSP(getDataSource()), params);
        String sucursal = (String) procRes.get("pv_cdunieco_o");
        logger.debug("recuperarSucursalUsuarioPorTramite sucursal: {}", sucursal);
        return sucursal;
    }
    
    protected class RecuperarSucursalUsuarioPorTramiteSP extends StoredProcedure {
        protected RecuperarSucursalUsuarioPorTramiteSP (DataSource dataSource) {
            super(dataSource, "P_DSPCH_GET_SUCURSAL_USR_TRA");
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_cdunieco_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
        }
    }
    
    @Override
    public String recuperarNombreUsuario (String cdusuari) throws Exception {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("cdusuari" , cdusuari);
        Map<String, Object> procRes = ejecutaSP(new RecuperarNombreUsuarioSP(getDataSource()), params);
        String nombre = (String) procRes.get("pv_nombre_o");
        logger.debug("recuperarNombreUsuario nombre: {}", nombre);
        return nombre;
    }
    
    protected class RecuperarNombreUsuarioSP extends StoredProcedure {
        protected RecuperarNombreUsuarioSP (DataSource dataSource) {
            super(dataSource, "P_DSPCH_GET_NOMBRE_USUARIO");
            declareParameter(new SqlParameter("cdusuari", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_nombre_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
            compile();
        }
    }
    
    @Override
    public List<Map<String, String>> recuperarHistorialMesa (String ntramite) throws Exception {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("ntramite", ntramite);
        Map<String, Object> procRes = ejecutaSP(new RecuperarHistorialMesaSP(getDataSource()), params);
        List<Map<String, String>> lista = (List<Map<String, String>>) procRes.get("pv_registro_o");
        if (lista == null) {
            lista = new ArrayList<Map<String, String>>();
        }
        logger.debug("recuperarHistorialMesa lista: {}", lista);
        return lista;
    }
    
    protected class RecuperarHistorialMesaSP extends StoredProcedure {
        protected RecuperarHistorialMesaSP (DataSource dataSource) {
            super(dataSource, "P_DSPCH_GET_THMESACONTROL");
            declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
            String[] cols = new String[] {
                    "FEFECHA",
                    "NTRAMITE",
                    "CDUSUARI",
                    "CDSISROL",
                    "STATUS",
                    "CDUNIDSPCH",
                    "CDTIPASIG",
                    "CDUSUARI_FIN",
                    "CDSISROL_FIN",
                    "STATUS_FIN",
                    "FEFECHA_FIN"
                    };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
        }
    }
    
    @Override
    public Map<String, String> recuperarAgenteDestino (String ntramite) throws Exception {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("ntramite", ntramite);
        Map<String, Object> procRes = ejecutaSP(new RecuperarAgenteDestinoSP(getDataSource()), params);
        List<Map<String, String>> lista = (List<Map<String, String>>) procRes.get("pv_registro_o");
        Map<String, String> agente = null;
        if (lista != null && lista.size() > 0) {
            agente = lista.get(0);
        } else {
            throw new ApplicationException("No hay agente activo para turnar el tr\u00e1mite");
        }
        logger.debug("recuperarAgenteDestino agente: {}", agente);
        return agente;
    }
    
    protected class RecuperarAgenteDestinoSP extends StoredProcedure {
        protected RecuperarAgenteDestinoSP (DataSource dataSource) {
            super(dataSource, "P_DSPCH_GET_AGENTE_DESTINO");
            declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
            String[] cols = new String[] { "NTRAMITE", "CDAGENTE", "CDUSUARI", "CDUNIECO", "CDUNISLD", "CDTIPRAM" };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
        }
    }
    
    @Override
    public String recuperarSucursalUsuarioPorTipoTramite (String cdusuari, String cdflujomc) throws Exception {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("cdusuari"  , cdusuari);
        params.put("cdflujomc" , cdflujomc);
        Map<String, Object> procRes = ejecutaSP(new RecuperarSucursalUsuarioPorTipoTramiteSP(getDataSource()), params);
        String sucursal = (String) procRes.get("pv_cdunieco_o");
        logger.debug("recuperarSucursalUsuarioPorTramite sucursal: {}", sucursal);
        return sucursal;
    }
    
    protected class RecuperarSucursalUsuarioPorTipoTramiteSP extends StoredProcedure {
        protected RecuperarSucursalUsuarioPorTipoTramiteSP (DataSource dataSource) {
            super(dataSource, "P_DSPCH_GET_SUCURSAL_USR_FLU");
            declareParameter(new SqlParameter("cdusuari"  , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdflujomc" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_cdunieco_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
        }
    }
    
    @Override
    public List<Map<String, String>> recuperarPermisosEndosos (String cdusuari, String cdsisrol) throws Exception {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("cdusuari", cdusuari);
        params.put("cdsisrol", cdsisrol);
        Map<String, Object> procRes = ejecutaSP(new RecuperarPermisosEndososSP(getDataSource()), params);
        List<Map<String, String>> lista = (List<Map<String, String>>) procRes.get("pv_registro_o");
        if (lista == null) {
            lista = new ArrayList<Map<String, String>>();
        }
        logger.debug("recuperarPermisosEndosos lista: {}", Utils.log(lista));
        return lista;
    }
    
    protected class RecuperarPermisosEndososSP extends StoredProcedure {
        protected RecuperarPermisosEndososSP (DataSource dataSource) {
            super(dataSource, "P_DSPCH_GET_PERMISOS_ENDOSOS");
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
            String[] cols = new String[] {
                    "CDRAMO", "DSRAMO", "CDTIPSUP", "DSTIPSUP", "PORUSUARIO", "TIPOFLOT"
                    };
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
        }
    }
}