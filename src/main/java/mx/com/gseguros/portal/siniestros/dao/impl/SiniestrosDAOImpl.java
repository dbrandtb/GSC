package mx.com.gseguros.portal.siniestros.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.siniestros.dao.SiniestrosDAO;
import mx.com.gseguros.portal.siniestros.model.AutorizaServiciosVO;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaManteniVO;
import mx.com.gseguros.portal.siniestros.model.PolizaVigenteVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaPorcentajeVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaProveedorVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTDETAUTSVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTTAPVAATVO;
import mx.com.gseguros.portal.siniestros.model.DatosSiniestroVO;
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

	
    /******************************		ARCHIVO QUE SE TIENE QUE MODIFICAR 		***************************************/    
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
	public List<ConsultaProveedorVO> obtieneListadoProvMedico(String tipoprov,String cdpresta) throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_tipoprov_i", tipoprov);
		params.put("pv_cdpresta_i", cdpresta);
		
		Map<String, Object> mapResult = ejecutaSP(new ObtieneListadoProvMedicoSP(getDataSource()), params);
		return (List<ConsultaProveedorVO>) mapResult.get("pv_registro_o");
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
        	ConsultaProveedorVO consulta = new ConsultaProveedorVO();
        	consulta.setCdpresta(rs.getString("CDPRESTA"));
        	consulta.setNombre(rs.getString("NOMBRE"));
        	consulta.setCdespeci(rs.getString("CDESPECI"));
        	consulta.setDescesp(rs.getString("DESCESP"));
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


	/*@Override
	public List<GenericVO> obtieneListadoMovRechazo(String cdmotRechazo)
			throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdtabla_i", cdmotRechazo);
		
		Map<String, Object> mapResult = ejecutaSP(new ObtieneListadoMovRechazo(getDataSource()), params);
		return (List<GenericVO>) mapResult.get("pv_registro_o");
	}
	
	protected class ObtieneListadoMovRechazo extends StoredProcedure
	{
		protected ObtieneListadoMovRechazo(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_LISTA_TTAPVAT1");
			declareParameter(new SqlParameter("pv_cdtabla_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DatosListaMotRechazo()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
    protected class DatosListaMotRechazo  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	GenericVO consulta = new GenericVO();
        	consulta.setKey(rs.getString("OTCLAVE"));
        	consulta.setValue(rs.getString("OTVALOR"));
            return consulta;
        }
    }*/
    
    
	@Override
	public List<ConsultaTDETAUTSVO> obtieneListadoTDeTauts(String nmautser)
			throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmautser_i", nmautser);
		
		Map<String, Object> mapResult = ejecutaSP(new ObtieneListadoTDeTauts(getDataSource()), params);
		
		@SuppressWarnings("unchecked")
		List<ConsultaTDETAUTSVO> listadoTDeTauts = (List<ConsultaTDETAUTSVO>)mapResult.get("pv_registro_o");
		return listadoTDeTauts;
	}
	
	protected class ObtieneListadoTDeTauts extends StoredProcedure {

		protected ObtieneListadoTDeTauts(DataSource dataSource) {
			super(dataSource, "PKG_PRESINIESTRO.P_CONSULTA_TDETAUTS");
			declareParameter(new SqlParameter("pv_nmautser_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosListadoTDeTauts()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
    protected class DatosListadoTDeTauts  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ConsultaTDETAUTSVO consulta = new ConsultaTDETAUTSVO();
        	consulta.setNmautser(rs.getString("NMAUTSER"));
        	consulta.setCdtipaut(rs.getString("CDTIPAUT"));
        	consulta.setCdmedico(rs.getString("CDMEDICO"));
        	consulta.setNombreMedico(rs.getString("NOMBREMEDICO"));
        	consulta.setCdtipmed(rs.getString("CDTIPMED"));
        	consulta.setCdcpt(rs.getString("CDCPT"));
        	consulta.setDesccpt(rs.getString("DESCCPT"));
        	consulta.setCantporc(rs.getString("CANTPORC"));
        	consulta.setPrecio(rs.getString("PRECIO"));
        	consulta.setPtimport(rs.getString("PTIMPORT"));
        	consulta.setDescTipMed(rs.getString("DESCTIPMED"));
            return consulta;
        }
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
        	System.out.println("VALOR DE Nmautant");
        	System.out.println(consulta.getNmautser());
        	
            return consulta;
        }
    }

	@Override
	public String guardarListaTDeTauts(HashMap<String, Object> paramsTDeTauts)
			throws DaoException {
		// TODO Auto-generated method stub
		Map<String, Object> mapResult = ejecutaSP(new GuardaListaTDeTautsSP(getDataSource()), paramsTDeTauts);
		
		@SuppressWarnings("unchecked")
		String respuesta=(String) mapResult.get("pv_registro_o");
		return respuesta;
	}
	
	protected class GuardaListaTDeTautsSP extends StoredProcedure {

		protected GuardaListaTDeTautsSP(DataSource dataSource) {
			super(dataSource, "PKG_PRESINIESTRO.P_GUARDA_TDETAUTS");
			declareParameter(new SqlParameter("pv_nmautser_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipaut_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdmedico_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipmed_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdctp_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_precio_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cantporc_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptimport_i", OracleTypes.VARCHAR));	
			//declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosGuardardoListaTDeTautsMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
    /*protected class DatosGuardardoListaTDeTautsMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	logger.debug("ENTRA A DatosGuardardoListaTDeTautsMapper");
        	logger.debug(rs);
        	logger.debug(rowNum);
        	logger.debug("SALE A DatosGuardardoListaTDeTautsMapper");
            return rs;
        }
    }*/


	@Override
	public List<ConsultaTTAPVAATVO> obtieneListadoTTAPVAAT(
			HashMap<String, Object> paramTTAPVAAT) throws DaoException {
Map<String, Object> mapResult = ejecutaSP(new ObtieneListadoTTAPVAATSP(getDataSource()), paramTTAPVAAT);
		
		@SuppressWarnings("unchecked")
		List<ConsultaTTAPVAATVO> listaDatosPoliza = (List<ConsultaTTAPVAATVO>)mapResult.get("pv_registro_o");
		return listaDatosPoliza;
	}
	protected class ObtieneListadoTTAPVAATSP extends StoredProcedure {

		protected ObtieneListadoTTAPVAATSP(DataSource dataSource) {
			super(dataSource, "PKG_PRESINIESTRO.P_LISTA_TTAPVAAT");
			declareParameter(new SqlParameter("pv_cdtabla_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otclave1_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otclave2_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otclave3_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otclave4_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otclave5_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosListaTTAPVAATMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
    protected class DatosListaTTAPVAATMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	logger.debug("VALOR DE RESPUESTA");
        	logger.debug(rs);
        	
        	logger.debug("VALOR DE colunma");
        	logger.debug(rowNum);
        	
        	ConsultaTTAPVAATVO consulta = new ConsultaTTAPVAATVO();
        	consulta.setNmtabla(rs.getString("NMTABLA"));
        	consulta.setOtclave1(rs.getString("OTCLAVE1"));
        	consulta.setOtclave2(rs.getString("OTCLAVE2"));
        	consulta.setOtclave3(rs.getString("OTCLAVE3"));
        	consulta.setOtclave4(rs.getString("OTCLAVE4"));
        	consulta.setOtclave5(rs.getString("OTCLAVE5"));
        	consulta.setFedesde(rs.getString("FEDESDE"));
        	consulta.setFehasta(rs.getString("FEHASTA"));
        	consulta.setOtvalor01(rs.getString("OTVALOR01"));
            return consulta;
        }
    }


	@Override
	public List<ConsultaManteniVO> obtieneListadoManteni(
			String cdtabla, String codigo) throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdtabla_i", cdtabla);
		params.put("pv_codigo_i", codigo);
		
		Map<String, Object> mapResult = ejecutaSP(new ObtieneListadoManteniSP(getDataSource()), params);
		
		@SuppressWarnings("unchecked")
		List<ConsultaManteniVO> listaDatosPoliza = (List<ConsultaManteniVO>)mapResult.get("pv_registro_o");
		return listaDatosPoliza;
	}
	
	protected class ObtieneListadoManteniSP extends StoredProcedure {

		protected ObtieneListadoManteniSP(DataSource dataSource) {
			super(dataSource, "PKG_PRESINIESTRO.P_LISTA_TMANTENI");
			declareParameter(new SqlParameter("pv_cdtabla_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_codigo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosListadoMantenimientoMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
    protected class DatosListadoMantenimientoMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ConsultaManteniVO consulta = new ConsultaManteniVO();
        	consulta.setCdtabla(rs.getString("CDTABLA"));
        	consulta.setCodigo(rs.getString("CODIGO"));
        	consulta.setDescripc(rs.getString("DESCRIPC"));
        	consulta.setDescripl(rs.getString("DESCRIPL"));
            return consulta;
        }
    }


	@Override
	public List<ConsultaPorcentajeVO> obtieneListadoPorcentaje(String cdcpt,
			String cdtipmed,String mtobase) throws DaoException {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdcpt_i", cdcpt);
		params.put("pv_cdtipmed_i", cdtipmed);
		params.put("pv_mtobase_i", mtobase);
		
		
		Map<String, Object> mapResult = ejecutaSP(new ObtieneListadoPorcentajeSP(getDataSource()), params);
		
		@SuppressWarnings("unchecked")
		List<ConsultaPorcentajeVO> listaDatosPoliza = (List<ConsultaPorcentajeVO>)mapResult.get("pv_registro_o");
		return listaDatosPoliza;
	}
	
	protected class ObtieneListadoPorcentajeSP extends StoredProcedure {

		protected ObtieneListadoPorcentajeSP(DataSource dataSource) {
			super(dataSource, "PKG_PRESINIESTRO.P_LISTA_TABULADOR");
			declareParameter(new SqlParameter("pv_cdcpt_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipmed_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_mtobase_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosListadoPorcentajeMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}
	
    protected class DatosListadoPorcentajeMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ConsultaPorcentajeVO consulta = new ConsultaPorcentajeVO();
        	consulta.setPorcentaje(rs.getString("PORCENTAJE"));
        	consulta.setMtomedico(rs.getString("MTOMEDICO"));
            return consulta;
        }
    }


	@Override
	public List<PolizaVigenteVO> obtieneListadoPoliza(String cdperson)
			throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdperson_i", cdperson);
		
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerListadoPoliza(this.getDataSource()), params);
		return (List<PolizaVigenteVO>) resultadoMap.get("pv_registro_o");
	}
	protected class ObtenerListadoPoliza extends StoredProcedure
	{
		protected ObtenerListadoPoliza(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_LISTA_POLIZAS");
			declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DatosListaPoliza()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class DatosListaPoliza  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	PolizaVigenteVO consulta = new PolizaVigenteVO();
        	consulta.setCdunieco(rs.getString("CDUNIECO"));
        	consulta.setCdramo(rs.getString("CDRAMO"));
        	consulta.setEstado(rs.getString("ESTADO"));
        	consulta.setNmpoliza(rs.getString("NMPOLIZA"));
        	consulta.setNmsituac(rs.getString("NMSITUAC"));
        	consulta.setMtoBase(rs.getString("MTOBASE"));
        	consulta.setFeinicio(Utilerias.formateaFecha(rs.getString("FEINICIO")));
        	consulta.setFefinal(Utilerias.formateaFecha(rs.getString("FEFINAL")));
        	consulta.setDssucursal(rs.getString("DSUNIECO"));
        	consulta.setDsramo(rs.getString("DSRAMO"));
        	consulta.setEstatus(rs.getString("STATUS"));
        	consulta.setDsestatus(rs.getString("DSTATUS"));
            return consulta;
        }
    }


	@Override
	public void eliminacionRegistrosTabla(String nmautser)
			throws DaoException {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pv_nmautser_i", nmautser);
			
			Map<String,Object> resultadoMap=this.ejecutaSP(new EliminacionRegistros(this.getDataSource()), params);
		}
		protected class EliminacionRegistros extends StoredProcedure
		{
			protected EliminacionRegistros(DataSource dataSource)
			{
				super(dataSource, "PKG_PRESINIESTRO.P_BORRA_TDETAUTS");
				declareParameter(new SqlParameter("pv_nmautser_i", OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
				declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
				compile();
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