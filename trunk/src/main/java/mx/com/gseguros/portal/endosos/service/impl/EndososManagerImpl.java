package mx.com.gseguros.portal.endosos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
	public void setEndososDAO(EndososDAO endososDAO) {
		this.endososDAO = endososDAO;
	}
	
}