package mx.com.gseguros.portal.general.service;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Tatri;

public interface PantallasManager
{
	public List<Tatri> obtenerCamposPantalla(
			 Object cduno   , Object cddos
	        ,Object cdtres  , Object cdcuatro
			,Object cdcinco , Object cdseis
			,Object cdsiete , Object cdocho
			,Object cdnueve , Object cddiez
           ) throws Exception;
	
	public List<Map<String, String>> obtenerParametrosPantalla(
			 Object cduno   , Object cddos
			,Object cdtres  , Object cdcuatro
			,Object cdcinco , Object cdseis
			,Object cdsiete , Object cdocho
			,Object cdnueve , Object cddiez
           ) throws Exception;
	
	public void borrarParametrosPantalla(
			 Object cduno   , Object cddos
			,Object cdtres  , Object cdcuatro
			,Object cdcinco , Object cdseis
			,Object cdsiete , Object cdocho
			,Object cdnueve , Object cddiez
			) throws Exception;
	
	public void insertarParametrosPantalla(Map<String,String> params) throws Exception;
}