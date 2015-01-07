package mx.com.gseguros.portal.general.dao;

import java.util.List;
import java.util.Map;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.general.util.Rango;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.general.util.Validacion;

public interface CatalogosDAO {
	
	public List<GenericVO> obtieneTmanteni(String cdTabla) throws Exception;
	
	public List<GenericVO> obtieneAgentes(String claveONombre) throws Exception;
	
	public List<GenericVO> obtieneAtributosSituacion(String cdAtribu, String cdTipSit ,String otValor) throws Exception;
	
	public List<GenericVO> obtieneAtributosSiniestro(String cdAtribu, String cdTipSit ,String otValor) throws Exception;
	
	public List<GenericVO> obtieneAtributosPoliza(String cdAtribu, String cdRamo, String otValor) throws Exception;

	public List<GenericVO> obtieneAtributosGarantia(String cdAtribu, String cdTipSit, String cdRamo, String valAnt, String cdGarant) throws Exception;
	
	public List<GenericVO> obtieneAtributosRol(String cdAtribu, String cdTipSit ,String cdRamo, String valAnt, String cdRol) throws Exception;
	
	public List<GenericVO> obtieneColonias(String codigoPostal) throws Exception;

	public List<GenericVO> obtieneMunicipios(String cdEstado) throws Exception;

	public List<GenericVO> obtieneZonasPorModalidad(String cdtipsit) throws Exception;
	
	public List<GenericVO> obtieneRolesSistema(String dsRol) throws Exception;
	
	/**
	 * Obtiene las sucursales
	 * @param cdunieco Sucursal administradora por la que se va a filtrar
	 * @return Listado de sucursales
	 * @throws Exception
	 */
	public List<GenericVO> obtieneSucursales(String cdunieco,String cdusuari) throws Exception;
	
	public List<GenericVO> obtieneStatusTramite(Map<String,String> params) throws Exception;
	
	public String obtieneCantidadMaxima(String cdramo, String cdtipsit, TipoTramite tipoTramite, Rango rango, Validacion validacion) throws Exception;
	
	public List<GenericVO> cargarAgentesPorPromotor(Map<String,String> params) throws Exception;
	
	public List<GenericVO> cargarServicioPublicoAutos(Map<String,String> params) throws Exception;
	
	public String agregaCodigoPostal(Map<String,String>params)throws Exception;

	public String asociaZonaCodigoPostal(Map<String,String>params)throws Exception;
	
	public List<GenericVO> cargarDescuentosPorAgente(
    		String tipoUnidad
    		,String uso
    		,String zona
    		,String promotoria
    		,String cdagente
    		,String cdtipsit
    		,String cdatribu)throws Exception;
	
	public List<GenericVO>cargarListaNegocioServicioPublico(
			String cdtipsit
			,String cdatribu
			,String tipoUnidad
			,String cdagente)throws Exception;
	
	public List<GenericVO>cargarModelosPorSubmarcaRamo5(String submarca,String cdtipsit)throws Exception;
	
	public List<GenericVO>cargarVersionesPorModeloSubmarcaRamo5(String submarca,String modelo,String cdtipsit)throws Exception;
	
	public List<GenericVO>cargarAutosPorCadenaRamo5(String cadena,String cdtipsit)throws Exception;
	
	public List<GenericVO>cargarTtapvat1(String cdtabla)throws ApplicationException,Exception;
	public List<GenericVO>cargarNegocioPorCdtipsitRamo5(String cdtipsit)throws Exception;
	public List<GenericVO>cargarUsosPorNegocioRamo5(String cdnegocio,String cdtipsit)throws Exception;
	public List<GenericVO>cargarMarcasPorNegocioRamo5(String cdnegocio,String cdtipsit)throws Exception;
	public List<GenericVO>cargarNegociosPorAgenteRamo5(String cdagente)throws Exception;
	public List<GenericVO>cargarCargasPorNegocioRamo5(String cdsisrol,String negocio)throws Exception;
}