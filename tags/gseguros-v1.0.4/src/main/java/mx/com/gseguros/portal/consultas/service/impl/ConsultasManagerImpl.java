package mx.com.gseguros.portal.consultas.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.consultas.service.ConsultasManager;
import mx.com.gseguros.portal.general.util.ObjetoBD;

import org.apache.log4j.Logger;

public class ConsultasManagerImpl implements ConsultasManager
{
	private static Logger logger = Logger.getLogger(ConsultasManagerImpl.class);
	
	private ConsultasDAO consultasDAO;
	
	@Override
	public List<Map<String,String>> consultaDinamica(ObjetoBD objetoBD,LinkedHashMap<String,Object>params) throws Exception
	{
		logger.debug(""
				+ "\n##############################"
				+ "\n###### consultaDinamica ######"
				);
		logger.debug("storedProcedure: "+ objetoBD.getNombre());
		logger.debug("params: "+params);
		List<Map<String,String>> lista = consultasDAO.consultaDinamica(objetoBD.getNombre(), params);
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug("lista: "+lista);
		logger.debug(""
				+ "\n###### consultaDinamica ######"
				+ "\n##############################"
				);
		return lista;
	}
	
	///////////////////////////////
	////// getters y setters //////
	///////////////////////////////
	public void setConsultasDAO(ConsultasDAO consultasDAO)
	{
		this.consultasDAO = consultasDAO;
	}
}