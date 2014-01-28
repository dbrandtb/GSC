package mx.com.gseguros.portal.siniestros.service;

import java.util.HashMap;
import java.util.List;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.siniestros.model.AutorizaServiciosVO;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.DatosSiniestroVO;

public interface SiniestrosManager {
	
	/**
	 * Obtiene la información de la Autorización de servicio en especifico
	 * @param nmautser
	 * @return List AutorizacionServicioVO
	 * @throws ApplicationException
	 */
	public List<AutorizacionServicioVO> getConsultaAutorizacionesEsp(String nmautser) throws ApplicationException;

	/**
	 * Obtiene la lista de los asegurados
	 * @return List GenericVO
	 * @throws ApplicationException
	 */
	public List<GenericVO> getConsultaListaAsegurado(String cdperson) throws ApplicationException;
	
	/**
	 * Obtiene la información de la lista de autorizaciones
	 * @param cdperson
	 * @return List AutorizaServiciosVO  
	 * @throws ApplicationException
	 */
	public List<AutorizaServiciosVO> getConsultaListaAutorizaciones(String tipoAut,String cdperson) throws ApplicationException;
	
	/**
	 * Obtiene la lista de los Proveedores y Medicos
	 * @param tipoprov
	 * @param cdpresta
	 * @return List GenericVO
	 * @throws ApplicationException
	 */
	public List<GenericVO> getConsultaListaProveedorMedico(String tipoprov,String cdpresta) throws ApplicationException;
	
	
	public List<GenericVO> getConsultaListaCausaSiniestro(String cdcausa) throws ApplicationException;
	
	
	public List<CoberturaPolizaVO> getConsultaListaCoberturaPoliza(HashMap<String, Object> paramCobertura) throws ApplicationException;
	
	public List<DatosSiniestroVO> getConsultaListaDatSubGeneral(HashMap<String, Object> paramDatSubGral) throws ApplicationException;
	
	public List<GenericVO> getConsultaListaSubcobertura(String cdgarant, String cdsubcob) throws ApplicationException;
	
	public List<GenericVO> getConsultaListaCPTICD(String cdtabla, String otclave) throws ApplicationException;
	
	/* ############################################################################## 
	 * ##################################### VERIFICAR ##############################*/
	
	public List<AutorizacionServicioVO> guardarAutorizacionServicio(HashMap<String, Object> paramsR) throws ApplicationException;

	

	

	

	


	



	

	

	
	
}