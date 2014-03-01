package mx.com.gseguros.portal.siniestros.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import mx.com.gseguros.portal.siniestros.model.AutorizaServiciosVO;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaManteniVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaPorcentajeVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaProveedorVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTDETAUTSVO;
import mx.com.gseguros.portal.siniestros.model.ConsultaTTAPVAATVO;
import mx.com.gseguros.portal.siniestros.model.DatosSiniestroVO;
import mx.com.gseguros.portal.siniestros.model.ListaFacturasVO;
import mx.com.gseguros.portal.siniestros.model.PolizaVigenteVO;
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
	    public List<HashMap<String, String>> loadListaIncisosRechazos(
	    		HashMap<String, String> params) throws DaoException {
	    	
	    	Map<String, Object> mapResult = ejecutaSP(new LoadListaIncisosRechazos(getDataSource()), params);
	    	
	    	return (List<HashMap<String, String>>) mapResult.get("pv_registro_o");
	    }
	    
	    protected class LoadListaIncisosRechazos extends StoredProcedure {
	    	
	    	protected LoadListaIncisosRechazos(DataSource dataSource) {
	    		super(dataSource, "PKG_LISTAS.P_GET_CAUSAS_MOT_RECHAZO");
	    		
	    		declareParameter(new SqlParameter("pv_cdmotivo_i", OracleTypes.VARCHAR));
	    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new LoadListaIncisosRechazosMapper()));
	    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
	    		compile();
	    	}
	    }
	    
	    protected class LoadListaIncisosRechazosMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	HashMap<String, String> map =  new HashMap<String, String>();
	        	
	        	map.put("key", rs.getString("CDCAUMOT"));
	        	map.put("value", rs.getString("DSCAUMOT"));
	        	
	            return map;
	        }
	    }

	    @Override
	    public List<HashMap<String, String>> loadListaRechazos() throws DaoException {
	    	Map<String, Object> params = new HashMap<String, Object>();
	    	Map<String, Object> mapResult = ejecutaSP(new LoadListaRechazos(getDataSource()), params);
	    	
	    	return (List<HashMap<String, String>>) mapResult.get("pv_registro_o");
	    }
	    
	    protected class LoadListaRechazos extends StoredProcedure {
	    	
	    	protected LoadListaRechazos(DataSource dataSource) {
	    		super(dataSource, "PKG_LISTAS.P_GET_MOTIVOS_RECHAZO");
	    		
	    		declareParameter(new SqlOutParameter("pv_registro_o", OracleTypes.CURSOR, new LoadListaRechazosMapper()));
	    		declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.VARCHAR));
	    		declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
	    		compile();
	    	}
	    }
		
		
	    protected class LoadListaRechazosMapper  implements RowMapper {
	        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	HashMap<String, String> map =  new HashMap<String, String>();
	        	
	        	map.put("key", rs.getString("CDMOTIVO"));
	        	map.put("value", rs.getString("DSMOTIVO"));
	        	
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
				super(dataSource, "PKG_SATELITES.P_MOV_TFACMESCTRL");
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
		return (String) mapResult.get("pv_msg_id_o");
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
			String accion) throws Exception
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
		logger.debug("P_MOV_MSINIVAL params: "+p);
		ejecutaSP(new PMOVMSINIVAL(this.getDataSource()), p);
		logger.debug("P_MOV_MSINIVAL end");
	}
	
	protected class PMOVMSINIVAL extends StoredProcedure
	{
		protected PMOVMSINIVAL(DataSource dataSource)
		{
			super(dataSource, "PKG_SINIESTRO.P_MOV_MSINIVAl");
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
}