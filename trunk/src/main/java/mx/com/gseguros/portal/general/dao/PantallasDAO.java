package mx.com.gseguros.portal.general.dao;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.general.model.ComponenteVO;

public interface PantallasDAO
{
	/**
	 * PKG_CONF_PANTALLAS.P_GET_TCONFCMP
	 */
	public List<ComponenteVO>       obtenerComponentes         (Map<String,String> params) throws Exception;
	/**
	 * PKG_CONF_PANTALLAS.P_GET_TCONFCMP
	 */
	public List<Map<String,String>> obtenerParametros          (Map<String,String> params) throws Exception;
	/**
	 * PKG_CONF_PANTALLAS.P_MOV_TCONFCMP
	 */
	public void                     movParametros           (Map<String,String> params) throws Exception;
	/**
	 * PKG_CONF_PANTALLAS.P_INSERTA_TCONFCMP
	 */
	public void                     insertarParametros         (Map<String,String> params) throws Exception;
	/**
	 * PKG_CONF_PANTALLAS.P_OBT_ARBOL_TCONFCMP
	 */
	public List<Map<String,String>> obtenerArbol               ()                          throws Exception;
}