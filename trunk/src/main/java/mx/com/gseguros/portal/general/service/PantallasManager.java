package mx.com.gseguros.portal.general.service;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;

public interface PantallasManager
{
	/*
	public List<Tatri> obtenerCamposPantalla(
			 Object cdunieco , Object cdramo
	        ,Object cdtipsit , Object estado
			,Object nmpoliza , Object nmsuplem
			,Object pantalla , Object rol
			,Object orden    , Object componente
           ) throws Exception;
    */
	
	/**
	 * PKG_CONF_PANTALLAS.P_GET_TCONFCMP
	 */
	public List<ComponenteVO> obtenerComponentes(
			 String cdtiptra
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String cdsisrol
			,String pantalla
			,String seccion
			,String orden
			) throws Exception;
	
	/*
	public List<Map<String, String>> obtenerParametrosPantalla(
			 Object cdunieco , Object cdramo
	        ,Object cdtipsit , Object estado
			,Object nmpoliza , Object nmsuplem
			,Object pantalla , Object rol
			,Object orden    , Object componente
           ) throws Exception;
    */
	
	/**
	 * PKG_CONF_PANTALLAS.P_GET_TCONFCMP
	 */
	public List<Map<String, String>> obtenerParametros(
			 String cdtiptra
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String cdsisrol
			,String pantalla
			,String seccion
			,String orden
			) throws Exception;
	/*
	public void borrarParametrosPantalla(
			 Object cdunieco , Object cdramo
	        ,Object cdtipsit , Object estado
			,Object nmpoliza , Object nmsuplem
			,Object pantalla , Object rol
			,Object orden    , Object componente
           ) throws Exception;
	*/
	/**
	 * PKG_CONF_PANTALLAS.P_BORRAR_TCONFCMP
	 */
	public void borrarParametros(
			 String cdtiptra
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String cdsisrol
			,String pantalla
			,String seccion
			,String orden
			) throws Exception;
	//public void                     insertarParametrosPantalla (Map<String,String> params) throws Exception;
	/**
	 * PKG_CONF_PANTALLAS.P_INSERTA_TCONFCMP
	 */
	public void                     insertarParametros         (Map<String,String> params) throws Exception;
	/**
	 * PKG_CONF_PANTALLAS.P_OBT_ARBOL_TCONFCMP
	 */
	public Item                     obtenerArbol               ()                          throws Exception;
}