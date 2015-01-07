package mx.com.gseguros.portal.general.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.general.dao.CatalogosDAO;
import mx.com.gseguros.portal.general.util.Rango;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.general.util.Validacion;
import mx.com.gseguros.utils.Utilerias;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class CatalogosDAOImpl extends AbstractManagerDAO implements CatalogosDAO {

	protected static final transient Logger logger = Logger.getLogger(CatalogosDAOImpl.class);
	
	@Override
	public List<GenericVO> obtieneTmanteni(String cdTabla) throws Exception{
		
		try {
			Map<String,Object> params=new LinkedHashMap<String,Object>(0);
			params.put("pv_cdtabla", cdTabla);
			logger.debug(
	        		new StringBuilder()
	        		.append("\n***************************************")
	        		.append("\n****** PKG_LISTAS.P_GET_TMANTENI ******")
	        		.append("\n****** params=").append(params)
	        		.append("\n***************************************")
	        		.toString()
	        		);
			Map<String, Object> resultado = ejecutaSP(new ObtenerTmanteni(getDataSource()), params);
			return (List<GenericVO>) resultado.get("pv_registro_o");
			
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	protected class ObtenerTmanteni extends StoredProcedure {
    	protected ObtenerTmanteni(DataSource dataSource) {
            super(dataSource,"PKG_LISTAS.P_GET_TMANTENI");
            declareParameter(new SqlParameter("pv_cdtabla",       OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtenerTmanteniMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o",   OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o",    OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    protected class ObtenerTmanteniMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            GenericVO generic=new GenericVO();
            generic.setKey(rs.getString("CODIGO"));
            generic.setValue(rs.getString("DESCRIPC"));
            return generic;
        }
    }
    
    
    public List<GenericVO> obtieneColonias(String codigoPostal) throws Exception {
    	try {
    		HashMap<String,Object> params =  new LinkedHashMap<String, Object>();
    		params.put("pv_codpostal_i", codigoPostal);
    		
    		Map<String, Object> resultado = ejecutaSP(new ObtenCatalogoColonias(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
    }
    
    protected class ObtenCatalogoColonias extends StoredProcedure {
    	
    	protected ObtenCatalogoColonias(DataSource dataSource) {
    		super(dataSource, "PKG_LISTAS.P_GET_COLONIAS");
    		
    		declareParameter(new SqlParameter("pv_codpostal_i", OracleTypes.VARCHAR));			
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ColoniasMapper()));
    		declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    }
    
    protected class ColoniasMapper  implements RowMapper {
    	
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		GenericVO base = new GenericVO();	    
    		base.setKey(rs.getString("CODIGO"));
    		base.setValue(rs.getString("NOMBRE"));
    		return base;
    	}
    }

    public List<GenericVO> obtieneMunicipios(String cdEstado) throws Exception {
    	try {
    		HashMap<String,Object> params =  new LinkedHashMap<String, Object>();
    		params.put("pv_estado_i", cdEstado);
    		
    		Map<String, Object> resultado = ejecutaSP(new ObtieneMunicipios(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
    	} catch (Exception e) {
    		throw new Exception(e.getMessage(), e);
    	}
    }
    
    protected class ObtieneMunicipios extends StoredProcedure {
    	
    	protected ObtieneMunicipios(DataSource dataSource) {
    		super(dataSource, "PKG_LISTAS.P_GET_MUNICIPIOS_X_EDO");
    		
    		declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));			
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new MunicipiosMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    }
    
    protected class MunicipiosMapper  implements RowMapper {
    	
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		GenericVO base = new GenericVO();	    
    		base.setKey(rs.getString("CODIGO"));
    		base.setValue(rs.getString("DESCRIPCION"));
    		return base;
    	}
    }

    public List<GenericVO> obtieneZonasPorModalidad(String cdtipsit) throws Exception {
    	try {
    		HashMap<String,Object> params =  new LinkedHashMap<String, Object>();
    		params.put("pv_cdtipsit_i", cdtipsit);
    		
    		Map<String, Object> resultado = ejecutaSP(new ObtieneZonasPorModalidad(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
    	} catch (Exception e) {
    		throw new Exception(e.getMessage(), e);
    	}
    }
    
    protected class ObtieneZonasPorModalidad extends StoredProcedure {
    	
    	protected ObtieneZonasPorModalidad(DataSource dataSource) {
    		super(dataSource, "PKG_LISTAS.___");
    		
    		declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));			
    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ZonasMapper()));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    }
    
    protected class ZonasMapper  implements RowMapper {
    	
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		GenericVO base = new GenericVO();	    
    		base.setKey(rs.getString("CODIGO"));
    		base.setValue(rs.getString("DESCRIPCION"));
    		return base;
    	}
    }


	@Override
	public List<GenericVO> obtieneAtributosSituacion(String cdAtribu, String cdTipSit, String otValor) throws Exception {
		try {
    		HashMap<String,Object> params = new LinkedHashMap<String,Object>();
            params.put("pv_cdatribu_i",cdAtribu);
            params.put("pv_cdtipsit_i",cdTipSit);
            params.put("pv_otvalor_i",otValor);
    		logger.debug(
    				new StringBuilder()
    				.append("\n********************************************")
    				.append("\n****** PKG_LISTAS.P_GET_ATRIBUTOS_SIT ******")
    				.append("\n****** params=").append(params)
    				.append("\n********************************************")
    				.toString()
    				);
    		Map<String, Object> resultado = ejecutaSP(new ObtieneAtributosSit(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	protected class ObtieneAtributosSit extends StoredProcedure {

        protected ObtieneAtributosSit(DataSource dataSource) {
            super(dataSource, "PKG_LISTAS.P_GET_ATRIBUTOS_SIT");
            declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdatribu_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_otvalor_i", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneAtributosSitMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
        }
    }

    protected class ObtieneAtributosSitMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new GenericVO(rs.getString("CODIGO"),rs.getString("DESCRIPCION"));
        }
    }
    
    @Override
	public List<GenericVO> obtieneAtributosSiniestro(String cdAtribu, String cdTipSit, String otValor) throws Exception {
		try {
    		HashMap<String,Object> params = new LinkedHashMap<String,Object>();
            params.put("pv_cdatribu_i",cdAtribu);
            params.put("pv_cdtipsit_i",cdTipSit);
            params.put("pv_otvalor_i",otValor);
    		
    		Map<String, Object> resultado = ejecutaSP(new ObtieneAtributosSin(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
    
    protected class ObtieneAtributosSin extends StoredProcedure {

        protected ObtieneAtributosSin(DataSource dataSource) {
            super(dataSource, "PKG_LISTAS.P_GET_ATRIBUTOS_SIN");
            declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdatribu_i", OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_otvalor_i", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneAtributosSinMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
            compile();
        }
    }

    protected class ObtieneAtributosSinMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new GenericVO(rs.getString("CODIGO"),rs.getString("DESCRIPCION"));
        }
    }

	@Override
	public List<GenericVO> obtieneAtributosPoliza(String cdAtribu, String cdRamo, String otValor) throws Exception {
		
		try {
			HashMap<String,Object> params = new LinkedHashMap<String,Object>();
			params.put("pv_cdatribu_i", cdAtribu);
			params.put("pv_cdramo_i", cdRamo);
			params.put("pv_otvalor_i", otValor);
    		
    		Map<String, Object> resultado = ejecutaSP(new ObtieneAtributosPol(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	protected class ObtieneAtributosPol extends StoredProcedure {
	
		protected ObtieneAtributosPol(DataSource dataSource) {
			super(dataSource, "PKG_LISTAS.P_GET_ATRIBUTOS_POL");
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdatribu_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneAtributosPolMapper()));
			declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class ObtieneAtributosPolMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new GenericVO(rs.getString("CODIGO"),rs.getString("DESCRIPCION"));
		}
	}
	

	@Override
	public List<GenericVO> obtieneAtributosGarantia(String cdAtribu, String cdTipSit, String cdRamo, String valAnt, String cdGarant)
			throws Exception {
		try {
			HashMap<String,Object> params = new LinkedHashMap<String,Object>();
			params.put("pv_cdramo_i"  ,cdRamo);
			params.put("pv_cdtipsit_i",cdTipSit);
			params.put("pv_cdgarant_i",cdGarant);
			params.put("pv_cdatribu_i",cdAtribu);
			params.put("pv_otvalor_i" ,valAnt);
    		
    		Map<String, Object> resultado = ejecutaSP(new ObtieneAtributosGar(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	protected class ObtieneAtributosGar extends StoredProcedure {

		protected ObtieneAtributosGar(DataSource dataSource) {
			super(dataSource, "PKG_LISTAS.P_GET_ATRIBUTOS_GAR");
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdatribu_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneAtributosGarMapper()));
			declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}

	protected class ObtieneAtributosGarMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new GenericVO(rs.getString("CODIGO"),rs.getString("DESCRIPCION"));
		}
	}

	
	@Override
	public List<GenericVO> obtieneAtributosRol(String cdAtribu,
			String cdTipSit, String cdRamo, String valAnt, String cdRol)
			throws Exception {
		try {
			HashMap<String,Object> params = new LinkedHashMap<String,Object>();
			params.put("pv_cdramo_i", cdRamo);
			params.put("pv_cdrol_i", cdRol);
			params.put("pv_cdatribu_i", cdAtribu);
			params.put("pv_cdtipsit_i", cdTipSit);
			params.put("pv_otvalor_i", valAnt);
    		
    		Map<String, Object> resultado = ejecutaSP(new ObtieneAtributosRol(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
    

	protected class ObtieneAtributosRol extends StoredProcedure {
	
		protected ObtieneAtributosRol(DataSource dataSource) {
			super(dataSource, "PKG_LISTAS.P_GET_ATRIBUTOS_ROL");
			declareParameter(new SqlParameter("pv_cdramo_i",   OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdrol_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdatribu_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor_i",  OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ObtieneAtributosRolMapper()));
			declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class ObtieneAtributosRolMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new GenericVO(rs.getString("CODIGO"),rs.getString("DESCRIPCION"));
		}
	}


	@Override
	public List<GenericVO> obtieneRolesSistema(String dsRol) throws Exception {
		HashMap<String,Object> params =  new LinkedHashMap<String, Object>();
		params.put("pv_dssysrol_i", dsRol);
		Map<String, Object> resultado = ejecutaSP(new ObtieneRolesSistema(getDataSource()), params);
		return (List<GenericVO>) resultado.get("PV_REGISTRO_O");
	}
	
	protected class ObtieneRolesSistema extends StoredProcedure {
		protected ObtieneRolesSistema(DataSource dataSource) {
			super(dataSource, "PKG_GENERA_USUARIO.P_BUSCA_ROL");
			declareParameter(new SqlParameter("pv_dssysrol_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_REGISTRO_O", OracleTypes.CURSOR, new ObtieneRolesSistemaMapper()));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class ObtieneRolesSistemaMapper implements RowMapper {
		
    	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		return new GenericVO(rs.getString("CDSISROL"), rs.getString("DSSISROL"));
    	}
    }


	@Override
	public List<GenericVO> obtieneAgentes(String claveONombre) throws Exception {
		try {
			HashMap<String,Object> params =  new LinkedHashMap<String, Object>();
			params.put("pv_nombre_i", claveONombre);
    		
    		Map<String, Object> resultado = ejecutaSP(new ObtieneAgentes(getDataSource()), params);
    		return (List<GenericVO>) resultado.get("pv_registro_o");
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	protected class ObtieneAgentes extends StoredProcedure
	{
		protected ObtieneAgentes(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_OBTIENE_AGENTES");
			declareParameter(new SqlParameter("pv_nombre_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new ObtenerAgentesMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class ObtenerAgentesMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
			GenericVO generic=new GenericVO();
            generic.setKey(rs.getString("CDAGENTE"));
            generic.setValue(rs.getString("Nombre_agente"));
            return generic;
		}
	}
	
	@Override
	public List<GenericVO> obtieneStatusTramite(Map<String,String> params) throws Exception
	{
		Map<String, Object> resultado = ejecutaSP(new ObtieneStatusTramite(getDataSource()), params);
		return (List<GenericVO>) resultado.get("pv_registro_o");
	}
	
	protected class ObtieneStatusTramite extends StoredProcedure
	{
    	protected ObtieneStatusTramite(DataSource dataSource)
    	{
            super(dataSource,"PKG_CONSULTA.P_GET_STATUS_TRAMITE");
            declareParameter(new SqlParameter(   "pv_cdtiptra_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtenerTmanteniMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public List<GenericVO> obtieneSucursales(String cdunieco,String cdusuari) throws Exception {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("pv_suc_admon_i" , cdunieco);
		params.put("pv_cdusuari_i"  , cdusuari);
		Utilerias.debugPrecedure(logger, "PKG_LISTAS.P_GET_SUCURSALES", params);
		Map<String, Object> resultado = ejecutaSP(new ObtieneSucursales(getDataSource()), params);
		return (List<GenericVO>) resultado.get("pv_registro_o");
	}
	
	protected class ObtieneSucursales extends StoredProcedure {
    	protected ObtieneSucursales(DataSource dataSource) {
            super(dataSource,"PKG_LISTAS.P_GET_SUCURSALES");
            declareParameter(new SqlParameter("pv_suc_admon_i" , OracleTypes.NUMERIC));
            declareParameter(new SqlParameter("pv_cdusuari_i"  , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtieneSucursalesMapper()));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	protected class ObtieneSucursalesMapper implements RowMapper<GenericVO> {
		public GenericVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new GenericVO(rs.getString("CODIGO"),rs.getString("DESCRIPC"));
		}
	}

	
	@Override
	public String obtieneCantidadMaxima(String cdramo, String cdtipsit, TipoTramite tipoTramite, Rango rango, Validacion validacion) throws Exception {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_cdtipsit_i" , cdtipsit);
		params.put("pv_cdtiptra_i" , tipoTramite.getCdtiptra());
		params.put("pv_tipocant_i" , rango.getClave());
		params.put("pv_numval_i"   , validacion.getClave());
		logger.debug("obtieneCantidadMaxima params: "+params);
		Map<String, Object> resultado = ejecutaSP(new ObtieneCantidadMaxima(getDataSource()), params);
		return (String) resultado.get("pv_cantidad_o");
	}
	
    protected class ObtieneCantidadMaxima extends StoredProcedure {
    	
    	protected ObtieneCantidadMaxima(DataSource dataSource) {
    		
    		super(dataSource, "PKG_CONSULTA.P_GET_CANTIDAD_MAXIMA");
    		declareParameter(new SqlParameter("pv_cdramo_i",   OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtiptra_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipocant_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_numval_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_cantidad_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    }
    
    @Override
    public List<GenericVO> cargarAgentesPorPromotor(Map<String,String> params) throws Exception
    {
    	Map<String,Object>respuestaProcedure=ejecutaSP(new CargarAgentesPorPromotor(getDataSource()), params);
    	List<GenericVO>listaResp=new ArrayList<GenericVO>();
    	List<Map<String,String>>lista=(List<Map<String,String>>)respuestaProcedure.get("pv_registro_o");
    	if(lista!=null&&lista.size()>0)
    	{
    		for(Map<String,String>iAgente:lista)
    		{
    			listaResp.add(new GenericVO(iAgente.get("CDAGENTE"),iAgente.get("DSAGENTE")));
    		}
    	}
    	return listaResp;
    }
	
    protected class CargarAgentesPorPromotor extends StoredProcedure {
    	
    	protected CargarAgentesPorPromotor(DataSource dataSource) {
    		
    		super(dataSource, "PKG_CONSULTA.P_GET_AGENTES_X_PROMOTOR");
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"CDAGENTE" , "DSAGENTE"
			};
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
    		compile();
    	}
    }
    
    @Override
    public List<GenericVO> cargarServicioPublicoAutos(Map<String,String> params) throws Exception
    {
    	Map<String,Object>respuestaProcedure=ejecutaSP(new CargarServicioPublicoAutos(getDataSource()), params);
    	List<Map<String,String>>lista=(List<Map<String,String>>)respuestaProcedure.get("pv_registro_o");
    	List<GenericVO>listaGen=new ArrayList<GenericVO>();
    	if(lista==null)
    	{
    		lista=new ArrayList<Map<String,String>>();
    	}
    	for(Map<String,String>iAuto:lista)
    	{
    		listaGen.add(new GenericVO(iAuto.get("CLAVEGS"),
    				new StringBuilder()
    		        .append(iAuto.get("CLAVEGS")).append(" - ")
    		        .append(iAuto.get("TIPUNI")).append(" - ")
    		        .append(iAuto.get("MARCA")).append(" - ")
    		        .append(iAuto.get("SUBMARCA")).append(" - ")
    		        .append(iAuto.get("MODELO")).append(" - ")
    		        .append(iAuto.get("VERSION"))
    		        .toString()
    		        ));
    	}
    	return listaGen;
    }
	
    protected class CargarServicioPublicoAutos extends StoredProcedure {
    	
    	protected CargarServicioPublicoAutos(DataSource dataSource) {
    		
    		super(dataSource, "PKG_CONSULTA.P_GET_DESC_VEHICULOS");
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("substring" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"  , OracleTypes.VARCHAR));
			String[] cols = new String[]{
					"TIPUNI"
					,"MARCA"
					,"SUBMARCA"
					,"MODELO"
					,"VERSION"
					,"CLAVEGS"
			};
    		declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
    		declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
    		compile();
    	}
    }
    
    @Override
	public String agregaCodigoPostal(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new AgregaCodigoPostal(getDataSource()), params);
		return (String)resultado.get("pv_msg_id_o");
	}
	
	protected class AgregaCodigoPostal extends StoredProcedure
	{
		protected AgregaCodigoPostal(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_MOV_CODIGO_POSTAL");
			declareParameter(new SqlParameter("pv_cdpostal_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdmunici_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public String asociaZonaCodigoPostal(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new AsociaZonaCodigoPostal(getDataSource()), params);
		return (String)resultado.get("pv_msg_id_o");
	}
	
	protected class AsociaZonaCodigoPostal extends StoredProcedure
	{
		protected AsociaZonaCodigoPostal(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_ASOCIA_ZONA_A_CODIPOS");
			declareParameter(new SqlParameter("pv_cdpostal_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cvezona_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<GenericVO> cargarDescuentosPorAgente(
    		String tipoUnidad
    		,String uso
    		,String zona
    		,String promotoria
    		,String cdagente
    		,String cdtipsit
    		,String cdatribu)throws Exception
    {
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("tipoUnidad" , tipoUnidad);
		params.put("uso"        , uso);
		params.put("zona"       , zona);
		params.put("promotoria" , promotoria);
		params.put("cdagente"   , cdagente);
		params.put("cdtipsit"   , cdtipsit);
		params.put("cdatribu"   , cdatribu);
		Map<String,Object>procedureResult = ejecutaSP(new CargarDescuentosPorAgente(getDataSource()),params);
		List<Map<String,String>>lista     = (List<Map<String,String>>)procedureResult.get("pv_registro_o");
		List<GenericVO>listaGen           = new ArrayList<GenericVO>();
		if(lista!=null)
		{
			for(Map<String,String>it:lista)
			{
				listaGen.add(new GenericVO(it.get("codigo"),it.get("descripcion")));
			}
		}
		return listaGen;
    }
	
	protected class CargarDescuentosPorAgente extends StoredProcedure
	{
		protected CargarDescuentosPorAgente(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_VALIDA_DESCUENTO_COMERCIAL");
			declareParameter(new SqlParameter("tipoUnidad" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("uso"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("zona"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("promotoria" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdagente"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdatribu"   , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"codigo"
					,"descripcion"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<GenericVO>cargarListaNegocioServicioPublico(
			String cdtipsit
			,String cdatribu
			,String tipoUnidad
			,String cdagente)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdtipsit"   , cdtipsit);
		params.put("cdatribu"   , cdatribu);
		params.put("tipoUnidad" , tipoUnidad);
		params.put("cdagente"   , cdagente);
		logger.debug(
				new StringBuilder()
				.append("\n********************************************")
				.append("\n****** PKG_LISTAS.P_GET_ATRIBUTOS_NEG ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************")
				.toString()
				);
		Map<String,Object>procResult     = ejecutaSP(new CargarListaNegocioServicioPublico(getDataSource()),params);
		List<GenericVO>lista             = new ArrayList<GenericVO>();
		List<Map<String,String>>listaAux = (List<Map<String,String>>)procResult.get("pv_registro_o");
		logger.debug(
				new StringBuilder()
				.append("\n********************************************")
				.append("\n****** PKG_LISTAS.P_GET_ATRIBUTOS_NEG ******")
				.append("\n****** params=").append(params)
				.append("\n****** registro=").append(listaAux)
				.append("\n********************************************")
				.toString()
				);
		if(listaAux==null)
		{
			//lista.add(new GenericVO("0","TRADICIONAL"));
		}
		else
		{
			for(Map<String,String>elem:listaAux)
			{
				lista.add(new GenericVO(elem.get("codigo"),elem.get("descripcion")));
			}
		}
		return lista;
	}
	
	protected class CargarListaNegocioServicioPublico extends StoredProcedure
	{
		protected CargarListaNegocioServicioPublico(DataSource dataSource)
		{
			super(dataSource,"PKG_LISTAS.P_GET_ATRIBUTOS_NEG");
			declareParameter(new SqlParameter("cdtipsit"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdatribu"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipoUnidad" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdagente"   , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"codigo"
					,"descripcion"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<GenericVO>cargarModelosPorSubmarcaRamo5(String submarca,String cdtipsit)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("submarca" , submarca);
		params.put("cdtipsit" , cdtipsit);
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************")
				.append("\n****** PKG_LISTAS.P_RECUPERA_MODELOS ******")
				.append("\n****** params=").append(params)
				.append("\n*******************************************")
				.toString()
				);
		Map<String,Object>procResult=ejecutaSP(new CargarModelosPorSubmarcaRamo5(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		List<GenericVO>listaGeneric=new ArrayList<GenericVO>();
		for(Map<String,String>modelo:lista)
		{
			listaGeneric.add(new GenericVO(modelo.get("anno"),modelo.get("anno")));
		}
		logger.debug(
				new StringBuilder()
				.append("\n*******************************************")
				.append("\n****** registro=").append(listaGeneric)
				.append("\n****** PKG_LISTAS.P_RECUPERA_MODELOS ******")
				.append("\n*******************************************")
				.toString()
				);
		return listaGeneric;
	}
	
	protected class CargarModelosPorSubmarcaRamo5 extends StoredProcedure
	{
		protected CargarModelosPorSubmarcaRamo5(DataSource dataSource)
		{
			super(dataSource,"PKG_LISTAS.P_RECUPERA_MODELOS");
			declareParameter(new SqlParameter("submarca" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"anno"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<GenericVO>cargarVersionesPorModeloSubmarcaRamo5(String submarca,String modelo,String cdtipsit)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("submarca" , submarca);
		params.put("modelo"   , modelo);
		params.put("cdtipsit" , cdtipsit);
		logger.debug(
				new StringBuilder()
				.append("\n*************************************************")
				.append("\n****** PKG_LISTAS.P_RECUPERA_DESCRIPCIONES ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************************")
				.toString()
				);
		Map<String,Object>procResult=ejecutaSP(new CargarVersionesPorModeloSubmarcaRamo5(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		List<GenericVO>listaGeneric=new ArrayList<GenericVO>();
		for(Map<String,String>descripcion:lista)
		{
			listaGeneric.add(new GenericVO(descripcion.get("codigo"),descripcion.get("descripcion")));
		}
		logger.debug(
				new StringBuilder()
				.append("\n*************************************************")
				.append("\n****** registro=").append(listaGeneric)
				.append("\n****** PKG_LISTAS.P_RECUPERA_DESCRIPCIONES ******")
				.append("\n*************************************************")
				.toString()
				);
		return listaGeneric;
	}
	
	protected class CargarVersionesPorModeloSubmarcaRamo5 extends StoredProcedure
	{
		protected CargarVersionesPorModeloSubmarcaRamo5(DataSource dataSource)
		{
			super(dataSource,"PKG_LISTAS.P_RECUPERA_DESCRIPCIONES");
			declareParameter(new SqlParameter("submarca" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("modelo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"codigo"
					,"descripcion"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<GenericVO>cargarAutosPorCadenaRamo5(String cadena,String cdtipsit)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cadena"   , cadena);
		params.put("cdtipsit" , cdtipsit);
		logger.debug(
				new StringBuilder()
				.append("\n*************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_VEHICULOS_RAMO_5 ******")
				.append("\n****** params=").append(params)
				.toString()
				);
		Map<String,Object>procResult=ejecutaSP(new CargarAutosPorCadenaRamo5(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		List<GenericVO>listaGeneric=new ArrayList<GenericVO>();
		for(Map<String,String>descripcion:lista)
		{
			listaGeneric.add(
					new GenericVO(
							descripcion.get("clave")
							,new StringBuilder(descripcion.get("clave"))
							.append(" - ")
							.append(descripcion.get("marca"))
							.append(" - ")
							.append(descripcion.get("submarca"))
							.append(" - ")
							.append(descripcion.get("modelo"))
							.append(" - ")
							.append(descripcion.get("descripcion"))
							.toString()
					));
		}
		logger.debug(
				new StringBuilder()
				.append("\n*************************************************")
				.append("\n****** registro=").append(listaGeneric)
				.append("\n****** PKG_CONSULTA.P_GET_VEHICULOS_RAMO_5 ******")
				.append("\n*************************************************")
				.toString()
				);
		return listaGeneric;
	}
	
	protected class CargarAutosPorCadenaRamo5 extends StoredProcedure
	{
		protected CargarAutosPorCadenaRamo5(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_VEHICULOS_RAMO_5");
			declareParameter(new SqlParameter("cadena"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"clave"
					,"marca"
					,"submarca"
					,"descripcion"
					,"modelo"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<GenericVO>cargarTtapvat1(String cdtabla)throws ApplicationException,Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdtabla" , cdtabla);
		logger.debug(
				new StringBuilder()
				.append("\n*****************************************")
				.append("\n****** PKG_CONSULTA.P_GET_TTAPVAT1 ******")
				.append("\n****** params=").append(params)
				.append("\n*****************************************")
				.toString()
				);
		Map<String,Object>procResult  = ejecutaSP(new CargarTtapvat1(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No hay datos en la tabla de apoyo");
		}
		List<GenericVO>listaGen = new ArrayList<GenericVO>();
		for(Map<String,String>item:lista)
		{
			listaGen.add(new GenericVO(item.get("otclave"),item.get("otvalor")));
		}
		return listaGen;
	}
	
	protected class CargarTtapvat1 extends StoredProcedure
	{
		protected CargarTtapvat1(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TTAPVAT1");
			declareParameter(new SqlParameter("cdtabla" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "otclave" , "otvalor" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<GenericVO>cargarNegocioPorCdtipsitRamo5(String cdtipsit)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdtipsit",cdtipsit);
		logger.debug(
				new StringBuilder()
				.append("\n***************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_NEGOCIO_X_CDTIPSIT ******")
				.append("\n****** params=").append(params)
				.append("\n***************************************************")
				.toString()
				);
		Map<String,Object>procResult  = ejecutaSP(new CargarNegocioPorCdtipsitRamo5(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No hay datos de negocio por situacion de riesgo");
		}
		List<GenericVO>listaGen = new ArrayList<GenericVO>();
		for(Map<String,String>item:lista)
		{
			listaGen.add(new GenericVO(item.get("clave"),item.get("valor")));
		}
		return listaGen;
	}
	
	protected class CargarNegocioPorCdtipsitRamo5 extends StoredProcedure
	{
		protected CargarNegocioPorCdtipsitRamo5(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_NEGOCIO_X_CDTIPSIT");
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "clave" , "valor" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<GenericVO>cargarUsosPorNegocioRamo5(String cdnegocio,String cdtipsit)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdnegocio" , cdnegocio);
		params.put("cdtipsit"  , cdtipsit);
		logger.debug(
				new StringBuilder()
				.append("\n***********************************************")
				.append("\n****** PKG_CONSULTA.P_GET_USOS_X_NEGOCIO ******")
				.append("\n****** params=").append(params)
				.append("\n***********************************************")
				.toString()
				);
		Map<String,Object>procResult  = ejecutaSP(new CargarUsosPorNegocioRamo5(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No hay datos de uso por negocio");
		}
		List<GenericVO>listaGen = new ArrayList<GenericVO>();
		for(Map<String,String>item:lista)
		{
			listaGen.add(new GenericVO(item.get("clave"),item.get("valor")));
		}
		return listaGen;
	}
	
	protected class CargarUsosPorNegocioRamo5 extends StoredProcedure
	{
		protected CargarUsosPorNegocioRamo5(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_USOS_X_NEGOCIO");
			declareParameter(new SqlParameter("cdnegocio" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"  , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "clave" , "valor" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<GenericVO>cargarMarcasPorNegocioRamo5(String cdnegocio,String cdtipsit)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdnegocio" , cdnegocio);
		params.put("cdtipsit"  , cdtipsit);
		logger.debug(
				new StringBuilder()
				.append("\n*************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_MARCAS_X_NEGOCIO ******")
				.append("\n****** params=").append(params)
				.append("\n*************************************************")
				.toString()
				);
		Map<String,Object>procResult  = ejecutaSP(new CargarMarcasPorNegocioRamo5(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new ApplicationException("No hay datos de uso por negocio");
		}
		List<GenericVO>listaGen = new ArrayList<GenericVO>();
		for(Map<String,String>item:lista)
		{
			listaGen.add(new GenericVO(item.get("clave"),item.get("valor")));
		}
		return listaGen;
	}
	
	protected class CargarMarcasPorNegocioRamo5 extends StoredProcedure
	{
		protected CargarMarcasPorNegocioRamo5(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_MARCAS_X_NEGOCIO");
			declareParameter(new SqlParameter("cdnegocio" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"  , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "clave" , "valor" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<GenericVO>cargarNegociosPorAgenteRamo5(String cdagente)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdagente" , cdagente);
		Utilerias.debugPrecedure(logger, "PKG_DESARROLLO.P_GET_NEGOCIO_X_AGENTE_RAMO5", params);
		Map<String,Object>procResult  = ejecutaSP(new CargarNegociosPorAgenteRamo5(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		Utilerias.debugPrecedure(logger, "PKG_DESARROLLO.P_GET_NEGOCIO_X_AGENTE_RAMO5", params,lista);
		List<GenericVO>listaGen       = new ArrayList<GenericVO>();
		if(lista!=null&&lista.size()>0)
		{
			for(Map<String,String>negocio:lista)
			{
				listaGen.add(new GenericVO(negocio.get("OTCLAVE"),negocio.get("OTVALOR")));
			}
		}
		return listaGen;
	}
	
	protected class CargarNegociosPorAgenteRamo5 extends StoredProcedure
	{
		protected CargarNegociosPorAgenteRamo5(DataSource dataSource)
		{
			super(dataSource,"PKG_DESARROLLO.P_GET_NEGOCIO_X_AGENTE_RAMO5");
			declareParameter(new SqlParameter("cdagente" , OracleTypes.VARCHAR));
			String[] cols=new String[]{ "OTCLAVE" , "OTVALOR" };
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<GenericVO>cargarCargasPorNegocioRamo5(String cdsisrol,String negocio)throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>();
		params.put("cdsisrol" , cdsisrol);
		params.put("negocio"  , negocio);
		Utilerias.debugPrecedure(logger, "PKG_DESARROLLO.P_GET_CAT_CARGA_RAMO5", params);
		Map<String,Object>procResult  = ejecutaSP(new CargarCargasPorNegocioRamo5(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)procResult.get("pv_registro_o");
		Utilerias.debugPrecedure(logger, "PKG_DESARROLLO.P_GET_CAT_CARGA_RAMO5", params, lista);
		List<GenericVO>listaGen = new ArrayList<GenericVO>();
		if(lista!=null)
		{
			for(Map<String,String>elem:lista)
			{
				listaGen.add(new GenericVO(elem.get("ID"),elem.get("NOMBRE")));
			}
		}
		return listaGen;
	}
	
	protected class CargarCargasPorNegocioRamo5 extends StoredProcedure
	{
		protected CargarCargasPorNegocioRamo5(DataSource dataSource)
		{
			super(dataSource,"PKG_DESARROLLO.P_GET_CAT_CARGA_RAMO5");
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("negocio"  , OracleTypes.VARCHAR));
			String[] cols=new String[]{
					"ID"
					,"NOMBRE"
					,"TIPO"
					,"ROL"
					,"PERMITIDO"
					,"NEGOCIO"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
}