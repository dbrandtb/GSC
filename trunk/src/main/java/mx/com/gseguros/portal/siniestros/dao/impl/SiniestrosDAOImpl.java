package mx.com.gseguros.portal.siniestros.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.DinamicMapper;
import mx.com.gseguros.portal.siniestros.dao.SiniestrosDAO;
import mx.com.gseguros.portal.siniestros.model.AltaTramiteVO;
import mx.com.gseguros.portal.siniestros.model.AutorizaServiciosVO;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaManteniVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaPorcentajeVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaProveedorVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTDETAUTSVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTTAPVAATVO;
import mx.com.gseguros.portal.siniestros.model.DatosSiniestroVO;
import mx.com.gseguros.portal.siniestros.model.HistorialSiniestroVO;
import mx.com.gseguros.portal.siniestros.model.ListaFacturasVO;
import mx.com.gseguros.portal.siniestros.model.MesaControlVO;
import mx.com.gseguros.portal.siniestros.model.PolizaVigenteVO;
import mx.com.gseguros.portal.siniestros.model.SiniestroVO;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.Utilerias;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Reclamo;
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
        	consulta.setCopagofi(rs.getString("COPAGOFI"));
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
        	consulta.setCirculo(rs.getString("CIRCULO"));
        	consulta.setCodpos(rs.getString("CODPOS"));
        	consulta.setZonaHospitalaria(rs.getString("ZONA"));
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
			declareParameter(new SqlParameter("pv_cdpresta_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipo_i", OracleTypes.VARCHAR));
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
			declareParameter(new SqlParameter("pv_copagofi_i", OracleTypes.VARCHAR));
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
	public String guardarListaTDeTauts(HashMap<String, Object> paramsTDeTauts)
			throws DaoException {
		// TODO Auto-generated method stub
		Map<String, Object> mapResult = ejecutaSP(new GuardaListaTDeTautsSP(getDataSource()), paramsTDeTauts);
		return (String) mapResult.get("pv_registro_o");
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
        	consulta.setNmsolici(rs.getString("NMSOLICI"));
        	consulta.setNmsuplem(rs.getString("NMSUPLEM"));
        	consulta.setCdtipsit(rs.getString("CDTIPSIT"));
        	consulta.setEstatusCliente(rs.getString("STATUS_CLIENTE"));
        	if(rs.getString("STATUS_CLIENTE").equalsIgnoreCase("v"))
        	{
        		consulta.setDesEstatusCliente("Vigente");
        	}else{
        		consulta.setDesEstatusCliente("Cancelado");
        	}
        	
        	consulta.setFcancelacionAfiliado(rs.getString("FCANCEL_AFILIADO"));
        	consulta.setFaltaAsegurado(Utilerias.formateaFecha(rs.getString("FALTA_ASEGURADO")));
        	consulta.setMtoBeneficioMax(rs.getString("BENEFICIO_MAXIMO"));
        	consulta.setZonaContratada(rs.getString("ZONA_CONTRATADA"));
        	consulta.setVigenciaPoliza(Utilerias.formateaFecha(rs.getString("FEINICIO"))+"\t\t|\t\t"+Utilerias.formateaFecha(rs.getString("FEFINAL")));
        	consulta.setNumPoliza(rs.getString("NUMPOLIZA"));
        	consulta.setDsplan(rs.getString("DSPLAN"));
        	consulta.setMesesAsegurado(rs.getString("MESESASEGURADO"));
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
	    public List<Map<String, String>> loadListaIncisosRechazos(
	    		Map<String, String> params) throws DaoException {
	    	
	    	Map<String, Object> mapResult = ejecutaSP(new LoadListaIncisosRechazos(getDataSource()), params);
	    	
	    	return (List<Map<String, String>>) mapResult.get("pv_registro_o");
	    }
	    
	    protected class LoadListaIncisosRechazos extends StoredProcedure {
	    	
	    	protected LoadListaIncisosRechazos(DataSource dataSource) {
	    		super(dataSource, "PKG_LISTAS.P_GET_CAUSAS_MOT_RECHAZO");
	    		
	    		declareParameter(new SqlParameter("pv_cdmotivo_i", OracleTypes.VARCHAR));
	    		//CDCAUMOT
	    		//DSCAUMOT
	    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DinamicMapper()));
	    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
	    		compile();
	    	}
	    }

	    @Override
	    public List<Map<String, String>> loadListaRechazos() throws DaoException {
	    	Map<String, Object> params = new HashMap<String, Object>();
	    	Map<String, Object> mapResult = ejecutaSP(new LoadListaRechazos(getDataSource()), params);
	    	
	    	return (List<Map<String, String>>) mapResult.get("pv_registro_o");
	    }
	    
	    protected class LoadListaRechazos extends StoredProcedure {
	    	
	    	protected LoadListaRechazos(DataSource dataSource) {
	    		super(dataSource, "PKG_LISTAS.P_GET_MOTIVOS_RECHAZO");
	    		
	    		//CDMOTIVO
	    		//DSMOTIVO
	    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DinamicMapper()));
	    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
	    		compile();
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

		@Override
		public List<GenericVO> obtieneListadoPlaza() throws DaoException {
			Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerListaPlazas(this.getDataSource()), new HashMap<String,String>());
			return (List<GenericVO>) resultadoMap.get("pv_registro_o");
		}
		
		protected class ObtenerListaPlazas extends StoredProcedure
		{
			protected ObtenerListaPlazas(DataSource dataSource)
			{
				super(dataSource, "PKG_LISTAS.P_GET_PLAZAS");
				
				declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DatosListaPlazas()));
				declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
				declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
				compile();
			}
		}
		
		protected class DatosListaPlazas  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	GenericVO consulta = new GenericVO();
	        	consulta.setKey(rs.getString("CDUNIECO"));
	        	consulta.setValue(rs.getString("DSUNIECO"));
	            return consulta;
	        }
	    }

		@Override
		public String rechazarTramite(HashMap<String, String> params)
				throws DaoException {
			
			logger.debug("parms: "+params);
			Map<String, Object> mapResult = ejecutaSP(new RechazarTramite(getDataSource()), params);
			
			return (String) mapResult.get("pv_msg_id_o");
		}
		
		protected class RechazarTramite extends StoredProcedure {
			
			protected RechazarTramite(DataSource dataSource) {
				super(dataSource, "");
				
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

		@Override
		public String guardaFacMesaControl(HashMap<String, Object> paramsFacMesaCtrl) throws DaoException {
				Map<String, Object> mapResult = ejecutaSP(new guardaFacMesaControlSP(getDataSource()), paramsFacMesaCtrl);
				return (String) mapResult.get("pv_msg_id_o");
		}
		
		protected class guardaFacMesaControlSP extends StoredProcedure {
			protected guardaFacMesaControlSP(DataSource dataSource) {
				super(dataSource, "PKG_SATELITES.P_MOV_TFACMESCTRL2");
				declareParameter(new SqlParameter("pv_accion_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_nfactura_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_ffactura_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdtipser_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdpresta_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_ptimport_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdgarant_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdconval_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_descporc_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_descnume_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_cdmoneda_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_tasacamb_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_ptimporta_i", OracleTypes.VARCHAR));
				declareParameter(new SqlParameter("pv_dctonuex_i", OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
				declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
				compile();
			}
		}


	@Override
	public String guardaListaTworkSin(HashMap<String, Object> paramsTworkSin) throws DaoException {
		Map<String, Object> mapResult = ejecutaSP(new guardaListaTworkSinSP(getDataSource()), paramsTworkSin);
		return (String) mapResult.get("pv_msg_id_o");
	}
	
	protected class guardaListaTworkSinSP extends StoredProcedure {
		protected guardaListaTworkSinSP(DataSource dataSource) {
			super(dataSource, "PKG_PRESINIESTRO.P_GUARDA_TWORKSIN");
			declareParameter(new SqlParameter("pv_nmtramite_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsolici_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feocurre_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmautser_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}


	@Override
	public String guardaAltaSiniestroAutServicio(String nmautser) throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmautser_i", nmautser);
		Map<String, Object> mapResult = ejecutaSP(new GuardAltaSiniestroAutServicio(this.getDataSource()), params);
		java.math.BigDecimal msgId = (java.math.BigDecimal)mapResult.get("pv_msg_id_o"); 
		return msgId.toPlainString();
	}
	
	protected class GuardAltaSiniestroAutServicio extends StoredProcedure
	{
		protected GuardAltaSiniestroAutServicio(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_GEN_SINIEST_AUT");
			declareParameter(new SqlParameter("pv_nmautser_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String guardaAltaSiniestroAltaTramite(String ntramite) throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_ntramite_i", ntramite);
		Map<String, Object> mapResult = ejecutaSP(new GuardaSiniestroAltaTramite(this.getDataSource()), params);
		java.math.BigDecimal msgId = (java.math.BigDecimal)mapResult.get("pv_msg_id_o"); 
		return msgId.toPlainString();
	}
	
	protected class GuardaSiniestroAltaTramite extends StoredProcedure
	{
		protected GuardaSiniestroAltaTramite(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_GEN_SINIEST_TRA");
			declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}


	@Override
	public String guardaAltaMsinival(HashMap<String, Object> paramMsinival) throws DaoException {
		Map<String, Object> mapResult = ejecutaSP(new guardaAltaMsinivalSP(getDataSource()), paramMsinival);
		return (String) mapResult.get("pv_msg_id_o");
	}
	
	protected class guardaAltaMsinivalSP extends StoredProcedure {
		protected guardaAltaMsinivalSP(DataSource dataSource) {
			super(dataSource, "PKG_SINIESTRO.P_GEN_MSINIVAL");
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_aaapertu_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsinies_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdconval_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdcapita_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmordina_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_femovimi_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdmoneda_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptpagos_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptrecobr_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptimprec_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptimpimp_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_factura_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swlibera_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipmov_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdidemov_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}


	@Override
	public List<ListaFacturasVO> obtieneListadoFacturas(HashMap<String, Object> paramFact) throws DaoException {
		Map<String, Object> mapResult = ejecutaSP(new ObtieneListadoFacturasSP(getDataSource()), paramFact);
		List<ListaFacturasVO> listaDatosPoliza = (List<ListaFacturasVO>)mapResult.get("pv_registro_o");
		return listaDatosPoliza;
	}
	
	protected class ObtieneListadoFacturasSP extends StoredProcedure {

		protected ObtieneListadoFacturasSP(DataSource dataSource) {
			super(dataSource, "PKG_SINIESTRO.P_LISTA_FACTURA");
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_aaapertu_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsinies_i", OracleTypes.VARCHAR));			
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new ListadoFacturasMapper()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}

	protected class ListadoFacturasMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	ListaFacturasVO consulta = new ListaFacturasVO();
        	consulta.setCdfactur(rs.getString("CDFACTUR"));
        	consulta.setFefactur(rs.getString("FEFACTUR"));
        	consulta.setCdprovee(rs.getString("CDPROVEE"));
        	consulta.setCdrol(rs.getString("CDROL"));
        	consulta.setCdperson(rs.getString("CDPERSON"));
        	consulta.setMtolocal(rs.getString("MTOLOCAL"));
        	consulta.setDesProvee(rs.getString("PROVEEDOR"));
            return consulta;
        }
    }


	@Override
	public String bajaMsinival(HashMap<String, Object> paramBajasinival) throws DaoException {
		Map<String, Object> mapResult = ejecutaSP(new BajaMsinivalSP(this.getDataSource()), paramBajasinival);
		return (String) mapResult.get("pv_msg_id_o");
	}
	
	protected class BajaMsinivalSP extends StoredProcedure
	{
		protected BajaMsinivalSP(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_BORRA_MSINIVAL");
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_aaapertu_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsinies_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public String generaContraRecibo(HashMap<String, Object> params) throws DaoException {
		Map<String, Object> mapResult = ejecutaSP(new GeneraContraRecibo(this.getDataSource()), params);
		return (String) mapResult.get("pv_title_o");
	}
	
	protected class GeneraContraRecibo extends StoredProcedure
	{
		protected GeneraContraRecibo(DataSource dataSource)
		{
			super(dataSource, "Pkg_Presiniestro.p_update_contrarecibo");
			declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<GenericVO> obtieneListadoCobertura(String cdramo,String cdtipsit) throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_cdtipsit_i", cdtipsit);
		Map<String, Object> mapResult = ejecutaSP(new ObtenerListaCoberturas(this.getDataSource()), params);
		List<GenericVO> listaCoberturas = (List<GenericVO>)mapResult.get("pv_registro_o");
		return listaCoberturas;
	}
	
	protected class ObtenerListaCoberturas extends StoredProcedure
	{
		protected ObtenerListaCoberturas(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_GET_COBERTURASXRAMO");
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DatosListaCobertura()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class DatosListaCobertura  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	GenericVO consulta = new GenericVO();
        	consulta.setKey(rs.getString("CDGARANT"));
        	consulta.setValue(rs.getString("DSGARANT"));
            return consulta;
        }
    }
	
	@Override
	public String actualizaOTValorMesaControl(Map<String, Object> params) throws DaoException {
		Map<String, Object> mapResult = ejecutaSP(new ActualizaOTValorMesaControl(this.getDataSource()), params);
		return (String) mapResult.get("pv_title_o");
	}
	
	protected class ActualizaOTValorMesaControl extends StoredProcedure
	{
		protected ActualizaOTValorMesaControl(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.p_upd_tmesacontrol");
			declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsucadm_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdsucdoc_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor01_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor02_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor03_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor04_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor05_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor06_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor07_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor08_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor09_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor10_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor11_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor12_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor13_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor14_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor15_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor16_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor17_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor18_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor19_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor20_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor21_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor22_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor23_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor24_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor25_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor26_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor27_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor28_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor29_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor30_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor31_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor32_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor33_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor34_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor35_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor36_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor37_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor38_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor39_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor40_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor41_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor42_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor43_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor44_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor45_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor46_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor47_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor48_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor49_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_otvalor50_i", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	/**
	 * PKG_SINIESTRO.P_LISTA_SINIESTROSXTRAMITE
	 * 6969 NMSINIES,
	 * 500 NMAUTSER,
	 * 510918 CDPERSON,
	 * 'JUAN PEREZ' NOMBRE,
	 * SYSDATE FEOCURRE,
	 * 1009 CDUNIECO,
	 * 'SALUD CAMPECHE' DSUNIECO,
	 * 2 CDRAMO,
	 * 'SALUD VITAL' DSRAMO,
	 * 'SL' CDTIPSIT,
	 * 'SALUD VITAL' DSTIPSIT,
	 * status,
	 * 'M' ESTADO,
	 * 500 NMPOLIZA,
	 * 'S' VOBOAUTO,
	 * '65' CDICD,
	 * 'GRIPE' DSICD,
	 * '66' ICD2,
	 * 'FIEBRE' DSICD2,
	 * 12.5 DESCPORC,
	 * 300 DESCNUME,
	 * 15 COPAGO,
	 * 3500 PTIMPORT,
	 * 'S' AUTRECLA,
	 * 54647 NMRECLAM,
	 * aaapertu
	 */
	@Override
	public List<Map<String,String>> listaSiniestrosTramite(Map<String, String> params) throws Exception
	{
		Map<String, Object> mapResult = ejecutaSP(new ListaSiniestrosTramite(this.getDataSource()), params);
		return (List<Map<String,String>>) mapResult.get("pv_registro_o");
	}
	
	protected class ListaSiniestrosTramite extends StoredProcedure
	{
		protected ListaSiniestrosTramite(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_LISTA_SINIESTROSXTRAMITE");
			declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	/**
	 * PKG_PRESINIESTRO.P_GET_TRAMITE_COMPLETO
	 */
	@Override
	public Map<String,String> obtenerTramiteCompleto(Map<String, String> params) throws Exception
	{
		Map<String, Object> mapResult = ejecutaSP(new ObtenerTramiteCompleto(this.getDataSource()), params);
		List<Map<String,String>> listaTramites = (List<Map<String,String>>) mapResult.get("pv_registro_o");
		if(listaTramites==null||listaTramites.size()==0)
		{
			throw new Exception("No se encuentra el tramite "+params.get("pv_ntramite_i"));
		}
		return listaTramites.get(0);
	}
	
	protected class ObtenerTramiteCompleto extends StoredProcedure
	{
		protected ObtenerTramiteCompleto(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_GET_TRAMITE_COMPLETO");
			declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	/**
	 * PKG_SATELITES.P_OBT_TFACMESCTRL
	 * ntramite,
		nfactura,
		ffactura,
		cdtipser,
		DescServicio,
		cdpresta,
		NombreProveedor,
		ptimport,
		cdgarant,
		DSGARANT,
		DESCPORC,
		DESCNUME
	 */
	@Override
	public List<Map<String,String>> obtenerFacturasTramite(Map<String, String> params) throws Exception
	{
		Map<String, Object> mapResult = ejecutaSP(new ObtenerFacturasTramite(this.getDataSource()), params);
		return (List<Map<String,String>>) mapResult.get("pv_registro_o");
	}
	
	protected class ObtenerFacturasTramite extends StoredProcedure
	{
		protected ObtenerFacturasTramite(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.P_OBT_TFACMESCTRL");
			declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	

	@Override
	public List<HashMap<String,String>> obtenerFacturasTramiteSiniestro(HashMap<String, String> params) throws Exception
	{
		Map<String, Object> mapResult = ejecutaSP(new ObtenerFacturasTramiteSiniestro(this.getDataSource()), params);
		return (List<HashMap<String,String>>) mapResult.get("pv_registro_o");
	}
	
	protected class ObtenerFacturasTramiteSiniestro extends StoredProcedure
	{
		protected ObtenerFacturasTramiteSiniestro(DataSource dataSource)
		{
			super(dataSource, "");
			declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new FacturasTramiteMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class FacturasTramiteMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AutorizacionServicioVO consulta = new AutorizacionServicioVO();
        	consulta.setNmautser(rs.getString("NMAUTSER"));
        	consulta.setNmautant(rs.getString("NMAUTANT"));
            return consulta;
        }
    }
	
	@Override
	public void actualizarAutorizacionTworksin(Map<String, String> params) throws Exception
	{
		ejecutaSP(new ActualizarAutorizacionTworksin(this.getDataSource()), params);
	}
	
	protected class ActualizarAutorizacionTworksin extends StoredProcedure
	{
		protected ActualizarAutorizacionTworksin(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_UPD_NMAUTSER_TWORKSIN");
			declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmautser_i", OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}


	@Override
	public List<PolizaVigenteVO> consultaPolizaUnica(HashMap<String, Object> paramPolUnica) throws Exception {
		Map<String, Object> mapResult = ejecutaSP(new ObtienePolizaUnicaSP(getDataSource()), paramPolUnica);
		List<PolizaVigenteVO> listaDatosPoliza = (List<PolizaVigenteVO>)mapResult.get("pv_registro_o");
		return listaDatosPoliza;
	}
	
	protected class ObtienePolizaUnicaSP extends StoredProcedure {

		protected ObtienePolizaUnicaSP(DataSource dataSource) {
			super(dataSource, "PKG_PRESINIESTRO.P_OBTIENE_POLIZAUNICA");
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new DatosListaPoliza()));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}


	@Override
	public String validaExclusionPenalizacion(HashMap<String, Object> paramExclusion) throws Exception {
		Map<String, Object> resultado = ejecutaSP(new ExisteEsclusionPenalizacion(getDataSource()), paramExclusion);
		logger.debug( resultado.get("pv_existe_o"));
		return (String) resultado.get("pv_existe_o");
	}
	
    protected class ExisteEsclusionPenalizacion extends StoredProcedure {
    	
    	protected ExisteEsclusionPenalizacion(DataSource dataSource) {
    		
    		super(dataSource, "PKG_PRESINIESTRO.P_ELIMACION_PENALIZACION");
    		declareParameter(new SqlParameter("pv_cdunieco_i",   OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_existe_o", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    }
	
	@Override
	public void actualizaMsinies(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsituac,
			String nmsuplem,
			String status,
			String aaapertu,
			String nmsinies,
			Date feocurre,
			String cdicd,
			String cdicd2,
			String nreclamo) throws Exception
	{
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsituac_i" , nmsituac);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_status_i"   , status);
		params.put("pv_aaapertu_i" , aaapertu);
		params.put("pv_nmsinies_i" , nmsinies);
		params.put("pv_feocurre_i" , feocurre);
		params.put("pv_cdicd_i"    , cdicd);
		params.put("pv_cdicd2_i"   , cdicd2);
		params.put("pv_nreclamo_i" , nreclamo);
		logger.debug("actualizaMsinies params: "+params);
		ejecutaSP(new ActualizaMultisiniestro(this.getDataSource()), params);
		logger.debug("actualizaMsinies end");
	}
	
	protected class ActualizaMultisiniestro extends StoredProcedure
	{
		protected ActualizaMultisiniestro(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_ACTUALIZA_MULTISINIESTRO");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_aaapertu_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsinies_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feocurre_i" , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cdicd_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdicd2_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nreclamo_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void P_MOV_MAUTSINI(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem,
			String nmsituac,
			String aaapertu,
			String status,
			String nmsinies,
			String nfactura,
			String cdgarant,
			String cdconval,
			String cdconcep,
			String idconcep,
			String nmordina,
			String areaauto,
			String swautori,
			String tipautor,
			String comments,
			String accion) throws Exception
	{
		Map<String,String> p = new HashMap<String,String>();
		p.put("pv_cdunieco_i" , cdunieco);
		p.put("pv_cdramo_i"   , cdramo);
		p.put("pv_estado_i"   , estado);
		p.put("pv_nmpoliza_i" , nmpoliza);
		p.put("pv_nmsuplem_i" , nmsuplem);
		p.put("pv_nmsituac_i" , nmsituac);
		p.put("pv_aaapertu_i" , aaapertu);
		p.put("pv_status_i"   , status);
		p.put("pv_nmsinies_i" , nmsinies);
		p.put("pv_nfactura_i" , nfactura);
		p.put("pv_cdgarant_i" , cdgarant);
		p.put("pv_cdconval_i" , cdconval);
		p.put("pv_cdconcep_i" , cdconcep);
		p.put("pv_idconcep_i" , idconcep);
		p.put("pv_nmordina_i" , nmordina);
		p.put("pv_areaauto_i" , areaauto);
		p.put("pv_swautori_i" , swautori);
		p.put("pv_tipautor_i" , tipautor);
		p.put("pv_comments_i" , comments);
		p.put("pv_accion_i"   , accion);
		logger.debug("actualizaMsinies params: "+p);
		ejecutaSP(new PMOVMAUTSINI(this.getDataSource()), p);
		logger.debug("actualizaMsinies end");
	}
	
	protected class PMOVMAUTSINI extends StoredProcedure
	{
		protected PMOVMAUTSINI(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_MOV_MAUTSINI");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_aaapertu_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsinies_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nfactura_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdconval_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdconcep_i" , OracleTypes.VARCHAR));
			//declareParameter(new SqlParameter("pv_idconcep_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmordina_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_areaauto_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_swautori_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipautor_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_comments_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_accion_i"   , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	public void P_MOV_MSINIVAL(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem,
			String nmsituac,
			String aaapertu,
			String status,
			String nmsinies,
			String nfactura,
			String cdgarant,
			String cdconval,
			String cdconcep,
			String idconcep,
			String cdcapita,
			String nmordina,
			Date   femovimi,
			String cdmoneda,
			String ptprecio,
			String cantidad,
			String destopor,
			String destoimp,
			String ptimport,
			String ptrecobr,
			String nmanno,
			String nmapunte,
			String userregi,
			Date   feregist,
			String accion,
			String ptpcioex,
			String dctoimex,
			String ptimpoex) throws Exception
	{
		Map<String,Object>p=new HashMap<String,Object>();
		p.put("pv_cdunieco_i" , cdunieco);
		p.put("pv_cdramo_i"   , cdramo);
		p.put("pv_estado_i"   , estado);
		p.put("pv_nmpoliza_i" , nmpoliza);
		p.put("pv_nmsuplem_i" , nmsuplem);
		p.put("pv_nmsituac_i" , nmsituac);
		p.put("pv_aaapertu_i" , aaapertu);
		p.put("pv_status_i"   , status);
		p.put("pv_nmsinies_i" , nmsinies);
		p.put("pv_nfactura_i" , nfactura);
		p.put("pv_cdgarant_i" , cdgarant);
		p.put("pv_cdconval_i" , cdconval);
		p.put("pv_cdconcep_i" , cdconcep);
		p.put("pv_idconcep_i" , idconcep);
		p.put("pv_cdcapita_i" , cdcapita);
		p.put("pv_nmordina_i" , nmordina);
		p.put("pv_femovimi_i" , femovimi);
		p.put("pv_cdmoneda_i" , cdmoneda);
		p.put("pv_ptprecio_i" , ptprecio);
		p.put("pv_cantidad_i" , cantidad);
		p.put("pv_destopor_i" , destopor);
		p.put("pv_destoimp_i" , destoimp);
		p.put("pv_ptimport_i" , ptimport);
		p.put("pv_ptrecobr_i" , ptrecobr);
		p.put("pv_nmanno_i"   , nmanno);
		p.put("pv_nmapunte_i" , nmapunte);
		p.put("pv_userregi_i" , userregi);
		p.put("pv_feregist_i" , feregist);
		p.put("pv_accion_i"   , accion);
		p.put("pv_ptpcioex_i"   , ptpcioex);
		p.put("pv_dctoimex_i"   , dctoimex);
		p.put("pv_ptimpoex_i"   , ptimpoex);
		logger.debug("P_MOV_MSINIVAL params: "+p);
		ejecutaSP(new PMOVMSINIVAL(this.getDataSource()), p);
		logger.debug("P_MOV_MSINIVAL end");
	}
	
	protected class PMOVMSINIVAL extends StoredProcedure
	{
		protected PMOVMSINIVAL(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_MOV_MSINIVAL1");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_aaapertu_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsinies_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nfactura_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdconval_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdconcep_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_idconcep_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdcapita_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmordina_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_femovimi_i" , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cdmoneda_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptprecio_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cantidad_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_destopor_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_destoimp_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptimport_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptrecobr_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmanno_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmapunte_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_userregi_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feregist_i" , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_accion_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptpcioex_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dctoimex_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptimpoex_i"   , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>P_GET_MSINIVAL(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem,
			String nmsituac,
			String aaapertu,
			String status,
			String nmsinies,
			String nfactura) throws Exception
	{
		Map<String,String>p=new HashMap<String,String>();
		p.put("pv_cdunieco_i" , cdunieco);
		p.put("pv_cdramo_i"   , cdramo);
		p.put("pv_estado_i"   , estado);
		p.put("pv_nmpoliza_i" , nmpoliza);
		p.put("pv_nmsuplem_i" , nmsuplem);
		p.put("pv_nmsituac_i" , nmsituac);
		p.put("pv_aaapertu_i" , aaapertu);
		p.put("pv_status_i"   , status);
		p.put("pv_nmsinies_i" , nmsinies);
		p.put("pv_nfactura_i" , nfactura);
		logger.debug("P_GET_MSINIVAL params: "+p);
		Map<String, Object> mapResult = ejecutaSP(new PGETMSINIVAL(this.getDataSource()), p);
		return (List<Map<String,String>>) mapResult.get("pv_registro_o");
	}
	
	protected class PGETMSINIVAL extends StoredProcedure
	{
		protected PGETMSINIVAL(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_MSINIVAL");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_aaapertu_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsinies_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nfactura_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void P_MOV_TDSINIVAL(
			String cdunieco,String cdramo,String estado,String nmpoliza,String nmsuplem,
			String nmsituac,String aaapertu,String status,String nmsinies,String nfactura,
			String cdgarant,String cdconval,String cdconcep,String idconcep,String nmordina,
			String nmordmov,String ptimport,String comments,String userregi,Date feregist,String accion) throws Exception
	{
		Map<String,Object>p=new HashMap<String,Object>();
		p.put("pv_cdunieco_i" , cdunieco);
		p.put("pv_cdramo_i"   , cdramo);
		p.put("pv_estado_i"   , estado);
		p.put("pv_nmpoliza_i" , nmpoliza);
		p.put("pv_nmsuplem_i" , nmsuplem);
		p.put("pv_nmsituac_i" , nmsituac);
		p.put("pv_aaapertu_i" , aaapertu);
		p.put("pv_status_i"   , status);
		p.put("pv_nmsinies_i" , nmsinies);
		p.put("pv_nfactura_i" , nfactura);
		p.put("pv_cdgarant_i" , cdgarant);
		p.put("pv_cdconval_i" , cdconval);
		p.put("pv_cdconcep_i" , cdconcep);
		p.put("pv_idconcep_i" , idconcep);
		p.put("pv_nmordina_i" , nmordina);
		p.put("pv_nmordmov_i" , nmordmov);
		p.put("pv_ptimport_i" , ptimport);
		p.put("pv_comments_i" , comments);
		p.put("pv_userregi_i" , userregi);
		p.put("pv_feregist_i" , feregist);
		p.put("pv_accion_i"   , accion);
		logger.debug("P_MOV_TDSINIVAL paras: "+p);
		ejecutaSP(new PMOVTDSINIVAL(this.getDataSource()), p);
		logger.debug("P_MOV_TDSINIVAL end");
	}
	
	protected class PMOVTDSINIVAL extends StoredProcedure
	{
		protected PMOVTDSINIVAL(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_MOV_TDSINIVAL");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_aaapertu_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsinies_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nfactura_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdconval_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdconcep_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_idconcep_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmordina_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmordmov_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ptimport_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_comments_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_userregi_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_feregist_i" , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_accion_i"   , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>P_GET_TDSINIVAL(
			String cdunieco,String cdramo,String estado,String nmpoliza,String nmsuplem,
			String nmsituac,String aaapertu,String status,String nmsinies,String nfactura,
			String cdgarant,String cdconval,String cdconcep,String idconcep,String nmordina) throws Exception
	{
		Map<String,Object>p=new HashMap<String,Object>();
		p.put("pv_cdunieco_i" , cdunieco);
		p.put("pv_cdramo_i"   , cdramo);
		p.put("pv_estado_i"   , estado);
		p.put("pv_nmpoliza_i" , nmpoliza);
		p.put("pv_nmsuplem_i" , nmsuplem);
		p.put("pv_nmsituac_i" , nmsituac);
		p.put("pv_aaapertu_i" , aaapertu);
		p.put("pv_status_i"   , status);
		p.put("pv_nmsinies_i" , nmsinies);
		p.put("pv_nfactura_i" , nfactura);
		p.put("pv_cdgarant_i" , cdgarant);
		p.put("pv_cdconval_i" , cdconval);
		p.put("pv_cdconcep_i" , cdconcep);
		p.put("pv_idconcep_i" , idconcep);
		p.put("pv_nmordina_i" , nmordina);
		logger.debug("P_GET_TDSINIVAL params: "+p);
		Map<String, Object> mapResult = ejecutaSP(new PGETTDSINIVAL(this.getDataSource()), p);
		return (List<Map<String,String>>) mapResult.get("pv_registro_o");
	}
	
	protected class PGETTDSINIVAL extends StoredProcedure
	{
		protected PGETTDSINIVAL(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_TDSINIVAL");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_aaapertu_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsinies_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nfactura_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdgarant_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdconval_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdconcep_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_idconcep_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmordina_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	public List<Map<String,String>>P_GET_FACTURAS_SINIESTRO(
			String cdunieco,String cdramo,String estado,String nmpoliza,String nmsuplem,
			String nmsituac,String aaapertu,String status,String nmsinies) throws Exception
	{
		Map<String,Object>p=new HashMap<String,Object>();
		p.put("pv_cdunieco_i" , cdunieco);
		p.put("pv_cdramo_i"   , cdramo);
		p.put("pv_estado_i"   , estado);
		p.put("pv_nmpoliza_i" , nmpoliza);
		p.put("pv_nmsuplem_i" , nmsuplem);
		p.put("pv_nmsituac_i" , nmsituac);
		p.put("pv_aaapertu_i" , aaapertu);
		p.put("pv_status_i"   , status);
		p.put("pv_nmsinies_i" , nmsinies);
		logger.debug("P_GET_FACTURAS_SINIESTRO params: "+p);
		Map<String, Object> mapResult = ejecutaSP(new PGETFACTURASSINIESTRO(this.getDataSource()), p);
		return (List<Map<String,String>>) mapResult.get("pv_registro_o");
	}
	
	protected class PGETFACTURASSINIESTRO extends StoredProcedure
	{
		protected PGETFACTURASSINIESTRO(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_GET_FACTURAS_SINIESTRO");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_aaapertu_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsinies_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	@Override
	public List<Map<String,String>>cargaHistorialSiniestros(Map<String,String> params) throws Exception
	{
		Map<String, Object> mapResult = ejecutaSP(new CargaHistorialHiniestros(this.getDataSource()), params);
		return (List<Map<String,String>>) mapResult.get("pv_registro_o");
	}
	
	protected class CargaHistorialHiniestros extends StoredProcedure
	{
		protected CargaHistorialHiniestros(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_GET_HIST_STROS");
			declareParameter(new SqlParameter("pv_cdperson_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<GenericVO>obtenerCodigosMedicos(String idconcep, String subcaden) throws Exception
	{
		Map<String,String>p=new HashMap<String,String>();
		p.put("pv_idconcep_i" , idconcep);
		p.put("pv_descripc_i" , subcaden);
		logger.debug("obtenerCodigosMedicos params: "+p);
		Map<String, Object> mapResult = ejecutaSP(new ObtenerCodigosMedicos(this.getDataSource()), p);
		List<Map<String,String>>lista=(List<Map<String,String>>) mapResult.get("pv_registro_o");
		List<GenericVO>listaG=new ArrayList<GenericVO>();
		if(lista!=null)
		{
			for(Map<String,String>cpt:lista)
			{
				listaG.add(new GenericVO(cpt.get("CLAVE"),cpt.get("VALOR")));
			}
		}
		return listaG;
	}
	
	protected class ObtenerCodigosMedicos extends StoredProcedure
	{
		protected ObtenerCodigosMedicos(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_LISTA_CODIGOS_MEDICOS");
			declareParameter(new SqlParameter("pv_idconcep_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_descripc_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>obtenerLlaveSiniestroReembolso(String ntramite) throws Exception
	{
		Map<String,String>p=new HashMap<String,String>();
		p.put("pv_ntramite_i" , ntramite);
		logger.debug("obtenerLlaveSiniestroReembolso params: "+p);
		Map<String, Object> mapResult = ejecutaSP(new ObtenerLlaveSiniestroReembolso(this.getDataSource()), p);
		List<Map<String,String>>lista=(List<Map<String,String>>) mapResult.get("pv_registro_o");
		Map<String,String>llave=null;
		if(lista!=null)
		{
			llave=lista.get(0);
		}
		logger.debug("obtenerLlaveSiniestroReembolso llave: "+llave);
		return llave;
	}
	
	protected class ObtenerLlaveSiniestroReembolso extends StoredProcedure
	{
		protected ObtenerLlaveSiniestroReembolso(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_GET_LLAVE_SINIES_REEMBOLSO");
			declareParameter(new SqlParameter("pv_ntramite_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	public List<Map<String,String>> obtieneDatosGeneralesSiniestro(Map<String, Object> params) throws Exception {
		Map<String, Object> result = ejecutaSP(new ObtieneDatosGeneralesSiniestro(this.getDataSource()), params);
		return (List<Map<String,String>>)result.get("pv_registro_o");
	}
	
	protected class ObtieneDatosGeneralesSiniestro extends StoredProcedure {
		protected ObtieneDatosGeneralesSiniestro(DataSource dataSource) {
			// TODO: Terminar cuando este listo el SP
			super(dataSource, "PKG_SINIESTRO.P_LISTA_DG_STROS");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_aaapertu_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsinies_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ntramite_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	/*
	protected class ObtieneDatosGeneralesSiniestroMapper  implements RowMapper<DatosGeneralesSiniestroVO> {
        public DatosGeneralesSiniestroVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        	DatosGeneralesSiniestroVO datosSiniestro = new DatosGeneralesSiniestroVO();
        	// TODO: Terminar cuando este listo el SP
            return datosSiniestro;
        }
    }
    */
	
	
	@Override
	public Map<String, Object> actualizaDatosGeneralesSiniestro(Map<String, Object> params) throws Exception {
		return ejecutaSP(new ActualizaDatosGeneralesSiniestro(this.getDataSource()), params);
	}
	
	protected class ActualizaDatosGeneralesSiniestro extends StoredProcedure {
		protected ActualizaDatosGeneralesSiniestro(DataSource dataSource) {
			super(dataSource, "PKG_SINIESTRO.P_MOD_MSINIEST");
            declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdramo_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_aaapertu_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmsinies_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_estado_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_feocurre_i" , OracleTypes.DATE));
            declareParameter(new SqlParameter("pv_nmreclamo_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdicd_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdicd2_i" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("pv_cdcausa_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<HistorialSiniestroVO> obtieneHistorialReclamaciones(Map<String, Object> params) throws Exception {
		Map<String, Object> result = ejecutaSP(new ObtieneHistorialReclamaciones(this.getDataSource()), params);
		return (List<HistorialSiniestroVO>)result.get("pv_registro_o");
	}
	
	protected class ObtieneHistorialReclamaciones extends StoredProcedure {
		protected ObtieneHistorialReclamaciones(DataSource dataSource) {
			// TODO: Terminar cuando este listo el SP
			super(dataSource, "");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_aaapertu_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsinies_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ntramite_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtieneHistorialReclamacionesMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class ObtieneHistorialReclamacionesMapper  implements RowMapper<HistorialSiniestroVO> {
        public HistorialSiniestroVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        	HistorialSiniestroVO historialReclamacion = new HistorialSiniestroVO();
        	// TODO: Terminar cuando este listo el SP
            return historialReclamacion;
        }
    }
	
	
	
	@Override
	public List<Map<String,String>>P_GET_CONCEPTOS_FACTURA(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem,
			String nmsituac,
			String aaapertu,
			String status,
			String nmsinies,
			String nfactura) throws Exception
	{
		Map<String,Object>p=new HashMap<String,Object>();
		p.put("pv_cdunieco_i" , cdunieco);
		p.put("pv_cdramo_i"   , cdramo);
		p.put("pv_estado_i"   , estado);
		p.put("pv_nmpoliza_i" , nmpoliza);
		p.put("pv_nmsuplem_i" , nmsuplem);
		p.put("pv_nmsituac_i" , nmsituac);
		p.put("pv_aaapertu_i" , aaapertu);
		p.put("pv_status_i"   , status);
		p.put("pv_nmsinies_i" , nmsinies);
		p.put("pv_nfactura_i" , nfactura);
		logger.debug("P_GET_CONCEPTOS_FACTURA params: "+p);
		Map<String, Object> mapResult = ejecutaSP(new PGETCONCEPTOSFACTURA(this.getDataSource()), p);
		return (List<Map<String,String>>) mapResult.get("pv_registro_o");
	}
	
	protected class PGETCONCEPTOSFACTURA extends StoredProcedure
	{
		protected PGETCONCEPTOSFACTURA(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_GET_CONCEPTOS_FACTURA");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_aaapertu_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsinies_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nfactura_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>obtenerCopagoDeducible(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem,
			String nmsituac,
			String aaapertu,
			String status,
			String nmsinies,
			String nfactura) throws Exception
	{
		Map<String,Object>p=new HashMap<String,Object>();
		p.put("cdunieco" , cdunieco);
		p.put("cdramo"   , cdramo);
		p.put("estado"   , estado);
		p.put("nmpoliza" , nmpoliza);
		p.put("nmsuplem" , nmsuplem);
		p.put("nmsituac" , nmsituac);
		p.put("aaapertu" , aaapertu);
		p.put("status"   , status);
		p.put("nmsinies" , nmsinies);
		p.put("nfactura" , nfactura);
		logger.debug("obtenerCopagoDeducible params: "+p);
		Map<String, Object> mapResult = ejecutaSP(new ObtenerCopagoDeducible(this.getDataSource()), p);
		List<Map<String,String>> lista = (List<Map<String,String>>) mapResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new Exception("No se encuentra el copago deducible");
		}
		if(lista.size()>1)
		{
			throw new Exception("Copago y deducible duplicado");
		}
		Map<String,String>copagoDeducible = lista.get(0);
		logger.debug("Copago deducible: "+copagoDeducible);
		return copagoDeducible;
	}
	
	protected class ObtenerCopagoDeducible extends StoredProcedure
	{
		protected ObtenerCopagoDeducible(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_GET_DATOS_SUBG");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("aaapertu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsinies" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nfactura" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String validaPorcentajePenalizacion(String zonaContratada,String zonaAtencion) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_zcontratada_i", zonaContratada);
		params.put("pv_zatencion_i", zonaAtencion);
		Map<String, Object> mapResult = ejecutaSP(new ValidaPorcentajePenalizacionSP(getDataSource()), params);
		return (String) mapResult.get("pv_penalizacion_o");
	}
	
	protected class ValidaPorcentajePenalizacionSP extends StoredProcedure {

		protected ValidaPorcentajePenalizacionSP(DataSource dataSource) {
			super(dataSource, "PKG_PRESINIESTRO.P_GET_PENALIZACION");
			declareParameter(new SqlParameter("pv_zcontratada_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_zatencion_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_penalizacion_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			compile();
		}
	}


	@Override
	public String obtieneAutorizacionProceso(String nmAutSer) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("PV_NMAUTSER_I", nmAutSer);
		
		Map<String, Object> resultado = ejecutaSP(new ExisteAutorizacionProceso(getDataSource()), params);
		return (String) resultado.get("PV_VALOR_I");
	}
	
    protected class ExisteAutorizacionProceso extends StoredProcedure {
    	protected ExisteAutorizacionProceso(DataSource dataSource) {
    		super(dataSource, "PKG_PRESINIESTRO.P_VALIDA_TDETAUTS");
    		declareParameter(new SqlParameter("PV_NMAUTSER_I",   OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_VALOR_I", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
    		compile();
    	}
    }
	
	@Override
	public String validaDocumentosCargados(HashMap<String, String> params) throws Exception {
		Map<String, Object> mapResult = ejecutaSP(new ValidaDocumentosCargados(getDataSource()), params);
		return (String) mapResult.get("PV_VALOR_O");
	}
	
	protected class ValidaDocumentosCargados extends StoredProcedure {

		protected ValidaDocumentosCargados(DataSource dataSource) {
			super(dataSource, "PKG_PRESINIESTRO.P_VALIDA_DOCUMENTOS");
			declareParameter(new SqlParameter("PV_NTRAMITE_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDRAMO_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_cdtippag_I", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTIPATE_I", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("PV_VALOR_O", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.VARCHAR));
	        declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
			compile();
		}
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Reclamo> obtieneDatosReclamoWS(Map<String, Object> params) throws Exception{
		Map<String, Object> result = ejecutaSP(new ObtieneDatosReclamoWS(this.getDataSource()), params);
		return (List<Reclamo>)result.get("pv_registro_o");
	}
	
	protected class ObtieneDatosReclamoWS extends StoredProcedure {
		protected ObtieneDatosReclamoWS(DataSource dataSource) {
			super(dataSource, "PKG_CONSULTA.P_WS_RECLAMACIONES");
			declareParameter(new SqlParameter("pv_ntramite_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ReclamoWSMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class ReclamoWSMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Reclamo reclamo =  new Reclamo();
        	Calendar cal;
        	
        	reclamo.setCanIce(rs.getDouble("CAN_ICE"));
        	reclamo.setConPag(rs.getInt("CON_PAG"));
        	reclamo.setConReg(0);
        	reclamo.setCveAfe(rs.getInt("CVE_AFE"));
        	reclamo.setCveAge(rs.getInt("CVE_AGE"));
        	reclamo.setCveCap(rs.getString("CVE_CAP"));
        	reclamo.setCveCob(rs.getInt("CVE_COB"));
        	reclamo.setCveDes(rs.getInt("CVE_DES"));
        	reclamo.setCveEdo(rs.getInt("CVE_EDO"));
        	reclamo.setCveMun(rs.getInt("CVE_MUN"));
        	reclamo.setEdoImp(rs.getInt("EDO_IMP"));
        	reclamo.setEdoOcu(rs.getInt("EDO_OCU"));
        	reclamo.setEstReg(rs.getString("EST_REG"));
        	
        	cal = Utilerias.getCalendar(rs.getString("FEC_FAC"), Constantes.FORMATO_FECHA);
        	if(cal != null){
        		reclamo.setFecFac(cal);
        	}else{
        		logger.error("NO SE PUDO PARSEAR LA FECHA FEC_FAC !!! " + rs.getString("FEC_FAC"));
        	}
        	
        	cal = Utilerias.getCalendar(rs.getString("FEC_OCU"), Constantes.FORMATO_FECHA);
        	if(cal != null){
        		reclamo.setFecOcu(cal);
        	}else{
        		logger.error("NO SE PUDO PARSEAR LA FECHA FEC_OCU !!! " + rs.getString("FEC_OCU"));
        	}

        	cal = Utilerias.getCalendar(rs.getString("FEC_PRO"), Constantes.FORMATO_FECHA);
        	if(cal != null){
        		reclamo.setFecPro(cal);
        	}else{
        		logger.error("NO SE PUDO PARSEAR LA FECHA FEC_PRO !!! " + rs.getString("FEC_PRO"));
        	}

        	cal = Utilerias.getCalendar(rs.getString("FEC_REG"), Constantes.FORMATO_FECHA);
        	if(cal != null){
        		reclamo.setFecReg(cal);
        	}else{
        		logger.error("NO SE PUDO PARSEAR LA FECHA FEC_REG !!! " + rs.getString("FEC_REG"));
        	}

        	cal = Utilerias.getCalendar(rs.getString("FIN_VIG"), Constantes.FORMATO_FECHA);
        	if(cal != null){
        		reclamo.setFinVig(cal);
        	}else{
        		logger.error("NO SE PUDO PARSEAR LA FECHA FIN_VIG !!! " + rs.getString("FIN_VIG"));
        	}


        	try {
        		logger.debug("--> Parseando hora rs.getString(HOR_OCU) -->> "+ rs.getString("HOR_OCU"));
        		cal = Calendar.getInstance();
        		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), rs.getInt("HOR_OCU"), 0);
        		logger.debug("--> Calendario obtenido -->> "+ cal);
        		reclamo.setHorOcu(cal);
        	} catch (Exception e) {
        		logger.error("NO SE PUDO PARSEAR LA hora HOR_OCU !!! ");
        		reclamo.setHorOcu(null);
        	}
        	try {
        		logger.debug("--> Parseando hora rs.getString(HOR_PRO) -->> "+ rs.getString("HOR_PRO"));
        		cal = Calendar.getInstance();
        		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), rs.getInt("HOR_PRO"), 0);
        		logger.debug("--> Calendario obtenido -->> "+ cal);
        		reclamo.setHorPro(cal);
        	} catch (Exception e) {
        		logger.error("NO SE PUDO PARSEAR LA hora HOR_PRO !!! ");
        		reclamo.setHorPro(null);
        	}
        	
        	reclamo.setIcodreclamo(rs.getInt("ICODRECLAMO"));
        	reclamo.setIdBen(rs.getInt("ID_BEN"));
        	reclamo.setIdBen1(rs.getInt("ID_BEN1"));
        	reclamo.setImpMov(rs.getDouble("IMP_MOV"));
        	reclamo.setIncPol(rs.getInt("INC_POL"));
        	
        	
        	cal = Utilerias.getCalendar(rs.getString("INI_VIG"), Constantes.FORMATO_FECHA);
        	if(cal != null){
        		reclamo.setIniVig(cal);
        	}else{
        		logger.error("NO SE PUDO PARSEAR LA FECHA INI_VIG !!! " + rs.getString("INI_VIG"));
        	}
        	
        	
        	reclamo.setIsrMov(rs.getDouble("ISR_MOV"));
        	reclamo.setIvaMov(rs.getDouble("IVA_MOV"));
        	reclamo.setIvrMov(rs.getDouble("IVR_MOV"));
        	reclamo.setMatAfe(rs.getString("MAT_AFE"));
        	reclamo.setMatCli(rs.getString("MAT_CLI"));
        	reclamo.setMunImp(rs.getInt("MUN_IMP"));
        	reclamo.setMunOcu(rs.getInt("MUN_OCU"));
        	reclamo.setNomAfe(rs.getString("NOM_AFE"));
        	reclamo.setNomCli(rs.getString("NOM_CLI"));
        	reclamo.setNumDoc(rs.getString("NUM_DOC"));
        	reclamo.setNumMov(rs.getInt("NUM_MOV"));
        	reclamo.setNumPol(rs.getInt("NUM_POL"));
        	reclamo.setNumSin(rs.getInt("NUM_SIN"));
        	reclamo.setNumSol(rs.getInt("NUM_SOL"));
        	reclamo.setObsSin(rs.getString("OBS_SIN"));
        	reclamo.setPatAfe(rs.getString("PAT_AFE"));
        	reclamo.setPatCli(rs.getString("PAT_CLI"));
        	reclamo.setRamPol(rs.getInt("RAM_POL"));
        	reclamo.setRegRec(rs.getString("REG_REC"));
        	reclamo.setSucAdm(rs.getInt("SUC_ADM"));
        	reclamo.setSucPol(rs.getInt("SUC_POL"));
        	reclamo.setTipBen(rs.getString("TIP_BEN"));
        	reclamo.setTipBen1(rs.getString("TIP_BEN1"));
        	reclamo.setTipImp(rs.getString("TIP_IMP"));
        	reclamo.setTipMov(rs.getString("TIP_MOV"));
        	reclamo.setTisecuencialafi(rs.getInt("TISECUENCIALAFI"));
            return reclamo;
        }
    }


	@Override
	public void cambiarEstatusMAUTSERV(String nmautser, String status) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmautser_i", nmautser);
		params.put("pv_status_i", status);
		Map<String, Object> mapResult = ejecutaSP(new CambioEstatusMAUTSERV(getDataSource()), params);
	}
	
	protected class CambioEstatusMAUTSERV extends StoredProcedure
	{
		protected CambioEstatusMAUTSERV(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.p_upd_status_mautserv");
			declareParameter(new SqlParameter("pv_nmautser_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_status_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}


	@Override
	public List<AltaTramiteVO> consultaListaAltaTramite(String ntramite) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_ntramite_i", ntramite);
		Map<String, Object> mapResult = ejecutaSP(new ConsultaListaAltaTramite(getDataSource()), params);
		return (List<AltaTramiteVO>) mapResult.get("pv_registro_o");
	}
	
	protected class ConsultaListaAltaTramite extends StoredProcedure
	{
		protected ConsultaListaAltaTramite(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.p_get_tfacmesctrl_tworksin");
			declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DatosListaAltaTramite()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	protected class DatosListaAltaTramite  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	AltaTramiteVO consulta = new AltaTramiteVO();
        	//rs.getString("")
        	consulta.setNtramite(rs.getString("NTRAMITE"));
        	consulta.setNfactura(rs.getString("NFACTURA"));
        	consulta.setFfactura(Utilerias.formateaFecha(rs.getString("FFACTURA")));
        	consulta.setCdtipser(rs.getString("CDTIPSER"));
        	consulta.setDstipser(rs.getString("DSTIPSER"));
        	consulta.setDspresta(rs.getString("DSPRESTA"));
        	consulta.setCdpresta(rs.getString("CDPRESTA"));
        	consulta.setPtimport(rs.getString("PTIMPORT"));
        	consulta.setCdgarant(rs.getString("CDGARANT"));
        	consulta.setCdconval(rs.getString("CDCONVAL"));
        	consulta.setDescporc(rs.getString("DESCPORC"));
        	consulta.setDescnume(rs.getString("DESCNUME"));
        	consulta.setCdunieco(rs.getString("CDUNIECO"));
        	consulta.setCdramo(rs.getString("CDRAMO"));
        	consulta.setEstado(rs.getString("ESTADO"));
        	consulta.setNmpoliza(rs.getString("NMPOLIZA"));
        	consulta.setNmsolici(rs.getString("NMSOLICI"));
        	consulta.setNmsuplem(rs.getString("NMSUPLEM"));
        	consulta.setNmsituac(rs.getString("NMSITUAC"));
        	consulta.setCdtipsit(rs.getString("CDTIPSIT"));
        	consulta.setNombre(rs.getString("NOMBRE"));
        	consulta.setCdperson(rs.getString("CDPERSON"));
        	consulta.setFeocurre(Utilerias.formateaFecha(rs.getString("FEOCURRE")));
        	consulta.setNmautser(rs.getString("NMAUTSER"));
        	consulta.setNmpoliex(rs.getString("NMPOLIEX"));
        	consulta.setNombreAsegurado(rs.getString("CDPERSON")+" "+ rs.getString("NOMBRE"));
        	consulta.setCdmoneda(rs.getString("CDMONEDA"));
        	consulta.setDesTipomoneda(rs.getString("DESTIPOMONEDA"));
        	consulta.setTasacamb(rs.getString("TASACAMB"));
        	consulta.setPtimporta(rs.getString("PTIMPORTA"));
            return consulta;
        }
    }


	@Override
	public List<MesaControlVO> consultaListaMesaControl(String ntramite) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_ntramite_i", ntramite);
		Map<String, Object> mapResult = ejecutaSP(new ConsultaListaMesaControl(getDataSource()), params);
		return (List<MesaControlVO>) mapResult.get("pv_registro_o");
	}
	
	protected class ConsultaListaMesaControl extends StoredProcedure
	{
		protected ConsultaListaMesaControl(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_GET_TRAMITE_COMPLETO");
			declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DatosListaMesaControl()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	protected class DatosListaMesaControl  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	MesaControlVO consulta = new MesaControlVO();
        	//rs.getString("")
        	consulta.setNtramitemc(rs.getString("NTRAMITE"));
        	consulta.setCduniecomc(rs.getString("CDUNIECO"));
        	consulta.setCdramomc(rs.getString("CDRAMO"));
        	consulta.setEstadomc(rs.getString("ESTADO"));
        	consulta.setNmpolizamc(rs.getString("NMPOLIZA"));
        	consulta.setNmsuplemmc(rs.getString("NMSUPLEM"));
        	consulta.setNmsolicimc(rs.getString("NMSOLICI"));
        	consulta.setCdsucadmmc(rs.getString("CDSUCADM"));
        	consulta.setCdsucdocmc(rs.getString("CDSUCDOC"));
        	consulta.setCdsubrammc(rs.getString("CDSUBRAM"));
        	consulta.setCdtiptramc(rs.getString("CDTIPTRA"));
        	consulta.setFerecepcmc(Utilerias.formateaFecha(rs.getString("FERECEPC")));
        	consulta.setCdagentemc(rs.getString("CDAGENTE"));
        	consulta.setReferenciamc(rs.getString("REFERENCIA"));
        	consulta.setNombremc(rs.getString("NOMBRE"));
        	consulta.setFecstatumc(Utilerias.formateaFecha(rs.getString("FECSTATU")));
        	consulta.setStatusmc(rs.getString("STATUS"));
        	consulta.setCommentsmc(rs.getString("COMMENTS"));
        	consulta.setCdtipsitmc(rs.getString("CDTIPSIT"));
        	consulta.setOtvalor01mc(rs.getString("OTVALOR01"));
        	consulta.setOtvalor02mc(rs.getString("OTVALOR02"));
        	consulta.setOtvalor03mc(rs.getString("OTVALOR03"));
        	consulta.setOtvalor04mc(rs.getString("OTVALOR04"));
        	consulta.setOtvalor05mc(rs.getString("OTVALOR05"));
        	consulta.setOtvalor06mc(Utilerias.formateaFecha(rs.getString("OTVALOR06")));
        	consulta.setOtvalor07mc(rs.getString("OTVALOR07"));
        	consulta.setOtvalor08mc(rs.getString("OTVALOR08"));
        	consulta.setOtvalor09mc(rs.getString("OTVALOR09"));
        	consulta.setOtvalor10mc(Utilerias.formateaFecha(rs.getString("OTVALOR10")));
        	consulta.setOtvalor11mc(rs.getString("OTVALOR11"));
        	consulta.setOtvalor12mc(rs.getString("OTVALOR12"));
        	consulta.setOtvalor13mc(rs.getString("OTVALOR13"));
        	consulta.setOtvalor14mc(rs.getString("OTVALOR14"));
        	consulta.setOtvalor15mc(rs.getString("OTVALOR15"));
        	consulta.setOtvalor16mc(rs.getString("OTVALOR16"));
        	consulta.setOtvalor17mc(rs.getString("OTVALOR17"));
        	consulta.setOtvalor18mc(rs.getString("OTVALOR18"));
        	consulta.setOtvalor19mc(rs.getString("OTVALOR19"));
        	consulta.setOtvalor20mc(rs.getString("OTVALOR20"));
        	consulta.setOtvalor21mc(rs.getString("OTVALOR21"));
        	consulta.setOtvalor22mc(rs.getString("OTVALOR22"));
        	consulta.setOtvalor23mc(rs.getString("OTVALOR23"));
        	consulta.setOtvalor24mc(rs.getString("OTVALOR24"));
        	consulta.setOtvalor25mc(rs.getString("OTVALOR25"));
        	consulta.setOtvalor26mc(rs.getString("OTVALOR26"));
        	consulta.setOtvalor27mc(rs.getString("OTVALOR27"));
        	consulta.setOtvalor28mc(rs.getString("OTVALOR28"));
        	consulta.setOtvalor29mc(rs.getString("OTVALOR29"));
        	consulta.setOtvalor30mc(rs.getString("OTVALOR30"));
        	consulta.setOtvalor31mc(rs.getString("OTVALOR31"));
        	consulta.setOtvalor32mc(rs.getString("OTVALOR32"));
        	consulta.setOtvalor33mc(rs.getString("OTVALOR33"));
        	consulta.setOtvalor34mc(rs.getString("OTVALOR34"));
        	consulta.setOtvalor35mc(rs.getString("OTVALOR35"));
        	consulta.setOtvalor36mc(rs.getString("OTVALOR36"));
        	consulta.setOtvalor37mc(rs.getString("OTVALOR37"));
        	consulta.setOtvalor38mc(rs.getString("OTVALOR38"));
        	consulta.setOtvalor39mc(rs.getString("OTVALOR39"));
        	consulta.setOtvalor40mc(rs.getString("OTVALOR40"));
        	consulta.setOtvalor41mc(rs.getString("OTVALOR41"));
        	consulta.setOtvalor42mc(rs.getString("OTVALOR42"));
        	consulta.setOtvalor43mc(rs.getString("OTVALOR43"));
        	consulta.setOtvalor44mc(rs.getString("OTVALOR44"));
        	consulta.setOtvalor45mc(rs.getString("OTVALOR45"));
        	consulta.setOtvalor46mc(rs.getString("OTVALOR46"));
        	consulta.setOtvalor47mc(rs.getString("OTVALOR47"));
        	consulta.setOtvalor48mc(rs.getString("OTVALOR48"));
        	consulta.setOtvalor49mc(rs.getString("OTVALOR49"));
        	consulta.setOtvalor50mc(rs.getString("OTVALOR50"));
        	return consulta;
        }
    }
	
	@Override
	public void eliminacionTworksin(String ntramite) throws DaoException {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmtramite_i", ntramite);
		params.put("pv_nmautser_i", null);
		
		logger.debug("VALOR DEL PARAMS");
		logger.debug(params);
		Map<String,Object> resultadoMap=this.ejecutaSP(new EliminacionRegistrosTworksin(this.getDataSource()), params);
	}
	protected class EliminacionRegistrosTworksin extends StoredProcedure
	{
		protected EliminacionRegistrosTworksin(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_BORRA_TWORKSIN");
			declareParameter(new SqlParameter("pv_nmtramite_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmautser_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}


	@Override
	public void eliminacionTFacMesaControl(String ntramite) throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_nmtramite_i", ntramite);
		 
		Map<String,Object> resultadoMap=this.ejecutaSP(new EliminacionTFacMesaControl(this.getDataSource()), params);
	}
	protected class EliminacionTFacMesaControl extends StoredProcedure
	{
		protected EliminacionTFacMesaControl(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.p_borra_tfacmesctrl_tramite");
			declareParameter(new SqlParameter("pv_nmtramite_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public List<SiniestroVO>  solicitudPagoEnviada(Map params)throws DaoException {
		Map<String,Object> resultadoMap=this.ejecutaSP(new SolicitudPagoEnviada(this.getDataSource()), params);
		return (List<SiniestroVO>) resultadoMap.get("pv_registro_o");	
	}
	protected class SolicitudPagoEnviada extends StoredProcedure
	{
		protected SolicitudPagoEnviada(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_ACT_TRAMITE_SINI_A_ENVIADO");
			declareParameter(new SqlParameter("pv_ntramite_i", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new SiniestrosMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class SiniestrosMapper  implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        	SiniestroVO siniestro = new SiniestroVO();
        	siniestro.setCdunieco(rs.getString("CDUNIECO"));
        	siniestro.setCdramo(rs.getString("CDRAMO"));
        	siniestro.setEstado(rs.getString("ESTADO"));
        	siniestro.setNmpoliza(rs.getString("NMPOLIZA"));
        	siniestro.setNmsuplem(rs.getString("NMSUPLEM"));
        	siniestro.setNmsituac(rs.getString("NMSITUAC"));
        	siniestro.setAapertu(rs.getString("AAAPERTU"));
        	siniestro.setStatusSinies(rs.getString("STATUS"));
        	siniestro.setNmsinies(rs.getString("NMSINIES"));
        	
        	return siniestro;
        }
    }
	
	
	/*	public void eliminacionRegistrosTabla(String nmautser)
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
		*/
	
	@Override
	public Map<String,String> obtenerDatosProveedor(String cdpresta) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdpresta",cdpresta);
		Map<String,Object>respuesta = this.ejecutaSP(new ObtenerDatosProveedor(this.getDataSource()), params);
		List<Map<String,String>>registro = (List<Map<String, String>>) respuesta.get("pv_registro_o");
		if(registro==null||registro.isEmpty())
		{
			throw new Exception("No se encuentra el proveedor con clave '"+cdpresta+"'");
		}
		if(registro.size()>1)
		{
			throw new Exception("Se encontro mas de un proveedor con clave '"+cdpresta+"'");
		}
		return registro.get(0);
	}

	protected class ObtenerDatosProveedor extends StoredProcedure
	{
		protected ObtenerDatosProveedor(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_GET_MPRESTAD");
			declareParameter(new SqlParameter("cdpresta", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movTimpsini(String accion
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String aaapertu
			,String status
			,String nmsinies
			,String ntramite
			,String ptimport
			,String iva
			,String ivr
			,String isr
			,String cedular
			,boolean enviado) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("accion"   , accion);
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("nmsituac" , nmsituac);
		params.put("aaapertu" , aaapertu);
		params.put("status"   , status);
		params.put("nmsinies" , nmsinies);
		params.put("ntramite" , ntramite);
		params.put("ptimport" , ptimport);
		params.put("iva"      , iva);
		params.put("ivr"      , ivr);
		params.put("isr"      , isr);
		params.put("cedular"  , cedular);
		params.put("enviado",enviado?Constantes.SI:Constantes.NO);
		logger.debug("params: "+params);
		ejecutaSP(new MovTimpsini(this.getDataSource()), params);
	}
	
	protected class MovTimpsini extends StoredProcedure
	{
		protected MovTimpsini(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_MOV_TIMPSINI");
			declareParameter(new SqlParameter("accion"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("aaapertu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsinies" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ptimport" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("iva"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ivr"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("isr"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cedular"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("enviado"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>obtenerAutorizacionesFactura(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String aaapertu
			,String status
			,String nmsinies
			,String nfactura) throws Exception
	{
		Map<String,String>params = new HashMap<String,String>();
		params.put("cdunieco",cdunieco);
		params.put("cdramo",cdramo);
		params.put("estado",estado);
		params.put("nmpoliza",nmpoliza);
		params.put("nmsuplem",nmsuplem);
		params.put("nmsituac",nmsituac);
		params.put("aaapertu",aaapertu);
		params.put("status",status);
		params.put("nmsinies",nmsinies);
		params.put("nfactura",nfactura);
		logger.info("params: "+params);
		Map<String,Object> result=ejecutaSP(new ObtenerAutorizacionesFactura(getDataSource()), params);
		List<Map<String,String>>autorizacionesFacturaLista = (List<Map<String,String>>)result.get("pv_registro_o");
		if(autorizacionesFacturaLista==null || autorizacionesFacturaLista.size()==0)
		{
			throw new Exception("No se encuentran las autorizaciones de factura");
		}
		if(autorizacionesFacturaLista.size()>1)
		{
			throw new Exception("Se encontraron autorizaciones de factura duplicada");
		}
		return autorizacionesFacturaLista.get(0);
	}
	
	protected class ObtenerAutorizacionesFactura extends StoredProcedure
	{
		protected ObtenerAutorizacionesFactura(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_GET_MAUTSINI_FACTURA");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("aaapertu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsinies" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nfactura" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public void guardarTotalProcedenteFactura(String ntramite,String nfactura,String importe)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("ntramite" , ntramite);
		params.put("nfactura" , nfactura);
		params.put("importe"  , importe);
		logger.debug("params: "+params);
		ejecutaSP(new GuardarTotalProcedenteFactura(this.getDataSource()), params);
	}
	
	protected class GuardarTotalProcedenteFactura extends StoredProcedure
	{
		protected GuardarTotalProcedenteFactura(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_UPD_TFACTURA");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nfactura" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("importe"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String validaDocumentosAutServicio(String ntramite) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pv_ntramite_i", ntramite);
		
		Map<String, Object> resultado = ejecutaSP(new ExisteDocumentosAutServ(getDataSource()), params);
		return (String) resultado.get("PV_VALOR_O");
	}
	
    protected class ExisteDocumentosAutServ extends StoredProcedure {
    	
    	protected ExisteDocumentosAutServ(DataSource dataSource) {
    		
    		super(dataSource, "PKG_PRESINIESTRO.p_valida_doc_autser");
    		declareParameter(new SqlParameter("pv_ntramite_i",   OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("PV_VALOR_O", OracleTypes.VARCHAR));
    		declareParameter(new SqlOutParameter("PV_MSG_ID_O", OracleTypes.NUMERIC));
    		declareParameter(new SqlOutParameter("PV_TITLE_O", OracleTypes.VARCHAR));
    		compile();
    	}
    }
    
    @Override
    public void turnarTramite(String ntramite,String cdsisrol,String cdusuari) throws Exception
    {
    	Map<String,String>params=new HashMap<String,String>();
    	params.put("ntramite",ntramite);
    	params.put("cdsisrol",cdsisrol);
    	params.put("cdusuari",cdusuari);
    	logger.debug("params: "+params);
		ejecutaSP(new TurnarTramite(this.getDataSource()), params);
	}
	
	protected class TurnarTramite extends StoredProcedure
	{
		protected TurnarTramite(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_TURNAR_TRAMITE");
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>> obtenerUsuariosPorRol(String cdsisrol)throws Exception
	{
		Map<String,String>params = new HashMap<String,String>();
		params.put("cdsisrol",cdsisrol);
		Map<String,Object>result = this.ejecutaSP(new ObtenerUsuariosPorRol(this.getDataSource()), params);
		return (List<Map<String,String>>)result.get("pv_registro_o");
	}
	
	protected class ObtenerUsuariosPorRol extends StoredProcedure
	{
		protected ObtenerUsuariosPorRol(DataSource dataSource)
		{
			super(dataSource, "PKG_PRESINIESTRO.P_GET_USUARIOS_ROL");
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR  , new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void moverTramite(
			String ntramite
			,String nuevoStatus
			,String comments
			,String cdusuariSesion
			,String cdsisrolSesion
			,String cdusuariDestino
			,String cdsisrolDestino
			,String cdmotivo
			,String cdclausu
			) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("ntramite"        , ntramite);
		params.put("nuevoStatus"     , nuevoStatus);
		params.put("comments"        , comments);
		params.put("cdusuariSesion"  , cdusuariSesion);
		params.put("cdsisrolSesion"  , cdsisrolSesion);
		params.put("cdusuariDestino" , cdusuariDestino);
		params.put("cdsisrolDestino" , cdsisrolDestino);
		params.put("cdmotivo"        , cdmotivo);
		params.put("cdclausu"        , cdclausu);
		logger.info("params: "+params);
		ejecutaSP(new MoverTramite(getDataSource()), params);
	}
	
	protected class MoverTramite extends StoredProcedure
	{
		protected MoverTramite(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES.P_MOV_TRAMITE");
			declareParameter(new SqlParameter("ntramite"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nuevoStatus"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("comments"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuariSesion"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrolSesion"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuariDestino" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrolDestino" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdmotivo"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdclausu"        , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
}