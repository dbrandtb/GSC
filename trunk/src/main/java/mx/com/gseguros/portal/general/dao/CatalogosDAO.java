package mx.com.gseguros.portal.general.dao;

import java.util.List;
import java.util.Map;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.general.util.Rango;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.general.util.Validacion;

public interface CatalogosDAO {
	
	public List<GenericVO> obtieneTmanteni(String cdTabla) throws DaoException;
	
	public List<GenericVO> obtieneAgentes(String claveONombre) throws DaoException;
	
	public List<GenericVO> obtieneAtributosSituacion(String cdAtribu, String cdTipSit ,String otValor) throws DaoException;
	
	public List<GenericVO> obtieneAtributosSiniestro(String cdAtribu, String cdTipSit ,String otValor) throws DaoException;
	
	public List<GenericVO> obtieneAtributosPoliza(String cdAtribu, String cdRamo, String otValor) throws DaoException;

	public List<GenericVO> obtieneAtributosGarantia(String cdAtribu, String cdTipSit, String cdRamo, String valAnt, String cdGarant) throws DaoException;
	
	public List<GenericVO> obtieneAtributosRol(String cdAtribu, String cdTipSit ,String cdRamo, String valAnt, String cdRol) throws DaoException;
	
	public List<GenericVO> obtieneColonias(String codigoPostal) throws DaoException;
	
	public List<GenericVO> obtieneRolesSistema(String dsRol) throws DaoException;
	
	/**
	 * Obtiene las sucursales
	 * @param cdunieco Sucursal administradora por la que se va a filtrar
	 * @return Listado de sucursales
	 * @throws DaoException
	 */
	public List<GenericVO> obtieneSucursales(String cdunieco) throws DaoException;
	
	public List<GenericVO> obtieneStatusTramite(Map<String,String> params) throws Exception;
	
	public String obtieneCantidadMaxima(String cdramo, String cdtipsit, TipoTramite tipoTramite, Rango rango, Validacion validacion) throws Exception;
	
	public List<GenericVO> cargarAgentesPorPromotor(Map<String,String> params) throws Exception;
	
	public List<GenericVO> cargarServicioPublicoAutos(Map<String,String> params) throws Exception;
}
