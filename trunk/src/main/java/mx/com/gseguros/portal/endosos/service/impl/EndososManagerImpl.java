package mx.com.gseguros.portal.endosos.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Tatri;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.endosos.service.EndososManager;

import org.apache.log4j.Logger;

public class EndososManagerImpl implements EndososManager
{
    private static Logger log        = Logger.getLogger(EndososManagerImpl.class); 
	private EndososDAO    endososDAO;

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
	public List<Tatri> obtPantallaAlvaro(Object cduno, Object cddos, Object cdtres, Object cdcuatro, Object cdcinco,
			Object cdseis, Object cdsiete, Object cdocho, Object cdnueve, Object cddiez) throws Exception
	{
		Map<String,Object>params=new LinkedHashMap<String,Object>(0);
		params.put("PV_CDUNO_I",cduno);
		params.put("PV_CDDOS_I",cddos);
		params.put("PV_CDTRES_I",cdtres);
		params.put("PV_CDCUATRO_I",cdcuatro);
		params.put("PV_CDCINCO_I",cdcinco);
		params.put("PV_CDSEIS_I",cdseis);
		params.put("PV_CDSIETE_I",cdsiete);
		params.put("PV_CDOCHO_I",cdocho);
		params.put("PV_CDNUEVE_I",cdnueve);
		params.put("PV_CDDIEZ_I",cddiez);
		log.debug("EndososManager obtPantallaAlvaro params: "+params);
		List<Tatri> lista=endososDAO.obtPantallaAlvaro(params);
		lista=lista!=null?lista:new ArrayList<Tatri>(0);
		log.debug("EndososManager obtPantallaAlvaro lista size: "+lista.size());
		for(Tatri t:lista)
		{
			log.debug("getCdatribu: "+t.getCdatribu());
			log.debug("getSwformat: "+t.getSwformat());
			log.debug("getNmlmin: "+t.getNmlmin());
			log.debug("getType: "+t.getType());
			log.debug("getNmlmax: "+t.getNmlmax());
			log.debug("getSwobliga: "+t.getSwobliga());
			log.debug("getDsatribu: "+t.getDsatribu());
			log.debug("getOttabval: "+t.getOttabval());
			log.debug("getCdtablj1: "+t.getCdtablj1());
			log.debug("getMapa: "+t.getMapa());
		}
		return lista;
	}
	
	/////////////////////////////////
	////// getters and setters //////
	/*/////////////////////////////*/
	public void setEndososDAO(EndososDAO endososDAO) {
		this.endososDAO = endososDAO;
	}
	
}