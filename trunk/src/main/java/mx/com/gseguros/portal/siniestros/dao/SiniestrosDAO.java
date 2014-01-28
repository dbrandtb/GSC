package mx.com.gseguros.portal.siniestros.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.siniestros.model.AutorizaServiciosVO;
import mx.com.gseguros.portal.siniestros.model.AutorizacionServicioVO;
import mx.com.gseguros.portal.siniestros.model.CoberturaPolizaVO;
import mx.com.gseguros.portal.siniestros.model.DatosSiniestroVO;

public interface SiniestrosDAO {

	public List<AutorizacionServicioVO> obtieneDatosAutorizacionEsp(String nmautser) throws DaoException;

	public List<GenericVO> obtieneListadoAsegurado(String cdperson) throws DaoException;

	public List<AutorizaServiciosVO> obtieneListadoAutorizaciones(String tipoAut,String cdperson) throws DaoException;
	
	public List<GenericVO> obtieneListadoProvMedico(String tipoprov,String cdpresta) throws DaoException;
	
	public List<GenericVO> obtieneListadoCausaSiniestro(String cdcausa) throws DaoException;
	
	public List<CoberturaPolizaVO> obtieneListadoCoberturaPoliza(HashMap<String, Object> paramCobertura) throws DaoException;

	public List<DatosSiniestroVO> obtieneListadoDatSubGeneral(HashMap<String, Object> paramDatSubGral) throws DaoException;
	
	public List<GenericVO> obtieneListadoSubcobertura(String cdgarant, String cdsubcob) throws DaoException;
	
	public List<GenericVO> obtieneListadoCPTICD(String cdtabla, String otclave) throws DaoException;
	
	/* ############################################################################## 
	 * ##################################### VERIFICAR ##############################*/
	public List<AutorizacionServicioVO> guardarAutorizacionServicio(Map<String, Object> paramsR) throws DaoException;

	

	

	

	
	

	

	
	
}
