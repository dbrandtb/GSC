package mx.com.gseguros.mesacontrol.service;

import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Item;

public interface FlujoMesaControlManager
{
	
	public Map<String,Item> workflow(
			StringBuilder sb
			,String cdsisrol
			) throws Exception;
	
}