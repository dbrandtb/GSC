package mx.com.gseguros.portal.endosos.service;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.SlistSmapVO;

public interface EndososAutoManager
{
	public Map<String,Item> construirMarcoEndosos(String cdsisrol)throws Exception;
	
	public String recuperarColumnasIncisoRamo(String cdramo) throws Exception;
	
	public SlistSmapVO recuperarEndososClasificados(
			String cdramo
			,String nivel
			,String multiple
			,String tipoflot
			,List<Map<String,String>>incisos
			,String cdsisrol
			)throws Exception;
}