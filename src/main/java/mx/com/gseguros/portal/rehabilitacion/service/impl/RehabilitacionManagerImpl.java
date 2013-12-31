package mx.com.gseguros.portal.rehabilitacion.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.rehabilitacion.dao.RehabilitacionDAO;
import mx.com.gseguros.portal.rehabilitacion.service.RehabilitacionManager;

import org.apache.log4j.Logger;

public class RehabilitacionManagerImpl implements RehabilitacionManager
{
	private static Logger     log               = Logger.getLogger(RehabilitacionManagerImpl.class);
	private RehabilitacionDAO rehabilitacionDAO;

	@Override
	public List<Map<String, String>> buscarPolizas(Map<String, String> params) throws Exception {
		log.debug("RehabilitacionManager buscarPolizas params: "+params);
		List<Map<String,String>> lista=rehabilitacionDAO.buscarPolizas(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("RehabilitacionManager buscarPolizas lista size: "+lista.size());
		return lista;
	}

	@Override
	public void rehabilitarPoliza(Map<String, String> params) throws Exception
	{
		log.debug("RehabilitacionManager rehabilitarPoliza params: "+params);
		rehabilitacionDAO.rehabilitarPoliza(params);
		log.debug("RehabilitacionManager rehabilitarPoliza end");
	}
	
	/////////////////////////////////
	////// getters and setters //////
	/*/////////////////////////////*/
	public void setRehabilitacionDAO(RehabilitacionDAO rehabilitacionDAO) {
		this.rehabilitacionDAO = rehabilitacionDAO;
	}
	
}
