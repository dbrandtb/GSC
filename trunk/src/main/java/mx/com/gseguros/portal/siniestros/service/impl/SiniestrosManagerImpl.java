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
		// TODO Auto-generated method stub
		try {
			return siniestrosDAO.obtieneListadoAutorizaciones(tipoAut,cdperson);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<GenericVO> getConsultaListaProveedorMedico(String tipoprov,String cdpresta)
			throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoProvMedico(tipoprov,cdpresta);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	public List<GenericVO> getConsultaListaCausaSiniestro(String cdcausa)
			throws ApplicationException {
		try {
			return siniestrosDAO.obtieneListadoCausaSiniestro(cdcausa);
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
	
	public void setSiniestrosDAO(SiniestrosDAO siniestrosDAO) {
		this.siniestrosDAO = siniestrosDAO;
	}
	
	/* ############################################################################## 
	 * ##################################### VERIFICAR ##############################*/
	@Override
	public List<AutorizacionServicioVO> guardarAutorizacionServicio(HashMap<String, Object> paramsR)throws ApplicationException {
		try {
			return siniestrosDAO.guardarAutorizacionServicio(paramsR);			
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}





	

	



	




}