package mx.com.gseguros.portal.general.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.gseguros.portal.cotizacion.model.Item;
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
	public Item obtenerArbol() throws Exception
    {
		log.debug("EndososManager obtenerArbol inicio");
		
		//obtener registros
		List<Map<String,String>> lista=pantallasDAO.obtenerArbol();
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerArbol lista size: "+lista.size());
		
		String pantallaKey   = "CDSIETE";
		String componenteKey = "CDDIEZ";
		
		//poner llaves (pantalla) y hojas (componentes)
		Map<String,List<String>>arbol=new LinkedHashMap<String,List<String>>();
		for(Map<String,String>registroIte:lista)
		{
			String pantallaIte   = registroIte.get(pantallaKey);
			String componenteIte = registroIte.get(componenteKey);
			
			//poner llave
			if(!arbol.containsKey(pantallaIte))
			{
				arbol.put(pantallaIte,new ArrayList<String>());
			}
			
			//poner hoja
			arbol.get(pantallaIte).add(componenteIte);
		}
		
		log.debug("EndososManager obtenerArbol arbol map: "+arbol);
		
		Item iArbol=new Item("root",null,Item.OBJ);
		iArbol.add("expanded",true);
		
		Item iArbolChildren=new Item("children",null,Item.ARR);
		
		iArbol.add(iArbolChildren);
		
		for(Entry<String,List<String>>pantallaIte:arbol.entrySet())
		{
			log.debug("pantallaIte: "+pantallaIte);
			
			String      nombrePantallaIte      = pantallaIte.getKey();
			List<String>pantallaItecomponentes = pantallaIte.getValue();
			
			log.debug("nombrePantallaIte: "      + nombrePantallaIte);
			log.debug("pantallaItecomponentes: " + pantallaItecomponentes);
			
			Item iPantallaIte=new Item(null,null,Item.OBJ);
			
			iArbolChildren.add(iPantallaIte);
			
			iPantallaIte.add("expanded" , false);
			iPantallaIte.add("text"     , nombrePantallaIte);
			
			if(pantallaItecomponentes!=null&&pantallaItecomponentes.size()>0)
			{
				Item iPantallaIteChildren=new Item("children",null,Item.ARR);
				
				iPantallaIte.add(iPantallaIteChildren);
				
				for(String pantallaItecomponenteIte:pantallaItecomponentes)
				{
					log.debug("pantallaItecomponenteIte: "+pantallaItecomponenteIte);
					
					if(pantallaItecomponenteIte!=null)
					{
						Item iPantallaIteComponenteIte=new Item(null,null,Item.OBJ);
						
						iPantallaIteChildren.add(iPantallaIteComponenteIte);
						
						iPantallaIteComponenteIte.add("text" , pantallaItecomponenteIte);
						iPantallaIteComponenteIte.add("leaf" , true);
					}
				}
			}			
		}
		
		log.debug("EndososManager obtenerArbol arbol item: "+iArbol);
		
		return iArbol;
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