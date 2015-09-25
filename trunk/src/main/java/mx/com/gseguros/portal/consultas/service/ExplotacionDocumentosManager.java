package mx.com.gseguros.portal.consultas.service;

import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Item;

public interface ExplotacionDocumentosManager
{
	
	public Map<String,Item> pantallaExplotacionDocumentos(String cdusuari, String cdsisrol) throws Exception;
	
}