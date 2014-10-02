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

	public List<GenericVO> obtieneMunicipios(String cdEstado) throws Exception;

	public List<GenericVO> obtieneZonasPorModalidad(String cdtipsit) throws Exception;
	
	public List<GenericVO> obtieneAgentes(String claveONombre) throws Exception;

	public List<GenericVO> obtieneAtributosSituacion(String cdAtribu, String cdTipSit, String idPadre) throws Exception;
	
	public List<GenericVO> obtieneAtributosSiniestro(String cdAtribu, String cdTipSit, String idPadre) throws Exception;
	
	public List<GenericVO> obtieneAtributosPoliza(String cdAtribu, String cdRamo, String idPadre) throws Exception;

	public List<GenericVO> obtieneAtributosGarantia(String cdAtribu, String cdTipSit, String cdRamo, String idPadre, String cdGarant) throws Exception;
	
	public List<GenericVO> obtieneAtributosRol(String cdAtribu, String cdTipSit ,String cdRamo, String valAnt, String cdRol) throws Exception;
	
	public List<GenericVO> obtieneRolesSistema(String dsRol) throws Exception;
	
	/**
	 * Obtiene las sucursales
	 * @param cdunieco Sucursal administradora por la que se filtra
	 * @return
	 * @throws Exception
	 */
	public List<GenericVO> obtieneSucursales(String cdunieco) throws Exception;
	
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
    
    public List<GenericVO> cargarAgentesPorPromotor(String cdusuari)throws Exception;
    
    public List<GenericVO> cargarServicioPublicoAutos(String substring,String cdramo,String cdtipsit)throws Exception;
    
    public String agregaCodigoPostal(Map<String, String> params)throws Exception;

    public String asociaZonaCodigoPostal(Map<String, String> params)throws Exception;
    
    public List<Map<String, String>> obtieneTablasApoyo(Map<String,String> params) throws Exception;

    public String guardaTablaApoyo(Map<String,String> params) throws Exception;

    public boolean guardaClavesTablaApoyo(Map<String, String> params, List<Map<String, String>> saveList) throws Exception;

    public boolean guardaAtributosTablaApoyo(Map<String, String> params, List<Map<String, String>> saveList) throws Exception;
    
    public List<Map<String, String>> obtieneClavesTablaApoyo(Map<String,String> params) throws Exception;

    public List<Map<String, String>> obtieneAtributosTablaApoyo(Map<String,String> params) throws Exception;
    
    public List<GenericVO> cargarDescuentosPorAgente(
    		String tipoUnidad
    		,String uso
    		,String zona
    		,String promotoria
    		,String cdagente
    		,String cdtipsit
    		,String cdatribu)throws Exception;
}