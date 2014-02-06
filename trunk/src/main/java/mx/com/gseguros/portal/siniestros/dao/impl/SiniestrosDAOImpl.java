package mx.com.gseguros.portal.siniestros.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.siniestros.dao.SiniestrosDAO;
import mx.com.gseguros.portal.siniestros.model.AutorizaServiciosVO;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.DatosSiniestroVO;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.DinamicMapper;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.Utilerias;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class SiniestrosDAOImpl extends AbstractManagerDAO implements SiniestrosDAO {

	private static Logger logger = Logger.getLogger(SiniestrosDAOImpl.class);
	
	@Override
	public List<AutorizacionServicioVO> obtieneDatosAutorizacionEsp(String nmautser) throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmautser_i", nmautser);
		
		Map<String, Object> mapResult = ejecutaSP(new ObtieneAutorizacionServicioSP(getDataSource()), params);
		
		@SuppressWarnings("unchecked")
		List<AutorizacionServicioVO> listaDatosPoliza = (List<AutorizacionServicioVO>)mapResult.get("pv_registro_o");
		return listaDatosPoliza;
	}
	
	protected class ObtieneAutorizacionServicioSP extends StoredProcedure {

		protected ObtieneAutorizacionServicioSP(DataSource dataSource) {
			super(dataSource, "PKG_PRESINIESTRO.P_CONSULTA_MAUTSERV");
			declareParameter(new SqlParameter("pv_nmautser_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosAutorizacionServicioMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
    protected class DatosAutorizacionServicioMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AutorizacionServicioVO consulta = new AutorizacionServicioVO();
        	consulta.setNmautser(rs.getString("NMAUTSER"));
        	consulta.setNmautant(rs.getString("NMAUTANT"));
        	consulta.setCdperson(rs.getString("CDPERSON"));
        	consulta.setNombreCliente(rs.getString("NOMBRECLIENTE"));
        	consulta.setFesolici(Utilerias.formateaFecha(rs.getString("FESOLICI")));
        	consulta.setFeautori(Utilerias.formateaFecha(rs.getString("FEAUTORI")));
        	consulta.setFevencim(Utilerias.formateaFecha(rs.getString("FEVENCIM")));
        	consulta.setFeingres(Utilerias.formateaFecha(rs.getString("FEINGRES")));
        	consulta.setCdunieco(rs.getString("CDUNIECO"));
        	consulta.setEstado(rs.getString("ESTADO"));
        	consulta.setCdramo(rs.getString("CDRAMO"));
        	consulta.setNmpoliza(rs.getString("NMPOLIZA"));
        	consulta.setNmsituac(rs.getString("NMSITUAC"));
        	consulta.setCduniecs(rs.getString("CDUNIECS"));
        	consulta.setCdgarant(rs.getString("CDGARANT"));
        	consulta.setDescGarantia(rs.getString("DESCGARANTIA"));
        	consulta.setCdconval(rs.getString("CDCONVAL"));
        	consulta.setDescSubGarantia(rs.getString("DESCSUBGARANTIA"));
        	consulta.setCdprovee(rs.getString("CDPROVEE"));
        	consulta.setNombreProveedor(rs.getString("NOMBREPROVEEDOR"));
        	consulta.setCdmedico(rs.getString("CDMEDICO"));
        	consulta.setNombreMedico(rs.getString("NOMBREMEDICO"));
        	consulta.setMtsumadp(rs.getString("MTSUMADP"));
        	consulta.setPorpenal(rs.getString("PORPENAL"));
        	consulta.setCdicd(rs.getString("CDICD"));
        	consulta.setDescICD(rs.getString("DESCICD"));
        	consulta.setCdcausa(rs.getString("CDCAUSA"));
        	consulta.setDescCausa(rs.getString("DESCCAUSA"));
        	consulta.setAaapertu(rs.getString("AAAPERTU"));
        	consulta.setStatus(rs.getString("STATUS"));
        	consulta.setDstratam(rs.getString("DSTRATAM"));
        	consulta.setDsobserv(rs.getString("DSOBSERV"));
        	consulta.setDsnotas(rs.getString("DSNOTAS"));
        	consulta.setFesistem(Utilerias.formateaFecha(rs.getString("FESISTEM")));
        	consulta.setCduser(rs.getString("CDUSER"));
            return consulta;
        }
    }

	@SuppressWarnings("unchecked")
	public List<GenericVO> obtieneListadoAsegurado(String cdperson) throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdperson_i", cdperson);
		
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerListadoAsegurado(this.getDataSource()), params);
		return (List<GenericVO>) resultadoMap.get("pv_registro_o");
	}
	protected class ObtenerListadoAsegurado extends StoredProcedure
	{
		protected ObtenerListadoAsegurado(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_LISTA_ASEGURADO");
			declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DatosListaAsegurado()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
    protected class DatosListaAsegurado  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	GenericVO consulta = new GenericVO();
        	consulta.setKey(rs.getString("CODIGO"));
        	consulta.setValue(rs.getString("ASEGURADO"));
            return consulta;
        }
    }
    
	@Override
	public List<AutorizaServiciosVO> obtieneListadoAutorizaciones(String tipoAut,String cdperson) throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_tipo_aut_i", tipoAut);
		params.put("pv_cdperson_i", cdperson);
		
		Map<String, Object> mapResult = ejecutaSP(new ObtieneListadoAutorizacionServicioSP(getDataSource()), params);
		
		@SuppressWarnings("unchecked")
		List<AutorizaServiciosVO> listaDatosPoliza = (List<AutorizaServiciosVO>)mapResult.get("pv_registro_o");
		return listaDatosPoliza;
	}
	
	protected class ObtieneListadoAutorizacionServicioSP extends StoredProcedure {

		protected ObtieneListadoAutorizacionServicioSP(DataSource dataSource) {
			super(dataSource, "PKG_PRESINIESTRO.P_LISTA_AUTORIZA");
			declareParameter(new SqlParameter("pv_tipo_aut_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosListaAutorizacionServicioMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
    protected class DatosListaAutorizacionServicioMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AutorizaServiciosVO consulta = new AutorizaServiciosVO();
        	consulta.setNmautser(rs.getString("NMAUTSER"));
        	consulta.setNmautant(rs.getString("NMAUTANT"));
        	consulta.setFesolici(Utilerias.formateaFecha(rs.getString("FESOLICI")));
        	consulta.setPolizaafectada(rs.getString("POLIZAAFECTADA"));
        	consulta.setCdprovee(rs.getString("CDPROVEE"));
        	consulta.setNombreProveedor(rs.getString("NOMBREPROVEEDOR"));        	
            return consulta;
        }
    }
    
    
	@SuppressWarnings("unchecked")
	@Override
	public List<GenericVO> obtieneListadoProvMedico(String tipoprov,String cdpresta) throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_tipoprov_i", tipoprov);
		params.put("pv_cdpresta_i", cdpresta);
		
		Map<String, Object> mapResult = ejecutaSP(new ObtieneListadoProvMedicoSP(getDataSource()), params);
		return (List<GenericVO>) mapResult.get("pv_registro_o");
	}
	
	protected class ObtieneListadoProvMedicoSP extends StoredProcedure
	{
		protected ObtieneListadoProvMedicoSP(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_LISTA_PROVEEDOR");
			declareParameter(new SqlParameter("pv_tipoprov_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdpresta_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DatosListaProvMedico()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
    protected class DatosListaProvMedico  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	GenericVO consulta = new GenericVO();
        	consulta.setKey(rs.getString("CDPRESTA"));
        	consulta.setValue(rs.getString("NOMBRE"));
            return consulta;
        }
    }
    
	@Override
	public List<GenericVO> obtieneListadoCausaSiniestro(String cdcausa)
			throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdcausa_i", cdcausa);
		
		Map<String, Object> mapResult = ejecutaSP(new ObtieneListadoCausaSiniestroSP(getDataSource()), params);
		return (List<GenericVO>) mapResult.get("pv_registro_o");
	}
	
	protected class ObtieneListadoCausaSiniestroSP extends StoredProcedure
	{
		protected ObtieneListadoCausaSiniestroSP(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_LISTA_CAUSAS");
			declareParameter(new SqlParameter("pv_cdcausa_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DatosListaCausaSiniestro()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
    protected class DatosListaCausaSiniestro  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	GenericVO consulta = new GenericVO();
        	consulta.setKey(rs.getString("CDCAUSA"));
        	consulta.setValue(rs.getString("DSCAUSA"));
            return consulta;
        }
    }
    
    
	@Override
	public List<CoberturaPolizaVO> obtieneListadoCoberturaPoliza(
			HashMap<String, Object> paramCobertura) throws DaoException {
		Map<String, Object> mapResult = ejecutaSP(new ObtieneListadoCoberturaSP(getDataSource()), paramCobertura);
		
		@SuppressWarnings("unchecked")
		List<CoberturaPolizaVO> listaDatosPoliza = (List<CoberturaPolizaVO>)mapResult.get("pv_registro_o");
		return listaDatosPoliza;
	}
	protected class ObtieneListadoCoberturaSP extends StoredProcedure {

		protected ObtieneListadoCoberturaSP(DataSource dataSource) {
			super(dataSource, "PKG_PRESINIESTRO.P_LISTA_COBERT_POL");
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosListaCoberturasMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
    protected class DatosListaCoberturasMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	CoberturaPolizaVO consulta = new CoberturaPolizaVO();
        	consulta.setCdgarant(rs.getString("CDGARANT"));
        	consulta.setDsgarant(rs.getString("DSGARANT"));
        	consulta.setPtcapita(rs.getString("PTCAPITA"));
            return consulta;
        }
    }
    
	@Override
	public List<DatosSiniestroVO> obtieneListadoDatSubGeneral(
			HashMap<String, Object> paramDatSubGral) throws DaoException {
		Map<String, Object> mapResult = ejecutaSP(new ObtieneListadoDatSubGeneralSP(getDataSource()), paramDatSubGral);
		
		@SuppressWarnings("unchecked")
		List<DatosSiniestroVO> listaDatosPoliza = (List<DatosSiniestroVO>)mapResult.get("pv_registro_o");
		return listaDatosPoliza;
	}
    
	protected class ObtieneListadoDatSubGeneralSP extends StoredProcedure {

		protected ObtieneListadoDatSubGeneralSP(DataSource dataSource) {
			super(dataSource, "PKG_PRESINIESTRO.P_LISTA_DAT_SUBG");
			
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_subcober_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosListaDatSubGeneralMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
    protected class DatosListaDatSubGeneralMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DatosSiniestroVO consulta = new DatosSiniestroVO();
        	consulta.setCdgarant(rs.getString("CDGARANT"));
        	consulta.setSubcobertura(rs.getString("OTCLAVE2"));
        	consulta.setCdcapita(rs.getString("CDCAPITA"));
        	consulta.setLuc(rs.getString("LUC"));
        	consulta.setDeducible(rs.getString("DEDUCIBLE"));
        	consulta.setCopago(rs.getString("COPAGO"));
        	consulta.setBenefmax(rs.getString("BENEFMAX"));
        	consulta.setIcd(rs.getString("ICD"));
        	consulta.setCpt(rs.getString("CPT"));
        	consulta.setLimites(rs.getString("LIMITES"));
            return consulta;
        }
    }
    
    @Override
	public List<GenericVO> obtieneListadoSubcobertura(String cdgarant,
			String cdsubcob) throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdgarant_i", cdgarant);
		params.put("pv_cdsubcob_i", cdsubcob);
		
		Map<String, Object> mapResult = ejecutaSP(new ObtieneListadoSubcoberturaSP(getDataSource()), params);
		return (List<GenericVO>) mapResult.get("pv_registro_o");
	}
	
	protected class ObtieneListadoSubcoberturaSP extends StoredProcedure
	{
		protected ObtieneListadoSubcoberturaSP(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_LISTA_SUB_COBERT");
			declareParameter(new SqlParameter("pv_cdgarant_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsubcob_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DatosListaSubcobertura()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
    protected class DatosListaSubcobertura  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	GenericVO consulta = new GenericVO();
        	consulta.setKey(rs.getString("OTCLAVE"));
        	consulta.setValue(rs.getString("OTVALOR"));
            return consulta;
        }
    }
    
    
	public List<GenericVO> obtieneListadoCPTICD(String cdtabla, String otclave)
			throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdtabla_i", cdtabla);
		params.put("pv_otclave_i", otclave);
		
		Map<String, Object> mapResult = ejecutaSP(new ObtieneListadoCPTICDSP(getDataSource()), params);
		return (List<GenericVO>) mapResult.get("pv_registro_o");
	}
	
	protected class ObtieneListadoCPTICDSP extends StoredProcedure
	{
		protected ObtieneListadoCPTICDSP(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_LISTA_TTAPVAT1");
			declareParameter(new SqlParameter("pv_cdtabla_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otclave_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DatosListaCPTICD()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
    protected class DatosListaCPTICD  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	GenericVO consulta = new GenericVO();
        	consulta.setKey(rs.getString("OTCLAVE"));
        	consulta.setValue(rs.getString("OTVALOR"));
            return consulta;
        }
    }
    
    public static void setLogger(Logger logger) {
		SiniestrosDAOImpl.logger = logger;
	}

    
    
    /* ############################################################################## 
	 * ##################################### VERIFICAR ##############################*/

	@Override
	public List<AutorizacionServicioVO>  guardarAutorizacionServicio(Map<String, Object> paramsR)
			throws DaoException {
		
		Map<String, Object> mapResult = ejecutaSP(new GuardaAutorizacionServicioSP(getDataSource()), paramsR);
		
		@SuppressWarnings("unchecked")
		List<AutorizacionServicioVO> listaDatosPoliza = (List<AutorizacionServicioVO>)mapResult.get("pv_registro_o");
		return listaDatosPoliza;
	}
	
	protected class GuardaAutorizacionServicioSP extends StoredProcedure {

		protected GuardaAutorizacionServicioSP(DataSource dataSource) {
			super(dataSource, "PKG_PRESINIESTRO.P_GUARDA_MAUTSERV");
			
			declareParameter(new SqlParameter("pv_nmautser_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmautant_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fesolici_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feautori_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fevencim_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feingres_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cduniecs_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdconval_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdprovee_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdmedico_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_mtsumadp_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_porpenal_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdicd_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdcausa_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_aaapertu_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dstratam_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsobserv_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dsnotas_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fesistem_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cduser_i", OracleTypes.VARCHAR));			
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosGuardardoAutorizacionServicioMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
    protected class DatosGuardardoAutorizacionServicioMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AutorizacionServicioVO consulta = new AutorizacionServicioVO();
        	consulta.setNmautser(rs.getString("nmautser"));
            return consulta;
        }
    }

    @Override
	public List<HashMap<String, String>> loadListaDocumentos(
			HashMap<String, String> params) throws DaoException {
		
		Map<String, Object> mapResult = ejecutaSP(new LoadListaDocumentos(getDataSource()), params);
		
		return (List<HashMap<String, String>>) mapResult.get("pv_registro_o");
	}
    
    protected class LoadListaDocumentos extends StoredProcedure {

		protected LoadListaDocumentos(DataSource dataSource) {
			super(dataSource, "PKG_LISTAS.P_GET_DOCUMENTOS_SINIESTROS");
			
			declareParameter(new SqlParameter("pv_cdtippag_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipate_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmtramite_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new LoadListaDocumentosMapper()));
			declareParameter(new SqlOutParameter("pv_messages_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
    protected class LoadListaDocumentosMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	HashMap<String, String> map =  new HashMap<String, String>();
        	String obl = Constantes.SI.equalsIgnoreCase(rs.getString("SWOBLIGA"))? "Si" : "No";
        	map.put("id", rs.getString("CODIGO"));
        	map.put("listo", rs.getString("VALOR"));
        	map.put("nombre", rs.getString("DESCRIP"));
        	map.put("obligatorio", obl);
            return map;
        }
    }


	@Override
	public String guardaEstatusDocumento(HashMap<String, String> params)
			throws DaoException {
		
		logger.debug("parms: "+params);
		Map<String, Object> mapResult = ejecutaSP(new GuardaEstatusDocumento(getDataSource()), params);
		
		return (String) mapResult.get("pv_msg_id_o");
	}
	
	protected class GuardaEstatusDocumento extends StoredProcedure {

		protected GuardaEstatusDocumento(DataSource dataSource) {
			super(dataSource, "PKG_SATELITES.P_INSERTA_MDOCUTRA");
			
			declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtippag_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipate_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cddocume_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_accion_i", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}

}