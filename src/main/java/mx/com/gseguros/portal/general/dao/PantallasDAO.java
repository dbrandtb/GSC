package mx.com.gseguros.portal.general.dao;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.general.model.ComponenteVO;

public interface PantallasDAO
{
	//public List<Tatri>              obtenerCamposPantalla      (Map<String,Object> params) throws Exception;
	/**
	 * PKG_CONF_PANTALLAS.P_GET_TCONFCMP
	 */
	public List<ComponenteVO>       obtenerComponentes         (Map<String,String> params) throws Exception;
	//public List<Map<String,String>> obtenerParametrosPantalla  (Map<String,Object> params) throws Exception;
	/**
	 * PKG_CONF_PANTALLAS.P_GET_TCONFCMP
	 */
	public List<Map<String,String>> obtenerParametros          (Map<String,String> params) throws Exception;
	//public void                     borrarParametrosPantalla   (Map<String,Object> params) throws Exception;
	/**
	 * PKG_CONF_PANTALLAS.P_BORRAR_TCONFCMP
	 */
	public void                     borrarParametros           (Map<String,String> params) throws Exception;
	//public void                     insertarParametrosPantalla (Map<String,String> params) throws Exception;
	/**
	 * PKG_CONF_PANTALLAS.P_INSERTA_TCONFCMP
	 */
	public void                     insertarParametros         (Map<String,String> params) throws Exception;
	/**
	 * PKG_CONF_PANTALLAS.P_OBT_ARBOL_TCONFCMP
	 */
	public List<Map<String,String>> obtenerArbol               ()                          throws Exception;
}