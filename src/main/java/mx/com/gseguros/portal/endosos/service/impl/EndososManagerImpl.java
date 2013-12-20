package mx.com.gseguros.portal.endosos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.endosos.service.EndososManager;

import org.apache.log4j.Logger;

public class EndososManagerImpl implements EndososManager
{
    private static Logger log = Logger.getLogger(EndososManagerImpl.class);
    
	private EndososDAO endososDAO;

	@Override
	public List<Map<String, String>> obtenerEndosos(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager obtenerEndosos params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerEndosos(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerEndosos lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public Map<String, String> guardarEndosoNombres(Map<String, Object> params) throws Exception
	{
		log.debug("EndososManager guardarEndosoNombres params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoNombres(params);
		log.debug("EndososManager guardarEndosoNombres response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String, String> confirmarEndosoB(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager confirmarEndosoB params: "+params);
		Map<String,String> mapa=endososDAO.confirmarEndosoB(params);
		log.debug("EndososManager confirmarEndosoB response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String, String> guardarEndosoDomicilio(Map<String, Object> params) throws Exception
	{
		log.debug("EndososManager guardarEndosoDomicilio params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoNombres(params);
		log.debug("EndososManager guardarEndosoDomicilio response map: "+mapa);
        return mapa;
	}
	
	@Override
	public List<Map<String, String>> reimprimeDocumentos(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager reimprimeDocumentos params: "+params);
		List<Map<String,String>> lista=endososDAO.reimprimeDocumentos(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager reimprimeDocumentos lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public List<Map<String, String>> obtieneCoberturasDisponibles(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager obtieneCoberturasDisponibles params: "+params);
		List<Map<String,String>> lista=endososDAO.obtieneCoberturasDisponibles(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtieneCoberturasDisponibles lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public Map<String, String> guardarEndosoCoberturas(Map<String, Object> params) throws Exception
	{
		log.debug("EndososManager guardarEndosoCoberturas params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoCoberturas(params);
		log.debug("EndososManager guardarEndosoCoberturas response map: "+mapa);
        return mapa;
	}
	
	@Override
	public List<Map<String, String>> obtenerAtributosCoberturas(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager obtenerAtributosCoberturas params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerAtributosCoberturas(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerAtributosCoberturas lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public Map<String,Object> sigsvalipolEnd(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager sigsvalipolEnd params: "+params);
		Map<String,Object> mapa=endososDAO.sigsvalipolEnd(params);
		log.debug("EndososManager sigsvalipolEnd response map: "+mapa);
        return mapa;
	}
	
	/////////////////////////////////
	////// getters and setters //////
	/*/////////////////////////////*/
	public void setEndososDAO(EndososDAO endososDAO) {
		this.endososDAO = endososDAO;
	}
	
}