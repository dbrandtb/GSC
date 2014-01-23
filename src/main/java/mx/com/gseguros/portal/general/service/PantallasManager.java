package mx.com.gseguros.portal.general.service;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.Tatri;

public interface PantallasManager
{
	public List<Tatri> obtenerCamposPantalla(
			 Object cdunieco , Object cdramo
	        ,Object cdtipsit , Object estado
			,Object nmpoliza , Object nmsuplem
			,Object pantalla , Object rol
			,Object orden    , Object componente
           ) throws Exception;
	
	public List<Map<String, String>> obtenerParametrosPantalla(
			 Object cdunieco , Object cdramo
	        ,Object cdtipsit , Object estado
			,Object nmpoliza , Object nmsuplem
			,Object pantalla , Object rol
			,Object orden    , Object componente
           ) throws Exception;
	
	public void borrarParametrosPantalla(
			 Object cdunieco , Object cdramo
	        ,Object cdtipsit , Object estado
			,Object nmpoliza , Object nmsuplem
			,Object pantalla , Object rol
			,Object orden    , Object componente
           ) throws Exception;
	
	public void                     insertarParametrosPantalla (Map<String,String> params) throws Exception;
	public Item                     obtenerArbol               ()                          throws Exception;
}