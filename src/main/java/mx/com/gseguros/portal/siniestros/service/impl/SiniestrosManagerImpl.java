package mx.com.gseguros.portal.siniestros.service.impl;

import java.util.HashMap;
import java.util.List;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.exception.DaoException;
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
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;

public class SiniestrosManagerImpl implements SiniestrosManager {
	private SiniestrosDAO siniestrosDAO;
	
	private static org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(SiniestrosManagerImpl.class);
	
	
	@Override
	public List<AutorizacionServicioVO> getConsultaAutorizacionesEsp(String nmautser) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneDatosAutorizacionEsp(nmautser);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<GenericVO> getConsultaListaAsegurado(String cdperson) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoAsegurado(cdperson);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<AutorizaServiciosVO> getConsultaListaAutorizaciones(
			String tipoAut, String cdperson) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoAutorizaciones(tipoAut,cdperson);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<ConsultaProveedorVO> getConsultaListaProveedorMedico(String tipoprov,String cdpresta)
			throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoProvMedico(tipoprov,cdpresta);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<CoberturaPolizaVO> getConsultaListaCoberturaPoliza(
			HashMap<String, Object> paramCobertura) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoCoberturaPoliza(paramCobertura);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<DatosSiniestroVO> getConsultaListaDatSubGeneral(
			HashMap<String, Object> paramDatSubGral)
			throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoDatSubGeneral(paramDatSubGral);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<GenericVO> getConsultaListaSubcobertura(String cdgarant,
			String cdsubcob) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoSubcobertura(cdgarant,cdsubcob);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<GenericVO> getConsultaListaCPTICD(String cdtabla, String otclave)
			throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoCPTICD(cdtabla,otclave);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public List<HashMap<String, String>> loadListaDocumentos(HashMap<String, String> params)
			throws ApplicationException {
		try {
			return siniestrosDAO.loadListaDocumentos(params);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public List<HashMap<String, String>> loadListaIncisosRechazos(HashMap<String, String> params)
			throws ApplicationException {
		try {
			return siniestrosDAO.loadListaIncisosRechazos(params);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public boolean guardaEstatusDocumentos(HashMap<String, String> params, List<HashMap<String, String>> saveList)
			throws ApplicationException {
		
		boolean allUpdated = true;
		
		for(HashMap<String, String> doc : saveList){
			try {
				params.put("pv_accion_i", doc.get("listo"));
				params.put("pv_cddocume_i", doc.get("id"));
				siniestrosDAO.guardaEstatusDocumento(params);
			} catch (DaoException daoExc) {
				allUpdated = false;
			}
		}
		
		return allUpdated;
	}
	
	@Override
	public List<HashMap<String, String>> loadListaRechazos()
			throws ApplicationException {
		try {
			return siniestrosDAO.loadListaRechazos();
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public boolean rechazarTramite(HashMap<String, String> params)
			throws ApplicationException {
		try {
			siniestrosDAO.rechazarTramite(params);
		} catch (DaoException daoExc) {
			log.error("Error al rechazar tramite: " + daoExc.getMessage(), daoExc);	
			return false;
		}
		return true;
	}

	@Override
	public boolean turnarAreaReclamaciones(HashMap<String, String> params)
			throws ApplicationException {
		try {
			siniestrosDAO.turnarAreaReclamaciones(params);
		} catch (DaoException daoExc) {
			log.error("Error al turnarAreaReclamaciones: " + daoExc.getMessage(), daoExc);	
			return false;
		}
		return true;
	}

	@Override
	public boolean turnarAreaMedica(HashMap<String, String> params)
			throws ApplicationException {
		try {
			siniestrosDAO.turnarAreaMedica(params);
		} catch (DaoException daoExc) {
			log.error("Error al turnarAreaMedica: " + daoExc.getMessage(), daoExc);	
			return false;
		}
		return true;
	}

	@Override
	public boolean turnarOperadorAR(HashMap<String, String> params)
			throws ApplicationException {
		try {
			siniestrosDAO.turnarOperadorAR(params);
		} catch (DaoException daoExc) {
			log.error("Error al turnarOperadorAR: " + daoExc.getMessage(), daoExc);	
			return false;
		}
		return true;
	}
	
	@Override
	public boolean solicitarPago(HashMap<String, String> params)
			throws ApplicationException {
		try {
			//siniestrosDAO.turnarOperadorAR(params);
		} catch (Exception daoExc) {
			log.error("Error al solicitarPago: " + daoExc.getMessage(), daoExc);	
			return false;
		}
		return true;
	}

	
	public void setSiniestrosDAO(SiniestrosDAO siniestrosDAO) {
		this.siniestrosDAO = siniestrosDAO;
	}
	
	@Override
	public List<ConsultaTDETAUTSVO> getConsultaListaTDeTauts(String nmautser)
			throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoTDeTauts(nmautser);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public void getEliminacionRegistros(String nmautser)
			throws ApplicationException {
			try {
				siniestrosDAO.eliminacionRegistrosTabla(nmautser);
			} catch (DaoException daoExc) {
				throw new ApplicationException(daoExc.getMessage(), daoExc);
			}
	}
	
	@Override
	public List<AutorizacionServicioVO> guardarAutorizacionServicio(HashMap<String, Object> paramsR)throws ApplicationException {
		try {
			return siniestrosDAO.guardarAutorizacionServicio(paramsR);			
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public String guardaListaTDeTauts(HashMap<String, Object> paramsTDeTauts)
			throws ApplicationException {
		try {
			return siniestrosDAO.guardarListaTDeTauts(paramsTDeTauts);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public List<ConsultaTTAPVAATVO> getConsultaListaTTAPVAAT(
			HashMap<String, Object> paramTTAPVAAT) throws ApplicationException {
		 try {
		        return siniestrosDAO.obtieneListadoTTAPVAAT(paramTTAPVAAT);
		    } catch (DaoException daoExc) {
		        throw new ApplicationException(daoExc.getMessage(), daoExc);
		    }
	}

	@Override
	public List<ConsultaManteniVO> getConsultaListaManteni(String cdtabla, String codigo) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoManteni(cdtabla,codigo);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public List<ConsultaPorcentajeVO> getConsultaListaPorcentaje(String cdcpt, String cdtipmed,String mtobase) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoPorcentaje(cdcpt,cdtipmed,mtobase);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public List<PolizaVigenteVO> getConsultaListaPoliza(String cdperson) throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoPoliza(cdperson);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	@Override
	public List<GenericVO> getConsultaListaPlaza() throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoPlaza();
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	
	@Override
	public String guardaListaFacMesaControl(HashMap<String, Object> paramsFacMesaCtrl) throws ApplicationException {
		// TODO Auto-generated method stub
		try {
			return siniestrosDAO.guardaFacMesaControl(paramsFacMesaCtrl);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
}