package mx.com.aon.flujos.presinietros.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import mx.com.aon.flujos.presinietros.service.PresiniestrosManager;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.InstrumentoPagoAtributosVO;
import mx.com.aon.portal.model.presiniestros.PreSiniestroVO;
import mx.com.aon.portal.model.presiniestros.AutomovilVO;
import mx.com.aon.portal.model.presiniestros.BeneficioVO;
import mx.com.aon.portal.model.presiniestros.DanoVO;
import mx.com.aon.flujos.presinietros.model.DocumentoVO;
import mx.com.aon.export.model.TableModelExport;

import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.service.PagedList;
import mx.com.gseguros.exception.ApplicationException;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.SqlParameter;

import java.text.SimpleDateFormat;

import static mx.com.aon.utils.Constantes.INSERT_MODE;
import static mx.com.aon.utils.Constantes.UPDATE_MODE;

import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_TRAMITES;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_PADECIMIENTOS;
import static mx.com.aon.portal.dao.PresiniestrosDAO.EJECUTA_GUARDA_TPREBEN;
import static mx.com.aon.portal.dao.PresiniestrosDAO.EJECUTA_GUARDA_TPREVGFAP;
import static mx.com.aon.portal.dao.PresiniestrosDAO.EJECUTA_GUARDA_TPREAUTO;
import static mx.com.aon.portal.dao.PresiniestrosDAO.EJECUTA_GUARDA_TPREDANIO;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_PRE_AUTO;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_PRE_BENEFICIO;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_PRE_BENEFICIO_GASFUN;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_PRE_DANO;
import static mx.com.aon.portal.dao.PresiniestrosDAO.OBTIENE_DOCUMENTOS_PRE;
import static mx.com.aon.portal.dao.PresiniestrosDAO.GUARDA_ATRIBUTOS_DCTO_PRE;
import static mx.com.aon.portal.dao.PresiniestrosDAO.ELIMINA_DCTO_PRE;

public class PresiniestrosManagerImpl extends AbstractManagerJdbcTemplateInvoke implements PresiniestrosManager{
	
	@SuppressWarnings("unchecked")
	public String test() throws ApplicationException{
		
		logger.debug("Hola desde Manager ENTRAR");
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("pv_cdelemento_i", null);
        params.put("pv_cdforpag_i", null);
        params.put("pv_cduniage_i", null);
        params.put("pv_cdramo_i", null);
		
		WrapperResultados res;
		
		res = returnBackBoneInvoke(params, "TEST");
		ArrayList<InstrumentoPagoAtributosVO> instrumVO = (ArrayList<InstrumentoPagoAtributosVO>) res.getItemList();
		logger.debug("REsultado "+instrumVO);
		 
		return "OK";
	}
	
	@SuppressWarnings("unchecked")
	public List<BaseObjectVO> getItemList(Map<String, String> parameters, String endpoint) 
		throws ApplicationException {

		List<BaseObjectVO> lbovo = new ArrayList<BaseObjectVO>();
		try {
			logger.debug("*** Entrando a getItemList");
			WrapperResultados resultado = returnBackBoneInvoke(parameters, endpoint);
			lbovo = (List<BaseObjectVO>) resultado.getItemList();
		
		} catch (Exception e) {
			logger.error("Error método getItemList: " + e,e);
		}
		return lbovo;

	}
	
	@SuppressWarnings("unchecked")
	public PagedList paginaResultado(Map<String, String> parameters, String endpoint, int start, int limit) 
		throws ApplicationException {

		logger.debug("*** Entrando a paginaResultado");
		return pagedBackBoneInvoke(parameters, endpoint, start, limit);
		
	}

	@SuppressWarnings("unchecked")
	public TableModelExport obtieneModelo(Map<String, String> params, String endpoint, String columnas[]) 
		throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		List lista = null;

		try {
			lista = (ArrayList) super.getExporterAllBackBoneInvoke(params, endpoint);
		} catch (Exception e) {
			logger.error("Error método obtieneModelo: " + e,e);
			throw new ApplicationException("Error en método obtieneModelo al obtener resultados para exportación");
		}

		model.setInformation(lista);
		// 	Se agregan los nombre de las columnas del modelo de datos
		model.setColumnName(columnas);
		
		return model;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public WrapperResultados servicePresiniestroAutomovil(PreSiniestroVO psVO, AutomovilVO aVO, String operacion) 
		throws ApplicationException {
		
		logger.debug("*** Entrando a servicePresiniestroAutomovil");
		logger.debug("+++ OPERACION = " + operacion);
		
		WrapperResultados res = new WrapperResultados();
		
		Map<String, String> params = new HashMap<String, String>();
		
		try {

			if ( StringUtils.isNotBlank( operacion ) && operacion.equals(INSERT_MODE) && !aVO.equals( null ) ) {

				Date d = new Date();
				String formato1 = new String("dd/MM/yyyy");
				SimpleDateFormat formatoSimple = new SimpleDateFormat(formato1);
				aVO.setFecha( formatoSimple.format(d) );

			} else if ( StringUtils.isNotBlank( operacion ) && operacion.equals(UPDATE_MODE) && !aVO.equals( null ) && aVO.getFecha().length() > 10 ) {

				aVO.setFecha( aVO.getFecha().substring(8, 10) + "/" + aVO.getFecha().substring(5,7) + "/" + aVO.getFecha().substring(0, 4) );

			}
			
			params.put("p_poliza_i", psVO.getNmpoliza());
			params.put("p_nmfolacw_i", StringUtils.isBlank(operacion) ? null:aVO.getFolio());
			params.put("p_cdpostal_i", StringUtils.isBlank(operacion) ? null:null); ///////// ??????? DAÑOS
			params.put("p_cduniage_i", StringUtils.isBlank(operacion) ? null:psVO.getCdAseguradora());
			params.put("p_dsaltura_i", StringUtils.isBlank(operacion) ? null:null); ///////// ??????? DAÑOS
			params.put("p_dsasegur_i", StringUtils.isBlank(operacion) ? null:aVO.getAsegurado());
			params.put("p_dsbenefe_i", StringUtils.isBlank(operacion) ? null:null); /////// ???? DAÑOS
			params.put("p_dsbitarep_i", StringUtils.isBlank(operacion) ? null:aVO.getReportoAPersonal());
			params.put("p_dscalle_i", StringUtils.isBlank(operacion) ? null:null); /////// ???? DAÑOS
			params.put("p_dscobert_i", StringUtils.isBlank(operacion) ? null:null); /////// ???? DAÑOS
			params.put("p_dscoloni_i", StringUtils.isBlank(operacion) ? null:aVO.getColonia());
			params.put("p_dscolor_i", StringUtils.isBlank(operacion) ? null:aVO.getColor());
			params.put("p_dsconduc_i", StringUtils.isBlank(operacion) ? null:aVO.getConductor());
			params.put("p_dsdatoad_i", StringUtils.isBlank(operacion) ? null:null); /////// ???? DAÑOS 
			params.put("p_dsdesacc_i", StringUtils.isBlank(operacion) ? null:aVO.getDescripcionAccidente());
			params.put("p_dslugar_i", StringUtils.isBlank(operacion) ? null:aVO.getLugarVehiculo());
			params.put("p_dsmarca_i", StringUtils.isBlank(operacion) ? null:aVO.getMarca());
			params.put("p_dsmodelo_i", StringUtils.isBlank(operacion) ? null:aVO.getModelo());
			params.put("p_dsmunici_i", StringUtils.isBlank(operacion) ? null:aVO.getDelegacion());
			params.put("p_dsobsacc_i", StringUtils.isBlank(operacion) ? null:null); /////// ???? DAÑOS
			params.put("p_dsreporta_i", StringUtils.isBlank(operacion) ? null:aVO.getReportadoPor());
			params.put("p_dstaller_i", StringUtils.isBlank(operacion) ? null:aVO.getTaller());
			params.put("p_dstercero_i", StringUtils.isBlank(operacion) ? null:aVO.getTercero());
			params.put("p_feaccid_i", StringUtils.isBlank(operacion) ? null:aVO.getFechaAccidente());
			params.put("p_feingtal_i", StringUtils.isBlank(operacion) ? null:null); /////// ???? DAÑOS
			params.put("p_ferepaon_i", StringUtils.isBlank(operacion) ? null:aVO.getFechaReporte());
			params.put("p_ferepaseg_i", StringUtils.isBlank(operacion) ? null:aVO.getFechaReportada());
			params.put("p_fesinies_i", StringUtils.isBlank(operacion) ? null:aVO.getFecha()); //// ???? cableado
			params.put("p_nmhoraac_i", StringUtils.isBlank(operacion) ? null:aVO.getHoraAccidente());
			params.put("p_nmhoraaon_i", StringUtils.isBlank(operacion) ? null:aVO.getHoraReporte()); //// ???? cableado
			params.put("p_nmhorarep_i", StringUtils.isBlank(operacion) ? null:aVO.getHoraReportada());
			params.put("p_nmmotor_i", StringUtils.isBlank(operacion) ? null:aVO.getNumeroMotor());
			params.put("p_nmplacas_i", StringUtils.isBlank(operacion) ? null:aVO.getNumeroPlacas());
			params.put("p_nmpoliex_i", StringUtils.isBlank(operacion) ? null:aVO.getPoliza());
			params.put("p_nmrepaon_i", StringUtils.isBlank(operacion) ? null:null); /////// ???? DAÑOS
			params.put("p_nmrepase_i", StringUtils.isBlank(operacion) ? null:aVO.getNumeroReporte()); /// REPETIDA CON ULTIMO params
			params.put("p_nmserie_i", StringUtils.isBlank(operacion) ? null:aVO.getNumeroSerie());
			params.put("p_nmsituex_i", StringUtils.isBlank(operacion) ? null:aVO.getCertificado());
			params.put("p_nmtelase_i", StringUtils.isBlank(operacion) ? null:aVO.getTelefono1());
			params.put("p_nmtelef_i", StringUtils.isBlank(operacion) ? null:null); /////// ???? DAÑOS
			params.put("p_nmtelrep_i", StringUtils.isBlank(operacion) ? null:aVO.getTelefono3());
			params.put("p_nmteltal_i", StringUtils.isBlank(operacion) ? null:aVO.getTelefono2()); //// ???? cableado
			params.put("p_swrepase_i", StringUtils.isBlank(operacion) ? null:null); //// ???? cableado
			params.put("p_cdramo_i", psVO.getCdramo());
			params.put("p_dsrecrep_i", StringUtils.isBlank(operacion) ? null:null); /////// ???? DAÑOS
			params.put("p_nmrepaseg", StringUtils.isBlank(operacion) ? null:null); //// REPETIDA
			params.put("p_operacion_i", operacion);
		} catch (Exception e) {
			logger.error("Error seteando parámetros de entrada en método servicePresiniestroAutomovil: ");
			logger.error(e,e);
			throw new ApplicationException("Error seteando parámetros de entrada en método servicePresiniestroAutomovil");
		}

		try {
			logger.debug(">>> Invocando endpoint EJECUTA_GUARDA_TPREAUTO");
			if ( StringUtils.isBlank(operacion) ) {
				res = this.returnBackBoneInvoke(params, EJECUTA_GUARDA_TPREAUTO);
			}
			else {
				res = this.returnBackBoneInvoke(params, EJECUTA_GUARDA_TPREAUTO);
			}
			
		} catch (Exception e) {
			logger.error("Error método servicePresiniestroAutomovil: " + e.getMessage(),e);
			if ( StringUtils.isBlank(operacion) ) {
				throw new ApplicationException("Error al obtener datos para AGREGAR Presiniestro Automóviles");
			} else if ( operacion.equals(INSERT_MODE) ) {
				throw new ApplicationException("Error al GUARDAR datos para Presiniestro Automóviles");
			} else if ( operacion.equals(UPDATE_MODE) ) {
				throw new ApplicationException("Error al ACTUALIZAR datos para Presiniestro Automóviles");
			}
		}
		
		return res;
		
	}

	public WrapperResultados obtieneDatosEditarAutomovil(PreSiniestroVO psVO) 
		throws ApplicationException {

		logger.debug("*** Entrando a obtieneDatosEditarAutomovil");

		WrapperResultados res = new WrapperResultados();
		
		Map<String, String> params = new HashMap<String, String>();
		
		try {
			params.put("p_nmfolacw_i", psVO.getFolio());
		} catch (Exception e) {
			logger.error("Error seteando parámetros de entrada en método obtieneDatosEditarAutomovil: " + e,e);
			throw new ApplicationException("Error seteando parámetros de entrada en método obtieneDatosEditarAutomovil");
		}
		
		try {
			logger.debug(">>> Invocando endpoint OBTIENE_PRE_AUTO");
			res = this.returnBackBoneInvoke(params, OBTIENE_PRE_AUTO);
		} catch (Exception e) {
			logger.error("Error en método obtieneDatosEditarAutomovil: " + e,e);
			throw new ApplicationException("Error al obtener datos para Editar Presiniestro Automóviles");
		}
		
		return res;
	}

	@SuppressWarnings("unchecked")
	public WrapperResultados servicePresiniestroDano(PreSiniestroVO psVO, DanoVO dVO, String operacion) 
		throws ApplicationException {
		
		logger.debug("*** Entrando a servicePresiniestroDano");
		logger.debug("+++ OPERACION = " + operacion);
		
		WrapperResultados res = new WrapperResultados();
		
		Map<String, String> params = new HashMap<String, String>();
		
		try {

			if ( StringUtils.isNotBlank( operacion ) && operacion.equals(INSERT_MODE) && !dVO.equals( null ) ) {

				Date d = new Date();
				String formato1 = new String("dd/MM/yyyy");
				SimpleDateFormat formatoSimple = new SimpleDateFormat(formato1);
				dVO.setFecha( formatoSimple.format(d) );

			} else if ( StringUtils.isNotBlank( operacion ) && operacion.equals(UPDATE_MODE) && !dVO.equals( null ) && dVO.getFecha().length() > 10 ) {

				dVO.setFecha( dVO.getFecha().substring(8, 10) + "/" + dVO.getFecha().substring(5,7) + "/" + dVO.getFecha().substring(0, 4) );

			}
			
			params.put("p_poliza_i", psVO.getNmpoliza());
			params.put("p_fereporte_i", StringUtils.isBlank(operacion) ? null:dVO.getFecha());
			params.put("p_nmpoliex_i", StringUtils.isBlank(operacion) ? null:psVO.getPoliza());
			params.put("p_nmsituext_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_cdramo_i", psVO.getCdramo());
			params.put("p_cduniage_i", StringUtils.isBlank(operacion) ? null:psVO.getCdAseguradora());
			params.put("p_dsrecrep_i", StringUtils.isBlank(operacion) ? null:dVO.getPersonaRecibeReporte());
			params.put("p_dsbienes_i", StringUtils.isBlank(operacion) ? null:dVO.getEmbarqueBienesDanados());
			params.put("p_dsaccidente_i", StringUtils.isBlank(operacion) ? null:dVO.getDescripcionDano());
			params.put("p_fesinies_i", StringUtils.isBlank(operacion) ? null:dVO.getFechaDano());
			params.put("p_dslugbie_i", StringUtils.isBlank(operacion) ? null:dVO.getLugarBienesAfectados());
			params.put("p_dsentrev_i", StringUtils.isBlank(operacion) ? null:dVO.getPersonaEntrevista());
			params.put("p_dsobserva_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_nmtelefono_i", StringUtils.isBlank(operacion) ? null:dVO.getTelefono1());
			params.put("p_dsestiobs_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_dsreporte_i", StringUtils.isBlank(operacion) ? null:dVO.getPersonaReporto());
			params.put("p_nmtelrep_i", StringUtils.isBlank(operacion) ? null:dVO.getTelefono2());
			params.put("p_nmestima_i", StringUtils.isBlank(operacion) ? null:dVO.getEstimacionDano());
			params.put("p_nmrepaon_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_dsdatoad_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_dsbenefe_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_dsbitarep_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_dscobert_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_nmhorarep_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_nmhoraacc_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_feaccid_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_cdpostal_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_dslocal_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_dsaltura_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_dscalle_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_dsasegur_i", StringUtils.isBlank(operacion) ? null:dVO.getNombreAsegurado());
			params.put("p_nmfolacw_i", StringUtils.isBlank(operacion) ? null:psVO.getFolio());
			params.put("p_dstipram_i", StringUtils.isBlank(operacion) ? null:dVO.getRamo());
			params.put("p_dsperrecibe_i", StringUtils.isBlank(operacion) ? null:null);
			params.put("p_operacion_i", operacion);
		} catch (Exception e) {
			logger.error("Error seteando parámetros de entrada en método servicePresiniestroDano: ");
			logger.error(e,e);
			throw new ApplicationException("Error seteando parámetros de entrada en método servicePresiniestroDano");
		}

		try {
			logger.debug(">>> Invocando endpoint EJECUTA_GUARDA_TPREDANIO");
			if ( StringUtils.isBlank(operacion) ) {
				res = this.returnBackBoneInvoke(params, EJECUTA_GUARDA_TPREDANIO);
			}
			else {
				res = this.returnBackBoneInvoke(params, EJECUTA_GUARDA_TPREDANIO);
			}
			
		} catch (Exception e) {
			logger.error("Error método servicePresiniestroDano: " + e.getMessage(),e);
			if ( StringUtils.isBlank(operacion) ) {
				throw new ApplicationException("Error al obtener datos para AGREGAR Presiniestro Daños");
			} else if ( operacion.equals(INSERT_MODE) ) {
				throw new ApplicationException("Error al GUARDAR datos para Presiniestro Daños");
			} else if ( operacion.equals(UPDATE_MODE) ) {
				throw new ApplicationException("Error al ACTUALIZAR datos para Presiniestro Daños");
			}
		}
		
		return res;
		
	}

	public WrapperResultados obtieneDatosEditarDano(PreSiniestroVO psVO) 
		throws ApplicationException {

		logger.debug("*** Entrando a obtieneDatosEditarDano");

		WrapperResultados res = new WrapperResultados();
	
		Map<String, String> params = new HashMap<String, String>();
	
		try {
			params.put("p_nmfolacw_i", psVO.getFolio());
		} catch (Exception e) {
			logger.error("Error seteando parámetros de entrada en método obtieneDatosEditarDano: " + e,e);
			throw new ApplicationException("Error seteando parámetros de entrada en método obtieneDatosEditarDano");
		}
	
		try {
			logger.debug(">>> Invocando endpoint OBTIENE_PRE_DANO");
			res = this.returnBackBoneInvoke(params, OBTIENE_PRE_DANO);
		} catch (Exception e) {
			logger.error("Error en método obtieneDatosEditarDano: " + e,e);
			throw new ApplicationException("Error al obtener datos para Editar Presiniestro Daños");
		}
	
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<BaseObjectVO> obtieneListaTramites() 
		throws ApplicationException {

		List<BaseObjectVO> lbovo = new ArrayList<BaseObjectVO>();
		try {
			logger.debug("*** Entrando a obtieneListaTramites");
			WrapperResultados resultado = returnBackBoneInvoke(new HashMap<String, String>(), OBTIENE_TRAMITES);
			lbovo = (List<BaseObjectVO>) resultado.getItemList();
		
		} catch (Exception e) {
			logger.error("Error método obtieneListaTramites: " + e.getMessage(),e);
		}
		return lbovo;

	}
	
	
	@SuppressWarnings("unchecked")
	public List<BaseObjectVO> obtieneListaPadecimientos() 
		throws ApplicationException {

		List<BaseObjectVO> lbovo = new ArrayList<BaseObjectVO>();
		try {
			logger.debug("*** Entrando a obtieneListaPadecimientos");
			WrapperResultados resultado = returnBackBoneInvoke(new HashMap<String, String>(), OBTIENE_PADECIMIENTOS);
			lbovo = (List<BaseObjectVO>) resultado.getItemList();
		
		} catch (Exception e) {
			logger.error("Error método obtieneListaPadecimientos: " + e.getMessage(),e);
		}
		return lbovo;

	}
	
	@SuppressWarnings("unchecked")
	public BeneficioVO obtieneDatosAgregarBeneficio(String poliza, String ramo) 
	throws ApplicationException {
		
		BeneficioVO beneficio = null;
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("p_poliza_i", poliza);
		params.put("p_cdtiptra_i", null);
		params.put("p_nmfolacw_i", null);
		params.put("p_nmfolaona_i", null);
		params.put("p_nmsinaseg_i", null);
		params.put("p_dspadecimiento_i", null);
		params.put("p_dsasegurado_i", null);
		params.put("p_dsobserv_i", null);
		params.put("p_cdusuario_i", null);
		params.put("p_feprigas_i", null);
		params.put("p_dsfactura_i", null);
		params.put("p_dstramite_i", null);
		params.put("p_nmpoliex_i", null);
		params.put("p_dsrecrep_i", null);
		params.put("p_fereporte_i", null);
		params.put("p_dsacciden_i", null);
		params.put("p_nmhorarep_i", null);
		params.put("p_nmhoraacc_i", null);
		params.put("p_feaccid_i", null);
		params.put("p_dslugar_i", null);
		params.put("p_nmtelrep_i", null);
		params.put("p_dsreporta_i", null);
		params.put("p_dsciaase_i", null);
		params.put("p_dstitular_i", null);
		params.put("p_fepresin_i", null);
		params.put("p_nmsituext_i", null);
		params.put("p_cduniage_i", null);
		params.put("p_cdramo_i", ramo);
		params.put("p_nmsbsitext", null);
		params.put("p_operacion_i", null);
		
		
		try {
			logger.debug("*** Entrando a obtieneDatosAgregarBeneficio");
			beneficio = (BeneficioVO) getBackBoneInvoke(params, EJECUTA_GUARDA_TPREBEN);
			
		} catch (Exception e) {
			logger.error("Error método obtieneDatosAgregarBeneficio: " + e.getMessage(),e);
			throw new ApplicationException("Error al obtener los datos. Consulte a su soporte");
		}
		return beneficio;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public BeneficioVO obtienePresiniestroBeneficio(String folio, String cdRamo, String nmPoliza) 
	throws ApplicationException {
		
		BeneficioVO beneficio = null;
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("p_nmfolacw_i", folio);
		params.put("p_cdramo_i", cdRamo);
		params.put("p_nmpoliza_i", nmPoliza);
		
		try {
			logger.debug("*** Entrando a obtienePresiniestroBeneficio");
			beneficio = (BeneficioVO) getBackBoneInvoke(params, OBTIENE_PRE_BENEFICIO);
			
		} catch (Exception e) {
			logger.error("Error método obtienePresiniestroBeneficio: " + e.getMessage(),e);
			throw new ApplicationException("Error al obtener los datos. Consulte a su soporte");
		}
		return beneficio;
		
	}
	
	public String guardaPresiniestroBeneficios(BeneficioVO beneficio, String operacion) throws ApplicationException{
		String resultado = "";
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("p_poliza_i", beneficio.getPoliza());
		params.put("p_cdtiptra_i", beneficio.getCdTipoTramite());
		params.put("p_nmfolacw_i", beneficio.getFolio());
		params.put("p_nmfolaona_i", beneficio.getFolioAA());
		params.put("p_nmsinaseg_i", beneficio.getNoSiniestroAseg());
		params.put("p_dspadecimiento_i", beneficio.getDsPadecimiento());
		params.put("p_dsasegurado_i", beneficio.getAsegurado());
		params.put("p_dsobserv_i", beneficio.getObservaciones());
		params.put("p_cdusuario_i", null);
		params.put("p_feprigas_i", beneficio.getFechaPrimerGasto());
		params.put("p_dsfactura_i", null);
		params.put("p_dstramite_i", beneficio.getDescripcionTramite());
		params.put("p_nmpoliex_i", beneficio.getPolizaExt());
		params.put("p_dsrecrep_i", null);
		params.put("p_fereporte_i", beneficio.getFecha());
		params.put("p_dsacciden_i", null);
		params.put("p_nmhorarep_i", null);
		params.put("p_nmhoraacc_i", null);
		params.put("p_feaccid_i", null);
		params.put("p_dslugar_i", null);
		params.put("p_nmtelrep_i", beneficio.getTelefonoRep());
		params.put("p_dsreporta_i", beneficio.getReportadoPor());
		params.put("p_dsciaase_i", beneficio.getDsAseguradora());
		params.put("p_dstitular_i", beneficio.getTitular());
		params.put("p_fepresin_i", null);
		params.put("p_nmsituext_i", beneficio.getInciso());
		params.put("p_cduniage_i", beneficio.getCdAseguradora());
		params.put("p_cdramo_i", beneficio.getCdRamo());
		params.put("p_nmsbsitext", beneficio.getSubinciso());
		params.put("p_operacion_i", operacion);
		
		
		try {
			logger.debug("*** Entrando a guardaPresiniestroBeneficio");
			WrapperResultados result = this.returnBackBoneInvoke(params, EJECUTA_GUARDA_TPREBEN);
			
			if(result.getItemMap() != null){
				if(result.getItemMap().containsKey("NUMERO_FOLIO")) resultado = (String) result.getItemMap().get("NUMERO_FOLIO");
			}
			
		} catch (Exception e) {
			logger.error("Error método guardaPresiniestroBeneficio: " + e.getMessage(),e);
			throw new ApplicationException("Error al guardar. Consulte a su soporte");
		}
		return resultado;
		
	}
	@SuppressWarnings("unchecked")
	public BeneficioVO obtieneDatosAgregarBeneficioGastosFunerarios(String poliza, String ramo) 
	throws ApplicationException {
		
		BeneficioVO beneficio = null;
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		 params.put("p_poliza_i", poliza);
		 params.put("p_cdtiptra_i", null);
		 params.put("p_nmfolacw_i", null);
		 params.put("p_dspersona_i", null);
		 params.put("p_dsempresa_i", null);
		 params.put("p_dstitular_i", null);
		 params.put("p_dsafectado_i", null);
		 params.put("p_dsacciden_i", null);
		 params.put("p_dsciaase_i", null);
		 params.put("p_dslugar_i", null);
		 params.put("p_dsrecrep_i", null);
		 params.put("p_dsreporta_i", null);
		 params.put("p_feaccid_i", null);
		 params.put("p_fepresin_i", null);
		 params.put("p_fereporte_i", null);
		 params.put("p_nmhoraacc_i", null);
		 params.put("p_nmhorarep_i", null);
		 params.put("p_nmpoliex_i", null);
		 params.put("p_nmtelrep_i", null);
		 params.put("p_cduniage_i", null);
		 params.put("p_nmsituext_i", null);
		 params.put("p_cdramo_i", ramo);
		 params.put("p_feprigas_i", null);
		 params.put("p_nmsinaseg", null);
		 params.put("p_operacion_i", null);

		try {
			logger.debug("*** Entrando a obtieneDatosAgregarBeneficio");
			beneficio = (BeneficioVO) getBackBoneInvoke(params, EJECUTA_GUARDA_TPREVGFAP);
			
		} catch (Exception e) {
			logger.error("Error método obtieneDatosAgregarBeneficio: " + e.getMessage(),e);
			throw new ApplicationException("Error al obtener los datos. Consulte a su soporte");
		}
		return beneficio;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public BeneficioVO obtienePresiniestroBeneficioGastosFunerarios(String folio, String cdRamo, String nmPoliza) 
	throws ApplicationException {
		
		BeneficioVO beneficio = null;
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("p_nmfolacw_i", folio);
		params.put("p_cdramo_i", cdRamo);
		params.put("p_nmpoliza_i", nmPoliza);
		
		try {
			beneficio = (BeneficioVO) getBackBoneInvoke(params, OBTIENE_PRE_BENEFICIO_GASFUN);
			
		} catch (Exception e) {
			logger.error("Error método obtienePresiniestroBeneficio: " + e.getMessage(),e);
			throw new ApplicationException("Error al obtener los datos. Consulte a su soporte");
		}
		return beneficio;
		
	}
	
	public String guardaPresiniestroBeneficiosGastosFunerarios(BeneficioVO beneficio, String operacion) throws ApplicationException{
		String resultado = "";
		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("p_poliza_i", beneficio.getPoliza());
		 params.put("p_cdtiptra_i", beneficio.getCdTipoTramite());
		 params.put("p_nmfolacw_i", beneficio.getFolio());
		 params.put("p_dspersona_i", beneficio.getAsegurado());
		 params.put("p_dsempresa_i", beneficio.getEmpresa());
		 params.put("p_dstitular_i", beneficio.getTitular());
		 params.put("p_dsafectado_i", beneficio.getAsegurado());
		 params.put("p_dsacciden_i", beneficio.getObservaciones());
		 params.put("p_dsciaase_i", beneficio.getDsAseguradora());
		 params.put("p_dslugar_i", null);
		 params.put("p_dsrecrep_i", null);
		 params.put("p_dsreporta_i", beneficio.getReportadoPor());
		 params.put("p_feaccid_i", null);
		 params.put("p_fepresin_i", null);
		 params.put("p_fereporte_i", beneficio.getFecha());
		 params.put("p_nmhoraacc_i", null);
		 params.put("p_nmhorarep_i", null);
		 params.put("p_nmpoliex_i", beneficio.getPolizaExt());
		 params.put("p_nmtelrep_i", beneficio.getTelefonoRep());
		 params.put("p_cduniage_i", beneficio.getCdAseguradora());
		 params.put("p_nmsituext_i", beneficio.getInciso());
		 params.put("p_cdramo_i", beneficio.getCdRamo());
		 params.put("p_feprigas_i", null);
		 params.put("p_nmsinaseg", beneficio.getNoSiniestroAseg());
		 params.put("p_operacion_i", operacion);

		
		try {
			WrapperResultados result = this.returnBackBoneInvoke(params, EJECUTA_GUARDA_TPREVGFAP);
			
			if(result.getItemMap() != null){
				if(result.getItemMap().containsKey("NUMERO_FOLIO")) resultado = (String) result.getItemMap().get("NUMERO_FOLIO");
			}
			
		} catch (Exception e) {
			logger.error("Error método guardaPresiniestroBeneficiosGastosFunerarios: " + e.getMessage(),e);
			throw new ApplicationException("Error al guardar. Consulte a su soporte");
		}
		return resultado;
		
	}
	
	
	/*para los documentos en presiniestros de BENEFICIOS*/
	public PagedList obtieneDocumentosPre(Map<String, String> parameters, int start, int limit) throws ApplicationException{

		return pagedBackBoneInvoke(parameters, OBTIENE_DOCUMENTOS_PRE, start, limit);
		
	}

	@SuppressWarnings("unchecked")
	public String guardaAtributosDocumentoPre(Map<String, String> params)throws ApplicationException {
		
		String mensaje = null;
        WrapperResultados resultado = returnBackBoneInvoke(params, GUARDA_ATRIBUTOS_DCTO_PRE);
        mensaje = resultado.getMsgText();
		
		return mensaje;
	}
	
	
	public DocumentoVO obtenerAtributosDocumentoPre(Map<String, String> params, DocumentoVO documento)throws ApplicationException{
		
		return documento;
	}

	
	public boolean insertaDocumento(Map<String, String> params,
			DocumentoVO documento) throws ApplicationException {
			
		
		params.put("pv_cdunica", documento.getCdUnica());
		
		params.put("pv_cdgrupa", null);// se madna null
		params.put("pv_status", "V");
		params.put("pv_cdtipdoc", documento.getCdTipDoc());

		params.put("pv_otvalor01", documento.getAtr1());
		params.put("pv_otvalor02", documento.getAtr2());
		params.put("pv_otvalor03", documento.getAtr3());
		params.put("pv_otvalor04", documento.getAtr4());
		params.put("pv_otvalor05", documento.getAtr5());
		params.put("pv_otvalor06", documento.getAtr6());
		params.put("pv_otvalor07", documento.getAtr7());
		params.put("pv_otvalor08", documento.getAtr8());
		params.put("pv_otvalor09", documento.getAtr9());
		params.put("pv_otvalor10", documento.getAtr10());
		params.put("pv_otvalor11", documento.getAtr11());
		params.put("pv_otvalor12", documento.getAtr12());
		params.put("pv_otvalor13", documento.getAtr13());
		params.put("pv_otvalor14", documento.getAtr14());
		params.put("pv_otvalor15", documento.getAtr15());
		params.put("pv_otvalor16", documento.getAtr16());
		params.put("pv_otvalor17", documento.getAtr17());
		params.put("pv_otvalor18", documento.getAtr18());
		params.put("pv_otvalor19", documento.getAtr19());
		params.put("pv_otvalor20", documento.getAtr20());
		params.put("pv_otvalor21", documento.getAtr21());
		params.put("pv_otvalor22", documento.getAtr22());
		params.put("pv_otvalor23", documento.getAtr23());
		params.put("pv_otvalor24", documento.getAtr24());
		params.put("pv_otvalor25", documento.getAtr25());
		params.put("pv_otvalor26", documento.getAtr26());
		params.put("pv_otvalor27", documento.getAtr27());
		params.put("pv_otvalor28", documento.getAtr28());
		params.put("pv_otvalor29", documento.getAtr29());
		params.put("pv_otvalor30", documento.getAtr30());
		params.put("pv_otvalor31", documento.getAtr31());
		params.put("pv_otvalor32", documento.getAtr32());
		params.put("pv_otvalor33", documento.getAtr33());
		params.put("pv_otvalor34", documento.getAtr34());
		params.put("pv_otvalor35", documento.getAtr35());
		params.put("pv_otvalor36", documento.getAtr36());
		params.put("pv_otvalor37", documento.getAtr37());
		params.put("pv_otvalor38", documento.getAtr38());
		params.put("pv_otvalor39", documento.getAtr39());
		params.put("pv_otvalor40", documento.getAtr40());
		params.put("pv_otvalor41", documento.getAtr41());
		params.put("pv_otvalor42", documento.getAtr42());
		params.put("pv_otvalor43", documento.getAtr43());
		params.put("pv_otvalor44", documento.getAtr44());
		params.put("pv_otvalor45", documento.getAtr45());
		params.put("pv_otvalor46", documento.getAtr46());
		params.put("pv_otvalor47", documento.getAtr47());
		params.put("pv_otvalor48", documento.getAtr48());
		params.put("pv_otvalor49", documento.getAtr49());
		params.put("pv_otvalor50", documento.getAtr50());
		
		this.returnBackBoneInvoke(params, GUARDA_ATRIBUTOS_DCTO_PRE);
		
		return true;
	}
	
	public boolean actualizaDocumento(Map<String, String> params,
			DocumentoVO documento) throws ApplicationException {
			
		
		params.put("pv_cdunica", documento.getCdUnica());
		
		params.put("pv_cdgrupa", null);// se madna null
		params.put("pv_status", "V");
		params.put("pv_cdtipdoc", documento.getCdTipDoc());

		params.put("pv_otvalor01", documento.getAtr1());
		params.put("pv_otvalor02", documento.getAtr2());
		params.put("pv_otvalor03", documento.getAtr3());
		params.put("pv_otvalor04", documento.getAtr4());
		params.put("pv_otvalor05", documento.getAtr5());
		params.put("pv_otvalor06", documento.getAtr6());
		params.put("pv_otvalor07", documento.getAtr7());
		params.put("pv_otvalor08", documento.getAtr8());
		params.put("pv_otvalor09", documento.getAtr9());
		params.put("pv_otvalor10", documento.getAtr10());
		params.put("pv_otvalor11", documento.getAtr11());
		params.put("pv_otvalor12", documento.getAtr12());
		params.put("pv_otvalor13", documento.getAtr13());
		params.put("pv_otvalor14", documento.getAtr14());
		params.put("pv_otvalor15", documento.getAtr15());
		params.put("pv_otvalor16", documento.getAtr16());
		params.put("pv_otvalor17", documento.getAtr17());
		params.put("pv_otvalor18", documento.getAtr18());
		params.put("pv_otvalor19", documento.getAtr19());
		params.put("pv_otvalor20", documento.getAtr20());
		params.put("pv_otvalor21", documento.getAtr21());
		params.put("pv_otvalor22", documento.getAtr22());
		params.put("pv_otvalor23", documento.getAtr23());
		params.put("pv_otvalor24", documento.getAtr24());
		params.put("pv_otvalor25", documento.getAtr25());
		params.put("pv_otvalor26", documento.getAtr26());
		params.put("pv_otvalor27", documento.getAtr27());
		params.put("pv_otvalor28", documento.getAtr28());
		params.put("pv_otvalor29", documento.getAtr29());
		params.put("pv_otvalor30", documento.getAtr30());
		params.put("pv_otvalor31", documento.getAtr31());
		params.put("pv_otvalor32", documento.getAtr32());
		params.put("pv_otvalor33", documento.getAtr33());
		params.put("pv_otvalor34", documento.getAtr34());
		params.put("pv_otvalor35", documento.getAtr35());
		params.put("pv_otvalor36", documento.getAtr36());
		params.put("pv_otvalor37", documento.getAtr37());
		params.put("pv_otvalor38", documento.getAtr38());
		params.put("pv_otvalor39", documento.getAtr39());
		params.put("pv_otvalor40", documento.getAtr40());
		params.put("pv_otvalor41", documento.getAtr41());
		params.put("pv_otvalor42", documento.getAtr42());
		params.put("pv_otvalor43", documento.getAtr43());
		params.put("pv_otvalor44", documento.getAtr44());
		params.put("pv_otvalor45", documento.getAtr45());
		params.put("pv_otvalor46", documento.getAtr46());
		params.put("pv_otvalor47", documento.getAtr47());
		params.put("pv_otvalor48", documento.getAtr48());
		params.put("pv_otvalor49", documento.getAtr49());
		params.put("pv_otvalor50", documento.getAtr50());
		
		this.returnBackBoneInvoke(params, GUARDA_ATRIBUTOS_DCTO_PRE);
		
		return true;
	}

	public boolean eliminaDocumento(Map<String, String> params,
			DocumentoVO documento) throws ApplicationException {

		this.returnBackBoneInvoke(params, ELIMINA_DCTO_PRE);
		
		return true;
	}

}
