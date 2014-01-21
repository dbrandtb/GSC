package mx.com.gseguros.portal.general.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Tatri;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.service.PantallasManager;

import org.apache.log4j.Logger;

public class PantallasManagerImpl implements PantallasManager
{
	private static Logger log = Logger.getLogger(PantallasManagerImpl.class);
	
	private PantallasDAO  pantallasDAO;
	
	@Override
	public List<Tatri> obtenerCamposPantalla(
			 Object cduno   , Object cddos
			,Object cdtres  , Object cdcuatro
			,Object cdcinco , Object cdseis
            ,Object cdsiete , Object cdocho
            ,Object cdnueve , Object cddiez
            ) throws Exception
	{
		Map<String,Object>params=new LinkedHashMap<String,Object>(0);
		params.put("PV_CDUNO_I"    , cduno);
		params.put("PV_CDDOS_I"    , cddos);
		params.put("PV_CDTRES_I"   , cdtres);
		params.put("PV_CDCUATRO_I" , cdcuatro);
		params.put("PV_CDCINCO_I"  , cdcinco);
		params.put("PV_CDSEIS_I"   , cdseis);
		params.put("PV_CDSIETE_I"  , cdsiete);
		params.put("PV_CDOCHO_I"   , cdocho);
		params.put("PV_CDNUEVE_I"  , cdnueve);
		params.put("PV_CDDIEZ_I"   , cddiez);
		log.debug("EndososManager obtenerCamposPantalla params: "+params);
		
		List<Tatri> lista=pantallasDAO.obtenerCamposPantalla(params);
		lista=lista!=null?lista:new ArrayList<Tatri>(0);
		log.debug("EndososManager obtenerCamposPantalla lista size: "+lista.size());
		
		return lista;
	}
	
	@Override
	public List<Map<String, String>> obtenerParametrosPantalla(
			 Object cduno   , Object cddos
			,Object cdtres  , Object cdcuatro
			,Object cdcinco , Object cdseis
			,Object cdsiete , Object cdocho
			,Object cdnueve , Object cddiez
			) throws Exception
    {
		Map<String,Object>params=new LinkedHashMap<String,Object>(0);
		params.put("PV_CDUNO_I"    , cduno);
		params.put("PV_CDDOS_I"    , cddos);
		params.put("PV_CDTRES_I"   , cdtres);
		params.put("PV_CDCUATRO_I" , cdcuatro);
		params.put("PV_CDCINCO_I"  , cdcinco);
		params.put("PV_CDSEIS_I"   , cdseis);
		params.put("PV_CDSIETE_I"  , cdsiete);
		params.put("PV_CDOCHO_I"   , cdocho);
		params.put("PV_CDNUEVE_I"  , cdnueve);
		params.put("PV_CDDIEZ_I"   , cddiez);
		log.debug("EndososManager obtenerParametrosPantalla params: "+params);
		List<Map<String,String>> lista=pantallasDAO.obtenerParametrosPantalla(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerParametrosPantalla lista size: "+lista.size());
		return lista;
    }
	
	@Override
	public void borrarParametrosPantalla(
			 Object cduno   , Object cddos
			,Object cdtres  , Object cdcuatro
			,Object cdcinco , Object cdseis
			,Object cdsiete , Object cdocho
			,Object cdnueve , Object cddiez
			) throws Exception
    {
		Map<String,Object>params=new LinkedHashMap<String,Object>(0);
		params.put("PV_CDUNO_I"    , cduno);
		params.put("PV_CDDOS_I"    , cddos);
		params.put("PV_CDTRES_I"   , cdtres);
		params.put("PV_CDCUATRO_I" , cdcuatro);
		params.put("PV_CDCINCO_I"  , cdcinco);
		params.put("PV_CDSEIS_I"   , cdseis);
		params.put("PV_CDSIETE_I"  , cdsiete);
		params.put("PV_CDOCHO_I"   , cdocho);
		params.put("PV_CDNUEVE_I"  , cdnueve);
		params.put("PV_CDDIEZ_I"   , cddiez);
		log.debug("EndososManager borrarParametrosPantalla params: "+params);
		pantallasDAO.borrarParametrosPantalla(params);
		log.debug("EndososManager borrarParametrosPantalla end");
    }
	
	@Override
	public void insertarParametrosPantalla(Map<String,String> params) throws Exception
	{
		log.debug("EndososManager insertarParametrosPantalla params: "+params);
		pantallasDAO.insertarParametrosPantalla(params);
		log.debug("EndososManager insertarParametrosPantalla end");
	}
	
	@Override
	public List<Map<String, String>> obtenerArbol() throws Exception
    {
		log.debug("EndososManager obtenerArbol inicio");
		List<Map<String,String>> lista=pantallasDAO.obtenerArbol();
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerArbol lista size: "+lista.size());
		return lista;
    }

	///////////////////////////////
	////// getters y setters //////
	/*///////////////////////////*/
	public PantallasDAO getPantallasDAO() {
		return pantallasDAO;
	}

	public void setPantallasDAO(PantallasDAO pantallasDAO) {
		this.pantallasDAO = pantallasDAO;
	}
	/*///////////////////////////*/
	////// getters y setters //////
	///////////////////////////////
}