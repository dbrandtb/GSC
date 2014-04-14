package mx.com.gseguros.portal.general.service;

import java.util.List;
import java.util.Map;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.general.util.Catalogos;
import mx.com.gseguros.portal.general.util.Rango;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.general.util.Validacion;

public interface CatalogosManager {
	
	public List<GenericVO> getTmanteni(Catalogos catalogo) throws Exception;
	
	public List<GenericVO> obtieneColonias(String codigoPostal) throws Exception;
	
	public List<GenericVO> obtieneAgentes(String claveONombre) throws Exception;

	public List<GenericVO> obtieneAtributosSituacion(String cdAtribu, String cdTipSit, String idPadre) throws Exception;
	
	public List<GenericVO> obtieneAtributosPoliza(String cdAtribu, String cdRamo, String idPadre) throws Exception;

	public List<GenericVO> obtieneAtributosGarantia(String cdAtribu, String cdTipSit, String cdRamo, String idPadre, String cdGarant) throws Exception;
	
	public List<GenericVO> obtieneAtributosRol(String cdAtribu, String cdTipSit ,String cdRamo, String valAnt, String cdRol) throws Exception;
	
	public List<GenericVO> obtieneRolesSistema() throws Exception;
	
	public List<GenericVO> obtieneStatusTramite(Map<String,String> params) throws Exception;
	
	/**
     * Obtiene la cantidad m&aacute;xima de acuerdo a un tipo de rango (unidad de medida) solicitado
     * @param cdramo      cdramo
     * @param cdtipsit    cdtipsit
     * @param tipoTramite tipo de tramite
     * @param rango       tipo de rango solicitado
     * @return Cantidad m&aacute;xima solicitada
     * @throws Exception
     */
    public String obtieneCantidadMaxima(String cdramo, String cdtipsit, TipoTramite tipoTramite, Rango rango, Validacion validacion) throws Exception;
}