package mx.com.gseguros.portal.general.dao;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Tatri;

public interface PantallasDAO
{
	public List<Tatri>              obtenerCamposPantalla      (Map<String,Object> params) throws Exception;
	public List<Map<String,String>> obtenerParametrosPantalla  (Map<String,Object> params) throws Exception;
	public void                     borrarParametrosPantalla   (Map<String,Object> params) throws Exception;
	public void                     insertarParametrosPantalla (Map<String,String> params) throws Exception;
}