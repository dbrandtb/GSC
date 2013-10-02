package mx.com.aon.flujos.cotizacion.service;

import java.util.List;
import java.util.Map;

import mx.com.aon.configurador.pantallas.model.PantallaVO;
import mx.com.aon.configurador.pantallas.model.components.ComboClearOnSelectVO;
import mx.com.aon.configurador.pantallas.model.components.GridVO;
import mx.com.aon.flujos.cotizacion.model.ObjetoCotizacionVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.CotizacionMasivaVO;
import mx.com.gseguros.exception.ApplicationException;

public interface CotizacionPrincipalManager {
	
	public List<CotizacionMasivaVO> obtineTvalositCotiza(String cdunieco,String poliza,String cdramo,String estado ) throws ApplicationException;
	public PantallaVO getPantallaFinal(Map<String, String> parameters) throws ApplicationException;
	public List<ComboClearOnSelectVO> getComboPadre(Map<String, String> parameters) throws ApplicationException;
	public BaseObjectVO getEtiqueta(String cdramo, String cdtipsit)throws ApplicationException;
	public List<ObjetoCotizacionVO> getListaEquipo(ObjetoCotizacionVO objetoCotizacionVO) throws ApplicationException;
	public List<BaseObjectVO> getTipos(String cdramo, String cdtipsit)throws ApplicationException;
	public GridVO getResultados(Map<String, String> parameters) throws ApplicationException;
}


